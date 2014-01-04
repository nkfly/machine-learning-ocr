package ml.humaning.preprocessor;

import java.util.ArrayList;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.SparseInstance;
import weka.core.Attribute;

import ml.humaning.util.Logger;
import ml.humaning.data.Point;

public class DownSampler extends Preprocessor {
	private int scaleX;
	private int scaleY;
	private int width;
	private int height;

	public void registerOptions() {
		super.registerOptions();

		options.addOption(new Option("x", "scale-x", true,
										"number of x pixel to compress"));
		options.addOption(new Option("y", "scale-y", true,
										"number of y pixel to compress"));
		options.addOption(new Option("s", "scale", true,
										"number of pixel to compress (x == y)"));
	}

	public boolean parseArgv(String [] argv) throws Exception {
		if (!super.parseArgv(argv)) return false;

		if (!(line.hasOption("scale-x") && line.hasOption("scale-y"))) {
			if (!line.hasOption("scale")) {
				return false;
			} else {
				this.scaleX = Integer.parseInt(line.getOptionValue("scale"));
				this.scaleY = this.scaleX;
			}
		} else {
			this.scaleX = Integer.parseInt(line.getOptionValue("scale-x"));
			this.scaleY = Integer.parseInt(line.getOptionValue("scale-y"));
		}

		return true;
	}

	public Instances downSample(Instances data) {
		Logger.log("DownSampling...");

		width = Point.IMAGE_WIDTH / scaleX;
		height = Point.IMAGE_HEIGHT / scaleY;
		int numAttr = width * height;

		// construct sampled instances
		Attribute classAttribute = data.classAttribute();
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		for (int i = 0; i < numAttr; i++) {
			attributes.add(new Attribute("attr" + i));
		}
		attributes.add(classAttribute);

		Instances sampled = new Instances("downsampled", attributes,
														data.numInstances());
		sampled.setClass(classAttribute);

		/* Instance s = data.instance(9); */
		for (Instance s: data) {
			sampled.add(downSample(s, sampled));
		}

		Logger.log("Done");
		return sampled;
	}

	public Instance downSample(Instance s, Instances dataset) {
		SparseInstance t = new SparseInstance(width * height + 1);
		int sClassIndex = s.classIndex();
		for (int tr = 0; tr < height; tr++) {
			for (int tc = 0; tc < width; tc++) {
				double sum = 0.0;

				for (int i = 0; i < scaleY; i++) {
					int sr = tr * scaleY + i;
					if (sr >= Point.IMAGE_HEIGHT) continue;

					for (int j = 0; j < scaleX; j++) {
						int sc = tc * scaleX + j;
						if (sc >= Point.IMAGE_WIDTH) continue;
						if (sr * Point.IMAGE_WIDTH + sc >= sClassIndex) continue;

						sum += s.value(sr * Point.IMAGE_WIDTH + sc);
					}
				}

				double value = sum/(scaleX * scaleY);
				t.setValue(tr * width + tc, value);
			}
		}

		t.setDataset(dataset);
		t.setClassValue(s.classValue());

		return t;
	}

	public void run() throws Exception {
		data = downSample(data);
		this.writeData(data);
	}
}
