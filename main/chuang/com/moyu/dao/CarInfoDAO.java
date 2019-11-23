package com.moyu.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.moyu.entity.CarInfo;

public class CarInfoDAO {
	private static String DRIVER;
	private static String URL;
	private static String USER;
	private static String PWD;
	private static Connection conn;
	private static Statement stmt;
	private static PreparedStatement ps;
	private static ResultSet rs; 
	public CarInfoDAO(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
		//从jdbc.properties获取驱动类  URL 表名 
		Properties pro=new Properties();
		pro.load(new FileInputStream(path));
		DRIVER=pro.getProperty("jdbc.driver");
		URL=pro.getProperty("jdbc.url");
		USER=pro.getProperty("jdbc.user");
		PWD=pro.getProperty("jdbc.password");
		Class.forName(DRIVER);
	}
	private void Close() throws SQLException{
		if(ps!=null){
			ps.close();
			ps=null;
		}
		if(rs!=null){
			rs.close();
			rs=null;
		}
		if(stmt!=null){
			stmt.close();
			stmt=null;
		}
		if(conn!=null){
			conn.close();
			conn=null;
		}
	}
	public List<CarInfo> ListAllCarInfo() throws SQLException{
		conn=DriverManager.getConnection(URL, USER, PWD);
		stmt=conn.createStatement();
		ps=conn.prepareStatement("select * "
				+ "from carinfos limit 0,3000");
		rs=ps.executeQuery();
		List<CarInfo> list=new ArrayList<CarInfo>();
		while(rs.next()){
			CarInfo ci=new CarInfo();
			ci.setCar_id(rs.getString(1));
			ci.setEvent(rs.getString(2));
			ci.setCarstatus(rs.getString(3));
			ci.setTime(rs.getString(4));
			ci.setLongitude(rs.getDouble(5));
			ci.setLatitude(rs.getDouble(6));
			ci.setSpeed(rs.getInt(7));
			ci.setDirection(rs.getInt(8));
			ci.setGpsstatus(rs.getString(9));
			list.add(ci);
		}
		Close();
		return list;
	}
	public List<CarInfo> ListCarsbySpeedTime(String maxSp,String bgtime,String edtime) throws SQLException {
		conn=DriverManager.getConnection(URL, USER, PWD);
		stmt=conn.createStatement();
		String sql="select * from carinfos where speed<="+maxSp;
		if(bgtime!=null&&!bgtime.trim().isEmpty()){
			sql+=" and time between "
						+ "'2012-11-30 "+bgtime+"'";
			if(edtime!=null&&!bgtime.trim().isEmpty()){
				sql+=" and '2012-11-30 "+edtime+"'";
			}else{
				sql+=" and '2012-12-01 00:00:00'";
			}
		}
		ps=conn.prepareStatement(sql);
		rs=ps.executeQuery();
		List<CarInfo> list=new ArrayList<CarInfo>();
		while(rs.next()){
			CarInfo ci=new CarInfo();
			ci.setCar_id(rs.getString(1));
			ci.setEvent(rs.getString(2));
			ci.setCarstatus(rs.getString(3));
			ci.setTime(rs.getString(4));
			ci.setLongitude(rs.getDouble(5));
			ci.setLatitude(rs.getDouble(6));
			ci.setSpeed(rs.getInt(7));
			ci.setDirection(rs.getInt(8));
			ci.setGpsstatus(rs.getString(9));
			list.add(ci);
		}
		Close();
		return list;
	}
	
}
