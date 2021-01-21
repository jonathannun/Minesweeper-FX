import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	@Override
	public void start(Stage stage) {
		Model model = new Model(9, 9, 10);														//Model-object intialized
		Controller controller = new Controller(model);												//Controller-object initialized with access to Model-object.
		View view = new View(model, controller);													//View-object initialized with access to both Model-, and View-object.
		
		Scene scene = new Scene(view.asParent());													//Scene get from View-object.
		
		view.getWidth().addListener((obs, oldW, newW) -> {											//Listens to changes in scene width.
			stage.setWidth(newW.doubleValue() <500 ? 500 : newW.doubleValue());						//Updates stage width.
			});
		view.getHeight().addListener((obs, oldH, newH) -> {											//Listens to changes in scene height.
			stage.setHeight(newH.doubleValue());													//Updates stage height.
			});
		
		stage.setScene(scene);																		//Sets scene.
		stage.show();																				//Show.
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
