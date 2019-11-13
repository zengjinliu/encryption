package cn.kiway.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 周晋平
 * @date 2019/6/26 10:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultJson<E> {
    /**
     * 业务成功
     */
    public static final String STATUS_TYPE_SUCCEED = "200";

    /**
     * 业务失败
     */
    public static final String STATUS_TYPE_BUST = "300";

    /**
     * 系统异常
     */
    public static final String STATUS_TYPE_ERROR = "500";

    /**
     * 非法请求
     */
    public static final String MSG_ILLEGAL_ACTION = "ILLEGAL_ACTION";
    /**
     * 系统异常 信息提示
     */
    public static final String MSG_SYS_ERROR = "SYS_ERROR";

    /**
     * 请登陆 提示信息
     */
    public static final ResultJson<String> PLEASE_LOGIN =new ResultJson<>(STATUS_TYPE_BUST, "PLEASE_LOGIN", null) ;

    /**
     * 系统异常提示
     */
    public static final ResultJson<String> ERROR_JSON = new ResultJson<>(STATUS_TYPE_ERROR, MSG_SYS_ERROR, null);
    /**
     * 非法请求提示
     */
    public static final  ResultJson<String> UNAUTHORIZED =new ResultJson<>(STATUS_TYPE_BUST, MSG_ILLEGAL_ACTION, null) ;

    //状态 1业务调用成功；-1业务调用失败；-2 系统异常

    private String statusCode;
    //提示信息

    private String msg;
    //返回数据

    private E data;


}
