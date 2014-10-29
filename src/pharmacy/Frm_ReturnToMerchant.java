package pharmacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

import org.his.JPAUtil;
import org.his.bind.MedicineReturnTableBind;

import errormessage.StoredErrorMessage;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;
import cc.johnwu.sql.HISPassword;
import multilingual.Language;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frm_ReturnToMerchant extends JFrame {
	private JPanel contentPane;
	private JTextField txt_Search;
	private JScrollPane scrollPane_MedList;
	private JTable tab_MedicineList;
	private static final Language paragraph = Language.getInstance();
	private Frm_Pharmacy Frm_Parent;
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	/**
	 * Create the frame.
	 */
	public Frm_ReturnToMerchant(Frm_Pharmacy Frm_Parent) {
		this.Frm_Parent = Frm_Parent;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {	
				btn_CloseActionPerformed(null);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 692);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 685, 0 };
		gbl_contentPane.rowHeights = new int[] { 47, 582, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.weightx = 1.0;
		gbc_panel.weighty = 0.1;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 590, 97, 0 };
		gbl_panel.rowHeights = new int[] { 37, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		txt_Search = new JTextField();
		GridBagConstraints gbc_txt_Search = new GridBagConstraints();
		gbc_txt_Search.weightx = 0.7;
		gbc_txt_Search.weighty = 0.1;
		gbc_txt_Search.fill = GridBagConstraints.BOTH;
		gbc_txt_Search.insets = new Insets(0, 0, 0, 5);
		gbc_txt_Search.gridx = 0;
		gbc_txt_Search.gridy = 0;
		panel.add(txt_Search, gbc_txt_Search);
		txt_Search.setColumns(10);

		JButton btn_Search = new JButton(paragraph.getString("SEARCH"));
		btn_Search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_SearchActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btn_Search = new GridBagConstraints();
		gbc_btn_Search.weightx = 0.3;
		gbc_btn_Search.weighty = 0.1;
		gbc_btn_Search.fill = GridBagConstraints.BOTH;
		gbc_btn_Search.gridx = 1;
		gbc_btn_Search.gridy = 0;
		panel.add(btn_Search, gbc_btn_Search);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.weightx = 1.0;
		gbc_panel_1.weighty = 0.9;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 568, 87, 0 };
		gbl_panel_1.rowHeights = new int[] { 515, 32, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		scrollPane_MedList = new JScrollPane();
		tab_MedicineList = new JTable();
		scrollPane_MedList.setViewportView(tab_MedicineList);
		GridBagConstraints gbc_scrollPane_MedList = new GridBagConstraints();
		gbc_scrollPane_MedList.weightx = 1.0;
		gbc_scrollPane_MedList.weighty = 0.9;
		gbc_scrollPane_MedList.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_MedList.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_MedList.gridwidth = 2;
		gbc_scrollPane_MedList.gridx = 0;
		gbc_scrollPane_MedList.gridy = 0;
		panel_1.add(scrollPane_MedList, gbc_scrollPane_MedList);
		tab_MedicineList.setModel(new DefaultTableModel(
				new String[][] { { "No Information." } },
				new String[] { "Message" }));

		JButton btn_Return = new JButton(
				paragraph.getString("RETURNTOMERCHANT"));
		btn_Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_ReturnActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btn_Return = new GridBagConstraints();
		gbc_btn_Return.weightx = 0.7;
		gbc_btn_Return.weighty = 0.1;
		gbc_btn_Return.anchor = GridBagConstraints.EAST;
		gbc_btn_Return.fill = GridBagConstraints.BOTH;
		gbc_btn_Return.insets = new Insets(0, 0, 0, 5);
		gbc_btn_Return.gridx = 0;
		gbc_btn_Return.gridy = 1;
		panel_1.add(btn_Return, gbc_btn_Return);

		JButton btn_Close = new JButton(paragraph.getString("CLOSE"));
		btn_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_CloseActionPerformed(e);
			}
		});
		GridBagConstraints gbc_btn_Close = new GridBagConstraints();
		gbc_btn_Close.weightx = 0.3;
		gbc_btn_Close.weighty = 0.1;
		gbc_btn_Close.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btn_Close.fill = GridBagConstraints.BOTH;
		gbc_btn_Close.gridx = 1;
		gbc_btn_Close.gridy = 1;
		panel_1.add(btn_Close, gbc_btn_Close);
	}

	private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {
		String reg_guid = txt_Search.getText();
		showMedicineList(reg_guid);
	}

	private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.print(System.identityHashCode(Frm_Parent)+"\n");
		Frm_Parent.setEnabled(true);
		Frm_Parent.initWorkList();
		this.dispose();
	}

	private void btn_ReturnActionPerformed(java.awt.event.ActionEvent evt) {
		if (tab_MedicineList.getColumnCount() == 1) {
			JOptionPane.showMessageDialog(null, "nothing returned");
			return;
		}
		String sql = "";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DBC.s_ServerURL,
					DBC.s_ServerName, DBC.s_ServerPasswd);
			conn.setAutoCommit(false);
			int i = 0;
			for (i = 0; i < tab_MedicineList.getRowCount(); i++) {
				if ((Boolean) tab_MedicineList.getValueAt(i, 0) == true) {
					sql = "UPDATE medicine_stock SET medicine_stock.return_medicine_time=now() "
							+ "WHERE medicine_stock.guid = '" + tab_MedicineList.getValueAt(i, 1) + "'";
					conn.prepareStatement(sql).executeUpdate();
					conn.commit();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		showMedicineList(txt_Search.getText());
		JOptionPane.showMessageDialog(null, "Save Complete");
	}

	private void showMedicineList(String reg_guid) {
		String sqlMedicines = "SELECT "
				+ "medicine_stock.guid, medicines.code, medicines.item, medicine_stock.dosage, medicines.unit, "
				+ "medicine_stock.usage, medicine_stock.unit_price, medicine_stock.price "
				+ "FROM medicines, medicine_stock, registration_info "
				+ "WHERE registration_info.guid = '" + txt_Search.getText()
				+ "' "
				+ "AND medicine_stock.reg_guid = registration_info.guid "
				+ "AND medicine_stock.return_medicine_time IS NULL "
				+ "AND medicines.code = medicine_stock.m_code";
		ResultSet rsMedicines = null;
		try {
			String[] MedTitle = { "", "",
					paragraph.getString("COL_MEDICINE_CODE"),
					paragraph.getString("COL_MEDICINE_NAME"),
					paragraph.getString("COL_MEDICINE_DOSAGE"),
					paragraph.getString("COL_MEDICINE_UNIT"),
					paragraph.getString("COL_MEDICINE_USAGE"),
					paragraph.getString("COL_MEDICINE_UNIT_PRICE"),
					paragraph.getString("COL_MEDICINE_TOTAL_PRICE") };
			rsMedicines = DBC.executeQuery(sqlMedicines);
			rsMedicines.last();
			Object[][] MedicineList = new Object[rsMedicines.getRow()][9];

			rsMedicines.beforeFirst();

			int i = 0;
			while (rsMedicines.next()) {
				MedicineList[i][0] = false;
				MedicineList[i][1] = rsMedicines.getString(1);
				MedicineList[i][2] = rsMedicines.getString(2);
				MedicineList[i][3] = rsMedicines.getString(3);
				MedicineList[i][4] = rsMedicines.getString(4);
				MedicineList[i][5] = rsMedicines.getString(5);
				MedicineList[i][6] = rsMedicines.getString(6);
				MedicineList[i][7] = rsMedicines.getString(7);
				MedicineList[i][7] = rsMedicines.getString(8);
				i = i + 1;
			}

			tab_MedicineList.setModel(new DefaultTableModel(MedicineList,
					MedTitle) {
				private static final long serialVersionUID = 1379918560104957676L;

				@Override
				public Class<?> getColumnClass(int columnIndex) {
					if (columnIndex == 0) {
						return Boolean.class;
					} else {
						return String.class;
					}
				}

				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 0) {
						return true;
					} else {
						return false;
					}
				}
			});
			tab_MedicineList.getColumnModel().getColumn(1).setMinWidth(0);
			tab_MedicineList.getColumnModel().getColumn(1).setMaxWidth(0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rsMedicines);
			} catch (SQLException e) {
				ErrorMessage.setData(
						"Pharmacy",
						"Frm_ReturnToMerchant",
						"print(Graphics g, PageFormat pf, int pageIndex)",
						e.toString().substring(
								e.toString().lastIndexOf(".") + 1,
								e.toString().length()));
				Logger.getLogger(RefreshPharmacy.class.getName()).log(
						Level.SEVERE, null, e);
			}
		}
	}
}
