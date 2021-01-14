import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{

	@Override
	public void start(Stage stage) {
		Model model = new Model(20, 20, 40);
		Controller controller = new Controller(model);
		View view = new View(model, controller);
		
		Scene scene = new Scene(view.asParent());
		
		view.getWidth().addListener((obs, oldW, newW) -> {
			stage.setWidth(newW.doubleValue() + 16);
			});
		view.getHeight().addListener((obs, oldH, newH) -> {
			stage.setHeight(newH.doubleValue() + 39);
			});
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
