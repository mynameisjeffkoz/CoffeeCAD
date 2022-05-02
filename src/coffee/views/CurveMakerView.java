package coffee.views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CurveMakerView extends BorderPane {

	private PointCurveView ptCurvView;
	private bSplineCurveView bSplineView;
	private GridPane upperGrid;
	private GridPane lowerGrid;
	private static final String OPTION_1 = "Point-Driven Spline";
	private static final String OPTION_2 = "B-Spline Curve";
	private static final int MIN_CONTROL_POINTS = 2;
	private static final int MAX_CONTROL_POINTS = 4;

	public CurveMakerView() {
		super();
		init();
	}

	private void init() {
		initUpperGrid();
		makeSelectorBox();
		makeNumPointsSpinner();
		initLowerGrid();
		addControlButtons();
	}

	private void initUpperGrid() {
		upperGrid = new GridPane();
		upperGrid.setAlignment(Pos.CENTER);
		upperGrid.setPadding(new Insets(20, 0, 20, 0));
		upperGrid.setVgap(10);
		upperGrid.setHgap(10);
		setTop(upperGrid);
	}

	private void makeSelectorBox() {
		ObservableList<String> optionsList = FXCollections.observableArrayList(OPTION_1, OPTION_2);
		ComboBox<String> comboBox = new ComboBox<String>(optionsList);
		comboBox.setPromptText("Curve Style");
		Label label = new Label("Curve Style:");
		upperGrid.add(label, 0, 0);
		upperGrid.add(comboBox, 1, 0);
	}
	
	@SuppressWarnings("unchecked")
	private String readSelectorBox() {
		return ((ComboBoxBase<String>) upperGrid.getChildren().get(1)).getValue();
	}

	private void selectCurveStyle(String curveStyle, int numPoints) {
		if (curveStyle == null)
			return;
		// TODO: modify behavior here
		if (curveStyle.equals(OPTION_1))
			setCenter(pointCurvePane(numPoints));
		if (curveStyle.equals(OPTION_2))
			setCenter(bSplinePane(numPoints));
	}

	private void makeNumPointsSpinner() {
		Spinner<Integer> spinner = new Spinner<Integer>(MIN_CONTROL_POINTS, MAX_CONTROL_POINTS, MIN_CONTROL_POINTS);
		Label label = new Label("# of Control Points:");
		upperGrid.add(label, 0, 1);
		upperGrid.add(spinner, 1, 1);
	}
	
	@SuppressWarnings("unchecked")
	private int readSpinner() {
		return ((Spinner<Integer>) upperGrid.getChildren().get(3)).getValue();
	}

	private GridPane pointCurvePane(int numPoints) {
		ptCurvView = new PointCurveView(numPoints);
		return ptCurvView;
	}

	private GridPane bSplinePane(int numPoints) {
		return null;
	}

	private void initLowerGrid() {
		lowerGrid = new GridPane();
		lowerGrid.setAlignment(Pos.CENTER);
		lowerGrid.setPadding(new Insets(20, 0, 20, 0));
		lowerGrid.setVgap(10);
		lowerGrid.setHgap(10);
		setBottom(lowerGrid);
	}

	private void addControlButtons() {
		Button saveButton = new Button("Save");
		Button updateButton = new Button("Update");
		saveButton.setMinWidth(updateButton.getWidth());
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveHandler();
			}
		});
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectCurveStyle(readSelectorBox(), readSpinner());
			}
		});
		lowerGrid.addRow(0, saveButton, updateButton);
	}
	
	private void saveHandler() {
		PointCurveView view = (PointCurveView) getChildren().get(2);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File file = new File("./Curves/");
		file.mkdir();
		fileChooser.setInitialDirectory(file);
		try {
			FileWriter writer = new FileWriter(fileChooser.showSaveDialog(getScene().getWindow()).toString());
			writer.write(view.toString());
			writer.close();
		} catch (IOException e) {
			// No code to execute on failure
		}
	}
}
