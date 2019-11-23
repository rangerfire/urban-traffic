package Exception_Models1;
import java.io.*;
import java.sql.*;
public class SetNormal {

	private int[] num=new int[8];
	private static double[] percent=new double[8];
	private static int mytime;
	
	public static void main(String[] args) throws Exception 
	{
		SetNormal sn=new SetNormal();
		File dir=new File("G://1_Study//Exception//taxi_group");//该文件应为按路段划分好的数据文件,名字为路的id
		File[] dis=dir.listFiles();
	
		//本人代码	
		File froadDia=new File("G://1_Study//Exception//RoadDia.txt");//写出的文件:所有路的分布数据，一次包括：路的id，15*8个percent
		BufferedWriter bw=new BufferedWriter(new FileWriter(froadDia));
		
		for(File e:dis)//不同路段数据文件夹
		{	
			String s1=e.getName();
			int j=0;
			while(s1.charAt(j)!='.')
			{
				j++;
			}
			s1=s1.substring(0,j);//获得路的id
			bw.write(s1);
			bw.write("\r\n");
			
			for(int i=0; i<15; i++)
			{
				sn.SetTime(i);
				sn.Set(e);
				sn.SetPercent();
				String str=String.valueOf(percent[0]);
				bw.write(str);
				for(int j1=1; j1<8; j1++)
				{
					str=String.valueOf(percent[j1]);
					bw.write("\r\n");
					bw.write(str);
				}
				bw.write("\r\n");
			}
		}
	
		bw.close();
		bw=null;
		//本人代码
	}
	public void Set(File e) throws Exception//扫描一个文件的每一行，将符合当前时间段的数据提取出来对num自增，构建直方图
	{
		
		BufferedReader br=new BufferedReader(new FileReader(e));
		String line=null;
		double speed=0;
		Timestamp t=null;
		String str=null,str1=null;
		while((line=br.readLine())!=null)//读取文件将某一文件所有数据都设好
		{
			if(line.length()>0)
			{
			
				if(line.charAt(50)==',')
				{
					speed=Double.valueOf(line.substring(49,50));
				}
				else//取速度
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
				
				//我的代码
				int con=Integer.parseInt(line.substring(17,18));
				//通过时间判断下标
				int index=0;
				if(con>=0&&con<6)
				{
					index=0;
				}
				if(con<20&&con>=6)
				{
					index=con-5;
				}
				if(con>=20&&con<=24)
				{
					index=14;
				}
				SetTimeNum(index,speed);	
				//我的代码
				
			}
		}
	}
	
	//我的代码
	public void SetTime(int index)//我的设置时间变量
	{
		mytime=index;
	}
	
	public SetNormal()//初始化
	{
		for(int i=0;i<num.length;i++)
		{
			num[i]=0;
		}
	}	
	//我的代码
	
	public void SetTimeNum(int index,double speed)//我的将t小时的速度分类
	{
		if(mytime==index)
		{
			SetNum(speed);
		}
	}
	//我的代码
	public void SetNum(double speed)//输入速度设置在某个速度范围的数量
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
	public void SetPercent()//设置某速度范围的百分比
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
	private static  void WritePercent(String e) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(e));
		String str=String.valueOf(percent[0]);
		bw.write(str);
		for(int i=1; i<8; i++)
		{
			str=String.valueOf(percent[i]);
			bw.write(",");
			bw.write(str);
		}
		bw.close();
		bw=null;
	}	
	
	
}
