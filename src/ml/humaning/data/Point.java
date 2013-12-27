package ml.humaning.data;

import java.util.ArrayList;
import java.util.Arrays;

import weka.core.Instance;

import ml.humaning.util.Logger;

public class Point {

	public static final int IMAGE_WIDTH = 105;
	public static final int IMAGE_HIGHT = 122;

	/* private int zodiac; */
	/* private Dimension [] dimensionArray; */
	/* private double distanceToReference = 0.0; */
	/* private int maskRegion = 0; // 0 means no blending , 1, 2, 3, 4 represents the empty area */

	private Instance inst;

	public Point(Instance inst) {
		this.inst = inst;
	}

	// ----- Getter and Setter -----
	public Instance instance() {
		return inst;
	}

	public double value(int i) {
		return inst.value(i);
	}

	public int getZodiac() {
		return (int)inst.classValue();
	}

	public double length(){
		double square = 0.0;

		for (int i = 0; i < IMAGE_WIDTH * IMAGE_HIGHT; i++) {
			double value = this.value(i);
			square += value * value;
		}

		return Math.sqrt(square);
	}

	public double innerProduct(Point p){
		double product = 0.0;

		for (int i = 0; i < IMAGE_WIDTH * IMAGE_HIGHT; i++) {
			product += this.value(i) * p.value(i);
		}

		return product;
	}

	public double cosineSimilarity(Point p){
		double l = this.length();
		double pl = p.length();
		if (l != 0.0 && pl != 0.0) return this.innerProduct(p)/(l*pl);
		return 0.0;
	}

	// ----- Region Related -----
	/* public void setMaskRegion(int r){ */
	/* 	maskRegion = r; */
	/* } */
	/* public Dimension[] getDimensionArray() { */
	/* 	return dimensionArray; */
	/* } */


	/*
	 * get the empty region
	 *  --------------------
	 *  |        |         |
	 *  |   1    |    2    |
	 *  |        |         |
	 *  --------------------
	 *  |        |         |
	 *  |   3    |    4    |
	 *  |        |         |
	 *  --------------------
	 */

	public int getRegion(int pixel){
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
/*  */
/* 		for (int x= 0; x< dimensionArray.length; x++){ */
/*  */
/* 			int pixel = dimensionArray[x].getDimension(); */
/* 			region[getRegion(pixel)-1] += 1; */
/*  */
/* 		} */

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

	/* public double distance(Point p, boolean isNormalized){ */
	/* 	int i = 0; */
	/* 	int j = 0; */
	/* 	double distance = 0.0; */

	/* 	double myLength = 0.0; */
	/* 	double pLength = 0.0; */
	/* 	if(isNormalized){ */
	/* 		myLength = this.length(); */
	/* 		pLength = p.length(); */
	/* 	} */

	/* 	while (i < this.dimensionArray.length && j < p.dimensionArray.length) { */
	/* 		if (this.dimensionArray[i].getDimension() < p.dimensionArray[j].getDimension() ) { */
	/* 			if(getRegion(this.dimensionArray[i].getDimension()) != maskRegion){ */
	/* 				if(isNormalized)distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue()/(myLength*myLength)); */
	/* 				else distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue());						 */
	/* 			} */
	/* 			i++; */
	/* 		} else if (this.dimensionArray[i].getDimension() > p.dimensionArray[j].getDimension()) { */
	/* 			if(getRegion(p.dimensionArray[j].getDimension()) != maskRegion){ */
	/* 				if(isNormalized)distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue()/(pLength*pLength)); */
	/* 				else distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue()); */
	/* 			} */
	/* 			j++; */
	/* 		} else { */
	/* 			if(getRegion(this.dimensionArray[i].getDimension()) != maskRegion){ */
	/* 				if(isNormalized)distance += (this.dimensionArray[i].getValue()/myLength-p.dimensionArray[j].getValue()/pLength)*(this.dimensionArray[i].getValue()/myLength-p.dimensionArray[j].getValue()/pLength); */
	/* 				else distance += (this.dimensionArray[i].getValue()-p.dimensionArray[j].getValue())*(this.dimensionArray[i].getValue()-p.dimensionArray[j].getValue());  */
	/* 			} */
	/* 			i++; */
	/* 			j++; */
	/* 		} */
	/* 	} */

	/* 	while (i < this.dimensionArray.length) { */
	/* 		if(getRegion(this.dimensionArray[i].getDimension()) != maskRegion){ */
	/* 			if(isNormalized)distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue()/(myLength*myLength)); */
	/* 			else distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue()); */
	/* 		} */
	/* 		i++; */
	/* 	} */

	/* 	while (j < p.dimensionArray.length) { */
	/* 		if(getRegion(p.dimensionArray[j].getDimension()) != maskRegion){ */
	/* 			if(isNormalized)distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue()/(pLength*pLength)); */
	/* 			else distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue()); */
	/* 		} */
	/* 		j++; */
	/* 	} */

	/* 	return distance; */
	/* } */

	/* public String cutThreshold(double threshold){ */
	/* 	String representation = zodiac + " "; */
	/* 	for(Dimension d : dimensionArray){ */
	/* 		if(d.getValue() > threshold){ */
	/* 			representation += d.getDimension()+":"+d.getValue()+" "; */
	/* 		} */
	/* 	} */
	/* 	return representation.trim(); */
	/* } */
}
