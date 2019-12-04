package gamefield;

public class Position {
	public int row;
	public int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Position
				&& ((Position)obj).row == this.row
				&& ((Position)obj).col == this.col;
	}
	
	public Position add(Position delta) {
		return new Position(this.row + delta.row, this.col + delta.col);
	}
}
