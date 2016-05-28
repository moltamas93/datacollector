package eu.iqmulus.iqlib.datacollector.models;

import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RasterFile class represents raster file various attributes.
 *
 */
public class RasterFile {
	
	private String name;
	private String uri;
	private String format;
	
	private String driver;
	private int[] dimension;
	private String projection;
	@JsonProperty("corner_coordinates")
	private HashMap<String, double[]> cornerCoordinates;
	
	private String[] keywords;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date samplingDate;
	private String sensorType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public int[] getDimension() {
		return dimension;
	}
	public void setDimension(int[] dimension) {
		this.dimension = dimension;
	}
	public String getProjection() {
		return projection;
	}
	public void setProjection(String projection) {
		this.projection = projection;
	}
	public String[] getKeywords() {
		return keywords;
	}
	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
	public Date getSamplingDate() {
		return samplingDate;
	}
	public void setSamplingDate(Date samplingDate) {
		this.samplingDate = samplingDate;
	}
	public String getSensorType() {
		return sensorType;
	}
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}
	public HashMap<String, double[]> getCornerCoordinates() {
		return cornerCoordinates;
	}
	public void setCornerCoordinates(HashMap<String, double[]> cornerCoordinates) {
		this.cornerCoordinates = cornerCoordinates;
	}
	
}
