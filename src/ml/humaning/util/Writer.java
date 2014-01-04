package ml.humaning.util;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.LibSVMSaver;

import ml.humaning.util.Logger;

// for fix classValue in output file
class MySaver extends LibSVMSaver {
	private static final long serialVersionUID = 1L;

	protected String instanceToLibsvm(Instance inst) {
		StringBuffer result;
		int i;

		// class
		int value = (int)inst.classValue() + 1;
		result = new StringBuffer("" + value);

		// attributes
		for (i = 0; i < inst.numAttributes(); i++) {
			if (i == inst.classIndex())
				continue;
			if (inst.value(i) == 0)
				continue;
			result.append(" " + (i+1) + ":" + inst.value(i));
		}

		return result.toString();
	}
}

public class Writer {

	public static void writeData(Instances data, String fileName) throws IOException {
		Logger.log("Write data from file " + fileName + "...");

		LibSVMSaver saver = new MySaver();
		saver.setFile(new File(fileName));
		saver.setInstances(data);
		saver.writeBatch();

		Logger.log("Done");
	}
}
