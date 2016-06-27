package eu.iqmulus.iqlib.datacollector.configurations;

import java.io.File;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.iqmulus.iqlib.datacollector.jsonprocessing.JsonProcessor;

/**
 * Read the parameters from json file to object by methods. 
 * 
 */

public class ConfigurationReader {

	final static Logger logger = Logger.getLogger(ConfigurationReader.class);
	
	/**
	 * 
	 * @param configFile This is the configuration file of the rasters.
	 * @return RasterParameters This returns with RasterParameters object.
	 */
	public RasterParameters getRasterParametersByConfig(File configFile) {
		JsonProcessor jsonProcessor = new JsonProcessor();
		RasterParameters rasterParameters = new RasterParameters();
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode configJsonNode = jsonProcessor.jsonFileToJsonNode(configFile);
		rasterParameters = mapper.convertValue(configJsonNode.get("datafiles_parameters"), RasterParameters.class);
		return rasterParameters;
		
	}
	
}