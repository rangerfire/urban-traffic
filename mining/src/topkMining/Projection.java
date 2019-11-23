package topkMining;
import java.util.*;
import java.io.*;

public class Projection
implements Comparable
{
	public ArrayList<Trajectory> trajlist=null;  //������й켣
	public ArrayList<Integer> prefix=new ArrayList<Integer>(); //ģʽ��ǰ׺
	private Transfergap transfer=new Transfergap();  //Ǩ��ʱ��
	private int supp=Integer.MAX_VALUE;      //֧����
	//���캯��
	public Projection(ArrayList<Trajectory> trajlist)
	{
		this.trajlist=trajlist;
		supp=trajlist.size(); 
	}
	//���캯��
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
	//trajlist��������
	
	//��ù켣������
	public int TrajectorySize()
	{
		return trajlist.size();
	}
	
	//���ָ��λ�õĹ켣
	public Trajectory getTrajectory(int index)
	{
		return trajlist.get(index);
	}
	
	//��ʾ���й켣
	 public void showTrajectory()
	 {
		for(int i=0;i<trajlist.size();i++)
		{
			trajlist.get(i).show();
		}
	}
	
	 /////////////////////////
	
	 
	 
	 
	 
	 
	 
	////////////////ǰ׺�Ļ�������
	
	//���ǰ׺
	public void PrefixAdd(int regionnum)
	{
		prefix.add(regionnum);
	}
	
	//���ǰ׺�ĳ���
    public int prefixSize() 
    {
		
		return prefix.size();
	}
    
   //��ʾǰ׺
	public void showPrefix()
	{
		for(int i=0;i<prefix.size();i++)
		{
			System.out.print(prefix.get(i)+"  ");
		}
		System.out.println();
	}
	
	//���ǰ׺�Ŀ���
	public ArrayList<Integer> getPrefix() 
	{
		return (ArrayList<Integer>) prefix.clone();
	}
	
	//����ǰ׺
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
	
	
	
	
	
	////////֧�����Ļ�������
	
	//���֧����
	public int getSupp()
	{
		return supp;
	}
	
	//����֧����
	public void setSupp(int support)
	{
		supp=support;
	}
	/////////////////////////////
	
	
	//////////��transfer�Ļ�������
	public void showTransfer()
	{
		transfer.show();
	}
	
	//////////////
	
	
	///////////////////////
	//ʵ�ֽӿں������Ա�����
	public int compareTo(Object o) 
	{
		Projection proj=(Projection)o;
		if(supp<proj.supp)
		{
			return 1;
		}
		return 0;
	}
	
	//����켣��ÿ������ܶȣ������ܶ�����
	private  ArrayList<Integer> compute_density()
	{
		short[] density=new short[7219];
		Trajectory traj=null;
		String id=""; 
		for(int i=0;i<trajlist.size();i++)
		{
			//���ڹ켣�����ظ�id��ֻ����һ���ܶ�
			if(!id.equals(trajlist.get(i).getID()))
			{
				traj=trajlist.get(i);
				id=traj.getID();
			    int region=0;
			    boolean[] per_density=new boolean[7219];
			   
			    for(int j=0;j<traj.SequenceSize();j++)
			    {
			    	
				    region=traj.getRegion(j);
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
	
	//������չk������Ϊlength��pattern���Դ��������ֵ���Ա������������ռ�
	public void Extend(int length,ArrayList<Projection> topklist,int timethreshold,int k)
	{
		//showPrefix();
		//topklist����
		if(topklist.size()==k)
		{
			return ;
		}
		
		//ǰ׺Ϊָ������
		if(prefix.size()==length)
		{
			topklist.add(this);
			return ;
		}
		
		
		ArrayList<Integer> ROIlist=null;//���ROI�ļ���
	    Projection pro=null;     //�µ�Projection
		ArrayList<Gap> gaplist=null;   //���Gap
		
		ROIlist=compute_density();
		if(prefix.size()==0)
		{
		for(int i=0;i<k;i++)
		{
			pro=CopyTrajectory(ROIlist.get(i));
			if(pro.prefix.size()>=2)
			{
				gaplist=pro.getGap(timethreshold);
				pro.prune(gaplist);     //��ȥ�޹صĹ켣
			}
			pro.Extend(length, topklist,timethreshold,k);  //�ݹ����
		}
		}
		else
		{
			pro=CopyTrajectory(ROIlist.get(0));
			if(pro.prefix.size()>=2)
			{
				gaplist=pro.getGap(timethreshold);
				pro.prune(gaplist);     //��ȥ�޹صĹ켣
			}
			pro.Extend(length, topklist,timethreshold,k);  //�ݹ����

		}
	}
	
	///��projection ������չ��������Regionnum �Ժ�Ĺ켣�Ĳ���
	public Projection CopyTrajectory(Integer regionnum)
	{
		Trajectory traj=null;
		Projection P=null;  //����µ�Projection
		//�µ�Projection
		P=new Projection();
		P.trajlist=new ArrayList<Trajectory>();
		P.prefix=new ArrayList<Integer>();
		
		//�ҵ�����Region�����й켣
		for(int i=0;i<trajlist.size();i++)
		{
			traj=trajlist.get(i);
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
		P.SetPrefix(getPrefix());
		P.PrefixAdd(regionnum);
		return P;
	}
	
	
	
	
	//���projection���е�ʱ����
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
	
	
	//��gaplist����ܶ�����transfer,�����޹صĹ켣���ˣ�����supp
	public void prune(ArrayList<Gap> gaplist)
	{
		ArrayList<Integer> B=new ArrayList<Integer>();
		ArrayList<Gap> max=new ArrayList<Gap>();
		int gaptime=0;
		
		////�ռ�����ʱ����
		for(int i=0;i<gaplist.size();i++)
		{
			B.add(gaplist.get(i).getGapl());
			B.add(gaplist.get(i).getGaph());
		}
		Collections.sort(B);  //����
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
			
			//����µ�����ܶ�
			if(temp.size()>max.size())
			{
				max=temp;
				gaptime=(B.get(i)+B.get(i+1))/2;
			}
			temp=null;
		}
		//���Ǩ��ʱ��
		transfer.Add(gaptime);
		
		//����֧����
		if(supp>max.size())
		{
			supp=max.size();
		}
		Trajectory traj=null;
		
		//�Ƴ�����صĹ켣
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
		   ArrayList<Transaction> A=new ArrayList<Transaction>();  //�������ǰ�����е�transaction
		   //�������ǰ׺���е�transaction
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
		   //�ݹ����
		   int supp=Integer.MAX_VALUE;
		   Recursive_density(A,topklist,prefix.size()-2,H,supp);
	}
	 
	 
	 
	//ͨ����ά�ķ�������annotation
	private void Recursive_density(ArrayList<Transaction> A,Toplist toplist,int d,Transfergap H,int supp)
	{
		//���ڴ�����е�ʱ��ָ���
		ArrayList<Integer> B=new ArrayList<Integer>();
		
		//��������dά�ϵ����зֽ�
		for(int i=0;i<A.size();i++)
		{
			B.add(A.get(i).getLow(d));
			B.add(A.get(i).getHight(d));
		}
		
		//�����зֽ⣬���ռ���л���
		Collections.sort(B);
		
		//�ж�����������dά�ϵ��ܼ��̶�
		for(int i=0;i<B.size()-1;i++)
		{
			ArrayList<Transaction> temp=new ArrayList<Transaction>();   //����µ�Transaction
			
			//��ÿһ�οռ�����ܶ�ͳ��
			for(int j=0;j<A.size();j++)
			{
				if(A.get(j).intersect(d,B.get(i),B.get(i+1)))
				{
					temp.add(A.get(j));
				}
			}
			//System.out.println(B.get(i)+" "+B.get(i+1)+" density="+temp.size()+" d="+d+" threshold="+threshold);
			
			//�ܶȴ�����ֵ
			if(temp.size()>=toplist.getMinSupp())
			{
				//System.out.println(B.get(i)+"   "+B.get(i+1)+" tempsize="+temp.size());
				if(temp.size()<supp)
				{
					supp=temp.size();
				}
				//���annotation�ķ���ֵ
				
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
					//ͨ���ݹ�ʵ�ֽ�ά����
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
