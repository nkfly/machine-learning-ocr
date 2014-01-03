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

public abstract class Algorithm implements Runner {

	Instances trainData;
	Instances testData;
	String outputPath;
	Options options;
	CommandLine line;

	public void registerOptions() {
		if (options == null) options = new Options();

		Option trainFile = new Option("tr", "train-file", true, "train file");
		Option testFile = new Option("te", "test-file", true, "test file");
		Option output = new Option("o", "output", true, "output file");

		options.addOption(trainFile);
		options.addOption(testFile);
		options.addOption(output);
	}

	public boolean parseArgv(String [] argv, Options options) throws Exception {
		this.options = options;
		return parseArgv(argv);
	}

	public boolean parseArgv(String [] argv) throws Exception {
		registerOptions();

		CommandLineParser parser = new GnuParser();
		line = parser.parse(options, argv, true);

		if (!line.hasOption("train-file") ||
				!line.hasOption("test-file") ||
				!line.hasOption("output")) {
			return false;
		}

		this.trainData = Reader.readData(line.getOptionValue("train-file"));
		this.testData = Reader.readData(line.getOptionValue("test-file"));
		this.outputPath = line.getOptionValue("output");

		return true;
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

	public void run() throws Exception {
		this.train(trainData);
		ArrayList<Integer> results = this.predict(testData);

		writeResult(results);
	}
}
