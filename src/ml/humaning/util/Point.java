package ml.humaning.util;

import java.util.ArrayList;
import java.util.Arrays;


public class Point implements Comparable <Point>{
	private int zodiac;
	private Dimension [] dimensionArray;
	private double distanceToReference = 0.0;
	private int maskRegion = 0; // 0 means no blending , 1, 2, 3, 4 represents the empty area 

	public Point(String record){
		String [] tokens = record.split("\\s+");
		zodiac = Integer.parseInt(tokens[0]);

		ArrayList <Dimension> tempList = new ArrayList<Dimension>();
		for(int i = 1;i < tokens.length;i++){
			String [] dimensionAndGreyValue = tokens[i].split(":");
			tempList.add(new Dimension(Integer.parseInt(dimensionAndGreyValue[0]),
					Double.parseDouble(dimensionAndGreyValue[1])));
		}

		dimensionArray = new Dimension[tempList.size()];
		dimensionArray = tempList.toArray(dimensionArray);
		Arrays.sort(dimensionArray);

	}
	
	public void setMaskRegion(int r){
		maskRegion = r;		
	}
	/*
	 * get the empty region
	 *  --------------------
	 *  |        |         |
	 *  |   0    |    1    |
	 *  |        |         |
	 *  --------------------
	 *  |        |         |
	 *  |   2    |    3    |
	 *  |        |         |
	 *  --------------------
	 */
	

	private int getRegion(int pixel){
		int row = (pixel-1) / 105;
		int col = (pixel-1) % 105;

		if (row<105/2) { //UP
			if(col<105/2){
				return 1;
			}else{
				return 2;
			}
		} else {   //Down
			if (col < 105/2) {
				return 3;
			} else {
				return 4;
			}
		}
		
	}
	public int getEmptyRegion(){

		int[] region = new int[4];

		for (int x= 0; x< dimensionArray.length; x++){

			int pixel = dimensionArray[x].getDimension();
			region[getRegion(pixel)-1] += 1;

		}

		int mini = region[0];
		int idx  = 0;

		for(int x = 1; x < region.length; x++){
			if( mini > region[x] ) {
				mini = region[x];
				idx  = x;
		    }
		}

		return idx+1;
	}

	public int getZodiac() {
		return zodiac;
	}

	public void setZodiac(int zodiac) {
		this.zodiac = zodiac;
	}

	@Override
	public int compareTo(Point p) {
		if (this.distanceToReference < p.getDistanceToReference()) return -1;
		else if (this.distanceToReference > p.getDistanceToReference()) return 1;
		return 0;
	}

	public double getDistanceToReference() {
		return distanceToReference;
	}

	public void setDistanceToReference(double d) {
		distanceToReference = d;
	}

	public double innerProduct(Point p){
		int i = 0;
		int j = 0;
		double product = 0.0;

		while (i < this.dimensionArray.length && j < p.dimensionArray.length) {
			if (this.dimensionArray[i].getDimension() < p.dimensionArray[j].getDimension()) {
				i++;
			} else if (this.dimensionArray[i].getDimension() > p.dimensionArray[j].getDimension()) {
				j++;
			} else {
				if(getRegion(this.dimensionArray[i].getDimension()) != maskRegion)product += (this.dimensionArray[i].getValue()*p.dimensionArray[j].getValue());
				
				i++;
				j++;
			}
		}

		return product;
	}

	public double length(){
		double square = 0.0;
		for(Dimension d : dimensionArray){
			if(getRegion(d.getDimension()) != maskRegion)square += d.getValue() * d.getValue();
		}
		return Math.sqrt(square);
	}

	public double cosineSimilarity(Point p){
		double l = this.length();
		double pl = p.length();
		if (l != 0.0 && pl != 0.0) return this.innerProduct(p)/(l*pl);
		return 0.0;
	}
	
	public String toLIBSVMString(){
		String libsvm = zodiac + " ";
		for(Dimension d : dimensionArray){
			if(getRegion(d.getDimension()) != maskRegion){
				libsvm += d.getDimension()+":"+d.getValue()+" ";
			}
		}
		return libsvm.trim();
	}
	
	
	

	public double distance(Point p, boolean isNormalized){
		int i = 0;
		int j = 0;
		double distance = 0.0;
		
		double myLength = 0.0;
		double pLength = 0.0;
		if(isNormalized){
			myLength = this.length();
			pLength = p.length();
		}

		while (i < this.dimensionArray.length && j < p.dimensionArray.length) {
			if (this.dimensionArray[i].getDimension() < p.dimensionArray[j].getDimension() ) {
				if(getRegion(this.dimensionArray[i].getDimension()) != maskRegion){
					if(isNormalized)distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue()/(myLength*myLength));
					else distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue());						
				}
				i++;
			} else if (this.dimensionArray[i].getDimension() > p.dimensionArray[j].getDimension()) {
				if(getRegion(p.dimensionArray[j].getDimension()) != maskRegion){
					if(isNormalized)distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue()/(pLength*pLength));
					else distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue());
				}
				j++;
			} else {
				if(getRegion(this.dimensionArray[i].getDimension()) != maskRegion){
					if(isNormalized)distance += (this.dimensionArray[i].getValue()/myLength-p.dimensionArray[j].getValue()/pLength)*(this.dimensionArray[i].getValue()/myLength-p.dimensionArray[j].getValue()/pLength);
					else distance += (this.dimensionArray[i].getValue()-p.dimensionArray[j].getValue())*(this.dimensionArray[i].getValue()-p.dimensionArray[j].getValue()); 
				}
				i++;
				j++;
			}
		}

		while (i < this.dimensionArray.length) {
			if(getRegion(this.dimensionArray[i].getDimension()) != maskRegion){
				if(isNormalized)distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue()/(myLength*myLength));
				else distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue());
			}
			i++;
		}

		while (j < p.dimensionArray.length) {
			if(getRegion(p.dimensionArray[j].getDimension()) != maskRegion){
				if(isNormalized)distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue()/(pLength*pLength));
				else distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue());
			}
			j++;
		}

		return distance;
	}
}
