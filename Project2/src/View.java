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
	
	
	static class ErrorMessage extends TextFlow{														//Internal class ErrorMessage-object.

		public static void display (String message) {
			Stage stage = new Stage();																//New window.
			
			stage.initModality(Modality.APPLICATION_MODAL);											//Interaction with the main window stops while the error message is open.
			stage.setTitle("Error Message");														//Sets title of the new window.
			stage.setMinWidth(300);																	
			stage.setMinHeight(250);
			
			Label label = new Label();																//Initializes new label node.
			label.setText(message);																	//Label text fetched from parameter.
			label.setFont(Font.font(14));															
			label.setStyle("-fx-font-weight: bold");												//Bold font.
			label.setTextAlignment(TextAlignment.CENTER);
			Button closeButton = new Button("OK");													//Initializes new button node.
			closeButton.setOnAction(e -> stage.close());											//Button closes the new window.
			
			VBox layout = new VBox(10);																//VBox.
			layout.getChildren().addAll(label, closeButton);										//Adds nodes to VBox.
			layout.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(layout);														//New scene from VBox.
			stage.setScene(scene);																	//Sets scene.
			stage.showAndWait();
		}
	}

	
	static class PopUpBox {
		static int[] widthLengthMines = new int[3];
		
		public static int[] show() {
			Stage window = new Stage();															//New window.
			window.initModality(Modality.APPLICATION_MODAL);									//Interaction with the main window stops while the pop-up is open.
							
			
	        //Placement of width-label
	        Label labelWidth = new Label("Insert the width of your grid: ");						//Label with user-instruction.
	        GridPane.setConstraints(labelWidth, 0, 0);
	        labelWidth.setFont(Font.font(14));
	        TextField widthInput = new TextField();													//Initializes TextField.
	        GridPane.setConstraints(widthInput, 1, 0);
	        
	        
	        //Placement of length-label
	        Label labelHeight = new Label("Insert the height of your grid: ");						//Label with user-instruction.
	        GridPane.setConstraints(labelHeight, 0, 1);
	        labelHeight.setFont(Font.font(14));
	        TextField heightInput = new TextField();												//Initializes TextField.
	        GridPane.setConstraints(heightInput, 1, 1);
	        
	        
	        //Placement of mine settings
	        Label labelMines = new Label("Insert the number of mines: ");							//Label with user-instruction.
	        GridPane.setConstraints(labelMines, 0, 2);
	        labelMines.setFont(Font.font(14));
	        TextField mineInput = new TextField();													//Initializes TextField.
	        GridPane.setConstraints(mineInput, 1, 2);
	        
	        
	        //Submit button to save the input
	        Button submitButton = new Button("Submit");												//Initializes submit-Button.
	        submitButton.setFont(Font.font(14));
	        GridPane.setConstraints(submitButton, 1, 3);
	        
	      //Close button to close the window
	        Button closeButton = new Button("Close");												//Initializes close-Button.
	        closeButton.setFont(Font.font(14));
	        GridPane.setConstraints(closeButton, 1, 4);
	        
	       closeButton.setOnAction(e->{																//close-Button closes window.
	    	   window.close();
	    	   
	       });
	        
	         //Checking if input is empty
	        submitButton.setOnAction(e-> {
	        	
	        	//If input is int
	        	if (isInt(widthInput) && isInt(heightInput) && isInt(mineInput)) {					//Checks validity of input.
	        		
	        		if(Integer.parseInt(widthInput.getText()) > 100 || Integer.parseInt(heightInput.getText()) > 100) {
	        			ErrorMessage.display("ERROR!\n"												//New error message. Size of board too big.
	        								+ "The size of the board is too big.\n"			
	        								+ "It cannot exceed 100. Reduce the size.");
	        		} else if(Integer.parseInt(widthInput.getText()) < 4 || Integer.parseInt(heightInput.getText()) < 4) {
	        			ErrorMessage.display("ERROR!\n"												//New error message. Size of board too small.
	        								+ "The size of the board is too small.\n"		
	        								+ "It must be larger than 4. Adjust the size.");
	        		} else if(Integer.parseInt(widthInput.getText())*Integer.parseInt(heightInput.getText()) <= Integer.parseInt(mineInput.getText())) {
	            		ErrorMessage.display("ERROR!\n"												//New error message. Too many bombs.
	            							+ "The number of mines exceeds the size of the board.\n"
	            							+ "Reduce the amount of mines, or adjust the size of the board.");
	            	} else {
	            		widthLengthMines[0] = Integer.parseInt(widthInput.getText());				//Parse user input as integer.
	            		widthLengthMines[1] = Integer.parseInt(heightInput.getText());				//^
	            		widthLengthMines[2]= Integer.parseInt(mineInput.getText());					//^
	            		window.close();																//Close window.
	            	}
	        	} else if(widthInput.getText().trim().isEmpty()||heightInput.getText().trim().isEmpty()||mineInput.getText().trim().isEmpty()) {
	        		ErrorMessage.display("ERROR!\n"													//New error message. Missing input.
	        							+ "One or more boxes are left empty!\n"
	        							+ "Please insert a number in all boxes." );
	        	} else {
	        		ErrorMessage.display("ERROR!\n"													//New error message. NaN.
	        							+ "Please insert a number." );
	        	}
	        	
	        });
	         
	        //Grid
	        GridPane grid = new GridPane();															//New GridPane.
	        grid.setPadding(new Insets(10,10,10,10));												//Padding between window border.
			grid.setVgap(8);																		//Vertical gap between nodes.
			grid.setHgap(10);																		//Horizontal gap between nodes.
	        grid.getChildren().addAll(labelWidth, 													//Adds nodes as children to GridPane.
	        						  widthInput, 
	        						  labelHeight, 
	        						  heightInput, 
	        						  labelMines, 
	        						  mineInput, 
	        						  submitButton,
	        						  closeButton);
	        
	        Scene scene = new Scene(grid, 400, 250);												//New scene from GridPane.
	        window.setTitle("Modify...");															//Sets window title.
	        window.setScene(scene);																	//Sets scene.
	        window.showAndWait();
	        
	       return widthLengthMines;																	//Returns user input.
	        
		}
	 
		//Verify input
	    private static boolean isInt(TextField input) {												//Checks if input is an integer.
	    	try {
	    		int number = Integer.parseInt(input.getText());
	    		return true;
	    	}catch(NumberFormatException e) {
	    		return false;
	    	}
	    }
	 
	}
	
	class Timer extends Pane {																		//Timer feature.
		private Timeline timeline = new Timeline();													//Initializes TimeLine.
		private int minute = 0;																		//Sets starting value.
		private int second = 0;																		//^
		private String clock = "";
		private boolean startCounter = false;
		
		Label label = new Label(String.format("%02d:%02d", minute,second));							//Formatting.
		
		public Timer() {																			//Timer-object constructor.
			getChildren().add(label);																//Adds label as child node.
			timeline = new Timeline(new KeyFrame(Duration.seconds(1), e-> counter()));				//Increment timer.
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();																		//Start timer.
			model.getGameStatus().addListener((obs) -> { 											//Listens to changes to BooleanProperty lost.
				timeline.stop();																	//Stops timer.
			});
		}

		public void counter() {
			if(!startCounter) {																		//Checks if timer has started.
				return;
			}
			
			if(second >= 59 ) {																		//Increment minutes.
				minute++;
				second = 0;
			} else {
				second++;																			//Increment seconds.
			}
			clock = String.format("%02d:%02d",minute, second);										//More formatting.
			label.setText(clock);																	//Sets timer label text.
		}
		
		public void setStartCounter(boolean start) {												//Starts timer.
			this.startCounter = start;
		}
	}

	
	class Tile extends StackPane {																	//Tile object-class.
		private Rectangle tile;																		//Rectangle object.
		private Text text;																			//Text object.
		
		ImageView mine = new ImageView(getClass().getResource("Images/mine.png").toString());
		ImageView flag = new ImageView(getClass().getResource("Images/flag.png").toString());
		
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
					getChildren().add(flag);														//Places flag image on tile.
					flagCount.set(flagCount.get() + 1);												//Increments flag counter.
				} else {
					for (int i = 0; i < getChildren().size(); i++) {
						if (getChildren().get(i) == flag) {
							getChildren().remove(i);												//Removes flag from tile.
							flagCount.set(flagCount.get() - 1);										//Decrement flag counter.
						}
					}
				}
			});
			
			getChildren().add(this.tile);															//Adds rectangle as child node to tile.
			
			if (model.getTile(x, y).bombOnTile().get()) {											//Formats bomb image is tile has bomb.
				mine.setFitHeight(tileSize);														//Sets height of image.
				mine.setFitWidth(tileSize);															//Sets width of image.
				mine.setVisible(false);																//Hides image.
				getChildren().add(mine);															//Adds image of bomb as child node to tile.
			} else {																				//Sets string-value of Text-object.
				this.text.setText(controller.getNeighbours(x, y) == 0 ? "" : "" + controller.getNeighbours(x, y));
				text.setVisible(false);
			}
			
			switch (controller.getNeighbours(x, y)) {												//Sets text-color.
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
		
		timer = new Timer();																		//New Timer-object.
		AnchorPane Anchor = new AnchorPane();														//New AcnhorPane
		
		Button restartButton = new Button("Restart"); 												//New restart-Button
		
		model.getGameStatus().addListener((obs,oldInt,newInt) -> {									//Setting collors when game over
			switch (newInt.intValue()) {
				case 1:
					restartButton.setStyle("-fx-background-color: #e31212;");						//Red colloer for lost game
					break;
				case 2:
					restartButton.setStyle("-fx-background-color: #0be31d;");						//Green collor for won game
					break;
			}
		});
		
		restartButton.setOnAction(e-> restart(model.getW().intValue(), 								//Button restarts game when clicked.
									  model.getH().intValue(), 
									  model.getBombCount()));
		
		ImageView flag = new ImageView(getClass().getResource("Images/flag.png").toString());		//Flag image for flag counter.
		
		counter.setGraphic(flag);																	
		flag.setFitHeight(20);																		//Image formatting.
		flag.setFitWidth(20);
		
		this.flagCount.addListener((obs, oldInt, newInt) -> {										//Listens to changes to flagCount.
			this.counter.setText("Remaining bombs: " 												//Flag counter set to sum of amount of bombs and number of placed flags.
								+ (model.getBombCount() - flagCount.intValue()));
		});
		
		AnchorPane.setLeftAnchor(restartButton, Double.valueOf((this.width.intValue()) - 350));		//Button placement.
		AnchorPane.setLeftAnchor(timer, Double.valueOf((this.width.intValue()) - 250));				//^
		AnchorPane.setLeftAnchor(counter, Double.valueOf((this.width.intValue()) - 200));			//^
		
		Anchor.getChildren().addAll(this.menu, counter, timer, restartButton);						//Adds nodes as children to AnchorPane.
		
		this.window.setCenter(gameWindow);															//Sets game window in center of window.
		this.window.setTop(Anchor);																	//Sets AnchorPane at the top of the window.
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