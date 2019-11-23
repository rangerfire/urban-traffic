/*������Ҫ�����趨ÿ��·�������ֲ������ͨ��ɨ�����е����ݣ�������ÿ��·�ڲ�ͬ��ʱ�εĳ��������ֲ����
 *
 */
package Exception_Models1;
import java.sql.Timestamp;
import java.util.*;
import java.io.*;
public class RoadSet {
	private HashMap<Integer,Road> roadset=new HashMap<Integer,Road>(); 
	static ArrayList<RoadInfo> roadInfo_list=new ArrayList<RoadInfo>();//���·��id����ʼ�㣬�����㣬���������ݵ��·��
	public static double threshold=80.0;//�����õ��쳣�����ж�ֵ
	int hour;

	//����Ϊδ�޸ĵ�״̬
	public void reset()
	{
		Iterator iter=roadset.entrySet().iterator();
		Map.Entry<Integer, Road> entry=null;
		Road r=null;
		while(iter.hasNext())
		{
			entry=(Map.Entry<Integer, Road>)iter.next();
			r=(Road)entry.getValue();
			r.reset();
		}
	}
	
	public void SetHour(int hour)
	{
		this.hour=hour;
	}
	
   //����·�ķֲ���Ϣ
	public void LoadRoadset() throws Exception//��������·�ε�Info��id
	{
		int id;
		double[] data=null;

		BufferedReader bf=new BufferedReader(new FileReader("G://1_Study//Exception//RoadDia.txt"));//�ε�·������Ϣ
		Road r=null;
		String line = "";
		while((line=bf.readLine()) != null)
		{
			id=Integer.parseInt(line);
			r=new Road();
			for(int i=0; i<15; i++)
			{
				data=new double[8];
				for(int j=0;j<8;j++)//8��percent
				{
					line=bf.readLine();
					data[j]=Double.parseDouble(line);
				}
				r.info[i]=data;
			}
			roadset.put(id, r);
		}
		bf.close();
		bf=null;
	
	/*	while(sc.hasNextInt())//·��id
		{
			id=sc.nextInt();
			r=new Road();
			for(int i=0;i<15;i++)//ʱ��
			{
				data=new double[8];
				for(int j=0;j<8;j++)//8��percent
				{
					data[j]=sc.nextDouble();
				}
				r.info[i]=data;
			}
			roadset.put(id, r);
		}
		sc.close();
		sc=null;
		*/
	}
	//����·�Ļ�����Ϣ
	public void LoadRoadInfo() throws Exception
	{
		BufferedReader bf=new BufferedReader(new FileReader("G://1_Study//Exception//yichang_sections.txt"));//�ε�·������Ϣ
		RoadInfo ri=null;
		String line = "";
		while((line=bf.readLine()) != null)
		{
			ri = new RoadInfo();
			//System.out.println(line);
			String ss = "";
			int index = 1;
			char ch = line.charAt(index);
			
			//����1������
			while(Character.isDigit(ch))
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.id = Integer.parseInt(ss);
			
			//System.out.println(ri.id);
			//����2������
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.x1 = Double.parseDouble(ss);
			//System.out.println(ri.x1);
			//����3������
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.y1 = Double.parseDouble(ss);
			//System.out.println(ri.y1);
			//����4������
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.x2 = Double.parseDouble(ss);
			//System.out.println(ri.x2);
			//�����������
			index++;
			ch = line.charAt(index);
			while(!Character.isDigit(ch))
			{
				index++;
				ch = line.charAt(index);
			}
			ss = "" + ch;
			index++;
			ch = line.charAt(index);
			while(Character.isDigit(ch) || ch=='.')
			{
				ss = ss + ch;
				index++;
				ch = line.charAt(index);
			}
			ri.y2 = Double.parseDouble(ss);
			//System.out.println(ri.y2);
			//���ˣ�5������ȫ���������ˣ�
			while(ch!=',')
			{
				index++;
				ch = line.charAt(index);
			}
			index=index+3;//��·������
			ch = line.charAt(index);
			int i=index;
			while(ch!=']')
			{
				index++;
				ch = line.charAt(index);
			}
			index=index-1;
			
			ri.name=line.substring(i, index);
			//���ˣ�·����Ϣ���������ˣ�
			roadInfo_list.add(ri);
			ri=null;
		}
		bf.close();
		bf=null;
	}
	 
	//��ȡ��Ӧ·��id�����������򷵻�0

	
	//��ȡ��Ӧ·��id�����������򷵻�0
	public int GetRoadID(double x,double y){
		for(RoadInfo ri:roadInfo_list)
		{
			if(getDistance(x,y,ri)<20)
			{
				return ri.id;
			}
		}
		return 0;
	}
	public double getDistance(double x,double y,RoadInfo ri)//��õ���·�ľ������ڻ�õ��·��id
	{
		double a,b,c;
		double r;
		a=SearchData.GetDistance(x,y,ri.x1,ri.y1);
		b=SearchData.GetDistance(x,y,ri.x2,ri.y2);
		c=SearchData.GetDistance(ri.x1,ri.y1,ri.x2,ri.y2);
		r=(a+b+c)/2;
		double S=Math.sqrt((r-a)*(r-b)*(r-c)*r);
		double dis=2*S/c;
		if(dis<20)
		{
			double cs1=(a*a+c*c-b*b)/(2*a*c);
			double cs2=(b*b+c*c-a*a)/(2*b*c);
			if(cs1<0||cs2<0){
				return Double.MAX_VALUE;
			}
			else
			{
				return dis;
			}
		}
		else
		{
			return dis;
		}
	} 
	//����·�ĵ�ǰ״̬
	public void SetInfo(int id,double speed)
	{	
		Road r=roadset.get(id);
		r.SetCur(speed);
	}
	public ArrayList<OutlierInfo> check()//������·���쳣��Ϣ�ŵ�oi_list�в�����
	{
		Iterator iter=roadset.entrySet().iterator();
		Map.Entry<Integer, Road> entry=null;
		Road r=null;
		int id;
		OutlierInfo oi=null;
		ArrayList<OutlierInfo> oi_list=new ArrayList<OutlierInfo>();
		while(iter.hasNext())
		{
			entry=(Map.Entry<Integer, Road>)iter.next();
			r=(Road)entry.getValue();
			id=entry.getKey();
			oi=r.distance(hour);
			if(oi!=null)
			{
				oi.id=id;
				oi_list.add(oi);
			}
		}
		return oi_list;
	}
	public void display()
	{
		Road r=null;
		for(RoadInfo ri:roadInfo_list)
		{
			
			r=roadset.get(ri.id);
			ri.display();
			r.display();
			System.out.println();
		}
	}
	public static void main(String[] args)
	{
		RoadSet rs=new RoadSet();
		try
		{
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
class RoadInfo
{
	public int id;
	public double x1,y1,x2,y2;
	public String name;
	public void display()
	{
		System.out.println("roadID:"+id);
		System.out.println("point1:"+x1+","+y1+" point2:"+x2+","+y2);
		System.out.println("name:"+name);
	}
}
class Road
{
	
	public double[][] info=new double[15][8];   //��¼·�ķֲ���Ϣ
	private int[] curinfo=new int[8];
	private boolean isUpdate=false;
	
	public void display()
	{
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<8;j++)
			{
				System.out.print(info[i][j]+" ");
			}
			System.out.println();
		}
	}
	//���õ�ǰ�ķֲ���Ϣ
	public void SetCur(double speed)
	{
		int index;
		if(speed<1)
		{
			
			index=0;
		}
		else
		{
			if(speed<60)
			{
				index=(int)(speed/10);
			}
			else
			{
				index=7;
			}
		}
		curinfo[index]++;
		isUpdate=true;  //��־λ���޸�
	}
	
	public void Load(double[] data,int hour)//��ĳʱ�ε��ٶȷֲ����ݵ���
	{
		info[hour]=data;
	}
	
	
	public void reset()//����Ϊδ�޸�״̬
	{
		isUpdate=false;
		for(int i:curinfo)
		{
			i=0;
		}
	}
	
	public OutlierInfo distance(int hour)//����ֱ��ͼ���룬���ж��Ƿ��쳣,���쳣������һ��outlier����
	{
		//δ�޸Ĺ�
		if(!isUpdate)
		{
			return null;
		}
		double[] data=new double[8];//��ֱ��ͼ�İٷֱ�
		int sum=0;
		for(int i:curinfo)
		{
			sum+=i;
		}
		for(int i=0;i<8;i++)
		{
			data[i]=(double)curinfo[i]/sum;
		}
		int index = 0;//ʱ���ݴ���
		//ͨ��ʱ���ж��±�
		if(hour>=0&&hour<6)
		{
			index=0;
		}
		if(hour<20&&hour>=6)
		{
			index=hour-5;
		}
		if(hour>=20&&hour<=24)
		{
			index=14;
		}
            //���������ֲ���ֱ��ͼ����                  
		if(data.length==8)
		{
			double bc=0;
			for(int i=0;i<data.length;i++)
			{
				bc+=Math.sqrt(data[i]*info[index][i]);
			}
			double db=-Math.log(bc)*100;
			if(db>RoadSet.threshold)
			{
				OutlierInfo oi=new OutlierInfo();
				oi.normal=info[index];
				oi.abnormal=data;
				oi.dis=db;
				return oi;
			}
			else
			{
				return null;
			}
			
		}
		else
		{
			return null;
		}
	}
}
//һ���쳣�ֲ�����Ϣ
class OutlierInfo
{
	public int id;  // ·��ID��
	public double[] normal;  //·�������ֲ����
	public double[] abnormal;  //·���쳣�ֲ����
	public double dis;       //�ֲ�֮��ľ���
	public String name="";//·������
	public double x1;
	public double y1;
	public double x2;
	public double y2;
	
}

