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
	public static final String MENU_ITEM_CURV = "MENU_ITEM_CURV";
	public static final String MENU_ITEM_SURF = "MENU_ITEM_SURF";
	
	
	public ApplicationView() {
		super();
		configureMenuBar();
		setTop(menuBar);
		setCenter(new Button("Button!"));
	}
	
	private void configureMenuBar() {
		menuMap = new HashMap<String,MenuItem>();
		Menu fileMenu = createFileMenu();
		Menu viewMenu = createViewMenu();
		menuBar = new MenuBar(fileMenu, viewMenu);
	}
	
	public MenuItem getMenuItem(String name) {
		return menuMap.getOrDefault(name, null);
	}
	
	private Menu createFileMenu() {
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
		
		menuMap.put(MENU_ITEM_OPEN, open);
		menuMap.put(MENU_ITEM_SAVE, save);
		menuMap.put(MENU_ITEM_EXIT, exit);
		return menu;
	}
	
	private Menu createViewMenu() {
		Menu menu = new Menu("_View");
		MenuItem curve = new MenuItem("_Curve");
		MenuItem surface = new MenuItem("_Surface");
		menu.getItems().addAll(curve, surface);
		menuMap.put(MENU_ITEM_CURV, curve);
		menuMap.put(MENU_ITEM_SURF, surface);
		return menu;
	}
}
