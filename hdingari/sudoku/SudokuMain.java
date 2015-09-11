package hdingari.sudoku;

import java.io.*;
import hdingari.sudoku.SudokuSolver;
import java.nio.*;
import java.util.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SudokuMain {

	
	private static String stdinpath = "sudoku.input";
	private static String stdoutpath = "sudoku.output";
	
	private String inputFileName = null;
	private String outputFileName = null;
	private int[][] inputValues; // Sudoku puzzle board.
	private int inputRows;
	private int inputCols;
	
	private int writeOutputFile(SudokuSolver s)
	{
		try{
			PrintWriter writer = new PrintWriter(outputFileName);
			writer.println("9 9");
			for(int i=0; i<9; i++)
			{
				
				for(int j=0; j<9; j++)
				{
					writer.print(s.valueOf(i, j));
					if(j<8)
						writer.print(" ");
				}
				writer.println();
			}
			writer.close();
		
		}catch (Exception e)
		{
			System.out.println("ERROR: In writeOutputFile(): Exception " + e.getMessage());
		}
		return(0);
	}
	private int readInputFile()
	{
		List<Integer> inputNumbers = new ArrayList<Integer>();
		
		// read from input file "inputFileName", 
		// expecting to find initial values in the puzzle
		// store results into an array "inputValues"
		// input File format is  <rows> <cols> <value r1 c1> <value r1 c2> ....
		if(inputFileName == null )
		{
			System.out.println("ERROR: Input file is null");
			return(-1); // return failure; code = -1;
		}
		
		System.out.println("DEBUG: Reading from Inputfile : " + inputFileName);
		
		
		try{
			System.out.println("DEBUG: Reading ...");
			for(String line: Files.readAllLines(Paths.get(inputFileName), Charset.defaultCharset())) {
				for(String part : line.split("\\s+")) {
					Integer i = Integer.valueOf(part);
					inputNumbers.add(i);
					System.out.print(i);
					System.out.print(" ");
				}
				System.out.println();
			}
		}catch (Exception e)
		{
			System.out.println("ERROR: Exception ->" + e.getMessage());
			return(-1);
		}
		
		// Now that we have read the values into a list, lets create the sudoku array.
		inputRows = inputNumbers.get(0);
		inputCols = inputNumbers.get(1);
		
		System.out.println("DEBUG: Rows = " + inputRows + " Cols = "+ inputCols);
		System.out.println("DEBUG: List Size = " + inputNumbers.size());
		if (inputNumbers.size() != (inputRows * inputCols + 2))
		//{
		//	System.out.println("DEBUG: input file format looks right with right number of numbers");
		//}else
		{
			System.out.println("ERROR: Input file format not correct, expecting <rows> <cols> <r1c1> <r2c2> ...");
			return(-2); // return failure; code = -2
		}
		
		inputValues = new int[inputRows][inputCols];
		
		int k=2;
		for(int i=0; i<inputRows; i++)
			for (int j=0; j<inputCols; j++)
			{
				inputValues[i][j] =  inputNumbers.get(k);
				k++;
			}
		
		//inputValues is now set with right values.
		
		return(0); // return success
	}
	
	public static void PrintSudokuPuzzle(int v[][])
	{
		
		System.out.println("----------------------------");
		System.out.println("* * * Sudoku Puzzle * * *");
		int rows = v.length;
		
		if(rows != 9) 
		{
			System.out.println("ERROR: Can't print sudoku puzzle, need 9 rows but they are :" + rows);
			return;
		}
		System.out.println("         0 1 2  3 4 5  6 7 8");
		System.out.println("      ------------------------");
		for(int r=0; r<rows; r++)
		{
			int cols = v[0].length;
			if(cols !=9)
			{
				System.out.println("ERROR: Can't print sudoku puzzle, need 9 cols but they are :" + cols);
				return;
			}
			if( r==3|| r==6)
				System.out.println("        _____________________");
			System.out.print("[" + (r) + "] ->  ");
			for(int c=0; c<cols; c++)
			{
				if(c==0 || c==3 || c==6)
					System.out.print("|");
				System.out.print(v[r][c]);
				System.out.print(" ");
			}
			System.out.println();
				
		}
		
		System.out.println("______________________________");
	
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Reads a filename from the argument and creates a sudoku object from it.
		
		SudokuMain sm = new SudokuMain();
		
		if(args == null || args.length == 0)
		{
		    System.out.println("ERROR: No arguments passed");
		    System.out.println("DEBUG: setting standard file location as: " + stdinpath);
		    sm.inputFileName = stdinpath;
		} else {
			System.out.println("DEBUG: Input filename = " + args[0]);
			sm.inputFileName = args[0];
		}
		sm.outputFileName = stdoutpath;
		sm.readInputFile();
		SudokuMain.PrintSudokuPuzzle(sm.inputValues);
		
		SudokuSolver solver = new SudokuSolver(sm.inputValues);
		
		if(solver.solveIt())
		{
			solver.printIt();
			sm.writeOutputFile(solver);
		}
		else
		{
			System.out.println(" ERROR : Can't solve this, sorry! ");
		}
		
		System.out.println("Good Bye !\n * * * \n\n");
	}
	
	
	
}
