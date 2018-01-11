package twentyseventeen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day13Part2 {

	public static final int ARRAY_SIZE = 93;
	
	public static void main(String[] args) {
		//Read the firewall information from the input file
		int[] firewall = readFirewallInfo("resources/day13input.txt");
		//Calculate the severity of the trip
		int delayRequired = findRequiredDelay(firewall);
		System.out.println("Picosecond delay required to not get caught: " + delayRequired);
	}

	private static int findRequiredDelay(int[] firewall) {
		int delay = -1;
		boolean caught = true;
		
		//Loop until we find a delay that doesn't result in being caught
		while (caught) {
			delay++;
			caught = checkIfCaught(delay, firewall);
		}
		
		return delay;
	}

	private static boolean checkIfCaught(int picosecond, int[] firewall) {
		int packetLayer = -1;
		
		while (packetLayer < firewall.length - 1) {
			//Move our packet one layer over
			packetLayer++;
			//Determine if we were caught
			if (isCaught(picosecond, firewall[packetLayer])) {
				//Stop if we were
				return true;
			}
			picosecond++;
		}
		
		return false;
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
		//Special case for 2: all multiples of 2 result in being caught
		if (scanLength == 2) {
			return picosecond % 2 == 0;
		}
		//Special case for 3: all multiples of 4 result in being caught
		else if (scanLength == 3){
			return picosecond % 4 == 0;
		}
		//General case
		else if (picosecond >= scanLength) {
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
