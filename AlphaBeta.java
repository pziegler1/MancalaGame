package mancala;

public class AlphaBeta
{
    public static double value(Board board, int depth, double alfa, double beta, Player player)
    {
    	//System.out.println("Enter alphabeta d = " + depth + " a = " + alfa + " b = " + beta + " P = " + player);
        double value = 0.0;
        Player opponent = player == Player.MAX ? Player.MIN : Player.MAX;
        if (depth == 0 || board.isGameOver()) {
            value = board.heuristicValue();
            //System.out.println("Return value = " + value);
        }
        else {
        	if (player == Player.MAX) {
                for (int col = 1; col < Board.NR_COLS-1; col++) {
                    if (board.canMove(Player.MAX, col)) {
                        Board nextPos = board.makeMove(Player.MAX, col);
                        double thisVal = value(nextPos, depth - 1, alfa, beta, opponent);
                        if (thisVal > alfa)
                            alfa = thisVal;
                        if (beta <= alfa)
                             break;
                    }
                }
                value = alfa;
            }
            else { // player == Player.MIN
                for (int col = 1; col < Board.NR_COLS-1; col++) {
                    if (board.canMove(Player.MIN, col)) {
                        Board nextPos = board.makeMove(Player.MIN, col);
                        double thisVal = value(nextPos, depth - 1, alfa, beta, opponent);
                        if (thisVal < beta)
                            beta = thisVal;
                        if (beta <= alfa)
                             break;
                    }
                }
                value = beta;
            }
        }
        return value;
    }
}
