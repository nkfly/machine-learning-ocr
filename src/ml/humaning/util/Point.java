package ml.humaning.util;

import java.util.Arrays;
import java.util.Vector;

public class Point implements Comparable{
	private int zodiac;
	private Dimension [] dimensionArray;
	private double distanceToReference;
	
	public Point(String record){
		String [] tokens = record.split("\\s+");
		zodiac = Integer.parseInt(tokens[0]);
		
		
		Vector <Dimension> tempVector = new Vector<Dimension>();
		for(int i = 1;i < tokens.length;i++){
			String [] dimensionAndGreyValue = tokens[i].split(":");
			tempVector.add(new Dimension(Integer.parseInt(dimensionAndGreyValue[0]), 
					Double.parseDouble(dimensionAndGreyValue[1])));
			
		}
		dimensionArray = new Dimension[tempVector.size()];
		dimensionArray = tempVector.toArray(dimensionArray);
		Arrays.sort(dimensionArray);
		
		distanceToReference = 0.0;
		
		
	}

	public int getZodiac() {
		return zodiac;
	}

	public void setZodiac(int zodiac) {
		this.zodiac = zodiac;
	}

	@Override
	public int compareTo(Object o) {
		Point p = (Point)o;
		if(this.distanceToReference < p.getDistanceToReference())return -1;
		else if(this.distanceToReference > p.getDistanceToReference())return 1;
		return 0;
	}
	
	public double getDistanceToReference(){
		return distanceToReference;
	}
	
	public void setDistanceToReference(double d){
		distanceToReference = d;
	}
	
	public double distance(Point p){
		int i = 0;
		int j = 0;
		double distance = 0.0;
		while(i < this.dimensionArray.length && j < p.dimensionArray.length){
			if(this.dimensionArray[i].getDimension() < p.dimensionArray[j].getDimension() ){
				distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue());
				i++;
			}else if(this.dimensionArray[i].getDimension() > p.dimensionArray[j].getDimension()){
				distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue());
				j++;								
			}else {
				distance += (this.dimensionArray[i].getValue()-p.dimensionArray[j].getValue())*
						(this.dimensionArray[i].getValue()-p.dimensionArray[j].getValue());
				i++;
				j++;
			}
		}
		while(i < this.dimensionArray.length ){		
			distance += (this.dimensionArray[i].getValue()*this.dimensionArray[i].getValue());
			i++;
		}
		
		while(j < p.dimensionArray.length ){		
			distance += (p.dimensionArray[j].getValue()*p.dimensionArray[j].getValue());
			j++;
		}
		
		return distance;
		
		
	}

}