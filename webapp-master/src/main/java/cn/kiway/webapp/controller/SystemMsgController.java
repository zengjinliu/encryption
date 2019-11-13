package cn.kiway.webapp.controller;

import cn.kiway.webapp.model.ResultJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统级提示
 * @author minte
 * @date 2019/11/5 17:13
 */
@Controller
@RequestMapping("/sys")
public class SystemMsgController {


    @RequestMapping(value = "/illegalAction")
    @ResponseBody
    public ResultJson<String> illegalAction(){

        return  ResultJson.UNAUTHORIZED;
    }




}
