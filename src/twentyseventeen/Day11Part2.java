package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day11Part2 {
	
	public static final String N = "n";
	public static final String NE = "ne";
	public static final String NW = "nw";
	public static final String S = "s";
	public static final String SE = "se";
	public static final String SW = "sw";
	public static final String E = "e";
	public static final String W = "w";
	
	public static void main(String[] args) {
		//Calculate the furthest distance from starting point
		Double farthestDistance = findFarthestDistance("resources/day11input.txt");
		System.out.println("Fewest steps path: " + farthestDistance);
	}

	private static Double findFarthestDistance(String filename) {
		Double maxDistance = 0D;
		
		//Setup a map to track each time a direction is taken
		Map<String, Double> path = new HashMap<String, Double>();
		path.put(N,  0D);
		path.put(S,  0D);
		path.put(E,  0D);
		path.put(W,  0D);
		
		//Read in all path inputs
		try {
			Scanner fileScan = new Scanner(new File(filename));
			fileScan.useDelimiter(",");
			//Read the input token by token separated by commas
			while (fileScan.hasNext()) {
				String curr = fileScan.next();
				//Update our map with the current instruction
				path = countInstruction(curr, path);
				//Calculate our current distance from starting point
				Double distance = calculateDistance(path);
				//Update our max accordingly
				if (distance > maxDistance) {
					maxDistance = distance;
				}
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return maxDistance;
	}

	/*
	 * Update our map to count the latest instruction
	 */
	private static Map<String, Double> countInstruction(String curr, Map<String, Double> path) {
		//Complex instruction (NE, NW, SE, SW)
		if (curr.length() > 1) {
			//First character (North or South)
			if (curr.charAt(0) == 'n') {
				Double existing = path.get(N);
				path.put(N, existing + 0.5D);
			}
			else {
				Double existing = path.get(S);
				path.put(S, existing + 0.5D);
			}
			//Second character (East or West)
			if (curr.charAt(1) == 'e') {
				Double existing = path.get(E);
				path.put(E, ++existing);
			}
			else {
				Double existing = path.get(W);
				path.put(W, ++existing);
			}
		}
		//Simple instruction (North or South)
		else {
			Double existing = path.get(curr);
			path.put(curr, ++existing);
		}
		return path;
	}
	
	/*
	 * Calculate our current distance from the starting point
	 */
	private static Double calculateDistance(Map<String, Double> path) {
		Map<String, Double> temp = new HashMap<String, Double>(path);
		temp = simplifyValues(temp);
		return findShortestPath(temp);
	}

	/**
	 * Simplify values by removing S from N, NE from SW, SE from NW, etc.
	 */
	private static Map<String, Double> simplifyValues(Map<String, Double> path) {
		Double north = path.get(N);
		Double south =  path.get(S);
		Double east =  path.get(E);
		Double west =  path.get(W);
		
		//North and South
		if (north > south) {
			north -= south;
			south = 0D;
		}
		else {
			south -= north;
			north = 0D;
		}
		path.put(N, north);
		path.put(S, south);
		
		//East and West
		if (east > west) {
			east -= west;
			west = 0D;
		}
		else {
			west -= east;
			west = 0D;
		}
		path.put(W, west);
		path.put(E, east);
		
		return path;
	}
	
	/*
	 * Used the updated values to determine the shortest path
	 */
	private static Double findShortestPath(Map<String, Double> path) {
		Double north = path.get(N);
		Double south = path.get(S);
		Double east =  path.get(E);
		Double west =  path.get(W);
		
		//At this point the direction of the path should be either: NE, NW, SE, SW
		Double ns = 0D;
		Double ew = 0D;
		if (north > 0 && east > 0) {
			ns = north;
			ew = east;
		}
		else if (north > 0 && west > 0) {
			ns = north;
			ew = west;
		}
		else if (south > 0 && east > 0) {
			ns = south;
			ew = east;
		}
		else if (south > 0 && west > 0) {
			ns = south;
			ew = west;
		}
		
		//Calculate shortest path by trying to make as many complex instructions as possible
		if (ew >= ns || ns * 2 > ew) {
			return ew + (ns - (ew/2));
		}
		else {
			return (ns * 2) + ew;
		}
	}
	
}
