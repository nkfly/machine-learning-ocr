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
				if(d.getValue() > _gradient &&  d.getDimension() % 105 < _left){
					_left = d.getDimension() % 105;
				}
				if(d.getValue() > _gradient && d.getDimension() % 105 > _right){
					_right = d.getDimension() % 105;
				}
	            if(d.getValue() > _gradient && d.getDimension() / 105 < _top){
	            	_top = d.getDimension() / 105;
	            }
	            if(d.getValue() > _gradient && d.getDimension() / 105 > _bottom){
	            	_bottom = d.getDimension() / 105;
	            }
	                
	                
			}
			
		} 
		
		
	}

}
