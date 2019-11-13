package cn.kiway.webapp.controller;


import cn.kiway.webapp.model.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 全局异常处理类
 *
 * @Author 刘玉祥
 * @Date 2019/3/16 11:06
 */


@Slf4j
@Order(1)
@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 验证框架错误提示
     *
     * @param validException 验证异常
     * @return
     * @auther minte
     * @date 2019/3/16 15:11
     */

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.OK)
    @Order(1)
    @ResponseBody
    public ResultJson<String> handlerValid(Exception validException) {

        ResultJson<String> rj = new ResultJson<String>();
        rj.setStatusCode(ResultJson.STATUS_TYPE_BUST);

        //提示信息
        if (validException instanceof MethodArgumentNotValidException) {
            rj.setMsg(getAllErrors(((MethodArgumentNotValidException) validException).getBindingResult()));
        } else {
            rj.setMsg(getAllErrors(((BindException) validException).getBindingResult()));
        }

        return rj;
    }

    /**
     * <br>全局异常处理 ，所有的异常处理，
     * <br> 500错误
     *
     * @param
     * @return
     * @date 2019/3/16 11:15
     */
    @ExceptionHandler(value = Exception.class)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(2)
    @ResponseBody
    public ResultJson<String> handlerException(Exception e) {

        //记录日志
        log.error(e.getMessage(), e);
        return ResultJson.ERROR_JSON;
    }

    /**
     * 获取校验错误信息
     *
     * @param result
     * @return
     */
    private String getAllErrors(BindingResult result) {
        StringBuffer sb = new StringBuffer();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage() + ",");
        }
        if (sb.length() > 1) {
            sb = sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
