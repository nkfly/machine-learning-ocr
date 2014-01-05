package ml.humaning.algorithm;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.trees.RandomForest;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class RForest extends Algorithm {

	private RandomForest forest;

	public String getName() {
		return "random_forest";
	}

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("RandomForest: Loading model from " + modelPath + "...");

		forest = (RandomForest)WekaClassifierManager.loadClassifier(modelPath);

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("RandomForest: Saving model to " + modelPath + "...");

		WekaClassifierManager.saveClassifier(forest, modelPath);

		Logger.log("DONE");
	}

	// ----- train and predict -----
	public void train(Instances data) throws Exception {
		Logger.log("Start RandomForest train...");

		// -I 10 -K 30 -S 1 -num-slots 1

		String[] options = new String[8];
		options[0]  = "-I";
		options[1]  = "10";
		options[2]  = "-K";
		options[3]  = "50";
		options[4]  = "-S";
		options[5]  = "1";
		options[6]  = "-num-slots";
		options[7]  = "1";

		forest = new RandomForest();
		forest.setOptions(options);
		forest.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)forest.classifyInstance(inst);
	}
}
