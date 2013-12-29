package ml.humaning.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.LibSVMLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class NB {
	Instances data;
	NaiveBayes nb;
	public void train(String trainFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(trainFile));
		data = libsvmLoader.getDataSet();
		
		NumericToNominal filter = new NumericToNominal();
        filter.setInputFormat(data);
        data = Filter.useFilter(data, filter);
        
		
		nb = new NaiveBayes();
		nb.buildClassifier(data);

		
		

	}

	public void predict(String testFile, String outputFile) throws Exception{
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(testFile));
		Instances test = libsvmLoader.getDataSet();
		
//		NumericToNominal filter = new NumericToNominal();
//        filter.setInputFormat(test);
//        test = Filter.useFilter(test, filter);

		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

		for (int i = 0; i < test.numInstances(); i++) {
			//double pred = nb.classifyInstance(test.instance(i));
			double [] hhh = nb.distributionForInstance(test.instance(i));
			//System.out.println(pred);
			//bw.write(data.classAttribute().value((int)pred)+"\n");
			//System.out.println(data.classAttribute().value((int)pred)+"\n");

		}

		bw.close();

	}


}
