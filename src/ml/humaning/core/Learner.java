package ml.humaning.core;



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
			try {
				KNN knn = new KNN(argv[1]);
				System.out.println(knn.getValidationError(3, 10, 1));



			}catch (Exception e){
				e.printStackTrace();

			}




		}




	}

	public static void printUsage(){
		System.out.println("Usage: java Learner [-MODEL] ... ");

	}

}
