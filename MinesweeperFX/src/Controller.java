import javafx.scene.input.MouseButton;

public class Controller {
	
	public static void tileEvent(int x, int y) {
		if(Minesweeper.game.GameOver) {return;}
		Minesweeper.game.data[x][y].revealTile();
		Minesweeper.game.revealEmpty(x, y);
		for (int i = 0; i < Minesweeper.game.data[0].length; i++) {
			for (int j = 0; j < Minesweeper.game.data.length; j++) {
				Minesweeper.grid[j][i].update(j, i);
			}
		}
	}
	
	public static boolean isBomb(int x, int y) {
		return Minesweeper.game.data[x][y].isTileBomb();
	}
	
	public static String getNeighbours(int x, int y) 
	{
		return Minesweeper.game.data[x][y].getNeighbours() == 0 ? "" : "" + Minesweeper.game.data[x][y].getNeighbours();
	}
	
	public static int getTileSize() {
		return Board.getTilesize();
	}
	
	public static boolean isVisable(int x, int y) {
		return Minesweeper.game.data[x][y].isTileVisible();
	}
	
	//MinewSweeper fetches
	public static int getWidth() {
		return Board.getWidth();
	}

	public static void setWidth(int width) {
		Board.setWidth(width);
	}

	public static int getHeight() {
		return Board.getHeight();
	}

	public static void setHeight(int height) {
		Board.setHeight(height);
	}

	public static int getWindowWidth() {
		return Board.getWindowWidth();
	}

	public static void setWindowWidth(int windowWidth) {
		Board.setWindowWidth(windowWidth);
	}

	public static int getWindowHeight() {
		return Board.getWindowHeight();
	}

	public static void setWindowHeight(int windowHeight) {
		Board.setWindowHeight(windowHeight);
	}
	
	public static int getMines() {
		return Board.getMines();
	}
	
	public static void setMines(int mines) {
		Board.setMines(mines);
	}
	
	public static int getFlagCounter() {
		return Board.getFlagCounter();
	}

	public static void setFlagCounter(int flagCounter) {
		Board.setFlagCounter(flagCounter);
	}
	
	public static void setFlag(int x, int y) {
		if(!Minesweeper.game.data[x][y].getHasFlag()) {
			Minesweeper.game.data[x][y].setHasFlag(true);
			setFlagCounter(getFlagCounter()+1);
		}else {
			Minesweeper.game.data[x][y].setHasFlag(false);
			setFlagCounter(getFlagCounter()-1);
		}
	}
	
	public static boolean hasFlag(int x, int y) {
		return Minesweeper.game.data[x][y].getHasFlag();
	}
	
	public static void click(MouseButton b, int x, int y) {
		switch (b) {
		case PRIMARY:
			if(!Controller.hasFlag(x, y)) {
				Controller.tileEvent(x, y);
			} 
			break;
		case SECONDARY:
			setFlag(x,y);
			Minesweeper.grid[x][y].setFlag(x,y);
			Minesweeper.grid[x][y].setCounter();
			break;
		}
	}
	
	
}
