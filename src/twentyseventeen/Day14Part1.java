package twentyseventeen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.util.StringUtils;

public class Day14Part1 {
	
	public static void main(String[] args) {
		//String testInput = "flqrgnkx";
		String puzzleInput = "amgozmfv";
		//Create list of 128 hash key inputs
		String[] hashInputs = getHashInputs(puzzleInput);
		//Count the amount of used squares in the grid
		int count = 0;
		for (int i = 0; i < hashInputs.length; i++) {
			count += countUsedSquares(hashInputs[i]);
		}
		System.out.println("Used squares: " + count);
	}

	private static int countUsedSquares(String hashInput) {
		//Call Day10Part2 solution to compute knot hash for given hash key
		String knotHash = Day10Part2.doKnotHash(hashInput, false);
		//Convert the hexadecimal to binary
		String binary = hexToBin(knotHash);
		//Count the amount of 1's in the given string
		return countMatches(binary, "1");
	}

	//Count the occurrences of a string in another string
	private static int countMatches(String binary, String countMe) {
		Pattern pattern = Pattern.compile(countMe);
		Matcher matcher = pattern.matcher(binary);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	//Helper function to create our 128 hash inputs
	private static String[] getHashInputs(String input) {
		String[] hashInputs = new String[128];
		for (int i = 0; i < hashInputs.length; i++) {
			hashInputs[i] = input + "-" + i;
		}
		return hashInputs;
	}

	//Convert a hexadecimal string into a binary string
	private static String hexToBin(String s) {
	  return new BigInteger(s, 16).toString(2);
	}
}
