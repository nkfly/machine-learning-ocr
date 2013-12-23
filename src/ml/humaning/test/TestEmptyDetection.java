package ml.humaning.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import ml.humaning.util.Point;

public class TestEmptyDetection {
	public static void main(String [] argv){

		String inputFile = "/Users/elliot-air/Documents/ntu master/course/machine learning/ml2013final_train.dat";
		Point [] allData;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String line;

			Vector <Point> tempVector = new Vector<Point>();
			while ((line = reader.readLine()) != null) {
				tempVector.add(new Point(line));
			}

			allData = new Point[tempVector.size()];
			allData = tempVector.toArray(allData);

			reader.close();

			
			/*
			 * Test Empty Detection
			 */
			for (int x= 0;x<allData.length;x++)
				System.out.println("[Empty]" + allData[x].getEmptyRegion());
			
			
			
			System.out.println("Data size: "+ allData.length);
			
			
			
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
