/*
Student name: Katie Wu
PennID: 62427079
Partner(s): N/A
Resources used: Course material, W3 schools
Same code submission: No
Statement: I worked alone on this assignment.
*/ 

package battleship;

import java.util.Scanner;

/**
 * Main method that structures how the Battleship Game works
 */
public class BattleshipGame {

	public static void main(String[] args) {
		
		//creates a new instance of the Battleship game
		BattleshipGame game = new BattleshipGame();
		game.playGame();
		
	}

	/**
	 * Main game loop that goes through each turn until all ships are sunk
	 */
	private void playGame() {
		
		//creates a new Ocean then computer places all ships randomly
		Ocean ocean = new Ocean();
		ocean.placeAllShipsRandomly();
		
		//welcomes the user then prints an empty ocean grid
		System.out.println("Welcome to Battleship!");
		ocean.print();
		
		//continues to get the user's row and column input until the game is over
		while (!ocean.isGameOver()) {
			int row = getInput("Enter row (0-9): ");
			int column = getInput("Enter column (0-9): ");
		
			//lets the user know they've hit a ship
			if (ocean.shootAt(row, column)) {
				System.out.println("Hit!");
				
			//lets the user know they missed
			} else {
				System.out.println("Miss!");
			}
			
			//prints the updated ocean grid
			ocean.print();				
		}
		
		//prints the final score when the game is over
		printFinalScore(ocean);
		
	}
	
	//helper method to get valid user input for row or column
	private int getInput(String string) {
		
		//initializes the scanner and input variable to -1
		Scanner scanner = new Scanner(System.in);
		int input = -1;
		
		//prompts the user to enter a row or column value
		while (input < 0 || input > 9) {
			System.out.print(string);
			
			//checks if the input is an integer
			if (scanner.hasNextInt()) {
				input = scanner.nextInt();
				
				//prints an error message if input is an integer outside of the valid range (0 to 9)
				if (input < 0 || input > 9) {
					System.out.println("Invalid input. Please enter a number between 0 and 9.");
				}
				
			} else {
				
				//prints an error message if user enters anything other than an integer
				System.out.println("Invalid input. Please enter an integer.");
				
				//clears the invalid input from the scanner
				scanner.next();
			}
		}
		
		//returns user's input if it's valid
		return input;
		
	}
	
	//helper method to print final scores once the game is over
	private void printFinalScore(Ocean ocean) {
		
		//prints a message indicating the game is over
		System.out.println("\nGame Over!");
		
		//prints the total number of shots fired
		System.out.println("Total shots fired: " + ocean.getShotsFired());
		
		//prints the total number of hits recorded
		System.out.println("Total hits recorded: " + ocean.getHitCount());
		
		//prints the total number of ships sunk
		System.out.println("Total ships sunk: " + ocean.getShipsSunk());
		
	}
	
}