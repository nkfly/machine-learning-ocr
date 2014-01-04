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

	public void loadModel(String path) throws Exception {
		// do nothing
	}

	public void saveModel(String path) throws Exception {
		// do nothing
	}

	public void train(Instances data) throws Exception {
		// Uniform don't need training
	}

	public int predict(Instance inst) throws Exception {
		double [] result = new double[12];
		double current, max = -1;
		int currentIndex, maxIndex = -1;

		for (Algorithm algo : algorithms) {
			currentIndex = algo.predict(inst);

			current = ++result[currentIndex];
			if (current > max) {
				max = current;
				maxIndex = currentIndex;
			}
		}
		Logger.log("result = " + Arrays.toString(result));
		Logger.log("uniform predict = " + maxIndex);

		return maxIndex;
	}
}
