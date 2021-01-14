import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Model {
	private Tile[][] board;
	private IntegerProperty w;
	private IntegerProperty h;
	private int bombCount;

	class Tile {
		private Boolean hasBomb;
		private BooleanProperty hasFlag = new SimpleBooleanProperty();
		private BooleanProperty isVisible = new SimpleBooleanProperty();
		private IntegerProperty neighbours = new SimpleIntegerProperty();
		
		public Tile() {																				//Tile constructor
			this.hasBomb = false;
			this.hasFlag.set(false);																//Sets BooleanProperty value
			this.isVisible.set(false);																//^
			
			this.neighbours.set(0);;
		}
		
		public Boolean bombOnTile() {																//Returns Boolean.
			return this.hasBomb;
		}
		
		public BooleanProperty flagOnTile() {														//Returns BooleanProperty.
			return this.hasFlag;
		}
		
		public BooleanProperty tileVisible() {														//Returns BooleanProperty.
			return this.isVisible;
		}
		
		public IntegerProperty getNeighbours() {													//Returns integer.
			return this.neighbours;
		}
		
		public void setBomb() {																		//Places bomb on tile
			this.hasBomb = true;
		}
		
		public void toggleFlag() {																	//Toggles flag placement on tile.
			this.hasFlag.set(!this.hasFlag.get());
		}
		
		public void setNeighbours(int neighbours) {													//Sets counted number of adjacent bombs to tile.
			this.neighbours.set(neighbours);
		}
		
		public void setVisible() {																	//Sets BooleanProperty to true.
			this.isVisible.set(true);
		}
	}
	
	public Model(int w, int h, int bombCount) {														//Model constructor
		this.w = new SimpleIntegerProperty();
		this.h = new SimpleIntegerProperty();
		
		this.w.set(w);
		this.h.set(h);
		this.bombCount = bombCount;
		
		this.board = new Tile[this.w.intValue()][this.h.intValue()];								//Construct board.
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				this.board[j][i] = new Tile();														//Fills object-array.
			}
		}
		
		populateBoard();																			//Populates board with bombs and counts adjacent bombs of tiles.
	}
	
	public void populateBoard() {
		int x, y;
		int i = 0;
		
		while(i < this.bombCount) {																	//'Pool' of bombs.
			x = (int) (Math.random() * this.w.intValue());											//Coordinates for bomb chosen randomly.
			y = (int) (Math.random() * this.h.intValue());
			
			if (!this.board[x][y].bombOnTile()) {													//Bomb placed at coordinates if empty.
				this.board[x][y].setBomb();
				i++;																				//Increment.
			}
		}
		
		for (i = 0; i < this.h.intValue(); i++) {
			for (int j = 0; j < this.w.intValue(); j++) {
				countNeighbours(j, i);																//Counts number of bombs adjacent to tile.
			}
		}
	}
	
	private void countNeighbours(int j, int i) {
		int x, y;
		int count = 0;
		
		for (int iOffset = -1; iOffset <= 1; iOffset++) {											//Adjacency reach as coordinate offset.
			y = (i + iOffset) < this.h.intValue() ? (i + iOffset) : -1;								//OoB-handling.
			
			for (int jOffset = -1; jOffset <= 1; jOffset++) {										
				x = (j + jOffset) < this.w.intValue() ? (j + jOffset) : -1;
				
				if ((x >= 0) && (y >= 0)) {															//Checks if tile-coordinates are valid.
					count += this.board[x][y].bombOnTile() ? 1 : 0;									//Increment if bomb found on tile.
				}
			}
		}
		
		this.board[j][i].setNeighbours(count);														//Set number of adjacent bombs to tile.
	}
	
	public void reveal(int j, int i) {
		int x, y;
		for (int iOffset = -1; iOffset <= 1; iOffset++) {
			y = (i + iOffset) < this.h.intValue() ? (i + iOffset) : -1;
			
			for (int jOffset = -1; jOffset <= 1; jOffset++) {
				x = (j + jOffset) < this.w.intValue() ? (j + jOffset) : -1;
				
				if ((x >= 0) && (y >= 0)) {															//Checks if tile-coordinates are valid.
					if (this.board[x][y].flagOnTile().get()) {
						continue;
					}
					if (this.board[x][y].getNeighbours().intValue() == 0 && !this.board[x][y].tileVisible().get()) {
						this.board[x][y].setVisible();
						reveal(x, y);
					}
					if (this.board[j][i].getNeighbours().intValue() == 0) {
						this.board[x][y].setVisible();
					}

				}
			}
		}
	}

	public IntegerProperty getW() {																	//Returns first dimension of the board.
		return this.w;
	}
	
	public IntegerProperty getH() {																	//Returns second dimension of the board.
		return this.h;
	}
	
	public int getBombCount() {																		//Returns total number of bombs.
		return this.bombCount;
	}
	
	public Tile getTile(int x, int y) {																//Returns tile.
		return this.board[x][y];
	}
	
	public String toString() {																		//Temporary toString method for troubleshooting
		String out = "";
		for (int i = 0; i < this.h.intValue(); i++) {
			for (int j = 0; j < this.w.intValue(); j++) {
				out += getTile(j, i).bombOnTile() ? "X" : this.getTile(j, i).getNeighbours().intValue();
			}
			out += "\n";
		}
		return out;
	}
}
