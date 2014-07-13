package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the health_teach database table.
 * 
 */
@Entity
@Table(name="health_teach")
public class HealthTeach implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String acceptance;

	private String confirm;

	//bi-directional many-to-one association to HealthTeachItem
	@ManyToOne
	@JoinColumn(name="hti_code")
	private HealthTeachItem healthTeachItem;

	//bi-directional many-to-one association to RegistrationInfo
	@ManyToOne
	@JoinColumn(name="reg_guid")
	private RegistrationInfo registrationInfo;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_id")
	private StaffInfo staffInfo;

	public HealthTeach() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAcceptance() {
		return this.acceptance;
	}

	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}

	public String getConfirm() {
		return this.confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public HealthTeachItem getHealthTeachItem() {
		return this.healthTeachItem;
	}

	public void setHealthTeachItem(HealthTeachItem healthTeachItem) {
		this.healthTeachItem = healthTeachItem;
	}

	public RegistrationInfo getRegistrationInfo() {
		return this.registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfo registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}