package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import sun.misc.Queue;

public class Day12Part2 {

	public static void main(String[] args) {
		//Build a list of all programs and their connections
		Map<Integer, List<Integer>> pipes = readInput("resources/day12input.txt");
		//Find all programs attached to program 0
		int numGroups = findNumGroups(pipes);
		System.out.println("Number of groups are: " + numGroups);
	}

	private static int findNumGroups(Map<Integer, List<Integer>> pipes) {
		List<List<Integer>> groups = new ArrayList<List<Integer>>();
		Set<Integer> programsProcessed = new HashSet<Integer>();
		
		//Iterate through each entry in our list of pipes
		Iterator<Integer> it = pipes.keySet().iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			//Make sure we don't process an entry more than once
			if (!programsProcessed.contains(key)) {
				//Find all programs in the group this key belongs to
				List<Integer> currGroup = findAllPrograms(key, pipes);
				//Add to our list of processed programs and group list
				programsProcessed.addAll(currGroup);
				groups.add(currGroup);
			}
		}
		
		return groups.size();
	}

	private static List<Integer> findAllPrograms(int startIndex, Map<Integer, List<Integer>> pipes) {
		List<Integer> programsInGroup = new ArrayList<Integer>();
		
		//Set to hold processed programs
		Set<Integer> processed = new HashSet<Integer>();
		processed.add(startIndex);
		
		//Queue to process each program in order
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(startIndex);
		
		while (!q.isEmpty()) {
			//Get the current program ID and increment count
			int currId = -1;
			try {
				currId = (int) q.dequeue();
				programsInGroup.add(currId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Add all connections to the stack
			List<Integer> connections = pipes.get(currId);
			for (int i = 0; i < connections.size(); i++) {
				//Don't process programs twice
				if (!processed.contains(connections.get(i))) {
					q.enqueue(connections.get(i));
					processed.add(connections.get(i));
				}
			}
		}
		
		return programsInGroup;
	}

	private static Map<Integer, List<Integer>> readInput(String filename) {		
		Map<Integer, List<Integer>> pipes = new HashMap<Integer, List<Integer>>();
		try {
			Scanner fileScan = new Scanner(new File(filename));
			//Scan every line in the file
			while (fileScan.hasNextLine()) {
				//Scan tokens for the current line
				String line = fileScan.nextLine();
				Scanner lineScan = new Scanner(line);
				//Get the key for this line
				int key = lineScan.nextInt();
				//Skip the <-> token
				lineScan.next();
				//Process all connections to this key
				List<Integer> connections = new ArrayList<Integer>();
				while (lineScan.hasNext()) {
					String currToken = lineScan.next().replaceAll(",", "");
					connections.add(Integer.parseInt(currToken));
				}
				//Place this key-value pair into the map
				pipes.put(key, connections);
				lineScan.close();
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return pipes;
	}
	
}
