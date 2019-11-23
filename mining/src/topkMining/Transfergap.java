package topkMining;
import java.util.*;

public class Transfergap {
    private ArrayList<Integer> transfer=new ArrayList<Integer>();
    public int size()
    {
    	return transfer.size();
    }
    public void setTransfer(ArrayList<Integer> trans)
    {
    	transfer=trans;
    }
	public ArrayList<Integer> getTransfer()
	{
		return (ArrayList<Integer>) transfer.clone();
	}
	//添加
	public void Add(Integer a)
	{
		transfer.add(a);
	}
	
	public void Add(int index,int a)
	{
		transfer.add(index, a);
	}
	
	//获得指定的元素
	public int getTranfer(int index)
	{
		return transfer.get(index);
	}
	
	//移除第一个元素
	public void removeFirst()
	{
		transfer.remove(0);
	}
	
	//移除最后一个元素
	public void removeLast()
	{
		transfer.remove(transfer.size()-1);
	}
	
	public ArrayList<Integer> getTransfergap()
	{
		return transfer;
	}
	
	public void Copy(Transfergap trans)
	{
		for(int i=0;i<transfer.size();i++)
		{
			int a=transfer.get(i);
			trans.Add(a);
		}
	}
	public void show()
	{
		for(int i=0;i<transfer.size();i++)
		{
			System.out.print(transfer.get(i)+"  ");
		}
		System.out.println();
	}

}
