package ml.humaning.util;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.LibSVMLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

import ml.humaning.util.Logger;

public class Reader {

	public static Instances readData(String fileName) throws Exception {
		Logger.log("Read data from file " + fileName + "...");

		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(fileName));
		Instances data = libsvmLoader.getDataSet();

		NumericToNominal filter = new NumericToNominal();

		String[] options = new String[2];
		options[0] = "-R";
		options[1] = "last";

		filter.setOptions(options);
        filter.setInputFormat(data);
        data = Filter.useFilter(data, filter);

		Logger.log("Done");

		return data;
	}
}
