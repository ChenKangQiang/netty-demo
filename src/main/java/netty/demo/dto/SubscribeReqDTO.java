package netty.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/29
 */
@Data
public class SubscribeReqDTO implements Serializable {
    private String messageId;
    private String userName;
    private String productName;
    private String phone;
    private String address;

}
