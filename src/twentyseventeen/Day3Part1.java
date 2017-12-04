package twentyseventeen;

public class Day3Part1 {
	
	public static void main(String[] args) {
		int input = 312051;
		int layer = 0;
		int cornerNum = 1;
		
		//Determine what layer number our input value is on
		while (cornerNum < input) {
			layer++;
			cornerNum += (8 * layer);
		}
		
		int faceSize = (layer * 8) / 4;
		int lastCornerNum = cornerNum - faceSize;
		int midpoint = cornerNum - (faceSize / 2);
		
		System.out.println("Corner Num is: " + cornerNum);
		System.out.println("Previous corner Num is: " + lastCornerNum);
		System.out.println("Midpoint is: " + midpoint);
		
		int answer = 0;
		if (midpoint == input) {
			answer = layer;
		}
		else if (cornerNum == input) {
			answer = layer * 2;
		}
		else if (midpoint < input) {
			answer = layer + (input - midpoint);
		}
		else if (midpoint > input) {
			answer = layer + (midpoint - input);
		}
		
		System.out.println("Distance from center is: " + answer);
	}
	
}
			
			
			