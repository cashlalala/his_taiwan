package registration;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import org.his.bind.PatientsInfoJPATable;
import org.his.dao.PatientsInfoDao;
import org.his.model.PatientsInfo;

import cc.johnwu.date.DateInterface;
import cc.johnwu.date.DateMethod;
import cc.johnwu.finger.FingerPrintViewerInterface;
import cc.johnwu.sql.DBC;
import patients.Frm_PatientMod;
import patients.PatientsInterface;
import multilingual.Language;
import system.Setting;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Frm_RegAndInpatient extends JFrame implements
		FingerPrintViewerInterface, DateInterface, PatientsInterface {

	private JPanel pan_WholeFrame;

	// GUI for left top patient info
	private JPanel pan_PatientInfo;
	private JLabel lbl_PatientNo;
	private JLabel lbl_NHISNo;
	private JLabel lbl_NIANo;
	private JLabel lbl_FirstName;
	private JLabel lbl_Last_Name;
	private JLabel lbl_Birthday;
	private JLabel lbl_Age;
	private JLabel lbl_Gender;
	private JLabel lbl_BloodType;
	private JLabel lbl_Height;
	private JLabel lbl_Weight;
	private JPanel pan_Code;
	private JButton btn_AddPatient;
	// GUI for right top patient search
	private JButton btn_PatientSearch;
	private JTextField txt_PatientSearch;
	private JScrollPane scrollPane_Patient;
	private JTable tab_PatientList;

	// bottom half tabbed panel
	private JTabbedPane pan_ClinicOrInpatient;
	// GUI for Clinic tab
	private JPanel pan_tabClinicInfo;
	private JPanel pan_ClinicInfo;
	private JLabel lbl_RegistrationMethod;
	private JLabel lbl_Date;
	private JComboBox cbb_Date;
	private JLabel lbl_Division;
	private JComboBox cbb_Division;
	private JLabel lbl_Shift;
	private JComboBox cbb_Shift;
	private JLabel lbl_Doctor;
	private JLabel lbl_Clinic;
	private JLabel lbl_WaitingNo;
	private JButton btn_ClinicSave;
	private JButton btn_ClinicClose;
	private JScrollPane scrollPane_Clinic;
	private JTable tab_ClinicList;
	// GUI for Inpatient tab
	private JPanel pan_tabInpatientInfo;
	private JPanel pan_InpatientInfo;
	private JLabel lbl_InpatientDivision;
	private JComboBox cbb_InpatientDivision;
	private JLabel lbl_InpatientDoctor;
	private JComboBox cbb_InpatientDoctor;
	private JLabel lbl_CheckInDate;
	private JComboBox cbb_CheckInDate;
	private JLabel lbl_CheckOutDate;
	private JComboBox cbb_CheckOutDate;
	private JButton btn_InpatientSave;
	private JButton btn_InpatientClose;
	private JScrollPane scrollPane_Bed;
	private JTable tab_BedList;

	private Language paragraph = Language.getInstance();
	private Setting set = new Setting();
	private String[] lineSet = set.setSystem("GIS").split("\n");

	private PatientsInfoDao patientsInfoDao = new PatientsInfoDao();
	private List<PatientsInfo> patientsInfo;
	private EntityTransaction etx;

	boolean dead = true;
	
	/**
	 * Create the frame.
	 */
	public Frm_RegAndInpatient() {
		String[][] matrix = { { "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" },
				{ "aaa", "bbb", "ccc" }, { "aaa", "bbb", "ccc" } };
		String[] header = { "111", "222", "333" };
		// Init GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 705);

		pan_WholeFrame = new JPanel();
		pan_WholeFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pan_WholeFrame);
		GridBagLayout gbl_pan_WholeFrame = new GridBagLayout();
		gbl_pan_WholeFrame.columnWidths = new int[] { 235, 251, 146, 0 };
		gbl_pan_WholeFrame.rowHeights = new int[] { 36, 253, 355, 0 };
		gbl_pan_WholeFrame.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_WholeFrame.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		pan_WholeFrame.setLayout(gbl_pan_WholeFrame);

		pan_PatientInfo = new JPanel();
		GridBagConstraints gbc_pan_PatientInfo = new GridBagConstraints();
		gbc_pan_PatientInfo.weightx = 0.4;
		gbc_pan_PatientInfo.weighty = 0.7;
		gbc_pan_PatientInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_PatientInfo.insets = new Insets(0, 0, 5, 5);
		gbc_pan_PatientInfo.gridheight = 2;
		gbc_pan_PatientInfo.gridx = 0;
		gbc_pan_PatientInfo.gridy = 0;
		pan_WholeFrame.add(pan_PatientInfo, gbc_pan_PatientInfo);
		GridBagLayout gbl_pan_PatientInfo = new GridBagLayout();
		gbl_pan_PatientInfo.columnWidths = new int[] { 139, 93, 0 };
		gbl_pan_PatientInfo.rowHeights = new int[] { 13, 13, 13, 13, 13, 13,
				13, 13, 13, 13, 13, 52, 0 };
		gbl_pan_PatientInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_PatientInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pan_PatientInfo.setLayout(gbl_pan_PatientInfo);

		lbl_PatientNo = new JLabel(paragraph.getString("PATIENTNO"));
		GridBagConstraints gbc_lbl_PatientNo = new GridBagConstraints();
		gbc_lbl_PatientNo.weighty = 0.05;
		gbc_lbl_PatientNo.weightx = 1.0;
		gbc_lbl_PatientNo.fill = GridBagConstraints.BOTH;
		gbc_lbl_PatientNo.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_PatientNo.gridwidth = 2;
		gbc_lbl_PatientNo.gridx = 0;
		gbc_lbl_PatientNo.gridy = 0;
		pan_PatientInfo.add(lbl_PatientNo, gbc_lbl_PatientNo);

		lbl_NHISNo = new JLabel(paragraph.getString("TITLENHISNO"));
		GridBagConstraints gbc_lbl_NHISNo = new GridBagConstraints();
		gbc_lbl_NHISNo.weighty = 0.05;
		gbc_lbl_NHISNo.weightx = 1.0;
		gbc_lbl_NHISNo.fill = GridBagConstraints.BOTH;
		gbc_lbl_NHISNo.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_NHISNo.gridwidth = 2;
		gbc_lbl_NHISNo.gridx = 0;
		gbc_lbl_NHISNo.gridy = 1;
		pan_PatientInfo.add(lbl_NHISNo, gbc_lbl_NHISNo);

		lbl_NIANo = new JLabel(paragraph.getString("TITLENIANO"));
		GridBagConstraints gbc_lbl_NIANo = new GridBagConstraints();
		gbc_lbl_NIANo.weighty = 0.05;
		gbc_lbl_NIANo.weightx = 1.0;
		gbc_lbl_NIANo.fill = GridBagConstraints.BOTH;
		gbc_lbl_NIANo.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_NIANo.gridwidth = 2;
		gbc_lbl_NIANo.gridx = 0;
		gbc_lbl_NIANo.gridy = 2;
		pan_PatientInfo.add(lbl_NIANo, gbc_lbl_NIANo);

		lbl_FirstName = new JLabel(paragraph.getString("TITLEFIRSTNAME"));
		GridBagConstraints gbc_lbl_FirstName = new GridBagConstraints();
		gbc_lbl_FirstName.weighty = 0.05;
		gbc_lbl_FirstName.weightx = 1.0;
		gbc_lbl_FirstName.fill = GridBagConstraints.BOTH;
		gbc_lbl_FirstName.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_FirstName.gridwidth = 2;
		gbc_lbl_FirstName.gridx = 0;
		gbc_lbl_FirstName.gridy = 3;
		pan_PatientInfo.add(lbl_FirstName, gbc_lbl_FirstName);

		lbl_Last_Name = new JLabel(paragraph.getString("TITLELASTNAME"));
		GridBagConstraints gbc_lbl_Last_Name = new GridBagConstraints();
		gbc_lbl_Last_Name.weighty = 0.05;
		gbc_lbl_Last_Name.weightx = 1.0;
		gbc_lbl_Last_Name.fill = GridBagConstraints.BOTH;
		gbc_lbl_Last_Name.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Last_Name.gridwidth = 2;
		gbc_lbl_Last_Name.gridx = 0;
		gbc_lbl_Last_Name.gridy = 4;
		pan_PatientInfo.add(lbl_Last_Name, gbc_lbl_Last_Name);

		lbl_Birthday = new JLabel(paragraph.getString("TITLEBIRTHDAY"));
		GridBagConstraints gbc_lbl_Birthday = new GridBagConstraints();
		gbc_lbl_Birthday.weighty = 0.05;
		gbc_lbl_Birthday.weightx = 1.0;
		gbc_lbl_Birthday.fill = GridBagConstraints.BOTH;
		gbc_lbl_Birthday.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Birthday.gridwidth = 2;
		gbc_lbl_Birthday.gridx = 0;
		gbc_lbl_Birthday.gridy = 5;
		pan_PatientInfo.add(lbl_Birthday, gbc_lbl_Birthday);

		lbl_Age = new JLabel(paragraph.getString("TITLEAGE"));
		GridBagConstraints gbc_lbl_Age = new GridBagConstraints();
		gbc_lbl_Age.weighty = 0.05;
		gbc_lbl_Age.weightx = 1.0;
		gbc_lbl_Age.fill = GridBagConstraints.BOTH;
		gbc_lbl_Age.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Age.gridwidth = 2;
		gbc_lbl_Age.gridx = 0;
		gbc_lbl_Age.gridy = 6;
		pan_PatientInfo.add(lbl_Age, gbc_lbl_Age);

		lbl_Gender = new JLabel(paragraph.getString("GENDER"));
		GridBagConstraints gbc_lbl_Gender = new GridBagConstraints();
		gbc_lbl_Gender.weighty = 0.05;
		gbc_lbl_Gender.weightx = 1.0;
		gbc_lbl_Gender.fill = GridBagConstraints.BOTH;
		gbc_lbl_Gender.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Gender.gridwidth = 2;
		gbc_lbl_Gender.gridx = 0;
		gbc_lbl_Gender.gridy = 7;
		pan_PatientInfo.add(lbl_Gender, gbc_lbl_Gender);

		lbl_BloodType = new JLabel(paragraph.getString("TITLEBLOODTYPE"));
		GridBagConstraints gbc_lbl_BloodType = new GridBagConstraints();
		gbc_lbl_BloodType.weighty = 0.05;
		gbc_lbl_BloodType.weightx = 1.0;
		gbc_lbl_BloodType.fill = GridBagConstraints.BOTH;
		gbc_lbl_BloodType.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_BloodType.gridwidth = 2;
		gbc_lbl_BloodType.gridx = 0;
		gbc_lbl_BloodType.gridy = 8;
		pan_PatientInfo.add(lbl_BloodType, gbc_lbl_BloodType);

		lbl_Height = new JLabel(paragraph.getString("TITLEHEIGHT"));
		GridBagConstraints gbc_lbl_Height = new GridBagConstraints();
		gbc_lbl_Height.weightx = 1.0;
		gbc_lbl_Height.weighty = 0.05;
		gbc_lbl_Height.fill = GridBagConstraints.BOTH;
		gbc_lbl_Height.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Height.gridwidth = 2;
		gbc_lbl_Height.gridx = 0;
		gbc_lbl_Height.gridy = 9;
		pan_PatientInfo.add(lbl_Height, gbc_lbl_Height);

		lbl_Weight = new JLabel(paragraph.getString("TITLEWEIGHT"));
		GridBagConstraints gbc_lbl_Weight = new GridBagConstraints();
		gbc_lbl_Weight.weightx = 1.0;
		gbc_lbl_Weight.weighty = 0.05;
		gbc_lbl_Weight.fill = GridBagConstraints.BOTH;
		gbc_lbl_Weight.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Weight.gridwidth = 2;
		gbc_lbl_Weight.gridx = 0;
		gbc_lbl_Weight.gridy = 10;
		pan_PatientInfo.add(lbl_Weight, gbc_lbl_Weight);

		pan_Code = new JPanel();
		GridBagConstraints gbc_pan_Code = new GridBagConstraints();
		gbc_pan_Code.weightx = 0.6;
		gbc_pan_Code.weighty = 0.4;
		gbc_pan_Code.fill = GridBagConstraints.BOTH;
		gbc_pan_Code.insets = new Insets(0, 0, 5, 5);
		gbc_pan_Code.gridx = 0;
		gbc_pan_Code.gridy = 11;
		pan_PatientInfo.add(pan_Code, gbc_pan_Code);

		btn_AddPatient = new JButton(paragraph.getString("NEWPATIENT"));
		btn_AddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_AddPatientactionPerformed(evt);
			}
		});
		GridBagConstraints gbc_btn_AddPatient = new GridBagConstraints();
		gbc_btn_AddPatient.insets = new Insets(0, 0, 10, 10);
		gbc_btn_AddPatient.fill = GridBagConstraints.BOTH;
		gbc_btn_AddPatient.weightx = 0.4;
		gbc_btn_AddPatient.weighty = 0.4;
		gbc_btn_AddPatient.gridx = 1;
		gbc_btn_AddPatient.gridy = 11;
		pan_PatientInfo.add(btn_AddPatient, gbc_btn_AddPatient);

		txt_PatientSearch = new JTextField();
		GridBagConstraints gbc_txt_PatientSearch = new GridBagConstraints();
		gbc_txt_PatientSearch.weightx = 0.4;
		gbc_txt_PatientSearch.weighty = 0.15;
		gbc_txt_PatientSearch.fill = GridBagConstraints.BOTH;
		gbc_txt_PatientSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txt_PatientSearch.gridx = 1;
		gbc_txt_PatientSearch.gridy = 0;
		pan_WholeFrame.add(txt_PatientSearch, gbc_txt_PatientSearch);
		txt_PatientSearch.setColumns(10);

		btn_PatientSearch = new JButton(paragraph.getString("SEARCH"));
		btn_PatientSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_PatientSearchactionPerformed(evt);
			}
		});
		GridBagConstraints gbc_btn_PatientSearch = new GridBagConstraints();
		gbc_btn_PatientSearch.weightx = 0.2;
		gbc_btn_PatientSearch.weighty = 0.15;
		gbc_btn_PatientSearch.fill = GridBagConstraints.BOTH;
		gbc_btn_PatientSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btn_PatientSearch.gridx = 2;
		gbc_btn_PatientSearch.gridy = 0;
		pan_WholeFrame.add(btn_PatientSearch, gbc_btn_PatientSearch);

		scrollPane_Patient = new JScrollPane();
		GridBagConstraints gbc_scrollPane_Patient = new GridBagConstraints();
		gbc_scrollPane_Patient.weighty = 0.55;
		gbc_scrollPane_Patient.weightx = 0.6;
		gbc_scrollPane_Patient.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Patient.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_Patient.gridwidth = 2;
		gbc_scrollPane_Patient.gridx = 1;
		gbc_scrollPane_Patient.gridy = 1;
		pan_WholeFrame.add(scrollPane_Patient, gbc_scrollPane_Patient);
		tab_PatientList = new JTable();
		scrollPane_Patient.setViewportView(tab_PatientList);
		tab_PatientList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_PatientListMouseClicked(evt);
			}
		});
		tab_PatientList.setModel(new DefaultTableModel(matrix, header));

		pan_ClinicOrInpatient = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_pan_ClinicOrInpatient = new GridBagConstraints();
		gbc_pan_ClinicOrInpatient.weightx = 1.0;
		gbc_pan_ClinicOrInpatient.weighty = 0.3;
		gbc_pan_ClinicOrInpatient.fill = GridBagConstraints.BOTH;
		gbc_pan_ClinicOrInpatient.gridwidth = 3;
		gbc_pan_ClinicOrInpatient.gridx = 0;
		gbc_pan_ClinicOrInpatient.gridy = 2;
		pan_WholeFrame.add(pan_ClinicOrInpatient, gbc_pan_ClinicOrInpatient);

		pan_tabClinicInfo = new JPanel();
		pan_ClinicOrInpatient.addTab(paragraph.getString("CLINIC"),
				pan_tabClinicInfo);
		GridBagLayout gbl_pan_tabClinicInfo = new GridBagLayout();
		gbl_pan_tabClinicInfo.columnWidths = new int[] { 231, 388, 0 };
		gbl_pan_tabClinicInfo.rowHeights = new int[] { 369, 0 };
		gbl_pan_tabClinicInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_tabClinicInfo.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pan_tabClinicInfo.setLayout(gbl_pan_tabClinicInfo);

		pan_ClinicInfo = new JPanel();
		GridBagConstraints gbc_pan_ClinicInfo = new GridBagConstraints();
		gbc_pan_ClinicInfo.weighty = 1.0;
		gbc_pan_ClinicInfo.weightx = 0.45;
		gbc_pan_ClinicInfo.anchor = GridBagConstraints.WEST;
		gbc_pan_ClinicInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_ClinicInfo.insets = new Insets(0, 0, 0, 5);
		gbc_pan_ClinicInfo.gridx = 0;
		gbc_pan_ClinicInfo.gridy = 0;
		pan_tabClinicInfo.add(pan_ClinicInfo, gbc_pan_ClinicInfo);
		GridBagLayout gbl_pan_ClinicInfo = new GridBagLayout();
		gbl_pan_ClinicInfo.columnWidths = new int[] { 127, 97, 0 };
		gbl_pan_ClinicInfo.rowHeights = new int[] { 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 23, 0 };
		gbl_pan_ClinicInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_ClinicInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pan_ClinicInfo.setLayout(gbl_pan_ClinicInfo);

		lbl_RegistrationMethod = new JLabel(paragraph.getString("TITLEVISITS"));
		GridBagConstraints gbc_lbl_RegistrationMethod = new GridBagConstraints();
		gbc_lbl_RegistrationMethod.weighty = 0.08;
		gbc_lbl_RegistrationMethod.weightx = 1.0;
		gbc_lbl_RegistrationMethod.gridwidth = 2;
		gbc_lbl_RegistrationMethod.anchor = GridBagConstraints.WEST;
		gbc_lbl_RegistrationMethod.fill = GridBagConstraints.BOTH;
		gbc_lbl_RegistrationMethod.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_RegistrationMethod.gridx = 0;
		gbc_lbl_RegistrationMethod.gridy = 0;
		pan_ClinicInfo.add(lbl_RegistrationMethod, gbc_lbl_RegistrationMethod);

		lbl_Date = new JLabel(paragraph.getString("DATE"));
		GridBagConstraints gbc_lbl_Date = new GridBagConstraints();
		gbc_lbl_Date.gridwidth = 2;
		gbc_lbl_Date.weighty = 0.08;
		gbc_lbl_Date.weightx = 1.0;
		gbc_lbl_Date.anchor = GridBagConstraints.WEST;
		gbc_lbl_Date.fill = GridBagConstraints.BOTH;
		gbc_lbl_Date.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Date.gridx = 0;
		gbc_lbl_Date.gridy = 1;
		pan_ClinicInfo.add(lbl_Date, gbc_lbl_Date);

		cbb_Date = new JComboBox();
		GridBagConstraints gbc_cbb_Date = new GridBagConstraints();
		gbc_cbb_Date.weighty = 0.09;
		gbc_cbb_Date.weightx = 1.0;
		gbc_cbb_Date.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbb_Date.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_Date.gridwidth = 2;
		gbc_cbb_Date.gridx = 0;
		gbc_cbb_Date.gridy = 2;
		pan_ClinicInfo.add(cbb_Date, gbc_cbb_Date);

		lbl_Division = new JLabel(paragraph.getString("DIVISION"));
		GridBagConstraints gbc_lbl_Division = new GridBagConstraints();
		gbc_lbl_Division.weighty = 0.08;
		gbc_lbl_Division.weightx = 1.0;
		gbc_lbl_Division.gridwidth = 2;
		gbc_lbl_Division.anchor = GridBagConstraints.WEST;
		gbc_lbl_Division.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_Division.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Division.gridx = 0;
		gbc_lbl_Division.gridy = 3;
		pan_ClinicInfo.add(lbl_Division, gbc_lbl_Division);

		cbb_Division = new JComboBox();
		GridBagConstraints gbc_cbb_Division = new GridBagConstraints();
		gbc_cbb_Division.weighty = 0.09;
		gbc_cbb_Division.weightx = 1.0;
		gbc_cbb_Division.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbb_Division.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_Division.gridwidth = 2;
		gbc_cbb_Division.gridx = 0;
		gbc_cbb_Division.gridy = 4;
		pan_ClinicInfo.add(cbb_Division, gbc_cbb_Division);

		lbl_Shift = new JLabel(paragraph.getString("SHIFT"));
		GridBagConstraints gbc_lbl_Shift = new GridBagConstraints();
		gbc_lbl_Shift.weighty = 0.08;
		gbc_lbl_Shift.weightx = 1.0;
		gbc_lbl_Shift.gridwidth = 2;
		gbc_lbl_Shift.anchor = GridBagConstraints.WEST;
		gbc_lbl_Shift.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_Shift.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Shift.gridx = 0;
		gbc_lbl_Shift.gridy = 5;
		pan_ClinicInfo.add(lbl_Shift, gbc_lbl_Shift);

		cbb_Shift = new JComboBox();
		GridBagConstraints gbc_cbb_Shift = new GridBagConstraints();
		gbc_cbb_Shift.weighty = 0.09;
		gbc_cbb_Shift.weightx = 1.0;
		gbc_cbb_Shift.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbb_Shift.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_Shift.gridwidth = 2;
		gbc_cbb_Shift.gridx = 0;
		gbc_cbb_Shift.gridy = 6;
		pan_ClinicInfo.add(cbb_Shift, gbc_cbb_Shift);

		lbl_Doctor = new JLabel(paragraph.getString("DOCTOR"));
		GridBagConstraints gbc_lbl_Doctor = new GridBagConstraints();
		gbc_lbl_Doctor.gridwidth = 2;
		gbc_lbl_Doctor.weighty = 0.08;
		gbc_lbl_Doctor.weightx = 1.0;
		gbc_lbl_Doctor.anchor = GridBagConstraints.WEST;
		gbc_lbl_Doctor.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_Doctor.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Doctor.gridx = 0;
		gbc_lbl_Doctor.gridy = 7;
		pan_ClinicInfo.add(lbl_Doctor, gbc_lbl_Doctor);

		lbl_Clinic = new JLabel(paragraph.getString("CLINIC"));
		GridBagConstraints gbc_lbl_Clinic = new GridBagConstraints();
		gbc_lbl_Clinic.weighty = 0.08;
		gbc_lbl_Clinic.weightx = 1.0;
		gbc_lbl_Clinic.gridwidth = 2;
		gbc_lbl_Clinic.anchor = GridBagConstraints.WEST;
		gbc_lbl_Clinic.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_Clinic.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Clinic.gridx = 0;
		gbc_lbl_Clinic.gridy = 8;
		pan_ClinicInfo.add(lbl_Clinic, gbc_lbl_Clinic);

		lbl_WaitingNo = new JLabel(paragraph.getString("TITLEWAITNO"));
		GridBagConstraints gbc_lbl_WaitingNo = new GridBagConstraints();
		gbc_lbl_WaitingNo.gridwidth = 2;
		gbc_lbl_WaitingNo.weighty = 0.08;
		gbc_lbl_WaitingNo.weightx = 1.0;
		gbc_lbl_WaitingNo.anchor = GridBagConstraints.WEST;
		gbc_lbl_WaitingNo.fill = GridBagConstraints.VERTICAL;
		gbc_lbl_WaitingNo.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_WaitingNo.gridx = 0;
		gbc_lbl_WaitingNo.gridy = 9;
		pan_ClinicInfo.add(lbl_WaitingNo, gbc_lbl_WaitingNo);

		btn_ClinicSave = new JButton(paragraph.getString("SAVE"));
		GridBagConstraints gbc_btn_ClinicSave = new GridBagConstraints();
		gbc_btn_ClinicSave.weightx = 0.5;
		gbc_btn_ClinicSave.weighty = 0.17;
		gbc_btn_ClinicSave.anchor = GridBagConstraints.NORTH;
		gbc_btn_ClinicSave.fill = GridBagConstraints.BOTH;
		gbc_btn_ClinicSave.insets = new Insets(0, 0, 0, 5);
		gbc_btn_ClinicSave.gridx = 0;
		gbc_btn_ClinicSave.gridy = 10;
		pan_ClinicInfo.add(btn_ClinicSave, gbc_btn_ClinicSave);

		btn_ClinicClose = new JButton(paragraph.getString("CLOSE"));
		GridBagConstraints gbc_btn_ClinicClose = new GridBagConstraints();
		gbc_btn_ClinicClose.fill = GridBagConstraints.BOTH;
		gbc_btn_ClinicClose.weighty = 0.17;
		gbc_btn_ClinicClose.weightx = 0.5;
		gbc_btn_ClinicClose.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_ClinicClose.gridx = 1;
		gbc_btn_ClinicClose.gridy = 10;
		pan_ClinicInfo.add(btn_ClinicClose, gbc_btn_ClinicClose);

		scrollPane_Clinic = new JScrollPane();
		GridBagConstraints gbc_scrollPane_Clinic = new GridBagConstraints();
		gbc_scrollPane_Clinic.anchor = GridBagConstraints.SOUTHEAST;
		gbc_scrollPane_Clinic.weighty = 1.0;
		gbc_scrollPane_Clinic.weightx = 0.55;
		gbc_scrollPane_Clinic.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Clinic.gridx = 1;
		gbc_scrollPane_Clinic.gridy = 0;
		pan_tabClinicInfo.add(scrollPane_Clinic, gbc_scrollPane_Clinic);

		tab_ClinicList = new JTable();
		scrollPane_Clinic.setViewportView(tab_ClinicList);
		tab_ClinicList.setModel(new DefaultTableModel(matrix, header));

		pan_tabInpatientInfo = new JPanel();
		pan_ClinicOrInpatient.addTab(paragraph.getString("INPATIENT"),
				pan_tabInpatientInfo);
		GridBagLayout gbl_pan_tabInpatientInfo = new GridBagLayout();
		gbl_pan_tabInpatientInfo.columnWidths = new int[] { 231, 388, 0 };
		gbl_pan_tabInpatientInfo.rowHeights = new int[] { 369, 0 };
		gbl_pan_tabInpatientInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_tabInpatientInfo.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		pan_tabInpatientInfo.setLayout(gbl_pan_tabInpatientInfo);

		pan_InpatientInfo = new JPanel();
		GridBagConstraints gbc_pan_InpatientInfo = new GridBagConstraints();
		gbc_pan_InpatientInfo.weighty = 1.0;
		gbc_pan_InpatientInfo.weightx = 0.45;
		gbc_pan_InpatientInfo.anchor = GridBagConstraints.SOUTHWEST;
		gbc_pan_InpatientInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_InpatientInfo.insets = new Insets(0, 0, 0, 5);
		gbc_pan_InpatientInfo.gridx = 0;
		gbc_pan_InpatientInfo.gridy = 0;
		pan_tabInpatientInfo.add(pan_InpatientInfo, gbc_pan_InpatientInfo);
		GridBagLayout gbl_pan_InpatientInfo = new GridBagLayout();
		gbl_pan_InpatientInfo.columnWidths = new int[] { 115, 110, 0 };
		gbl_pan_InpatientInfo.rowHeights = new int[] { 20, 20, 20, 20, 20, 20,
				20, 20, 23, 0 };
		gbl_pan_InpatientInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_InpatientInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pan_InpatientInfo.setLayout(gbl_pan_InpatientInfo);

		lbl_InpatientDivision = new JLabel(paragraph.getString("DIVISION"));
		GridBagConstraints gbc_lbl_InpatientDivision = new GridBagConstraints();
		gbc_lbl_InpatientDivision.weighty = 0.1;
		gbc_lbl_InpatientDivision.weightx = 1.0;
		gbc_lbl_InpatientDivision.gridwidth = 2;
		gbc_lbl_InpatientDivision.anchor = GridBagConstraints.NORTH;
		gbc_lbl_InpatientDivision.fill = GridBagConstraints.BOTH;
		gbc_lbl_InpatientDivision.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_InpatientDivision.gridx = 0;
		gbc_lbl_InpatientDivision.gridy = 0;
		pan_InpatientInfo.add(lbl_InpatientDivision, gbc_lbl_InpatientDivision);

		cbb_InpatientDivision = new JComboBox();
		GridBagConstraints gbc_cbb_InpatientDivision = new GridBagConstraints();
		gbc_cbb_InpatientDivision.anchor = GridBagConstraints.NORTH;
		gbc_cbb_InpatientDivision.weighty = 0.1;
		gbc_cbb_InpatientDivision.weightx = 1.0;
		gbc_cbb_InpatientDivision.fill = GridBagConstraints.BOTH;
		gbc_cbb_InpatientDivision.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_InpatientDivision.gridwidth = 2;
		gbc_cbb_InpatientDivision.gridx = 0;
		gbc_cbb_InpatientDivision.gridy = 1;
		pan_InpatientInfo.add(cbb_InpatientDivision, gbc_cbb_InpatientDivision);

		lbl_InpatientDoctor = new JLabel(paragraph.getString("DOCTOR"));
		GridBagConstraints gbc_lbl_InpatientDoctor = new GridBagConstraints();
		gbc_lbl_InpatientDoctor.gridwidth = 2;
		gbc_lbl_InpatientDoctor.weighty = 0.1;
		gbc_lbl_InpatientDoctor.weightx = 1.0;
		gbc_lbl_InpatientDoctor.anchor = GridBagConstraints.NORTH;
		gbc_lbl_InpatientDoctor.fill = GridBagConstraints.BOTH;
		gbc_lbl_InpatientDoctor.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_InpatientDoctor.gridx = 0;
		gbc_lbl_InpatientDoctor.gridy = 2;
		pan_InpatientInfo.add(lbl_InpatientDoctor, gbc_lbl_InpatientDoctor);

		cbb_InpatientDoctor = new JComboBox();
		GridBagConstraints gbc_cbb_InpatientDoctor = new GridBagConstraints();
		gbc_cbb_InpatientDoctor.weighty = 0.1;
		gbc_cbb_InpatientDoctor.weightx = 1.0;
		gbc_cbb_InpatientDoctor.anchor = GridBagConstraints.NORTH;
		gbc_cbb_InpatientDoctor.fill = GridBagConstraints.BOTH;
		gbc_cbb_InpatientDoctor.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_InpatientDoctor.gridwidth = 2;
		gbc_cbb_InpatientDoctor.gridx = 0;
		gbc_cbb_InpatientDoctor.gridy = 3;
		pan_InpatientInfo.add(cbb_InpatientDoctor, gbc_cbb_InpatientDoctor);

		lbl_CheckInDate = new JLabel(paragraph.getString("CHECKINDATE"));
		GridBagConstraints gbc_lbl_CheckInDate = new GridBagConstraints();
		gbc_lbl_CheckInDate.weighty = 0.1;
		gbc_lbl_CheckInDate.weightx = 1.0;
		gbc_lbl_CheckInDate.gridwidth = 2;
		gbc_lbl_CheckInDate.anchor = GridBagConstraints.NORTH;
		gbc_lbl_CheckInDate.fill = GridBagConstraints.BOTH;
		gbc_lbl_CheckInDate.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_CheckInDate.gridx = 0;
		gbc_lbl_CheckInDate.gridy = 4;
		pan_InpatientInfo.add(lbl_CheckInDate, gbc_lbl_CheckInDate);

		cbb_CheckInDate = new JComboBox();
		GridBagConstraints gbc_cbb_CheckInDate = new GridBagConstraints();
		gbc_cbb_CheckInDate.weighty = 0.1;
		gbc_cbb_CheckInDate.weightx = 1.0;
		gbc_cbb_CheckInDate.anchor = GridBagConstraints.NORTH;
		gbc_cbb_CheckInDate.fill = GridBagConstraints.BOTH;
		gbc_cbb_CheckInDate.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_CheckInDate.gridwidth = 2;
		gbc_cbb_CheckInDate.gridx = 0;
		gbc_cbb_CheckInDate.gridy = 5;
		pan_InpatientInfo.add(cbb_CheckInDate, gbc_cbb_CheckInDate);

		lbl_CheckOutDate = new JLabel(paragraph.getString("CHECKOUTDATE"));
		GridBagConstraints gbc_lbl_CheckOutDate = new GridBagConstraints();
		gbc_lbl_CheckOutDate.weighty = 0.1;
		gbc_lbl_CheckOutDate.weightx = 1.0;
		gbc_lbl_CheckOutDate.gridwidth = 2;
		gbc_lbl_CheckOutDate.anchor = GridBagConstraints.NORTH;
		gbc_lbl_CheckOutDate.fill = GridBagConstraints.BOTH;
		gbc_lbl_CheckOutDate.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_CheckOutDate.gridx = 0;
		gbc_lbl_CheckOutDate.gridy = 6;
		pan_InpatientInfo.add(lbl_CheckOutDate, gbc_lbl_CheckOutDate);

		cbb_CheckOutDate = new JComboBox();
		GridBagConstraints gbc_cbb_CheckOutDate = new GridBagConstraints();
		gbc_cbb_CheckOutDate.weighty = 0.1;
		gbc_cbb_CheckOutDate.weightx = 1.0;
		gbc_cbb_CheckOutDate.anchor = GridBagConstraints.NORTH;
		gbc_cbb_CheckOutDate.fill = GridBagConstraints.BOTH;
		gbc_cbb_CheckOutDate.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_CheckOutDate.gridwidth = 2;
		gbc_cbb_CheckOutDate.gridx = 0;
		gbc_cbb_CheckOutDate.gridy = 7;
		pan_InpatientInfo.add(cbb_CheckOutDate, gbc_cbb_CheckOutDate);

		btn_InpatientSave = new JButton(paragraph.getString("SAVE"));
		GridBagConstraints gbc_btn_InpatientSave = new GridBagConstraints();
		gbc_btn_InpatientSave.weighty = 0.2;
		gbc_btn_InpatientSave.weightx = 0.5;
		gbc_btn_InpatientSave.fill = GridBagConstraints.BOTH;
		gbc_btn_InpatientSave.insets = new Insets(0, 0, 0, 5);
		gbc_btn_InpatientSave.gridx = 0;
		gbc_btn_InpatientSave.gridy = 8;
		pan_InpatientInfo.add(btn_InpatientSave, gbc_btn_InpatientSave);

		btn_InpatientClose = new JButton(paragraph.getString("CLOSE"));
		GridBagConstraints gbc_btn_InpatientClose = new GridBagConstraints();
		gbc_btn_InpatientClose.weighty = 0.2;
		gbc_btn_InpatientClose.weightx = 0.5;
		gbc_btn_InpatientClose.fill = GridBagConstraints.BOTH;
		gbc_btn_InpatientClose.gridx = 1;
		gbc_btn_InpatientClose.gridy = 8;
		pan_InpatientInfo.add(btn_InpatientClose, gbc_btn_InpatientClose);

		scrollPane_Bed = new JScrollPane();
		GridBagConstraints gbc_scrollPane_Bed = new GridBagConstraints();
		gbc_scrollPane_Bed.weighty = 1.0;
		gbc_scrollPane_Bed.weightx = 0.55;
		gbc_scrollPane_Bed.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Bed.gridx = 1;
		gbc_scrollPane_Bed.gridy = 0;
		pan_tabInpatientInfo.add(scrollPane_Bed, gbc_scrollPane_Bed);

		tab_BedList = new JTable();
		scrollPane_Bed.setViewportView(tab_BedList);
		tab_BedList.setModel(new DefaultTableModel(matrix, header));
		// End of init GUI
	}

	private void btn_PatientSearchactionPerformed(ActionEvent evt) {
		ResultSet rs = null;
		String target = txt_PatientSearch.getText();
		patientsInfo = patientsInfoDao.getPatientsBySearch(target);
		if (patientsInfo.size() != 0) {
			tab_PatientList.setModel(new PatientsInfoJPATable(patientsInfo));
		} else {
			tab_PatientList.setModel(new DefaultTableModel(
					new String[][] { { "No Information." } },
					new String[] { "Message" }));
			JOptionPane.showMessageDialog(new Frame(),
					paragraph.getString("FIRSTTIMEVISIT"));
		}
		txt_PatientSearch.setFocusable(true);
	}

	private void btn_AddPatientactionPerformed(ActionEvent evt) {
		Frm_PatientMod patientMod = new Frm_PatientMod(this, true);
		patientMod.setVisible(true);
		this.setEnabled(false);
	}

	private void tab_PatientListMouseClicked(java.awt.event.MouseEvent evt) {
		if (tab_PatientList.getSelectedRow() < 0
				|| tab_PatientList.getColumnCount() == 1) {
			return;
		} else {
			String sql = "SELECT * "
					+ "FROM patients_info "
					+ "WHERE exist = 1 "
					+ "AND p_no = '"
					+ tab_PatientList.getValueAt(
							tab_PatientList.getSelectedRow(), 0) + "' ";
			showImage(null, "");
			ResultSet rs = null;
			
			try {
				rs = DBC.executeQuery(sql);
				rs.next();
				this.lbl_PatientNo.setText(rs.getString("p_no"));
				this.lbl_NHISNo.setText(rs.getString("nhis_no"));
				this.lbl_NIANo.setText(rs.getString("nia_no"));
				this.lbl_FirstName.setText(rs.getString("firstname"));
				this.lbl_Last_Name.setText(rs.getString("lastname"));
				this.lbl_Birthday.setText(rs.getString("birth"));
				this.lbl_Age
						.setText((lbl_Birthday.getText() == null || lbl_Birthday
								.getText().isEmpty()) ? "" : DateMethod
								.getAgeWithMonth(rs.getDate("birth")));
				this.lbl_Gender.setText(rs.getString("gender"));
				this.lbl_BloodType.setText(rs.getString("bloodtype"));
				this.lbl_Height.setText(rs.getString("height"));
				this.lbl_Weight.setText(rs.getString("weight"));
				if (rs.getString("dead_guid") != null) {
					dead = true;
				}
				else{
					dead = false;
				}
				//btn_EditP.setEnabled(true);
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
	}

	@Override
	public void reLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPatientMod(String pno) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDateChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFingerDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showImage(BufferedImage bufferedimage, String msg) {
		// TODO Auto-generated method stub

	}
}
