/*
 *������Ҫ����Ѱ��ָ��·�õ����ݣ�����������·�ϵ��߹������ݵ�
 *������ֱ�ӴӰٶȵ�ͼ�ҵü���·�����ԾͰ���Щ·����Ϣֱ�ӷ���һ���ļ��У�Ȼ    ����ļ���ֱ�Ӷ�ȡ��
 *·����Ϣ��Ҫ����·�����˵ľ�γ�ȣ����һ��·�������ߣ�����Ϊ������·
 */
package Exception_Models1;
import java.io.*;
import java.util.Scanner;
public class SearchData 
{
	static String path="F://result1/"; //������յ���ѡ��������Ϣ�ļ�
	static Line road=new Line();
	static Line[] roads=new Line[8];
	static double[][] data=new double[8][4];
	public static void main(String[] args) throws Exception 
	{	
	    Scanner sc=new Scanner(new File("H://road.txt"));   //���·����Ϣ���ļ�
	    int i=0;
	    while(sc.hasNextDouble())
	    {
	    	roads[i]=new Line();
	    	roads[i].s.x=sc.nextDouble(); //x,y�ֱ��ʾ·�ľ�γ�ȣ�s��·��һ�ˣ�t��·����һ��
	    	roads[i].s.y=sc.nextDouble();
	    	roads[i].t.x=sc.nextDouble();
	    	roads[i].t.y=sc.nextDouble();
	    	roads[i].set();
	    	i++;
	    }
		File dir=new File("F://result");   //ԭʼ�����ļ�
		File[] fs=dir.listFiles();
		System.out.println(fs.length);    //�ݹ��ȡ����11�·������ļ�
		for(File e:fs)
		{
			Search1(e);
			System.out.println(e.getName());
		}
	}
	static public void Search1(File e) throws Exception
	{
		System.out.println(e.getName());
		BufferedReader br=new BufferedReader(new FileReader(e));
		BufferedWriter bw=new BufferedWriter(new FileWriter(path+e.getName()));
		String line=null;
		int i,j;
		String num1,num2;
		double x,y;
		while((line=br.readLine())!=null)//��ȡÿ�����¼������ȡ��γ�ȣ��ж��Ƿ������е�·���У�������򱣴棬����������
		{
			if(line.length()>0)
			{
				i=26;
				while(line.charAt(i)!=',')
				{
					i++;
				}
				num1=line.substring(26,i);
				i++;
				j=i;
				while(line.charAt(i)!=',')
				{
					i++;
				}
				num2=line.substring(j,i);
				x=Double.valueOf(num1);
				y=Double.valueOf(num2);
				for(int k=0;k<8;k++)  //�����8����Ϊ����ʵ����ֻ����8��·��
				{
					if(roads[k].getDistance(x, y)<20) //���õ��뵱ǰ·���ľ���С��20ʱ��Ϊ�õ����ڸ�·��
					{
						bw.write(line);
						bw.newLine();
						break;
					}
				}
			}
		}
	}

	private  static final  double EARTH_RADIUS = 6378137;//����뾶(��λm)  
    private  static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
	public static double GetDistance(double lon1,double lat1,double lon2, double lat2) //���ڼ��㣬������֮��ľ��룬��ʹ�þ�γ�ȱ�ʾ�������㷨���Բ������漰���ռ伸�ε�֪ʶ
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
class Point//���࣬���ڴ�ž�γ�ȵ�
{
	public double x;
	public double y;
}
class Line
{
	public Point s=new Point();
	public Point t=new Point();
	public double length;
	public void set()  //���ڼ����·�ĳ���
	{
		length=SearchData.GetDistance(s.x,s.y,t.x,t.y);
	}
	public double getDistance(double x,double y)  //���ڼ���㵽·�ľ��룬�������/��=�߼���������ʹ�ú��׹�ʽ����
	{
		double a,b,c;
		double r;
		a=SearchData.GetDistance(x,y,s.x,s.y);
		b=SearchData.GetDistance(x,y,t.x,t.y);
		c=length;
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
	public void show()
	{
		System.out.println(s.x+","+s.y+" "+t.x+","+t.y);
	}
}
