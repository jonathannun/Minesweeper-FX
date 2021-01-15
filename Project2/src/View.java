import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

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
	
	
	static class ErrorMessage extends TextFlow{

		public static void display (String message) {
			Stage stage = new Stage();
			
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Error Message");
			stage.setMinWidth(300);
			stage.setMinHeight(250);
			
			Label label = new Label();
			label.setText(message);
			label.setFont(Font.font(14));
			label.setStyle("-fx-font-weight: bold");
			label.setTextAlignment(TextAlignment.CENTER);
			Button closeButton = new Button("OK");
			closeButton.setOnAction(e -> stage.close());
			
			VBox layout = new VBox(10);
			layout.getChildren().addAll(label, closeButton);
			layout.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(layout);
			stage.setScene(scene);
			stage.showAndWait();
		}
	}

	
	static class PopUpBox {
		static Stage window = new Stage();
		
		static int[] widthLengthMines = new int[3];
		
		public static int[] show() {
			
	        
	        //Placement of width-label
	        Label labelWidth = new Label("Insert the width of your grid: ");
	        GridPane.setConstraints(labelWidth, 0, 0);
	        labelWidth.setFont(Font.font(14));
	        TextField widthInput = new TextField();
	        GridPane.setConstraints(widthInput, 1, 0);
	        
	        
	        //Placement of length-label
	        Label labelHeight = new Label("Insert the height of your grid: ");
	        GridPane.setConstraints(labelHeight, 0, 1);
	        labelHeight.setFont(Font.font(14));
	        TextField heightInput = new TextField();
	        GridPane.setConstraints(heightInput, 1, 1);
	        
	        
	        //Placement of mine settings
	        Label labelMines = new Label("Insert the number of mines: ");
	        GridPane.setConstraints(labelMines, 0, 2);
	        labelMines.setFont(Font.font(14));
	        TextField mineInput = new TextField();
	        GridPane.setConstraints(mineInput, 1, 2);
	        
	        
	        //Submit button to save the input
	        Button submitButton = new Button("Submit");
	        submitButton.setFont(Font.font(14));
	        GridPane.setConstraints(submitButton, 1, 3);
	        
	      //Close button to close the window
	        Button closeButton = new Button("Close");
	        closeButton.setFont(Font.font(14));
	        GridPane.setConstraints(closeButton, 1, 4);
	        
	       closeButton.setOnAction(e->{
	    	   window.close();
	    	   
	       });
	        
	         //Checking if input is empty
	        submitButton.setOnAction(e-> {
	        	
	        	//If input is int
	        	if (isInt(widthInput) && isInt(heightInput) && isInt(mineInput)) {
	        		
	        		if(Integer.parseInt(widthInput.getText()) > 100 || Integer.parseInt(heightInput.getText()) > 100) {
	        			ErrorMessage.display("ERROR!\nThe size of the board is too big.\nIt cannot exceed 100. Reduce the size.");
	        		} else if(Integer.parseInt(widthInput.getText()) < 4 || Integer.parseInt(heightInput.getText()) < 4) {
	        			ErrorMessage.display("ERROR!\nThe size of the board is too small.\nIt must be larger than 4. Adjust the size.");
	        		} else if(Integer.parseInt(widthInput.getText())*Integer.parseInt(heightInput.getText()) <= Integer.parseInt(mineInput.getText())) {
	            		ErrorMessage.display("ERROR!\nThe number of mines exceeds the size of the board.\nReduce the amount of mines, or adjust the size of the board.");
	            	} else {
	            		widthLengthMines[0] = Integer.parseInt(widthInput.getText());
	            		widthLengthMines[1] = Integer.parseInt(heightInput.getText());
	            		widthLengthMines[2]= Integer.parseInt(mineInput.getText());
	            		window.close();
	            	}
	        	} else if(widthInput.getText().trim().isEmpty()||heightInput.getText().trim().isEmpty()||mineInput.getText().trim().isEmpty()) {
	        		ErrorMessage.display("ERROR!\nOne or more boxes are left empty!\nPlease insert a number in all boxes." );
	        	} else {
	        		ErrorMessage.display("ERROR!\nPlease insert a number." );
	        	}
	        	
	        });
	         
	        //Grid
	        GridPane grid = new GridPane();
	        grid.setPadding(new Insets(10,10,10,10));
			grid.setVgap(8);
			grid.setHgap(10);
	        grid.getChildren().addAll(labelWidth, widthInput, labelHeight, heightInput, labelMines, mineInput, submitButton,closeButton);
	        Scene scene = new Scene(grid, 400, 250);
	        window.setTitle("Modify...");
	        window.setScene(scene);
	        window.showAndWait();
	       return widthLengthMines;
	        
		}
	 
		//Verify input
	    private static boolean isInt(TextField input) {
	    	try {
	    		int number = Integer.parseInt(input.getText());
	    		return true;
	    	}catch(NumberFormatException e) {
	    		return false;
	    	}
	    }
	 
	}
	
	class Timer extends Pane {
		private Timeline timeline = new Timeline();
		private int minute = 0;
		private int second = 0;
		private String clock = "";
		private boolean startCounter = false;
		
		Label label = new Label(String.format("%02d:%02d", minute,second));
		
		public Timer() {
			getChildren().add(label);
			timeline = new Timeline(new KeyFrame(Duration.seconds(1), e-> counter()));
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();
			model.getGameStatus().addListener((obs) -> { 
				timeline.stop();
			});
		}

		public void counter() {
			if(!startCounter) {
				return;
			}
			
			if(second >= 59 ) {
				minute++;
				second = 0;
			} else {
				second++;
			}
			clock = String.format("%02d:%02d",minute, second);
			label.setText(clock);
		}
		
		public void setStartCounter(boolean start) {
			this.startCounter = start;
		}
	}

	
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
			//PopUpBox.show();
			int[] widthLengthMines = PopUpBox.show();
			
			if(!(widthLengthMines[0] == 0) || !(widthLengthMines[1] == 0) || !(widthLengthMines[2]==0)) {
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
		this.model = this.controller.updateModel(w, h, mines);
		
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