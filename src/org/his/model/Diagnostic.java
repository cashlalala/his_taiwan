package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the diagnostic database table.
 * 
 */
@Entity
public class Diagnostic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Column(name="dia_code")
	private String diaCode;

	@Column(name="os_guid")
	private String osGuid;

	private int priority;

	private byte state;

	public Diagnostic() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDiaCode() {
		return this.diaCode;
	}

	public void setDiaCode(String diaCode) {
		this.diaCode = diaCode;
	}

	public String getOsGuid() {
		return this.osGuid;
	}

	public void setOsGuid(String osGuid) {
		this.osGuid = osGuid;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

}