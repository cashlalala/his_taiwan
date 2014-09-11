package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the death_info database table.
 * 
 */
@Entity
@Table(name="death_info")
@NamedQuery(name="DeathInfo.findAll", query="SELECT d FROM DeathInfo d")
public class DeathInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Column(name="cause")
	private String cause;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_of_death")
	private Date dateOfDeath;

	private String indicator;

	//bi-directional many-to-one association to PatientsInfo
	@OneToMany(mappedBy="deathInfo")
	private List<PatientsInfo> patientsInfos;

	public DeathInfo() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCause() {
		return this.cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Date getDateOfDeath() {
		return this.dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public String getIndicator() {
		return this.indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public List<PatientsInfo> getPatientsInfos() {
		return this.patientsInfos;
	}

	public void setPatientsInfos(List<PatientsInfo> patientsInfos) {
		this.patientsInfos = patientsInfos;
	}

	public PatientsInfo addPatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().add(patientsInfo);
		patientsInfo.setDeathInfo(this);

		return patientsInfo;
	}

	public PatientsInfo removePatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().remove(patientsInfo);
		patientsInfo.setDeathInfo(null);

		return patientsInfo;
	}

}