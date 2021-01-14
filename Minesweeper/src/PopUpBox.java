import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
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
        Label labelLength = new Label("Insert the lenght of your grid: ");
        GridPane.setConstraints(labelLength, 0, 1);
        labelLength.setFont(Font.font(14));
        TextField lengthInput = new TextField();
        GridPane.setConstraints(lengthInput, 1, 1);
        
        
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
        	if(widthInput.getText().trim().isEmpty()||lengthInput.getText().trim().isEmpty()||mineInput.getText().trim().isEmpty()) {
        		ErrorMessage.display("You haven't inserted anything!\n       Please Insert A Number" );
        	}
        	//Checking if input is int
        	else {
        	isInt(widthInput);
        	isInt(lengthInput);
        	isInt(mineInput);
        	
        	//If input is int
        	if (isInt(widthInput)&&
        	isInt(lengthInput)&&
        	isInt(mineInput)) {
        	widthLengthMines[0] = Integer.parseInt(widthInput.getText());
        	widthLengthMines[1] = Integer.parseInt(lengthInput.getText());
        	widthLengthMines[2]= Integer.parseInt(mineInput.getText());
        	window.close();
        	}
        	//If not
        	else {
        		ErrorMessage.display("Please Insert A Number" );
        	}
        	
        	}
        });
         
        //Grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
		grid.setVgap(8);
		grid.setHgap(10);
        grid.getChildren().addAll(labelWidth, widthInput, labelLength, lengthInput, labelMines, mineInput, submitButton,closeButton);
        Scene scene = new Scene(grid, 400, 250);
        window.setTitle("Modify...");
        window.setScene(scene);
        window.showAndWait();
       return widthLengthMines ;
        
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