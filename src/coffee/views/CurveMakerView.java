package coffee.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CurveMakerView extends BorderPane {

	private GridPane grid;
	private static final String OPTION_1 = "Point-Driven Spline";
	private static final String OPTION_2 = "B-Spline Curve";
	private static final int MIN_CONTROL_POINTS = 2;
	private static final int MAX_CONTROL_POINTS = 4;

	public CurveMakerView() {
		super();
		init();
	}

	private void init() {
		initGrid();
		makeSelectorBox();
		makeNumPointsSpinner();
	}

	private void initGrid() {
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(20, 0, 20, 0));
		grid.setVgap(10);
		grid.setHgap(10);
		setTop(grid);
	}

	private void makeSelectorBox() {
		ObservableList<String> optionsList = FXCollections.observableArrayList(OPTION_1, OPTION_2);
		ComboBox<String> comboBox = new ComboBox<String>(optionsList);
		comboBox.setPromptText("Curve Style");
		comboBox.setOnHidden(new EventHandler<Event>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				selectCurveStyle(((ComboBoxBase<String>) event.getSource()).getValue());
			}
		});
		Label label = new Label("Curve Style:");
		grid.add(label, 0, 0);
		grid.add(comboBox, 1, 0);
	}

	private void selectCurveStyle(String curveStyle) {
		if (curveStyle == null)
			return;
		// TODO: modify behavior here
		if (curveStyle.equals(OPTION_1))
			setCenter(pointCurvePane());
		if (curveStyle.equals(OPTION_2))
			setCenter(bSplinePane());
	}

	private void makeNumPointsSpinner() {
		Spinner<Integer> spinner = new Spinner<Integer>(MIN_CONTROL_POINTS, MAX_CONTROL_POINTS, MIN_CONTROL_POINTS);
		Label label = new Label("# of Control Points:");
		grid.add(label, 0, 1);
		grid.add(spinner, 1, 1);
	}
	
	private GridPane pointCurvePane() {
		return null;
	}
	
	private GridPane bSplinePane() {
		return null;
	}
}
