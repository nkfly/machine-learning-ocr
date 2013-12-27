package ml.humaning.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.aliasi.matrix.SvdMatrix;

public class SVD {
// reference page: http://alias-i.com/lingpipe/demos/tutorial/svd/read-me.html
	double [][] matrix;
	Point [] allData;
	public SVD(int numberOfAttributes, String trainFile) throws IOException{
		allData = Reader.readPoints(trainFile);
		matrix = new double[numberOfAttributes][allData.length];

		for(int i = 0;i < allData.length;i++){
			Dimension [] dimensionArray = allData[i].getDimensionArray();
			for(Dimension d : dimensionArray){
				matrix[d.getDimension()-1][i] = d.getValue();// 1-based dimension
			}
		}



	}

	public void decompose(int reduceDimension, String outputFile) throws IOException{
	    double featureInit = 0.01;
	    double initialLearningRate = 0.005;
	    int annealingRate = 1000;
	    double regularization = 0.00;
	    double minImprovement = 0.0000;
	    int minEpochs = 10;
	    int maxEpochs = 50000;

	    SvdMatrix svdMatrix = SvdMatrix.svd(matrix,
	                        reduceDimension,
	                        featureInit,
	                        initialLearningRate,
	                        annealingRate,
	                        regularization,
	                        null,
	                        minImprovement,
	                        minEpochs,
	                        maxEpochs);
	    //double[][] termVectors = svdMatrix.leftSingularVectors();
	    double[][] instanceVectors = svdMatrix.rightSingularVectors();
	    BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
	    for(int i = 0;i < instanceVectors.length;i++){
	    	String reducedInstance = allData[i].getZodiac()+ " ";
	    	for(int j = 0;j < instanceVectors[i].length;j++){
	    		reducedInstance += (j+1) + ":" + instanceVectors[i][j] + " ";
	    	}
	    	bw.write(reducedInstance.trim()+"\n");
	    }
	    bw.close();

	}
}
