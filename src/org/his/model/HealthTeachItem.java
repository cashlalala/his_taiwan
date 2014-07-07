package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the health_teach_item database table.
 * 
 */
@Entity
@Table(name="health_teach_item")
public class HealthTeachItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String code;

	private String item;

	public HealthTeachItem() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}