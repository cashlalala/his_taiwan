package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the anamnesis_retrieve database table.
 * 
 */
@Entity
@Table(name="anamnesis_retrieve")
public class AnamnesisRetrieve implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="borrow_time")
	private Date borrowTime;

	@Column(name="p_no")
	private int pNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="return_time")
	private Date returnTime;

	private String type;

	//bi-directional many-to-one association to Anamnesi
	@OneToMany(mappedBy="anamnesisRetrieve")
	private List<Anamnesi> anamnesis;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	//bi-directional many-to-one association to ShiftTable
	@ManyToOne
	@JoinColumn(name="shift_guid")
	private ShiftTable shiftTable;

	public AnamnesisRetrieve() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getBorrowTime() {
		return this.borrowTime;
	}

	public void setBorrowTime(Date borrowTime) {
		this.borrowTime = borrowTime;
	}

	public int getPNo() {
		return this.pNo;
	}

	public void setPNo(int pNo) {
		this.pNo = pNo;
	}

	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Anamnesi> getAnamnesis() {
		return this.anamnesis;
	}

	public void setAnamnesis(List<Anamnesi> anamnesis) {
		this.anamnesis = anamnesis;
	}

	public Anamnesi addAnamnesi(Anamnesi anamnesi) {
		getAnamnesis().add(anamnesi);
		anamnesi.setAnamnesisRetrieve(this);

		return anamnesi;
	}

	public Anamnesi removeAnamnesi(Anamnesi anamnesi) {
		getAnamnesis().remove(anamnesi);
		anamnesi.setAnamnesisRetrieve(null);

		return anamnesi;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

	public ShiftTable getShiftTable() {
		return this.shiftTable;
	}

	public void setShiftTable(ShiftTable shiftTable) {
		this.shiftTable = shiftTable;
	}

}