package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the shipping_method database table.
 * 
 */
@Entity
@Table(name="shipping_method")
@NamedQuery(name="ShippingMethod.findAll", query="SELECT s FROM ShippingMethod s")
public class ShippingMethod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String value;

	private String descrition;

	public ShippingMethod() {
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