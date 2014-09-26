package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the permission_info database table.
 * 
 */
@Entity
@Table(name="permission_info")
@NamedQuery(name="PermissionInfo.findAll", query="SELECT p FROM PermissionInfo p")
public class PermissionInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Column(name="grp_name")
	private String grpName;

	private byte lvl;

	@Column(name="sys_name")
	private String sysName;

	public PermissionInfo() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public byte getLvl() {
		return this.lvl;
	}

	public void setLvl(byte lvl) {
		this.lvl = lvl;
	}

	public String getSysName() {
		return this.sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

}