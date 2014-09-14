package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the prescription_code database table.
 * 
 */
@Entity
@Table(name="prescription_code")
@NamedQuery(name="PrescriptionCode.findAll", query="SELECT p FROM PrescriptionCode p")
public class PrescriptionCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private float cost;

	@Column(name="data_type")
	private String dataType;

	@Column(name="default_price")
	private float defaultPrice;

	private byte effective;

	private String equipment_ID;

	private String guideline;

	private String ICDVersion;

	@Lob
	private String limit;

	private String loinc;

	private String name;

	private String shortname;

	private String type;

	private String unit;

	//bi-directional many-to-one association to Prescription
	@OneToMany(mappedBy="prescriptionCode")
	private List<Prescription> prescriptions;

	//bi-directional many-to-one association to SetPrescription
	@OneToMany(mappedBy="prescriptionCode")
	private List<SetPrescription> setPrescriptions;

	public PrescriptionCode() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getCost() {
		return this.cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public float getDefaultPrice() {
		return this.defaultPrice;
	}

	public void setDefaultPrice(float defaultPrice) {
		this.defaultPrice = defaultPrice;
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

	public String getGuideline() {
		return this.guideline;
	}

	public void setGuideline(String guideline) {
		this.guideline = guideline;
	}

	public String getICDVersion() {
		return this.ICDVersion;
	}

	public void setICDVersion(String ICDVersion) {
		this.ICDVersion = ICDVersion;
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

	public List<Prescription> getPrescriptions() {
		return this.prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Prescription addPrescription(Prescription prescription) {
		getPrescriptions().add(prescription);
		prescription.setPrescriptionCode(this);

		return prescription;
	}

	public Prescription removePrescription(Prescription prescription) {
		getPrescriptions().remove(prescription);
		prescription.setPrescriptionCode(null);

		return prescription;
	}

	public List<SetPrescription> getSetPrescriptions() {
		return this.setPrescriptions;
	}

	public void setSetPrescriptions(List<SetPrescription> setPrescriptions) {
		this.setPrescriptions = setPrescriptions;
	}

	public SetPrescription addSetPrescription(SetPrescription setPrescription) {
		getSetPrescriptions().add(setPrescription);
		setPrescription.setPrescriptionCode(this);

		return setPrescription;
	}

	public SetPrescription removeSetPrescription(SetPrescription setPrescription) {
		getSetPrescriptions().remove(setPrescription);
		setPrescription.setPrescriptionCode(null);

		return setPrescription;
	}

}