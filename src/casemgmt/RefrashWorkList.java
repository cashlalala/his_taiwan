/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package casemgmt;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import multilingual.Language;
import cc.johnwu.date.DateMethod;
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

	private Frm_WorkList parentFrame;

	public Frm_WorkList getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(Frm_WorkList parentFrame) {
		this.parentFrame = parentFrame;
	}

	public int curItemCnt;

	protected RefrashWorkList(javax.swing.JTable tab, long time,
			String SysName, String finished) {
		m_SysName = SysName;
		curItemCnt = 0;

		sql = "SELECT A.visits_no AS '"
				+ paragraph.getString("COL_NO")
				+ "', "
				+ "'' AS '"
				+ paragraph.getString("COL_REGISTER")
				+ "', "
				+ "(SELECT CASE COUNT(registration_info.guid) "
				+ "WHEN 0 THEN '*' "
				+ "END  "
				+ "FROM outpatient_services, registration_info "
				+ "WHERE registration_info.guid = outpatient_services.reg_guid AND p_no = A.p_no ) AS '"
				+ paragraph.getString("COL_FIRST")
				+ "', "
				+ "CASE A.case_finish WHEN 'F' THEN 'F' END 'Status', "
				+ "A.reg_time AS '"
				+ paragraph.getString("COL_REGTIME")
				+ "', A.p_no AS '"
				+ paragraph.getString("COL_PATIENTNO")
				+ "', "
				+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS '"
				+ paragraph.getString("COL_NAME")
				+ "', "
				+ "patients_info.birth AS '"
				+ paragraph.getString("COL_BIRTH")
				+ "', "
				+ "patients_info.gender AS '"
				+ paragraph.getString("COL_GENDER")
				+ "', "
				+ "concat(patients_info.bloodtype,patients_info.rh_type) AS '"
				+ paragraph.getString("COL_BLOOD")
				+ "', "
				+ "patients_info.ps AS '"
				+ paragraph.getString("COL_PS")
				+ "', "
				+ "A.guid, policlinic.type  "
				+ "FROM registration_info AS A, patients_info, shift_table,staff_info, poli_room, policlinic  "
				+ "WHERE A.shift_guid = shift_table.guid "
				+ "AND shift_table.room_guid = poli_room.guid "
				+ "AND shift_table.shift_date = '" + DateMethod.getTodayYMD()
				+ "' " + "AND shift_table.shift = '"
				+ DateMethod.getNowShiftNum() + "' "
				+ "AND poli_room.poli_guid = policlinic.guid "
				+ "AND shift_table.s_id = staff_info.s_id "
				+ "AND (A.case_finish = 'F' OR A.case_finish IS NULL) "
				+ "AND A.p_no = patients_info.p_no "
				+ "AND policlinic.type = 'DM' "
				+ "ORDER BY Status, A.visits_no";

		this.m_Tab = tab;
		this.m_Time = time;
		try {
			rs = DBC.executeQuery(sql);
			((DefaultTableModel) this.m_Tab.getModel()).setRowCount(0);
			this.m_Tab.setModel(HISModel.getModel(rs));
			rs.last();
			setCloumnWidth(this.m_Tab);

			Object[][] array = { { "F", Constant.FINISH_COLOR } };
			TabTools.setTabColor(m_Tab, 3, array);
			TabTools.setHideColumn(m_Tab, 0);
			TabTools.setHideColumn(m_Tab, 11);

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
					String check_sql = "SELECT MAX(touchtime) "
							+ "FROM registration_info,shift_table "
							+ "WHERE registration_info.shift_guid = shift_table.guid "
							+ "AND shift_table.shift_date = '"
							+ DateMethod.getTodayYMD() + "' "
							+ "AND shift_table.shift = '"
							+ DateMethod.getNowShiftNum() + "' ";
					rs = stmt.executeQuery(check_sql);
					if (rs.next()
							&& (rs.getString(1) == null || rs.getString(1)
									.equals(m_LastTouchTime))) {
						RefrashWorkList.sleep(m_Time);
						continue;
					}
					m_LastTouchTime = rs.getString(1);
					rs.close();

					rs = stmt.executeQuery(sql);
					curItemCnt = 0;
					if (rs.last()) {
						this.m_Guid = new String[rs.getRow()];
						((DefaultTableModel) m_Tab.getModel()).setRowCount(rs
								.getRow());
						rs.beforeFirst();
						int colCnt = rs.getMetaData().getColumnCount();
						while (rs.next()) {
							for (int col = 0; col < colCnt; col++)
								m_Tab.setValueAt(rs.getString(col + 1),
										curItemCnt, col);
							curItemCnt++;
						}
					} else {
						((DefaultTableModel) m_Tab.getModel()).setRowCount(0);
					}
					parentFrame.lbl_InpNoVal
							.setText(String.valueOf(curItemCnt));
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
				if (!m_SysName.equals("inp"))
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
	public void getSelectDate(String date, String finished) {
		sql = "SELECT A.visits_no AS '"
				+ paragraph.getLanguage(line, "COL_NO")
				+ "', "
				+ "'' AS '"
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
		try {
			System.out.println(sql);
			rs = DBC.executeQuery(sql);
			((DefaultTableModel) this.m_Tab.getModel()).setRowCount(0);
			this.m_Tab.setModel(HISModel.getModel(rs));
			rs.last();
			setCloumnWidth(this.m_Tab);

			Object[][] array = { { "F", new Color(204, 255, 153) } };
			TabTools.setTabColor(m_Tab, 3, array);
			TabTools.setHideColumn(m_Tab, 0);
			TabTools.setHideColumn(m_Tab, 11);

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
}
