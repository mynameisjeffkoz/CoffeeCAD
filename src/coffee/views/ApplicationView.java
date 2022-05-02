package coffee.views;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ApplicationView extends BorderPane {

	Button backButton;
	
	public ApplicationView() {
		super();
		configureBackButton();
	}
	
	private void configureBackButton() {
		backButton = new Button("Back");
	}
}
