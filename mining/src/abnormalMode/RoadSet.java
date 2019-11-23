import java.util.*;
import java.io.*;
public class RoadSet {
	private HashMap<Integer,Road> roadset=new HashMap<Integer,Road>(); 
	private ArrayList<RoadInfo> roadInfo_list=new ArrayList<RoadInfo>();
	public static double threshold;
	private int hour;
	
	//更新为未修改的状态
	public void reset()
	{
		Iterator iter=roadset.entrySet().iterator();
		Map.Entry<Integer, Road> entry=null;
		Road r=null;
		while(iter.hasNext())
		{
			entry=(Map.Entry<Integer, Road>)iter.next();
			r=(Road)entry.getValue();
			r.reset();
		}
	}
	
	public void SetHour(int hour)
	{
		this.hour=hour;
	}
	
   //加载路的分布信息
	public void LoadRoadset() throws Exception
	{
		Scanner sc=new Scanner(new File("H://test.txt"));
		Road r=null;
		int id;
		double[] data=null;
		
		while(sc.hasNextInt())
		{
			id=sc.nextInt();
			r=new Road();
			for(int i=0;i<15;i++)
			{
				data=new double[8];
				for(int j=0;j<8;j++)
				{
					data[j]=sc.nextDouble();
				}
				r.info[i]=data;
			}
			roadset.put(id, r);
		}
		sc.close();
		sc=null;
	}
	//加载路的基本信息
	public void LoadRoadInfo() throws Exception
	{
		Scanner sc=new Scanner(new File("H://roadinfo.txt"));
		RoadInfo ri=null;
		while(sc.hasNextInt())
		{
			ri=new RoadInfo();
			ri.id=sc.nextInt();
			ri.x1=sc.nextDouble();
			ri.y1=sc.nextDouble();
			ri.x2=sc.nextDouble();
			ri.y2=sc.nextDouble();
			roadInfo_list.add(ri);
			ri=null;
		}
		sc.close();
		sc=null;
	}
	 
	//获取对应路的id，若不存在则返回0
	public int GetRoadID(CPoint p)
	{
		return 0;
	}
	
	//获取对应路的id，若不存在则返回0
	public int GetRoadID(double x,double y){
		for(RoadInfo ri:roadInfo_list)
		{
			if(getDistance(x,y,ri)<20)
			{
				return ri.id;
			}
		}
		return 0;
	}
	public double getDistance(double x,double y,RoadInfo ri)
	{
		double a,b,c;
		double r;
		a=SearchData.GetDistance(x,y,ri.x1,ri.y1);
		b=SearchData.GetDistance(x,y,ri.x2,ri.y2);
		c=SearchData.GetDistance(ri.x1,ri.y1,ri.x2,ri.y2);
		r=(a+b+c)/2;
		double S=Math.sqrt((r-a)*(r-b)*(r-c)*r);
		double dis=2*S/c;
		if(dis<20)
		{
			double cs1=(a*a+c*c-b*b)/(2*a*c);
			double cs2=(b*b+c*c-a*a)/(2*b*c);
			if(cs1<0||cs2<0){
				return Double.MAX_VALUE;
			}
			else
			{
				return dis;
			}
		}
		else
		{
			return dis;
		}
	} 
	//设置路的当前状态
	public void SetInfo(int id,double speed)
	{
		Road r=roadset.get(id);
		r.SetCur(speed);
	}
	public ArrayList<OutlierInfo> check()
	{
		Iterator iter=roadset.entrySet().iterator();
		Map.Entry<Integer, Road> entry=null;
		Road r=null;
		int id;
		OutlierInfo oi=null;
		ArrayList<OutlierInfo> oi_list=new ArrayList<OutlierInfo>();
		while(iter.hasNext())
		{
			entry=(Map.Entry<Integer, Road>)iter.next();
			r=(Road)entry.getValue();
			id=entry.getKey();
			oi=r.distance(hour);
			if(oi!=null)
			{
				oi.id=id;
				oi_list.add(oi);
			}
		}
		return oi_list;
	}
	public void display()
	{
		Road r=null;
		for(RoadInfo ri:roadInfo_list)
		{
			r=roadset.get(ri.id);
			ri.display();
			r.display();
			System.out.println();
		}
	}
	public static void main(String[] args)
	{
		RoadSet rs=new RoadSet();
		try
		{
		   rs.LoadRoadset();
		   rs.LoadRoadInfo();
		   rs.display();
		   int id;
		   id=rs.GetRoadID(116.419123,39.955482);
		   System.out.println(id);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
class RoadInfo
{
	public int id;
	public double x1,y1,x2,y2;
	public void display()
	{
		System.out.println("roadID:"+id);
		System.out.println("point1:"+x1+","+y1+" point2:"+x2+","+y2);
	}
}
class Road
{
	
	public double[][] info=new double[15][8];   //记录路的分布信息
	private int[] curinfo=new int[8];
	private boolean isUpdate=false;
	
	public void display()
	{
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<8;j++)
			{
				System.out.print(info[i][j]+" ");
			}
			System.out.println();
		}
	}
	//设置当前的分布信息
	public void SetCur(double speed)
	{
		int index;
		if(speed<1)
		{
			index=0;
		}
		else
		{
			if(speed<60)
			{
				index=(int)(speed/10);
			}
			else
			{
				index=7;
			}
		}
		curinfo[index]++;
		isUpdate=true;  //标志位以修改
	}
	
	public void Load(double[] data,int hour)
	{
		info[hour]=data;
	}
	
	//更新为未修改状态
	public void reset()
	{
		isUpdate=false;
		for(int i:curinfo)
		{
			i=0;
		}
	}
	//计算直方图距离，以判断是否异常,
	public OutlierInfo distance(int hour)
	{
		//未修改过
		if(!isUpdate)
		{
			return null;
		}
		double[] data=new double[8];
		int sum=0;
		for(int i:curinfo)
		{
			sum+=i;
		}
		for(int i=0;i<8;i++)
		{
			data[i]=(double)curinfo[i]/sum;
		}
		int index = 0;
		//通过时间判断下标
		if(hour>=0&&hour<6)
		{
			index=0;
		}
		if(hour<20&&hour>=6)
		{
			index=hour-5;
		}
		if(hour>=20&&hour<=24)
		{
			index=14;
		}
		if(data.length==8)
		{
			double bc=0;
			for(int i=0;i<data.length;i++)
			{
				bc+=Math.sqrt(data[i]*info[index][i]);
			}
			double db=-Math.log(bc)*100;
			if(db>RoadSet.threshold)
			{
				OutlierInfo oi=new OutlierInfo();
				oi.normal=info[index];
				oi.abnormal=data;
				oi.dis=db;
				return oi;
			}
			else
			{
				return null;
			}
			
		}
		else
		{
			return null;
		}
	}
}
class OutlierInfo
{
	public int id;
	public double[] normal;
	public double[] abnormal;
	public double dis;
}