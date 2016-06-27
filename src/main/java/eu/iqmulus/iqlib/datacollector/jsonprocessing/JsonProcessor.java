package eu.iqmulus.iqlib.datacollector.jsonprocessing;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {

	final static Logger LOG = Logger.getLogger(JsonProcessor.class);
	ObjectMapper mapper;
	
	public JsonProcessor() {
		mapper = new ObjectMapper();
	}

	public JsonNode jsonFileToJsonNode(File file) {
		JsonNode jsonNode = null;
		try{
			jsonNode = mapper.readValue(file, JsonNode.class);
		} catch (JsonParseException ex) {
			LOG.error(ex);
		} catch (JsonMappingException ex) {
			LOG.error(ex);
		} catch (IOException ex) {
			LOG.error(ex);
		}
		return jsonNode;
	}
	
}
