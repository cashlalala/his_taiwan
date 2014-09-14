package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the medicines database table.
 * 
 */
@Entity
@Table(name="medicines")
@NamedQuery(name="Medicine.findAll", query="SELECT m FROM Medicine m")
public class Medicine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String ATC_code;

	@Column(name="default_freq")
	private String defaultFreq;

	@Column(name="default_way")
	private String defaultWay;

	private byte effective;

	private String injection;

	private String item;

	private String selfDrug;

	private int sort;

	private String unit;

	@Column(name="unit_cost")
	private float unitCost;

	@Column(name="unit_dosage")
	private float unitDosage;

	@Column(name="unit_price")
	private float unitPrice;

	//bi-directional many-to-one association to Allergy
	@OneToMany(mappedBy="medicine")
	private List<Allergy> allergies;

	//bi-directional many-to-one association to MedicalStockChangeRecord
	@OneToMany(mappedBy="medicine")
	private List<MedicalStockChangeRecord> medicalStockChangeRecords;

	//bi-directional many-to-one association to MedicineFavorite
	@OneToMany(mappedBy="medicine")
	private List<MedicineFavorite> medicineFavorites;

	//bi-directional many-to-one association to MedicineStock
	@OneToMany(mappedBy="medicine")
	private List<MedicineStock> medicineStocks;

	//bi-directional one-to-one association to PackageItem
	@OneToOne(mappedBy="medicine")
	private PackageItem packageItem;

	//bi-directional many-to-one association to SetMedicine
	@OneToMany(mappedBy="medicine")
	private List<SetMedicine> setMedicines;

	public Medicine() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getATC_code() {
		return this.ATC_code;
	}

	public void setATC_code(String ATC_code) {
		this.ATC_code = ATC_code;
	}

	public String getDefaultFreq() {
		return this.defaultFreq;
	}

	public void setDefaultFreq(String defaultFreq) {
		this.defaultFreq = defaultFreq;
	}

	public String getDefaultWay() {
		return this.defaultWay;
	}

	public void setDefaultWay(String defaultWay) {
		this.defaultWay = defaultWay;
	}

	public byte getEffective() {
		return this.effective;
	}

	public void setEffective(byte effective) {
		this.effective = effective;
	}

	public String getInjection() {
		return this.injection;
	}

	public void setInjection(String injection) {
		this.injection = injection;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getSelfDrug() {
		return this.selfDrug;
	}

	public void setSelfDrug(String selfDrug) {
		this.selfDrug = selfDrug;
	}

	public int getSort() {
		return this.sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public float getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}

	public float getUnitDosage() {
		return this.unitDosage;
	}

	public void setUnitDosage(float unitDosage) {
		this.unitDosage = unitDosage;
	}

	public float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public List<Allergy> getAllergies() {
		return this.allergies;
	}

	public void setAllergies(List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public Allergy addAllergy(Allergy allergy) {
		getAllergies().add(allergy);
		allergy.setMedicine(this);

		return allergy;
	}

	public Allergy removeAllergy(Allergy allergy) {
		getAllergies().remove(allergy);
		allergy.setMedicine(null);

		return allergy;
	}

	public List<MedicalStockChangeRecord> getMedicalStockChangeRecords() {
		return this.medicalStockChangeRecords;
	}

	public void setMedicalStockChangeRecords(List<MedicalStockChangeRecord> medicalStockChangeRecords) {
		this.medicalStockChangeRecords = medicalStockChangeRecords;
	}

	public MedicalStockChangeRecord addMedicalStockChangeRecord(MedicalStockChangeRecord medicalStockChangeRecord) {
		getMedicalStockChangeRecords().add(medicalStockChangeRecord);
		medicalStockChangeRecord.setMedicine(this);

		return medicalStockChangeRecord;
	}

	public MedicalStockChangeRecord removeMedicalStockChangeRecord(MedicalStockChangeRecord medicalStockChangeRecord) {
		getMedicalStockChangeRecords().remove(medicalStockChangeRecord);
		medicalStockChangeRecord.setMedicine(null);

		return medicalStockChangeRecord;
	}

	public List<MedicineFavorite> getMedicineFavorites() {
		return this.medicineFavorites;
	}

	public void setMedicineFavorites(List<MedicineFavorite> medicineFavorites) {
		this.medicineFavorites = medicineFavorites;
	}

	public MedicineFavorite addMedicineFavorite(MedicineFavorite medicineFavorite) {
		getMedicineFavorites().add(medicineFavorite);
		medicineFavorite.setMedicine(this);

		return medicineFavorite;
	}

	public MedicineFavorite removeMedicineFavorite(MedicineFavorite medicineFavorite) {
		getMedicineFavorites().remove(medicineFavorite);
		medicineFavorite.setMedicine(null);

		return medicineFavorite;
	}

	public List<MedicineStock> getMedicineStocks() {
		return this.medicineStocks;
	}

	public void setMedicineStocks(List<MedicineStock> medicineStocks) {
		this.medicineStocks = medicineStocks;
	}

	public MedicineStock addMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().add(medicineStock);
		medicineStock.setMedicine(this);

		return medicineStock;
	}

	public MedicineStock removeMedicineStock(MedicineStock medicineStock) {
		getMedicineStocks().remove(medicineStock);
		medicineStock.setMedicine(null);

		return medicineStock;
	}

	public PackageItem getPackageItem() {
		return this.packageItem;
	}

	public void setPackageItem(PackageItem packageItem) {
		this.packageItem = packageItem;
	}

	public List<SetMedicine> getSetMedicines() {
		return this.setMedicines;
	}

	public void setSetMedicines(List<SetMedicine> setMedicines) {
		this.setMedicines = setMedicines;
	}

	public SetMedicine addSetMedicine(SetMedicine setMedicine) {
		getSetMedicines().add(setMedicine);
		setMedicine.setMedicine(this);

		return setMedicine;
	}

	public SetMedicine removeSetMedicine(SetMedicine setMedicine) {
		getSetMedicines().remove(setMedicine);
		setMedicine.setMedicine(null);

		return setMedicine;
	}

}