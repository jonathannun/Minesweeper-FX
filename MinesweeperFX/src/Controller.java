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
		return Minesweeper.getTileSize() - 1;
	}
	
	public static boolean isVisable(int x, int y) {
		return Minesweeper.game.data[x][y].isTileVisible();
	}
	
	public static void setFlag(int x, int y) {
		if(!Minesweeper.game.data[x][y].getHasFlag()) {
			Minesweeper.game.data[x][y].setHasFlag(true);
			Minesweeper.flagCounter++;
		}else {
			Minesweeper.game.data[x][y].setHasFlag(false);
			Minesweeper.flagCounter--;
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
