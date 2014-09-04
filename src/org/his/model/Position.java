package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the position database table.
 * 
 */
@Entity
@Table(name="position")
@NamedQuery(name="Position.findAll", query="SELECT p FROM Position p")
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	private String name;

	//bi-directional many-to-one association to StaffInfo
	@OneToMany(mappedBy="position")
	private List<StaffInfo> staffInfos;

	public Position() {
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

	public List<StaffInfo> getStaffInfos() {
		return this.staffInfos;
	}

	public void setStaffInfos(List<StaffInfo> staffInfos) {
		this.staffInfos = staffInfos;
	}

	public StaffInfo addStaffInfo(StaffInfo staffInfo) {
		getStaffInfos().add(staffInfo);
		staffInfo.setPosition(this);

		return staffInfo;
	}

	public StaffInfo removeStaffInfo(StaffInfo staffInfo) {
		getStaffInfos().remove(staffInfo);
		staffInfo.setPosition(null);

		return staffInfo;
	}

}