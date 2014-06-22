package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the policlinic database table.
 * 
 */
@Entity
public class Policlinic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String guid;

	private int lim;

	private String name;

	private String typ;

	public Policlinic() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getLim() {
		return this.lim;
	}

	public void setLim(int lim) {
		this.lim = lim;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

}