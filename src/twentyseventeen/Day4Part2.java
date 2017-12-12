package twentyseventeen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day4Part2 {
	
	public static void main(String[] args) {
		ArrayList<String[]> passwords = readFromFile("resources/day4input.txt");
		
		int numValid = getValidPassphrases(passwords);
		
		System.out.println(numValid);
	}

	private static int getValidPassphrases(ArrayList<String[]> passwords) {
		int validCount = 0;
		for (String[] line : passwords) {
			if (checkIfValid(line)) {
				validCount++;
			}
		}
		return validCount;
	}

	private static boolean checkIfValid(String[] line) {
		//Check every token in the line
		for (int i = 0; i <= line.length - 2; i++) {
			//If the current token matches the next token then this is invalid
			if (line[i].equals(line[i+1])) {
				return false;
			}
		}
		return true;
	}	

	private static ArrayList<String[]> readFromFile(String filename) {
		ArrayList<String[]> ret = new ArrayList<String[]>();
		
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				//Get the current passphrase line
				String line = scan.nextLine();
				//Break up into individual strings
				String[] tokens = line.split(" ");
				//Sort each String alphabetically
				for (int i = 0; i < tokens.length; i++) {
					tokens[i] = sortString(tokens[i]);
				}
				//Sort alphabetically
				Arrays.sort(tokens);
				//Add to our passwords list
				ret.add(tokens);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	private static String sortString(String input) {
		//Convert the string to a char array
        char tempArray[] = input.toCharArray();
        //Sort the char array
        Arrays.sort(tempArray);
        //Return a new string made from sorted char array
        return new String(tempArray);
	}
}
