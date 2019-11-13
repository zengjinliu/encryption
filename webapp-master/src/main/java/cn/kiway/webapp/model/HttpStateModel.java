package cn.kiway.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author minte
 * @date 2019/9/23 23:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpStateModel implements JacksonEncode {

    /**
     * 错误状态码
     */
    private String httpStateCode;
    /**
     * 错误信息
     */
    private String msg;


}
