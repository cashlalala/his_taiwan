package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the complication database table.
 * 
 */
@Entity
public class Complication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private String angina;

	private String claudication;

	@Column(name="coronary_heart")
	private String coronaryHeart;

	private String dka;

	@Column(name="eye_lesions")
	private String eyeLesions;

	private String hhs;

	private String hypoglycemia;

	private String kidney;

	private String neuropathy;

	@Lob
	private String other;

	private String paod;

	@Column(name="peripheral_neuropathy")
	private String peripheralNeuropathy;

	@Column(name="postural_hypotension")
	private String posturalHypotension;

	@Column(name="reg_guid")
	private String regGuid;

	private String stroke;

	@Column(name="u_sid")
	private String uSid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date udate;

	private double waist;

	public Complication() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAngina() {
		return this.angina;
	}

	public void setAngina(String angina) {
		this.angina = angina;
	}

	public String getClaudication() {
		return this.claudication;
	}

	public void setClaudication(String claudication) {
		this.claudication = claudication;
	}

	public String getCoronaryHeart() {
		return this.coronaryHeart;
	}

	public void setCoronaryHeart(String coronaryHeart) {
		this.coronaryHeart = coronaryHeart;
	}

	public String getDka() {
		return this.dka;
	}

	public void setDka(String dka) {
		this.dka = dka;
	}

	public String getEyeLesions() {
		return this.eyeLesions;
	}

	public void setEyeLesions(String eyeLesions) {
		this.eyeLesions = eyeLesions;
	}

	public String getHhs() {
		return this.hhs;
	}

	public void setHhs(String hhs) {
		this.hhs = hhs;
	}

	public String getHypoglycemia() {
		return this.hypoglycemia;
	}

	public void setHypoglycemia(String hypoglycemia) {
		this.hypoglycemia = hypoglycemia;
	}

	public String getKidney() {
		return this.kidney;
	}

	public void setKidney(String kidney) {
		this.kidney = kidney;
	}

	public String getNeuropathy() {
		return this.neuropathy;
	}

	public void setNeuropathy(String neuropathy) {
		this.neuropathy = neuropathy;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getPaod() {
		return this.paod;
	}

	public void setPaod(String paod) {
		this.paod = paod;
	}

	public String getPeripheralNeuropathy() {
		return this.peripheralNeuropathy;
	}

	public void setPeripheralNeuropathy(String peripheralNeuropathy) {
		this.peripheralNeuropathy = peripheralNeuropathy;
	}

	public String getPosturalHypotension() {
		return this.posturalHypotension;
	}

	public void setPosturalHypotension(String posturalHypotension) {
		this.posturalHypotension = posturalHypotension;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public String getStroke() {
		return this.stroke;
	}

	public void setStroke(String stroke) {
		this.stroke = stroke;
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

	public double getWaist() {
		return this.waist;
	}

	public void setWaist(double waist) {
		this.waist = waist;
	}

}