package cn.kiway.webapp.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * key 值必须为16位
 * @author  minte
 */
public class AESCBCUtil {

    private static final String ENCODING = "UTF-8";

    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    /**
     * 填充向量
     */
    private static final String FILL_VECTOR = "1234560405060708";

    /**
     * 加密字符串
     *
     * @param content  字符串
     * @param password 密钥KEY
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {


        byte[] raw = hex2byte(password);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        Cipher cipher = null;

            cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(FILL_VECTOR.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] anslBytes = content.getBytes(ENCODING);
            byte[] encrypted = cipher.doFinal(anslBytes);
            return byte2hex(encrypted).toUpperCase();

    }

    /**
     * 解密
     *
     * @param content  解密前的字符串
     * @param password 解密KEY key 值必须为16位
     * @return
     */
    public static String decrypt(String content, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

            byte[] raw = hex2byte(password);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(FILL_VECTOR.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = hex2byte(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, ENCODING);

    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

}