package eu.iqmulus.iqlib.datacollector;

import java.io.File;

import eu.iqmulus.iqlib.datacollector.http.RestClient;
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
		
		//Command Line Validate
		CliValidator cliValidator = new CliValidator(cli.getCmd());
		cliValidator.validate();
		
		//Reader
		DataFilesReader dataFilesReader = new DataFilesReader();
		dataFilesReader.collectDataFiles(cliValidator.getInputFile(), cliValidator.getConfigFile(), cliValidator.getDataFileType());
		
		//Writer
		DataFilesWriter dataFilesWriter = new DataFilesWriter(dataFilesReader.getDataFilesMap());
		dataFilesWriter.writeDataFilesToFile(new File(cli.getCmd().getOptionValue("o")));
		
		//Loader
		RestClient restClient = new RestClient();
		restClient.initHttpClientWithConfigFile(cliValidator.getConfigFile());
		
		String json = restClient.getSurveyAreaByIdAsJsonString(new Long(53));
		System.out.println(json.equals(""));
		
		DataFilesLoader dataFilesLoader = new DataFilesLoader(restClient);	
		dataFilesLoader.saveToDatabaseByConfig(dataFilesReader.getDataFilesMap(), cliValidator.getConfigFile());

		System.out.println("The program has been successful!");
	}

}
