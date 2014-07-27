package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the set_medicine database table.
 * 
 */
@Entity
@Table(name="set_medicine")
public class SetMedicine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String name;

	private int no;

	//bi-directional many-to-one association to SetDiagnostic
	@OneToMany(mappedBy="setMedicine")
	private List<SetDiagnostic> setDiagnostics;

	//bi-directional many-to-one association to Medicine
	@ManyToOne
	@JoinColumn(name="m_code")
	private Medicine medicine;

	public SetMedicine() {
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
		setDiagnostic.setSetMedicine(this);

		return setDiagnostic;
	}

	public SetDiagnostic removeSetDiagnostic(SetDiagnostic setDiagnostic) {
		getSetDiagnostics().remove(setDiagnostic);
		setDiagnostic.setSetMedicine(null);

		return setDiagnostic;
	}

	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

}