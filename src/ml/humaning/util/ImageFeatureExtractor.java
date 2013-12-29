package ml.humaning.util;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.process.FloatProcessor;

import java.io.IOException;

public class ImageFeatureExtractor {
	Point [] allData;
	
	public void saveAllPoints(){
		for(int i = 0;i < allData.length;i++){
			FloatProcessor fp = new FloatProcessor(allData[i].getFloatArray());
			fp.invert();
			ImagePlus imp = new ImagePlus("test.tif", fp);
			IJ.saveAs(imp, "tif", "image"+i+".tif");
		}
		
	}
		
	
	public ImageFeatureExtractor(String trainFileInLibsvmFormat){
		try {
			allData = Reader.readPoints(trainFileInLibsvmFormat);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
		
	

}
