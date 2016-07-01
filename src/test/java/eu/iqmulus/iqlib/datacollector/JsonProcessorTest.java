package eu.iqmulus.iqlib.datacollector;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import eu.iqmulus.iqlib.datacollector.jsonprocessing.JsonProcessor;

import static org.junit.Assert.*;

public class JsonProcessorTest {
	
	private JsonProcessor jsonProcessor;
	private ClassLoader classLoader;
	private static final String VALID_JSON_STRING = "{\"id\":1 ,\"name\":\"cat\"}";
	private static final String IN_VALID_JSON_STRING = "{ id : 1 ,\"name\":\"cat\"}";
	
	@Before
	public void setup() {
		jsonProcessor = new JsonProcessor();
		classLoader = getClass().getClassLoader();
	}
	
	@Test
	public void should_return_valid_json_node_by_host_from_json_file() {
	    File file = new File(classLoader.getResource("jsonprocessor/valid_json_config.json").getFile());
	    JsonNode jsonNodeFromFile = jsonProcessor.jsonFileToJsonNode(file);
	    String host = jsonNodeFromFile.get("host").asText();
		assertEquals("localhost", host);
	}
	
	@Test(expected = NullPointerException.class)  
	public void file_to_json_node_should_throw_null_pointer_exception() {
	    File file = new File(classLoader.getResource("jsonprocessor/invalid_json_config.json").getFile());
	    JsonNode jsonNodeFromFile = jsonProcessor.jsonFileToJsonNode(file);
	    String host = jsonNodeFromFile.get("host").asText();
	}
	
	@Test
	public void should_return_json_node_from_json_string() {
		JsonNode jsonNode = jsonProcessor.jsonStringToJsonNode(VALID_JSON_STRING);
		assertEquals(1, jsonNode.get("id").asInt());
		assertEquals("cat", jsonNode.get("name").asText());
	}

	@Test(expected = NullPointerException.class)  
	public void string_to_json_node_should_throw_null_pointer_exception() {
		JsonNode jsonNode = jsonProcessor.jsonStringToJsonNode(IN_VALID_JSON_STRING);
		int id = jsonNode.get("id").asInt();
	}
	
	
	
}
