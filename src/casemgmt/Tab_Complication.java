package casemgmt;

import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.TabTools;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

public class Tab_Complication extends JPanel implements ISaveable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.ButtonGroup buttonGroup3;
	private javax.swing.ButtonGroup buttonGroup4;
	public javax.swing.JButton btn_ComSave;
	private javax.swing.JComboBox com_coronary_heart;
	private javax.swing.JComboBox com_dka;
	private javax.swing.JComboBox com_eye_lesions;
	private javax.swing.JComboBox com_hhs;
	private javax.swing.JComboBox com_hypoglycemia;
	private javax.swing.JComboBox com_kidney;
	private javax.swing.JComboBox com_neuropathy;
	private javax.swing.JComboBox com_paod;
	private javax.swing.JComboBox com_stroke;
	public javax.swing.JLabel Lab_record;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel182;
	private javax.swing.JLabel jLabel183;
	private javax.swing.JLabel jLabel184;
	private javax.swing.JLabel jLabel185;
	private javax.swing.JLabel jLabel186;
	private javax.swing.JLabel jLabel187;
	private javax.swing.JLabel jLabel188;
	private javax.swing.JLabel jLabel189;
	private javax.swing.JLabel jLabel190;
	private javax.swing.JLabel jLabel47;
	private javax.swing.JLabel jLabel48;
	private javax.swing.JLabel jLabel49;
	private javax.swing.JLabel jLabel50;
	private javax.swing.JLabel jLabel58;
	private javax.swing.JLabel jLabel59;
	private javax.swing.JLabel jLabel67;
	private javax.swing.JLabel jLabel68;
	private javax.swing.JLabel jLabel69;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel16;
	private javax.swing.JScrollPane jScrollPane6;
	public javax.swing.JLabel lab_bgac;
	public javax.swing.JLabel lab_dm;
	public javax.swing.JLabel lab_hdl;
	public javax.swing.JLabel lab_sbp;
	public javax.swing.JLabel lab_tg;
	private javax.swing.JRadioButton rad_angina_no;
	private javax.swing.JRadioButton rad_angina_yes;
	private javax.swing.JRadioButton rad_claudication_no;
	private javax.swing.JRadioButton rad_claudication_yes;
	private javax.swing.JRadioButton rad_peripheral_neuropathy_no;
	private javax.swing.JRadioButton rad_peripheral_neuropathy_yes;
	private javax.swing.JRadioButton rad_postural_hypotension_no;
	private javax.swing.JRadioButton rad_postural_hypotension_yes;
	private javax.swing.JTextField txt_other;
	public javax.swing.JTextField txt_waist;

	private String regGuid;
	private String pNo;
	private String caseGuid;
	private Frm_Case parent;
	private String complicationId;

	private int modifyCount;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_Complication(String caseGuid, String pNo, String regGuid) {
		this.caseGuid = caseGuid;
		this.regGuid = regGuid;
		this.pNo = pNo;
		modifyCount = 0;
		complicationId = UUID.randomUUID().toString();
		initComponents();
		new Thread(new Runnable() {

			@Override
			public void run() {
				init();
			}
		}).start();
	}

	private void refreashHist() {
		String sqlHist = String
				.format("select c.udate, "
						+ "case c.dka when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end dka, "
						+ "case c.hhs when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end hhs, "
						+ "case c.hypoglycemia when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end hypoglycemia, "
						+ "case c.stroke when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end stroke, "
						+ "case c.coronary_heart when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end coronary_heart, "
						+ "case c.paod when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end paod, "
						+ "case c.eye_lesions when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end eye_lesions, "
						+ "case c.neuropathy when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end neuropathy, "
						+ "case c.kidney when '1' then 'yes' when '2' then 'no' when '3' then 'I don''t know' else '' end kidney, "
						+ "c.waist, "
						+ "c.other, "
						+ "case c.postural_hypotension when '1' then 'yes' when '2' then 'no' else '' end postural_hypotension, "
						+ "case c.peripheral_neuropathy when '1' then 'yes' when '2' then 'no' else '' end peripheral_neuropathy, "
						+ "case c.angina when '1' then 'yes' when '2' then 'no' else '' end angina, "
						+ "case c.claudication when '1' then 'yes' when '2' then 'no' else '' end claudication "
						+ "from complication c "
						+ "where p_no = '%s' order by udate desc",
						pNo);

		ResultSet rsHist = null;

		try {
			rsHist = DBC.executeQuery(sqlHist);
			this.tabCompHist.setModel(HISModel.getModel(rsHist));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rsHist);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		String sqlcs = String
				.format("SELECT dka, hhs, hypoglycemia, stroke, coronary_heart, paod, eye_lesions, neuropathy, "
						+ "kidney, waist, other, postural_hypotension, peripheral_neuropathy, angina, "
						+ "claudication, u_sid, udate "
						+ " FROM complication "
						+ " WHERE complication.reg_guid = '%s' "
						+ " AND complication.p_no = '%s' "
						+ " ORDER BY udate DESC LIMIT 0, 1", regGuid, pNo);
		ResultSet cs = null;

		try {
			cs = DBC.executeQuery(sqlcs);

			if (cs.next()) {
				if (cs.getString("udate") != null) {
					com_dka.setSelectedIndex(Integer.parseInt(cs
							.getString("dka")));
					com_hhs.setSelectedIndex(Integer.parseInt(cs
							.getString("hhs")));
					com_hypoglycemia.setSelectedIndex(Integer.parseInt(cs
							.getString("hypoglycemia")));

					com_stroke.setSelectedIndex(Integer.parseInt(cs
							.getString("stroke")));
					com_coronary_heart.setSelectedIndex(Integer.parseInt(cs
							.getString("coronary_heart")));
					com_paod.setSelectedIndex(Integer.parseInt(cs
							.getString("paod")));

					com_eye_lesions.setSelectedIndex(Integer.parseInt(cs
							.getString("eye_lesions")));
					com_neuropathy.setSelectedIndex(Integer.parseInt(cs
							.getString("neuropathy")));
					com_kidney.setSelectedIndex(Integer.parseInt(cs
							.getString("kidney")));

					txt_waist.setText(cs.getString("waist"));
					txt_other.setText(cs.getString("other"));
					// txt_other.setSelectedIndex(Integer.parseInt(cs.getString("dka")));

					if (cs.getString("postural_hypotension").equals("1")) {
						rad_postural_hypotension_yes.setSelected(true);
					} else if (cs.getString("postural_hypotension").equals("2")) {
						rad_postural_hypotension_no.setSelected(true);
					} else {
					}

					if (cs.getString("peripheral_neuropathy").equals("1")) {
						rad_peripheral_neuropathy_yes.setSelected(true);
					} else if (cs.getString("peripheral_neuropathy")
							.equals("2")) {
						rad_peripheral_neuropathy_no.setSelected(true);
					} else {
					}

					if (cs.getString("angina").equals("1")) {
						rad_angina_yes.setSelected(true);
					} else if (cs.getString("angina").equals("2")) {
						rad_angina_no.setSelected(true);
					} else {
					}

					if (cs.getString("claudication").equals("1")) {
						rad_claudication_yes.setSelected(true);
					} else if (cs.getString("claudication").equals("2")) {
						rad_claudication_no.setSelected(true);
					} else {
					}
				}
			} else {
				// 所有資料帶入空值
			}

			refreashHist();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(cs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			btn_ComSave.setEnabled(false);
		}
	}

	private void initComponents() {
		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();
		buttonGroup3 = new javax.swing.ButtonGroup();
		buttonGroup4 = new javax.swing.ButtonGroup();
		jScrollPane6 = new javax.swing.JScrollPane();
		jPanel15 = new javax.swing.JPanel();
		jPanel16 = new javax.swing.JPanel();
		jLabel48 = new javax.swing.JLabel();
		jLabel49 = new javax.swing.JLabel();
		jLabel50 = new javax.swing.JLabel();
		jLabel58 = new javax.swing.JLabel();
		rad_postural_hypotension_yes = new javax.swing.JRadioButton();
		rad_postural_hypotension_no = new javax.swing.JRadioButton();
		rad_peripheral_neuropathy_yes = new javax.swing.JRadioButton();
		rad_peripheral_neuropathy_no = new javax.swing.JRadioButton();
		rad_angina_yes = new javax.swing.JRadioButton();
		rad_angina_no = new javax.swing.JRadioButton();
		rad_claudication_yes = new javax.swing.JRadioButton();
		rad_claudication_no = new javax.swing.JRadioButton();
		btn_ComSave = new javax.swing.JButton();
		Lab_record = new javax.swing.JLabel();
		btn_ComSave.setText("Save");
		btn_ComSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_ComSaveActionPerformed(evt);
			}
		});

		jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

		jPanel16.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Symptom"));

		jLabel48.setText("Postural hypotension：");

		jLabel49.setText("Peripheral neuropathy：");

		jLabel50.setText("Angina chest pain：");

		jLabel58.setText("Leg claudication：");

		buttonGroup1.add(rad_postural_hypotension_yes);
		rad_postural_hypotension_yes.setText("Yes");
		rad_postural_hypotension_yes
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged_C(evt);
					}
				});
		rad_postural_hypotension_yes
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
					}
				});

		buttonGroup1.add(rad_postural_hypotension_no);
		rad_postural_hypotension_no.setText("No");
		rad_postural_hypotension_no
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged_C(evt);
					}
				});

		buttonGroup2.add(rad_peripheral_neuropathy_yes);
		rad_peripheral_neuropathy_yes.setText("Yes");
		rad_peripheral_neuropathy_yes
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged_C(evt);
					}
				});

		buttonGroup2.add(rad_peripheral_neuropathy_no);
		rad_peripheral_neuropathy_no.setText("No");
		rad_peripheral_neuropathy_no
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						ItemStateChanged_C(evt);
					}
				});

		buttonGroup3.add(rad_angina_yes);
		rad_angina_yes.setText("Yes");
		rad_angina_yes.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});

		buttonGroup3.add(rad_angina_no);
		rad_angina_no.setText("No");
		rad_angina_no.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});

		buttonGroup4.add(rad_claudication_yes);
		rad_claudication_yes.setText("Yes");
		rad_claudication_yes.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});

		buttonGroup4.add(rad_claudication_no);
		rad_claudication_no.setText("No");
		rad_claudication_no.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});

		javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(
				jPanel16);
		jPanel16.setLayout(jPanel16Layout);
		jPanel16Layout
				.setHorizontalGroup(jPanel16Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel16Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel16Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jLabel50,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jLabel49,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jLabel48,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jLabel58,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addGap(33, 33, 33)
										.addGroup(
												jPanel16Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																rad_claudication_yes)
														.addComponent(
																rad_angina_yes)
														.addComponent(
																rad_peripheral_neuropathy_yes)
														.addComponent(
																rad_postural_hypotension_yes))
										.addGap(18, 18, 18)
										.addGroup(
												jPanel16Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																rad_angina_no)
														.addComponent(
																rad_peripheral_neuropathy_no)
														.addComponent(
																rad_postural_hypotension_no)
														.addComponent(
																rad_claudication_no))
										.addContainerGap(573, Short.MAX_VALUE)));
		jPanel16Layout
				.setVerticalGroup(jPanel16Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel16Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel16Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel48)
														.addComponent(
																rad_postural_hypotension_yes)
														.addComponent(
																rad_postural_hypotension_no))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel16Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel49)
														.addComponent(
																rad_peripheral_neuropathy_no)
														.addComponent(
																rad_peripheral_neuropathy_yes))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel16Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel50)
														.addComponent(
																rad_angina_no)
														.addComponent(
																rad_angina_yes))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jPanel16Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel58)
														.addComponent(
																rad_claudication_no)
														.addComponent(
																rad_claudication_yes))
										.addContainerGap(18, Short.MAX_VALUE)));

		scrollPane = new JScrollPane();

		JPanel panel = new JPanel();

		javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(
				jPanel15);
		jPanel15Layout.setHorizontalGroup(
			jPanel15Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel15Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel15Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jPanel16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 848, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
					.addContainerGap())
		);
		jPanel15Layout.setVerticalGroup(
			jPanel15Layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(jPanel15Layout.createSequentialGroup()
					.addGroup(jPanel15Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel15Layout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(jPanel16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
					.addContainerGap())
		);
		jLabel187 = new javax.swing.JLabel();

		jLabel187.setText("HDL：");
		jLabel186 = new javax.swing.JLabel();

		jLabel186.setText("SBP：");
		jLabel67 = new javax.swing.JLabel();
		jLabel67.setText("HHHK：");
		jLabel47 = new javax.swing.JLabel();

		jLabel47.setText("Hypertension：");
		jLabel182 = new javax.swing.JLabel();

		jLabel182.setText("DKA：");
		jLabel189 = new javax.swing.JLabel();

		jLabel189.setText("TG > 150：");
		com_dka = new javax.swing.JComboBox();

		com_dka.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Yes", "No", "I don’t know" }));
		com_dka.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		com_hhs = new javax.swing.JComboBox();

		com_hhs.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Yes", "No", "I don’t know" }));
		com_hhs.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		com_hypoglycemia = new javax.swing.JComboBox();

		com_hypoglycemia.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "　", "Yes", "No", "I don’t know" }));
		com_hypoglycemia.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		lab_tg = new javax.swing.JLabel();

		lab_tg.setText("No");
		lab_hdl = new javax.swing.JLabel();

		lab_hdl.setText("No");
		lab_sbp = new javax.swing.JLabel();

		lab_sbp.setText("No");
		jLabel183 = new javax.swing.JLabel();

		jLabel183.setText("Stroke：");
		jLabel12 = new javax.swing.JLabel();

		jLabel12.setText("DM：");
		jLabel14 = new javax.swing.JLabel();

		jLabel14.setText("BGAc：");
		jLabel59 = new javax.swing.JLabel();

		jLabel59.setText("Waist：");
		jLabel184 = new javax.swing.JLabel();

		jLabel184.setText("Coronary Heart Disease：");
		jLabel68 = new javax.swing.JLabel();

		jLabel68.setText("PAOD：");
		com_stroke = new javax.swing.JComboBox();

		com_stroke.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Yes", "No", "I don’t know" }));
		com_stroke.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		lab_bgac = new javax.swing.JLabel();

		lab_bgac.setText("No");
		txt_waist = new javax.swing.JTextField();

		txt_waist.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_C(evt);
			}
		});
		jLabel13 = new javax.swing.JLabel();

		jLabel13.setText("cm");
		lab_dm = new javax.swing.JLabel();

		lab_dm.setText("No");
		com_coronary_heart = new javax.swing.JComboBox();

		com_coronary_heart.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "　", "Yes", "No", "I don’t know" }));
		com_coronary_heart.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		com_paod = new javax.swing.JComboBox();

		com_paod.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Yes", "No", "I don’t know" }));
		com_paod.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		jLabel185 = new javax.swing.JLabel();

		jLabel185.setText("Neuropathy：");
		jLabel190 = new javax.swing.JLabel();

		jLabel190.setText("Others：");
		jLabel69 = new javax.swing.JLabel();

		jLabel69.setText("Eye lesions：");
		jLabel188 = new javax.swing.JLabel();

		jLabel188.setText("Kidney Diseases：");
		txt_other = new javax.swing.JTextField();

		txt_other.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				KeyReleased_C(evt);
			}
		});
		com_eye_lesions = new javax.swing.JComboBox();

		com_eye_lesions.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "　", "Yes", "No", "I don’t know" }));
		com_eye_lesions.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		com_kidney = new javax.swing.JComboBox();

		com_kidney.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"　", "Yes", "No", "I don’t know" }));
		com_kidney.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		com_neuropathy = new javax.swing.JComboBox();

		com_neuropathy.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "　", "Yes", "No", "I don’t know" }));
		com_neuropathy.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				ItemStateChanged_C(evt);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(44)
							.addComponent(jLabel182)
							.addGap(10)
							.addComponent(com_dka, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(114)
							.addComponent(jLabel183)
							.addGap(10)
							.addComponent(com_stroke, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(94)
							.addComponent(jLabel69)
							.addGap(4)
							.addComponent(com_eye_lesions, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(37)
							.addComponent(jLabel67)
							.addGap(10)
							.addComponent(com_hhs, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(jLabel184)
							.addGap(10)
							.addComponent(com_coronary_heart, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(90)
							.addComponent(jLabel185)
							.addGap(4)
							.addComponent(com_neuropathy, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(jLabel47)
							.addGap(10)
							.addComponent(com_hypoglycemia, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(117)
							.addComponent(jLabel68)
							.addGap(10)
							.addComponent(com_paod, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
							.addGap(70)
							.addComponent(jLabel188)
							.addGap(4)
							.addComponent(com_kidney, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(46)
							.addComponent(jLabel186)
							.addGap(10)
							.addComponent(lab_sbp)
							.addGap(230)
							.addComponent(jLabel59)
							.addGap(10)
							.addComponent(txt_waist, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(jLabel13)
							.addGap(166)
							.addComponent(jLabel190)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txt_other))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(45)
							.addComponent(jLabel187)
							.addGap(10)
							.addComponent(lab_hdl)
							.addGap(232)
							.addComponent(jLabel14)
							.addGap(10)
							.addComponent(lab_bgac))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addComponent(jLabel189)
							.addGap(10)
							.addComponent(lab_tg)
							.addGap(242)
							.addComponent(jLabel12)
							.addGap(10)
							.addComponent(lab_dm)))
					.addContainerGap(50, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel182))
						.addComponent(com_dka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel183))
						.addComponent(com_stroke, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel69))
						.addComponent(com_eye_lesions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel67))
						.addComponent(com_hhs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel184))
						.addComponent(com_coronary_heart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel185))
						.addComponent(com_neuropathy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel47))
						.addComponent(com_hypoglycemia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel68))
						.addComponent(com_paod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(jLabel188))
						.addComponent(com_kidney, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(3)
									.addComponent(jLabel186))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(3)
									.addComponent(lab_sbp))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(3)
									.addComponent(jLabel59))
								.addComponent(txt_waist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(3)
									.addComponent(jLabel13))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(3)
									.addComponent(jLabel190)))
							.addGap(6)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(jLabel187)
								.addComponent(lab_hdl)
								.addComponent(jLabel14)
								.addComponent(lab_bgac))
							.addGap(8)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(jLabel189)
								.addComponent(lab_tg)
								.addComponent(jLabel12)
								.addComponent(lab_dm)))
						.addComponent(txt_other, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

		tabCompHist = new JTable();
		scrollPane.setViewportView(tabCompHist);
		jPanel15.setLayout(jPanel15Layout);
		jScrollPane6.setViewportView(jPanel15);

		javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(
				this);
		setLayout(jPanel18Layout);
		jPanel18Layout
				.setHorizontalGroup(jPanel18Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel18Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel18Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane6,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																791,
																Short.MAX_VALUE)
														.addGroup(
																jPanel18Layout
																		.createSequentialGroup()
																		.addComponent(
																				Lab_record,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				701,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btn_ComSave,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				86,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		jPanel18Layout
				.setVerticalGroup(jPanel18Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel18Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jScrollPane6,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												278, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel18Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																btn_ComSave)
														.addComponent(
																Lab_record,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																24,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

	}

	private void btn_ComSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_ComSaveActionPerformed
		try {
			save();
			new Thread(new Runnable() {

				@Override
				public void run() {
					refreashHist();
				}
			}).start();
			JOptionPane.showMessageDialog(null, "Save Complete");
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Save Failed: " + ex.getMessage());
			btn_ComSave.setEnabled(true);
		}
	}

	private void KeyReleased_C(java.awt.event.KeyEvent evt) {
		btn_ComSave.setEnabled(true);
	}

	private void ItemStateChanged_C(ItemEvent evt) {
		btn_ComSave.setEnabled(true);
	}

	@Override
	public boolean isSaveable() {
		return btn_ComSave.isEnabled();
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

	@Override
	public void save(Connection conn) throws Exception {
		String bg1 = "0", bg2 = "0", bg3 = "0", bg4 = "0";

		if (buttonGroup1.getSelection() == rad_postural_hypotension_yes
				.getModel()) {
			bg1 = "1";
		} else if (buttonGroup1.getSelection() == rad_postural_hypotension_no
				.getModel()) {
			bg1 = "2";
		} else {
			bg1 = "3";
		}

		if (buttonGroup2.getSelection() == rad_peripheral_neuropathy_yes
				.getModel()) {
			bg2 = "1";
		} else if (buttonGroup2.getSelection() == rad_peripheral_neuropathy_no
				.getModel()) {
			bg2 = "2";
		} else {
			bg2 = "3";
		}

		if (buttonGroup3.getSelection() == rad_angina_yes.getModel()) {
			bg3 = "1";
		} else if (buttonGroup3.getSelection() == rad_angina_no.getModel()) {
			bg3 = "2";
		} else {
			bg3 = "3";
		}

		if (buttonGroup4.getSelection() == rad_claudication_yes.getModel()) {
			bg4 = "1";
		} else if (buttonGroup4.getSelection() == rad_claudication_no
				.getModel()) {
			bg4 = "2";
		} else {
			bg4 = "3";
		}

		// String caseMgmtSql = String
		// .format("INSERT into case_manage (guid, reg_guid, p_no, status, s_no, modify_count,isdiagnosis, finish_time) "
		// + "VALUES ('%s','%s','%s','N', %s, %d, '1', NOW()) ON DUPLICATE KEY "
		// +
		// "UPDATE reg_guid='%s',p_no='%s', status='N', s_no=%s, modify_count=%d, isdiagnosis='1', finish_time=NOW() ",
		// caseGuid, regGuid, pNo, UserInfo.getUserNO(),
		// ++modifyCount, regGuid, pNo, UserInfo.getUserNO(),
		// modifyCount);
		// logger.debug("[{}][{}] {}", UserInfo.getUserID(),
		// UserInfo.getUserName(), caseMgmtSql);
		// PreparedStatement psCaseUpate = conn.prepareStatement(caseMgmtSql);
		// try {
		// psCaseUpate.executeUpdate();
		// } catch (Exception e) {
		// throw e;
		// } finally {
		// if (psCaseUpate != null)
		// psCaseUpate.close();
		// }

		String sql = String
				.format("INSERT INTO complication (guid, reg_guid, dka, hhs, hypoglycemia, stroke, "
						+ " coronary_heart, paod, eye_lesions, neuropathy, kidney, waist, other, postural_hypotension, "
						+ " peripheral_neuropathy, angina, claudication, u_sid, udate, case_guid, p_no ) "
						+ "VALUES ( "
						+ "'%s','%s', %d, %d, %d, %d, %d, %d, %d, %d, %d, "
						+ "%s,'%s', %s, %s, %s, %s, '%s', NOW(), '%s', '%s')  ON DUPLICATE KEY "
						+ "UPDATE "
						+ "reg_guid='%s', dka=%d, hhs=%d, hypoglycemia=%d, stroke=%d, coronary_heart=%d, "
						+ "paod=%d, eye_lesions=%d, neuropathy=%d, kidney=%d, "
						+ "waist=%s,other='%s', postural_hypotension=%s, peripheral_neuropathy=%s, angina=%s, "
						+ "claudication=%s, u_sid='%s', udate=NOW(), p_no='%s' ",
						complicationId,
						regGuid,
						com_dka.getSelectedIndex(),
						com_hhs.getSelectedIndex(),
						com_hypoglycemia.getSelectedIndex(),
						com_stroke.getSelectedIndex(),
						com_coronary_heart.getSelectedIndex(),
						com_paod.getSelectedIndex(),
						com_eye_lesions.getSelectedIndex(),
						com_neuropathy.getSelectedIndex(),
						com_kidney.getSelectedIndex(),
						(txt_waist.getText().equals("")) ? "NULL" : String
								.format("'%s'", txt_waist.getText()),
						txt_other.getText(),
						bg1,
						bg2,
						bg3,
						bg4,
						UserInfo.getUserID(),
						caseGuid,
						pNo,
						regGuid,
						com_dka.getSelectedIndex(),
						com_hhs.getSelectedIndex(),
						com_hypoglycemia.getSelectedIndex(),
						com_stroke.getSelectedIndex(),
						com_coronary_heart.getSelectedIndex(),
						com_paod.getSelectedIndex(),
						com_eye_lesions.getSelectedIndex(),
						com_neuropathy.getSelectedIndex(),
						com_kidney.getSelectedIndex(),
						(txt_waist.getText().equals("")) ? "NULL" : String
								.format("'%s'", txt_waist.getText()), txt_other
								.getText(), bg1, bg2, bg3, bg4, UserInfo
								.getUserID(), pNo);

		logger.debug("[{}][{}] {}", UserInfo.getUserID(),
				UserInfo.getUserName(), sql);
		PreparedStatement ps = conn.prepareStatement(sql);
		try {
			ps.executeUpdate();
			complicationId = UUID.randomUUID().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}

		parent.setOverValue();
		btn_ComSave.setEnabled(false);
	}

	private static Logger logger = LogManager.getLogger(Tab_Complication.class
			.getName());
	private JScrollPane scrollPane;
	private JTable tabCompHist;
}
