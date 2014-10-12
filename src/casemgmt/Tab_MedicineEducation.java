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

public class Tab_MedicineEducation extends JPanel {
	private static final long serialVersionUID = -2973240376137842576L;
	private String regGuid;
	private String pNo;
	private String[] title = { "", "Guid", "Code", "Item", "Injection" };
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
		btn_ConSave.setEnabled(true);
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

	private void btn_ConSaveActionPerformed(java.awt.event.ActionEvent evt) {
		for (int i = 0; i < tab_MedicineTeach.getRowCount(); i++) {
			if ((Boolean) tab_MedicineTeach.getValueAt(i, 0) == true) {
				try {
					String sql = "UPDATE medicine_stock SET "
							+ "teach_complete = '1' "
							+ "WHERE medicine_stock.guid = '"
							+ (String) tab_MedicineTeach.getValueAt(i, 1)
							+ "'";
					System.out.println(sql);
					DBC.executeUpdate(sql);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		JOptionPane.showMessageDialog(null, "Save Complete");
	}

	public javax.swing.JButton btn_ConSave;
	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JTable tab_MedicineTeach;

}
