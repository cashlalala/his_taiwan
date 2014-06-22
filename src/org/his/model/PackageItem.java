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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private int days;

	private String typ;

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

}