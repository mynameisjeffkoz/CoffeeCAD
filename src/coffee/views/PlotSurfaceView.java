package coffee.views;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import com.mathworks.engine.MatlabEngine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PlotSurfaceView extends GridPane {

	MatlabEngine engine;
	private ComboBox<String> surfaceBox;
	private ComboBox<String> curveBox;
	private Button loadButton;
	private Label dataLabel;
	private Button plotButton;
	private String data;
	public static final String S_OPTION_1 = "Revolved";
	public static final String S_OPTION_2 = "Ruled";
	private static final String C_OPTION_1 = "Point-Driven Spline";
	private static final String C_OPTION_2 = "B-Spline Curve";

	public PlotSurfaceView(MatlabEngine engine) {
		super();
		this.engine = engine;
		initGrid();
		initButtons();
		add(surfaceBox, 0, 0);
		add(curveBox, 0, 1);
		add(loadButton, 0, 2);
		add(dataLabel, 0, 3);
		add(plotButton, 0, 4);
	}

	private void initGrid() {
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20, 0, 20, 0));
		setVgap(10);
		setHgap(30);
	}

	private void initButtons() {
		initSurfaceBox();
		initLoadButton();
		initPlotButton();
		initCurveBox();
		dataLabel = new Label();
	}

	private void initSurfaceBox() {
		ObservableList<String> optionsList = FXCollections.observableArrayList(S_OPTION_1, S_OPTION_2);
		ComboBox<String> comboBox = new ComboBox<String>(optionsList);
		comboBox.setPromptText("Surface Style");
		this.surfaceBox = comboBox;
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
		plotButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				plotHandler();
			}
		});
	}

	private void initCurveBox() {
		ObservableList<String> optionsList = FXCollections.observableArrayList(C_OPTION_1, C_OPTION_2);
		ComboBox<String> comboBox = new ComboBox<String>(optionsList);
		comboBox.setPromptText("Curve Style");
		this.curveBox = comboBox;
	}

	private void loadHandler() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File defaultLocation = new File("./Surfaces/");
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
		if (surfaceBox.getValue().equals(S_OPTION_1))
			plotRevolve();
		if (surfaceBox.getValue().equals(S_OPTION_2))
			plotRuled();
	}

	private void plotRevolve() {
		if (curveBox.getValue().equals(C_OPTION_1))
			plotSplineRevolve();
		if (curveBox.getValue().equals(C_OPTION_2)) {
			plotBSplineRevolve();
		}
	}

	private void plotRuled() {
		if (curveBox.getValue().equals(C_OPTION_1))
			plotSplineRule();
		if (curveBox.getValue().equals(C_OPTION_2))
			plotBSplineRule();
	}

	private void plotSplineRule() {
		Scanner scnr = new Scanner(data);
		if (!scnr.nextLine().equals(S_OPTION_2)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Invalid Surface Selection");
			alert.setContentText("You have selected a Surface which does not match the chosen parameters");
			alert.showAndWait();
			scnr.close();
			return;
		}
		int numCurves = scnr.nextInt();
		if (numCurves != 2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Invalid Surface Selection");
			alert.setContentText("CoffeeCAD currently only supports ruled surfaces between two similar curves");
			alert.showAndWait();
			scnr.close();
		}
		scnr.nextLine();
		int numPoints = scnr.nextInt();
		scnr.nextLine();
		StringJoiner arrayMaker = new StringJoiner(";");
		for (int i = 0; i < numPoints; ++i) {
			arrayMaker.add(scnr.nextLine());
		}
		String points1 = arrayMaker.toString();
		System.out.println(points1);
		arrayMaker = new StringJoiner(";");
		scnr.nextLine();
		for (int i = 0; i < numPoints; ++i) {
			arrayMaker.add(scnr.nextLine());
		}
		String points2 = arrayMaker.toString();
		scnr.close();
		StringWriter writer = new StringWriter();
		try {
			engine.eval("P = " + points1 + "", writer, null);
			engine.eval("Q = " + points2, writer, null);
			if (numPoints == 2) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Not Yet Implemented");
				alert.setContentText("We didn't think this was interesting enough to implement.");
				alert.showAndWait();
			} else if (numPoints == 3)
				engine.eval("Ruled_qSpline(P,Q)");
			else if (numPoints == 4)
				engine.eval("Ruled_cSpline(P,Q)");
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Size not permitted");
				alert.setContentText("This application currently supports only linear, quadratic, and cubic Splines");
				alert.showAndWait();
			}
			System.out.println(writer.toString());
		} catch (CancellationException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void plotBSplineRule() {
		Scanner scnr = new Scanner(data);
		if (!scnr.nextLine().equals(S_OPTION_2)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Invalid Surface Selection");
			alert.setContentText("You have selected a Surface which does not match the chosen parameters");
			alert.showAndWait();
			scnr.close();
			return;
		}
		int numCurves = scnr.nextInt();
		if (numCurves != 2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Invalid Surface Selection");
			alert.setContentText("CoffeeCAD currently only supports ruled surfaces between two similar curves");
			alert.showAndWait();
			scnr.close();
		}
		scnr.nextLine();
		int numPoints = scnr.nextInt();
		scnr.nextLine();
		int degree = scnr.nextInt();
		scnr.nextLine();
		StringJoiner arrayMaker = new StringJoiner(";");
		for (int i = 0; i < numPoints; ++i) {
			arrayMaker.add(scnr.nextLine());
		}
		String points1 = arrayMaker.toString();
		arrayMaker = new StringJoiner(";");
		scnr.nextLine();
		scnr.nextLine();
		for (int i = 0; i < numPoints; ++i) {
			arrayMaker.add(scnr.nextLine());
		}
		String points2 = arrayMaker.toString();
		scnr.close();
		StringWriter writer = new StringWriter();
		try {
			engine.eval("P = " + points1 + "", writer, null);
			engine.eval("Q = " + points2, writer, null);
			if (degree == 2) {
				engine.eval("Ruled_quadBspline(P,Q)");
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Size not permitted");
				alert.setContentText("This application currently supports only quadratic ruled B-Splines");
				alert.showAndWait();
			}
			System.out.println(writer.toString());
		} catch (CancellationException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void plotSplineRevolve() {
		Scanner scnr = new Scanner(data);
		if (!scnr.nextLine().equals(S_OPTION_1)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Invalid Surface Selection");
			alert.setContentText("You have selected a Surface which does not match the chosen parameters");
			alert.showAndWait();
			scnr.close();
			return;
		}
		String lowerBound = scnr.next();
		String upperBound = scnr.next();
		scnr.nextLine();
		int numPoints = scnr.nextInt();
		scnr.nextLine();
		String dataPoints = "";
		while (scnr.hasNextLine()) {
			dataPoints += scnr.nextLine();
			if (scnr.hasNextLine())
				dataPoints += System.lineSeparator();
		}
		scnr.close();
		StringWriter writer = new StringWriter();
		try {
			engine.eval("x = " + dataPoints + "", writer, null);
			engine.eval("lb = " + lowerBound, writer, null);
			engine.eval("ub = " + upperBound, writer, null);
			if (numPoints == 2)
				// TODO: new function here
				engine.eval("RS_lSpline(lb, ub, x)");
			else if (numPoints == 3)
				engine.eval("RS_qSpline(lb, ub, x)");
			else if (numPoints == 4)
				engine.eval("RS_cSpline(lb, ub, x)");
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Size not permitted");
				alert.setContentText("This application currently supports only linear, quadratic, and cubic Splines");
				alert.showAndWait();
			}
			System.out.println(writer.toString());
		} catch (CancellationException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void plotBSplineRevolve() {
		Scanner scnr = new Scanner(data);
		if (!scnr.nextLine().equals(S_OPTION_1)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Invalid Surface Selection");
			alert.setContentText("You have selected a Surface which does not match the chosen parameters");
			alert.showAndWait();
			scnr.close();
			return;
		}
		String lowerBound = scnr.next();
		String upperBound = scnr.next();
		scnr.nextLine();
		scnr.nextLine();
		int degree = scnr.nextInt();
		scnr.nextLine();
		String dataPoints = "";
		while (scnr.hasNextLine()) {
			dataPoints += scnr.nextLine();
			if (scnr.hasNextLine())
				dataPoints += System.lineSeparator();
		}
		scnr.close();
		StringWriter writer = new StringWriter();
		try {
			engine.eval("x = " + dataPoints + "", writer, null);
			engine.eval("lb = " + lowerBound, writer, null);
			engine.eval("ub = " + upperBound, writer, null);
			if (degree == 2)
				// TODO: new function here
				engine.eval("RS_quadBspline(lb, ub, x)");
			else if (degree == 3)
				engine.eval("RS_cubBspline(lb, ub, x)");
			System.out.println(writer.toString());
		} catch (CancellationException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
