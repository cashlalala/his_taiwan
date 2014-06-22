package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the radiation_body database table.
 * 
 */
@Entity
@Table(name="radiation_body")
public class RadiationBody implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Column(name="bodypart_id")
	private String bodypartId;

	@Column(name="pre_code")
	private String preCode;

	public RadiationBody() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getBodypartId() {
		return this.bodypartId;
	}

	public void setBodypartId(String bodypartId) {
		this.bodypartId = bodypartId;
	}

	public String getPreCode() {
		return this.preCode;
	}

	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}

}