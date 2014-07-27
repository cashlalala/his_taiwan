package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the policlinic database table.
 * 
 */
@Entity
@Table(name="policlinic")
public class Policlinic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String name;

	@Column(name="room_num")
	private int roomNum;

	private String type;

	//bi-directional many-to-one association to BedCode
	@OneToMany(mappedBy="policlinic")
	private List<BedCode> bedCodes;

	//bi-directional many-to-one association to BedRecord
	@OneToMany(mappedBy="policlinic")
	private List<BedRecord> bedRecords;

	//bi-directional many-to-one association to Clinical
	@OneToMany(mappedBy="policlinic")
	private List<Clinical> clinicals;

	//bi-directional many-to-one association to PoliRoom
	@OneToMany(mappedBy="policlinic")
	private List<PoliRoom> poliRooms;

	//bi-directional many-to-one association to StaffInfo
	@OneToMany(mappedBy="policlinic")
	private List<StaffInfo> staffInfos;

	public Policlinic() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomNum() {
		return this.roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<BedCode> getBedCodes() {
		return this.bedCodes;
	}

	public void setBedCodes(List<BedCode> bedCodes) {
		this.bedCodes = bedCodes;
	}

	public BedCode addBedCode(BedCode bedCode) {
		getBedCodes().add(bedCode);
		bedCode.setPoliclinic(this);

		return bedCode;
	}

	public BedCode removeBedCode(BedCode bedCode) {
		getBedCodes().remove(bedCode);
		bedCode.setPoliclinic(null);

		return bedCode;
	}

	public List<BedRecord> getBedRecords() {
		return this.bedRecords;
	}

	public void setBedRecords(List<BedRecord> bedRecords) {
		this.bedRecords = bedRecords;
	}

	public BedRecord addBedRecord(BedRecord bedRecord) {
		getBedRecords().add(bedRecord);
		bedRecord.setPoliclinic(this);

		return bedRecord;
	}

	public BedRecord removeBedRecord(BedRecord bedRecord) {
		getBedRecords().remove(bedRecord);
		bedRecord.setPoliclinic(null);

		return bedRecord;
	}

	public List<Clinical> getClinicals() {
		return this.clinicals;
	}

	public void setClinicals(List<Clinical> clinicals) {
		this.clinicals = clinicals;
	}

	public Clinical addClinical(Clinical clinical) {
		getClinicals().add(clinical);
		clinical.setPoliclinic(this);

		return clinical;
	}

	public Clinical removeClinical(Clinical clinical) {
		getClinicals().remove(clinical);
		clinical.setPoliclinic(null);

		return clinical;
	}

	public List<PoliRoom> getPoliRooms() {
		return this.poliRooms;
	}

	public void setPoliRooms(List<PoliRoom> poliRooms) {
		this.poliRooms = poliRooms;
	}

	public PoliRoom addPoliRoom(PoliRoom poliRoom) {
		getPoliRooms().add(poliRoom);
		poliRoom.setPoliclinic(this);

		return poliRoom;
	}

	public PoliRoom removePoliRoom(PoliRoom poliRoom) {
		getPoliRooms().remove(poliRoom);
		poliRoom.setPoliclinic(null);

		return poliRoom;
	}

	public List<StaffInfo> getStaffInfos() {
		return this.staffInfos;
	}

	public void setStaffInfos(List<StaffInfo> staffInfos) {
		this.staffInfos = staffInfos;
	}

	public StaffInfo addStaffInfo(StaffInfo staffInfo) {
		getStaffInfos().add(staffInfo);
		staffInfo.setPoliclinic(this);

		return staffInfo;
	}

	public StaffInfo removeStaffInfo(StaffInfo staffInfo) {
		getStaffInfos().remove(staffInfo);
		staffInfo.setPoliclinic(null);

		return staffInfo;
	}

}