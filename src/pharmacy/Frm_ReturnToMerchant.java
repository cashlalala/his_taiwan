package pharmacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

import org.his.JPAUtil;
import org.his.bind.MedicineReturnTableBind;

import errormessage.StoredErrorMessage;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISPassword;
import multilingual.Language;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frm_ReturnToMerchant extends JFrame {
	private JPanel contentPane;
	private JTextField txt_Search;
	private JTable tab_MedicineList;
	private static final Language paragraph = Language.getInstance();

	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	/**
	 * Create the frame.
	 */
	public Frm_ReturnToMerchant() {
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

		tab_MedicineList = new JTable();
		GridBagConstraints gbc_tab_MedicineList = new GridBagConstraints();
		gbc_tab_MedicineList.weightx = 1.0;
		gbc_tab_MedicineList.weighty = 0.9;
		gbc_tab_MedicineList.fill = GridBagConstraints.BOTH;
		gbc_tab_MedicineList.insets = new Insets(0, 0, 5, 0);
		gbc_tab_MedicineList.gridwidth = 2;
		gbc_tab_MedicineList.gridx = 0;
		gbc_tab_MedicineList.gridy = 0;
		panel_1.add(tab_MedicineList, gbc_tab_MedicineList);

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
		// todo close

	}

	private void btn_ReturnActionPerformed(java.awt.event.ActionEvent evt) {
		// todo return
		String sql = "";
		Connection conn=null;
		try {
			conn = DriverManager.getConnection(DBC.s_ServerURL,
					DBC.s_ServerName, DBC.s_ServerPasswd);
			conn.setAutoCommit(false);
			conn.prepareStatement(sql).executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void showMedicineList(String reg_guid) {
		String sqlMedicines = "SELECT "
				+ "medicines.code, medicines.item, medicine_stock.dosage, medicines.unit, "
				+ "medicine_stock.usage, medicine_stock.unit_price, medicine_stock.price "
				+ "FROM medicines, medicine_stock, registration_info "
				+ "WHERE registration_info.guid = '"
				+ reg_guid
				+ "' "
				+ "AND medicine_stock.reg_guid = registration_info.guid "
				+ "AND medicines.code = medicine_stock.m_code";
		ResultSet rsMedicines = null;
		try {
			Connection conn = DriverManager.getConnection(DBC.s_ServerURL,
					DBC.s_ServerName, DBC.s_ServerPasswd);
			Statement stmt = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rsMedicines = stmt.executeQuery(sqlMedicines);
			tab_MedicineList.setModel(new MedicineReturnTableBind(rsMedicines));
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
