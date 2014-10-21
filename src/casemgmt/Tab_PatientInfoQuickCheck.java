package casemgmt;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.his.util.CustomLogger;

import common.Tools;
import casemgmt.Frm_Case.Pair;
import cc.johnwu.date.DateMethod;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

public class Tab_PatientInfoQuickCheck extends JPanel implements ISaveable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1527062084844346493L;

	private static Logger logger = LogManager.getLogger(Frm_Case.class
			.getName());

	private String m_Pno;

	private List<Pair<String, String>> presCodeMap;

	private String regGuid;

	private Frm_Case parent;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_PatientInfoQuickCheck(String pNo, String regGuid,
			String icdVersion) {
		m_Pno = pNo;
		this.regGuid = regGuid;
		presCodeMap = new ArrayList<Pair<String, String>>();

		if (icdVersion.equalsIgnoreCase("ICD-10")) {
			presCodeMap.add(new Pair<String, String>("155601", UUID
					.randomUUID().toString()));
			presCodeMap.add(new Pair<String, String>("1044971", UUID
					.randomUUID().toString()));
			presCodeMap.add(new Pair<String, String>("1507481", UUID
					.randomUUID().toString()));
		} else {
			presCodeMap.add(new Pair<String, String>("155602", UUID
					.randomUUID().toString()));
			presCodeMap.add(new Pair<String, String>("1044972", UUID
					.randomUUID().toString()));
			presCodeMap.add(new Pair<String, String>("1507482", UUID
					.randomUUID().toString()));
		}

		initComponents();
		init();
	}

	private void init() {
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM patients_info WHERE p_no = '"
					+ this.m_Pno + "'";
			// 取出病患基本資料
			rs = DBC.executeQuery(sql);
			rs.next();
			m_Pno = rs.getString("p_no");
			this.lab_Pno.setText(rs.getString("p_no"));
			this.lab_Name.setText(rs.getString("firstname") + " "
					+ rs.getString("lastname"));
			this.lab_Gender.setText(rs.getString("gender"));
			this.lab_Age.setText((rs.getDate("birth") == null) ? ""
					: DateMethod.getAgeWithMonth(rs.getDate("birth")));
			this.txt_Height.setText(rs.getString("height"));
			this.txt_Weight.setText(rs.getString("weight"));
			this.txt_AC.setText(Tools.getPrescriptionResult("BGAc", m_Pno));
			this.txt_PC.setText(Tools.getPrescriptionResult("BGPc", m_Pno));
			this.txt_ST.setText(Tools.getPrescriptionResult("St.", m_Pno));
			if (rs.getString("education") != null)
				this.com_edu.setSelectedIndex(rs.getInt("education"));
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					DBC.closeConnection(rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		// Save按鍵初始化
		btn_Ddate_Save.setEnabled(true);

	}

	public void revertPrescription(Connection conn) throws SQLException {
		String sql = "";
		for (Pair<String, String> pair : presCodeMap) {
			sql = String.format("delete from prescription where guid = '%s'",
					pair.getValue());
			PreparedStatement stmt4 = conn.prepareStatement(sql);
			stmt4.executeUpdate();
			stmt4.close();
			CustomLogger.debug(logger, sql);
		}
	}

	private void initComponents() {

		jLabel60 = new javax.swing.JLabel();
		lab_Pno = new javax.swing.JLabel();
		btn_Ddate_Save = new javax.swing.JButton();
		jLabel85 = new javax.swing.JLabel();
		lab_Age = new javax.swing.JLabel();
		jLabel88 = new javax.swing.JLabel();
		lab_Gender = new javax.swing.JLabel();
		jLabel90 = new javax.swing.JLabel();
		lab_Name = new javax.swing.JLabel();
		jPanel4 = new javax.swing.JPanel();
		jLabel23 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		txt_PC = new javax.swing.JTextField();
		txt_AC = new javax.swing.JTextField();
		jLabel24 = new javax.swing.JLabel();
		jLabel25 = new javax.swing.JLabel();
		jLabel26 = new javax.swing.JLabel();
		txt_ST = new javax.swing.JTextField();
		jLabel7 = new javax.swing.JLabel();
		jLabel62 = new javax.swing.JLabel();
		txt_Height = new javax.swing.JTextField();
		jLabel63 = new javax.swing.JLabel();
		txt_Weight = new javax.swing.JTextField();
		jLabel71 = new javax.swing.JLabel();
		com_edu = new javax.swing.JComboBox();
		jLabel18 = new javax.swing.JLabel();
		jLabel20 = new javax.swing.JLabel();

		jLabel60.setText("Patient No：");

		lab_Pno.setText("0");

		btn_Ddate_Save.setText("Save");
		btn_Ddate_Save.setEnabled(false);
		btn_Ddate_Save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_Ddate_SaveActionPerformed(evt);
			}
		});

		jLabel85.setText("Age：");

		lab_Age.setText("30");

		jLabel88.setText("Gender：");

		lab_Gender.setText("M");

		jLabel90.setText("Name：");

		lab_Name.setText("Steven Chung");

		jPanel4.setBorder(javax.swing.BorderFactory
				.createTitledBorder("One Touch"));

		jLabel23.setText("BGPc： ");

		jLabel15.setText("BGAc：");

		txt_PC.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		txt_AC.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel24.setText("mg/dl");

		jLabel25.setText("mg/dl");

		jLabel26.setText("BG(st)：");

		txt_ST.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel7.setText("mg/dl");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
				.setHorizontalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel4Layout
										.createSequentialGroup()
										.addGap(7, 7, 7)
										.addComponent(jLabel15)
										.addGap(18, 18, 18)
										.addComponent(
												txt_AC,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												74,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(6, 6, 6)
										.addComponent(jLabel24)
										.addGap(32, 32, 32)
										.addComponent(jLabel23)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												txt_PC,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel25)
										.addGap(42, 42, 42)
										.addComponent(jLabel26)
										.addGap(12, 12, 12)
										.addComponent(
												txt_ST,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												73,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jLabel7)
										.addContainerGap(121, Short.MAX_VALUE)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel4Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel15)
														.addComponent(jLabel24)
														.addComponent(jLabel23)
														.addComponent(
																txt_AC,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_PC,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel25)
														.addComponent(
																txt_ST,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel26)
														.addComponent(jLabel7))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jLabel62.setText("Height：");

		txt_Height.setText("165");
		txt_Height.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txt_HeightActionPerformed(evt);
			}
		});
		txt_Height.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				txt_HeightFocusGained(evt);
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				txt_HeightFocusLost(evt);
			}
		});
		txt_Height.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel63.setText("Weight：");

		txt_Weight.setText("60");
		txt_Weight.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txt_WeightActionPerformed(evt);
			}
		});
		txt_Weight.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusLost(java.awt.event.FocusEvent evt) {
				txt_WeightFocusLost(evt);
			}
		});
		txt_Weight.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_D(evt);
			}
		});

		jLabel71.setText("Education：");

		com_edu.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Illiterate", "Digital knowledge", "Literacy",
				"Elementary School", "Middle S. ", "High S. ",
				"College or above" }));
		com_edu.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_D(evt);
			}
		});

		jLabel18.setText("cm");

		jLabel20.setText("kg");

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(
				this);
		setLayout(jPanel11Layout);
		jPanel11Layout
				.setHorizontalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel11Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jPanel4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				25,
																				Short.MAX_VALUE)
																		.addComponent(
																				btn_Ddate_Save,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				80,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanel11Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGap(12,
																												12,
																												12)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.TRAILING)
																														.addComponent(
																																jLabel63)
																														.addGroup(
																																jPanel11Layout
																																		.createSequentialGroup()
																																		.addGroup(
																																				jPanel11Layout
																																						.createParallelGroup(
																																								javax.swing.GroupLayout.Alignment.LEADING)
																																						.addGroup(
																																								jPanel11Layout
																																										.createSequentialGroup()
																																										.addGap(87,
																																												87,
																																												87)
																																										.addComponent(
																																												lab_Pno,
																																												javax.swing.GroupLayout.PREFERRED_SIZE,
																																												105,
																																												javax.swing.GroupLayout.PREFERRED_SIZE))
																																						.addComponent(
																																								jLabel60))
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				jLabel90)))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addGroup(
																																jPanel11Layout
																																		.createSequentialGroup()
																																		.addComponent(
																																				txt_Weight,
																																				javax.swing.GroupLayout.PREFERRED_SIZE,
																																				73,
																																				javax.swing.GroupLayout.PREFERRED_SIZE)
																																		.addPreferredGap(
																																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																																		.addComponent(
																																				jLabel20))
																														.addComponent(
																																lab_Name,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																127,
																																javax.swing.GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addContainerGap()
																										.addComponent(
																												jLabel62)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												txt_Height,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												71,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(6,
																												6,
																												6)
																										.addComponent(
																												jLabel18)))
																		.addGap(18,
																				18,
																				18)
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel71)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGap(28,
																												28,
																												28)
																										.addComponent(
																												jLabel85)))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addComponent(
																												lab_Age)
																										.addGap(46,
																												46,
																												46)
																										.addComponent(
																												jLabel88)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												lab_Gender))
																						.addComponent(
																								com_edu,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));
		jPanel11Layout
				.setVerticalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																btn_Ddate_Save)
														.addGroup(
																jPanel11Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel11Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel60)
																														.addComponent(
																																lab_Pno)
																														.addComponent(
																																jLabel90)
																														.addComponent(
																																lab_Name))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel62)
																														.addComponent(
																																txt_Height,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																jLabel18)
																														.addComponent(
																																jLabel63)
																														.addComponent(
																																txt_Weight,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																jLabel20)))
																						.addGroup(
																								jPanel11Layout
																										.createSequentialGroup()
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel85)
																														.addComponent(
																																lab_Age)
																														.addComponent(
																																jLabel88)
																														.addComponent(
																																lab_Gender))
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addGroup(
																												jPanel11Layout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.BASELINE)
																														.addComponent(
																																jLabel71)
																														.addComponent(
																																com_edu,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																javax.swing.GroupLayout.DEFAULT_SIZE,
																																javax.swing.GroupLayout.PREFERRED_SIZE))))
																		.addGap(14,
																				14,
																				14)
																		.addComponent(
																				jPanel4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(14, Short.MAX_VALUE)));

	}

	protected void ItemStateChanged_D(ItemEvent evt) {
		btn_Ddate_Save.setEnabled(true);
	}

	protected void txt_WeightFocusLost(FocusEvent evt) {
		// TODO Auto-generated method stub

	}

	protected void txt_WeightActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	}

	protected void txt_HeightFocusLost(FocusEvent evt) {
		// TODO Auto-generated method stub

	}

	protected void txt_HeightFocusGained(FocusEvent evt) {
		// TODO Auto-generated method stub

	}

	protected void txt_HeightActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	}

	protected void KeyReleased_D(KeyEvent evt) {
		btn_Ddate_Save.setEnabled(true);

	}

	private void btn_Ddate_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			save();
			JOptionPane.showMessageDialog(null, "Save Complete");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Save Failed: " + e.getMessage());
			btn_Ddate_Save.setEnabled(true);
		}
	}

	@Override
	public boolean isSaveable() {
		return this.btn_Ddate_Save.isEnabled();
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
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	private javax.swing.JButton btn_Ddate_Save;
	private javax.swing.JComboBox com_edu;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel20;
	private javax.swing.JLabel jLabel23;
	private javax.swing.JLabel jLabel24;
	private javax.swing.JLabel jLabel25;
	private javax.swing.JLabel jLabel26;
	private javax.swing.JLabel jLabel60;
	private javax.swing.JLabel jLabel62;
	private javax.swing.JLabel jLabel63;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel71;
	private javax.swing.JLabel jLabel85;
	private javax.swing.JLabel jLabel88;
	private javax.swing.JLabel jLabel90;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JLabel lab_Age;
	public javax.swing.JLabel lab_Gender;
	private javax.swing.JLabel lab_Name;
	private javax.swing.JLabel lab_Pno;
	private javax.swing.JTextField txt_AC;
	private javax.swing.JTextField txt_Height;
	private javax.swing.JTextField txt_PC;
	private javax.swing.JTextField txt_ST;
	private javax.swing.JTextField txt_Weight;

	@Override
	public void save(Connection conn) throws Exception {
		PreparedStatement ps = null;
		PreparedStatement psBgac = null;
		PreparedStatement psBgpc = null;
		PreparedStatement psSt = null;
		try {
			String sql = "UPDATE patients_info  SET height = '"
					+ txt_Height.getText() + "',  weight = '"
					+ txt_Weight.getText() + "', education = '"
					+ com_edu.getSelectedIndex() + "' WHERE p_no = '" + m_Pno
					+ "'";
			logger.debug("[{}][{}] {}", UserInfo.getUserID(),
					UserInfo.getUserName(), sql);
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

			if (!txt_AC.getText().isEmpty()) {
				String sqlBgac = String
						.format("INSERT INTO prescription (guid, code, reg_guid, date_test, date_results, result, "
								+ "isnormal, cost, finish, state) "
								+ "values ('%s', '%s', '%s', NOW(), NOW(), '%s', 0, 0 , 'F', 1) ON DUPLICATE KEY UPDATE "
								+ "date_test = NOW(), date_results= NOW(), result = '%s', "
								+ "isnormal = 0, cost = 0, finish = 'F', state = 1 ",
								presCodeMap.get(0).getValue(),
								presCodeMap.get(0).getKey(), regGuid,
								txt_AC.getText(), txt_AC.getText());
				logger.debug("[{}][{}] {}", UserInfo.getUserID(),
						UserInfo.getUserName(), sqlBgac);
				psBgac = conn.prepareStatement(sqlBgac);
				psBgac.executeUpdate();
				psBgac.close();
			}

			if (!txt_PC.getText().isEmpty()) {
				String sqlBgpc = String
						.format("INSERT INTO prescription (guid, code, reg_guid, date_test, date_results, result, "
								+ "isnormal, cost, finish, state) "
								+ "values ('%s', '%s', '%s', NOW(), NOW(), '%s', 0, 0 , 'F', 1) ON DUPLICATE KEY UPDATE "
								+ "date_test = NOW(), date_results= NOW(), result = '%s', "
								+ "isnormal = 0, cost = 0, finish = 'F', state = 1 ",
								presCodeMap.get(1).getValue(),
								presCodeMap.get(1).getKey(), regGuid,
								txt_PC.getText(), txt_PC.getText());
				logger.debug("[{}][{}] {}", UserInfo.getUserID(),
						UserInfo.getUserName(), sqlBgpc);
				psBgpc = conn.prepareStatement(sqlBgpc);
				psBgpc.executeUpdate();
				psBgpc.close();
			}

			if (!txt_ST.getText().isEmpty()) {
				String sqlSt = String
						.format("INSERT INTO prescription (guid, code, reg_guid, date_test, date_results, result, "
								+ "isnormal, cost, finish, state) "
								+ "values ('%s', '%s', '%s', NOW(), NOW(), '%s', 0, 0 , 'F', 1) ON DUPLICATE KEY UPDATE "
								+ "date_test = NOW(), date_results= NOW(), result = '%s', "
								+ "isnormal = 0, cost = 0, finish = 'F', state = 1 ",
								presCodeMap.get(2).getValue(),
								presCodeMap.get(2).getKey(), regGuid,
								txt_ST.getText(), txt_ST.getText());
				logger.debug("[{}][{}] {}", UserInfo.getUserID(),
						UserInfo.getUserName(), sqlSt);
				psSt = conn.prepareStatement(sqlSt);
				psSt.executeUpdate();
				psSt.close();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (psBgac != null)
					psBgac.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (psBgpc != null)
					psBgpc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (psSt != null)
					psSt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		parent.pan_AssComp.txt_bmi.setText(Tools.getBmi(txt_Height.getText(),
				txt_Weight.getText()));
		btn_Ddate_Save.setEnabled(false);

	}

}
