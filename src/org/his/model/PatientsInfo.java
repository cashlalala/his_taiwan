package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the patients_info database table.
 * 
 */

@NamedQueries ({
    @NamedQuery(
        name="QueryExistedUsers",
        query="SELECT pinfo FROM PatientsInfo pinfo WHERE pinfo.exist = 1"),
})

@Entity
@Table(name="patients_info")
public class PatientsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="p_no")
	private int pNo;

	@Column(name="account_num")
	private String accountNum;

	private String address;

	private String alias;

	@Temporal(TemporalType.DATE)
	private Date birth;

	@Column(name="birth_order")
	private Integer birthOrder;

	private String bloodtype;

	@Column(name="business_phone")
	private String businessPhone;

	@Column(name="c_sno")
	private Integer cSno;

	@Temporal(TemporalType.TIMESTAMP)
	private Date cdate;

	@Column(name="cell_phone")
	private String cellPhone;

	private String citizenship;

	private String country;

	@Column(name="cp_guid")
	private String cpGuid;

	@Column(name="dead_guid")
	private String deadGuid;

	@Column(name="driver_num")
	private String driverNum;

	private String education;

	private byte exist;

	private String firstname;

	private String gender;

	private String height;

	@Column(name="identity_code")
	private String identityCode;

	@Column(name="identity_unknown")
	private String identityUnknown;

	private String language;

	private String lastname;

	@Column(name="marital_status")
	private String maritalStatus;

	@Column(name="mother_id")
	private String motherId;

	@Column(name="mother_name")
	private String motherName;

	@Column(name="multiple_birth")
	private String multipleBirth;

	private String nationality;

	@Column(name="nhis_no")
	private String nhisNo;

	@Column(name="nia_no")
	private String niaNo;

	private String occupation;

	private String phone;

	@Column(name="place_of_birth")
	private String placeOfBirth;

	private String ps;

	private String race;

	private String religion;

	@Column(name="rh_type")
	private String rhType;

	@Column(name="ssn_num")
	private String ssnNum;

	private String state;

	private String town;

	private String tribe;

	@Column(name="u_sno")
	private Integer uSno;

	@Temporal(TemporalType.TIMESTAMP)
	private Date udate;

	@Column(name="veterans_military")
	private String veteransMilitary;

	private String weight;

	public PatientsInfo() {
	}

	public int getPNo() {
		return this.pNo;
	}

	public void setPNo(int pNo) {
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

	public int getBirthOrder() {
		return this.birthOrder;
	}

	public void setBirthOrder(int birthOrder) {
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

	public int getCSno() {
		return this.cSno;
	}

	public void setCSno(int cSno) {
		this.cSno = cSno;
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

	public String getCpGuid() {
		return this.cpGuid;
	}

	public void setCpGuid(String cpGuid) {
		this.cpGuid = cpGuid;
	}

	public String getDeadGuid() {
		return this.deadGuid;
	}

	public void setDeadGuid(String deadGuid) {
		this.deadGuid = deadGuid;
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

	public String getReligion() {
		return this.religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
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

	public String getTribe() {
		return this.tribe;
	}

	public void setTribe(String tribe) {
		this.tribe = tribe;
	}

	public int getUSno() {
		return this.uSno;
	}

	public void setUSno(int uSno) {
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

}