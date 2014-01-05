package ml.humaning.algorithm;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.trees.J48;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class JFourtyEight extends Algorithm {

	private J48 j48;

	public String getName() {
		return "j48";
	}

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("J48: Loading model from " + modelPath + "...");

		j48 = (J48)WekaClassifierManager.loadClassifier(modelPath);

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("J48: Saving model to " + modelPath + "...");

		WekaClassifierManager.saveClassifier(j48, modelPath);

		Logger.log("DONE");
	}

	// ----- train and predict -----
	public void train(Instances data) throws Exception {
		Logger.log("Start J48 train...");

		j48 = new J48();
		j48.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)j48.classifyInstance(inst);
	}
}
