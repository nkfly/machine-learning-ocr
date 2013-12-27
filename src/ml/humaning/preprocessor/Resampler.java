package ml.humaning.preprocessor;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

import weka.core.Instances;
import weka.core.Instance;

import ml.humaning.util.OptionsHandler;
import ml.humaning.util.Logger;

import ml.humaning.util.Reader;
import ml.humaning.util.Writer;

public class Resampler implements OptionsHandler {

	private String inputFilePath;
	private String outputFilePath;
	private int numberOfSample;

	public void registerOptions(Options options) {
		options.addOption(new Option("i", "input", true, "input file"));
		options.addOption(new Option("o", "output", true, "output file"));
		options.addOption(new Option("rspn", "resample-number", true, "number of resample"));
	}

	public boolean parseOptions(CommandLine line) {
		boolean success = true;
		if (line.hasOption("input")) {
			inputFilePath = line.getOptionValue("input");
		} else {
			Logger.log("Missing option: input");
			success = false;
		}

		if (line.hasOption("output")) {
			outputFilePath = line.getOptionValue("output");
		} else {
			Logger.log("Missing option: output");
			success = false;
		}

		if (line.hasOption("resample-number")) {
			numberOfSample = Integer.parseInt(line.getOptionValue("resample-number"));
		} else {
			Logger.log("Missing option: resample-number");
			success = false;
		}

		return success;
	}

	public Instances resample(Instances data){
		Logger.log("Resampling...");

		Instances sampled = new Instances(data, 0, numberOfSample);
		sampled.randomize(new Random(System.currentTimeMillis()));

		Logger.log("Done");
		return sampled;
	}

	public void run() throws Exception {
		Instances data = Reader.readData(inputFilePath);
		data = resample(data);
		Writer.writeData(data, outputFilePath);
	}
}
