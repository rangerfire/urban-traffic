import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class storeTexi {
	private static Connection conn;
	private static Statement st;

	public static void main(String[] args) throws SQLException {
		storeTexi st = new storeTexi();
//		st.storetexi();
		st.storetexiredian();
//		st.storefrequent();
//		st.storefrequentmain();
	}

	public void storetexi() throws SQLException {

		String readFilePath = "";
		readFilePath = "data/001142.txt";
		File fileInput = new File(readFilePath);
		MyFileReader mfr = new MyFileReader(fileInput);
		mfr.open();
		String s = "";
		int i=0;
		conn=getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement("INSERT INTO taxi(tid,time,lat,lon) VALUES(?,?,?,?)"); 
		int batchNum=1000;
		while ((s = mfr.readLine()) != null) {
			i++;
			String[] ss = s.split(",");
			pst.setString(1, ss[0]);  
			pst.setString(2, ss[1]);
			pst.setString(3, ss[2]);
			pst.setString(4, ss[3]);
			    // 把一个SQL命令加入命令列表  
			pst.addBatch(); 
			if (i % batchNum == 0) {  
	                pst.executeBatch();  
	                conn.commit();  
	            }  
			} 
		// 执行批量更新  
		pst.executeBatch();  
		// 语句执行完毕，提交本事务  
		conn.commit();  
		mfr.close();
	}

	public void storetexiredian() throws SQLException {

		String readFilePath = "";
		readFilePath = "data/23.txt";
		File fileInput = new File(readFilePath);
		MyFileReader mfr = new MyFileReader(fileInput);
		mfr.open();
		String s = "";
		int i=0;
		conn=getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement("INSERT INTO taxiredian(tid,wgid,timestamp,lat,lon,count,llat,llon,rlat,rlon) VALUES(?,?,?,?,?,?,?,?,?,?)"); 
		int batchNum=1000;
		while ((s = mfr.readLine()) != null) {
			i++;
			String[] ss = s.split(",");
			pst.setString(1, "0");
			pst.setString(2, ss[0]);  
			pst.setString(3, ss[1]);
			pst.setString(4, ss[2]);
			pst.setString(5, ss[3]);
			pst.setString(6, ss[4]);
			pst.setString(7, ss[5]);
			pst.setString(8, ss[6]);
			pst.setString(9, ss[7]);
			pst.setString(10, ss[8]);
			    // 把一个SQL命令加入命令列表  
			pst.addBatch(); 
			if (i % batchNum == 0) {  
	                pst.executeBatch();  
	                conn.commit();  
	            }  
			} 
		// 执行批量更新  
		pst.executeBatch();  
		// 语句执行完毕，提交本事务  
		conn.commit();  
		mfr.close();
	}
	
	public void storefrequent() throws SQLException {

		String readFilePath = "";
		readFilePath = "data/table_of_freqtraj.txt";
		File fileInput = new File(readFilePath);
		MyFileReader mfr = new MyFileReader(fileInput);
		mfr.open();
		String s = "";
		int i=0;
		conn=getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement("INSERT INTO frequent(freq_id,timestart,timeend,poi_id,support,num) VALUES(?,?,?,?,?,?)"); 
		int batchNum=1000;
		while ((s = mfr.readLine()) != null) {
			i++;
			String[] ss = s.split(",");
			pst.setString(1, ss[0]);
			pst.setString(2, "2012-11-30 08:00:00");
			pst.setString(3, "2012-11-30 09:00:00");
			pst.setString(4, ss[1]);
			pst.setString(5, ss[2]);
			pst.setString(6, ss[3]);
			    // 把一个SQL命令加入命令列表  
			pst.addBatch(); 
			if (i % batchNum == 0) {  
	                pst.executeBatch();  
	                conn.commit();  
	            }  
			} 
		// 执行批量更新  
		pst.executeBatch();  
		// 语句执行完毕，提交本事务  
		conn.commit();  
		mfr.close();
	}
	
	public void storefrequentmain() throws SQLException {

		String readFilePath = "";
		readFilePath = "data/table_of_POI.txt";
		File fileInput = new File(readFilePath);
		MyFileReader mfr = new MyFileReader(fileInput);
		mfr.open();
		String s = "";
		int i=0;
		conn=getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement("INSERT INTO frequentmain(poi_id,name,lat,lon) VALUES(?,?,?,?)"); 
		int batchNum=1000;
		while ((s = mfr.readLine()) != null) {
			i++;
			String[] ss = s.split(",");
			pst.setString(1, ss[0]);  
			pst.setString(2, ss[1]);
			pst.setString(3, ss[2]);
			pst.setString(4, ss[3]);
			    // 把一个SQL命令加入命令列表  
			pst.addBatch(); 
			if (i % batchNum == 0) {  
	                pst.executeBatch();  
	                conn.commit();  
	            }  
			} 
		// 执行批量更新  
		pst.executeBatch();  
		// 语句执行完毕，提交本事务  
		conn.commit();  
		mfr.close();
	}

//	public static void insertTaxi(String tid, String time, String lat,
//			String lon) {
//		Connection conn2 = getConnection(); // 首先要获取连接，即连接到数据库
//		// System.out.println(fromurl);
//		try {
//
//			String sql = "INSERT INTO taxi(tid,time,lat,lon)" + " VALUES ('"
//					+ tid + "','" + time + "','" + lat + "','" + lon + "')"; // 插入数据的sql语句
//
//			st = (Statement) conn2.createStatement(); // 创建用于执行静态sql语句的Statement对象
//
//			int countnum = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
//
//			System.out.println("向taxi表中插入 " + countnum + " 条数据"); // 输出插入操作的处理结果
//			// rs2.last();
//
//		} catch (SQLException e) {
//			System.out.println("向taxt表插入数据失败" + e.getMessage());
//		} finally {
//			try {
//				conn2.close(); // 关闭数据库连接
//			} catch (Exception e) {
//
//			}
//
//		}
//
//	}
//
//	public static void insertTaxiShiTi(String tid, String name, String lat,
//			String lon) {
//		Connection conn2 = getConnection(); // 首先要获取连接，即连接到数据库
//		// System.out.println(fromurl);
//		try {
//
//			String sql = "INSERT INTO taxishiti(tid,name,lat,lon)"
//					+ " VALUES ('" + tid + "','" + name + "','" + lat + "','"
//					+ lon + "')"; // 插入数据的sql语句
//
//			st = (Statement) conn2.createStatement(); // 创建用于执行静态sql语句的Statement对象
//
//			int countnum = st.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
//
//			System.out.println("向taxishiti表中插入 " + countnum + " 条数据"); // 输出插入操作的处理结果
//			// rs2.last();
//
//		} catch (SQLException e) {
//			System.out.println("向taxtshiti表插入数据失败" + e.getMessage());
//		} finally {
//			try {
//				conn2.close(); // 关闭数据库连接
//			} catch (Exception e) {
//
//			}
//
//		}
//
//	}

	public static Connection getConnection() {
		conn = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动

			conn = DriverManager.getConnection(
					 "jdbc:mysql://localhost/tweet?user=root&password=118417&useUnicode=true&characterEncoding=GBK");// 创建数据连接
			// System.out.println("数据库连接成功");
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return conn; // 返回所建立的数据库连接
	}

}
