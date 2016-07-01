package eu.iqmulus.iqlib.datacollector.processors;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataFilesWriter {

	private final static Logger LOG = Logger.getLogger(DataFilesWriter.class);
	private Map<String, Object> dataFilesMap;
	
	public DataFilesWriter(Map<String, Object> dataFilesMap) {
		this.dataFilesMap = dataFilesMap;
	}
	
	public void writeDataFilesToFile(File outputJsonFile) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(outputJsonFile, dataFilesMap);
		} catch(IOException ex) {
			LOG.error(ex);
		}
	}

	public Map<String, Object> getDataFilesMap() {
		return dataFilesMap;
	}

	public void setDataFilesMap(Map<String, Object> dataFilesMap) {
		this.dataFilesMap = dataFilesMap;
	}
	
}
