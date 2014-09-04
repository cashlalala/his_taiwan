package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the set_prescription database table.
 * 
 */
@Entity
@Table(name="set_prescription")
@NamedQuery(name="SetPrescription.findAll", query="SELECT s FROM SetPrescription s")
public class SetPrescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	private String name;

	private int no;

	//bi-directional many-to-one association to SetDiagnostic
	@OneToMany(mappedBy="setPrescription")
	private List<SetDiagnostic> setDiagnostics;

	//bi-directional many-to-one association to PrescriptionCode
	@ManyToOne
	@JoinColumn(name="pre_code")
	private PrescriptionCode prescriptionCode;

	public SetPrescription() {
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

	public List<SetDiagnostic> getSetDiagnostics() {
		return this.setDiagnostics;
	}

	public void setSetDiagnostics(List<SetDiagnostic> setDiagnostics) {
		this.setDiagnostics = setDiagnostics;
	}

	public SetDiagnostic addSetDiagnostic(SetDiagnostic setDiagnostic) {
		getSetDiagnostics().add(setDiagnostic);
		setDiagnostic.setSetPrescription(this);

		return setDiagnostic;
	}

	public SetDiagnostic removeSetDiagnostic(SetDiagnostic setDiagnostic) {
		getSetDiagnostics().remove(setDiagnostic);
		setDiagnostic.setSetPrescription(null);

		return setDiagnostic;
	}

	public PrescriptionCode getPrescriptionCode() {
		return this.prescriptionCode;
	}

	public void setPrescriptionCode(PrescriptionCode prescriptionCode) {
		this.prescriptionCode = prescriptionCode;
	}

}