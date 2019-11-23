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
	    ArrayList<Projection> T=new ArrayList<Projection>();  //存放找到的Projection
	    T.add(new Projection(trajlist));
	    while(T.size()>0)
	    {
	    	ArrayList<Projection> T1=new ArrayList<Projection>();  //存放扩展的Projection
			ArrayList<Pattern> p=null;
			for(int i=0;i<T.size();i++)
			{
				Projection pro=T.get(i);  //第i个Projection
				
				//Projection的前缀长度大于2，可以得到一个模式
				if(pro.prefixSize()>=length)
				{
					//计算迁移时间
					pro.Compute_density_blocks(top,timeThreshold);
				}
				else
				{
				
					//获得在此Projection中轨迹构成的ROI
				    ArrayList<Integer> ROI=null;
				    ROI=PopularRegions(pro,top.getMinSupp());
				
				    //对每一个ROI判断其是否可以扩展
				    for(int j=0;j<ROI.size();j++)
				    {
				    	T1=ExtendProjection(pro,ROI.get(j),T1,top.getMinSupp());
				    }
				}
				
			}
			//更新T
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
	
	//计算ROI
	public static ArrayList<Integer> PopularRegions(Projection T,int threshold)
	{
		short[] density=new short[7219];
		Trajectory traj=null;
		String id=""; 
		for(int i=0;i<T.TrajectorySize();i++)
		{
			//对于轨迹中有重复id的只计算一次密度
			if(!id.equals(T.getTrajectory(i).getID()))
			{
				id=T.getTrajectory(i).getID();
			    int region=0;
			    boolean[] per_density=new boolean[7219];
			   
			    for(int j=0;j<T.getTrajectory(i).Sequence.size();j++)
			    {
			    	
				    region=T.getTrajectory(i).getRegion(j);
				    //轨迹中的不同的元素只能计算一次密度
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
		
		//大于阈值的元素为ROI
		for(int i=0;i<density.length;i++)
		{
			if(density[i]>=threshold)
			{
				ROI.add(i);
			}
		}
		
		//密度小于阈值的元素在本次及后续的扩展中不可能成为频繁元素，故删去
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
	
	//对Projection进行扩展
	public static ArrayList<Projection> ExtendProjection(Projection T,int regionnum,ArrayList<Projection> T1,int threshold)
	{
		//System.out.println("extend region="+regionnum);
		Trajectory traj=null;
		Projection P=null;  //存放新的Projection
		P=new Projection();
		P.trajlist=new ArrayList<Trajectory>();
		P.prefix=new ArrayList<Integer>();
		
		//找到包含Region的所有轨迹
		for(int i=0;i<T.TrajectorySize();i++)
		{
			traj=T.getTrajectory(i);
			Trajectory traj1=null;
			ArrayList<Integer> index=traj.contain(regionnum);  //获得Region所在轨迹的位置
			
			//Region在此轨迹中存在
			if(index!=null)
			{
				for(int j=0;j<index.size();j++)
				{
					traj1=new Trajectory();  //存放新轨迹
					traj1.ID=traj.ID;
				
				    //对前缀进行复制和添加
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
				
				    //对轨迹进行修改
				    traj1.SetSequence(traj.SubSequence(index.get(j)+1));
				    traj1.SetAnnotation(traj.SubAnnotation(index.get(j)+1));
				    P.trajlist.add(traj1);
				}
			}
		}
		if(P.TrajectorySize()>=threshold)
		{
			//对Projection中的前缀进行复制
			P.SetPrefix(T.getPrefix());
			P.PrefixAdd(regionnum);
			T1.add(P);
		}
		return T1;
	}

	//对模式进行输出
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
