package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the shift_table database table.
 * 
 */
@Entity
@Table(name="shift_table")
public class ShiftTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String shift;

	@Temporal(TemporalType.DATE)
	@Column(name="shift_date")
	private Date shiftDate;

	//bi-directional many-to-one association to AnamnesisRetrieve
	@OneToMany(mappedBy="shiftTable")
	private List<AnamnesisRetrieve> anamnesisRetrieves;

	//bi-directional many-to-one association to RegistrationInfo
	@OneToMany(mappedBy="shiftTable")
	private List<RegistrationInfo> registrationInfos;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_id")
	private StaffInfo staffInfo;

	//bi-directional many-to-one association to PoliRoom
	@ManyToOne
	@JoinColumn(name="room_guid")
	private PoliRoom poliRoom;

	public ShiftTable() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getShift() {
		return this.shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public Date getShiftDate() {
		return this.shiftDate;
	}

	public void setShiftDate(Date shiftDate) {
		this.shiftDate = shiftDate;
	}

	public List<AnamnesisRetrieve> getAnamnesisRetrieves() {
		return this.anamnesisRetrieves;
	}

	public void setAnamnesisRetrieves(List<AnamnesisRetrieve> anamnesisRetrieves) {
		this.anamnesisRetrieves = anamnesisRetrieves;
	}

	public AnamnesisRetrieve addAnamnesisRetrieve(AnamnesisRetrieve anamnesisRetrieve) {
		getAnamnesisRetrieves().add(anamnesisRetrieve);
		anamnesisRetrieve.setShiftTable(this);

		return anamnesisRetrieve;
	}

	public AnamnesisRetrieve removeAnamnesisRetrieve(AnamnesisRetrieve anamnesisRetrieve) {
		getAnamnesisRetrieves().remove(anamnesisRetrieve);
		anamnesisRetrieve.setShiftTable(null);

		return anamnesisRetrieve;
	}

	public List<RegistrationInfo> getRegistrationInfos() {
		return this.registrationInfos;
	}

	public void setRegistrationInfos(List<RegistrationInfo> registrationInfos) {
		this.registrationInfos = registrationInfos;
	}

	public RegistrationInfo addRegistrationInfo(RegistrationInfo registrationInfo) {
		getRegistrationInfos().add(registrationInfo);
		registrationInfo.setShiftTable(this);

		return registrationInfo;
	}

	public RegistrationInfo removeRegistrationInfo(RegistrationInfo registrationInfo) {
		getRegistrationInfos().remove(registrationInfo);
		registrationInfo.setShiftTable(null);

		return registrationInfo;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

	public PoliRoom getPoliRoom() {
		return this.poliRoom;
	}

	public void setPoliRoom(PoliRoom poliRoom) {
		this.poliRoom = poliRoom;
	}

}