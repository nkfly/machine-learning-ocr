package ml.humaning.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import ml.humaning.util.Point;
import ml.humaning.util.Preprocess;

public class TestPreprocess {
	public static void main(String [] argv){

		String inputFile = "/Users/elliot-air/Documents/ntu master/course/machine learning/ml2013final_train.dat";
		Point [] allData;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String line;

			Vector <Point> tempVector = new Vector<Point>();
			int count = 0;
			while ((line = reader.readLine()) != null) {
				tempVector.add(new Point(line));
				count++;
				
				
			}

			allData = new Point[tempVector.size()];
			allData = tempVector.toArray(allData);

			reader.close();

			
			/*
			 * Test Empty Detection
			 */
			Preprocess[] preprocesses = new Preprocess[allData.length];
			
			for (int x= 0;x<allData.length;x++){
				preprocesses[x] = new Preprocess(allData[x]);
			}
			
			
			PrintWriter f0 = new PrintWriter(new FileWriter("depth.data"));
			
			for(Preprocess record: preprocesses){
				StringBuilder builder = new StringBuilder();
				builder.append(record.getZodiac());
				int[] depth_vector = record.depthVector();
				for (int x=0; x< depth_vector.length;x++){
					builder.append(" ");
					builder.append(x);
					builder.append(":");
					builder.append(depth_vector[x]);
				}
				f0.println(builder.toString());
			}
			f0.close();
			//System.out.println("Data size: "+ allData.length);
			
			
			
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
