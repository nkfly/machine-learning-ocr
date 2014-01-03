package ml.humaning.data;

import java.util.ArrayList;
import java.util.Arrays;

import weka.core.Instance;

import ml.humaning.util.Logger;

public class Point {

	public static final int IMAGE_WIDTH = 105;
	public static final int IMAGE_HEIGHT = 122;

	/* private int zodiac; */
	/* private Dimension [] dimensionArray; */
	/* private double distanceToReference = 0.0; */
	/* private int maskRegion = 0; // 0 means no blending , 1, 2, 3, 4 represents the empty area */

	private Instance inst;
	private int emptyRegion;

	public Point(Instance inst) {
		this.inst = inst;
		this.emptyRegion = getEmptyRegion();
	}

	// ----- Getter and Setter -----
	public Instance instance() {
		return inst;
	}

	public double value(int i) {
		return inst.value(i);
	}

	public double [] toDoubleArray() {
		return inst.toDoubleArray();
	}

	public int getZodiac() {
		return (int)inst.classValue();
	}

	public double length(){
		double square = 0.0;
		double [] values = inst.toDoubleArray();

		for (int i = 0; i < values.length - 1; i++) {
			if (toRegion(i) == emptyRegion) continue;

			double value = values[i];
			square += value * value;
		}

		return Math.sqrt(square);
	}

	public double lengthWithoutRegion(int region) {
		double square = 0.0;
		double [] values = inst.toDoubleArray();

		for (int i = 0; i < values.length - 1; i++) {
			if (toRegion(i) == region) continue;

			double value = values[i];
			square += value * value;
		}

		return Math.sqrt(square);
	}

	public double innerProduct(Point p){
		double product = 0.0;

		double [] values = inst.toDoubleArray();
		double [] pValues = p.inst.toDoubleArray();

		for (int i = 0; i < values.length - 1; i++) {
			if (toRegion(i) == emptyRegion) continue;

			product += values[i] * pValues[i];
		}

		return product;
	}

	public double cosineSimilarity(Point p) {
		double l = this.length();
		double pl = p.lengthWithoutRegion(this.emptyRegion);
		if (l != 0.0 && pl != 0.0) return this.innerProduct(p)/(l*pl);
		return 0.0;
	}

	// ----- Region Related -----

	/*
	 *  -------------------
	 *  |        |        |
	 *  |   1    |   2    |
	 *  |        |        |
	 *  -------------------
	 *  |        |        |
	 *  |   3    |   4    |
	 *  |        |        |
	 *  -------------------
	 */

	/* public void setMaskRegion(int r){ */
	/* 	maskRegion = r; */
	/* } */
	/* public Dimension[] getDimensionArray() { */
	/* 	return dimensionArray; */
	/* } */

	public static int toRegion(int r, int c) {
		int u = r / (Point.IMAGE_HEIGHT/2);
		int v = c / (Point.IMAGE_WIDTH/2);
		if (u > 1) u = 1;
		if (v > 1) v = 1;

		return u * 2 + v + 1;
	}

	public int toRegion(int index){
		int row = index / Point.IMAGE_WIDTH;
		int col = index % Point.IMAGE_WIDTH;

		return toRegion(row, col);
	}

	public static int toIndex(int r, int c) {
		return r * Point.IMAGE_WIDTH + c;
	}

	public int getEmptyRegion(){

		double [] regions = new double[4];

		for (int i = 0; i < Point.IMAGE_WIDTH * Point.IMAGE_HEIGHT; i++) {
			int idx = toRegion(i);
			regions[idx - 1] += inst.value(i);
		}

		double mini = regions[0];
		int idx  = 0;

		for(int x = 1; x < regions.length; x++){
			if( mini > regions[x] ) {
				mini = regions[x];
				idx  = x;
		    }
		}

		return idx + 1;
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
