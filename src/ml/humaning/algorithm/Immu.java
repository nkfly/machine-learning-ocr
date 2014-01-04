package ml.humaning.algorithm;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.immune.immunos.Immunos1;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class Immu extends Algorithm {

	private Immunos1 immu;

	public String getName() {
		return "immunos1";
	}

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("Immunos1: Loading model from " + modelPath + "...");

		immu = (Immunos1)WekaClassifierManager.loadClassifier(modelPath);

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("Immunos1: Saving model to " + modelPath + "...");

		WekaClassifierManager.saveClassifier(immu, modelPath);

		Logger.log("DONE");
	}

	// ----- train and predict -----
	public void train(Instances data) throws Exception {
		Logger.log("Start Immunos1 train...");

		immu = new Immunos1();
		immu.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)immu.classifyInstance(inst);
	}
}
