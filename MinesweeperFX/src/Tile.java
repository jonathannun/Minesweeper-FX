public class Tile {
	private Boolean isBomb, isClicked;
	private int neighbours;
	
	public Tile() {
		this.isBomb = false;
		this.isClicked = false;
		this.neighbours = 0;
	}
	
	public void setBomb() {
		this.isBomb = true;
	}
	
	public void click() {
		this.isClicked = true;
	}
	
	public Boolean bombOnTile() {
		return this.isBomb;
	}
	
	public Boolean tileClicked() {
		return this.isClicked;
	}
	
	public int getNeighbours() {
		return this.neighbours;
	}
}
