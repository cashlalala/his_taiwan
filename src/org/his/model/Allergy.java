package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the allergy database table.
 * 
 */
@Entity
@Table(name="allergy")
@NamedQuery(name="Allergy.findAll", query="SELECT a FROM Allergy a")
public class Allergy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	private byte level;

	@Temporal(TemporalType.DATE)
	private Date udate;

	//bi-directional many-to-one association to PatientsInfo
	@ManyToOne
	@JoinColumn(name="p_no")
	private PatientsInfo patientsInfo;

	//bi-directional many-to-one association to Medicine
	@ManyToOne
	@JoinColumn(name="m_code")
	private Medicine medicine;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="u_sid")
	private StaffInfo staffInfo;

	public Allergy() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public byte getLevel() {
		return this.level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public Date getUdate() {
		return this.udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

	public PatientsInfo getPatientsInfo() {
		return this.patientsInfo;
	}

	public void setPatientsInfo(PatientsInfo patientsInfo) {
		this.patientsInfo = patientsInfo;
	}

	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}