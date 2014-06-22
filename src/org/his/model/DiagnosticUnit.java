package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diagnostic_unit database table.
 * 
 */
@Entity
@Table(name="diagnostic_unit")
public class DiagnosticUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String value;

	private String descrition;

	public DiagnosticUnit() {
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