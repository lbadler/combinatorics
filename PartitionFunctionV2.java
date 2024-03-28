import java.math.BigInteger;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
		BigInteger sum = BigInteger.ZERO;
		if (max > n)
			sum = p(n, max-1);
		else
			sum = p(n, max-1).add(p(n-max, max));
		pnmvals[n][max] = sum;
		return sum;
	}
	
	public static void main(String[]args) {
		LocalTime startTime = LocalTime.now();
		int max = 10000;
		pnmvals = new BigInteger[max+1][max+1];
		for (int i = 0; i <= max; i++) {
			System.out.println(i + ": " + p(i));
		}
		LocalTime endTime = LocalTime.now();
		long runTime = startTime.until(endTime, ChronoUnit.SECONDS);
		System.out.println("Total runtime: " + runTime + " seconds");
	}
}