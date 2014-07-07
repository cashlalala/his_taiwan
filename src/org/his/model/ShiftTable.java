package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the shift_table database table.
 * 
 */
@Entity
@Table(name="shift_table")
public class ShiftTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Column(name="room_guid")
	private String roomGuid;

	@Column(name="s_id")
	private String sId;

	private String shift;

	@Temporal(TemporalType.DATE)
	@Column(name="shift_date")
	private Date shiftDate;

	public ShiftTable() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getRoomGuid() {
		return this.roomGuid;
	}

	public void setRoomGuid(String roomGuid) {
		this.roomGuid = roomGuid;
	}

	public String getSId() {
		return this.sId;
	}

	public void setSId(String sId) {
		this.sId = sId;
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

}