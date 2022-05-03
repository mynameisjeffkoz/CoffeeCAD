package coffee.views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.StringJoiner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class RuleMakerView extends BorderPane{
	
	private Spinner<Integer> spinner;
	private Button updateButton;
	private GridPane upperGrid;
	private GridPane centerGrid;
	private ArrayList<String> dataList;
	private Button saveButton;
	
	public RuleMakerView() {
		super();
		initUpperGrid();
		initCenterGrid();
		initSpinner();
		initSaveButton();
	}
	
	private void initUpperGrid() {
		upperGrid = new GridPane();
		upperGrid.setAlignment(Pos.CENTER);
		upperGrid.setPadding(new Insets(20, 0, 20, 0));
		upperGrid.setVgap(10);
		upperGrid.setHgap(30);
		setTop(upperGrid);
	}
	
	private void initCenterGrid() {
		centerGrid = new GridPane();
		centerGrid.setAlignment(Pos.CENTER);
		centerGrid.setPadding(new Insets(20, 0, 20, 0));
		centerGrid.setVgap(10);
		centerGrid.setHgap(30);
		setCenter(centerGrid);
	}
	
	private void initSpinner() {
		Label spinnerLabel = new Label("Number of Curves:");
		spinner = new Spinner<Integer>(2, Integer.MAX_VALUE, 2);
		updateButton = new Button("Update");
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				updateHandler();
			}
		});
		upperGrid.addRow(0, spinnerLabel, spinner, updateButton);
	}
	
	private void updateHandler() {
		initCenterGrid();
		makeLines();
	}
	
	private void makeLines() {
		int numLines = spinner.getValue();
		dataList = new ArrayList<String>(numLines);
		for (int row = 0; row < numLines; ++row) {
			initLoad(row);
		}
	}
	
	private void initLoad(int row) {
		Label loadLabel = new Label("Load a curve:");
		Button loadButton = new Button("Load");
		Label dataLabel = new Label();
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				dataLabel.setText(loadHandler(row));
			}
		});
		centerGrid.addRow(row, loadLabel, loadButton, dataLabel);
	}
	
	private String loadHandler(int row) {
		String data = "";
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File defaultLocation = new File("./Curves/");
		defaultLocation.mkdir();
		fileChooser.setInitialDirectory(defaultLocation);
		File toRead = new File((fileChooser.showOpenDialog(getScene().getWindow()).toString()));
		try {
			data = Files.readString(toRead.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataList.add(row, data);
		return data;
	}
	
	private void initSaveButton() {
		saveButton = new Button("Save");
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(20,0,20,0));
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				saveHandler();
			}
		});
		hbox.getChildren().add(saveButton);
		setBottom(hbox);
	}
	
	private void saveHandler() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		File file = new File("./Surfaces/");
		file.mkdir();
		fileChooser.setInitialDirectory(file);
		try {
			FileWriter writer = new FileWriter(fileChooser.showSaveDialog(getScene().getWindow()).toString());
			writer.write(toString());
			writer.close();
		} catch (IOException e) {
			// No code to execute on failure
		}
	}
	
	public String toString() {
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		joiner.add("Ruled");
		joiner.add(spinner.getValue().toString());
		for (String data:dataList) {
			joiner.add(data);
		}
		return joiner.toString();
	}
	
}

