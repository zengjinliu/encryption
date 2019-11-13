package cn.kiway.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 加密信息 ，包装返回的VO数据
 *
 * @author minte
 * @date 2019/11/5 21:13
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EncodeInfo {
    /**
     * 是否加密
     * <br>{@link  cn.kiway.webapp.constant.ActionConstant#RESULT_JSON_ENCRYPTION} 加密
     * <br>  {@link  cn.kiway.webapp.constant.ActionConstant#RESULT_JSON_NOT_ENCRYPTION} 不加密
     */
    private boolean encodeType;

    /**
     * AES密钥
     */
    private String aesKey;

    private Object vo;

    public boolean getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(boolean encodeType) {
        this.encodeType = encodeType;
    }
}
