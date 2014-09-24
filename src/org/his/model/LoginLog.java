package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the login_log database table.
 * 
 */
@Entity
@Table(name="login_log")
@NamedQuery(name="LoginLog.findAll", query="SELECT l FROM LoginLog l")
public class LoginLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="log_time")
	private Date logTime;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public LoginLog() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}