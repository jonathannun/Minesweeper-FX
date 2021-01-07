import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TileFX extends StackPane{
	private Rectangle tileBorder = new Rectangle(Minesweeper.tileSize - 1, Minesweeper.tileSize - 1);
	private Text tileText = new Text();
	
	public TileFX(int x, int y) {
		tileBorder.setStroke(Color.GRAY);
		tileBorder.setFill(Color.LIGHTGRAY);
		
		tileText.setText(Minesweeper.game.data[x][y].isTileBomb() ? "X" : "" + Minesweeper.game.data[x][y].getNeighbours());
		tileText.setVisible(Minesweeper.game.data[x][y].isTileVisible());
		
		getChildren().addAll(tileBorder, tileText);
		
		setTranslateX(x * Minesweeper.tileSize);
		setTranslateY(y * Minesweeper.tileSize);
		
		setOnMouseClicked(e -> {
			switch (e.getButton()) {
			case PRIMARY:
				update(x, y);
				break;
			case SECONDARY:
				setFlag();
				break;
			}
		});
	}
	
	public void setFlag() {
		tileBorder.setFill(Color.CHARTREUSE);
	}
	
	public void update(int x, int y) {
		if (Minesweeper.game.data[x][y].isTileVisible()) {
			return;
		}
		
		Minesweeper.game.data[x][y].revealTile();
		tileText.setVisible(Minesweeper.game.data[x][y].isTileVisible());
		tileBorder.setFill(Minesweeper.game.data[x][y].isTileVisible() ? null : Color.LIGHTGRAY);
	}
}
