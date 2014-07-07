package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the prescription_code database table.
 * 
 */
@Entity
@Table(name="prescription_code")
public class PrescriptionCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String code;

	@Column(name="data_type")
	private String dataType;

	private byte effective;

	private String equipment_ID;

	@Lob
	private String limit;

	private String loinc;

	private String name;

	private String shortname;

	private String type;

	private String unit;

	public PrescriptionCode() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public byte getEffective() {
		return this.effective;
	}

	public void setEffective(byte effective) {
		this.effective = effective;
	}

	public String getEquipment_ID() {
		return this.equipment_ID;
	}

	public void setEquipment_ID(String equipment_ID) {
		this.equipment_ID = equipment_ID;
	}

	public String getLimit() {
		return this.limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLoinc() {
		return this.loinc;
	}

	public void setLoinc(String loinc) {
		this.loinc = loinc;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}