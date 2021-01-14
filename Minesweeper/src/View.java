import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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
	Timer timer;
	
	private static int tileSize = 20;
	private static IntegerProperty width;
	private static IntegerProperty height;
	private static IntegerProperty flagCount;
	
	
	class Tile extends StackPane {																	//Tile object-class.
		private Rectangle tile;																		//Rectangle object.
		private Text text;																			//Text object.
		
		ImageView mine = new ImageView("Images/mine.png");
		ImageView flag = new ImageView("Images/flag.png");
		
		public Tile(int x, int y) {																	//Constructor.
			this.tile = new Rectangle(tileSize, tileSize);								  			//Sets size of rectangle.
			this.text = new Text();																	//Initializes empty text object.
			
			this.tile.setStroke(Color.GREY);														//Adds grey border to rectangle object.
			this.tile.setFill(Color.LIGHTGRAY);														//Changes color of rectangle.
			
			flag.setFitHeight(tileSize - 1);
			flag.setFitWidth(tileSize - 1);
			
			model.getTile(x, y).tileVisible().addListener((obs) -> {								//Listens to changes to tileVisible BooleanProperty of tile.
				this.tile.setFill(null);
				this.text.setVisible(true);
				mine.setVisible(true);
				timer.setStartCounter(true);
			});
			model.getTile(x, y).flagOnTile().addListener((obs, oldBool, newBool) -> {				//Listens to changes to flagOnTile BooleanProperty of tile.
				if (newBool && !model.getTile(x, y).tileVisible().get()) {
					getChildren().add(flag);
					flagCount.set(flagCount.get() + 1);
				} else {
					for (int i = 0; i < getChildren().size(); i++) {
						if (getChildren().get(i) == flag) {
							getChildren().remove(i);
							flagCount.set(flagCount.get() - 1);
						}
					}
				}
			});
			
			getChildren().add(this.tile);
			
			if (model.getTile(x, y).bombOnTile().get()) {													//Sets string value of text object.
				mine.setFitHeight(tileSize);
				mine.setFitWidth(tileSize);
				mine.setVisible(false);
				getChildren().add(mine);
			} else {
				this.text.setText(controller.getNeighbours(x, y) == 0 ? "" : "" + controller.getNeighbours(x, y));
				text.setVisible(false);
			}
			
			switch (controller.getNeighbours(x, y)) {
			case 1:
				this.text.setFill(Color.BLUE);
				break;
			case 2:
				this.text.setFill(Color.GREEN);
				break;
			case 3:
				this.text.setFill(Color.RED);
				break;
			case 4:
				this.text.setFill(Color.DARKBLUE);
				break;
			case 5:
				this.text.setFill(Color.DARKRED);
				break;
			case 6:
				this.text.setFill(Color.DARKCYAN);
				break;
			case 7:
				this.text.setFill(Color.BLACK);
				break;
			default:
				this.text.setFill(Color.GREY);
				break;
			}
			
			getChildren().add(this.text);															//Adds nodes to tile.
			
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
		this.flagCount = new SimpleIntegerProperty();
		
		this.width.set(tileSize * this.model.getW().intValue() < 550 ? 550 : tileSize * this.model.getW().intValue());
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
		
		timer = new Timer();
		AnchorPane Anchor = new AnchorPane();
		
		Button restartButton = new Button("Restart"); 
		restartButton.setOnAction(e-> restart(model.getW().intValue(), model.getH().intValue(), model.getBombCount()));
		
		ImageView flag = new ImageView("Images/Flag.png");
		counter.setGraphic(flag);
		flag.setFitHeight(20);
		flag.setFitWidth(20);
		
		this.flagCount.addListener((obs, oldInt, newInt) -> {
			this.counter.setText("Remaining bombs: " + (model.getBombCount() - flagCount.intValue()));
		});
		
		AnchorPane.setLeftAnchor(restartButton, Double.valueOf((this.width.intValue()) - 350));
		AnchorPane.setLeftAnchor(timer, Double.valueOf((this.width.intValue()) - 250));
		AnchorPane.setLeftAnchor(counter, Double.valueOf((this.width.intValue()) - 200));
		
		Anchor.getChildren().addAll(this.menu, counter, timer, restartButton);
		
		this.window.setCenter(gameWindow);
		this.window.setTop(Anchor);
	}
	
	public MenuBar createMenuBar() {
		//Menu
		Menu menu = new Menu("Difficulty Level...");
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
		easy.setOnAction(e -> restart(9,9,10));
		RadioMenuItem medium = new RadioMenuItem("Medium");
		medium.setOnAction(e -> restart(16,16,40));
		RadioMenuItem hard = new RadioMenuItem("Hard");
		hard.setOnAction(e -> restart(16,30,99));
				
		//Add to the ToogleGroup
		m1.setToggleGroup(difficultyLevelGroup);
		easy.setToggleGroup(difficultyLevelGroup);
		medium.setToggleGroup(difficultyLevelGroup);
		hard.setToggleGroup(difficultyLevelGroup);
				
		//Add menu-items to the menu object
		menu.getItems().addAll(m1,easy,medium,hard);

		//MenuBar
		MenuBar menuBar = new MenuBar();
		
		//Add the menu full of items to the menu-bar
		menuBar.getMenus().addAll(menu, mineFallButton);
		return menuBar;
	}
	
	public void restart(int w, int h, int mines) {
		this.model = new Model(w, h, mines);
		this.controller = new Controller(this.model);
		
		this.gameWindow = new Pane();
		
		if (model.getH().intValue() <= 20) {
			this.tileSize  = 40;
		} else if (model.getH().intValue() <= 40) {
			this.tileSize = 20;
		} else {
			this.tileSize = 10;
		}
			
		this.height.set(tileSize * this.model.getH().intValue() + 3);
		this.width.set(tileSize * this.model.getW().intValue() < 550 ? 550 : tileSize * this.model.getW().intValue()); 
		this.flagCount.set(0);
		
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
