package twentyseventeen;

public class Day3Part1 {
	
	public static void main(String[] args) {
		int input = 312051;
		int layer = 0;
		int cornerNum = 1;
		
		//Determine what layer number our input value is on as well as next corner number
		while (cornerNum < input) {
			layer++;
			cornerNum += (8 * layer);
		}
		
		//Calculate the midpoint value of the face our input is on
		int faceSize = (layer * 8) / 4;
		int midpoint = cornerNum - (faceSize / 2);
		
		//Distance to center is simply the layer number + the difference between midpoint number and the input number
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
			
			
			