package Mining;

import java.util.ArrayList;

public class Transaction {
	private ArrayList<Integer> tran_l=new ArrayList<Integer>(); //ʱ���½�����
	private ArrayList<Integer> tran_h=new ArrayList<Integer>(); //ʱ���Ͻ�����
	
	//���ʱ�����еĳ���
	public int Size()
	{
		return tran_l.size();
	}
	
	//�ж�ʱ�������Ƿ���һ��ʱ���ཻ
	public boolean intersect(int d,int l,int h)
	{
		if(tran_l.get(d)<h&&tran_h.get(d)>l)
		{
			return true;
		}
		return false;
	}
	
	//���
	public void insert(int l,int h)
	{
		tran_l.add(l);
		tran_h.add(h);
	}
	public int getLow(int index)
	{
		return tran_l.get(index);
	}
	public int getHight(int index)
	{
		return tran_h.get(index);
	}
	public void show()
	{
		for(int i=0;i<tran_l.size();i++)
		{
			System.out.print("["+tran_l.get(i)+","+tran_h.get(i)+"] ");
		}
		System.out.println();
	}

}
