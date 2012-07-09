package allan.pichardo;

import java.util.Arrays;

public class Solution {
	
	private int[][] solution;
	
	public Solution(){
		solution = new int[9][9];
		generateBaseSolution(solution);
		randomRotate(solution);
		scrambleCols(solution);
		scrambleRows(solution);
	}
	
	public int[][] getSolution(){
		return solution;
	}
	
	private void generateBaseSolution(int[][] grid){
		/*this method generates the basic
		 * solution. It should be called
		 * before calling the shuffler
		 */
		int[] basic = {1,2,3,4,5,6,7,8,9};
		
		for(int row = 0;row < 9;++row){
			/*if row is not divisible
			 * by 3, then push array
			 * to (row*3);
			 * else reset and
			 * push to (row/3)
			 */
			if(row%3 != 0){
				if(row < 3)
					push(basic,(row*3));
				if(row > 3 && row < 6)
					push(basic,((row*3)-8));
				if(row > 6)
					push(basic,((row*3)-16));
			}
			else{
				Arrays.sort(basic);
				push(basic,(row/3));
			}
			for(int col = 0;col < 9;++col){
				grid[row][col] = basic[col];
			}
		}
	}
	
	private void push(int[] a, int spaces){
		/*"pushes" an array
		 * a number of spaces to the
		 * right.
		 */
		int[] temp = new int[a.length];
		int last = 0;
		int first = 1;
		
		for(int i=spaces;i < 9;++i){
			temp[i] = first++;
			if(i==8) last = first;
		}
		for(int i=0;i < spaces; ++i){
			temp[i] = last++;
		}
		for(int i=0;i<a.length;++i){
			a[i] = temp[i];
		}
	}
	
	public String printGrid(){
		String grid = "\n";
		
		for(int i=0; i < 9; ++i){
			for(int j=0;j<9;++j){
				//System.out.print(solution[i][j] + " ");
				grid += solution[i][j] + " ";
				if(j ==8) grid += "\n";
			}
		}
		return grid;
	}
	
	private int getSubGroup(int index){
		/*takes an index and
		 * returns the subrow
		 * or subcolumn
		 * that it is in 1-3
		 */
		int subrow = 0;
		
		if(index < 3)
			subrow = 1;
		if(index < 6 && index > 2)
			subrow = 2;
		if(index > 5)
			subrow = 3;
		return subrow;
	}
	
	private void swapRows(int[][] a, int row0, int row1) {
		int cols = a[0].length;
		for (int col=0; col<cols; col++)
			swap(a, row0, col, row1, col);
	}
	
	private void swapCols(int[][] a, int col0, int col1) {
	    int rows = a.length;
	    for (int row=0; row<rows; row++)
	    	swap(a, row, col0, row, col1);
	}
	
	private void swap(int[][] a, int i0, int j0, int i1, int j1) {
	    int temp = a[i0][j0];
	    a[i0][j0] = a[i1][j1];
	    a[i1][j1] = temp;
	}
	
	private int randomBetween(int low, int high){
		return low + (int)(Math.random() * (high-low+1));
	}
	
	private void scrambleRows(int[][] grid){
		/* scramble random sub rows
		 * a random number of times
		 */
		int depth = (int)(Math.random() * 50);
		int i = 0;
		do{
			int startRow = (int)(Math.random() * 9);
			int row = 0;
			
			switch(getSubGroup(startRow)){
			case 1:
				row = randomBetween(0,2);
				swapRows(grid,startRow,row);
				break;
			case 2:
				row = randomBetween(3,5);
				swapRows(grid,startRow,row);
				break;
			case 3:
				row = randomBetween(6,8);
				swapRows(grid,startRow,row);
				break;
			}
			++i;
		}while(i < depth);
	}
	
	private void scrambleCols(int[][] grid){
		/* scramble random sub cols
		 * a random number of times
		 */
		int depth = (int)(Math.random() * 50);
		int i = 0;
		do{
			int startCol = (int)(Math.random() * 9);
			int col = 0;
			
			switch(getSubGroup(startCol)){
			case 1:
				col = randomBetween(0,2);
				swapCols(grid,startCol,col);
				break;
			case 2:
				col = randomBetween(3,5);
				swapCols(grid,startCol,col);
				break;
			case 3:
				col = randomBetween(6,8);
				swapCols(grid,startCol,col);
				break;
			}
			++i;
		}while(i < depth);
	}
	
	private void rotateGrid(int[][] grid)
	{
		/*rotate the grid 90 degrees
		 * to add even more randomization
		 */
		
		int[][] temp = new int[9][9];
	    for (int i = 0; i < 9; ++i) {
	        for (int j = 0; j < 9; ++j) {
	            temp[i][j] = grid[9 - j - 1][i];
	        }
	    }
	    for(int i=0;i<9;++i)
	    	for(int j=0;j<9;++j)
	    		grid[i][j] = temp[i][j];
	}
	
	private void randomRotate(int[][] grid){
		/*rotate grid a random number
		 * of times
		 */
		int depth = (int)(Math.random() * 4);
		for(int i=0;i<depth;++i)
			rotateGrid(grid);
	}
}
