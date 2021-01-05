public class Board {
	private Tile[][] data;
	
	public Board(int widthOfBoard, int heightOfBoard) {
		this.data = new Tile[widthOfBoard][heightOfBoard];
		
		for (int i = 0; i < heightOfBoard; i++) {
			for (int j = 0; j < widthOfBoard; j++) {
				this.data[j][i] = new Tile(j, i);
			}
		}
	}
	
	public void populateBoard(int numberOfBombs) {
		int x, y;
		for (int i = 0; i <= numberOfBombs; i++) {
			x = (int) (Math.random() * this.data.length);
			y = (int) (Math.random() * this.data[0].length);
			
			if (this.data[x][y].bombOnTile()) {
				i--;
			} else {
				this.data[x][y].setBomb();
			}
		}
	}
	
	public String toString() {
		String out = "";
		for (int i = 0; i < this.data[0].length; i++) {
			for (int j = 0; j < this.data.length; j++) {
				out += this.data[j][i].bombOnTile() ? "X" : "-";
			}
			out += "\n";
		}
		return out;
	}
}
