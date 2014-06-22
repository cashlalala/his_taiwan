package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the staff_info database table.
 * 
 */
@Entity
@Table(name="staff_info")
public class StaffInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	private int daysRemaining;

	@Column(name="days_taken")
	private int daysTaken;

	@Column(name="dep_guid")
	private String depGuid;

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

	@Column(name="gp_guid")
	private String gpGuid;

	@Column(name="grade_level")
	private String gradeLevel;

	@Column(name="groos_salary")
	private int groosSalary;

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

	@Column(name="poli_guid")
	private String poliGuid;

	@Column(name="posi_guid")
	private String posiGuid;

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

	private String type;

	private String website;

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

	public int getDaysRemaining() {
		return this.daysRemaining;
	}

	public void setDaysRemaining(int daysRemaining) {
		this.daysRemaining = daysRemaining;
	}

	public int getDaysTaken() {
		return this.daysTaken;
	}

	public void setDaysTaken(int daysTaken) {
		this.daysTaken = daysTaken;
	}

	public String getDepGuid() {
		return this.depGuid;
	}

	public void setDepGuid(String depGuid) {
		this.depGuid = depGuid;
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

	public String getGpGuid() {
		return this.gpGuid;
	}

	public void setGpGuid(String gpGuid) {
		this.gpGuid = gpGuid;
	}

	public String getGradeLevel() {
		return this.gradeLevel;
	}

	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public int getGroosSalary() {
		return this.groosSalary;
	}

	public void setGroosSalary(int groosSalary) {
		this.groosSalary = groosSalary;
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

	public String getPoliGuid() {
		return this.poliGuid;
	}

	public void setPoliGuid(String poliGuid) {
		this.poliGuid = poliGuid;
	}

	public String getPosiGuid() {
		return this.posiGuid;
	}

	public void setPosiGuid(String posiGuid) {
		this.posiGuid = posiGuid;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}