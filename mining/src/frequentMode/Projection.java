

//Merge pattern 
//different pattern that share the same sequence but have similar annotation can be merged by a special function

package Mining;
import java.util.*;

public class Projection {
	public ArrayList<Trajectory> traj=null;  //������й켣
	public ArrayList<Integer> prefix=new ArrayList<Integer>(); //ģʽ��ǰ׺
	private ArrayList<Transfergap> D=null;  //������յ�annotation
	Projection(ArrayList<Trajectory> traj)
	{
		this.traj=traj;
	}
	public Projection() {}
	
	//���ǰ׺�ĳ���
	public int prefixSize()
	{
		return prefix.size();
	}
	
	//��ù켣������
	public int TrajectorySize()
	{
		return traj.size();
	}
	
	//���ָ��λ�õĹ켣
	public Trajectory getTrajectory(int index)
	{
		return traj.get(index);
	}
	
	//������й켣
	public ArrayList<Trajectory> getTrajectory()
	{
		return traj;
	}
	
	//���ǰ׺
	public ArrayList<Integer> getPrefix()
	{
		ArrayList<Integer> clone = (ArrayList<Integer>) prefix.clone();
		return clone;
	}
	
	public void SetPrefix(ArrayList<Integer> pre)
	{
		prefix=pre;
	}
	
	//���ǰ����ָ��Ԫ��
	public Integer getPrefixItem(int index)
	{
		return prefix.get(index);
	}
	
	//��ǰ�����н������
	public void PrefixAdd(int regionnum)
	{
		prefix.add(regionnum);
	}
	
	//����Projection����������Pattern
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
	
	//���annotation
	public ArrayList<Transfergap> getTransfer(int threshold,int timethreshold)
	{
		if(D==null)
		{
			Compute_density_blocks(threshold,timethreshold);
		}
		return D;
	}
	
	//���¼���annotation��������
	public ArrayList<Transfergap> setAnnotation(int threshold,int timethreshold)
	{
		Compute_density_blocks(threshold,timethreshold);
		return D;
	}
	
	//�����ܶ�
	public  ArrayList<Transfergap> Compute_density_blocks(int threshold,int timethreshold)
	{	
		//showPrefix();
		ArrayList<Transaction> A=new ArrayList<Transaction>();  //�������ǰ�����е�transaction
		D=new ArrayList<Transfergap>();
		
		//�������ǰ׺���е�transaction
		for(int i=0;i<traj.size();i++)
		{
			//traj.get(i).getTransactionTime(timethreshold).show();
			A.add(traj.get(i).getTransactionTime(timethreshold));
		}
		
		Transfergap H=new Transfergap();
		//�ݹ����
		Recursive_density(A,prefix.size()-2,H,threshold);
		return D;
	}
	
	//ͨ����ά�ķ�������annotation
	private void Recursive_density(ArrayList<Transaction> A,int d,Transfergap H,int threshold)
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
			if(temp.size()>=threshold)
			{
				//���annotation�ķ���ֵ
				
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
					//ͨ���ݹ�ʵ�ֽ�ά����
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
