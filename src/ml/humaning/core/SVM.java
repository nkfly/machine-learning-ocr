package ml.humaning.core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ml.humaning.util.Command;
import ml.humaning.util.Point;
import ml.humaning.util.Reader;

public class SVM {
	private Point [] allData;
	public SVM(String trainFile) throws IOException{
		allData = Reader.readPoints(trainFile);
			
	}
	
	public void train(boolean isCrossValidation, int svmType, int kernelType, int degree, double gamma, double coef, double cost , double nu, double epsilon) throws IOException, InterruptedException{
		ArrayList <String> commandList = new ArrayList<String>();
		commandList.add("svm-train");
		if(svmType == 0){// C-SVC
			commandList.add("-c");// cost
			commandList.add(String.valueOf(cost));			
		}else if(svmType == 1){// nu-SVC
			commandList.add("-n");// nu
			commandList.add(String.valueOf(nu));			
		}else if(svmType == 2){// one-class SVM
			commandList.add("-n");// nu
			commandList.add(String.valueOf(nu));
		}else if(svmType == 3){// epsilon-SVR
			commandList.add("-c");// cost
			commandList.add(String.valueOf(cost));
			commandList.add("-p");// epsilon
			commandList.add(String.valueOf(epsilon));			
		}else if(svmType == 4){// nu-SVR
			commandList.add("-c");// cost
			commandList.add(String.valueOf(cost));
			commandList.add("-n");// nu
			commandList.add(String.valueOf(nu));
		}
		
		if(kernelType == 0){// linear
						
		}else if(kernelType == 1){// polynomial
			commandList.add("-d");// degree
			commandList.add(String.valueOf(degree));
			commandList.add("-g");// gamma
			commandList.add(String.valueOf(gamma));
			commandList.add("-r");// coef0
			commandList.add(String.valueOf(coef));
		}else if(kernelType == 2){// radial basis
			commandList.add("-g");// gamma
			commandList.add(String.valueOf(gamma));
		}else if(kernelType == 3){// sigmoid
			commandList.add("-g");// gamma
			commandList.add(String.valueOf(gamma));
			commandList.add("-r");// coef0
			commandList.add(String.valueOf(coef));		
		}
		
		Command command = new Command();
		for(int maskRegion = 1;maskRegion <= 4;maskRegion++){// 4 mask regions
			String maskTrainFile = "mask"+maskRegion+"_svm"+svmType+"_kernel_"+kernelType; 
			BufferedWriter trainFileWriter = new BufferedWriter(new FileWriter(maskTrainFile));
			for(Point p : allData){
				p.setMaskRegion(maskRegion);
				trainFileWriter.write(p.toLIBSVMString()+"\n");				
			}
			command.call(commandList.toString()+" " + maskTrainFile);
			trainFileWriter.close();
		}
		
		
		
		
	}
	


}
