package eu.iqmulus.iqlib.datacollector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.iqmulus.iqlib.datacollector.reader.RasterFileHandler;

public class IOFileHandler {

	final static Logger LOG = Logger.getLogger(App.class);
	File configFile;
	File filesPath;
	File outputPath;
	ObjectMapper mapper = new ObjectMapper();
	Map<String, Object> map;
	
	public void parseFiles(CommandLine cmd) {
		try {
			configFile = new File(cmd.getOptionValue("c"));
			tryFileFound(configFile);
			filesPath = new File(cmd.getOptionValue("f"));
			tryFileFound(filesPath);
			outputPath = new File(cmd.getOptionValue("o"));
			runFileHandlerByType(cmd.getOptionValue("t"));
		} catch(FileNotFoundException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
	}
	
	private void runFileHandlerByType(String gisFileType) {
		map = new HashMap<String, Object>();
		if (gisFileType.equals("raster")) {
			runRasterFileHandler();
		}
	}
	
	private void runRasterFileHandler() {
		RasterFileHandler rasterFileHandler = new RasterFileHandler();
		rasterFileHandler.collect(filesPath, configFile);
		map.put("parentDirectory", filesPath.getPath());
		map.put("files", rasterFileHandler.getRasterFiles());
		createOutputJsonFileWithGisFileProperties();
	}
	
	private void runPointcloudFileHandler() {
		//now emtpy
	}
	
	private void runVectorFileHandler(){
		//now empty
	}
	
	private void createOutputJsonFileWithGisFileProperties() {
		try {
			mapper.writeValue(outputPath, map);
		} catch(IOException ex) {
			LOG.debug(ex);
		}
	}
	
	private void tryFileFound(File file) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException("The system cannot find the file: " + file.getAbsolutePath());
		} 
	}
	
}
