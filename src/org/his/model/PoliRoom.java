package org.his.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the poli_room database table.
 * 
 */
@Entity
@Table(name="poli_room")
public class PoliRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String guid;

	private String name;

	private String type;

	//bi-directional many-to-one association to Policlinic
	@ManyToOne
	@JoinColumn(name="poli_guid")
	private Policlinic policlinic;

	//bi-directional many-to-one association to ShiftTable
	@OneToMany(mappedBy="poliRoom")
	private List<ShiftTable> shiftTables;

	public PoliRoom() {
	}

	public String getGuid() {
		return this.guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Policlinic getPoliclinic() {
		return this.policlinic;
	}

	public void setPoliclinic(Policlinic policlinic) {
		this.policlinic = policlinic;
	}

	public List<ShiftTable> getShiftTables() {
		return this.shiftTables;
	}

	public void setShiftTables(List<ShiftTable> shiftTables) {
		this.shiftTables = shiftTables;
	}

	public ShiftTable addShiftTable(ShiftTable shiftTable) {
		getShiftTables().add(shiftTable);
		shiftTable.setPoliRoom(this);

		return shiftTable;
	}

	public ShiftTable removeShiftTable(ShiftTable shiftTable) {
		getShiftTables().remove(shiftTable);
		shiftTable.setPoliRoom(null);

		return shiftTable;
	}

}