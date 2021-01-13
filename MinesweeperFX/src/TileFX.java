import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TileFX extends StackPane{
	private Rectangle tileBorder;
	private Text tileText = new Text();
	
	ImageView mine = new ImageView("Images/Mine.png");
	ImageView flag = new ImageView("Images/Flag.png");
	
	public TileFX(int x, int y) {
		tileBorder = new Rectangle(Controller.getTileSize()-1, Controller.getTileSize()-1);
		tileBorder.setStroke(Color.GRAY);
		tileBorder.setFill(Color.LIGHTGRAY);
		
		if(Controller.isBomb(x, y)) {
			mine.setFitHeight(Controller.getTileSize()-1);
			mine.setFitWidth(Controller.getTileSize()-1);
			getChildren().add(mine);
		} else {
			tileText.setText(Controller.getNeighbours(x, y));
			getChildren().add(tileText);
		}
		
		getChildren().add(tileBorder);
		
		setTranslateX(x* Controller.getTileSize());
		setTranslateY(y* Controller.getTileSize());
		
		setOnMouseClicked(e->Controller.click(e.getButton(), x,y));
	}
	
	public void setFlag(int x, int y) {
		if(Controller.hasFlag(x,y)) {
			flag.setFitHeight(Controller.getTileSize()-1);
			flag.setFitWidth(Controller.getTileSize()-1);
			getChildren().add(flag);
		} else {
			for(int i = 0; i < getChildren().size(); i++) {
				if(getChildren().get(i) == flag) {
					getChildren().remove(i);
				}
			}
		}
	}
	
	public void update(int x, int y) {
		tileText.setVisible(Controller.isVisable(x, y));
		tileBorder.setFill(Controller.isVisable(x, y) ? null : Color.LIGHTGRAY);
	}
	
	
	public static void setCounter() {
		Minesweeper.counter.setText("Remaining flags : " + String.valueOf(Controller.getMines()-Controller.getFlagCounter()));
	}
	
}

