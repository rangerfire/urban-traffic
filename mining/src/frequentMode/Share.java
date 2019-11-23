package Mining;


public class Share {
	private int a;
	Share()
	{
		a=0;
	}
	Share(int i)
	{
		a=i;
	}
	public int add()
	{
		synchronized(this)
		{
			a=a+1;
			return a-1;
		}
	}
}
