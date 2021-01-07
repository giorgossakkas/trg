package org.trg;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Giorgos Sakkas
 * This class represents the trip information 
 *
 */

public class TripInformation {

	@JsonProperty("car_id")
	private long carId;
	@JsonProperty("driver_id")
	private long driverId;
	@JsonProperty("speed")
	private BigDecimal speed;
	@JsonProperty("from_lat")
	private BigDecimal fromLat;
	@JsonProperty("from_lon")
	private BigDecimal fromLon;
	@JsonProperty("to_lat")
	private BigDecimal toLat;
	@JsonProperty("to_lon")
	private BigDecimal toLon;
	
	public long getCarId() {
		return carId;
	}
	public void setCarId(long carId) {
		this.carId = carId;
	}
	public long getDriverId() {
		return driverId;
	}
	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}
	public BigDecimal getSpeed() {
		return speed;
	}
	public void setSpeed(BigDecimal speed) {
		this.speed = speed;
	}
	public BigDecimal getFromLat() {
		return fromLat;
	}
	public void setFromLat(BigDecimal fromLat) {
		this.fromLat = fromLat;
	}
	public BigDecimal getFromLon() {
		return fromLon;
	}
	public void setFromLon(BigDecimal fromLon) {
		this.fromLon = fromLon;
	}
	public BigDecimal getToLat() {
		return toLat;
	}
	public void setToLat(BigDecimal toLat) {
		this.toLat = toLat;
	}
	public BigDecimal getToLon() {
		return toLon;
	}
	public void setToLon(BigDecimal toLon) {
		this.toLon = toLon;
	}
	
	
	
}
