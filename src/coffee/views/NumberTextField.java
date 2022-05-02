package coffee.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

	// Credit to Evan Knowles on stackexchange
	public NumberTextField() {
		super();
		textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
	}
}
