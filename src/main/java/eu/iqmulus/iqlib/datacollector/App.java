package eu.iqmulus.iqlib.datacollector;

public class App {

	public static void main(String[] args) {
		String[] testArgs = {"-t", "raster", "-c", "d:\\IQlib\\fileReader2.cfg", "-f", "d:\\IQlib\\data\\rasters\\", 
				"-o", "d://file.json"};
		Cli cli = new Cli(testArgs);
		cli.parse();
		
		CliValidator cliValidator = new CliValidator(cli.getCmd());
		cliValidator.validate();
		
		IOFileHandler handler = new IOFileHandler();
		handler.parseFiles(cli.getCmd());
		System.out.println("The program has been successful!");
	}

}
