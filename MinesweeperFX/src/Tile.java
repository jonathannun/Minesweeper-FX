public class Tile {
	private Boolean isBomb, isVisible;
	private boolean hasFlag; 
	private int neighbours;
	
	public Tile() {																					//Constructs empty tile
		this.isBomb = false;
		this.isVisible = false;
		this.neighbours = 0;
	}
	
	public void setBomb() {																			//Places bomb on tile
		this.isBomb = true;
	}
	
	public void setNeighbours(int count) {															//Updates neighbour counter
		this.neighbours = count;
	}
	
	public void revealTile() {																		//Reveals tile
		this.isVisible = true;
	}
	
	public Boolean isTileBomb() {																	//Returns boolean bomb value of tile
		return this.isBomb;
	}
	
	public Boolean isTileVisible() {																//Returns boolean visibility value of tile
		return this.isVisible;
	}
	
	public int getNeighbours() {																	//Return int value of number of adjacent bombs
		return this.neighbours;
	}

	public boolean getHasFlag() {
		return hasFlag;
	}

	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}
}
