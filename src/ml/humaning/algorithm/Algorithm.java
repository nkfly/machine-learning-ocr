package ml.humaning.algorithm;

import java.util.ArrayList;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;

import weka.core.Instances;

import ml.humaning.util.Reader;

public abstract class Algorithm {

	Instances trainData;
	Instances testData;
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

		return true;
	}

	public abstract void train(Instances trainData) throws Exception;
	public abstract ArrayList<Integer> predict(Instances testData) throws Exception;

	public void run() throws Exception {
		this.train(trainData);
		this.predict(testData);

		// [todo] - write output file
	}
}
