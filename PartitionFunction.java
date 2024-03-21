import java.math.BigInteger;
public class PartitionFunction {
	public static BigInteger[][] pnmvals;
	
	public static BigInteger p(int n) {
		return p(n, n);
	}
	
	public static BigInteger p(int n, int max) {
		if (n == 0)
			return BigInteger.ONE;
		if (pnmvals[n][max] != null)
			return pnmvals[n][max];
		BigInteger sum = BigInteger.ZERO;
		for (int i = max; i > 0; i--) //size of largest partition
			for (int j = 1; j*i <= n; j++) //how many of that partition
				sum = sum.add(p(n-j*i, i-1));
		pnmvals[n][max] = sum;
		return sum;
	}
	
	public static void main(String[]args) {
		pnmvals = new BigInteger[10001][10001];
		for (int i = 0; i <= 10000; i++) {
			System.out.println(i + ": " + p(i));
		}
	}
}