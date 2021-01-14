import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class View {
	private Model model;
	private Controller controller;
	private Pane gameWindow;
	private MenuBar menu;
	private Label counter;
	private BorderPane window;
	
	private static int tileSize = 20;
	private static IntegerProperty width;
	private static IntegerProperty height;
	
	
	class Tile extends StackPane {																	//Tile object-class.
		private Rectangle tile;																		//Rectangle object.
		private Text text;																			//Text object.
		
		ImageView mine = new ImageView("Images/mine.png");
		ImageView flag = new ImageView("Images/flag.png");
		
		public Tile(int x, int y) {																	//Constructor.
			this.tile = new Rectangle(tileSize - 1, tileSize - 1);									//Sets size of rectangle.
			this.text = new Text();																	//Initializes empty text object.
			
			if (model.getTile(x, y).bombOnTile()) {													//Sets string value of text object.
				mine.setFitHeight(tileSize - 1);
				mine.setFitWidth(tileSize - 1);
				getChildren().add(mine);
			} else {
				this.text.setText(controller.getNeighbours(x, y) == 0 ? "" : "" + controller.getNeighbours(x, y));
			}
			
			this.tile.setStroke(Color.GREY);														//Adds grey border to rectangle object.
			this.tile.setFill(Color.LIGHTGRAY);														//Changes color of rectangle.
			
			flag.setFitHeight(tileSize - 1);
			flag.setFitWidth(tileSize - 1);
			
			model.getTile(x, y).tileVisible().addListener((obs) -> {								//Listens to changes to tileVisible BooleanProperty of tile.
				this.tile.setFill(null);
			});
			model.getTile(x, y).flagOnTile().addListener((obs, oldBool, newBool) -> {				//Listens to changes to flagOnTile BooleanProperty of tile.
				if (newBool && !model.getTile(x, y).tileVisible().get()) {
					getChildren().add(flag);
				} else {
					for (int i = 0; i < getChildren().size(); i++) {
						if (getChildren().get(i) == flag) {
							getChildren().remove(i);
						}
					}
				}
			});
			
			getChildren().addAll(this.text, this.tile);												//Adds nodes to tile.
			
			setOnMouseClicked(e -> {																//Handles mouse-click event.
				switch (e.getButton()) {
				case PRIMARY:																		//Left click.
					controller.leftClick(x, y);
					break;
				case SECONDARY:																		//Right click.
					controller.rightClick(x, y);
					break;
				default:																			//Empty default to remove warning.
					break;
				}
			});
		}
	}
	
	
	public View(Model model, Controller controller) {												//Constructor
		this.model = model;
		this.controller = controller;
		this.gameWindow = new Pane();
		this.menu = createMenuBar();
		this.counter = new Label("Remaining bombs: " + this.model.getBombCount());
		this.window = new BorderPane();
		
		this.width = new SimpleIntegerProperty();
		this.height = new SimpleIntegerProperty();
		
		this.width.set(tileSize * this.model.getW().intValue());
		this.height.set(tileSize * this.model.getH().intValue() + 3);
		
		createGame();																				//Creates and adds tiles to pane.
	}
	
	public Parent asParent() {																		//Returns pane as parent.
		return this.window;
	}
	
	private void createGame() {
		this.gameWindow.setPrefHeight(this.height.intValue());										//Sets dimensions of pane object.
		this.gameWindow.setPrefWidth(this.width.intValue());
		
		for (int i = 0; i < this.model.getH().intValue(); i++) {
			for (int j = 0; j < this.model.getW().intValue(); j++) {
				Tile tile = new Tile(j, i);															//Creates tile object.
				
				tile.setTranslateX(j * View.tileSize);												//Sets placement of tile.
				tile.setTranslateY(i * View.tileSize);
				
				this.gameWindow.getChildren().add(tile);											//Adds tile to pane.
			}
		}
		
		Timer timer = new Timer();
		AnchorPane Anchor = new AnchorPane();
		
		Button restartButton = new Button("Restart"); 
		restartButton.setOnAction(e-> restart(model.getW().intValue(), model.getH().intValue(), model.getBombCount()));
		
		ImageView flag = new ImageView("Images/Flag.png");
		counter.setGraphic(flag);
		flag.setFitHeight(20);
		flag.setFitWidth(20);
		
		AnchorPane.setLeftAnchor(restartButton, Double.valueOf((this.model.getW().intValue() * this.tileSize) - 350));
		AnchorPane.setLeftAnchor(timer, Double.valueOf((this.model.getW().intValue() * this.tileSize) - 250));
		AnchorPane.setLeftAnchor(counter, Double.valueOf((this.model.getW().intValue() * this.tileSize) - 200));
		
		Anchor.getChildren().addAll(this.menu, counter, timer, restartButton);
		
		this.window.setCenter(gameWindow);
		this.window.setTop(Anchor);
	}
	
	public MenuBar createMenuBar() {
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
				restart(widthLengthMines[0], widthLengthMines[1], widthLengthMines[2]);
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
	
	public void restart(int w, int h, int mines) {
	
		this.model = new Model(w, h, mines);
		this.controller = new Controller(this.model);
		
		this.gameWindow = new Pane();
		
		this.height.set(tileSize * this.model.getH().intValue() + 3);
		this.width.set(tileSize * this.model.getW().intValue()); 
		
		this.gameWindow.setPrefHeight(this.height.intValue());									//Sets dimensions of pane object.
		this.gameWindow.setPrefWidth(this.width.intValue());
		
		this.window.getChildren().clear();
		createGame();
	}
	
	public int getTileSize() {
		return this.tileSize;
	}
	
	public IntegerProperty getHeight() {
		return this.height;
	}
	
	public IntegerProperty getWidth() {
		return this.width;
	}
}
