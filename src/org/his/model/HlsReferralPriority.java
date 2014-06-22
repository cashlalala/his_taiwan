package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hls_referral_priority database table.
 * 
 */
@Entity
@Table(name="hls_referral_priority")
public class HlsReferralPriority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String value;

	private String descrition;

	public HlsReferralPriority() {
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescrition() {
		return this.descrition;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

}