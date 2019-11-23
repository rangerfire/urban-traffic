/*该类主要用于设定每条路的正常分布情况，通过扫描所有的数据，建立起每条路在不同的时段的车辆正常分布情况
 *
 */
package Exception_Models1;
import java.sql.Timestamp;
import java.util.*;
import java.io.*;
public class RoadSet {
	private HashMap<Integer,Road> roadset=new HashMap<Integer,Road>(); 
	static ArrayList<RoadInfo> roadInfo_list=new ArrayList<RoadInfo>();//存放路的id，起始点，结束点，用来将数据点归路段
	public static double threshold=80.0;//已设置的异常距离判断值
	int hour;

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
	public void LoadRoadset() throws Exception//设置所有路段的Info和id
	{
		int id;
		double[] data=null;

		BufferedReader bf=new BufferedReader(new FileReader("G://1_Study//Exception//RoadDia.txt"));//何的路基本信息
		Road r=null;
		String line = "";
		while((line=bf.readLine()) != null)
		{
			id=Integer.parseInt(line);
			r=new Road();
			for(int i=0; i<15; i++)
			{
				data=new double[8];
				for(int j=0;j<8;j++)//8个percent
				{
					line=bf.readLine();
					data[j]=Double.parseDouble(line);
				}
				r.info[i]=data;
			}
			roadset.put(id, r);
		}
		bf.close();
		bf=null;
	
	/*	while(sc.hasNextInt())//路的id
		{
			id=sc.nextInt();
			r=new Road();
			for(int i=0;i<15;i++)//时间
			{
				data=new double[8];
				for(int j=0;j<8;j++)//8个percent
				{
					data[j]=sc.nextDouble();
				}
				r.info[i]=data;
			}
			roadset.put(id, r);
		}
		sc.close();
		sc=null;
		*/
	}
	//加载路的基本信息
	public void LoadRoadInfo() throws Exception
	{
		BufferedReader bf=new BufferedReader(new FileReader("G://1_Study//Exception//yichang_sections.txt"));//何的路基本信息
		RoadInfo ri=null;
		String line = "";
		while((line=bf.readLine()) != null)
		{
			ri = new RoadInfo();
			//System.out.println(line);
			String ss = "";
			int index = 1;
			char ch = line.charAt(index);
			
			//读第1个数字
			while(Character.isDigit(ch))
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.id = Integer.parseInt(ss);
			
			//System.out.println(ri.id);
			//读第2个数字
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.x1 = Double.parseDouble(ss);
			//System.out.println(ri.x1);
			//读第3个数字
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.y1 = Double.parseDouble(ss);
			//System.out.println(ri.y1);
			//读第4个数字
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.x2 = Double.parseDouble(ss);
			//System.out.println(ri.x2);
			//读第五个数字
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.y2 = Double.parseDouble(ss);
			//System.out.println(ri.y2);
			//到此，5个数字全部读进来了！
			while(ch!=',')
			{
				index++;
				ch = line.charAt(index);
			}
			index=index+3;//到路名部分
			ch = line.charAt(index);
			int i=index;
			while(ch!=']')
			{
				index++;
				ch = line.charAt(index);
			}
			index=index-1;
			
			ri.name=line.substring(i, index);
			//到此，路的信息都读出来了！
			roadInfo_list.add(ri);
			ri=null;
		}
		bf.close();
		bf=null;
	}
	 
	//获取对应路的id，若不存在则返回0

	
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
	public double getDistance(double x,double y,RoadInfo ri)//获得点与路的距离用于获得点的路的id
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
	public ArrayList<OutlierInfo> check()//将所有路的异常信息放到oi_list中并返回
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
	public String name;
	public void display()
	{
		System.out.println("roadID:"+id);
		System.out.println("point1:"+x1+","+y1+" point2:"+x2+","+y2);
		System.out.println("name:"+name);
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
	
	public void Load(double[] data,int hour)//将某时段的速度分布数据导入
	{
		info[hour]=data;
	}
	
	
	public void reset()//更新为未修改状态
	{
		isUpdate=false;
		for(int i:curinfo)
		{
			i=0;
		}
	}
	
	public OutlierInfo distance(int hour)//计算直方图距离，以判断是否异常,若异常则生成一个outlier对象
	{
		//未修改过
		if(!isUpdate)
		{
			return null;
		}
		double[] data=new double[8];//放直方图的百分比
		int sum=0;
		for(int i:curinfo)
		{
			sum+=i;
		}
		for(int i=0;i<8;i++)
		{
			data[i]=(double)curinfo[i]/sum;
		}
		int index = 0;//时间暂存量
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
            //计算两个分布的直方图距离                  
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
//一个异常分布的信息
class OutlierInfo
{
	public int id;  // 路的ID号
	public double[] normal;  //路的正常分布情况
	public double[] abnormal;  //路的异常分布情况
	public double dis;       //分布之间的距离
	public String name="";//路的名字
	public double x1;
	public double y1;
	public double x2;
	public double y2;
	
}

