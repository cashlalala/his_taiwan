package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the escort_required database table.
 * 
 */
@Entity
@Table(name="escort_required")
public class EscortRequired implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String value;

	private String descrition;

	public EscortRequired() {
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