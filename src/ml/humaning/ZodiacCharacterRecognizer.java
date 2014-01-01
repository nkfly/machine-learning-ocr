package ml.humaning;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

import ml.humaning.algorithm.ANN;
import ml.humaning.algorithm.NB;
import ml.humaning.algorithm.SMO;
import ml.humaning.algorithm.SVM;
import ml.humaning.algorithm.KNN;
import ml.humaning.util.ImageFeatureExtractor;
import ml.humaning.util.Preprocess;
import ml.humaning.util.SVD;

public class ZodiacCharacterRecognizer {
	static Options options;

	public static void main(String [] argv) {
		prepareOptions();

		CommandLineParser parser = new GnuParser();
		


		try {
			

			CommandLine line = parser.parse(options, argv);
			if (!line.hasOption("a")) {
				printUsage();
				return;
			}

			String algorithm = line.getOptionValue("a");
			if ("svm".equals(algorithm)) {
				runSVM(line);
			} else if ("knn".equals(algorithm)) {
				runKNN(line);
			} else if("ann".equals(algorithm)) {
				runANN(line);
			} else if("smo".equals(algorithm)) {
				runSMO(line);
			} else if("nb".equals(algorithm)) {
				runNB(line);
			}

		} catch (ParseException e) {
			System.err.println( "Parsing failed.  Reason: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void prepareOptions() {
		Option algorithm = new Option("a", "algorithm", true, "algorithm");
		Option trainFile = new Option("tr", "train-file", true, "train file");
		Option testFile = new Option("te", "test-file", true, "test file");
		Option output = new Option("o", "output", true, "output file");

		options = new Options();
		options.addOption(algorithm);
		options.addOption(trainFile);
		options.addOption(testFile);
		options.addOption(output);
	}

	public static void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("ZodiacCharacterRecognizer", options);
	}

	public static void runSVM(CommandLine line) throws Exception {
		if (!line.hasOption("train-file")) {
			printUsage();
			return;
		}

		SVM svm = new SVM(line.getOptionValue("train-file"));
//		String testFilePath = line.getOptionValue("test-file");
//		String outputFilePath = line.getOptionValue("output");
//		svm.train(0, 1, 3, 0.0001, -1, 10, -1, -1);
//		svm.predict(0, 1, testFilePath, outputFilePath);
		svm.parallelCrossValidationSVM(0, 1);
		
	}

	public static void runSMO(CommandLine line) throws Exception {
		if (!line.hasOption("train-file") ||
				!line.hasOption("test-file") ||
				!line.hasOption("output")) {

			printUsage();
			return;
		}
		SMO smo = new SMO();
		String testFilePath = line.getOptionValue("test-file");
		String outputFilePath = line.getOptionValue("output");
		smo.train(line.getOptionValue("train-file"));
		smo.predict(testFilePath, outputFilePath);

	}
	
	public static void runANN(CommandLine line) throws Exception {
		if (!line.hasOption("train-file") ||
				!line.hasOption("test-file") ||
				!line.hasOption("output")) {

			printUsage();
			return;
		}
		// argv[0] : -knn, argv[1] : trainFile , argv[2] : testFile , argv[3] outputFile
		ANN ann = new ANN();
		String testFilePath = line.getOptionValue("test-file");
		String outputFilePath = line.getOptionValue("output");
		ann.train(line.getOptionValue("train-file"));
		ann.predict(testFilePath, outputFilePath);

	}
	
	public static void runNB(CommandLine line) throws Exception {
		if (!line.hasOption("train-file") ||
				!line.hasOption("test-file") ||
				!line.hasOption("output")) {

			printUsage();
			return;
		}
		// argv[0] : -knn, argv[1] : trainFile , argv[2] : testFile , argv[3] outputFile
		NB nb = new NB();
		String testFilePath = line.getOptionValue("test-file");
		String outputFilePath = line.getOptionValue("output");
		nb.train(line.getOptionValue("train-file"));
		nb.predict(testFilePath, outputFilePath);

	}

	public static void runKNN(CommandLine line) throws Exception {
		if (!line.hasOption("train-file") ||
				!line.hasOption("test-file") ||
				!line.hasOption("output")) {

			printUsage();
			return;
		}
		// argv[0] : -knn, argv[1] : trainFile , argv[2] : testFile , argv[3] outputFile
		KNN knn = new KNN(line.getOptionValue("train-file"));
//		System.out.println(knn.getCVError(15, 5));
		String testFilePath = line.getOptionValue("test-file");
		String outputFilePath = line.getOptionValue("output");
		knn.predict(15, testFilePath, outputFilePath);
	}
}
