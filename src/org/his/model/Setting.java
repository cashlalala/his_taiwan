package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the setting database table.
 * 
 */
@Entity
@Table(name="setting")
@NamedQuery(name="Setting.findAll", query="SELECT s FROM Setting s")
public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="bed_price")
	private float bedPrice;

	@Column(name="diagnosis_price")
	private float diagnosisPrice;

	@Lob
	@Column(name="dm_drugtip")
	private String dmDrugtip;

	@Column(name="evening_shift_e")
	private int eveningShiftE;

	@Column(name="evening_shift_s")
	private int eveningShiftS;

	private String gps;

	@Column(name="hl7_edition")
	private String hl7Edition;

	@Column(name="hos_address")
	private String hosAddress;

	@Column(name="hos_icon_path")
	private String hosIconPath;

	@Column(name="hos_mail")
	private String hosMail;

	@Column(name="hos_name")
	private String hosName;

	@Column(name="hos_phone")
	private String hosPhone;

	private String hospicalID;

	private String ICDVersion;

	private String language;

	@Column(name="morning_shift_e")
	private int morningShiftE;

	@Column(name="morning_shift_s")
	private int morningShiftS;

	@Column(name="noon_shift_e")
	private int noonShiftE;

	@Column(name="noon_shift_s")
	private int noonShiftS;

	@Column(name="registration_price")
	private float registrationPrice;

	@Column(name="sms_remind_days")
	private int smsRemindDays;

	@Column(name="updata_ftp")
	private String updataFtp;

	public Setting() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getBedPrice() {
		return this.bedPrice;
	}

	public void setBedPrice(float bedPrice) {
		this.bedPrice = bedPrice;
	}

	public float getDiagnosisPrice() {
		return this.diagnosisPrice;
	}

	public void setDiagnosisPrice(float diagnosisPrice) {
		this.diagnosisPrice = diagnosisPrice;
	}

	public String getDmDrugtip() {
		return this.dmDrugtip;
	}

	public void setDmDrugtip(String dmDrugtip) {
		this.dmDrugtip = dmDrugtip;
	}

	public int getEveningShiftE() {
		return this.eveningShiftE;
	}

	public void setEveningShiftE(int eveningShiftE) {
		this.eveningShiftE = eveningShiftE;
	}

	public int getEveningShiftS() {
		return this.eveningShiftS;
	}

	public void setEveningShiftS(int eveningShiftS) {
		this.eveningShiftS = eveningShiftS;
	}

	public String getGps() {
		return this.gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getHl7Edition() {
		return this.hl7Edition;
	}

	public void setHl7Edition(String hl7Edition) {
		this.hl7Edition = hl7Edition;
	}

	public String getHosAddress() {
		return this.hosAddress;
	}

	public void setHosAddress(String hosAddress) {
		this.hosAddress = hosAddress;
	}

	public String getHosIconPath() {
		return this.hosIconPath;
	}

	public void setHosIconPath(String hosIconPath) {
		this.hosIconPath = hosIconPath;
	}

	public String getHosMail() {
		return this.hosMail;
	}

	public void setHosMail(String hosMail) {
		this.hosMail = hosMail;
	}

	public String getHosName() {
		return this.hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public String getHosPhone() {
		return this.hosPhone;
	}

	public void setHosPhone(String hosPhone) {
		this.hosPhone = hosPhone;
	}

	public String getHospicalID() {
		return this.hospicalID;
	}

	public void setHospicalID(String hospicalID) {
		this.hospicalID = hospicalID;
	}

	public String getICDVersion() {
		return this.ICDVersion;
	}

	public void setICDVersion(String ICDVersion) {
		this.ICDVersion = ICDVersion;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getMorningShiftE() {
		return this.morningShiftE;
	}

	public void setMorningShiftE(int morningShiftE) {
		this.morningShiftE = morningShiftE;
	}

	public int getMorningShiftS() {
		return this.morningShiftS;
	}

	public void setMorningShiftS(int morningShiftS) {
		this.morningShiftS = morningShiftS;
	}

	public int getNoonShiftE() {
		return this.noonShiftE;
	}

	public void setNoonShiftE(int noonShiftE) {
		this.noonShiftE = noonShiftE;
	}

	public int getNoonShiftS() {
		return this.noonShiftS;
	}

	public void setNoonShiftS(int noonShiftS) {
		this.noonShiftS = noonShiftS;
	}

	public float getRegistrationPrice() {
		return this.registrationPrice;
	}

	public void setRegistrationPrice(float registrationPrice) {
		this.registrationPrice = registrationPrice;
	}

	public int getSmsRemindDays() {
		return this.smsRemindDays;
	}

	public void setSmsRemindDays(int smsRemindDays) {
		this.smsRemindDays = smsRemindDays;
	}

	public String getUpdataFtp() {
		return this.updataFtp;
	}

	public void setUpdataFtp(String updataFtp) {
		this.updataFtp = updataFtp;
	}

}