package casemgmt;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

public class Tab_ConfirmEducation extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -698594221923096308L;

	private String regGuid;
	private String pNo;
	private String[] title = { "", "Code", "Item", "ID", "Uesr", "Check",
			"Acceptance", "guid" };
	private Frm_Case parent;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_ConfirmEducation(String pNo, String regGuid) {
		this.regGuid = regGuid;
		this.pNo = pNo;
		initComponents();
		init();
	}

	private void init() {
		// ----tab_HealthTeach 衛教確認---------------------
		Object[][] dataArray = null;
		ResultSet rs = null;

		try {
			String sql = "";

			sql = "SELECT health_teach_item.item, health_teach.guid, "
					+ "concat(staff_info.firstname,'  ',staff_info.lastname) AS user, "
					+ "health_teach_item.code, health_teach.s_id, health_teach.confirm, "
					+ "health_teach.acceptance FROM health_teach_item "
					+ "LEFT JOIN (health_teach LEFT JOIN staff_info ON health_teach.s_id = staff_info.s_id "
					+ " ) "
					+ "ON health_teach_item.code = health_teach.hti_code AND health_teach.reg_guid = '"
					+ this.regGuid + "'";

			rs = DBC.executeQuery(sql);
			rs.last();
			dataArray = new Object[rs.getRow()][8];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
				dataArray[i][1] = rs.getString("code");
				dataArray[i][2] = rs.getString("item");
				if (rs.getString("s_id") != null) {
					dataArray[i][3] = rs.getString("s_id");
				}

				if (rs.getString("user") != null) {
					dataArray[i][4] = rs.getString("user");
				}

				if (rs.getString("confirm") != null
						&& rs.getString("confirm").equals("1")) {
					dataArray[i][5] = true;
				} else {
					dataArray[i][5] = false;
				}

				if (rs.getString("acceptance") != null
						&& !rs.getString("acceptance").equals("0")) {
					if (rs.getString("acceptance").equals("1")) {
						dataArray[i][6] = "Excellent";
					} else if (rs.getString("acceptance").equals("2")) {
						dataArray[i][6] = "Good";
					} else if (rs.getString("acceptance").equals("3")) {
						dataArray[i][6] = "<html><font color='FF0000'>Poor</font></html>";
					}
				} else {
					dataArray[i][6] = "";
				}

				if (rs.getString("guid") != null) {
					dataArray[i][7] = rs.getString("guid");
				}

				dataArray[i][1] = rs.getString("code");
				dataArray[i][2] = rs.getString("item");
				dataArray[i][0] = i + 1;
				i++;
			}

			DefaultTableModel TableModel = new DefaultTableModel(dataArray,
					title) {

				/**
						 * 
						 */
				private static final long serialVersionUID = -2995151919565263320L;

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					if (columnIndex == 5 || columnIndex == 6) {
						return true;
					} else {
						return false;
					}
				}
			};
			tab_HealthTeach.setModel(TableModel);
			TableColumn columnNumber = tab_HealthTeach.getColumnModel()
					.getColumn(0);
			common.TabTools.setHideColumn(tab_HealthTeach, 1);
			TableColumn columnName = tab_HealthTeach.getColumnModel()
					.getColumn(2);
			common.TabTools.setHideColumn(tab_HealthTeach, 3);
			TableColumn columnUser = tab_HealthTeach.getColumnModel()
					.getColumn(4);
			TableColumn columnChoose = tab_HealthTeach.getColumnModel()
					.getColumn(5);
			TableColumn columnAcceptance = tab_HealthTeach.getColumnModel()
					.getColumn(6);
			common.TabTools.setHideColumn(tab_HealthTeach, 7);
			// set column width
			columnNumber.setMaxWidth(30);
			columnName.setPreferredWidth(250);
			columnUser.setPreferredWidth(50);
			columnChoose.setMaxWidth(40);
			// columnAcceptance.setPreferredWidth(50);
			final JComboBox com_Acceptance = new JComboBox();

			com_Acceptance.addItem("");
			com_Acceptance.addItem("Excellent");
			com_Acceptance.addItem("Good");
			com_Acceptance
					.addItem("<html><font color='FF0000'>Poor</font></html>");
			columnAcceptance
					.setCellEditor(new DefaultCellEditor(com_Acceptance));

			columnChoose
					.setCellRenderer(new TableTriStateCell.TriStateCellRenderer());
			columnChoose
					.setCellEditor(new TableTriStateCell.TriStateCellEditor());
			tab_HealthTeach.setRowHeight(30);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void initComponents() {
		btn_ConSave = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		tab_HealthTeach = new javax.swing.JTable();

		btn_ConSave.setText("Save");
		btn_ConSave.setEnabled(false);
		btn_ConSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_ConSaveActionPerformed(evt);
			}
		});

		tab_HealthTeach.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {} }, title));
		tab_HealthTeach.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_HealthTeachMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(tab_HealthTeach);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				this);
		setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																791,
																Short.MAX_VALUE)
														.addComponent(
																btn_ConSave,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																86,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												279, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btn_ConSave)
										.addContainerGap()));
		//
		// jTabbedPane1.addTab("Confirm the completion of health education",
		// jPanel2);
	}

	private void tab_HealthTeachMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_HealthTeachMouseClicked
		// if (!m_From.equals("dia")) {
		if (tab_HealthTeach.getSelectedColumn() == 5
				|| tab_HealthTeach.getSelectedColumn() == 6) {
			btn_ConSave.setEnabled(true);
		}

		if (tab_HealthTeach.getValueAt(tab_HealthTeach.getSelectedRow(), 5)
				.toString().equals("true")) {
			tab_HealthTeach.setValueAt(UserInfo.getUserID(),
					tab_HealthTeach.getSelectedRow(), 3);
			tab_HealthTeach.setValueAt(UserInfo.getUserName(),
					tab_HealthTeach.getSelectedRow(), 4);
		} else {
			tab_HealthTeach.setValueAt("", tab_HealthTeach.getSelectedRow(), 3);
			tab_HealthTeach.setValueAt("", tab_HealthTeach.getSelectedRow(), 4);
		}
		// }
	}

	private void btn_ConSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_ConSaveActionPerformed
		for (int i = 0; i < tab_HealthTeach.getRowCount(); i++) {

			if (tab_HealthTeach.getValueAt(i, 3) == null
					|| tab_HealthTeach.getValueAt(i, 3).toString().trim()
							.equals(""))
				continue;

			try {
				if (tab_HealthTeach.getValueAt(i, 7) == null) {
					System.out.println("INININININININININ");
					String thcheck = "0";
					if (tab_HealthTeach.getValueAt(i, 5).toString()
							.equals("true")) {
						thcheck = "1";
					}
					String acceptance = "0";
					if (tab_HealthTeach.getValueAt(i, 6).toString()
							.equals("Excellent")) {
						acceptance = "1";
					} else if (tab_HealthTeach.getValueAt(i, 6).toString()
							.equals("Good")) {
						acceptance = "2";
					} else if (tab_HealthTeach
							.getValueAt(i, 6)
							.toString()
							.equals("<html><font color='FF0000'>Poor</font></html>")) {
						acceptance = "3";
					}

					String sql = "INSERT INTO health_teach (guid, hti_code, reg_guid, s_id, confirm, acceptance) "
							+ "VALUES (uuid(), '"
							+ tab_HealthTeach.getValueAt(i, 1)
							+ "','"
							+ regGuid + "', ";

					if (tab_HealthTeach.getValueAt(i, 3) == null) {
						sql += "NULL, ";
					} else {
						sql += "'" + tab_HealthTeach.getValueAt(i, 3) + "', ";
					}
					sql += " '" + thcheck + "','" + acceptance + "' )";

					System.out.println(sql);
					DBC.executeUpdate(sql);

				} else {
					System.out.println("UPUPUPUPUUPUPUPUUPUP");
					// 做UPDATE的動作
					String thcheck = "0";
					if (tab_HealthTeach.getValueAt(i, 5).toString()
							.equals("true")) {
						thcheck = "1";
					}

					String acceptance = "0";
					if (tab_HealthTeach.getValueAt(i, 6).toString()
							.equals("Excellent")) {
						acceptance = "1";
					} else if (tab_HealthTeach.getValueAt(i, 6).toString()
							.equals("Good")) {
						acceptance = "2";
					} else if (tab_HealthTeach
							.getValueAt(i, 6)
							.toString()
							.equals("<html><font color='FF0000'>Poor</font></html>")) {
						acceptance = "3";
					}

					String sql = "UPDATE health_teach SET " + "guid = uuid(),"
							+ "hti_code = '" + tab_HealthTeach.getValueAt(i, 1)
							+ "'," + "reg_guid = '" + regGuid + "',";

					if (tab_HealthTeach.getValueAt(i, 3) == null) {
						sql += "s_id = NULL, ";
					} else {
						sql += "s_id = '" + tab_HealthTeach.getValueAt(i, 3)
								+ "', ";
					}

					sql += " confirm = '" + thcheck + "'," + "acceptance = '"
							+ acceptance + "'" + " WHERE hti_code = '"
							+ tab_HealthTeach.getValueAt(i, 1) + "'";
					System.out.println(sql);
					DBC.executeUpdate(sql);
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "Save Complete");
		btn_ConSave.setEnabled(false);
	}

	public javax.swing.JButton btn_ConSave;
	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JTable tab_HealthTeach;

}
