package Mining;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class myThread extends Thread{
	private Share s1=null;
	private File[] fs=null;
	ArrayList<BusStop> A=null;
	myThread(Share s,File[] f,ArrayList<BusStop> a)
	{
		s1=s;
		fs=f;
		A=new ArrayList<BusStop>();
		BusStop m=null;
		for(int i=0;i<a.size();i++)
		{
			m=new BusStop();
			m.region=String.valueOf(a.get(i).region);
			m.lon=a.get(i).lon;
			m.lat=a.get(i).lat;
			A.add(m);
		}
	}
	public void run()
	{
		int len=fs.length;
		int num;
		while((num=s1.add())<len)
		{
			try
			{
				translate(fs[num],A);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			finally
			{
				
			}
		}
	}
	public static void translate(File e,ArrayList<BusStop> bus) throws IOException
	{
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(e));
		BufferedWriter bw=new BufferedWriter(new FileWriter("E:/"+e.getName()));
		java.util.regex.Pattern p=java.util.regex.Pattern.compile("\\d+(\\.\\d+)?");
		Matcher m=null;
		String str=null;
		Point point=new Point();
		while((str=br.readLine())!=null)
		{	
			if(str.length()>0)
			{
				double lon,lat;
				m=p.matcher(str);
				m.find();
				point.id=m.group();
				m.find();
				point.trig=Integer.valueOf(m.group());
			    m.find();
			    point.status=Integer.valueOf(m.group());
			    m.find();
			    point.time=changeTime(m.group());
			    m.find();
			    lon=Double.valueOf(m.group());
			    m.find();
			    lat=Double.valueOf(m.group());
			    m.find();
			    point.GPSSpeed=Integer.valueOf(m.group());
			    m.find();
			    point.GPSDirection=Integer.valueOf(m.group());
			    m.find();
			    point.GPSStatus=Integer.valueOf(m.group());
			    for(int i=0;i<bus.size();i++)
			    {
			    	double dis=bus.get(i).getDistance(lon, lat);
			    	if(dis<53&&dis>=0)
			    	{
			    		point.regionnum=i;
			    		bw.write(point.toString());
					    bw.newLine();
					    break;
				    }
			    }
			    //System.out.println(point.toString());
			}
		}
		bw.flush();
		System.out.println(e.getName());
	}
	public static String changeTime(String time)
	{
		StringBuffer sb=new StringBuffer(time);
		sb.insert(4, '-');
		sb.insert(7, '-');
		sb.insert(10, ' ');
		sb.insert(13, ':');
		sb.insert(16, ':');
		return sb.toString();
	}
}
