package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the asscement database table.
 * 
 */
@Entity
@Table(name="asscement")
public class Asscement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Column(name="abortions_count")
	private int abortionsCount;

	private String acupuncture;

	@Column(name="advanced_dm_eyedisease")
	private String advancedDmEyedisease;

	@Column(name="bloodtest_aweek")
	private int bloodtestAweek;

	@Column(name="bypass_surgery")
	private String bypassSurgery;

	private String cataract;

	private double dbp;

	@Column(name="dm_type")
	private String dmType;

	@Column(name="dm_typeo")
	private String dmTypeo;

	@Temporal(TemporalType.DATE)
	@Column(name="dm_year")
	private Date dmYear;

	private String drink;

	@Column(name="drink_aweek")
	private int drinkAweek;

	private String education;

	@Column(name="eye_lvision")
	private double eyeLvision;

	@Column(name="eye_rvision")
	private double eyeRvision;

	@Column(name="family_history")
	private String familyHistory;

	@Column(name="fundus_check")
	private String fundusCheck;

	private String gestation;

	@Column(name="gestation_count")
	private int gestationCount;

	private String insulin;

	@Temporal(TemporalType.DATE)
	@Column(name="insulin_syear")
	private Date insulinSyear;

	@Column(name="light_coagulation")
	private String lightCoagulation;

	@Column(name="macular_degeneration")
	private String macularDegeneration;

	@Column(name="non_proliferative_retinopathy")
	private String nonProliferativeRetinopathy;

	@Column(name="oral_hypoglycemic")
	private String oralHypoglycemic;

	@Column(name="oral_syear")
	private String oralSyear;

	@Column(name="pre_proliferative_retinopathy")
	private String preProliferativeRetinopathy;

	@Column(name="proliferative_retinopathy")
	private String proliferativeRetinopathy;

	private String pulse;

	@Column(name="reg_guid")
	private String regGuid;

	@Column(name="retinal_check")
	private String retinalCheck;

	private double sbp;

	@Column(name="self_care")
	private String selfCare;

	private String smoke;

	@Column(name="smoke_aday")
	private int smokeAday;

	private String sport;

	@Column(name="u_sid")
	private String uSid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date udate;

	private String ulcer;

	@Column(name="ulcer_cured")
	private String ulcerCured;

	@Column(name="urine_aweek")
	private int urineAweek;

	private String vibration;

	public Asscement() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getAbortionsCount() {
		return this.abortionsCount;
	}

	public void setAbortionsCount(int abortionsCount) {
		this.abortionsCount = abortionsCount;
	}

	public String getAcupuncture() {
		return this.acupuncture;
	}

	public void setAcupuncture(String acupuncture) {
		this.acupuncture = acupuncture;
	}

	public String getAdvancedDmEyedisease() {
		return this.advancedDmEyedisease;
	}

	public void setAdvancedDmEyedisease(String advancedDmEyedisease) {
		this.advancedDmEyedisease = advancedDmEyedisease;
	}

	public int getBloodtestAweek() {
		return this.bloodtestAweek;
	}

	public void setBloodtestAweek(int bloodtestAweek) {
		this.bloodtestAweek = bloodtestAweek;
	}

	public String getBypassSurgery() {
		return this.bypassSurgery;
	}

	public void setBypassSurgery(String bypassSurgery) {
		this.bypassSurgery = bypassSurgery;
	}

	public String getCataract() {
		return this.cataract;
	}

	public void setCataract(String cataract) {
		this.cataract = cataract;
	}

	public double getDbp() {
		return this.dbp;
	}

	public void setDbp(double dbp) {
		this.dbp = dbp;
	}

	public String getDmType() {
		return this.dmType;
	}

	public void setDmType(String dmType) {
		this.dmType = dmType;
	}

	public String getDmTypeo() {
		return this.dmTypeo;
	}

	public void setDmTypeo(String dmTypeo) {
		this.dmTypeo = dmTypeo;
	}

	public Date getDmYear() {
		return this.dmYear;
	}

	public void setDmYear(Date dmYear) {
		this.dmYear = dmYear;
	}

	public String getDrink() {
		return this.drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public int getDrinkAweek() {
		return this.drinkAweek;
	}

	public void setDrinkAweek(int drinkAweek) {
		this.drinkAweek = drinkAweek;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public double getEyeLvision() {
		return this.eyeLvision;
	}

	public void setEyeLvision(double eyeLvision) {
		this.eyeLvision = eyeLvision;
	}

	public double getEyeRvision() {
		return this.eyeRvision;
	}

	public void setEyeRvision(double eyeRvision) {
		this.eyeRvision = eyeRvision;
	}

	public String getFamilyHistory() {
		return this.familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}

	public String getFundusCheck() {
		return this.fundusCheck;
	}

	public void setFundusCheck(String fundusCheck) {
		this.fundusCheck = fundusCheck;
	}

	public String getGestation() {
		return this.gestation;
	}

	public void setGestation(String gestation) {
		this.gestation = gestation;
	}

	public int getGestationCount() {
		return this.gestationCount;
	}

	public void setGestationCount(int gestationCount) {
		this.gestationCount = gestationCount;
	}

	public String getInsulin() {
		return this.insulin;
	}

	public void setInsulin(String insulin) {
		this.insulin = insulin;
	}

	public Date getInsulinSyear() {
		return this.insulinSyear;
	}

	public void setInsulinSyear(Date insulinSyear) {
		this.insulinSyear = insulinSyear;
	}

	public String getLightCoagulation() {
		return this.lightCoagulation;
	}

	public void setLightCoagulation(String lightCoagulation) {
		this.lightCoagulation = lightCoagulation;
	}

	public String getMacularDegeneration() {
		return this.macularDegeneration;
	}

	public void setMacularDegeneration(String macularDegeneration) {
		this.macularDegeneration = macularDegeneration;
	}

	public String getNonProliferativeRetinopathy() {
		return this.nonProliferativeRetinopathy;
	}

	public void setNonProliferativeRetinopathy(String nonProliferativeRetinopathy) {
		this.nonProliferativeRetinopathy = nonProliferativeRetinopathy;
	}

	public String getOralHypoglycemic() {
		return this.oralHypoglycemic;
	}

	public void setOralHypoglycemic(String oralHypoglycemic) {
		this.oralHypoglycemic = oralHypoglycemic;
	}

	public String getOralSyear() {
		return this.oralSyear;
	}

	public void setOralSyear(String oralSyear) {
		this.oralSyear = oralSyear;
	}

	public String getPreProliferativeRetinopathy() {
		return this.preProliferativeRetinopathy;
	}

	public void setPreProliferativeRetinopathy(String preProliferativeRetinopathy) {
		this.preProliferativeRetinopathy = preProliferativeRetinopathy;
	}

	public String getProliferativeRetinopathy() {
		return this.proliferativeRetinopathy;
	}

	public void setProliferativeRetinopathy(String proliferativeRetinopathy) {
		this.proliferativeRetinopathy = proliferativeRetinopathy;
	}

	public String getPulse() {
		return this.pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public String getRetinalCheck() {
		return this.retinalCheck;
	}

	public void setRetinalCheck(String retinalCheck) {
		this.retinalCheck = retinalCheck;
	}

	public double getSbp() {
		return this.sbp;
	}

	public void setSbp(double sbp) {
		this.sbp = sbp;
	}

	public String getSelfCare() {
		return this.selfCare;
	}

	public void setSelfCare(String selfCare) {
		this.selfCare = selfCare;
	}

	public String getSmoke() {
		return this.smoke;
	}

	public void setSmoke(String smoke) {
		this.smoke = smoke;
	}

	public int getSmokeAday() {
		return this.smokeAday;
	}

	public void setSmokeAday(int smokeAday) {
		this.smokeAday = smokeAday;
	}

	public String getSport() {
		return this.sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getUSid() {
		return this.uSid;
	}

	public void setUSid(String uSid) {
		this.uSid = uSid;
	}

	public Date getUdate() {
		return this.udate;
	}

	public void setUdate(Date udate) {
		this.udate = udate;
	}

	public String getUlcer() {
		return this.ulcer;
	}

	public void setUlcer(String ulcer) {
		this.ulcer = ulcer;
	}

	public String getUlcerCured() {
		return this.ulcerCured;
	}

	public void setUlcerCured(String ulcerCured) {
		this.ulcerCured = ulcerCured;
	}

	public int getUrineAweek() {
		return this.urineAweek;
	}

	public void setUrineAweek(int urineAweek) {
		this.urineAweek = urineAweek;
	}

	public String getVibration() {
		return this.vibration;
	}

	public void setVibration(String vibration) {
		this.vibration = vibration;
	}

}