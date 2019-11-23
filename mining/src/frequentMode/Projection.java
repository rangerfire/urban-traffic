

//Merge pattern 
//different pattern that share the same sequence but have similar annotation can be merged by a special function

package Mining;
import java.util.*;

public class Projection {
	public ArrayList<Trajectory> traj=null;  //存放所有轨迹
	public ArrayList<Integer> prefix=new ArrayList<Integer>(); //模式的前缀
	private ArrayList<Transfergap> D=null;  //存放最终的annotation
	Projection(ArrayList<Trajectory> traj)
	{
		this.traj=traj;
	}
	public Projection() {}
	
	//获得前缀的长度
	public int prefixSize()
	{
		return prefix.size();
	}
	
	//获得轨迹的条数
	public int TrajectorySize()
	{
		return traj.size();
	}
	
	//获得指定位置的轨迹
	public Trajectory getTrajectory(int index)
	{
		return traj.get(index);
	}
	
	//获得所有轨迹
	public ArrayList<Trajectory> getTrajectory()
	{
		return traj;
	}
	
	//获得前缀
	public ArrayList<Integer> getPrefix()
	{
		ArrayList<Integer> clone = (ArrayList<Integer>) prefix.clone();
		return clone;
	}
	
	public void SetPrefix(ArrayList<Integer> pre)
	{
		prefix=pre;
	}
	
	//获得前续的指定元素
	public Integer getPrefixItem(int index)
	{
		return prefix.get(index);
	}
	
	//对前续序列进行添加
	public void PrefixAdd(int regionnum)
	{
		prefix.add(regionnum);
	}
	
	//返回Projection中所包含的Pattern
	public ArrayList<Pattern> getPattern()
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
			//////////////////
			//System.out.println("Pattern");
			//showPrefix();
			//anno.show();
			/////////////////
			p.SetAnnotation(anno.getTransfergap());
			pattern.add(p);
		}
		return pattern;
	}
	
	//获得annotation
	public ArrayList<Transfergap> getTransfer(int threshold,int timethreshold)
	{
		if(D==null)
		{
			Compute_density_blocks(threshold,timethreshold);
		}
		return D;
	}
	
	//重新计算annotation，并返回
	public ArrayList<Transfergap> setAnnotation(int threshold,int timethreshold)
	{
		Compute_density_blocks(threshold,timethreshold);
		return D;
	}
	
	//计算密度
	public  ArrayList<Transfergap> Compute_density_blocks(int threshold,int timethreshold)
	{	
		//showPrefix();
		ArrayList<Transaction> A=new ArrayList<Transaction>();  //存放所有前续序列的transaction
		D=new ArrayList<Transfergap>();
		
		//获得所有前缀序列的transaction
		for(int i=0;i<traj.size();i++)
		{
			//traj.get(i).getTransactionTime(timethreshold).show();
			A.add(traj.get(i).getTransactionTime(timethreshold));
		}
		
		Transfergap H=new Transfergap();
		//递归调用
		Recursive_density(A,prefix.size()-2,H,threshold);
		return D;
	}
	
	//通过降维的方法计算annotation
	private void Recursive_density(ArrayList<Transaction> A,int d,Transfergap H,int threshold)
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
			if(temp.size()>=threshold)
			{
				//添加annotation的分量值
				
				H.Add(0,(B.get(i)+B.get(i+1))/2);
				//System.out.println(B.get(i)+" "+B.get(i+1)+" "+H.getAnnotation(0));
				if(d==0)
				{
					Transfergap anno=new Transfergap();
					//H.show();
					H.Copy(anno);
					D.add(anno);
				}
				else
				{
					//通过递归实现降维处理
					Recursive_density(temp,d-1,H,threshold);
				}
				H.removeFirst();
			}
		}
	}
	public void showPrefix()
	{
		for(int i=0;i<prefix.size();i++)
		{
			System.out.print(prefix.get(i)+" ");
		}
		System.out.println();
	}
}
