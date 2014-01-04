package ml.humaning.algorithm;

import java.util.ArrayList;
import java.util.Random;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.cli.Option;

import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LibLINEAR;
import weka.classifiers.Evaluation;

import ml.humaning.util.Logger;

public class LinearSVM extends Algorithm {

	private LibLINEAR libLinear;

	private int s;

	// ----- commandline options -----
	public void registerOptions() {
		super.registerOptions();

		Option s = new Option("s", true, "the s of LinearSVM");
		options.addOption(s);
	}

	public boolean parseArgv(String [] argv) throws Exception {
		if (!super.parseArgv(argv)) return false;

		if (!line.hasOption("s")) {
			Logger.log("Missing options: s");
			return false;
		}

		this.s = Integer.parseInt(line.getOptionValue('s'));

		return true;
	}

	// ----- naming -----
	public String getName() {
		return "linear_svm";
	}

	// ----- load/save model -----
	public void loadModel(String modelPath) throws Exception {
		Logger.log("LinearSVM: Loading model from " + modelPath + "...");

		ObjectInputStream in = new ObjectInputStream(new FileInputStream(modelPath));
		libLinear = (LibLINEAR)in.readObject();
		in.close();

		Logger.log("DONE");
	}

	public void saveModel(String modelPath) throws Exception {
		Logger.log("LinearSVM: Saving model to " + modelPath + "...");

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(modelPath));
		out.writeObject(libLinear);
		out.close();

		Logger.log("DONE");
	}

	// ----- train and predict -----
	public void train(Instances data) throws Exception {
		Logger.log("Start LibLinear train...");

		libLinear = new LibLINEAR();

		String[] options = new String[2];
		options[0] = "-S";
		options[1] = Integer.toString(this.s);

		libLinear.setOptions(options);
		libLinear.buildClassifier(data);

		/* Evaluation eval = new Evaluation(data); */
		/* eval.crossValidateModel(libLinear, data, 10, new Random()); */
		/* Logger.log("accuracy = " + eval.pctCorrect()); */

		/* Logger.log("s = " + this.s); */
		Logger.log("DONE");
	}

	public int predict(Instance inst) throws Exception {
		return (int)libLinear.classifyInstance(inst);
	}
}
