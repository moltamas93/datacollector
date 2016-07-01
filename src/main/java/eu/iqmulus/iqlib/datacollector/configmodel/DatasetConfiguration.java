package eu.iqmulus.iqlib.datacollector.configmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatasetConfiguration {
	
	private Long id;
	private String name;
	@JsonProperty("is_non_overlapping")
	private boolean isNonOverLapping;
	private String source;
	private String purpose;
	private String owner;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isNonOverLapping() {
		return isNonOverLapping;
	}
	
	public void setNonOverLapping(boolean isNonOverLapping) {
		this.isNonOverLapping = isNonOverLapping;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getPurpose() {
		return purpose;
	}
	
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getOwner() {
		return owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "DatasetConfiguration [id=" + id + ", name=" + name + ", isNonOverLapping=" + isNonOverLapping
				+ ", source=" + source + ", purpose=" + purpose + ", owner=" + owner + "]";
	}
	
}
