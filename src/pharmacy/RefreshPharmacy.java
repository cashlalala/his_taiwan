package pharmacy;

//import Diagnosis.*;
import cc.johnwu.date.DateMethod;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import common.PrintTools;
import errormessage.StoredErrorMessage;
import multilingual.Language;

public class RefreshPharmacy extends Thread {
	private javax.swing.JTable tab;
	private long time;
	private javax.swing.JCheckBox checkBox;
	private String m_LastTouchTime;
	private Object m_LastPharmacyPrintUse = null;
	private ResultSet rs = null;
	private ResultSet rsTouchTime = null;
	private Frm_Pharmacy Frm_p;
	private Language paragraph = Language.getInstance();
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();
	private String sql = "SELECT registration_info.pharmacy_no AS '"
			+ paragraph.getString("NO")
			+ "', "
			+ "registration_info.p_no AS '"
			+ paragraph.getString("PATIENTNO")
			+ "', "
			+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS '"
			+ paragraph.getString("NAME")
			+ "', "
			+ "patients_info.birth AS 'Birthday', "
			+ "patients_info.gender AS '"
			+ paragraph.getString("GENDER")
			+ "', "
			+ "patients_info.ps AS '"
			+ paragraph.getString("PS")
			+ "', "
			+ "policlinic.name AS Poli, "
			+ "concat(staff_info.firstname,'  ',staff_info.lastname) AS '"
			+ paragraph.getString("DOCTOR")
			+ "', "
			+ "concat(patients_info.bloodtype,patients_info.rh_type) AS '"
			+ paragraph.getString("BLOOD")
			+ "',"
			+ "registration_info.guid, "
			+ "DATE_FORMAT(registration_info.touchtime,'%Y-%m-%d_%H:%i:%s') AS '"
			+ paragraph.getString("TIME")
			+ "',"
			+ "registration_info.touchtime , registration_info.medicine_touchtime "
			+ "FROM registration_info, patients_info, shift_table,staff_info ,poli_room, policlinic, outpatient_services ,medicine_stock "
			+ "WHERE registration_info.shift_guid = shift_table.guid "
			+ "AND shift_table.room_guid = poli_room.guid "
			+ "AND poli_room.poli_guid = policlinic.guid "
			+ "AND shift_table.s_id = staff_info.s_id "
			+ "AND medicine_stock.get_medicine_time IS NULL "
			+ "AND registration_info.p_no = patients_info.p_no "
			+ "AND shift_table.shift_date = '" + DateMethod.getTodayYMD()
			+ "' " + "AND shift_table.shift = '" + DateMethod.getNowShiftNum()
			+ "' " + "AND registration_info.finish = 'F' "
			+ "AND registration_info.pharmacy_payment = 'F' "
			+ "AND medicine_stock.reg_guid =  registration_info.guid "
			+"GROUP BY registration_info.guid " + "ORDER BY 'No.' ";
	private String touchTimeSql = // 最新更新時間
	"SELECT MAX(registration_info.medicine_touchtime) AS medicine_touchtime "
			+ "FROM registration_info, shift_table, outpatient_services, medicine_stock "
			+ "WHERE registration_info.shift_guid = shift_table.guid "
			+ "AND shift_table.shift_date = '" + DateMethod.getTodayYMD()
			+ "' " + "AND shift_table.shift = '" + DateMethod.getNowShiftNum()
			+ "' " + "AND registration_info.finish IS NOT NULL ";

	protected RefreshPharmacy(javax.swing.JTable tab, long time,
			Frm_Pharmacy frm, javax.swing.JCheckBox checkBox) {

		this.tab = tab;
		this.time = time;
		this.checkBox = checkBox;
		Frm_p = frm;
		((DefaultTableModel) this.tab.getModel()).setRowCount(0);
		try {

			rs = DBC.executeQuery(sql);
			rsTouchTime = DBC.executeQuery(touchTimeSql);
			rs.next();
			this.tab.setModel(HISModel.getModel(rs, false));

			common.TabTools.setHideColumn(this.tab, 9);
			common.TabTools.setHideColumn(this.tab, 11);

			rsTouchTime.next();
			if (rsTouchTime.getString("medicine_touchtime") != null) {
				m_LastPharmacyPrintUse = rsTouchTime
						.getString("medicine_touchtime");
			} else {
				m_LastPharmacyPrintUse = 0;
			}
		} catch (SQLException e) {
			ErrorMessage
					.setData(
							"Pharmacy",
							"RefrashPharmacy",
							"RefrashPharmacy(javax.swing.JTable tab,long time,Frm_Pharmacy frm, javax.swing.JCheckBox checkBox)",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
			Logger.getLogger(RefreshPharmacy.class.getName()).log(Level.SEVERE,
					null, e);
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				ErrorMessage
						.setData(
								"Pharmacy",
								"RefrashPharmacy",
								"RefrashPharmacy(javax.swing.JTable tab,long time,Frm_Pharmacy frm, javax.swing.JCheckBox checkBox) - DBC.closeConnection",
								e.toString().substring(
										e.toString().lastIndexOf(".") + 1,
										e.toString().length()));
			}
		}
	}

	private boolean isRunning = true;

	public void stopRunning() {
		this.isRunning = false;
	}

	public void restartRunning(){
		this.isRunning = true;
	}
	
	@Override
	public void run() {
		try {
			while (isRunning) {
				ResultSet rsCheck = null;
				try {
					rsCheck = DBC.executeQuery(touchTimeSql);
					rsCheck.beforeFirst();
					// 有看診完畢資料 ＆＆（第一筆資料是null 代表目前清單無人 ｜｜ 取得時間 ＝ 最大時間 代表資料無變動 ）
					if (rsCheck.next()
							&& (rsCheck.getString("medicine_touchtime") == null || rsCheck
									.getString("medicine_touchtime").equals(
											m_LastPharmacyPrintUse))) {
						if (rsCheck.getString("medicine_touchtime") == null
								&& tab.getRowCount() != 0) {
							((DefaultTableModel) tab.getModel()).setRowCount(0);
						}
						RefreshPharmacy.sleep(time);
						DBC.closeConnection(rsCheck);
						continue;
					}
					DBC.closeConnection(rsCheck);
					rs = DBC.executeQuery(sql);
					rsTouchTime = DBC.executeQuery(touchTimeSql);
					if (rs.last()) {
						Frm_p.setNowRowCount(rs.getRow() + "");
						int row = 0;
						((DefaultTableModel) tab.getModel()).setRowCount(rs
								.getRow());
						rs.beforeFirst();
						while (rs.next()) {
							for (int col = 0; col < 11; col++) {
								tab.setValueAt(rs.getString(col + 1), row, col);
							}
							row++;
							if (checkBox.isSelected()) {
								tab.setRowSelectionInterval(
										tab.getRowCount() - 1,
										tab.getRowCount() - 1);
								tab.changeSelection(tab.getSelectedRow(),
										tab.getSelectedColumn(), false, false);
							}

							// 取出表單這一筆資料的 touchtime
							m_LastTouchTime = rs
									.getString("medicine_touchtime").substring(
											6, 20);

							// ****************************print
							if (m_LastPharmacyPrintUse.equals(0)
									|| (Long.parseLong(m_LastTouchTime) > Long
											.parseLong(String.valueOf(
													m_LastPharmacyPrintUse)
													.substring(6, 20)))) {
								PrintTools pt = new PrintTools();
								pt.DoPrint(9, rs.getString("guid"));
							}
							// ****************************
						}
						rsTouchTime = DBC.executeQuery(touchTimeSql);
						rsTouchTime.next();
						m_LastPharmacyPrintUse = rsTouchTime
								.getString("medicine_touchtime");
					}

				} catch (SQLException e) {
					ErrorMessage.setData(
							"Pharmacy",
							"RefrashPharmacy",
							"run()",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
					Logger.getLogger(RefreshPharmacy.class.getName()).log(
							Level.SEVERE, null, e);
				} finally {
					try {
						DBC.closeConnection(rsCheck);
					} catch (SQLException e) {
						ErrorMessage.setData(
								"Pharmacy",
								"RefrashPharmacy",
								"run() - DBC.closeConnection",
								e.toString().substring(
										e.toString().lastIndexOf(".") + 1,
										e.toString().length()));
					}
				}
			}
		} catch (InterruptedException e) {
			ErrorMessage.setData(
					"Pharmacy",
					"RefrashPharmacy",
					"run() - InterruptedException",
					e.toString().substring(e.toString().lastIndexOf(".") + 1,
							e.toString().length()));
		} finally {
			try {
				DBC.closeConnection(rs);
				DBC.closeConnection(rsTouchTime);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
