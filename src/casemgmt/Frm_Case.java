package casemgmt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import laboratory.Frm_LabDM;
import multilingual.Language;
import casemgmt.TableTriStateCell.TriStateCellEditor;
import casemgmt.TableTriStateCell.TriStateCellRenderer;
import cc.johnwu.date.DateInterface;
import cc.johnwu.date.DateMethod;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

import common.Constant;
import common.PrintTools;
import common.TabTools;
import common.Tools;

import diagnosis.Frm_DiagnosisPrescription;
import errormessage.StoredErrorMessage;

public class Frm_Case extends javax.swing.JFrame implements DateInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2326826109148182159L;

	private String m_Pno;

	private DefaultTableModel m_PrescriptionModel;
	private int m_PrescriptionRowNo; // Prescription table row number
	// ---AUTO COMPLETE
	private String m_AutoTable;
	private String[] m_AutoColumn; // AutoComplete 印出的欄位
	private String m_AutoColumnName; // 資料庫比對的Column name
	private JTextField m_AutoTxt = new JTextField();
	private JScrollPane m_AutoLocationSpane;
	private String[] m_AutoListValue;
	private int m_RsRowCount;
	private int m_Row;
	// focus 的 table 成為目前系統的暫存變數
	private JTable m_SelectTable = null;
	private DefaultTableModel m_SelectTableModel = null;
	private Map<Object, Object> m_PrescriptionHashMap = new LinkedHashMap<Object, Object>(); // 儲存Prescription看診資料
																								// 用來比對避免重複相同診斷
	private int m_SelectTableRowNo = 0;
	private int m_SelectTableNo;
	private int m_InsertRow;
	private int m_SelectTableAddRowLimitColumn; // TABLE 按下 ENTER 判斷是否允許增加新行的欄位
	private Map<Object, Object> m_SelectTableHashMap = new LinkedHashMap<Object, Object>();
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	private boolean m_FinishState; // T 完成 F 第一次
	private String m_RegGuid; // registration guid
	private int m_ModifyCount = 0; // 修改次數
	private String m_From;

	private static Language lang = Language.getInstance();

	public static void enableComponents(Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				enableComponents((Container) component, enable);
			}
		}
	}

	public Frm_Case(String p_no, String regGuid, boolean finishState,
			String from) {
		m_RegGuid = regGuid;
		m_Pno = p_no;
		initComponents();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				setFrmClose();
			}
		});

		dia_RevisitTime.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				setCloseRevisitTime();
			}
		});
		if (from.equals("dia")) {
			// enableComponents(pan_AssComp, false);
			mnb.setVisible(false);
			pan_ConfEdu.tab_HealthTeach.setEnabled(true);
			tab_Prescription.setEnabled(false);
			jTabbedPane1.setSelectedIndex(1);
		} else if (from.equals("medicine")) {
			jPanel4.setVisible(false);
			mnb.setVisible(false);
			jTabbedPane1.remove(3);
			jTabbedPane1.remove(2);
			jTabbedPane1.remove(1);
			jTabbedPane1.remove(0);
			this.setTitle("Medicine Education");
		}
		btn_Ddate_Save.setVisible(true);
		btn_Ddate_Save.setEnabled(true);
		m_From = from;
		m_Pno = p_no;
		m_RegGuid = regGuid;
		m_FinishState = finishState;
		showWhoUpdate(m_FinishState);
		this.setExtendedState(Frm_Case.MAXIMIZED_BOTH); // 最大化
		this.setLocationRelativeTo(this);
		init();
		initTable();
		if (finishState)
			setHistoryPre();
		setOverValue();
	}

	// 初始化

	private void init() {
		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable

			@Override
			public void windowClosing(WindowEvent windowevent) {
				// jButton11ActionPerformed(null);
			}
		});
		try {
			String sql = "SELECT * FROM patients_info WHERE p_no = '"
					+ this.m_Pno + "'";
			// 取出病患基本資料
			ResultSet rs = DBC.executeQuery(sql);
			rs.next();
			m_Pno = rs.getString("p_no");
			this.lab_Pno.setText(rs.getString("p_no"));
			this.lab_Name.setText(rs.getString("firstname") + " "
					+ rs.getString("lastname"));
			this.lab_Gender.setText(rs.getString("gender"));
			this.lab_Age.setText((rs.getDate("birth") == null) ? ""
					: DateMethod.getAgeWithMonth(rs.getDate("birth")));
			this.txt_Height.setText(rs.getString("height"));
			this.txt_Weight.setText(rs.getString("weight"));
			this.txt_AC.setText(Tools.getPrescriptionResult("BGAc", m_Pno));
			this.txt_PC.setText(Tools.getPrescriptionResult("BGPc", m_Pno));
			this.txt_ST.setText(Tools.getPrescriptionResult("St.", m_Pno));
			if (rs.getString("education") != null)
				this.com_edu.setSelectedIndex(rs.getInt("education"));
		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		// Save按鍵初始化
		btn_Ddate_Save.setEnabled(true);
		pan_ConfEdu.btn_ConSave.setEnabled(false);
		btn_PreSave.setEnabled(false);
		btn_DheSave.setEnabled(false);
	}

	private void setCloseRevisitTime() {
		this.setEnabled(true);
		dia_RevisitTime.dispose();
	}

	// 建立 TABLE
	public void initTable() {
		Object getModelAndRowNo[] = new Object[1]; // getModelAndRowNo[0] get
													// model getModelAndRowNo[1]
													// get rowNo
		// -----tab_Prescription-------------------------------------------------
		String[] prescriptionTitle = { " ", "Code", "Item", "Body Part", "Type" }; // table表頭
		int[] prescriptionColumnEditable = { 1, 3 }; // 可編輯欄位
		getModelAndRowNo = TabTools.setTableEditColumn(m_PrescriptionModel,
				this.tab_Prescription, prescriptionTitle,
				prescriptionColumnEditable, m_PrescriptionRowNo);
		m_PrescriptionModel = (DefaultTableModel) getModelAndRowNo[0];
		m_PrescriptionRowNo = Integer.parseInt(getModelAndRowNo[1].toString());

		TableColumn prescriptionColumnNo = tab_Prescription.getColumnModel()
				.getColumn(0);
		TableColumn prescriptionColumnCode = tab_Prescription.getColumnModel()
				.getColumn(1);
		TableColumn prescriptionColumnName = tab_Prescription.getColumnModel()
				.getColumn(2);
		TableColumn prescriptionColumnPlace = tab_Prescription.getColumnModel()
				.getColumn(3);
		tab_Prescription.getColumnModel().getColumn(4);
		prescriptionColumnNo.setMaxWidth(30);
		prescriptionColumnCode.setMaxWidth(80);
		prescriptionColumnName.setPreferredWidth(650);
		prescriptionColumnPlace.setPreferredWidth(80);
		prescriptionColumnCode.setCellEditor(new DefaultCellEditor(m_AutoTxt)); // textField加入table
		TabTools.setHideColumn(tab_Prescription, 3);

		// ---- tab_MedicineTeach 藥品衛教---------------------
		Object[][] dataArray_MT = null;
		ResultSet rs_MT = null;
		try {
			Object[] title_MT = { "", "Code", "Item", "s_id", "Uesr", "Check",
					"ps" };
			String sql_MT = "SELECT medicine_stock.m_code AS 'code', medicines.item AS 'item', medicine_stock.ps AS 'ps', medicine_stock.teach_complete AS 'teach_complete', concat(staff_info.firstname,'  ',staff_info.lastname) AS 'user', medicine_stock.s_id AS 'sid'"
					+ " FROM  medicine_stock LEFT JOIN staff_info ON staff_info.s_id = medicine_stock.s_id , medicines, registration_info, outpatient_services "
					+ " WHERE  medicines.code= medicine_stock.m_code "
					+ " AND outpatient_services.reg_guid = registration_info.guid "
					+ " AND registration_info.p_no = '"
					+ m_Pno
					+ "' "
					+ " AND registration_info.guid = '" + m_RegGuid + "'";
			System.out.println(sql_MT);
			rs_MT = DBC.executeQuery(sql_MT);
			rs_MT.last();
			dataArray_MT = new Object[rs_MT.getRow()][7];
			rs_MT.beforeFirst();

			int i = 0;
			while (rs_MT.next()) {
				dataArray_MT[i][0] = i + 1;
				dataArray_MT[i][1] = rs_MT.getString("code");
				dataArray_MT[i][2] = rs_MT.getString("item");
				dataArray_MT[i][3] = rs_MT.getString("sid");

				if (rs_MT.getString("user") != null) {
					dataArray_MT[i][4] = rs_MT.getString("user");
				}

				if (rs_MT.getString("teach_complete") != null
						&& rs_MT.getString("teach_complete").equals("1")) {
					dataArray_MT[i][5] = true;
				} else {
					dataArray_MT[i][5] = false;
				}
				if (rs_MT.getString("ps") != null) {
					dataArray_MT[i][6] = rs_MT.getString("ps");
				}
				i++;
			}

			DefaultTableModel TableModel = new DefaultTableModel(dataArray_MT,
					title_MT) {

				/**
						 * 
						 */
				private static final long serialVersionUID = -5596812643978453160L;

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					if (columnIndex == 5 || columnIndex == 6) {
						return true;
					} else {
						return false;
					}
				}
			};
			tab_MedicineTeach.setModel(TableModel);
			TableColumn columnNumber = this.tab_MedicineTeach.getColumnModel()
					.getColumn(0);
			common.TabTools.setHideColumn(tab_MedicineTeach, 1);
			TableColumn columnName = this.tab_MedicineTeach.getColumnModel()
					.getColumn(2);
			common.TabTools.setHideColumn(tab_MedicineTeach, 3);
			TableColumn columnUser = this.tab_MedicineTeach.getColumnModel()
					.getColumn(4);
			TableColumn columnChoose = this.tab_MedicineTeach.getColumnModel()
					.getColumn(5);
			TableColumn columnPs = this.tab_MedicineTeach.getColumnModel()
					.getColumn(6);
			// set column width
			columnNumber.setMaxWidth(30);
			columnName.setPreferredWidth(200);
			columnUser.setPreferredWidth(50);
			columnChoose.setMaxWidth(40);
			columnChoose.setCellRenderer(new TriStateCellRenderer());
			columnChoose.setCellEditor(new TriStateCellEditor());
			columnPs.setPreferredWidth(100);
			tab_MedicineTeach.setRowHeight(30);
		} catch (SQLException e) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					e);
			ErrorMessage.setData(
					"Case",
					"Frm_Case",
					"initTable()",
					e.toString().substring(e.toString().lastIndexOf(".") + 1,
							e.toString().length()));
		} finally {
			try {
				DBC.closeConnection(rs_MT);
			} catch (SQLException e) {
				ErrorMessage
						.setData(
								"Diagnosis",
								"Frm_DiagnosisTherapy",
								"setModel(String condition, String state) - DBC.closeConnection",
								e.toString().substring(
										e.toString().lastIndexOf(".") + 1,
										e.toString().length()));
			}
		}
	}

	public void setFrmClose() {
		if (m_From.equals("dia") || m_From.equals("medicine")) {
			// 關閉此視窗
			this.dispose();
		} else {
			// 開啟看診 視窗
			new worklist.Frm_WorkList(0, "case").setVisible(true);
			// 關閉此視窗
			this.dispose();
		}
	}

	private void setHistoryPre() {
		ResultSet rs = null;
		try {
			// 取出處置
			String sqlPrescription = "SELECT prescription.code, prescription_code.name, prescription_code.type, prescription.place "
					+ "FROM prescription, registration_info, prescription_code "
					+ "WHERE registration_info.guid = '"
					+ m_RegGuid
					+ "' "
					+ "AND prescription_code.code = prescription.code ";

			rs = DBC.executeQuery(sqlPrescription);
			int rowPrescription = 0;
			while (rs.next()) {
				tab_Prescription.setValueAt(rs.getString("code"),
						rowPrescription, 1);
				tab_Prescription.setValueAt(rs.getString("name"),
						rowPrescription, 2);
				tab_Prescription.setValueAt(rs.getString("place"),
						rowPrescription, 3);
				tab_Prescription.setValueAt(rs.getString("type"),
						rowPrescription, 4);
				if ((rowPrescription + 2) > tab_Prescription.getRowCount()) {
					m_PrescriptionModel.addRow(new Vector<Object>());
					tab_Prescription.setValueAt(++m_PrescriptionRowNo,
							tab_Prescription.getRowCount() - 1, 0);
				}
				rowPrescription++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {

			}
		}
	}

	// 取得檢驗值
	@SuppressWarnings("unused")
	private double getResult(String name) {
		try {
			ResultSet rs = null;
			String sql = "SELECT prescription.result AS result "
					+ "FROM prescription, patients_info, outpatient_services, registration_info, prescription_code  "
					+ "WHERE prescription.os_guid = outpatient_services.guid "
					+ "AND outpatient_services.reg_guid = registration_info.guid "
					+ "AND registration_info.p_no = patients_info.p_no "
					+ "AND prescription_code.name = '" + name + "' "
					+ "AND patients_info.p_no = '" + m_Pno + "'";
			rs = DBC.executeQuery(sql);
			if (rs.next()) {
				return rs.getDouble("result");
			} else {
				return -1;
			}

		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
			return -1;
		}
	}

	public void setOverValue() {
		int check = 0;
		String tg = Tools.getPrescriptionResult("TG", m_Pno);
		String hdl = Tools.getPrescriptionResult("HDL", m_Pno);
		String bgac = Tools.getPrescriptionResult("BGAc", m_Pno);
		String sbp = pan_AssComp.txt_sbp.getText();
		String waist = pan_CompliComp.txt_waist.getText();

		// ----------------------------
		if (!tg.equals("")) {
			if (Double.parseDouble(tg) >= 150) {
				pan_CompliComp.lab_tg.setText("Yes");
				check++;
			} else {
				pan_CompliComp.lab_tg.setText("No");
			}
		} else {
			pan_CompliComp.lab_tg.setText("");
		}
		// ----------------------------
		if (!hdl.equals("")) {
			if (lab_Gender.getText().equals("M")
					&& Double.parseDouble(hdl) < 40
					|| lab_Gender.getText().equals("F")
					&& Double.parseDouble(hdl) < 50) {
				pan_CompliComp.lab_hdl.setText("Yes");
				check++;
			} else {
				pan_CompliComp.lab_hdl.setText("No");
			}
		} else {
			pan_CompliComp.lab_hdl.setText("");
		}
		// ----------------------------
		if (!sbp.equals("")) {
			if (Double.parseDouble(sbp) >= 130) {
				pan_CompliComp.lab_sbp.setText("Yes");
				check++;
			} else {
				pan_CompliComp.lab_sbp.setText("No");
			}
		} else {
			pan_CompliComp.lab_sbp.setText("");
		}
		// ---------------------------
		if (!bgac.equals("")) {
			if (Double.parseDouble(bgac) >= 5.6) {
				pan_CompliComp.lab_bgac.setText("Yes");
				check++;
			} else {
				pan_CompliComp.lab_bgac.setText("No");
			}
		} else {
			pan_CompliComp.lab_bgac.setText("");
		}
		if (!bgac.equals("") && lab_Gender.getText() != null && waist != null
				&& !waist.trim().equals("")) {
			if ((lab_Gender.getText().equals("M") && Double.parseDouble(waist) >= 90)
					|| (lab_Gender.getText().equals("F") && Double
							.parseDouble(waist) >= 80)) {

				check++;
			}
		}

		if (check >= 3) {
			pan_CompliComp.lab_dm.setText("Yes");
		} else {
			pan_CompliComp.lab_dm.setText("No");
		}

	}

	// AUTOCOMPLET 顯示
	// 參數：span的point table的point span的寬度 span的高度 編號欄位的寬度 欄位高度 搜尋的資料庫名稱
	// 顯示的資料庫欄位[] 作為條件比對的欄位名稱
	public void showAutoComplete(Point point, Point barPoint, int width,
			int height, int NoColumnWidth, int row, String DBtable,
			String[] DBColumn, String ColumnName) {

		String[] barPointArray = barPoint.toString()
				.replace("java.awt.Point[x=", "").replace(",y=", "")
				.replace("]", "").trim().split("-");
		int bar = 0;
		if (barPointArray.length == 2) {
			bar = Integer.parseInt(barPointArray[1]);
		}
		this.m_Row = row;
		this.m_AutoTable = DBtable;
		this.m_AutoColumn = DBColumn;
		this.m_AutoColumnName = ColumnName;
		// point取出 X Y
		int x = point.x;
		int y = point.y;
		y += (row + 1) * height + 15 - bar;
		x += NoColumnWidth;
		dia.setLocation(x, y); // 設定位置
		dia.setSize(width - NoColumnWidth, Constant.AUTOCOMPLETE_HEIGHT); // 設定大小
	}

	// 焦點在於 AUTOCOMPLETE 的按鍵判斷 限制輸入值

	public boolean isKeyIn(String str) {
		Pattern pattern = Pattern.compile("[0-9,.,-,~,A-Z,a-z]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		} else {
			return true;
		}
	}

	// 顯示 AUTOCOMPLETE 前的按鍵判斷 迴避非值輸入
	public boolean isAllowKeyIn(KeyEvent evt) {
		// System.out.println("顯示 auto 前的判斷");
		if (m_SelectTable.getSelectedColumn() == 1
				&& evt.getKeyCode() != KeyEvent.VK_UP
				&& evt.getKeyCode() != KeyEvent.VK_DOWN
				&& evt.getKeyCode() != KeyEvent.VK_RIGHT
				&& evt.getKeyCode() != KeyEvent.VK_LEFT
				&& evt.getKeyCode() != KeyEvent.VK_DELETE
				&& evt.getKeyCode() != KeyEvent.VK_ENTER
				&& evt.getKeyCode() != KeyEvent.VK_SPACE
				&& evt.getKeyCode() != KeyEvent.VK_ESCAPE
				&& evt.getKeyCode() != KeyEvent.VK_SUBTRACT
				&& evt.getKeyCode() != KeyEvent.VK_ADD
				&& evt.getKeyCode() != KeyEvent.VK_PAGE_DOWN
				&& evt.getKeyCode() != KeyEvent.VK_PAGE_UP) {
			return true;
		}
		return false;
	}

	// 焦點在於 AUTOCOMPLETE 按鍵輸入 進行搜尋
	public void setTxt(String keyWord) {
		// System.out.println("按下 " +keyWord + " 搜尋");
		m_AutoTxt.setText(m_AutoTxt.getText() + keyWord);
		setAutoCompleteList(m_AutoTxt.getText());
	}

	// 焦點在於 AUTOCOMPLETE 按下 Backspace 刪除 m_AutoTxt 最後一個字元
	public void setBackspaceTxt() {
		// System.out.println("按下Backspace刪除最後一個字元 ");
		if (m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(), 2) != null) {
			// System.out.println("移除hash map "+m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(),
			// 1 ));
			m_SelectTableHashMap.remove(m_SelectTable
					.getValueAt(m_SelectTable.getSelectedRow(), 1).toString()
					.trim());
			for (int i = 2; i < m_SelectTable.getColumnCount(); i++) {
				m_SelectTable.setValueAt(null, m_SelectTable.getSelectedRow(),
						i);
			}
		}
		if (m_AutoTxt.getText().length() != 0) {
			m_AutoTxt.setText(m_AutoTxt.getText().substring(0,
					m_AutoTxt.getText().length() - 1));
			m_SelectTable.setValueAt(m_AutoTxt.getText(),
					m_SelectTable.getSelectedRow(), 1);
			setAutoCompleteList(m_AutoTxt.getText());
		}
	}

	// AUTOCOMPLET split陣列的value丟入各個tableColumn
	public void setTableValue(String[] value) {
		// System.out.println("split 傳入陣列值 ");
		switch (m_SelectTableNo) {
		case 2: // tab_Prescription
			m_AutoTxt.setText(value[0]);
			tab_Prescription.setValueAt(value[0],
					tab_Prescription.getSelectedRow(), 1);
			tab_Prescription.setValueAt(value[1],
					tab_Prescription.getSelectedRow(), 2);
			tab_Prescription.setValueAt(value[2],
					tab_Prescription.getSelectedRow(), 4);
			break;

		}
	}

	// AUTOCOMPLETE 搜尋不到值 清空前一個遺留下的值
	public void setClearTableRow(int row) {
		// System.out.println("auto搜不到值  清空前一個遺留下的值 ROW 為 " + row);

		for (int i = 1; i < m_SelectTable.getColumnCount(); i++) {
			if (m_SelectTable.getSelectedRow() != -1
					&& m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(),
							i) != null) {
				m_SelectTable.setValueAt(null, row, i);
			}
		}
	}

	// AUTOCOMPLETE 選單消失 移除單元格的編輯0
	public void setLostAutoCompleteEdit() {
		this.list_Menu.clearSelection();
		dia.setVisible(false);
		// System.out.println("選單消失 移除單元格的編輯");
		m_AutoTxt.setText(null);
		m_SelectTable.removeEditor();
	}

	// hsql 搜尋顯示出資料表 傳入要搜尋的資料表
	public void setAutoCompleteList(String condition) {
		System.out.println("搜尋條件 " + condition);
		if (condition.equals("")) {
			setClearTableRow(m_Row);
			btn_CloseActionPerformed(null);
		} else {
			String[] list = null;
			String sql = null;
			if (m_AutoTable.equals("prescription_code")) {
				// System.out.println("搜尋用 處置 語法");
				sql = "SELECT * FROM " + m_AutoTable + "  " + "WHERE LOWER("
						+ m_AutoColumnName + ") LIKE LOWER('" + condition
						+ "%') " + "AND effective = 1  ORDER BY "
						+ m_AutoColumnName + "";
			}
			int index = 0;
			ResultSet rs = null;
			try {
				rs = DBC.localExecuteQuery(sql);
				rs.last();
				m_RsRowCount = rs.getRow();
				setListheight();
				if (m_RsRowCount < Constant.AUTOCOMPLETE_SHOW_ROW) {
					list = new String[m_RsRowCount];
				} else {
					list = new String[Constant.AUTOCOMPLETE_SHOW_ROW + 1];
				}

				rs.beforeFirst();
				String str = "";
				while (rs.next()) {
					if (rs.getRow() > Constant.AUTOCOMPLETE_SHOW_ROW + 1) {
						break;
					} else {
						str = "";
						for (int i = 0; i < m_AutoColumn.length; i++) {
							if (m_AutoColumn.length > 2 && i == 1
									&& m_AutoTable.equals("medicines")) {
								str += ("" + "       "); // Medicine hide
							} else {
								str += (rs.getString(m_AutoColumn[i]).trim() + "       ");
							}
						}
						list[index++] = str;
					}
				}
				list_Menu.setListData(list);

				list_Menu.removeSelectionInterval(0,
						Constant.AUTOCOMPLETE_SHOW_ROW + 1);
			} catch (SQLException e) {
				// Logger.getLogger(Frm_DiagnosisInfo.class.getName()).log(Level.SEVERE,
				// null, e);
				ErrorMessage.setData(
						"Diagnosis",
						"Frm_DiagnosisInfo",
						"setAutoCompleteList(String condition)",
						e.toString().substring(
								e.toString().lastIndexOf(".") + 1,
								e.toString().length()));
			} finally {
				try {
					DBC.closeConnection(rs);
				} catch (SQLException e) {
					ErrorMessage
							.setData(
									"Diagnosis",
									"Frm_DiagnosisInfo",
									"setAutoCompleteList(String condition) - DBC.closeConnection(rs)",
									e.toString().substring(
											e.toString().lastIndexOf(".") + 1,
											e.toString().length()));
				}
			}
		}
	}

	// AUTOCOMPLETE 值顯示到經過 split 轉為陣列丟入 TABLE
	public void getSplitValue() {
		if (list_Menu.getSelectedValue() != null) {
			m_AutoListValue = list_Menu.getSelectedValue().toString()
					.split("       ");
			// 回傳table autoCompleteList表單值切割的陣列
			if (m_AutoListValue.length > 1) {
				setTableValue(m_AutoListValue);
			} else if (m_AutoListValue.length == 1) {
				setTableValue(m_AutoListValue);
			} else {
				m_AutoListValue = new String[5];
				for (int i = 1; i < m_AutoListValue.length; i++) {
					m_AutoListValue[i] = null;
				}
				setTableValue(m_AutoListValue);
			}
		}
	}

	// 輸入值傳入 AUTOCOMPLETE 進行HSQL搜尋
	public void getAutoCompleteValue(String value) {
		// System.out.println("顯示auto 值傳到autocomplete "+ value);
		if (m_SelectTable.getSelectedColumn() == 1
				&& !m_AutoTxt.getText().equals("")) {
			TableColumn columnNo = m_SelectTable.getColumnModel().getColumn(0);
			showAutoComplete(m_AutoLocationSpane.getLocationOnScreen(),
					m_SelectTable.getLocation(), m_SelectTable.getWidth(),
					m_SelectTable.getRowHeight(), columnNo.getWidth(),
					m_SelectTable.getSelectedRow(), m_AutoTable, m_AutoColumn,
					m_AutoColumnName);
			setAutoCompleteList(value);
			dia.setVisible(true);
		}
	}

	// TABLE 按鍵進行 ROW 增加 或清除值
	public int setRowValue(KeyEvent evt) {
		m_SelectTable.removeEditor();
		// 上一行特定欄位不為空白才能增加行
		if (evt.getKeyCode() == KeyEvent.VK_ENTER
				&& m_SelectTable.getValueAt(m_SelectTableRowNo - 1,
						m_SelectTableAddRowLimitColumn) != null
				&& m_SelectTable.getValueAt(m_SelectTableRowNo - 1,
						m_SelectTableAddRowLimitColumn) != null
				&& !m_SelectTable
						.getValueAt(m_SelectTableRowNo - 1,
								m_SelectTableAddRowLimitColumn).toString()
						.trim().equals("")
				&& !m_SelectTable
						.getValueAt(m_SelectTableRowNo - 1,
								m_SelectTableAddRowLimitColumn).toString()
						.trim().equals("")) {
			m_SelectTableModel.addRow(new Vector<Object>());
			m_SelectTable.setValueAt(++m_SelectTableRowNo,
					m_SelectTable.getRowCount() - 1, 0);
		}
		// DELETE 鍵 清除資料
		if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
			if (m_SelectTable.getColumnCount() > 2
					&& m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(),
							2) != null) {
				// System.out.println("移除 hashmap code = "+m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(),
				// 1).toString().trim());
				m_SelectTableHashMap.remove(m_SelectTable
						.getValueAt(m_SelectTable.getSelectedRow(), 1)
						.toString().trim());
			}
			setClearRowValue();
			m_SelectTable.removeEditor();
		}
		return m_SelectTableRowNo;
	}

	// 判斷代碼是否有重複
	public boolean isCodeAtHashMap(Object code) {
		System.out.println(code);
		if (m_SelectTableHashMap.get(code) != null) { // 取出做判斷
			m_SelectTableHashMap.put(code.toString().trim(), code.toString()
					.trim()); // 在放回
			if (m_SelectTable.getSelectedRow() != -1) {
				setClearRowValue();
			}
			return false;
		} else if (code != null && !code.toString().trim().equals("")) {
			m_SelectTableHashMap.put(code.toString().trim(), code.toString()
					.trim());
			return true;
		}
		return true;
	}

	// 診斷代碼重複新增後再清除新增的該行
	public void setClearRowValue() {
		if (m_SelectTable.getSelectedRow() != -1) {
			for (int i = 1; i < m_SelectTable.getColumnCount(); i++) {
				m_SelectTable.setValueAt(null, m_SelectTable.getSelectedRow(),
						i);
			}
		} else {
			for (int i = 1; i < m_SelectTable.getColumnCount(); i++) {
				m_SelectTable.setValueAt(null, m_InsertRow, i);
			}
		}
	}

	// 設定 AUTOCOMPLETE 高度
	public void setListheight() {
		if (m_RsRowCount <= Constant.AUTOCOMPLETE_HEIGHT && m_RsRowCount != 0) {
			if (m_RsRowCount * 40 > Constant.AUTOCOMPLETE_HEIGHT) {
				dia.setSize(dia.getWidth(), Constant.AUTOCOMPLETE_HEIGHT);
			} else {
				dia.setSize(dia.getWidth(), m_RsRowCount * 40);
			}

		} else if (m_RsRowCount == 0) {
			dia.setSize(dia.getWidth(), 1);
			setClearTableRow(m_Row);
		} else {
			dia.setSize(dia.getWidth(), Constant.AUTOCOMPLETE_HEIGHT);
		}
	}

	// 移除所有TABLE 編輯狀態的欄位 與 取消光條選
	public void setAllRemoveEditAndSelection() {
		setRemoveEditAndSelection(this.tab_Prescription);
	}

	// 移除TABLE 編輯狀態的欄位 與 取消光條選
	public void setRemoveEditAndSelection(JTable table) {
		table.removeEditor();
		table.removeRowSelectionInterval(0, table.getRowCount() - 1);
	}

	public void showWhoUpdate(boolean m_finsh) {
		try {
			if (m_finsh) {
				ResultSet rs = DBC
						.executeQuery("SELECT * FROM case_manage , staff_info WHERE reg_guid = '"
								+ m_RegGuid + "'");
				while (rs.next()) {
					// 跳出Lable"顯示資訊"
					this.pan_CompliComp.Lab_record
							.setText("Last Modified By : "
									+ rs.getString("s_no") + " "
									+ rs.getString("firstname") + " "
									+ rs.getString("lastname")
									+ "    Last Modified : "
									+ rs.getString("finish_time"));
				}
			} else {
				// 跳出Lable"尚無資訊"
				this.pan_CompliComp.Lab_record.setText("No Modify the message");
			}
		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	// 套餐 V1&V3
	private void setV(String v) {

		tab_Prescription.removeRowSelectionInterval(0,
				tab_Prescription.getRowCount() - 1);
		tab_PrescriptionFocusGained(null);

		int[] column = { 1, 2, 4 };
		Collection<?> collection = Tools.getV(v).values();
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object[] value = iterator.next().toString().split("  ");
			if (isCodeAtHashMap(value[0].toString().trim()))
				setDiagnosisInfoTable(value, column);
		}
		btn_PreSave.setEnabled(true);
	}

	// 從其他 FRAME 增加一筆資料到指定 TABLE 指定 TABLE 欄位
	public void setDiagnosisInfoTable(Object[] value, int[] appointColumn) {
		btn_PreSave.setEnabled(true);
		for (int i = 0; i <= m_SelectTable.getRowCount(); i++) {

			if (i < m_SelectTable.getRowCount()
					&& (m_SelectTable.getValueAt(i, 1) == null || m_SelectTable
							.getValueAt(i, 1).toString().trim().equals(""))) {

				for (int t = 0; t < value.length; t++) {
					m_SelectTable.setValueAt(value[t], i, appointColumn[t]);
				}
				m_InsertRow = i;
				break;
			} else if (i == m_SelectTable.getRowCount()) {

				m_SelectTableModel.addRow(new Vector<Object>());
				m_SelectTable.setValueAt(m_SelectTableRowNo + 1,
						m_SelectTableRowNo, 0); // 設定行號
				for (int t = 0; t < value.length; t++) {
					m_SelectTable.setValueAt(value[t], m_SelectTableRowNo,
							appointColumn[t]);
				}
				m_SelectTableRowNo++;
				m_InsertRow = i;
				break;
			}
		}
	}

	// 儲存X-Ray照射部位與列印
	private void setPrint(boolean prescriptionState, boolean xrayState) {

		if (prescriptionState) {
			new PrintTools().DoPrint(12, this.m_RegGuid);
		}

		// if (xrayState) {
		// new PrintTools().DoPrint(4, this.m_RegGuid);
		// }
	}

	private void showEnterClinic() {
		try {
			String sql1 = "", sql2 = "";
			String getfinish = null;
			int getisd = 0;
			ResultSet rs = DBC
					.executeQuery("SELECT finish FROM registration_info WHERE guid = '"
							+ m_RegGuid + "'");
			while (rs.next()) {
				getfinish = rs.getString("finish");
			}
			ResultSet rs2 = DBC
					.executeQuery("SELECT isdiagnosis FROM case_manage WHERE guid = '"
							+ m_RegGuid + "'");
			while (rs2.next()) {
				getisd = Integer.parseInt(rs2.getString("isdiagnosis"));
			}
			if (m_FinishState) {
				// 跳出Message
				if (getfinish.equals("") || getfinish.equals("O")) {
					Object[] options = { "YES", "NO" };
					int dialog = JOptionPane.showOptionDialog(new Frame(),
							"Modify patient need to visit the doctor ?",
							"Message", JOptionPane.YES_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
					String fin = "";
					int isd = 0;
					if (dialog == 0) {
						// 選擇 YES 時
						if (getisd == 0) {
							isd = 1;
						} else {
							isd = 0;
						}
					} else {
						// 選擇 NO 時
					}
					sql1 = "UPDATE registration_info SET " + "finish  = '"
							+ fin + "'," + "case_finish = 'F'"
							+ "WHERE guid = '" + m_RegGuid + "' ";
					DBC.executeUpdate(sql1);
					m_ModifyCount += 1;
					sql2 = "UPDATE case_manage SET " + "finish_time = NOW() ,"
							+ "modify_count = '" + m_ModifyCount + "',"
							+ "isdiagnosis = '" + isd + "'"
							+ "WHERE reg_guid = '" + m_RegGuid + "'";
					DBC.executeUpdate(sql2);
				}
				// 開啟看診 視窗
				new worklist.Frm_WorkList(0, "case").setVisible(true);
				// 關閉此視窗
				this.dispose();
			} else {
				// 第一次
				// 跳出Message
				Object[] options = { "YES", "NO" };
				int dialog = JOptionPane.showOptionDialog(new Frame(),
						"If the patient need to visit the doctor ?", "Message",
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
				String fin = "";
				int isd = 1;
				if (dialog == 0) {
					// 選擇 YES 時
					isd = 1;
				} else {
					// 選擇 NO 時
					fin = "O";
					isd = 0;
				}
				sql1 = "UPDATE registration_info SET "
						+ "finish  = '"
						+ fin
						+ "', "
						+ "case_finish = 'F', "
						+ "touchtime = RPAD((SELECT CASE "
						+ "WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') "
						+ "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),COUNT(B.touchtime)) "
						+ "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') "
						+ "END touchtime "
						+ "FROM (SELECT touchtime FROM registration_info) AS B "
						+ "WHERE B.touchtime LIKE concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000') "
						+ "WHERE guid = '" + m_RegGuid + "'";
				DBC.executeUpdate(sql1);
				sql2 = "INSERT INTO case_manage (guid, reg_guid, finish_time, modify_count, s_no, isdiagnosis)"
						+ "VALUES (uuid() , '"
						+ m_RegGuid
						+ "', NOW() , "
						+ ""
						+ m_ModifyCount
						+ ","
						+ UserInfo.getUserNO()
						+ ",'"
						+ isd + "')";
				DBC.executeUpdate(sql2);
				// 開啟看診 視窗
				new worklist.Frm_WorkList(0, "case").setVisible(true);
				// 關閉此視窗
				this.dispose();
			}
		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		btn_Close = new javax.swing.JButton();
		dia = new javax.swing.JDialog();
		span_ListMenu = new javax.swing.JScrollPane();
		list_Menu = new javax.swing.JList();
		dia_RevisitTime = new javax.swing.JDialog();
		jButton2 = new javax.swing.JButton();
		jPanel13 = new javax.swing.JPanel();
		txt_PackageId = new javax.swing.JTextField();
		jLabel27 = new javax.swing.JLabel();
		jLabel28 = new javax.swing.JLabel();
		jLabel29 = new javax.swing.JLabel();
		txt_PackageType = new javax.swing.JTextField();
		jLabel31 = new javax.swing.JLabel();
		txt_ComeBackDays = new javax.swing.JTextField();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		pan_AssComp = new Tab_Assessment(m_Pno, m_RegGuid);
		pan_AssComp.setParent(this);
		pan_CompliComp = new Tab_Complication(m_Pno, m_RegGuid);
		pan_CompliComp.setParent(this);
		new javax.swing.JPanel();
		new javax.swing.JScrollPane();
		jPanel12 = new javax.swing.JPanel();
		pan_Prescription = new javax.swing.JPanel();
		span_Prescription = new javax.swing.JScrollPane();
		tab_Prescription = new javax.swing.JTable();
		btn_PreSave = new javax.swing.JButton();
		jPanel9 = new javax.swing.JPanel();
		jPanelFoot = new Tab_FootCase();
		pan_HIVComp = new Tab_HIVCase();
		pan_HIVComp.setParent(this);
		pan_ConfEdu = new Tab_ConfirmEducation(m_Pno, m_RegGuid);
		pan_ConfEdu.setParent(this);
		jScrollPane2 = new javax.swing.JScrollPane();
		tab_MedicineTeach = new javax.swing.JTable();
		btn_DheSave = new javax.swing.JButton();
		btn_CaseClose = new javax.swing.JButton();
		jPanel11 = new javax.swing.JPanel();
		jLabel60 = new javax.swing.JLabel();
		lab_Pno = new javax.swing.JLabel();
		btn_Ddate_Save = new javax.swing.JButton();
		jLabel85 = new javax.swing.JLabel();
		lab_Age = new javax.swing.JLabel();
		jLabel88 = new javax.swing.JLabel();
		lab_Gender = new javax.swing.JLabel();
		jLabel90 = new javax.swing.JLabel();
		lab_Name = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jLabel23 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		txt_PC = new javax.swing.JTextField();
		txt_AC = new javax.swing.JTextField();
		jLabel24 = new javax.swing.JLabel();
		jLabel25 = new javax.swing.JLabel();
		jLabel26 = new javax.swing.JLabel();
		txt_ST = new javax.swing.JTextField();
		jLabel7 = new javax.swing.JLabel();
		jLabel62 = new javax.swing.JLabel();
		txt_Height = new javax.swing.JTextField();
		jLabel63 = new javax.swing.JLabel();
		txt_Weight = new javax.swing.JTextField();
		jLabel71 = new javax.swing.JLabel();
		com_edu = new javax.swing.JComboBox();
		jLabel18 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();
		mnb = new javax.swing.JMenuBar();
		mn_Fiele = new javax.swing.JMenu();
		mnit_Lab = new javax.swing.JMenuItem();
		mnit_History = new javax.swing.JMenuItem();
		mnit_Close = new javax.swing.JMenuItem();
		menu_SetDM = new javax.swing.JMenu();
		jMenu1 = new javax.swing.JMenu();
		mnit_V1 = new javax.swing.JMenuItem();
		mnit_V2 = new javax.swing.JMenuItem();
		mnit_V3 = new javax.swing.JMenuItem();

		btn_Close.setText("Close");
		btn_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CloseActionPerformed(evt);
			}
		});

		dia.setAlwaysOnTop(true);
		dia.setModal(true);
		dia.setResizable(false);
		dia.setUndecorated(true);

		list_Menu
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		list_Menu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				list_MenuMouseClicked(evt);
			}
		});
		list_Menu.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				list_MenuKeyPressed(evt);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				list_MenuKeyReleased(evt);
			}
		});
		span_ListMenu.setViewportView(list_Menu);

		javax.swing.GroupLayout diaLayout = new javax.swing.GroupLayout(
				dia.getContentPane());
		dia.getContentPane().setLayout(diaLayout);
		diaLayout.setHorizontalGroup(diaLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				span_ListMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 235,
				Short.MAX_VALUE));
		diaLayout.setVerticalGroup(diaLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				span_ListMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 183,
				Short.MAX_VALUE));

		dia_RevisitTime.setTitle("Revisit");
		dia_RevisitTime.setMinimumSize(new java.awt.Dimension(480, 185));
		dia_RevisitTime.setResizable(false);

		jButton2.setText("OK");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jPanel13.setBackground(new java.awt.Color(246, 246, 246));

		txt_PackageId.setEditable(false);

		jLabel27.setText("Need to revisit the project:");

		jLabel28.setText("Package Codet:");

		jLabel29.setText("Next revisit will be in days is:");

		txt_PackageType.setEditable(false);

		jLabel31.setText("Days");

		javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(
				jPanel13);
		jPanel13.setLayout(jPanel13Layout);
		jPanel13Layout
				.setHorizontalGroup(jPanel13Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel13Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel13Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel29)
														.addComponent(jLabel28)
														.addComponent(jLabel27))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel13Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txt_PackageId,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE)
														.addComponent(
																txt_PackageType,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel13Layout
																		.createSequentialGroup()
																		.addComponent(
																				txt_ComeBackDays,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				237,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jLabel31)))
										.addContainerGap()));
		jPanel13Layout
				.setVerticalGroup(jPanel13Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel13Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel13Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel27)
														.addComponent(
																txt_PackageType,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel13Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel28)
														.addComponent(
																txt_PackageId,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel13Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel29)
														.addComponent(jLabel31)
														.addComponent(
																txt_ComeBackDays,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(14, Short.MAX_VALUE)));

		javax.swing.GroupLayout dia_RevisitTimeLayout = new javax.swing.GroupLayout(
				dia_RevisitTime.getContentPane());
		dia_RevisitTime.getContentPane().setLayout(dia_RevisitTimeLayout);
		dia_RevisitTimeLayout
				.setHorizontalGroup(dia_RevisitTimeLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								dia_RevisitTimeLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												dia_RevisitTimeLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jPanel13,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jButton2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																119,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		dia_RevisitTimeLayout
				.setVerticalGroup(dia_RevisitTimeLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								dia_RevisitTimeLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jPanel13,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton2)
										.addContainerGap()));

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Case Management");

		jTabbedPane1.addTab("Assessment", pan_AssComp);
		jTabbedPane1.addTab("Complication", pan_CompliComp);
		jTabbedPane1.addTab("Confirm the completion of health education",
				pan_ConfEdu);

		pan_Prescription.setBorder(javax.swing.BorderFactory
				.createTitledBorder(""));
		pan_Prescription.setPreferredSize(new java.awt.Dimension(813, 173));

		tab_Prescription.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] {

				}));
		tab_Prescription.setRowHeight(25);
		tab_Prescription.getTableHeader().setReorderingAllowed(false);
		tab_Prescription.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_PrescriptionMouseClicked(evt);
			}
		});
		tab_Prescription.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				tab_PrescriptionFocusGained(evt);
			}
		});
		tab_Prescription.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_PrescriptionKeyPressed(evt);
			}

			public void keyReleased(java.awt.event.KeyEvent evt) {
				tab_PrescriptionKeyReleased(evt);
			}
		});
		span_Prescription.setViewportView(tab_Prescription);

		javax.swing.GroupLayout pan_PrescriptionLayout = new javax.swing.GroupLayout(
				pan_Prescription);
		pan_Prescription.setLayout(pan_PrescriptionLayout);
		pan_PrescriptionLayout.setHorizontalGroup(pan_PrescriptionLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(span_Prescription,
						javax.swing.GroupLayout.DEFAULT_SIZE, 779,
						Short.MAX_VALUE));
		pan_PrescriptionLayout.setVerticalGroup(pan_PrescriptionLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(span_Prescription,
						javax.swing.GroupLayout.DEFAULT_SIZE, 262,
						Short.MAX_VALUE));

		btn_PreSave.setText("Save");
		btn_PreSave.setEnabled(false);
		btn_PreSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_PreSaveActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(
				jPanel12);
		jPanel12.setLayout(jPanel12Layout);
		jPanel12Layout
				.setHorizontalGroup(jPanel12Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel12Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel12Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																pan_Prescription,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																791,
																Short.MAX_VALUE)
														.addComponent(
																btn_PreSave,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																86,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		jPanel12Layout
				.setVerticalGroup(jPanel12Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel12Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												pan_Prescription,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												275, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(btn_PreSave)
										.addContainerGap()));

		jTabbedPane1.addTab("Laboratory", jPanel12);

		tab_MedicineTeach.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {}, {}, {} }, new String[] {

				}));
		tab_MedicineTeach.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_MedicineTeachMouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(tab_MedicineTeach);

		btn_DheSave.setText("Save");
		btn_DheSave.setEnabled(false);
		btn_DheSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_DheSaveActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(
				jPanel9);
		jPanel9.setLayout(jPanel9Layout);
		jPanel9Layout
				.setHorizontalGroup(jPanel9Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel9Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel9Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane2,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																791,
																Short.MAX_VALUE)
														.addComponent(
																btn_DheSave,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																86,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		jPanel9Layout
				.setVerticalGroup(jPanel9Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel9Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jScrollPane2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												279, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btn_DheSave)
										.addContainerGap()));

		jTabbedPane1.addTab("Medicine Education", jPanel9);

		jTabbedPane1.addTab(lang.getString("FOOT_EXAM"), jPanelFoot);
		jTabbedPane1.addTab(lang.getString("HIV_TAB"), pan_HIVComp);

		btn_CaseClose.setText("Send");
		btn_CaseClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CaseCloseActionPerformed(evt);
			}
		});

		jPanel11.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Demographic data"));

		jLabel60.setText("Patient No：");

		lab_Pno.setText("0");

		btn_Ddate_Save.setText("Save");
		btn_Ddate_Save.setEnabled(false);
		btn_Ddate_Save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_Ddate_SaveActionPerformed(evt);
			}
		});

		jLabel85.setText("Age：");

		lab_Age.setText("30");

		jLabel88.setText("Gender：");

		lab_Gender.setText("M");

		jLabel90.setText("Name：");

		lab_Name.setText("Steven Chung");

		jPanel4.setBorder(javax.swing.BorderFactory
				.createTitledBorder("One Touch"));

		jLabel23.setText("BGPc： ");

		jLabel15.setText("BGAc：");

		txt_PC.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		txt_AC.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel24.setText("mg/dl");

		jLabel25.setText("mg/dl");

		jLabel26.setText("BG(st)：");

		txt_ST.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel7.setText("mg/dl");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
				.setHorizontalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel4Layout
										.createSequentialGroup()
										.addGap(7, 7, 7)
										.addComponent(jLabel15)
										.addGap(18, 18, 18)
										.addComponent(
												txt_AC,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												74,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(6, 6, 6)
										.addComponent(jLabel24)
										.addGap(32, 32, 32)
										.addComponent(jLabel23)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												txt_PC,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel25)
										.addGap(42, 42, 42)
										.addComponent(jLabel26)
										.addGap(12, 12, 12)
										.addComponent(
												txt_ST,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel7)
										.addContainerGap(121, Short.MAX_VALUE)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel4Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel15)
														.addComponent(jLabel24)
														.addComponent(jLabel23)
														.addComponent(
																txt_AC,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_PC,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel25)
														.addComponent(
																txt_ST,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel26)
														.addComponent(jLabel7))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jLabel62.setText("Height：");

		txt_Height.setText("165");
		txt_Height.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txt_HeightActionPerformed(evt);
			}
		});
		txt_Height.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				txt_HeightFocusGained(evt);
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				txt_HeightFocusLost(evt);
			}
		});
		txt_Height.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel63.setText("Weight：");

		txt_Weight.setText("60");
		txt_Weight.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txt_WeightActionPerformed(evt);
			}
		});
		txt_Weight.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txt_WeightFocusLost(evt);
			}
		});
		txt_Weight.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel71.setText("Education：");

		com_edu.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Illiterate", "Digital knowledge", "Literacy",
				"Elementary School", "Middle S. ", "High S. ",
				"College or above" }));
		com_edu.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_D(evt);
			}
		});

		jLabel18.setText("cm");

		jLabel20.setText("kg");

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(
				jPanel11);
		jPanel11.setLayout(jPanel11Layout);
		jPanel11Layout
				.setHorizontalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel11Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jPanel4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				25,
																				Short.MAX_VALUE)
																		.addComponent(
																				btn_Ddate_Save,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				80,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanel11Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGap(12,
																												12,
																												12)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addComponent(
																																jLabel63)
																														.addGroup(
																																jPanel11Layout
																																		.createSequentialGroup()
																																		.addGroup(
																																				jPanel11Layout
																																						.createParallelGroup(
																																								javax.swing.GroupLayout.Alignment.LEADING)
																																						.addGroup(
																																								jPanel11Layout
																																										.createSequentialGroup()
																																										.addGap(87,
																																												87,
																																												87)
																																										.addComponent(
																																												lab_Pno,
																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																												105,
																																												javax.swing.GroupLayout.PREFERRED_SIZE))
																																						.addComponent(
																																								jLabel60))
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				jLabel90)))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addGroup(
																																jPanel11Layout
																																		.createSequentialGroup()
																																		.addComponent(
																																				txt_Weight,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				73,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				jLabel20))
																														.addComponent(
																																lab_Name,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																127,
																																javax.swing.GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addContainerGap()
																										.addComponent(
																												jLabel62)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txt_Height,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												71,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(6,
																												6,
																												6)
																										.addComponent(
																												jLabel18)))
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel71)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGap(28,
																												28,
																												28)
																										.addComponent(
																												jLabel85)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addComponent(
																												lab_Age)
																										.addGap(46,
																												46,
																												46)
																										.addComponent(
																												jLabel88)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												lab_Gender))
																						.addComponent(
																								com_edu,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));
		jPanel11Layout
				.setVerticalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																btn_Ddate_Save)
														.addGroup(
																jPanel11Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel60)
																														.addComponent(
																																lab_Pno)
																														.addComponent(
																																jLabel90)
																														.addComponent(
																																lab_Name))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel62)
																														.addComponent(
																																txt_Height,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																jLabel18)
																														.addComponent(
																																jLabel63)
																														.addComponent(
																																txt_Weight,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																jLabel20)))
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel85)
																														.addComponent(
																																lab_Age)
																														.addComponent(
																																jLabel88)
																														.addComponent(
																																lab_Gender))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel71)
																														.addComponent(
																																com_edu,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE))))
																		.addGap(14,
																				14,
																				14)
																		.addComponent(
																				jPanel4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(14, Short.MAX_VALUE)));

		mn_Fiele.setText("File");
		mn_Fiele.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mn_FieleActionPerformed(evt);
			}
		});

		mnit_Lab.setText("Laboratory/Radiology(X-RAY) Request");
		mnit_Lab.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_LabActionPerformed(evt);
			}
		});
		mn_Fiele.add(mnit_Lab);

		mnit_History.setText("Laboratory Recoard(For DM)");
		mnit_History.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_HistoryActionPerformed(evt);
			}
		});
		mn_Fiele.add(mnit_History);

		mnit_Close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_ESCAPE, 0));
		mnit_Close.setText("Close");
		mnit_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_CloseActionPerformed(evt);
			}
		});
		mn_Fiele.add(mnit_Close);

		mnb.add(mn_Fiele);

		menu_SetDM.setText("Set");

		jMenu1.setText("DM  ");

		mnit_V1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F1,
				java.awt.event.InputEvent.ALT_MASK));
		mnit_V1.setText("V1 new patient");
		mnit_V1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_V1ActionPerformed(evt);
			}
		});
		jMenu1.add(mnit_V1);

		mnit_V2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F2,
				java.awt.event.InputEvent.ALT_MASK));
		mnit_V2.setText("V2 F/U per 3 months");
		mnit_V2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_V2ActionPerformed(evt);
			}
		});
		jMenu1.add(mnit_V2);

		mnit_V3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_F3,
				java.awt.event.InputEvent.ALT_MASK));
		mnit_V3.setText("V3 per year");
		mnit_V3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_V3ActionPerformed(evt);
			}
		});
		jMenu1.add(mnit_V3);

		menu_SetDM.add(jMenu1);

		mnb.add(menu_SetDM);

		setJMenuBar(mnb);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jPanel11,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(10, 10,
																		10)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jTabbedPane1,
																						0,
																						0,
																						Short.MAX_VALUE)
																				.addComponent(
																						btn_CaseClose,
																						javax.swing.GroupLayout.Alignment.TRAILING,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						85,
																						javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jPanel11,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jTabbedPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										357, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btn_CaseClose).addContainerGap()));

		jTabbedPane1.getAccessibleContext().setAccessibleName("Assess");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	@SuppressWarnings("unused")
	private void jCheckBox142ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBox142ActionPerformed

	}// GEN-LAST:event_jCheckBox142ActionPerformed

	private void btn_CaseCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CaseCloseActionPerformed

		if (m_From.equals("dia") || m_From.equals("medicine")) {
			if (m_From.equals("dia")) {
				if (this.pan_CompliComp.btn_ComSave.isEnabled() == true) {
					Object[] options = { "YES", "NO" };
					int dialog = JOptionPane.showOptionDialog(new Frame(),
							"Not saved to continue ?", "Message",
							JOptionPane.YES_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
					if (dialog == 0) {
						// 選擇 YES 時
						// 關閉此視窗
						this.dispose();
					} else {
						// 選擇 NO 時
					}
				}

				// 關閉此視窗
				this.dispose();
			} else if (m_From.equals("medicine")) {
				if (btn_DheSave.isEnabled() == true) {
					Object[] options = { "YES", "NO" };
					int dialog = JOptionPane.showOptionDialog(new Frame(),
							"Not saved to continue ?", "Message",
							JOptionPane.YES_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options,
							options[0]);
					if (dialog == 0) {
						// 選擇 YES 時
						// 關閉此視窗
						this.dispose();
					} else {
						// 選擇 NO 時
					}
				}
				// 關閉此視窗
				this.dispose();
			}
		} else {
			// 判斷哪些表格還沒存檔
			String tab_name = "";

			if (btn_Ddate_Save.isEnabled()) {
				tab_name += "Demographic data \n";
			}

			if (this.pan_AssComp.btn_AssSave.isEnabled()) {
				tab_name += "Asscement \n";
			}

			if (this.pan_CompliComp.btn_ComSave.isEnabled()) {
				tab_name += "Complication \n";
			}

			if (pan_ConfEdu.btn_ConSave.isEnabled()) {
				tab_name += "Confirm the completion of health education \n";
			}

			if (btn_PreSave.isEnabled()) {
				tab_name += "Laboratory \n";
			}

			if (btn_DheSave.isEnabled()) {
				tab_name += "Drug health education \n";
			}

			if (!tab_name.equals("")) {
				Object[] options_con = { "YES", "NO" };
				int dialog_con = JOptionPane.showOptionDialog(new Frame(),
						"Not saved to continue ?\n" + tab_name + "\n",
						"Message", JOptionPane.YES_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options_con,
						options_con[0]);
				if (dialog_con == 0)
					showEnterClinic();
			} else
				showEnterClinic();
		}

	}// GEN-LAST:event_btn_CaseCloseActionPerformed

	private void txt_HeightFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txt_HeightFocusLost
	}// GEN-LAST:event_txt_HeightFocusLost

	private void txt_HeightFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txt_HeightFocusGained

	}// GEN-LAST:event_txt_HeightFocusGained

	private void txt_HeightActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txt_HeightActionPerformed

	}// GEN-LAST:event_txt_HeightActionPerformed

	private void txt_WeightFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txt_WeightFocusLost
	}// GEN-LAST:event_txt_WeightFocusLost

	private void txt_WeightActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txt_WeightActionPerformed

	}// GEN-LAST:event_txt_WeightActionPerformed

	private void btn_Ddate_SaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_Ddate_SaveActionPerformed

		try {
			String sql = "UPDATE patients_info  SET height = '"
					+ txt_Height.getText() + "',  weight = '"
					+ txt_Weight.getText() + "', education = '"
					+ com_edu.getSelectedIndex() + "' WHERE p_no = '" + m_Pno
					+ "'";
			System.out.println(sql);
			DBC.executeUpdate(sql);

			String sqlBgac = "INSERT INTO prescription (guid, code, case_guid, date_test, date_results, result, isnormal, cost, finish, state) "
					+ "SELECT UUID(), prescription_code.code,'"
					+ m_RegGuid
					+ "', NOW(), NOW(), '"
					+ txt_AC.getText()
					+ "', 0,0,'F',1  FROM prescription_code WHERE prescription_code.name = 'BGAc'";
			DBC.executeUpdate(sqlBgac);

			String sqlBgpc = "INSERT INTO prescription (guid, code, case_guid, date_test, date_results, result, isnormal, cost, finish, state) "
					+ "SELECT UUID(), prescription_code.code,'"
					+ m_RegGuid
					+ "', NOW(), NOW(), '"
					+ txt_PC.getText()
					+ "', 0,0,'F',1  FROM prescription_code WHERE prescription_code.name = 'BGPc'";
			DBC.executeUpdate(sqlBgpc);

			String sqlSt = "INSERT INTO prescription (guid, code, case_guid, date_test, date_results, result, isnormal, cost, finish, state) "
					+ "SELECT UUID(), prescription_code.code,'"
					+ m_RegGuid
					+ "', NOW(), NOW(), '"
					+ txt_ST.getText()
					+ "', 0,0,'F',1  FROM prescription_code WHERE prescription_code.name = 'St.'";
			DBC.executeUpdate(sqlSt);

			JOptionPane.showMessageDialog(null, "Save Complete");
			pan_AssComp.txt_bmi.setText(Tools.getBmi(txt_Height.getText(),
					txt_Weight.getText()));
			btn_Ddate_Save.setEnabled(false);
		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
		}

	}// GEN-LAST:event_btn_Ddate_SaveActionPerformed

	private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CloseActionPerformed
		setLostAutoCompleteEdit();
		if (m_RsRowCount == 0) {
			setClearTableRow(m_Row);
		}
		if (m_SelectTable.getValueAt(m_Row, 2) != null
				&& m_AutoListValue != null
				|| m_AutoTable.equals("prescription_code")) {
			if (!isCodeAtHashMap(m_AutoListValue[0].trim())) {
				setClearTableRow(m_Row);
			}
		} else {
			setClearTableRow(m_Row);
		}
		dia.setVisible(false);
	}// GEN-LAST:event_btn_CloseActionPerformed

	private void tab_MedicineTeachMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_MedicineTeachMouseClicked

		if (tab_MedicineTeach.getSelectedColumn() == 5
				|| tab_MedicineTeach.getSelectedColumn() == 6) {
			btn_DheSave.setEnabled(true);
		}

		if (tab_MedicineTeach.getValueAt(tab_MedicineTeach.getSelectedRow(), 5)
				.toString().equals("true")) {
			tab_MedicineTeach.setValueAt(UserInfo.getUserID(),
					tab_MedicineTeach.getSelectedRow(), 3);
			tab_MedicineTeach.setValueAt(UserInfo.getUserName(),
					tab_MedicineTeach.getSelectedRow(), 4);
		} else {
			tab_MedicineTeach.setValueAt("",
					tab_MedicineTeach.getSelectedRow(), 3);
			tab_MedicineTeach.setValueAt("",
					tab_MedicineTeach.getSelectedRow(), 4);
		}

	}// GEN-LAST:event_tab_MedicineTeachMouseClicked

	private void btn_PreSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_PreSaveActionPerformed

		try {
			boolean prescriptionState = false; // 判斷是否有檢驗處置
			boolean xrayState = false; // 判斷是否有x光處置
			// 存入處置
			String sqlDelete = "DELETE FROM prescription WHERE case_guid = '"
					+ m_RegGuid + "'";
			DBC.executeUpdate(sqlDelete);
			for (int i = 0; i < this.tab_Prescription.getRowCount(); i++) {
				if (this.tab_Prescription.getValueAt(i, 1) != null
						&& !this.tab_Prescription.getValueAt(i, 1).toString()
								.trim().equals("")) {
					if (this.tab_Prescription.getValueAt(i, 4) != null
							&& this.tab_Prescription.getValueAt(i, 4)
									.toString().trim()
									.equals(Constant.X_RAY_CODE)) {
						xrayState = true;
					} else {
						prescriptionState = true;
					}
					if (this.tab_Prescription.getValueAt(i, 3) == null) {
						this.tab_Prescription.setValueAt("", i, 3);
					}
					String sql = "INSERT INTO prescription (guid, case_guid, code, place, state) "
							+ "VALUES (uuid(), '"
							+ m_RegGuid
							+ "', '"
							+ this.tab_Prescription.getValueAt(i, 1).toString()
									.trim()
							+ "', '"
							+ this.tab_Prescription.getValueAt(i, 3).toString()
									.trim() + "', 1)";
					DBC.executeUpdate(sql);
				}
			}
			setPrint(prescriptionState, xrayState);
			JOptionPane.showMessageDialog(null, "Save Complete");

			// 提示回診日 *************************************
			// String packageSetAll = "";
			// if (m_PackageSet != null) {
			// dia_RevisitTime.setLocationRelativeTo(this);
			// dia_RevisitTime.setVisible(true);
			// String packageSet[] = m_PackageSet.split(",");
			// String packageSetId[] = m_PackageSetId.split(",");
			// for (int i = 0; i < packageSet.length; i++) {
			// packageSetAll+= i+1 +". "+ packageSet[i] + " ";
			// String sql =
			// "SELECT days FROM package_item WHERE id = '"+packageSetId[i]+"'";
			// ResultSet rs = DBC.executeQuery(sql);
			// txt_PackageId.setText(packageSetId[i]);
			// if (rs.next()) txt_ComeBackDays.setText(rs.getString ("days"));
			// }
			// txt_PackageType.setText(packageSetAll);
			// this.setEnabled(false);
			// }
			// *************************************************
			btn_PreSave.setEnabled(false);
		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}// GEN-LAST:event_btn_PreSaveActionPerformed

	private void tab_PrescriptionKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tab_PrescriptionKeyReleased
		btn_PreSave.setEnabled(true);
		if (isAllowKeyIn(evt) && m_SelectTable.getSelectedRow() != -1) {
			if (m_SelectTable.getColumnCount() > 2
					&& m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(),
							2) != null) {
				// System.out.println("移除 hashmap code = "+m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(),
				// 1).toString().trim());
				m_SelectTableHashMap.remove(m_SelectTable
						.getValueAt(m_SelectTable.getSelectedRow(), 1)
						.toString().trim());
			}
			getAutoCompleteValue(m_AutoTxt.getText());
		}
		tab_PrescriptionMouseClicked(null);
		if (tab_Prescription.getSelectedRow() != -1
				&& (evt.getKeyCode() == KeyEvent.VK_ENTER || evt.getKeyCode() == KeyEvent.VK_DELETE)) {
			m_PrescriptionRowNo = setRowValue(evt);
		}
	}// GEN-LAST:event_tab_PrescriptionKeyReleased

	private void tab_PrescriptionKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tab_PrescriptionKeyPressed
		if (m_SelectTable.getSelectedColumn() == 1
				&& evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (m_SelectTable.getValueAt(m_SelectTable.getSelectedRow(), 1) != null) {
				setBackspaceTxt();
			}
		}

		if (evt.getKeyCode() == KeyEvent.VK_ALT) {
			setAllRemoveEditAndSelection();
		}

	}// GEN-LAST:event_tab_PrescriptionKeyPressed

	private void tab_PrescriptionFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_tab_PrescriptionFocusGained
		m_AutoTable = "prescription_code";
		String[] prescriptionRsList = { "code", "name", "type" };
		m_AutoColumn = prescriptionRsList;
		m_AutoColumnName = "code";
		m_SelectTable = tab_Prescription;
		m_AutoLocationSpane = span_Prescription;
		m_SelectTableAddRowLimitColumn = 1;
		m_SelectTableModel = m_PrescriptionModel;
		m_SelectTableRowNo = m_PrescriptionRowNo;
		m_SelectTableNo = 2;
		m_SelectTableHashMap = m_PrescriptionHashMap;
	}// GEN-LAST:event_tab_PrescriptionFocusGained

	private void tab_PrescriptionMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_PrescriptionMouseClicked

	}

	private void mnit_LabActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_LabActionPerformed
		new Frm_DiagnosisPrescription(this).setVisible(true);
		tab_Prescription.removeRowSelectionInterval(0,
				tab_Prescription.getRowCount() - 1);
		tab_PrescriptionFocusGained(null);
	}// GEN-LAST:event_mnit_LabActionPerformed

	private void mnit_HistoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_HistoryActionPerformed
		new Frm_LabDM(m_Pno).setVisible(true);
	}// GEN-LAST:event_mnit_HistoryActionPerformed

	private void mnit_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_CloseActionPerformed

	}// GEN-LAST:event_mnit_CloseActionPerformed

	private void mn_FieleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mn_FieleActionPerformed

	}// GEN-LAST:event_mn_FieleActionPerformed

	private void btn_DheSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_DheSaveActionPerformed

		for (int i = 0; i < tab_MedicineTeach.getRowCount(); i++) {
			if (tab_MedicineTeach.getValueAt(i, 5).toString().equals("true")) {
				try {

					String check = "0";
					if (tab_MedicineTeach.getValueAt(i, 5).toString()
							.equals("true")) {
						check = "1";
					}

					String sql = "UPDATE medicine_stock SET ";

					if (tab_MedicineTeach.getValueAt(i, 3) == null) {
						sql += "s_id = NULL, ";
					} else {
						sql += "s_id = '" + tab_MedicineTeach.getValueAt(i, 3)
								+ "', ";
					}

					sql += "teach_complete = '" + check + "', ";

					if (tab_MedicineTeach.getValueAt(i, 6) == null) {
						sql += " ps = NULL ";
					} else {
						sql += " ps = '" + tab_MedicineTeach.getValueAt(i, 6)
								+ "' ";
					}
					sql += " WHERE os_guid = (SELECT outpatient_services.guid  FROM outpatient_services, registration_info WHERE registration_info.guid = outpatient_services.reg_guid AND registration_info.guid = '"
							+ m_RegGuid
							+ "' AND m_code = '"
							+ tab_MedicineTeach.getValueAt(i, 1) + "')";
					System.out.println(sql);
					DBC.executeUpdate(sql);
				} catch (SQLException ex) {
					Logger.getLogger(Frm_Case.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Save Complete");
		btn_DheSave.setEnabled(false);
	}// GEN-LAST:event_btn_DheSaveActionPerformed

	private void ItemStateChanged_D(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_ItemStateChanged_D

		btn_Ddate_Save.setEnabled(true);
	}// GEN-LAST:event_ItemStateChanged_D

	private void KeyReleased_D(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_KeyReleased_D

		btn_Ddate_Save.setEnabled(true);
	}// GEN-LAST:event_KeyReleased_D

	private void mnit_V1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_V1ActionPerformed
		setV("V1");
		jTabbedPane1.setSelectedIndex(3);
	}// GEN-LAST:event_mnit_V1ActionPerformed

	private void mnit_V2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_V2ActionPerformed
		setV("V2");
		jTabbedPane1.setSelectedIndex(3);
	}// GEN-LAST:event_mnit_V2ActionPerformed

	private void mnit_V3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_V3ActionPerformed
		setV("V3");
		jTabbedPane1.setSelectedIndex(3);

	}// GEN-LAST:event_mnit_V3ActionPerformed

	private void list_MenuMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_list_MenuMouseClicked
		getSplitValue();
		if (evt.getClickCount() == 2) {
			btn_CloseActionPerformed(null);
		}
	}// GEN-LAST:event_list_MenuMouseClicked

	private void list_MenuKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_list_MenuKeyPressed
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			btn_CloseActionPerformed(null);
			setClearTableRow(m_Row);
			setLostAutoCompleteEdit();
		}
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			btn_CloseActionPerformed(null);
		}
		if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) { // 刪除一個字元
			setBackspaceTxt();
		}
	}// GEN-LAST:event_list_MenuKeyPressed

	private void list_MenuKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_list_MenuKeyReleased
		if (isKeyIn(String.valueOf(evt.getKeyChar()))) {
			setTxt(String.valueOf(evt.getKeyChar()));
		}
		if (evt.getKeyCode() != KeyEvent.VK_DELETE
				&& list_Menu.getVisibleRowCount() > 0
				&& (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN)) {
			getSplitValue();
		}
	}// GEN-LAST:event_list_MenuKeyReleased

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		try {
			if (common.Tools.isNumber(txt_ComeBackDays.getText().trim())) {

				DBC.executeUpdate("INSERT INTO package_set(guid, reg_guid, id, use_date, sms_state, days) "
						+ "VALUES(UUID(), '"
						+ m_RegGuid
						+ "', '"
						+ txt_PackageId.getText()
						+ "', NOW(), '0', "
						+ txt_ComeBackDays.getText().trim() + ")");

				JOptionPane.showMessageDialog(null, "Saved successfully.");
				this.setEnabled(true);
				dia_RevisitTime.dispose();

			} else {
				JOptionPane.showMessageDialog(null, "Please Check Days");
			}
		} catch (SQLException ex) {
			Logger.getLogger(Frm_Case.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}// GEN-LAST:event_jButton2ActionPerformed

	private javax.swing.JButton btn_CaseClose;
	private javax.swing.JButton btn_Close;
	private javax.swing.JButton btn_Ddate_Save;
	private javax.swing.JButton btn_DheSave;
	private javax.swing.JButton btn_PreSave;
	private javax.swing.JComboBox com_edu;
	private javax.swing.JDialog dia;
	private javax.swing.JDialog dia_RevisitTime;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel23;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel27;
	private javax.swing.JLabel jLabel28;
	private javax.swing.JLabel jLabel29;
	private javax.swing.JLabel jLabel31;
	private javax.swing.JLabel jLabel60;
	private javax.swing.JLabel jLabel62;
	private javax.swing.JLabel jLabel63;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel71;
	private javax.swing.JLabel jLabel85;
	private javax.swing.JLabel jLabel88;
	private javax.swing.JLabel jLabel90;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel9;
	private javax.swing.JPanel jPanelFoot;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JLabel lab_Age;
	private javax.swing.JLabel lab_Gender;
	private javax.swing.JLabel lab_Name;
	private javax.swing.JLabel lab_Pno;
	private javax.swing.JList list_Menu;
	private javax.swing.JMenu menu_SetDM;
	private javax.swing.JMenu mn_Fiele;
	private javax.swing.JMenuBar mnb;
	private javax.swing.JMenuItem mnit_Close;
	private javax.swing.JMenuItem mnit_History;
	private javax.swing.JMenuItem mnit_Lab;
	private javax.swing.JMenuItem mnit_V1;
	private javax.swing.JMenuItem mnit_V2;
	private javax.swing.JMenuItem mnit_V3;
	private Tab_Assessment pan_AssComp;
	private Tab_Complication pan_CompliComp;
	private Tab_HIVCase pan_HIVComp;
	private Tab_ConfirmEducation pan_ConfEdu;
	private javax.swing.JPanel pan_Prescription;
	private javax.swing.JScrollPane span_ListMenu;
	private javax.swing.JScrollPane span_Prescription;
	private javax.swing.JTable tab_MedicineTeach;
	private javax.swing.JTable tab_Prescription;
	private javax.swing.JTextField txt_AC;
	private javax.swing.JTextField txt_ComeBackDays;
	private javax.swing.JTextField txt_Height;
	private javax.swing.JTextField txt_PC;
	private javax.swing.JTextField txt_PackageId;
	private javax.swing.JTextField txt_PackageType;
	private javax.swing.JTextField txt_ST;
	private javax.swing.JTextField txt_Weight;

	// End of variables declaration//GEN-END:variables
	@Override
	public void onDateChanged() {

	}

}
