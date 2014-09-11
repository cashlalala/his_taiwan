package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the religion database table.
 * 
 */
@NamedQueries ({
    @NamedQuery(
        name="QueryReligions",
        query="SELECT religion FROM Religion religion"),
    @NamedQuery(
        name="QueryReligionByValue",
        query="SELECT religion FROM Religion religion WHERE religion.value = :val")
})

@Entity
@Table(name="religion")
public class Religion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String value;

	private String descrition;

	//bi-directional many-to-one association to PatientsInfo
	@OneToMany(mappedBy="religionBean")
	private List<PatientsInfo> patientsInfos;

	public Religion() {
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescrition() {
		return this.descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	public List<PatientsInfo> getPatientsInfos() {
		return this.patientsInfos;
	}

	public void setPatientsInfos(List<PatientsInfo> patientsInfos) {
		this.patientsInfos = patientsInfos;
	}

	public PatientsInfo addPatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().add(patientsInfo);
		patientsInfo.setReligionBean(this);

		return patientsInfo;
	}

	public PatientsInfo removePatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().remove(patientsInfo);
		patientsInfo.setReligionBean(null);

		return patientsInfo;
	}

}