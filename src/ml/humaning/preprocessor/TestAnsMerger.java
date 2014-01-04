package ml.humaning.preprocessor;

import java.util.ArrayList;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.Attribute;

import ml.humaning.util.AnswerLoader;
import ml.humaning.util.Logger;

public class TestAnsMerger extends Preprocessor {

	public Instances merge(Instances data) throws Exception {
		int numAttr = data.numAttributes();
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for (int i = 0; i < numAttr - 1; i++) {
			attributes.add(new Attribute("attr" + i));
		}

		Attribute classAttribute = new Attribute("class");
		attributes.add(classAttribute);

		Instances result = new Instances("merged", attributes, data.numInstances());
		result.setClass(classAttribute);

		ArrayList<Integer> ans = new ArrayList<Integer>();
		ans = AnswerLoader.load(ans, "./data/ans1.dat.txt");

		int nData = data.numInstances();
		for (int i = 0; i < nData; i++) {
			Instance inst = data.instance(i);
			inst.setClassValue(ans.get(i));
			Logger.log("" + ans.get(i) + " " + inst.classValue());
			inst.setDataset(result);
			result.add(inst);
		}

		return result;
	}

	public void run() throws Exception {
		data = merge(data);
		this.writeData(data);
	}
}
