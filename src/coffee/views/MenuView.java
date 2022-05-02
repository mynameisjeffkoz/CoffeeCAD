package coffee.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MenuView extends BorderPane{

	private ImageView logoView;
	private HBox hbox;
	private Button startButton;
	private static final String LOGO_PATH = "logo.png";
	
	public MenuView() {
		super();
		try {
			logoView = readImage(LOGO_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		configureHBox();
		startButton = new Button("Start");
		setCenter(logoView);
		setBottom(hbox);
	}
	
	public Button getButton() {
		return startButton;
	}
	
	private ImageView readImage(String filename) throws FileNotFoundException {
		InputStream stream = new FileInputStream(filename);
		Image logo = new Image(stream);
		ImageView view = new ImageView();
		view.setImage(logo);
		view.setFitWidth(800);
		view.setPreserveRatio(true);
		return view;
	}
	
	private Button configureButton(String text) {
		Button button = new Button(text);
		return button;
	}
	
	private void configureHBox() {
		hbox = new HBox();
		startButton = configureButton("Start");
		hbox.getChildren().add(startButton);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(20, 0, 20, 0));
	}
}
