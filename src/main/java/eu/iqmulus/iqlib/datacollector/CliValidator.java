package eu.iqmulus.iqlib.datacollector;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;

public class CliValidator {

	private final static Logger LOG = Logger.getLogger(CliValidator.class);
	private File configFile;
	private File inputFile;
	private File outputFile;
	
	public CliValidator(CommandLine cli) {
		this.configFile = new File(cli.getOptionValue("c"));
		this.inputFile = new File(cli.getOptionValue("f"));
		this.outputFile = new File(cli.getOptionValue("o"));
	}

	public void validate() {
		checkFileExists(configFile);
		checkFileExists(inputFile);
		checkFileExists(outputFile.getParentFile());
	}

	private void checkFileExists(File file) {
		if (!file.exists()) {
			LOG.error("The system cannot find the file: " + file.getAbsolutePath());
			System.exit(0);
		} 
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
}
