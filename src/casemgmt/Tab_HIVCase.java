package casemgmt;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import multilingual.Language;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Tab_HIVCase extends JPanel {
	private Frm_Case parent;
	private static final long serialVersionUID = 1L;
	private static final Language lang = Language.getInstance();

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

	private JButton btn_Save;

	private String caseGuid;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_HIVCase(String caseGuid) {
		this.caseGuid = caseGuid;
		setLayout(null);

		lbl_Risk = new JLabel("Risk:");
		lbl_Risk.setBounds(10, 10, 60, 15);
		add(lbl_Risk);

		pan_Risk = new JPanel();
		pan_Risk.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_Risk.setBounds(10, 35, 280, 273);
		add(pan_Risk);
		pan_Risk.setLayout(null);

		chckbx_PHHD = new JCheckBox(lang.getString("PHHD"));
		chckbx_PHHD.setBounds(6, 6, 144, 23);
		pan_Risk.add(chckbx_PHHD);

		chckbx_PHCD = new JCheckBox(lang.getString("PHCD"));
		chckbx_PHCD.setBounds(6, 29, 144, 23);
		pan_Risk.add(chckbx_PHCD);

		chckbx_PHHC = new JCheckBox(lang.getString("PHHC"));
		chckbx_PHHC.setBounds(6, 54, 144, 23);
		pan_Risk.add(chckbx_PHHC);

		chckbx_PHDM = new JCheckBox(lang.getString("PHDM"));
		chckbx_PHDM.setBounds(6, 79, 144, 23);
		pan_Risk.add(chckbx_PHDM);

		chckbx_TypeA = new JCheckBox(lang.getString("PHTYPEA"));
		chckbx_TypeA.setBounds(6, 104, 144, 23);
		pan_Risk.add(chckbx_TypeA);

		chckbx_TypeB = new JCheckBox(lang.getString("PHTYPEB"));
		chckbx_TypeB.setBounds(6, 129, 144, 23);
		pan_Risk.add(chckbx_TypeB);

		chckbx_TypeC = new JCheckBox(lang.getString("PHTYPEC"));
		chckbx_TypeC.setBounds(6, 154, 144, 23);
		pan_Risk.add(chckbx_TypeC);

		chckbx_Malignancies = new JCheckBox(lang.getString("PHMALIG"));
		chckbx_Malignancies.setBounds(152, 6, 97, 23);
		pan_Risk.add(chckbx_Malignancies);

		chckbx_HD = new JCheckBox(lang.getString("HD"));
		chckbx_HD.setBounds(152, 29, 97, 23);
		pan_Risk.add(chckbx_HD);

		chckbx_PHNS = new JCheckBox(lang.getString("PHNS"));
		chckbx_PHNS.setBounds(152, 54, 122, 23);
		pan_Risk.add(chckbx_PHNS);

		chckbx_Smoke = new JCheckBox(lang.getString("PHSMOKE"));
		chckbx_Smoke.setBounds(152, 79, 122, 23);
		pan_Risk.add(chckbx_Smoke);

		chckbx_Alcoholism = new JCheckBox(lang.getString("PHALCOHOLISM"));
		chckbx_Alcoholism.setBounds(152, 104, 122, 23);
		pan_Risk.add(chckbx_Alcoholism);

		chckbx_OW = new JCheckBox(lang.getString("PHOW"));
		chckbx_OW.setBounds(152, 129, 122, 23);
		pan_Risk.add(chckbx_OW);

		lbl_Other = new JLabel(lang.getString("PHOTHER"));
		lbl_Other.setBounds(6, 183, 46, 15);
		pan_Risk.add(lbl_Other);

		txt_Other = new JTextField();
		txt_Other.setBounds(62, 180, 187, 21);
		pan_Risk.add(txt_Other);
		txt_Other.setColumns(10);

		lbl_HBP = new JLabel("High Blood Pressure:");
		lbl_HBP.setBounds(300, 10, 104, 15);
		add(lbl_HBP);

		pan_HBP = new JPanel();
		pan_HBP.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_HBP.setBounds(300, 35, 380, 165);
		add(pan_HBP);
		pan_HBP.setLayout(null);

		chckbx_IHHC = new JCheckBox(lang.getString("IHHC"));
		chckbx_IHHC.setBounds(6, 6, 368, 23);
		pan_HBP.add(chckbx_IHHC);

		chckbx_IHDM = new JCheckBox(lang.getString("IHDM"));
		chckbx_IHDM.setBounds(6, 31, 368, 23);
		pan_HBP.add(chckbx_IHDM);

		chckbx_IHEBP = new JCheckBox(lang.getString("IHEBP"));
		chckbx_IHEBP.setBounds(6, 56, 368, 23);
		pan_HBP.add(chckbx_IHEBP);

		chckbx_FMHHC = new JCheckBox(lang.getString("FMHHC"));
		chckbx_FMHHC.setBounds(6, 81, 368, 23);
		pan_HBP.add(chckbx_FMHHC);

		chckbx_FMHDM = new JCheckBox(lang.getString("FMHDM"));
		chckbx_FMHDM.setBounds(6, 106, 368, 23);
		pan_HBP.add(chckbx_FMHDM);

		chckbx_FMHEBP = new JCheckBox(lang.getString("FMHEBP"));
		chckbx_FMHEBP.setBounds(6, 131, 368, 23);
		pan_HBP.add(chckbx_FMHEBP);

		lbl_SB = new JLabel("Sexual Behavior:");
		lbl_SB.setBounds(300, 210, 83, 15);
		add(lbl_SB);

		pan_SB = new JPanel();
		pan_SB.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_SB.setBounds(300, 235, 380, 73);
		add(pan_SB);
		pan_SB.setLayout(null);

		chckbx_WSVD = new JCheckBox(lang.getString("WSVD"));
		chckbx_WSVD.setBounds(6, 6, 368, 23);
		pan_SB.add(chckbx_WSVD);

		chckbx_10SP = new JCheckBox(lang.getString("10SP"));
		chckbx_10SP.setBounds(6, 25, 368, 23);
		pan_SB.add(chckbx_10SP);

		chckbx_HSIWS = new JCheckBox(lang.getString("HSIWS"));
		chckbx_HSIWS.setBounds(6, 44, 368, 23);
		pan_SB.add(chckbx_HSIWS);

		lbl_IDU = new JLabel("IDU:");
		lbl_IDU.setBounds(690, 10, 60, 15);
		add(lbl_IDU);

		pan_IDU = new JPanel();
		pan_IDU.setBorder(new LineBorder(new Color(0, 0, 0)));
		pan_IDU.setBounds(690, 35, 258, 273);
		add(pan_IDU);
		pan_IDU.setLayout(null);

		lbl_IDUAge = new JLabel(lang.getString("IDUAGE"));
		lbl_IDUAge.setBounds(6, 13, 139, 15);
		pan_IDU.add(lbl_IDUAge);

		txt_IDUAge = new JTextField();
		txt_IDUAge.setBounds(155, 10, 97, 21);
		pan_IDU.add(txt_IDUAge);
		txt_IDUAge.setColumns(10);

		lbl_IDUDuration = new JLabel(lang.getString("IDUDURATION"));
		lbl_IDUDuration.setBounds(6, 44, 139, 15);
		pan_IDU.add(lbl_IDUDuration);

		txt_IDUDuration = new JTextField();
		txt_IDUDuration.setBounds(156, 41, 96, 21);
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
		lbl_StartMethadone.setBounds(6, 224, 140, 15);
		pan_IDU.add(lbl_StartMethadone);

		txt_StartMethadone = new JTextField();
		txt_StartMethadone.setBounds(156, 221, 96, 21);
		pan_IDU.add(txt_StartMethadone);
		txt_StartMethadone.setColumns(10);

		lbl_StaticsMethadone = new JLabel(lang.getString("STATICSMETHADONE"));
		lbl_StaticsMethadone.setBounds(6, 249, 139, 15);
		pan_IDU.add(lbl_StaticsMethadone);

		cbb_StaticsMethadone = new JComboBox();
		cbb_StaticsMethadone.setBounds(156, 246, 96, 21);
		pan_IDU.add(cbb_StaticsMethadone);
		cbb_StaticsMethadone.removeAllItems();
		this.cbb_StaticsMethadone.addItem("Q:Success");
		this.cbb_StaticsMethadone.addItem("U:Fail");

		lbl_Vaccine = new JLabel("Vaccine:");
		lbl_Vaccine.setBounds(10, 318, 60, 15);
		add(lbl_Vaccine);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 343, 841, 107);
		add(scrollPane);

		tab_Vaccine = new JTable();
		scrollPane.setViewportView(tab_Vaccine);

		lbl_VenerealDisease = new JLabel("Venereal Disease:");
		lbl_VenerealDisease.setBounds(10, 460, 93, 15);
		add(lbl_VenerealDisease);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 485, 841, 112);
		add(scrollPane_1);

		tab_VenerealDisease = new JTable();
		scrollPane_1.setViewportView(tab_VenerealDisease);

		btn_Save = new JButton(lang.getString("SAVE"));
		btn_Save.setBounds(861, 574, 87, 23);
		add(btn_Save);
		btn_Save.setEnabled(true);
		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_SaveactionPerformed(evt);
			}
		});

		btn_AddVaccine = new JButton("New button");
		btn_AddVaccine.setBounds(57, 314, 87, 23);
		add(btn_AddVaccine);
		btn_AddVaccine.setEnabled(true);
		btn_AddVaccine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_AddVaccineactionPerformed(evt);
			}
		});

		btn_AddVD = new JButton("New button");
		btn_AddVD.setBounds(113, 456, 87, 23);
		add(btn_AddVD);
		btn_AddVD.setEnabled(true);
		btn_AddVD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_AddVDactionPerformed(evt);
			}
		});
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
		String sql = "UPDATE HIV_assessment SET";
		if (chckbx_PHHD.isSelected()) {
			sql = sql + " HIV_assessment.Hypertension = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.Hypertension = 'N', ";
		}
		if (chckbx_PHCD.isSelected()) {
			sql = sql + " HIV_assessment.BrainVessel = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.BrainVessel = 'N', ";
		}
		if (chckbx_PHHC.isSelected()) {
			sql = sql + " HIV_assessment.Hyperlipidemia = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.Hyperlipidemia = 'N', ";
		}
		if (chckbx_PHDM.isSelected()) {
			sql = sql + " HIV_assessment.Diabetes = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.Diabetes = 'N', ";
		}
		if (chckbx_TypeA.isSelected()) {
			sql = sql + " HIV_assessment.HepatitisA = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.HepatitisA = 'N', ";
		}
		if (chckbx_TypeB.isSelected()) {
			sql = sql + " HIV_assessment.HepatitisB = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.HepatitisB = 'N', ";
		}
		if (chckbx_TypeC.isSelected()) {
			sql = sql + " HIV_assessment.HepatitisC = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.HepatitisC = 'N', ";
		}
		if (chckbx_Malignancies.isSelected()) {
			sql = sql + " HIV_assessment.Cancer = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.Cancer = 'N', ";
		}
		if (chckbx_HD.isSelected()) {
			sql = sql + " HIV_assessment.HeartDisease = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.HeartDisease = 'N', ";
		}
		if (chckbx_PHNS.isSelected()) {
			sql = sql + " HIV_assessment.NephroticSyndrome = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.NephroticSyndrome = 'N', ";
		}
		if (chckbx_Smoke.isSelected()) {
			sql = sql + " HIV_assessment.Smoking = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.Smoking = 'N', ";
		}
		if (chckbx_Alcoholism.isSelected()) {
			sql = sql + " HIV_assessment.Drinking = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.Drinking = 'N', ";
		}
		if (chckbx_OW.isSelected()) {
			sql = sql + " HIV_assessment.OverWeight = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.OverWeight = 'N', ";
		}

		sql = sql + " HIV_assessment.OtherDiseaseHistory = '"
				+ txt_Other.getText() + "', ";

		if (chckbx_IHHC.isSelected()) {
			sql = sql + " HIV_assessment.SelfHPL = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.SelfHPL = 'N', ";
		}
		if (chckbx_IHDM.isSelected()) {
			sql = sql + " HIV_assessment.SelfDiabetes = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.SelfDiabetes = 'N', ";
		}
		if (chckbx_IHEBP.isSelected()) {
			sql = sql + " HIV_assessment.SelfHTN = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.SelfHTN = 'N', ";
		}
		if (chckbx_FMHHC.isSelected()) {
			sql = sql + " HIV_assessment.DirectHPL = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.DirectHPL = 'N', ";
		}
		if (chckbx_FMHDM.isSelected()) {
			sql = sql + " HIV_assessment.DirectDiabetes = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.DirectDiabetes = 'N', ";
		}
		if (chckbx_FMHEBP.isSelected()) {
			sql = sql + " HIV_assessment.DirectHTN = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.DirectHTN = 'N', ";
		}
		if (chckbx_WSVD.isSelected()) {
			sql = sql + " HIV_assessment.HasSTD = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.HasSTD = 'N', ";
		}
		if (chckbx_10SP.isSelected()) {
			sql = sql + " HIV_assessment.Has10ST = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.Has10ST = 'N', ";
		}
		if (chckbx_HSIWS.isSelected()) {
			sql = sql + " HIV_assessment.HasOTS = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.HasOTS = 'N', ";
		}

		sql = sql + " HIV_assessment.IDUAge = '" + txt_IDUAge.getText() + "', ";
		sql = sql + " HIV_assessment.IDUDuration = '"
				+ txt_IDUDuration.getText() + "', ";

		if (chckbx_WTD.isSelected()) {
			sql = sql + " HIV_assessment.UsedDrugs = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.UsedDrugs = 'N', ";
		}
		if (chckbx_HUID.isSelected()) {
			sql = sql + " HIV_assessment.UsedInjectionDrugs = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.UsedInjectionDrugs = 'N', ";
		}
		if (chckbx_SN.isSelected() || chckbx_SNW.isSelected()) {
			sql = sql + " HIV_assessment.SharingNeedle = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.SharingNeedle = 'N', ";
		}
		if (chckbx_SW.isSelected() || chckbx_SNW.isSelected()) {
			sql = sql + " HIV_assessment.SharingWater = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.SharingWater = 'N', ";
		}
		if (chckbx_Methadone.isSelected()) {
			sql = sql + " HIV_assessment.UsedMethadone = 'Y', ";
		} else {
			sql = sql + " HIV_assessment.UsedMethadone = 'N', ";
		}

		sql = sql + " HIV_assessment.FirstMethadone = '"
				+ txt_StartMethadone.getText() + "', ";

		if (cbb_StaticsMethadone.getSelectedIndex() == 0) {
			sql = sql + " HIV_assessment.MethadoneStatus = 'U', ";
		} else {
			sql = sql + " HIV_assessment.MethadoneStatus = 'Q', ";
		}

		sql = sql + " HIV_assessment.s_no = '" + UserInfo.getUserNO() + "' ";

		sql = sql + "WHERE HIV_assessment.case_guid = '" + caseGuid + "'";

		try {
			DBC.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
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
				tab_VenerealDisease.setModel(new DefaultTableModel(matrix, header) {
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
				+ "HIV_assessment.case_guid = '" + caseGuid + "'";

		ResultSet rs = null;
		ResultSet rsPatient = null;

		try {
			rsPatient = DBC.executeQuery(sqlPateint);
			rsPatient.next();
			String p_no = rsPatient.getString("case_manage.p_no");
			rs = DBC.executeQuery(sqlHIV);
			if (!rs.next()) {
				String newUUID = UUID.randomUUID().toString();
				String sqlInsert = "INSERT into HIV_assessment SELECT '"
						+ newUUID + "', '" + caseGuid + "', '" + p_no + "' ";
				int i = 0;
				for (i = 0; i < 44; i++) {
					sqlInsert = sqlInsert + ", NULL";
				}
				System.out.print(sqlInsert + "\n");
				DBC.executeUpdate(sqlInsert);
			} else {
				if (((String) rs.getString("HIV_assessment.Hypertension"))
						.equals("Y")) {
					chckbx_PHHD.setSelected(true);
				} else {
					chckbx_PHHD.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.BrainVessel"))
						.equals("Y")) {
					chckbx_PHCD.setSelected(true);
				} else {
					chckbx_PHCD.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.Hyperlipidemia"))
						.equals("Y")) {
					chckbx_PHHC.setSelected(true);
				} else {
					chckbx_PHHC.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.Diabetes"))
						.equals("Y")) {
					chckbx_PHDM.setSelected(true);
				} else {
					chckbx_PHDM.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.HepatitisA"))
						.equals("Y")) {
					chckbx_TypeA.setSelected(true);
				} else {
					chckbx_TypeA.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.HepatitisB"))
						.equals("Y")) {
					chckbx_TypeB.setSelected(true);
				} else {
					chckbx_TypeB.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.HepatitisC"))
						.equals("Y")) {
					chckbx_TypeC.setSelected(true);
				} else {
					chckbx_TypeC.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.Cancer"))
						.equals("Y")) {
					chckbx_Malignancies.setSelected(true);
				} else {
					chckbx_Malignancies.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.HeartDisease"))
						.equals("Y")) {
					chckbx_HD.setSelected(true);
				} else {
					chckbx_HD.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.NephroticSyndrome"))
						.equals("Y")) {
					chckbx_PHNS.setSelected(true);
				} else {
					chckbx_PHNS.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.Smoking"))
						.equals("Y")) {
					chckbx_Smoke.setSelected(true);
				} else {
					chckbx_Smoke.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.Drinking"))
						.equals("Y")) {
					chckbx_Alcoholism.setSelected(true);
				} else {
					chckbx_Alcoholism.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.OverWeight"))
						.equals("Y")) {
					chckbx_OW.setSelected(true);
				} else {
					chckbx_OW.setSelected(false);
				}
				txt_Other.setText((String) rs
						.getString("HIV_assessment.OtherDiseaseHistory"));
				if (((String) rs.getString("HIV_assessment.SelfHPL"))
						.equals("Y")) {
					chckbx_IHHC.setSelected(true);
				} else {
					chckbx_IHHC.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.SelfDiabetes"))
						.equals("Y")) {
					chckbx_IHDM.setSelected(true);
				} else {
					chckbx_IHDM.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.SelfHTN"))
						.equals("Y")) {
					chckbx_IHEBP.setSelected(true);
				} else {
					chckbx_IHEBP.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.DirectHPL"))
						.equals("Y")) {
					chckbx_FMHHC.setSelected(true);
				} else {
					chckbx_FMHHC.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.DirectDiabetes"))
						.equals("Y")) {
					chckbx_FMHDM.setSelected(true);
				} else {
					chckbx_FMHDM.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.DirectHTN"))
						.equals("Y")) {
					chckbx_FMHEBP.setSelected(true);
				} else {
					chckbx_FMHEBP.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.HasSTD"))
						.equals("Y")) {
					chckbx_WSVD.setSelected(true);
				} else {
					chckbx_WSVD.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.Has10ST"))
						.equals("Y")) {
					chckbx_10SP.setSelected(true);
				} else {
					chckbx_10SP.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.HasOTS"))
						.equals("Y")) {
					chckbx_HSIWS.setSelected(true);
				} else {
					chckbx_HSIWS.setSelected(false);
				}
				txt_IDUAge.setText((String) rs
						.getString("HIV_assessment.IDUAge"));
				txt_IDUDuration.setText((String) rs
						.getString("HIV_assessment.IDUDuration"));
				if (((String) rs.getString("HIV_assessment.UsedDrugs"))
						.equals("Y")) {
					chckbx_WTD.setSelected(true);
				} else {
					chckbx_WTD.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.UsedInjectionDrugs"))
						.equals("Y")) {
					chckbx_HUID.setSelected(true);
				} else {
					chckbx_HUID.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.SharingNeedle"))
						.equals("Y")) {
					chckbx_SN.setSelected(true);
				} else {
					chckbx_SN.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.SharingWater"))
						.equals("Y")) {
					chckbx_SW.setSelected(true);
				} else {
					chckbx_SW.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.SharingNeedle"))
						.equals("Y")
						&& ((String) rs
								.getString("HIV_assessment.SharingWater"))
								.equals("Y")) {
					chckbx_SNW.setSelected(true);
				} else {
					chckbx_SNW.setSelected(false);
				}
				if (((String) rs.getString("HIV_assessment.UsedMethadone"))
						.equals("Y")) {
					chckbx_Methadone.setSelected(true);
				} else {
					chckbx_Methadone.setSelected(false);
				}
				txt_StartMethadone.setText((String) rs
						.getString("HIV_assessment.FirstMethadone"));
				if (((String) rs.getString("HIV_assessment.MethadoneStatus"))
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
	private void writeVaccine(){
	}
}
