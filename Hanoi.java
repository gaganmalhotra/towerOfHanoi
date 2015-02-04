/* 
 * Hanoi.java 
 * 
 * Version: 0.1
 * 
 */

import java.util.InputMismatchException;
import java.util.Scanner;

/** 
 * Tower of Hanoi implementation.
 * 
 * @author Gagandeep
 */

public class Hanoi {

	// Instance variable declaration
	private static int poleStart;
	private static int poleOver;
	private static int poleEnd;
	private static int n; // Number of discs

	
	/**
	 * main()
	 * This method takes the input from user and invokes the intializeAndExecute method
	 *
	 * @param       args    				 String array
	 *
	 * @return             					 void
	 *
	 * @exception   InputMismatchException   When the input is not of type defined
	 * @exception   NumberFormatException    Format of number doesn't match
	 */
	public static void main(String[] args) {
		while (true) {
			try {
				Scanner in = new Scanner(System.in);
				System.out
						.println("Enter the number of discs for Tower of Hanoi");
				n = in.nextInt();
				System.out.println("You entered " + n + " number of discs");

				while(true){
					try{
						
						int[] val = new int[3];
						int i = 0;

						System.out
								.println("Please enter the StartPole>PoleOver>EndPole in the same sequence from 0,1,2");
						while (i <= 2) {
							val[i] = in.nextInt();
							i++;
						}
						poleStart = val[0];
						poleOver = val[1];
						poleEnd = val[2];
						// handling of inputs
						if(val[0]==val[1]  || val[1]==val[2] || val[0]==val[2]){
							throw new Exception( "Any two values cannot be same" );
						}else{
							for(int j=0; j<3; j++){
								if(val[j]<0 || val[j]>2){
									throw new Exception("Range of the values should be between 0 and 2");
								}
							}
						}
						break;
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				
				in.close();
				break;
			} catch (InputMismatchException | NumberFormatException e) {
				System.out.println("Please re-enter a integer value");
			}
		}
		initializeAndExecute(n, poleStart, poleOver, poleEnd);
	}

	/**
	 * initializeAndExecute()
	 * This method initializes the start array with filling up the discs in ascending order
	 * and further calls the move method where recursively the implementation is done.
	 *
	 * @param       n    		int
	 * @param       startPole   int
	 * @param       midPole     int
	 * @param       endPole     int
	 * 
	 * @return              	void
	 */
	private static void initializeAndExecute(int n, int startPole, int midPole,
			int endPole) {
		int towerArray[][] = new int[n][3];
		for (int i = 0; i < n; i++) {
			towerArray[i][startPole] = i + 1;
		}

		printPuzzle(n, towerArray); 
		move(n, startPole+1, endPole+1, towerArray); //

	}

	/**
	 * move()
	 * This method is where the recursion is implemented, it goes iteratively until value of n reaches zero
	 *
	 * @param       n    		int
	 * @param       startPole   int
	 * @param       endPole     int
	 * @param       towerArray  int[][]
	 * 
	 * @return              	void
	 */
	public static void move(int n, int startPole, int endPole,
			int[][] towerArray) {
		if (n == 0) {
			return;
		}
		int intermediatePole = 6 - startPole - endPole; // midpole calculation
		move(n - 1, startPole, intermediatePole, towerArray); //recursively calling the move function
		System.out.println("Move " + n + " from " + startPole + " to "
				+ endPole);

		// movement of discs in the array
		towerArray = moveDiscs(startPole, endPole, towerArray);

		//changing the pole values and calling back move recursively
		move(n - 1, intermediatePole, endPole, towerArray);
	}
	
	/**
	 * moveDiscs()
	 * This method is used for movement of discs inside the array
	 *
	 * @param       startPole   int
	 * @param       endPole     int
	 * @param       towerArray  int[][]
	 * 
	 * @return               	int[][]
	 */
	private static int[][] moveDiscs(int startPole, int endPole,
			int[][] towerArray) {
		// Decrementing the values of poles to traverse in the array
		startPole--;
		endPole--;
		
		// finding the first nonzero value in any row of a particular column as passed
		int p = findFirstNonZeroPointer(startPole, towerArray); 
		// if there are no non-zero rows in column passed then mark the zeroth row to move
		if (p > towerArray.length) {
			p = 0;
		}

		int q = findFirstNonZeroPointer(endPole, towerArray);
		q--; // decrementing so as to get the array pointer where a disc can be placed

		towerArray[q][endPole] = towerArray[p][startPole];
		towerArray[p][startPole] = 0; // resetting the value

		printPuzzle(n, towerArray);// printing the tower array after every step

		return towerArray;
	}

	/**
	 * findFirstNonZeroPointer()
	 * This method is used to find the row inside a particular column passed as an argument, which holds a non-zero value
	 *
	 * @param       column   int
	 * @param       array  	 int[][]
	 * 
	 * @return               int
	 */
	public static int findFirstNonZeroPointer(int column, int array[][]) {
		if (array[0][column] != 0) {
			return array.length + 1; // identify that there are non zero elements
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i][column] != 0) {
				return i;
			}
		}
		return array.length; // if all the elements are zero then return the length
	}

	/**
	 * printPuzzle()
	 * 
	 * Outputs the current state of the Tower of Hanoi puzzle
	 */
	public static void printPuzzle(int n, int[][] towerArray) {
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < 3; col++) {
				System.out.print(String.format("%02d", towerArray[row][col])
						+ "|");
			}
			System.out.println();
		}
		System.out.println("---------");
		System.out.println(" 0  1  2");
	}

}
