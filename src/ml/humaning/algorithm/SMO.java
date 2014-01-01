package ml.humaning.algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import ml.humaning.util.Point;
import ml.humaning.util.Reader;

import weka.core.Instances;
import weka.core.converters.LibSVMLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class SMO {
	Instances data;	
	ArrayList <weka.classifiers.functions.SMO> smoList;
	
	public void train(String trainFile) throws Exception{
		Point [] allData = Reader.readPoints(trainFile);
		int maxDimension = Reader.getMaxDimension(allData);
		for(int maskRegion = 1;maskRegion <= 4;maskRegion++){// 4 mask regions
			String maskTrainFile = "mask"+maskRegion+"_smo";
			BufferedWriter trainFileWriter = new BufferedWriter(new FileWriter(maskTrainFile));
			for(Point p : allData){
				p.setMaskRegion(maskRegion);
				trainFileWriter.write(p.toLIBSVMString(maxDimension)+"\n");
			}
			trainFileWriter.close();
		}
		
		smoList = new ArrayList <weka.classifiers.functions.SMO>();
		
		LibSVMLoader libsvmLoader = new LibSVMLoader();
		libsvmLoader.setSource(new File(trainFile));
		data = libsvmLoader.getDataSet();
		
		NumericToNominal filter = new NumericToNominal();
        filter.setInputFormat(data);
        data = Filter.useFilter(data, filter);
		
		for(int maskRegion = 1;maskRegion <= 4;maskRegion++){// 4 mask regions
			LibSVMLoader tempLibsvmLoader = new LibSVMLoader();
			tempLibsvmLoader.setSource(new File("mask"+maskRegion+"_smo"));
			Instances tempData = tempLibsvmLoader.getDataSet();
			
			NumericToNominal tempFilter = new NumericToNominal();
	        tempFilter.setInputFormat(tempData);
	        tempData = Filter.useFilter(tempData, tempFilter);
	        
			
	        weka.classifiers.functions.SMO tempSmo = new weka.classifiers.functions.SMO();
			tempSmo.buildClassifier(tempData);
			smoList.add(tempSmo);
			
		}
		
		
	}
	
	

	public void predict(String testFile, String outputFile) throws Exception{
		
		BufferedReader testReader = new BufferedReader(new FileReader(testFile));

		String testPointsWithMask1 = "mask1.in";
		String testPointsWithMask2 = "mask2.in";
		String testPointsWithMask3 = "mask3.in";
		String testPointsWithMask4 = "mask4.in";

		ArrayList <BufferedWriter> maskPointsWriter = new ArrayList <BufferedWriter>();
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask1)));
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask2)));
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask3)));
		maskPointsWriter.add(new BufferedWriter(new FileWriter(testPointsWithMask4)));

		ArrayList <ArrayList <Integer> > lineMapping = new ArrayList <ArrayList <Integer> >();
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
			bw.close();
		}

		String mask1Output = "mask1.out";
		String mask2Output = "mask2.out";
		String mask3Output = "mask3.out";
		String mask4Output = "mask4.out";
		
		for(int maskRegion = 1;maskRegion <= 4; maskRegion++){
			LibSVMLoader libsvmLoader = new LibSVMLoader();
			libsvmLoader.setSource(new File("mask"+maskRegion+".in"));
			Instances test = libsvmLoader.getDataSet();
			
			NumericToNominal filter = new NumericToNominal();
	        filter.setInputFormat(test);
	        test = Filter.useFilter(test, filter);

			BufferedWriter bw = new BufferedWriter(new FileWriter("mask"+maskRegion+".out"));

			for (int i = 0; i < test.numInstances(); i++) {
				double pred = smoList.get(maskRegion-1).classifyInstance(test.instance(i));
				bw.write(data.classAttribute().value((int)pred)+"\n");

			}
			bw.close();
		}
		
		

		merge(lineMapping, mask1Output, mask2Output, mask3Output, mask4Output, outputFile);


	}

	private void merge(ArrayList <ArrayList <Integer> > lineMapping,String mask1Input, String mask2Input, String mask3Input, String mask4Input,String outputFile) throws IOException{
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

		writer.close();
		mask1Reader.close();
		mask2Reader.close();
		mask3Reader.close();
		mask4Reader.close();

	}

	

}
