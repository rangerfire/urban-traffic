package com.moyu.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedList;

public class ImportAllData {
	static Connection conn=null;
	static Statement st=null;
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	static void ImportFiles(LinkedList<File> files) throws SQLException{
		System.out.println(Thread.currentThread().getName()+" is running");
		Iterator<File> it=files.iterator();
		while(it.hasNext()){
			String filename=it.next().getAbsolutePath();
			filename=filename.replace("\\", "\\\\");
			String sql = "load data local infile "
					+"'"+filename+"'"
					+" into table `carinfos`" 
					+" fields terminated by ',' escaped by '\\\\'" 
					+" lines terminated by '\r\n';";
			System.out.println(Thread.currentThread().getName()+" in while");
			synchronized (st) {
				st.executeUpdate(sql);
			}
		}
		System.err.println(Thread.currentThread().getName()+" is end!");
	}
	public static void main(String[] args) {
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/chuang",
					"root", "110114");
			conn.setAutoCommit(false);
			st=conn.createStatement();
			//路径名不要用中文
			File dir=new File("C:\\Users\\Yc\\Desktop\\BJtaxis\\data");
			File[] files=dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith("txt");
				}
			});
			final LinkedList<LinkedList<File>> flss=new LinkedList<LinkedList<File>>();
			//[101][17]
			int j=0;
			LinkedList<File> fs=new LinkedList<File>();
			for (File fl : files) {
				if(j==17){
					j=0;
					flss.add(fs);
					fs=new LinkedList<File>();
				}			
				fs.add(fl);
				j++;
			}
			for (int i=0;i<flss.size();i++) {
				new Thread(){
					public void run() {
						try {
							ImportFiles(flss.poll());
							synchronized (conn) {
							//使所有上一次提交/回滚后进行的更改成为持久更改，
							//并释放此 Connection 对象当前持有的所有数据库锁。
								conn.commit();	
							}
						}
						catch (SQLException e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
