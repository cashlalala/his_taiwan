package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gis database table.
 * 
 */
@Entity
@Table(name="gis")
public class Gi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	@Lob
	private String address;

	private String gis;

	@Column(name="reg_guid")
	private String regGuid;

	public Gi() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGis() {
		return this.gis;
	}

	public void setGis(String gis) {
		this.gis = gis;
	}

	public String getRegGuid() {
		return this.regGuid;
	}

	public void setRegGuid(String regGuid) {
		this.regGuid = regGuid;
	}

}