package org.his.bind;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import multilingual.Language;

import org.his.model.PatientsInfo;

public class PatientsInfoJPATable extends AbstractTableModel implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5911893676376693604L;

	private static final Language LANG = Language.getInstance();

	public static final String[] COL_NAMES = { LANG.getString("COL_NO"),
			LANG.getString("COL_NAME"), LANG.getString("COL_BIRTH"),
			LANG.getString("COL_PHONE"), LANG.getString("COL_CELLPHONE"),
			LANG.getString("COL_TOWN"), LANG.getString("COL_ADDRESS") };

	private static final int COL_NO = 0;
	private static final int COL_NAME = 1;
	private static final int COL_BIRTH = 2;
	private static final int COL_PHONE = 3;
	private static final int COL_CELLPHONE = 4;
	private static final int COL_TOWN = 5;
	private static final int COL_ADDRESS = 6;

	public final int MAX_ROWS_OF_PAGE = 25;

	public PatientsInfoJPATable(List<PatientsInfo> patiensInfo) {
		this.patiensInfo = patiensInfo;
	}

	private List<PatientsInfo> patiensInfo;

	@Override
	public int getRowCount() {
		return patiensInfo.size();
	}

	@Override
	public int getColumnCount() {
		return COL_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COL_NAMES[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		PatientsInfo pInfo = patiensInfo.get(rowIndex);
		switch (columnIndex) {
		case COL_NO:
			return pInfo.getPNo();
		case COL_NAME:
			return String.format("%s %s", pInfo.getFirstname(),
					pInfo.getLastname());
		case COL_BIRTH:
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return (pInfo.getBirth() == null)? "" : sdf.format(pInfo.getBirth());
		case COL_PHONE:
			return pInfo.getPhone();
		case COL_CELLPHONE:
			return pInfo.getCellPhone();
		case COL_TOWN:
			return pInfo.getTown();
		case COL_ADDRESS:
			return pInfo.getAddress();
		}
		return null;
	}

}
