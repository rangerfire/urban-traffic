package Exception_Models1;

/*
 * 第四步：
  		
  
  
  
 */
import java.io.*;
import java.sql.*;
import java.util.*;
public class Col1 
{
	public static RoadSet roads=new RoadSet();	//共有30天，每天都有一个roadset对象
	public static BufferedReader br=null;
	public static ArrayList<RoadInfo> roadInfo_list=new ArrayList<RoadInfo>();
	
	//主函数两个参数是日期和小时
	public static void main(String[] args) throws Exception 
	{
	
		//道路信息表只需要加载一遍
		roads.LoadRoadInfo();
		roadInfo_list = roads.roadInfo_list;
		//每天都要加载出一个 道路速度直方图 表
		
		roads.LoadRoadset();	//加载正常直方图
		
		//关键就是如何加载当前直方图！
	
		//每条路的每天的每个时间段   =》 都有一个当前时间的速度直方图
		for(int hour=0;hour<24;hour++)
		{
			roads.hour = hour;	//设置时间
			
			File f = new File("G://1_Study//Exception//taxi_group");//放所有按路分的数据的文件夹
			File[] fs = f.listFiles();
			for(int i=0;i<fs.length;i++)
			{
				//id为文件名
				int id = Integer.parseInt( fs[i].getName().replaceAll("[.][^.]+$", "") );
				//System.out.println(id);
				br = new BufferedReader(new FileReader(fs[i]));
				
				Proccess(br, hour, id);	//设置当前时间的直方图！
				br.close();
				br = null;
				
			}
			//读完所有文件，此时段有一个    roads表，可以进行check（）
			System.out.println(hour);
			ArrayList<OutlierInfo> oi_list =  roads.check();
			for(int myi=0; myi<oi_list.size(); myi++)
			{
				int index=oi_list.get(myi).id;//获得某异常信息点的路段id
				System.out.println(oi_list.get(myi).id);
				oi_list.get(myi).name =roadInfo_list.get(index).name;
				System.out.println(oi_list.get(myi).name);
				oi_list.get(myi).x1 =roadInfo_list.get(index).x1;
				oi_list.get(myi).y1 =roadInfo_list.get(index).y1;
				oi_list.get(myi).x2 =roadInfo_list.get(index).x2;
				oi_list.get(myi).y2 =roadInfo_list.get(index).y2;
			}
			OutPut(oi_list,hour);
		}
	}

	//从某一行数据中得出速度值
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
	
	/*
	 * 从某个文件中读取每条记录信息,找到符合输入时间的点信息
	         设置该点所属于的道路的id号，和相应的速度，存放在(RoadSet)roads中  -> 相当于设置当前速度直方图
	*/
	public static void Proccess(BufferedReader br,int hour,int id) throws Exception
	{
		String line=null;
		while((line=br.readLine())!=null)
		{
			if(line.length()>0)
			{
				if(hour == Integer.parseInt(line.substring(19, 21)))  //时间匹配
				{
					
					//设置信息
					if(id!=0)  
						roads.SetInfo(id, getSpeed(line));
				}
			}
		}
	}
	
	
	
	//输出异常信息记录表 ， 到 F:\Taxi_Data\Outlier_list.txt 中
	public static void OutPut(ArrayList<OutlierInfo> oi_list,int hour) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("G://1_Study//Exception//Outlier_list.txt",true));
		double speed=0;
		
		for(OutlierInfo e:oi_list)
		{
			bw.write(e.id+",");
			bw.write(e.name+";");
			  
			bw.write(e.y1+";");
			bw.write(e.x1+",");
			
			bw.write(e.y2+";");
			bw.write(e.x2+",");
			bw.write(hour+",");
			for(int i=0;i<7;i++)
			{
				speed+=e.normal[i]*(i*10+5);				
			}
			bw.write(speed+",");//写入正常平均速度
			
			speed=0;
			for(int i=0;i<7;i++)
			{
				speed+=e.abnormal[i]*(i*10+5);
			}
			bw.write(speed+"");//写入正常平均速度
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}

