import java.io.*;
import java.util.Scanner;
public class SearchData {
	static String path="F://result1/";
	static Line road=new Line();
	static Line[] roads=new Line[8];
	static double[][] data=new double[8][4];
	public static void main(String[] args) throws Exception 
	{	
	    Scanner sc=new Scanner(new File("H://road.txt"));
	    int i=0;
	    while(sc.hasNextDouble())
	    {
	    	roads[i]=new Line();
	    	roads[i].s.x=sc.nextDouble();
	    	roads[i].s.y=sc.nextDouble();
	    	roads[i].t.x=sc.nextDouble();
	    	roads[i].t.y=sc.nextDouble();
	    	roads[i].set();
	    	i++;
	    }
		File dir=new File("F://result");
		File[] fs=dir.listFiles();
		System.out.println(fs.length);
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
		while((line=br.readLine())!=null)
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
				for(int k=0;k<8;k++)
				{
					if(roads[k].getDistance(x, y)<20)
					{
						bw.write(line);
						bw.newLine();
						break;
					}
				}
			}
		}
	}
	static public void Search(File e) throws Exception 
	{
		System.out.println(e.getName());
		BufferedReader br=new BufferedReader(new FileReader(e));
		BufferedWriter bw=new BufferedWriter(new FileWriter(path+e.getName()));
		String line=null;
		int i,j;
		String num1,num2;
		double x,y;
		while((line=br.readLine())!=null)
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
				
				if(road.getDistance(x, y)<20)
				{
					bw.write(line);
					bw.newLine();
					break;
				}
			}
		}
		bw.flush();
		bw.close();
		br.close();
		
	}
	private  static final  double EARTH_RADIUS = 6378137;//³àµÀ°ë¾¶(µ¥Î»m)  
    private  static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
	public static double GetDistance(double lon1,double lat1,double lon2, double lat2)  
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
class Point
{
	public double x;
	public double y;
}
class Line
{
	public Point s=new Point();
	public Point t=new Point();
	public double length;
	public void set()
	{
		length=SearchData.GetDistance(s.x,s.y,t.x,t.y);
	}
	public double getDistance(double x,double y)
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
