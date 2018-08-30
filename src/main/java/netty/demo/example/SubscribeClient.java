package netty.demo.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/25
 */
public class SubscribeClient {
    private static final String host = "localhost";
    private static final int port = 8080;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup) //配置eventLoop线程池
                    .channel(NioSocketChannel.class)  //配置channel类型
                    .remoteAddress(new InetSocketAddress(host, port))  //配置远程连接地址
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {  //配置channelHandler链
                            //解码器
                            socketChannel.pipeline().addLast(new ObjectDecoder(1024 * 1024,
                                    ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                            //编码器
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            //业务Handlers
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("与服务端成功建立连接");
                }
            });
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}
