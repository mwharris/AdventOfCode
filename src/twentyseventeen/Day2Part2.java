package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day2Part2 {
	
static ArrayList<ArrayList<Integer>> inputs = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String[] args) {
		inputs = readIntsFromFile();
		
		//Loop through inputs row by row
		int sum = 0;
		for (int row = 0; row < inputs.get(0).size(); row++) {			
			for (int col = 0; col < inputs.size(); col++) {
				int curr = inputs.get(row).get(col);
				
				//Check if any inputs after this evenly divide
				int col2 = col+1;
				int other = 0;
				boolean found = false;
				while (col2 < inputs.size()) {
					other = inputs.get(row).get(col2);
					if (curr % other == 0 || other % curr == 0) {
						found = true;
						break;	
					}
					col2++;
				}
				
				if (found) {
					if (curr > other) {
						sum += curr / other;
					} else {
						sum += other / curr;
					}
					break;	
				}
			}
		}
		
		System.out.println(sum);
	}
	
	private static ArrayList<ArrayList<Integer>> readIntsFromFile() {
		ArrayList<ArrayList<Integer>> inputs = new ArrayList<ArrayList<Integer>>();
		
		try {
			Scanner scanner = new Scanner(new File("resources/input.txt"));
			
			int x = 0;
			ArrayList<Integer> line = new ArrayList<Integer>();
			while(scanner.hasNextInt()) {
				if (x == 16) {
					inputs.add(line);
					line = new ArrayList<Integer>();
					x = 0;
				}
				line.add(scanner.nextInt());
				x++;
			}
			inputs.add(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return inputs;
	}
	
}
