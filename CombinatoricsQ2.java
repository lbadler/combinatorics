import java.util.*;

/* Various combinatorics algorithms for MA2273.
 * Bernhardt Adler, Ian Wood
 *
 * general usage notes:
 * some of these algorithms will break for large n due to factorial values causing integer overflow (12! is fine, 13! is not)
 * subsets are assumed to be in size order, smallest to largest. if providing unordered subsets, use Arrays.Sort before passing to my methods
 */


public class CombinatoricsQ2 {
	//rank algorithm for lexicological order
	public static int calcLexRank(int[] perm) {
		int rank = 0;
		int size = perm.length;
		for (int i = 1; i < size; i++)
			for (int j = i; j < size; j++)
				if (perm[j] < perm[i-1])
					rank += factorial(size - i);
		return rank;
	}
	
	//unrank algorithm for lex order
	public static int[] generateLex (int size, int rank) {
		int[] perm = new int[size];
		ArrayList<Integer> unused = new ArrayList<Integer>(size);
		for (int i = 1; i <= size; i++)
			unused.add(i);
		for (int i = 1; i <= size; i++) {
			int factorial = factorial(size-i);
			perm[i-1] = unused.remove(rank / factorial);
			rank = rank % factorial;
		}
		return perm;
	}
	
	//Alternative unrank algorithm for lex order
	public static int[] generateAltLex (int size, int rank) {
		int[] perm = new int[size];
		for (int i = 1; i <= size; i++) {
			int factorial = factorial(size-i);
			int num = rank / factorial + 1;
			for (int j = 1; j <= num; j++)
				for (int k = 0; k < i-1; k++)
					if (j == perm[k])
						num++;
			perm[i-1] = num;
			rank = rank % factorial;
		}
		return perm;
	}
	
	//successor function for lex order. if given the final permutation, returns the first
	public static int[] generateNextLex (int[] prev) {
		int size = prev.length;
		int i = size - 2;
		while (i >= 0 && prev[i] > prev[i+1])
			i--;
		if (i == -1) //prev is last, in reverse order
			return generateLex(size, 0);
		int[] next = new int[size];
		for (int j = 0; j < size; j++)
			next[j] = prev[j];
		int currentInPos = prev[i];
		outer: for (int j = currentInPos + 1; j <= size; j++)
			for (int k = i+1; k < size; k++)
				if (j == prev[k]) {
					next[k] = currentInPos;
					next[i] = j;
					break outer;
				}
		for (int j = i+1; j < size; j++)
			for (int k = j+1; k < size; k++)
				if (next[j] > next[k]) {
					int temp = next[j];
					next[j] = next[k];
					next[k] = temp;
				}
		return next;
	}
	
	//Some demonstration of the algorithms in this question
	public static void main (String[]args) {
		//feel free to play with these values
		int demoSize = 10;
		int demoRank = 3000000;
		int[] demoPerm = new int[]{9, 4, 2, 3, 5, 6, 8, 7, 10, 1};
		
		int[] algo1perm = generateLex(demoSize, demoRank);
		int[] algo2perm = generateAltLex(demoSize, demoRank);
		int[] demoSuccessor = generateNextLex(demoPerm);
		int demoPermRank = calcLexRank(demoPerm);
		int demoSuccessorRank = calcLexRank(demoSuccessor);
		
		System.out.printf("\nThe permutation of size %d and rank %d is %s\n\n", demoSize, demoRank, Arrays.toString(algo1perm));
		System.out.printf("Calculated with the alt algorithm, we get %s\n\n", Arrays.toString(algo1perm));
		System.out.printf("The successor to %s is %s\n\n", Arrays.toString(demoPerm), Arrays.toString(demoSuccessor));
		System.out.printf("Your permutation has rank %d, and its successor has rank %d\n\n", demoPermRank, demoSuccessorRank);
	}
	
	
	
	//Various numerical calculations. used in several places throughout other methods
	
	public static int factorial (int x) {
		int prod = 1;
		for (int i = 2; i <= x; i++)
			prod *= i;
		return prod;
	}
	
	public static int choose (int n, int k) {
		if (k > n)
			return 0;
		int answer = 1;
		k = Math.min(k, n-k);
		for (int i = 0; i < k; i++)
			answer *= (n-i);
		answer /= factorial(k);
		return answer;
	}
}