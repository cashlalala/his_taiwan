package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the administrative database table.
 * 
 */
@Entity
public class Administrative implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int no;

	@Column(name="dep_guid")
	private String depGuid;

	@Column(name="s_no")
	private int sNo;

	public Administrative() {
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getDepGuid() {
		return this.depGuid;
	}

	public void setDepGuid(String depGuid) {
		this.depGuid = depGuid;
	}

	public int getSNo() {
		return this.sNo;
	}

	public void setSNo(int sNo) {
		this.sNo = sNo;
	}

}