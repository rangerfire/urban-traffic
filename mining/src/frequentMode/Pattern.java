package Mining;
import java.util.*;
import java.sql.*;
import java.io.*;
public class Pattern {
	private ArrayList<Integer> Sequence=new ArrayList<Integer>();   
	private ArrayList<Integer> annotation=new ArrayList<Integer>();
	
	//获得模式的长度
	public int Size()
	{
		return annotation.size();
	}
	
	//增加模式的区域
	public void addRegion(int regionnum)
	{
		Sequence.add(regionnum);
	}
	
	//增加模式的annotation
	public void addAnnotation(int time)
	{
		annotation.add(time);
	}
	
	//增加模式的区域和annotation
	public void add(int regionnum,Integer time)
	{
		Sequence.add(regionnum);
		annotation.add(time);
	}
	
	//设置序列
	public void SetSequence(ArrayList<Integer> sequence)
	{
		Sequence=sequence;
	}
	
	//设置时间
	public void SetAnnotation(ArrayList<Integer> anno)
	{
		annotation=anno;
	}
	
	//显示模式
	public void show()
	{
		System.out.print(Sequence.get(0));
		for(int i=0;i<annotation.size();i++)
		{
			System.out.print("-"+annotation.get(i)+"->"+Sequence.get(i+1));
		}
		System.out.println();
	}
	
	public void PrintToFile(BufferedWriter bw) throws IOException
	{
		int n=annotation.size();
		bw.write(n+",");
		bw.write("{");
		int i=0;
		for(i=0;i<n;i++)
		{
			bw.write(Sequence.get(i)+",");
		}
		bw.write(Sequence.get(i)+"},{");
		for(i=0;i<n-1;i++)
		{
			bw.write(annotation.get(i)+",");
		}
		bw.write(annotation.get(i)+"}");
		bw.newLine();
	}
}
