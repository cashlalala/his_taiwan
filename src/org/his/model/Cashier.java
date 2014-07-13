package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the cashier database table.
 * 
 */
@Entity
@Table(name="cashier")
public class Cashier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String no;

	@Column(name="amount_receivable")
	private double amountReceivable;

	private double arrears;

	@Column(name="p_no")
	private int pNo;

	@Column(name="paid_amount")
	private double paidAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="payment_time")
	private Date paymentTime;

	@Column(name="reg_guid")
	private String regGuid;

	@Column(name="s_no")
	private int sNo;

	private String type;

	public Cashier() {
	}

	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public double getAmountReceivable() {
		return this.amountReceivable;
	}

	public void setAmountReceivable(double amountReceivable) {
		this.amountReceivable = amountReceivable;
	}

	public double getArrears() {
		return this.arrears;
	}

	public void setArrears(double arrears) {
		this.arrears = arrears;
	}

	public int getPNo() {
		return this.pNo;
	}

	public void setPNo(int pNo) {
		this.pNo = pNo;
	}

	public double getPaidAmount() {
		return this.paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Date getPaymentTime() {
		return this.paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}