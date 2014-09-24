package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diagnostic database table.
 * 
 */
@Entity
@Table(name="diagnostic")
@NamedQuery(name="Diagnostic.findAll", query="SELECT d FROM Diagnostic d")
public class Diagnostic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	private String ICDVersion;

	private int priority;

	private String ps;

	private byte state;

	@Lob
	private String summary;

	//bi-directional many-to-one association to DiagnosisCode
	@ManyToOne
	@JoinColumn(name="dia_code")
	private DiagnosisCode diagnosisCode;

	//bi-directional many-to-one association to RegistrationInfo
	@ManyToOne
	@JoinColumn(name="reg_guid")
	private RegistrationInfo registrationInfo;

	public Diagnostic() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getICDVersion() {
		return this.ICDVersion;
	}

	public void setICDVersion(String ICDVersion) {
		this.ICDVersion = ICDVersion;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getPs() {
		return this.ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public DiagnosisCode getDiagnosisCode() {
		return this.diagnosisCode;
	}

	public void setDiagnosisCode(DiagnosisCode diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public RegistrationInfo getRegistrationInfo() {
		return this.registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfo registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

}