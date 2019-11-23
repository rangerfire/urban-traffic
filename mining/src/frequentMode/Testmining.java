package Mining;
import java.sql.*;
import java.util.*;
import java.io.*;
public class Testmining 
{
	public static void main(String[] args) throws Exception
	{
		int freThreshold=5;    //Ƶ����ֵ
		int timeThreshold=300;  //ʱ�����ֵ
		Connection conn=null;
		BufferedWriter bw=new BufferedWriter(new FileWriter("E:/Pattern.txt"));
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/osm","postgres","123456");
		pstmt=conn.prepareStatement("select distinct id from taxi order by id");
		
		ArrayList<String> id=new ArrayList<String>();  //�������ID
		ArrayList<Trajectory> traj=new ArrayList<Trajectory>();  //������й켣
		rs=pstmt.executeQuery();
		//�������ID
		while(rs.next())
		{
			id.add(rs.getString(1));
		}
		
		pstmt=conn.prepareStatement("select regionnum,time from taxi where id=? order by time");
		
		//����ID������Ӧ�Ĺ켣
		
		for(int i=0;i<id.size();i++)
		{
			pstmt.setString(1, id.get(i));
			rs=pstmt.executeQuery();
			Trajectory traj1=new Trajectory();
			traj1.ID=id.get(i);
			int regionnum=-1;
			while(rs.next())
			{
				if(regionnum!=rs.getInt(1))
				{
					traj1.add(rs.getInt(1), rs.getTimestamp(2));
					regionnum=rs.getInt(1);
				}
			}
			if(traj1.size()>1)
			{
				traj.add(traj1);
			}
		}
		
		////print all trajectory
		/*for(int i=0;i<traj.size();i++)
		{
			traj.get(i).show();
		}*/
		
		
		ArrayList<Projection> T=new ArrayList<Projection>();  //����ҵ���Projection
		
		T.add(new Projection(traj));
		
		//ѭ�������е�Projection,����ʽ����
		while(T.size()>0)
		{
			ArrayList<Projection> T1=new ArrayList<Projection>();  //�����չ��Projection
			ArrayList<Pattern> p=null;
			for(int i=0;i<T.size();i++)
			{
				Projection pro=T.get(i);  //��i��Projection
				
				//Projection��ǰ׺���ȴ���2�����Եõ�һ��ģʽ
				if(pro.prefixSize()>=2)
				{
					//����Ǩ��ʱ��
					T.get(i).Compute_density_blocks(freThreshold,timeThreshold);
					p=T.get(i).getPattern();
					if(p!=null)
					{
						for(int j=0;j<p.size();j++)
					    {
							p.get(j).PrintToFile(bw);
					    }
					}
					else
					{
						System.out.println("no pattern");
					}
				}
				
				//����ڴ�Projection�й켣���ɵ�ROI
				ArrayList<Integer> ROI=null;
				ROI=PopularRegions(pro,freThreshold);
				
				//��ÿһ��ROI�ж����Ƿ������չ
				for(int j=0;j<ROI.size();j++)
				{
					T1=ExtendProjection(pro,ROI.get(j),T1,freThreshold);
				}
				//show(ROI);
				
			}
			//����T
			T=T1;
		}
		bw.flush();
		bw.close();
		System.out.println("end");
	}
	
	//��ʾ���е�ROI
	public static void show(ArrayList<Integer> ROI)
	{
		for(int i=0;i<ROI.size();i++)
		{
			System.out.print(ROI.get(i)+" ");
		}
		System.out.println();
	}
	
	//��ʾ���еĹ켣
	public static void show(Trajectory traj)
	{
		for(int i=0;i<traj.Sequence.size();i++)
		{
			System.out.print(traj.Sequence.get(i)+" ");
		}
		System.out.println();
	}
	
	//��ʾ����Projection�еĹ켣
	public static void show(Projection T)
	{
		System.out.println("prefix="+T.prefix);
		for(int i=0;i<T.traj.size();i++)
		{
			show(T.traj.get(i));
		}
	}
	
	//����ROI
	public static ArrayList<Integer> PopularRegions(Projection T,int threshold)
	{
		short[] density=new short[7219];
		Trajectory traj=null;
		String id=""; 
		for(int i=0;i<T.TrajectorySize();i++)
		{
			//���ڹ켣�����ظ�id��ֻ����һ���ܶ�
			if(!id.equals(T.getTrajectory(i).getID()))
			{
				id=T.getTrajectory(i).getID();
			    int region=0;
			    boolean[] per_density=new boolean[7219];
			   
			    for(int j=0;j<T.getTrajectory(i).Sequence.size();j++)
			    {
			    	
				    region=T.getTrajectory(i).getRegion(j);
				    //�켣�еĲ�ͬ��Ԫ��ֻ�ܼ���һ���ܶ�
				    if(per_density[region]==false)
				    {
					    density[region]++;
					    per_density[region]=true;
				    } 
			    }
			    per_density=null;	    
			}
		}
		ArrayList<Integer> ROI=new ArrayList<Integer>();
		
		//������ֵ��Ԫ��ΪROI
		for(int i=0;i<density.length;i++)
		{
			if(density[i]>=threshold)
			{
				ROI.add(i);
			}
		}
		
		//�ܶ�С����ֵ��Ԫ���ڱ��μ���������չ�в����ܳ�ΪƵ��Ԫ�أ���ɾȥ
		for(int i=0;i<T.TrajectorySize();i++)
		{
			int region=0;
			traj=T.getTrajectory(i);
			for(int j=traj.size()-1;j>=0;j--)
			{
				region=T.getTrajectory(i).getRegion(j);
				if(density[region]<threshold)
				{
					traj.remove(j);
				}
			}
		}
		return ROI;
	}
	
	//��ģʽ�������
	public static void Output(ArrayList<String> prefix,ArrayList<Integer> annotation)
	{
		//System.out.print(prefix.get(0));
		for(int i=1;i<prefix.size();i++)
		{
			System.out.print("->"+prefix.get(i));
		}
		System.out.println();
	}
	
	//��Projection������չ
	public static ArrayList<Projection> ExtendProjection(Projection T,int regionnum,ArrayList<Projection> T1,int threshold)
	{
		//System.out.println("extend region="+regionnum);
		Trajectory traj=null;
		Projection P=null;  //����µ�Projection
		//�µ�Projection
		P=new Projection();
		P.traj=new ArrayList<Trajectory>();
		P.prefix=new ArrayList<Integer>();
		
		//�ҵ�����Region�����й켣
		for(int i=0;i<T.TrajectorySize();i++)
		{
			traj=T.getTrajectory(i);
			Trajectory traj1=null;
			ArrayList<Integer> index=traj.contain(regionnum);  //���Region���ڹ켣��λ��
			
			//Region�ڴ˹켣�д���
			if(index!=null)
			{
				for(int j=0;j<index.size();j++)
				{
					traj1=new Trajectory();  //����¹켣
					traj1.ID=traj.ID;
				
				    //��ǰ׺���и��ƺ����
					try{
				    traj1.SetPrefixTime(traj.getPrefixtime());
				    traj1.addPrefix(traj.getAnnotationItem(index.get(j)));
					}
					catch(Exception e)
					{
						System.out.println(e);
						System.out.println(traj.size());
						traj.show();
						return null;
					}
					finally
					{
						//return null;
					}
				
				    //�Թ켣�����޸�
				    traj1.SetSequence(traj.SubSequence(index.get(j)+1));
				    traj1.SetAnnotation(traj.SubAnnotation(index.get(j)+1));
				    P.traj.add(traj1);
				}
			}
		}
		if(P.TrajectorySize()>=threshold)
		{
			//��Projection�е�ǰ׺���и���
			P.SetPrefix(T.getPrefix());
			P.PrefixAdd(regionnum);
			T1.add(P);
		}
		return T1;
	}
}
