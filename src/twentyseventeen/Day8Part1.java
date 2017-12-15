package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Day8Part1 {
	
	public static void main(String[] args) {
		Map<String, Integer> registers = processInstructions();
		
		int max = findLargestValue(registers);
		
		System.out.println("The largest value is: " + max);
	}
	
	private static int findLargestValue(Map<String, Integer> registers) {
		int largest = 0;
		Iterator<Map.Entry<String, Integer>> it = registers.entrySet().iterator();
		while (it.hasNext()) {
			int curr = it.next().getValue();
			if (curr > largest) {
				largest = curr;
			}
		}
		return largest;
	}

	private static Map<String, Integer> processInstructions() {
		Map<String, Integer> registers = new HashMap<String, Integer>();
		try {
			Scanner scan = new Scanner(new File("resources/day8input.txt"));
			while (scan.hasNextLine()) {
				processLine(scan.nextLine(), registers);
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return registers;
	}

	private static void processLine(String line, Map<String, Integer> registers) {
		Scanner scan = new Scanner(line);
		//Get the left side of the instruction
		String lRegisterName = scan.next();
		boolean inc = scan.next().equals("inc") ? true : false;
		int lValue = scan.nextInt();
		//Skip the "if"
		scan.next();
		//Get the condition of the instruction
		String rRegisterName = scan.next();
		String condition = scan.next();
		int rValue = scan.nextInt();
		//Close our scanner
		scan.close();
		//Set any registers that are missing
		Integer lReg = registers.get(lRegisterName);
		if (lReg == null) {
			lReg = 0;
			registers.put(lRegisterName, lReg);
		}
		Integer rReg = registers.get(rRegisterName);
		if (rReg == null) {
			rReg = 0;
			registers.put(rRegisterName, rReg);
		}
		//Process the instruction using the values received
		switch(condition) {
		case "<":
			if (rReg < rValue) {
				if (inc) {
					lReg += lValue;
				}
				else {
					lReg -= lValue;
				}
			}
			break;
		case "<=":
			if (rReg <= rValue) {
				if (inc) {
					lReg += lValue;
				}
				else {
					lReg -= lValue;
				}
			}
			break;
		case ">":
			if (rReg > rValue) {
				if (inc) {
					lReg += lValue;
				}
				else {
					lReg -= lValue;
				}
			}
			break;
		case ">=":
			if (rReg >= rValue) {
				if (inc) {
					lReg += lValue;
				}
				else {
					lReg -= lValue;
				}
			}
			break;
		case "==":
			if (rReg == rValue) {
				if (inc) {
					lReg += lValue;
				}
				else {
					lReg -= lValue;
				}
			}
			break;
		case "!=":
			if (rReg != rValue) {
				if (inc) {
					lReg += lValue;
				}
				else {
					lReg -= lValue;
				}
			}
			break;
		default:
			break;
		}
		//Update the registers with the new value
		registers.put(lRegisterName, lReg);
	}

	
	
}
