package ml.humaning.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import ml.humaning.util.Dimension;
import ml.humaning.util.Point;

public class KNN {
	private Point [] trainData;
	public KNN(String inputTrainFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputTrainFile));
		String line;
		
		Vector <Point> tempVector = new Vector<Point>();
		while((line = reader.readLine()) != null){
			tempVector.add(new Point(line));		
		}
		
		trainData = new Point[tempVector.size()];
		trainData = tempVector.toArray(trainData);
		
		
	}

}
