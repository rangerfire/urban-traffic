package com.moyu.entity;

public class HourData {
	private int day;
	private int hour;
	private int value;
	public HourData(int day, int hour, int value) {
		super();
		this.day = day;
		this.hour = hour;
		this.value = value;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
