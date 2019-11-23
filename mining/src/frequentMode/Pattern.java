package Mining;
import java.util.*;
import java.sql.*;
import java.io.*;
public class Pattern {
	private ArrayList<Integer> Sequence=new ArrayList<Integer>();   
	private ArrayList<Integer> annotation=new ArrayList<Integer>();
	
	//���ģʽ�ĳ���
	public int Size()
	{
		return annotation.size();
	}
	
	//����ģʽ������
	public void addRegion(int regionnum)
	{
		Sequence.add(regionnum);
	}
	
	//����ģʽ��annotation
	public void addAnnotation(int time)
	{
		annotation.add(time);
	}
	
	//����ģʽ�������annotation
	public void add(int regionnum,Integer time)
	{
		Sequence.add(regionnum);
		annotation.add(time);
	}
	
	//��������
	public void SetSequence(ArrayList<Integer> sequence)
	{
		Sequence=sequence;
	}
	
	//����ʱ��
	public void SetAnnotation(ArrayList<Integer> anno)
	{
		annotation=anno;
	}
	
	//��ʾģʽ
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
