package coffee.presenters;

import coffee.views.ApplicationView;
import coffee.views.MenuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class CoffeePresenter extends BorderPane{

	private MenuView menuView;
	private ApplicationView appView;
	
	public CoffeePresenter() {
		configureMenu();
		setCenter(menuView);
	}
	
	private void configureStartButton(Button button, BorderPane pane) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				appView = new ApplicationView();
				pane.setCenter(appView);
			}
		});
	}
	
	private void configureMenu() {
		menuView = new MenuView();
		Button startButton = menuView.getButton();
		configureStartButton(startButton, this);
	}
	
	public Button getButton() {
		return menuView.getButton();
	}
}
