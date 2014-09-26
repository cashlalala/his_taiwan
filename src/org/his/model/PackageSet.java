package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the package_set database table.
 * 
 */
@Entity
@Table(name="package_set")
@NamedQuery(name="PackageSet.findAll", query="SELECT p FROM PackageSet p")
public class PackageSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Column(name="cell_phone")
	private String cellPhone;

	@Lob
	private String content;

	private int days;

	private String id;

	@Column(name="reg_guid")
	private String regGuid;

	@Column(name="send_sno")
	private int sendSno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_time")
	private Date sendTime;

	@Column(name="sms_state")
	private String smsState;

	private String title;

	@Temporal(TemporalType.DATE)
	@Column(name="use_date")
	private Date useDate;

	public PackageSet() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDays() {
		return this.days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public int getSendSno() {
		return this.sendSno;
	}

	public void setSendSno(int sendSno) {
		this.sendSno = sendSno;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSmsState() {
		return this.smsState;
	}

	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUseDate() {
		return this.useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

}