package ml.humaning.util;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.measure.Measurements;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.imageio.ImageIO;

import de.lmu.ifi.dbs.jfeaturelib.edgeDetector.Canny;
import de.lmu.ifi.dbs.jfeaturelib.features.CEDD;
import de.lmu.ifi.dbs.jfeaturelib.features.FCTH;
import de.lmu.ifi.dbs.jfeaturelib.features.Gabor;
import de.lmu.ifi.dbs.jfeaturelib.features.Haralick;
import de.lmu.ifi.dbs.jfeaturelib.features.LocalBinaryPatterns;
import de.lmu.ifi.dbs.jfeaturelib.features.MPEG7EdgeHistogram;
import de.lmu.ifi.dbs.jfeaturelib.features.PHOG;
import de.lmu.ifi.dbs.jfeaturelib.features.Sift;
import de.lmu.ifi.dbs.jfeaturelib.features.Tamura;
import de.lmu.ifi.dbs.jfeaturelib.shapeFeatures.AdaptiveGridResolution;
import de.lmu.ifi.dbs.jfeaturelib.shapeFeatures.PolygonEvolution;
import de.lmu.ifi.dbs.utilities.Arrays2;

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
	public static void addFeature(int maxDimension, String inputFile, String outputFile) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		Point [] data = Reader.readPoints(inputFile);
		for(int i = 0;i < data.length;i++){
			int extremeDimension = maxDimension;
			FloatProcessor fp = new FloatProcessor(data[i].getFloatArray());
			fp.invert();
			ImagePlus imp = new ImagePlus("test.tif", fp);
			
			String record = data[i].toLIBSVMString()+" ";
			
			ResultsTable rt = new ResultsTable();
			rt = new ResultsTable();
			
//			Analyzer analyzer = new Analyzer(imp, Measurements.INTEGRATED_DENSITY, rt);
//			analyzer.measure();
//			record += (++extremeDimension)+":"+rt.getValue("RawIntDen", 0)+ " ";
			
//			rt = new ResultsTable(); 
//			Analyzer analyzer = new Analyzer(imp, Measurements.KURTOSIS, rt);
//			analyzer.measure();
//			record += (++extremeDimension)+":"+rt.getValue("Kurt", 0)+ " ";
			
			
			rt = new ResultsTable(); 
			Analyzer analyzer = new Analyzer(imp, Measurements.SKEWNESS, rt);
			analyzer.measure();
			record += (++extremeDimension)+":"+rt.getValue("Skew", 0)+ " ";
			
			bw.write(record.trim()+"\n");
			
		}
		br.close();
		bw.close();
		
		
	}
	
	public static void featureExtract(String inputFile, String outputFile) throws URISyntaxException, IOException{
		Point [] allData = Reader.readPoints(inputFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		
		for(int i = 0;i < allData.length;i++){
			FloatProcessor fp = new FloatProcessor(allData[i].getFloatArray());
			fp.invert();
			//ImagePlus imp = new ImagePlus("test.jpg", fp);
			//IJ.saveAs(imp, "jpg", "image"+i+".jpg");
			
			//File f = new File("image"+i+".jpg");
	        //ColorProcessor image = new FloatProcessor(ImageIO.read(f));
	        
			Tamura descriptor = new Tamura() ;
	        //descriptor.setNeighborhoodSize(8);
	        //descriptor.setNumberOfHistogramBins(3);

	        // run the descriptor and extract the features
	        descriptor.run(fp);

	        // obtain the features
	        List<double[]> features = descriptor.getFeatures();

	        // print the features to system out
	        String record = allData[i].getZodiac() + " ";
	        int index = 1;
	        for (double[] feature : features) {
	        	for(double d : feature){
	        		record += (index++)+":"+d+" ";
	        	}
	            //record += System.out.println(Arrays2.join(feature, ", ", "%.5f"));
	        }
	        bw.write(record.trim()+"\n");
	        //f.delete();
			
		}
		bw.close();
		

        // initialize the descriptor
        
		
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
