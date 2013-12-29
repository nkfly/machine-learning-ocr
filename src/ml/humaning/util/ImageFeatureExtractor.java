package ml.humaning.util;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.process.FloatProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ImageFeatureExtractor {
	Point [] allData;
	int [] measurementArray ;
	
	public void saveAllPoints(){
		for(int i = 0;i < allData.length;i++){
			savePoint(i, "image"+i+".tif");
		}
		
		
	}

	public void savePoint(int index, String name){
		FloatProcessor fp = new FloatProcessor(allData[index].getFloatArray());
		fp.invert();
		ImagePlus imp = new ImagePlus("test.tif", fp);
		IJ.saveAs(imp, "tif", "image.tif");
		
	}
	public void analyze(int index){
		FloatProcessor fp = new FloatProcessor(allData[index].getFloatArray());
		fp.invert();
		ImagePlus imp = new ImagePlus("test.tif", fp);		
		for (int measurement : measurementArray){
			ResultsTable rt = new ResultsTable(); 
			Analyzer analyzer = new Analyzer(imp, measurement, rt);
			analyzer.measure();
			analyzer.displayResults();
		}

	}
	
	public void extractFeature(String outputFile) throws IOException{
		
		
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		for(int i = 0;i < allData.length;i++){
			
			FloatProcessor fp = new FloatProcessor(allData[i].getFloatArray());
			fp.invert();
			ImagePlus imp = new ImagePlus("test.tif", fp);
			int index = 1;
			String record = allData[i].getZodiac() + " ";


			
			ResultsTable rt = new ResultsTable(); 
			Analyzer analyzer = new Analyzer(imp, Measurements.INTEGRATED_DENSITY, rt);
			analyzer.measure();
			record += (index++)+":"+rt.getValue("RawIntDen", 0)+ " ";
			
			rt = new ResultsTable(); 
			analyzer = new Analyzer(imp, Measurements.KURTOSIS, rt);
			analyzer.measure();
			record += (index++)+":"+rt.getValue("Kurt", 0)+ " ";
			
			
			rt = new ResultsTable(); 
			analyzer = new Analyzer(imp, Measurements.SKEWNESS, rt);
			analyzer.measure();
			record += (index++)+":"+rt.getValue("Skew", 0)+ " ";
			
			bw.write(record.trim()+"\n");
			
		}
		bw.close();
		
	}
		
	public ImageFeatureExtractor(String trainFileInLibsvmFormat){
		try {
			allData = Reader.readPoints(trainFileInLibsvmFormat);
			measurementArray = new int[]{Measurements.ADD_TO_OVERLAY
					,Measurements.AREA
					,Measurements.AREA_FRACTION
					,Measurements.CENTER_OF_MASS
					,Measurements.CENTROID
					,Measurements.CIRCULARITY
					,Measurements.ELLIPSE
					,Measurements.FERET
					,Measurements.INTEGRATED_DENSITY
					,Measurements.INVERT_Y
					,Measurements.KURTOSIS
					,Measurements.LABELS
					,Measurements.LIMIT
					,Measurements.MAX_STANDARDS
					,Measurements.MEAN
					,Measurements.MEDIAN
					,Measurements.MIN_MAX
					,Measurements.MODE
					,Measurements.PERIMETER
					,Measurements.RECT
					,Measurements.SCIENTIFIC_NOTATION
					,Measurements.SHAPE_DESCRIPTORS
					,Measurements.SKEWNESS
					,Measurements.SLICE
					,Measurements.STACK_POSITION
					,Measurements.STD_DEV};
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
		
	

}
