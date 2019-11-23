package topkMining;
import java.util.*;
import java.sql.*;
public class Trajectory {
	public String ID;   //轨迹ID
	public ArrayList<Integer> Sequence=new ArrayList<Integer>();   //轨迹的序列
	public ArrayList<Timestamp> annotation=new ArrayList<Timestamp>();  //轨迹的时间
	public ArrayList<Timestamp> prefixtimestamp=new ArrayList<Timestamp>();  //轨迹符合模式的前缀时间
	
	
	
	////////对整体的基本操作
	public int size()
	{
		return Sequence.size();
	}
	
	//对轨迹进行添加
	public void add(int region,Timestamp time)
	{
		Sequence.add(region);
		annotation.add(time);
	}
	
	//移除轨迹的指定结点
	public void remove (int index)
	{
		Sequence.remove(index);
		annotation.remove(index);
	}
	
	public void show()
	{
		System.out.print(ID+" ");
		for(int i=0;i<Sequence.size();i++)
		{
			System.out.print("<"+Sequence.get(i)+","+annotation.get(i)+"> ");
			
		}
		System.out.println();
	}
	//////////////////////
	
	
	
	
	
	
	
	/////////////对ID的基本操作
	//设置轨迹ID
	public void SetID(String ID)
	{
		this.ID=ID;
	}
	
	public String getID()
	{
		return ID;
	}
	//////////////////////////////
	
	
	
	
	
	
	///////////////对序列的基本操作
	//设置序列
	public void SetSequence(ArrayList<Integer> seq)
	{
		Sequence=seq;
	}
	
	//获得轨迹序列的长度
	public int SequenceSize()
	{
		return Sequence.size();
	}
	
	//获得指定的区域
	public int getRegion(int index)
	{
		return Sequence.get(index);
	}
	
	//获得子序列
	public ArrayList<Integer> SubSequence(int index)
	{
		int size=Sequence.size();
		ArrayList<Integer> sequence=new ArrayList<Integer>();
		for(int i=index;i<size;i++)
		{
			sequence.add(Sequence.get(i));
		}
		return sequence;
	}
	
	//判断轨迹序列中是否包含某个元素，若 存在返回下表，不存在返回-1
	public ArrayList<Integer> contain(int regionnum)
	{
		ArrayList<Integer> a=new ArrayList<Integer>();
		for(int i=0;i<Sequence.size();i++)
		{
			if(Sequence.get(i)==regionnum)
			{
				a.add(i);
			}
		}
		if(a.size()>0)
		{
			return a;
		}
		else
		{
			return null;
		}
	}
	
	
	/////////////////////////////
	
	
	
	
	///////////对序列时间的基本操作
	//设置序列时间
	public void SetAnnotation(ArrayList<Timestamp> anno)
	{
		annotation=anno;
	}
	
	//获得轨迹序列时间的长度
	public int AnnotationSize()
	{
		return annotation.size();
	}
	
	
	//获得指定的annotation元素
	public Timestamp getAnnotationItem(int index)
	{
		return annotation.get(index);
	}
	
	//获得子序列时间
	public ArrayList<Timestamp> SubAnnotation(int index)
	{
		int size=annotation.size();
		ArrayList<Timestamp> anno=new ArrayList<Timestamp>();
		for(int i=index;i<size;i++)
		{
			anno.add(annotation.get(i));
		}
		return anno;
	}
	
	
	///////////////////////
	
	
	
	
	
	
	///////////////////////对前缀时间的基本操作
	public void SetPrefixTime(ArrayList<Timestamp> pre)
	{
		prefixtimestamp=null;
		prefixtimestamp=pre;
	}
	
	//获得轨迹前缀时间的长度
	public int prefixSize()
	{
		return prefixtimestamp.size();
	}
	
	//获得指定的前缀时间
	public Timestamp getPrefixTimeItem(int index)
	{
		return prefixtimestamp.get(index);
	}
	
	//获得前缀的最后时间
	public Timestamp getLastPrefix()
	{
		return prefixtimestamp.get(prefixtimestamp.size()-1);
	}
	
	//获得前缀的最后时间间隔
	public int getLastGap()
	{
		int size=prefixtimestamp.size();
		int gap=(int)(prefixtimestamp.get(size-1).getTime()-prefixtimestamp.get(size-2).getTime())/1000;
		return gap;
	}
	
	//获得轨迹的前序的traveltime
	public Transaction getTransactionTime(int timeout)
	{
		Transaction tran=new Transaction(ID,prefixtimestamp.get(prefixtimestamp.size()-1));
		for(int i=0;i<prefixtimestamp.size()-1;i++)
		{
			
			int time=(int)(prefixtimestamp.get(i+1).getTime()-prefixtimestamp.get(i).getTime())/1000;
			if(time-timeout>0)
			{
				tran.insert(time-timeout,time+timeout);
			}
			else
			{
				tran.insert(0,time+timeout);
			}
		}
	    return tran;
	}
	
	//添加前缀时间
	public void addPrefix(Timestamp time)
	{
		prefixtimestamp.add(time);
	}
	
	public ArrayList<Timestamp> getPrefixtime()
	{
		ArrayList<Timestamp> time=(ArrayList<Timestamp>) prefixtimestamp.clone();
		return time;
	}
	
	///////////////////////////////
}
