package twentyseventeen;

public class Day3Part1 {
	
	public static void main(String[] args) {
		int layer = 0;
		int cornerNum = 1;
		
		//Determine what layer number our input value is on
		while (cornerNum < 312051) {
			layer++;
			cornerNum = ((2 * layer) + 1) * ((2 * layer) + 1);
		}
		
		System.out.println("Corner Num is: " + cornerNum);
		System.out.println("Distance from center is: " + (cornerNum - 312051));
		
	}
	
}
			
			
			