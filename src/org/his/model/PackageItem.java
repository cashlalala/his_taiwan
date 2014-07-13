package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the package_item database table.
 * 
 */
@Entity
@Table(name="package_item")
public class PackageItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private int days;

	private String typ;

	//bi-directional one-to-one association to Medicine
	@OneToOne
	@JoinColumn(name="id")
	private Medicine medicine;

	public PackageItem() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDays() {
		return this.days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

}