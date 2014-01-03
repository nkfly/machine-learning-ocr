package ml.humaning.algorithm;

import java.util.ArrayList;

import weka.core.Instance;
import weka.core.Instances;

import ml.humaning.algorithm.KNN;
import ml.humaning.algorithm.LinearSVM;

import ml.humaning.util.Logger;

public class UniformAggregation extends Algorithm {

	private ArrayList<Algorithm> algorithms = new ArrayList<Algorithm>();

	public void saveModel() throws Exception {
		// do nothing
	}

	private void addAlgorithm(Algorithm algo) throws Exception {
		algo.loadModel();
		algorithms.add(algo);
	}

	public void train(Instances data) throws Exception {
		// load output files
		// do voting

		// Uniform don't need training

		// load models
		addAlgorithm(new KNN());
		addAlgorithm(new LinearSVM());
	}

	public int predict(Instance inst) throws Exception {
		double [] result = new double[12];
		double current, max = -1;
		int currentIndex, maxIndex = -1;

		for (Algorithm algo : algorithms) {
			currentIndex = algo.predict(inst);
			Logger.log("currentIndex = " + currentIndex);
			current = result[currentIndex]++;
			if (current > max) {
				max = current;
				maxIndex = currentIndex;
			}
		}

		// query predict
		// do the voting
		return maxIndex;
	}
}
