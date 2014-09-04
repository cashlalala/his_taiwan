package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the children database table.
 * 
 */
@Entity
@Table(name="children")
@NamedQuery(name="Children.findAll", query="SELECT c FROM Children c")
public class Children implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int no;

	@Temporal(TemporalType.DATE)
	@Column(name="date_birth")
	private Date dateBirth;

	private String name;

	@Column(name="nhis_no")
	private String nhisNo;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public Children() {
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Date getDateBirth() {
		return this.dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNhisNo() {
		return this.nhisNo;
	}

	public void setNhisNo(String nhisNo) {
		this.nhisNo = nhisNo;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}