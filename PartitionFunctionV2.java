import java.math.BigInteger;
public class PartitionFunctionV2 {
	public static BigInteger[][] pnmvals;
	
	public static BigInteger p(int n) {
		return p(n, n);
	}
	
	public static BigInteger p(int n, int max) {
		if (n == 0)
			return BigInteger.ONE;
		if (max == 0)
			return BigInteger.ZERO;
		if (pnmvals[n][max] != null)
			return pnmvals[n][max];
		BigInteger sum = p(n, max - 1);
		for (int j = 1; j*max <= n; j++)
			sum = sum.add(p(n-j*max, max-1));
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