import java.util.*;

/* Various combinatorics algorithms for MA2273.
 * Bernhardt Adler, Ian Wood
 *
 * general usage notes:
 * some of these algorithms will break for large n due to factorial values causing integer overflow (12! is fine, 13! is not)
 * subsets are assumed to be in size order, smallest to largest. if providing unordered subsets, use Arrays.Sort before passing to my methods
 */


public class CombinatoricsStuff {
	//Permutation algorithms
	
	//Rank algorithm for johnson-trotter
	public static int calcJTRank(int[] perm) {
		int size = perm.length;
		if (size == 1)
			return 0;
		int pos = -1;
		for (int i = 0; i < size; i++) {
			if (perm[i] == size) {
				pos=i;
				break;
			}
		}
		int[] smallerPerm = new int[size-1];
		int j = 0;
		for (int i = 0; i < size; i++) {
			if (i != pos)
				smallerPerm[j++] = perm[i];
		}
		int smallerRank = calcJTRank(smallerPerm);
		if (smallerRank % 2 == 0) {
			return size * smallerRank + (size-pos-1);
		} else {
			return size * smallerRank + pos;
		}
	}
	
	//Unrank for johnson-trotter
	public static int[] generateJT (int size, int rank) {
		if (size == 1) {
			return new int[]{1};
		}
		int[] toReturn = new int[size];
		int prevRank = rank / size;
		int newPos = rank % size;
		int[] prevPerm = generateJT (size - 1, prevRank);
		int j = 0;
		for (int i = 0; i < size; i++) {
			if ((prevRank % 2 == 0) ? (i == (size - newPos - 1)) : (i == newPos)) {
				toReturn[i] = size;
			} else {
				toReturn[i] = prevPerm[j++];
			}
		}
		return toReturn;
	}
	
	//Rank algorithm for lexicological order
	public static int calcLexRank(int[] perm) {
		int rank = 0;
		int size = perm.length;
		for (int i = 1; i < size; i++)
			for (int j = i; j < size; j++)
				if (perm[j] < perm[i-1])
					rank += factorial(size - i);
		return rank;
	}
	
	//Unrank algorithm for lex order
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
	
	//Successor function for lex order. if given the final permutation, returns the first
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
	
	//Takes an inversion sequence, and returns the associated permutation
	public static int[] inversionToPermutation (int[] inversion) {
		inversion = inversion.clone();
		int size = inversion.length;
		int[] perm = new int[size];
		for (int i = size; i > 0; i--) {
			int index = -1;
			for (int j = 0; j < size; j++)
				if (inversion[j] == 0)
					index = j;
			perm[index] = i;
			for (int j = index; j < size; j++)
				inversion[j]--;
		}
		return perm;
	}
	
	//Calculates the inversion sequence for a permutation
	public static int[] permutationToInversion (int[] perm) {
		int size = perm.length;
		int[] inversion = new int[size];
		for (int i = 0; i < size; i++) {
			inversion[i] = 0;
			for (int j = 0; j < i; j++)
				if (perm[j] > perm[i])
					inversion[i]++;
		}
		return inversion;
	}
	
	
	//Subset algorithms
	
	//Calculates the rank of a subset in Colex order
	public static int calcColexRank (int[] subset) {
		int rank = 0;
		int size = subset.length;
		for (int i = 0; i < size; i++)
			rank += choose(subset[i]-1, i+1);
		return rank;
	}
	
	//Unrank subset in colex order
	public static int[] generateColex (int size, int rank) {
		int[] subset = new int[size];
		for (int i = size; i > 0; i--)
			for (int j = i; true; j++)
				if (choose(j,i) > rank) {
					subset[i-1] = j;
					rank -= choose(j-1, i);
					break;
				}
		return subset;
	}
	
	//Successor function for subsets in colex order. Never wraps, just increases the size of the set from which the subset is from
	public static int[] generateNextColex (int[] subset) {
		int size = subset.length;
		int[] next = new int[size];
		int index = 0;
		while (index < size-1 && subset[index]+1 == subset[index+1])
			index++;
		for (int i = 0; i < index; i++)
			next[i] = i+1;
		next[index] = subset[index] + 1;
		for (int i = index+1; i < size; i++)
			next[i] = subset[i];
		return next;
	}
	
	
	//Integer partitions
	
	//Generate the conjugate of a given partition
	public static int[] generateConjugatePartition (int[] partition) {
		int[] conjugate = new int[partition[0]];
		for (int i = 0; i < conjugate.length; i++)
			for (int j = 0; j < partition.length && partition[j] > i; j++)
				conjugate[i]++;
		return conjugate;
	}
	
	//A clever alternative algorithm to find conjugate partitions
	public static int[] altConjugatePartition (int[] partition) {
		int[] conjugate = new int[partition[0]];
		for (int i = 0; i < partition.length; i++)
			Arrays.fill(conjugate, 0, partition[i], i+1);
		return conjugate;
	}
	
	
	//Insert my random code to test/output things here
	public static void main (String[]args) {
		int[] demoPartition = new int[]{3, 1};
		int[] demoConjugate = generateConjugatePartition(demoPartition);
		int[] demoAltConjugate = altConjugatePartition(demoPartition);
		System.out.println(Arrays.toString(demoPartition));
		System.out.println(Arrays.toString(demoConjugate));
		System.out.println(Arrays.toString(demoAltConjugate));
	}
	
	
	//Various numerical calculations. used in several places throughout other methods
	
	public static int factorial (int x) {
		int prod = 1;
		for (int i = 2; i <= x; i++)
			prod *= i;
		return prod;
	}
	
	//N choose K
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