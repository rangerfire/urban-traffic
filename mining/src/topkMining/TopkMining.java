package topkMining;
import java.util.*;
import java.sql.*;
import java.io.*;

public class TopkMining {
	public static void main(String[] args) throws Exception
	{
		int k=20;
		int length=3;
		int timeThreshold=300;
		int frethreshold=Integer.MAX_VALUE;
		
		
		System.out.println("Topk mining");
		ArrayList<String> id=new ArrayList<String>();
		ArrayList<Trajectory> trajlist=new ArrayList<Trajectory>();
		LoadID(id);
		LoadTrajectory(trajlist,id);
		
		
		
		Projection findthreshold=new Projection(trajlist);
		ArrayList<Projection> topklist=new ArrayList<Projection>();
		findthreshold.Extend(length, topklist,300, k);
		for(int i=0;i<topklist.size();i++)
		{
			System.out.print(topklist.get(i).getSupp()+"   ");
			if(topklist.get(i).getSupp()<frethreshold)
			{
				frethreshold=topklist.get(i).getSupp();
			}
		}
		System.out.println();
		System.out.println("threshold="+frethreshold);
		topklist.clear();
		
		
		Toplist top=new Toplist(k);
		top.setMinSupp(frethreshold);
		
		
		Support threshold=new Support();
		threshold.supp=frethreshold;
	    ArrayList<Projection> T=new ArrayList<Projection>();  //����ҵ���Projection
	    T.add(new Projection(trajlist));
	    while(T.size()>0)
	    {
	    	ArrayList<Projection> T1=new ArrayList<Projection>();  //�����չ��Projection
			ArrayList<Pattern> p=null;
			for(int i=0;i<T.size();i++)
			{
				Projection pro=T.get(i);  //��i��Projection
				
				//Projection��ǰ׺���ȴ���2�����Եõ�һ��ģʽ
				if(pro.prefixSize()>=length)
				{
					//����Ǩ��ʱ��
					pro.Compute_density_blocks(top,timeThreshold);
				}
				else
				{
				
					//����ڴ�Projection�й켣���ɵ�ROI
				    ArrayList<Integer> ROI=null;
				    ROI=PopularRegions(pro,top.getMinSupp());
				
				    //��ÿһ��ROI�ж����Ƿ������չ
				    for(int j=0;j<ROI.size();j++)
				    {
				    	T1=ExtendProjection(pro,ROI.get(j),T1,top.getMinSupp());
				    }
				}
				
			}
			//����T
			T=T1;
	    }
	    for(int i=0;i<top.size();i++)
	    {
	    	System.out.print(top.getProj(i).getSupp()+"   ");
	    	top.getProj(i).showPrefix();
	    	//top.getProj(i).showTransfer();
	    }
	    System.out.println(top.size());
	    BufferedWriter bw=new BufferedWriter(new FileWriter("E:/TopkPattern.txt"));
	    top.write(bw);
	    bw.flush();
	    bw.close();
		
	}
	public static void LoadID(ArrayList<String> id) throws Exception
	{
		Class.forName("org.postgresql.Driver");
		Connection conn=null;
		conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/osm","postgres","123456");
		PreparedStatement pstmt=conn.prepareStatement("select distinct id from taxi order by id");
		ResultSet rs=pstmt.executeQuery();
		while(rs.next())
		{
			id.add(rs.getString(1));
		}
		conn=null;
		pstmt=null;
		rs=null;
	}
	public static void LoadTrajectory(ArrayList<Trajectory> traj,ArrayList<String> id) throws Exception
	{
		Class.forName("org.postgresql.Driver");
		Connection conn=null;
		conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/osm","postgres","123456");
		PreparedStatement pstmt=conn.prepareStatement("select regionnum,time from taxi where id=? order by time");
		ResultSet rs=null;
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
		conn=null;
		pstmt=null;
		rs=null;
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
	
	//��Projection������չ
	public static ArrayList<Projection> ExtendProjection(Projection T,int regionnum,ArrayList<Projection> T1,int threshold)
	{
		//System.out.println("extend region="+regionnum);
		Trajectory traj=null;
		Projection P=null;  //����µ�Projection
		P=new Projection();
		P.trajlist=new ArrayList<Trajectory>();
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
				    P.trajlist.add(traj1);
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
}
