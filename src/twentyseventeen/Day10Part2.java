package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10Part2 {
	
	public static void main(String[] args) {
		String knotHash = doKnotHash("resources/day10Part2input.txt", true);
		System.out.println("Knot Hash: " + knotHash);
	}

	public static String doKnotHash(String input, boolean fromFile) {
		//Get our lengths either from a file OR input string
		List<Integer> lengths = readLengths(input, fromFile);
		//Create our list and fill with values 0 - 255
		int[] list = new int[256];
		for (int i = 0; i < list.length; i++) {
			list[i] = i;
		}
		//Generate the sparse hash by processing the list of lengths
		int[] sparseHash = generateSparseHash(list, lengths);
		//Generate the dense hash
		int[] denseHash = generateDenseHash(sparseHash);
		//Generate the knot hash string based on dense and sparse hashes
		return generateKnotHash(denseHash);
	}

	private static String generateKnotHash(int[] denseHash) {
		String knotHash = "";
		for (int i = 0; i < denseHash.length; i++) {
			String hex = Integer.toHexString(denseHash[i]);
			if (hex.length() == 1) {
				knotHash += "0"+  hex;
			}
			else {
				knotHash += hex;
			}
		}
		return knotHash;
	}

	private static int[] generateDenseHash(int[] sparseHash) {
		int[] denseHash = new int[16];
		int denseIndex = 0;
		for (int i = 0; i < sparseHash.length; i += 16) {
			//Combine values by XOR'ing in 16 element chunks
			int xor = getXOR(i, sparseHash);
			//Store in our dense hash
			denseHash[denseIndex] = xor;
			denseIndex++;
		}
		return denseHash;
	}

	private static int[] generateSparseHash(int[] list, List<Integer> lengths) {
		int currPos = 0;
		int skipSize = 0;
		
		//Run this process for 64 rounds
		for (int j = 0; j < 64; j++) {
			//Process all lengths in the list
			for (int i = 0; i < lengths.size(); i++) {
				int currLength = lengths.get(i);
				//Reverse all elements in the range: (currPos, endIndex)
				list = reverseLength(list, currPos, currLength);
				//Update the position
				currPos = findNewPosition(list, currPos, currLength, skipSize);
				//Update skip size
				skipSize++;
			}
		}
		
		return list;
	}

	private static int[] reverseLength(int[] list, int start, int length) {
		//Find the midpoint, wrapping around if necessary
		int mid = length / 2;
		//Loop for 1/2 of the elements in length
		for (int i = 0; i < mid; i++) {
			//Make sure we are wrapping around as needed
			int curr = wrapAround(start + i, list.length);
			//Find the swap index, wrap if needed
			int swapIndex = wrapAround(start+length-1-i, list.length);
			//Swap the elements
			int temp = list[curr];
			list[curr] = list[swapIndex];
			list[swapIndex] = temp;	
		}
		return list;
	}
	
	private static List<Integer> readLengths(String input, boolean fromFile) {
		List<Integer> lengths = new ArrayList<Integer>();
		
		//Fill out the standard length suffix values
		List<Integer> standardLengthSuffix = new ArrayList<Integer>();
		standardLengthSuffix.add(17);
		standardLengthSuffix.add(31);
		standardLengthSuffix.add(73);
		standardLengthSuffix.add(47);
		standardLengthSuffix.add(23);
		
		try {
			//Read in the input from file OR input string
			Scanner fileScan;
			if (fromFile) {
				fileScan = new Scanner(new File(input));
			}
			else {
				fileScan = new Scanner(input);
			}
			//There should be only 1 line in the file
			String line = fileScan.nextLine();
			fileScan.close();
			//Read each character of the input
			for (int i = 0; i < line.length(); i++) {
				//Convert to ASCII code then add to list
				int ascii = (int) line.charAt(i);
				lengths.add(ascii);
			}
			//Add the standard length suffix values
			lengths.addAll(standardLengthSuffix);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return lengths;
	}
	
	//Compute the new position, wrap around array if needed
	private static int findNewPosition(int[] list, int pos, int length, int skipSize) {
		//Jump position ahead by length + skip size
		int newPos = pos + length + skipSize;
		//Wrap around to the front of the arary if needed
		newPos = wrapAround(newPos, list.length);
		return newPos;
	}
	
	private static int wrapAround(int index, int wrapIndex) {
		if (index >= wrapIndex) {
			index = index % wrapIndex;
		}
		return index;
	}
	
	private static int getXOR(int i, int[] sparseHash) {
		return sparseHash[i] ^ sparseHash[i+1] ^ sparseHash[i+2] ^ sparseHash[i+3]
				^ sparseHash[i+4] ^ sparseHash[i+5] ^ sparseHash[i+6] ^ sparseHash[i+7]
				^ sparseHash[i+8] ^ sparseHash[i+9]  ^ sparseHash[i+10] ^ sparseHash[i+11]
				^ sparseHash[i+12] ^ sparseHash[i+13] ^ sparseHash[i+14] ^ sparseHash[i+15];
	}
	
}
