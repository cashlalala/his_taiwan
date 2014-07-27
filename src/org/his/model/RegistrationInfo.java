package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the registration_info database table.
 * 
 */
@Entity
@Table(name="registration_info")
public class RegistrationInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Column(name="bed_payment")
	private String bedPayment;

	@Column(name="case_finish")
	private String caseFinish;

	@Column(name="dia_cost")
	private double diaCost;

	@Column(name="diagnosis_payment")
	private String diagnosisPayment;

	private String finish;

	@Column(name="first_visit")
	private String firstVisit;

	private String hospitalID;

	@Column(name="lab_payment")
	private String labPayment;

	@Column(name="medicine_touchtime")
	private String medicineTouchtime;

	@Column(name="modify_count")
	private int modifyCount;

	@Column(name="pharmacy_no")
	private int pharmacyNo;

	@Column(name="pharmacy_payment")
	private String pharmacyPayment;

	@Column(name="radiology_payment")
	private String radiologyPayment;

	@Column(name="record_touchtime")
	private String recordTouchtime;

	@Column(name="reg_cost")
	private double regCost;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reg_time")
	private Date regTime;

	@Column(name="registration_payment")
	private String registrationPayment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="reserved_date")
	private Date reservedDate;

	private String touchtime;

	private String type;

	@Column(name="visits_no")
	private int visitsNo;

	//bi-directional many-to-one association to Complication
	@OneToMany(mappedBy="registrationInfo")
	private List<Complication> complications;

	//bi-directional many-to-one association to Diagnostic
	@OneToMany(mappedBy="registrationInfo")
	private List<Diagnostic> diagnostics;

	//bi-directional many-to-one association to Gi
	@OneToMany(mappedBy="registrationInfo")
	private List<Gi> gis;

	//bi-directional many-to-one association to HealthTeach
	@OneToMany(mappedBy="registrationInfo")
	private List<HealthTeach> healthTeaches;

	//bi-directional many-to-one association to MedicineStock
	@OneToMany(mappedBy="registrationInfo")
	private List<MedicineStock> medicineStocks;

	//bi-directional many-to-one association to OutpatientService
	@OneToMany(mappedBy="registrationInfo")
	private List<OutpatientService> outpatientServices;

	//bi-directional many-to-one association to Prescription
	@OneToMany(mappedBy="registrationInfo")
	private List<Prescription> prescriptions;

	//bi-directional many-to-one association to BedRecord
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bed_guid")
	private BedRecord bedRecord;

	//bi-directional many-to-one association to Gi
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gis_guid")
	private Gi gi;

	//bi-directional many-to-one association to PatientsInfo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="p_no")
	private PatientsInfo patientsInfo;

	//bi-directional many-to-one association to ShiftTable
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shift_guid")
	private ShiftTable shiftTable;

	public RegistrationInfo() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getBedPayment() {
		return this.bedPayment;
	}

	public void setBedPayment(String bedPayment) {
		this.bedPayment = bedPayment;
	}

	public String getCaseFinish() {
		return this.caseFinish;
	}

	public void setCaseFinish(String caseFinish) {
		this.caseFinish = caseFinish;
	}

	public double getDiaCost() {
		return this.diaCost;
	}

	public void setDiaCost(double diaCost) {
		this.diaCost = diaCost;
	}

	public String getDiagnosisPayment() {
		return this.diagnosisPayment;
	}

	public void setDiagnosisPayment(String diagnosisPayment) {
		this.diagnosisPayment = diagnosisPayment;
	}

	public String getFinish() {
		return this.finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public String getFirstVisit() {
		return this.firstVisit;
	}

	public void setFirstVisit(String firstVisit) {
		this.firstVisit = firstVisit;
	}

	public String getHospitalID() {
		return this.hospitalID;
	}

	public void setHospitalID(String hospitalID) {
		this.hospitalID = hospitalID;
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

	public int getModifyCount() {
		return this.modifyCount;
	}

	public void setModifyCount(int modifyCount) {
		this.modifyCount = modifyCount;
	}

	public int getPharmacyNo() {
		return this.pharmacyNo;
	}

	public void setPharmacyNo(int pharmacyNo) {
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

	public double getRegCost() {
		return this.regCost;
	}

	public void setRegCost(double regCost) {
		this.regCost = regCost;
	}

	public Date getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getRegistrationPayment() {
		return this.registrationPayment;
	}

	public void setRegistrationPayment(String registrationPayment) {
		this.registrationPayment = registrationPayment;
	}

	public Date getReservedDate() {
		return this.reservedDate;
	}

	public void setReservedDate(Date reservedDate) {
		this.reservedDate = reservedDate;
	}

	public String getTouchtime() {
		return this.touchtime;
	}

	public void setTouchtime(String touchtime) {
		this.touchtime = touchtime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getVisitsNo() {
		return this.visitsNo;
	}

	public void setVisitsNo(int visitsNo) {
		this.visitsNo = visitsNo;
	}

	public List<Complication> getComplications() {
		return this.complications;
	}

	public void setComplications(List<Complication> complications) {
		this.complications = complications;
	}

	public Complication addComplication(Complication complication) {
		getComplications().add(complication);
		complication.setRegistrationInfo(this);

		return complication;
	}

	public Complication removeComplication(Complication complication) {
		getComplications().remove(complication);
		complication.setRegistrationInfo(null);

		return complication;
	}

	public List<Diagnostic> getDiagnostics() {
		return this.diagnostics;
	}

	public void setDiagnostics(List<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public Diagnostic addDiagnostic(Diagnostic diagnostic) {
		getDiagnostics().add(diagnostic);
		diagnostic.setRegistrationInfo(this);

		return diagnostic;
	}

	public Diagnostic removeDiagnostic(Diagnostic diagnostic) {
		getDiagnostics().remove(diagnostic);
		diagnostic.setRegistrationInfo(null);

		return diagnostic;
	}

	public List<Gi> getGis() {
		return this.gis;
	}

	public void setGis(List<Gi> gis) {
		this.gis = gis;
	}

	public Gi addGi(Gi gi) {
		getGis().add(gi);
		gi.setRegistrationInfo(this);

		return gi;
	}

	public Gi removeGi(Gi gi) {
		getGis().remove(gi);
		gi.setRegistrationInfo(null);

		return gi;
	}

	public List<HealthTeach> getHealthTeaches() {
		return this.healthTeaches;
	}

	public void setHealthTeaches(List<HealthTeach> healthTeaches) {
		this.healthTeaches = healthTeaches;
	}

	public HealthTeach addHealthTeach(HealthTeach healthTeach) {
		getHealthTeaches().add(healthTeach);
		healthTeach.setRegistrationInfo(this);

		return healthTeach;
	}

	public HealthTeach removeHealthTeach(HealthTeach healthTeach) {
		getHealthTeaches().remove(healthTeach);
		healthTeach.setRegistrationInfo(null);

		return healthTeach;
	}

	public List<MedicineStock> getMedicineStocks() {
		return this.medicineStocks;
	}

	public void setMedicineStocks(List<MedicineStock> medicineStocks) {
		this.medicineStocks = medicineStocks;
	}

	public MedicineStock addMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().add(medicineStock);
		medicineStock.setRegistrationInfo(this);

		return medicineStock;
	}

	public MedicineStock removeMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().remove(medicineStock);
		medicineStock.setRegistrationInfo(null);

		return medicineStock;
	}

	public List<OutpatientService> getOutpatientServices() {
		return this.outpatientServices;
	}

	public void setOutpatientServices(List<OutpatientService> outpatientServices) {
		this.outpatientServices = outpatientServices;
	}

	public OutpatientService addOutpatientService(OutpatientService outpatientService) {
		getOutpatientServices().add(outpatientService);
		outpatientService.setRegistrationInfo(this);

		return outpatientService;
	}

	public OutpatientService removeOutpatientService(OutpatientService outpatientService) {
		getOutpatientServices().remove(outpatientService);
		outpatientService.setRegistrationInfo(null);

		return outpatientService;
	}

	public List<Prescription> getPrescriptions() {
		return this.prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Prescription addPrescription(Prescription prescription) {
		getPrescriptions().add(prescription);
		prescription.setRegistrationInfo(this);

		return prescription;
	}

	public Prescription removePrescription(Prescription prescription) {
		getPrescriptions().remove(prescription);
		prescription.setRegistrationInfo(null);

		return prescription;
	}

	public BedRecord getBedRecord() {
		return this.bedRecord;
	}

	public void setBedRecord(BedRecord bedRecord) {
		this.bedRecord = bedRecord;
	}

	public Gi getGi() {
		return this.gi;
	}

	public void setGi(Gi gi) {
		this.gi = gi;
	}

	public PatientsInfo getPatientsInfo() {
		return this.patientsInfo;
	}

	public void setPatientsInfo(PatientsInfo patientsInfo) {
		this.patientsInfo = patientsInfo;
	}

	public ShiftTable getShiftTable() {
		return this.shiftTable;
	}

	public void setShiftTable(ShiftTable shiftTable) {
		this.shiftTable = shiftTable;
	}

}