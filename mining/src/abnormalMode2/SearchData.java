/*
 *该类主要用于寻找指定路得点数据，就是在这条路上得走过的数据点
 *由于是直接从百度地图找得几条路，所以就把这些路的信息直接放在一个文件中，然    后从文件中直接读取。
 *路的信息主要包含路的两端的经纬度，如果一条路径有折线，则认为是两条路
 */
package Exception_Models1;
import java.io.*;
import java.util.Scanner;
public class SearchData 
{
	static String path="F://result1/"; //存放最终的挑选出来的信息文件
	static Line road=new Line();
	static Line[] roads=new Line[8];
	static double[][] data=new double[8][4];
	public static void main(String[] args) throws Exception 
	{	
	    Scanner sc=new Scanner(new File("H://road.txt"));   //存放路的信息的文件
	    int i=0;
	    while(sc.hasNextDouble())
	    {
	    	roads[i]=new Line();
	    	roads[i].s.x=sc.nextDouble(); //x,y分别表示路的经纬度，s是路的一端，t是路的另一端
	    	roads[i].s.y=sc.nextDouble();
	    	roads[i].t.x=sc.nextDouble();
	    	roads[i].t.y=sc.nextDouble();
	    	roads[i].set();
	    	i++;
	    }
		File dir=new File("F://result");   //原始数据文件
		File[] fs=dir.listFiles();
		System.out.println(fs.length);    //递归读取所有11月份所有文件
		for(File e:fs)
		{
			Search1(e);
			System.out.println(e.getName());
		}
	}
	static public void Search1(File e) throws Exception
	{
		System.out.println(e.getName());
		BufferedReader br=new BufferedReader(new FileReader(e));
		BufferedWriter bw=new BufferedWriter(new FileWriter(path+e.getName()));
		String line=null;
		int i,j;
		String num1,num2;
		double x,y;
		while((line=br.readLine())!=null)//读取每个点记录，并提取经纬度，判断是否在现有的路径中，如果在则保存，不在则舍弃
		{
			if(line.length()>0)
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
				x=Double.valueOf(num1);
				y=Double.valueOf(num2);
				for(int k=0;k<8;k++)  //这里的8是因为当初实验室只用了8个路径
				{
					if(roads[k].getDistance(x, y)<20) //当该点与当前路径的距离小于20时认为该点属于该路径
					{
						bw.write(line);
						bw.newLine();
						break;
					}
				}
			}
		}
	}

	private  static final  double EARTH_RADIUS = 6378137;//赤道半径(单位m)  
    private  static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
	public static double GetDistance(double lon1,double lat1,double lon2, double lat2) //用于计算，两个点之间的距离，点使用经纬度表示，具体算法可以不看，涉及到空间几何的知识
    {  
		if(lon1<1e-6||lon2<1e-6)
		{
			return 0;
		}
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = rad(lon1) - rad(lon2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       //s = Math.round(s * 10000) / 10000;  
       return s;  
    }
	

}
class Point//点类，用于存放经纬度的
{
	public double x;
	public double y;
}
class Line
{
	public Point s=new Point();
	public Point t=new Point();
	public double length;
	public void set()  //用于计算该路的长度
	{
		length=SearchData.GetDistance(s.x,s.y,t.x,t.y);
	}
	public double getDistance(double x,double y)  //用于计算点到路的距离，采用面积/底=高计算机，面积使用海伦公式计算
	{
		double a,b,c;
		double r;
		a=SearchData.GetDistance(x,y,s.x,s.y);
		b=SearchData.GetDistance(x,y,t.x,t.y);
		c=length;
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
	public void show()
	{
		System.out.println(s.x+","+s.y+" "+t.x+","+t.y);
	}
}
