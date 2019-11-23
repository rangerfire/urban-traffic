package topkMining;
import java.util.*;
import java.io.*;
public class Toplist {
	private Projection[] topklist=null;
	private int minsupp=0;
	private int scale=0;
	private int size=0;
	
	public int size()
	{
		return size;
	}
	public void show()
	{
		for(int i=0;i<size;i++)
		{
			System.out.print(topklist[i].getSupp()+" ");
		}
		System.out.println();
	}
	
	public Projection getProj(int index)
	{
		return topklist[index];
	}
	
	public void setMinSupp(int min)
	{
		minsupp=min;
	}
	
	public int getMinSupp()
	{
		return minsupp;
	}
	
	////构造函数
	Toplist(ArrayList<Projection> top)
	{
		scale=top.size();
		topklist=new Projection[scale];
		for(int i=0;i<scale;i++)
		{
			topklist[i]=top.get(i);
		}
		Arrays.sort(topklist);
		minsupp=topklist[scale-1].getSupp();
		size=scale;
	}
	
	//构造函数
	public Toplist(int k)
	{
		topklist=new Projection[k];
		size=0;
		scale=k;
	}
	
	/*public void add(Projection proj)
	{
		if(size<scale)
		{
			size++;
		}
		topklist[size-1]=proj;
		for(int i=size-1;i>=1;i--)
		{
			if(topklist[i].getSupp()>topklist[i-1].getSupp())
			{
				swap(i,i-1);
			}
			else
			{
				break;
			}
		}
		if(size==scale)
		{
		    minsupp=topklist[size-1].getSupp();
		}
	}*/
	
	public void add(Projection proj)
	{
		
		int flag=0;
		int index=0;
		for(int i=0;i<size-1;i++)
		{
			if(proj.prefixEqual(topklist[i]))
			{
				//System.out.println("equal");
				if(proj.getSupp()>topklist[i].getSupp())
				{
					topklist[i]=proj;
					flag=1;
					index=i;
				    break;
				}
				else
				{
					return ;
				}
			}
		}
		if(flag==0)
		{
			if(size<scale)
			{
				size++;
			}
			topklist[size-1]=proj;
			index=size-1;
		}
		for(int i=index;i>=1;i--)
		{
			if(topklist[i].getSupp()>topklist[i-1].getSupp())
			{
				swap(i,i-1);
			}
			else
			{
				break;
			}
		}
		if(size==scale)
		{
		    minsupp=topklist[size-1].getSupp();
		}
	}
	
	public void insert(Projection proj,int index)
	{
		topklist[index]=proj;
	}
	
	
	public void swap(int i,int j)
	{
		Projection temp=null;
		temp=topklist[i];
		topklist[i]=topklist[j];
		topklist[j]=temp;
	}
	public void write(BufferedWriter bw) throws Exception
	{
		ArrayList<Integer> list=null;
		for(int i=0;i<size-1;i++)
		{
			topklist[i].write(bw);
		}
	}

}
