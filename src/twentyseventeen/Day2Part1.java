package twentyseventeen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jdk.internal.org.xml.sax.InputSource;

public class Day2Part1 {

	static ArrayList<ArrayList<Integer>> inputs = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String[] args) {
		inputs = readIntsFromFile();
		
		int sum = 0;
		for (int row = 0; row < inputs.get(0).size(); row++) {
			int max = 0;
			int min = 99999;
			
			for (int col = 0; col < inputs.size(); col++) {
				if (max < inputs.get(row).get(col)) {
					max = inputs.get(row).get(col);
				}
				if (min > inputs.get(row).get(col)) {
					min = inputs.get(row).get(col);
				}
			}
		
			sum += (max - min);
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
