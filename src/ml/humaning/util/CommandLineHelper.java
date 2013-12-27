package ml.humaning.util;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;

public class CommandLineHelper {
	public static void printUsage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("ZodiacCharacterRecognizer", options);
	}
}
