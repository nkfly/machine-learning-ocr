package ml.humaning.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;


import ml.humaning.util.Point;

public class KNN {
	private Point [] allData;
	public KNN(String trainFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(trainFile));
		String line;

		Vector <Point> tempVector = new Vector<Point>();
		while((line = reader.readLine()) != null){
			tempVector.add(new Point(line));
		}

		allData = new Point[tempVector.size()];
		allData = tempVector.toArray(allData);

		reader.close();

	}

	private HashSet <Integer> getValidationPoints(int numberOfValidationPoints){
		HashSet<Integer> validationPoints = new HashSet<Integer>();
		Random generator = new Random(System.currentTimeMillis());
		while(validationPoints.size() < numberOfValidationPoints)validationPoints.add(generator.nextInt(allData.length));
		return validationPoints;

	}

	private int classify(Point [] trainData, int k){
		HashMap <Integer, Integer> zodiacToFrequency = new HashMap <Integer, Integer>();
		for(int i = 0;i < trainData.length && i < k;i++){
			if(zodiacToFrequency.get(trainData[i].getZodiac() ) == null){
				zodiacToFrequency.put(trainData[i].getZodiac(), 1);
			}else {
				zodiacToFrequency.put(trainData[i].getZodiac(), zodiacToFrequency.get(trainData[i].getZodiac())+1);
			}
		}

		int maxFrequency = 0;
		int maxZodiac = 0;
		for(Integer zodiac : zodiacToFrequency.keySet()){
			if(zodiacToFrequency.get(zodiac) > maxFrequency){
				maxFrequency = zodiacToFrequency.get(zodiac);
				maxZodiac = zodiac;

			}
		}

		return maxZodiac;

	}
	
	public double getCVError(int k, int numberOfFold){
		if(numberOfFold < 2)return 0.0;
		double crossValidationError = 0.0;
		for(int i = 1;i <= numberOfFold;i++){
			crossValidationError += getValidationError(k, numberOfFold, i);
		}
		return crossValidationError/numberOfFold;
	}
	
	public void predict(int k, String testFile, String outputFile){
		
		
	}
	// fold is 1-based, [1, numberOfFold]
	private double getValidationError(int k, int numberOfFold, int fold){
		if(numberOfFold == 0)return 0.0;

		int interval = allData.length/numberOfFold;
		int numberOfValidationPoints = (fold == numberOfFold)?
				allData.length - (fold-1)*interval : interval;

		HashSet <Integer> validationPoints = getValidationPoints(numberOfValidationPoints);
		Point [] trainData = new Point[allData.length - numberOfValidationPoints];
		double error = 0.0;

		for(Integer validationPointIndex : validationPoints){
			int trainIndex = 0;
			for(int i = 0;i < allData.length;i++){
				if(!validationPoints.contains(i)){// it's a train point
					trainData[trainIndex] = allData[i];
					//trainData[trainIndex].setDistanceToReference(allData[validationPointIndex].distance(allData[i]) );
					//trainData[trainIndex].setDistanceToReference(-1*allData[validationPointIndex].innerProduct(allData[i]) );
					trainData[trainIndex].setDistanceToReference(-1*allData[validationPointIndex].cosineSimilarity(allData[i]) );
					trainIndex++;
				}
			}
			Arrays.sort(trainData);
			if(classify(trainData, k) != allData[validationPointIndex].getZodiac())error++;
		}
		return error/numberOfValidationPoints;
	}








}
