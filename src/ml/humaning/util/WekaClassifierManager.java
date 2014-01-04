package ml.humaning.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;

import ml.humaning.util.Logger;

public class WekaClassifierManager {

	public static Classifier loadClassifier(String modelPath) throws Exception {
		Classifier classifier;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(modelPath));
		classifier = (Classifier)in.readObject();
		in.close();

		return classifier;
	}

	public static void saveClassifier(Classifier classifier, String modelPath)
		throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(modelPath));
		out.writeObject(classifier);
		out.close();
	}
}
