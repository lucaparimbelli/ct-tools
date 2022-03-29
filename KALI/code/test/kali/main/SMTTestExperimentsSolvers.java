package kali.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.java_smt.SolverContextFactory.Solvers;

import kali.safeelements.TestContext;
import kali.util.Order;

// for the experiments
public class SMTTestExperimentsSolvers {

//	final static String Z3Path = "C:\\Users\\garganti\\Documents\\z3-4.8.14-x64-win\\z3-4.8.14-x64-win\\bin";
//
	static {
//		String oldPath = System.getProperty("java.library.path");
//		System.setProperty("java.library.path", oldPath + File.pathSeparator + Z3Path);
	}

	public static int NUM_EXECUTION = 10;
	public static String BASE_FOLDER = "./examples/ctcomp/";

	public static void main(String[] args) throws IOException, InterruptedException, InvalidConfigurationException {
		/// solvers
		Solvers[] solvers = { /*Solvers.SMTINTERPOL, Solvers.PRINCESS, */ Solvers.Z3 /* , Solvers.MATHSAT5 */ };
		TestContext.config = Configuration.builder().setOption("solver.z3.usePhantomReferences", "true").build();
		KALI.OUTPUT_TXT = "output_solvers.txt";
		//
		int numThreads =  Runtime.getRuntime().availableProcessors();
		// Find all the files in the test folder
		File file = new File(BASE_FOLDER);
		List<File> fileList = Arrays.asList(file.listFiles());
		Collections.shuffle(fileList);
		for (int i = 0; i < NUM_EXECUTION; i++)
			for (File str : fileList) {
				// Handle only CTW files
				if (str.getAbsolutePath().endsWith(".ctw")) {
					// Repeat the same experiment without sorting the test contexts
					KALI.SORT = false;
					KALI.ORDER = Order.IN_ORDER_SIZE_ASC;
					//
					for (Solvers s : solvers) {
						TestContext.SMTSolver = s;
						SMTTestBatchExecutor.callMain(numThreads, 2, str.getAbsolutePath(), false);
					}
				}
			}
	}
}
