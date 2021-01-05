package application;

public class Tile {
	private Boolean isBomb, isClicked;
	private int neighbours;
	
	public Tile() {
		this.isBomb = false;
		this.isClicked = false;
		this.neighbours = 0;
	}
}
