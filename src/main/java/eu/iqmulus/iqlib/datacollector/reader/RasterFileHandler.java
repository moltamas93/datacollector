package eu.iqmulus.iqlib.datacollector.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

import eu.iqmulus.iqlib.datacollector.configurations.ConfigurationReader;
import eu.iqmulus.iqlib.datacollector.configurations.RasterParameters;
import eu.iqmulus.iqlib.datacollector.models.InputHandler;
import eu.iqmulus.iqlib.datacollector.models.RasterFile;

/**
 * Reads all raster files or one raster file with your gdal parameters.
 *
 */

public class RasterFileHandler implements InputHandler {
	
	final static Logger LOG = Logger.getLogger(RasterFileHandler.class);
	
	ArrayList<RasterFile> rasterFiles;
	RasterParameters rasterParameters;
	
	public void collect(File file, File withConfig) {
		rasterFiles = new ArrayList<RasterFile>();
		rasterParameters = new ConfigurationReader().getRasterParametersByConfig(withConfig);
		ArrayList<File> files = new FileCollector(file, rasterParameters.getExtensions()).getFiles();
		for (File f : files) {
			RasterFile rasterFile = new RasterFile();
			rasterFile.setName(f.getName());
			rasterFile.setUri(f.getPath().replace(file.getPath(), ""));
			rasterFile.setFormat(rasterFile.getName().substring(rasterFile.getName().lastIndexOf('.')+1));
			try{
				// Parsing GDAL specific parameters
				parseGdalParameters(f, rasterFile);
			} catch(NullPointerException ex) {
				LOG.debug("Invalid raster file: " + f.getPath(), ex);
				// Error.json file kibovitese ujabb hibas rekordal
			}
		}
	}
	
	/**
	 * Reading properties of the raster file into RasterFile object.
	 * 
	 * @param file This is the raster file.
	 * @param rasterFile This is the RasterFile object.
	 * @throws NullPointerException This method throw NullPointerException if the raster file is not readable.
	 */
	private void parseGdalParameters(File fromFile, RasterFile usingRasterFile) throws NullPointerException {
		Dataset hDataset = null;
		Driver hDriver;
		gdal.AllRegister();
		hDataset = gdal.Open(fromFile.getPath(), gdalconstConstants.GA_ReadOnly);
		if (hDataset == null) {
			throw new NullPointerException();
		}
		hDriver = hDataset.GetDriver();
		usingRasterFile.setDriver(hDriver.getShortName());
		usingRasterFile.setDimension(new int[]{ hDataset.getRasterXSize(), hDataset.getRasterYSize() });
		usingRasterFile.setProjection(hDataset.GetProjectionRef().toString());
		HashMap<String, double[]> cornerCoords = new HashMap<String, double[]>();
		cornerCoords.put("ul", parseGdalCorner(hDataset, 0.0, 0.0));
		cornerCoords.put("ll", parseGdalCorner(hDataset, 0.0, hDataset.getRasterYSize()));
		cornerCoords.put("ur", parseGdalCorner(hDataset, hDataset.getRasterXSize(), 0.0));
		cornerCoords.put("lr", parseGdalCorner(hDataset, hDataset.getRasterXSize(), hDataset.getRasterYSize()));
		usingRasterFile.setCornerCoordinates(cornerCoords);
		usingRasterFile.setKeywords(rasterParameters.getKeywords());
		usingRasterFile.setSamplingDate(rasterParameters.getSamplingDate());
		usingRasterFile.setSensorType(rasterParameters.getSensorType());
		rasterFiles.add(usingRasterFile);
		hDataset.delete();
	}
	
	/**
	 * 
	 * @param hDataset
	 * @param x
	 * @param y
	 * @return
	 */
	private double[] parseGdalCorner(Dataset hDataset, double x, double y) {
		double dfGeoX, dfGeoY;
		double[] adfGeoTransform = new double[6];
		hDataset.GetGeoTransform(adfGeoTransform);
		dfGeoX = adfGeoTransform[0] + adfGeoTransform[1] * x
				+ adfGeoTransform[2] * y;
		dfGeoY = adfGeoTransform[3] + adfGeoTransform[4] * x
				+ adfGeoTransform[5] * y;
		return new double[] { dfGeoX, dfGeoY };
	}
	
	public ArrayList<RasterFile> getRasterFiles() {
		return rasterFiles;
	}

	public void setRasterFiles(ArrayList<RasterFile> rasterFiles) {
		this.rasterFiles = rasterFiles;
	}

	public RasterParameters getRasterParameters() {
		return rasterParameters;
	}

	public void setRasterParameters(RasterParameters rasterParameters) {
		this.rasterParameters = rasterParameters;
	}
	
}
