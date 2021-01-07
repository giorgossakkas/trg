package org.trg.dataobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.trg.enums.CarType;

/**
 * @author Giorgos Sakkas
 * This is the Car data object mapped to cars database table.
 *
 */

@Entity
@Table(name = "cars")
@Where(clause = "isDeleted = 0")
@SQLDelete(sql = "UPDATE cars SET isdeleted = 1 WHERE id = ? and recversion = ?", check = ResultCheckStyle.COUNT)
public class Car extends org.trg.dataobjects.Entity {
	
	@Id
    @SequenceGenerator(
            name = "carsSequence",
            sequenceName = "cars_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carsSequence")
    private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="driver_id", nullable=false)
	private Driver driver;

	@NotNull(message="Car brand cannot be blank")
    @Column(name = "brand",length = 256)
	@Size(min = 0, max = 256)
	private String brand;
    
	@NotNull(message="Car type cannot be blank")
    @Column(name= "type", length = 32)
	@Enumerated(EnumType.STRING)
	private CarType type;
	
	@NotNull(message="Car license plates cannot be blank")
    @Column(name = "licenseplates",length = 32)
	@Size(min = 0, max = 32)
	private String licensePlates;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public CarType getType() {
		return type;
	}

	public void setType(CarType type) {
		this.type = type;
	}

	public String getLicensePlates() {
		return licensePlates;
	}

	public void setLicensePlates(String licensePlates) {
		this.licensePlates = licensePlates;
	}
	
	
}
