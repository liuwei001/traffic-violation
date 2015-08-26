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
	 * 运营商编码--对应cities.js城市supplier，通过这个来选择指定的运营商查询
	 */
	private String code;
	
	/**
	 * 获取支持的城市列表接口地址	
	 */
	private String cityUrl;

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

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getCityUrl() {
		return cityUrl;
	}

	public void setCityUrl(String cityUrl) {
		this.cityUrl = cityUrl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
