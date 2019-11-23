
public class Calulate {
	public static void main(String[] args)
	{
		double[] p={0.3211554800339847,
				0.13423959218351741,
				0.19541206457094307,
				0.14613423959218352,
				0.16567544604927784,
				0.03228547153780799,
				0.005097706032285472,
				0.0};
		double[] q={0.3137254901960784,
				0.19607843137254902,
				0.3137254901960784,
				0.058823529411764705,
				0.11764705882352941,
				0.0,
				0.0,
				0.0};
		double bc=0;
		for(int i=0;i<p.length;i++)
		{
			bc+=Math.sqrt(p[i]*q[i]);
		}
		double db=-Math.log(bc)*100;
		System.out.println(db);
	}

}
