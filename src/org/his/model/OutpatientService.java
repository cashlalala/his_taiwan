package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the outpatient_services database table.
 * 
 */
@Entity
@Table(name="outpatient_services")
public class OutpatientService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date finishtime;

	private String ps;

	private byte state;

	@Lob
	private String summary;

	//bi-directional many-to-one association to RegistrationInfo
	@ManyToOne
	@JoinColumn(name="reg_guid")
	private RegistrationInfo registrationInfo;

	//bi-directional many-to-one association to Prescription
	@OneToMany(mappedBy="outpatientService")
	private List<Prescription> prescriptions;

	//bi-directional many-to-one association to Treat
	@OneToMany(mappedBy="outpatientService")
	private List<Treat> treats;

	public OutpatientService() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	public String getPs() {
		return this.ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public byte getState() {
		return this.state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public RegistrationInfo getRegistrationInfo() {
		return this.registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfo registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

	public List<Prescription> getPrescriptions() {
		return this.prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public Prescription addPrescription(Prescription prescription) {
		getPrescriptions().add(prescription);
		prescription.setOutpatientService(this);

		return prescription;
	}

	public Prescription removePrescription(Prescription prescription) {
		getPrescriptions().remove(prescription);
		prescription.setOutpatientService(null);

		return prescription;
	}

	public List<Treat> getTreats() {
		return this.treats;
	}

	public void setTreats(List<Treat> treats) {
		this.treats = treats;
	}

	public Treat addTreat(Treat treat) {
		getTreats().add(treat);
		treat.setOutpatientService(this);

		return treat;
	}

	public Treat removeTreat(Treat treat) {
		getTreats().remove(treat);
		treat.setOutpatientService(null);

		return treat;
	}

}