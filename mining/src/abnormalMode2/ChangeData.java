/*
 *x������Ҫ���ڽ�ԭʼ���ݵĸ�ʽ����ת�䣬��Ҫ����ʱ���Ͻ���ת�䣬ԭ����ʱ���ʽ����20121103123050��ת���2012-11-03 12:30:50���ָ�ʽ������������Ķ�ȡ����
 *!!��һ�������ļ��ĳɰ���Ϊ��λ��30���ļ���
 */
package Exception_Models1;
import java.io.*;
public class ChangeData {
	public static BufferedWriter[] brlist=new BufferedWriter[30];
	public static void main(String[] args) throws Exception 
	{
		SetList();
		File f=new File("G://1_Study//Exception//taxi_group");//ԭʼ�����ļ�
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
	public static void SetList() throws Exception//�½�30����ļ�
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
