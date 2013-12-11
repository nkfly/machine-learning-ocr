package ml.humaning.core;

import java.io.BufferedWriter;
import java.io.FileWriter;



public class Learner {
	public static void main(String [] argv){

		if(argv.length < 1){
			printUsage();
			System.exit(-1);

		}


		if("-knn".equals(argv[0])){
			if(argv.length < 4){
				System.exit(-1);
			}
			try {// argv[0] : -knn, argv[1] : trainFile , argv[2] : testFile , argv[3] outputFile
				KNN knn = new KNN(argv[1]);
				int optimalK = 0;
				double optimalCrossValidationError = Double.MAX_VALUE;
				BufferedWriter writer = new BufferedWriter(new FileWriter("knn_validation"));
				writer.write("k\tcrossValidationError\n");
				for(int i = 1;i <= 100;i++){
					double crossValidationError = 0.0;
					for(int j = 1;j <= 10;j++){// perform the 10-fold validation
						crossValidationError += knn.getValidationError(i, 10, j);
					}
					crossValidationError /= 10;
					if(crossValidationError < optimalCrossValidationError){
						optimalCrossValidationError = crossValidationError;
						optimalK = i;
					}
					writer.write(i+"\t"+crossValidationError);					
				}
				writer.write("optimal: "+optimalK+"\t"+optimalCrossValidationError);



			}catch (Exception e){
				e.printStackTrace();

			}




		}




	}

	public static void printUsage(){
		System.out.println("Usage: java Learner [-MODEL] ... ");

	}

}
