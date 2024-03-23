public class PartitionFunctionLong {
	public static long[][] pnmvals;
	
	public static long p(int n) {
		return p(n, n);
	}
	
	public static long p(int n, int max) {
		if (n == 0)
			return 1;
		if (pnmvals[n][max] != 0)
			return pnmvals[n][max];
		long sum = 0;
		for (int i = max; i > 0; i--) //size of largest partition
			for (int j = 1; j*i <= n; j++) //how many of that partition
				sum += p(n-j*i, i-1);
		pnmvals[n][max] = sum;
		return sum;
	}
	
	public static void main(String[]args) {
		pnmvals = new long[411][411];
		for (int i = 0; i <= 410; i++) {
			System.out.println(i + ": " + p(i));
		}
	}
}