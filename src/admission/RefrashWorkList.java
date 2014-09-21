/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package admission;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import multilingual.Language;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

import common.TabTools;

/**
 * 
 * @author steven
 */
public class RefrashWorkList extends Thread {
	private String m_SysName; // 進入系統名稱
	private Language paragraph = Language.getInstance();

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

	private List<String> colList = Arrays.asList(paragraph.getString("COL_NO"),
			paragraph.getString("STATUS"),
			paragraph.getString("COL_CHECKINTIME"),
			paragraph.getString("COL_NAME"), paragraph.getString("COL_BIRTH"),
			paragraph.getString("COL_AGE"), paragraph.getString("COL_GENDER"),
			paragraph.getString("COL_BLOOD"), "MainDoctor",
			paragraph.getString("COL_POLICLINIC"),
			paragraph.getString("COL_BED_NO"), paragraph.getString("COL_NOTE"));

	protected RefrashWorkList(javax.swing.JTable tab, long time, String SysName) {
		m_SysName = SysName;
		curItemCnt = 0;
		sql = String
				.format("SELECT bd_rec.p_no as '%s', bd_rec.status as '%s', bd_rec.checkinTime as '%s',"
						+ "concat(pInfo.firstname,' ',pInfo.lastname) as '%s', pInfo.birth as '%s', "
						+ "TIMESTAMPDIFF(year,pInfo.birth, now()) as '%s', "
						+ "pInfo.gender as '%s', concat(pInfo.bloodtype,pInfo.rh_type) as '%s', "
						+ "concat(staff_info.firstname,' ',staff_info.lastname) as '%s', policlinic.name as '%s',"
						+ "bed_code.guid as '%s', bd_rec.note as '%s', bd_rec.guid, reg.guid as 'REG_GUID' "
						+ "FROM bed_record bd_rec, bed_code, patients_info pInfo, staff_info, policlinic, registration_info reg "
						+ "WHERE reg.type = 'I' and reg.bed_guid = bd_rec.guid and bd_rec.status = 'N' "
						+ "and bd_rec.bed_guid = bed_code.guid and bd_rec.p_no = pInfo.p_no "
						+ "and bed_code.poli_guid = policlinic.guid and bd_rec.mainDr_no = staff_info.s_no",
						colList.toArray());
		this.m_Tab = tab;
		this.m_Time = time;
		try {
			rs = DBC.executeQuery(sql);
			((DefaultTableModel) this.m_Tab.getModel()).setRowCount(0);
			this.m_Tab.setModel(HISModel.getModel(rs));
			rs.last();
			setCloumnWidth(this.m_Tab);

			if (SysName.equals("inp")) {
				TabTools.setHideColumn(this.m_Tab, 12);
				TabTools.setHideColumn(this.m_Tab, 13);
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
					Boolean isShowBtn = curItemCnt != 0;
					parentFrame.btn_Diagnostic.setEnabled(isShowBtn);
					parentFrame.btn_CheckOut.setEnabled(isShowBtn);
					parentFrame.btn_Reg.setEnabled(isShowBtn);

					String check_sql = "";

					if (!m_SysName.equals("inp")) {
						rs = stmt.executeQuery(check_sql);
						if (rs.next()
								&& (rs.getString(1) == null || rs.getString(1)
										.equals(m_LastTouchTime))) {
							RefrashWorkList.sleep(m_Time);
							continue;
						}
						m_LastTouchTime = rs.getString(1);
						rs.close();
					}

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
	public void setCondition(String dept, String doctor, String date) {

		try {

			sql = String
					.format("SELECT bd_rec.p_no as '%s', bd_rec.status as '%s', bd_rec.checkinTime as '%s',"
							+ "concat(pInfo.firstname,' ',pInfo.lastname) as '%s', pInfo.birth as '%s', "
							+ "TIMESTAMPDIFF(year,pInfo.birth, now()) as '%s', "
							+ "pInfo.gender as '%s', concat(pInfo.bloodtype,pInfo.rh_type) as '%s', "
							+ "concat(staff_info.firstname,' ',staff_info.lastname) as '%s', policlinic.name as '%s',"
							+ "bed_code.guid as '%s', bd_rec.note as '%s', bd_rec.guid, reg.guid as 'REG_GUID' "
							+ "FROM bed_record bd_rec, bed_code, patients_info pInfo, staff_info, policlinic, registration_info reg "
							+ "WHERE reg.type = 'I' and reg.bed_guid = bd_rec.guid and bd_rec.status = 'N' "
							+ "and bd_rec.bed_guid = bed_code.guid and bd_rec.p_no = pInfo.p_no "
							+ "and bd_rec.mainDr_no = staff_info.s_no "
							+ "and bed_code.poli_guid = policlinic.guid "
							+ "and bd_rec.checkinTime <= STR_TO_DATE('"
							+ date
							+ " 23:59:59', '%%Y-%%m-%%d %%H:%%i:%%s') "
							+ " and bed_code.poli_guid = "
							+ ((dept.isEmpty() || dept.equals("-")) ? "policlinic.guid"
									: String.format("'%s'", dept))
							+ " and bd_rec.mainDr_no = "
							+ ((doctor.isEmpty() || doctor.equals("-")) ? "staff_info.s_no"
									: String.format("'%s'", doctor))
							+ " order by bd_rec.checkinTime desc", colList
							.toArray());

			System.out.println(sql);
			rs = DBC.executeQuery(sql);
			((DefaultTableModel) this.m_Tab.getModel()).setRowCount(0);
			this.m_Tab.setModel(HISModel.getModel(rs));
			rs.last();
			setCloumnWidth(this.m_Tab);

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
