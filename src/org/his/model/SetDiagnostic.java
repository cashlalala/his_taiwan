package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the set_diagnostic database table.
 * 
 */
@Entity
@Table(name="set_diagnostic")
public class SetDiagnostic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String name;

	private int no;

	//bi-directional many-to-one association to DiagnosisCode
	@ManyToOne
	@JoinColumn(name="dia_code")
	private DiagnosisCode diagnosisCode;

	//bi-directional many-to-one association to SetMedicine
	@ManyToOne
	@JoinColumn(name="related_medicine_set_no")
	private SetMedicine setMedicine;

	//bi-directional many-to-one association to SetPrescription
	@ManyToOne
	@JoinColumn(name="related_prescription_set_no")
	private SetPrescription setPrescription;

	public SetDiagnostic() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public DiagnosisCode getDiagnosisCode() {
		return this.diagnosisCode;
	}

	public void setDiagnosisCode(DiagnosisCode diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public SetMedicine getSetMedicine() {
		return this.setMedicine;
	}

	public void setSetMedicine(SetMedicine setMedicine) {
		this.setMedicine = setMedicine;
	}

	public SetPrescription getSetPrescription() {
		return this.setPrescription;
	}

	public void setSetPrescription(SetPrescription setPrescription) {
		this.setPrescription = setPrescription;
	}

}