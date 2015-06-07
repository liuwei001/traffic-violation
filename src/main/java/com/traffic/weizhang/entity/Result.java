package com.traffic.weizhang.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Result implements Serializable , Comparable<Result>{

	private static final long serialVersionUID = 1L;

	private String code;
	
	private String fen;
	
	private float money;
	
	private String area;
	
	private String act;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date date;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public int compareTo(Result result) {
		// TODO Auto-generated method stub
		return result.getDate().compareTo(this.getDate());
	}
}
