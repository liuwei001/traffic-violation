package com.traffic.weizhang.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traffic.common.base.BaseController;
import com.traffic.common.constants.Constants;
import com.traffic.common.message.ResponseMessage;

/**
 * 短信验证码controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/validatemsg")
public class ValidateMsgController extends BaseController {
	
	private final Logger logger = Logger.getLogger(ValidateMsgController.class);
	
	/**
	 * 短信验证码失效时间 单位:分钟
	 */
	@Value("${msg.validatecode.timeout}")
	private String vcode_timeout;

	/**
	 * 发送验证码
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/send",method = RequestMethod.GET)
	public ResponseMessage sendValidateMsg(HttpServletRequest request) {
		long validatecode = (int)((Math.random()*9+1)*100000);
		logger.info("validatecode = " + validatecode);
		Long[] vcode_info = new Long[]{validatecode,new Date().getTime()};
		request.getSession().setAttribute(Constants.VALIDATE_CODE, vcode_info);
		return getResponseMsg_success(validatecode);
	}
	
	/**
	 * 检查验证码
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/check/{validatecode}",method = RequestMethod.GET)
	public ResponseMessage checkValidateMsg(HttpServletRequest request,@PathVariable long validatecode) {
		Long[] vcode_info = (Long[])request.getSession().getAttribute(Constants.VALIDATE_CODE);
		if(vcode_info == null) {
			ResponseMessage resMsg = new ResponseMessage();
			resMsg.setResultCode("1100001");
			resMsg.setResultMsg("验证码失效");
			return resMsg;
		}
		long timeout = 3*60*1000; //默认3分钟
		if(StringUtils.isNotEmpty(vcode_timeout)) {
			timeout = Long.valueOf(vcode_timeout)*60*1000;
		}
		long nowTime = new Date().getTime();
		long sendTime = vcode_info[1];
		if(nowTime - sendTime > timeout) {
			ResponseMessage resMsg = new ResponseMessage();
			resMsg.setResultCode("1100001");
			resMsg.setResultMsg("验证码失效");
			return resMsg;
		}
		
		long sendValidatecode = vcode_info[0];
		if(validatecode != sendValidatecode) {
			ResponseMessage resMsg = new ResponseMessage();
			resMsg.setResultCode("1100002");
			resMsg.setResultMsg("验证码错误");
			return resMsg;
		}
		
		return getResponseMsg_success("验证成功");
	}
}
