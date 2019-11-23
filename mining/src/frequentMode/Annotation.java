package Mining;
import java.util.*;
import java.sql.*;

//时间标记类
public class Annotation {
	
	//时间标记
	private ArrayList<Timestamp> annotation=new ArrayList<Timestamp>();
	
	//获得时间标记
	public ArrayList<Timestamp> getAnnotation()
	{
		return annotation;
	}
	
	//获得指定下标的时间标记
	public Timestamp getAnnotation(int index)
	{
		return annotation.get(index);
	}
	
	//添加时间标记
	public void Add(Timestamp anno)
	{
		annotation.add(anno);
	}
	
	//在头处添加时间标记
	public void Add(int index,Timestamp anno)
	{
		annotation.add(0,anno);
	}
	
	//移除尾部时间标记
	public void removeLast()
	{
		annotation.remove(annotation.size()-1);
	}
	
	//移除头部时间标记
	public void removeFirst()
	{
		annotation.remove(0);
	}
	
	//显示时间标记
	public void show()
	{
		for(int i=0;i<annotation.size();i++)
		{
			System.out.print(annotation.get(i)+" ");
		}
		System.out.println();
	}
	
	//拷贝时间标记
	public void Copy(Annotation anno)
	{
		for(int i=0;i<annotation.size();i++)
		{
			Timestamp a=annotation.get(i);
			anno.Add(a);
		}
	}

}
