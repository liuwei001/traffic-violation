package com.traffic.weizhang.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订阅记录
 * @author Administrator
 *
 */
public class TSubscribe implements Serializable {

	private String id;
	
	private String mobile;
	
	private String carno;
	
	private String city;
	
	private String engineno;
	
	private String classno;
	
	private String carmodel;
	
	private String cartype;
	
	private Date createTime;
	
	private Date lastsendTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCarmodel() {
		return carmodel;
	}

	public void setCarmodel(String carmodel) {
		this.carmodel = carmodel;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastsendTime() {
		return lastsendTime;
	}

	public void setLastsendTime(Date lastsendTime) {
		this.lastsendTime = lastsendTime;
	}
}
