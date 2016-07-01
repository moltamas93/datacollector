package eu.iqmulus.iqlib.datacollector;

import java.io.File;

import org.apache.commons.cli.CommandLine;

import eu.iqmulus.iqlib.datacollector.http.RestClient;
import eu.iqmulus.iqlib.datacollector.processors.DataFileType;
import eu.iqmulus.iqlib.datacollector.processors.DataFilesLoader;
import eu.iqmulus.iqlib.datacollector.processors.DataFilesReader;
import eu.iqmulus.iqlib.datacollector.processors.DataFilesWriter;

public class App {

	public static void main(String[] args) {
		String[] testArgs = {"-t", "raster", "-c", "d:\\IQlib\\config.cfg", "-f", "d:\\IQlib\\data\\rasters\\", 
				"-o", "d://file.json"};
		//Command Line Parser
		Cli cli = new Cli(testArgs);
		cli.parse();
		CommandLine cmd = cli.getCmd();
		
		//Command Line Files Validate
		CliValidator cliValidator = new CliValidator(cli.getCmd());
		cliValidator.validate();
		
		//Reader
		DataFilesReader dataFilesReader = new DataFilesReader();
		dataFilesReader.collectDataFiles(cmd.getOptionValue("f"), cmd.getOptionValue("c"), 
				DataFileType.valueOf(cmd.getOptionValue("t").toUpperCase()));
	
		//Writer
		DataFilesWriter dataFilesWriter = new DataFilesWriter(dataFilesReader.getDataFilesMap());
		dataFilesWriter.writeDataFilesToFile(new File(cmd.getOptionValue("o")));
		
		//HttpClient
		RestClient restClient = new RestClient();
		restClient.initHttpClientWithConfigFile(cliValidator.getConfigFile());
		
		//Loader
		DataFilesLoader dataFilesLoader = new DataFilesLoader(restClient);	
		dataFilesLoader.saveToDatabaseByConfig(dataFilesReader.getDataFilesMap(), cliValidator.getConfigFile());

		System.out.println("The program has been successful!");
	}

}
