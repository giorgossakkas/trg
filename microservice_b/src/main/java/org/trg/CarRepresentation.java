package org.trg;

public class CarRepresentation {

	private long createdDate;
	private long modifiedDate;
	private long id;
	private String brand;
	private String type;
	private String licensePlates;
	private DriverRepresenation driver;
	
	public long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(long createdDate) {
		this.createdDate = createdDate;
	}
	public long getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(long modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public DriverRepresenation getDriver() {
		return driver;
	}
	public void setDriver(DriverRepresenation driver) {
		this.driver = driver;
	}
	public String getLicensePlates() {
		return licensePlates;
	}
	public void setLicensePlates(String licensePlates) {
		this.licensePlates = licensePlates;
	}
	
	
	
}
