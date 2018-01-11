package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day13Part1 {

	public static final int ARRAY_SIZE = 93;
	
	public static void main(String[] args) {
		//Read the firewall information from the input file
		int[] firewall = readFirewallInfo("resources/day13input.txt");
		//Calculate the severity of the trip
		int severity = findTripSeverity(firewall);
		System.out.println("Severity: " + severity);
	}

	private static int findTripSeverity(int[] firewall) {
		int severity = 0;
		int packetLayer = -1;
		
		for (int picosecond = 0; picosecond < firewall.length; picosecond++) {
			//First move our packet one layer over
			packetLayer++;
			//Determine if we were caught
			if (isCaught(picosecond, firewall[packetLayer])) {
				severity += packetLayer * firewall[packetLayer];
			}
		}
		
		return severity;
	}

	private static boolean isCaught(int picosecond, int scanLength) {
		//We are always caught at picosecond 0
		if (picosecond == 0) {
			return true;
		}
		//We can't be caught if there's no scanner
		else if (scanLength == 0) {
			return false;
		}
		//Determine if our scanner is at position 0
		if (picosecond > scanLength) {
			//Get the amount of times we've reverse movement
			int division = picosecond / (scanLength-1);
			//Scanner is at 0 every even-numbered reversal
			if (picosecond % (scanLength-1) == 0 && division % 2 == 0) {
				return true;
			}
		}
		return false;
	}

	private static int[] readFirewallInfo(String filename) {
		int[] firewall = new int[ARRAY_SIZE];
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				String layer = scan.next().replaceAll(":", "");
				int layerNum = Integer.parseInt(layer);
				firewall[layerNum] = scan.nextInt();
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return firewall;
	}
	
}
