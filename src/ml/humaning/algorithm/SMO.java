package ml.humaning.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.attributeSelection.PrincipalComponents;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.LibSVMLoader;
import weka.core.converters.LibSVMSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class SMO {
	Instances data;
	weka.classifiers.functions.SMO smo;
	public void train(String trainFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(trainFile));
		data = libsvmLoader.getDataSet();
		
		NumericToNominal filter = new NumericToNominal();
        filter.setInputFormat(data);
        data = Filter.useFilter(data, filter);
		
		smo = new weka.classifiers.functions.SMO();
		smo.buildClassifier(data);

		
		

	}

	public void predict(String testFile, String outputFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(testFile));
		Instances test = libsvmLoader.getDataSet();

		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

		for (int i = 0; i < test.numInstances(); i++) {
			double pred = smo.classifyInstance(test.instance(i));
			bw.write(test.classAttribute().value((int) pred)+"\n");
		}

		bw.close();

	}

}
