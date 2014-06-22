package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hls_administrative_sex database table.
 * 
 */
@Entity
@Table(name="hls_administrative_sex")
public class HlsAdministrativeSex implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String value;

	private String descrition;

	public HlsAdministrativeSex() {
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