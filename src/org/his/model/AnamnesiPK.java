package org.his.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the anamnesis database table.
 * 
 */
@Embeddable
public class AnamnesiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="p_no")
	private String pNo;

	@Column(name="reg_guid")
	private String regGuid;

	public AnamnesiPK() {
	}
	public String getPNo() {
		return this.pNo;
	}
	public void setPNo(String pNo) {
		this.pNo = pNo;
	}
	public String getRegGuid() {
		return this.regGuid;
	}
	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AnamnesiPK)) {
			return false;
		}
		AnamnesiPK castOther = (AnamnesiPK)other;
		return 
			this.pNo.equals(castOther.pNo)
			&& this.regGuid.equals(castOther.regGuid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pNo.hashCode();
		hash = hash * prime + this.regGuid.hashCode();
		
		return hash;
	}
}