package com.traffic.weizhang.strategy;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class SuppplierContext {

	private AbstractSuppplier supplier;
	
	public SuppplierContext(AbstractSuppplier supplier) {
		this.supplier = supplier;
	}
	
	/**
	 * 根据不同的供应商构建不同的请求对象
	 */
	public JSONObject executeQuery(JSONObject reqJsonBody,String interUrl) {
		return supplier.executeQuery(reqJsonBody,interUrl);
	}
	
	/**
	 * 查询供应商支持查询的城市列表
	 */
	public Map<String, String> executeQueryCities(String interUrl) {
		 return supplier.executeQueryCities(interUrl);
	}
}
