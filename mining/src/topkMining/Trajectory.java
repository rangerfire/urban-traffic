package topkMining;
import java.util.*;
import java.sql.*;
public class Trajectory {
	public String ID;   //�켣ID
	public ArrayList<Integer> Sequence=new ArrayList<Integer>();   //�켣������
	public ArrayList<Timestamp> annotation=new ArrayList<Timestamp>();  //�켣��ʱ��
	public ArrayList<Timestamp> prefixtimestamp=new ArrayList<Timestamp>();  //�켣����ģʽ��ǰ׺ʱ��
	
	
	
	////////������Ļ�������
	public int size()
	{
		return Sequence.size();
	}
	
	//�Թ켣�������
	public void add(int region,Timestamp time)
	{
		Sequence.add(region);
		annotation.add(time);
	}
	
	//�Ƴ��켣��ָ�����
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
	
	
	
	
	
	
	
	/////////////��ID�Ļ�������
	//���ù켣ID
	public void SetID(String ID)
	{
		this.ID=ID;
	}
	
	public String getID()
	{
		return ID;
	}
	//////////////////////////////
	
	
	
	
	
	
	///////////////�����еĻ�������
	//��������
	public void SetSequence(ArrayList<Integer> seq)
	{
		Sequence=seq;
	}
	
	//��ù켣���еĳ���
	public int SequenceSize()
	{
		return Sequence.size();
	}
	
	//���ָ��������
	public int getRegion(int index)
	{
		return Sequence.get(index);
	}
	
	//���������
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
	
	//�жϹ켣�������Ƿ����ĳ��Ԫ�أ��� ���ڷ����±������ڷ���-1
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
	
	
	
	
	///////////������ʱ��Ļ�������
	//��������ʱ��
	public void SetAnnotation(ArrayList<Timestamp> anno)
	{
		annotation=anno;
	}
	
	//��ù켣����ʱ��ĳ���
	public int AnnotationSize()
	{
		return annotation.size();
	}
	
	
	//���ָ����annotationԪ��
	public Timestamp getAnnotationItem(int index)
	{
		return annotation.get(index);
	}
	
	//���������ʱ��
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
	
	
	
	
	
	
	///////////////////////��ǰ׺ʱ��Ļ�������
	public void SetPrefixTime(ArrayList<Timestamp> pre)
	{
		prefixtimestamp=null;
		prefixtimestamp=pre;
	}
	
	//��ù켣ǰ׺ʱ��ĳ���
	public int prefixSize()
	{
		return prefixtimestamp.size();
	}
	
	//���ָ����ǰ׺ʱ��
	public Timestamp getPrefixTimeItem(int index)
	{
		return prefixtimestamp.get(index);
	}
	
	//���ǰ׺�����ʱ��
	public Timestamp getLastPrefix()
	{
		return prefixtimestamp.get(prefixtimestamp.size()-1);
	}
	
	//���ǰ׺�����ʱ����
	public int getLastGap()
	{
		int size=prefixtimestamp.size();
		int gap=(int)(prefixtimestamp.get(size-1).getTime()-prefixtimestamp.get(size-2).getTime())/1000;
		return gap;
	}
	
	//��ù켣��ǰ���traveltime
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
	
	//���ǰ׺ʱ��
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
