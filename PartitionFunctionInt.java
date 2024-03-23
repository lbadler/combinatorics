public class PartitionFunctionInt {
	public static int[][] pnmvals;
	
	public static int p(int n) {
		return p(n, n);
	}
	
	public static int p(int n, int max) {
		if (n == 0)
			return 1;
		if (pnmvals[n][max] != 0)
			return pnmvals[n][max];
		int sum = 0;
		for (int i = max; i > 0; i--) //size of largest partition
			for (int j = 1; j*i <= n; j++) //how many of that partition
				sum += p(n-j*i, i-1);
		pnmvals[n][max] = sum;
		return sum;
	}
	
	public static void main(String[]args) {
		pnmvals = new int[126][126];
		for (int i = 0; i <= 125; i++) {
			System.out.println(i + ": " + p(i));
		}
	}
}