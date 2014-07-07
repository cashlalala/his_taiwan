package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dia_class database table.
 * 
 */
@Entity
@Table(name="dia_class")
public class DiaClass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String value;

	private String descrition;

	public DiaClass() {
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescrition() {
		return this.descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

}