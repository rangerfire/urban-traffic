package topkMining;
import java.sql.*;
public class Gap {
	private String ID=null;
	private Timestamp lasttime=null;
	private int gap_l=0;
	private int gap_h=0;
	
	public Gap(String id,Timestamp time,int transfer,int timethreshold)
	{
		ID=id;
		lasttime=time;
		gap_l=transfer-timethreshold;
		gap_h=transfer+timethreshold;
		if(gap_l<0){
			gap_l=0;
		}
	}
	public Gap()
	{}
	public void Set(String id,Timestamp time,int transfer,int timethreshold)
	{
		ID=id;
		lasttime=time;
		gap_l=transfer-timethreshold;
		gap_h=transfer+timethreshold;
		if(gap_l<0){
			gap_l=0;
		}
	}
	public boolean intersect(int l,int h)
	{
		if(gap_l<h&&gap_h>l)
		{
			return true;
		}
		return false;
	}
	
	public int getGapl()
	{
		return gap_l;
	}
	
	
	
	public int getGaph()
	{
		return gap_h;
	}
	
	
	public boolean equal(String ID,Timestamp time)
	{
		
		if(this.ID.equals(ID)&&lasttime.equals(time))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void show() {
		// TODO Auto-generated method stub
		System.out.println(ID+" "+lasttime+"  "+gap_l+" "+gap_h);
		
	}

}
