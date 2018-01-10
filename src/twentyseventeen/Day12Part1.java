package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import sun.misc.Queue;

public class Day12Part1 {

	public static void main(String[] args) {
		//Build a list of all programs and their connections
		Map<Integer, List<Integer>> pipes = findProgramCount("resources/day12input.txt");
		//Find all programs attached to program 0
		int numPrograms = findAllPrograms(0, pipes);
		System.out.println("Number of programs attached to 0: " + numPrograms);
	}

	private static int findAllPrograms(int startIndex, Map<Integer, List<Integer>> pipes) {
		int numPrograms = 0;
		Set<Integer> processed = new HashSet<Integer>();
		processed.add(startIndex);
		Queue q = new Queue();
		q.enqueue(startIndex);
		
		while (!q.isEmpty()) {
			//Get the current program ID and increment count
			int currId;
			try {
				currId = (int) q.dequeue();
				numPrograms++;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return 0;
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
		
		return numPrograms;
	}

	private static Map<Integer, List<Integer>> findProgramCount(String filename) {		
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
