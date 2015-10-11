package com.traffic.weizhang.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.traffic.common.utils.CustomDateSerializer;


public class Result implements Serializable, Comparable<Result> {

	private static final long serialVersionUID = 1L;

	private String code;

	private String fen;

	private String money;

	private String area;

	private String act;

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

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
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

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public boolean equals(Object obj) {
		Result r = (Result) obj;
		return code.equals(r.code) && fen.equals(r.fen) && (money.equals(r.money))
				&& area.equals(r.area) && act.equals(r.act) && date.equals(r.date);
	}

	@Override
	public int hashCode() {
		String str = code + fen + money + area + act + date;
		return str.hashCode();
	}
	
	@Override
	public int compareTo(Result result) {
		// TODO Auto-generated method stub
		return result.getDate().compareTo(this.getDate());
	}
}
