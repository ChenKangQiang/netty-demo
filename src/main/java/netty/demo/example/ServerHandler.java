package netty.demo.example;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.dto.SubscribeReqDTO;
import netty.demo.dto.SubscribeRespDTO;

import java.util.concurrent.TimeUnit;


/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/25
 */
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //提交任务到EventLoop，该任务由EventLoop线程执行
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("添加自定义用户任务");
            }
        });

        //提交任务到EventLoop，该任务由EventExecutor执行，当没有指定EventExecutor时，由EventLoop线程执行
        ctx.executor().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("使用线程池执行用户自定义任务");
            }
        });

        //提交调度任务到EventLoop，该调度任务由EventExecutor执行，当没有指定EventExecutor时，由EventLoop线程执行
        ctx.executor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("添加自定义用户调度任务");
            }
        }, 6, 6, TimeUnit.SECONDS);

        SubscribeReqDTO reqDTO = (SubscribeReqDTO) msg;
        System.out.println("subscribeReqDTO:" + JSON.toJSONString(reqDTO));

        SubscribeRespDTO respDTO = new SubscribeRespDTO();
        respDTO.setDescription("success");
        respDTO.setMessageId(reqDTO.getMessageId());
        respDTO.setRespCode(1);
        ctx.writeAndFlush(respDTO);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
