package hdingari.sudoku;

import hdingari.sudoku.SudokuMain;

public class SudokuSolver {
	
	private int[][] values;
	
	//public enum Day
	//private int[][][] flags;
	
	
	public SudokuSolver(int[][] v)
	{
		values = new int[9][9];
		//flags = new int[9][9][9];
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
			{
				//for(int k=0; k<9; k++)
					//flags[i][j][k] = 0; // initialize to show these are all untried.
				values[i][j] = v[i][j];
			}
	
				
		
	} 
	
	public int valueOf(int i, int j)
	{
		if(i> -1 && i < 9 && j > -1 && j< 9 )
			return values[i][j];
		return(0);
	}
	
	public void printIt()
	{
		SudokuMain.PrintSudokuPuzzle(values);
	}
	
	public boolean checkValidity(int r, int c)
	{
		 
		for(int i=0; i<9; i++)
		{
			if ( (values[r][i] == values[r][c]) && (i!=c) )
			{
				System.out.println("DEBUG In checkValidity(): Rowcheck failed, Found a duplicate at [" + r + "][" + i + "] = " + values[r][c] );
				return false; // found duplicate in row;
			}
			if ( (values[i][c] == values[r][c]) && (i!=r) )
			{
				System.out.println("DEBUG In checkValidity(): Col check failed, Found a duplicate at [" + i + "][" + c + "] = " + values[r][c] );
				return false; // found duplicate in column.
			}
		}
		//find its grid;
		int gr = r/3;
		int gc = c/3;
		//  if gr=0 then sr = 0
		//  if gr=1 then sr = 3
		//  if gr=2 then sr = 6
		
		for(int i=gr*3; i<gr*3+3; i++)
			for(int j=gc*3; j<gc*3+3; j++)
				if( ((i!=r) || (j!=c)) && (values[r][c] == values[i][j]) )
				{
					System.out.println("DEBUG In checkValidity(): gridcheck failure, Found a duplicate at [" + i + "][" + j + "] = " + values[i][j] );
					return false;
				}
		
		return true;
	}
	
	public boolean solveIt()  // returns 0 when it is solved. 
	{
		boolean isValid = true;
		
		//scan for an empty cell
		printIt();
		System.out.print("DEBUG In SolveIt: Scanning for Empty Cell ...");
		for(int r=0; r<9; r++)
			for(int c=0; c<9; c++)
			{
				if(values[r][c] == 0) 
				{
					//found empty cell, now try a number from 1 to 9
					System.out.println("...found ["+ r + "][" + c + "]");
					for(int v=1; v<10; v++)
					{
						// try value 'v'
						values[r][c] = v;
						isValid = checkValidity(r,c); 
						if(isValid)
						{
							System.out.println("DEBUG In SolveIt() trying [" + r + "][" + c + "] = " + v);
							if (solveIt())
								return true; // solution found.
							else 
							{
								System.out.println("DEBUG In SolveIt() backtracking from [" + r + "][" + c + "] = " + v);
							}
						}
					}
					//if we reached here, means, we have a dead end.
					System.out.println("DEBUG In SolveIt() reached dead end at [" + r + "][" + c + "] , returning false");
					values[r][c] = 0; // reset this value to zero.
					return(false);
				}
			} 
		System.out.println("DEBUG In SolveIt, returning " + isValid);
		return(isValid); 
	}

}
