package ml.humaning.preprocessor;

import java.io.IOException;

import weka.core.Instances;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;

import ml.humaning.Runner;

import ml.humaning.util.Reader;
import ml.humaning.util.Writer;
import ml.humaning.util.Logger;

public abstract class Preprocessor implements Runner {

	Instances data;
	Options options;
	CommandLine line;
	String outputPath;

	public void registerOptions() {
		if (options == null) options = new Options();

		Option input = new Option("i", "input", true, "input file");
		Option output = new Option("o", "output", true, "output file");

		options.addOption(input);
		options.addOption(output);
	}

	// [todo] - same funciont in algorithm/Algorithm refactor it
	public boolean parseArgv(String [] argv, Options options) throws Exception {
		this.options = options;
		return parseArgv(argv);
	}

	public boolean parseArgv(String [] argv) throws Exception {
		registerOptions();

		CommandLineParser parser = new GnuParser();
		line = parser.parse(options, argv, true);

		if (!line.hasOption("input") || !line.hasOption("output")) {
			return false;
		}

		this.data = Reader.readData(line.getOptionValue("input"));
		this.outputPath = line.getOptionValue("output");

		return true;
	}

	protected void writeData(Instances data) {
		try {
			Writer.writeData(data, outputPath);
		} catch (IOException e) {
			Logger.log("Write data to " + outputPath + " fail");
			e.printStackTrace();
		}
	}
}

