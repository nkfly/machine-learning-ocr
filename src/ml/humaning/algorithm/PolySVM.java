package ml.humaning.algorithm;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LibSVM;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class PolySVM extends Algorithm {

	private LibSVM svm;

	// ----- naming -----
	public String getName() {
		return "poly_svm";
	}

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("PolySVM: Loading model from " + modelPath + "...");

		svm = (LibSVM)WekaClassifierManager.loadClassifier(modelPath);

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("PolySVM: Saving model to " + modelPath + "...");

		WekaClassifierManager.saveClassifier(svm, modelPath);

		Logger.log("DONE");
	}

	// ----- train and predict -----
	public void train(Instances data) throws Exception {
		Logger.log("Start Polynomial SVM train...");

		//  -c 260.0 -d 3 -g 1.7E-4 -r -1.0 -s 0 -t 1

		String[] options = new String[12];
		options[0]  = "-S";
		options[1]  = "0";
		options[2]  = "-K";
		options[3]  = "1";
		options[4]  = "-R";
		options[5]  = "-1.0";
		options[6]  = "-G";
		options[7]  = "0.00017";
		options[8]  = "-D";
		options[9]  = "3";
		options[10] = "-C";
		options[11] = "260.0";

		svm = new LibSVM();
		svm.setOptions(options);
		svm.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)svm.classifyInstance(inst);
	}
}
