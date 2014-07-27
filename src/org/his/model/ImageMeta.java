package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the image_meta database table.
 * 
 */
@Entity
@Table(name="image_meta")
public class ImageMeta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Column(name="file_path")
	private String filePath;

	@Column(name="item_guid")
	private String itemGuid;

	private String type;

	//bi-directional many-to-one association to PatientsInfo
	@ManyToOne
	@JoinColumn(name="p_no")
	private PatientsInfo patientsInfo;

	//bi-directional many-to-one association to Prescription
	@OneToMany(mappedBy="imageMeta")
	private List<Prescription> prescriptions;

	public ImageMeta() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getItemGuid() {
		return this.itemGuid;
	}

	public void setItemGuid(String itemGuid) {
		this.itemGuid = itemGuid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PatientsInfo getPatientsInfo() {
		return this.patientsInfo;
	}

	public void setPatientsInfo(PatientsInfo patientsInfo) {
		this.patientsInfo = patientsInfo;
	}

	public List<Prescription> getPrescriptions() {
		return this.prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Prescription addPrescription(Prescription prescription) {
		getPrescriptions().add(prescription);
		prescription.setImageMeta(this);

		return prescription;
	}

	public Prescription removePrescription(Prescription prescription) {
		getPrescriptions().remove(prescription);
		prescription.setImageMeta(null);

		return prescription;
	}

}