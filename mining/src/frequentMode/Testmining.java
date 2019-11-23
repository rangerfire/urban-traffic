package Mining;
import java.sql.*;
import java.util.*;
import java.io.*;
public class Testmining 
{
	public static void main(String[] args) throws Exception
	{
		int freThreshold=5;    //频度阈值
		int timeThreshold=300;  //时间差阈值
		Connection conn=null;
		BufferedWriter bw=new BufferedWriter(new FileWriter("E:/Pattern.txt"));
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection("jdbc:postgresql://localhost:5432/osm","postgres","123456");
		pstmt=conn.prepareStatement("select distinct id from taxi order by id");
		
		ArrayList<String> id=new ArrayList<String>();  //存放所有ID
		ArrayList<Trajectory> traj=new ArrayList<Trajectory>();  //存放所有轨迹
		rs=pstmt.executeQuery();
		//获得所有ID
		while(rs.next())
		{
			id.add(rs.getString(1));
		}
		
		pstmt=conn.prepareStatement("select regionnum,time from taxi where id=? order by time");
		
		//根据ID获得其对应的轨迹
		
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
		
		
		ArrayList<Projection> T=new ArrayList<Projection>();  //存放找到的Projection
		
		T.add(new Projection(traj));
		
		//循环找所有的Projection,增量式计算
		while(T.size()>0)
		{
			ArrayList<Projection> T1=new ArrayList<Projection>();  //存放扩展的Projection
			ArrayList<Pattern> p=null;
			for(int i=0;i<T.size();i++)
			{
				Projection pro=T.get(i);  //第i个Projection
				
				//Projection的前缀长度大于2，可以得到一个模式
				if(pro.prefixSize()>=2)
				{
					//计算迁移时间
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
				
				//获得在此Projection中轨迹构成的ROI
				ArrayList<Integer> ROI=null;
				ROI=PopularRegions(pro,freThreshold);
				
				//对每一个ROI判断其是否可以扩展
				for(int j=0;j<ROI.size();j++)
				{
					T1=ExtendProjection(pro,ROI.get(j),T1,freThreshold);
				}
				//show(ROI);
				
			}
			//更新T
			T=T1;
		}
		bw.flush();
		bw.close();
		System.out.println("end");
	}
	
	//显示所有的ROI
	public static void show(ArrayList<Integer> ROI)
	{
		for(int i=0;i<ROI.size();i++)
		{
			System.out.print(ROI.get(i)+" ");
		}
		System.out.println();
	}
	
	//显示所有的轨迹
	public static void show(Trajectory traj)
	{
		for(int i=0;i<traj.Sequence.size();i++)
		{
			System.out.print(traj.Sequence.get(i)+" ");
		}
		System.out.println();
	}
	
	//显示所有Projection中的轨迹
	public static void show(Projection T)
	{
		System.out.println("prefix="+T.prefix);
		for(int i=0;i<T.traj.size();i++)
		{
			show(T.traj.get(i));
		}
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
	
	//对Projection进行扩展
	public static ArrayList<Projection> ExtendProjection(Projection T,int regionnum,ArrayList<Projection> T1,int threshold)
	{
		//System.out.println("extend region="+regionnum);
		Trajectory traj=null;
		Projection P=null;  //存放新的Projection
		//新的Projection
		P=new Projection();
		P.traj=new ArrayList<Trajectory>();
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
				    P.traj.add(traj1);
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
}
