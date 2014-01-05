package ml.humaning.algorithm;

import java.util.ArrayList;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.cli.Option;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LibLINEAR;
import weka.classifiers.Evaluation;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class LinearSVM extends Algorithm {

	private LibLINEAR libLinear;

	// ----- naming -----
	public String getName() {
		return "linear_svm";
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

	// ----- train and predict -----
	public void train(Instances data) throws Exception {
		Logger.log("Start LibLinear train...");

		libLinear = new LibLINEAR();

		String[] options = new String[4];
		options[0] = "-S";
		options[1] = "1";
		options[2] = "-C";
		options[3] = "0.01";

		libLinear.setOptions(options);
		libLinear.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)libLinear.classifyInstance(inst);
	}
}
