package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_info database table.
 * 
 */
@Entity
@Table(name="sys_info")
public class SysInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="hos_name")
	private String hosName;

	@Column(name="hl7_edition")
	private String hl7Edition;

	@Column(name="hos_address")
	private String hosAddress;

	@Column(name="hos_icon_path")
	private String hosIconPath;

	@Column(name="hos_id")
	private String hosId;

	@Column(name="hos_info")
	private String hosInfo;

	@Column(name="hos_local")
	private String hosLocal;

	@Column(name="hos_mail")
	private String hosMail;

	@Column(name="hos_phone")
	private String hosPhone;

	@Column(name="icd_deition")
	private String icdDeition;

	@Column(name="remind_days")
	private int remindDays;

	public SysInfo() {
	}

	public String getHosName() {
		return this.hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public String getHl7Edition() {
		return this.hl7Edition;
	}

	public void setHl7Edition(String hl7Edition) {
		this.hl7Edition = hl7Edition;
	}

	public String getHosAddress() {
		return this.hosAddress;
	}

	public void setHosAddress(String hosAddress) {
		this.hosAddress = hosAddress;
	}

	public String getHosIconPath() {
		return this.hosIconPath;
	}

	public void setHosIconPath(String hosIconPath) {
		this.hosIconPath = hosIconPath;
	}

	public String getHosId() {
		return this.hosId;
	}

	public void setHosId(String hosId) {
		this.hosId = hosId;
	}

	public String getHosInfo() {
		return this.hosInfo;
	}

	public void setHosInfo(String hosInfo) {
		this.hosInfo = hosInfo;
	}

	public String getHosLocal() {
		return this.hosLocal;
	}

	public void setHosLocal(String hosLocal) {
		this.hosLocal = hosLocal;
	}

	public String getHosMail() {
		return this.hosMail;
	}

	public void setHosMail(String hosMail) {
		this.hosMail = hosMail;
	}

	public String getHosPhone() {
		return this.hosPhone;
	}

	public void setHosPhone(String hosPhone) {
		this.hosPhone = hosPhone;
	}

	public String getIcdDeition() {
		return this.icdDeition;
	}

	public void setIcdDeition(String icdDeition) {
		this.icdDeition = icdDeition;
	}

	public int getRemindDays() {
		return this.remindDays;
	}

	public void setRemindDays(int remindDays) {
		this.remindDays = remindDays;
	}

}