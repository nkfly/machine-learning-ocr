package ml.humaning.preprocessor;

import java.util.Random;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

import weka.core.Instances;
import weka.core.Instance;

import ml.humaning.util.Logger;


public class Resampler extends Preprocessor {

	private int numberOfSample;

	public void registerOptions() {
		super.registerOptions();

		options.addOption(new Option("rspn", "resample-number", true, "number of resample"));
	}

	public boolean parseArgv(String [] argv) throws Exception {
		if (!super.parseArgv(argv)) return false;

		if (!line.hasOption("resample-number")) {
			Logger.log("Missing option: resample-number");
			return false;
		}

		this.numberOfSample = Integer.parseInt(line.getOptionValue("resample-number"));

		return true;
	}

	public Instances resample(Instances data) {
		Logger.log("Resampling...");

		Instances sampled = new Instances(data, 0, numberOfSample);
		sampled.randomize(new Random(System.currentTimeMillis()));

		Logger.log("Done");
		return sampled;
	}

	public void run() throws Exception {
		data = resample(data);
		this.writeData(data);
	}
}
