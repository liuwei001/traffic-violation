package com.traffic.weizhang.strategy;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.traffic.weizhang.entity.Supplier;

public class SuppplierContext {

	private AbstractSuppplierStrate supplierStrate;
	
	public SuppplierContext(AbstractSuppplierStrate supplierStrate) {
		this.supplierStrate = supplierStrate;
	}
	
	/**
	 * 根据不同的供应商构建不同的请求对象
	 */
	public JSONObject executeQuery(JSONObject reqJsonBody,Supplier supplier) {
		return supplierStrate.executeQuery(reqJsonBody,supplier);
	}
	
	/**
	 * 查询供应商支持查询的城市列表
	 */
	public Map<String, String> executeQueryCities(String interUrl) {
		 return supplierStrate.executeQueryCities(interUrl);
	}
}
