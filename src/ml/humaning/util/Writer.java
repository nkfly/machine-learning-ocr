package ml.humaning.util;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.LibSVMSaver;

import ml.humaning.util.Logger;

public class Writer {

	public static void writeData(Instances data, String fileName) throws IOException {
		Logger.log("Write data from file " + fileName + "...");

		LibSVMSaver saver = new LibSVMSaver();
		saver.setFile(new File(fileName));
		saver.setInstances(data);
		saver.writeBatch();

		Logger.log("Done");
	}
}
