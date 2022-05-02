package coffee.views;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PlotCurveView extends GridPane {

	private MatlabEngine engine;
	private ComboBox<String> comboBox;
	private Button loadButton;
	private Button plotButton;
	private Label dataLabel;
	private String data;
	private static final String OPTION_1 = "Point-Driven Spline";
	private static final String OPTION_2 = "B-Spline Curve";

	public PlotCurveView() {
		super();
		initGrid();
		initButtons();
		add(comboBox, 0, 0);
		add(loadButton, 0, 1);
		add(dataLabel, 0, 2);
		add(plotButton, 0, 3);
	}

	private void initGrid() {
		try {
			engine = MatlabEngine.startMatlab();
		} catch (EngineException | IllegalArgumentException | IllegalStateException | InterruptedException e) {
			e.printStackTrace();
		}
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20, 0, 20, 0));
		setVgap(10);
		setHgap(30);
	}

	private void initButtons() {
		initComboBox();
		initLoadButton();
		initPlotButton();
		dataLabel = new Label();
	}

	private void initComboBox() {
		ObservableList<String> optionsList = FXCollections.observableArrayList(OPTION_1, OPTION_2);
		ComboBox<String> comboBox = new ComboBox<String>(optionsList);
		comboBox.setPromptText("Curve Style");
		this.comboBox = comboBox;
	}

	private void initLoadButton() {
		Button loadButton = new Button("Load");
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				loadHandler();
			}
		});
		this.loadButton = loadButton;
	}
	
	private void initPlotButton() {
		plotButton = new Button("Plot");
		plotButton.setOnAction(new EventHandler<ActionEvent> () {
			public void handle(ActionEvent event) {
				plotHandler();
			}
		});
	}

	private void loadHandler() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File defaultLocation = new File("./Curves/");
		defaultLocation.mkdir();
		fileChooser.setInitialDirectory(defaultLocation);
		File toRead = new File((fileChooser.showOpenDialog(getScene().getWindow()).toString()));
		try {
			this.data = Files.readString(toRead.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataLabel.setText(data);
	}
	
	private void plotHandler() {
		try {
			StringWriter writer = new StringWriter();
			engine.eval("x = " + data, writer, null);
			engine.eval("plot3dmat(x)");
			System.out.println(writer.toString());
		} catch (CancellationException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
