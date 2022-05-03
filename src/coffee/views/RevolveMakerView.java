package coffee.views;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.StringJoiner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class RevolveMakerView extends BorderPane {

	private GridPane upperGrid;
	private GridPane centerGrid;
	private TextField lowerBound;
	private TextField upperBound;
	private String data;
	private Label dataLabel;
	private Button saveButton;

	public RevolveMakerView() {
		super();
		initUpperGrid();
		initCenterGrid();
		initRange();
		initLoad();
		initSave();
	}

	public void initUpperGrid() {
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

	private void initRange() {
		lowerBound = new TextField();
		upperBound = new TextField();
		Label rangeLabel = new Label("Range: ");
		Label toLabel = new Label("to");
		upperGrid.addRow(0, rangeLabel, lowerBound, toLabel, upperBound);
	}

	private void initLoad() {
		Label loadLabel = new Label("Load a curve:");
		Button loadButton = new Button("Load");
		dataLabel = new Label();
		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				loadHandler();
			}
		});
		centerGrid.addRow(0, loadLabel, loadButton, dataLabel);
	}

	private void loadHandler() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load");
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
	
	private void initSave() {
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
		joiner.add("Revolved");
		joiner.add(readBounds());
		joiner.add(data);
		return joiner.toString();
	}
	
	private String readBounds() {
		StringJoiner joiner = new StringJoiner(" ", "[", "]");
		joiner.add(lowerBound.getText());
		joiner.add(upperBound.getText());
		return joiner.toString();
	}
}
