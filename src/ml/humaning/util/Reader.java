package ml.humaning.util;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.LibSVMLoader;

import ml.humaning.util.Logger;

public class Reader {

	public static Instances readData(String fileName) throws Exception {
		Logger.log("Read data from file " + fileName + "...");

		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(fileName));
		Instances data = libsvmLoader.getDataSet();

		Logger.log("Done");

		return data;
	}
}
