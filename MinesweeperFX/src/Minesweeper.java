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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Minesweeper extends Application {
	private static final int tileSize = 20;
	private static int width = 60;
	private static int height = 20;
	public static int mines = 200;
	private static int windowWidth = width*tileSize;
	private static int windowHeight = height*tileSize;
	public static Label counter = new Label("Reamining flags : " + mines);
	public static Pane pane = new Pane();
	
	static MenuBar m = createMenuBar();
	
	public static Board game = new Board(width, height);
	public static TileFX[][] grid = new TileFX[width][height];
	
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(createGame());
		System.out.println("start");
		m.prefWidthProperty().bind(stage.widthProperty());
		stage.setScene(scene);
		
		stage.show();
	}
	
	private static Parent createGame() {
		BorderPane root = new BorderPane();
		
		pane.setPrefSize(windowWidth-16, windowHeight-11);
		game.populateBoard(mines);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j <  width; j++) {
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
		
		AnchorPane.setLeftAnchor(restartButton, Double.valueOf(windowWidth/2));
		AnchorPane.setLeftAnchor(timer, Double.valueOf(windowWidth-250));
		AnchorPane.setLeftAnchor(counter, Double.valueOf(windowWidth-200));
		
		Anchor.getChildren().addAll(m,counter,timer,restartButton);
		
		root.setCenter(pane);
		root.setTop(Anchor);
		
		return root;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static int getTileSize() {
		return tileSize;
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
				width = widthLengthMines[0] ;
				height = widthLengthMines[1];
				mines = widthLengthMines[2];
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
		Minesweeper.game = new Board(width, height);
		game.populateBoard(mines);
		grid = new TileFX[width][height];
		pane.getChildren().clear();
		windowWidth = width*tileSize;
		windowHeight = height*tileSize;
		pane.setPrefSize(windowWidth, windowHeight);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j <  width; j++) {
				TileFX tile = new TileFX(j, i);
				grid[j][i] = tile;
				pane.getChildren().add(tile);
			}
		}
	}
}
