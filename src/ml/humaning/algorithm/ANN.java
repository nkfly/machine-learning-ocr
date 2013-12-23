package ml.humaning.algorithm;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.LibSVMLoader;

public class ANN {
	private Instances data;
	public ANN(String inputFile) throws IOException{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(inputFile));
		data = libsvmLoader.getDataSet();
		
		System.out.println(data.classIndex());
		
	}

}
