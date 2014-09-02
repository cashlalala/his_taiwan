package org.his.bind;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public MedicineReturnTableBind(ResultSet medicinesInfo) {
		this.medicinesInfo = medicinesInfo;
	}

	private ResultSet medicinesInfo;

	@Override
	public int getRowCount() {
		try {
			medicinesInfo.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int counter = 0;

		try {
			while (medicinesInfo.next()) {
				counter = counter + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return counter;
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
		try {
			medicinesInfo.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		int i = 0;
		while (i <= rowIndex) {
			try {
				medicinesInfo.next();
				i=i+1;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		switch (columnIndex) {
		case COL_MEDICINE_CODE:
			try {
				return medicinesInfo.getString(COL_MEDICINE_CODE+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		case COL_MEDICINE_NAME:
			try {
				return medicinesInfo.getString(COL_MEDICINE_NAME+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		case COL_MEDICINE_DOSAGE:
			try {
				return medicinesInfo.getString(COL_MEDICINE_DOSAGE+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		case COL_MEDICINE_UNIT:
			try {
				return medicinesInfo.getString(COL_MEDICINE_UNIT+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		case COL_MEDICINE_QUANTITY:
			try {
				return medicinesInfo.getString(COL_MEDICINE_QUANTITY+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		case COL_MEDICINE_UNIT_PRICE:
			try {
				return medicinesInfo.getString(COL_MEDICINE_UNIT_PRICE+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		case COL_MEDICINE_TOTAL_PRICE:
			try {
				return medicinesInfo.getString(COL_MEDICINE_UNIT_PRICE+1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
