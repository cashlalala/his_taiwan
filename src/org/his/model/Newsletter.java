package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the newsletter database table.
 * 
 */
@Entity
public class Newsletter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String name;

	@Lob
	private String detail;

	public Newsletter() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}