package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the grade database table.
 * 
 */
@Entity
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private String name;

	public Grade() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}