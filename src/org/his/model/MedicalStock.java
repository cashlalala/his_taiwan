package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the medical_stock database table.
 * 
 */
@Entity
@Table(name="medical_stock")
@NamedQuery(name="MedicalStock.findAll", query="SELECT m FROM MedicalStock m")
public class MedicalStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Column(name="current_amount")
	private float currentAmount;

	@Column(name="default_unit_price")
	private float defaultUnitPrice;

	@Column(name="item_guid")
	private String itemGuid;

	@Column(name="minimal_amount")
	private float minimalAmount;

	private String type;

	public MedicalStock() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public float getCurrentAmount() {
		return this.currentAmount;
	}

	public void setCurrentAmount(float currentAmount) {
		this.currentAmount = currentAmount;
	}

	public float getDefaultUnitPrice() {
		return this.defaultUnitPrice;
	}

	public void setDefaultUnitPrice(float defaultUnitPrice) {
		this.defaultUnitPrice = defaultUnitPrice;
	}

	public String getItemGuid() {
		return this.itemGuid;
	}

	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}

	public float getMinimalAmount() {
		return this.minimalAmount;
	}

	public void setMinimalAmount(float minimalAmount) {
		this.minimalAmount = minimalAmount;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}