package Mining;
import java.util.*;
import java.sql.*;

//ʱ������
public class Annotation {
	
	//ʱ����
	private ArrayList<Timestamp> annotation=new ArrayList<Timestamp>();
	
	//���ʱ����
	public ArrayList<Timestamp> getAnnotation()
	{
		return annotation;
	}
	
	//���ָ���±��ʱ����
	public Timestamp getAnnotation(int index)
	{
		return annotation.get(index);
	}
	
	//���ʱ����
	public void Add(Timestamp anno)
	{
		annotation.add(anno);
	}
	
	//��ͷ�����ʱ����
	public void Add(int index,Timestamp anno)
	{
		annotation.add(0,anno);
	}
	
	//�Ƴ�β��ʱ����
	public void removeLast()
	{
		annotation.remove(annotation.size()-1);
	}
	
	//�Ƴ�ͷ��ʱ����
	public void removeFirst()
	{
		annotation.remove(0);
	}
	
	//��ʾʱ����
	public void show()
	{
		for(int i=0;i<annotation.size();i++)
		{
			System.out.print(annotation.get(i)+" ");
		}
		System.out.println();
	}
	
	//����ʱ����
	public void Copy(Annotation anno)
	{
		for(int i=0;i<annotation.size();i++)
		{
			Timestamp a=annotation.get(i);
			anno.Add(a);
		}
	}

}
