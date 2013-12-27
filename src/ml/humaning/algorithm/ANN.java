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

public class ANN {
	private Instances data;
	private MultilayerPerceptron multilayerPerceptron;
	public ANN() {

	}

	public void train(String trainFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(trainFile));
		data = libsvmLoader.getDataSet();

		NumericToNominal filter = new NumericToNominal();
        filter.setInputFormat(data);
        data = Filter.useFilter(data, filter);

		//System.out.println("1");

		PrincipalComponents pca = new PrincipalComponents();
		pca.setMaximumAttributeNames(300);
		pca.buildEvaluator(data);
		data = pca.transformedData(data);

		//System.out.println("after pca");

		LibSVMSaver libsvmSaver = new LibSVMSaver();
		libsvmSaver.setInstances(data);
		libsvmSaver.setFile(new File("pca.dat"));
		libsvmSaver.writeBatch();

//		multilayerPerceptron = new MultilayerPerceptron();
//		multilayerPerceptron.setTrainingTime(1);
//		multilayerPerceptron.buildClassifier(data);
//
//		System.out.println("after ann");



	}

	public void predict(String testFile, String outputFile) throws Exception{
//		LibSVMLoader libsvmLoader = new LibSVMLoader();
//		libsvmLoader.setSource(new File(testFile));
//		Instances test = libsvmLoader.getDataSet();
//
//		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
//
//		System.out.println("before predict");
//		for (int i = 0; i < test.numInstances(); i++) {
//			double pred = multilayerPerceptron.classifyInstance(test.instance(i));
//			//bw.write(data.classAttribute().value((int) pred)+"\n");
//			System.out.println(data.classAttribute().value((int) pred));
//		}
//
//		bw.close();

	}

}
