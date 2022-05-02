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
	private CurveMakerView curvView;
	private SurfaceMakerView surfView;
	private PlotCurveView plotCurvView;
	private PlotSurfaceView plotSurfView;
	public static final String MENU_ITEM_OPEN = "MENU_ITEM_OPEN";
	public static final String MENU_ITEM_SAVE = "MENU_ITEM_SAVE";
	public static final String MENU_ITEM_EXIT = "MENU_ITEM_EXIT";
	public static final String MENU_ITEM_CURV = "MENU_ITEM_CURV";
	public static final String MENU_ITEM_SURF = "MENU_ITEM_SURF";
	public static final String MENU_ITEM_PLOTC = "MENU_ITEM_PLOTC";
	public static final String MENU_ITEM_PLOTS = "MENU_ITEM_PLOTS";
	
	
	public ApplicationView() {
		super();
		configureMenuBar();
		setTop(menuBar);
		initViews();
		setCenter(curvView);
	}
	
	private void configureMenuBar() {
		menuMap = new HashMap<String,MenuItem>();
		Menu fileMenu = createFileMenu();
		Menu makeMenu = createMakeMenu();
		Menu plotMenu = createPlotMenu();
		menuBar = new MenuBar(fileMenu, makeMenu, plotMenu);
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
	
	private Menu createMakeMenu() {
		Menu menu = new Menu("_Make");
		MenuItem curve = new MenuItem("_Curve");
		MenuItem surface = new MenuItem("_Surface");
		curve.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setCenter(curvView);
			}
		});
		surface.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setCenter(surfView);
			}
		});
		menu.getItems().addAll(curve, surface);
		menuMap.put(MENU_ITEM_CURV, curve);
		menuMap.put(MENU_ITEM_SURF, surface);
		return menu;
	}
	
	private Menu createPlotMenu() {
		Menu menu = new Menu("_Plot");
		MenuItem plotCurve = new MenuItem("_Curve");
		MenuItem plotSurface = new MenuItem("_Surface");
		plotCurve.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setCenter(plotCurvView);
			}
		});
		plotSurface.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setCenter(plotSurfView);
			}
		});
		menu.getItems().addAll(plotCurve, plotSurface);
		menuMap.put(MENU_ITEM_PLOTC, plotCurve);
		menuMap.put(MENU_ITEM_PLOTS, plotSurface);
		return menu;
	}
	
	private void initViews() {
		curvView = new CurveMakerView();
		surfView = new SurfaceMakerView();
		plotCurvView = new PlotCurveView();
		plotSurfView = new PlotSurfaceView();
	}
}
