package com.traffic.weizhang.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * 供应商
 * @author Administrator
 *
 */
public class Supplier {

	private String description;
	
	private String paramKey;
	
	private String url;
	
	private List<CityCode> cityCodeList = new ArrayList<CityCode>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void addCityCode(CityCode citycode) {
		cityCodeList.add(citycode);
	}

	public List<CityCode> getCityCodeList() {
		return cityCodeList;
	}

	public void setCityCodeList(List<CityCode> cityCodeList) {
		this.cityCodeList = cityCodeList;
	}

}
