package ml.humaning.core;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Learner {

	public static void main(String [] argv){

		if(argv.length < 1){
			printUsage();
			System.exit(-1);
		}
		
		try {
			if("-svm".equals(argv[0])){
				SVM svm = new SVM(argv[1]);
				for(int c = 1000; c <= 10000;c += 1000){
					for(double gamma = 0.00001; gamma < 0.0001;gamma += 0.00001){
						svm.train(true, 0, 2, -1, gamma, -1, c, -1, -1);
					}
				}
				
				//svm.predict(0, 2, argv[2], argv[3]);
				
			}else if("-knn".equals(argv[0])){
				if(argv.length < 4){
					System.exit(-1);
				}
				// argv[0] : -knn, argv[1] : trainFile , argv[2] : testFile , argv[3] outputFile
				KNN knn = new KNN(argv[1]);
				String testFilePath = argv[2];
				String outputFilePath = argv[3];

//				int optimalK = 0;
//				double optimalCrossValidationError = Double.MAX_VALUE;
//
//				BufferedWriter writer = new BufferedWriter(new FileWriter("knn_validation"));
//				writer.write("k\tcrossValidationError\n");
//
//				for(int i = 1;i <= 100;i++){
//					double crossValidationError = knn.getCVError(i, 10);
//
//					if(crossValidationError < optimalCrossValidationError){
//						optimalCrossValidationError = crossValidationError;
//						optimalK = i;
//					}
//
//				 	writer.write(i + "\t" + crossValidationError);
//				}
//
//				writer.write("optimal: " + optimalK + "\t" + optimalCrossValidationError);
//				writer.close();

				knn.predict(15, testFilePath, outputFilePath);

			}
			
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public static void printUsage(){
		System.out.println("Usage: java Learner [-MODEL] ... ");
	}
}
