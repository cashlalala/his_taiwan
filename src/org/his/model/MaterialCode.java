package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the material_code database table.
 * 
 */
@Entity
@Table(name="material_code")
public class MaterialCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Lob
	private String description;

	private String name;

	private String unit;

	@Column(name="unit_cost")
	private float unitCost;

	@Column(name="unit_price")
	private float unitPrice;

	public MaterialCode() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}

	public float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

}