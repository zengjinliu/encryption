package cn.kiway.webapp.constant;

/**
 * @author minte
 * @date 2019/9/21 14:29
 */
public class ActionConstant {
    /**
     * json 加密
     */
    public static final boolean RESULT_JSON_ENCRYPTION = true;

    /**
     * json 不加密
     */
    public static final boolean RESULT_JSON_NOT_ENCRYPTION = false;

    /**
     * 是否加密的key
     */
    public static final String RESULT_JSON_ENCRYPTION_ATT = "json_is_encode_att";
    /**
     *  aes key 的key
     */
    public static final  String RESULT_JSON_ENCRYPTION_AES_KEY_ATT = "json_encode_aes_key_att";

}
