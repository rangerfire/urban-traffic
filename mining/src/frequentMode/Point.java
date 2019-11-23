package Mining;

public class Point {
	public String id;
	public int trig;
	public int status;
	public String time;
	public int regionnum=0;
	public int GPSSpeed;
	public int GPSDirection;
	public int GPSStatus;
	public String toString()
	{
		String str=id+","+String.valueOf(trig)+","+String.valueOf(status)+","+time+","+String.valueOf(regionnum)+","+String.valueOf(GPSSpeed)+","+String.valueOf(GPSDirection)+","+String.valueOf(GPSStatus);
		return str;
	}
}
