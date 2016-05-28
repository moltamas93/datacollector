package eu.iqmulus.iqlib.datacollector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.iqmulus.iqlib.datacollector.reader.RasterFileHandler;

public class App {

	final static Logger logger = Logger.getLogger(App.class);
	
	public static void main(String[] args) {
		File filesPath = new File("d:\\IQlib\\data\\rasters\\");
		String confPath = "d:\\IQlib\\fileReader2.cfg";
		File conf = new File(confPath);
		
		RasterFileHandler rasterFileHandler = new RasterFileHandler();
		rasterFileHandler.collect(filesPath, conf);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentDirectory", filesPath.getPath());
		map.put("files", rasterFileHandler.getRasterFiles());
		try {
			mapper.writeValue(new File("d:\\file.json"), map);
		} catch(IOException ex) {
			logger.debug(ex);
		}
		System.out.println(rasterFileHandler.getRasterFiles().size());
	}

}
