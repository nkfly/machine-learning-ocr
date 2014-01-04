package ml.humaning.algorithm;

import java.util.ArrayList;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.Attribute;

import ml.humaning.util.AnswerLoader;
import ml.humaning.util.Logger;
import ml.humaning.util.InstancesHelper;

public abstract class Aggregation extends Algorithm {

	protected ArrayList<Algorithm> algorithms = new ArrayList<Algorithm>();

	public void addAlgorithm(Algorithm algo) throws Exception {
		algo.loadModel();
		algorithms.add(algo);
	}

	protected void loadCVFoldData(Instances data, int nFold, int currentFold) throws Exception {
		int nAlgo = algorithms.size();

		// construct raw array
		ArrayList<ArrayList<Integer>> raw = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < nAlgo; i++){
			raw.add(new ArrayList<Integer>());
		}

		// load
		for (int featureIndex = 0; featureIndex < nAlgo ; featureIndex++) {
			Algorithm algo = algorithms.get(featureIndex);
			String resultPath = algo.getCVResultPath(nFold, currentFold);
			AnswerLoader.load(raw.get(featureIndex), resultPath);
		}

		Instances originTestCVData = super.loadCVTestData(nFold, currentFold);

		// insert inst
		int nData = originTestCVData.numInstances();
		for (int i = 0; i < nData; i++) {
			Instance originInst = originTestCVData.instance(i);
			SparseInstance inst = new SparseInstance(nAlgo + 1);

			for (int j = 0; j < nAlgo; j++) {
				inst.setValue(j, (double)raw.get(j).get(i));
			}

			inst.setDataset(data);
			inst.setClassValue(originInst.classValue());

			data.add(inst);
		}

	}

	protected Instances loadCVTrainData(int nFold, int fold) throws Exception {
		int nAlgo = algorithms.size();

		// generate data
		Instances data =
			InstancesHelper.genInstancesWithNumAttribute("feature", nAlgo, trainData);

		// load answers from all registered algorithms, given fold = 3 => load 0,1,2,4
		for (int currentFold = 0; currentFold < nFold; currentFold++) {
			if (currentFold == fold) continue;

			loadCVFoldData(data, nFold, currentFold);
		}

		return data;
	}
}
