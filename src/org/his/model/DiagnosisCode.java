package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diagnosis_code database table.
 * 
 */
@Entity
@Table(name="diagnosis_code")
public class DiagnosisCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="icd_code")
	private String icdCode;

	private byte effective;

	private String name;

	public DiagnosisCode() {
	}

	public String getIcdCode() {
		return this.icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public byte getEffective() {
		return this.effective;
	}

	public void setEffective(byte effective) {
		this.effective = effective;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}