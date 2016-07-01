package eu.iqmulus.iqlib.datacollector.processors;

import java.io.File;
import java.util.HashMap;

import eu.iqmulus.iqlib.datacollector.reader.RasterFileHandler;

public class DataFilesReader {
	
	private HashMap<String, Object> dataFilesMap;
	
	private File inputFile;
	private File configurationFile;
	
	
	public DataFilesReader() {
		this.dataFilesMap = new HashMap<String, Object>();
	}
	
	public void collectDataFiles(String fromInputFilePath, String configurationFilePath, DataFileType dataFileType) {
		inputFile = new File(fromInputFilePath);
		configurationFile = new File(configurationFilePath);

        switch (dataFileType) {
            case RASTER:
            	System.out.println("RASTER");
    			collectRasterDataFiles(inputFile, configurationFile);
                break;

            case VECTOR:
				//empty
                break;

            case POINTCLOUD:
    			//empty
                break;
            default:
                break;
        }
	}

	private void collectRasterDataFiles(File fromInputFile, File configFile) {
		RasterFileHandler rasterFileHandler = new RasterFileHandler();
		rasterFileHandler.collect(fromInputFile, configFile);
		dataFilesMap.put("parentDirectory", fromInputFile.getPath());
		dataFilesMap.put("files", rasterFileHandler.getRasterFiles());
	}
	
	public HashMap<String, Object> getDataFilesMap() {
		return dataFilesMap;
	}
	
	public void setDataFilesMap(HashMap<String, Object> dataFilesMap) {
		this.dataFilesMap = dataFilesMap;
	}
	
}
