package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the administrative database table.
 * 
 */
@Entity
@Table(name="administrative")
public class Administrative implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int no;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="dep_guid")
	private Department department;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public Administrative() {
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}