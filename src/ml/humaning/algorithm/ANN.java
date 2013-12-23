package ml.humaning.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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

		multilayerPerceptron = new MultilayerPerceptron();
		multilayerPerceptron.buildClassifier(data);

	}

	public void predict(String testFile, String outputFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(testFile));
		Instances test = libsvmLoader.getDataSet();

		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

		for (int i = 0; i < test.numInstances(); i++) {
			double pred = multilayerPerceptron.classifyInstance(test.instance(i));
			bw.write(test.classAttribute().value((int) pred)+"\n");
		}

		bw.close();

	}

}
