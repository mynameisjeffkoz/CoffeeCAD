import coffee.presenters.CoffeePresenter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		CoffeePresenter presenter = new CoffeePresenter();
		Pane pane = presenter;
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CoffeeCAD");
		primaryStage.show();
	}

}
