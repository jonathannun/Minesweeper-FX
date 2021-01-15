import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.geometry.*;

public class ErrorMessage extends TextFlow{

	public static void display( String message) {
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Error Message");
		stage.setMinWidth(300);
		stage.setMinHeight(250);
		
		Label label = new Label();
		label.setText(message);
		label.setFont(Font.font( 14));
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
