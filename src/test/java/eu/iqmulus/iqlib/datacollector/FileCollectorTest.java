package eu.iqmulus.iqlib.datacollector;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import eu.iqmulus.iqlib.datacollector.reader.FileCollector;

public class FileCollectorTest {
	
	private FileCollector fileCollector;
	
	@Test
	public void test() {
		int fileCount = 9;
		String[] extensions = { "tif", "TIF", "tiff", "TIFF", "DMG", "dmg", "jpg" };
	    ClassLoader classLoader = getClass().getClassLoader();
	    File files = new File(classLoader.getResource("files/rasters").getFile());
	    fileCollector = new FileCollector(files, extensions);
	    assertEquals(fileCount, fileCollector.getFiles().size());
	}

}
