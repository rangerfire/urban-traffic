import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
public class Check {
	public static Timestamp t=Timestamp.valueOf("2012-11-14 09:23:00");
	public static int linenum=0;
	public static int allnum=0;
	public static double dissum=0;
	public static double alldissum=0;
	public static double timesum=0;
	public static void main(String[] agrs) throws Exception
	{
		File dir=new File("F://result");
		File[] fs=dir.listFiles();
		for(File e:fs)
		{
			check(e);
		}
		System.out.println(alldissum+" "+timesum+" "+(alldissum/1000)/(timesum/(3600)));
		System.out.println(linenum+" "+allnum+" "+(double)linenum/allnum);
		/*double s=GetDistance(116.4681168,39.8991966,116.4680328,39.8992195);
		System.out.println(s);*/
	}
	public static void check(File e) throws Exception
	{
		//System.out.println(e.getName());
		BufferedReader br=new BufferedReader(new FileReader(e));
		String line=null;
		String time=null;
		String ts=null;
		Timestamp curtime=null;
		Timestamp pretime=null;
		ArrayList<CPoint> pointlist=new ArrayList<CPoint>();
		double speed=0;
		CPoint p=null;
		boolean flag=true;
		while(true)
		{
			if(flag)
			{
				if((line=br.readLine())==null)
			    {
					if(pointlist.size()>1){
					
						 double len=distance(pointlist);
					 	 double delt=(pointlist.get(pointlist.size()-1).time.getTime()-pointlist.get(0).time.getTime())/(1000);
				 	     if((len/delt)>0){
				 		 timesum+=delt;
					 	 alldissum+=len;
				 		 linenum++;
				 	 }
					}
					break;
			     }
			}
			else
			{
				flag=true;
			}
			if(line.length()>0)
			{
				time=line.substring(11,25);
				ts=time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+time.substring(8,10)+":"+time.substring(10,12)+":"+time.substring(12,14);
				curtime=Timestamp.valueOf(ts);
				//if(Math.abs(curtime.getTime()-t.getTime())<(3600*1000))
				if(curtime.getHours()>=8&&curtime.getHours()<=10)
				{
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
				p=new CPoint();
				p.x=Double.valueOf(line.substring(26,37));
				p.y=Double.valueOf(line.substring(38,48));
				p.time=curtime;
				//System.out.println(p.x+" "+p.y);
				if(pretime==null)
				{
					pointlist.add(p);
					pretime=curtime;
				}
				else
				{
					double timedelt=curtime.getTime()-pretime.getTime();
					if(timedelt<(10*60*1000)&&timedelt>0)
					{
						pointlist.add(p);
						pretime=curtime;
					}
					else
					{
						if(pointlist.size()>1)
						{
						   double len=distance(pointlist);
					 	   double delt=(pointlist.get(pointlist.size()-1).time.getTime()-pointlist.get(0).time.getTime())/(1000);
				
					 	 if((len/delt)>0){
					 		 timesum+=delt;
						 	 alldissum+=len;
					 		 linenum++;
					 	 }
					 	 if(delt<0)
					 	 {
					 		 System.out.println(e.getName()+" "+pointlist.get(0).time+" "+pointlist.get(pointlist.size()-1).time);
					 	 }
					 	 
						}
						pretime=null;
						pointlist=new ArrayList<CPoint>();
						flag=false;
					}		
				}
				}
			}
		}
		br.close();
	}
	public static double distance(ArrayList<CPoint> p)
	{
		allnum++;
		Collections.sort(p);
		if(p.size()<=1)
		{
			return 0;
		}
		double sum=0;
		int i=0;
		for(i=1;i<p.size();i++)
		{
			sum+=GetDistance(p.get(i-1).x,p.get(i-1).y,p.get(i).x,p.get(i).y);
		}
		//System.out.println(sum);
		return sum;
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
class CPoint implements Comparable
{
    public double x;
	public double y;
	public Timestamp time=null;
	public void show()
	{
		System.out.println("("+x+","+y+") "+time);
	}
	@Override
	public int compareTo(Object o) {
	     CPoint p=(CPoint)o;
	    if(time.compareTo(p.time)<0)
	    {
	    	return 0;
	    }
	    else
	    {
	    	return 1;
	    }
	}
}
