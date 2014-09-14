package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the bed_record database table.
 * 
 */
@Entity
@Table(name="bed_record")
@NamedQuery(name="BedRecord.findAll", query="SELECT b FROM BedRecord b")
public class BedRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkinTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkoutTime;

	private float cost;

	private String note;

	private String status;

	//bi-directional many-to-one association to PatientsInfo
	@ManyToOne
	@JoinColumn(name="p_no")
	private PatientsInfo patientsInfo;

	//bi-directional many-to-one association to BedCode
	@ManyToOne
	@JoinColumn(name="bed_no")
	private BedCode bedCode;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="checkinStaff_no")
	private StaffInfo staffInfo1;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="mainDr_no")
	private StaffInfo staffInfo2;

	//bi-directional many-to-one association to Policlinic
	@ManyToOne
	@JoinColumn(name="actual_poli_guid")
	private Policlinic policlinic;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="mainNurse_no")
	private StaffInfo staffInfo3;

	//bi-directional many-to-one association to RegistrationInfo
	@OneToMany(mappedBy="bedRecord")
	private List<RegistrationInfo> registrationInfos;

	public BedRecord() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getCheckinTime() {
		return this.checkinTime;
	}

	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}

	public Date getCheckoutTime() {
		return this.checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public float getCost() {
		return this.cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PatientsInfo getPatientsInfo() {
		return this.patientsInfo;
	}

	public void setPatientsInfo(PatientsInfo patientsInfo) {
		this.patientsInfo = patientsInfo;
	}

	public BedCode getBedCode() {
		return this.bedCode;
	}

	public void setBedCode(BedCode bedCode) {
		this.bedCode = bedCode;
	}

	public StaffInfo getStaffInfo1() {
		return this.staffInfo1;
	}

	public void setStaffInfo1(StaffInfo staffInfo1) {
		this.staffInfo1 = staffInfo1;
	}

	public StaffInfo getStaffInfo2() {
		return this.staffInfo2;
	}

	public void setStaffInfo2(StaffInfo staffInfo2) {
		this.staffInfo2 = staffInfo2;
	}

	public Policlinic getPoliclinic() {
		return this.policlinic;
	}

	public void setPoliclinic(Policlinic policlinic) {
		this.policlinic = policlinic;
	}

	public StaffInfo getStaffInfo3() {
		return this.staffInfo3;
	}

	public void setStaffInfo3(StaffInfo staffInfo3) {
		this.staffInfo3 = staffInfo3;
	}

	public List<RegistrationInfo> getRegistrationInfos() {
		return this.registrationInfos;
	}

	public void setRegistrationInfos(List<RegistrationInfo> registrationInfos) {
		this.registrationInfos = registrationInfos;
	}

	public RegistrationInfo addRegistrationInfo(RegistrationInfo registrationInfo) {
		getRegistrationInfos().add(registrationInfo);
		registrationInfo.setBedRecord(this);

		return registrationInfo;
	}

	public RegistrationInfo removeRegistrationInfo(RegistrationInfo registrationInfo) {
		getRegistrationInfos().remove(registrationInfo);
		registrationInfo.setBedRecord(null);

		return registrationInfo;
	}

}