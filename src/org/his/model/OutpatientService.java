package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the outpatient_services database table.
 * 
 */
@Entity
@Table(name="outpatient_services")
public class OutpatientService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date finishtime;

	private String ps;

	@Column(name="reg_guid")
	private String regGuid;

	private byte state;

	@Lob
	private String summary;

	public OutpatientService() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	public String getPs() {
		return this.ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}