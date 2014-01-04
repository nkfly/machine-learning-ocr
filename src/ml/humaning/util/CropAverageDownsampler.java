package ml.humaning.util;

import java.io.IOException;

public class CropAverageDownsampler {
	private int [] emptyRegionMapping;
	Point [] originalData;
	public CropAverageDownsampler(String trainFile) throws IOException{
		originalData = Reader.readPoints(trainFile);
				
	}
	
	public void crop(){
		emptyRegionMapping = new int[originalData.length];
		for(int i = 0;i < originalData.length;i++){
			emptyRegionMapping[i] = originalData[i].getEmptyRegion();
		}
		
		double _gradient = 0.1;
		for(int i = 0;i < originalData.length;i++){
			int _left = 105;
			int _right = 0;
			int _top  = 105;
			int _bottom = 0;
			
			for(Dimension d : originalData[i].getDimensionArray()){
				if(d.getValue() > _gradient &&  d.getDimension() % 105 < _left):
	                _left = elem[0] % 105
	            if(elem[1] > _gradient and elem[0] % 105 > _right):
	                _right = elem[0] % 105
	            if(elem[1] > _gradient and elem[0] / 105 < _top):
	                _top = elem[0] / 105
	            if(elem[1] > _gradient and elem[0] / 105 > _bottom):
	                _bottom = elem[0] / 105
			}
			
		} 
		
		
	}

}
