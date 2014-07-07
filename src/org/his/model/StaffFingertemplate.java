package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_fingertemplate database table.
 * 
 */
@Entity
@Table(name="staff_fingertemplate")
public class StaffFingertemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private int id;

	@Lob
	private byte[] template;

	public StaffFingertemplate() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getTemplate() {
		return this.template;
	}

	public void setTemplate(byte[] template) {
		this.template = template;
	}

}