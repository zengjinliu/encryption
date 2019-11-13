# encryption 该工具包含aes加密rsa加密

/**
 * 全局拦截器，解密 Parameters。<br>
 * 该拦截器用来解密参数，并且重构参数,其大致思路如下<br>
 * 一对rsa公钥私钥，约定为AES对称式加密方案。公钥在客户端保存，私钥在服务端保存<br>
 * 客户端<br>
 * 1、客户端代码生成一个随机码为aesKey<br>
 * 2、用aesKey对请求的参数进行AES加密<br>
 * 3、利用公钥对aesKey进行rsa加密<br>
 * 4、将加密后的aesKey放在header中发起请求<br>
 * 服务端<br>
 * 5、服务端接收到请求，拦截其中取到加密的aesKey，利用私钥解密得到aesKey<br>
 * 6、利用aesKey对请求参数的所有value解密<br>
 * 7、返回数据时，再利用aesKey对返回的json串加密<br>
 * 客户端<br>
 * 8、客户端接收到数据时，用自己生成的aesKey进行解密<br>
 * 注意：因为使用setAttribute往下传值，而视图层需要使用aesKey 加密，所以，使用加密模式时，下层不可以重定向，只能请求分派。
 * 一旦重定向，则无法加密返回参数。
 */
