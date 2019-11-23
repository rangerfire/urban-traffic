import java.io.*;
import java.sql.*;
public class SetNormal {

	private int[] num=new int[8];
	private double[] percent=new double[8];
	private Timestamp time=null;
	public static void main(String[] args) throws Exception 
	{
		SetNormal sn=new SetNormal();
		File dir=new File("F://result3");
		File[] dis=dir.listFiles();
		sn.SetTime("2012-11-01 17:48:00");
		for(File e:dis)
		{
			sn.Set(e);
		}
		sn.SetPercent();
		sn.showPercent();
	}
	public void Set(File e) throws Exception
	{
		
		BufferedReader br=new BufferedReader(new FileReader(e));
		String line=null;
		double speed=0;
		Timestamp t=null;
		String str=null,str1=null;
		while((line=br.readLine())!=null)
		{
			if(line.length()>0)
			{
				str=line.substring(11,25);
				str1=str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8)+" "+str.substring(8,10)+":"+str.substring(10,12)+":"+str.substring(12,14);
				t=Timestamp.valueOf(str1);
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
				SetTimeNum(t,speed);
			}
		}
	}
	public void SetTime(String str)
	{
		time=Timestamp.valueOf(str);
	}
	public SetNormal()
	{
		for(int i=0;i<num.length;i++)
		{
			num[i]=0;
		}
	}
	public void SetTimeNum(Timestamp t,double speed)
	{
		if(t.getHours()==time.getHours())
		{
			if(t.getDate()==time.getDate())
			SetNum(speed);
		}
	}
	public void SetNum(double speed)
	{
		if(speed<1)
		{
			num[0]++;
			return ;
		}
		
		int s=(int)(speed/10);
		
		if(s>=6)
		{
			num[7]++;
		}
		else
		{
			num[s+1]++;
		}
	}
	public void SetPercent()
	{
		int sum=0;
		int i=0;
		for(i=0;i<8;i++)
		{
			sum+=num[i];
		}
		for(i=0;i<8;i++)
		{
			percent[i]=(double)num[i]/sum;
		}
	}
	public void showPercent()
	{
		for(int i=0;i<8;i++){
			System.out.println(num[i]+"  "+percent[i]);
		}
		System.out.println();
	}

}
