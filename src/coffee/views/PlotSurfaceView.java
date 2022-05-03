package coffee.views;

import com.mathworks.engine.MatlabEngine;

import javafx.scene.layout.BorderPane;

public class PlotSurfaceView extends BorderPane {

	MatlabEngine engine;
	
	public PlotSurfaceView(MatlabEngine engine) {
		super();
		this.engine = engine;
	}
}
