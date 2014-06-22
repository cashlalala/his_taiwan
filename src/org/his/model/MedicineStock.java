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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private float day;

	private float dosage;

	private String druggist;

	private byte exist;

	@Column(name="m_code")
	private String mCode;

	@Column(name="os_guid")
	private String osGuid;

	private String powder;

	private float price;

	private String ps;

	private float quantity;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="replenish_date")
	private Date replenishDate;

	@Column(name="s_id")
	private String sId;

	@Column(name="teach_complete")
	private String teachComplete;

	private String urgent;

	private String usage;

	private String way;

	public MedicineStock() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public float getDay() {
		return this.day;
	}

	public void setDay(float day) {
		this.day = day;
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

	public String getMCode() {
		return this.mCode;
	}

	public void setMCode(String mCode) {
		this.mCode = mCode;
	}

	public String getOsGuid() {
		return this.osGuid;
	}

	public void setOsGuid(String osGuid) {
		this.osGuid = osGuid;
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

	public Date getReplenishDate() {
		return this.replenishDate;
	}

	public void setReplenishDate(Date replenishDate) {
		this.replenishDate = replenishDate;
	}

	public String getSId() {
		return this.sId;
	}

	public void setSId(String sId) {
		this.sId = sId;
	}

	public String getTeachComplete() {
		return this.teachComplete;
	}

	public void setTeachComplete(String teachComplete) {
		this.teachComplete = teachComplete;
	}

	public String getUrgent() {
		return this.urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getWay() {
		return this.way;
	}

	public void setWay(String way) {
		this.way = way;
	}

}