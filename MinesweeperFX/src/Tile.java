public class Tile {
	private Boolean isBomb, isVisible;
	private int x, y, neighbours;
	
	public Tile(int x, int y) {
		this.isBomb = false;
		this.isVisible = false;
		this.neighbours = 0;
		
		this.x = x;
		this.y = y;
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
	
	public Boolean bombOnTile() {
		return this.isBomb;
	}
	
	public Boolean tileVisible() {
		return this.isVisible;
	}
	
	public int getNeighbours() {
		return this.neighbours;
	}
}
