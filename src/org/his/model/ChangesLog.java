package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the changes_log database table.
 * 
 */
@Entity
@Table(name="changes_log")
@NamedQuery(name="ChangesLog.findAll", query="SELECT c FROM ChangesLog c")
public class ChangesLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="chg_guid")
	private String chgGuid;

	@Column(name="chg_info")
	private String chgInfo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="chg_time")
	private Date chgTime;

	@Column(name="table_guid")
	private String tableGuid;

	@Column(name="table_name")
	private String tableName;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public ChangesLog() {
	}

	public String getChgGuid() {
		return this.chgGuid;
	}

	public void setChgGuid(String chgGuid) {
		this.chgGuid = chgGuid;
	}

	public String getChgInfo() {
		return this.chgInfo;
	}

	public void setChgInfo(String chgInfo) {
		this.chgInfo = chgInfo;
	}

	public Date getChgTime() {
		return this.chgTime;
	}

	public void setChgTime(Date chgTime) {
		this.chgTime = chgTime;
	}

	public String getTableGuid() {
		return this.tableGuid;
	}

	public void setTableGuid(String tableGuid) {
		this.tableGuid = tableGuid;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}