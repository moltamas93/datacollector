package eu.iqmulus.iqlib.datacollector;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import eu.iqmulus.iqlib.datacollector.configurations.ConfigurationReader;
import eu.iqmulus.iqlib.datacollector.configurations.RasterParameters;

public class ConfigurationReaderTest {

	ConfigurationReader configurationReader;

	@Test
	public void testGetRasterParametersByConfig() throws ParseException{
		configurationReader = new ConfigurationReader();
		ClassLoader classLoader = getClass().getClassLoader();
		File config = new File(classLoader.getResource("fileReader.cfg").getFile());
		RasterParameters rasterParameters = configurationReader.getRasterParametersByConfig(config);
		String[] expectedExtensions = { "tif", "TIF", "tiff", "TIFF", "DMG", "dmg", "jpg" };
		Assert.assertArrayEquals(expectedExtensions, rasterParameters.getExtensions());
		String[] expectedKeywords = { "SENTINEL", "SPOT5" };
		Assert.assertArrayEquals(expectedKeywords, rasterParameters.getKeywords());
		Date expectedSamplingDate = new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-01");
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(expectedSamplingDate), new SimpleDateFormat("yyyy-MM-dd").format(rasterParameters.getSamplingDate()));
		assertEquals("BLA BLA", rasterParameters.getSensorType());
	}

}
