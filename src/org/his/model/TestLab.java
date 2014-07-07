package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the test_lab database table.
 * 
 */
@Entity
@Table(name="test_lab")
public class TestLab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private double bgac;

	private double bgpc;

	private double bun;

	private double bw;

	private double cr;

	private double dbp;

	@Column(name="`DUP(Daily urine protein)`")
	private double DUP_Daily_urine_protein_;

	private double got;

	private double gpt;

	private double hbA1C;

	private double hdl;

	private double ldl;

	private double sbp;

	private double tc;

	private double tg;

	@Column(name="`UAE(Ualb/Ucr)`")
	private double UAE_Ualb_Ucr_;

	private double waist;

	public TestLab() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getBgac() {
		return this.bgac;
	}

	public void setBgac(double bgac) {
		this.bgac = bgac;
	}

	public double getBgpc() {
		return this.bgpc;
	}

	public void setBgpc(double bgpc) {
		this.bgpc = bgpc;
	}

	public double getBun() {
		return this.bun;
	}

	public void setBun(double bun) {
		this.bun = bun;
	}

	public double getBw() {
		return this.bw;
	}

	public void setBw(double bw) {
		this.bw = bw;
	}

	public double getCr() {
		return this.cr;
	}

	public void setCr(double cr) {
		this.cr = cr;
	}

	public double getDbp() {
		return this.dbp;
	}

	public void setDbp(double dbp) {
		this.dbp = dbp;
	}

	public double getDUP_Daily_urine_protein_() {
		return this.DUP_Daily_urine_protein_;
	}

	public void setDUP_Daily_urine_protein_(double DUP_Daily_urine_protein_) {
		this.DUP_Daily_urine_protein_ = DUP_Daily_urine_protein_;
	}

	public double getGot() {
		return this.got;
	}

	public void setGot(double got) {
		this.got = got;
	}

	public double getGpt() {
		return this.gpt;
	}

	public void setGpt(double gpt) {
		this.gpt = gpt;
	}

	public double getHbA1C() {
		return this.hbA1C;
	}

	public void setHbA1C(double hbA1C) {
		this.hbA1C = hbA1C;
	}

	public double getHdl() {
		return this.hdl;
	}

	public void setHdl(double hdl) {
		this.hdl = hdl;
	}

	public double getLdl() {
		return this.ldl;
	}

	public void setLdl(double ldl) {
		this.ldl = ldl;
	}

	public double getSbp() {
		return this.sbp;
	}

	public void setSbp(double sbp) {
		this.sbp = sbp;
	}

	public double getTc() {
		return this.tc;
	}

	public void setTc(double tc) {
		this.tc = tc;
	}

	public double getTg() {
		return this.tg;
	}

	public void setTg(double tg) {
		this.tg = tg;
	}

	public double getUAE_Ualb_Ucr_() {
		return this.UAE_Ualb_Ucr_;
	}

	public void setUAE_Ualb_Ucr_(double UAE_Ualb_Ucr_) {
		this.UAE_Ualb_Ucr_ = UAE_Ualb_Ucr_;
	}

	public double getWaist() {
		return this.waist;
	}

	public void setWaist(double waist) {
		this.waist = waist;
	}

}