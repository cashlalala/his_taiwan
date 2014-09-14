package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the staff_fingertemplate database table.
 * 
 */
@Entity
@Table(name="staff_fingertemplate")
@NamedQuery(name="StaffFingertemplate.findAll", query="SELECT s FROM StaffFingertemplate s")
public class StaffFingertemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Lob
	private byte[] template;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="id")
	private StaffInfo staffInfo;

	public StaffFingertemplate() {
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

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}