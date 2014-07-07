package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medicine_way database table.
 * 
 */
@Entity
@Table(name="medicine_way")
public class MedicineWay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String code;

	private String explain;

	public MedicineWay() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExplain() {
		return this.explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}