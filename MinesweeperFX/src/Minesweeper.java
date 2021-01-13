import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Minesweeper extends Application {
	public static Label counter;
	public static GridPane pane = new GridPane();
	private static Stage window;
	static MenuBar m = createMenuBar();
	
	public static Board game;
	public static TileFX[][] grid;
	
	@Override
	public void start(Stage stage) {
		counter = new Label("Reamining flags : " + Controller.getMines());
		game = new Board(Controller.getWidth(),Controller.getHeight());
		grid = new TileFX[Controller.getWidth()][Controller.getHeight()];
		window = stage;
		Scene scene = new Scene(createGame());
		System.out.println("start");
		m.prefWidthProperty().bind(stage.widthProperty());
		stage.setScene(scene);
		
		stage.show();
	}
	
	private static Parent createGame() {
		BorderPane root = new BorderPane();
		pane.setPrefSize(Controller.getWindowWidth(), Controller.getWindowHeight());
		game.populateBoard();
		
		for (int i = 0; i < Controller.getHeight(); i++) {
			for (int j = 0; j <  Controller.getWidth(); j++) {
				TileFX tile = new TileFX(j, i);
				grid[j][i] = tile;
				pane.getChildren().add(tile);
			}
		}
		Timer timer = new Timer();
		AnchorPane Anchor = new AnchorPane();
		
		Button restartButton = new Button("Restart");
		restartButton.setOnAction(e-> restartGame());
		
		ImageView flag = new ImageView("Images/Flag.png");
		counter.setGraphic(flag);
		flag.setFitHeight(20);
		flag.setFitWidth(20);
		
		AnchorPane.setLeftAnchor(restartButton, Double.valueOf(Controller.getWindowWidth()/2));
		AnchorPane.setLeftAnchor(timer, Double.valueOf(Controller.getWindowWidth()-250));
		AnchorPane.setLeftAnchor(counter, Double.valueOf(Controller.getWindowWidth()-200));
		
		Anchor.getChildren().addAll(m,counter,timer,restartButton);
		
		root.setCenter(pane);
		root.setTop(Anchor);
		
		return root;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static MenuBar createMenuBar() {
		//Menu
		Menu menu = new Menu("Difficulty Level...");
		Menu timeButton = new Menu("Time");
		Menu mineFallButton = new Menu("MineFall");
				
		//Create menuItems for Difficult settings
		RadioMenuItem m1 = new RadioMenuItem("Modify...");
		ToggleGroup difficultyLevelGroup = new ToggleGroup(); 
				
		//Pop-up window - Modify Level
		m1.setOnAction(e -> {
			PopUpBox.show();
			int[] widthLengthMines = PopUpBox.show();
			if(widthLengthMines[0]==0||widthLengthMines[1]==0||widthLengthMines[2]==0) 
			{
				System.out.println("empty");
			}				
			else {
				Controller.setWidth(widthLengthMines[0]);
				Controller.setHeight(widthLengthMines[1]);
				Controller.setMines(widthLengthMines[2]);
				restartGame();
			}				
		});
				
		RadioMenuItem easy = new RadioMenuItem("Easy");
		easy.setOnAction(e -> System.out.println("easy"));
		RadioMenuItem medium = new RadioMenuItem("Medium");
		medium.setOnAction(e -> System.out.println("medium"));
		RadioMenuItem hard = new RadioMenuItem("Hard");
		hard.setOnAction(e -> System.out.println("hard"));
		MenuItem restart = new MenuItem("Restart");
				
		//Add to the ToogleGroup
		m1.setToggleGroup(difficultyLevelGroup);
		easy.setToggleGroup(difficultyLevelGroup);
		medium.setToggleGroup(difficultyLevelGroup);
		hard.setToggleGroup(difficultyLevelGroup);
				
		//Add menu-items to the menu object
		menu.getItems().addAll(m1,easy,medium,hard,restart);

		//MenuBar
		MenuBar menuBar = new MenuBar();
		
		//Add the menu full of items to the menu-bar
		menuBar.getMenus().addAll(menu ,timeButton, mineFallButton);
		return menuBar;
	}
	public static void restartGame() {
		Minesweeper.game = new Board(Controller.getWidth(), Controller.getHeight());
		grid = new TileFX[Controller.getWidth()][Controller.getHeight()];
		pane.getChildren().clear();
		
		Scene newScene = new Scene(createGame());
		window.setHeight(Controller.getWindowHeight()+50);
		window.setWidth(Controller.getWindowWidth());
		window.setScene(newScene);
	}
}
