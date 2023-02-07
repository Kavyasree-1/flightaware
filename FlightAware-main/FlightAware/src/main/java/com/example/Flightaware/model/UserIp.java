package com.example.Flightaware.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserIp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String Cnt;
	private String Country;
	private String Locale;
	private String Origin;
	private String Dest;
	private String Currency;
	private String inDate;
	private String outDate;
	private String Curr;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCountry() {
		return Country;
	}
	
	public String getCurr() {
		return Curr;
	}
	public void setCurr(String curr) {
		Curr = curr;
	}
	public String getCnt() {
		return Cnt;
	}
	public void setCnt(String cnt) {
		Cnt = cnt;
	}
	public String getOrigin() {
		return Origin;
	}
	public void setOrigin(String origin) {
		Origin = origin;
	}
	public String getDest() {
		return Dest;
	}
	public void setDest(String dest) {
		Dest = dest;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getLocale() {
		return Locale;
	}
	public void setLocale(String locale) {
		Locale = locale;
	}
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	
}
