package ml.humaning;

import ml.humaning.algorithm.Algorithm;
import ml.humaning.algorithm.KNN;

public class AlgorithmFactory {

	public static Algorithm create(String name) {

		if ("knn".equals(name)) return new KNN();

		return null;
	}
}

