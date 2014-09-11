package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the gis database table.
 * 
 */
@Entity
@Table(name="gis")
@NamedQuery(name="Gi.findAll", query="SELECT g FROM Gi g")
public class Gi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String guid;

	@Lob
	private String address;

	private String gis;

	//bi-directional many-to-one association to RegistrationInfo
	@ManyToOne
	@JoinColumn(name="reg_guid")
	private RegistrationInfo registrationInfo;

	//bi-directional many-to-one association to RegistrationInfo
	@OneToMany(mappedBy="gi")
	private List<RegistrationInfo> registrationInfos;

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

	public RegistrationInfo getRegistrationInfo() {
		return this.registrationInfo;
	}

	public void setRegistrationInfo(RegistrationInfo registrationInfo) {
		this.registrationInfo = registrationInfo;
	}

	public List<RegistrationInfo> getRegistrationInfos() {
		return this.registrationInfos;
	}

	public void setRegistrationInfos(List<RegistrationInfo> registrationInfos) {
		this.registrationInfos = registrationInfos;
	}

	public RegistrationInfo addRegistrationInfo(RegistrationInfo registrationInfo) {
		getRegistrationInfos().add(registrationInfo);
		registrationInfo.setGi(this);

		return registrationInfo;
	}

	public RegistrationInfo removeRegistrationInfo(RegistrationInfo registrationInfo) {
		getRegistrationInfos().remove(registrationInfo);
		registrationInfo.setGi(null);

		return registrationInfo;
	}

}