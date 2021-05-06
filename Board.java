package mancala;

public class Board {
	public final static int NR_COLS = 8;

	private Cell[][] board = new Cell[2][NR_COLS];

	public Board() {
		board[0][0] = new MancalaCell();
		board[1][NR_COLS - 1] = new MancalaCell();
		for (int row = 0; row < 2; row++) {
			for (int col = 1; col < NR_COLS - 1; col++) {
				board[row][col] = new Cell();
			}
		}
	}

	public Board(Cell[][] other) {
		for (int row = 1; row >= 0; --row) {
			for (int col = 0; col < NR_COLS; ++col) {
				if (other[row][col] != null) {
					board[row][col] = new Cell(other[row][col]);
				}
			}
		}
	}
	
	public Board(Board other) {
		for (int row = 1; row >= 0; --row) {
			for (int col = 0; col < NR_COLS; ++col) {
				if (other.board[row][col] != null) {
					board[row][col] = new Cell(other.board[row][col]);
				}
			}
		}
	}

	public void showBoard() {
		System.out.println();
		for (int col = 0; col < NR_COLS; col++) {
			System.out.print("--");
		}
		System.out.println("---");
		
		System.out.printf("%2d |", board[0][0].getNum());
		for (int col = 1; col < NR_COLS - 1; col++) {
			System.out.print(board[0][col].getNum() + "|");
		}
		System.out.println();

		System.out.print("   ");
		for (int col = 1; col < NR_COLS - 1; col++) {
			System.out.print("--");
		}
		System.out.println("-");

		System.out.print("   |");
		for (int col = 1; col < NR_COLS - 1; col++) {
			System.out.print(board[1][col].getNum() + "|");
		}
		System.out.print(" " + board[1][NR_COLS - 1].getNum());
		System.out.println();

		for (int col = 0; col < NR_COLS; col++) {
			System.out.print("--");
		}
		System.out.println("---");

		System.out.print("   ");
		for (int col = 0; col < NR_COLS - 2; col++) {
			System.out.printf("%2d", col + 1);
		}
		System.out.println();
	}

	public Board makeMove(Player player, int column) {
		Board newBoard = new Board(board);
		int row = player == Player.MAX ? 0 : 1;
		int toDistribute = newBoard.board[row][column].removeNum();
		while(toDistribute > 0) {
			if(row == 0) {
				if(column > 1 || (player == Player.MAX && column == 1)) 
					column--;
				else {
					row = 1;
					if(player == Player.MAX) {
						column = 1;
					}
				}
			}
			else {
				if(column < NR_COLS-1 || (player == Player.MIN && column == NR_COLS-2)) 
					column++;
				else {
					row = 0;
					if(player == Player.MIN) {
						column = NR_COLS-2;
					}
				}
			}
			newBoard.board[row][column].addNum();
			toDistribute--;
		}
		if(newBoard.board[row][column].getNum() == 1) {
			if(player == Player.MAX && row == 0 && column != 0 && newBoard.board[1][column].getNum() != 0) {
				newBoard.board[0][0].addNums(newBoard.board[0][column].removeNum());
				newBoard.board[0][0].addNums(newBoard.board[1][column].removeNum());
			}
			else if(player == Player.MIN && row == 1 && column != NR_COLS - 1 && newBoard.board[0][column].getNum() != 0) {
				newBoard.board[1][NR_COLS - 1].addNums(newBoard.board[0][column].removeNum());
				newBoard.board[1][NR_COLS - 1].addNums(newBoard.board[1][column].removeNum());
			}
		}
		return newBoard;
	}

	public boolean canMove(Player player, int col) {
		int row = player == Player.MAX ? 0 : 1;
		return board[row][col].getNum() != 0;
	}

	public boolean isWin(Player player) {
		boolean win = false;
		if(isGameOver()) {
			Cell comp = board[0][0];
			Cell user = board[1][NR_COLS-1];
			if(player == Player.MAX) {
				if(comp.getNum() > user.getNum()) {
					win = true;
				}
			}
			else {
				if(user.getNum() > comp.getNum()) {
					win = true;
				}
			}
		}
		return win;
	}

	public boolean isGameOver() {
		boolean compOver = true;
		for (int col = 1; col < NR_COLS - 1; col++) {
			if (board[0][col].getNum() != 0) {
				compOver = false;
				break;
			}
		}
		boolean userOver = true;
		for (int col = 1; col < NR_COLS - 1; col++) {
			if (board[1][col].getNum() != 0) {
				userOver = false;
				break;
			}
		}
		return compOver || userOver;
	}

	public double heuristicValue() {
		double val;
		if (isWin(Player.MAX)) {
			val = 1.0;
		} else if (isWin(Player.MIN)) {
			val = -1.0;
		} else {
			val = heuristicValueCalculator();
		}
		return val;
	}

	private double heuristicValueCalculator() {
		int comp = board[0][0].getNum();
		int user = board[1][NR_COLS-1].getNum();
		return (comp - user) * .04;
	}

}
