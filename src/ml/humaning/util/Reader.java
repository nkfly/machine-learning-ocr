package ml.humaning.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {

	public static Point [] readPoints(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line;

		ArrayList <Point> tempList = new ArrayList<Point>();
		while((line = reader.readLine()) != null ){
			tempList.add(new Point(line));
		}

		Point [] data = new Point[tempList.size()];
		data = tempList.toArray(data);

		reader.close();
		return data;

	}
	
	public static int getMaxDimension(Point [] allData){
		int maxDimension = 0;
		for(Point p : allData){
			Dimension [] dArray = p.getDimensionArray();
			if(dArray != null && dArray.length > 0 && dArray[dArray.length-1].getDimension() > maxDimension){
				maxDimension = dArray[dArray.length-1].getDimension();				
			}
		}
		
		return maxDimension;
		
	}
	
	public static double getTestAccuracy(String groundTruth, String testResult) throws NumberFormatException, IOException{
		double error = 0.0;
		int number = 0;
		BufferedReader g = new BufferedReader(new FileReader(groundTruth));
		BufferedReader t = new BufferedReader(new FileReader(testResult));
		String truth;
		String myAnswer;
		while((truth = g.readLine()) != null && (myAnswer = t.readLine()) != null ){
			number++;
			if(Integer.parseInt(truth) != Integer.parseInt(myAnswer)){
				error++;
			}
		}
		
		return (1.0- error/number)*100;
		
	}

}
