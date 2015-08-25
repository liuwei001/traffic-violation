package com.traffic.weizhang.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 供应商
 * @author Administrator
 *
 */
public class Supplier {

	/**
	 * 描述
	 */
	private String name;
	
	/**
	 * 执行的class路径
	 */
	private String classname;
	
	/**
	 * 接口url
	 */
	private String url;

	/**
	 * 调用该供应商的城市
	 */	
	private List<CityCode> cityCodeList = new ArrayList<CityCode>();
	
	/**
	 * citycode映射关系
	 */
	private Map<String,String> cityCodeMap = new HashMap<String, String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}


	public Map<String, String> getCityCodeMap() {
		return cityCodeMap;
	}

	public void setCityCodeMap(Map<String, String> cityCodeMap) {
		this.cityCodeMap = cityCodeMap;
	}

}
