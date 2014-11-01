package registration;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

import main.Frm_Main;
import multilingual.Language;

import org.his.bind.PatientsInfoJPATable;
import org.his.dao.PatientsInfoDao;
import org.his.model.PatientsInfo;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import barcode.PrintBarcode;
import patients.Frm_PatientMod;
import patients.LabelSticker;
import patients.PatientsInterface;
import system.Setting;
import cc.johnwu.date.DateInterface;
import cc.johnwu.date.DateMethod;
import cc.johnwu.finger.FingerPrintScanner;
import cc.johnwu.finger.FingerPrintViewerInterface;
import cc.johnwu.sql.DBC;
import common.PrintTools;
import QR.QRUtility;

public class Frm_RegAndInpatient extends JFrame implements
		FingerPrintViewerInterface, DateInterface, PatientsInterface {
	private static final long serialVersionUID = 1L;
	private JPanel pan_WholeFrame;
	// GUI for left top patient info
	private JPanel pan_PatientInfo;
	private JLabel lbl_PatientNo;
	private JLabel lbl_NHISNo;
	private JLabel lbl_NIANo;
	private JLabel lbl_FirstName;
	private JLabel lbl_LastName;
	private JLabel lbl_Birthday;
	private JLabel lbl_Age;
	private JLabel lbl_Gender;
	private JLabel lbl_BloodType;
	private JLabel lbl_Height;
	private JLabel lbl_Weight;
	private cc.johnwu.finger.FingerPrintViewer Frm_FingerPrintViewer;
	private JButton btn_AddPatient;
	private JButton btn_EditPatient;
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
	private cc.johnwu.date.DateChooser pan_ClinicDate;
	private JLabel lbl_Division;
	private JComboBox cbb_Division;
	private JLabel lbl_Shift;
	private JComboBox cbb_Shift;
	private JLabel lbl_Doctor;
	private JLabel lbl_Room;
	private JLabel lbl_WaitingNo;
	private JButton btn_MobSave;
	private JButton btn_ClinicSave;
	private JButton btn_ClinicClose;
	private JScrollPane scrollPane_Clinic;
	private JTable tab_ClinicList;
	// GUI for Inpatient tab
	private JPanel pan_tabInpatientInfo;
	private JPanel pan_InpatientInfo;
	private JLabel lbl_InpatientType;
	private JComboBox cbb_InpatientType;
	private JLabel lbl_InpatientDivision;
	private JComboBox cbb_InpatientDivision;
	private JLabel lbl_InpatientDoctor;
	private JComboBox cbb_InpatientDoctor;
	private JLabel lbl_CheckInDate;
	private cc.johnwu.date.DateChooser pan_CheckInDate;
	private JLabel lbl_CheckOutDate;
	private cc.johnwu.date.DateChooser pan_CheckOutDate;
	private JButton btn_InpatientSave;
	private JButton btn_InpatientClose;
	private JScrollPane scrollPane_Bed;
	private JTable tab_BedList;
	// GUI for History
	private JPanel pan_tabHistory;
	private JLabel lbl_HistoryDate;
	private cc.johnwu.date.DateChooser pan_HistoryDate;
	private JScrollPane scrollPane_History;
	private JTable tab_HistoryList;
	private JButton btn_HistoryReprint;
	private JButton btn_HistoryCancel;
	private JButton btn_HistoryClose;
	// GUI define End

	private Language paragraph = Language.getInstance();
	private Setting set = new Setting();
	private String[] lineSet = set.setSystem("GIS").split("\n");

	private PatientsInfoDao patientsInfoDao = new PatientsInfoDao();
	private List<PatientsInfo> patientsInfo;

	// cached DB info
	private String selectedDoctorName = "";
	private String selectedDoctorID = "";
	private String selectedDoctorNo = "";
	private String selectedShiftGUID = "";
	private String selectedPatientGUID = "";
	private boolean selectedPatientWithBirthdayInfo = false;
	private String selectedBedGUID = "";
	private String selectedBedDevision = "";
	private String selectedRegGUID = "";
	private Integer m_Number = 0;

	boolean dead = true;

	private JFrame parentFrame;

	public Frm_RegAndInpatient(JFrame parentFrame, String p_no) {
		this();
		selectedPatientGUID = p_no;
		setPatientInfo(p_no);
		this.parentFrame = parentFrame;
	}

	/**
	 * Create the frame.
	 */
	public Frm_RegAndInpatient() {

		this.parentFrame = null;
		// Init GUI
		this.setExtendedState(Frm_Registration.MAXIMIZED_BOTH);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				if (isEnabled()) {
					FingerPrintScanner.stop();
					new Frm_Main().setVisible(true);
					dispose();
				}
			}
		});

		setBounds(100, 100, 670, 705);

		pan_WholeFrame = new JPanel();
		pan_WholeFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pan_WholeFrame);
		GridBagLayout gbl_pan_WholeFrame = new GridBagLayout();
		gbl_pan_WholeFrame.columnWidths = new int[] { 643, 0 };
		gbl_pan_WholeFrame.rowHeights = new int[] { 363, 287, 0 };
		gbl_pan_WholeFrame.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pan_WholeFrame.rowWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		pan_WholeFrame.setLayout(gbl_pan_WholeFrame);

		JPanel pan_Patinet = new JPanel();
		GridBagConstraints gbc_pan_Patinet = new GridBagConstraints();
		gbc_pan_Patinet.weighty = 0.7;
		gbc_pan_Patinet.weightx = 1.0;
		gbc_pan_Patinet.fill = GridBagConstraints.BOTH;
		gbc_pan_Patinet.insets = new Insets(0, 0, 5, 0);
		gbc_pan_Patinet.gridx = 0;
		gbc_pan_Patinet.gridy = 0;
		pan_WholeFrame.add(pan_Patinet, gbc_pan_Patinet);
		GridBagLayout gbl_pan_Patinet = new GridBagLayout();
		gbl_pan_Patinet.columnWidths = new int[] { 240, 250, 135, 0 };
		gbl_pan_Patinet.rowHeights = new int[] { 20, 327, 0 };
		gbl_pan_Patinet.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_Patinet.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pan_Patinet.setLayout(gbl_pan_Patinet);

		pan_PatientInfo = new JPanel();
		GridBagConstraints gbc_pan_PatientInfo = new GridBagConstraints();
		gbc_pan_PatientInfo.weightx = 0.45;
		gbc_pan_PatientInfo.weighty = 1.0;
		gbc_pan_PatientInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_PatientInfo.insets = new Insets(0, 0, 0, 5);
		gbc_pan_PatientInfo.gridheight = 2;
		gbc_pan_PatientInfo.gridx = 0;
		gbc_pan_PatientInfo.gridy = 0;
		pan_Patinet.add(pan_PatientInfo, gbc_pan_PatientInfo);
		GridBagLayout gbl_pan_PatientInfo = new GridBagLayout();
		gbl_pan_PatientInfo.columnWidths = new int[] { 135, 100, 0 };
		gbl_pan_PatientInfo.rowHeights = new int[] { 15, 15, 15, 15, 15, 15,
				15, 15, 15, 15, 15, 29, 28, 0 };
		gbl_pan_PatientInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_PatientInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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

		lbl_LastName = new JLabel(paragraph.getString("TITLELASTNAME"));
		GridBagConstraints gbc_lbl_LastName = new GridBagConstraints();
		gbc_lbl_LastName.weighty = 0.05;
		gbc_lbl_LastName.weightx = 1.0;
		gbc_lbl_LastName.fill = GridBagConstraints.BOTH;
		gbc_lbl_LastName.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_LastName.gridwidth = 2;
		gbc_lbl_LastName.gridx = 0;
		gbc_lbl_LastName.gridy = 4;
		pan_PatientInfo.add(lbl_LastName, gbc_lbl_LastName);

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
		gbc_lbl_Height.weighty = 0.05;
		gbc_lbl_Height.weightx = 1.0;
		gbc_lbl_Height.fill = GridBagConstraints.BOTH;
		gbc_lbl_Height.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Height.gridwidth = 2;
		gbc_lbl_Height.gridx = 0;
		gbc_lbl_Height.gridy = 9;
		pan_PatientInfo.add(lbl_Height, gbc_lbl_Height);

		lbl_Weight = new JLabel(paragraph.getString("TITLEWEIGHT"));
		GridBagConstraints gbc_lbl_Weight = new GridBagConstraints();
		gbc_lbl_Weight.weighty = 0.05;
		gbc_lbl_Weight.weightx = 1.0;
		gbc_lbl_Weight.fill = GridBagConstraints.BOTH;
		gbc_lbl_Weight.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Weight.gridwidth = 2;
		gbc_lbl_Weight.gridx = 0;
		gbc_lbl_Weight.gridy = 10;
		pan_PatientInfo.add(lbl_Weight, gbc_lbl_Weight);

		Frm_FingerPrintViewer = new cc.johnwu.finger.FingerPrintViewer();
		Frm_FingerPrintViewer.setVisible(true);
		GridBagConstraints gbc_Frm_FingerPrintViewer = new GridBagConstraints();
		gbc_Frm_FingerPrintViewer.weighty = 0.4;
		gbc_Frm_FingerPrintViewer.weightx = 0.7;
		gbc_Frm_FingerPrintViewer.fill = GridBagConstraints.BOTH;
		gbc_Frm_FingerPrintViewer.insets = new Insets(0, 0, 0, 5);
		gbc_Frm_FingerPrintViewer.gridheight = 2;
		gbc_Frm_FingerPrintViewer.gridx = 0;
		gbc_Frm_FingerPrintViewer.gridy = 11;
		pan_PatientInfo.add(Frm_FingerPrintViewer, gbc_Frm_FingerPrintViewer);

		btn_AddPatient = new JButton(paragraph.getString("NEWPATIENT"));
		btn_AddPatient.setMnemonic(java.awt.event.KeyEvent.VK_N);
		btn_AddPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_AddPatientactionPerformed(evt);
			}
		});

		btn_EditPatient = new JButton(paragraph.getString("EDITPATIENTDATA"));
		btn_EditPatient.setEnabled(false);
		btn_EditPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_EditPatientactionPerformed(evt);
			}
		});
		GridBagConstraints gbc_btn_EditPatient = new GridBagConstraints();
		gbc_btn_EditPatient.weightx = 0.3;
		gbc_btn_EditPatient.weighty = 0.2;
		gbc_btn_EditPatient.fill = GridBagConstraints.BOTH;
		gbc_btn_EditPatient.insets = new Insets(0, 0, 5, 5);
		gbc_btn_EditPatient.gridx = 1;
		gbc_btn_EditPatient.gridy = 11;
		pan_PatientInfo.add(btn_EditPatient, gbc_btn_EditPatient);
		GridBagConstraints gbc_btn_AddPatient = new GridBagConstraints();
		gbc_btn_AddPatient.insets = new Insets(0, 0, 5, 5);
		gbc_btn_AddPatient.weighty = 0.2;
		gbc_btn_AddPatient.weightx = 0.3;
		gbc_btn_AddPatient.fill = GridBagConstraints.BOTH;
		gbc_btn_AddPatient.gridx = 1;
		gbc_btn_AddPatient.gridy = 12;
		pan_PatientInfo.add(btn_AddPatient, gbc_btn_AddPatient);

		txt_PatientSearch = new JTextField();
		GridBagConstraints gbc_txt_PatientSearch = new GridBagConstraints();
		gbc_txt_PatientSearch.weighty = 0.15;
		gbc_txt_PatientSearch.weightx = 0.4;
		gbc_txt_PatientSearch.fill = GridBagConstraints.BOTH;
		gbc_txt_PatientSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txt_PatientSearch.gridx = 1;
		gbc_txt_PatientSearch.gridy = 0;
		pan_Patinet.add(txt_PatientSearch, gbc_txt_PatientSearch);
		txt_PatientSearch.setColumns(10);

		btn_PatientSearch = new JButton(paragraph.getString("SEARCH"));
		GridBagConstraints gbc_btn_PatientSearch = new GridBagConstraints();
		gbc_btn_PatientSearch.weighty = 0.15;
		gbc_btn_PatientSearch.weightx = 0.15;
		gbc_btn_PatientSearch.fill = GridBagConstraints.BOTH;
		gbc_btn_PatientSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btn_PatientSearch.gridx = 2;
		gbc_btn_PatientSearch.gridy = 0;
		pan_Patinet.add(btn_PatientSearch, gbc_btn_PatientSearch);
		btn_PatientSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_PatientSearchactionPerformed(evt);
			}
		});

		scrollPane_Patient = new JScrollPane();
		GridBagConstraints gbc_scrollPane_Patient = new GridBagConstraints();
		gbc_scrollPane_Patient.weighty = 0.85;
		gbc_scrollPane_Patient.weightx = 0.55;
		gbc_scrollPane_Patient.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Patient.gridwidth = 2;
		gbc_scrollPane_Patient.gridx = 1;
		gbc_scrollPane_Patient.gridy = 1;
		pan_Patinet.add(scrollPane_Patient, gbc_scrollPane_Patient);

		tab_PatientList = new JTable();
		scrollPane_Patient.setViewportView(tab_PatientList);
		tab_PatientList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_PatientListMouseClicked(evt);
			}
		});
		tab_PatientList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_PatientListKeyPressed(evt);
			}
		});

		pan_ClinicOrInpatient = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_pan_ClinicOrInpatient = new GridBagConstraints();
		gbc_pan_ClinicOrInpatient.weighty = 0.3;
		gbc_pan_ClinicOrInpatient.weightx = 1.0;
		gbc_pan_ClinicOrInpatient.fill = GridBagConstraints.BOTH;
		gbc_pan_ClinicOrInpatient.gridx = 0;
		gbc_pan_ClinicOrInpatient.gridy = 1;
		pan_WholeFrame.add(pan_ClinicOrInpatient, gbc_pan_ClinicOrInpatient);

		pan_tabClinicInfo = new JPanel();
		pan_ClinicOrInpatient.addTab(paragraph.getString("CLINIC"),
				pan_tabClinicInfo);
		GridBagLayout gbl_pan_tabClinicInfo = new GridBagLayout();
		gbl_pan_tabClinicInfo.columnWidths = new int[] { 240, 390, 0 };
		gbl_pan_tabClinicInfo.rowHeights = new int[] { 338, 0 };
		gbl_pan_tabClinicInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_tabClinicInfo.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pan_tabClinicInfo.setLayout(gbl_pan_tabClinicInfo);

		pan_ClinicInfo = new JPanel();
		GridBagConstraints gbc_pan_ClinicInfo = new GridBagConstraints();
		gbc_pan_ClinicInfo.weightx = 0.45;
		gbc_pan_ClinicInfo.weighty = 1.0;
		gbc_pan_ClinicInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_ClinicInfo.insets = new Insets(0, 0, 0, 5);
		gbc_pan_ClinicInfo.gridx = 0;
		gbc_pan_ClinicInfo.gridy = 0;
		pan_tabClinicInfo.add(pan_ClinicInfo, gbc_pan_ClinicInfo);
		GridBagLayout gbl_pan_ClinicInfo = new GridBagLayout();
		gbl_pan_ClinicInfo.columnWidths = new int[] { 75, 75, 75, 0 };
		gbl_pan_ClinicInfo.rowHeights = new int[] { 15, 15, 30, 15, 22, 15, 22,
				15, 15, 15, 58, 35, 0 };
		gbl_pan_ClinicInfo.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_ClinicInfo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pan_ClinicInfo.setLayout(gbl_pan_ClinicInfo);

		lbl_RegistrationMethod = new JLabel(paragraph.getString("TITLEVISITS"));
		GridBagConstraints gbc_lbl_RegistrationMethod = new GridBagConstraints();
		gbc_lbl_RegistrationMethod.weighty = 0.08;
		gbc_lbl_RegistrationMethod.weightx = 1.0;
		gbc_lbl_RegistrationMethod.fill = GridBagConstraints.BOTH;
		gbc_lbl_RegistrationMethod.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_RegistrationMethod.gridwidth = 3;
		gbc_lbl_RegistrationMethod.gridx = 0;
		gbc_lbl_RegistrationMethod.gridy = 0;
		pan_ClinicInfo.add(lbl_RegistrationMethod, gbc_lbl_RegistrationMethod);

		lbl_Date = new JLabel(paragraph.getString("DATE"));
		GridBagConstraints gbc_lbl_Date = new GridBagConstraints();
		gbc_lbl_Date.weighty = 0.08;
		gbc_lbl_Date.weightx = 1.0;
		gbc_lbl_Date.fill = GridBagConstraints.BOTH;
		gbc_lbl_Date.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Date.gridwidth = 3;
		gbc_lbl_Date.gridx = 0;
		gbc_lbl_Date.gridy = 1;
		pan_ClinicInfo.add(lbl_Date, gbc_lbl_Date);

		pan_ClinicDate = new cc.johnwu.date.DateChooser();
		pan_ClinicDate.setParentFrame(this);
		GridBagConstraints gbc_pan_ClinicDate = new GridBagConstraints();
		gbc_pan_ClinicDate.weighty = 0.08;
		gbc_pan_ClinicDate.weightx = 1.0;
		gbc_pan_ClinicDate.fill = GridBagConstraints.BOTH;
		gbc_pan_ClinicDate.insets = new Insets(0, 0, 5, 0);
		gbc_pan_ClinicDate.gridwidth = 3;
		gbc_pan_ClinicDate.gridx = 0;
		gbc_pan_ClinicDate.gridy = 2;
		pan_ClinicInfo.add(pan_ClinicDate, gbc_pan_ClinicDate);

		lbl_Division = new JLabel(paragraph.getString("DIVISION"));
		GridBagConstraints gbc_lbl_Division = new GridBagConstraints();
		gbc_lbl_Division.weighty = 0.08;
		gbc_lbl_Division.weightx = 1.0;
		gbc_lbl_Division.fill = GridBagConstraints.BOTH;
		gbc_lbl_Division.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Division.gridwidth = 3;
		gbc_lbl_Division.gridx = 0;
		gbc_lbl_Division.gridy = 3;
		pan_ClinicInfo.add(lbl_Division, gbc_lbl_Division);

		cbb_Division = new JComboBox();
		GridBagConstraints gbc_cbb_Division = new GridBagConstraints();
		gbc_cbb_Division.weighty = 0.08;
		gbc_cbb_Division.weightx = 1.0;
		gbc_cbb_Division.fill = GridBagConstraints.BOTH;
		gbc_cbb_Division.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_Division.gridwidth = 3;
		gbc_cbb_Division.gridx = 0;
		gbc_cbb_Division.gridy = 4;
		pan_ClinicInfo.add(cbb_Division, gbc_cbb_Division);
		cbb_Division.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				refreshClinicInfo();
			}
		});

		lbl_Shift = new JLabel(paragraph.getString("SHIFT"));
		GridBagConstraints gbc_lbl_Shift = new GridBagConstraints();
		gbc_lbl_Shift.weighty = 0.08;
		gbc_lbl_Shift.weightx = 1.0;
		gbc_lbl_Shift.fill = GridBagConstraints.BOTH;
		gbc_lbl_Shift.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Shift.gridwidth = 3;
		gbc_lbl_Shift.gridx = 0;
		gbc_lbl_Shift.gridy = 5;
		pan_ClinicInfo.add(lbl_Shift, gbc_lbl_Shift);

		cbb_Shift = new JComboBox();
		GridBagConstraints gbc_cbb_Shift = new GridBagConstraints();
		gbc_cbb_Shift.fill = GridBagConstraints.BOTH;
		gbc_cbb_Shift.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_Shift.gridwidth = 3;
		gbc_cbb_Shift.gridx = 0;
		gbc_cbb_Shift.gridy = 6;
		pan_ClinicInfo.add(cbb_Shift, gbc_cbb_Shift);
		cbb_Shift.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				refreshClinicInfo();
			}
		});

		lbl_Doctor = new JLabel(paragraph.getString("DOCTOR"));
		GridBagConstraints gbc_lbl_Doctor = new GridBagConstraints();
		gbc_lbl_Doctor.weighty = 0.08;
		gbc_lbl_Doctor.weightx = 1.0;
		gbc_lbl_Doctor.fill = GridBagConstraints.BOTH;
		gbc_lbl_Doctor.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Doctor.gridwidth = 3;
		gbc_lbl_Doctor.gridx = 0;
		gbc_lbl_Doctor.gridy = 7;
		pan_ClinicInfo.add(lbl_Doctor, gbc_lbl_Doctor);

		lbl_Room = new JLabel(paragraph.getString("TITLEROOM"));
		GridBagConstraints gbc_lbl_Room = new GridBagConstraints();
		gbc_lbl_Room.weighty = 0.08;
		gbc_lbl_Room.weightx = 1.0;
		gbc_lbl_Room.fill = GridBagConstraints.BOTH;
		gbc_lbl_Room.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_Room.gridwidth = 3;
		gbc_lbl_Room.gridx = 0;
		gbc_lbl_Room.gridy = 8;
		pan_ClinicInfo.add(lbl_Room, gbc_lbl_Room);

		lbl_WaitingNo = new JLabel(paragraph.getString("TITLEWAITNO"));
		GridBagConstraints gbc_lbl_WaitingNo = new GridBagConstraints();
		gbc_lbl_WaitingNo.weighty = 0.08;
		gbc_lbl_WaitingNo.weightx = 1.0;
		gbc_lbl_WaitingNo.fill = GridBagConstraints.BOTH;
		gbc_lbl_WaitingNo.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_WaitingNo.gridwidth = 3;
		gbc_lbl_WaitingNo.gridx = 0;
		gbc_lbl_WaitingNo.gridy = 9;
		pan_ClinicInfo.add(lbl_WaitingNo, gbc_lbl_WaitingNo);

		btn_MobSave = new JButton(paragraph.getString("BOBILEREGISTRATION"));
		btn_MobSave.setEnabled(false);
		GridBagConstraints gbc_btn_MobSave = new GridBagConstraints();
		gbc_btn_MobSave.weighty = 0.2;
		gbc_btn_MobSave.weightx = 0.3;
		gbc_btn_MobSave.fill = GridBagConstraints.BOTH;
		gbc_btn_MobSave.insets = new Insets(0, 0, 0, 5);
		gbc_btn_MobSave.gridx = 0;
		gbc_btn_MobSave.gridy = 11;
		pan_ClinicInfo.add(btn_MobSave, gbc_btn_MobSave);
		btn_MobSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_ClinicSaveactionPerformed(evt, "M");
			}
		});

		btn_ClinicSave = new JButton(paragraph.getString("SAVE"));
		btn_ClinicSave.setEnabled(false);
		GridBagConstraints gbc_btn_ClinicSave = new GridBagConstraints();
		gbc_btn_ClinicSave.weighty = 0.2;
		gbc_btn_ClinicSave.weightx = 0.4;
		gbc_btn_ClinicSave.fill = GridBagConstraints.BOTH;
		gbc_btn_ClinicSave.insets = new Insets(0, 0, 0, 5);
		gbc_btn_ClinicSave.gridx = 1;
		gbc_btn_ClinicSave.gridy = 11;
		pan_ClinicInfo.add(btn_ClinicSave, gbc_btn_ClinicSave);
		btn_ClinicSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_ClinicSaveactionPerformed(evt, "O");
			}
		});

		btn_ClinicClose = new JButton(paragraph.getString("CLOSE"));
		GridBagConstraints gbc_btn_ClinicClose = new GridBagConstraints();
		gbc_btn_ClinicClose.weighty = 0.2;
		gbc_btn_ClinicClose.weightx = 0.3;
		gbc_btn_ClinicClose.fill = GridBagConstraints.BOTH;
		gbc_btn_ClinicClose.gridx = 2;
		gbc_btn_ClinicClose.gridy = 11;
		pan_ClinicInfo.add(btn_ClinicClose, gbc_btn_ClinicClose);
		btn_ClinicClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_ClinicCloseactionPerformed(evt);
			}
		});

		scrollPane_Clinic = new JScrollPane();
		GridBagConstraints gbc_scrollPane_Clinic = new GridBagConstraints();
		gbc_scrollPane_Clinic.weightx = 0.55;
		gbc_scrollPane_Clinic.weighty = 1.0;
		gbc_scrollPane_Clinic.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Clinic.gridx = 1;
		gbc_scrollPane_Clinic.gridy = 0;
		pan_tabClinicInfo.add(scrollPane_Clinic, gbc_scrollPane_Clinic);

		tab_ClinicList = new JTable();
		scrollPane_Clinic.setViewportView(tab_ClinicList);
		tab_ClinicList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_ClinicListMouseClicked(evt);
			}
		});
		tab_ClinicList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_ClinicListKeyPressed(evt);
			}
		});

		pan_tabInpatientInfo = new JPanel();
		pan_ClinicOrInpatient.addTab(paragraph.getString("INPATIENT"),
				pan_tabInpatientInfo);
		GridBagLayout gbl_pan_tabInpatientInfo = new GridBagLayout();
		gbl_pan_tabInpatientInfo.columnWidths = new int[] { 240, 390, 0 };
		gbl_pan_tabInpatientInfo.rowHeights = new int[] { 338, 0 };
		gbl_pan_tabInpatientInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_tabInpatientInfo.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		pan_tabInpatientInfo.setLayout(gbl_pan_tabInpatientInfo);

		pan_InpatientInfo = new JPanel();
		GridBagConstraints gbc_pan_InpatientInfo = new GridBagConstraints();
		gbc_pan_InpatientInfo.weighty = 0.1;
		gbc_pan_InpatientInfo.weightx = 0.45;
		gbc_pan_InpatientInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_InpatientInfo.insets = new Insets(0, 0, 0, 5);
		gbc_pan_InpatientInfo.gridx = 0;
		gbc_pan_InpatientInfo.gridy = 0;
		pan_tabInpatientInfo.add(pan_InpatientInfo, gbc_pan_InpatientInfo);
		GridBagLayout gbl_pan_InpatientInfo = new GridBagLayout();
		gbl_pan_InpatientInfo.columnWidths = new int[] { 112, 122, 0 };
		gbl_pan_InpatientInfo.rowHeights = new int[] { 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 0 };
		gbl_pan_InpatientInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_InpatientInfo.rowWeights = new double[] { 0.08, 0.08, 0.08,
				0.08, 0.08, 0.08, 0.08, 0.08, 0.08, 0.08, 0.2, Double.MIN_VALUE };
		pan_InpatientInfo.setLayout(gbl_pan_InpatientInfo);

		lbl_InpatientType = new JLabel(paragraph.getString("INPATIENTTYPE"));
		GridBagConstraints gbc_lbl_InpatientType = new GridBagConstraints();
		gbc_lbl_InpatientType.weighty = 0.08;
		gbc_lbl_InpatientType.weightx = 1.0;
		gbc_lbl_InpatientType.fill = GridBagConstraints.BOTH;
		gbc_lbl_InpatientType.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_InpatientType.gridwidth = 2;
		gbc_lbl_InpatientType.gridx = 0;
		gbc_lbl_InpatientType.gridy = 0;
		pan_InpatientInfo.add(lbl_InpatientType, gbc_lbl_InpatientType);

		cbb_InpatientType = new JComboBox();
		GridBagConstraints gbc_cbb_InpatientType = new GridBagConstraints();
		gbc_cbb_InpatientType.fill = GridBagConstraints.BOTH;
		gbc_cbb_InpatientType.weighty = 0.08;
		gbc_cbb_InpatientType.weightx = 1.0;
		gbc_cbb_InpatientType.gridwidth = 2;
		gbc_cbb_InpatientType.insets = new Insets(0, 0, 5, 5);
		gbc_cbb_InpatientType.gridx = 0;
		gbc_cbb_InpatientType.gridy = 1;
		pan_InpatientInfo.add(cbb_InpatientType, gbc_cbb_InpatientType);
		cbb_InpatientType.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				refreshInpatientInfo();
			}
		});

		lbl_InpatientDivision = new JLabel(paragraph.getString("DIVISION"));
		GridBagConstraints gbc_lbl_InpatientDivision = new GridBagConstraints();
		gbc_lbl_InpatientDivision.weighty = 0.08;
		gbc_lbl_InpatientDivision.weightx = 1.0;
		gbc_lbl_InpatientDivision.fill = GridBagConstraints.BOTH;
		gbc_lbl_InpatientDivision.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_InpatientDivision.gridwidth = 2;
		gbc_lbl_InpatientDivision.gridx = 0;
		gbc_lbl_InpatientDivision.gridy = 2;
		pan_InpatientInfo.add(lbl_InpatientDivision, gbc_lbl_InpatientDivision);

		cbb_InpatientDivision = new JComboBox();
		GridBagConstraints gbc_cbb_InpatientDivision = new GridBagConstraints();
		gbc_cbb_InpatientDivision.weighty = 0.08;
		gbc_cbb_InpatientDivision.weightx = 1.0;
		gbc_cbb_InpatientDivision.fill = GridBagConstraints.BOTH;
		gbc_cbb_InpatientDivision.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_InpatientDivision.gridwidth = 2;
		gbc_cbb_InpatientDivision.gridx = 0;
		gbc_cbb_InpatientDivision.gridy = 3;
		pan_InpatientInfo.add(cbb_InpatientDivision, gbc_cbb_InpatientDivision);
		cbb_InpatientDivision
				.addItemListener(new java.awt.event.ItemListener() {
					public void itemStateChanged(java.awt.event.ItemEvent evt) {
						refreshInpatientInfo();
						refreshInpatientDoctorList();
					}
				});

		lbl_InpatientDoctor = new JLabel(paragraph.getString("DOCTOR"));
		GridBagConstraints gbc_lbl_InpatientDoctor = new GridBagConstraints();
		gbc_lbl_InpatientDoctor.weighty = 0.08;
		gbc_lbl_InpatientDoctor.weightx = 1.0;
		gbc_lbl_InpatientDoctor.fill = GridBagConstraints.BOTH;
		gbc_lbl_InpatientDoctor.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_InpatientDoctor.gridwidth = 2;
		gbc_lbl_InpatientDoctor.gridx = 0;
		gbc_lbl_InpatientDoctor.gridy = 4;
		pan_InpatientInfo.add(lbl_InpatientDoctor, gbc_lbl_InpatientDoctor);

		cbb_InpatientDoctor = new JComboBox();
		GridBagConstraints gbc_cbb_InpatientDoctor = new GridBagConstraints();
		gbc_cbb_InpatientDoctor.weighty = 0.08;
		gbc_cbb_InpatientDoctor.weightx = 1.0;
		gbc_cbb_InpatientDoctor.fill = GridBagConstraints.BOTH;
		gbc_cbb_InpatientDoctor.insets = new Insets(0, 0, 5, 0);
		gbc_cbb_InpatientDoctor.gridwidth = 2;
		gbc_cbb_InpatientDoctor.gridx = 0;
		gbc_cbb_InpatientDoctor.gridy = 5;
		pan_InpatientInfo.add(cbb_InpatientDoctor, gbc_cbb_InpatientDoctor);
		cbb_InpatientDoctor.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				selectedDoctorName = (String) cbb_InpatientDoctor
						.getSelectedItem();
			}
		});

		lbl_CheckInDate = new JLabel(paragraph.getString("CHECKINDATE"));
		GridBagConstraints gbc_lbl_CheckInDate = new GridBagConstraints();
		gbc_lbl_CheckInDate.weighty = 0.08;
		gbc_lbl_CheckInDate.weightx = 1.0;
		gbc_lbl_CheckInDate.fill = GridBagConstraints.BOTH;
		gbc_lbl_CheckInDate.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_CheckInDate.gridwidth = 2;
		gbc_lbl_CheckInDate.gridx = 0;
		gbc_lbl_CheckInDate.gridy = 6;
		pan_InpatientInfo.add(lbl_CheckInDate, gbc_lbl_CheckInDate);

		pan_CheckInDate = new cc.johnwu.date.DateChooser();
		pan_CheckInDate.setParentFrame(this);
		GridBagConstraints gbc_pan_CheckInDate = new GridBagConstraints();
		gbc_pan_CheckInDate.weighty = 0.08;
		gbc_pan_CheckInDate.weightx = 1.0;
		gbc_pan_CheckInDate.fill = GridBagConstraints.BOTH;
		gbc_pan_CheckInDate.insets = new Insets(0, 0, 5, 0);
		gbc_pan_CheckInDate.gridwidth = 2;
		gbc_pan_CheckInDate.gridx = 0;
		gbc_pan_CheckInDate.gridy = 7;
		pan_InpatientInfo.add(pan_CheckInDate, gbc_pan_CheckInDate);

		lbl_CheckOutDate = new JLabel(paragraph.getString("CHECKOUTDATE"));
		GridBagConstraints gbc_lbl_CheckOutDate = new GridBagConstraints();
		gbc_lbl_CheckOutDate.weighty = 0.08;
		gbc_lbl_CheckOutDate.weightx = 1.0;
		gbc_lbl_CheckOutDate.fill = GridBagConstraints.BOTH;
		gbc_lbl_CheckOutDate.insets = new Insets(0, 0, 5, 0);
		gbc_lbl_CheckOutDate.gridwidth = 2;
		gbc_lbl_CheckOutDate.gridx = 0;
		gbc_lbl_CheckOutDate.gridy = 8;
		pan_InpatientInfo.add(lbl_CheckOutDate, gbc_lbl_CheckOutDate);

		pan_CheckOutDate = new cc.johnwu.date.DateChooser();
		pan_CheckOutDate.setParentFrame(this);
		GridBagConstraints gbc_pan_CheckOutDate = new GridBagConstraints();
		gbc_pan_CheckOutDate.weighty = 0.08;
		gbc_pan_CheckOutDate.weightx = 1.0;
		gbc_pan_CheckOutDate.fill = GridBagConstraints.BOTH;
		gbc_pan_CheckOutDate.insets = new Insets(0, 0, 5, 0);
		gbc_pan_CheckOutDate.gridwidth = 2;
		gbc_pan_CheckOutDate.gridx = 0;
		gbc_pan_CheckOutDate.gridy = 9;
		pan_InpatientInfo.add(pan_CheckOutDate, gbc_pan_CheckOutDate);

		btn_InpatientSave = new JButton(paragraph.getString("SAVE"));
		btn_InpatientSave.setEnabled(false);
		GridBagConstraints gbc_btn_InpatientSave = new GridBagConstraints();
		gbc_btn_InpatientSave.weighty = 0.2;
		gbc_btn_InpatientSave.weightx = 0.5;
		gbc_btn_InpatientSave.fill = GridBagConstraints.BOTH;
		gbc_btn_InpatientSave.insets = new Insets(0, 0, 0, 5);
		gbc_btn_InpatientSave.gridx = 0;
		gbc_btn_InpatientSave.gridy = 10;
		pan_InpatientInfo.add(btn_InpatientSave, gbc_btn_InpatientSave);
		btn_InpatientSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_InpatientSaveactionPerformed(evt, "I");
			}
		});

		btn_InpatientClose = new JButton(paragraph.getString("CLOSE"));
		GridBagConstraints gbc_btn_InpatientClose = new GridBagConstraints();
		gbc_btn_InpatientClose.weighty = 0.2;
		gbc_btn_InpatientClose.weightx = 0.5;
		gbc_btn_InpatientClose.fill = GridBagConstraints.BOTH;
		gbc_btn_InpatientClose.gridx = 1;
		gbc_btn_InpatientClose.gridy = 10;
		pan_InpatientInfo.add(btn_InpatientClose, gbc_btn_InpatientClose);
		btn_InpatientClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// reuse Clinic Close
				btn_ClinicCloseactionPerformed(evt);
			}
		});

		scrollPane_Bed = new JScrollPane();
		GridBagConstraints gbc_scrollPane_Bed = new GridBagConstraints();
		gbc_scrollPane_Bed.weighty = 0.1;
		gbc_scrollPane_Bed.weightx = 0.55;
		gbc_scrollPane_Bed.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_Bed.gridx = 1;
		gbc_scrollPane_Bed.gridy = 0;
		pan_tabInpatientInfo.add(scrollPane_Bed, gbc_scrollPane_Bed);

		tab_BedList = new JTable();
		tab_BedList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_BedListMouseClicked(evt);
			}
		});
		tab_BedList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_BedListKeyPressed(evt);
			}
		});

		scrollPane_Bed.setViewportView(tab_BedList);

		pan_tabHistory = new JPanel();
		pan_ClinicOrInpatient.addTab(paragraph.getString("TITLEHISTORYRECORD"),
				pan_tabHistory);

		pan_tabClinicInfo.setLayout(gbl_pan_tabClinicInfo);
		GridBagLayout gbl_pan_tabHistory = new GridBagLayout();
		gbl_pan_tabHistory.columnWidths = new int[] { 113, 109, 405, 0 };
		gbl_pan_tabHistory.rowHeights = new int[] { 20, 29, 23, 228, 0 };
		gbl_pan_tabHistory.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_tabHistory.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		pan_tabHistory.setLayout(gbl_pan_tabHistory);

		lbl_HistoryDate = new JLabel(paragraph.getString("DATE"));
		GridBagConstraints gbc_lbl_HistoryDate = new GridBagConstraints();
		gbc_lbl_HistoryDate.weighty = 0.1;
		gbc_lbl_HistoryDate.weightx = 0.3;
		gbc_lbl_HistoryDate.anchor = GridBagConstraints.SOUTH;
		gbc_lbl_HistoryDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lbl_HistoryDate.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_HistoryDate.gridwidth = 2;
		gbc_lbl_HistoryDate.gridx = 0;
		gbc_lbl_HistoryDate.gridy = 0;
		pan_tabHistory.add(lbl_HistoryDate, gbc_lbl_HistoryDate);

		scrollPane_History = new JScrollPane();
		GridBagConstraints gbc_scrollPane_History = new GridBagConstraints();
		gbc_scrollPane_History.weightx = 0.7;
		gbc_scrollPane_History.weighty = 1.0;
		gbc_scrollPane_History.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_History.gridheight = 4;
		gbc_scrollPane_History.gridx = 2;
		gbc_scrollPane_History.gridy = 0;
		pan_tabHistory.add(scrollPane_History, gbc_scrollPane_History);

		tab_HistoryList = new JTable();
		tab_HistoryList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_HistoryListMouseClicked();
			}
		});
		tab_HistoryList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_HistoryListMouseClicked();
			}
		});
		scrollPane_History.setViewportView(tab_HistoryList);

		pan_HistoryDate = new cc.johnwu.date.DateChooser();
		pan_HistoryDate.setParentFrame(this);
		GridBagConstraints gbc_pan_HistoryDate = new GridBagConstraints();
		gbc_pan_HistoryDate.anchor = GridBagConstraints.NORTH;
		gbc_pan_HistoryDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_pan_HistoryDate.insets = new Insets(0, 0, 5, 5);
		gbc_pan_HistoryDate.gridwidth = 2;
		gbc_pan_HistoryDate.weighty = 0.1;
		gbc_pan_HistoryDate.weightx = 0.3;
		gbc_pan_HistoryDate.gridx = 0;
		gbc_pan_HistoryDate.gridy = 1;
		pan_tabHistory.add(pan_HistoryDate, gbc_pan_HistoryDate);

		btn_HistoryReprint = new JButton(paragraph.getString("REPRINT"));
		btn_HistoryReprint.setEnabled(false);
		GridBagConstraints gbc_btn_HistoryReprint = new GridBagConstraints();
		gbc_btn_HistoryReprint.weighty = 0.1;
		gbc_btn_HistoryReprint.weightx = 0.15;
		gbc_btn_HistoryReprint.anchor = GridBagConstraints.NORTH;
		gbc_btn_HistoryReprint.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_HistoryReprint.insets = new Insets(0, 0, 5, 5);
		gbc_btn_HistoryReprint.gridx = 0;
		gbc_btn_HistoryReprint.gridy = 2;
		pan_tabHistory.add(btn_HistoryReprint, gbc_btn_HistoryReprint);
		btn_HistoryReprint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_HistoryReprintactionPerformed(evt);
			}
		});

		btn_HistoryCancel = new JButton(paragraph.getString("CANCEL"));
		btn_HistoryCancel.setEnabled(false);
		GridBagConstraints gbc_btn_HistoryCancel = new GridBagConstraints();
		gbc_btn_HistoryCancel.weighty = 0.1;
		gbc_btn_HistoryCancel.weightx = 0.15;
		gbc_btn_HistoryCancel.anchor = GridBagConstraints.NORTH;
		gbc_btn_HistoryCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_HistoryCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btn_HistoryCancel.gridx = 1;
		gbc_btn_HistoryCancel.gridy = 2;
		pan_tabHistory.add(btn_HistoryCancel, gbc_btn_HistoryCancel);
		btn_HistoryCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_HistoryCancelactionPerformed(evt);
			}
		});

		btn_HistoryClose = new JButton(paragraph.getString("CLOSE"));
		GridBagConstraints gbc_btn_HistoryClose = new GridBagConstraints();
		gbc_btn_HistoryClose.weighty = 0.1;
		gbc_btn_HistoryClose.weightx = 0.3;
		gbc_btn_HistoryClose.anchor = GridBagConstraints.NORTH;
		gbc_btn_HistoryClose.fill = GridBagConstraints.HORIZONTAL;
		gbc_btn_HistoryClose.insets = new Insets(0, 0, 0, 5);
		gbc_btn_HistoryClose.gridwidth = 2;
		gbc_btn_HistoryClose.gridx = 0;
		gbc_btn_HistoryClose.gridy = 3;
		pan_tabHistory.add(btn_HistoryClose, gbc_btn_HistoryClose);
		btn_HistoryClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btn_ClinicCloseactionPerformed(evt);
			}
		});

		pan_ClinicOrInpatient
				.addChangeListener(new javax.swing.event.ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {

						new Thread(new Runnable() {
							@Override
							public void run() {
								initClinicInfo();
								initInpatientInfo();
								initHistory();
							}
						}).start();
					}
				});
		// End of init GUI

		new Thread(new Runnable() {

			@Override
			public void run() {
				initClinicInfo();
				initInpatientInfo();
				initHistory();
				reLoad();
			}
		}).start();

	}

	private void initHistory() {
		btn_HistoryReprint.setEnabled(false);
		btn_HistoryCancel.setEnabled(false);
		refreshHistory();
	}

	private void btn_PatientSearchactionPerformed(ActionEvent evt) {
		String target = txt_PatientSearch.getText();
		patientsInfo = patientsInfoDao.getPatientsBySearch(target);
		if (patientsInfo.size() != 0) {
			tab_PatientList.setModel(new PatientsInfoJPATable(patientsInfo));
		} else {
			tab_PatientList.setModel(new DefaultTableModel(
					new String[][] { { "No Information." } },
					new String[] { "Message" }) {
				private static final long serialVersionUID = 5657385170938938827L;

				@Override
				public boolean isCellEditable(int row, int column) {
					// all cells false
					return false;
				}
			});
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

	private void btn_EditPatientactionPerformed(ActionEvent evt) {
		showImage(null, "");
		new Frm_PatientMod(this, selectedPatientGUID).setVisible(true);
		this.setEnabled(false);
	}

	private void tab_PatientListKeyPressed(java.awt.event.KeyEvent evt) {
		tab_PatientListMouseClicked(null);
	}

	private void setPatientInfo(String p_no) {
		String sql = "SELECT * " + "FROM patients_info " + "WHERE exist = 1 "
				+ "AND p_no = '" + p_no + "' ";
		showImage(null, "");
		ResultSet rs = null;

		try {
			rs = DBC.executeQuery(sql);
			rs.next();
			selectedPatientWithBirthdayInfo = false;
			if (rs.getString("birth") != null) {
				selectedPatientWithBirthdayInfo = true;
			}
			selectedPatientGUID = rs.getString("p_no");
			this.lbl_PatientNo.setText(paragraph.getString("PATIENTNO")
					+ rs.getString("p_no"));
			this.lbl_NHISNo.setText(paragraph.getString("TITLENHISNO")
					+ rs.getString("nhis_no"));
			this.lbl_NIANo.setText(paragraph.getString("TITLENIANO")
					+ rs.getString("nia_no"));
			this.lbl_FirstName.setText(paragraph.getString("TITLEFIRSTNAME")
					+ rs.getString("firstname"));
			this.lbl_LastName.setText(paragraph.getString("TITLELASTNAME")
					+ rs.getString("lastname"));
			this.lbl_Birthday.setText(paragraph.getString("TITLEBIRTHDAY")
					+ rs.getString("birth"));
			this.lbl_Age.setText(paragraph.getString("TITLEAGE")
					+ ((!selectedPatientWithBirthdayInfo || lbl_Birthday
							.getText().isEmpty()) ? "null" : DateMethod
							.getAgeWithMonth(rs.getDate("birth"))));
			this.lbl_Gender.setText(paragraph.getString("GENDER")
					+ rs.getString("gender"));
			this.lbl_BloodType.setText(paragraph.getString("TITLEBLOODTYPE")
					+ rs.getString("bloodtype"));
			this.lbl_Height.setText(paragraph.getString("TITLEHEIGHT")
					+ rs.getString("height"));
			this.lbl_Weight.setText(paragraph.getString("TITLEWEIGHT")
					+ rs.getString("weight"));
			if (rs.getString("dead_guid") != null) {
				dead = true;
			} else {
				dead = false;
			}
			btn_EditPatient.setEnabled(true);
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

	private void tab_PatientListMouseClicked(java.awt.event.MouseEvent evt) {
		if (tab_PatientList.getSelectedRow() < 0
				|| tab_PatientList.getColumnCount() == 1) {
			return;
		} else {
			selectedPatientGUID = (String) tab_PatientList.getValueAt(
					tab_PatientList.getSelectedRow(), 0);
			setPatientInfo(selectedPatientGUID);
			refreshInpatientInfo();
		}
	}

	private void tab_HistoryListMouseClicked() {
		if (tab_HistoryList.getSelectedRow() < 0
				|| tab_HistoryList.getColumnCount() == 1) {
			return;
		} else {
			selectedRegGUID = (String) tab_HistoryList.getValueAt(
					tab_HistoryList.getSelectedRow(), 0);
			btn_HistoryReprint.setEnabled(true);
			btn_HistoryCancel.setEnabled(true);
		}
	}

	private void tab_ClinicListKeyPressed(java.awt.event.KeyEvent evt) {
		tab_ClinicListMouseClicked(null);
	}

	private void tab_ClinicListMouseClicked(java.awt.event.MouseEvent evt) {
		if (tab_ClinicList.getSelectedRow() < 0
				|| tab_ClinicList.getColumnCount() == 1) {
			return;
		} else {
			selectedShiftGUID = (String) tab_ClinicList.getValueAt(
					tab_ClinicList.getSelectedRow(), 0);

			String sqlDocAndRoom = "SELECT poli_room.name, staff_info.s_id, staff_info.s_no,"
					+ "concat(staff_info.firstname,'  ',staff_info.lastname) AS 'Doctor' "
					+ "FROM shift_table, staff_info, poli_room "
					+ "WHERE shift_table.guid = '"
					+ selectedShiftGUID
					+ "' "
					+ "AND shift_table.s_id = staff_info.s_id "
					+ "AND shift_table.room_guid = poli_room.guid ";
			String sqlWait = "SELECT COUNT(guid) AS count "
					+ "FROM registration_info " + "WHERE shift_guid = '"
					+ selectedShiftGUID + "' " + "AND finish = 'W' ";
			showImage(null, "");
			ResultSet rs = null;
			try {
				rs = DBC.executeQuery(sqlDocAndRoom);
				rs.next();
				this.lbl_Doctor.setText(paragraph.getString("TITLEDOCTORNAME")
						+ rs.getString("Doctor"));
				this.lbl_Room.setText(paragraph.getString("TITLEROOM")
						+ rs.getString("poli_room.name"));

				selectedDoctorID = rs.getString("staff_info.s_id");
				selectedDoctorNo = rs.getString("staff_info.s_no");
				selectedDoctorName = rs.getString("Doctor");
				rs = DBC.executeQuery(sqlWait);
				rs.next();
				this.lbl_WaitingNo.setText(paragraph.getString("TITLEWAITNO")
						+ rs.getString("count"));
				if (dead == false) {
					btn_ClinicSave.setEnabled(true);
					btn_MobSave.setEnabled(true);
				}
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

	private void btn_ClinicCloseactionPerformed(ActionEvent evt) {
		if (parentFrame == null)
			new Frm_Main().setVisible(true);
		else
			parentFrame.setVisible(true);
		this.dispose();
	}

	private void initClinicInfo() {
		// Date chooser initialed in constructor
		// Init Division
		ResultSet rs = null;
		try {
			this.cbb_Division.removeAllItems();
			this.cbb_Division.addItem(paragraph.getString("ALL"));
			String sql = "SELECT name FROM policlinic";
			rs = DBC.executeQuery(sql);
			while (rs.next()) {
				this.cbb_Division.addItem(rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Init Shift
		this.cbb_Shift.addItem(paragraph.getString("ALL"));
		this.cbb_Shift.addItem(paragraph.getString("MORNING"));
		this.cbb_Shift.addItem(paragraph.getString("NOON"));
		this.cbb_Shift.addItem(paragraph.getString("NIGHT"));
		this.cbb_Shift.setSelectedIndex(DateMethod.getNowShiftNum());
		refreshClinicInfo();
	}

	private void refreshHistory() {
		String dateStart = "'" + pan_HistoryDate.getValue() + " 00:00:00'";
		String dateEnd = "'" + pan_HistoryDate.getValue() + " 23:59:59'";
		String sql = "SELECT "
				+ "registration_info.guid, "
				+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS 'Name', "
				+ "policlinic.name, "
				+ "shift_table.shift, "
				+ "policlinic.room_num, "
				+ "concat(staff_info.firstname,'  ',staff_info.lastname) AS 'Doctor' "
				+ "FROM "
				+ "registration_info, patients_info, policlinic, shift_table, staff_info, poli_room "
				+ "WHERE patients_info.p_no=registration_info.p_no "
				+ "AND registration_info.shift_guid=shift_table.guid "
				+ "AND shift_table.room_guid=poli_room.guid "
				+ "AND poli_room.poli_guid=policlinic.guid "
				+ "AND shift_table.s_id=staff_info.s_id "
				+ "AND registration_info.type='O' "
				+ "AND registration_info.finish='W' "
				+ "AND registration_info.reg_time > " + dateStart + " "
				+ "AND registration_info.reg_time < " + dateEnd;
		ResultSet rs = null;
		try {
			System.out.print(sql + "\n");
			rs = DBC.executeQuery(sql);
			rs.last();
			if (rs.getRow() == 0) {
				tab_HistoryList.setModel(new DefaultTableModel(
						new String[][] { { "No Information." } },
						new String[] { "Message" }) {

					/**
							 * 
							 */
					private static final long serialVersionUID = 3852489926998119919L;
				});
			} else {
				ResultSetMetaData rsMetaData = rs.getMetaData();
				Integer rowCount = rs.getRow();

				String[] historyHeader = new String[] {
						paragraph.getString("REGISTRATION"),
						paragraph.getString("NAME"),
						paragraph.getString("DEPARTMENT"),
						paragraph.getString("SHIFT"),
						paragraph.getString("ROOM"),
						paragraph.getString("DOCTOR") };
				String[][] HistoryMatrix = new String[rowCount][rsMetaData
						.getColumnCount()];
				int i;
				int j;
				rs.beforeFirst();
				for (i = 0; i < rowCount; i++) {
					rs.next();
					for (j = 0; j < rsMetaData.getColumnCount(); j++) {
						HistoryMatrix[i][j] = rs.getString(j + 1);
					}
					if (HistoryMatrix[i][3] == "1") {
						HistoryMatrix[i][3] = "Morning";
					} else if (HistoryMatrix[i][3] == "2") {
						HistoryMatrix[i][3] = "Noon";
					} else {
						HistoryMatrix[i][3] = "Night";
					}
				}
				tab_HistoryList.setModel(new DefaultTableModel(HistoryMatrix,
						historyHeader) {

					/**
							 * 
							 */
					private static final long serialVersionUID = -1149443021733251964L;
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void btn_HistoryReprintactionPerformed(ActionEvent evt) {
		printRegClinic(selectedRegGUID);
	}

	private void btn_HistoryCancelactionPerformed(ActionEvent evt) {
		String sql = "UPDATE registration_info SET registration_info.finish='C' "
				+ "WHERE registration_info.guid='" + selectedRegGUID + "'";
		try {
			DBC.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			refreshHistory();
		}
	}

	private void refreshClinicInfo() {
		ResultSet rs = null;
		String sql = "SELECT shift_table.guid, policlinic.name AS "
				+ paragraph.getString("DIVISION")
				+ ", "
				+ "CASE shift_table.shift "
				+ "WHEN '1' THEN 'Morning' "
				+ "WHEN '2' THEN 'Afternoon' "
				+ "WHEN '3' THEN 'Night' "
				+ "ELSE 'All Night'"
				+ "END 'Shift', "
				+ "concat(staff_info.firstname,'  ',staff_info.lastname) AS 'Doctor' "
				+ "FROM staff_info,shift_table,policlinic,poli_room "
				+ "WHERE shift_table.shift_date = '"
				+ pan_ClinicDate.getValue() + "' "
				+ "AND shift_table.s_id = staff_info.s_id "
				+ "AND shift_table.room_guid = poli_room.guid "
				+ "AND poli_room.poli_guid = policlinic.guid ";
		if (cbb_Division.getSelectedItem() != null
				&& !cbb_Division.getSelectedItem().toString().equals("All")) {
			sql = sql + "AND policlinic.name = '"
					+ cbb_Division.getSelectedItem().toString() + "' ";
		}
		if (cbb_Shift.getSelectedItem() != null
				&& !cbb_Shift.getSelectedItem().toString().equals("All")) {
			sql = sql + "AND shift_table.shift = '"
					+ cbb_Shift.getSelectedIndex() + "' ";
		}
		try {
			rs = DBC.executeQuery(sql);
			// convert rs to model and tab_ClinicList.setModel
			String[] clinicHeader = { paragraph.getString("SHIFTID"),
					paragraph.getString("DIVISION"),
					paragraph.getString("SHIFT"), paragraph.getString("DOCTOR") };
			rs.last();
			if (rs.getRow() == 0) {
				tab_ClinicList.setModel(new DefaultTableModel(
						new String[][] { { "No Information." } },
						new String[] { "Message" }) {
					/**
							 * 
							 */
					private static final long serialVersionUID = -2187364991003967906L;

					@Override
					public boolean isCellEditable(int row, int column) {
						// all cells false
						return false;
					}
				});
			} else {
				ResultSetMetaData rsMetaData = rs.getMetaData();
				Integer rowCount = rs.getRow();
				String[][] clinicMatrix = new String[rowCount][rsMetaData
						.getColumnCount()];
				int i;
				int j;
				rs.beforeFirst();
				for (i = 0; i < rowCount; i++) {
					rs.next();
					for (j = 0; j < rsMetaData.getColumnCount(); j++) {
						clinicMatrix[i][j] = rs.getString(j + 1);
					}
				}
				tab_ClinicList.setModel(new DefaultTableModel(clinicMatrix,
						clinicHeader) {
					private static final long serialVersionUID = 5796864181214611950L;

					@Override
					public boolean isCellEditable(int row, int column) {
						// all cells false
						return false;
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (DateMethod.getDaysRange(pan_ClinicDate.getValue()) > 0
				|| (DateMethod.getDaysRange(pan_ClinicDate.getValue()) == 0 && cbb_Shift
						.getSelectedIndex() > DateMethod.getNowShiftNum())) {
			this.lbl_RegistrationMethod.setText(paragraph
					.getString("TITLEVISITS")
					+ paragraph.getString("RESERVATION"));
		} else {
			this.lbl_RegistrationMethod
					.setText(paragraph.getString("TITLEVISITS")
							+ paragraph.getString("LOCALITY"));
		}
	}

	private void btn_ClinicSaveactionPerformed(java.awt.event.ActionEvent evt,
			String type) {
		String sql = "";
		if (this.lbl_RegistrationMethod.getText().replace(
				paragraph.getString("TITLEVISITS"), "") == paragraph
				.getString("RESERVATION")) {
			type = "R";
		}
		String newRegUUID = UUID.randomUUID().toString();
		try {
			sql = "INSERT INTO registration_info SELECT " + "'"
					+ newRegUUID
					+ "',"
					+ // guid
					"NULL,"
					+ // bed_guid
					"'"
					+ selectedPatientGUID
					+ "'," // p_no
					+ "now()," // reg_time
					+ "NULL," // gis_guid
					+ " '"
					+ selectedShiftGUID
					+ "', " // shift_guid
					+ // first visit start
					"(SELECT CASE "
					+ "WHEN (SELECT COUNT(*) from registration_info WHERE p_no='"
					+ selectedPatientGUID
					+ "')=0 "
					+ "THEN 'Y' "
					+ "ELSE 'N' END),"
					+ // first visit end
					"NULL,"
					+ "'"
					+ type
					+ "',"
					+ // type
					"NULL,"
					+ "'W',"
					+ // finish
					"NULL,"
					+ "NULL,"
					+ "100,"
					+ // reg_cost
					"100,"
					+ // dia_cost
					"NULL,"
					+ // registration_payment
					"'Z',"
					+ // diagnosis_payment
					"'Z',"
					+ // pharmacy_payment
					"'Z',"
					+ // lab_payment
					"'Z',"
					+ // radiology_payment
					"'Z',"
					+ // bed_payment
						// visit_no_start
					"(SELECT COUNT(*) from registration_info "
					+ "WHERE shift_guid='"
					+ selectedShiftGUID
					+ "')+1,"
					+
					// visit_no_end
					"NULL,"
					+
					// touchtime start
					"RPAD((SELECT CASE "
					+ "WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') "
					+ "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),COUNT(B.touchtime)) "
					+ "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') "
					+ "END touchtime "
					+ "FROM (SELECT touchtime FROM registration_info) AS B "
					+ "WHERE B.touchtime LIKE concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000'), "
					+
					// touchtime end
					"NULL," + "NULL;";

			DBC.executeUpdate(sql);

			sql = "SELECT visits_no, guid FROM registration_info "
					+ "WHERE shift_guid = '"
					+ tab_ClinicList.getValueAt(
							tab_ClinicList.getSelectedRow(), 0)
					+ "' "
					+ "AND p_no ='"
					+ selectedPatientGUID
					+ "' "
					+ "AND finish='W' "
					+ "AND touchtime LIKE concat(DATE_FORMAT(now(),'%Y%m%d%H%i'),'%') ";
			ResultSet rs = DBC.executeQuery(sql);

			if (rs.next()) {
				m_Number = rs.getInt(1);
				rs.getString("guid");
			}

			String html = "<html><font size='6'>";

			sql = "INSERT INTO gis(guid, gis, reg_guid, address) "
					+ "VALUES(uuid()," + "'TempGIS'," + "'" + newRegUUID + "',"
					+ "'TempCOUNTRY'" + " ) ";
			DBC.executeUpdate(sql);

			sql = "SELECT p_no, firstname, lastname, gender, birth, '"
					+ lbl_Age.getText().replace(
							paragraph.getString("TITLEAGE"), "")
					+ "' AS age, '"
					+ pan_ClinicDate.getValue()
					+ "' AS date, '"
					+ lbl_Room.getText().replace(
							paragraph.getString("TITLEROOM"), "")
					+ "' AS clinic, "
					+ "'"
					+ cbb_Shift.getSelectedItem().toString()
					+ "' AS shift, "
					+ "'"
					+ cbb_Division.getSelectedItem().toString()
					+ "' AS dept, "
					+ "'"
					+ m_Number
					+ "' AS waitno, "
					+ "'"
					+ selectedDoctorName
					+ "' AS doctor,"
					+ "'"
					+ lbl_RegistrationMethod.getText().replace(
							paragraph.getString("TITLEVISITS"), "")
					+ "' AS type " + "FROM patients_info "
					+ "WHERE exist = 1 AND p_no = '" + selectedPatientGUID
					+ "' ";

			rs = DBC.executeQuery(sql);
			rs.next();
			JOptionPane.showMessageDialog(
					null,
					html
							+ paragraph.getString("PATIENTNAME")
							+ this.lbl_FirstName.getText().replace(
									paragraph.getString("TITLEFIRSTNAME"), "")
							+ " "
							+ this.lbl_LastName.getText().replace(
									paragraph.getString("TITLELASTNAME"), "")
							+ " \n" + html + this.lbl_Birthday.getText()
							+ " \n" + html + this.lbl_Age.getText() + " \n"
							+ html + this.lbl_Gender.getText() + " \n"
							+ "***************************************\n"
							+ html + paragraph.getString("DATE")
							+ this.pan_ClinicDate.getValue() + "\n" + html
							+ paragraph.getString("TITLEPOLICLINIC")
							+ this.cbb_Division.getSelectedItem().toString()
							+ "\n" + html + paragraph.getString("TITLESHIFT")
							+ this.cbb_Shift.getSelectedItem().toString()
							+ "\n" + html + this.lbl_Doctor.getText() + "\n"
							+ html + this.lbl_Room.getText() + "\n" + html
							+ paragraph.getString("TITLEWAITNO") + m_Number
							+ "\n" + html
							+ "***************************************",
					paragraph.getString("REGISTRATIONSUCCESSFUL"),
					JOptionPane.DEFAULT_OPTION);
			DBC.closeConnection(rs);

			reLoad();
			tab_PatientList.setModel(new DefaultTableModel(
					new String[][] { { "You need to give more keywords." } },
					new String[] { "Message" }) {
				private static final long serialVersionUID = -8196430493734834613L;

				@Override
				public boolean isCellEditable(int row, int column) {
					// all cells false
					return false;
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		printRegClinic(newRegUUID);
	}

	private void initInpatientInfo() {
		// Date chooser initialized in constructor
		// Init Division
		ResultSet rs = null;
		try {

			this.cbb_InpatientType.removeAllItems();
			this.cbb_InpatientType.addItem(paragraph
					.getString("ONDESKINPATIENT"));
			this.cbb_InpatientType.addItem(paragraph
					.getString("MAKERESERVATION"));
			this.cbb_InpatientType.addItem(paragraph
					.getString("RESERVEDCHECKIN"));
			this.cbb_InpatientType.setSelectedIndex(0);

			this.cbb_InpatientDivision.removeAllItems();
			this.cbb_InpatientDivision.addItem(paragraph.getString("ALL"));
			String sql = "SELECT name FROM policlinic";
			rs = DBC.executeQuery(sql);
			while (rs.next()) {
				this.cbb_InpatientDivision.addItem(rs.getString("name"));
			}

			sql = "SELECT concat(staff_info.firstname,'  ',staff_info.lastname) AS 'Doctor' "
					+ " FROM staff_info " + "WHERE grp_name='Doctor'";
			rs = DBC.executeQuery(sql);
			this.cbb_InpatientDoctor.removeAllItems();
			while (rs.next()) {
				this.cbb_InpatientDoctor.addItem(rs.getString("Doctor"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.showImage(null, "");
		// Init Bed
		refreshInpatientInfo();
	}

	private void refreshInpatientInfo() {
		if (cbb_InpatientType.getSelectedIndex() == 0
				|| cbb_InpatientType.getSelectedIndex() == 1) {
			ResultSet rs = null;
			String plannedCheckInTime = "'" + pan_CheckInDate.getValue()
					+ " 00:00:00'";
			String plannedCheckOutTime = "'" + pan_CheckOutDate.getValue()
					+ " 23:59:59'";
			String sql = "SELECT bed_code.guid, bed_code.description, policlinic.name "
					+ "FROM bed_code, policlinic "
					+ "WHERE bed_code.guid NOT IN "
					+ "("
					+ "SELECT distinct bed_record.bed_guid FROM bed_record "
					+ "WHERE (" + "(" + "bed_record.plannedCheckOutTime > "
					+ plannedCheckInTime
					+ " AND bed_record.status = 'N'"
					+ ")"
					+ " OR "
					+ "("
					+ "("
					+ "("
					+ "bed_record.plannedCheckInTime > "
					+ plannedCheckInTime
					+ " AND "
					+ "bed_record.plannedCheckInTime < "
					+ plannedCheckOutTime
					+ ")"
					+ " OR "
					+ "("
					+ "bed_record.plannedCheckOutTime > "
					+ plannedCheckInTime
					+ " AND "
					+ "bed_record.plannedCheckOutTime < "
					+ plannedCheckOutTime
					+ ")"
					+ " OR "
					+ "("
					+ "bed_record.plannedCheckInTime < "
					+ plannedCheckInTime
					+ " AND "
					+ "bed_record.plannedCheckOutTime > "
					+ plannedCheckOutTime
					+ ")"
					+ ")"
					+ " AND bed_record.status = 'R'"
					+ ")"
					+ ")"
					+ ")"
					+ " AND policlinic.guid = bed_code.poli_guid";

			if (cbb_InpatientDivision.getSelectedItem() != null
					&& !cbb_InpatientDivision.getSelectedItem().toString()
							.equals("All")) {
				sql = sql + " AND policlinic.name = '"
						+ cbb_InpatientDivision.getSelectedItem().toString()
						+ "' " + "AND policlinic.guid = bed_code.poli_guid";
			}
			try {
				rs = DBC.executeQuery(sql);
				// convert rs to model and tab_ClinicList.setModel
				String[] bedHeader = { paragraph.getString("COL_BED_NO"),
						paragraph.getString("BEDDESCRIPTION"),
						paragraph.getString("DIVISION") };
				rs.last();
				if (rs.getRow() == 0) {
					tab_BedList.setModel(new DefaultTableModel(
							new String[][] { { "No Information." } },
							new String[] { "Message" }) {
						/**
								 * 
								 */
						private static final long serialVersionUID = 7065751750381425512L;

						@Override
						public boolean isCellEditable(int row, int column) {
							// all cells false
							return false;
						}
					});
				} else {
					ResultSetMetaData rsMetaData = rs.getMetaData();
					Integer rowCount = rs.getRow();
					String[][] bedMatrix = new String[rowCount][rsMetaData
							.getColumnCount()];
					int i;
					int j;
					rs.beforeFirst();
					for (i = 0; i < rowCount; i++) {
						rs.next();
						for (j = 0; j < rsMetaData.getColumnCount(); j++) {
							bedMatrix[i][j] = rs.getString(j + 1);
						}
					}
					tab_BedList.setModel(new DefaultTableModel(bedMatrix,
							bedHeader) {
						private static final long serialVersionUID = -4588820734236675321L;

						@Override
						public boolean isCellEditable(int row, int column) {
							// all cells false
							return false;
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBC.closeConnection(rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			ResultSet rs = null;
			try {
				String sql = "SELECT bed_record.guid, "
						+ "bed_record.plannedCheckInTime, "
						+ "bed_record.plannedCheckOutTime FROM bed_record "
						+ "WHERE bed_record.status='R' "
						+ "AND UPPER(bed_record.p_no) LIKE UPPER('%"
						+ selectedPatientGUID.replace(" ", "%") + "%') ";
				System.out.print(sql);
				rs = DBC.executeQuery(sql);

				String[] bedHeader = { paragraph.getString("COL_BED_NO"),
						paragraph.getString("CHECKINDATE"),
						paragraph.getString("CHECKOUTDATE") };
				rs.last();
				if (rs.getRow() == 0) {
					tab_BedList.setModel(new DefaultTableModel(
							new String[][] { { "No Information." } },
							new String[] { "Message" }) {
						/**
								 * 
								 */
						private static final long serialVersionUID = -2817170893774324590L;

						@Override
						public boolean isCellEditable(int row, int column) {
							// all cells false
							return false;
						}
					});
				} else {
					ResultSetMetaData rsMetaData = rs.getMetaData();
					Integer rowCount = rs.getRow();
					String[][] bedMatrix = new String[rowCount][rsMetaData
							.getColumnCount()];
					int i;
					int j;
					rs.beforeFirst();
					for (i = 0; i < rowCount; i++) {
						rs.next();
						for (j = 0; j < rsMetaData.getColumnCount(); j++) {
							bedMatrix[i][j] = rs.getString(j + 1);
						}
					}
					tab_BedList.setModel(new DefaultTableModel(bedMatrix,
							bedHeader) {
						/**
								 * 
								 */
						private static final long serialVersionUID = -6133434111591529583L;

						@Override
						public boolean isCellEditable(int row, int column) {
							// all cells false
							return false;
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBC.closeConnection(rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void refreshInpatientDoctorList() {
		ResultSet rs = null;
		try {
			String sql = "SELECT distinct concat(staff_info.firstname,'  ',staff_info.lastname) AS 'Doctor' "
					+ "FROM shift_table, staff_info, policlinic, poli_room "
					+ "WHERE "
					+ "shift_table.s_id = staff_info.s_id "
					+ "AND shift_table.room_guid = poli_room.guid "
					+ "AND poli_room.poli_guid = policlinic.guid ";
			if (cbb_InpatientDivision.getSelectedItem() != null
					&& !cbb_InpatientDivision.getSelectedItem().toString()
							.equals("All")) {
				sql = sql + "AND policlinic.name = '"
						+ cbb_InpatientDivision.getSelectedItem().toString()
						+ "' ";
			}
			System.out.print(sql + "\n");
			rs = DBC.executeQuery(sql);
			this.cbb_InpatientDoctor.removeAllItems();
			while (rs.next()) {
				this.cbb_InpatientDoctor.addItem(rs.getString("Doctor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void tab_BedListKeyPressed(java.awt.event.KeyEvent evt) {
		tab_BedListMouseClicked(null);
	}

	private void tab_BedListMouseClicked(java.awt.event.MouseEvent evt) {
		if (tab_BedList.getSelectedRow() < 0
				|| tab_BedList.getColumnCount() == 1) {
			return;
		} else if (cbb_InpatientType.getSelectedIndex() != 2) {
			selectedBedGUID = (String) tab_BedList.getValueAt(
					tab_BedList.getSelectedRow(), 0);
			selectedBedDevision = (String) tab_BedList.getValueAt(
					tab_BedList.getSelectedRow(), 2);
			btn_InpatientSave.setEnabled(true);
		} else if (cbb_InpatientType.getSelectedIndex() == 2) {
			ResultSet rs = null;
			String sql = "SELECT distinct policlinic.name,"
					+ "concat(staff_info.firstname,'  ',staff_info.lastname) AS 'Doctor'"
					+ " FROM bed_record,bed_code,policlinic,staff_info "
					+ " WHERE bed_record.guid='"
					+ (String) tab_BedList.getValueAt(
							tab_BedList.getSelectedRow(), 0) + "'"
					+ " AND bed_record.bed_guid=bed_code.guid "
					+ " AND bed_code.poli_guid=policlinic.guid "
					+ " AND bed_record.mainDr_no=staff_info.s_no";
			try {
				Integer cachedRow = tab_BedList.getSelectedRow();
				rs = DBC.executeQuery(sql);
				rs.next();
				cbb_InpatientDivision.setSelectedItem(rs
						.getString("policlinic.name"));
				cbb_InpatientDoctor.setSelectedItem(rs.getString("Doctor"));
				tab_BedList.setRowSelectionInterval(cachedRow, cachedRow);
				String plannedCheckInDate = (String) tab_BedList.getValueAt(
						tab_BedList.getSelectedRow(), 1);
				plannedCheckInDate = plannedCheckInDate.split(" ")[0];
				plannedCheckInDate = plannedCheckInDate.split("-")[2] + "-"
						+ plannedCheckInDate.split("-")[1] + "-"
						+ plannedCheckInDate.split("-")[0];
				pan_CheckInDate.setValue(plannedCheckInDate);
				String plannedCheckOutDate = (String) tab_BedList.getValueAt(
						tab_BedList.getSelectedRow(), 2);
				plannedCheckOutDate = plannedCheckOutDate.split(" ")[0];
				plannedCheckOutDate = plannedCheckOutDate.split("-")[2] + "-"
						+ plannedCheckOutDate.split("-")[1] + "-"
						+ plannedCheckOutDate.split("-")[0];
				pan_CheckOutDate.setValue(plannedCheckOutDate);
				btn_InpatientSave.setEnabled(true);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DBC.closeConnection(rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void btn_InpatientSaveactionPerformed(
			java.awt.event.ActionEvent evt, String type) {
		m_Number = 0;
		ResultSet rs = null;
		String sql = "";
		String newBedRecordUUID = UUID.randomUUID().toString();
		String newRegUUID = UUID.randomUUID().toString();
		String selectedType = "";
		if (cbb_InpatientType.getSelectedIndex() == 0) {
			selectedType = "N";
		} else if (cbb_InpatientType.getSelectedIndex() == 1) {
			selectedType = "R";
		} else {
			selectedType = "Checkin";
		}
		try {
			sql = "SELECT bed_record.guid FROM bed_record WHERE bed_record.p_no='"
					+ selectedPatientGUID + "' AND bed_record.status='N'";
			rs = DBC.executeQuery(sql);
			if (rs.next()) {
				JOptionPane.showMessageDialog(new Frame(),
						paragraph.getString("ERRORALREADYINPATIENT"));
				return;
			}

			if (selectedType == "N" || selectedType == "R") {
				// get doctor No
				sql = "SELECT staff_info.s_no FROM staff_info WHERE "
						+ "concat(staff_info.firstname,'  ',staff_info.lastname)"
						+ "='" + selectedDoctorName + "'";
				rs = DBC.executeQuery(sql);
				rs.next();
				String doctorNo = rs.getString("staff_info.s_no");

				// Insert bed_record
				sql = "INSERT INTO bed_record SELECT '" + newBedRecordUUID
						+ "', " + " '" + selectedPatientGUID + "', " + " '"
						+ selectedBedGUID + "', " + "NULL, " + " '"
						+ selectedType + "', ";
				if (selectedType == "N") {
					sql = sql + "NULL, now(), NULL, ";
				} else if (selectedType == "R") {
					sql = sql + " '" + pan_CheckInDate.getValue() + " 00:00:00"
							+ "', NULL, NULL, ";
				}
				sql = sql + " '" + pan_CheckOutDate.getValue() + " 23:59:59"
						+ "', " + "NULL," + "'" + doctorNo
						+ "', NULL, NULL, NULL";
				DBC.executeUpdate(sql);
			} else {
				sql = "UPDATE bed_record SET bed_record.status='N',"
						+ "checkinTime=now() WHERE guid='"
						+ (String) tab_BedList.getValueAt(
								tab_BedList.getSelectedRow(), 0) + "'";
				DBC.executeUpdate(sql);
			}

			if (selectedType == "R") {
				return;
			}

			if (selectedType == "Checkin") {
				newBedRecordUUID = (String) tab_BedList.getValueAt(
						tab_BedList.getSelectedRow(), 0);
			}
			// Insert registration_info
			sql = "INSERT INTO registration_info SELECT " + "'"
					+ newRegUUID
					+ "'," // guid
					+ "'"
					+ newBedRecordUUID
					+ "', " // bed_guid
					+ "'"
					+ selectedPatientGUID
					+ "'," // p_no
					+ "now()," // reg_time
					+ "NULL," // gis_guid
					+ "NULL," // shift_guid
					// first visit start
					+ "(SELECT CASE "
					+ "WHEN (SELECT COUNT(*) from registration_info WHERE p_no='"
					+ selectedPatientGUID
					+ "')=0 "
					+ "THEN 'Y' "
					+ "ELSE 'N' END),"
					// first visit end
					+ "NULL,"
					+ "'I'," // type
					+ "NULL,"
					+ "'F'," // finish
					+ "NULL,"
					+ "NULL,"
					+ "100,"
					+ // reg_cost
					"100,"
					+ // dia_cost
					"NULL,"
					+ // registration_payment
					"'Z',"
					+ // diagnosis_payment
					"'Z',"
					+ // pharmacy_payment
					"'Z',"
					+ // lab_payment
					"'Z',"
					+ // radiology_payment
					"NULL," // bed_payment
					+ "0," // visit_no_end
					+ "NULL,"
					+
					// touchtime start
					"RPAD((SELECT CASE "
					+ "WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') "
					+ "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),COUNT(B.touchtime)) "
					+ "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') "
					+ "END touchtime "
					+ "FROM (SELECT touchtime FROM registration_info) AS B "
					+ "WHERE B.touchtime LIKE concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000'), "
					+
					// touchtime end
					"NULL," + "NULL";
			DBC.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		printRegInpatient(newRegUUID);
	}

	private void printRegClinic(String reg_guid) {
		String sql = "SELECT "
				+ "registration_info.p_no, "
				+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS 'Name', "
				+ "patients_info.gender, "
				+ "registration_info.reg_time, "
				+ "policlinic.name, "
				+ "shift_table.shift, "
				+ "policlinic.room_num, "
				+ "concat(staff_info.firstname,'  ',staff_info.lastname) AS 'Doctor' "
				+ "FROM "
				+ "registration_info, patients_info, policlinic, shift_table, staff_info, poli_room "
				+ "WHERE " + "registration_info.guid = '" + reg_guid + "' "
				+ "AND patients_info.p_no=registration_info.p_no "
				+ "AND registration_info.shift_guid=shift_table.guid "
				+ "AND shift_table.room_guid=poli_room.guid "
				+ "AND poli_room.poli_guid=policlinic.guid "
				+ "AND shift_table.s_id=staff_info.s_id";
		System.out.print(sql);
		ResultSet rs = null;
		String[] regInfo = null;
		try {
			rs = DBC.executeQuery(sql);
			rs.next();
			String strShift;
			if (rs.getString("shift_table.shift") == "1") {
				strShift = "Morning";
			} else if (rs.getString("shift_table.shift") == "2") {
				strShift = "Noon";
			} else {
				strShift = "Night";
			}
			regInfo = new String[] { reg_guid,
					rs.getString("registration_info.p_no"),
					rs.getString("Name"), rs.getString("patients_info.gender"),
					rs.getString("registration_info.reg_time"),
					rs.getString("policlinic.name"), strShift,
					"Room " + rs.getString("policlinic.room_num"),
					rs.getString("Doctor"), Integer.toString(m_Number) };
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = pj.defaultPage();
		Paper paper = new Paper();
		paper.setSize(600, 800);
		paper.setImageableArea(0, 0, 600, 800);
		pf.setPaper(paper);
		pj.setPrintable(new RegPrintable(regInfo), pf);
		try {
			pj.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printRegInpatient(String reg_guid) {
		String sql = "SELECT "
				+ "registration_info.p_no, "
				+ "concat(patients_info.firstname,'  ',patients_info.lastname) AS 'Name', "
				+ "patients_info.gender, " + "registration_info.reg_time "
				+ "FROM " + "registration_info, patients_info " + "WHERE "
				+ "registration_info.guid = '" + reg_guid + "' "
				+ "AND patients_info.p_no=registration_info.p_no";

		System.out.print(sql);
		ResultSet rs = null;
		String[] regInfo = null;
		try {
			rs = DBC.executeQuery(sql);
			rs.next();
			regInfo = new String[] {
					reg_guid,
					rs.getString("registration_info.p_no"),
					rs.getString("Name"),
					rs.getString("patients_info.gender"),
					rs.getString("registration_info.reg_time"),
					(String) tab_BedList.getValueAt(
							tab_BedList.getSelectedRow(), 2), "----", "----",
					(String) cbb_InpatientDoctor.getSelectedItem(), "----" };
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = pj.defaultPage();
		Paper paper = new Paper();
		paper.setSize(600, 800);
		paper.setImageableArea(0, 0, 600, 800);
		pf.setPaper(paper);
		pj.setPrintable(new RegPrintable(regInfo), pf);
		try {
			pj.print();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reLoad() {
		this.setEnabled(true);
		FingerPrintScanner.setParentFrame(this);
	}

	@Override
	public void onPatientMod(String pno) {

	}

	@Override
	public void onDateChanged() {
		if (pan_ClinicOrInpatient.getSelectedIndex() == 0) {
			refreshClinicInfo();
		} else if (pan_ClinicOrInpatient.getSelectedIndex() == 1) {
			refreshInpatientInfo();
		} else if (pan_ClinicOrInpatient.getSelectedIndex() == 2) {
			refreshHistory();
		}
	}

	@Override
	public void onFingerDown() {
		ResultSet rsCodes = null;
		try {
			String sql_FingerSelect = "SELECT id,template FROM fingertemplate ";
			// this.tab_PatientList.setRowSelectionInterval(0, 0);
			this.txt_PatientSearch.setText("");
			rsCodes = DBC.executeQuery(sql_FingerSelect);
			String PatientsNO = FingerPrintScanner.identify(rsCodes);
			if (PatientsNO.equals("")) {
				JOptionPane.showMessageDialog(new Frame(),
						"No Fingerprint Match");
			} else {
				this.txt_PatientSearch.setText(PatientsNO);
				btn_PatientSearchactionPerformed(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rsCodes);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void showImage(BufferedImage bufferedimage, String msg) {
		this.Frm_FingerPrintViewer.showImage(bufferedimage);
		this.Frm_FingerPrintViewer.setTitle(msg);
	}
}
