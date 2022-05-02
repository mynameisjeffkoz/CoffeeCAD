package coffee.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CurveMakerView extends BorderPane {

	private GridPane grid;
	private HBox hbox;
	private static final String OPTION_1 = "Point-Driven Spline";
	private static final String OPTION_2 = "B-Spline Curve";

	public CurveMakerView() {
		super();
		init();
	}

	private void init() {
		grid = new GridPane();
		hbox = new HBox();
		makeSelectorBox();
	}

	private void makeSelectorBox() {
		ObservableList<String> optionsList = FXCollections.observableArrayList(OPTION_1, OPTION_2);
		ComboBox<String> comboBox = new ComboBox<String>(optionsList);
		setTop(comboBox);
	}
}
