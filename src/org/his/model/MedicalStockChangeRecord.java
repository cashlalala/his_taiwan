package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the medical_stock_change_record database table.
 * 
 */
@Entity
@Table(name="medical_stock_change_record")
@NamedQuery(name="MedicalStockChangeRecord.findAll", query="SELECT m FROM MedicalStockChangeRecord m")
public class MedicalStockChangeRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	private String action;

	@Column(name="diff_amount")
	private float diffAmount;

	@Column(name="diff_minimal_amount")
	private float diffMinimalAmount;

	@Column(name="diff_price")
	private float diffPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="purchase_date")
	private Date purchaseDate;

	private String type;

	@Column(name="unit_cost")
	private float unitCost;

	private String vendor;

	//bi-directional many-to-one association to Medicine
	@ManyToOne
	@JoinColumn(name="item_guid")
	private Medicine medicine;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public MedicalStockChangeRecord() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public float getDiffAmount() {
		return this.diffAmount;
	}

	public void setDiffAmount(float diffAmount) {
		this.diffAmount = diffAmount;
	}

	public float getDiffMinimalAmount() {
		return this.diffMinimalAmount;
	}

	public void setDiffMinimalAmount(float diffMinimalAmount) {
		this.diffMinimalAmount = diffMinimalAmount;
	}

	public float getDiffPrice() {
		return this.diffPrice;
	}

	public void setDiffPrice(float diffPrice) {
		this.diffPrice = diffPrice;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
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

}