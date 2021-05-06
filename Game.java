package mancala;

import java.util.Scanner;

public class Game {
	
	final static int DEPTH = 4;

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);
        Board gameBoard = new Board();
        boolean gameOver = false;
        gameBoard.showBoard();

        //extra credit: player can go first
        if(firstPlayer(keyboard) == 1) {
        	gameBoard = userMove(gameBoard, keyboard);
        }
        
        while (!gameOver)
        {
            gameBoard = computerMove(gameBoard);
        	System.out.println();
            if (gameBoard.isWin(Player.MAX))
            {
                System.out.println("\n Computer wins");
                gameOver = true;
            }            
            else            
            	gameBoard = userMove(gameBoard, keyboard);
            
            if (gameBoard.isWin(Player.MIN))
            {
                System.out.println("\n You win!");
                gameOver = true;
            }
	    }		
        keyboard.close();
	}
	
	private static Board computerMove(Board gameBoard) {
		System.out.println("\nI am thinking about my move now");
        double highVal = -1.0;
        int bestMove = gameBoard.canMove(Player.MAX, 1) ? 1 : (gameBoard.canMove(Player.MAX, 2) ? 2 : (gameBoard.canMove(Player.MAX, 3) ? 3 : (gameBoard.canMove(Player.MAX, 4) ? 4 : (gameBoard.canMove(Player.MAX, 5) ? 5 : 6))));
        double alfa = -1.0;
        double beta = 1.0;
        // check each move
        for (int col = 1; col < Board.NR_COLS-1; col++)
        {
            if (gameBoard.canMove(Player.MAX, col))
            {
                Board nextPos = gameBoard.makeMove(Player.MAX, col);
                double thisVal = AlphaBeta.value(nextPos, DEPTH-1, alfa, beta, Player.MIN);
                //System.out.println("col = " + col + " thisVal = " + thisVal);
                if (thisVal > highVal)
                {
                    bestMove = col;
                    highVal = thisVal;
                }
            }
        }

        System.out.println("My move is " + (bestMove));
        gameBoard = gameBoard.makeMove(Player.MAX, bestMove);
        gameBoard.showBoard();
		return gameBoard;
	}
	
	private static Board userMove(Board gameBoard, Scanner keyboard) {        
    	System.out.print("Your move: Select column 1 - 6: ");
        int theirMove = keyboard.nextInt();
        while(theirMove < 1 || theirMove > 6 || !gameBoard.canMove(Player.MIN, theirMove)) {
        	System.out.print("Select a column 1 - 6 that has a number greater than 0: ");
            theirMove = keyboard.nextInt();
        }
        gameBoard = gameBoard.makeMove(Player.MIN, theirMove);
        gameBoard.showBoard();        
        return gameBoard;
    }
	
	private static int firstPlayer(Scanner keyboard) {
    	System.out.print("Do you want to go first or second? (Enter 1 or 2) ");
        int theirMove = keyboard.nextInt();
        while(theirMove != 1 && theirMove != 2) {
        	System.out.print("Enter 1 or 2: ");
            theirMove = keyboard.nextInt();
        }
        return theirMove;        
    }

}
