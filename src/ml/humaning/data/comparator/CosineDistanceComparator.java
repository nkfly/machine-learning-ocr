package ml.humaning.data.comparator;

import java.util.Comparator;
import java.util.HashMap;
import ml.humaning.data.Point;
import ml.humaning.util.Logger;

public class CosineDistanceComparator implements Comparator<Point> {

	private Point referencePoint;
	private HashMap<Point, Double> cache;

	public CosineDistanceComparator(Point point) {
		this.referencePoint = point;
		this.cache = new HashMap<Point, Double>();
	}

	public int compare(Point p1, Point p2) {
		double d1 = getCosineSimilarity(p1);
		double d2 = getCosineSimilarity(p2);

		if (d1 > d2) {
			return -1;
		}

		if (d1 < d2) {
			return 1;
		}

		return 0;
	}

	private double getCosineSimilarity(Point p) {
		Double obj = cache.get(p);
		if (obj != null) return obj.doubleValue();

		double value = referencePoint.cosineSimilarity(p);
		cache.put(p, value);
		return value;
	}

}
