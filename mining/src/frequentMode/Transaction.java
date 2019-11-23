package Mining;

import java.util.ArrayList;

public class Transaction {
	private ArrayList<Integer> tran_l=new ArrayList<Integer>(); //时间下界序列
	private ArrayList<Integer> tran_h=new ArrayList<Integer>(); //时间上界序列
	
	//获得时间序列的长度
	public int Size()
	{
		return tran_l.size();
	}
	
	//判断时间序列是否与一段时间相交
	public boolean intersect(int d,int l,int h)
	{
		if(tran_l.get(d)<h&&tran_h.get(d)>l)
		{
			return true;
		}
		return false;
	}
	
	//添加
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
