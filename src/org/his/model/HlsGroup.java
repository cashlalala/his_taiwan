package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String value;

	private String descrition;

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

}