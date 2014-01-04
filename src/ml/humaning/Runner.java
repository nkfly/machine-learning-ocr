package ml.humaning;

import org.apache.commons.cli.Options;

public interface Runner {

	public void registerOptions();
	public boolean parseArgv(String [] argv, Options options) throws Exception;

	public void run() throws Exception;
}
