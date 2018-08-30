package netty.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/29
 */
@Data
public class SubscribeRespDTO implements Serializable {
    private String messageId;
    /**
     * 0：失败，1：成功
     */
    private Integer respCode;
    private String description;
}
