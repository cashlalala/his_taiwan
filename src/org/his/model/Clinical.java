package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the clinical database table.
 * 
 */
@Entity
@Table(name="clinical")
@NamedQuery(name="Clinical.findAll", query="SELECT c FROM Clinical c")
public class Clinical implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int no;

	//bi-directional many-to-one association to Policlinic
	@ManyToOne
	@JoinColumn(name="poli_guid")
	private Policlinic policlinic;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public Clinical() {
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Policlinic getPoliclinic() {
		return this.policlinic;
	}

	public void setPoliclinic(Policlinic policlinic) {
		this.policlinic = policlinic;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}