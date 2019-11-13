package cn.kiway.webapp.util;

import lombok.Data;

/**
 * 存储RSA 的公钥和私钥
 * @author  minte
 * @date  2019/3/24 19:49
 */
@Data
public class RSAKey {
    private String publicKey;
    private String privateKey;


}
