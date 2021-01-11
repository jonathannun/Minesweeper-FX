import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Minesweeper extends Application {
	private static final int tileSize = 40;
	private static final int windowWidth = 800;
	private static final int windowHeight = 600;
	
	public static Board game = new Board(windowWidth / tileSize, windowHeight / tileSize);
	public static TileFX[][] grid = new TileFX[windowWidth / tileSize][windowHeight / tileSize];
	
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(createGame());
		System.out.println("start");
		stage.setScene(scene);
		stage.show();
	}
	
	private Parent createGame() {
		Pane root = new Pane();
		root.setPrefSize(windowWidth, windowHeight);
		game.populateBoard(50);
		
		for (int i = 0; i < windowHeight / tileSize; i++) {
			for (int j = 0; j < windowWidth / tileSize; j++) {
				TileFX tile = new TileFX(j, i);
				grid[j][i] = tile;
				root.getChildren().add(tile);
			}
		}
		
		return root;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static int getTileSize() {
		return tileSize;
	}
}
