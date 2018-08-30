package netty.demo.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/25
 */
public class SubscribeServer {

    private static final EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(16);

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup acceptor = new NioEventLoopGroup();    //创建EventLoopGroup
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(acceptor, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(8080))  //配置监听的端口
                    .childHandler(new ChannelInitializer<SocketChannel>() {     //添加一个EchoServerHandler到子channel的chanelPipeline
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //解码器
                            socketChannel.pipeline().addLast(new ObjectDecoder(1024 * 1024,
                                    ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())));
                            //编码器
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            //业务Handlers
                            socketChannel.pipeline().addLast(new TimeConsumeHandler());
                            //socketChannel.pipeline().addLast(businessGroup, "timeConsumeHandler", new TimeConsumeHandler());
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });
            //ChannelFuture future = serverBootstrap.bind().sync();
            ChannelFuture future = serverBootstrap.bind();
            future.addListener((ChannelFutureListener) channelFuture -> System.out.println("SubscribeServer running..."));
            future.channel().closeFuture().sync();
        } finally {
            acceptor.shutdownGracefully().sync();
            workGroup.shutdownGracefully().sync();
        }
    }

}
