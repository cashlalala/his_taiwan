package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the medical_stock_change_record database table.
 * 
 */
@Entity
@Table(name="medical_stock_change_record")
public class MedicalStockChangeRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String action;

	private int amount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private String type;

	//bi-directional many-to-one association to MedicalStock
	@ManyToOne
	@JoinColumn(name="item_guid")
	private MedicalStock medicalStock;

	//bi-directional many-to-one association to StaffInfo
	@ManyToOne
	@JoinColumn(name="s_no")
	private StaffInfo staffInfo;

	public MedicalStockChangeRecord() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MedicalStock getMedicalStock() {
		return this.medicalStock;
	}

	public void setMedicalStock(MedicalStock medicalStock) {
		this.medicalStock = medicalStock;
	}

	public StaffInfo getStaffInfo() {
		return this.staffInfo;
	}

	public void setStaffInfo(StaffInfo staffInfo) {
		this.staffInfo = staffInfo;
	}

}