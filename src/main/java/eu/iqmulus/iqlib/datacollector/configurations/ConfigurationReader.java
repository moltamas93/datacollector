package eu.iqmulus.iqlib.datacollector.configurations;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Read the parameters from json file to object by methods. 
 * 
 */

public class ConfigurationReader {

	final static Logger logger = Logger.getLogger(ConfigurationReader.class);
	
	/**
	 * 
	 * @param file This is the configuration file of the rasters.
	 * @return RasterParameters This returns with RasterParameters object.
	 */
	public RasterParameters getRasterParametersByConfig(File file) {
		RasterParameters rasterParameters = new RasterParameters();
		ObjectMapper mapper = new ObjectMapper();
		try {
			rasterParameters = mapper.readValue(file, RasterParameters.class);
			return rasterParameters;
		} catch (JsonGenerationException ex) {
			logger.debug(ex);
		} catch (JsonMappingException ex) {
			logger.debug(ex);
		} catch (IOException ex) {
			logger.debug(ex);
		} finally {
			return rasterParameters;	
		}
	}
	
}