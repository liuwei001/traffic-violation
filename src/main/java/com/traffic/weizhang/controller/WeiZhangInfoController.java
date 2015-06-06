package com.traffic.weizhang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.traffic.common.base.BaseController;
import com.traffic.common.configuration.PropertiesCfgHolder;
import com.traffic.common.enumcode.ResultCodeEnum;
import com.traffic.common.message.ResponseMessage;
import com.traffic.common.utils.MD5Encrypt;
import com.traffic.common.utils.http.HttpClientUtils;
import com.traffic.weizhang.entity.City;
import com.traffic.weizhang.entity.Province;

/**
 * 交通违章信息controller
 * @author Administrator
 *
 */
@Controller
public class WeiZhangInfoController extends BaseController {

	private final Logger logger = Logger.getLogger(WeiZhangInfoController.class);
	
	/**
	 * 查询城市
	 * @return
	 */
	@RequestMapping( value = "/citylist",method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage cityList() {
		
		String reqUrl = PropertiesCfgHolder.getProperty("weizhang.citys.interface");
		if(StringUtils.isEmpty(reqUrl)) {
			logger.error("cfg.properties属性文件中没有配置城市查询接口地址,配置参数为：weizhang.citys.interface");
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
		String respBody = HttpClientUtils.httpGet(reqUrl);
		
		Map<String, List<City>> citysMap = new HashMap<String, List<City>>();
		
		List<City> abcdList = new ArrayList<City>();
		List<City> efghList = new ArrayList<City>();
		List<City> ijklmList = new ArrayList<City>();
		List<City> nopqrList = new ArrayList<City>();
		List<City> stuvwList = new ArrayList<City>();
		List<City> xyzList = new ArrayList<City>();
		String abcd_text = "ABCD";
		String efgh_text = "EFGH";
		String ijklm_text = "IJKLM";
		String nopqr_text = "NOPQR";
		String stuvw_text = "STUVW";
		String xyz_text = "XYZ";
		try {
			if(StringUtils.isNotBlank(respBody)) {
				ResponseMessage respMsg = JSON.parseObject(respBody, ResponseMessage.class);
				if("0".equals(respMsg.getResultCode())) {
					List<Province> provinces = JSONArray.parseArray(JSON.toJSONString(respMsg.getResult()), Province.class);
					if(provinces != null && provinces.size() > 0) {
						for(Province province : provinces) {
							if(province.getCitys() != null) {
								for(City city : province.getCitys()) {
									String province_code = province.getProvince_code();
									String city_full_code = city.getCity_code();
									char start_char ;
									if(city_full_code.indexOf("_") > 0) {
										String city_code = city_full_code.substring(province_code.length() + 1);
										start_char = city_code.charAt(0);
									} else {
										start_char = city_full_code.charAt(0);
									}
									if(abcd_text.indexOf(start_char) > -1) {
										abcdList.add(city);
									} else if(efgh_text.indexOf(start_char) > -1) {
										efghList.add(city);
									} else if(ijklm_text.indexOf(start_char) > -1) {
										ijklmList.add(city);
									} else if(nopqr_text.indexOf(start_char) > -1) {
										nopqrList.add(city);
									} else if(stuvw_text.indexOf(start_char) > -1) {
										stuvwList.add(city);
									} else if(xyz_text.indexOf(start_char) > -1) {
										xyzList.add(city);
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
		
		citysMap.put("abcd", abcdList);
		citysMap.put("efgh", efghList);
		citysMap.put("ijklm", ijklmList);
		citysMap.put("nopqr", nopqrList);
		citysMap.put("stuvw", stuvwList);
		citysMap.put("xyz", xyzList);
		
		ResponseMessage resMsg = new ResponseMessage();
		resMsg.setResult(citysMap);
		
		return resMsg;
	}
	
	/**
	 * 查询违章列表
	 * @return
	 */
	@RequestMapping( value = "/querylist",method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryList(HttpServletRequest request) {
		JSONObject reqJsonBody = (JSONObject)request.getAttribute("reqBody");
		String reqUrl = PropertiesCfgHolder.getProperty("weizhang.query.interface");
		if(StringUtils.isEmpty(reqUrl)) {
			logger.error("cfg.properties属性文件中没有配置违章查询接口地址,配置参数为：violation.query.interface");
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
		try {
			reqJsonBody.put("appid", PropertiesCfgHolder.getProperty("weizhang.appid"));
			
			String appkey = PropertiesCfgHolder.getProperty("weizhang.appkey");
			String city = reqJsonBody.getString("city");
			String carno = reqJsonBody.getString("carno");
			String sign = MD5Encrypt.encrypt(city + carno + appkey);
			//设置签名参数
			reqJsonBody.put("sign", sign);
			
			String respBody = HttpClientUtils.httpPost_JSONObject(reqUrl, reqJsonBody);
			return JSON.parseObject(respBody, ResponseMessage.class);
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
		
	}
}
