package casemgmt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import multilingual.Language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import javax.swing.ScrollPaneConstants;

public class Tab_HIVCase extends JPanel implements ISaveable {
	private Frm_Case parent;
	private static final long serialVersionUID = 1L;
	private static final Language lang = Language.getInstance();

	private JScrollPane jScrollPane_Frame;
	private JPanel pan_Frame;
	
	private JLabel lbl_Risk;
	private JPanel pan_Risk;
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
	private JLabel lbl_Other;
	private JTextField txt_Other;

	private JLabel lbl_HBP;
	private JPanel pan_HBP;
	private JCheckBox chckbx_IHHC;
	private JCheckBox chckbx_IHDM;
	private JCheckBox chckbx_IHEBP;
	private JCheckBox chckbx_FMHHC;
	private JCheckBox chckbx_FMHDM;
	private JCheckBox chckbx_FMHEBP;

	private JLabel lbl_SB;
	private JPanel pan_SB;
	private JCheckBox chckbx_WSVD;
	private JCheckBox chckbx_10SP;
	private JCheckBox chckbx_HSIWS;

	private JLabel lbl_IDU;
	private JPanel pan_IDU;
	private JLabel lbl_IDUAge;
	private JTextField txt_IDUAge;
	private JLabel lbl_IDUDuration;
	private JTextField txt_IDUDuration;
	private JCheckBox chckbx_WTD;
	private JCheckBox chckbx_HUID;
	private JCheckBox chckbx_SN;
	private JCheckBox chckbx_SW;
	private JCheckBox chckbx_SNW;
	private JCheckBox chckbx_Methadone;
	private JLabel lbl_StartMethadone;
	private JTextField txt_StartMethadone;
	private JLabel lbl_StaticsMethadone;
	private JComboBox cbb_StaticsMethadone;

	private JLabel lbl_Vaccine;
	private JButton btn_AddVaccine;
	private JScrollPane scrollPane;
	private JTable tab_Vaccine;

	private JLabel lbl_VenerealDisease;
	private JButton btn_AddVD;
	private JScrollPane scrollPane_1;
	private JTable tab_VenerealDisease;

	public JButton btn_Save;

	private String caseGuid;
	private String p_no;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_HIVCase(String caseGuid) {
		this.caseGuid = caseGuid;
		setLayout(null);
		
		pan_Frame = new JPanel();
		pan_Frame.setLayout(null);
		pan_Frame.setBounds(0, 0, 1000, 1000);

		lbl_Risk = new JLabel("Risk:");
		lbl_Risk.setBounds(12, 5, 34, 15);
		pan_Frame.add(lbl_Risk);

		pan_Risk = new JPanel();
		pan_Risk.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_Risk.setBounds(12, 23, 392, 253);
		pan_Frame.add(pan_Risk);
		pan_Risk.setLayout(null);

		chckbx_PHHD = new JCheckBox(lang.getString("PHHD"));
		chckbx_PHHD.setBounds(6, 6, 201, 23);
		pan_Risk.add(chckbx_PHHD);

		chckbx_PHCD = new JCheckBox(lang.getString("PHCD"));
		chckbx_PHCD.setBounds(6, 29, 201, 23);
		pan_Risk.add(chckbx_PHCD);

		chckbx_PHHC = new JCheckBox(lang.getString("PHHC"));
		chckbx_PHHC.setBounds(6, 54, 201, 23);
		pan_Risk.add(chckbx_PHHC);

		chckbx_PHDM = new JCheckBox(lang.getString("PHDM"));
		chckbx_PHDM.setBounds(6, 79, 201, 23);
		pan_Risk.add(chckbx_PHDM);

		chckbx_TypeA = new JCheckBox(lang.getString("PHTYPEA"));
		chckbx_TypeA.setBounds(6, 104, 201, 23);
		pan_Risk.add(chckbx_TypeA);

		chckbx_TypeB = new JCheckBox(lang.getString("PHTYPEB"));
		chckbx_TypeB.setBounds(6, 129, 201, 23);
		pan_Risk.add(chckbx_TypeB);

		chckbx_TypeC = new JCheckBox(lang.getString("PHTYPEC"));
		chckbx_TypeC.setBounds(6, 154, 201, 23);
		pan_Risk.add(chckbx_TypeC);

		chckbx_Malignancies = new JCheckBox(lang.getString("PHMALIG"));
		chckbx_Malignancies.setBounds(211, 6, 168, 23);
		pan_Risk.add(chckbx_Malignancies);

		chckbx_HD = new JCheckBox(lang.getString("HD"));
		chckbx_HD.setBounds(211, 29, 168, 23);
		pan_Risk.add(chckbx_HD);

		chckbx_PHNS = new JCheckBox(lang.getString("PHNS"));
		chckbx_PHNS.setBounds(211, 54, 168, 23);
		pan_Risk.add(chckbx_PHNS);

		chckbx_Smoke = new JCheckBox(lang.getString("PHSMOKE"));
		chckbx_Smoke.setBounds(211, 79, 168, 23);
		pan_Risk.add(chckbx_Smoke);

		chckbx_Alcoholism = new JCheckBox(lang.getString("PHALCOHOLISM"));
		chckbx_Alcoholism.setBounds(211, 104, 168, 23);
		pan_Risk.add(chckbx_Alcoholism);

		chckbx_OW = new JCheckBox(lang.getString("PHOW"));
		chckbx_OW.setBounds(211, 129, 168, 23);
		pan_Risk.add(chckbx_OW);

		lbl_Other = new JLabel(lang.getString("PHOTHER"));
		lbl_Other.setBounds(6, 183, 61, 15);
		pan_Risk.add(lbl_Other);

		txt_Other = new JTextField();
		txt_Other.setBounds(62, 180, 303, 21);
		pan_Risk.add(txt_Other);
		txt_Other.setColumns(10);

		lbl_HBP = new JLabel("High Blood Pressure:");
		lbl_HBP.setBounds(417, 5, 149, 15);
		pan_Frame.add(lbl_HBP);

		pan_HBP = new JPanel();
		pan_HBP.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_HBP.setBounds(415, 23, 680, 155);
		pan_Frame.add(pan_HBP);
		pan_HBP.setLayout(null);

		chckbx_IHHC = new JCheckBox(lang.getString("IHHC"));
		chckbx_IHHC.setBounds(6, 6, 368, 23);
		pan_HBP.add(chckbx_IHHC);

		chckbx_IHDM = new JCheckBox(lang.getString("IHDM"));
		chckbx_IHDM.setBounds(6, 31, 368, 23);
		pan_HBP.add(chckbx_IHDM);

		chckbx_IHEBP = new JCheckBox(lang.getString("IHEBP"));
		chckbx_IHEBP.setBounds(6, 56, 562, 23);
		pan_HBP.add(chckbx_IHEBP);

		chckbx_FMHHC = new JCheckBox(lang.getString("FMHHC"));
		chckbx_FMHHC.setBounds(6, 81, 562, 23);
		pan_HBP.add(chckbx_FMHHC);

		chckbx_FMHDM = new JCheckBox(lang.getString("FMHDM"));
		chckbx_FMHDM.setBounds(6, 106, 562, 23);
		pan_HBP.add(chckbx_FMHDM);

		chckbx_FMHEBP = new JCheckBox(lang.getString("FMHEBP"));
		chckbx_FMHEBP.setBounds(6, 131, 666, 23);
		pan_HBP.add(chckbx_FMHEBP);

		lbl_SB = new JLabel("Sexual Behavior:");
		lbl_SB.setBounds(417, 180, 118, 15);
		pan_Frame.add(lbl_SB);

		pan_SB = new JPanel();
		pan_SB.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_SB.setBounds(416, 203, 679, 73);
		pan_Frame.add(pan_SB);
		pan_SB.setLayout(null);

		chckbx_WSVD = new JCheckBox(lang.getString("WSVD"));
		chckbx_WSVD.setBounds(6, 6, 299, 23);
		pan_SB.add(chckbx_WSVD);

		chckbx_10SP = new JCheckBox(lang.getString("10SP"));
		chckbx_10SP.setBounds(6, 25, 299, 23);
		pan_SB.add(chckbx_10SP);

		chckbx_HSIWS = new JCheckBox(lang.getString("HSIWS"));
		chckbx_HSIWS.setBounds(6, 44, 299, 23);
		pan_SB.add(chckbx_HSIWS);

		lbl_IDU = new JLabel("IDU:");
		lbl_IDU.setBounds(616, 288, 29, 15);
		pan_Frame.add(lbl_IDU);

		pan_IDU = new JPanel();
		pan_IDU.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_IDU.setBounds(616, 306, 479, 281);
		pan_Frame.add(pan_IDU);
		pan_IDU.setLayout(null);

		lbl_IDUAge = new JLabel(lang.getString("IDUAGE"));
		lbl_IDUAge.setBounds(6, 13, 139, 15);
		pan_IDU.add(lbl_IDUAge);

		txt_IDUAge = new JTextField();
		txt_IDUAge.setBounds(248, 10, 139, 21);
		pan_IDU.add(txt_IDUAge);
		txt_IDUAge.setColumns(10);

		lbl_IDUDuration = new JLabel(lang.getString("IDUDURATION"));
		lbl_IDUDuration.setBounds(6, 44, 139, 15);
		pan_IDU.add(lbl_IDUDuration);

		txt_IDUDuration = new JTextField();
		txt_IDUDuration.setBounds(248, 41, 139, 21);
		pan_IDU.add(txt_IDUDuration);
		txt_IDUDuration.setColumns(10);

		chckbx_WTD = new JCheckBox(lang.getString("WTD"));
		chckbx_WTD.setBounds(6, 68, 246, 23);
		pan_IDU.add(chckbx_WTD);

		chckbx_HUID = new JCheckBox(lang.getString("HUID"));
		chckbx_HUID.setBounds(6, 93, 246, 23);
		pan_IDU.add(chckbx_HUID);

		chckbx_SN = new JCheckBox(lang.getString("SN"));
		chckbx_SN.setBounds(6, 118, 246, 23);
		pan_IDU.add(chckbx_SN);

		chckbx_SW = new JCheckBox(lang.getString("SW"));
		chckbx_SW.setBounds(6, 143, 246, 23);
		pan_IDU.add(chckbx_SW);

		chckbx_SNW = new JCheckBox(lang.getString("SNW"));
		chckbx_SNW.setBounds(6, 168, 246, 23);
		pan_IDU.add(chckbx_SNW);

		chckbx_Methadone = new JCheckBox(lang.getString("METHADONE"));
		chckbx_Methadone.setBounds(6, 193, 246, 23);
		pan_IDU.add(chckbx_Methadone);

		lbl_StartMethadone = new JLabel(lang.getString("STARTMETHADONE"));
		lbl_StartMethadone.setBounds(6, 224, 224, 15);
		pan_IDU.add(lbl_StartMethadone);

		txt_StartMethadone = new JTextField();
		txt_StartMethadone.setBounds(248, 221, 139, 21);
		pan_IDU.add(txt_StartMethadone);
		txt_StartMethadone.setColumns(10);

		lbl_StaticsMethadone = new JLabel(lang.getString("STATICSMETHADONE"));
		lbl_StaticsMethadone.setBounds(6, 249, 230, 15);
		pan_IDU.add(lbl_StaticsMethadone);

		cbb_StaticsMethadone = new JComboBox();
		cbb_StaticsMethadone.setBounds(248, 246, 139, 21);
		pan_IDU.add(cbb_StaticsMethadone);
		cbb_StaticsMethadone.removeAllItems();
		this.cbb_StaticsMethadone.addItem("Q:Success");
		this.cbb_StaticsMethadone.addItem("U:Fail");

		lbl_Vaccine = new JLabel("Vaccine:");
		lbl_Vaccine.setBounds(12, 288, 59, 15);
		pan_Frame.add(lbl_Vaccine);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 309, 592, 165);
		pan_Frame.add(scrollPane);

		tab_Vaccine = new JTable();
		scrollPane.setViewportView(tab_Vaccine);

		lbl_VenerealDisease = new JLabel("Venereal Disease:");
		lbl_VenerealDisease.setBounds(12, 482, 130, 15);
		pan_Frame.add(lbl_VenerealDisease);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 504, 592, 127);
		pan_Frame.add(scrollPane_1);

		tab_VenerealDisease = new JTable();
		scrollPane_1.setViewportView(tab_VenerealDisease);

		btn_Save = new JButton(lang.getString("SAVE"));
		btn_Save.setBounds(616, 599, 262, 32);
		pan_Frame.add(btn_Save);
		btn_Save.setEnabled(true);
		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_SaveactionPerformed(evt);
			}
		});

		btn_AddVaccine = new JButton(lang.getString("ADD"));
		btn_AddVaccine.setBounds(81, 283, 61, 25);
		pan_Frame.add(btn_AddVaccine);
		btn_AddVaccine.setEnabled(true);
		btn_AddVaccine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_AddVaccineactionPerformed(evt);
			}
		});

		btn_AddVD = new JButton(lang.getString("ADD"));
		btn_AddVD.setBounds(144, 477, 61, 25);
		pan_Frame.add(btn_AddVD);
		btn_AddVD.setEnabled(true);
		btn_AddVD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_AddVDactionPerformed(evt);
			}
		});
		
		
		jScrollPane_Frame = new JScrollPane();
		jScrollPane_Frame.setLocation(0, 0);
		jScrollPane_Frame.setSize(1120, 650);
		jScrollPane_Frame.setViewportView(pan_Frame);
		
		add(jScrollPane_Frame);
		
		refreshHIV();
		refreshVaccine();
		refreshSD();
	}

	public void btn_AddVaccineactionPerformed(ActionEvent evt) {
		String sql = "INSERT into vaccine_history SELECT uuid(), '" + caseGuid
				+ "', NULL, NULL, NULL, NULL, NULL, '" + UserInfo.getUserNO()
				+ "'";
		try {
			DBC.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		btn_SaveactionPerformed(null);
		refreshVaccine();
	}

	public void btn_AddVDactionPerformed(ActionEvent evt) {
		String sql = "INSERT into sexsual_disease SELECT uuid(), '" + caseGuid
				+ "', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '"
				+ UserInfo.getUserNO() + "'";
		try {
			DBC.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		btn_SaveactionPerformed(null);
		refreshSD();
	}

	public void btn_SaveactionPerformed(ActionEvent evt) {
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

	private void refreshVaccine() {
		String sqlVaccine = "SELECT * FROM vaccine_history WHERE vaccine_history.case_guid='"
				+ caseGuid + "'";
		ResultSet rsVaccine = null;
		try {
			rsVaccine = DBC.executeQuery(sqlVaccine);
			rsVaccine.last();
			if (rsVaccine.getRow() == 0) {
				tab_Vaccine.setModel(new DefaultTableModel(
						new String[][] { { "No Information." } },
						new String[] { "Message" }));
			} else {
				int rowCount = rsVaccine.getRow();
				Object[][] matrix = new Object[rowCount][6];
				String[] header = new String[] { "Id", "HPV EXAM",
						"HPV EXAM DATE", "Vaccine Type", "Date" };
				rsVaccine.beforeFirst();
				int i = 0;
				while (rsVaccine.next()) {
					matrix[i][0] = (String) rsVaccine
							.getString("vaccine_history.guid");
					if (rsVaccine.getString("vaccine_history.HPVExam") != null
							&& ((String) rsVaccine
									.getString("vaccine_history.HPVExam"))
									.equals("Y")) {
						matrix[i][1] = true;
					} else {
						matrix[i][1] = false;
					}
					matrix[i][2] = (String) rsVaccine
							.getString("vaccine_history.HPVExamDate");
					matrix[i][3] = (String) rsVaccine
							.getString("vaccine_history.VaccineType");
					matrix[i][4] = (String) rsVaccine
							.getString("vaccine_history.InjectionDate");
					i = i + 1;
				}
				tab_Vaccine.setModel(new DefaultTableModel(matrix, header) {
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						if (columnIndex == 1) {
							return Boolean.class;
						} else {
							return String.class;
						}
					}

					@Override
					public boolean isCellEditable(int row, int column) {
						if (column == 0) {
							return false;
						} else {
							return true;
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshSD() {
		String sqlSD = "SELECT * FROM sexsual_disease WHERE sexsual_disease.case_guid='"
				+ caseGuid + "'";
		ResultSet rsSD = null;
		try {
			rsSD = DBC.executeQuery(sqlSD);
			rsSD.last();
			if (rsSD.getRow() == 0) {
				tab_VenerealDisease.setModel(new DefaultTableModel(
						new String[][] { { "No Information." } },
						new String[] { "Message" }));
			} else {
				int rowCount = rsSD.getRow();
				Object[][] matrix = new Object[rowCount][8];
				String[] header = new String[] { "Guid", "Happen Date",
						"Syphilis", "Gonorrhea", "NongonococcalUrethritis",
						"Wart", "Amebiasis", "Created Date" };
				rsSD.beforeFirst();
				int i = 0;
				while (rsSD.next()) {
					matrix[i][0] = (String) rsSD
							.getString("sexsual_disease.guid");
					matrix[i][1] = (String) rsSD
							.getString("sexsual_disease.happenDate");
					if (rsSD.getString("sexsual_disease.Syphilis") != null
							&& ((String) rsSD
									.getString("sexsual_disease.Syphilis"))
									.equals("Y")) {
						matrix[i][2] = true;
					} else {
						matrix[i][2] = false;
					}
					if (rsSD.getString("sexsual_disease.Gonorrhea") != null
							&& ((String) rsSD
									.getString("sexsual_disease.Gonorrhea"))
									.equals("Y")) {
						matrix[i][3] = true;
					} else {
						matrix[i][3] = false;
					}
					if (rsSD.getString("sexsual_disease.NongonococcalUrethritis") != null
							&& ((String) rsSD
									.getString("sexsual_disease.NongonococcalUrethritis"))
									.equals("Y")) {
						matrix[i][4] = true;
					} else {
						matrix[i][4] = false;
					}
					if (rsSD.getString("sexsual_disease.wart") != null
							&& ((String) rsSD.getString("sexsual_disease.wart"))
									.equals("Y")) {
						matrix[i][5] = true;
					} else {
						matrix[i][5] = false;
					}
					if (rsSD.getString("sexsual_disease.Amebiasis") != null
							&& ((String) rsSD
									.getString("sexsual_disease.Amebiasis"))
									.equals("Y")) {
						matrix[i][6] = true;
					} else {
						matrix[i][6] = false;
					}
					matrix[i][7] = rsSD
							.getString("sexsual_disease.createdatetime");
					i = i + 1;
				}
				tab_VenerealDisease.setModel(new DefaultTableModel(matrix,
						header) {
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						if (columnIndex > 1 && columnIndex < 7) {
							return Boolean.class;
						} else {
							return String.class;
						}
					}

					@Override
					public boolean isCellEditable(int row, int column) {
						if (column == 0) {
							return false;
						} else {
							return true;
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshHIV() {
		String sqlPateint = "SELECT case_manage.p_no FROM case_manage WHERE case_manage.guid='"
				+ caseGuid + "'";

		String sqlHIV = "SELECT * FROM HIV_assessment WHERE "
				+ "HIV_assessment.case_guid = '" + caseGuid + "' "
				+ "AND HIV_assessment.createdatetime = "
				+ "(SELECT MAX(HIV_assessment.createdatetime) FROM HIV_assessment WHERE HIV_assessment.case_guid = '" 
				+ caseGuid + "')";

		ResultSet rs = null;
		ResultSet rsPatient = null;

		try {
			rsPatient = DBC.executeQuery(sqlPateint);
			rsPatient.next();
			p_no = rsPatient.getString("case_manage.p_no");
			rs = DBC.executeQuery(sqlHIV);
			if (rs.next()) {
				if (rs.getString("HIV_assessment.Hypertension") != null
						&& ((String) rs
								.getString("HIV_assessment.Hypertension"))
								.equals("Y")) {
					chckbx_PHHD.setSelected(true);
				} else {
					chckbx_PHHD.setSelected(false);
				}
				if (rs.getString("HIV_assessment.BrainVessel") != null
						&& ((String) rs.getString("HIV_assessment.BrainVessel"))
								.equals("Y")) {
					chckbx_PHCD.setSelected(true);
				} else {
					chckbx_PHCD.setSelected(false);
				}
				if (rs.getString("HIV_assessment.Hyperlipidemia") != null
						&& ((String) rs
								.getString("HIV_assessment.Hyperlipidemia"))
								.equals("Y")) {
					chckbx_PHHC.setSelected(true);
				} else {
					chckbx_PHHC.setSelected(false);
				}
				if (rs.getString("HIV_assessment.Diabetes") != null
						&& ((String) rs.getString("HIV_assessment.Diabetes"))
								.equals("Y")) {
					chckbx_PHDM.setSelected(true);
				} else {
					chckbx_PHDM.setSelected(false);
				}
				if (rs.getString("HIV_assessment.HepatitisA") != null
						&& ((String) rs.getString("HIV_assessment.HepatitisA"))
								.equals("Y")) {
					chckbx_TypeA.setSelected(true);
				} else {
					chckbx_TypeA.setSelected(false);
				}
				if (rs.getString("HIV_assessment.HepatitisB") != null
						&& ((String) rs.getString("HIV_assessment.HepatitisB"))
								.equals("Y")) {
					chckbx_TypeB.setSelected(true);
				} else {
					chckbx_TypeB.setSelected(false);
				}
				if (rs.getString("HIV_assessment.HepatitisC") != null
						&& ((String) rs.getString("HIV_assessment.HepatitisC"))
								.equals("Y")) {
					chckbx_TypeC.setSelected(true);
				} else {
					chckbx_TypeC.setSelected(false);
				}
				if (rs.getString("HIV_assessment.Cancer") != null
						&& ((String) rs.getString("HIV_assessment.Cancer"))
								.equals("Y")) {
					chckbx_Malignancies.setSelected(true);
				} else {
					chckbx_Malignancies.setSelected(false);
				}
				if (rs.getString("HIV_assessment.HeartDisease") != null
						&& ((String) rs
								.getString("HIV_assessment.HeartDisease"))
								.equals("Y")) {
					chckbx_HD.setSelected(true);
				} else {
					chckbx_HD.setSelected(false);
				}
				if (rs.getString("HIV_assessment.NephroticSyndrome") != null
						&& ((String) rs
								.getString("HIV_assessment.NephroticSyndrome"))
								.equals("Y")) {
					chckbx_PHNS.setSelected(true);
				} else {
					chckbx_PHNS.setSelected(false);
				}
				if (rs.getString("HIV_assessment.Smoking") != null
						&& ((String) rs.getString("HIV_assessment.Smoking"))
								.equals("Y")) {
					chckbx_Smoke.setSelected(true);
				} else {
					chckbx_Smoke.setSelected(false);
				}
				if (rs.getString("HIV_assessment.Drinking") != null
						&& ((String) rs.getString("HIV_assessment.Drinking"))
								.equals("Y")) {
					chckbx_Alcoholism.setSelected(true);
				} else {
					chckbx_Alcoholism.setSelected(false);
				}
				if (rs.getString("HIV_assessment.OverWeight") != null
						&& ((String) rs.getString("HIV_assessment.OverWeight"))
								.equals("Y")) {
					chckbx_OW.setSelected(true);
				} else {
					chckbx_OW.setSelected(false);
				}
				if (rs.getString("HIV_assessment.OtherDiseaseHistory") != null) {
					txt_Other.setText((String) rs
							.getString("HIV_assessment.OtherDiseaseHistory"));
				}
				if (rs.getString("HIV_assessment.SelfHPL") != null
						&& ((String) rs.getString("HIV_assessment.SelfHPL"))
								.equals("Y")) {
					chckbx_IHHC.setSelected(true);
				} else {
					chckbx_IHHC.setSelected(false);
				}
				if (rs.getString("HIV_assessment.SelfDiabetes") != null
						&& ((String) rs
								.getString("HIV_assessment.SelfDiabetes"))
								.equals("Y")) {
					chckbx_IHDM.setSelected(true);
				} else {
					chckbx_IHDM.setSelected(false);
				}
				if (rs.getString("HIV_assessment.SelfHTN") != null
						&& ((String) rs.getString("HIV_assessment.SelfHTN"))
								.equals("Y")) {
					chckbx_IHEBP.setSelected(true);
				} else {
					chckbx_IHEBP.setSelected(false);
				}
				if (rs.getString("HIV_assessment.DirectHPL") != null
						&& ((String) rs.getString("HIV_assessment.DirectHPL"))
								.equals("Y")) {
					chckbx_FMHHC.setSelected(true);
				} else {
					chckbx_FMHHC.setSelected(false);
				}
				if (rs.getString("HIV_assessment.DirectDiabetes") != null
						&& ((String) rs
								.getString("HIV_assessment.DirectDiabetes"))
								.equals("Y")) {
					chckbx_FMHDM.setSelected(true);
				} else {
					chckbx_FMHDM.setSelected(false);
				}
				if (rs.getString("HIV_assessment.DirectHTN") != null
						&& ((String) rs.getString("HIV_assessment.DirectHTN"))
								.equals("Y")) {
					chckbx_FMHEBP.setSelected(true);
				} else {
					chckbx_FMHEBP.setSelected(false);
				}
				if (rs.getString("HIV_assessment.HasSTD") != null
						&& ((String) rs.getString("HIV_assessment.HasSTD"))
								.equals("Y")) {
					chckbx_WSVD.setSelected(true);
				} else {
					chckbx_WSVD.setSelected(false);
				}
				if (rs.getString("HIV_assessment.Has10ST") != null
						&& ((String) rs.getString("HIV_assessment.Has10ST"))
								.equals("Y")) {
					chckbx_10SP.setSelected(true);
				} else {
					chckbx_10SP.setSelected(false);
				}
				if (rs.getString("HIV_assessment.HasOTS") != null
						&& ((String) rs.getString("HIV_assessment.HasOTS"))
								.equals("Y")) {
					chckbx_HSIWS.setSelected(true);
				} else {
					chckbx_HSIWS.setSelected(false);
				}
				if (rs.getString("HIV_assessment.IDUAge") != null) {
					txt_IDUAge.setText((String) rs
							.getString("HIV_assessment.IDUAge"));
				}
				if (rs.getString("HIV_assessment.IDUDuration") != null) {
					txt_IDUDuration.setText((String) rs
							.getString("HIV_assessment.IDUDuration"));
				}
				if (rs.getString("HIV_assessment.UsedDrugs") != null
						&& ((String) rs.getString("HIV_assessment.UsedDrugs"))
								.equals("Y")) {
					chckbx_WTD.setSelected(true);
				} else {
					chckbx_WTD.setSelected(false);
				}
				if (rs.getString("HIV_assessment.UsedInjectionDrugs") != null
						&& ((String) rs
								.getString("HIV_assessment.UsedInjectionDrugs"))
								.equals("Y")) {
					chckbx_HUID.setSelected(true);
				} else {
					chckbx_HUID.setSelected(false);
				}
				if (rs.getString("HIV_assessment.SharingNeedle") != null
						&& ((String) rs
								.getString("HIV_assessment.SharingNeedle"))
								.equals("Y")) {
					chckbx_SN.setSelected(true);
				} else {
					chckbx_SN.setSelected(false);
				}
				if (rs.getString("HIV_assessment.SharingWater") != null
						&& ((String) rs
								.getString("HIV_assessment.SharingWater"))
								.equals("Y")) {
					chckbx_SW.setSelected(true);
				} else {
					chckbx_SW.setSelected(false);
				}
				if (rs.getString("HIV_assessment.SharingNeedle") != null
						&& rs.getString("HIV_assessment.SharingWater") != null
						&& ((String) rs
								.getString("HIV_assessment.SharingNeedle"))
								.equals("Y")
						&& ((String) rs
								.getString("HIV_assessment.SharingWater"))
								.equals("Y")) {
					chckbx_SNW.setSelected(true);
				} else {
					chckbx_SNW.setSelected(false);
				}
				if (rs.getString("HIV_assessment.UsedMethadone") != null
						&& ((String) rs
								.getString("HIV_assessment.UsedMethadone"))
								.equals("Y")) {
					chckbx_Methadone.setSelected(true);
				} else {
					chckbx_Methadone.setSelected(false);
				}
				if (rs.getString("HIV_assessment.FirstMethadone") != null) {
					txt_StartMethadone.setText((String) rs
							.getString("HIV_assessment.FirstMethadone"));
				}
				if (rs.getString("HIV_assessment.MethadoneStatus") != null
						&& ((String) rs
								.getString("HIV_assessment.MethadoneStatus"))
								.equals("U")) {
					cbb_StaticsMethadone.setSelectedItem(0);
				} else {
					cbb_StaticsMethadone.setSelectedItem(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
				DBC.closeConnection(rsPatient);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeVaccine(Connection conn) throws Exception {
		int i = 0;
		if (tab_Vaccine.getColumnCount() > 1) {
			for (i = 0; i < tab_Vaccine.getRowCount(); i++) {
				String sql = "UPDATE vaccine_history SET ";
				if ((Boolean) tab_Vaccine.getValueAt(i, 1) == true) {
					sql = sql + "vaccine_history.HPVExam = 'Y' ";
				} else {
					sql = sql + "vaccine_history.HPVExam = 'N' ";
				}
				if (tab_Vaccine.getValueAt(i, 2) != null
						&& !tab_Vaccine.getValueAt(i, 2).equals("")) {
					sql = sql + ",vaccine_history.HPVExamDate = '"
							+ tab_Vaccine.getValueAt(i, 2) + "' ";
				}
				if (tab_Vaccine.getValueAt(i, 3) != null
						&& !tab_Vaccine.getValueAt(i, 3).equals("")) {
					sql = sql + ",vaccine_history.VaccineType = '"
							+ tab_Vaccine.getValueAt(i, 3) + "' ";
				}
				if (tab_Vaccine.getValueAt(i, 4) != null
						&& !tab_Vaccine.getValueAt(i, 4).equals("")) {
					sql = sql + ",vaccine_history.InjectionDate = '"
							+ tab_Vaccine.getValueAt(i, 4) + "' ";
				}
				sql = sql + "WHERE vaccine_history.guid = '"
						+ tab_Vaccine.getValueAt(i, 0) + "'";
				System.out.print(sql);
				PreparedStatement ps = conn.prepareStatement(sql);
				try {
					ps.executeUpdate();
				} catch (Exception e) {
					throw e;
				} finally {
					if (ps != null)
						ps.close();
				}
			}
		}
	}

	private void writeSD(Connection conn) throws Exception {
		int i = 0;
		if (tab_VenerealDisease.getColumnCount() > 1) {
			for (i = 0; i < tab_VenerealDisease.getRowCount(); i++) {
				String sql = "UPDATE sexsual_disease SET ";
				if (tab_VenerealDisease.getValueAt(i, 1) != null
						&& !tab_VenerealDisease.getValueAt(i, 1).equals("")) {
					sql = sql + "sexsual_disease.happenDate = '"
							+ tab_VenerealDisease.getValueAt(i, 1) + "', ";
				}
				if ((Boolean) tab_VenerealDisease.getValueAt(i, 2) == true) {
					sql = sql + "sexsual_disease.Syphilis = 'Y', ";
				} else {
					sql = sql + "sexsual_disease.Syphilis = 'N', ";
				}
				if ((Boolean) tab_VenerealDisease.getValueAt(i, 3) == true) {
					sql = sql + "sexsual_disease.Gonorrhea = 'Y', ";
				} else {
					sql = sql + "sexsual_disease.Gonorrhea = 'N', ";
				}
				if ((Boolean) tab_VenerealDisease.getValueAt(i, 4) == true) {
					sql = sql
							+ "sexsual_disease.NongonococcalUrethritis = 'Y', ";
				} else {
					sql = sql
							+ "sexsual_disease.NongonococcalUrethritis = 'N', ";
				}
				if ((Boolean) tab_VenerealDisease.getValueAt(i, 5) == true) {
					sql = sql + "sexsual_disease.wart = 'Y', ";
				} else {
					sql = sql + "sexsual_disease.wart = 'N', ";
				}
				if ((Boolean) tab_VenerealDisease.getValueAt(i, 6) == true) {
					sql = sql + "sexsual_disease.Amebiasis = 'Y' ";
				} else {
					sql = sql + "sexsual_disease.Amebiasis = 'N' ";
				}
				if (tab_VenerealDisease.getValueAt(i, 7) != null
						&& !tab_VenerealDisease.getValueAt(i, 7).equals("")) {
					sql = sql + ",sexsual_disease.createdatetime = '"
							+ tab_VenerealDisease.getValueAt(i, 7) + "' ";
				}
				sql = sql + "WHERE sexsual_disease.guid = '"
						+ tab_VenerealDisease.getValueAt(i, 0) + "'";
				System.out.print(sql);
				PreparedStatement ps = conn.prepareStatement(sql);
				try {
					ps.executeUpdate();
				} catch (Exception e) {
					throw e;
				} finally {
					if (ps != null)
						ps.close();
				}
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
			writeVaccine(conn);
			writeSD(conn);
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	@Override
	public void save(Connection conn) throws Exception {
		String newUUID = UUID.randomUUID().toString();
	
		String sql = "INSERT into HIV_assessment SELECT '"
				+ newUUID + "', '" + caseGuid + "', '" + p_no + "',";
		if (chckbx_PHHD.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_PHCD.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_PHHC.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_PHDM.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_TypeA.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_TypeB.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_TypeC.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_Malignancies.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_HD.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_PHNS.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_Smoke.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_Alcoholism.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_OW.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}

		sql = sql + " '" + txt_Other.getText() + "', ";

		if (chckbx_IHHC.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_IHDM.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_IHEBP.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + "  'N', ";
		}
		if (chckbx_FMHHC.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_FMHDM.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_FMHEBP.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_WSVD.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_10SP.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_HSIWS.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}

		sql = sql + " '" + txt_IDUAge.getText() + "', ";
		sql = sql + " '" + txt_IDUDuration.getText() + "', ";

		if (chckbx_WTD.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_HUID.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_SN.isSelected() || chckbx_SNW.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_SW.isSelected() || chckbx_SNW.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (chckbx_Methadone.isSelected()) {
			sql = sql + " 'Y', ";
		} else {
			sql = sql + " 'N', ";
		}
		if (!txt_StartMethadone.getText().equals("")) {
			sql = sql + " '" + txt_StartMethadone.getText() + "', ";
		}
		else{
			sql = sql + " NULL, ";
		}
		
		if (cbb_StaticsMethadone.getSelectedIndex() == 0) {
			sql = sql + " 'U', ";
		} else {
			sql = sql + " 'Q', ";
		}

		sql = sql + "NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, ";
		
		sql = sql + "now(), '" + UserInfo.getUserNO() + "' ";

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

		// TODO :
		// btn_Save.setEnabled(false);

	}

	private static Logger logger = LogManager.getLogger(Tab_HIVCase.class
			.getName());
}
