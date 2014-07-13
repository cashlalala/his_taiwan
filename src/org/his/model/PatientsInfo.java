package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the patients_info database table.
 * 
 */

@NamedQueries ({
    @NamedQuery(
        name="QueryExistedUsers",
        query="SELECT pinfo FROM PatientsInfo pinfo WHERE pinfo.exist = 1"),
    @NamedQuery(
        name="QueryPatientCount",
        query="SELECT COUNT(pinfo) FROM PatientsInfo pinfo WHERE pinfo.exist = 1"),
    @NamedQuery(
        name="DeleteAutoGenUser",
        query="DELETE FROM PatientsInfo p WHERE p.firstname = :uuid AND p.exist = 0")
})

@Entity
@Table(name = "patients_info")
public class PatientsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "p_no")
	private String pNo;

	@Column(name = "account_num")
	private String accountNum;

	private String address;

	private String alias;

	@Temporal(TemporalType.DATE)
	private Date birth;

	@Column(name = "birth_order")
	private Integer birthOrder;

	private String bloodtype;

	@Column(name = "business_phone")
	private String businessPhone;

	@Temporal(TemporalType.TIMESTAMP)
	private Date cdate;

	@Column(name = "cell_phone")
	private String cellPhone;

	private String citizenship;

	private String country;

	@Column(name = "driver_num")
	private String driverNum;

	private String education;

	private byte exist;

	private String firstname;

	private String gender;

	private String height;

	@Column(name = "identity_code")
	private String identityCode;

	@Column(name = "identity_unknown")
	private String identityUnknown;

	private String language;

	private String lastname;

	@Column(name = "marital_status")
	private String maritalStatus;

	@Column(name = "mother_id")
	private String motherId;

	@Column(name = "mother_name")
	private String motherName;

	@Column(name = "multiple_birth")
	private String multipleBirth;

	private String nationality;

	@Column(name = "nhis_no")
	private String nhisNo;

	@Column(name = "nia_no")
	private String niaNo;

	private String note;

	private String occupation;

	private String phone;

	@Column(name = "place_of_birth")
	private String placeOfBirth;

	private String ps;

	private String race;

	@Column(name = "rh_type")
	private String rhType;

	@Column(name = "ssn_num")
	private String ssnNum;

	private String state;

	private String town;

	@Column(name = "u_sno")
	private Integer uSno;

	@Temporal(TemporalType.TIMESTAMP)
	private Date udate;

	@Column(name = "veterans_military")
	private String veteransMilitary;

	private String weight;

	// bi-directional many-to-one association to Allergy
	@OneToMany(mappedBy = "patientsInfo")
	private List<Allergy> allergies;

	// bi-directional many-to-one association to Anamnesi
	@OneToMany(mappedBy = "patientsInfo")
	private List<Anamnesi> anamnesis;

	// bi-directional many-to-one association to BedRecord
	@OneToMany(mappedBy = "patientsInfo")
	private List<BedRecord> bedRecords;

	// bi-directional many-to-one association to Fingertemplate
	@OneToMany(mappedBy = "patientsInfo")
	private List<Fingertemplate> fingertemplates;

	// bi-directional many-to-one association to ImageMeta
	@OneToMany(mappedBy = "patientsInfo")
	private List<ImageMeta> imageMetas;

	// bi-directional many-to-one association to StaffInfo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "c_sno")
	private StaffInfo staffInfo;

	// bi-directional many-to-one association to Religion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "religion")
	private Religion religionBean;

	// bi-directional many-to-one association to HlsGroup
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tribe")
	private HlsGroup hlsGroup;

	// bi-directional many-to-one association to ContactpersonInfo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cp_guid")
	private ContactpersonInfo contactpersonInfo;

	// bi-directional many-to-one association to DeathInfo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dead_guid")
	private DeathInfo deathInfo;

	// bi-directional many-to-one association to RegistrationInfo
	@OneToMany(mappedBy = "patientsInfo", fetch = FetchType.LAZY)
	private List<RegistrationInfo> registrationInfos;

	public PatientsInfo() {
	}

	public String getPNo() {
		return this.pNo;
	}

	public void setPNo(String pNo) {
		this.pNo = pNo;
	}

	public String getAccountNum() {
		return this.accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Integer getBirthOrder() {
		return this.birthOrder;
	}

	public void setBirthOrder(Integer birthOrder) {
		this.birthOrder = birthOrder;
	}

	public String getBloodtype() {
		return this.bloodtype;
	}

	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	public String getBusinessPhone() {
		return this.businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getCitizenship() {
		return this.citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDriverNum() {
		return this.driverNum;
	}

	public void setDriverNum(String driverNum) {
		this.driverNum = driverNum;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public byte getExist() {
		return this.exist;
	}

	public void setExist(byte exist) {
		this.exist = exist;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getIdentityCode() {
		return this.identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public String getIdentityUnknown() {
		return this.identityUnknown;
	}

	public void setIdentityUnknown(String identityUnknown) {
		this.identityUnknown = identityUnknown;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMotherId() {
		return this.motherId;
	}

	public void setMotherId(String motherId) {
		this.motherId = motherId;
	}

	public String getMotherName() {
		return this.motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMultipleBirth() {
		return this.multipleBirth;
	}

	public void setMultipleBirth(String multipleBirth) {
		this.multipleBirth = multipleBirth;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPlaceOfBirth() {
		return this.placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getPs() {
		return this.ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getRace() {
		return this.race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getRhType() {
		return this.rhType;
	}

	public void setRhType(String rhType) {
		this.rhType = rhType;
	}

	public String getSsnNum() {
		return this.ssnNum;
	}

	public void setSsnNum(String ssnNum) {
		this.ssnNum = ssnNum;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public Integer getUSno() {
		return this.uSno;
	}

	public void setUSno(Integer uSno) {
		this.uSno = uSno;
	}

	public Date getUdate() {
		return this.udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

	public String getVeteransMilitary() {
		return this.veteransMilitary;
	}

	public void setVeteransMilitary(String veteransMilitary) {
		this.veteransMilitary = veteransMilitary;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public List<Allergy> getAllergies() {
		return this.allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public Allergy addAllergy(Allergy allergy) {
		getAllergies().add(allergy);
		allergy.setPatientsInfo(this);

		return allergy;
	}

	public Allergy removeAllergy(Allergy allergy) {
		getAllergies().remove(allergy);
		allergy.setPatientsInfo(null);

		return allergy;
	}

	public List<Anamnesi> getAnamnesis() {
		return this.anamnesis;
	}

	public void setAnamnesis(List<Anamnesi> anamnesis) {
		this.anamnesis = anamnesis;
	}

	public Anamnesi addAnamnesi(Anamnesi anamnesi) {
		getAnamnesis().add(anamnesi);
		anamnesi.setPatientsInfo(this);

		return anamnesi;
	}

	public Anamnesi removeAnamnesi(Anamnesi anamnesi) {
		getAnamnesis().remove(anamnesi);
		anamnesi.setPatientsInfo(null);

		return anamnesi;
	}

	public List<BedRecord> getBedRecords() {
		return this.bedRecords;
	}

	public void setBedRecords(List<BedRecord> bedRecords) {
		this.bedRecords = bedRecords;
	}

	public BedRecord addBedRecord(BedRecord bedRecord) {
		getBedRecords().add(bedRecord);
		bedRecord.setPatientsInfo(this);

		return bedRecord;
	}

	public BedRecord removeBedRecord(BedRecord bedRecord) {
		getBedRecords().remove(bedRecord);
		bedRecord.setPatientsInfo(null);

		return bedRecord;
	}

	public List<Fingertemplate> getFingertemplates() {
		return this.fingertemplates;
	}

	public void setFingertemplates(List<Fingertemplate> fingertemplates) {
		this.fingertemplates = fingertemplates;
	}

	public Fingertemplate addFingertemplate(Fingertemplate fingertemplate) {
		getFingertemplates().add(fingertemplate);
		fingertemplate.setPatientsInfo(this);

		return fingertemplate;
	}

	public Fingertemplate removeFingertemplate(Fingertemplate fingertemplate) {
		getFingertemplates().remove(fingertemplate);
		fingertemplate.setPatientsInfo(null);

		return fingertemplate;
	}

	public List<ImageMeta> getImageMetas() {
		return this.imageMetas;
	}

	public void setImageMetas(List<ImageMeta> imageMetas) {
		this.imageMetas = imageMetas;
	}

	public ImageMeta addImageMeta(ImageMeta imageMeta) {
		getImageMetas().add(imageMeta);
		imageMeta.setPatientsInfo(this);

		return imageMeta;
	}

	public ImageMeta removeImageMeta(ImageMeta imageMeta) {
		getImageMetas().remove(imageMeta);
		imageMeta.setPatientsInfo(null);

		return imageMeta;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

	public Religion getReligionBean() {
		return this.religionBean;
	}

	public void setReligionBean(Religion religionBean) {
		this.religionBean = religionBean;
	}

	public HlsGroup getHlsGroup() {
		return this.hlsGroup;
	}

	public void setHlsGroup(HlsGroup hlsGroup) {
		this.hlsGroup = hlsGroup;
	}

	public ContactpersonInfo getContactpersonInfo() {
		return this.contactpersonInfo;
	}

	public void setContactpersonInfo(ContactpersonInfo contactpersonInfo) {
		this.contactpersonInfo = contactpersonInfo;
	}

	public DeathInfo getDeathInfo() {
		return this.deathInfo;
	}

	public void setDeathInfo(DeathInfo deathInfo) {
		this.deathInfo = deathInfo;
	}

	public List<RegistrationInfo> getRegistrationInfos() {
		return this.registrationInfos;
	}

	public void setRegistrationInfos(List<RegistrationInfo> registrationInfos) {
		this.registrationInfos = registrationInfos;
	}

	public RegistrationInfo addRegistrationInfo(
			RegistrationInfo registrationInfo) {
		getRegistrationInfos().add(registrationInfo);
		registrationInfo.setPatientsInfo(this);

		return registrationInfo;
	}

	public RegistrationInfo removeRegistrationInfo(
			RegistrationInfo registrationInfo) {
		getRegistrationInfos().remove(registrationInfo);
		registrationInfo.setPatientsInfo(null);

		return registrationInfo;
	}

}