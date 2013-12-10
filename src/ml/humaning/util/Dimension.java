package ml.humaning.util;

public class Dimension implements Comparable<Dimension>{
	private int dimension;
	private double value;
	public Dimension(int d, double v){
		dimension = d;
		value = v;


	}
	@Override
	public int compareTo(Dimension d) {
		if(this.dimension < d.getDimension())return -1;
		else if(this.dimension > d.getDimension())return 1;
		return 0;
	}

	public int getDimension(){
		return dimension;
	}
	public double getValue(){
		return value;

	}




}
