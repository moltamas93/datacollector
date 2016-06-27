package eu.iqmulus.iqlib.datacollector;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;

public class CliValidator {

	final static Logger LOG = Logger.getLogger(CliValidator.class);
	File configFile;
	File inputFile;
	String dataFileType;
	
	public CliValidator(CommandLine cli) {
		this.configFile = new File(cli.getOptionValue("c"));
		this.inputFile = new File(cli.getOptionValue("f"));
		this.dataFileType = cli.getOptionValue("t");
	}

	public void validate() {
		checkFileExists(configFile);
		checkFileExists(inputFile);
		checkDataFileType(dataFileType);
	}
	
	private void checkDataFileType(String type) {
		String[] validTypes = new String[] {"raster", "pointcloud", "vector"};
		if ( !(Arrays.asList(validTypes).contains(type)) ) {
			LOG.debug("The follow type is invalid: " + type);
			System.out.println("Valid types: " + "raster" + " pointcloud " + "vector" );
			System.exit(0);	
		}
	}

	private void checkFileExists(File file) {
		if (!file.exists()) {
			LOG.debug("The system cannot find the file: " + file.getAbsolutePath());
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

	public String getDataFileType() {
		return dataFileType;
	}

	public void setDataFileType(String dataFileType) {
		this.dataFileType = dataFileType;
	}
	
}
