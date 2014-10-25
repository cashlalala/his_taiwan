package casemgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tab_MedicineEducation extends JPanel implements ISaveable {
	private static final long serialVersionUID = -2973240376137842576L;
	@SuppressWarnings("unused")
	private String regGuid;
	private String pNo;
	private String[] title = { "", "Guid", "Code", "Item", "Injection" };
	@SuppressWarnings("unused")
	private Frm_Case parent;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_MedicineEducation(String pNo, String regGuid) {
		this.regGuid = regGuid;
		this.pNo = pNo;
		initComponents();
		init();
	}

	private void init() {
		Object[][] dataArray = null;
		ResultSet rs = null;
		try {
			String sql = "";

			sql = "SELECT medicine_stock.guid, medicine_stock.m_code, medicines.item, medicines.injection "
					+ "FROM registration_info, medicine_stock, medicines "
					+ "WHERE registration_info.p_no = '"
					+ pNo
					+ "' "
					+ "AND medicine_stock.reg_guid = registration_info.guid "
					+ "AND medicine_stock.teach_complete = '0' "
					+ "AND medicines.code = medicine_stock.m_code";
			System.out.print(sql + "\n");
			rs = DBC.executeQuery(sql);
			rs.last();
			dataArray = new Object[rs.getRow()][5];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				dataArray[i][0] = false;
				dataArray[i][1] = rs.getString("medicine_stock.guid");
				dataArray[i][2] = rs.getString("medicine_stock.m_code");
				dataArray[i][3] = rs.getString("medicines.item");
				dataArray[i][4] = rs.getString("medicines.injection");
				i++;
			}

			DefaultTableModel TableModel = new DefaultTableModel(dataArray,
					title) {
				@Override
				public void setValueAt(Object aValue, int row, int column) {
					super.setValueAt(aValue, row, column);
					onMouseClicked(column);
				}

				/**
						 * 
						 */
				private static final long serialVersionUID = -8914636449822252206L;

				@Override
				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 0) {
						return Boolean.class;
					} else {
						return String.class;
					}
				}

				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					if (columnIndex == 0) {
						return true;
					} else {
						return false;
					}
				}
			};
			tab_MedicineTeach.setModel(TableModel);
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
		tab_MedicineTeach = new javax.swing.JTable();

		btn_ConSave.setText("Save");
		btn_ConSave.setEnabled(false);
		btn_ConSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_ConSaveActionPerformed(evt);
			}
		});

		tab_MedicineTeach.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {} }, title));

		jScrollPane1.setViewportView(tab_MedicineTeach);

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
	}

	protected void onMouseClicked(int column) {
		if (column == 0) {
			this.btn_ConSave.setEnabled(true);
		}
	}

	private void btn_ConSaveActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			save();
			JOptionPane.showMessageDialog(null, "Save Complete");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Save Failed: " + e.getMessage());
			btn_ConSave.setEnabled(true);
		}
	}

	public javax.swing.JButton btn_ConSave;
	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JTable tab_MedicineTeach;

	@Override
	public boolean isSaveable() {
		return btn_ConSave.isEnabled();
	}

	@Override
	public void save() throws Exception {
		Connection conn = DBC.getConnectionExternel();
		try {
			conn.setAutoCommit(false);
			save(conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	private static Logger logger = LogManager
			.getLogger(Tab_MedicineEducation.class.getName());

	@Override
	public void save(Connection conn) throws Exception {
		String msg = "";
		String sql = "UPDATE medicine_stock SET " + "teach_complete = '1' "
				+ "WHERE medicine_stock.guid = ? ";
		logger.debug("[{}][{}] {}", UserInfo.getUserID(),
				UserInfo.getUserName(), sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		for (int i = 0; i < tab_MedicineTeach.getRowCount(); i++) {
			if ((Boolean) tab_MedicineTeach.getValueAt(i, 0) == true) {
				logger.debug("[{}][{}] value: {}", UserInfo.getUserID(),
						UserInfo.getUserName(),
						tab_MedicineTeach.getValueAt(i, 1));
				ps.setString(1, (String) tab_MedicineTeach.getValueAt(i, 1));
				ps.addBatch();
			}
		}
		try {
			ps.executeBatch();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		if (msg.isEmpty())
			btn_ConSave.setEnabled(false);

	}

}
