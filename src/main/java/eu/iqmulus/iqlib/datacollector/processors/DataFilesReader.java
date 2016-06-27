package eu.iqmulus.iqlib.datacollector.processors;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.iqmulus.iqlib.datacollector.reader.RasterFileHandler;

public class DataFilesReader {
	
	final static Logger LOG = Logger.getLogger(DataFilesReader.class);
	Map<String, Object> dataFilesMap;
	
	public DataFilesReader() {
		this.dataFilesMap = new HashMap<String, Object>();
	}
	
	public void collectDataFiles(File fromInputFile, File configFile, String dataFileType) {
		if (dataFileType.equals("raster")) {
			collectRasterDataFiles(fromInputFile, configFile);
		} else if (dataFileType.equals("pointcloud")) {
			//empty
		} else if (dataFileType.equals("vector")) {
			//empty
		}
	}

	private void collectRasterDataFiles(File fromInputFile, File configFile) {
		RasterFileHandler rasterFileHandler = new RasterFileHandler();
		rasterFileHandler.collect(fromInputFile, configFile);
		dataFilesMap.put("parentDirectory", fromInputFile.getPath());
		dataFilesMap.put("files", rasterFileHandler.getRasterFiles());
	}
	
	public Map<String, Object> getDataFilesMap() {
		return dataFilesMap;
	}
	
	public void setDataFilesMap(Map<String, Object> dataFilesMap) {
		this.dataFilesMap = dataFilesMap;
	}
	
}
