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
		HBox selectorBox = new HBox();
		selectorBox.setAlignment(Pos.CENTER);
		selectorBox.setPadding(new Insets(20, 0, 20, 0));
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
		selectorBox.getChildren().add(comboBox);
		setTop(selectorBox);
	}

	private void selectCurveStyle(String curveStyle) {
		if (curveStyle == null)
			return;
		//TODO: modify behavior here
		if (curveStyle.equals(OPTION_1))
			setCenter(new Label(OPTION_1));
		if (curveStyle.equals(OPTION_2))
			setCenter(new Label(OPTION_2));
	}
}
