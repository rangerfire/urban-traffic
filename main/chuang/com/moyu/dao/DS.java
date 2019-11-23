package com.moyu.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DS {
	static private final String JNDI="CHUANGJNDI";
	static DataSource ds;
	static{
		try{
			Context ctx=new InitialContext();
			ctx=(Context)ctx.lookup("java:comp/env/jdbc/CHUANG");
			ds=(DataSource)ctx.lookup(JNDI);
		}catch(NamingException e){
			e.printStackTrace();
		}
	}
}
