package ml.humaning.util;

import java.util.Arrays;
import java.util.Vector;

public class Point implements Comparable <Point>{
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
	public int getEmptyRegion(){
		
		int[] region = new int[4];
		
		for (int x= 0; x< dimensionArray.length;x++){
			
			int pixel = dimensionArray[x].getDimension();
			int row = (pixel-1) / 105;
			int col = (pixel-1) % 105;
			
			if(row<105/2){ //UP
				if(col<105/2){
					region[0] +=1;
				}else{
					region[1] +=1;
				}
			}else{   //Down
				if(col<105/2){
					region[2] +=1;
				}else{
					region[3] +=1;
				}
			}
		}
		int mini=region[0];
		int idx =0;
		for(int x=1; x< region.length;x++){
			if(mini>region[x]) {
				mini=region[x];
				idx = x;
		    }
		}
		return idx ;
		
	}
	
	public int getZodiac() {
		return zodiac;
	}

	public void setZodiac(int zodiac) {
		this.zodiac = zodiac;
	}

	@Override
	public int compareTo(Point p) {		
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
	
	public double innerProduct(Point p){
		int i = 0;
		int j = 0;
		double product = 0.0;
		while(i < this.dimensionArray.length && j < p.dimensionArray.length){
			if(this.dimensionArray[i].getDimension() < p.dimensionArray[j].getDimension() ){
				i++;
			}else if(this.dimensionArray[i].getDimension() > p.dimensionArray[j].getDimension()){
				j++;								
			}else {
				product += (this.dimensionArray[i].getValue()*p.dimensionArray[j].getValue());
				i++;
				j++;
			}
		}
			
		return product;
		
	}
	
	public double length(){
		double square = 0.0;
		for(Dimension d : dimensionArray){
			square += d.getValue()*d.getValue();
		}
		return Math.sqrt(square);
	}
	
	public double cosineSimilarity(Point p){
		double l = this.length();
		double pl = p.length();
		if(l != 0.0 && pl != 0.0)return this.innerProduct(p)/(l*pl);
		return 0.0;
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
