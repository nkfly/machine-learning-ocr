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
import ml.humaning.algorithm.PolySVM;
import ml.humaning.algorithm.NBayes;
import ml.humaning.algorithm.RForest;
import ml.humaning.algorithm.AdaBoost;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class LinearSVMAggregation extends Aggregation {

	private LibLINEAR libLinear;

	public LinearSVMAggregation() throws Exception {
		KNN knn = new KNN();
		knn.setK(13);

		addAlgorithm(knn); // 83%
		addAlgorithm(new PolySVM()); // 71%
		addAlgorithm(new AdaBoost()); // 64%
		addAlgorithm(new RForest()); // 63%
		addAlgorithm(new NBayes()); // 58%
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

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("LinearSVM: Loading model from " + modelPath + "...");

		libLinear = (LibLINEAR)WekaClassifierManager.loadClassifier(modelPath);

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("LinearSVM: Saving model to " + modelPath + "...");

		WekaClassifierManager.saveClassifier(libLinear, modelPath);

		Logger.log("DONE");
	}

	public void train(Instances data) throws Exception {
		Logger.log("Start LibLinear aggregation train...");

		libLinear = new LibLINEAR();

		String[] options = new String[2];
		options[0] = "-S";
		options[1] = Integer.toString(this.s);

		libLinear.setOptions(options);
		libLinear.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		int value = (int)libLinear.classifyInstance(inst);
		Logger.log("linear svm aggregation predict = " + value);

		return value;
	}
}
