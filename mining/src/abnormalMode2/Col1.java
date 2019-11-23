package Exception_Models1;

/*
 * ���Ĳ���
  		
  
  
  
 */
import java.io.*;
import java.sql.*;
import java.util.*;
public class Col1 
{
	public static RoadSet roads=new RoadSet();	//����30�죬ÿ�춼��һ��roadset����
	public static BufferedReader br=null;
	public static ArrayList<RoadInfo> roadInfo_list=new ArrayList<RoadInfo>();
	
	//�������������������ں�Сʱ
	public static void main(String[] args) throws Exception 
	{
	
		//��·��Ϣ��ֻ��Ҫ����һ��
		roads.LoadRoadInfo();
		roadInfo_list = roads.roadInfo_list;
		//ÿ�춼Ҫ���س�һ�� ��·�ٶ�ֱ��ͼ ��
		
		roads.LoadRoadset();	//��������ֱ��ͼ
		
		//�ؼ�������μ��ص�ǰֱ��ͼ��
	
		//ÿ��·��ÿ���ÿ��ʱ���   =�� ����һ����ǰʱ����ٶ�ֱ��ͼ
		for(int hour=0;hour<24;hour++)
		{
			roads.hour = hour;	//����ʱ��
			
			File f = new File("G://1_Study//Exception//taxi_group");//�����а�·�ֵ����ݵ��ļ���
			File[] fs = f.listFiles();
			for(int i=0;i<fs.length;i++)
			{
				//idΪ�ļ���
				int id = Integer.parseInt( fs[i].getName().replaceAll("[.][^.]+$", "") );
				//System.out.println(id);
				br = new BufferedReader(new FileReader(fs[i]));
				
				Proccess(br, hour, id);	//���õ�ǰʱ���ֱ��ͼ��
				br.close();
				br = null;
				
			}
			//���������ļ�����ʱ����һ��    roads�����Խ���check����
			System.out.println(hour);
			ArrayList<OutlierInfo> oi_list =  roads.check();
			for(int myi=0; myi<oi_list.size(); myi++)
			{
				int index=oi_list.get(myi).id;//���ĳ�쳣��Ϣ���·��id
				System.out.println(oi_list.get(myi).id);
				oi_list.get(myi).name =roadInfo_list.get(index).name;
				System.out.println(oi_list.get(myi).name);
				oi_list.get(myi).x1 =roadInfo_list.get(index).x1;
				oi_list.get(myi).y1 =roadInfo_list.get(index).y1;
				oi_list.get(myi).x2 =roadInfo_list.get(index).x2;
				oi_list.get(myi).y2 =roadInfo_list.get(index).y2;
			}
			OutPut(oi_list,hour);
		}
	}

	//��ĳһ�������еó��ٶ�ֵ
	public static double getSpeed(String line)
	{
		double speed;
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
		return speed;
	}
	
	/*
	 * ��ĳ���ļ��ж�ȡÿ����¼��Ϣ,�ҵ���������ʱ��ĵ���Ϣ
	         ���øõ������ڵĵ�·��id�ţ�����Ӧ���ٶȣ������(RoadSet)roads��  -> �൱�����õ�ǰ�ٶ�ֱ��ͼ
	*/
	public static void Proccess(BufferedReader br,int hour,int id) throws Exception
	{
		String line=null;
		while((line=br.readLine())!=null)
		{
			if(line.length()>0)
			{
				if(hour == Integer.parseInt(line.substring(19, 21)))  //ʱ��ƥ��
				{
					
					//������Ϣ
					if(id!=0)  
						roads.SetInfo(id, getSpeed(line));
				}
			}
		}
	}
	
	
	
	//����쳣��Ϣ��¼�� �� �� F:\Taxi_Data\Outlier_list.txt ��
	public static void OutPut(ArrayList<OutlierInfo> oi_list,int hour) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("G://1_Study//Exception//Outlier_list.txt",true));
		double speed=0;
		
		for(OutlierInfo e:oi_list)
		{
			bw.write(e.id+",");
			bw.write(e.name+";");
			  
			bw.write(e.y1+";");
			bw.write(e.x1+",");
			
			bw.write(e.y2+";");
			bw.write(e.x2+",");
			bw.write(hour+",");
			for(int i=0;i<7;i++)
			{
				speed+=e.normal[i]*(i*10+5);				
			}
			bw.write(speed+",");//д������ƽ���ٶ�
			
			speed=0;
			for(int i=0;i<7;i++)
			{
				speed+=e.abnormal[i]*(i*10+5);
			}
			bw.write(speed+"");//д������ƽ���ٶ�
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
}

