package coffee.views;

import java.util.ArrayList;
import java.util.StringJoiner;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PointCurveView extends GridPane {

	private int numPoints;
	private ArrayList<NumberTextField> points;

	public PointCurveView(int numPoints) {
		super();
		this.numPoints = numPoints;
		initGrid();
		addRows();
	}

	public void initGrid() {
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20, 0, 20, 0));
		setVgap(10);
		setHgap(30);
		Label label = new Label("Control Point:");
		Label xdim = new Label("x");
		Label ydim = new Label("y");
		Label zdim = new Label("z");
		addRow(0, label, xdim, ydim, zdim);
	}

	public void addRows() {
		points = new ArrayList<NumberTextField>();
		for (int cp = 0, row = 1; cp < numPoints; ++cp, ++row) {
			Label cpNum = new Label(cp + "");
			add(cpNum, 0, row);
			for (int col = 1; col <= 3; ++col) {
				NumberTextField field = new NumberTextField();
				points.add(field);
				add(field, col, row);
			}
		}
	}
	
	public int getField(int row, int col) {
		int index = 3 * row + col;
		String input = points.get(index).getText();
		int output = 0;
		try {
			output = Integer.parseInt(input);
		}
		catch (NumberFormatException  e) {
			
		}
		return output;
	}
	
	@Override
	public String toString() {
		StringJoiner joiner;
		StringJoiner lineJoiner = new StringJoiner(System.lineSeparator(), "[", "]");
		for (int row = 0; row < numPoints; ++row) {
			joiner = new StringJoiner(" ");
			for (int col = 0; col < 3; ++col) {
				joiner.add(getField(row,col) + "");
			}
			lineJoiner.add(joiner.toString());
		}
		return lineJoiner.toString();
	}
}
