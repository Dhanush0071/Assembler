public class binary_conv {


	public static int[] binary(int a)
	{
		int[] out = new int[16];
		for (int i=0;i<16;i++) 
		{
			out[i]=0;
		}
		int j =15;
		while ( a>0) {
			out[j]= a %2;
			a= a/2 - out[j]/2;
			j=j-1;
		}
		return out;
	}
	public static void main(String[]args)
	{
		int p=4;
		int[] ans = binary_conv.binary(p);
		for (int i=0;i<16;i++) 
		{
			System.out.print(ans[i]);
		}
	}

}
