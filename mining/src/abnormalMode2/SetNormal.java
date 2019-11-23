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
		File dir=new File("G://1_Study//Exception//taxi_group");//���ļ�ӦΪ��·�λ��ֺõ������ļ�,����Ϊ·��id
		File[] dis=dir.listFiles();
	
		//���˴���	
		File froadDia=new File("G://1_Study//Exception//RoadDia.txt");//д�����ļ�:����·�ķֲ����ݣ�һ�ΰ�����·��id��15*8��percent
		BufferedWriter bw=new BufferedWriter(new FileWriter(froadDia));
		
		for(File e:dis)//��ͬ·�������ļ���
		{	
			String s1=e.getName();
			int j=0;
			while(s1.charAt(j)!='.')
			{
				j++;
			}
			s1=s1.substring(0,j);//���·��id
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
		//���˴���
	}
	public void Set(File e) throws Exception//ɨ��һ���ļ���ÿһ�У������ϵ�ǰʱ��ε�������ȡ������num����������ֱ��ͼ
	{
		
		BufferedReader br=new BufferedReader(new FileReader(e));
		String line=null;
		double speed=0;
		Timestamp t=null;
		String str=null,str1=null;
		while((line=br.readLine())!=null)//��ȡ�ļ���ĳһ�ļ��������ݶ����
		{
			if(line.length()>0)
			{
			
				if(line.charAt(50)==',')
				{
					speed=Double.valueOf(line.substring(49,50));
				}
				else//ȡ�ٶ�
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
				
				//�ҵĴ���
				int con=Integer.parseInt(line.substring(17,18));
				//ͨ��ʱ���ж��±�
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
				//�ҵĴ���
				
			}
		}
	}
	
	//�ҵĴ���
	public void SetTime(int index)//�ҵ�����ʱ�����
	{
		mytime=index;
	}
	
	public SetNormal()//��ʼ��
	{
		for(int i=0;i<num.length;i++)
		{
			num[i]=0;
		}
	}	
	//�ҵĴ���
	
	public void SetTimeNum(int index,double speed)//�ҵĽ�tСʱ���ٶȷ���
	{
		if(mytime==index)
		{
			SetNum(speed);
		}
	}
	//�ҵĴ���
	public void SetNum(double speed)//�����ٶ�������ĳ���ٶȷ�Χ������
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
	public void SetPercent()//����ĳ�ٶȷ�Χ�İٷֱ�
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
