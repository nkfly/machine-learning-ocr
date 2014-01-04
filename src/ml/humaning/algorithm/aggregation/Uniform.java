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
import ml.humaning.algorithm.NBayes;

import ml.humaning.util.Logger;

public class Uniform extends Aggregation {

	public Uniform() throws Exception {
		KNN knn = new KNN();
		knn.setK(13);

		addAlgorithm(knn); // 83%
		addAlgorithm(new PolySVM()); // 71%
		addAlgorithm(new NBayes()); // 58%
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

	protected ArrayList<Integer> predictCV(int nFold, int fold) throws Exception {
		this.testCV = loadCVTestData(nFold, fold);

		return this.predict(testCV);
	}

	public int predict(Instance inst) throws Exception {
		double [] result = new double[12];
		double current, max = -1;
		int currentIndex, maxIndex = -1;

		for (Algorithm algo : algorithms) {
			currentIndex = algo.predict(inst);

			if (currentIndex < 0 || currentIndex >= 12) {
				Random random = new Random();
				currentIndex = random.nextInt(12);
			}

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
