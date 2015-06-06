package com.traffic.weizhang.entity;

import java.io.Serializable;

/**
 * 城市
 * @author Administrator
 *
 */
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String city_name;
	
	private String city_code;
	
	private String abbr;
	
	private String engineno;
	
	private String classno;

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getClassno() {
		return classno;
	}

	public void setClassno(String classno) {
		this.classno = classno;
	}
}
