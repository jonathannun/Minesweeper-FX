public class Board {
	public Tile[][] data;																			//Two-dimensional Tile-array
	public boolean GameOver;
	
	private static int tileSize = 20;
	private static int mines = 15;
	private static int width = 60;
	private static int height = 20;
	private static int windowWidth;
	private static int windowHeight;
	private static int flagCounter;
	
	public Board(int widthOfBoard, int heightOfBoard) {
		if(tileSize*width < 550) {
			tileSize = 550/width;
		} else if(height>70) {
			tileSize = 8;
		}
			else if (width > 75) {
			tileSize = 1400/width;
		}
		
		this.data = new Tile[widthOfBoard][heightOfBoard];											//Initializes null object-array 
		
		for (int i = 0; i < heightOfBoard; i++) {
			for (int j = 0; j < widthOfBoard; j++) {
				this.data[j][i] = new Tile();														//Fills object-array
			}
		}
		
		windowWidth = width*tileSize<550 ? 550 : width*tileSize;
		windowHeight = height*tileSize;
	}
	
	public void populateBoard() {
		int x, y;
		int i = 0;
		while(i < mines) {																	//'Pool' of bombs
			x = (int) (Math.random() * this.data.length);											//Coordinates for bomb chosen randomly
			y = (int) (Math.random() * this.data[0].length);
			
			if (!this.data[x][y].isTileBomb()) {													//Bomb placed at coordinates if empty
				this.data[x][y].setBomb();
				i++; 																				//Increment
			}
		}
		
		for (i = 0; i < this.data[0].length; i++) {
			for (int j = 0; j < this.data.length; j++) {
				countNeighbours(j, i);																//Counts number of bombs adjacent to tile
			}
		}
	}
	
	public void revealEmpty(int j, int i) {
		if(this.data[j][i].isTileBomb()) {this.GameOver = true; System.out.println("gameOver");}
		int x, y;
		for (int iOffset = -1; iOffset <= 1; iOffset++) {
			y = (i + iOffset) < this.data[0].length ? i + iOffset : -1;		
			for (int jOffset = -1; jOffset <= 1; jOffset++) {
				x = (j + jOffset) < this.data.length ? j + jOffset : -1;
				if (x > -1 && y > -1 && !this.data[j][i].isTileBomb() && !this.data[x][y].isTileVisible()) {
					if (this.data[j][i].getNeighbours() == 0) {
						if(this.data[x][y].getHasFlag()) {
							continue;
						}
						this.data[x][y].revealTile();
					}
					if (this.data[x][y].getNeighbours() == 0) {
						if(this.data[x][y].getHasFlag()) {
							continue;
						}
						revealEmpty(x, y);
					}
				}
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

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Board.width = width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		Board.height = height;
	}

	public static int getWindowWidth() {
		return windowWidth;
	}

	public static void setWindowWidth(int windowWidth) {
		Board.windowWidth = windowWidth;
	}

	public static int getWindowHeight() {
		return windowHeight;
	}

	public static void setWindowHeight(int windowHeight) {
		Board.windowHeight = windowHeight;
	}
	
	public static int getMines() {
		return mines;
	}

	public static void setMines(int mines) {
		Board.mines = mines;
	}

	public static int getTilesize() {
		return tileSize;
	}

	public static int getFlagCounter() {
		return flagCounter;
	}

	public static void setFlagCounter(int flagCounter) {
		Board.flagCounter = flagCounter;
	}
	
}
