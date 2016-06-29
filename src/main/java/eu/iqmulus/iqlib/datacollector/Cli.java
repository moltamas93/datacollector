package eu.iqmulus.iqlib.datacollector;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Cli {
	
	private String[] args = null;
	private Options options = new Options();
	private CommandLine cmd;
	
	public Cli(String[] args) {
		this.args = args;
		Option typeOption = Option.builder("t")
                .longOpt("type")
                .required(true)
                .hasArg(true)
                .desc("GIS file type.")
                .build();
		Option configOption = Option.builder("c")
                .longOpt("config")
                .required(true)
                .hasArg(true)
                .desc("Config file path.")
                .build();
		Option fileOption = Option.builder("f")
                .longOpt("file")
                .required(true)
                .hasArg(true)
                .desc("Directory or file path.")
                .build();
		Option outputOption = Option.builder("o")
                .longOpt("output")
                .required(true)
                .hasArg(true)
                .desc("Output file of files data.")
                .build();
		options.addOption("h", "help", false, "show help.");
		options.addOption(configOption);
		options.addOption(fileOption);
		options.addOption(outputOption);
		options.addOption(typeOption);
	}
	
	public void parse() {
		CommandLineParser parser = new DefaultParser();
		try {
			this.cmd = parser.parse(options, args);
		} catch (ParseException e) {
			help();
		}
	}

	private void help() {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("App", options);
		System.exit(0);
	}
	
	public CommandLine getCmd() {
		return this.cmd;
	}

}
