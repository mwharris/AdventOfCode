package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10Part1 {

	public static void main(String[] args) {
		//Read list of lengths from file
		List<Integer> lengths = readLengthsFromFile("resources/day10input.txt");
		
		//Create our list and fill with values 0 - 255
		int[] list = new int[256];
		for (int i = 0; i < list.length; i++) {
			list[i] = i;
		}
		
		int answer = processLengths(list, lengths);
		
		System.out.println("The answer is: " + answer);
	}

	private static int processLengths(int[] list, List<Integer> lengths) {
		int currPos = 0;
		int skipSize = 0;
		
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
		
		//Multiply the first two elements
		return list[0] * list[1];
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
	
	private static List<Integer> readLengthsFromFile(String filename) {
		List<Integer> lengths = new ArrayList<Integer>();
		
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextInt()) {
				lengths.add(scan.nextInt());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return lengths;
	}

	
	
}
