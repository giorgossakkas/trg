package org.trg.dataobjects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author Giorgos Sakkas
 * This is the Trip data object mapped to trips database table.
 *
 */

@Entity
@Table(name = "trips")
@Where(clause = "isDeleted = 0")
@SQLDelete(sql = "UPDATE trips SET isdeleted = 1 WHERE id = ? and recversion = ?", check = ResultCheckStyle.COUNT)
public class Trip extends org.trg.dataobjects.Entity {

	@Id
    @SequenceGenerator(
            name = "tripsSequence",
            sequenceName = "trips_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tripsSequence")
    private Long id;
	
	@NotNull(message="From lat cannot be blank")
    @Column(name = "fromlat",length = 53)
	private BigDecimal fromLat;
	
	@NotNull(message="From lon cannot be blank")
    @Column(name = "fromlon",length = 53)
	private BigDecimal fromLon;
	
	@NotNull(message="To lat cannot be blank")
    @Column(name = "tolat",length = 53)
	private BigDecimal toLat;
	
	@NotNull(message="To lon cannot be blank")
    @Column(name = "tolon",length = 53)
	private BigDecimal toLon;

	@NotNull(message="Car cannot be blank")
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id", nullable=false)
	private Car car;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	
}
