package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the medical_stock database table.
 * 
 */
@Entity
@Table(name="medical_stock")
public class MedicalStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Column(name="current_amount")
	private int currentAmount;

	@Column(name="item_guid")
	private String itemGuid;

	@Column(name="minimal_amount")
	private int minimalAmount;

	private String type;

	//bi-directional many-to-one association to MedicalStockChangeRecord
	@OneToMany(mappedBy="medicalStock")
	private List<MedicalStockChangeRecord> medicalStockChangeRecords;

	public MedicalStock() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getCurrentAmount() {
		return this.currentAmount;
	}

	public void setCurrentAmount(int currentAmount) {
		this.currentAmount = currentAmount;
	}

	public String getItemGuid() {
		return this.itemGuid;
	}

	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}

	public int getMinimalAmount() {
		return this.minimalAmount;
	}

	public void setMinimalAmount(int minimalAmount) {
		this.minimalAmount = minimalAmount;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MedicalStockChangeRecord> getMedicalStockChangeRecords() {
		return this.medicalStockChangeRecords;
	}

	public void setMedicalStockChangeRecords(List<MedicalStockChangeRecord> medicalStockChangeRecords) {
		this.medicalStockChangeRecords = medicalStockChangeRecords;
	}

	public MedicalStockChangeRecord addMedicalStockChangeRecord(MedicalStockChangeRecord medicalStockChangeRecord) {
		getMedicalStockChangeRecords().add(medicalStockChangeRecord);
		medicalStockChangeRecord.setMedicalStock(this);

		return medicalStockChangeRecord;
	}

	public MedicalStockChangeRecord removeMedicalStockChangeRecord(MedicalStockChangeRecord medicalStockChangeRecord) {
		getMedicalStockChangeRecords().remove(medicalStockChangeRecord);
		medicalStockChangeRecord.setMedicalStock(null);

		return medicalStockChangeRecord;
	}

}