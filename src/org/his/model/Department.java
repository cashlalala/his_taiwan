package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the department database table.
 * 
 */
@Entity
@Table(name="department")
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String name;

	//bi-directional many-to-one association to Administrative
	@OneToMany(mappedBy="department")
	private List<Administrative> administratives;

	//bi-directional many-to-one association to StaffInfo
	@OneToMany(mappedBy="department")
	private List<StaffInfo> staffInfos;

	public Department() {
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

	public List<Administrative> getAdministratives() {
		return this.administratives;
	}

	public void setAdministratives(List<Administrative> administratives) {
		this.administratives = administratives;
	}

	public Administrative addAdministrative(Administrative administrative) {
		getAdministratives().add(administrative);
		administrative.setDepartment(this);

		return administrative;
	}

	public Administrative removeAdministrative(Administrative administrative) {
		getAdministratives().remove(administrative);
		administrative.setDepartment(null);

		return administrative;
	}

	public List<StaffInfo> getStaffInfos() {
		return this.staffInfos;
	}

	public void setStaffInfos(List<StaffInfo> staffInfos) {
		this.staffInfos = staffInfos;
	}

	public StaffInfo addStaffInfo(StaffInfo staffInfo) {
		getStaffInfos().add(staffInfo);
		staffInfo.setDepartment(this);

		return staffInfo;
	}

	public StaffInfo removeStaffInfo(StaffInfo staffInfo) {
		getStaffInfos().remove(staffInfo);
		staffInfo.setDepartment(null);

		return staffInfo;
	}

}