/*
 *x该类主要用于将原始数据的格式进行转变，主要是在时间上进行转变，原来的时间格式如下20121103123050，转变成2012-11-03 12:30:50这种格式，方便后面程序的读取处理
 *!!将一个数据文件改成按天为单位的30个文件。
 */
package Exception_Models1;
import java.io.*;
public class ChangeData {
	public static BufferedWriter[] brlist=new BufferedWriter[30];
	public static void main(String[] args) throws Exception 
	{
		SetList();
		File f=new File("G://1_Study//Exception//taxi_group");//原始数据文件
		File[] fs=f.listFiles();
		for(File e:fs)
		{
			Read(e);
		}
		for(BufferedWriter bw:brlist)
		{
			bw.flush();
			bw.close();
		}
	}
	public static void SetList() throws Exception//新建30天的文件
	{
		//String s="H://taxi/201211";
		String s="G://1_Study//taxi/201211";
		int i=1;
		for(i=1;i<=30;i++)
		{
			brlist[i-1]=new BufferedWriter(new FileWriter(s+i+".txt"));
		}
	}
	public static void Read(File e) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(e));
		BufferedWriter bw=null;
		String line=null;
		while((line=br.readLine())!=null)
		{
			if(line.length()>0)
			{
				int day=Integer.parseInt(line.substring(17, 19));
				bw=brlist[day-1];
				bw.write(line);
				bw.newLine();
			}
		}
		br.close();
		br=null;
	}
}
