package com.traffic.weizhang.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.traffic.common.message.ResponseMessage;
import com.traffic.common.utils.MD5Encrypt;
import com.traffic.common.utils.http.HttpClientUtils;
import com.traffic.weizhang.entity.City;
import com.traffic.weizhang.entity.Province;
import com.traffic.weizhang.entity.Supplier;

/**
 * 第一家供应商
 * @author Administrator
 *
 */
public class ZonghengcheSupplier extends AbstractSuppplierStrate {

	private final Logger logger = Logger.getLogger(ZonghengcheSupplier.class);
	
	/**
	 * 查询
	 */
	@Override
	public JSONObject executeQuery(JSONObject reqJsonBody,Supplier supplier) {
		
		String citycode = reqJsonBody.getString("city");
		
		String appkey = supplier.getAppKey();
		
		reqJsonBody.put("appid", supplier.getAppId());
		String carno = reqJsonBody.getString("carno");
		if(logger.isDebugEnabled()) {
			logger.debug("sign key : " + (citycode + carno + appkey));
		}
		String sign_md5 = MD5Encrypt.encrypt(citycode + carno + appkey,"UTF-8");
		if(logger.isDebugEnabled()) {
			logger.debug("sign md5 : " + sign_md5);
		}
		//设置签名参数
		reqJsonBody.put("sign", sign_md5);

		String respBody = HttpClientUtils.httpPost_JSONObject(supplier.getUrl(), reqJsonBody);
		
		JSONObject respObj = JSON.parseObject(respBody);
		
		return respObj;
	}

	@Override
	public Map<String, String> executeQueryCities(String interUrl) {
		Map<String, String> citiesMap = new HashMap<String, String>();		
		String respBody = HttpClientUtils.httpGet(interUrl);
		ResponseMessage respMsg = JSON.parseObject(respBody, ResponseMessage.class);
		if("0".equals(respMsg.getResultCode())) {
			List<Province> provinces = JSONArray.parseArray(JSON.toJSONString(respMsg.getResult()), Province.class);
			if(provinces != null && provinces.size() > 0) {
				for(Province province : provinces) {
					if(province.getCitys() != null) {
						for(City city : province.getCitys()) {
							citiesMap.put(city.getCity_name(), city.getCity_code());
						}
					}
				}
			}
		}
		return citiesMap;
	}
	
}
