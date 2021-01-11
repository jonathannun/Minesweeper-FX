import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler; 
import javafx.scene.control.*; 
import javafx.stage.Stage; 
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class View extends Application{
	
	Stage window;
	Scene scene1;
	
	public static void main (String[] args) {
		launch(args);

	}
	public void start(Stage stage) {
		window = stage;
		window.setTitle("MineSweeper");

		
		//Menu
		Menu menu = new Menu("Difficulty Level...");
		Menu flagLeftButton = new Menu("Remainder Flags");
		Menu restartButton = new Menu("Restart");
		Menu timeButton = new Menu("Time");
		Menu mineFallButton = new Menu("MineFall");
		
		//Create menuItems for Difficult settings
		RadioMenuItem m = new RadioMenuItem("Modify...");
		ToggleGroup difficultyLevelGroup = new ToggleGroup(); 
		
		//Pop-up window - Modify Level
				m.setOnAction(e -> {
					PopUpBox.show();
					int[] widthLengthMines = PopUpBox.show();
					if(widthLengthMines[0]==0||widthLengthMines[1]==0||widthLengthMines[2]==0) {
						System.out.println("empty");
					}
					else {
						System.out.println(widthLengthMines[0]+" "+ widthLengthMines[1]+" "+widthLengthMines[2]);
					}
					
				});
		
		//
		RadioMenuItem easy = new RadioMenuItem("Easy");
		easy.setOnAction(e -> System.out.println("easy"));
		RadioMenuItem medium = new RadioMenuItem("Medium");
		medium.setOnAction(e -> System.out.println("medium"));
		RadioMenuItem hard = new RadioMenuItem("Hard");
		hard.setOnAction(e -> System.out.println("hard"));
		
		//Add to the ToogleGroup
		m.setToggleGroup(difficultyLevelGroup);
		easy.setToggleGroup(difficultyLevelGroup);
		medium.setToggleGroup(difficultyLevelGroup);
		hard.setToggleGroup(difficultyLevelGroup);
		
		//Add menu-items to the menu object
		menu.getItems().addAll(m,easy,medium,hard);
		
		
		//MenuBar
		MenuBar menuBar = new MenuBar();
		
		
		//Add the menu full of items to the menu-bar
		menuBar.getMenus().addAll(menu, flagLeftButton,restartButton ,timeButton, mineFallButton);
		
	
		//GridPane
		GridPane.setConstraints(menuBar, 2,2);
		GridPane grid = new GridPane();
		grid.getChildren().add(menuBar);
		
		//Binds the menuBar to the width of the window
		menuBar.prefWidthProperty().bind(stage.widthProperty());
		//Sets the scene
		scene1 = new Scene(grid, 500, 500);
		window.setScene(scene1);
		window.show();
		
	}
	
	
}
