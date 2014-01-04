package ml.humaning.validation;

import java.util.ArrayList;

import weka.core.Instance;
import weka.core.Instances;

public class Validator {
	public static double getError(Instances testData, ArrayList<Integer> ans) {
		int total = ans.size();
		int errorCount = 0;

		for (int i = 0; i < total; i++) {
			Instance inst = testData.instance(i);
			if ((int)inst.classValue() != ans.get(i)) {
				errorCount++;
			}
		}

		return (double)errorCount/total;
	}
}
