package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the medicine_way database table.
 * 
 */
@Entity
@Table(name="medicine_way")
public class MedicineWay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String code;

	private String explain;

	//bi-directional many-to-one association to MedicineStock
	@OneToMany(mappedBy="medicineWay")
	private List<MedicineStock> medicineStocks;

	public MedicineWay() {
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

	public List<MedicineStock> getMedicineStocks() {
		return this.medicineStocks;
	}

	public void setMedicineStocks(List<MedicineStock> medicineStocks) {
		this.medicineStocks = medicineStocks;
	}

	public MedicineStock addMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().add(medicineStock);
		medicineStock.setMedicineWay(this);

		return medicineStock;
	}

	public MedicineStock removeMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().remove(medicineStock);
		medicineStock.setMedicineWay(null);

		return medicineStock;
	}

}