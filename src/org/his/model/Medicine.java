package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medicines database table.
 * 
 */
@Entity
@Table(name="medicines")
public class Medicine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String code;

	private byte effective;

	private String injection;

	private String item;

	private int sort;

	private String unit;

	@Column(name="unit_dosage")
	private float unitDosage;

	public Medicine() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public byte getEffective() {
		return this.effective;
	}

	public void setEffective(byte effective) {
		this.effective = effective;
	}

	public String getInjection() {
		return this.injection;
	}

	public void setInjection(String injection) {
		this.injection = injection;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getUnitDosage() {
		return this.unitDosage;
	}

	public void setUnitDosage(float unitDosage) {
		this.unitDosage = unitDosage;
	}

}