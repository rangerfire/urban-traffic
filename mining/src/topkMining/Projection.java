package topkMining;
import java.util.*;
import java.io.*;

public class Projection
implements Comparable
{
	public ArrayList<Trajectory> trajlist=null;  //存放所有轨迹
	public ArrayList<Integer> prefix=new ArrayList<Integer>(); //模式的前缀
	private Transfergap transfer=new Transfergap();  //迁移时间
	private int supp=Integer.MAX_VALUE;      //支持数
	//构造函数
	public Projection(ArrayList<Trajectory> trajlist)
	{
		this.trajlist=trajlist;
		supp=trajlist.size(); 
	}
	//构造函数
	public Projection() {}
	
	public void write(BufferedWriter bw) throws Exception
	{
		bw.write(prefix.size()-1+","+supp+",{");
		int i;
		for(i=0;i<prefix.size()-1;i++)
		{
			bw.write(prefix.get(i)+",");
		}
		bw.write(prefix.get(i)+"},{");
		for(i=0;i<transfer.size()-1;i++)
		{
			bw.write(transfer.getTranfer(i)+",");
		}
		bw.write(transfer.getTranfer(i)+"}");
		bw.newLine();
		
	}
	
	
	//////////////
	//trajlist基本操作
	
	//获得轨迹的条数
	public int TrajectorySize()
	{
		return trajlist.size();
	}
	
	//获得指定位置的轨迹
	public Trajectory getTrajectory(int index)
	{
		return trajlist.get(index);
	}
	
	//显示所有轨迹
	 public void showTrajectory()
	 {
		for(int i=0;i<trajlist.size();i++)
		{
			trajlist.get(i).show();
		}
	}
	
	 /////////////////////////
	
	 
	 
	 
	 
	 
	 
	////////////////前缀的基本操作
	
	//添加前缀
	public void PrefixAdd(int regionnum)
	{
		prefix.add(regionnum);
	}
	
	//获得前缀的长度
    public int prefixSize() 
    {
		
		return prefix.size();
	}
    
   //显示前缀
	public void showPrefix()
	{
		for(int i=0;i<prefix.size();i++)
		{
			System.out.print(prefix.get(i)+"  ");
		}
		System.out.println();
	}
	
	//获得前缀的拷贝
	public ArrayList<Integer> getPrefix() 
	{
		return (ArrayList<Integer>) prefix.clone();
	}
	
	//设置前缀
	public void SetPrefix(ArrayList<Integer> pre)
	{
		prefix=pre;
	}
	
	
	public boolean prefixEqual(Projection o)
	{
		int size1=prefix.size();
		int size2=o.prefixSize();
		if(size1>size2)
		{
			size1=size2;
		}
		for(int i=0;i<size1;i++)
		{
			if(prefix.get(i)!=o.prefix.get(i))
			{
				return false;
			}
		}
		return true;
	}
	////////////////////////////
	
	
	
	
	
	////////支持数的基本操作
	
	//获得支持数
	public int getSupp()
	{
		return supp;
	}
	
	//设置支持数
	public void setSupp(int support)
	{
		supp=support;
	}
	/////////////////////////////
	
	
	//////////对transfer的基本操作
	public void showTransfer()
	{
		transfer.show();
	}
	
	//////////////
	
	
	///////////////////////
	//实现接口函数，以便排序
	public int compareTo(Object o) 
	{
		Projection proj=(Projection)o;
		if(supp<proj.supp)
		{
			return 1;
		}
		return 0;
	}
	
	//计算轨迹中每个点的密度，并按密度排序
	private  ArrayList<Integer> compute_density()
	{
		short[] density=new short[7219];
		Trajectory traj=null;
		String id=""; 
		for(int i=0;i<trajlist.size();i++)
		{
			//对于轨迹中有重复id的只计算一次密度
			if(!id.equals(trajlist.get(i).getID()))
			{
				traj=trajlist.get(i);
				id=traj.getID();
			    int region=0;
			    boolean[] per_density=new boolean[7219];
			   
			    for(int j=0;j<traj.SequenceSize();j++)
			    {
			    	
				    region=traj.getRegion(j);
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
		ArrayList<Integer> ROI=new  ArrayList<Integer>();
		ArrayList<Item> item=new ArrayList<Item>();
		for(int i=0;i<density.length;i++)
		{
			if(density[i]>0)
			{
				item.add(new Item(i,density[i]));
				//System.out.println(i+"    "+density[i]);
			}
		}
		density=null;
		Collections.sort(item);
		for(int i=0;i<item.size();i++)
		{
			ROI.add(item.get(i).itme);
		}
		item=null;
		return ROI;
	}
	
	//优先扩展k个长度为length的pattern，以此来提高阈值，以便后面减少搜索空间
	public void Extend(int length,ArrayList<Projection> topklist,int timethreshold,int k)
	{
		//showPrefix();
		//topklist以满
		if(topklist.size()==k)
		{
			return ;
		}
		
		//前缀为指定长度
		if(prefix.size()==length)
		{
			topklist.add(this);
			return ;
		}
		
		
		ArrayList<Integer> ROIlist=null;//存放ROI的集合
	    Projection pro=null;     //新的Projection
		ArrayList<Gap> gaplist=null;   //存放Gap
		
		ROIlist=compute_density();
		if(prefix.size()==0)
		{
		for(int i=0;i<k;i++)
		{
			pro=CopyTrajectory(ROIlist.get(i));
			if(pro.prefix.size()>=2)
			{
				gaplist=pro.getGap(timethreshold);
				pro.prune(gaplist);     //剪去无关的轨迹
			}
			pro.Extend(length, topklist,timethreshold,k);  //递归完成
		}
		}
		else
		{
			pro=CopyTrajectory(ROIlist.get(0));
			if(pro.prefix.size()>=2)
			{
				gaplist=pro.getGap(timethreshold);
				pro.prune(gaplist);     //剪去无关的轨迹
			}
			pro.Extend(length, topklist,timethreshold,k);  //递归完成

		}
	}
	
	///对projection 进行扩展，并拷贝Regionnum 以后的轨迹的部分
	public Projection CopyTrajectory(Integer regionnum)
	{
		Trajectory traj=null;
		Projection P=null;  //存放新的Projection
		//新的Projection
		P=new Projection();
		P.trajlist=new ArrayList<Trajectory>();
		P.prefix=new ArrayList<Integer>();
		
		//找到包含Region的所有轨迹
		for(int i=0;i<trajlist.size();i++)
		{
			traj=trajlist.get(i);
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
		P.SetPrefix(getPrefix());
		P.PrefixAdd(regionnum);
		return P;
	}
	
	
	
	
	//获得projection所有的时间间隔
	public ArrayList<Gap> getGap(int timethreshold)
	{
		ArrayList<Gap> gaplist=new ArrayList<Gap>();
		Gap gap=null;
		for(int i=0;i<trajlist.size();i++)
		{
			gap=new Gap();
			gap.Set(trajlist.get(i).getID(), trajlist.get(i).getLastPrefix(),trajlist.get(i).getLastGap(), timethreshold);
			gaplist.add(gap);
		}
		return gaplist;
	}
	
	
	//由gaplist获得密度最大的transfer,并将无关的轨迹过滤，设置supp
	public void prune(ArrayList<Gap> gaplist)
	{
		ArrayList<Integer> B=new ArrayList<Integer>();
		ArrayList<Gap> max=new ArrayList<Gap>();
		int gaptime=0;
		
		////收集所有时间间隔
		for(int i=0;i<gaplist.size();i++)
		{
			B.add(gaplist.get(i).getGapl());
			B.add(gaplist.get(i).getGaph());
		}
		Collections.sort(B);  //排序
		for(int i=0;i<B.size()-1;i++)
		{
			int low=B.get(i);
			int high=B.get(i+1);
			ArrayList<Gap> temp=new ArrayList<Gap>();
			for(int j=0;j<gaplist.size();j++)
			{
				if(gaplist.get(j).intersect(low,high))
				{
					temp.add(gaplist.get(j));
				}
			}
			
			//获得新的最大密度
			if(temp.size()>max.size())
			{
				max=temp;
				gaptime=(B.get(i)+B.get(i+1))/2;
			}
			temp=null;
		}
		//添加迁移时间
		transfer.Add(gaptime);
		
		//更新支持数
		if(supp>max.size())
		{
			supp=max.size();
		}
		Trajectory traj=null;
		
		//移除不相关的轨迹
		boolean[] exist=new boolean[trajlist.size()];
		for(int i=0;i<max.size();i++)
		{
			for(int j=trajlist.size()-1;j>=0;j--)
			{
				traj=trajlist.get(j);
				if(max.get(i).equal(traj.getID(),traj.getLastPrefix()))
				{
					exist[j]=true;
				}
			}
		}
		for(int i=0;i<trajlist.size();i++)
		{
			if(!exist[i])
			{
				trajlist.remove(i);
			}
		}
	}
	
	
	
	
	 public void Compute_density_blocks(Toplist topklist,int timeThreshold) 
	 {
			//showPrefix();
		   ArrayList<Transaction> A=new ArrayList<Transaction>();  //存放所有前续序列的transaction
		   //获得所有前缀序列的transaction
		   for(int i=0;i<trajlist.size();i++)
		   {
			   A.add(trajlist.get(i).getTransactionTime(timeThreshold));
		   }
		 /*  if(prefix.get(0)==1&&prefix.get(1)==3&&prefix.get(2)==5)
		   {
			   System.out.println("1 3 5");
			   for(int i=0;i<A.size();i++)
			   {
				   A.get(i).show();
			   }
		   }*/
		   Transfergap H=new Transfergap();
		   //递归调用
		   int supp=Integer.MAX_VALUE;
		   Recursive_density(A,topklist,prefix.size()-2,H,supp);
	}
	 
	 
	 
	//通过降维的方法计算annotation
	private void Recursive_density(ArrayList<Transaction> A,Toplist toplist,int d,Transfergap H,int supp)
	{
		//用于存放所有的时间分隔点
		ArrayList<Integer> B=new ArrayList<Integer>();
		
		//在向量的d维上的所有分解
		for(int i=0;i<A.size();i++)
		{
			B.add(A.get(i).getLow(d));
			B.add(A.get(i).getHight(d));
		}
		
		//对所有分解，将空间进行划分
		Collections.sort(B);
		
		//判断所有向量在d维上的密集程度
		for(int i=0;i<B.size()-1;i++)
		{
			ArrayList<Transaction> temp=new ArrayList<Transaction>();   //存放新的Transaction
			
			//对每一段空间进行密度统计
			for(int j=0;j<A.size();j++)
			{
				if(A.get(j).intersect(d,B.get(i),B.get(i+1)))
				{
					temp.add(A.get(j));
				}
			}
			//System.out.println(B.get(i)+" "+B.get(i+1)+" density="+temp.size()+" d="+d+" threshold="+threshold);
			
			//密度大于阈值
			if(temp.size()>=toplist.getMinSupp())
			{
				//System.out.println(B.get(i)+"   "+B.get(i+1)+" tempsize="+temp.size());
				if(temp.size()<supp)
				{
					supp=temp.size();
				}
				//添加annotation的分量值
				
				H.Add(0,(B.get(i)+B.get(i+1))/2);
				//System.out.println(B.get(i)+" "+B.get(i+1)+" "+H.getAnnotation(0));
				if(d==0)
				{
					Projection top=NewProj(temp);
					top.transfer.setTransfer(H.getTransfer());
					//top.supp=supp;
					toplist.add(top);
				}
				else
				{
					//通过递归实现降维处理
					Recursive_density(temp,toplist,d-1,H,supp);
				}
				H.removeFirst();
			}
		}
	}
	public Projection NewProj(ArrayList<Transaction> tran)
	{
		Projection proj=new Projection();
		proj.prefix=(ArrayList<Integer>) prefix.clone();
		proj.supp=tran.size();
		/*proj.trajlist=new ArrayList<Trajectory>();
		Trajectory traj=null;
		for(int i=0;i<trajlist.size();i++)
		{
			traj=trajlist.get(i);
			for(int j=0;j<tran.size();j++)
			{
				if(tran.get(j).equal(traj.getID(),traj.getPrefixTimeItem(traj.prefixSize()-1)))
				{
					proj.trajlist.add(traj);
				}
			}
		}*/
		return proj;
	}
	
	
	/*public ArrayList<Pattern> getPattern()
	{
		if(D==null)
		{
			return null;
		}
		ArrayList<Pattern> pattern=new ArrayList<Pattern>(); 
		Pattern p=null;
		Transfergap anno=null;
		for(int i=0;i<D.size();i++)
		{
			
			anno=D.get(i);
			p=new Pattern();
			p.SetSequence(prefix);
			p.SetAnnotation(anno.getTransfergap());
			pattern.add(p);
		}
		return pattern;
	}*/
}
