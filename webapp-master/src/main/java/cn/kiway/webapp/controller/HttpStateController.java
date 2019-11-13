package cn.kiway.webapp.controller;

import cn.kiway.webapp.model.HttpStateModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 404 500 403 等状态处理
 * @author mitne
 *
 */
@Controller
@RequestMapping("httpState")
public class HttpStateController {

	@RequestMapping(value="{httpStateCode}/dealWith")
	@ResponseBody
	public HttpStateModel dealWith(HttpStateModel stateModel){
		return  stateModel;
	}
	

}
