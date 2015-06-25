package com.traffic.weizhang.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * 供应商
 * @author Administrator
 *
 */
public class Supplier {

	/**
	 * 描述
	 */
	private String description;
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
