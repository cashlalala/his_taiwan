package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the religion database table.
 * 
 */
@NamedQueries ({
    @NamedQuery(
        name="QueryReligions",
        query="SELECT religion FROM Religion religion"),
    @NamedQuery(
        name="QueryReligionByValue",
        query="SELECT religion FROM Religion religion WHERE religion.value = :val")
})

@Entity
@Table(name="religion")
public class Religion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String value;

	private String descrition;

	public Religion() {
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