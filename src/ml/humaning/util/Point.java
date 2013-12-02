package ml.humaning.util;

import java.util.Arrays;
import java.util.Vector;

public class Point {
	private int chineseZodiac;
	private Dimension [] dimensionArray;
	
	public Point(String record){
		String [] tokens = record.split("\\s+");
		setChineseZodiac(Integer.parseInt(tokens[0]));
		
		
		Vector <Dimension> tempVector = new Vector<Dimension>();
		for(int i = 1;i < tokens.length;i++){
			String [] dimensionAndGreyValue = tokens[i].split(":");
			tempVector.add(new Dimension(Integer.parseInt(dimensionAndGreyValue[0]), 
					Double.parseDouble(dimensionAndGreyValue[1])));
			
		}
		dimensionArray = new Dimension[tempVector.size()];
		dimensionArray = tempVector.toArray(dimensionArray);
		Arrays.sort(dimensionArray);
		
		
	}

	public int getChineseZodiac() {
		return chineseZodiac;
	}

	public void setChineseZodiac(int chineseZodiac) {
		this.chineseZodiac = chineseZodiac;
	}

}
