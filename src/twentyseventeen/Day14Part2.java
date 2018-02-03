package twentyseventeen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14Part2 {
	
	public static void main(String[] args) {
		String puzzleInput = "amgozmfv";
		//Create list of 128 hash key inputs
		String[] hashInputs = getHashInputs(puzzleInput);
		//Create our grid of binary numbers
		Integer[][] grid = new Integer[128][128];
		for (int i = 0; i < hashInputs.length; i++) {
			grid[i] = generateGridRow(hashInputs[i]);
		}
		//Find the number of regions in this grid
		System.out.println("Number of regions: " + findNumRegions(grid));
	}

	//Count the number of connected regions in the grid
	private static int findNumRegions(Integer[][] grid) {
		int numRegions = 0;		
		//Use a set to not count visited squares twice
		Set<String> visited = new HashSet<String>();
		//Process every square in the grid
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] == 1) {
					//Check if this square has been visited
					if (!visited.contains(y+"-"+x)) {
						//New region, mark all connected squares as visited
						markNeighborsVisited(y, x, grid, visited);
						//Count this as a new region
						numRegions++;
					}
				}
			}
		}
		return numRegions;
	}

	private static void markNeighborsVisited(int y, int x, Integer[][] grid, Set<String> visited) {
		//Mark our current position as visited
		String thisKey = y+"-"+x;
		visited.add(thisKey);
		//
		//Recurse through same-valued grid squares
		if (y > 127 || y < 0 || x > 127 || x < 0) {
			System.out.println("DAFUQ?");
		}
		//Down
		if (y+1 < grid.length && grid[y+1][x] == 1) {
			String key = (y+1)+"-"+x;
			if (!visited.contains(key)) {
				markNeighborsVisited(y+1, x, grid, visited);
			}
		}
		//Up
		if (y > 0 && grid[y-1][x] == 1) {
			String key = (y-1)+"-"+x;
			if (!visited.contains(key)) {
				markNeighborsVisited(y-1, x, grid, visited);
			}
		}
		//Right
		if (x+1 < grid[0].length && grid[y][x+1] == 1) {
			String key = y+"-"+(x+1);
			if (!visited.contains(key)) {
				markNeighborsVisited(y, x+1, grid, visited);
			}
		}
		//Left
		if (x > 0 && grid[y][x-1] == 1) {
			String key = y+"-"+(x-1);
			if (!visited.contains(key)) {
				markNeighborsVisited(y, x-1, grid, visited);
			}
		}
	}

	private static Integer[] generateGridRow(String hashInput) {
		//Call Day10Part2 solution to compute knot hash for given hash key
		String knotHash = Day10Part2.doKnotHash(hashInput, false);
		//Convert the hexadecimal to a list of binary digits
		return hexToBinaryList(knotHash);
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
	//TODO: Figure out to turn hex strings, by digits, into binary digits
	private static Integer[] hexToBinaryList(String hex) {
		//Create a list to hold the binary digits of 1s and 0s
		Integer[] binaryList = new Integer[128];
		//Fill the array using the hex string
		StringBuilder sb = new StringBuilder();
		//Convert each hex digit into a 4 bit binary string
		for (int i = 0; i < hex.length(); i++) {
			//Convert the current digit into binary 
			String binary = new BigInteger(Character.toString(hex.charAt(i)), 16).toString(2);
			//Add leading 0s if needed
			if (binary.length() < 4) {
				if (binary.length() == 3) {
					sb.append("0");
				}
				else if (binary.length() == 2) {
					sb.append("00");
				}
				else if (binary.length() == 1) {
					sb.append("000");
				}
			}
			//Add this string to our running string of binary 
			sb.append(binary);
		}
		//Now place each bit into the list one by one
		for (int i = 0; i < sb.length(); i++) {
			binaryList[i] = Integer.parseInt(Character.toString(sb.charAt(i)));
		}
		return binaryList;
	}
}
