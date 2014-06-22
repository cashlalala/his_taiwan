package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the prescription database table.
 * 
 */
@Entity
public class Prescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Column(name="abnormal_flags")
	private String abnormalFlags;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="analysis_time")
	private Date analysisTime;

	@Column(name="case_guid")
	private String caseGuid;

	private String code;

	private double cost;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_results")
	private Date dateResults;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_test")
	private Date dateTest;

	private String finish;

	private byte isnormal;

	@Lob
	@Column(name="obs_method")
	private String obsMethod;

	@Column(name="os_guid")
	private String osGuid;

	private String place;

	private BigDecimal probability;

	@Lob
	@Column(name="res_observer")
	private String resObserver;

	@Lob
	private String result;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="specimen_received")
	private Date specimenReceived;

	@Column(name="specimen_status")
	private String specimenStatus;

	private byte state;

	@Column(name="tissue_type")
	private String tissueType;

	public Prescription() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAbnormalFlags() {
		return this.abnormalFlags;
	}

	public void setAbnormalFlags(String abnormalFlags) {
		this.abnormalFlags = abnormalFlags;
	}

	public Date getAnalysisTime() {
		return this.analysisTime;
	}

	public void setAnalysisTime(Date analysisTime) {
		this.analysisTime = analysisTime;
	}

	public String getCaseGuid() {
		return this.caseGuid;
	}

	public void setCaseGuid(String caseGuid) {
		this.caseGuid = caseGuid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getCost() {
		return this.cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Date getDateResults() {
		return this.dateResults;
	}

	public void setDateResults(Date dateResults) {
		this.dateResults = dateResults;
	}

	public Date getDateTest() {
		return this.dateTest;
	}

	public void setDateTest(Date dateTest) {
		this.dateTest = dateTest;
	}

	public String getFinish() {
		return this.finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public byte getIsnormal() {
		return this.isnormal;
	}

	public void setIsnormal(byte isnormal) {
		this.isnormal = isnormal;
	}

	public String getObsMethod() {
		return this.obsMethod;
	}

	public void setObsMethod(String obsMethod) {
		this.obsMethod = obsMethod;
	}

	public String getOsGuid() {
		return this.osGuid;
	}

	public void setOsGuid(String osGuid) {
		this.osGuid = osGuid;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public BigDecimal getProbability() {
		return this.probability;
	}

	public void setProbability(BigDecimal probability) {
		this.probability = probability;
	}

	public String getResObserver() {
		return this.resObserver;
	}

	public void setResObserver(String resObserver) {
		this.resObserver = resObserver;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getSpecimenReceived() {
		return this.specimenReceived;
	}

	public void setSpecimenReceived(Date specimenReceived) {
		this.specimenReceived = specimenReceived;
	}

	public String getSpecimenStatus() {
		return this.specimenStatus;
	}

	public void setSpecimenStatus(String specimenStatus) {
		this.specimenStatus = specimenStatus;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public String getTissueType() {
		return this.tissueType;
	}

	public void setTissueType(String tissueType) {
		this.tissueType = tissueType;
	}

}