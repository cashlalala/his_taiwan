package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the clinical database table.
 * 
 */
@Entity
public class Clinical implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int no;

	@Column(name="poli_guid")
	private String poliGuid;

	@Column(name="s_no")
	private int sNo;

	public Clinical() {
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getPoliGuid() {
		return this.poliGuid;
	}

	public void setPoliGuid(String poliGuid) {
		this.poliGuid = poliGuid;
	}

	public int getSNo() {
		return this.sNo;
	}

	public void setSNo(int sNo) {
		this.sNo = sNo;
	}

}