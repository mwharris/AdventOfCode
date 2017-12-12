package twentyseventeen;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Day6Part2 {
	
	public static void main(String[] args) {
		Map<String, Integer> bankMap = new HashMap<String, Integer>();
		List<Integer> banks = readFromFile("resources/day6input.txt");
		
		int numCycles = getNumCycles(banks, bankMap);
		
		System.out.println("The number of cycles between infinite loops are: " + numCycles);
	}
	
	private static int getNumCycles(List<Integer> banks, Map<String, Integer> bankMap) {
		int numRedis = 0;
		//Add the initial bank set to the set
		bankMap.put(banks.toString(), 0);
		//Loop until we get a duplicate bank set
		while (true) {
			//Find the bank with the maximum blocks
			int maxIndex = findMax(banks);
			//Redistribute the blocks in that bank to the other banks
			banks = redistribute(banks, maxIndex);
			numRedis++;
			//Check if we've seen this bank set before
			Integer loopNum = bankMap.get(banks.toString());
			if (loopNum != null) {
				return numRedis - loopNum;
			}
			//If not, store for later checking
			else {
				bankMap.put(banks.toString(), numRedis);
			}
		}
	}

	private static List<Integer> redistribute(List<Integer> banks, int maxIndex) {
		//Store the amount of blocks we want to redistribute
		int blocksToRedis = banks.get(maxIndex);
		//Update the redistributed bank to 0
		banks.set(maxIndex, 0);
		//Redistribute the blocks among all banks
		int i = maxIndex + 1;
		while (blocksToRedis > 0) {
			//Loop to the front of the array if we went past size
			if (i == banks.size()) {
				i = 0;
			}
			//Redistribute one-by-one
			banks.set(i, banks.get(i) + 1);
			blocksToRedis--;
			i++;
		}
		return banks;
	}

	private static int findMax(List<Integer> banks) {
		int max = 0;
		int maxIndex = 0;
		for (int i = 0; i < banks.size(); i++) {
			if (max < banks.get(i).intValue()) {
				max = banks.get(i).intValue();
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private static List<Integer> readFromFile(String filename) {
		List<Integer> ret = new ArrayList<Integer>();
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextInt()) {
				ret.add(scan.nextInt());
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
