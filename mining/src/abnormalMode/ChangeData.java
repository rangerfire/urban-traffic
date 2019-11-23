import java.io.*;
public class ChangeData {
	public static BufferedWriter[] brlist=new BufferedWriter[30];
	public static void main(String[] args) throws Exception 
	{
		SetList();
		File f=new File("taxi_group");
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
	public static void SetList() throws Exception
	{
		String s="H://taxi/201211";
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
