package netty.demo.example;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.dto.SubscribeReqDTO;
import netty.demo.dto.SubscribeRespDTO;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/25
 */
@Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeRespDTO respDTO = (SubscribeRespDTO) msg;
        System.out.println("SubscribeRespDTO:" + JSON.toJSONString(respDTO));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SubscribeReqDTO reqDTO = new SubscribeReqDTO();
        reqDTO.setMessageId("1000000000001");
        reqDTO.setAddress("徐汇区xxx路xxx号");
        reqDTO.setPhone("13700000000");
        reqDTO.setProductName("Thinking in Java");
        reqDTO.setUserName("Tom");

        System.out.println("Subscribe send request! ");
        ctx.writeAndFlush(reqDTO);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
