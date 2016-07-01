package eu.iqmulus.iqlib.datacollector.jsonprocessing;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {

	private final static Logger LOG = Logger.getLogger(JsonProcessor.class);
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
	
	public <T> String objectToJsonString(T object) {
		String json = null;
		try {
			json = mapper.writeValueAsString(object);	
		} catch (JsonProcessingException ex) {
			LOG.error(ex);
		}
		return json;
	}
	
	public JsonNode jsonStringToJsonNode(String fromJson) {
		JsonNode json = null;
		try {
			json = mapper.readTree(fromJson);
		} catch (JsonProcessingException ex) {
			LOG.error("JSON I/O problems: " + fromJson, ex);
		} catch (IOException ex) {
			LOG.error("JSON I/O problems: " + fromJson, ex);
		}
		return json;
	}
	
}
