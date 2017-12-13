package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day7Part1 {
	
	public static void main(String[] args) {
		Map<String, Node> mappings = readFromFile("resources/day7input.txt");
		
		String bottom = findBottomProgram(mappings);
		
		System.out.println("The name of the bottom program is: " + bottom);
	}

	private static String findBottomProgram(Map<String, Node> mappings) {
		Iterator<Map.Entry<String,Node>> it = mappings.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Node> curr = it.next();
			if (curr.getValue().parentName == null || "".equals(curr.getValue().parentName)) {
				return curr.getValue().name;
			}
		}
		return null;
	}

	private static Map<String, Node> readFromFile(String filename) {
		Map<String, Node> nodes = new HashMap<String, Node>();
		
		Scanner scan;
		try {
			scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				//Create a node using the current line from the input file
				Node n = createNodeFromInput(scan.nextLine());
				//Attempt to set parent values using this node's children
				for (String child : n.childrenNames) {
					Node childNode = nodes.get(child);
					//If the child node already exists, set the parent name
					if (childNode != null) {
						childNode.parentName = n.name;
						nodes.put(child, childNode);
					}
					//If not, create a new node with the parent name set
					else {
						nodes.put(child, new Node(child, n.name));						
					}
				}
				//Put this node into the map - update existing if needed
				Node existing = nodes.get(n.name);
				if (existing != null) {
					existing.childrenNames = n.childrenNames;
					existing.name = n.name;
					existing.weight = n.weight;
					nodes.put(n.name, existing);
				}
				else {
					nodes.put(n.name, n);					
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return nodes;
	}
	
	private static Node createNodeFromInput(String line) {
		//Remove unnecessary strings
		line = line.replace(",", "");
		line = line.replace("(", "");
		line = line.replace(")", "");
		line = line.replace("->", "");
		//Read the values
		Scanner lineScan = new Scanner(line);
		String name = lineScan.next();
		int weight = lineScan.nextInt();
		//Read all children if they exist
		List<String> children = new ArrayList<String>();
		while (lineScan.hasNext()) {
			children.add(lineScan.next());
		}
		lineScan.close();
		//Create a node using these values
		return new Node(name, weight, children);
	}

	public static class Node {
		public String name;
		public int weight;
		public String parentName;
		public List<String> childrenNames;

		public Node(String name2, String parent) {
			name = name2;
			parentName = parent;
		}
		
		public Node(String name2, int weight2, List<String> children) {
			name = name2;
			weight = weight2;
			childrenNames = children;
		}
	}
	
}
