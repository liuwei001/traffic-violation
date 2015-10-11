package com.traffic.weizhang.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.traffic.common.base.BaseController;
import com.traffic.common.constants.Constants;
import com.traffic.common.enumcode.ResultCodeEnum;
import com.traffic.common.exception.DalException;
import com.traffic.common.message.ResponseMessage;
import com.traffic.common.utils.UUIDGenerator;
import com.traffic.weizhang.entity.TSubscribe;
import com.traffic.weizhang.service.ISubscribeService;

/**
 * 订阅
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/subscribe")
public class SubScribeController extends BaseController {

	@Autowired
	private ISubscribeService subscribeService;
	
	/**
	 * 保存订阅
	 * @param request
	 * @return
	 */
	@RequestMapping( value = "/save",method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage save(HttpServletRequest request) {
	
		JSONObject jsonObj = (JSONObject)request.getAttribute("reqBody");
		TSubscribe subscribe = JSONObject.toJavaObject(jsonObj, TSubscribe.class);
		subscribe.setId(UUIDGenerator.getUUID());
		subscribe.setCreateTime(new Date());
		
		Map<String, String> mapparam = new HashMap<String, String>();
		mapparam.put("mobile", subscribe.getMobile());
		mapparam.put("carno", subscribe.getCarno());
		List<TSubscribe> subscribeList = subscribeService.queryForCheck(mapparam);
		
		try {
			//保存最新的
			if(subscribeList != null && subscribeList.size() > 0) {
				ResponseMessage resMsg = new ResponseMessage();
				resMsg.setResultCode("2000001");
				resMsg.setResultMsg("已经订阅过，不需要重复订阅");
				return resMsg;
			} 
			
			subscribeService.saveEntity(subscribe);
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
		
		//移除验证码session
		request.getSession().removeAttribute(Constants.VALIDATE_CODE);
		
		return getResponseMsg_success(null);
	}
}
