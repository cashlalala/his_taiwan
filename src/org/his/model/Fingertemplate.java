package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fingertemplate database table.
 * 
 */
@Entity
@Table(name="fingertemplate")
public class Fingertemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Lob
	private byte[] template;

	//bi-directional many-to-one association to PatientsInfo
	@ManyToOne
	@JoinColumn(name="id")
	private PatientsInfo patientsInfo;

	public Fingertemplate() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public byte[] getTemplate() {
		return this.template;
	}

	public void setTemplate(byte[] template) {
		this.template = template;
	}

	public PatientsInfo getPatientsInfo() {
		return this.patientsInfo;
	}

	public void setPatientsInfo(PatientsInfo patientsInfo) {
		this.patientsInfo = patientsInfo;
	}

}