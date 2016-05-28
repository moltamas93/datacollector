package eu.iqmulus.iqlib.datacollector;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import eu.iqmulus.iqlib.datacollector.models.RasterFile;
import eu.iqmulus.iqlib.datacollector.reader.RasterFileHandler;

public class RasterFileHandlerTest {
	
	RasterFileHandler rasterFileHandler;
	private final int EXPECTEDVALIDRASTERNUMBER = 8;
	
	@Test
	public void testGetRasterFilesCount() {
	    ClassLoader classLoader = getClass().getClassLoader();
	    File files = new File(classLoader.getResource("rasters").getFile());
	    File config = new File(classLoader.getResource("fileReader.cfg").getFile());
	    ArrayList<RasterFile> rasterFiles = null;
    	rasterFileHandler = new RasterFileHandler();
    	rasterFileHandler.collect(files, config);
    	rasterFiles = rasterFileHandler.getRasterFiles();
    	assertEquals(EXPECTEDVALIDRASTERNUMBER, rasterFiles.size());
	}
	
	@Test
	public void testGetRasterAttributes() {
	    String expectedName = "03-112.TIF";
	    String expectedFormat = "TIF";
	    String expectedDriver = "GTiff";
	    int[] expectedDimension = { 3000, 2000 };
	    double[] expectedUpperLeftCoordinates = { 534000.0, 63999.0 };
	    double[] expectedLowerLeftCoordinates = { 534000.0, 59999.0 };
	    double[] expectedUpperRightCoordinates = { 540000.0, 63999.0 };
	    double[] expectedLowerRightCoordinates = { 540000.0, 59999.0 };
	    ClassLoader classLoader = getClass().getClassLoader();
	    File files = new File(classLoader.getResource("rasters/03-112.TIF").getFile());
	    File config = new File(classLoader.getResource("fileReader.cfg").getFile());
    	rasterFileHandler = new RasterFileHandler();
    	rasterFileHandler.collect(files, config);
	    RasterFile rasterFile = rasterFileHandler.getRasterFiles().get(0);
	    HashMap<String, double[]> cornerCoords = rasterFile.getCornerCoordinates();
	    assertEquals(1, rasterFileHandler.getRasterFiles().size());
	    assertEquals(expectedName, rasterFile.getName());
	    assertEquals(expectedFormat, rasterFile.getFormat());
	    assertEquals(expectedDriver, expectedDriver);
	    assertArrayEquals(expectedDimension, rasterFile.getDimension());
	    assertArrayEquals(expectedUpperLeftCoordinates, cornerCoords.get("ul"), 0.0);
	    assertArrayEquals(expectedLowerLeftCoordinates, cornerCoords.get("ll"), 0.0);
	    assertArrayEquals(expectedUpperRightCoordinates, cornerCoords.get("ur"), 0.0);
	    assertArrayEquals(expectedLowerRightCoordinates, cornerCoords.get("lr"), 0.0);
	}
	
}
