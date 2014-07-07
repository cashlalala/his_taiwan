package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the loinc database table.
 * 
 */
@Entity
public class Loinc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int sid;

	private String component;

	private String loinc;

	private String method;

	private String nhi;

	private String property;

	@Column(name="s_com_name")
	private String sComName;

	private String scale;

	private String system;

	private String time;

	public Loinc() {
	}

	public int getSid() {
		return this.sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getComponent() {
		return this.component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getLoinc() {
		return this.loinc;
	}

	public void setLoinc(String loinc) {
		this.loinc = loinc;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getNhi() {
		return this.nhi;
	}

	public void setNhi(String nhi) {
		this.nhi = nhi;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getSComName() {
		return this.sComName;
	}

	public void setSComName(String sComName) {
		this.sComName = sComName;
	}

	public String getScale() {
		return this.scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getSystem() {
		return this.system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}