package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the medicine_frequency database table.
 * 
 */
@Entity
@Table(name="medicine_frequency")
@NamedQuery(name="MedicineFrequency.findAll", query="SELECT m FROM MedicineFrequency m")
public class MedicineFrequency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String explain;

	private int value;

	//bi-directional many-to-one association to MedicineStock
	@OneToMany(mappedBy="medicineFrequency")
	private List<MedicineStock> medicineStocks;

	public MedicineFrequency() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public List<MedicineStock> getMedicineStocks() {
		return this.medicineStocks;
	}

	public void setMedicineStocks(List<MedicineStock> medicineStocks) {
		this.medicineStocks = medicineStocks;
	}

	public MedicineStock addMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().add(medicineStock);
		medicineStock.setMedicineFrequency(this);

		return medicineStock;
	}

	public MedicineStock removeMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().remove(medicineStock);
		medicineStock.setMedicineFrequency(null);

		return medicineStock;
	}

}