package ml.humaning;

import ml.humaning.algorithm.Algorithm;
import ml.humaning.algorithm.KNN;
import ml.humaning.algorithm.LinearSVM;
import ml.humaning.algorithm.AdaBoost;
import ml.humaning.algorithm.UniformAggregation;

import ml.humaning.preprocessor.Resampler;
import ml.humaning.preprocessor.DownSampler;

import ml.humaning.util.Logger;

public class RunnerFactory {

	public static Runner create(String name) {

		// algorithms
		if ("knn".equals(name)) return new KNN();
		if ("linear-svm".equals(name)) return new LinearSVM();
		if ("adaboost".equals(name)) return new AdaBoost(); // this one not work

		// preprecessors
		if ("resample".equals(name)) return new Resampler();
		if ("downsample".equals(name)) return new DownSampler();

		// aggregation
		if ("uniform-aggregation".equals(name)) return new UniformAggregation();

		Logger.log("no runner match for " + name);

		return null;
	}
}
