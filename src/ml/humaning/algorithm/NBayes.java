package ml.humaning.algorithm;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.bayes.NaiveBayes;

import ml.humaning.util.Logger;
import ml.humaning.util.WekaClassifierManager;

public class NBayes extends Algorithm {

	private NaiveBayes bayes;

	public String getName() {
		return "naive_bayes";
	}

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("NaiveBayes: Loading model from " + modelPath + "...");

		bayes = (NaiveBayes)WekaClassifierManager.loadClassifier(modelPath);

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("NaiveBayes: Saving model to " + modelPath + "...");

		WekaClassifierManager.saveClassifier(bayes, modelPath);

		Logger.log("DONE");
	}

	// ----- train and predict -----
	public void train(Instances data) throws Exception {
		Logger.log("Start NaiveBayes train...");

		bayes = new NaiveBayes();
		bayes.buildClassifier(data);

		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)bayes.classifyInstance(inst);
	}
}
