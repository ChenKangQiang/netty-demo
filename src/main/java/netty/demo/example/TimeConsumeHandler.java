package netty.demo.example;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/28
 */
@Sharable
public class TimeConsumeHandler extends ChannelInboundHandlerAdapter {

    private static ExecutorService businessExecutor = Executors.newFixedThreadPool(10);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        businessExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("耗时业务处理2秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctx.fireChannelRead(msg);
        });
    }
}
