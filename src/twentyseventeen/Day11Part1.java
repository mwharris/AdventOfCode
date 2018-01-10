package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day11Part1 {
	
	public static final String N = "n";
	public static final String NE = "ne";
	public static final String NW = "nw";
	public static final String S = "s";
	public static final String SE = "se";
	public static final String SW = "sw";
	public static final String E = "e";
	public static final String W = "w";
	
	public static void main(String[] args) {
		//Get a map of each direction and how many times they were taken
		Map<String, Double> path = processChildPath("resources/day11input.txt");
		//Simplify the values by removing contradictory values
		path = simplifyValues(path);
		//Determine the shortest path using N/S/E/W components
		Double shortestPath = findShortestPath(path);
		System.out.println("Fewest steps path: " + shortestPath);
	}

	private static Map<String, Double> processChildPath(String filename) {
		//Setup a map to track each time a direction was taken
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
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return path;
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
