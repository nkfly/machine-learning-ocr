package ml.humaning.algorithm;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.cli.Option;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.functions.LibLINEAR;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class AdaBoost extends Algorithm {

	AdaBoostM1 boost;

	public String getName() {
		return "adaboost";
	}

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("AdaBoost: Loading model from " + modelPath + "...");

		boost = (AdaBoostM1)WekaClassifierManager.loadClassifier(modelPath);

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("AdaBoost: Saving model to " + modelPath + "...");

		WekaClassifierManager.saveClassifier(boost, modelPath);

		Logger.log("DONE");
	}

	public void train(Instances data) throws Exception {
		Logger.log("Start AdaBoost train...");

		LibLINEAR libLinear = new LibLINEAR();

		String[] options = new String[8];
		options[0] = "-S";
		options[1] = "1";
		options[2] = "-C";
		options[3] = "1.0";
		options[4] = "-E";
		options[5] = "0.01";
		options[6] = "-B";
		options[7] = "1.0";

		libLinear.setOptions(options);

		boost = new AdaBoostM1();
		boost.setClassifier(libLinear);
		boost.setNumIterations(10);

		boost.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)boost.classifyInstance(inst);
	}
}
