package eu.iqmulus.iqlib.datacollector.modelbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import eu.iqmulus.iqlib.datacollector.jsonprocessing.JsonProcessor;
import eu.iqmulus.iqlib.datacollector.models.RasterFile;

public class DataCatalogModelBuilder {
	
	public DataCatalogModelBuilder() {
		
	}
	
	public List<HashMap<String, Object>> dataFilesBuilderFromMap(HashMap<String, Object> map) {
		JsonProcessor jsonProcessor = new JsonProcessor();
		List<HashMap<String, Object>> dataFilesMapList = new ArrayList<HashMap<String, Object>>();
		map.get("files");
		
		List<Object> dataFilesList = castObjectToList(map.get("files"));
		Object dataFiles = dataFilesList.get(0);
		
		if (dataFiles.getClass().equals(RasterFile.class)) {
			for (RasterFile rasterFile : (List<RasterFile>)map.get("files")) {
				HashMap<String, Object> dataFileMap = new HashMap<String, Object>();
				dataFileMap.put("path", map.get("parentDirectory") + rasterFile.getUri());
				HashMap<String, Object> metadata = new HashMap<String, Object>();
				metadata.put("driver", rasterFile.getDriver());
				metadata.put("dimension", rasterFile.getDimension());
				metadata.put("projection", rasterFile.getProjection());
				metadata.put("cornerCoordinates", rasterFile.getCornerCoordinates());
				metadata.put("keywords", rasterFile.getKeywords());
				metadata.put("sampling_date", rasterFile.getSamplingDate());
				metadata.put("sensor_type", rasterFile.getSensorType());
				String metadataAsJsonString = jsonProcessor.objectToJsonString(metadata);
				dataFileMap.put("metadata", metadataAsJsonString);
				dataFilesMapList.add(dataFileMap);
			}
		} else {
			//.....
		}
		return dataFilesMapList;
	}
	
	public static <T extends List<?>> T castObjectToList(Object obj) {
	    return (T) obj;
	}
}
