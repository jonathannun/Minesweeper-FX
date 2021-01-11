import javafx.scene.input.MouseEvent;

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
	
	public static void handle(MouseEvent event) {
		System.out.println(event.getX());
	}
	
}
