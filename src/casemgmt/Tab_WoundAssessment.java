package casemgmt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import multilingual.Language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Tab_WoundAssessment extends JPanel implements ISaveable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Language lang = Language.getInstance();
	private static Logger logger = LogManager
			.getLogger(Tab_WoundAssessment.class.getName());
	private String caseGuid;

	private JLabel lbl_WoundAssessment;
	private JPanel panel;

	private ChangeNotifyCheckBox chckbx_PHHD;
	private ChangeNotifyCheckBox chckbx_PHCD;
	private ChangeNotifyCheckBox chckbx_PHHC;
	private ChangeNotifyCheckBox chckbx_PHDM;
	private ChangeNotifyCheckBox chckbx_TypeA;
	private ChangeNotifyCheckBox chckbx_TypeB;
	private ChangeNotifyCheckBox chckbx_TypeC;
	private ChangeNotifyCheckBox chckbx_Malignancies;
	private ChangeNotifyCheckBox chckbx_HD;
	private ChangeNotifyCheckBox chckbx_PHNS;
	private ChangeNotifyCheckBox chckbx_Smoke;
	private ChangeNotifyCheckBox chckbx_Alcoholism;
	private ChangeNotifyCheckBox chckbx_OW;

	private JLabel lbl_CreateDate;
	private JTextField txt_CreateDate;

	private JLabel lbl_Other;
	private JTextField txt_Other;

	private JButton btn_Save;
	private JLabel lblSeverity;
	private JComboBox com_severity;

	public Tab_WoundAssessment(String CaseGuid) {
		setLayout(null);
		this.caseGuid = CaseGuid;

		lbl_WoundAssessment = new JLabel("WOUND_ASSESSMENT");
		lbl_WoundAssessment.setBounds(12, 12, 187, 15);
		add(lbl_WoundAssessment);

		panel = new JPanel();
		panel.setBounds(12, 30, 426, 258);
		add(panel);
		panel.setLayout(null);

		btn_Save = new JButton(lang.getString("SAVE"));

		chckbx_PHHD = new ChangeNotifyCheckBox(lang.getString("PHHD"), btn_Save);
		chckbx_PHHD.setBounds(0, 0, 206, 23);
		panel.add(chckbx_PHHD);

		chckbx_PHCD = new ChangeNotifyCheckBox(lang.getString("PHCD"), btn_Save);
		chckbx_PHCD.setBounds(0, 27, 206, 23);
		panel.add(chckbx_PHCD);

		chckbx_PHHC = new ChangeNotifyCheckBox(lang.getString("PHHC"), btn_Save);
		chckbx_PHHC.setBounds(0, 54, 206, 23);
		panel.add(chckbx_PHHC);

		chckbx_PHDM = new ChangeNotifyCheckBox(lang.getString("PHDM"), btn_Save);
		chckbx_PHDM.setBounds(0, 81, 206, 23);
		panel.add(chckbx_PHDM);

		chckbx_TypeA = new ChangeNotifyCheckBox(lang.getString("PHTYPEA"),
				btn_Save);
		chckbx_TypeA.setBounds(0, 108, 206, 23);
		panel.add(chckbx_TypeA);

		chckbx_TypeB = new ChangeNotifyCheckBox(lang.getString("PHTYPEB"),
				btn_Save);
		chckbx_TypeB.setBounds(0, 135, 206, 23);
		panel.add(chckbx_TypeB);

		chckbx_TypeC = new ChangeNotifyCheckBox(lang.getString("PHTYPEC"),
				btn_Save);
		chckbx_TypeC.setBounds(0, 162, 206, 23);
		panel.add(chckbx_TypeC);

		chckbx_Malignancies = new ChangeNotifyCheckBox(
				lang.getString("PHMALIG"), btn_Save);
		chckbx_Malignancies.setBounds(210, 0, 204, 23);
		panel.add(chckbx_Malignancies);

		chckbx_HD = new ChangeNotifyCheckBox(lang.getString("HD"), btn_Save);
		chckbx_HD.setBounds(210, 27, 204, 23);
		panel.add(chckbx_HD);

		chckbx_PHNS = new ChangeNotifyCheckBox(lang.getString("PHNS"), btn_Save);
		chckbx_PHNS.setBounds(210, 54, 204, 23);
		panel.add(chckbx_PHNS);

		chckbx_Smoke = new ChangeNotifyCheckBox(lang.getString("PHSMOKE"),
				btn_Save);
		chckbx_Smoke.setBounds(210, 81, 204, 23);
		panel.add(chckbx_Smoke);

		chckbx_Alcoholism = new ChangeNotifyCheckBox(
				lang.getString("PHALCOHOLISM"), btn_Save);
		chckbx_Alcoholism.setBounds(210, 108, 204, 23);
		panel.add(chckbx_Alcoholism);

		chckbx_OW = new ChangeNotifyCheckBox(lang.getString("PHOW"), btn_Save);
		chckbx_OW.setBounds(210, 135, 204, 23);
		panel.add(chckbx_OW);

		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_SaveactionPerformed();
			}
		});
		btn_Save.setBounds(297, 221, 117, 25);
		panel.add(btn_Save);

		lbl_CreateDate = new JLabel(lang.getString("WOUND_CREATE_DATE"));
		lbl_CreateDate.setBounds(210, 166, 91, 15);
		panel.add(lbl_CreateDate);

		txt_CreateDate = new JTextField();
		txt_CreateDate.setBounds(311, 164, 103, 19);
		panel.add(txt_CreateDate);
		txt_CreateDate.setColumns(10);

		lbl_Other = new JLabel(lang.getString("PHOTHER"));
		lbl_Other.setBounds(0, 193, 70, 15);
		panel.add(lbl_Other);

		txt_Other = new JTextField();
		txt_Other.setBounds(71, 193, 343, 19);
		panel.add(txt_Other);
		txt_Other.setColumns(10);

		lblSeverity = new JLabel("Severity :");
		lblSeverity.setBounds(341, 12, 59, 14);
		add(lblSeverity);

		com_severity = new JComboBox();
		com_severity.setModel(new DefaultComboBoxModel(new String[] { "L", "M",
				"H" }));
		com_severity.setBounds(399, 9, 39, 20);
		com_severity.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				btn_Save.setEnabled(true);
			}
		});
		add(com_severity);

		new Thread(new Runnable() {

			@Override
			public void run() {
				refreshData();
			}
		}).start();
		btn_Save.setEnabled(false);

	}

	private void btn_SaveactionPerformed() {
		try {
			save();
			JOptionPane.showMessageDialog(null, "Save Complete");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Save Failed: " + e.getMessage());
			btn_Save.setEnabled(true);
		}
	}

	private void refreshData() {
		String sqlPateint = "SELECT case_manage.p_no, severity FROM case_manage WHERE case_manage.guid='"
				+ caseGuid + "'";

		String sql = "SELECT * FROM wound_accessment WHERE wound_accessment.case_guid = '"
				+ this.caseGuid + "'";
		
		ResultSet rs = null;
		ResultSet rsPatient = null;
		try {
			rsPatient = DBC.executeQuery(sqlPateint);
			rsPatient.next();

			String severity = rsPatient.getString("severity");
			com_severity.setSelectedItem(severity);
			
			rs = DBC.executeQuery(sql);
			if (!rs.next()) {
				String p_no = rsPatient.getString("case_manage.p_no");
				String sqlInsert = "INSERT into wound_accessment SELECT "
						+ "uuid()," + " '" + this.caseGuid + "', '" + p_no
						+ "'";
				int i = 0;
				for (i = 0; i < 16; i++) {
					sqlInsert = sqlInsert + ", NULL";
				}
				DBC.executeUpdate(sqlInsert);
			} else {
				if (rs.getString("wound_accessment.Hypertension") != null
						&& ((String) rs
								.getString("wound_accessment.Hypertension"))
								.equals("Y")) {
					chckbx_PHHD.setSelected(true);
				} else {
					chckbx_PHHD.setSelected(false);
				}
				if (rs.getString("wound_accessment.BrainVessel") != null
						&& ((String) rs
								.getString("wound_accessment.BrainVessel"))
								.equals("Y")) {
					chckbx_PHCD.setSelected(true);
				} else {
					chckbx_PHCD.setSelected(false);
				}
				if (rs.getString("wound_accessment.Hyperlipidemia") != null
						&& ((String) rs
								.getString("wound_accessment.Hyperlipidemia"))
								.equals("Y")) {
					chckbx_PHHC.setSelected(true);
				} else {
					chckbx_PHHC.setSelected(false);
				}
				if (rs.getString("wound_accessment.Diabetes") != null
						&& ((String) rs.getString("wound_accessment.Diabetes"))
								.equals("Y")) {
					chckbx_PHDM.setSelected(true);
				} else {
					chckbx_PHDM.setSelected(false);
				}
				if (rs.getString("wound_accessment.HepatitisA") != null
						&& ((String) rs
								.getString("wound_accessment.HepatitisA"))
								.equals("Y")) {
					chckbx_TypeA.setSelected(true);
				} else {
					chckbx_TypeA.setSelected(false);
				}
				if (rs.getString("wound_accessment.HepatitisB") != null
						&& ((String) rs
								.getString("wound_accessment.HepatitisB"))
								.equals("Y")) {
					chckbx_TypeB.setSelected(true);
				} else {
					chckbx_TypeB.setSelected(false);
				}
				if (rs.getString("wound_accessment.HepatitisC") != null
						&& ((String) rs
								.getString("wound_accessment.HepatitisC"))
								.equals("Y")) {
					chckbx_TypeC.setSelected(true);
				} else {
					chckbx_TypeC.setSelected(false);
				}
				if (rs.getString("wound_accessment.Cancer") != null
						&& ((String) rs.getString("wound_accessment.Cancer"))
								.equals("Y")) {
					chckbx_Malignancies.setSelected(true);
				} else {
					chckbx_Malignancies.setSelected(false);
				}
				if (rs.getString("wound_accessment.HeartDisease") != null
						&& ((String) rs
								.getString("wound_accessment.HeartDisease"))
								.equals("Y")) {
					chckbx_HD.setSelected(true);
				} else {
					chckbx_HD.setSelected(false);
				}
				if (rs.getString("wound_accessment.NephroticSyndrome") != null
						&& ((String) rs
								.getString("wound_accessment.NephroticSyndrome"))
								.equals("Y")) {
					chckbx_PHNS.setSelected(true);
				} else {
					chckbx_PHNS.setSelected(false);
				}
				if (rs.getString("wound_accessment.Smoking") != null
						&& ((String) rs.getString("wound_accessment.Smoking"))
								.equals("Y")) {
					chckbx_Smoke.setSelected(true);
				} else {
					chckbx_Smoke.setSelected(false);
				}
				if (rs.getString("wound_accessment.Drinking") != null
						&& ((String) rs.getString("wound_accessment.Drinking"))
								.equals("Y")) {
					chckbx_Alcoholism.setSelected(true);
				} else {
					chckbx_Alcoholism.setSelected(false);
				}
				if (rs.getString("wound_accessment.OverWeight") != null
						&& ((String) rs
								.getString("wound_accessment.OverWeight"))
								.equals("Y")) {
					chckbx_OW.setSelected(true);
				} else {
					chckbx_OW.setSelected(false);
				}
				if (rs.getString("wound_accessment.createdatetime") != null) {
					txt_CreateDate.setText((String) rs
							.getString("wound_accessment.createdatetime"));
				}
				if (rs.getString("wound_accessment.OtherDiseaseHistory") != null) {
					txt_Other.setText((String) rs
							.getString("wound_accessment.OtherDiseaseHistory"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isSaveable() {
		return btn_Save.isEnabled();
	}

	@Override
	public void save() throws Exception {
		Connection conn = DBC.getConnectionExternel();
		try {
			save(conn);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public void save(Connection conn) throws Exception {
		String sql = "UPDATE wound_accessment SET";
		if (chckbx_PHHD.isSelected()) {
			sql = sql + " wound_accessment.Hypertension = 'Y', ";
		} else {
			sql = sql + " wound_accessment.Hypertension = 'N', ";
		}
		if (chckbx_PHCD.isSelected()) {
			sql = sql + " wound_accessment.BrainVessel = 'Y', ";
		} else {
			sql = sql + " wound_accessment.BrainVessel = 'N', ";
		}
		if (chckbx_PHHC.isSelected()) {
			sql = sql + " wound_accessment.Hyperlipidemia = 'Y', ";
		} else {
			sql = sql + " wound_accessment.Hyperlipidemia = 'N', ";
		}
		if (chckbx_PHDM.isSelected()) {
			sql = sql + " wound_accessment.Diabetes = 'Y', ";
		} else {
			sql = sql + " wound_accessment.Diabetes = 'N', ";
		}
		if (chckbx_TypeA.isSelected()) {
			sql = sql + " wound_accessment.HepatitisA = 'Y', ";
		} else {
			sql = sql + " wound_accessment.HepatitisA = 'N', ";
		}
		if (chckbx_TypeB.isSelected()) {
			sql = sql + " wound_accessment.HepatitisB = 'Y', ";
		} else {
			sql = sql + " wound_accessment.HepatitisB = 'N', ";
		}
		if (chckbx_TypeC.isSelected()) {
			sql = sql + " wound_accessment.HepatitisC = 'Y', ";
		} else {
			sql = sql + " wound_accessment.HepatitisC = 'N', ";
		}
		if (chckbx_Malignancies.isSelected()) {
			sql = sql + " wound_accessment.Cancer = 'Y', ";
		} else {
			sql = sql + " wound_accessment.Cancer = 'N', ";
		}
		if (chckbx_HD.isSelected()) {
			sql = sql + " wound_accessment.HeartDisease = 'Y', ";
		} else {
			sql = sql + " wound_accessment.HeartDisease = 'N', ";
		}
		if (chckbx_PHNS.isSelected()) {
			sql = sql + " wound_accessment.NephroticSyndrome = 'Y', ";
		} else {
			sql = sql + " wound_accessment.NephroticSyndrome = 'N', ";
		}
		if (chckbx_Smoke.isSelected()) {
			sql = sql + " wound_accessment.Smoking = 'Y', ";
		} else {
			sql = sql + " wound_accessment.Smoking = 'N', ";
		}
		if (chckbx_Alcoholism.isSelected()) {
			sql = sql + " wound_accessment.Drinking = 'Y', ";
		} else {
			sql = sql + " wound_accessment.Drinking = 'N', ";
		}
		if (chckbx_OW.isSelected()) {
			sql = sql + " wound_accessment.OverWeight = 'Y', ";
		} else {
			sql = sql + " wound_accessment.OverWeight = 'N', ";
		}
		if (!txt_CreateDate.getText().equals("")) {
			sql = sql + " wound_accessment.createdatetime = '"
					+ txt_CreateDate.getText() + "', ";
		}
		sql = sql + " wound_accessment.OtherDiseaseHistory = '"
				+ txt_Other.getText() + "', ";

		sql = sql + " wound_accessment.s_no = '" + UserInfo.getUserNO() + "'";

		logger.debug("[{}][{}] {}", UserInfo.getUserID(),
				UserInfo.getUserName(), sql);
		PreparedStatement ps = conn.prepareStatement(sql);

		String sqlSeverity = String.format(
				"update case_manage set severity = '%s' where guid = '%s'",
				com_severity.getSelectedItem(), caseGuid);
		logger.debug("[{}][{}] {}", UserInfo.getUserID(),
				UserInfo.getUserName(), sqlSeverity);
		PreparedStatement psSev = conn.prepareStatement(sqlSeverity);
		try {
			ps.executeUpdate();
			psSev.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (psSev != null)
				psSev.close();
		}

		btn_Save.setEnabled(false);
	}
}
