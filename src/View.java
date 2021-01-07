import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.layout.*; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.*; 
import javafx.stage.Stage; 
import javafx.scene.control.Alert.AlertType; 
import java.time.LocalDate; 

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class View extends Application{
	
	Stage window;
	Scene scene1;
	
	public static void main (String[] args) {
		launch(args);
	}
	public void start(Stage stage) {
		window = stage;
		stage.setTitle("MineSweeper");

		//Menu
		Menu menu = new Menu("Difficulty Level...");
		Menu flagLeftButton = new Menu("Remainder Flags");
		Menu restartButton = new Menu("Restart");
		Menu timeButton = new Menu("Time");
		Menu mineFallButton = new Menu("MineFall");
		
		//Create menuItems for menu1
		MenuItem m = new MenuItem("Modify...");

		
		//Add menu-items to the menu object
		menu.getItems().add(m);
		
		//MenuBar
		MenuBar menuBar = new MenuBar();
		
		//Add the menu full of items to the menu-bar
		menuBar.getMenus().addAll(menu, flagLeftButton, restartButton, timeButton, mineFallButton);
		
		
		//Pop-up window - Difficulty Level
		m.setOnAction(e -> PopUpBox.show());
		
		//GridPane
		GridPane grid = new GridPane();
		grid.getChildren().add(menuBar);
		//Binds the menuBar to the width of the window
		menuBar.prefWidthProperty().bind(stage.widthProperty());
		//Sets the scene
		scene1 = new Scene(grid, 500, 500);
		stage.setScene(scene1);
		stage.show();
		
	}
}
