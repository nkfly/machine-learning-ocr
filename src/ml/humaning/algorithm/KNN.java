package ml.humaning.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.cli.Option;

import weka.core.Instances;
import weka.core.Instance;

import ml.humaning.util.InstancesHelper;
import ml.humaning.data.Point;
import ml.humaning.data.comparator.CosineDistanceComparator;

import ml.humaning.util.Logger;
import ml.humaning.util.Writer;
import ml.humaning.util.Reader;

public class KNN extends Algorithm {

	private int k;
	private String modelDataPath = "./data/knn_model.dat";
	private Instances currentTrainData;

	// ----- Getter and Setter -----
	public void setK(int k) {
		this.k = k;
	}

	// ----- commandline options -----
	public void registerOptions() {
		super.registerOptions();

		Option k = new Option("k", true, "the k of KNN");
		options.addOption(k);
	}

	public boolean parseArgv(String [] argv) throws Exception {
		if (!super.parseArgv(argv)) return false;

		if (!line.hasOption("k")) {
			Logger.log("Missing options: k");
			return false;
		}

		this.k = Integer.parseInt(line.getOptionValue('k'));

		return true;
	}

	// ----- naming -----
	public String getName() {
		return "knn";
	}

	// ----- load/save model -----

	public void loadModel(String modelPath) throws Exception {
		Logger.log("KNN: Loading model from " + modelPath + "...");

		this.trainData = Reader.readData(modelDataPath);

		FileInputStream fs = new FileInputStream(modelPath);
		BufferedReader in = new BufferedReader(new InputStreamReader(fs));
		k = Integer.parseInt(in.readLine());

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("KNN: Saving model to " + modelPath + "...");

		Writer.writeData(trainData, modelDataPath);

		PrintWriter out = new PrintWriter(modelPath, "UTF-8");
		out.println(k);
		out.close();

		Logger.log("DONE");
	}

	// ----- Train -----
	public void train(Instances data) {
		// yup, there is no trainning process in KNN
		this.currentTrainData = data;
	}

	// ----- Predict -----
	// [todo] - refactor it
	public ArrayList<Integer> predict(Instances data) {
		Logger.log("Start KNN predict...");
		ArrayList<Integer> results = new ArrayList<Integer>();

		int total = data.numInstances();
		int count = 0;
		int current = 0;

		for (Instance point: data) {
			Logger.log("progress: " + current + "/" + total + "....");
			int result = predict(point);

			if (result == new Point(point).getZodiac()) count++;

			results.add(result);
			current++;

			Logger.log("count =  " + count);
			Logger.log("performance: " + (double)count/current);
		}

		Logger.log("Done");
		return results;
	}

	public int predict(Instance inst) {
		int result = 0;

		ArrayList<Point> array = InstancesHelper.toPointArray(currentTrainData);
		Collections.sort(array, new CosineDistanceComparator(new Point(inst)));

		Point targetPoint = new Point(inst);
		Logger.log("real = " + targetPoint.getZodiac());
		result = getMass(array, targetPoint, k);
		/* inst.setClassValue((double)result); */
		Logger.log("predict = " + result);

		return result;
	}


	// ----- Private Methods -----
	private int getMass(ArrayList<Point> array, Point targetPoint, int k) {
		double [] counter = new double[13];
		double max = 0;
		int result = -1;

		for (int i = 0; i < k; i++) {
			Point point = array.get(i);
			int zodiac = point.getZodiac();
			counter[zodiac] += targetPoint.cosineSimilarity(point);
		}

		for (int i = 0; i < 12; i++) {
			double vote = counter[i];
			if (vote > max) {
				max = vote;
				result = i;
			}
		}

		return result;
	}

/* 	private HashSet <Integer> getValidationPoints(int numberOfValidationPoints) { */
/* 		HashSet<Integer> validationPoints = new HashSet<Integer>(); */
/* 		Random generator = new Random(System.currentTimeMillis()); */
/*  */
/* 		while (validationPoints.size() < numberOfValidationPoints) */
/* 			validationPoints.add(generator.nextInt(allData.length)); */
/*  */
/* 		return validationPoints; */
/* 	} */
/*  */
	/* private int classify(Point [] trainData, int k) { */
	/* 	HashMap <Integer, Integer> zodiacToFrequency = new HashMap <Integer, Integer>(); */

	/* 	for(int i = 0;i < trainData.length && i < k;i++){ */
	/* 		if(zodiacToFrequency.get(trainData[i].getZodiac() ) == null){ */
	/* 			zodiacToFrequency.put(trainData[i].getZodiac(), 1); */
	/* 		}else { */
	/* 			zodiacToFrequency.put(trainData[i].getZodiac(), zodiacToFrequency.get(trainData[i].getZodiac())+1); */
	/* 		} */
	/* 	} */

	/* 	int maxFrequency = 0; */
	/* 	int maxZodiac = 0; */

	/* 	for(Integer zodiac : zodiacToFrequency.keySet()){ */
	/* 		if(zodiacToFrequency.get(zodiac) > maxFrequency){ */
	/* 			maxFrequency = zodiacToFrequency.get(zodiac); */
	/* 			maxZodiac = zodiac; */

	/* 		} */
	/* 	} */

	/* 	return maxZodiac; */
	/* } */

	/* public double getCVError(int k, int numberOfFold){ */
	/* 	if(numberOfFold < 2)return 0.0; */
	/* 	double crossValidationError = 0.0; */
	/* 	for(int i = 1;i <= numberOfFold;i++){ */
	/* 		crossValidationError += getValidationError(k, numberOfFold, i); */
	/* 	} */
	/* 	return crossValidationError/numberOfFold; */
	/* } */

	/* public void predict(int k, String testFile, String outputFile) throws IOException { */
	/* 	for(Point testP : testData){ */
	/* 		int emptyRegion = testP.getEmptyRegion(); */
	/* 		testP.setMaskRegion(emptyRegion); */
	/* 		for(int trainIndex = 0; trainIndex < allData.length;trainIndex++){ */
	/* 			allData[trainIndex].setMaskRegion(emptyRegion); */
	/* 			allData[trainIndex].setDistanceToReference(-1*testP.cosineSimilarity(allData[trainIndex])); */
	/* 		} */
	/* 		Arrays.sort(allData); */
	/* 		writer.write(classify(allData, k)+"\n"); */
	/* 	} */
	/* } */
/*  */
/* 	// fold is 1-based, [1, numberOfFold] */
/* 	private double getValidationError(int k, int numberOfFold, int fold){ */
/* 		if(numberOfFold == 0)return 0.0; */
/*  */
/* 		int interval = allData.length/numberOfFold; */
/* 		int numberOfValidationPoints = (fold == numberOfFold)? */
/* 				allData.length - (fold-1)*interval : interval; */
/*  */
/* 		HashSet <Integer> validationPoints = getValidationPoints(numberOfValidationPoints); */
/* 		Point [] trainData = new Point[allData.length - numberOfValidationPoints]; */
/* 		double error = 0.0; */
/*  */
/* 		for (Integer validationPointIndex : validationPoints) { */
/* 			int trainIndex = 0; */
/*  */
/* 			for (int i = 0;i < allData.length;i++){ */
/*  */
/* 				if (!validationPoints.contains(i)) {// it's a train point */
/* 					trainData[trainIndex] = allData[i]; */
/* 					//trainData[trainIndex].setDistanceToReference(allData[validationPointIndex].distance(allData[i]) ); */
/* 					//trainData[trainIndex].setDistanceToReference(-1*allData[validationPointIndex].innerProduct(allData[i]) ); */
/* 					trainData[trainIndex].setDistanceToReference(-1*allData[validationPointIndex].cosineSimilarity(allData[i])); */
/* 					trainIndex++; */
/* 				} */
/* 			} */
/* 			Arrays.sort(trainData); */
/* 			if (classify(trainData, k) != allData[validationPointIndex].getZodiac()) */
/* 				error++; */
/* 		} */
/* 		return error/numberOfValidationPoints; */
/* 	} */
}
