public class Board {
	private Tile[][] data;
	
	public Board(int widthOfBoard, int heightOfBoard) {
		this.data = new Tile[widthOfBoard][heightOfBoard];
		
		for (int i = 0; i < heightOfBoard; i++) {
			for (int j = 0; j < widthOfBoard; j++) {
				this.data[j][i] = new Tile();
			}
		}
	}
	
	public void populateBoard(int numberOfBombs) {
		int x, y;
		int i = 0;
		while(i < numberOfBombs) {
			x = (int) (Math.random() * this.data.length);
			y = (int) (Math.random() * this.data[0].length);
			
			if (!this.data[x][y].isTileBomb()) {
				this.data[x][y].setBomb();
				i++;
			}
		}
		
		for (i = 0; i < this.data[0].length; i++) {
			for (int j = 0; j < this.data.length; j++) {
				countNeighbours(j, i);
			}
		}
	}
	
	public void countNeighbours(int j, int i) {
		int x, y;
		int count = 0;

		for (int iOffset = -1; iOffset <= 1; iOffset++) {
			y = (i + iOffset) < this.data[0].length ? i + iOffset : -1;
			for (int jOffset = -1; jOffset <= 1; jOffset++) {
				x = (j + jOffset) < this.data.length ? j + jOffset : -1;	
				if (x > -1 && y > -1) {
					count += this.data[x][y].isTileBomb() ? 1 : 0;
				}
			}
		}
		this.data[j][i].setNeighbours(count);
	}
	
	public String toString() {
		String out = "";
		for (int i = 0; i < this.data[0].length; i++) {
			for (int j = 0; j < this.data.length; j++) {
				out += this.data[j][i].isTileBomb() ? "X" : this.data[j][i].getNeighbours();
			}
			out += "\n";
		}
		return out;
	}
}
