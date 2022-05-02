package coffee.views;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

public class bSplineCurveView extends PointCurveView {

	private Spinner<Integer> spinner;
	
	public bSplineCurveView(int numPoints) {
		super(numPoints);
		addDegreeSpinner();
	}

	private void addDegreeSpinner() {
		Label degLabel = new Label("Degree: ");
		spinner = new Spinner<Integer>(3, Integer.MAX_VALUE, 1);
		super.add(degLabel, 0, super.numPoints + 2);
		super.add(spinner, 1, super.numPoints + 2);
	}
	
	@Override
	public String toString() {
		String output = super.toString();
		output += System.lineSeparator() + spinner.getValue();
		return output;
	}
}
