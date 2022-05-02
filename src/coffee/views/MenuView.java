package coffee.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MenuView extends BorderPane{

	private ImageView logoView;
	private Button startButton;
	private HBox hbox;
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
	
	private void configureButton() {
		startButton = new Button("Start");
	}
	
	private void configureHBox() {
		hbox = new HBox();
		hbox.setPadding(new Insets(20,0,20,0));
		hbox.setAlignment(Pos.CENTER);
		configureButton();
		hbox.getChildren().add(startButton);
	}
}
