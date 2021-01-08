import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	Stage window;
	Scene scene;
	static int sizeX = 20;
	static int sizeY = 12;
	TileFX[][] tfx;
	Board level;
	public static boolean lost = false;
	public static boolean testLost = false; //SPAGETI CODE
	
	public static void main(String[] args) {
		System.out.println("start");
		launch(args);
	}

	@Override
	public void start(Stage prim) throws Exception {
		level = new Board(sizeX,sizeY);
		level.populateBoard(20);
		
		window = prim;
		window.setTitle("test");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10,10,10,10));
		
		tfx = new TileFX[sizeX][sizeY];
		
		for(int i = 0; i < sizeY; i++) 
		{
			for(int j = 0; j < sizeX; j++) 
			{
				tfx[j][i] = new TileFX(10, level.getTile(j,i).isTileBomb());
				if(!level.getTile(j,i).isTileBomb()) {
					tfx[j][i].setNumber(level.getTile(j,i).getNeighbours()); 
				}
				GridPane.setConstraints(tfx[j][i].getTileFX(), j, i);
				grid.getChildren().add(tfx[j][i]);
			}
		}
		
		/*
		TileFX test = new TileFX(10, true);
		GridPane.setConstraints(test, 0, 0);
		grid.getChildren().add(test);
		*/
		
		scene = new Scene(grid,sizeX*33,sizeY*33);
		window.setScene(scene);
		prim.show();
	}
	
}
