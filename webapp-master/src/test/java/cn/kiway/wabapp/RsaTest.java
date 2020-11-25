package cn.kiway.wabapp;

import cn.kiway.webapp.util.AESECBUtil;
import cn.kiway.webapp.util.RSAKey;
import cn.kiway.webapp.util.RSAUtils;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class RsaTest {

    @Test
    public void test(){
        RSAKey rsaKey = RSAUtils.createKeys(512);
        System.out.println(rsaKey.getPrivateKey());
        System.out.println("----------------->");
        System.out.println(rsaKey.getPublicKey());


    }

    @Test
    public void encodeAesKey(){
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIjJ-5lDlQCUDqe7HmAftUx0uOGe2qsmZpSJjletUBNgyRbMkB4Qp4lIqFPdzEFwsKp0OWyDWr6qucKZHlwWitsCAwEAAQ";
        String aeskey = "123456";

        try {
            System.out.println(RSAUtils.publicEncrypt(aeskey,RSAUtils.getPublicKey(publicKey)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() throws Exception{
        String privateKey = "MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAiMn7mUOVAJQOp7seYB-1THS44Z7aqyZmlImOV61QE2DJFsyQHhCniUioU93MQXCwqnQ5bINavqq5wpkeXBaK2wIDAQABAkBp202evQMpUGrK-O632rH1-fo9eC6m9m-6EX2bcl8ersy7FHbM0MY3QoZ6eMqU5S7xS9BwYAWE16ngvEvGNXuBAiEAxdBCx4xtK2mofEQmZZ7_1Ogu_CjKmlC4fof-S0aQjZsCIQCxBnUuASCipEiNRgHZE2Ce_kSGHafAeHWglo5szoRrwQIgO44W_OMPXxmha0BNWx0CowQAWVSiotEYqqeg7OLi1G8CIGQaFbsM8ipmIYyZcMQSfAmMGK2KKWjh9Qn4-NK4ORNBAiAir1ghpa1pqHkF1h8bIK07-uxobA8oNdbKhyYm_kyfjg";
        String a = "BM5C1MijX-T3odpMjhod-78W6ATS-SxLU5WZL_fsIeNvb9DSpEcKDCFF-pvP_OYBPsDXRzP6XtWCnatqPL0DNg";
        System.out.println(RSAUtils.privateDecrypt(a,RSAUtils.getPrivateKey(privateKey)));
    }

    @Test
    //aesk=k8ndKREIgCXpw5CFWoR-8hF67mpSvEWrRk0izsMERosHk8GxCKro_TvXlMc47d1u19t0A1qaHaulGqNERtpTMA
    public void aesKeyEn(){
        String aesk ="123456";
        String data = "{'username':'jay'}";

        try {
            System.out.println(AESECBUtil.encrypt(data,aesk));
            //加密后的数据z6JBkAaUHzAdMMsuegS3x8aJbPR/eQ94rZL/+D/Hu+o=
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2() throws Exception{
        String aesk ="123456";
        String decrypt = AESECBUtil.decrypt("z6JBkAaUHzAdMMsuegS3x8aJbPR/eQ94rZL/+D/Hu+o=", aesk);
        System.out.println(decrypt);
    }


}
