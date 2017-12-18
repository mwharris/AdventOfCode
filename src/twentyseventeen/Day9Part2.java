package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9Part2 {

	public static void main(String[] args) {
		//Read the input file
		String input = readFromFile("resources/day9input.txt");
		//Compute the garbage count
		int garbageCount = findGarbageCount(input);
		System.out.println("Total Group Score is: " + garbageCount);
	}

	private static int findGarbageCount(String input) {
		int garbageCount = 0; 
		
		//Loop through each character in the string
		for (int i = 0; i < input.length(); i++) {
			//Get the current character
			char curr = input.charAt(i);
			//Start of garbage sequence: remove the garbage
			if (curr == '<') {
				//Find the next closest '>' not preceded by a '!'
				int closeIndex = findClosingGarbageIndex(input.substring(i+1, input.length())) + i;
				//Find the garbage string
				String garbageStr = input.substring(i+1, closeIndex).replaceAll("!.", "");
				garbageCount += garbageStr.length();
				//Will return -1 if not found
				if (closeIndex > -1) {
					//Remove all garbage in this garbage sequence
					input = input.substring(0, i) 
							+ input.substring(closeIndex + 1, input.length());
				}
				//Decrement index to not skip any chars
				i--;
			}
		}
		
		return garbageCount;
	}

	//Find the correct end index of a garbage sequence
	private static int findClosingGarbageIndex(String input) {
		//Regex for finding next '>' not preceeded by '!'
		Pattern pattern = Pattern.compile(">");
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) { 
			int index = matcher.start();
			if (index == 0) { return 1; }
			String test = input.substring(0, index).replaceAll("!.", "");
			if (test.isEmpty() || test.charAt(test.length() - 1) != '!') {
				return index + 1;
			}
		}
		return -1;
	}

	private static String readFromFile(String filename) {
		String input = "";
		try {
			Scanner scan = new Scanner(new File(filename));
			input = scan.next();
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return input;
	}
	
}
