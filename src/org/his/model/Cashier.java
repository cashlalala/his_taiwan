package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the cashier database table.
 * 
 */
@Entity
public class Cashier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String no;

	@Column(name="amount_receivable")
	private double amountReceivable;

	private double arrears;

	private String backin;

	@Column(name="backin_sno")
	private int backinSno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="backin_time")
	private Date backinTime;

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

	private String typ;

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

	public String getBackin() {
		return this.backin;
	}

	public void setBackin(String backin) {
		this.backin = backin;
	}

	public int getBackinSno() {
		return this.backinSno;
	}

	public void setBackinSno(int backinSno) {
		this.backinSno = backinSno;
	}

	public Date getBackinTime() {
		return this.backinTime;
	}

	public void setBackinTime(Date backinTime) {
		this.backinTime = backinTime;
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

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

}