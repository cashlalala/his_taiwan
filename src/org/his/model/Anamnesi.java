package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the anamnesis database table.
 * 
 */
@Entity
@Table(name="anamnesis")
public class Anamnesi implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AnamnesiPK id;

	private String status;

	//bi-directional many-to-one association to PatientsInfo
	@ManyToOne
	@JoinColumn(name="p_no", insertable = false, updatable = false)
	private PatientsInfo patientsInfo;

	//bi-directional many-to-one association to RegistrationInfo
	@ManyToOne
	@JoinColumn(name="reg_guid", insertable = false, updatable = false)
	private RegistrationInfo registrationInfo;

	//bi-directional many-to-one association to AnamnesisRetrieve
	@ManyToOne
	@JoinColumn(name="borrow_guid")
	private AnamnesisRetrieve anamnesisRetrieve;

	public Anamnesi() {
	}

	public AnamnesiPK getId() {
		return this.id;
	}

	public void setId(AnamnesiPK id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PatientsInfo getPatientsInfo() {
		return this.patientsInfo;
	}

	public void setPatientsInfo(PatientsInfo patientsInfo) {
		this.patientsInfo = patientsInfo;
	}

	public RegistrationInfo getRegistrationInfo() {
		return this.registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfo registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

	public AnamnesisRetrieve getAnamnesisRetrieve() {
		return this.anamnesisRetrieve;
	}

	public void setAnamnesisRetrieve(AnamnesisRetrieve anamnesisRetrieve) {
		this.anamnesisRetrieve = anamnesisRetrieve;
	}

}