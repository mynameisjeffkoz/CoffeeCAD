package coffee.views;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class ApplicationView extends BorderPane {

	private MenuBar menuBar;
	private HashMap<String, MenuItem> menuMap;
	public static final String MENU_ITEM_OPEN = "MENU_ITEM_OPEN";
	public static final String MENU_ITEM_SAVE = "MENU_ITEM_SAVE";
	public static final String MENU_ITEM_EXIT = "MENU_ITEM_EXIT";
	
	
	public ApplicationView() {
		super();
		configureMenuBar();
		setTop(menuBar);
		setCenter(new Button("Button!"));
	}
	
	private void configureMenuBar() {
		Menu menu = new Menu("_File");
		MenuItem open = new MenuItem("_Open");
		MenuItem save = new MenuItem("_Save");
		MenuItem exit = new MenuItem("_Exit");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
		menu.getItems().addAll(open, save, exit);
		menuMap = new HashMap<String,MenuItem>();
		menuMap.put(MENU_ITEM_OPEN, open);
		menuMap.put(MENU_ITEM_SAVE, save);
		menuMap.put(MENU_ITEM_EXIT, exit);
		menuBar = new MenuBar(menu);
	}
	
	public MenuItem getMenuItem(String name) {
		return menuMap.getOrDefault(name, null);
	}
}
