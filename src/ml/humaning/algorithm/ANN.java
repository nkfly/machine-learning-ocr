package ml.humaning.algorithm;

import java.io.File;
import java.io.IOException;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.LibSVMLoader;

public class ANN {
	private Instances data;
	private MultilayerPerceptron multilayerPerceptron;
	public ANN() {
		
	}
	
	public void train(String trainFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(trainFile));
		data = libsvmLoader.getDataSet();
		
		System.out.println(data.firstInstance().classValue());
		
		multilayerPerceptron = new MultilayerPerceptron();
		multilayerPerceptron.buildClassifier(data);		 
		
	}
	
	public void predict(String testFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(testFile));
		Instances test = libsvmLoader.getDataSet();
		
		for (int i = 0; i < test.numInstances(); i++) {
			   double pred = multilayerPerceptron.classifyInstance(test.instance(i));
			   System.out.println(test.classAttribute().value((int) pred));
			 }
		
		
		multilayerPerceptron = new MultilayerPerceptron();
		multilayerPerceptron.buildClassifier(data);		 
		
	}

}
