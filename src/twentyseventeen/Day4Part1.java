package twentyseventeen;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day4Part1 {
	
	public static void main(String[] args) {
		ArrayList<String[]> passwords = readFromFile("resources/day4input.txt");
		
		int numValid = getValidPassphrases(passwords);
		
		System.out.println(numValid);
	}

	private static int getValidPassphrases(ArrayList<String[]> passwords) {
		int validCount = 0;
		int i = 0;
		while (i < passwords.size()) {
			if (checkIfValid(passwords.get(i))) {
				validCount++;
			}
			i++;
		}
		return validCount;
	}

	private static boolean checkIfValid(String[] line) {		
		int i = 0;
		while (i <= line.length - 2) {
			//If the current token matches the next token then this is invalid
			if (line[i].equals(line[i + 1])) {
				return false;
			}
			else {
				i++;
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
}
