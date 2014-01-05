package ml.humaning.algorithm.aggregation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;

import ml.humaning.algorithm.Aggregation;
import ml.humaning.algorithm.Algorithm;
import ml.humaning.algorithm.KNN;
import ml.humaning.algorithm.PolySVM;
import ml.humaning.algorithm.LinearSVM;
import ml.humaning.algorithm.NBayes;
import ml.humaning.algorithm.RForest;
import ml.humaning.algorithm.AdaBoost;
import ml.humaning.algorithm.JFourtyEight;

import ml.humaning.util.Logger;

public class Uniform extends Aggregation {

	private int [] weights = new int[]{7, 2, 2, 2, 1, 1, 1};

	public Uniform() throws Exception {
		KNN knn = new KNN();
		knn.setK(13);

		addAlgorithm(knn); // 83%
		addAlgorithm(new PolySVM()); // 71%
		addAlgorithm(new LinearSVM()); // 67%
		addAlgorithm(new AdaBoost()); // 66.8%
		addAlgorithm(new RForest()); // 65%
		addAlgorithm(new NBayes()); // 58%
		addAlgorithm(new JFourtyEight()); // 48%
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

	public void beforeTrain() throws Exception {
		for (Algorithm algo : algorithms) {
			algo.loadModel();
		}
	}

	public void train(Instances data) throws Exception {
		// Uniform don't need training
	}

	protected ArrayList<Integer> predictCV(int nFold, int fold) throws Exception {
		this.testCV = loadCVTestData(nFold, fold);

		return this.predict(testCV);
	}

	public int predict(Instance inst) throws Exception {
		double [] result = new double[12];
		double current, max = -1;
		int currentIndex, maxIndex = -1;

		int nAlgo = algorithms.size();
		for (int i = 0; i < nAlgo; i++) {
			Algorithm algo = algorithms.get(i);

			currentIndex = algo.predict(inst);

			if (currentIndex < 0 || currentIndex >= 12) {
				Random random = new Random();
				currentIndex = random.nextInt(12);
				Logger.log("WARNING!!!! Algorithm " + algo.getName() + " fail, guess one");
			}

			result[currentIndex] += weights[i];
			current = result[currentIndex];
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
