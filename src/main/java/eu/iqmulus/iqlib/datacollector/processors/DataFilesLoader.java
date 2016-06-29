package eu.iqmulus.iqlib.datacollector.processors;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import eu.iqmulus.iqlib.datacollector.http.RestClient;
import eu.iqmulus.iqlib.datacollector.jsonprocessing.JsonProcessor;
import eu.iqmulus.iqlib.datacollector.modelbuilder.DataCatalogModelBuilder;

public class DataFilesLoader {
	
	private RestClient restClient;
	
	public DataFilesLoader(RestClient restClient) {
		this.restClient = restClient;
	}
	
	public void saveToDatabaseByConfig(HashMap<String, Object> dataFilesMap, File configFile) {
		JsonProcessor jsonProcessor = new JsonProcessor();
		JsonNode configJsonNode = jsonProcessor.jsonFileToJsonNode(configFile);
		
		DataCatalogModelBuilder dataCatalogModelBuilder = new DataCatalogModelBuilder();
		List<HashMap<String, Object>> dataFilesListMap = dataCatalogModelBuilder.dataFilesBuilderFromMap(dataFilesMap);
		String dataFilesListAsJsonString = jsonProcessor.objectToJsonString(dataFilesListMap);
		System.out.println(dataFilesListAsJsonString);
		
		if (configJsonNode.get("surveyarea").get("id").asLong() < 1) {
			//SurveyArea save
			HashMap<String, Object> surveyAreaMap = dataCatalogModelBuilder.surveyAreaBuilderFromConfigJsonNode(configJsonNode);
			String surveyAreaAsJsonString = jsonProcessor.objectToJsonString(surveyAreaMap);
			String newSurveyAreaAsJsonString = restClient.saveSurveyAreaAndGetNewSurveyAreaAsJsonString(surveyAreaAsJsonString);
			
			//Dataset save
			JsonNode newSurveyAreaJsonNode = jsonProcessor.jsonStringToJsonNode(newSurveyAreaAsJsonString);
			HashMap<String, Object> datasetMap = dataCatalogModelBuilder.datasetBuilderFromConfigJsonNode(configJsonNode);
			String datasetAsJsonString = jsonProcessor.objectToJsonString(datasetMap);
			String newDatasetAsJsonString = restClient.saveDatasetAndGetNewDatasetAsJsonString(datasetAsJsonString, newSurveyAreaJsonNode.get("id").asLong());
			
			//DataFiles save
			JsonNode newDatasetJsonNode = jsonProcessor.jsonStringToJsonNode(newDatasetAsJsonString);
			String newDataFilesAsJsonString = restClient
					.saveDataFilesAndGetNewDataFilesAsJsonString(dataFilesListAsJsonString, newDatasetJsonNode.get("id").asLong());
			System.out.println(newDataFilesAsJsonString);
		} else {
			String surveyAreaAsJsonString = restClient.getSurveyAreaByIdAsJsonString(configJsonNode.get("surveyarea").get("id").asLong());
			if (surveyAreaAsJsonString.equals("")) {
				System.exit(0);
			}
			if (configJsonNode.get("dataset").get("id").asLong() < 1) {
				//New Dataset
				HashMap<String, Object> datasetMap = dataCatalogModelBuilder.datasetBuilderFromConfigJsonNode(configJsonNode);
				String datasetAsJsonString = jsonProcessor.objectToJsonString(datasetMap);
				String newDatasetAsJsonString = restClient.saveDatasetAndGetNewDatasetAsJsonString(datasetAsJsonString, 
						configJsonNode.get("surveyarea").get("id").asLong());
				//New DataFiles
				JsonNode newDatasetJsonNode = jsonProcessor.jsonStringToJsonNode(newDatasetAsJsonString);
				String newDataFilesAsJsonString = restClient
						.saveDataFilesAndGetNewDataFilesAsJsonString(dataFilesListAsJsonString, newDatasetJsonNode.get("id").asLong());
			} else {
				String datasetAsJsonString = restClient.getSurveyAreaByIdAsJsonString(configJsonNode.get("dataset").get("id").asLong());
				if (surveyAreaAsJsonString.equals("")) {
					System.exit(0);
				}
				String newDataFilesAsJsonString = restClient
						.saveDataFilesAndGetNewDataFilesAsJsonString(dataFilesListAsJsonString, configJsonNode.get("dataset").get("id").asLong());
			}
			
		}
	}
	
	private void saveDatasetByConfigJsonNode(JsonNode configJsonNode, Long surveyAreaId) {
		JsonProcessor jsonProcessor = new JsonProcessor();
		DataCatalogModelBuilder dataCatalogModelBuilder = new DataCatalogModelBuilder();
	}
	
}
