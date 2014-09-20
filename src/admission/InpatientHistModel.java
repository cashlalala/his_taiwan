package admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import multilingual.Language;

public class InpatientHistModel extends AbstractTableModel {

	public InpatientHistModel() {
		this.model = new ArrayList<HashMap<Integer, String>>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Language LANG = Language.getInstance();

	public static final String[] COL_NAMES = { " ", LANG.getString("DATE"),
			LANG.getString("SHIFT"), LANG.getString("COL_POLICLINIC"),
			LANG.getString("DOCTOR"), "GUID", "TYPE" };

	private static final int COL_NO = 0;
	// private static final int COL_DATE = 1;
	// private static final int COL_SHIFT = 2;
	// private static final int COL_POLI = 3;
	// private static final int COL_DOCTOR = 4;
	// private static final int COL_GUID = 5;
	// private static final int COL_TYPE = 6;

	private List<HashMap<Integer, String>> model;

	public List<HashMap<Integer, String>> getModel() {
		return model;
	}

	public void setModel(List<HashMap<Integer, String>> model) {
		this.model = model;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return model.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return COL_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return COL_NAMES[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Map<Integer, String> item = model.get(rowIndex);
		switch (columnIndex) {
		case COL_NO:
			return rowIndex + 1;
		default:
			return item.get(columnIndex - 1);
		}
	}

}
