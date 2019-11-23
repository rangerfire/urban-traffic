package Mining;

public class BusStop {
	public int id;
	public String region=null;
	public double lon;
	public double lat;
	BusStop()
	{
		
	}
	//设置公交站台的相关信息
	public void SetBusStop(String str)
	{
		int first,last;
		first=str.indexOf(',');
		last=str.lastIndexOf(',');
		region=str.substring(0,first);
		lon=Double.valueOf(str.substring(last+1));
		lat=Double.valueOf(str.substring(first+1,last));
	}
	
	public void print()
	{
		System.out.println(region+" "+lat+" "+lon);
	}
	
	
	//获得点到该公交站台的距离
	public double getDistance(double lon,double lat)
	{
		double dis=GetDistance(this.lon,this.lat ,lon,lat);
		return dis;
	}
	private  final  double EARTH_RADIUS = 6378137;//赤道半径(单位m)  
    private  double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
	private double GetDistance(double lon1,double lat1,double lon2, double lat2)  
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
