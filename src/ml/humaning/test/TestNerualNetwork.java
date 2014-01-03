package ml.humaning.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import ml.humaning.util.Dimension;
import ml.humaning.util.Point;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;
import org.neuroph.nnet.RbfNetwork;
import org.neuroph.nnet.SupervisedHebbianNetwork;



public class TestNerualNetwork {
	public static void main(String [] argv){
		
		int dimension = 105*105;
		
		NeuralNetwork neuralNetwork = new Perceptron(dimension, 1); 
		
		
		String inputFile = "correct.out";
		Point [] allData;
		try {
			
		
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String line;
	
			Vector <Point> tempVector = new Vector<Point>();
			while((line = reader.readLine()) != null){
				tempVector.add(new Point(line));
				
			}
	
			allData = new Point[tempVector.size()];
			allData = tempVector.toArray(allData);
	
			reader.close();
			
			
			
			DataSet trainingSet = 
					 new DataSet(dimension, 1); 
			for (int i= 0;i<allData.length;i++){
				
				double[] x = new double[dimension];
				double[] y = new double[]{allData[i].getZodiac()};
				
				for (Dimension d: allData[i].getDimensionArray()){
					x[d.getDimension()] = d.getValue();
				}
				
				
				trainingSet. addRow (new DataSetRow (x, y));
			}
			
			
			System.out.println("Data size: "+ allData.length);
			
			neuralNetwork.learn(trainingSet); 

			System.out.println("Training: "+ allData.length);
			neuralNetwork.save("or_perceptron.nnet"); 

			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
