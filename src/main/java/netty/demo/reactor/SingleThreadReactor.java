package netty.demo.reactor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.demo.example.ServerHandler;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/26
 */
public class SingleThreadReactor {

    public static void main(String[] args) throws InterruptedException {
        ServerHandler serverHandler = new ServerHandler();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(8080))
                    .childHandler(new ChannelInitializer<SocketChannel>() {     //添加一个EchoServerHandler到子channel的chanelPipeline
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}
