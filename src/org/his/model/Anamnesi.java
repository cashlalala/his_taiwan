package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the anamnesis database table.
 * 
 */
@Entity
@Table(name="anamnesis")
@NamedQuery(name="Anamnesi.findAll", query="SELECT a FROM Anamnesi a")
public class Anamnesi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="p_no")
	private String pNo;

	private String status;

	//bi-directional many-to-one association to AnamnesisRetrieve
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="borrow_guid")
	private AnamnesisRetrieve anamnesisRetrieve;

	//bi-directional many-to-one association to PatientsInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="p_no", insertable = false, updatable = false)
	private PatientsInfo patientsInfo;

	public Anamnesi() {
	}

	public String getPNo() {
		return this.pNo;
	}

	public void setPNo(String pNo) {
		this.pNo = pNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AnamnesisRetrieve getAnamnesisRetrieve() {
		return this.anamnesisRetrieve;
	}

	public void setAnamnesisRetrieve(AnamnesisRetrieve anamnesisRetrieve) {
		this.anamnesisRetrieve = anamnesisRetrieve;
	}

	public PatientsInfo getPatientsInfo() {
		return this.patientsInfo;
	}

	public void setPatientsInfo(PatientsInfo patientsInfo) {
		this.patientsInfo = patientsInfo;
	}

}