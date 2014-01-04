package ml.humaning.util;

import java.util.ArrayList;

import weka.core.Instances;
import weka.core.Instance;

import ml.humaning.data.Point;

public class InstancesHelper {
	public static ArrayList<Instance> toArray(Instances data) {
		int length = data.numInstances();
		ArrayList<Instance> array = new ArrayList<Instance>();
		for (Instance inst : data) {
			array.add(inst);
		}
		return array;
	}

	public static ArrayList<Point> toPointArray(Instances data) {
		int length = data.numInstances();
		ArrayList<Point> array = new ArrayList<Point>();
		for (Instance inst : data) {
			array.add(new Point(inst));
		}
		return array;
	}
}
