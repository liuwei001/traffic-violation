package com.traffic.weizhang.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.traffic.common.base.BaseController;
import com.traffic.common.constants.Constants;
import com.traffic.common.enumcode.ResultCodeEnum;
import com.traffic.common.exception.DalException;
import com.traffic.common.message.ResponseMessage;
import com.traffic.common.utils.UUIDGenerator;
import com.traffic.common.utils.http.HttpClientUtils;
import com.traffic.weizhang.entity.City;
import com.traffic.weizhang.entity.Province;
import com.traffic.weizhang.entity.Result;
import com.traffic.weizhang.entity.Supplier;
import com.traffic.weizhang.entity.Suppliers;
import com.traffic.weizhang.entity.TQueryHistory;
import com.traffic.weizhang.listener.SuppliersLoader;
import com.traffic.weizhang.service.IQueryHistoryService;
import com.traffic.weizhang.strategy.AbstractSuppplier;
import com.traffic.weizhang.strategy.SuppplierContext;

/**
 * 交通违章信息controller
 * @author Administrator
 *
 */
@Controller
public class WeiZhangController extends BaseController {

	private final Logger logger = Logger.getLogger(WeiZhangController.class);
	
	private static Map<String, List<City>> citysMap = new HashMap<String, List<City>>();
	
	@Autowired
	private IQueryHistoryService queryHistoryService;
	
	@Value("${weizhang.citys.interface}")
	private String citys_interface_url;
	
	/**
	 * 查询城市
	 * @return
	 */
	@RequestMapping( value = "/citylist",method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage cityList(HttpServletRequest request) {
		String reflushCityList = request.getParameter("reflush"); //当reflush参数为1时，重新查询接口，否则查询内存缓存
		if(citysMap == null || citysMap.keySet().size() == 0 || reflushCityList == "1") {
			if(StringUtils.isEmpty(citys_interface_url)) {
				logger.error("cfg.properties属性文件中没有配置城市查询接口地址,配置参数为：weizhang.citys.interface");
				return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
			}
			
			String respBody = HttpClientUtils.httpGet(citys_interface_url);

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
										
										if(citysMap.containsKey(String.valueOf(start_char))) {
											citysMap.get(String.valueOf(start_char)).add(city);
										} else {
											List<City> cityList = new ArrayList<City>();
											cityList.add(city);
											citysMap.put(String.valueOf(start_char), cityList);
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
			
		} 
		
		ResponseMessage resMsg = new ResponseMessage();
		resMsg.setResult(citysMap);
		
		return resMsg;
	}
	
	/**
	 * 查询历史车辆列表
	 * @return
	 */
	@RequestMapping( value = "/queryHistorylist",method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryHistorylist(HttpServletRequest request) {
		JSONObject reqJsonBody = (JSONObject)request.getAttribute("reqBody");
		
		if(!reqJsonBody.containsKey("mobile") || StringUtils.isEmpty(reqJsonBody.getString("mobile"))) {
			return getResponseMsg_failed(ResultCodeEnum.REQUEST_PARAM_ISNULL);			
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mobile", reqJsonBody.get("mobile"));
		List<TQueryHistory> historyList = null;
		try {
			historyList = queryHistoryService.queryList(param);
		} catch (DalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResponseMessage resMsg = new ResponseMessage();
			resMsg.setResultCode(ResultCodeEnum.SYSTEM_EXCEPTION.getResultCode());
			resMsg.setResultMsg(ResultCodeEnum.SYSTEM_EXCEPTION + ":" + e.getMessage());
			return resMsg;
		}
		
		//移除验证码session
		request.getSession().removeAttribute(Constants.VALIDATE_CODE);
		
		return getResponseMsg_success(historyList);
	}
	
	/**
	 * 查询违章列表
	 * @return
	 */
	@RequestMapping( value = "/querylist",method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryList(HttpServletRequest request) {
		JSONObject reqJsonBody = (JSONObject)request.getAttribute("reqBody");
		
		try {
			JSONArray jarray = new JSONArray();
			JSONObject resultObj = new JSONObject();
			
			if(!reqJsonBody.containsKey("supplier")) {
				return getResponseMsg_failed(ResultCodeEnum.REQUEST_PARAM_ISNULL);
			}
			
			//获取所有供应商遍历
			Suppliers  suppliers = SuppliersLoader.getSuppliers();
			if(suppliers != null && suppliers.getSupplierList() != null) {
				for(Supplier supplier : suppliers.getSupplierList()) {
					
					//根据参数supplier来选择运营商 比如 1： 运营商1  2：运营商2
					if(supplier.getCode().equals(reqJsonBody.get("supplier"))) {
						resultObj = queryResults(reqJsonBody,supplier);
						jarray.add(resultObj);
					}
				}
			}

			//保存查询历史记录
			TQueryHistory queryHistory = JSON.toJavaObject(reqJsonBody, TQueryHistory.class);
			queryHistory.setId(UUIDGenerator.getUUID());
			queryHistory.setCreateTime(new Date());
			
			Thread saveHistoryThead = new QueryHistoryThread(queryHistory);
			saveHistoryThead.start();
		    
			//移除验证码session
			request.getSession().removeAttribute(Constants.VALIDATE_CODE);
			
			return getResponseMsg_success(jarray);
		
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
	}
	
	
	/**
	 * 查询违章列表 --- 测试用
	 * @return
	 */
	@RequestMapping( value = "/querylistTest",method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryListTest(HttpServletRequest request) {
		JSONObject reqJsonBody = (JSONObject)request.getAttribute("reqBody");
		
		try {
			JSONArray jarray = new JSONArray();
			JSONObject resultObj = new JSONObject();
			
			//获取所有供应商遍历
			Suppliers  suppliers = SuppliersLoader.getSuppliers();
			if(suppliers != null && suppliers.getSupplierList() != null) {
				for(Supplier supplier : suppliers.getSupplierList()) {
					resultObj = queryResults(reqJsonBody,supplier);
					jarray.add(resultObj);
				}
			}
			
			return getResponseMsg_success(jarray);
		
		}catch(Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			return getResponseMsg_failed(ResultCodeEnum.SYSTEM_EXCEPTION);
		}
	}
	
	/**
	 * 查询结果
	 * @return
	 */
	private JSONObject queryResults(JSONObject reqJsonBodyParam,Supplier supplier) {
		
		JSONObject reqJsonBody = (JSONObject)reqJsonBodyParam.clone();
		
		long startTime = System.currentTimeMillis();
		
		JSONObject resultObj = new JSONObject();

		List<Result> resultList = new ArrayList<Result>();
		
		String cityStr = reqJsonBody.getString("city");
		String[] citys = cityStr.split("、");
		
		for(String cityName : citys) {
			
			if(logger.isDebugEnabled()) {
					logger.debug("citycode : " + cityName + ",url:" + supplier.getUrl() + ",classname:" + supplier.getClassname());
			}
			Map<String, String> citiesMap = SuppliersLoader.getCity_Supplier_Map().get(supplier.getCode());
			reqJsonBody.put("city", citiesMap.get(cityName));
			
			//根据类名反射策略对象，执行策略
			AbstractSuppplier instance;
			try {
				instance = (AbstractSuppplier) Class.forName(supplier.getClassname()).newInstance();
				SuppplierContext context = new SuppplierContext(instance);
				JSONObject respJsonBody = context.executeQuery(reqJsonBody,supplier.getUrl());
				
				if("0".equals(respJsonBody.getString("resultCode"))) {
					List<Result> _resultList = JSONArray.parseArray(respJsonBody.getString("result"), Result.class);
					if(_resultList != null && _resultList.size() > 0) {
						resultList.addAll(_resultList);
					}
				} else {
					return null;
				}
				
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} 
		}
		
		if(resultList.size() > 0) { 
			//通过hashset去重复
			HashSet<Result> h = new LinkedHashSet<Result>(resultList);  
			resultList.clear();  
			resultList.addAll(h);  
			//时间排序
			Collections.sort(resultList);
		}
		
		long endTime = System.currentTimeMillis();
		
		resultObj.put("supplierName", supplier.getName());//供应商名称
		resultObj.put("times", endTime - startTime);
		resultObj.put("wzlist", resultList);
		
		return resultObj;
	}
	
	
	/**
	 * 查询历史保存线程
	 * @author Administrator
	 *
	 */
	class QueryHistoryThread extends Thread{
		
		private TQueryHistory queryHistory;
		
		public  QueryHistoryThread(TQueryHistory queryHistory) {
			this.queryHistory = queryHistory;
		}

		@Override
		public void run() {
			try {
				Map<String, String> mapparam = new HashMap<String, String>();
				mapparam.put("mobile", queryHistory.getMobile());
				mapparam.put("carno", queryHistory.getCarno());
				List<TQueryHistory> historyList = queryHistoryService.queryForCheck(mapparam);
				
				//保存最新的
				if(historyList != null && historyList.size() > 0) {
					for(TQueryHistory history : historyList) {
						queryHistoryService.deleteByKey(history);
					}
				} 
				queryHistoryService.saveEntity(queryHistory);
				
			} catch (DalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("保存违章查询日志出错,错误：" + e.getMessage() );
			}
		}
		
	}
	
}
