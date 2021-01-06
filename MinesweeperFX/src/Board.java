public class Board {
	private Tile[][] data;																			//Two-dimensional Tile-array
	
	public Board(int widthOfBoard, int heightOfBoard) {
		this.data = new Tile[widthOfBoard][heightOfBoard];											//Initializes null object-array 
		
		for (int i = 0; i < heightOfBoard; i++) {
			for (int j = 0; j < widthOfBoard; j++) {
				this.data[j][i] = new Tile();														//Fills object-array
			}
		}
	}
	
	public void populateBoard(int numberOfBombs) {
		int x, y;
		int i = 0;
		while(i < numberOfBombs) {																	//'Pool' of bombs
			x = (int) (Math.random() * this.data.length);											//Coordinates for bomb chosen randomly
			y = (int) (Math.random() * this.data[0].length);
			
			if (!this.data[x][y].isTileBomb()) {													//Bomb placed at coordinates if empty
				this.data[x][y].setBomb();
				i++;																				//Increment
			}
		}
		
		for (i = 0; i < this.data[0].length; i++) {
			for (int j = 0; j < this.data.length; j++) {
				countNeighbours(j, i);																//Counts number of bombs adjacent to tile
			}
		}
	}
	
	public void countNeighbours(int j, int i) {
		int x, y;
		int count = 0;

		for (int iOffset = -1; iOffset <= 1; iOffset++) {											//Adjacency reach as coordinate offset
			y = (i + iOffset) < this.data[0].length ? i + iOffset : -1;								//OoB
			for (int jOffset = -1; jOffset <= 1; jOffset++) {										
				x = (j + jOffset) < this.data.length ? j + jOffset : -1;	
				if (x > -1 && y > -1) {																//Checks if tile-coordinates are valid
					count += this.data[x][y].isTileBomb() ? 1 : 0;									//Increment counter if bomb present
				}
			}
		}
		this.data[j][i].setNeighbours(count);														//Update object
	}
	
	public String toString() {																		//Temporary toString method for troubleshooting
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
