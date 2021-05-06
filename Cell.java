package mancala;

public class Cell {
	int num;
	
	public Cell() {
		num = 4;
	}
	
	public Cell(Cell other) {
		num = other.num;
	}
	
	public int removeNum() {
		int val = num;
		num = 0;
		return val;
	}

	public int getNum() {
		return num;
	}

	public void addNum() {
		num++;
	}
	
	public void addNums(int add) {
		num += add;
	}
}
