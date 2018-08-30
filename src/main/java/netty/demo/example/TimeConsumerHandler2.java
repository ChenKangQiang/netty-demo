package netty.demo.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/30
 */
public class TimeConsumerHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("耗时业务处理2秒");
            ctx.fireChannelRead(msg);
    }

}
