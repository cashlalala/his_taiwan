package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the health_teach_item database table.
 * 
 */
@Entity
@Table(name="health_teach_item")
public class HealthTeachItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String code;

	private String item;

	//bi-directional many-to-one association to HealthTeach
	@OneToMany(mappedBy="healthTeachItem")
	private List<HealthTeach> healthTeaches;

	public HealthTeachItem() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public List<HealthTeach> getHealthTeaches() {
		return this.healthTeaches;
	}

	public void setHealthTeaches(List<HealthTeach> healthTeaches) {
		this.healthTeaches = healthTeaches;
	}

	public HealthTeach addHealthTeach(HealthTeach healthTeach) {
		getHealthTeaches().add(healthTeach);
		healthTeach.setHealthTeachItem(this);

		return healthTeach;
	}

	public HealthTeach removeHealthTeach(HealthTeach healthTeach) {
		getHealthTeaches().remove(healthTeach);
		healthTeach.setHealthTeachItem(null);

		return healthTeach;
	}

}