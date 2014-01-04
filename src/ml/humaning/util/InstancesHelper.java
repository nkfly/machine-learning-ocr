package ml.humaning.util;

import java.util.ArrayList;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.SparseInstance;
import weka.core.Attribute;

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

	public static Instances genInstancesWithNumAttribute(String name, int nAttr,
																	Instances origin) {
		// generate attributes
		Attribute classAttribute = origin.classAttribute();
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for (int i = 0; i < nAttr; i++) {
			attributes.add(new Attribute("attr" + i));
		}
		attributes.add(classAttribute);

		// generate data
		Instances data = new Instances(name, attributes, nAttr + 1);
		data.setClass(classAttribute);

		return data;
	}
}
