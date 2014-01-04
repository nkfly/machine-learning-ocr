package ml.humaning.algorithm;

import java.util.ArrayList;
import java.io.PrintWriter;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;

import weka.core.Instance;
import weka.core.Instances;

import ml.humaning.Runner;
import ml.humaning.util.Reader;
import ml.humaning.util.Logger;
import ml.humaning.validation.Validator;

public abstract class Algorithm implements Runner {
	Instances trainData;
	Instances testData;
	String outputPath;
	String runMode = "normal";
	Options options;
	CommandLine line;

	public void registerOptions() {
		if (options == null) options = new Options();

		Option trainFile = new Option("tr", "train-file", true, "train file");
		Option testFile  = new Option("te", "test-file", true, "test file");
		Option output    = new Option("o", "output", true, "output file");
		Option mode   	 = new Option("m", "mode", true, "run mode: cv, lm");

		options.addOption(trainFile);
		options.addOption(testFile);
		options.addOption(output);
		options.addOption(mode);
	}

	public boolean parseArgv(String [] argv, Options options) throws Exception {
		this.options = options;
		return parseArgv(argv);
	}

	private boolean checkTrainFileOptionIfNeed(CommandLine line) {
		if (this instanceof Aggregation) {
			return true; // aggregation don't need to read train file
		}

		return line.hasOption("train-file");
	}

	public boolean parseArgv(String [] argv) throws Exception {
		registerOptions();

		CommandLineParser parser = new GnuParser();
		line = parser.parse(options, argv, true);

		if (line.hasOption("mode")) {
			this.runMode = line.getOptionValue("mode");
		}

		if ("normal".equals(this.runMode)) {
			if (!checkTrainFileOptionIfNeed(line) ||
					!line.hasOption("test-file")) {
				return false;
			}

			if (line.hasOption("output")) {
				this.outputPath = line.getOptionValue("output");
			}

		} else if ("lm".equals(this.runMode)) {
			if (!line.hasOption("test-file")) {
				return false;
			}

			if (line.hasOption("output")) {
				this.outputPath = line.getOptionValue("output");
			}

		} else if ("cv".equals(this.runMode)) {
			if (!checkTrainFileOptionIfNeed(line)) {
				return false;
			}
		}

		if (this.outputPath == null) this.outputPath = this.getOutputPath();

		return true;
	}

	public abstract String getName();

	public String getOutputPath() {
		return "./data/" + this.getName() + "_output.dat";
	}

	public String getModelPath() {
		return "./data/" + this.getName() + ".model";
	}

	public void loadModel() throws Exception {
		throw new Exception("Not implemented yet");
	}

	public void saveModel() throws Exception {
		throw new Exception("Not implemented yet");
	}

	public abstract void train(Instances trainData) throws Exception;
	public abstract int predict(Instance inst) throws Exception;
	public ArrayList<Integer> predict(Instances data) throws Exception {
		ArrayList<Integer> results = new ArrayList<Integer>();

		for (Instance inst : data) {
			results.add(this.predict(inst));
		}

		return results;
	}

	private void writeResult(ArrayList<Integer> results) throws Exception {
		Logger.log("Write output to " + outputPath + "...");
		PrintWriter writer = new PrintWriter(this.outputPath, "UTF-8");

		for (Integer value : results) {
			writer.println("" + (value + 1));
		}

		writer.close();
		Logger.log("DONE");
	}

	public void runPredictWithTrainData() throws Exception {
		if (!(this instanceof Aggregation)) {
			this.trainData = Reader.readData(line.getOptionValue("train-file"));
		}
		this.testData = Reader.readData(line.getOptionValue("test-file"));

		this.train(trainData);
		ArrayList<Integer> results = this.predict(testData);

		saveModel();
		writeResult(results);
	}

	public void runPredictWithLoadModel() throws Exception {
		this.testData = Reader.readData(line.getOptionValue("test-file"));
		this.loadModel();

		ArrayList<Integer> results = this.predict(testData);
		writeResult(results);
	}

	protected Instances loadCVTrainData(int nFold, int fold) {
		return trainData.trainCV(nFold, fold);
	}

	protected Instances loadCVTestData(int nFold, int fold) {
		return trainData.testCV(nFold, fold);
	}

	public void runCrossValidation() throws Exception {
		if (!(this instanceof Aggregation)) {
			this.trainData = Reader.readData(line.getOptionValue("train-file"));
		}

		int N = 5;
		int i;
		double average = 0.0;

		for (i = 0; i < N; i++) {
			Instances trainCV = loadCVTrainData(N, i);
			Instances testCV = loadCVTestData(N, i);

			this.train(trainCV);
			ArrayList<Integer> results = this.predict(testCV);
			double error = Validator.getError(testCV, results);
			Logger.log("error = " + error);

			average += error;
		}
		average /= N;
		Logger.log("Cross Validation Error = " + average);
		Logger.log("Cross Validation Accuracy = " + (1.0 - average));
	}

	public void run() throws Exception {
		if ("normal".equals(this.runMode)) {
			Logger.log("Run Training -> Predict");

			this.runPredictWithTrainData();
		} else if ("lm".equals(this.runMode)) {
			Logger.log("Run Load model -> Predict");

			this.runPredictWithLoadModel();
		} else if ("cv".equals(this.runMode)) {
			Logger.log("Run Cross Validation");

			this.runCrossValidation();
		}
	}
}