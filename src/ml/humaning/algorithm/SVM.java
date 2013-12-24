package ml.humaning.algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import weka.classifiers.functions.LibSVM;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.LibSVMLoader;

import ml.humaning.util.Command;
import ml.humaning.util.Point;
import ml.humaning.util.Reader;

public class SVM {
	private Point [] allData;
	private ArrayList <BufferedWriter> maskPointsWriter;
	private ArrayList <ArrayList <Integer> > lineMapping;
	private String trainFile;
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
	
	public void wekaLibSVM(String trainFile, String testFile, String outputFile) throws Exception{
		LibSVM libsvm = new LibSVM(); 
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(trainFile));
		Instances data = libsvmLoader.getDataSet();
		
		for (int i = 0; i < data.numInstances(); i++) {
			data.instance(i).setValue(data.instance(i).classAttribute(),  String.valueOf(data.instance(i).classValue()));
		}
		
		//libsvm.setSVMType(new SelectedTag(LibSVM.SVMTYPE_ONE_CLASS_SVM, LibSVM.TAGS_SVMTYPE));
		libsvm.buildClassifier(data);
		
		libsvmLoader.setSource(new File(testFile));
		Instances test = libsvmLoader.getDataSet();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

		for (int i = 0; i < test.numInstances(); i++) {
			double pred = libsvm.classifyInstance(test.instance(i));
			bw.write(test.classAttribute().value((int) pred)+"\n");
		}

		bw.close();
		
		
	}

	public void train(boolean isCrossValidation, int svmType, int kernelType, int degree, double gamma, double coef, double cost , double nu, double epsilon) throws IOException, InterruptedException{

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


		Command command = new Command();
		if(isCrossValidation){
			String configurationFile = "svm"+svmType+"_kernel"+kernelType;
			command.call(commandString+" -v 5 "+trainFile);
			String stdout = command.getStdout();
			String accuracyPercent = stdout.substring(stdout.indexOf("Accuracy")).split("\\s+")[2];
			double accuracy = Double.parseDouble(accuracyPercent.substring(0, accuracyPercent.length()-1));

			File accuracyRecord = new File(configurationFile+".record");
			if(accuracyRecord.exists()) {
				BufferedReader accuracyReader = new BufferedReader(new FileReader(accuracyRecord));
				double recordAccuracy = Double.parseDouble(accuracyReader.readLine());
				if(accuracy > recordAccuracy){
					accuracyReader.close();
					accuracyRecord.delete();
					BufferedWriter accuracyWriter = new BufferedWriter(new FileWriter(accuracyRecord));
					accuracyWriter.write(String.valueOf(accuracy)+"\n");
					accuracyWriter.write(commandString+"\n");
					accuracyWriter.flush();
					accuracyWriter.close();
				}

			}else {
				BufferedWriter accuracyWriter = new BufferedWriter(new FileWriter(accuracyRecord));
				accuracyWriter.write(String.valueOf(accuracy)+"\n");
				accuracyWriter.write(commandString+"\n");
				accuracyWriter.flush();
				accuracyWriter.close();
			}


		}else {
			for(int maskRegion = 1;maskRegion <= 4;maskRegion++){// 4 mask regions
				String maskTrainFile = "mask"+maskRegion+"_svm"+svmType+"_kernel"+kernelType;
				BufferedWriter trainFileWriter = new BufferedWriter(new FileWriter(maskTrainFile));
				for(Point p : allData){
					p.setMaskRegion(maskRegion);
					trainFileWriter.write(p.toLIBSVMString()+"\n");
				}
				command.call(commandString+maskTrainFile);
				trainFileWriter.flush();
				trainFileWriter.close();
			}
		}
	}
}
