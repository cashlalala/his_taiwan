package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diagnostic database table.
 * 
 */
@Entity
@Table(name="diagnostic")
public class Diagnostic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String ICDVersion;

	private int priority;

	private byte state;

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

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
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