package ml.humaning;

import ml.humaning.algorithm.Algorithm;
import ml.humaning.algorithm.KNN;
import ml.humaning.algorithm.LinearSVM;

public class AlgorithmFactory {

	public static Algorithm create(String name) {

		if ("knn".equals(name)) return new KNN();
		if ("linear-svm".equals(name)) return new LinearSVM();

		return null;
	}
}

