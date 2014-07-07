package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the registration_info database table.
 * 
 */
@Entity
@Table(name="registration_info")
public class RegistrationInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Column(name="case_finish")
	private String caseFinish;

	private Double cost;

	private String finish;

	private String gis;

	private String hospitalized;

	@Column(name="lab_payment")
	private String labPayment;

	@Column(name="medicine_touchtime")
	private String medicineTouchtime;

	@Column(name="modify_count")
	private Integer modifyCount;

	@Column(name="p_no")
	private int pNo;

	private String payment;

	@Column(name="pharmacy_no")
	private Integer pharmacyNo;

	@Column(name="pharmacy_payment")
	private String pharmacyPayment;

	@Column(name="radiology_payment")
	private String radiologyPayment;

	@Column(name="record_touchtime")
	private String recordTouchtime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_time")
	private Date regTime;

	private String register;

	@Column(name="shift_guid")
	private String shiftGuid;

	private String touchtime;

	@Column(name="visits_no")
	private int visitsNo;

	public RegistrationInfo() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCaseFinish() {
		return this.caseFinish;
	}

	public void setCaseFinish(String caseFinish) {
		this.caseFinish = caseFinish;
	}

	public Double getCost() {
		return this.cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getFinish() {
		return this.finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getGis() {
		return this.gis;
	}

	public void setGis(String gis) {
		this.gis = gis;
	}

	public String getHospitalized() {
		return this.hospitalized;
	}

	public void setHospitalized(String hospitalized) {
		this.hospitalized = hospitalized;
	}

	public String getLabPayment() {
		return this.labPayment;
	}

	public void setLabPayment(String labPayment) {
		this.labPayment = labPayment;
	}

	public String getMedicineTouchtime() {
		return this.medicineTouchtime;
	}

	public void setMedicineTouchtime(String medicineTouchtime) {
		this.medicineTouchtime = medicineTouchtime;
	}

	public Integer getModifyCount() {
		return this.modifyCount;
	}

	public void setModifyCount(Integer modifyCount) {
		this.modifyCount = modifyCount;
	}

	public int getPNo() {
		return this.pNo;
	}

	public void setPNo(int pNo) {
		this.pNo = pNo;
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Integer getPharmacyNo() {
		return this.pharmacyNo;
	}

	public void setPharmacyNo(Integer pharmacyNo) {
		this.pharmacyNo = pharmacyNo;
	}

	public String getPharmacyPayment() {
		return this.pharmacyPayment;
	}

	public void setPharmacyPayment(String pharmacyPayment) {
		this.pharmacyPayment = pharmacyPayment;
	}

	public String getRadiologyPayment() {
		return this.radiologyPayment;
	}

	public void setRadiologyPayment(String radiologyPayment) {
		this.radiologyPayment = radiologyPayment;
	}

	public String getRecordTouchtime() {
		return this.recordTouchtime;
	}

	public void setRecordTouchtime(String recordTouchtime) {
		this.recordTouchtime = recordTouchtime;
	}

	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getRegister() {
		return this.register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getShiftGuid() {
		return this.shiftGuid;
	}

	public void setShiftGuid(String shiftGuid) {
		this.shiftGuid = shiftGuid;
	}

	public String getTouchtime() {
		return this.touchtime;
	}

	public void setTouchtime(String touchtime) {
		this.touchtime = touchtime;
	}

	public int getVisitsNo() {
		return this.visitsNo;
	}

	public void setVisitsNo(int visitsNo) {
		this.visitsNo = visitsNo;
	}

}