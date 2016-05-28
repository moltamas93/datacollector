package eu.iqmulus.iqlib.datacollector.configurations;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * RasterParameters represents parameters of raster file
 *
 */
public class RasterParameters {
	
	private String[] extensions;
	
	private String[] keywords;
	
	@JsonProperty("sampling_date")
	private Date samplingDate;
	
	@JsonProperty("sensor_type")
	private String sensorType;
	/**
	 * Default constructor.
	 */
	public RasterParameters() {
		this.keywords = new String[] { "Exception!" }; 
	}
	
	public String[] getExtensions() {
		return extensions;
	}

	public void setExtensions(String[] extensions) {
		this.extensions = extensions;
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
	
}