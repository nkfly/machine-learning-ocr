package ml.humaning.algorithm.aggregation;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.cli.Option;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LibLINEAR;

import ml.humaning.algorithm.Aggregation;
import ml.humaning.algorithm.Algorithm;
import ml.humaning.algorithm.KNN;

import ml.humaning.util.Logger;

public class LinearSVMAggregation extends Aggregation {

	private LibLINEAR libLinear;

	public LinearSVMAggregation() throws Exception {
		addAlgorithm(new KNN());
		addAlgorithm(new ml.humaning.algorithm.LinearSVM());
	}

	private int s;

	// ----- commandline options -----
	public void registerOptions() {
		super.registerOptions();

		Option s = new Option("s", true, "the s of LinearSVM");
		options.addOption(s);
	}

	public boolean parseArgv(String [] argv) throws Exception {
		if (!super.parseArgv(argv)) return false;

		if (!line.hasOption("s")) {
			Logger.log("Missing options: s");
			return false;
		}

		this.s = Integer.parseInt(line.getOptionValue('s'));

		return true;
	}

	public String getName() {
		return "linear_svm_aggregation";
	}

	public void loadModel(String path) throws Exception {
		// todo
	}

	public void saveModel(String path) throws Exception {
		// todo
	}

	public void train(Instances data) throws Exception {
		Logger.log("Start LibLinear aggregation train...");

		libLinear = new LibLINEAR();

		String[] options = new String[2];
		options[0] = "-S";
		options[1] = Integer.toString(this.s);

		libLinear.setOptions(options);
		libLinear.buildClassifier(data);

		/* Evaluation eval = new Evaluation(data); */
		/* eval.crossValidateModel(libLinear, data, 10, new Random()); */
		/* Logger.log("accuracy = " + eval.pctCorrect()); */

		/* Logger.log("s = " + this.s); */
		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		int value = (int)libLinear.classifyInstance(inst);
		Logger.log("linear svm aggregation predict = " + value);

		return value;
	}
}
