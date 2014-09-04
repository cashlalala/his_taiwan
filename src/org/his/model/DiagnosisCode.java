package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the diagnosis_code database table.
 * 
 */
@Entity
@Table(name="diagnosis_code")
@NamedQuery(name="DiagnosisCode.findAll", query="SELECT d FROM DiagnosisCode d")
public class DiagnosisCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dia_code")
	private String diaCode;

	private byte effective;

	@Column(name="icd_code")
	private String icdCode;

	private String ICDVersion;

	private String name;

	//bi-directional many-to-one association to Diagnostic
	@OneToMany(mappedBy="diagnosisCode")
	private List<Diagnostic> diagnostics;

	//bi-directional many-to-one association to SetDiagnostic
	@OneToMany(mappedBy="diagnosisCode")
	private List<SetDiagnostic> setDiagnostics;

	public DiagnosisCode() {
	}

	public String getDiaCode() {
		return this.diaCode;
	}

	public void setDiaCode(String diaCode) {
		this.diaCode = diaCode;
	}

	public byte getEffective() {
		return this.effective;
	}

	public void setEffective(byte effective) {
		this.effective = effective;
	}

	public String getIcdCode() {
		return this.icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public String getICDVersion() {
		return this.ICDVersion;
	}

	public void setICDVersion(String ICDVersion) {
		this.ICDVersion = ICDVersion;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Diagnostic> getDiagnostics() {
		return this.diagnostics;
	}

	public void setDiagnostics(List<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public Diagnostic addDiagnostic(Diagnostic diagnostic) {
		getDiagnostics().add(diagnostic);
		diagnostic.setDiagnosisCode(this);

		return diagnostic;
	}

	public Diagnostic removeDiagnostic(Diagnostic diagnostic) {
		getDiagnostics().remove(diagnostic);
		diagnostic.setDiagnosisCode(null);

		return diagnostic;
	}

	public List<SetDiagnostic> getSetDiagnostics() {
		return this.setDiagnostics;
	}

	public void setSetDiagnostics(List<SetDiagnostic> setDiagnostics) {
		this.setDiagnostics = setDiagnostics;
	}

	public SetDiagnostic addSetDiagnostic(SetDiagnostic setDiagnostic) {
		getSetDiagnostics().add(setDiagnostic);
		setDiagnostic.setDiagnosisCode(this);

		return setDiagnostic;
	}

	public SetDiagnostic removeSetDiagnostic(SetDiagnostic setDiagnostic) {
		getSetDiagnostics().remove(setDiagnostic);
		setDiagnostic.setDiagnosisCode(null);

		return setDiagnostic;
	}

}