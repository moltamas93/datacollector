package eu.iqmulus.iqlib.datacollector;

import java.io.File;

import eu.iqmulus.iqlib.datacollector.processors.DataFilesReader;
import eu.iqmulus.iqlib.datacollector.processors.DataFilesWriter;

public class App {

	public static void main(String[] args) {
		String[] testArgs = {"-t", "raster", "-c", "d:\\IQlib\\config.cfg", "-f", "d:\\IQlib\\data\\rasters\\", 
				"-o", "d://file.json"};
		//Command Line Parser
		Cli cli = new Cli(testArgs);
		cli.parse();
		
		//Command Line Validate
		CliValidator cliValidator = new CliValidator(cli.getCmd());
		cliValidator.validate();
		
		//Reader
		DataFilesReader dataFilesReader = new DataFilesReader();
		dataFilesReader.collectDataFiles(cliValidator.getInputFile(), cliValidator.getConfigFile(), cliValidator.getDataFileType());
		
		//Writer
		DataFilesWriter dataFilesWriter = new DataFilesWriter(dataFilesReader.getDataFilesMap());
		dataFilesWriter.writeDataFilesToFile(new File(cli.getCmd().getOptionValue("o")));
		
		System.out.println("The program has been successful!");
	}

}
