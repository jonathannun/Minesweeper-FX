import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PopUpBox {
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