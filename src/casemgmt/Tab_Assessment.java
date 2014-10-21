package casemgmt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cc.johnwu.date.DateComboBox;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

public class Tab_Assessment extends JPanel implements ISaveable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JScrollPane jScrollPane4;
	public JButton btn_AssSave;

	private JComboBox com_dm_type;
	private JTextField txt_dm_typeo;

	private JCheckBox che_smoke;
	private JCheckBox che_drink;

	private JCheckBox che_pre_proliferative_retinopathy_L;
	private JCheckBox che_cataract_R;
	private JCheckBox che_non_proliferative_retinopathy_R;
	private JCheckBox che_pre_proliferative_retinopathy_R;
	private JCheckBox che_non_proliferative_retinopathy_L;
	private JCheckBox che_retinal_check_R;
	private JCheckBox che_proliferative_retinopathy_L;
	private JCheckBox che_proliferative_retinopathy_R;
	private JCheckBox che_macular_degeneration_L;
	private JCheckBox che_advanced_dm_eyedisease_R;
	private JCheckBox che_advanced_dm_eyedisease_L;
	private JCheckBox che_retinal_check_L;
	private JCheckBox che_macular_degeneration_R;
	private JCheckBox che_cataract_L;
	private JCheckBox che_light_coagulation_L;
	private JCheckBox che_light_coagulation_R;
	private JCheckBox che_pulse_L;
	private JCheckBox che_pulse_R;
	private JCheckBox che_vibration_L;
	private JCheckBox che_vibration_R;
	private JCheckBox che_ulcer_L;
	private JCheckBox che_ulcer_R;
	private JCheckBox che_acupuncture_L;
	private JCheckBox che_acupuncture_R;
	private JCheckBox che_ulcer_cured_L;
	private JCheckBox che_ulcer_cured_R;
	private JCheckBox che_bypass_surgery_L;
	private JCheckBox che_bypass_surgery_R;
	private JComboBox cbox_oral_syear;
	private JComboBox cbox_insulin_syear;
	private DateComboBox dateComboBox;
	private JFormattedTextField ftf_eye_lvision;
	private JFormattedTextField ftf_eye_rvision;
	private JFormattedTextField ftf_drink_aweek;
	public JTextField txt_sbp;
	public JTextField txt_bmi;
	private JTextField txt_dbp;
	private JComboBox com_fundus_check;
	private JComboBox com_oral_hypoglycemic;
	private JComboBox com_insulin;
	private JComboBox com_sport;
	private JComboBox com_family_history;
	private JComboBox com_education;
	private JComboBox com_self_care;
	private JComboBox com_gestation;
	private JSpinner spi_gestation_count;
	private JSpinner spi_abortions_count;
	private JSpinner spi_smoke_aday;
	private JSpinner spi_bloodtest_aweek;
	private JSpinner spi_urine_aweek;

	private String guid;
	@SuppressWarnings("unused")
	private String regGuid;
	private String caseGuid;
	private String pNo;

	private static Logger logger = LogManager.getLogger(Tab_Assessment.class
			.getName());

	private Frm_Case parent;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_Assessment(String caseGuid, String pNo, String regGuid) {
		super();
		this.guid = "";
		this.caseGuid = caseGuid;
		this.regGuid = regGuid;
		this.pNo = pNo;
		initComponents();
		init();
	}

	private void init() {
		for (int i = 1901; i < 2060; i++) {
			cbox_oral_syear.addItem(i);
			cbox_insulin_syear.addItem(i);
		}
		// 新病患第一次進入case management 所有資料帶入空值
		String sqlas = "SELECT guid, family_history, self_care, dm_type, dm_typeo, dm_year, oral_hypoglycemic, oral_syear, insulin, insulin_syear, "
				+ " gestation, gestation_count, abortions_count, education, sport, fundus_check, gestation_count, "
				+ " abortions_count, smoke, drink, smoke_aday, drink_aweek, education, sport, bloodtest_aweek, urine_aweek, "
				+ " dbp, sbp, bmi, eye_lvision, eye_rvision, fundus_check, light_coagulation, cataract, retinal_check, non_proliferative_retinopathy, pre_proliferative_retinopathy ,proliferative_retinopathy, macular_degeneration, advanced_dm_eyedisease, vibration, pulse, ulcer, acupuncture, ulcer_cured, bypass_surgery, u_sid, udate"
				+ " FROM asscement"
				+ " WHERE asscement.case_guid = '"
				+ caseGuid + "' ORDER BY udate DESC LIMIT 0, 1";
		ResultSet as = null;
		try {
			System.out.println(sqlas);
			as = DBC.executeQuery(sqlas);

			if (as.next()) {
				if (as.getString("udate") != null) {
					// 讀取asscement
					this.guid = as.getString("guid");
					com_family_history.setSelectedIndex(Integer.parseInt(as
							.getString("family_history")));
					com_self_care.setSelectedIndex(Integer.parseInt(as
							.getString("self_care")));
					com_dm_type.setSelectedIndex(Integer.parseInt(as
							.getString("dm_type")));
					if (com_dm_type.getSelectedIndex() == 3)
						txt_dm_typeo.setEnabled(true);
					else {
						txt_dm_typeo.setEnabled(false);
						txt_dm_typeo.setText("");
					}
					txt_dm_typeo.setText(as.getString("dm_typeo"));
					if (as.getString("dm_year") != null)
						dateComboBox.setValue(as.getString("dm_year"));

					com_oral_hypoglycemic.setSelectedIndex(Integer.parseInt(as
							.getString("oral_hypoglycemic")));

					System.out.println(as.getString("oral_syear"));

					cbox_oral_syear.setSelectedItem(as.getObject("oral_syear"));

					com_insulin.setSelectedIndex(Integer.parseInt(as
							.getString("insulin")));
					cbox_insulin_syear.setSelectedItem(as
							.getObject("insulin_syear"));
					com_gestation.setSelectedIndex(Integer.parseInt(as
							.getString("gestation")));
					spi_gestation_count.setValue(Integer.parseInt(as
							.getString("gestation_count")));
					spi_abortions_count.setValue(Integer.parseInt(as
							.getString("abortions_count")));

					if (as.getString("smoke") != null
							&& as.getString("smoke").equals("0")) {
						che_smoke.setSelected(true);
					} else {
					}

					if (as.getString("drink") != null
							&& as.getString("drink").equals("0")) {
						che_drink.setSelected(true);
					} else {
					}

					spi_smoke_aday.setValue(Integer.parseInt(as
							.getString("smoke_aday")));
					ftf_drink_aweek.setText(as.getString("drink_aweek"));
					com_education.setSelectedIndex(Integer.parseInt(as
							.getString("education")));
					com_sport.setSelectedIndex(Integer.parseInt(as
							.getString("sport")));
					spi_bloodtest_aweek.setValue(Integer.parseInt(as
							.getString("bloodtest_aweek")));
					spi_urine_aweek.setValue(Integer.parseInt(as
							.getString("urine_aweek")));
					txt_dbp.setText(as.getString("dbp"));
					txt_sbp.setText(as.getString("sbp"));
					txt_bmi.setText(as.getString("bmi"));
					ftf_eye_lvision.setText(as.getString("eye_lvision"));
					ftf_eye_rvision.setText(as.getString("eye_rvision"));
					com_fundus_check.setSelectedIndex(Integer.parseInt(as
							.getString("fundus_check")));

					if (as.getString("light_coagulation").equals("3")) {
						che_light_coagulation_L.setSelected(true);
						che_light_coagulation_R.setSelected(true);
					} else if (as.getString("light_coagulation").equals("2")) {
						che_light_coagulation_R.setSelected(true);
					} else if (as.getString("light_coagulation").equals("1")) {
						che_light_coagulation_L.setSelected(true);
					} else {
					}

					if (as.getString("cataract").equals("3")) {
						che_cataract_L.setSelected(true);
						che_cataract_R.setSelected(true);
					} else if (as.getString("cataract").equals("2")) {
						che_cataract_R.setSelected(true);
					} else if (as.getString("cataract").equals("1")) {
						che_cataract_L.setSelected(true);
					} else {
					}

					if (as.getString("retinal_check").equals("3")) {
						che_retinal_check_L.setSelected(true);
						che_retinal_check_R.setSelected(true);
					} else if (as.getString("retinal_check").equals("2")) {
						che_retinal_check_R.setSelected(true);
					} else if (as.getString("retinal_check").equals("1")) {
						che_retinal_check_L.setSelected(true);
					} else {
					}

					if (as.getString("non_proliferative_retinopathy").equals(
							"3")) {
						che_non_proliferative_retinopathy_L.setSelected(true);
						che_non_proliferative_retinopathy_R.setSelected(true);
					} else if (as.getString("non_proliferative_retinopathy")
							.equals("2")) {
						che_non_proliferative_retinopathy_R.setSelected(true);
					} else if (as.getString("non_proliferative_retinopathy")
							.equals("1")) {
						che_non_proliferative_retinopathy_L.setSelected(true);
					} else {
					}

					if (as.getString("pre_proliferative_retinopathy").equals(
							"3")) {
						che_pre_proliferative_retinopathy_L.setSelected(true);
						che_pre_proliferative_retinopathy_R.setSelected(true);
					} else if (as.getString("pre_proliferative_retinopathy")
							.equals("2")) {
						che_pre_proliferative_retinopathy_R.setSelected(true);
					} else if (as.getString("pre_proliferative_retinopathy")
							.equals("1")) {
						che_pre_proliferative_retinopathy_L.setSelected(true);
					} else {
					}

					if (as.getString("proliferative_retinopathy").equals("3")) {
						che_proliferative_retinopathy_L.setSelected(true);
						che_proliferative_retinopathy_R.setSelected(true);
					} else if (as.getString("proliferative_retinopathy")
							.equals("2")) {
						che_proliferative_retinopathy_R.setSelected(true);
					} else if (as.getString("proliferative_retinopathy")
							.equals("1")) {
						che_proliferative_retinopathy_L.setSelected(true);
					} else {
					}

					if (as.getString("macular_degeneration").equals("3")) {
						che_macular_degeneration_L.setSelected(true);
						che_macular_degeneration_R.setSelected(true);
					} else if (as.getString("macular_degeneration").equals("2")) {
						che_macular_degeneration_R.setSelected(true);
					} else if (as.getString("macular_degeneration").equals("1")) {
						che_macular_degeneration_L.setSelected(true);
					} else {
					}

					if (as.getString("advanced_dm_eyedisease").equals("3")) {
						che_advanced_dm_eyedisease_L.setSelected(true);
						che_advanced_dm_eyedisease_R.setSelected(true);
					} else if (as.getString("advanced_dm_eyedisease").equals(
							"2")) {
						che_advanced_dm_eyedisease_R.setSelected(true);
					} else if (as.getString("advanced_dm_eyedisease").equals(
							"1")) {
						che_advanced_dm_eyedisease_L.setSelected(true);
					} else {
					}

					if (as.getString("vibration").equals("3")) {
						che_vibration_L.setSelected(true);
						che_vibration_R.setSelected(true);
					} else if (as.getString("vibration").equals("2")) {
						che_vibration_R.setSelected(true);
					} else if (as.getString("vibration").equals("1")) {
						che_vibration_L.setSelected(true);
					} else {
					}

					if (as.getString("pulse").equals("3")) {
						che_pulse_L.setSelected(true);
						che_pulse_R.setSelected(true);
					} else if (as.getString("pulse").equals("2")) {
						che_pulse_R.setSelected(true);
					} else if (as.getString("pulse").equals("1")) {
						che_pulse_L.setSelected(true);
					} else {
					}

					if (as.getString("ulcer").equals("3")) {
						che_ulcer_L.setSelected(true);
						che_ulcer_R.setSelected(true);
					} else if (as.getString("ulcer").equals("2")) {
						che_ulcer_R.setSelected(true);
					} else if (as.getString("ulcer").equals("1")) {
						che_ulcer_L.setSelected(true);
					} else {
					}

					if (as.getString("acupuncture").equals("3")) {
						che_acupuncture_L.setSelected(true);
						che_acupuncture_R.setSelected(true);
					} else if (as.getString("acupuncture").equals("2")) {
						che_acupuncture_R.setSelected(true);
					} else if (as.getString("acupuncture").equals("1")) {
						che_acupuncture_L.setSelected(true);
					} else {
					}

					if (as.getString("ulcer_cured").equals("3")) {
						che_ulcer_cured_L.setSelected(true);
						che_ulcer_cured_R.setSelected(true);
					} else if (as.getString("ulcer_cured").equals("2")) {
						che_ulcer_cured_R.setSelected(true);
					} else if (as.getString("ulcer_cured").equals("1")) {
						che_ulcer_cured_L.setSelected(true);
					} else {
					}

					if (as.getString("bypass_surgery").equals("3")) {
						che_bypass_surgery_L.setSelected(true);
						che_bypass_surgery_R.setSelected(true);
					} else if (as.getString("bypass_surgery").equals("2")) {
						che_bypass_surgery_R.setSelected(true);
					} else if (as.getString("bypass_surgery").equals("1")) {
						che_bypass_surgery_L.setSelected(true);
					} else {
					}
				}
			} else {
				// 所有資料帶入空值
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (as != null) {
				try {
					DBC.closeConnection(as);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			btn_AssSave.setEnabled(false);
		}
	}

	private void initComponents() {

		JLabel jLabel159 = new JLabel();
		jLabel159.setText("mmHg");
		JLabel jLabel161 = new JLabel();
		jLabel161.setText("SBP：");
		JLabel jLabel162 = new JLabel();
		jLabel162.setText("mmHg");
		JLabel jLabel191 = new JLabel();
		jLabel191.setText("BMI：");
		JLabel jLabel158 = new JLabel();
		jLabel158.setText("DBP：");
		JLabel jLabel196 = new JLabel();
		jLabel196.setText("Ophthalmoscopy：");
		JLabel jLabel192 = new JLabel();
		jLabel192.setText("kg/m^2");
		txt_dbp = new javax.swing.JTextField();
		txt_dbp.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				onKeyReleased(evt);
			}
		});
		txt_sbp = new javax.swing.JTextField();
		txt_sbp.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				onKeyReleased(evt);
			}
		});
		txt_bmi = new javax.swing.JTextField();
		txt_bmi.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				onKeyReleased(evt);
			}
		});
		com_fundus_check = new javax.swing.JComboBox();
		com_fundus_check.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { " ", "Yes", "No" }));
		com_fundus_check.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		JPanel jPanel7 = new javax.swing.JPanel();
		jPanel7.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Vision"));

		com_fundus_check.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { " ", "Yes", "No" }));
		com_fundus_check.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		jPanel7.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Vision"));
		JLabel jLabel194 = new JLabel();
		jLabel194.setText("Left eye：");
		JLabel jLabel195 = new JLabel();
		jLabel195.setText("Right eye：");
		ftf_eye_lvision = new JFormattedTextField();
		try {
			ftf_eye_lvision
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("#.#")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		ftf_eye_lvision
				.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
		ftf_eye_lvision.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				onKeyReleased(evt);
			}
		});
		ftf_eye_rvision = new JFormattedTextField();
		try {
			ftf_eye_rvision
					.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.MaskFormatter("#.#")));
		} catch (java.text.ParseException ex) {
			ex.printStackTrace();
		}
		ftf_eye_rvision
				.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
		ftf_eye_rvision.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				onKeyReleased(evt);
			}
		});

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
				jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout
				.setHorizontalGroup(jPanel7Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel7Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel195)
														.addComponent(jLabel194))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																ftf_eye_lvision,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																44,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																ftf_eye_rvision,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																44,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(253, Short.MAX_VALUE)));
		jPanel7Layout
				.setVerticalGroup(jPanel7Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel7Layout
										.createSequentialGroup()
										.addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel194)
														.addComponent(
																ftf_eye_lvision,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel7Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel195)
														.addComponent(
																ftf_eye_rvision,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		JLabel jLabel197 = new javax.swing.JLabel();
		JLabel jLabel198 = new javax.swing.JLabel();
		che_cataract_R = new javax.swing.JCheckBox();
		che_non_proliferative_retinopathy_R = new javax.swing.JCheckBox();
		che_pre_proliferative_retinopathy_R = new javax.swing.JCheckBox();
		che_non_proliferative_retinopathy_L = new javax.swing.JCheckBox();
		JLabel jLabel199 = new javax.swing.JLabel();
		JLabel jLabel200 = new javax.swing.JLabel();
		JLabel jLabel201 = new javax.swing.JLabel();
		che_retinal_check_R = new javax.swing.JCheckBox();
		che_proliferative_retinopathy_L = new javax.swing.JCheckBox();
		che_proliferative_retinopathy_R = new javax.swing.JCheckBox();
		che_macular_degeneration_L = new javax.swing.JCheckBox();
		JLabel jLabel202 = new javax.swing.JLabel();
		che_advanced_dm_eyedisease_R = new javax.swing.JCheckBox();
		che_advanced_dm_eyedisease_L = new javax.swing.JCheckBox();
		JLabel jLabel203 = new javax.swing.JLabel();
		che_retinal_check_L = new javax.swing.JCheckBox();
		che_pre_proliferative_retinopathy_L = new javax.swing.JCheckBox();
		JLabel jLabel204 = new javax.swing.JLabel();
		che_macular_degeneration_R = new javax.swing.JCheckBox();
		che_cataract_L = new javax.swing.JCheckBox();
		che_light_coagulation_L = new javax.swing.JCheckBox();
		che_light_coagulation_R = new javax.swing.JCheckBox();

		che_cataract_L.setText("Left eye");
		che_cataract_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		che_light_coagulation_L.setText("Left eye");
		che_light_coagulation_L
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		che_light_coagulation_R.setText("Right eye");
		che_light_coagulation_R
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		jLabel197.setText("Advanced Diabetic Eye Disease:");

		jLabel198.setText("Cataract:");

		che_cataract_R.setText("Right eye");
		che_cataract_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		che_non_proliferative_retinopathy_R.setText("Right eye");
		che_non_proliferative_retinopathy_R
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		che_pre_proliferative_retinopathy_R.setText("Right eye");
		che_pre_proliferative_retinopathy_R
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		che_non_proliferative_retinopathy_L.setText("Left eye");
		che_non_proliferative_retinopathy_L
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		jLabel199.setText("Nonproliferative diabetic retinopathy:");

		jLabel200.setText("Retinal examination:");

		jLabel201.setText("Preproliferative diabetic retinopathy:");

		che_retinal_check_R.setText("Right eye");
		che_retinal_check_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		che_proliferative_retinopathy_L.setText("Left eye");
		che_proliferative_retinopathy_L
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		che_proliferative_retinopathy_R.setText("Right eye");
		che_proliferative_retinopathy_R
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});
		che_proliferative_retinopathy_R
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
					}
				});

		che_macular_degeneration_L.setText("Left eye");
		che_macular_degeneration_L
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		jLabel202.setText("Proliferative diabetic retinopathy:");

		che_advanced_dm_eyedisease_R.setText("Right eye");
		che_advanced_dm_eyedisease_R
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		che_advanced_dm_eyedisease_L.setText("Left eye");
		che_advanced_dm_eyedisease_L
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});
		che_advanced_dm_eyedisease_L
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
					}
				});

		jLabel203.setText("Macular degeneration:");

		che_retinal_check_L.setText("Left eye");
		che_retinal_check_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		che_pre_proliferative_retinopathy_L.setText("Left eye");
		che_pre_proliferative_retinopathy_L
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		jLabel204.setText("Photocoagulation therapy:");

		che_macular_degeneration_R.setText("Right eye");
		che_macular_degeneration_R
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		JPanel jPanel25 = new javax.swing.JPanel();
		jPanel25.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Eye examination and lesions"));

		javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(
				jPanel25);
		jPanel25.setLayout(jPanel25Layout);
		jPanel25Layout
				.setHorizontalGroup(jPanel25Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel25Layout
										.createSequentialGroup()
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																jPanel25Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				jPanel25Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel202,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jLabel198,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jLabel200,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jLabel201,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jLabel204,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jLabel199,
																								javax.swing.GroupLayout.Alignment.TRAILING))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				13,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel25Layout
																		.createSequentialGroup()
																		.addGap(44,
																				44,
																				44)
																		.addGroup(
																				jPanel25Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jLabel197)
																						.addComponent(
																								jLabel203))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																che_macular_degeneration_L)
														.addComponent(
																che_advanced_dm_eyedisease_L)
														.addComponent(
																che_proliferative_retinopathy_L)
														.addComponent(
																che_cataract_L)
														.addComponent(
																che_light_coagulation_L)
														.addComponent(
																che_retinal_check_L)
														.addComponent(
																che_pre_proliferative_retinopathy_L)
														.addComponent(
																che_non_proliferative_retinopathy_L))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																che_proliferative_retinopathy_R)
														.addComponent(
																che_advanced_dm_eyedisease_R)
														.addComponent(
																che_pre_proliferative_retinopathy_R)
														.addComponent(
																che_non_proliferative_retinopathy_R)
														.addComponent(
																che_cataract_R)
														.addComponent(
																che_retinal_check_R)
														.addComponent(
																che_light_coagulation_R)
														.addComponent(
																che_macular_degeneration_R))
										.addContainerGap()));
		jPanel25Layout
				.setVerticalGroup(jPanel25Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel25Layout
										.createSequentialGroup()
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																che_light_coagulation_R)
														.addComponent(
																che_light_coagulation_L)
														.addComponent(jLabel204))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel25Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel25Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								che_cataract_L)
																						.addComponent(
																								jLabel198))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel25Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								che_retinal_check_L)
																						.addComponent(
																								jLabel200)
																						.addComponent(
																								che_retinal_check_R))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel25Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								che_non_proliferative_retinopathy_L)
																						.addComponent(
																								jLabel199)
																						.addComponent(
																								che_non_proliferative_retinopathy_R))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel25Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								che_pre_proliferative_retinopathy_L)
																						.addComponent(
																								jLabel201)
																						.addComponent(
																								che_pre_proliferative_retinopathy_R)))
														.addComponent(
																che_cataract_R,
																javax.swing.GroupLayout.Alignment.LEADING))
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel25Layout
																		.createSequentialGroup()
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel25Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								che_proliferative_retinopathy_L)
																						.addComponent(
																								jLabel202)))
														.addComponent(
																che_proliferative_retinopathy_R,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel25Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				che_macular_degeneration_L)
																		.addComponent(
																				che_macular_degeneration_R))
														.addComponent(jLabel203))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel25Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																che_advanced_dm_eyedisease_L)
														.addComponent(
																che_advanced_dm_eyedisease_R)
														.addComponent(jLabel197))
										.addContainerGap(22, Short.MAX_VALUE)));

		JPanel jPanel26 = new javax.swing.JPanel();
		jPanel26.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Foot inspection and disease"));

		JLabel jLabel64 = new JLabel();
		jLabel64.setText("Vibration Sensation:");
		JLabel jLabel65 = new JLabel();
		jLabel65.setText("Dorsalis pedis pulse:");
		JLabel jLabel66 = new JLabel();
		jLabel66.setText("Acute foot ulcers:");
		JLabel jLabel72 = new JLabel();
		jLabel72.setText("Pinprick sensation:");
		JLabel jLabel73 = new JLabel();
		jLabel73.setText("Healed ulcers:");
		JLabel jLabel74 = new JLabel();
		jLabel74.setText("Coronary bypass/ Coronary angioplasty:");
		che_vibration_R = new JCheckBox();
		che_vibration_R.setText("Right");
		che_vibration_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_vibration_R.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			}
		});

		che_vibration_L = new JCheckBox();
		che_vibration_L.setText("Left");
		che_vibration_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_pulse_R = new JCheckBox();
		che_pulse_R.setText("Right");
		che_pulse_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_pulse_R.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			}
		});
		che_pulse_L = new JCheckBox();
		che_pulse_L.setText("Left");
		che_pulse_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_pulse_L.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			}
		});
		che_ulcer_R = new JCheckBox();
		che_ulcer_R.setText("Right");
		che_ulcer_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_ulcer_R.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			}
		});
		che_ulcer_L = new JCheckBox();
		che_ulcer_L.setText("Left");
		che_ulcer_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_acupuncture_R = new JCheckBox();
		che_acupuncture_R.setText("Right");
		che_acupuncture_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_acupuncture_R
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
					}
				});
		che_acupuncture_L = new JCheckBox();
		che_acupuncture_L.setText("Left");
		che_acupuncture_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_ulcer_cured_R = new JCheckBox();
		che_ulcer_cured_R.setText("Right");
		che_ulcer_cured_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_ulcer_cured_R
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
					}
				});
		che_ulcer_cured_L = new JCheckBox();
		che_ulcer_cured_L.setText("Left");
		che_ulcer_cured_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_bypass_surgery_R = new JCheckBox();
		che_bypass_surgery_R.setText("Right");
		che_bypass_surgery_R.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		che_bypass_surgery_R
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
					}
				});
		che_bypass_surgery_L = new JCheckBox();
		che_bypass_surgery_L.setText("Left");
		che_bypass_surgery_L.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(
				jPanel26);
		jPanel26.setLayout(jPanel26Layout);
		jPanel26Layout
				.setHorizontalGroup(jPanel26Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel26Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel66)
														.addComponent(jLabel65)
														.addComponent(jLabel64)
														.addComponent(jLabel72)
														.addComponent(jLabel73)
														.addComponent(jLabel74))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																jPanel26Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(
																				che_ulcer_cured_L)
																		.addComponent(
																				che_acupuncture_L)
																		.addComponent(
																				che_pulse_L)
																		.addComponent(
																				che_ulcer_L)
																		.addComponent(
																				che_vibration_L))
														.addComponent(
																che_bypass_surgery_L))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																che_bypass_surgery_R)
														.addComponent(
																che_ulcer_cured_R)
														.addComponent(
																che_vibration_R)
														.addComponent(
																che_ulcer_R)
														.addComponent(
																che_acupuncture_R)
														.addComponent(
																che_pulse_R))
										.addContainerGap()));
		jPanel26Layout
				.setVerticalGroup(jPanel26Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel26Layout
										.createSequentialGroup()
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																che_vibration_L)
														.addComponent(
																che_vibration_R)
														.addComponent(jLabel64))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																che_pulse_L)
														.addComponent(
																che_pulse_R)
														.addComponent(jLabel65))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																che_ulcer_L)
														.addComponent(
																che_ulcer_R)
														.addComponent(jLabel66))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel26Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel26Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								che_acupuncture_L)
																						.addComponent(
																								che_acupuncture_R))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jPanel26Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								che_ulcer_cured_L)
																						.addComponent(
																								che_ulcer_cured_R)
																						.addComponent(
																								jLabel73)))
														.addComponent(jLabel72))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel26Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																che_bypass_surgery_L)
														.addComponent(jLabel74)
														.addComponent(
																che_bypass_surgery_R))
										.addContainerGap(78, Short.MAX_VALUE)));

		JPanel jPanel24 = new javax.swing.JPanel();
		jPanel24.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Physical examination"));
		javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(
				jPanel24);
		jPanel24.setLayout(jPanel24Layout);
		jPanel24Layout
				.setHorizontalGroup(jPanel24Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel24Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel24Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel24Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel24Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLabel161)
																						.addComponent(
																								jLabel158)
																						.addComponent(
																								jLabel191))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel24Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								txt_dbp,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								100,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txt_sbp,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								100,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								txt_bmi,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								100,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel24Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jPanel24Layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel159)
																										.addGap(41,
																												41,
																												41)
																										.addComponent(
																												jLabel196)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												com_fundus_check,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												100,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								jLabel162)
																						.addComponent(
																								jLabel192))
																		.addGap(54,
																				54,
																				54)
																		.addComponent(
																				jPanel7,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																jPanel24Layout
																		.createSequentialGroup()
																		.addComponent(
																				jPanel25,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jPanel26,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(633, Short.MAX_VALUE)));
		jPanel24Layout
				.setVerticalGroup(jPanel24Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel24Layout
										.createSequentialGroup()
										.addGap(19, 19, 19)
										.addGroup(
												jPanel24Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel24Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel24Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jLabel158)
																						.addComponent(
																								txt_dbp,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel159)
																						.addComponent(
																								jLabel196)
																						.addComponent(
																								com_fundus_check,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel24Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jLabel161)
																						.addComponent(
																								txt_sbp,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel162))
																		.addGap(7,
																				7,
																				7)
																		.addGroup(
																				jPanel24Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								txt_bmi,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel191)
																						.addComponent(
																								jLabel192)))
														.addComponent(
																jPanel7,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel24Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jPanel25,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jPanel26,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));

		JPanel jPanel8 = new javax.swing.JPanel();
		jPanel8.setBackground(new java.awt.Color(204, 204, 204));
		jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		JLabel jLabel1 = new JLabel();
		jLabel1.setText("Oral hypoglycemic：");

		com_oral_hypoglycemic = new JComboBox();
		com_oral_hypoglycemic.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "　", "Dietitian", "Nurse", "Other" }));
		com_oral_hypoglycemic
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged(evt);
					}
				});

		JLabel jLabel5 = new JLabel();
		jLabel5.setText("Insulin：");

		com_insulin = new JComboBox();
		com_insulin.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				" ", "Yes", "No" }));
		com_insulin.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		com_insulin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			}
		});

		JLabel jLabel11 = new JLabel();
		jLabel11.setText("Use starting year：");

		JLabel jLabel3 = new JLabel();
		jLabel3.setText("Use starting year：");

		cbox_oral_syear = new JComboBox();
		cbox_oral_syear.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbox_oral_syearItemStateChanged(evt);
			}
		});

		cbox_insulin_syear = new JComboBox();
		cbox_insulin_syear.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				cbox_insulin_syearItemStateChanged(evt);
			}
		});

		JPanel jPanel5 = new javax.swing.JPanel();
		jPanel5.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Treatment"));
		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
				jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout
				.setHorizontalGroup(jPanel5Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel1)
														.addComponent(jLabel5))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																com_oral_hypoglycemic,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																100,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																com_insulin,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																100,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jLabel3,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jLabel11,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																cbox_insulin_syear,
																0,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																cbox_oral_syear,
																0, 103,
																Short.MAX_VALUE))
										.addGap(1058, 1058, 1058)));
		jPanel5Layout
				.setVerticalGroup(jPanel5Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLabel1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																15,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																com_oral_hypoglycemic,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel11)
														.addComponent(
																cbox_oral_syear,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel3)
														.addComponent(jLabel5)
														.addComponent(
																com_insulin,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cbox_insulin_syear,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		JLabel jLabel8 = new JLabel();
		jLabel8.setText("Termination of the last 12 months：");
		com_gestation = new javax.swing.JComboBox();
		com_gestation.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { " ", "Yes", "No" }));
		com_gestation.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		JLabel jLabel9 = new JLabel();
		jLabel9.setText("Number of normal pregnancy：");
		spi_gestation_count = new JSpinner();
		spi_gestation_count.setCursor(new java.awt.Cursor(
				java.awt.Cursor.DEFAULT_CURSOR));
		spi_gestation_count
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						onStateChanged(evt);
					}
				});

		JLabel jLabel10 = new JLabel();
		jLabel10.setText("Number of abortions：");
		spi_abortions_count = new JSpinner();
		spi_abortions_count
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						onStateChanged(evt);
					}
				});

		JPanel jPanel3 = new javax.swing.JPanel();
		jPanel3.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Pregnant pregnant"));
		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(jLabel8)
														.addComponent(jLabel10)
														.addComponent(jLabel9))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																com_gestation,
																0, 100,
																Short.MAX_VALUE)
														.addComponent(
																spi_gestation_count)
														.addComponent(
																spi_abortions_count))
										.addContainerGap(1204, Short.MAX_VALUE)));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel8)
														.addComponent(
																com_gestation,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addGap(33,
																				33,
																				33)
																		.addComponent(
																				jLabel10))
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel3Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								spi_gestation_count,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel9))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				spi_abortions_count,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		JLabel jLabel16 = new javax.swing.JLabel();
		jLabel16.setText("Family history：");

		com_family_history = new javax.swing.JComboBox();
		com_family_history.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "　", "Yes", "No", "I don’t know", "Patrilineal",
						"Maternal", "Siblings", "Children" }));
		com_family_history.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		com_self_care = new javax.swing.JComboBox();
		com_self_care.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "　", "Completely independent ",
						"Needs some assistance", "Completely dependent" }));
		com_self_care.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		JLabel JLable17 = new javax.swing.JLabel();
		JLable17.setText("Self-Care Ability：");

		JLabel jLabel30 = new javax.swing.JLabel();
		jLabel30.setText("Have you had a health education：");

		com_education = new javax.swing.JComboBox();
		com_education.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { " ", "Yes", "No" }));
		com_education.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		com_sport = new javax.swing.JComboBox();
		com_sport.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				" ", "Yes", "No" }));
		com_sport.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		JLabel jLabel51 = new javax.swing.JLabel();
		jLabel51.setText("Sports：");

		JPanel jPanel1 = new javax.swing.JPanel();
		jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Status of risk factors"));

		com_sport.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				" ", "Yes", "No" }));
		com_sport.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		com_education.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { " ", "Yes", "No" }));
		com_education.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Status of risk factors"));

		che_smoke = new JCheckBox();
		che_smoke.setText("Smoking");
		che_smoke.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		che_drink = new JCheckBox();
		che_drink.setText("Drinking");
		che_drink.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});

		spi_smoke_aday = new JSpinner();
		spi_smoke_aday.setCursor(new java.awt.Cursor(
				java.awt.Cursor.DEFAULT_CURSOR));
		spi_smoke_aday
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						onStateChanged(evt);
					}
				});

		ftf_drink_aweek = new JFormattedTextField();
		ftf_drink_aweek
				.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
		ftf_drink_aweek.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				onKeyReleased(evt);
			}
		});

		JLabel jLabel21 = new JLabel();
		jLabel21.setText("A / Daily");

		JLabel jLabel22 = new JLabel();
		jLabel22.setText("CC / Weekly");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																che_drink,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																85,
																Short.MAX_VALUE)
														.addComponent(
																che_smoke,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																85,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				spi_smoke_aday,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				100,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jLabel21))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				ftf_drink_aweek,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				100,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jLabel22,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				107,
																				Short.MAX_VALUE)))
										.addContainerGap(
												1169,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(che_smoke)
														.addComponent(
																spi_smoke_aday,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel21))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(che_drink)
														.addComponent(
																ftf_drink_aweek,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel22))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jPanel1.setLayout(jPanel1Layout);

		JPanel jPanel10 = new javax.swing.JPanel();
		jPanel10.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Self-monitoring"));

		JLabel jLabel4 = new JLabel();
		jLabel4.setText("The number of weekly blood tests:");

		JLabel jLabel6 = new JLabel();
		jLabel6.setText("The number of weekly urine:");

		spi_bloodtest_aweek = new JSpinner();
		spi_bloodtest_aweek
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						onStateChanged(evt);
					}
				});

		spi_urine_aweek = new JSpinner();
		spi_urine_aweek
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(javax.swing.event.ChangeEvent evt) {
						onStateChanged(evt);
					}
				});

		javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(
				jPanel10);
		jPanel10Layout
				.setHorizontalGroup(jPanel10Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel10Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel10Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel10Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel4)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				spi_bloodtest_aweek,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel10Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel6)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				spi_urine_aweek,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(1283, Short.MAX_VALUE)));
		jPanel10Layout
				.setVerticalGroup(jPanel10Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel10Layout
										.createSequentialGroup()
										.addGroup(
												jPanel10Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																spi_bloodtest_aweek,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel4))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel10Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																spi_urine_aweek,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel6))));
		jPanel10.setLayout(jPanel10Layout);

		JLabel jLabel2 = new javax.swing.JLabel();
		jLabel2.setText("Other：");
		JLabel jLabel17 = new javax.swing.JLabel();
		jLabel17.setText("Diabetes Type：");

		com_dm_type = new javax.swing.JComboBox();
		com_dm_type.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Type I ", "Type II ", "Others" }));
		com_dm_type.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged(evt);
			}
		});
		txt_dm_typeo = new javax.swing.JTextField();
		txt_dm_typeo.setEnabled(false);
		txt_dm_typeo.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				onKeyReleased(evt);
			}
		});
		JLabel jLabel19 = new javax.swing.JLabel();
		jLabel19.setText("Date of Discovery：");
		dateComboBox = new cc.johnwu.date.DateComboBox();

		JPanel jPanel6 = new javax.swing.JPanel();
		jPanel6.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Diabetes"));
		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(
				jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout
				.setHorizontalGroup(jPanel6Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel6Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel6Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jLabel2,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jLabel17,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel6Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																jPanel6Layout
																		.createSequentialGroup()
																		.addComponent(
																				com_dm_type,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				100,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jLabel19))
														.addComponent(
																txt_dm_typeo))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												dateComboBox,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(1037, Short.MAX_VALUE)));
		jPanel6Layout
				.setVerticalGroup(jPanel6Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel6Layout
										.createSequentialGroup()
										.addGroup(
												jPanel6Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel6Layout
																		.createSequentialGroup()
																		.addGroup(
																				jPanel6Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jLabel17)
																						.addComponent(
																								com_dm_type,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jLabel19))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel6Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jLabel2)
																						.addComponent(
																								txt_dm_typeo,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addComponent(
																dateComboBox,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(
				jPanel8);
		jPanel8.setLayout(jPanel8Layout);
		jPanel8Layout
				.setHorizontalGroup(jPanel8Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel8Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel8Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jPanel24,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanel5,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanel3,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel8Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel16)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				com_family_history,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				200,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				JLable17)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				com_self_care,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				200,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																jPanel8Layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel30)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				com_education,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				100,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(63,
																				63,
																				63)
																		.addComponent(
																				jLabel51)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				com_sport,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				100,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jPanel1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanel10,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanel6,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel8Layout
				.setVerticalGroup(jPanel8Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel8Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel8Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel16)
														.addComponent(
																com_family_history,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(JLable17)
														.addComponent(
																com_self_care,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jPanel6,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel5,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel8Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel30)
														.addComponent(
																com_education,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																com_sport,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jLabel51))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel10,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel24,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jScrollPane4 = new JScrollPane();
		jScrollPane4.setViewportView(jPanel8);
		jScrollPane4.setHorizontalScrollBar(null);

		btn_AssSave = new javax.swing.JButton();
		btn_AssSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_AssSaveActionPerformed(e);
			}
		});
		btn_AssSave.setText("Save");

		GroupLayout pan_AssLayout = new GroupLayout(this);
		setLayout(pan_AssLayout);
		pan_AssLayout
				.setHorizontalGroup(pan_AssLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_AssLayout
										.createSequentialGroup()
										.addGroup(
												pan_AssLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																pan_AssLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				jScrollPane4,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				791,
																				Short.MAX_VALUE))
														.addComponent(
																btn_AssSave,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																84,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		pan_AssLayout
				.setVerticalGroup(pan_AssLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_AssLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jScrollPane4,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												279, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btn_AssSave)
										.addContainerGap()));

	}

	private void ItemStateChanged(java.awt.event.ItemEvent evt) {
		if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
			if (com_dm_type.getSelectedIndex() == 3)
				txt_dm_typeo.setEnabled(true);
			else {
				txt_dm_typeo.setEnabled(false);
				txt_dm_typeo.setText("");
			}
		}

		btn_AssSave.setEnabled(true);
	}

	private void onKeyReleased(java.awt.event.KeyEvent evt) {
		btn_AssSave.setEnabled(true);
	}

	private void onStateChanged(javax.swing.event.ChangeEvent evt) {
		btn_AssSave.setEnabled(true);
	}

	private void cbox_oral_syearItemStateChanged(java.awt.event.ItemEvent evt) {
		btn_AssSave.setEnabled(true);
	}

	private void cbox_insulin_syearItemStateChanged(java.awt.event.ItemEvent evt) {
		btn_AssSave.setEnabled(true);
	}

	private void btn_AssSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_AssSaveActionPerformed
		try {
			save();
			JOptionPane.showMessageDialog(null, "Save Complete");

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Save Failed: " + ex.getMessage());
			btn_AssSave.setEnabled(true);
		}
	}

	@Override
	public boolean isSaveable() {
		return btn_AssSave.isEnabled();
	}

	@Override
	public void save() throws Exception {
		Connection conn = DBC.getConnectionExternel();
		try {
			save(conn);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	@Override
	public void save(Connection conn) throws Exception {
		String smo, dri;

		if (che_smoke.isSelected() == true) {
			smo = "0";
		} else {
			smo = "1";
		}

		if (che_drink.isSelected() == true) {
			dri = "0";
		} else {
			dri = "1";
		}

		String lig;
		if (che_light_coagulation_L.isSelected() == true
				&& che_light_coagulation_R.isSelected() == true) {
			lig = "3";
		} else if (che_light_coagulation_L.isSelected() == false
				&& che_light_coagulation_R.isSelected() == false) {
			lig = "0";
		} else if (che_light_coagulation_L.isSelected() == true) {
			lig = "1";
		} else {
			lig = "2";
		}

		String cat;
		if (che_cataract_L.isSelected() == true
				&& che_cataract_R.isSelected() == true) {
			cat = "3";
		} else if (che_cataract_L.isSelected() == false
				&& che_cataract_R.isSelected() == false) {
			cat = "0";
		} else if (che_cataract_L.isSelected() == true) {
			cat = "1";
		} else {
			cat = "2";
		}

		String ret;
		if (che_retinal_check_L.isSelected() == true
				&& che_retinal_check_R.isSelected() == true) {
			ret = "3";
		} else if (che_retinal_check_L.isSelected() == false
				&& che_retinal_check_R.isSelected() == false) {
			ret = "0";
		} else if (che_retinal_check_L.isSelected() == true) {
			ret = "1";
		} else {
			ret = "2";
		}

		String non;
		if (che_non_proliferative_retinopathy_L.isSelected() == true
				&& che_non_proliferative_retinopathy_R.isSelected() == true) {
			non = "3";
		} else if (che_non_proliferative_retinopathy_L.isSelected() == false
				&& che_non_proliferative_retinopathy_R.isSelected() == false) {
			non = "0";
		} else if (che_non_proliferative_retinopathy_L.isSelected() == true) {
			non = "1";
		} else {
			non = "2";
		}

		String pre;
		if (che_pre_proliferative_retinopathy_L.isSelected() == true
				&& che_pre_proliferative_retinopathy_R.isSelected() == true) {
			pre = "3";
		} else if (che_pre_proliferative_retinopathy_L.isSelected() == false
				&& che_pre_proliferative_retinopathy_R.isSelected() == false) {
			pre = "0";
		} else if (che_pre_proliferative_retinopathy_L.isSelected() == true) {
			pre = "1";
		} else {
			pre = "2";
		}

		String pro;
		if (che_proliferative_retinopathy_L.isSelected() == true
				&& che_proliferative_retinopathy_R.isSelected() == true) {
			pro = "3";
		} else if (che_proliferative_retinopathy_L.isSelected() == false
				&& che_proliferative_retinopathy_R.isSelected() == false) {
			pro = "0";
		} else if (che_proliferative_retinopathy_L.isSelected() == true) {
			pro = "1";
		} else {
			pro = "2";
		}

		String mac;
		if (che_macular_degeneration_L.isSelected() == true
				&& che_macular_degeneration_R.isSelected() == true) {
			mac = "3";
		} else if (che_macular_degeneration_L.isSelected() == false
				&& che_macular_degeneration_R.isSelected() == false) {
			mac = "0";
		} else if (che_macular_degeneration_L.isSelected() == true) {
			mac = "1";
		} else {
			mac = "2";
		}

		String adv;
		if (che_advanced_dm_eyedisease_L.isSelected() == true
				&& che_advanced_dm_eyedisease_R.isSelected() == true) {
			adv = "3";
		} else if (che_advanced_dm_eyedisease_L.isSelected() == false
				&& che_advanced_dm_eyedisease_R.isSelected() == false) {
			adv = "0";
		} else if (che_advanced_dm_eyedisease_L.isSelected() == true) {
			adv = "1";
		} else {
			adv = "2";
		}

		String vib;
		if (che_vibration_L.isSelected() == true
				&& che_vibration_R.isSelected() == true) {
			vib = "3";
		} else if (che_vibration_L.isSelected() == false
				&& che_vibration_R.isSelected() == false) {
			vib = "0";
		} else if (che_vibration_L.isSelected() == true) {
			vib = "1";
		} else {
			vib = "2";
		}

		String pul;
		if (che_pulse_L.isSelected() == true
				&& che_pulse_R.isSelected() == true) {
			pul = "3";
		} else if (che_pulse_L.isSelected() == false
				&& che_pulse_R.isSelected() == false) {
			pul = "0";
		} else if (che_pulse_L.isSelected() == true) {
			pul = "1";
		} else {
			pul = "2";
		}

		String ulc;
		if (che_ulcer_L.isSelected() == true
				&& che_ulcer_R.isSelected() == true) {
			ulc = "3";
		} else if (che_ulcer_L.isSelected() == false
				&& che_ulcer_R.isSelected() == false) {
			ulc = "0";
		} else if (che_ulcer_L.isSelected() == true) {
			ulc = "1";
		} else {
			ulc = "2";
		}

		String acu;
		if (che_acupuncture_L.isSelected() == true
				&& che_acupuncture_R.isSelected() == true) {
			acu = "3";
		} else if (che_acupuncture_L.isSelected() == false
				&& che_acupuncture_R.isSelected() == false) {
			acu = "0";
		} else if (che_acupuncture_L.isSelected() == true) {
			acu = "1";
		} else {
			acu = "2";
		}

		String cur;
		if (che_ulcer_cured_L.isSelected() == true
				&& che_ulcer_cured_R.isSelected() == true) {
			cur = "3";
		} else if (che_ulcer_cured_L.isSelected() == false
				&& che_ulcer_cured_R.isSelected() == false) {
			cur = "0";
		} else if (che_ulcer_cured_L.isSelected() == true) {
			cur = "1";
		} else {
			cur = "2";
		}

		String byp;
		if (che_bypass_surgery_L.isSelected() == true
				&& che_bypass_surgery_R.isSelected() == true) {
			byp = "3";
		} else if (che_bypass_surgery_L.isSelected() == false
				&& che_bypass_surgery_R.isSelected() == false) {
			byp = "0";
		} else if (che_bypass_surgery_L.isSelected() == true) {
			byp = "1";
		} else {
			byp = "2";
		}

		String dm_year = null;
		try {
			if (dateComboBox.getValue() != null
					&& !dateComboBox.getValue().trim().equals("")) {
				dm_year = dateComboBox.getValue();
			}
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		String oral_syear = null;
		try {
			if (cbox_oral_syear.getSelectedItem() != null
					&& !cbox_oral_syear.getSelectedItem().toString().trim()
							.equals("")) {
				oral_syear = cbox_oral_syear.getSelectedItem().toString();
			}
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		String insulin_syear = null;
		try {
			if (cbox_insulin_syear.getSelectedItem() != null
					&& !cbox_insulin_syear.getSelectedItem().toString().trim()
							.equals("")) {
				insulin_syear = cbox_insulin_syear.getSelectedItem().toString();
			}
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		String drink_aweek = null;
		try {
			if (ftf_drink_aweek.getText() != null
					&& !ftf_drink_aweek.getText().trim().equals("")) {
				drink_aweek = ftf_drink_aweek.getText();
			}
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		Double dbp = null;
		try {
			dbp = Double.valueOf(txt_dbp.getText());
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		Double sbp = null;
		try {
			sbp = Double.valueOf(txt_sbp.getText());
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		Double bmi = null;
		try {
			bmi = Double.valueOf(txt_bmi.getText());
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		Double eye_l = null;
		try {
			eye_l = Double.valueOf(ftf_eye_lvision.getText());
		} catch (Exception ex) {
			System.out.println("Bug");
		}

		Double eye_r = null;
		try {
			eye_r = Double.valueOf(ftf_eye_rvision.getText());
		} catch (Exception ex) {
			System.out.println("Bug");
		}
		// String caseMgmtSql = String
		// .format("INSERT into case_manage (guid, reg_guid, p_no, status, s_no, modify_count, isdiagnosis, finish_time) "
		// + "VALUES ('%s','%s','%s','N', %s, %d, '1', NOW()) "
		// + " ON DUPLICATE KEY UPDATE finish_time=NOW() ",
		// caseGuid, regGuid, pNo, UserInfo.getUserNO(), 1);
		// System.out.println(caseMgmtSql);
		// DBC.executeUpdate(caseMgmtSql);
		String sql = "INSERT INTO asscement (guid, case_guid, p_no, family_history, self_care, dm_type, dm_typeo, dm_year, "
				+ " oral_hypoglycemic, oral_syear, insulin, insulin_syear, gestation, gestation_count, abortions_count, "
				+ " smoke, smoke_aday, drink, drink_aweek, sport, education, bloodtest_aweek, urine_aweek, dbp, sbp, bmi, "
				+ " eye_lvision, eye_rvision, fundus_check, light_coagulation, cataract, retinal_check, non_proliferative_retinopathy, "
				+ " pre_proliferative_retinopathy, proliferative_retinopathy, macular_degeneration, advanced_dm_eyedisease, "
				+ " vibration, pulse, ulcer, acupuncture, ulcer_cured, bypass_surgery, u_sid, udate) "
				+ " VALUES ('"
				+ this.guid
				+ "', '"
				// + this.regGuid + "', '"
				+ this.caseGuid
				+ "', '"
				+ this.pNo
				+ "', "
				+ com_family_history.getSelectedIndex()
				+ ", "
				+ com_self_care.getSelectedIndex()
				+ ", "
				+ " "
				+ com_dm_type.getSelectedIndex()
				+ ", '"
				+ txt_dm_typeo.getText()
				+ "', "
				+ dm_year
				+ ", "
				+ com_oral_hypoglycemic.getSelectedIndex()
				+ ", "
				+ " "
				+ oral_syear
				+ ", "
				+ com_insulin.getSelectedIndex()
				+ ", "
				+ insulin_syear
				+ ", "
				+ com_gestation.getSelectedIndex()
				+ ", "
				+ " "
				+ spi_gestation_count.getValue()
				+ ", "
				+ spi_abortions_count.getValue()
				+ ", "
				+ smo
				+ ", "
				+ spi_smoke_aday.getValue()
				+ ", "
				+ " "
				+ dri
				+ ", "
				+ drink_aweek
				+ ", "
				+ com_sport.getSelectedIndex()
				+ ", "
				+ com_education.getSelectedIndex()
				+ ", "
				+ " "
				+ spi_bloodtest_aweek.getValue()
				+ ", "
				+ spi_urine_aweek.getValue()
				+ ", "
				+ dbp
				+ ", "
				+ sbp
				+ ", "
				+ bmi
				+ ", "
				+ eye_l
				+ ", "
				+ " "
				+ eye_r
				+ ", "
				+ com_fundus_check.getSelectedIndex()
				+ ", "
				+ lig
				+ ", "
				+ cat
				+ ", "
				+ ret
				+ ", "
				+ non
				+ ", "
				+ pre
				+ ", "
				+ pro
				+ ", "
				+ " "
				+ mac
				+ ", "
				+ adv
				+ ", "
				+ vib
				+ ", "
				+ pul
				+ ", "
				+ ulc
				+ ", "
				+ acu
				+ ", "
				+ cur
				+ ", "
				+ byp
				+ ", '"
				+ UserInfo.getUserID()
				+ "', NOW() ) "
				+ " ON DUPLICATE KEY UPDATE case_guid = '"
				+ this.caseGuid
				+ "', "
				+ "p_no = '"
				+ this.pNo
				+ "', "
				+ "family_history = '"
				+ com_family_history.getSelectedIndex()
				+ "', "
				+ "self_care = '"
				+ com_self_care.getSelectedIndex()
				+ "', "
				+ "dm_type = '"
				+ com_dm_type.getSelectedIndex()
				+ "', "
				+ "dm_typeo = '"
				+ txt_dm_typeo.getText()
				+ "', "
				+ "dm_year = "
				+ dm_year
				+ ", "
				+ "oral_hypoglycemic = '"
				+ com_oral_hypoglycemic.getSelectedIndex()
				+ "', "
				+ "oral_syear = '"
				+ oral_syear
				+ "', "
				+ "insulin = '"
				+ com_insulin.getSelectedIndex()
				+ "', "
				+ "insulin_syear = '"
				+ insulin_syear
				+ "', "
				+ "gestation = '"
				+ com_gestation.getSelectedIndex()
				+ "', "
				+ "gestation_count = '"
				+ spi_gestation_count.getValue()
				+ "', "
				+ "abortions_count = '"
				+ spi_abortions_count.getValue()
				+ "', "
				+ "smoke = '"
				+ smo
				+ "', "
				+ "smoke_aday = '"
				+ spi_smoke_aday.getValue()
				+ "', "
				+ "drink = '"
				+ dri
				+ "', "
				+ "drink_aweek = '"
				+ ((drink_aweek == null) ? "0" : drink_aweek)
				+ "', "
				+ "sport = '"
				+ com_sport.getSelectedIndex()
				+ "', "
				+ "education = '"
				+ com_education.getSelectedIndex()
				+ "', "
				+ "bloodtest_aweek = '"
				+ spi_bloodtest_aweek.getValue()
				+ "', "
				+ "urine_aweek = '"
				+ spi_urine_aweek.getValue()
				+ "', "
				+ "dbp = '"
				+ dbp
				+ "', "
				+ "sbp = '"
				+ sbp
				+ "', "
				+ "bmi = '"
				+ bmi
				+ "', "
				+ "eye_lvision = '"
				+ eye_l
				+ "', "
				+ "eye_rvision = '"
				+ eye_r
				+ "', "
				+ "fundus_check = '"
				+ com_fundus_check.getSelectedIndex()
				+ "', "
				+ "light_coagulation = '"
				+ lig
				+ "', "
				+ "cataract = '"
				+ cat
				+ "', "
				+ "retinal_check = '"
				+ ret
				+ "', "
				+ "non_proliferative_retinopathy = '"
				+ non
				+ "', "
				+ "pre_proliferative_retinopathy = '"
				+ pre
				+ "', "
				+ "proliferative_retinopathy = '"
				+ pro
				+ "', "
				+ "macular_degeneration = '"
				+ mac
				+ "', "
				+ "advanced_dm_eyedisease = '"
				+ adv
				+ "', "
				+ "vibration = '"
				+ vib
				+ "', "
				+ "pulse = '"
				+ pul
				+ "', "
				+ "ulcer = '"
				+ ulc
				+ "', "
				+ "acupuncture = '"
				+ acu
				+ "', "
				+ "ulcer_cured = '"
				+ cur
				+ "', "
				+ "bypass_surgery = '"
				+ byp
				+ "', "
				+ "u_sid = '" + UserInfo.getUserID() + "', " + "udate = NOW() ";

		logger.debug("[{}][{}] {}", UserInfo.getUserID(),
				UserInfo.getUserName(), sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		try {
			ps.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}
		parent.setOverValue();
		btn_AssSave.setEnabled(false);
	}

}
