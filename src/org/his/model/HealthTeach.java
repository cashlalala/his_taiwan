package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the health_teach database table.
 * 
 */
@Entity
@Table(name="health_teach")
public class HealthTeach implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private String acceptance;

	private String confirm;

	@Column(name="hti_code")
	private String htiCode;

	@Column(name="reg_guid")
	private String regGuid;

	@Column(name="s_id")
	private String sId;

	public HealthTeach() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAcceptance() {
		return this.acceptance;
	}

	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}

	public String getConfirm() {
		return this.confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getHtiCode() {
		return this.htiCode;
	}

	public void setHtiCode(String htiCode) {
		this.htiCode = htiCode;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public String getSId() {
		return this.sId;
	}

	public void setSId(String sId) {
		this.sId = sId;
	}

}