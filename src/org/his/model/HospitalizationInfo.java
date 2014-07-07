package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hospitalization_info database table.
 * 
 */
@Entity
@Table(name="hospitalization_info")
public class HospitalizationInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private String produce;

	public HospitalizationInfo() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getProduce() {
		return this.produce;
	}

	public void setProduce(String produce) {
		this.produce = produce;
	}

}