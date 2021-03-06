package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hls_specimen_source database table.
 * 
 */
@Entity
@Table(name="hls_specimen_source")
@NamedQuery(name="HlsSpecimenSource.findAll", query="SELECT h FROM HlsSpecimenSource h")
public class HlsSpecimenSource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String value;

	private String descrition;

	public HlsSpecimenSource() {
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