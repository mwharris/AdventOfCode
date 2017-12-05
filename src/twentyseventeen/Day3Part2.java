package twentyseventeen;

public class Day3Part2 {
	
	static int[][] nums = new int[100][100];
	
	public static void main(String[] args) {
		int input = 312051;
		int x = 49;
		int y = 49;
		int dirX = 1;
		int dirY = 0;
		int i = 0;
		int dirChangeIndex = 1;
		int currVal = 0;
		
		while (currVal < input) {
			if (currVal == 0 || currVal == 1) {
				currVal++;
				nums[x][y] = 1;
			}
			else {
				//Compute the surrounding elements
				currVal = addNeighbors(x, y);
				nums[x][y] = currVal;
				//Navigate to the next spot in the spiral
				move(x, y, dirX, dirY, i, dirChangeIndex);
				resetDirectionChangeIndex();
			}
			i++;
		}
		
		System.out.println(currVal);
	}

	private static void resetDirectionChangeIndex() {
		
	}

	private static void move(int x, int y, int dirX, int dirY, int i, int dirChangeIndex) {
		int prevDirX = dirX;
		int prevDirY = dirY;
		if (i == dirChangeIndex) {
			//Rotate X left
			if (dirX > 0 || dirX < 0) {
				dirX = 0;
			}
			else if (dirX == 0) { 
				dirX = -prevDirY;
			}
			//Rotate Y left
			if (dirY > 0 || dirY < 0) {
				dirY = 0;
			}
			else if (dirY == 0) {
				dirY = -prevDirX;
			}
		}
	}

	private static int addNeighbors(int x, int y) {
		return nums[x-1][y] + nums[x-1][y-1] + nums[x][y-1] + nums[x+1][y-1] + nums[x+1][y] + nums[x+1][y+1] + nums[x][y+1] + nums[x-1][y+1];
	}
	
}
			
			
			