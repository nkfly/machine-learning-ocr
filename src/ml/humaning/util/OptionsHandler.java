package ml.humaning.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public interface OptionsHandler {
	void registerOptions(Options options);
	boolean parseOptions(CommandLine line);
}
