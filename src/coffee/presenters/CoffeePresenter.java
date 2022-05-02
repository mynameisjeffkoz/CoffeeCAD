package coffee.presenters;

import coffee.views.MenuView;
import javafx.scene.layout.BorderPane;

public class CoffeePresenter extends BorderPane{

	private MenuView menuView;
	
	public CoffeePresenter() {
		configureMenu();
		setCenter(menuView);
	}
	
	private void configureMenu() {
		menuView = new MenuView();
	}
}
