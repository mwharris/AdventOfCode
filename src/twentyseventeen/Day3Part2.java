package twentyseventeen;

public class Day3Part2 {
	
	static int[][] nums = new int[100][100];
	static int x = 49;
	static int y = 49;
	static int dirX = 1;
	static int dirY = 0;
	
	public static void main(String[] args) {
		int input = 312051;
		int i = 0;
		int currVal = 0;
		
		while (currVal < input) {
			//The first two elements are defaulted to 1
			if (currVal == 0 || currVal == 1) {
				currVal++;
				nums[x][y] = 1;
			}
			//For all other elements, compute the value by adding neighbors
			else {
				currVal = addNeighbors(x, y);
				nums[x][y] = currVal;
			}
			//Navigate to the next spot in the spiral
			move();
			//Turn our direction if required
			turn(i);
			i++;
		}
		
		System.out.println(currVal);
	}

	private static void turn(int i) {
		//Headed to the right
		if (dirX == 1) {
			//We want to face upwards
			if (nums[x][y+1] == 0) {
				turnLeft();
			}
		}
		//Headed upwards
		if (dirY == 1) {
			//We want to face to the left
			if (nums[x-1][y] == 0) {
				turnLeft();
			}
		}
		//Headed to the left
		if (dirX == -1) {
			//We want to face downwards
			if (nums[x][y-1] == 0) {
				turnLeft();
			}
		}
		//Headed to downwards
		if (dirY == -1) {
			//We want to face to the right
			if (nums[x+1][y] == 0) {
				turnLeft();
			}
		}
	}

	private static void turnLeft() {
		if (dirY != 0) {
			dirX = -dirY;
			dirY = 0;
		}
		else {
			dirY = dirX;
			dirX = 0;
		}
	}

	private static void move() {
		x += dirX;
		y += dirY;
	}

	private static int addNeighbors(int x, int y) {
		return nums[x-1][y] + nums[x-1][y-1] 
				+ nums[x][y-1] + nums[x+1][y-1] 
				+ nums[x+1][y] + nums[x+1][y+1] 
				+ nums[x][y+1] + nums[x-1][y+1];
	}
	
}
			
			
			