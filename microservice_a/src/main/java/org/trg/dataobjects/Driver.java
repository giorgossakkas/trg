package org.trg.dataobjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * @author Giorgos Sakkas
 * This is the Driver data object mapped to drivers database table.
 *
 */

@Entity
@Table(name = "drivers")
@Where(clause = "isDeleted = 0")
@SQLDelete(sql = "UPDATE drivers SET isdeleted = 1 WHERE id = ? and recversion = ?", check = ResultCheckStyle.COUNT)
public class Driver extends org.trg.dataobjects.Entity{

	@Id
    @SequenceGenerator(
            name = "driversSequence",
            sequenceName = "drivers_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driversSequence")
    private Long id;
	
	@NotNull(message="Driver first name cannot be blank")
    @Column(name = "firstname",length = 256)
	@Size(min = 0, max = 256)
	private String firstName;

	@NotNull(message="Driver last name cannot be blank")
    @Column(name = "lastname",length = 256)
	@Size(min = 0, max = 256)
	private String lastName;

	@NotNull(message="Driver id number cannot be blank")
    @Column(name = "idnumber",length = 32)
	@Size(min = 0, max = 32)
	private String idnumber;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
    
    
	
}
