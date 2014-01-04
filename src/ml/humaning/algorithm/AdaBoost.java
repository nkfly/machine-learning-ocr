package ml.humaning.algorithm;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.cli.Option;

import weka.core.Instances;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.Evaluation;

import ml.humaning.util.Logger;

public class AdaBoost extends Algorithm {

	AdaBoostM1 boost;

	public void train(Instances data) throws Exception {
		Logger.log("Start AdaBoost train...");

		boost = new AdaBoostM1();
		boost.setNumIterations(1000000);
		boost.buildClassifier(data);

		Evaluation eval = new Evaluation(data);
		eval.evaluateModel(boost, data);
		/* eval.crossValidateModel(boost, data, 10, new Random()); */
		Logger.log("accuracy = " + eval.pctCorrect());

		Logger.log("DONE");
	}

	public ArrayList<Integer> predict(Instances data) {
		return null;
	}
}
