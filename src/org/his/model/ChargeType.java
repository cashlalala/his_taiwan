package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the charge_type database table.
 * 
 */
@Entity
@Table(name="charge_type")
@NamedQuery(name="ChargeType.findAll", query="SELECT c FROM ChargeType c")
public class ChargeType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String value;

	private String descrition;

	public ChargeType() {
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