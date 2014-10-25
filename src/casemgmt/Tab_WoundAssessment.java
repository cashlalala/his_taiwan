package casemgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import multilingual.Language;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tab_WoundAssessment extends JPanel implements ISaveable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Language lang = Language.getInstance();
	private static Logger logger = LogManager
			.getLogger(Tab_WoundAssessment.class.getName());
	private String CaseGuid;

	private JLabel lbl_WoundAssessment;
	private JPanel panel;

	private JCheckBox chckbx_PHHD;
	private JCheckBox chckbx_PHCD;
	private JCheckBox chckbx_PHHC;
	private JCheckBox chckbx_PHDM;
	private JCheckBox chckbx_TypeA;
	private JCheckBox chckbx_TypeB;
	private JCheckBox chckbx_TypeC;
	private JCheckBox chckbx_Malignancies;
	private JCheckBox chckbx_HD;
	private JCheckBox chckbx_PHNS;
	private JCheckBox chckbx_Smoke;
	private JCheckBox chckbx_Alcoholism;
	private JCheckBox chckbx_OW;

	private JLabel lbl_CreateDate;
	private JTextField txt_CreateDate;

	private JLabel lbl_Other;
	private JTextField txt_Other;

	private JButton btn_Save;

	public Tab_WoundAssessment(String CaseGuid) {
		setLayout(null);
		this.CaseGuid = CaseGuid;

		lbl_WoundAssessment = new JLabel("WOUND_ASSESSMENT");
		lbl_WoundAssessment.setBounds(12, 12, 187, 15);
		add(lbl_WoundAssessment);

		panel = new JPanel();
		panel.setBounds(12, 30, 426, 258);
		add(panel);
		panel.setLayout(null);

		chckbx_PHHD = new JCheckBox(lang.getString("PHHD"));
		chckbx_PHHD.setBounds(0, 0, 206, 23);
		panel.add(chckbx_PHHD);

		chckbx_PHCD = new JCheckBox(lang.getString("PHCD"));
		chckbx_PHCD.setBounds(0, 27, 206, 23);
		panel.add(chckbx_PHCD);

		chckbx_PHHC = new JCheckBox(lang.getString("PHHC"));
		chckbx_PHHC.setBounds(0, 54, 206, 23);
		panel.add(chckbx_PHHC);

		chckbx_PHDM = new JCheckBox(lang.getString("PHDM"));
		chckbx_PHDM.setBounds(0, 81, 206, 23);
		panel.add(chckbx_PHDM);

		chckbx_TypeA = new JCheckBox(lang.getString("PHTYPEA"));
		chckbx_TypeA.setBounds(0, 108, 206, 23);
		panel.add(chckbx_TypeA);

		chckbx_TypeB = new JCheckBox(lang.getString("PHTYPEB"));
		chckbx_TypeB.setBounds(0, 135, 206, 23);
		panel.add(chckbx_TypeB);

		chckbx_TypeC = new JCheckBox(lang.getString("PHTYPEC"));
		chckbx_TypeC.setBounds(0, 162, 206, 23);
		panel.add(chckbx_TypeC);

		chckbx_Malignancies = new JCheckBox(lang.getString("PHMALIG"));
		chckbx_Malignancies.setBounds(210, 0, 204, 23);
		panel.add(chckbx_Malignancies);

		chckbx_HD = new JCheckBox(lang.getString("HD"));
		chckbx_HD.setBounds(210, 27, 204, 23);
		panel.add(chckbx_HD);

		chckbx_PHNS = new JCheckBox(lang.getString("PHNS"));
		chckbx_PHNS.setBounds(210, 54, 204, 23);
		panel.add(chckbx_PHNS);

		chckbx_Smoke = new JCheckBox(lang.getString("PHSMOKE"));
		chckbx_Smoke.setBounds(210, 81, 204, 23);
		panel.add(chckbx_Smoke);

		chckbx_Alcoholism = new JCheckBox(lang.getString("PHALCOHOLISM"));
		chckbx_Alcoholism.setBounds(210, 108, 204, 23);
		panel.add(chckbx_Alcoholism);

		chckbx_OW = new JCheckBox(lang.getString("PHOW"));
		chckbx_OW.setBounds(210, 135, 204, 23);
		panel.add(chckbx_OW);

		btn_Save = new JButton(lang.getString("SAVE"));
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

		RefreshData();
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

	private void RefreshData() {
		String sqlPateint = "SELECT case_manage.p_no FROM case_manage WHERE case_manage.guid='"
				+ CaseGuid + "'";

		String sql = "SELECT * FROM wound_accessment WHERE wound_accessment.case_guid = '"
				+ this.CaseGuid + "'";
		ResultSet rs = null;
		ResultSet rsPatient = null;
		try {
			rs = DBC.executeQuery(sql);
			if (!rs.next()) {
				rsPatient = DBC.executeQuery(sqlPateint);
				rsPatient.next();
				String p_no = rsPatient.getString("case_manage.p_no");
				String sqlInsert = "INSERT into wound_accessment SELECT "
						+ "uuid()," + " '" + this.CaseGuid + "', '" + p_no
						+ "'";
				int i = 0;
				for (i = 0; i < 16; i++) {
					sqlInsert = sqlInsert + ", NULL";
				}
				System.out.print(sqlInsert + "\n");
				DBC.executeUpdate(sqlInsert);
			} else {
				if (((String) rs.getString("wound_accessment.Hypertension"))
						.equals("Y")) {
					chckbx_PHHD.setSelected(true);
				} else {
					chckbx_PHHD.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.BrainVessel"))
						.equals("Y")) {
					chckbx_PHCD.setSelected(true);
				} else {
					chckbx_PHCD.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.Hyperlipidemia"))
						.equals("Y")) {
					chckbx_PHHC.setSelected(true);
				} else {
					chckbx_PHHC.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.Diabetes"))
						.equals("Y")) {
					chckbx_PHDM.setSelected(true);
				} else {
					chckbx_PHDM.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.HepatitisA"))
						.equals("Y")) {
					chckbx_TypeA.setSelected(true);
				} else {
					chckbx_TypeA.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.HepatitisB"))
						.equals("Y")) {
					chckbx_TypeB.setSelected(true);
				} else {
					chckbx_TypeB.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.HepatitisC"))
						.equals("Y")) {
					chckbx_TypeC.setSelected(true);
				} else {
					chckbx_TypeC.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.Cancer"))
						.equals("Y")) {
					chckbx_Malignancies.setSelected(true);
				} else {
					chckbx_Malignancies.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.HeartDisease"))
						.equals("Y")) {
					chckbx_HD.setSelected(true);
				} else {
					chckbx_HD.setSelected(false);
				}
				if (((String) rs
						.getString("wound_accessment.NephroticSyndrome"))
						.equals("Y")) {
					chckbx_PHNS.setSelected(true);
				} else {
					chckbx_PHNS.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.Smoking"))
						.equals("Y")) {
					chckbx_Smoke.setSelected(true);
				} else {
					chckbx_Smoke.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.Drinking"))
						.equals("Y")) {
					chckbx_Alcoholism.setSelected(true);
				} else {
					chckbx_Alcoholism.setSelected(false);
				}
				if (((String) rs.getString("wound_accessment.OverWeight"))
						.equals("Y")) {
					chckbx_OW.setSelected(true);
				} else {
					chckbx_OW.setSelected(false);
				}
				txt_CreateDate.setText((String) rs
						.getString("wound_accessment.createdatetime"));
				txt_Other.setText((String) rs
						.getString("wound_accessment.OtherDiseaseHistory"));
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
		if(!txt_CreateDate.getText().equals("")){
			sql = sql + " wound_accessment.createdatetime = '"
					+ txt_CreateDate.getText() + "', ";
		}
		sql = sql + " wound_accessment.OtherDiseaseHistory = '"
				+ txt_Other.getText() + "', ";
		
		sql = sql + " wound_accessment.s_no = '" + UserInfo.getUserNO() + "'";
		
		logger.debug("[{}][{}] {}", UserInfo.getUserID(),
				UserInfo.getUserName(), sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		try {
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}
}
