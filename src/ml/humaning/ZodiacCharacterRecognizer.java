package ml.humaning;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;

import ml.humaning.util.CommandLineHelper;

public class ZodiacCharacterRecognizer {
	static Options options;

	public static void main(String [] argv) {
		prepareOptions();

		CommandLineParser parser = new GnuParser();

		try {
			// parse algorithm type (-a)
			CommandLine line = parser.parse(options, argv, true);
			if (!line.hasOption("a")) {
				printUsage();
				return;
			}

			// initial algorithm
			String algorithm = line.getOptionValue("a");
			Runner runner = RunnerFactory.create(algorithm);

			// parse argv for algorithm
			if (!runner.parseArgv(argv, options)) {
				printUsage();
				return;
			}

			runner.run();
		} catch (ParseException e) {
			System.err.println( "Parsing failed.  Reason: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void prepareOptions() {
		options = new Options();

		Option algorithm = new Option("a", "algorithm", true, "algorithm");
		options.addOption(algorithm);
	}

	public static void printUsage() {
		printUsage(options);
	}

	public static void printUsage(Options options) {
		CommandLineHelper.printUsage(options);
	}

	/* public static void runSVM(CommandLine line) throws Exception { */
	/* 	if (!line.hasOption("train-file")) { */
	/* 		printUsage(); */
	/* 		return; */
	/* 	} */

		/* SVM svm = new SVM(line.getOptionValue("train-file")); */
		/* svm.parallelCrossValidationSVM(0, 1); */
	/* } */

	/* public static void runSMO(CommandLine line) throws Exception { */
	/* 	if (!line.hasOption("train-file") || */
	/* 			!line.hasOption("test-file") || */
	/* 			!line.hasOption("output")) { */

	/* 		printUsage(); */
	/* 		return; */
	/* 	} */
	/* 	SMO smo = new SMO(); */
	/* 	String testFilePath = line.getOptionValue("test-file"); */
	/* 	String outputFilePath = line.getOptionValue("output"); */
	/* 	smo.train(line.getOptionValue("train-file")); */
	/* 	smo.predict(testFilePath, outputFilePath); */

	/* } */

	/* public static void runANN(CommandLine line) throws Exception { */
	/* 	if (!line.hasOption("train-file") || */
	/* 			!line.hasOption("test-file") || */
	/* 			!line.hasOption("output")) { */

	/* 		printUsage(); */
	/* 		return; */
	/* 	} */
	/* 	// argv[0] : -knn, argv[1] : trainFile , argv[2] : testFile , argv[3] outputFile */
	/* 	ANN ann = new ANN(); */
	/* 	String testFilePath = line.getOptionValue("test-file"); */
	/* 	String outputFilePath = line.getOptionValue("output"); */
	/* 	ann.train(line.getOptionValue("train-file")); */
	/* 	ann.predict(testFilePath, outputFilePath); */

	/* } */

	/* public static void runResample(CommandLine line) throws Exception { */
	/* 	Resampler resampler = new Resampler(); */
	/* 	if (!resampler.parseOptions(line)) { */
	/* 		printUsage(); */
	/* 		return; */
	/* 	} */

	/* 	resampler.run(); */
	/* } */

	/* public static void runFillAverage(CommandLine line) throws Exception { */
	/* 	AverageFiller filler = new AverageFiller(); */
	/* 	if (!filler.parseOptions(line)) { */
	/* 		printUsage(); */
	/* 		return; */
	/* 	} */

	/* 	filler.run(); */
	/* } */
}
