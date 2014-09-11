package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the treat database table.
 * 
 */
@Entity
@Table(name="treat")
@NamedQuery(name="Treat.findAll", query="SELECT t FROM Treat t")
public class Treat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Lob
	@Column(name="ane_code")
	private String aneCode;

	@Column(name="ane_id")
	private String aneId;

	@Lob
	@Column(name="ane_record")
	private String aneRecord;

	private String consent;

	@Lob
	private String description;

	private String priority;

	@Column(name="sur_id")
	private String surId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	@Lob
	@Column(name="treat_record")
	private String treatRecord;

	@Column(name="treat_type")
	private String treatType;

	//bi-directional many-to-one association to OutpatientService
	@ManyToOne
	@JoinColumn(name="os_guid")
	private OutpatientService outpatientService;

	public Treat() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAneCode() {
		return this.aneCode;
	}

	public void setAneCode(String aneCode) {
		this.aneCode = aneCode;
	}

	public String getAneId() {
		return this.aneId;
	}

	public void setAneId(String aneId) {
		this.aneId = aneId;
	}

	public String getAneRecord() {
		return this.aneRecord;
	}

	public void setAneRecord(String aneRecord) {
		this.aneRecord = aneRecord;
	}

	public String getConsent() {
		return this.consent;
	}

	public void setConsent(String consent) {
		this.consent = consent;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSurId() {
		return this.surId;
	}

	public void setSurId(String surId) {
		this.surId = surId;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTreatRecord() {
		return this.treatRecord;
	}

	public void setTreatRecord(String treatRecord) {
		this.treatRecord = treatRecord;
	}

	public String getTreatType() {
		return this.treatType;
	}

	public void setTreatType(String treatType) {
		this.treatType = treatType;
	}

	public OutpatientService getOutpatientService() {
		return this.outpatientService;
	}

	public void setOutpatientService(OutpatientService outpatientService) {
		this.outpatientService = outpatientService;
	}

}