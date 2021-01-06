public class Tile {
	private Boolean isBomb, isVisible;
	private int neighbours;
	
	public Tile() {
		this.isBomb = false;
		this.isVisible = false;
		this.neighbours = 0;
	}
	
	public void setBomb() {
		this.isBomb = true;
	}
	
	public void setNeighbours(int count) {
		this.neighbours = count;
	}
	
	public void revealTile() {
		this.isVisible = true;
	}
	
	public Boolean isTileBomb() {
		return this.isBomb;
	}
	
	public Boolean isTileVisible() {
		return this.isVisible;
	}
	
	public int getNeighbours() {
		return this.neighbours;
	}
}
