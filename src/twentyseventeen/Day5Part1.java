package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5Part1 {
	
	public static void main(String[] args) {
		//Find our maze to process
		List<Integer> maze = readInput();
		
		//Process the maze until we escape
		int steps = 0;
		int i = 0;
		while (i < maze.size()) {
			//Get the current instruction
			Integer currInstruct = maze.get(i);
			//Apply any offsets needed
			i += currInstruct.intValue();
			//Update the index of the current instruction
			int newVal = currInstruct.intValue();
			if (newVal >= 3) {
				newVal -= 1;
			} else {
				newVal += 1;
			}
			maze.set(i - currInstruct.intValue(), newVal);
			//Keep a running count of all steps
			steps++;
		}
		
		System.out.println(steps);
	}

	private static List<Integer> readInput() {
		String filename = "resources/day5input.txt";
		
		List<Integer> inputs = new ArrayList<Integer>();
		
		try {
			Scanner scanner = new Scanner(new File(filename));
			while(scanner.hasNextInt()) {
				inputs.add(scanner.nextInt());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		
		return inputs;
	}
	
}
