package ml.humaning.algorithm.aggregation;

import java.util.ArrayList;
import java.util.Arrays;

import weka.core.Instance;
import weka.core.Instances;

import ml.humaning.algorithm.Aggregation;
import ml.humaning.algorithm.Algorithm;
import ml.humaning.algorithm.KNN;
import ml.humaning.algorithm.LinearSVM;

import ml.humaning.util.Logger;

public class Uniform extends Aggregation {

	public Uniform() throws Exception {
		addAlgorithm(new KNN());
		addAlgorithm(new LinearSVM());
	}

	public String getName() {
		return "uniform_aggregation";
	}

	public void saveModel() throws Exception {
		// do nothing
	}

	public void train(Instances data) throws Exception {
		// load output files
		// do voting

		// Uniform don't need training

		// load models
	}

	public int predict(Instance inst) throws Exception {
		double [] result = new double[12];
		double current, max = -1;
		int currentIndex, maxIndex = -1;

		Logger.log("========");
		for (Algorithm algo : algorithms) {
			currentIndex = algo.predict(inst);

			Logger.log("algo = " + algo);
			Logger.log("currentIndex = " + currentIndex);

			current = ++result[currentIndex];
			if (current > max) {
				max = current;
				maxIndex = currentIndex;
			}
		}
		Logger.log("result = " + Arrays.toString(result));
		Logger.log("max = " + max);

		return maxIndex;
	}
}
