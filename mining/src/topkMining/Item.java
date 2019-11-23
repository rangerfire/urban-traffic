package topkMining;

public class Item
implements Comparable
{
	public int itme;
	public short density;
	public Item(int item,short density)
	{
		this.itme=item;
		this.density=density;
	}
	@Override
	public int compareTo(Object o) {
		Item a=(Item)o;
		if(density<a.density)
		{
			return 1;
		}
		return 0;
	}

}
