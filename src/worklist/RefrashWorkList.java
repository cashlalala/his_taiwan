/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package worklist;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import multilingual.Language;
import cc.johnwu.date.DateMethod;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

import common.Constant;
import common.TabTools;

/**
 * 
 * @author steven
 */
public class RefrashWorkList extends Thread {
	private String m_SysName; // 進入系統名稱
	private Language paragraph = Language.getInstance();
	private String[] line = paragraph.setlanguage("DIAGNOSISWORKLIST").split(
			"\n");
	private javax.swing.JTable m_Tab;
	private long m_Time;
	private String m_LastTouchTime;
	@SuppressWarnings("unused")
	private String[] m_Guid;
	private ResultSet rs = null;
	private String sql;

	private Statement stmt = null;

	
	protected String getLABSQLString(String date) {
		String sql = "SELECT "
			    + " NEWTABLE.visits_no AS 'NO.',"
			    + " NEWTABLE.visits_no AS 'Register',"
			    + " NEWTABLE.finish as 'Finish',"
			    + " NULL,"
			    + " NEWTABLE.reg_time AS 'Reg time',"
			    + " NEWTABLE.p_no AS 'Patient No.',"
			    + " NEWTABLE.Name AS 'Name',"
			    + " NEWTABLE.birth AS 'Birthday',"
			    + " NEWTABLE.gender AS 'Gender',"
			    + " NEWTABLE.Blood,"
			    + " NEWTABLE.ps AS 'P.S.',"
			    + " NEWTABLE.regguid AS guid,"
			    + " pre_status.status AS status"
			    + " FROM"
			    + " (SELECT distinct"
			    + " A.visits_no,"
			    + " A.reg_time,"
			    + " A.p_no,"
			    + " concat(patients_info.firstname, '  ', patients_info.lastname) AS 'Name',"
			    + " patients_info.birth,"
			    + " patients_info.gender,"
			    + " concat(patients_info.bloodtype, patients_info.rh_type) AS 'Blood',"
			    + " patients_info.ps,"
			    + " A.guid AS regguid, prescription.finish"
			    + " FROM"
			    + " registration_info AS A, patients_info, shift_table, staff_info, prescription"
			    + " LEFT JOIN prescription_code ON prescription.code = prescription_code.code"
			    + " WHERE"
			    + " A.shift_guid = shift_table.guid"
			    + " AND shift_table.s_id = staff_info.s_id"
			    + " AND A.p_no = patients_info.p_no"
			    + " AND prescription.code = prescription_code.code"
			    + " AND prescription.reg_guid = A.guid"
			    + " AND prescription_code.type <> '" + Constant.X_RAY_CODE + "' ";
//			    + " AND (SELECT" 
//			    + " COUNT(prescription.code)"
//			    + " FROM"
//			    + " prescription, registration_info, prescription_code"
//			    + " WHERE"
//			    + " (prescription.finish <> 'F'"
//			    + " OR prescription.finish IS NULL)"
//			    + " AND prescription.reg_guid = registration_info.guid"
//			    + " AND prescription.code = prescription_code.code"
//			    + " AND prescription_code.type <> 'X-RAY'"
//			    + " AND registration_info.guid = A.guid) > 0 "
			    if (date.compareTo("") != 0)			    
			    	sql += " AND A.reg_time LIKE '"	+ date + "%'"
			    + ") AS NEWTABLE"
			    + " LEFT JOIN"
			    + " (SELECT distinct"
			    + " reg_guid, '1' AS status"
			    + " FROM"
			    + " prescription"
			    + " WHERE"
			    + " prescription.specimen_status = '1') AS pre_status ON (pre_status.reg_guid = NEWTABLE.regguid)"
			    + " ORDER BY NEWTABLE.Finish ASC, pre_status.status , NEWTABLE.reg_time DESC , NEWTABLE.visits_no";
		return sql;
	}
	
	protected String getXRAYSQLString(String date) {
		String sql = "SELECT distinct A.visits_no AS '"
				+ paragraph.getLanguage(line, "COL_NO")
				+ "', "
				+ "A.visits_no AS '"
				+ paragraph.getLanguage(line, "COL_REGISTER")
				+ "', "
				+ " prescription.finish as 'Finish', "
				+ "NULL, "
				+ "A.reg_time AS '"
				+ paragraph.getLanguage(line, "COL_REGTIME")
				+ "', "
				+ "A.p_no AS '"
				+ paragraph.getLanguage(line, "COL_PATIENTNO")
				+ "', "
				+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS '"
				+ paragraph.getLanguage(line, "COL_NAME")
				+ "', "
				+ "patients_info.birth AS '"
				+ paragraph.getLanguage(line, "COL_BIRTH")
				+ "', "
				+ "patients_info.gender AS '"
				+ paragraph.getLanguage(line, "COL_GENDER")
				+ "', "
				+ "concat(patients_info.bloodtype,patients_info.rh_type) AS '"
				+ paragraph.getLanguage(line, "COL_BLOOD")
				+ "', "
				+ "patients_info.ps AS '"
				+ paragraph.getLanguage(line, "COL_PS")
				+ "', "
				+ "A.guid "
				+ "FROM registration_info AS A, patients_info, shift_table,staff_info, prescription, prescription_code "
				+ "WHERE A.shift_guid = shift_table.guid "
				+ "AND shift_table.s_id = staff_info.s_id "
				+ "AND A.p_no = patients_info.p_no "
				+ "AND prescription.code = prescription_code.code "
				+ "AND prescription.reg_guid = A.guid "
				+ "AND prescription_code.type = '" + Constant.X_RAY_CODE + "' "
//				+ "AND (SELECT COUNT(code) "
//				+ "FROM prescription, registration_info "
//				+ "WHERE (prescription.finish <> 'F' OR prescription.finish IS  NULL ) "
//				+ "AND prescription.code = prescription_code.code "
//				+ "AND prescription_code.type = '"
//				+ Constant.X_RAY_CODE
//				+ "' "
//				+ "AND prescription.reg_guid = registration_info.guid "
//				+ "AND registration_info.guid = A.guid)  > 0 "
				+ "AND A.reg_time LIKE '" + date + "%' "
				+ "ORDER BY prescription.Finish ASC, A.reg_time DESC, A.visits_no ";
		return sql;
	}
	@SuppressWarnings("deprecation")
	protected RefrashWorkList(javax.swing.JTable tab, long time, String SysName) {
		m_SysName = SysName;

		if (SysName.equals("dia")) {
			sql = "SELECT A.visits_no AS '"
					+ paragraph.getLanguage(line, "COL_NO")
					+ "', "
					+ "(SELECT CASE COUNT(registration_info.guid) "
					+ "WHEN 0 THEN '*' "
					+ "END  "
					+ "FROM outpatient_services, registration_info "
					+ "WHERE registration_info.guid = outpatient_services.reg_guid AND p_no = A.p_no ) AS '"
					+ paragraph.getLanguage(line, "COL_FIRST")
					+ "', "
					+ "CASE A.finish WHEN 'F' THEN 'F' WHEN 'O' THEN 'Skip' END 'Status', "
					+ "A.reg_time AS '"
					+ paragraph.getLanguage(line, "COL_REGTIME")
					+ "', A.p_no AS '"
					+ paragraph.getLanguage(line, "COL_PATIENTNO")
					+ "', "
					+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS '"
					+ paragraph.getLanguage(line, "COL_NAME")
					+ "', "
					+ "patients_info.birth AS '"
					+ paragraph.getLanguage(line, "COL_BIRTH")
					+ "', "
					+ "TIMESTAMPDIFF(year,patients_info.birth, now()) AS '"
					+ paragraph.getLanguage(line, "COL_AGE")
					+ "', "
					+ "patients_info.gender AS '"
					+ paragraph.getLanguage(line, "COL_GENDER")
					+ "', "
					+ "concat(patients_info.bloodtype,patients_info.rh_type) AS '"
					+ paragraph.getLanguage(line, "COL_BLOOD")
					+ "', "
					+ "patients_info.ps AS '"
					+ paragraph.getLanguage(line, "COL_PS")
					+ "', "
					+ "A.guid "
					+ "FROM registration_info AS A, patients_info, shift_table,staff_info, poli_room, policlinic  "
					+ "WHERE A.shift_guid = shift_table.guid "
					+ "AND shift_table.s_id = '"
					+ UserInfo.getUserID()
					+ "' "
					+ "AND shift_table.shift_date = '"
					+ DateMethod.getTodayYMD()
					+ "' "
					+ "AND shift_table.shift = '"
					+ DateMethod.getNowShiftNum()
					+ "' "
					+ "AND shift_table.room_guid = poli_room.guid "
					+ "AND poli_room.poli_guid = policlinic.guid "
					+ "AND shift_table.s_id = staff_info.s_id "
					+ "AND (A.finish = 'F' OR A.finish IS NULL OR A.finish = 'O' OR A.finish = '') "
					+ "AND A.p_no = patients_info.p_no "
					+ "ORDER BY A.finish, A.visits_no";
		} else if (SysName.equals("lab")) {
			Date today = new Date();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			sql = getLABSQLString(sdFormat.format(today)); 
		} else if (SysName.equals("xray")) {
			Date today = new Date();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			sql = getXRAYSQLString(sdFormat.format(today));
		} else if (SysName.equals("case")) {
			sql = "SELECT A.visits_no AS '"
					+ paragraph.getLanguage(line, "COL_NO")
					+ "', "
					+ "A.register AS '"
					+ paragraph.getLanguage(line, "COL_REGISTER")
					+ "', "
					+ "(SELECT CASE COUNT(registration_info.guid) "
					+ "WHEN 0 THEN '*' "
					+ "END  "
					+ "FROM outpatient_services, registration_info "
					+ "WHERE registration_info.guid = outpatient_services.reg_guid AND p_no = A.p_no ) AS '"
					+ paragraph.getLanguage(line, "COL_FIRST")
					+ "', "
					+ "CASE A.case_finish WHEN 'F' THEN 'F' END 'Status', "
					+ "A.reg_time AS '"
					+ paragraph.getLanguage(line, "COL_REGTIME")
					+ "', A.p_no AS '"
					+ paragraph.getLanguage(line, "COL_PATIENTNO")
					+ "', "
					+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS '"
					+ paragraph.getLanguage(line, "COL_NAME")
					+ "', "
					+ "patients_info.birth AS '"
					+ paragraph.getLanguage(line, "COL_BIRTH")
					+ "', "
					+ "patients_info.gender AS '"
					+ paragraph.getLanguage(line, "COL_GENDER")
					+ "', "
					+ "concat(patients_info.bloodtype,patients_info.rh_type) AS '"
					+ paragraph.getLanguage(line, "COL_BLOOD")
					+ "', "
					+ "patients_info.ps AS '"
					+ paragraph.getLanguage(line, "COL_PS")
					+ "', "
					+ "A.guid, policlinic.type  "
					+ "FROM registration_info AS A, patients_info, shift_table,staff_info, poli_room, policlinic  "
					+ "WHERE A.shift_guid = shift_table.guid "
					+ "AND shift_table.room_guid = poli_room.guid "
					+ "AND shift_table.shift_date = '"
					+ DateMethod.getTodayYMD() + "' "
					+ "AND shift_table.shift = '" + DateMethod.getNowShiftNum()
					+ "' " + "AND poli_room.poli_guid = policlinic.guid "
					+ "AND shift_table.s_id = staff_info.s_id "
					+ "AND (A.case_finish = 'F' OR A.case_finish IS NULL) "
					+ "AND A.p_no = patients_info.p_no "
					+ "AND policlinic.type = 'DM' "
					+ "ORDER BY Status, A.visits_no";
		}
		this.m_Tab = tab;
		this.m_Time = time;
		try {
			System.out.println(sql);
			rs = DBC.executeQuery(sql);
			((DefaultTableModel) this.m_Tab.getModel()).setRowCount(0);
			this.m_Tab.setModel(HISModel.getModel(rs));
			rs.last();
			setCloumnWidth(this.m_Tab);

			if (SysName.equals("dia")) {
				Object[][] array = { { "O", Constant.FINISH_COLOR },
						{ "F", Constant.FINISH_COLOR } };
				TabTools.setTabColor(m_Tab, 3, array);
				TabTools.setHideColumn(this.m_Tab, 11);
			} else if (SysName.equals("case")) {
				Object[][] array = { { "F", Constant.FINISH_COLOR } };
				TabTools.setTabColor(m_Tab, 3, array);
				TabTools.setHideColumn(m_Tab, 0);
				TabTools.setHideColumn(m_Tab, 11);
			} else if (SysName.equals("lab")) {

				Object[][] array = { { "1", Constant.WARNING_COLOR } };
				TabTools.setTabColor(m_Tab, 12, array);
				TabTools.setHideColumn(this.m_Tab, 0);
				TabTools.setHideColumn(this.m_Tab, 1);
				TabTools.setHideColumn(this.m_Tab, 2);
				TabTools.setHideColumn(this.m_Tab, 3);
				TabTools.setHideColumn(this.m_Tab, 11);
				TabTools.setHideColumn(this.m_Tab, 12);

			} else if (SysName.equals("xray")) {
				TabTools.setHideColumn(this.m_Tab, 0);
				TabTools.setHideColumn(this.m_Tab, 1);
				TabTools.setHideColumn(this.m_Tab, 2);
				TabTools.setHideColumn(this.m_Tab, 3);
				TabTools.setHideColumn(this.m_Tab, 11);
			}

			DBC.closeConnection(rs);

			cc.johnwu.login.OnlineState.OnlineState();
			Connection conn = DriverManager.getConnection(DBC.s_ServerURL,
					DBC.s_ServerName, DBC.s_ServerPasswd);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException ex) {
			}
		}
	}

	private boolean isRunning = true;

	public void stopRunning() {
		this.isRunning = false;
	}

	@Override
	public void run() {
		try {
			while (isRunning) {
				try {
					String check_sql = "";
					if (m_SysName.equals("dia")) {
						check_sql = "SELECT MAX(touchtime) "
								+ "FROM registration_info,shift_table "
								+ "WHERE registration_info.shift_guid = shift_table.guid "
								+ "AND shift_table.s_id = '"
								+ UserInfo.getUserID() + "' "
								+ "AND shift_table.shift_date = '"
								+ DateMethod.getTodayYMD() + "' "
								+ "AND shift_table.shift = '"
								+ DateMethod.getNowShiftNum() + "' ";
					} else if (m_SysName.equals("lab")) { // 有問題
						check_sql = "SELECT MAX(touchtime) "
								+ "FROM registration_info,shift_table "
								+ "WHERE registration_info.shift_guid = shift_table.guid "
								+ "AND shift_table.shift_date = '"
								+ DateMethod.getTodayYMD() + "' "
								+ "AND shift_table.shift = '"
								+ DateMethod.getNowShiftNum() + "' ";
					} else if (m_SysName.equals("xray")) { // 有問題
						check_sql = "SELECT MAX(touchtime) "
								+ "FROM registration_info,shift_table "
								+ "WHERE registration_info.shift_guid = shift_table.guid "
								+ "AND shift_table.shift_date = '"
								+ DateMethod.getTodayYMD() + "' "
								+ "AND shift_table.shift = '"
								+ DateMethod.getNowShiftNum() + "' ";
					} else if (m_SysName.equals("case")) {
						check_sql = "SELECT MAX(touchtime) "
								+ "FROM registration_info,shift_table "
								+ "WHERE registration_info.shift_guid = shift_table.guid "
								+ "AND shift_table.shift_date = '"
								+ DateMethod.getTodayYMD() + "' "
								+ "AND shift_table.shift = '"
								+ DateMethod.getNowShiftNum() + "' ";
					}
					System.out.println(check_sql);
					rs = stmt.executeQuery(check_sql);
					if (rs.next()
							&& (rs.getString(1) == null || rs.getString(1)
									.equals(m_LastTouchTime))) {
						RefrashWorkList.sleep(m_Time);
						continue;
					}
					m_LastTouchTime = rs.getString(1);
					rs.close();

					System.out.println(sql);
					rs = stmt.executeQuery(sql);
					if (rs.last()) {
						int row = 0;
						this.m_Guid = new String[rs.getRow()];
						((DefaultTableModel) m_Tab.getModel()).setRowCount(rs
								.getRow());
						rs.beforeFirst();
						while (rs.next()) {
							for (int col = 0; col < 11; col++)
								m_Tab.setValueAt(rs.getString(col + 1), row,
										col);
							row++;
						}
					}
					rs.close();
				} catch (SQLException ex) {
					System.out.println("WorkList:" + ex);
					ex.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
				try {
					DBC.closeConnection(stmt);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private void setCloumnWidth(javax.swing.JTable tab) {
		// 設定column寬度
		TableColumn columnVisits_no = tab.getColumnModel().getColumn(0);
		TableColumn columnVisits_reg = tab.getColumnModel().getColumn(1);
		TableColumn columnFirst = tab.getColumnModel().getColumn(2);
		TableColumn columnVisits = tab.getColumnModel().getColumn(3);
		TableColumn columnReg_time = tab.getColumnModel().getColumn(4);
		TableColumn columnP_no = tab.getColumnModel().getColumn(5);
		TableColumn columnName = tab.getColumnModel().getColumn(6);
		TableColumn columnAge = tab.getColumnModel().getColumn(7);
		TableColumn columnSex = tab.getColumnModel().getColumn(8);
		TableColumn columnBloodtype = tab.getColumnModel().getColumn(9);
		TableColumn columnPs = tab.getColumnModel().getColumn(10);
		columnVisits_no.setPreferredWidth(30);
		columnVisits_reg.setPreferredWidth(50);
		columnFirst.setPreferredWidth(50);
		columnVisits.setPreferredWidth(55);
		columnReg_time.setPreferredWidth(163);
		columnP_no.setPreferredWidth(75);
		columnName.setPreferredWidth(150);
		columnAge.setPreferredWidth(90);
		columnSex.setPreferredWidth(50);
		columnBloodtype.setPreferredWidth(50);
		columnPs.setPreferredWidth(360);
		tab.setRowHeight(30);
	}

	// 取得選定日期資料
	@SuppressWarnings("deprecation")
	public void getSelectDate(String date) {
		if (m_SysName.equals("lab")) {
			sql = getLABSQLString(date); 
		} else if (m_SysName.equals("xray")) {
			sql = getXRAYSQLString(date);
		} else if (m_SysName.equals("case")) {
			sql = "SELECT A.visits_no AS '"
					+ paragraph.getLanguage(line, "COL_NO")
					+ "', "
					+ "A.register AS '"
					+ paragraph.getLanguage(line, "COL_REGISTER")
					+ "', "
					+ "(SELECT CASE COUNT(registration_info.guid) "
					+ "WHEN 0 THEN '*' "
					+ "END  "
					+ "FROM outpatient_services, registration_info "
					+ "WHERE registration_info.guid = outpatient_services.reg_guid AND p_no = A.p_no ) AS '"
					+ paragraph.getLanguage(line, "COL_FIRST")
					+ "', "
					+ "CASE A.case_finish WHEN 'F' THEN 'F' END 'State', "
					+ "A.reg_time AS '"
					+ paragraph.getLanguage(line, "COL_REGTIME")
					+ "', A.p_no AS '"
					+ paragraph.getLanguage(line, "COL_PATIENTNO")
					+ "', "
					+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS '"
					+ paragraph.getLanguage(line, "COL_NAME")
					+ "', "
					+ "patients_info.birth AS '"
					+ paragraph.getLanguage(line, "COL_BIRTH")
					+ "', "
					+ "patients_info.gender AS '"
					+ paragraph.getLanguage(line, "COL_GENDER")
					+ "', "
					+ "concat(patients_info.bloodtype,patients_info.rh_type) AS '"
					+ paragraph.getLanguage(line, "COL_BLOOD")
					+ "', "
					+ "patients_info.ps AS '"
					+ paragraph.getLanguage(line, "COL_PS")
					+ "', "
					+ "A.guid, policlinic.type  "
					+ "FROM registration_info AS A, patients_info, shift_table,staff_info, poli_room, policlinic  "
					+ "WHERE A.shift_guid = shift_table.guid "
					+ "AND shift_table.room_guid = poli_room.guid "
					+ "AND poli_room.poli_guid = policlinic.guid "
					+ "AND shift_table.s_id = staff_info.s_id "
					+ "AND (A.case_finish = 'F' OR A.case_finish IS NULL) "
					+ "AND A.p_no = patients_info.p_no "
					+ "AND policlinic.type = 'DM' " + "AND A.reg_time LIKE '"
					+ date + "%' " + "ORDER BY State, A.visits_no";
		}
		try {
			System.out.println(sql);
			rs = DBC.executeQuery(sql);
			((DefaultTableModel) this.m_Tab.getModel()).setRowCount(0);
			this.m_Tab.setModel(HISModel.getModel(rs));
			rs.last();
			setCloumnWidth(this.m_Tab);

			if (m_SysName.equals("dia")) {
				Object[][] array = { { "O", new Color(204, 255, 153) },
						{ "F", new Color(204, 255, 153) } };
				TabTools.setTabColor(m_Tab, 3, array);
				TabTools.setHideColumn(this.m_Tab, 11);
			} else if (m_SysName.equals("case")) {
				Object[][] array = { { "F", new Color(204, 255, 153) } };
				TabTools.setTabColor(m_Tab, 3, array);
				TabTools.setHideColumn(m_Tab, 0);
				TabTools.setHideColumn(m_Tab, 11);
			} else if (m_SysName.equals("lab")) {
				Object[][] array = { { "1", new Color(250, 232, 176) } };
				TabTools.setTabColor(m_Tab, 12, array);
				TabTools.setHideColumn(this.m_Tab, 0);
				//TabTools.setHideColumn(this.m_Tab, 2);
				TabTools.setHideColumn(this.m_Tab, 3);
				TabTools.setHideColumn(this.m_Tab, 11);
				// TabTools.setHideColumn(this.m_Tab,12);
			} else if (m_SysName.equals("xray")) {
				TabTools.setHideColumn(this.m_Tab, 0);
				//TabTools.setHideColumn(this.m_Tab, 2);
				TabTools.setHideColumn(this.m_Tab, 3);
				TabTools.setHideColumn(this.m_Tab, 11);
			}
			DBC.closeConnection(rs);
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException ex) {
			}
		}
	}

	// @Override
	// public void interrupt(){
	// super.interrupt();
	// try{
	// DBC.closeConnection(rs);
	// System.out.println("DBC.closeConnection(rs)");
	// }catch(SQLException ex){}
	// try {
	// finalize();
	// } catch (Throwable ex) {}
	// }
}
