package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the employment_history database table.
 * 
 */
@Entity
@Table(name="employment_history")
@NamedQuery(name="EmploymentHistory.findAll", query="SELECT e FROM EmploymentHistory e")
public class EmploymentHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int no;

	@Temporal(TemporalType.DATE)
	@Column(name="commencement_date")
	private Date commencementDate;

	@Column(name="employer_name")
	private String employerName;

	@Temporal(TemporalType.DATE)
	@Column(name="leaving_date")
	private Date leavingDate;

	private String position;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public EmploymentHistory() {
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Date getCommencementDate() {
		return this.commencementDate;
	}

	public void setCommencementDate(Date commencementDate) {
		this.commencementDate = commencementDate;
	}

	public String getEmployerName() {
		return this.employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Date getLeavingDate() {
		return this.leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}