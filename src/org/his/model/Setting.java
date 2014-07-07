package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the setting database table.
 * 
 */
@Entity
public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	@Column(name="dm_drugtip")
	private String dmDrugtip;

	@Column(name="evening_shift_e")
	private int eveningShiftE;

	@Column(name="evening_shift_s")
	private int eveningShiftS;

	private String gps;

	private String language;

	@Column(name="morning_shift_e")
	private int morningShiftE;

	@Column(name="morning_shift_s")
	private int morningShiftS;

	@Column(name="noon_shift_e")
	private int noonShiftE;

	@Column(name="noon_shift_s")
	private int noonShiftS;

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

	public String getUpdataFtp() {
		return this.updataFtp;
	}

	public void setUpdataFtp(String updataFtp) {
		this.updataFtp = updataFtp;
	}

}