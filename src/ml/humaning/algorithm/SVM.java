package ml.humaning.algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ml.humaning.util.Command;
import ml.humaning.util.Point;
import ml.humaning.util.Reader;
import ml.humaning.util.Svm_train;

public class SVM {
	private Point [] allData;
	private ArrayList <BufferedWriter> maskPointsWriter;
	private ArrayList <ArrayList <Integer> > lineMapping;
	private String trainFile;
	private Executor pool;
	public SVM(String trainFile) throws IOException{
		allData = Reader.readPoints(trainFile);
		this.trainFile = trainFile;

	}

	public void predict(int svmType, int kernelType, String testFile, String outputFile) throws IOException, InterruptedException{
		BufferedReader testReader = new BufferedReader(new FileReader(testFile));

		String testPointsWithMask1 = "mask1.in";
		String testPointsWithMask2 = "mask2.in";
		String testPointsWithMask3 = "mask3.in";
		String testPointsWithMask4 = "mask4.in";

		maskPointsWriter = new ArrayList <BufferedWriter>();
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask1)));
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask2)));
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask3)));
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask4)));

		lineMapping = new ArrayList <ArrayList <Integer> >();
		lineMapping.add(new ArrayList <Integer>());
		lineMapping.add(new ArrayList <Integer>());
		lineMapping.add(new ArrayList <Integer>());
		lineMapping.add(new ArrayList <Integer>());

		int lineNumber = 0;
		String line = null;

		while((line = testReader.readLine()) != null){
			Point p = new Point(line);
			int maskRegion = p.getEmptyRegion();
			p.setMaskRegion(maskRegion);
			maskPointsWriter.get(maskRegion-1).write(line+"\n");
			lineMapping.get(maskRegion-1).add(lineNumber);
			lineNumber++;
		}
		testReader.close();

		for(BufferedWriter bw : maskPointsWriter){
			bw.flush();
			bw.close();
		}

		String mask1Output = "mask1.out";
		String mask2Output = "mask2.out";
		String mask3Output = "mask3.out";
		String mask4Output = "mask4.out";

		Command command = new Command();
		command.call("svm-predict " + testPointsWithMask1+ " mask1"+"_svm"+svmType+"_kernel"+kernelType+".model "+ mask1Output);
		command.call("svm-predict " + testPointsWithMask2+ " mask2"+"_svm"+svmType+"_kernel"+kernelType+".model "+ mask2Output);
		command.call("svm-predict " + testPointsWithMask3+ " mask3"+"_svm"+svmType+"_kernel"+kernelType+".model "+ mask3Output);
		command.call("svm-predict " + testPointsWithMask4+ " mask4"+"_svm"+svmType+"_kernel"+kernelType+".model "+ mask4Output);

		merge(mask1Output, mask2Output, mask3Output, mask4Output, outputFile);


	}

	private void merge(String mask1Input, String mask2Input, String mask3Input, String mask4Input,String outputFile) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		BufferedReader mask1Reader = new BufferedReader(new FileReader(mask1Input));
		BufferedReader mask2Reader = new BufferedReader(new FileReader(mask2Input));
		BufferedReader mask3Reader = new BufferedReader(new FileReader(mask3Input));
		BufferedReader mask4Reader = new BufferedReader(new FileReader(mask4Input));
		int lineNumber = 0;
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		int index4 = 0;
		while(index1 < lineMapping.get(0).size() || index2 < lineMapping.get(1).size()
				|| index3 < lineMapping.get(2).size() || index4 < lineMapping.get(3).size()){
			if(index1 < lineMapping.get(0).size() && lineMapping.get(0).get(index1) == lineNumber){
				writer.write(mask1Reader.readLine()+"\n");
				index1++;
			}else if(index2 < lineMapping.get(1).size() && lineMapping.get(1).get(index2) == lineNumber){
				writer.write(mask2Reader.readLine()+"\n");
				index2++;
			}else if(index3 < lineMapping.get(2).size() && lineMapping.get(2).get(index3) == lineNumber){
				writer.write(mask3Reader.readLine()+"\n");
				index3++;
			}else if(index4 < lineMapping.get(3).size() && lineMapping.get(3).get(index4) == lineNumber){
				writer.write(mask4Reader.readLine()+"\n");
				index4++;
			}
			lineNumber++;
		}

		writer.flush();
		writer.close();
		mask1Reader.close();
		mask2Reader.close();
		mask3Reader.close();
		mask4Reader.close();

	}
	
	
	
	private String processTrainCommand(int svmType, int kernelType, int degree, double gamma, double coef, double cost , double nu, double epsilon){
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
		
		commandString += ("-s " + svmType + " -t "+ kernelType + " "); 
		
		return commandString;

		
		
	}
	
	private class Trainer implements Runnable{
		
		private SVM svm;
		private String command;
		private String accuracyRecord;
		public Trainer(SVM svm, String command, String ar){
			this.svm = svm;
			this.command = command;
			this.accuracyRecord = ar;
			
		}

		@Override
		public void run() {
	
				Svm_train trainer = new Svm_train();
				try {
					double accuracy = trainer.run(command.split("\\s+"));
					svm.updateAccuracyRecord(accuracyRecord, accuracy, command);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				

			
		}
		
		
	}
	
	public synchronized void updateAccuracyRecord(String accuracyRecordPath, double accuracy, String command) throws IOException{
		File accuracyRecord = new File(accuracyRecordPath);
        if(accuracyRecord.exists()) {
                BufferedReader accuracyReader = new BufferedReader(new FileReader(accuracyRecord));
                double recordAccuracy = Double.parseDouble(accuracyReader.readLine());
                if(accuracy > recordAccuracy){
                        accuracyReader.close();
                        accuracyRecord.delete();
                        BufferedWriter accuracyWriter = new BufferedWriter(new FileWriter(accuracyRecord));
                        accuracyWriter.write(String.valueOf(accuracy)+"\n");
                        accuracyWriter.write(command+"\n");
                        accuracyWriter.close();
                }

        }else {
                BufferedWriter accuracyWriter = new BufferedWriter(new FileWriter(accuracyRecord));
                accuracyWriter.write(String.valueOf(accuracy)+"\n");
                accuracyWriter.write(command+"\n");
                accuracyWriter.close();
        }
		
	}
	
	public void parallelCrossValidationSVM(int svmType, int kernelType) throws IOException, InterruptedException{
		
		String configurationFile = "svm"+svmType+"_kernel"+kernelType;
		int threadNumber = 13;
		pool = Executors.newFixedThreadPool(threadNumber);
		
		for(double c = 1.0 ;c <= 100000.0; c *= 10){
			for(int p = 3; p <= 10;p += 1){
				for(double gamma = 0.0001;gamma <= 0.001;gamma += 0.0001){
					String commandString = processTrainCommand(svmType, kernelType, p, gamma, -1, c, -1, -1);
		            pool.execute(new Trainer(this, commandString+" -v 5 "+trainFile, configurationFile+".record"));
				}
			}
		}				
	}

	public void train(int svmType, int kernelType, int degree, double gamma, double coef, double cost , double nu, double epsilon) throws IOException, InterruptedException{

		String commandString = processTrainCommand(svmType, kernelType, degree, gamma, coef, cost, nu, epsilon);
		Command command = new Command();
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
