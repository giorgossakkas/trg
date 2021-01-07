package org.trg.dataobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * @author Giorgos Sakkas
 * This class contains all the common information for all data object.
 *
 */

@MappedSuperclass
public class Entity extends PanacheEntityBase {

	@JsonIgnore
    @Column(name = "recversion")
    @Version
	private Integer recVersion;

	@Column(name = "createddate")
	private Date createdDate;

	@Column(name = "modifieddate")
	private Date modifiedDate;

	@JsonIgnore
	@Column(name = "isdeleted")
 	@Min(value = 0)
 	@Max(value = 1)
 	@Digits(integer = 1, fraction = 0)
	private Integer isDeleted;

	public Entity() {
		setIsDeleted(Integer.valueOf(0));
	}

	public Integer getRecVersion() {
		return recVersion;
	}

	public void setRecVersion(Integer recVersion) {
		this.recVersion = recVersion;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@PrePersist
    protected void prePersist() {
    	
    	this.createdDate = new java.util.Date();
    	this.modifiedDate= new java.util.Date();
    }
	
    @PreUpdate
    protected void preUpdate() {
    	this.modifiedDate= new java.util.Date();
    }
	
	

}
