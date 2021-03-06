package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hls_country database table.
 * 
 */
@Entity
@Table(name="hls_country")
@NamedQuery(name="HlsCountry.findAll", query="SELECT h FROM HlsCountry h")
public class HlsCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String value;

	private String descrition;

	public HlsCountry() {
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