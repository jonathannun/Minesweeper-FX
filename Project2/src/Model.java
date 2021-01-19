import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Model {
	private Tile[][] board;
	private IntegerProperty w;
	private IntegerProperty h;
	private int bombCount;
	private BooleanProperty lost;

	class Tile {
		private BooleanProperty hasBomb = new SimpleBooleanProperty();								//Initializes objects.
		private BooleanProperty hasFlag = new SimpleBooleanProperty();
		private BooleanProperty isVisible = new SimpleBooleanProperty();
		private IntegerProperty neighbours = new SimpleIntegerProperty();
		
		public Tile() {																				//Tile constructor
			this.hasBomb.set(false);																//Sets BooleanProperty value
			this.hasFlag.set(false);																//^
			this.isVisible.set(false);																//^
			
			this.neighbours.set(0);;																//Sets IntegerProperty value.
		}
		
		public BooleanProperty bombOnTile() {														//Returns BooleanProperty hasBomb.
			return this.hasBomb;
		}
		
		public BooleanProperty flagOnTile() {														//Returns BooleanProperty hasFlag.
			return this.hasFlag;
		}
		
		public BooleanProperty tileVisible() {														//Returns BooleanProperty isVisible.
			return this.isVisible;
		}
		
		public IntegerProperty getNeighbours() {													//Returns IntegerProperty neighbours.
			return this.neighbours;
		}
		
		public void setBomb() {																		//Places bomb on tile
			this.hasBomb.set(true);
		}
		
		public void toggleFlag() {																	//Toggles flag placement on tile.
			this.hasFlag.set(!this.hasFlag.get());
		}
		
		public void setNeighbours(int neighbours) {													//Sets counted number of adjacent bombs to tile.
			this.neighbours.set(neighbours);
		}
		
		public void setVisible() {																	//Sets BooleanProperty isVisible to true.
			this.isVisible.set(true);
		}
	}
	
	public Model(int w, int h, int bombCount) {														//Model constructor
		this.w = new SimpleIntegerProperty();														//Initializes objects.
		this.h = new SimpleIntegerProperty();														//^
		this.lost = new SimpleBooleanProperty();													//^
		this.lost.set(false);
		
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
			
			if (!this.board[x][y].bombOnTile().get()) {												//Bomb placed at coordinates if empty.
				this.board[x][y].setBomb();
				i++;																				//Increment number of placed bombs.
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
					count += this.board[x][y].bombOnTile().get() ? 1 : 0;							//Increment if bomb found on tile.
				}
			}
		}
		
		this.board[j][i].setNeighbours(count);														//Set number of adjacent bombs to tile.
	}
	
	public void reveal(int j, int i) {																
		int x, y;
		for (int iOffset = -1; iOffset <= 1; iOffset++) {											//Adjacency reach as coordinate offset.
			y = (i + iOffset) < this.h.intValue() ? (i + iOffset) : -1;								//OoB-handling
			
			for (int jOffset = -1; jOffset <= 1; jOffset++) {
				x = (j + jOffset) < this.w.intValue() ? (j + jOffset) : -1;
				
				if ((x >= 0) && (y >= 0)) {															//Checks if tile-coordinates are valid.
					if (this.board[x][y].flagOnTile().get()) {										//Skips tiles with flags.
						continue;
					}
					if (this.board[x][y].getNeighbours().intValue() == 0 && !this.board[x][y].tileVisible().get()) {
						this.board[x][y].setVisible();
						reveal(x, y);																//Recursion if neighbour is empty
					}
					if (this.board[j][i].getNeighbours().intValue() == 0) {
						this.board[x][y].setVisible();												//Reveal neighbours is original tile is empty.
					}

				}
			}
		}
	}
	
	public Boolean gameWon() {																		//Checks if player has won.
		for(int i = 0; i < this.getH().intValue(); i++) {
			for(int j = 0; j < this.getW().intValue(); j++) {
				if(!board[j][i].hasBomb.get() && !board[j][i].tileVisible().get()) {
					return false;
				} else if (board[j][i].hasBomb.get() && !board[j][i].hasFlag.get()) {
					return false;
				}
			}
		}
		System.out.println("You Win");																//Confirmation.
		return true;
	}
	
	public void setGameStatus(Boolean lost) {														//Set game status.
		this.lost.set(lost); 
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
	
	public BooleanProperty getGameStatus() {														//Returns BooleanProperty lost.
		return this.lost;
	}
	
	public Tile getTile(int x, int y) {																//Returns tile.
		return this.board[x][y];
	}
	
	public String toString() {																		//Temporary toString method for troubleshooting
		String out = "";
		for (int i = 0; i < this.h.intValue(); i++) {
			for (int j = 0; j < this.w.intValue(); j++) {
				out += getTile(j, i).bombOnTile().get() ? "X" : this.getTile(j, i).getNeighbours().intValue();
			}
			out += "\n";
		}
		return out;
	}
}
