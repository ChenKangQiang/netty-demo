package netty.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: chenkangqiang
 * @Date: 2018/8/25
 */
@Data
@AllArgsConstructor
public class User implements Serializable {
    private String name;
    private Integer age;
}
