import java.io.*;
import java.sql.*;
import java.util.*;
public class CheckOutlier {
	public static RoadSet roads=new RoadSet();
	public static BufferedReader br=null;
	
	//主函数两个参数是日期和小时
	public static void main(String[] args) throws Exception 
	{
		for(String s:args)
		{
			System.out.println(s);
		}
		/*roads.LoadRoadset();
		roads.LoadRoadInfo();
		br=new BufferedReader(new FileReader("E://2012-11-11.txt"));
		Proccess(br,10);   //注意时间问题
		br.close();
		br=null;
		ArrayList<OutlierInfo> oi_list=null;
		roads.SetHour(10);  //注意时间问题
		oi_list=roads.check();
		OutPut(oi_list);*/
	}
	public static double getSpeed(String line)
	{
		double speed;
		if(line.charAt(50)==',')
		{
			speed=Double.valueOf(line.substring(49,50));
		}
		else
		{
			if(line.charAt(51)==',')
			{
				speed=Double.valueOf((line.substring(49,51)));
			}
			else
			{
				speed=Double.valueOf(line.substring(49,52));
			}
		}
		return speed;
	}
	
	//从文件中读取数据
	public static void Proccess(BufferedReader br,int hour) throws Exception
	{
		String line=null;
		String num1,num2;
		double x,y;
		int i,j;
		int id;
		while((line=br.readLine())!=null)
		{
			if(line.length()>0)
			{
				
				if(hour==Integer.parseInt(line.substring(19, 21)))  //时间匹配
				{
					i=26;
					while(line.charAt(i)!=',')
					{
						i++;
					}
					num1=line.substring(26,i);
					i++;
					j=i;
					while(line.charAt(i)!=',')
					{
						i++;
					}
					num2=line.substring(j,i);
					
					//获得经纬度
					x=Double.valueOf(num1);
					y=Double.valueOf(num2);
					id=roads.GetRoadID(x, y);
					
					//设置信息
					if(id!=0)  
						roads.SetInfo(id, getSpeed(line));
				}
			}
		}
	}
	
	
	//从数据库中读取数据
	public static void Process(int date,int hour) throws Exception
	{
		Class.forName("org.postgresql.Driver");
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/osm", "postgres",
                "123456");
		pstmt=conn.prepareStatement("select lon,lat,speed from miningtable where date_part('day',time)=? and date_part('hour',time)=?");
		pstmt.setInt(1, date);
		pstmt.setInt(2, hour);
		rs=pstmt.executeQuery();
		double x,y;
		int id;
		double speed;
		while(rs.next())
		{
			x=rs.getDouble(1);
			y=rs.getDouble(2);
			speed=rs.getDouble(3);
			id=roads.GetRoadID(x, y);
			if(id!=0)
			{
				roads.SetInfo(id, speed);
			}
		}
		rs.close();
		conn.close();	
	}
	
	
	//对异常进行输出
	public static void OutPut(ArrayList<OutlierInfo> oi_list) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(""));
		for(OutlierInfo e:oi_list)
		{
			bw.write(e.id+","+e.dis+",{");
			for(int i=0;i<7;i++)
			{
				bw.write(e.normal[i]+",");
			}
			bw.write(e.normal[7]+"},{");
			for(int i=0;i<7;i++)
			{
				bw.write(e.abnormal[i]+",");
			}
			bw.write(e.abnormal[7]+"}");
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}
