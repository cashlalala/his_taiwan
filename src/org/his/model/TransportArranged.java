package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the transport_arranged database table.
 * 
 */
@Entity
@Table(name="transport_arranged")
@NamedQuery(name="TransportArranged.findAll", query="SELECT t FROM TransportArranged t")
public class TransportArranged implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String value;

	private String descrition;

	public TransportArranged() {
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