package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the medicine_stock database table.
 * 
 */
@Entity
@Table(name="medicine_stock")
public class MedicineStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private float dosage;

	private String druggist;

	private byte exist;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="get_medicine_time")
	private Date getMedicineTime;

	private String powder;

	private float price;

	private String ps;

	private float quantity;

	@Column(name="repeat_number")
	private int repeatNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="replenish_date")
	private Date replenishDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="return_medicine_time")
	private Date returnMedicineTime;

	@Column(name="teach_complete")
	private String teachComplete;

	@Column(name="unit_price")
	private float unitPrice;

	private String urgent;

	//bi-directional many-to-one association to Medicine
	@ManyToOne
	@JoinColumn(name="m_code")
	private Medicine medicine;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_id")
	private StaffInfo staffInfo;

	//bi-directional many-to-one association to RegistrationInfo
	@ManyToOne
	@JoinColumn(name="reg_guid")
	private RegistrationInfo registrationInfo;

	//bi-directional many-to-one association to MedicineFrequency
	@ManyToOne
	@JoinColumn(name="usage")
	private MedicineFrequency medicineFrequency;

	//bi-directional many-to-one association to MedicineWay
	@ManyToOne
	@JoinColumn(name="way")
	private MedicineWay medicineWay;

	public MedicineStock() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public float getDosage() {
		return this.dosage;
	}

	public void setDosage(float dosage) {
		this.dosage = dosage;
	}

	public String getDruggist() {
		return this.druggist;
	}

	public void setDruggist(String druggist) {
		this.druggist = druggist;
	}

	public byte getExist() {
		return this.exist;
	}

	public void setExist(byte exist) {
		this.exist = exist;
	}

	public Date getGetMedicineTime() {
		return this.getMedicineTime;
	}

	public void setGetMedicineTime(Date getMedicineTime) {
		this.getMedicineTime = getMedicineTime;
	}

	public String getPowder() {
		return this.powder;
	}

	public void setPowder(String powder) {
		this.powder = powder;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getPs() {
		return this.ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public float getQuantity() {
		return this.quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public int getRepeatNumber() {
		return this.repeatNumber;
	}

	public void setRepeatNumber(int repeatNumber) {
		this.repeatNumber = repeatNumber;
	}

	public Date getReplenishDate() {
		return this.replenishDate;
	}

	public void setReplenishDate(Date replenishDate) {
		this.replenishDate = replenishDate;
	}

	public Date getReturnMedicineTime() {
		return this.returnMedicineTime;
	}

	public void setReturnMedicineTime(Date returnMedicineTime) {
		this.returnMedicineTime = returnMedicineTime;
	}

	public String getTeachComplete() {
		return this.teachComplete;
	}

	public void setTeachComplete(String teachComplete) {
		this.teachComplete = teachComplete;
	}

	public float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getUrgent() {
		return this.urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

	public RegistrationInfo getRegistrationInfo() {
		return this.registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfo registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

	public MedicineFrequency getMedicineFrequency() {
		return this.medicineFrequency;
	}

	public void setMedicineFrequency(MedicineFrequency medicineFrequency) {
		this.medicineFrequency = medicineFrequency;
	}

	public MedicineWay getMedicineWay() {
		return this.medicineWay;
	}

	public void setMedicineWay(MedicineWay medicineWay) {
		this.medicineWay = medicineWay;
	}

}