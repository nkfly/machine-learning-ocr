package ml.humaning.preprocessor;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.SparseInstance;

import ml.humaning.util.OptionsHandler;
import ml.humaning.util.Logger;

import ml.humaning.data.Point;

import ml.humaning.util.Reader;
import ml.humaning.util.Writer;

// [todo] - abstract the filter in terms of option parsing
public class AverageFiller implements OptionsHandler {
	private String inputFilePath;
	private String outputFilePath;

	private Instance [] averages;
	private int [] zodiacCount;

	public void registerOptions(Options options) {
		options.addOption(new Option("i", "input", true, "input file"));
		options.addOption(new Option("o", "output", true, "output file"));
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

		return success;
	}

	private Instance getCurrentAverage(int zodiac, Instance initInst) {
		Instance inst = averages[zodiac];

		if (inst == null){
			inst = new SparseInstance(initInst);
			averages[zodiac] = inst;
		}

		return inst;
	}

	public void calculateAverage(Instances data) {
		Logger.log("Calculating Average...");

		int l = Point.IMAGE_WIDTH * Point.IMAGE_HEIGHT;

		averages = new Instance[13];
		zodiacCount = new int[13];
		int counter = 0;

		for (Instance inst : data) {
			Point point = new Point(inst);

			int zodiac = point.getZodiac();
			Instance average = getCurrentAverage(zodiac, inst);
			zodiacCount[zodiac]++;

			Logger.log("Adding..." + counter);

			for (int i = 0; i < l; i++) {
				average.setValue(i, average.value(i) + inst.value(i));
			}

			counter++;
		}

		for (int i = 1; i <= 12; i++) {
			Instance average = averages[i];
			int count = zodiacCount[i];

			for (int j = 0; j < l; j++) {
				average.setValue(j, average.value(j)/count);
			}
		}

		Logger.log("Done");
	}

	public Instances fill(Instances data) {
		Logger.log("Filling Average...");

		calculateAverage(data);
		int count = 0;

		for (Instance inst : data) {
			Logger.log("Filling instance..." + count);
			fill(inst);
			count++;
		}

		Logger.log("Done");

		return data;
	}

	public Instance fill(Instance inst) {
		Point point = new Point(inst);
		Instance average = averages[point.getZodiac()];

		int emptyRegion = point.getEmptyRegion();
		for (int r = 0; r < Point.IMAGE_HEIGHT; r++) {
			for (int c = 0; c < Point.IMAGE_WIDTH; c++) {
				if (Point.toRegion(r, c) == emptyRegion) {
					int index = Point.toIndex(r, c);
					inst.setValue(index, average.value(index));
				}
			}
		}
		return inst;
	}

	public void run() throws Exception {
		Instances data = Reader.readData(inputFilePath);
		fill(data);
		Writer.writeData(data, outputFilePath);
	}
}
