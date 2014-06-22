package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the allergy database table.
 * 
 */
@Entity
public class Allergy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private byte level;

	@Column(name="m_code")
	private String mCode;

	@Column(name="p_no")
	private int pNo;

	@Column(name="u_sid")
	private String uSid;

	@Temporal(TemporalType.DATE)
	private Date udate;

	public Allergy() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public byte getLevel() {
		return this.level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public String getMCode() {
		return this.mCode;
	}

	public void setMCode(String mCode) {
		this.mCode = mCode;
	}

	public int getPNo() {
		return this.pNo;
	}

	public void setPNo(int pNo) {
		this.pNo = pNo;
	}

	public String getUSid() {
		return this.uSid;
	}

	public void setUSid(String uSid) {
		this.uSid = uSid;
	}

	public Date getUdate() {
		return this.udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

}