package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the case_manage database table.
 * 
 */
@Entity
@Table(name="case_manage")
public class CaseManage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Column(name="finish_time")
	private Time finishTime;

	private String isdiagnosis;

	@Column(name="modify_count")
	private int modifyCount;

	@Column(name="reg_guid")
	private String regGuid;

	@Column(name="s_no")
	private int sNo;

	public CaseManage() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Time getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(Time finishTime) {
		this.finishTime = finishTime;
	}

	public String getIsdiagnosis() {
		return this.isdiagnosis;
	}

	public void setIsdiagnosis(String isdiagnosis) {
		this.isdiagnosis = isdiagnosis;
	}

	public int getModifyCount() {
		return this.modifyCount;
	}

	public void setModifyCount(int modifyCount) {
		this.modifyCount = modifyCount;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public int getSNo() {
		return this.sNo;
	}

	public void setSNo(int sNo) {
		this.sNo = sNo;
	}

}