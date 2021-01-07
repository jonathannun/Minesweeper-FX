import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpBox {
	
	public static void show() {
		Stage window = new Stage();
        
        //Placement of width-label
        Label labelWidth = new Label("Insert the width of your grid: ");
        GridPane.setConstraints(labelWidth, 0, 0);
        ComboBox<String> width = new ComboBox<>();
        width.getItems().addAll(
        		"10", 
        		"20", 
        		"30"
        );
        width.setEditable(true);
        GridPane.setConstraints(width, 1, 0);
        
        
        //Placement of length-label
        Label labelLength = new Label("Insert the lenght of your grid: ");
        GridPane.setConstraints(labelLength, 0, 1);
        ComboBox<String> length = new ComboBox<>();
        length.getItems().addAll(
        		"10", 
        		"20", 
        		"30"
        );
        length.setEditable(true);
        GridPane.setConstraints(length, 1, 1);
        
        
        //Placement of mines
        Label labelMines = new Label("Insert the number of mines: ");
        GridPane.setConstraints(labelMines, 0, 2);
        ComboBox<String> mines = new ComboBox<>();
        mines.getItems().addAll(
        		"5", 
        		"10", 
        		"20"
        );
        mines.setEditable(true);
        GridPane.setConstraints(mines, 1, 2);
        
        //Submit button to close the window.
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 3);
        submitButton.setOnAction(e -> window.close());
        
        
        //Grid
        GridPane grid = new GridPane();
        grid.getChildren().addAll(labelWidth, width, labelLength, length, labelMines, mines, submitButton);
        Scene scene = new Scene(grid, 400, 250);
        window.setTitle("Modify...");
        window.setScene(scene);
        window.show();
	}
}
