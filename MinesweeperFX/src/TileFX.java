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
		tileBorder = new Rectangle(Controller.getTileSize(), Controller.getTileSize());
		tileBorder.setStroke(Color.GRAY);
		tileBorder.setFill(Color.LIGHTGRAY);
		
		if(Controller.isBomb(x, y)) {
			mine.setFitHeight(Controller.getTileSize());
			mine.setFitWidth(Controller.getTileSize());
			getChildren().add(mine);
		} else {
			tileText.setText(Controller.getNeighbours(x, y));
			getChildren().add(tileText);
		}
		
		getChildren().add(tileBorder);
		
		setTranslateX(x * Controller.getTileSize());
		setTranslateY(y * Controller.getTileSize());
		
		setOnMouseClicked(e -> {
			switch (e.getButton()) {
			case PRIMARY:
				if(!Controller.hasFlag(x, y)) {
					Controller.tileEvent(x, y);
				} 
				break;
			case SECONDARY:
				setFlag(x,y);
				break;
			}
		});
	}
	
	public void setFlag(int x, int y) {
		if(!Controller.setFlag(x,y)) {
			flag.setFitHeight(Controller.getTileSize());
			flag.setFitWidth(Controller.getTileSize());
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
}
