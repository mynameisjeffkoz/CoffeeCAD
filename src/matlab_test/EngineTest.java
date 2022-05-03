package matlab_test;

import java.io.StringWriter;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

public class EngineTest {

	public static void main(String[] args) throws IllegalArgumentException, IllegalStateException, InterruptedException, MatlabExecutionException, MatlabSyntaxException, ExecutionException {
		// TODO Auto-generated method stub
		MatlabEngine matEng = MatlabEngine.startMatlab();
		matEng.eval("nurbSphere(5);");
		Scanner stdio = new Scanner(System.in);
		stdio.nextLine();
		matEng.close();
		stdio.close();
	}

}
