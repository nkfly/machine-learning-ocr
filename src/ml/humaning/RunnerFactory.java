package ml.humaning;

import ml.humaning.algorithm.Algorithm;
import ml.humaning.algorithm.KNN;
import ml.humaning.algorithm.LinearSVM;
import ml.humaning.algorithm.PolySVM;
import ml.humaning.algorithm.AdaBoost;
import ml.humaning.algorithm.aggregation.Uniform;
import ml.humaning.algorithm.aggregation.LinearSVMAggregation;

import ml.humaning.preprocessor.Resampler;
import ml.humaning.preprocessor.DownSampler;
import ml.humaning.preprocessor.TestAnsMerger;

import ml.humaning.util.Logger;

public class RunnerFactory {

	public static Runner create(String name) throws Exception {

		// algorithms
		if ("knn".equals(name)) return new KNN();
		if ("linear-svm".equals(name)) return new LinearSVM();
		if ("poly-svm".equals(name)) return new PolySVM();
		if ("adaboost".equals(name)) return new AdaBoost(); // this one not work

		// preprecessors
		if ("resample".equals(name)) return new Resampler();
		if ("downsample".equals(name)) return new DownSampler();
		if ("merge-test".equals(name)) return new TestAnsMerger();

		// aggregation
		if ("uniform".equals(name)) return new Uniform();
		if ("linear-svm-aggregation".equals(name)) return new LinearSVMAggregation();

		Logger.log("no runner match for " + name);

		return null;
	}
}

