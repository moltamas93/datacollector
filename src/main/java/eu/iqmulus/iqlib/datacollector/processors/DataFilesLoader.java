package eu.iqmulus.iqlib.datacollector.processors;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.iqmulus.iqlib.datacollector.configmodel.DatasetConfiguration;
import eu.iqmulus.iqlib.datacollector.configmodel.SurveyAreaConfiguration;
import eu.iqmulus.iqlib.datacollector.http.RestClient;
import eu.iqmulus.iqlib.datacollector.jsonprocessing.JsonProcessor;
import eu.iqmulus.iqlib.datacollector.modelbuilder.DataCatalogModelBuilder;

public class DataFilesLoader {

	private final static Logger LOG = Logger.getLogger(DataFilesLoader.class);
	private RestClient restClient;
	private SurveyAreaConfiguration surveyAreaConfiguration;
	private DatasetConfiguration datasetConfiguration;
	
	public DataFilesLoader(RestClient restClient) {
		this.restClient = restClient;
	}
	
	private void initConfigurationModelsByConfigJsonNode(JsonNode configJsonNode) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.surveyAreaConfiguration = mapper.convertValue(configJsonNode.get("surveyarea"), SurveyAreaConfiguration.class);
			this.datasetConfiguration = mapper.convertValue(configJsonNode.get("dataset"), DatasetConfiguration.class);
		} catch(IllegalArgumentException ex) {
			LOG.error(ex, ex);
			System.exit(0);
		}
	}
	
	public void saveToDatabaseByConfig(HashMap<String, Object> dataFilesMap, File configFile) {
		JsonProcessor jsonProcessor = new JsonProcessor();
		JsonNode configJsonNode = jsonProcessor.jsonFileToJsonNode(configFile);
		
		initConfigurationModelsByConfigJsonNode(configJsonNode);
		
		DataCatalogModelBuilder dataCatalogModelBuilder = new DataCatalogModelBuilder();
		List<HashMap<String, Object>> dataFilesListMap = dataCatalogModelBuilder.dataFilesBuilderFromMap(dataFilesMap);
		
		String dataFilesListAsJsonString = jsonProcessor.objectToJsonString(dataFilesListMap);
		String surveyAreaAsJsonString = jsonProcessor.objectToJsonString(surveyAreaConfiguration);
		String datasetAsJsonString = jsonProcessor.objectToJsonString(datasetConfiguration);

		Long datasetId = null;
		
		if (surveyAreaConfiguration.getId() < 1) {
			//Post and Save New SurveyArea
			String newSurveyAreaAsJsonString = restClient
					.postAndGetResponseAsString("/datamodel/surveyarea", surveyAreaAsJsonString);
			
			//Post and Save New Dataset
			Long newSurveyAreaId = jsonProcessor.jsonStringToJsonNode(newSurveyAreaAsJsonString).get("id").asLong();
			System.out.println(newSurveyAreaId);
			String newDatasetAsJsonString = restClient
					.postAndGetResponseAsString("/datamodel/dataset/" + newSurveyAreaId, datasetAsJsonString);
			
			
			//New DatasetId
			datasetId = jsonProcessor.jsonStringToJsonNode(newDatasetAsJsonString).get("id").asLong();
		} else {
			//Get exists SurveyArea
			String getSurveyAreaAsJsonString = restClient.get("/datamodel/surveyarea/" + surveyAreaConfiguration.getId());
			if (getSurveyAreaAsJsonString.equals("")) {
				System.exit(0);
			}
			
			if (datasetConfiguration.getId() < 1) {
				//Post and Save New Dataset
				String newDatasetAsJsonString = restClient
						.postAndGetResponseAsString("/datamodel/dataset/" + surveyAreaConfiguration.getId(), datasetAsJsonString);
				//New DatasetId
				datasetId = jsonProcessor.jsonStringToJsonNode(newDatasetAsJsonString).get("id").asLong();
				
			} else {
				//Get exists SurveyArea
				String getDatasetAsJsonString = restClient.get("/datamodel/dataset/" + datasetConfiguration.getId());
				if (getDatasetAsJsonString.equals("")) {
					System.exit(0);
				}
				datasetId = datasetConfiguration.getId();
			}
		}
		
		//Post and Save Datafiles
		restClient.postAndGetResponseAsString("/datamodel/datafiles/" + datasetId, dataFilesListAsJsonString);
	}
	
}
