package com.moyu.entity;

public class CarInfo {
	private String car_id;
	private String event;
	private String carstatus;
	private String time;
	private double longitude;
	private double latitude;
	private int speed;
	private int direction;
	private String gpsstatus;
	
	
	public CarInfo(String car_id, String event, String carstatus, String time,
			double longitude, double latitude, int speed, int direction,
			String gpsstatus) {
		super();
		this.car_id = car_id;
		this.event = event;
		this.carstatus = carstatus;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
		this.speed = speed;
		this.direction = direction;
		this.gpsstatus = gpsstatus;
	}
	public CarInfo() {
		// TODO 自动生成的构造函数存根
	}
	public String getCar_id() {
		return car_id;
	}
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getCarstatus() {
		return carstatus;
	}
	public void setCarstatus(String carstatus) {
		this.carstatus = carstatus;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public String getGpsstatus() {
		return gpsstatus;
	}
	public void setGpsstatus(String gpsstatus) {
		this.gpsstatus = gpsstatus;
	}
	
	
}
