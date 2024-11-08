import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TennerGridSolver {

	static int consistency;
	static int totalConsistency = 0;
	static int numOfAssignments;
	static int totalAssignments = 0;
	static long time, totalTime;
	

	public static void main(String[] args) {

		int size = 0;
		Scanner scan = new Scanner(System.in);

		System.out.println("Welcome to the Tenner Grid Puzzle AI Solver!\n");
		System.out.println("What Tenner Grid size do you want to solve?\n" + "1. 3x10\n" + "2. 4x10\n" + "3. 5x10\n"
				+ "4. 6x10\n" + "\nEnter your choice: ");

		int s = scan.nextInt();
		switch (s) {
		case 1:
			size = 3;
			break;

		case 2:
			size = 4;
			break;

		case 3:
			size = 5;
			break;

		case 4:
			size = 6;
			break;

		}

		TennerGridGenerator allGrids[] = new TennerGridGenerator[5];
		TennerGridGenerator gridGenerator;

		for (int x = 0; x < 5; x++) {
			gridGenerator = new TennerGridGenerator(size);
			allGrids[x] = gridGenerator;
		}

		System.out.println("\n\nSimple Backtraking Method\n");
		for (int i = 0; i < 5; i++) {
			System.out.println("Run \"" + (i + 1) + "\"");

			System.out.println("----------------- Initial State ------------------");
			int[][] initialState = deepCopy(allGrids[i].grid);
			TennerGridGenerator.printGrid(initialState);

			long startTime = System.currentTimeMillis();
			backTracking(initialState, 0, 0);
            long endTime = System.currentTimeMillis();
            time = endTime - startTime;
            
			System.out.println("------------------- Final State ------------------");
			int[][] finalState = deepCopy(initialState);
			TennerGridGenerator.printGrid(finalState);
			initialState = deepCopy(allGrids[i].grid);
			
			System.out.println("Final Variable Assignments:");
			finalVariableAssignments(initialState, finalState);
			System.out.println("\nConsistency: " + consistency);
			System.out.println("Assignments: " + numOfAssignments);
			System.out.println("Time: " + time + "ms");
			System.out.println("-----------------------------------------------------\n");

			totalConsistency += consistency;
			totalAssignments += numOfAssignments;
			totalTime += time;
			consistency = 0;
			numOfAssignments = 0;
		}

		System.out.println("Average consistency: " + (totalConsistency / 5));
		System.out.println("Average assignments: " + (totalAssignments / 5));
		System.out.println("Average time: " + (totalTime / 5) + " ms");
		totalTime = 0;
		totalAssignments = 0;
		totalConsistency = 0;

		
		System.out.println("\n\nForward Checking  Method\n");
		for (int i = 0; i < 5; i++) {
			System.out.println("Run \"" + (i + 1) + "\"");

			System.out.println("----------------- Initial State ------------------");
			int[][] initialState = deepCopy(allGrids[i].grid);
			TennerGridGenerator.printGrid(initialState);
	
			long startTime = System.currentTimeMillis();
			forwardChecking(initialState, 0, 0, createPossibilities(size));
            long endTime = System.currentTimeMillis();
            time = endTime - startTime;
            
            
			System.out.println("------------------- Final State ------------------");
			int[][] finalState = deepCopy(initialState);
			TennerGridGenerator.printGrid(finalState);
			
			initialState = deepCopy(allGrids[i].grid);
			
			System.out.println("Final Variable Assignments:");
			finalVariableAssignments(initialState, finalState);
			System.out.println("\nConsistency: " + consistency);
			System.out.println("Assignments: " + numOfAssignments);
			System.out.println("Time: " + time + " ms");
			System.out.println("-----------------------------------------------------\n");
			
			totalConsistency += consistency;
			totalAssignments += numOfAssignments;
			totalTime += time;
			consistency = 0;
			numOfAssignments = 0;
		}
		
		System.out.println("Average consistency: " + (totalConsistency / 5));
		System.out.println("Average assignments: " + (totalAssignments / 5));
		System.out.println("Average time: " + (totalTime / 5) + " ms");
		totalTime = 0;
		totalAssignments = 0;
		totalConsistency = 0;

		
		System.out.println("\n\nForward Checking with MRV Method\n");
		for (int i = 0; i < 5; i++) {
			System.out.println("Run \"" + (i + 1) + "\"");

			System.out.println("----------------- Initial State ------------------");
			int[][] initialState = deepCopy(allGrids[i].grid);
			TennerGridGenerator.printGrid(initialState);

			long startTime = System.currentTimeMillis();
			forwardChecking_MRV(initialState, 0, 0, createPossibilities(size));
            long endTime = System.currentTimeMillis();
            time = endTime - startTime;
            
			System.out.println("------------------- Final State ------------------");
			int[][] finalState = deepCopy(initialState);
			TennerGridGenerator.printGrid(finalState);
			initialState = deepCopy(allGrids[i].grid);
			
			System.out.println("Final Variable Assignments:");
			finalVariableAssignments(initialState, finalState);
			System.out.println("\nConsistency: " + consistency);
			System.out.println("Assignments: " + numOfAssignments);
			System.out.println("Time: " + time + " ms");
			System.out.println("-----------------------------------------------------\n");
			
			totalConsistency += consistency;
			totalAssignments += numOfAssignments;
			totalTime += time;
			consistency = 0;
			numOfAssignments = 0;
		}
		
		System.out.println("Average consistency: " + (totalConsistency / 5));
		System.out.println("Average assignments: " + (totalAssignments / 5));
		System.out.println("Average time: " + (totalTime / 5) + " ms");
		totalTime = 0;
		totalAssignments = 0;
		totalConsistency = 0;

	}

	static void finalVariableAssignments(int[][] initialState, int[][] finalState) {
		for (int i = 0; i < initialState.length; i++) {
			for (int j = 0; j < initialState[i].length; j++) {
				if (initialState[i][j] == -1 && finalState[i][j] != -1) {
					System.out.println("[" + i + "," + j + "] was assigned: " + finalState[i][j]);
				}
			}
		}
	}
	
    static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

	static boolean backTracking(int grid[][], int row, int col) {

		if (row == grid.length - 1 && col == 10)
			return true;

		if (col == 10) {
			row++;
			col = 0;
		}

		if (grid[row][col] != -1)
			return backTracking(grid, row, col + 1);

		for (int num = 0; num < 10; num++) {

			if (isValid(grid, row, col, num)) {
				grid[row][col] = num;
				numOfAssignments++;
				if (backTracking(grid, row, col + 1))
					return true;
			}
			grid[row][col] = -1;
			numOfAssignments++;
		}
		return false;
	}

	// Forward checking to eliminate invalid values in the grid.
	static boolean forwardChecking(int[][] grid, int row, int column, boolean[][][] possibilities) {

		if (row == grid.length - 1 && column == 10)
			return true;

		if (column == 10) {
			row++;
			column = 0;
		}

		if (grid[row][column] != -1)
			return forwardChecking(grid, row, column + 1, possibilities);

		for (int number = 0; number < 10; number++) {
			if (possibilities[number][row][column] && isValid(grid, row, column, number)) {
				grid[row][column] = number;
				numOfAssignments++;
				updateThePossibilities(grid, row, column, possibilities, number, false);

				if (forwardChecking(grid, 0, 0, possibilities))
					return true;

				grid[row][column] = -1;
				numOfAssignments++;
				updateThePossibilities(grid, row, column, possibilities, number, true);
			}
		}
		return false;
	}

	static boolean forwardChecking_MRV(int[][] grid, int row, int column, boolean[][][] possibilities) {
		if (row == grid.length - 1 && column == 10)
			return true;

		if (column == 10) {
			row++;
			column = 0;
		}

		if (grid[row][column] != -1)
			return forwardChecking_MRV(grid, row, column + 1, possibilities);

		int minRemainingValues = Integer.MAX_VALUE;
		int minRow = -1, minColumn = -1;
		for (int i = 0; i < grid.length - 1; i++) {
			for (int j = 0; j < 10; j++) {
				if (grid[i][j] == -1) {
					int remainingValues = remainingValues(grid, i, j);
					if (remainingValues < minRemainingValues) {
						minRemainingValues = remainingValues;
						minRow = i;
						minColumn = j;
					}
				}
			}
		}

		if (minRow == -1 && minColumn == -1)
			return true;

		for (int number = 0; number < 10; number++) {
			if (possibilities[number][minRow][minColumn] && isValid(grid, minRow, minColumn, number)) {
				grid[minRow][minColumn] = number;
				numOfAssignments++;
				updateThePossibilities(grid, minRow, minColumn, possibilities, number, false);

				if (forwardChecking_MRV(grid, 0, 0, possibilities))
					return true;

				grid[minRow][minColumn] = -1;
				numOfAssignments++;
				updateThePossibilities(grid, minRow, minColumn, possibilities, number, true);
			}
		}
		return false;
	}

	// This
	static void updateThePossibilities(int[][] grid, int row, int column, boolean[][][] possibilities, int number,
			boolean update) {

		// Update the same row
		for (int j = 0; j < 10; j++) {
			possibilities[number][row][j] = update;
		}

		// Update the
		try {
			if (grid[row - 1][column] == number)
				possibilities[number][row - 1][column] = update;
		} catch (Exception e) {
			consistency--;
		}
		try {
			if (grid[row + 1][column] == number)
				possibilities[number][row + 1][column] = update;
		} catch (Exception e) {
			consistency--;
		}
		try {
			if (grid[row][column - 1] == number)
				possibilities[number][row][column - 1] = update;
		} catch (Exception e) {
			consistency--;
		}
		try {
			if (grid[row][column + 1] == number)
				possibilities[number][row][column + 1] = update;
		} catch (Exception e) {
			consistency--;
		}
		try {
			if (grid[row + 1][column + 1] == number)
				possibilities[number][row + 1][column + 1] = update;
		} catch (Exception e) {
			consistency--;
		}
		try {
			if (grid[row - 1][column - 1] == number)
				possibilities[number][row - 1][column - 1] = update;
		} catch (Exception e) {
			consistency--;
		}
		try {
			if (grid[row + 1][column - 1] == number)
				possibilities[number][row + 1][column - 1] = update;
		} catch (Exception e) {
			consistency--;
		}
		try {
			if (grid[row - 1][column + 1] == number)
				possibilities[number][row - 1][column + 1] = update;
		} catch (Exception e) {
			consistency--;
		}
	}

	// This method will create possibilities arrays for the FC method
	static boolean[][][] createPossibilities(int rows) {

		boolean[][][] possibilities = new boolean[10][rows][10];

		for (int number = 0; number < 10; number++)
			for (int i = 0; i < rows - 1; i++)
				for (int j = 0; j < 10; j++)
					possibilities[number][i][j] = true;

		return possibilities;
	}

	// Helper function to find the number of legal values for a cell
	static int remainingValues(int[][] grid, int row, int col) {

		int remainingValues = 0;
		for (int number = 0; number < 10; number++) {
			if (isValid(grid, row, col, number))
				remainingValues++;
		}
		return remainingValues;
	}

	// This method will check the consistency
	static boolean isValid(int[][] grid, int row, int column, int number) {

		// If we have the same number in the same row
		for (int x = 0; x < 10; x++) {
			consistency++;
			if (grid[row][x] == number) {
				return false;
			}
		}

		// Check the contiguous cells
		try {
			consistency++;
			if (grid[row - 1][column] == number) { // Top cell
				return false;
			}
		} catch (Exception e) {
			consistency--;
		}
		try {
			consistency++;
			if (grid[row + 1][column] == number && ((row + 1) != grid.length - 1)) { // Bottom cell
				return false;
			}
		} catch (Exception e) {
			consistency--;
		}

		// Check the diagonal cells
		try {
			consistency++;
			if (grid[row + 1][column + 1] == number && ((row + 1) != grid.length - 1)) {
				return false;
			}
		} catch (Exception e) {
			consistency--;
		}
		try {
			consistency++;
			if (grid[row - 1][column - 1] == number) {
				return false;
			}
		} catch (Exception e) {
			consistency--;
		}
		try {
			consistency++;
			if (grid[row + 1][column - 1] == number && ((row + 1) != grid.length - 1)) {
				return false;
			}
		} catch (Exception e) {
			consistency--;
		}
		try {
			consistency++;
			if (grid[row - 1][column + 1] == number)
				return false;
		} catch (Exception e) {
			consistency--;
		}

		// Calculate the sum of the column
		int sum = 0;
		for (int i = 0; i < grid.length - 1; i++) {
			if (grid[i][column] != -1)
				sum += grid[i][column];
		}
		consistency++;
		sum += number;

		// If the sum was greater than the actual sum
		if (sum > grid[grid.length - 1][column])
			return false;

		// If the row was the last row and the sum was not equal to the actual sum
		// if (row == grid.length - 2 && sum != grid[grid.length - 1][column])
		// return false;

		return true;
	}
}

class TennerGridGenerator {

	int[][] grid;

	// Constructor to make the Tenner Grid
	public TennerGridGenerator(int size) {

		grid = generateRandomTennerGrid(size);
		RandomIndexsToChange(grid, grid.length);

	}

	// To generate TennerGrid randomly.
	private static int[][] generateRandomTennerGrid(int wantedRow) {

		int row = 0; // Save the current row to access it later.
		int[][] grid = new int[wantedRow][10];
		int[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		shuffleArray(nums);

		for (int i = 0; i < wantedRow - 1; i++) {
			// Assign the fist row of the grid with the shuffled array.
			for (int j = 0; j < 10; j++)
				grid[i][j] = nums[j];
			// Reshuffle it to assign it to the second row.
			shuffleArray(nums);
			row++;
			// Check if the shuffled array satisfies the condition? if not reshuffle it.
			while (!isValid(nums, grid, row))
				shuffleArray(nums);
		}
		// Find the sum of each column.
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < wantedRow - 1; j++)
				sum += grid[j][i];
			// store it in the last row.
			grid[wantedRow - 1][i] = sum;
			sum = 0;
		}

		return grid;
	}

	// To check if the new row dose not breach the rules.
	static boolean isValid(int[] array, int[][] grid, int row) {

		// No need to check the fist row.
		if (row == 0)
			return true;

		for (int c = 0; c < 10; c++)
			// Check cells directly above the current row and diagonally adjacent cells.
			if (grid[row - 1][c] == array[c] || (c > 0 && grid[row - 1][c - 1] == array[c])
					|| (c < 9 && grid[row - 1][c + 1] == array[c]))
				return false;

		return true;
	}

	// To shuffle the arrays (rows).
	static void shuffleArray(int[] array) {

		Random random = new Random();

		for (int i = array.length - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);

			// Swap array[i] with array[index]
			int temp = array[i];
			array[i] = array[index];
			array[index] = temp;
		}
	}

	// Print the grid for visualization.
	public static void printGrid(int[][] grid) {

		int i = 0;
		for (int[] row : grid) {
			i++;
			for (int number : row) {
				if (number == -1)
					System.out.print("    |");
				else
					System.out.printf("%3d |", number);
			}
			System.out.println();

			if (i == grid.length - 1)
				System.out.println("--------------------------------------------------");

		}
		System.out.println();
	}

	// Find random indexes to remove in the grid.
	static int[][] RandomIndexsToChange(int[][] grid, int wantedRows) {

		Random random = new Random();
		int randomNumber = random.nextInt(25) + 10;

		for (int i = 0; i < randomNumber; i++) {
			int row = random.nextInt(wantedRows - 1);
			int column = random.nextInt(10); // chooses random number between 0-9
			grid[row][column] = -1;
		}

		return grid;
	}

}
