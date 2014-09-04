package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the bed_code database table.
 * 
 */
@Entity
@Table(name="bed_code")
@NamedQuery(name="BedCode.findAll", query="SELECT b FROM BedCode b")
public class BedCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="bed_no")
	private int bedNo;

	private String description;

	//bi-directional many-to-one association to Policlinic
	@ManyToOne
	@JoinColumn(name="poli_guid")
	private Policlinic policlinic;

	//bi-directional many-to-one association to BedRecord
	@OneToMany(mappedBy="bedCode")
	private List<BedRecord> bedRecords;

	public BedCode() {
	}

	public int getBedNo() {
		return this.bedNo;
	}

	public void setBedNo(int bedNo) {
		this.bedNo = bedNo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Policlinic getPoliclinic() {
		return this.policlinic;
	}

	public void setPoliclinic(Policlinic policlinic) {
		this.policlinic = policlinic;
	}

	public List<BedRecord> getBedRecords() {
		return this.bedRecords;
	}

	public void setBedRecords(List<BedRecord> bedRecords) {
		this.bedRecords = bedRecords;
	}

	public BedRecord addBedRecord(BedRecord bedRecord) {
		getBedRecords().add(bedRecord);
		bedRecord.setBedCode(this);

		return bedRecord;
	}

	public BedRecord removeBedRecord(BedRecord bedRecord) {
		getBedRecords().remove(bedRecord);
		bedRecord.setBedCode(null);

		return bedRecord;
	}

}