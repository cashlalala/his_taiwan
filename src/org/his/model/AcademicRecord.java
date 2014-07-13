package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the academic_record database table.
 * 
 */
@Entity
@Table(name="academic_record")
public class AcademicRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int no;

	@Temporal(TemporalType.DATE)
	@Column(name="commencement_date")
	private Date commencementDate;

	@Temporal(TemporalType.DATE)
	@Column(name="completion_date")
	private Date completionDate;

	private String name;

	private String qualification;

	@Column(name="s_no")
	private int sNo;

	public AcademicRecord() {
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Date getCommencementDate() {
		return this.commencementDate;
	}

	public void setCommencementDate(Date commencementDate) {
		this.commencementDate = commencementDate;
	}

	public Date getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualification() {
		return this.qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getSNo() {
		return this.sNo;
	}

	public void setSNo(int sNo) {
		this.sNo = sNo;
	}

}