package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the hls_group database table.
 * 
 */
@NamedQueries ({
    @NamedQuery(
        name="QueryHlsgroups",
        query="SELECT hls_group FROM HlsGroup hls_group"),
    @NamedQuery(
        name="QueryHlsgroupByValue",
        query="SELECT hls_group FROM HlsGroup hls_group WHERE hls_group.value = :val")
})

@Entity
@Table(name="hls_group")
public class HlsGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String value;

	private String descrition;

	//bi-directional many-to-one association to PatientsInfo
	@OneToMany(mappedBy="hlsGroup")
	private List<PatientsInfo> patientsInfos;

	public HlsGroup() {
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

	public List<PatientsInfo> getPatientsInfos() {
		return this.patientsInfos;
	}

	public void setPatientsInfos(List<PatientsInfo> patientsInfos) {
		this.patientsInfos = patientsInfos;
	}

	public PatientsInfo addPatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().add(patientsInfo);
		patientsInfo.setHlsGroup(this);

		return patientsInfo;
	}

	public PatientsInfo removePatientsInfo(PatientsInfo patientsInfo) {
		getPatientsInfos().remove(patientsInfo);
		patientsInfo.setHlsGroup(null);

		return patientsInfo;
	}

}