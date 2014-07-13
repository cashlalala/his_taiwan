package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the staff_info database table.
 * 
 */
@Entity
@Table(name="staff_info")
public class StaffInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="s_no")
	private int sNo;

	@Lob
	private String address;

	private String bloodgroup;

	@Column(name="category_post")
	private String categoryPost;

	private String cellphone;

	private String commitment;

	@Column(name="course_name")
	private String courseName;

	@Temporal(TemporalType.DATE)
	@Column(name="date_birth")
	private Date dateBirth;

	@Temporal(TemporalType.DATE)
	@Column(name="date_dismissal")
	private Date dateDismissal;

	@Temporal(TemporalType.DATE)
	@Column(name="date_resumption")
	private Date dateResumption;

	@Temporal(TemporalType.DATE)
	@Column(name="date_retirement")
	private Date dateRetirement;

	@Temporal(TemporalType.DATE)
	@Column(name="date_suspension")
	private Date dateSuspension;

	@Column(name="days_remaining")
	private Integer daysRemaining;

	@Column(name="days_taken")
	private Integer daysTaken;

	@Column(name="disability_description")
	private String disabilityDescription;

	@Column(name="disability_status")
	private String disabilityStatus;

	@Lob
	@Column(name="dismissal_reason")
	private String dismissalReason;

	private String email;

	@Column(name="employee_status")
	private String employeeStatus;

	@Temporal(TemporalType.DATE)
	private Date enddate;

	private String entitlement;

	private byte exist;

	@Column(name="expected_qualification")
	private String expectedQualification;

	@Temporal(TemporalType.DATE)
	@Column(name="first_appointment_date")
	private Date firstAppointmentDate;

	@Column(name="first_appointment_grade")
	private String firstAppointmentGrade;

	@Column(name="first_appointment_stafftype")
	private String firstAppointmentStafftype;

	private String firstname;

	@Column(name="grade_level")
	private String gradeLevel;

	@Column(name="groos_salary")
	private Integer groosSalary;

	@Column(name="grp_name")
	private String grpName;

	@Column(name="hss_no")
	private String hssNo;

	@Column(name="institution_name")
	private String institutionName;

	@Lob
	@Column(name="kin_address")
	private String kinAddress;

	@Column(name="kin_cellphone")
	private String kinCellphone;

	@Column(name="kin_email")
	private String kinEmail;

	@Column(name="kin_firstname")
	private String kinFirstname;

	@Column(name="kin_lastname")
	private String kinLastname;

	@Column(name="kin_phone")
	private String kinPhone;

	@Lob
	@Column(name="kin_postal")
	private String kinPostal;

	@Temporal(TemporalType.DATE)
	@Column(name="last_promotion_date")
	private Date lastPromotionDate;

	private String lastname;

	private String level;

	@Column(name="marital_status")
	private String maritalStatus;

	@Column(name="nhis_no")
	private String nhisNo;

	@Column(name="nia_no")
	private String niaNo;

	private byte online;

	@Column(name="online_guid")
	private String onlineGuid;

	@Column(name="passport_no")
	private String passportNo;

	private String passwd;

	@Temporal(TemporalType.DATE)
	@Column(name="period_enddate")
	private Date periodEnddate;

	@Temporal(TemporalType.DATE)
	@Column(name="period_startdate")
	private Date periodStartdate;

	private String phone;

	@Column(name="place_birth")
	private String placeBirth;

	@Lob
	private String postal;

	@Temporal(TemporalType.DATE)
	@Column(name="resumption_date")
	private Date resumptionDate;

	@Column(name="rh_type")
	private String rhType;

	@Column(name="s_id")
	private String sId;

	private String sex;

	private String sponsorship;

	@Temporal(TemporalType.DATE)
	@Column(name="spouse_date_birth")
	private Date spouseDateBirth;

	@Column(name="spouse_firstname")
	private String spouseFirstname;

	@Column(name="spouse_lastname")
	private String spouseLastname;

	@Column(name="spouse_nhis_no")
	private String spouseNhisNo;

	@Column(name="ss_no")
	private String ssNo;

	@Column(name="staff_category")
	private String staffCategory;

	@Column(name="staff_type")
	private String staffType;

	@Temporal(TemporalType.DATE)
	private Date startdate;

	private String status;

	private String step;

	private String surname;

	@Lob
	@Column(name="suspension_reason")
	private String suspensionReason;

	@Column(name="training_country")
	private String trainingCountry;

	@Column(name="training_type")
	private String trainingType;

	@Temporal(TemporalType.DATE)
	@Column(name="transfer_date")
	private Date transferDate;

	private String website;

	//bi-directional many-to-one association to Administrative
	@OneToMany(mappedBy="staffInfo")
	private List<Administrative> administratives;

	//bi-directional many-to-one association to Allergy
	@OneToMany(mappedBy="staffInfo")
	private List<Allergy> allergies;

	//bi-directional many-to-one association to AnamnesisRetrieve
	@OneToMany(mappedBy="staffInfo")
	private List<AnamnesisRetrieve> anamnesisRetrieves;

	//bi-directional many-to-one association to BedRecord
	@OneToMany(mappedBy="staffInfo1")
	private List<BedRecord> bedRecords1;

	//bi-directional many-to-one association to BedRecord
	@OneToMany(mappedBy="staffInfo2")
	private List<BedRecord> bedRecords2;

	//bi-directional many-to-one association to BedRecord
	@OneToMany(mappedBy="staffInfo3")
	private List<BedRecord> bedRecords3;

	//bi-directional many-to-one association to ChangesLog
	@OneToMany(mappedBy="staffInfo")
	private List<ChangesLog> changesLogs;

	//bi-directional many-to-one association to Children
	@OneToMany(mappedBy="staffInfo")
	private List<Children> childrens;

	//bi-directional many-to-one association to Clinical
	@OneToMany(mappedBy="staffInfo")
	private List<Clinical> clinicals;

	//bi-directional many-to-one association to EmploymentHistory
	@OneToMany(mappedBy="staffInfo")
	private List<EmploymentHistory> employmentHistories;

	//bi-directional many-to-one association to HealthTeach
	@OneToMany(mappedBy="staffInfo")
	private List<HealthTeach> healthTeaches;

	//bi-directional many-to-one association to LoginLog
	@OneToMany(mappedBy="staffInfo")
	private List<LoginLog> loginLogs;

	//bi-directional many-to-one association to MedicalStockChangeRecord
	@OneToMany(mappedBy="staffInfo")
	private List<MedicalStockChangeRecord> medicalStockChangeRecords;

	//bi-directional many-to-one association to MedicineFavorite
	@OneToMany(mappedBy="staffInfo")
	private List<MedicineFavorite> medicineFavorites;

	//bi-directional many-to-one association to MedicineStock
	@OneToMany(mappedBy="staffInfo")
	private List<MedicineStock> medicineStocks;

	//bi-directional many-to-one association to PatientsInfo
	@OneToMany(mappedBy="staffInfo")
	private List<PatientsInfo> patientsInfos;

	//bi-directional many-to-one association to ShiftTable
	@OneToMany(mappedBy="staffInfo")
	private List<ShiftTable> shiftTables;

	//bi-directional many-to-one association to StaffFingertemplate
	@OneToMany(mappedBy="staffInfo")
	private List<StaffFingertemplate> staffFingertemplates;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="dep_guid")
	private Department department;

	//bi-directional many-to-one association to Policlinic
	@ManyToOne
	@JoinColumn(name="poli_guid")
	private Policlinic policlinic;

	//bi-directional many-to-one association to Position
	@ManyToOne
	@JoinColumn(name="posi_guid")
	private Position position;

	public StaffInfo() {
	}

	public int getSNo() {
		return this.sNo;
	}

	public void setSNo(int sNo) {
		this.sNo = sNo;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBloodgroup() {
		return this.bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getCategoryPost() {
		return this.categoryPost;
	}

	public void setCategoryPost(String categoryPost) {
		this.categoryPost = categoryPost;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCommitment() {
		return this.commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getDateBirth() {
		return this.dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public Date getDateDismissal() {
		return this.dateDismissal;
	}

	public void setDateDismissal(Date dateDismissal) {
		this.dateDismissal = dateDismissal;
	}

	public Date getDateResumption() {
		return this.dateResumption;
	}

	public void setDateResumption(Date dateResumption) {
		this.dateResumption = dateResumption;
	}

	public Date getDateRetirement() {
		return this.dateRetirement;
	}

	public void setDateRetirement(Date dateRetirement) {
		this.dateRetirement = dateRetirement;
	}

	public Date getDateSuspension() {
		return this.dateSuspension;
	}

	public void setDateSuspension(Date dateSuspension) {
		this.dateSuspension = dateSuspension;
	}

	public Integer getDaysRemaining() {
		return this.daysRemaining;
	}

	public void setDaysRemaining(Integer daysRemaining) {
		this.daysRemaining = daysRemaining;
	}

	public Integer getDaysTaken() {
		return this.daysTaken;
	}

	public void setDaysTaken(Integer daysTaken) {
		this.daysTaken = daysTaken;
	}

	public String getDisabilityDescription() {
		return this.disabilityDescription;
	}

	public void setDisabilityDescription(String disabilityDescription) {
		this.disabilityDescription = disabilityDescription;
	}

	public String getDisabilityStatus() {
		return this.disabilityStatus;
	}

	public void setDisabilityStatus(String disabilityStatus) {
		this.disabilityStatus = disabilityStatus;
	}

	public String getDismissalReason() {
		return this.dismissalReason;
	}

	public void setDismissalReason(String dismissalReason) {
		this.dismissalReason = dismissalReason;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeStatus() {
		return this.employeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getEntitlement() {
		return this.entitlement;
	}

	public void setEntitlement(String entitlement) {
		this.entitlement = entitlement;
	}

	public byte getExist() {
		return this.exist;
	}

	public void setExist(byte exist) {
		this.exist = exist;
	}

	public String getExpectedQualification() {
		return this.expectedQualification;
	}

	public void setExpectedQualification(String expectedQualification) {
		this.expectedQualification = expectedQualification;
	}

	public Date getFirstAppointmentDate() {
		return this.firstAppointmentDate;
	}

	public void setFirstAppointmentDate(Date firstAppointmentDate) {
		this.firstAppointmentDate = firstAppointmentDate;
	}

	public String getFirstAppointmentGrade() {
		return this.firstAppointmentGrade;
	}

	public void setFirstAppointmentGrade(String firstAppointmentGrade) {
		this.firstAppointmentGrade = firstAppointmentGrade;
	}

	public String getFirstAppointmentStafftype() {
		return this.firstAppointmentStafftype;
	}

	public void setFirstAppointmentStafftype(String firstAppointmentStafftype) {
		this.firstAppointmentStafftype = firstAppointmentStafftype;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGradeLevel() {
		return this.gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public Integer getGroosSalary() {
		return this.groosSalary;
	}

	public void setGroosSalary(Integer groosSalary) {
		this.groosSalary = groosSalary;
	}

	public String getGrpName() {
		return this.grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getHssNo() {
		return this.hssNo;
	}

	public void setHssNo(String hssNo) {
		this.hssNo = hssNo;
	}

	public String getInstitutionName() {
		return this.institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getKinAddress() {
		return this.kinAddress;
	}

	public void setKinAddress(String kinAddress) {
		this.kinAddress = kinAddress;
	}

	public String getKinCellphone() {
		return this.kinCellphone;
	}

	public void setKinCellphone(String kinCellphone) {
		this.kinCellphone = kinCellphone;
	}

	public String getKinEmail() {
		return this.kinEmail;
	}

	public void setKinEmail(String kinEmail) {
		this.kinEmail = kinEmail;
	}

	public String getKinFirstname() {
		return this.kinFirstname;
	}

	public void setKinFirstname(String kinFirstname) {
		this.kinFirstname = kinFirstname;
	}

	public String getKinLastname() {
		return this.kinLastname;
	}

	public void setKinLastname(String kinLastname) {
		this.kinLastname = kinLastname;
	}

	public String getKinPhone() {
		return this.kinPhone;
	}

	public void setKinPhone(String kinPhone) {
		this.kinPhone = kinPhone;
	}

	public String getKinPostal() {
		return this.kinPostal;
	}

	public void setKinPostal(String kinPostal) {
		this.kinPostal = kinPostal;
	}

	public Date getLastPromotionDate() {
		return this.lastPromotionDate;
	}

	public void setLastPromotionDate(Date lastPromotionDate) {
		this.lastPromotionDate = lastPromotionDate;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNhisNo() {
		return this.nhisNo;
	}

	public void setNhisNo(String nhisNo) {
		this.nhisNo = nhisNo;
	}

	public String getNiaNo() {
		return this.niaNo;
	}

	public void setNiaNo(String niaNo) {
		this.niaNo = niaNo;
	}

	public byte getOnline() {
		return this.online;
	}

	public void setOnline(byte online) {
		this.online = online;
	}

	public String getOnlineGuid() {
		return this.onlineGuid;
	}

	public void setOnlineGuid(String onlineGuid) {
		this.onlineGuid = onlineGuid;
	}

	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Date getPeriodEnddate() {
		return this.periodEnddate;
	}

	public void setPeriodEnddate(Date periodEnddate) {
		this.periodEnddate = periodEnddate;
	}

	public Date getPeriodStartdate() {
		return this.periodStartdate;
	}

	public void setPeriodStartdate(Date periodStartdate) {
		this.periodStartdate = periodStartdate;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPlaceBirth() {
		return this.placeBirth;
	}

	public void setPlaceBirth(String placeBirth) {
		this.placeBirth = placeBirth;
	}

	public String getPostal() {
		return this.postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public Date getResumptionDate() {
		return this.resumptionDate;
	}

	public void setResumptionDate(Date resumptionDate) {
		this.resumptionDate = resumptionDate;
	}

	public String getRhType() {
		return this.rhType;
	}

	public void setRhType(String rhType) {
		this.rhType = rhType;
	}

	public String getSId() {
		return this.sId;
	}

	public void setSId(String sId) {
		this.sId = sId;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSponsorship() {
		return this.sponsorship;
	}

	public void setSponsorship(String sponsorship) {
		this.sponsorship = sponsorship;
	}

	public Date getSpouseDateBirth() {
		return this.spouseDateBirth;
	}

	public void setSpouseDateBirth(Date spouseDateBirth) {
		this.spouseDateBirth = spouseDateBirth;
	}

	public String getSpouseFirstname() {
		return this.spouseFirstname;
	}

	public void setSpouseFirstname(String spouseFirstname) {
		this.spouseFirstname = spouseFirstname;
	}

	public String getSpouseLastname() {
		return this.spouseLastname;
	}

	public void setSpouseLastname(String spouseLastname) {
		this.spouseLastname = spouseLastname;
	}

	public String getSpouseNhisNo() {
		return this.spouseNhisNo;
	}

	public void setSpouseNhisNo(String spouseNhisNo) {
		this.spouseNhisNo = spouseNhisNo;
	}

	public String getSsNo() {
		return this.ssNo;
	}

	public void setSsNo(String ssNo) {
		this.ssNo = ssNo;
	}

	public String getStaffCategory() {
		return this.staffCategory;
	}

	public void setStaffCategory(String staffCategory) {
		this.staffCategory = staffCategory;
	}

	public String getStaffType() {
		return this.staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStep() {
		return this.step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSuspensionReason() {
		return this.suspensionReason;
	}

	public void setSuspensionReason(String suspensionReason) {
		this.suspensionReason = suspensionReason;
	}

	public String getTrainingCountry() {
		return this.trainingCountry;
	}

	public void setTrainingCountry(String trainingCountry) {
		this.trainingCountry = trainingCountry;
	}

	public String getTrainingType() {
		return this.trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public Date getTransferDate() {
		return this.transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<Administrative> getAdministratives() {
		return this.administratives;
	}

	public void setAdministratives(List<Administrative> administratives) {
		this.administratives = administratives;
	}

	public Administrative addAdministrative(Administrative administrative) {
		getAdministratives().add(administrative);
		administrative.setStaffInfo(this);

		return administrative;
	}

	public Administrative removeAdministrative(Administrative administrative) {
		getAdministratives().remove(administrative);
		administrative.setStaffInfo(null);

		return administrative;
	}

	public List<Allergy> getAllergies() {
		return this.allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public Allergy addAllergy(Allergy allergy) {
		getAllergies().add(allergy);
		allergy.setStaffInfo(this);

		return allergy;
	}

	public Allergy removeAllergy(Allergy allergy) {
		getAllergies().remove(allergy);
		allergy.setStaffInfo(null);

		return allergy;
	}

	public List<AnamnesisRetrieve> getAnamnesisRetrieves() {
		return this.anamnesisRetrieves;
	}

	public void setAnamnesisRetrieves(List<AnamnesisRetrieve> anamnesisRetrieves) {
		this.anamnesisRetrieves = anamnesisRetrieves;
	}

	public AnamnesisRetrieve addAnamnesisRetrieve(AnamnesisRetrieve anamnesisRetrieve) {
		getAnamnesisRetrieves().add(anamnesisRetrieve);
		anamnesisRetrieve.setStaffInfo(this);

		return anamnesisRetrieve;
	}

	public AnamnesisRetrieve removeAnamnesisRetrieve(AnamnesisRetrieve anamnesisRetrieve) {
		getAnamnesisRetrieves().remove(anamnesisRetrieve);
		anamnesisRetrieve.setStaffInfo(null);

		return anamnesisRetrieve;
	}

	public List<BedRecord> getBedRecords1() {
		return this.bedRecords1;
	}

	public void setBedRecords1(List<BedRecord> bedRecords1) {
		this.bedRecords1 = bedRecords1;
	}

	public BedRecord addBedRecords1(BedRecord bedRecords1) {
		getBedRecords1().add(bedRecords1);
		bedRecords1.setStaffInfo1(this);

		return bedRecords1;
	}

	public BedRecord removeBedRecords1(BedRecord bedRecords1) {
		getBedRecords1().remove(bedRecords1);
		bedRecords1.setStaffInfo1(null);

		return bedRecords1;
	}

	public List<BedRecord> getBedRecords2() {
		return this.bedRecords2;
	}

	public void setBedRecords2(List<BedRecord> bedRecords2) {
		this.bedRecords2 = bedRecords2;
	}

	public BedRecord addBedRecords2(BedRecord bedRecords2) {
		getBedRecords2().add(bedRecords2);
		bedRecords2.setStaffInfo2(this);

		return bedRecords2;
	}

	public BedRecord removeBedRecords2(BedRecord bedRecords2) {
		getBedRecords2().remove(bedRecords2);
		bedRecords2.setStaffInfo2(null);

		return bedRecords2;
	}

	public List<BedRecord> getBedRecords3() {
		return this.bedRecords3;
	}

	public void setBedRecords3(List<BedRecord> bedRecords3) {
		this.bedRecords3 = bedRecords3;
	}

	public BedRecord addBedRecords3(BedRecord bedRecords3) {
		getBedRecords3().add(bedRecords3);
		bedRecords3.setStaffInfo3(this);

		return bedRecords3;
	}

	public BedRecord removeBedRecords3(BedRecord bedRecords3) {
		getBedRecords3().remove(bedRecords3);
		bedRecords3.setStaffInfo3(null);

		return bedRecords3;
	}

	public List<ChangesLog> getChangesLogs() {
		return this.changesLogs;
	}

	public void setChangesLogs(List<ChangesLog> changesLogs) {
		this.changesLogs = changesLogs;
	}

	public ChangesLog addChangesLog(ChangesLog changesLog) {
		getChangesLogs().add(changesLog);
		changesLog.setStaffInfo(this);

		return changesLog;
	}

	public ChangesLog removeChangesLog(ChangesLog changesLog) {
		getChangesLogs().remove(changesLog);
		changesLog.setStaffInfo(null);

		return changesLog;
	}

	public List<Children> getChildrens() {
		return this.childrens;
	}

	public void setChildrens(List<Children> childrens) {
		this.childrens = childrens;
	}

	public Children addChildren(Children children) {
		getChildrens().add(children);
		children.setStaffInfo(this);

		return children;
	}

	public Children removeChildren(Children children) {
		getChildrens().remove(children);
		children.setStaffInfo(null);

		return children;
	}

	public List<Clinical> getClinicals() {
		return this.clinicals;
	}

	public void setClinicals(List<Clinical> clinicals) {
		this.clinicals = clinicals;
	}

	public Clinical addClinical(Clinical clinical) {
		getClinicals().add(clinical);
		clinical.setStaffInfo(this);

		return clinical;
	}

	public Clinical removeClinical(Clinical clinical) {
		getClinicals().remove(clinical);
		clinical.setStaffInfo(null);

		return clinical;
	}

	public List<EmploymentHistory> getEmploymentHistories() {
		return this.employmentHistories;
	}

	public void setEmploymentHistories(List<EmploymentHistory> employmentHistories) {
		this.employmentHistories = employmentHistories;
	}

	public EmploymentHistory addEmploymentHistory(EmploymentHistory employmentHistory) {
		getEmploymentHistories().add(employmentHistory);
		employmentHistory.setStaffInfo(this);

		return employmentHistory;
	}

	public EmploymentHistory removeEmploymentHistory(EmploymentHistory employmentHistory) {
		getEmploymentHistories().remove(employmentHistory);
		employmentHistory.setStaffInfo(null);

		return employmentHistory;
	}

	public List<HealthTeach> getHealthTeaches() {
		return this.healthTeaches;
	}

	public void setHealthTeaches(List<HealthTeach> healthTeaches) {
		this.healthTeaches = healthTeaches;
	}

	public HealthTeach addHealthTeach(HealthTeach healthTeach) {
		getHealthTeaches().add(healthTeach);
		healthTeach.setStaffInfo(this);

		return healthTeach;
	}

	public HealthTeach removeHealthTeach(HealthTeach healthTeach) {
		getHealthTeaches().remove(healthTeach);
		healthTeach.setStaffInfo(null);

		return healthTeach;
	}

	public List<LoginLog> getLoginLogs() {
		return this.loginLogs;
	}

	public void setLoginLogs(List<LoginLog> loginLogs) {
		this.loginLogs = loginLogs;
	}

	public LoginLog addLoginLog(LoginLog loginLog) {
		getLoginLogs().add(loginLog);
		loginLog.setStaffInfo(this);

		return loginLog;
	}

	public LoginLog removeLoginLog(LoginLog loginLog) {
		getLoginLogs().remove(loginLog);
		loginLog.setStaffInfo(null);

		return loginLog;
	}

	public List<MedicalStockChangeRecord> getMedicalStockChangeRecords() {
		return this.medicalStockChangeRecords;
	}

	public void setMedicalStockChangeRecords(List<MedicalStockChangeRecord> medicalStockChangeRecords) {
		this.medicalStockChangeRecords = medicalStockChangeRecords;
	}

	public MedicalStockChangeRecord addMedicalStockChangeRecord(MedicalStockChangeRecord medicalStockChangeRecord) {
		getMedicalStockChangeRecords().add(medicalStockChangeRecord);
		medicalStockChangeRecord.setStaffInfo(this);

		return medicalStockChangeRecord;
	}

	public MedicalStockChangeRecord removeMedicalStockChangeRecord(MedicalStockChangeRecord medicalStockChangeRecord) {
		getMedicalStockChangeRecords().remove(medicalStockChangeRecord);
		medicalStockChangeRecord.setStaffInfo(null);

		return medicalStockChangeRecord;
	}

	public List<MedicineFavorite> getMedicineFavorites() {
		return this.medicineFavorites;
	}

	public void setMedicineFavorites(List<MedicineFavorite> medicineFavorites) {
		this.medicineFavorites = medicineFavorites;
	}

	public MedicineFavorite addMedicineFavorite(MedicineFavorite medicineFavorite) {
		getMedicineFavorites().add(medicineFavorite);
		medicineFavorite.setStaffInfo(this);

		return medicineFavorite;
	}

	public MedicineFavorite removeMedicineFavorite(MedicineFavorite medicineFavorite) {
		getMedicineFavorites().remove(medicineFavorite);
		medicineFavorite.setStaffInfo(null);

		return medicineFavorite;
	}

	public List<MedicineStock> getMedicineStocks() {
		return this.medicineStocks;
	}

	public void setMedicineStocks(List<MedicineStock> medicineStocks) {
		this.medicineStocks = medicineStocks;
	}

	public MedicineStock addMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().add(medicineStock);
		medicineStock.setStaffInfo(this);

		return medicineStock;
	}

	public MedicineStock removeMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().remove(medicineStock);
		medicineStock.setStaffInfo(null);

		return medicineStock;
	}

	public List<PatientsInfo> getPatientsInfos() {
		return this.patientsInfos;
	}

	public void setPatientsInfos(List<PatientsInfo> patientsInfos) {
		this.patientsInfos = patientsInfos;
	}

	public PatientsInfo addPatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().add(patientsInfo);
		patientsInfo.setStaffInfo(this);

		return patientsInfo;
	}

	public PatientsInfo removePatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().remove(patientsInfo);
		patientsInfo.setStaffInfo(null);

		return patientsInfo;
	}

	public List<ShiftTable> getShiftTables() {
		return this.shiftTables;
	}

	public void setShiftTables(List<ShiftTable> shiftTables) {
		this.shiftTables = shiftTables;
	}

	public ShiftTable addShiftTable(ShiftTable shiftTable) {
		getShiftTables().add(shiftTable);
		shiftTable.setStaffInfo(this);

		return shiftTable;
	}

	public ShiftTable removeShiftTable(ShiftTable shiftTable) {
		getShiftTables().remove(shiftTable);
		shiftTable.setStaffInfo(null);

		return shiftTable;
	}

	public List<StaffFingertemplate> getStaffFingertemplates() {
		return this.staffFingertemplates;
	}

	public void setStaffFingertemplates(List<StaffFingertemplate> staffFingertemplates) {
		this.staffFingertemplates = staffFingertemplates;
	}

	public StaffFingertemplate addStaffFingertemplate(StaffFingertemplate staffFingertemplate) {
		getStaffFingertemplates().add(staffFingertemplate);
		staffFingertemplate.setStaffInfo(this);

		return staffFingertemplate;
	}

	public StaffFingertemplate removeStaffFingertemplate(StaffFingertemplate staffFingertemplate) {
		getStaffFingertemplates().remove(staffFingertemplate);
		staffFingertemplate.setStaffInfo(null);

		return staffFingertemplate;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Policlinic getPoliclinic() {
		return this.policlinic;
	}

	public void setPoliclinic(Policlinic policlinic) {
		this.policlinic = policlinic;
	}

	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}