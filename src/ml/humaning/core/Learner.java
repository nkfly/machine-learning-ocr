package ml.humaning.core;



public class Learner {
	public static void main(String [] argv){
		
		if(argv.length < 4){
			printUsage();
			System.exit(-1);
			
		}
		
		
		if("-knn".equals(argv[0])){
			try {
				KNN knn = new KNN(argv[1]);
				System.out.println(knn.getValidationError(6, 10, 1));

				
				
			}catch (Exception e){
				e.printStackTrace();
				
			}
			
			
			
			
		}
		
		
		
		
	}
	
	public static void printUsage(){
		System.out.println("Usage: java Learner [-MODEL] [INPUT_TRAIN_FILE] [INPUT_TEST_FILE]  [OUTPUT_CLASSIFIED_FILE] ");
		
	}

}
