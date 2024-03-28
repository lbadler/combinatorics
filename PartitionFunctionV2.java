import java.math.BigInteger;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
public class PartitionFunctionV2 {
	public static BigInteger[][] pnmvals;
	
	public static BigInteger p(int n) {
		return p(n, n);
	}
	
	//Number of partitions of n with largest partition at most max
	public static BigInteger p(int n, int max) {
		if (n == 0) //there is always 1 way to partition 0, regardless of max
			return BigInteger.ONE;
		if (max == 0) //if n != 0, we cannot divide it since all partitions are necessarily >0
			return BigInteger.ZERO;
		max = Math.min(n, max); //no partition can be larger than n, so if max is larger, set it to be equal to n
		if (pnmvals[n][max] != null) //check lookup table
			return pnmvals[n][max];
		BigInteger sum = p(n, max-1).add(p(n-max, max)); //either we add one max sized partition, or we move on to smaller partitions
		pnmvals[n][max] = sum; //update lookup table
		return sum;
	}
	
	public static void main(String[]args) {
		LocalTime startTime = LocalTime.now();
		int max = 10000;
		pnmvals = new BigInteger[max+1][max+1]; //initialize lookup table
		for (int i = 0; i <= max; i++) {
			System.out.println(i + ": " + p(i));
		}
		LocalTime endTime = LocalTime.now();
		long runTime = startTime.until(endTime, ChronoUnit.SECONDS);
		System.out.println("Total runtime: " + runTime + " seconds");
	}
}