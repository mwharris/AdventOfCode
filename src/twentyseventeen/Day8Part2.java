package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Day8Part2 {
	
	public static void main(String[] args) {
		Map<String, Integer> registers = new HashMap<String, Integer>();
		int max = 0;
		
		try {
			Scanner scan = new Scanner(new File("resources/day8input.txt"));
			while (scan.hasNextLine()) {
				int curr = processLine(scan.nextLine(), registers);
				if (curr > max) {
					max = curr;
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("The largest value is: " + max);
	}

	private static int processLine(String line, Map<String, Integer> registers) {
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
		return lReg;
	}

}
