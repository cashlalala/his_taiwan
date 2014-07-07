package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the anamnesis_retrieve database table.
 * 
 */
@Entity
@Table(name="anamnesis_retrieve")
public class AnamnesisRetrieve implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="borrow_time")
	private Date borrowTime;

	@Column(name="reg_guid")
	private String regGuid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="return_time")
	private Date returnTime;

	public AnamnesisRetrieve() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getBorrowTime() {
		return this.borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

}