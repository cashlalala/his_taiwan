package org.his.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the map_country database table.
 * 
 */
@Entity
@Table(name="map_country")
public class MapCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private String name;

	public MapCountry() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}