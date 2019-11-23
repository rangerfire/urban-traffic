import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Test {
	public static void main(String[] args) throws Exception
	{
		Class.forName("org.postgresql.Driver");
		ResultSet rs=null;
		
		PreparedStatement pstmt=null;
		Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/osm", "postgres",
                "123456");
		pstmt=conn.prepareStatement("select * from test");
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			java.sql.Array a=rs.getArray(1);
			System.out.println(a.toString());
		}
		getNumber(null);
	}
	public static  double[] getNumber(String s) 
	{
		ArrayList<Double> a=new ArrayList<Double>();
		a.add(4.3);
		a.add(2.0);
		System.out.println(a.toArray());
		return null;
	}
}
