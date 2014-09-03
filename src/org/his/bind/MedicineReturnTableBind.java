package org.his.bind;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import multilingual.Language;

public class MedicineReturnTableBind extends AbstractTableModel implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 720456820326063203L;

	private static final Language LANG = Language.getInstance();

	public static final String[] COL_NAMES_FOR_RETURN = {
			LANG.getString("COL_MEDICINE_CODE"),
			LANG.getString("COL_MEDICINE_NAME"),
			LANG.getString("COL_MEDICINE_DOSAGE"),
			LANG.getString("COL_MEDICINE_UNIT"),
			LANG.getString("COL_MEDICINE_QUANTITY"),
			LANG.getString("COL_MEDICINE_UNIT_PRICE"),
			LANG.getString("COL_MEDICINE_TOTAL_PRICE") };

	private static final int COL_MEDICINE_CODE = 0;
	private static final int COL_MEDICINE_NAME = 1;
	private static final int COL_MEDICINE_DOSAGE = 2;
	private static final int COL_MEDICINE_UNIT = 3;
	private static final int COL_MEDICINE_QUANTITY = 4;
	private static final int COL_MEDICINE_UNIT_PRICE = 5;
	private static final int COL_MEDICINE_TOTAL_PRICE = 6;

	public MedicineReturnTableBind(ArrayList<ArrayList<String>> medicinesInfo) {
		this.medicinesInfo = medicinesInfo;
	}

	private ArrayList<ArrayList<String>> medicinesInfo;

	@Override
	public int getRowCount() {
		return medicinesInfo.size()+1;
	}

	@Override
	public int getColumnCount() {
		return COL_NAMES_FOR_RETURN.length;
	}

	@Override
	public String getColumnName(int column) {
		return COL_NAMES_FOR_RETURN[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex == 0) {
			return COL_NAMES_FOR_RETURN[columnIndex];
		}
		if (rowIndex > medicinesInfo.size()) {
			return null;
		}
		return medicinesInfo.get(rowIndex-1).get(columnIndex);
	}
}
