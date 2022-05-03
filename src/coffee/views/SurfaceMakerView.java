package coffee.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SurfaceMakerView extends BorderPane {
	
	private GridPane upperGrid;
	private ComboBox<String> comboBox;
	private RevolveMakerView revolveMakeView;
	private RuleMakerView RuleMakeView;
	public static final String OPTION_1 = "Revolved Surface";
	public static final String OPTION_2 = "Ruled Surface"; 

	public SurfaceMakerView() {
		super();
		initGrid();
		initComboBox();
		initViews();
	}

	public void initGrid() {
		upperGrid = new GridPane();
		upperGrid.setAlignment(Pos.CENTER);
		upperGrid.setPadding(new Insets(20, 0, 20, 0));
		upperGrid.setVgap(10);
		upperGrid.setHgap(30);
		setTop(upperGrid);
	}
	
	private void initComboBox() {
		ObservableList<String> optionsList = FXCollections.observableArrayList(OPTION_1, OPTION_2);
		ComboBox<String> comboBox = new ComboBox<String>(optionsList);
		comboBox.setPromptText("Surface Style");
		comboBox.setOnHidden(new EventHandler<Event>() {
			public void handle(Event event) {
				loadView();
			}
		});
		this.comboBox = comboBox;
		upperGrid.add(new Label("Surface Style: "), 0, 0);
		upperGrid.add(comboBox, 1,0);
	}
	
	private void initViews() {
		revolveMakeView = new RevolveMakerView();
		RuleMakeView = new RuleMakerView();
	}
	
	private void loadView() {
		if (comboBox.getValue() == null)
			return;
		if (comboBox.getValue().equals(OPTION_1))
			setCenter(revolveMakeView);
		if (comboBox.getValue().equals(OPTION_2))
			setCenter(RuleMakeView);
	}
}
