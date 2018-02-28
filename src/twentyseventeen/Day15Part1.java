package twentyseventeen;

import java.math.BigInteger;

public class Day15Part1 {

	private static final BigInteger A_START = BigInteger.valueOf(516);
	private static final BigInteger A_FACTOR = BigInteger.valueOf(16807);
	private static final BigInteger B_START = BigInteger.valueOf(190);
	private static final BigInteger B_FACTOR = BigInteger.valueOf(48271);
	private static final BigInteger DIVISOR = BigInteger.valueOf(2147483647);
	
	public static void main(String[] args) {
		int count = 0;
		BigInteger genA = A_START;
		BigInteger genB = B_START;
		
		for (int pairs = 40000000; pairs > 0; pairs--) {
			//Get each generator's next value
			genA = getGeneratorValue("A", genA);
			genB = getGeneratorValue("B", genB);
			//Test if the bottom 16 bits are matching
			if (testBitsMatch(genA, genB)) {
				count++;
			}
		}
		
		System.out.println("Judge's Count: " + count);
	}
	
	private static boolean testBitsMatch(BigInteger genA, BigInteger genB) {
		byte[] a = genA.toByteArray();
		byte[] b = genB.toByteArray();
		
		int i = 0;
		int endIndex = 0;
		int aIndex = a.length - 1;
		int bIndex = b.length - 1;
		if (aIndex > bIndex) {
			endIndex = bIndex - 2;
			i = bIndex;
		}
		else {
			endIndex = aIndex - 2;
			i = aIndex;
		}
		
		if (aIndex != bIndex) {
			System.out.println("");
		}
		
		boolean match = true;
		while (i > endIndex) {
			if (a[aIndex] != b[bIndex]) {
				match = false;
				break;
			}
			i--;
			aIndex--;
			bIndex--;
		}
		
		return match;
	}

	private static BigInteger getGeneratorValue(String generator, BigInteger prevValue) {
		if ("A".equals(generator)) {
			return prevValue.multiply(A_FACTOR).mod(DIVISOR);
		}
		else {
			return prevValue.multiply(B_FACTOR).mod(DIVISOR);
		}
	}
	
}
