package com.moyu.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.moyu.entity.Comment;

public class CommentsDAO {
	private static String DRIVER;
	private static String URL;
	private static String USER;
	private static String PWD;
	private static Connection conn;
	private static Statement stmt;
	private static PreparedStatement ps;
	public CommentsDAO(String path){
		//从jdbc.properties获取驱动类  URL 表名 
		Properties pro=new Properties();
		try {
			pro.load(new FileInputStream(path));
			DRIVER=pro.getProperty("jdbc.driver");
			URL=pro.getProperty("jdbc.url");
			USER=pro.getProperty("jdbc.user");
			PWD=pro.getProperty("jdbc.password");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public CommentsDAO(){}
	public boolean addComments(Comment comment){
		int v=0;
		try{
			//建立连接
			conn=DriverManager.getConnection(URL, USER, PWD);
		//	conn=DS.ds.getConnection();
			stmt=conn.createStatement();
			ps=conn.prepareStatement("insert into "
					+ "comments values(?,?,?,?,curdate())");
			ps.setString(1, comment.getName());
			ps.setString(2, comment.getEmail());
			ps.setString(3, comment.getPhone());
			ps.setString(4, comment.getContent());
			v=ps.executeUpdate();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}finally{
			try {
				if(ps!=null){
					ps.close();
					ps=null;
				}
				if(stmt!=null){
					stmt.close();
					stmt=null;
				}
				if(conn!=null){
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return v==0?false:true;
	}
}
