public class Controller {
	
	public static void tileEvent(int x, int y) {
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
	
	public static boolean setFlag(int x, int y) {
		if(!Minesweeper.game.data[x][y].getHasFlag()) {
			Minesweeper.game.data[x][y].setHasFlag(true);
			return false;
		}else {
			Minesweeper.game.data[x][y].setHasFlag(false);
			return true;
		}
	}
	
	public static boolean hasFlag(int x, int y) {
		return Minesweeper.game.data[x][y].getHasFlag();
	}
	
}
