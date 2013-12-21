package ml.humaning.core;

import java.io.BufferedWriter;
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
	
	public void train(boolean isCrossValidation, int svmType, int kernelType, int degree, double gamma, double coef, double cost , double nu, double epsilon) throws IOException, InterruptedException{
		Command command = new Command();
		command.add("svm-train");
		if(svmType == 0){// C-SVC
			command.add("-c");// cost
			command.add(String.valueOf(cost));			
		}else if(svmType == 1){// nu-SVC
			command.add("-n");// nu
			command.add(String.valueOf(nu));			
		}else if(svmType == 2){// one-class SVM
			command.add("-n");// nu
			command.add(String.valueOf(nu));
		}else if(svmType == 3){// epsilon-SVR
			command.add("-c");// cost
			command.add(String.valueOf(cost));
			command.add("-p");// epsilon
			command.add(String.valueOf(epsilon));			
		}else if(svmType == 4){// nu-SVR
			command.add("-c");// cost
			command.add(String.valueOf(cost));
			command.add("-n");// nu
			command.add(String.valueOf(nu));
		}
		
		if(kernelType == 0){// linear
						
		}else if(kernelType == 1){// polynomial
			command.add("-d");// degree
			command.add(String.valueOf(degree));
			command.add("-g");// gamma
			command.add(String.valueOf(gamma));
			command.add("-r");// coef0
			command.add(String.valueOf(coef));
		}else if(kernelType == 2){// radial basis
			command.add("-g");// gamma
			command.add(String.valueOf(gamma));
		}else if(kernelType == 3){// sigmoid
			command.add("-g");// gamma
			command.add(String.valueOf(gamma));
			command.add("-r");// coef0
			command.add(String.valueOf(coef));		
		}
		
		
		for(int maskRegion = 1;maskRegion <= 4;maskRegion++){// 4 mask regions
			String maskTrainFile = "mask"+maskRegion+"_svm"+svmType+"_kernel_"+kernelType; 
			BufferedWriter trainFileWriter = new BufferedWriter(new FileWriter(maskTrainFile));
			for(Point p : allData){
				p.setMaskRegion(maskRegion);
				trainFileWriter.write(p.toLIBSVMString()+"\n");				
			}
			command.call(command.toString()+" " + maskTrainFile);
			trainFileWriter.close();
		}
		
		
		
		
	}
	


}
