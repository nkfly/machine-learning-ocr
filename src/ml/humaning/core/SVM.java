package ml.humaning.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


import ml.humaning.util.Command;
import ml.humaning.util.Point;
import ml.humaning.util.Reader;

public class SVM {
	private Point [] allData;
	public SVM(String trainFile) throws IOException{
		allData = Reader.readPoints(trainFile);
			
	}
	
	public void predict(int svmType, int kernelType, String testFile, String outputFile) throws IOException, InterruptedException{
		BufferedReader testReader = new BufferedReader(new FileReader(testFile));
		BufferedWriter predictionWriter = new BufferedWriter(new FileWriter(outputFile));
		String line = null;
		String tempPredictFile = "svm-predict.tmp";
		String tempOutputFile = "svm-output.tmp";
		Command command = new Command();
		while((line = testReader.readLine()) != null){
			BufferedWriter tempWriter = new BufferedWriter(new FileWriter(tempPredictFile));
			tempWriter.write(line+"\n");
			tempWriter.flush();
			tempWriter.close();
			Point p = new Point(line);
			command.call("svm-predict " + tempPredictFile+ " mask"+p.getEmptyRegion()+"_svm"+svmType+"_kernel"+kernelType+".model "+ tempOutputFile);
			
			File temp = new File(tempOutputFile);
			BufferedReader tempReader = new BufferedReader(new FileReader(temp));
			String predictionOfPoint = tempReader.readLine(); 
			predictionWriter.write(predictionOfPoint+"\n");
			System.out.println(predictionOfPoint);
			
			temp.delete();
			tempReader.close();
			
		}
		testReader.close();
		predictionWriter.flush();
		predictionWriter.close();
		
	}
	
	public void train(boolean isCrossValidation, int svmType, int kernelType, int degree, double gamma, double coef, double cost , double nu, double epsilon) throws IOException, InterruptedException{
		Command command = new Command();
		String commandString =  "svm-train ";
		if(svmType == 0){// C-SVC
			commandString +="-c ";// cost
			commandString +=String.valueOf(cost)+" ";			
		}else if(svmType == 1){// nu-SVC
			commandString +="-n ";// nu
			commandString +=String.valueOf(nu)+" ";			
		}else if(svmType == 2){// one-class SVM
			commandString +="-n ";// nu
			commandString +=String.valueOf(nu)+" ";
		}else if(svmType == 3){// epsilon-SVR
			commandString +="-c ";// cost
			commandString +=String.valueOf(cost)+" ";
			commandString +="-p ";// epsilon
			commandString +=String.valueOf(epsilon)+" ";			
		}else if(svmType == 4){// nu-SVR
			commandString +="-c ";// cost
			commandString +=String.valueOf(cost)+" ";
			commandString +="-n ";// nu
			commandString +=String.valueOf(nu)+" ";
		}
		
		if(kernelType == 0){// linear
						
		}else if(kernelType == 1){// polynomial
			commandString +="-d ";// degree
			commandString +=String.valueOf(degree)+" ";
			commandString +="-g ";// gamma
			commandString +=String.valueOf(gamma)+" ";
			commandString +="-r ";// coef0
			commandString +=String.valueOf(coef)+" ";
		}else if(kernelType == 2){// radial basis
			commandString +="-g ";// gamma
			commandString +=String.valueOf(gamma)+" ";
		}else if(kernelType == 3){// sigmoid
			commandString +="-g ";// gamma
			commandString +=String.valueOf(gamma)+" ";
			commandString +="-r ";// coef0
			commandString +=String.valueOf(coef)+" ";
		}
		
		
		for(int maskRegion = 1;maskRegion <= 4;maskRegion++){// 4 mask regions
			String maskTrainFile = "mask"+maskRegion+"_svm"+svmType+"_kernel"+kernelType; 
			BufferedWriter trainFileWriter = new BufferedWriter(new FileWriter(maskTrainFile));
			for(Point p : allData){
				p.setMaskRegion(maskRegion);
				trainFileWriter.write(p.toLIBSVMString()+"\n");				
			}
			command.call(commandString+maskTrainFile);
			trainFileWriter.close();
		}
		
		
		
		
	}
	


}
