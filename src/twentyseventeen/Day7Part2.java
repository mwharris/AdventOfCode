package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import twentyseventeen.Day7Part2.Node;

public class Day7Part2 {
	
	public static void main(String[] args) {
		Map<String, Node> mappings = readFromFile("resources/day7input.txt");
		
		String bottom = findBottomProgram(mappings);
		
		int correctWeight = findCorrectWeight(bottom, mappings);
		
		System.out.println("The corrected weight is: " + correctWeight);
	}

	/*
	 * Calculate the fixed weight fiven all information
	 */
	private static int findCorrectWeight(String bottom, Map<String, Node> mappings) {
		//Find the key of the node that is incorrect
		String incorrect = findIncorrectWeightNode(bottom, mappings);
		//Get that node's information
		Node nIncorrect = mappings.get(incorrect);
		//Find the weight of the incorrect node's sibling
		int siblingWeight = findWeight(findSibling(incorrect, mappings), mappings);
		//Find the weight of the incorrect node
		int incorrectWeight = nIncorrect.weight + (nIncorrect.childWeights[0] * 3);
		//Determine the difference
		int difference = siblingWeight - incorrectWeight;
		//The incorrect node's new weight should be weight + difference between sibling and incorrect node
		return nIncorrect.weight + difference;
	}
	
	/*
	 * Find the node that has the incorrect weight by determining all 
	 * children's weight and then repeating the process with he outlier.  
	 * If no outlier exists, then we found the incorrect node
	 */
	private static String findIncorrectWeightNode(String programName, Map<String, Node> mappings) {	
		Node n = mappings.get(programName);
		//Determine the weight of all children nodes
		int[] childWeights = new int[n.childrenNames.size()];
		for (int i = 0; i < n.childrenNames.size(); i++) {
			childWeights[i] = findWeight(n.childrenNames.get(i), mappings);
			n.childWeights = childWeights;
			mappings.put(programName, n);
		}
		//Find the outlier weight if one exists
		int outlierIndex = findOutlier(childWeights);
		if (outlierIndex != -1) {
			//Outlier found, repeat the proces with that node
			return findIncorrectWeightNode(n.childrenNames.get(outlierIndex), mappings);
		}
		else {
			//No outlier found, we have found our incorrect node
			return n.name;
		}
	}

	/*
	 * Find the weight of a given node (node's weight + weight of all children)
	 */
	private static int findWeight(String nodeName, Map<String, Node> mappings) {
		Node n = mappings.get(nodeName);
		int weight = n.weight;
		if (n.childrenNames != null) {
			for (int i = 0; i < n.childrenNames.size(); i++) {
				weight += findWeight(n.childrenNames.get(i), mappings);
			}
		}
		return weight;
	}

	/*
	 * Find the outlier integer in a list of integers
	 */
	private static int findOutlier(int[] childWeights) {
		boolean outlierFound = false;
		int index = 0;
		for (index = 0; index < childWeights.length; index++) {
			if (index + 2 < childWeights.length) {
				if (childWeights[index] != childWeights[index+1] 
						&& childWeights[index] != childWeights[index+2]) {
					outlierFound = true;
					break;
				}
			}
			else if (index + 1 < childWeights.length) {
				if (childWeights[index] != childWeights[index+1] 
						&& childWeights[index] != childWeights[index-1]) {
					outlierFound = true;
					break;
				}
			}
			else if (index < childWeights.length) {
				if (childWeights[index] != childWeights[index-1] 
						&& childWeights[index] != childWeights[index-2]) {
					outlierFound = true;
					break;
				}
			}
		}
		if (outlierFound) {
			return index;
		} 
		else {
			return -1;
		}
	}
	/*
	 * Find the sibling of a given node
	 */
	private static String findSibling(String incorrect, Map<String, Node> mappings) {
		Node parent = mappings.get(mappings.get(incorrect).parentName);
		for (String child : parent.childrenNames) {
			if (!child.equals(incorrect)) {
				return child;
			}
		}
		return null;
	}

	/*
	 * Find the bottom-most node of this tree structure
	 */
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
		public int[] childWeights;

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
