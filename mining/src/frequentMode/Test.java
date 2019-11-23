package Mining;
import java.util.*;
import java.io.*;
import java.util.regex.*;
public class Test {
	public static void main(String[] args) throws IOException
	{
		InputStreamReader isr = new InputStreamReader(new FileInputStream("E:/bus_stop_gps.txt"), "UTF-8");
		BufferedReader br=new BufferedReader(isr);
		String str=null;
		ArrayList<BusStop> bus_stop=new ArrayList<BusStop>();
		BusStop bsp=null;
		System.out.println("start");
		while((str=br.readLine())!=null)
		{
			bsp=new BusStop();
			bsp.SetBusStop(str);
			bus_stop.add(bsp);
		}
		br.close();
		File dir=new File("E:/test");
		File[] fs=dir.listFiles();
		Share share=new Share(0);
		try
		{
			for(int i=0;i<20;i++)
			{
				new myThread(share,fs,bus_stop).start();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			System.out.println("end");
		}
	}
	/*public static void translate(File e,BufferedWriter bw,ArrayList<BusStop> bus) throws IOException
	{
		BufferedReader br=null;
		br=new BufferedReader(new FileReader(e));
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
			    	if(bus.get(i).getDistance(lon, lat)<53)
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
	}*/

}
