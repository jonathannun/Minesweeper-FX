import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TileFX extends StackPane{
	private Rectangle tileBorder = new Rectangle(Minesweeper.getTileSize() - 1, Minesweeper.getTileSize() - 1);
	private Text tileText = new Text();
	
	public TileFX(int x, int y) {
		tileBorder.setStroke(Color.GRAY);
		tileBorder.setFill(Color.LIGHTGRAY);
		
		tileText.setText(Minesweeper.game.data[x][y].isTileBomb() ? "X" : "" + Minesweeper.game.data[x][y].getNeighbours());
		tileText.setVisible(Minesweeper.game.data[x][y].isTileVisible());
		
		getChildren().addAll(tileBorder, tileText);
		
		setTranslateX(x * Minesweeper.getTileSize());
		setTranslateY(y * Minesweeper.getTileSize());
		
		setOnMouseClicked(e -> {
			switch (e.getButton()) {
			case PRIMARY:
				Controller.tileEvent(x, y);
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
		tileText.setVisible(Minesweeper.game.data[x][y].isTileVisible());
		tileBorder.setFill(Minesweeper.game.data[x][y].isTileVisible() ? null : Color.LIGHTGRAY);
	}
}
