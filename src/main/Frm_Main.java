package main;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import mobilehealth.Frm_MobileHealth;
import multilingual.Language;

import org.his.JPAUtil;

import statistic.Frm_Statistic;
import system.Frm_Setting;
import cashier.Frm_CashierList;
import cc.johnwu.loading.Frm_Loading;
import cc.johnwu.login.Frm_Login;
import cc.johnwu.login.UserInfo;
import codemaintenance.Frm_TableChooser;
import errormessage.StoredErrorMessage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frm_Main extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	private Language paragraph = Language.getInstance();;

	public Frm_Main() {
		initComponents();
		// ---------迦納-------------------------
		// pan_Case.setVisible(false);
		// ------------------------------------
		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable
			@Override
			public void windowClosing(WindowEvent windowevent) {
				mnit_ExitActionPerformed(null);
			}
		});

		try {
			initPermission();
		} catch (Exception e) {
			ErrorMessage.setData(
					"Main",
					"Frm_Main",
					"initPermission()",
					e.toString().substring(e.toString().lastIndexOf(".") + 1,
							e.toString().length()));
		}
		this.setLocationRelativeTo(this);

		btn_MedicineStock.setVisible(true);
		btn_MaterialStock.setVisible(true);
		btn_BedManagement.setVisible(true);
		btn_CodeMaintenance.setVisible(true);
		btn_DepartmentManagement.setVisible(true);
		btn_PositionManagement.setVisible(true);

		initLanguage();

	}

	public void initLanguage() {
		btn_ShiftManagement.setText(paragraph.getString("SHIFT_MANAGEMENT"));

		pan_Anamnesis.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("MEDICAL_HISTORYT")));

		btn_AnamnesisReturn.setText(paragraph.getString("FORDER_RETURN"));

		btn_Anamnesis.setText(paragraph.getString("MEDICAL_HISTORYT"));

		pan_Doctor.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("CLINIC")));

		btn_Diagnosis.setText(paragraph.getString("CLINIC"));

		btn_Patients.setText(paragraph.getString("PATIENT_INFORMATION"));

		pan_Pharmacy.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("PHARMACY")));

		btn_Pharmacy.setText(paragraph.getString("PHARMACY"));

		btn_Premission.setText(paragraph.getString("PERMISSION"));

		btn_System.setText(paragraph.getString("SYSTEM"));

		btn_Statistic.setText(paragraph.getString("STATISTIC"));

		btn_Cashier.setText(paragraph.getString("CASHIER"));
		
		btn_BedCashier.setText(paragraph.getString("BED") + " " + paragraph.getString("CASHIER"));

		pan_Investgations.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("INVESTGATIONS")));

		btn_Laboratory.setText(paragraph.getString("LABORATORY"));

		btn_Radiology.setText(paragraph.getString("RADIOLOGY(X-RAY)"));

		btn_PositionManagement.setText(paragraph
				.getString("POSITION_MANAGEMENT"));

		btn_DepartmentManagement.setText(paragraph
				.getString("POLINLIC_MANAGEMENT"));

		pan_Case.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("CASE_MANAGEMENT")));

		btn_HIVCase.setText(paragraph.getString("HIV_CASE_MANAGEMENT"));
		btn_DiabetesCase.setText(paragraph
				.getString("DIABETES_CASE_MANAGEMENT"));
		btn_WoundCase.setText(paragraph.getString("WOUND_CASE_MANAGEMENT"));

		btn_Sms.setText(paragraph.getString("MOBILE_HEALTH"));

		jMenu1.setText(paragraph.getString("FILE"));

		mnit_Logout.setText(paragraph.getString("LOGOUT"));

		mnit_Exit.setText(paragraph.getString("EXIT"));

		this.btn_Inpatient.setText("InPatient");
		this.btn_MedicineStock.setText(paragraph.getString("PHARMACYSTOCK"));
		this.btn_MaterialStock.setText(paragraph.getString("MATERIALSTOCK"));
		this.btn_BedManagement.setText(paragraph.getString("BEDMANAGEMENT"));
		this.btn_CodeMaintenance
				.setText(paragraph.getString("CODEMAINTENANCE"));
		this.btn_Pharmacy.setText(paragraph.getString("PHARMACY"));
		this.btn_PositionManagement.setText(paragraph
				.getString("POSITION_MANAGEMENT"));
		this.btn_DepartmentManagement.setText(paragraph
				.getString("DEPARTMENT_MANAGEMENT"));
		this.btn_ShiftManagement.setText(paragraph
				.getString("SHIFT_MANAGEMENT"));
		this.btn_StaffManagement.setText(paragraph
				.getString("STAFF_MANAGEMENT"));

		this.btn_Anamnesis.setText(paragraph.getString("MEDICAL_HISTORYT"));
		this.btn_Patients.setText(paragraph.getString("PATIENT_INFORMATION"));
		this.btn_Diagnosis.setText(paragraph.getString("CLINIC"));
		this.btn_Register.setText(paragraph.getString("REGISTRATION"));
		this.btn_ShiftManagement.setText(paragraph
				.getString("SHIFT_MANAGEMENT"));

		this.pan_Doctor.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("DOCTOR")));
		this.pan_PersonalManagement
				.setBorder(javax.swing.BorderFactory
						.createTitledBorder(paragraph
								.getString("PERSONNELMANAGEMENT")));
		this.pan_Pharmacy.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("PHARMACY")));
		this.pan_SystemManagement.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("MANAGEMENT")));
		this.pan_Cashier.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("CASHIER")));
		this.btn_Laboratory.setText(paragraph.getString("LABORATORY"));
		this.pan_Investgations.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("INVESTGATIONS")));
		this.pan_Registration.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("REGISTRATION")));
		this.btn_Premission.setText(paragraph.getString("PERMISSION"));
		this.pan_Anamnesis.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("MEDICAL_HISTORYT")));

		this.setTitle(paragraph.getString("MAIN"));

		this.pan_StockManagement.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("STOCKMANAGEMENT")));
		this.pan_BedManagement.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("BEDMANAGEMENT")));
		this.pan_CodeMaintenance.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("CODEMAINTENANCE")));
	}

	public void initPermission() {
		btn_Register.setEnabled(UserInfo.getSelectPow("Registration"));
		btn_Diagnosis.setEnabled(UserInfo.getSelectPow("Diagnosis"));
		btn_Patients.setEnabled(UserInfo.getSelectPow("Patient Information"));
		btn_Anamnesis.setEnabled(UserInfo.getSelectPow("Medical History"));
		btn_AnamnesisReturn.setEnabled(UserInfo.getSelectPow("Review"));
		btn_StaffManagement.setEnabled(UserInfo
				.getSelectPow("Staff Management"));
		btn_ShiftManagement.setEnabled(UserInfo
				.getSelectPow("Shift Management"));
		btn_DepartmentManagement.setEnabled(UserInfo
				.getSelectPow("Department Management"));
		btn_PositionManagement.setEnabled(UserInfo
				.getSelectPow("Position Management"));
		btn_Pharmacy.setEnabled(UserInfo.getSelectPow("Pharmacy"));
		btn_MedicineStock.setEnabled(UserInfo.getSelectPow("Medicine Stock"));
		btn_MaterialStock.setEnabled(UserInfo.getSelectPow("Material Stock"));
		btn_Premission.setEnabled(UserInfo.getSelectPow("Permission"));
		btn_Laboratory.setEnabled(UserInfo.getSelectPow("Laboratory"));
		btn_Radiology.setEnabled(UserInfo.getSelectPow("Radiology"));
		btn_System.setEnabled(UserInfo.getSelectPow("System"));
		btn_Statistic.setEnabled(UserInfo.getSelectPow("Statistic"));
		btn_Sms.setEnabled(UserInfo.getSelectPow("Mobile Health"));
		btn_HIVCase.setEnabled(UserInfo.getSelectPow("Case Management"));
		btn_WoundCase.setEnabled(UserInfo.getSelectPow("Case Management"));
		btn_DiabetesCase.setEnabled(UserInfo.getSelectPow("Case Management"));
		btn_Cashier.setEnabled(UserInfo.getSelectPow("Cashier"));
		btn_BedCashier.setEnabled(UserInfo.getSelectPow("Cashier"));
		btn_BedManagement.setEnabled(UserInfo.getSelectPow("Bed Management"));
		btn_CodeMaintenance.setEnabled(UserInfo
				.getSelectPow("Code Maintenance"));
		btn_Inpatient.setEnabled(UserInfo.getSelectPow("InPatient"));

		// btn_Inpatient.setEnabled(true);

		// btn_Anamnesis.setEnabled(false);
		// btn_AnamnesisReturn.setEnabled(false);
		// btn_Statistic.setEnabled(false);
		// btn_Sms.setEnabled(false);
		// btn_Case.setEnabled(false);
		// btn_BedManagement.setEnabled(false);
		// btn_Cashier.setEnabled(false);
	}

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		pan_PersonalManagement = new javax.swing.JPanel();
		btn_StaffManagement = new javax.swing.JButton();
		btn_ShiftManagement = new javax.swing.JButton();
		pan_Registration = new javax.swing.JPanel();
		btn_Register = new javax.swing.JButton();
		pan_Anamnesis = new javax.swing.JPanel();
		btn_AnamnesisReturn = new javax.swing.JButton();
		btn_Anamnesis = new javax.swing.JButton();
		pan_Doctor = new javax.swing.JPanel();
		btn_Diagnosis = new javax.swing.JButton();
		btn_Patients = new javax.swing.JButton();
		pan_Pharmacy = new javax.swing.JPanel();
		btn_Pharmacy = new javax.swing.JButton();
		pan_SystemManagement = new javax.swing.JPanel();
		pan_Cashier = new javax.swing.JPanel();
		btn_Premission = new javax.swing.JButton();
		btn_System = new javax.swing.JButton();
		btn_Statistic = new javax.swing.JButton();
		btn_Cashier = new javax.swing.JButton();
		btn_BedCashier = new javax.swing.JButton();
		pan_Investgations = new javax.swing.JPanel();
		btn_Laboratory = new javax.swing.JButton();
		btn_Radiology = new javax.swing.JButton();
		btn_PositionManagement = new javax.swing.JButton();
		btn_DepartmentManagement = new javax.swing.JButton();
		btn_MedicineStock = new javax.swing.JButton();
		btn_MaterialStock = new javax.swing.JButton();
		btn_BedManagement = new javax.swing.JButton();
		btn_CodeMaintenance = new javax.swing.JButton();
		btn_Inpatient = new javax.swing.JButton();
		pan_Case = new javax.swing.JPanel();
		btn_HIVCase = new javax.swing.JButton();
		btn_Sms = new javax.swing.JButton();
		mbar = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		mnit_Logout = new javax.swing.JMenuItem();
		mnit_Exit = new javax.swing.JMenuItem();
		pan_StockManagement = new javax.swing.JPanel();
		pan_BedManagement = new javax.swing.JPanel();
		pan_CodeMaintenance = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Main");
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		setMinimumSize(new java.awt.Dimension(800, 620));
		setResizable(false);

		pan_PersonalManagement.setBackground(new java.awt.Color(240, 246, 255));

		btn_StaffManagement.setText(paragraph.getString("STAFF_MANAGEMENT"));
		btn_StaffManagement.setPreferredSize(new java.awt.Dimension(75, 29));
		btn_StaffManagement
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_StaffManagementActionPerformed(evt);
					}
				});

		btn_ShiftManagement.setText(paragraph.getString("SHIFT_MANAGEMENT"));
		btn_ShiftManagement
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_ShiftManagementActionPerformed(evt);
					}
				});

		javax.swing.GroupLayout pan_PersonalManagementLayout = new javax.swing.GroupLayout(
				pan_PersonalManagement);
		pan_PersonalManagement.setLayout(pan_PersonalManagementLayout);
		pan_PersonalManagementLayout
				.setHorizontalGroup(pan_PersonalManagementLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_PersonalManagementLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_PersonalManagementLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_StaffManagement,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																200,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_ShiftManagement,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																200,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		pan_PersonalManagementLayout
				.setVerticalGroup(pan_PersonalManagementLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_PersonalManagementLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_StaffManagement,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_ShiftManagement,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(16, Short.MAX_VALUE)));

		pan_Registration.setBackground(new java.awt.Color(240, 246, 255));

		btn_Register.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_RegisterActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_RegistrationLayout = new javax.swing.GroupLayout(
				pan_Registration);
		pan_Registration.setLayout(pan_RegistrationLayout);
		pan_RegistrationLayout.setHorizontalGroup(pan_RegistrationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						pan_RegistrationLayout
								.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btn_Register,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										188,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		pan_RegistrationLayout.setVerticalGroup(pan_RegistrationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						pan_RegistrationLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(btn_Register,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pan_Anamnesis.setBackground(new java.awt.Color(240, 246, 255));
		pan_Anamnesis.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("MEDICAL_HISTORYT")));

		btn_AnamnesisReturn.setText(paragraph.getString("FORDER_RETURN"));
		btn_AnamnesisReturn.setPreferredSize(new java.awt.Dimension(200, 60));
		btn_AnamnesisReturn
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_AnamnesisReturnActionPerformed(evt);
					}
				});

		btn_Anamnesis.setText(paragraph.getString("MEDICAL_HISTORYT"));
		btn_Anamnesis.setPreferredSize(new java.awt.Dimension(200, 60));
		btn_Anamnesis.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_AnamnesisActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_AnamnesisLayout = new javax.swing.GroupLayout(
				pan_Anamnesis);
		pan_Anamnesis.setLayout(pan_AnamnesisLayout);
		pan_AnamnesisLayout
				.setHorizontalGroup(pan_AnamnesisLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_AnamnesisLayout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												pan_AnamnesisLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																btn_Anamnesis,
																javax.swing.GroupLayout.Alignment.TRAILING,
																0, 0,
																Short.MAX_VALUE)
														.addComponent(
																btn_AnamnesisReturn,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																188,
																Short.MAX_VALUE))
										.addContainerGap()));
		pan_AnamnesisLayout
				.setVerticalGroup(pan_AnamnesisLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_AnamnesisLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_Anamnesis,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_AnamnesisReturn,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		pan_Doctor.setBackground(new java.awt.Color(240, 246, 255));
		pan_Doctor.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("CLINIC")));

		btn_Diagnosis.setText(paragraph.getString("CLINIC"));
		btn_Diagnosis.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_DiagnosisActionPerformed(evt);
			}
		});

		btn_Inpatient.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_AdmissionActionPerformed(evt);
			}
		});

		btn_Patients.setText(paragraph.getString("PATIENT_INFORMATION"));
		btn_Patients.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_PatientsActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_DoctorLayout = new javax.swing.GroupLayout(
				pan_Doctor);
		pan_Doctor.setLayout(pan_DoctorLayout);
		pan_DoctorLayout
				.setHorizontalGroup(pan_DoctorLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_DoctorLayout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												pan_DoctorLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																btn_Inpatient,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																btn_Diagnosis,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																188,
																Short.MAX_VALUE)
														.addComponent(
																btn_Patients,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																188,
																Short.MAX_VALUE))
										.addContainerGap()));
		pan_DoctorLayout
				.setVerticalGroup(pan_DoctorLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_DoctorLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_Diagnosis,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_Inpatient,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_Patients,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		pan_Pharmacy.setBackground(new java.awt.Color(240, 246, 255));
		pan_Pharmacy.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("PHARMACY")));

		btn_Pharmacy.setText(paragraph.getString("PHARMACY"));
		btn_Pharmacy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_PharmacyActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_PharmacyLayout = new javax.swing.GroupLayout(
				pan_Pharmacy);
		pan_Pharmacy.setLayout(pan_PharmacyLayout);
		pan_PharmacyLayout.setHorizontalGroup(pan_PharmacyLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						pan_PharmacyLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(btn_Pharmacy,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										200,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		pan_PharmacyLayout.setVerticalGroup(pan_PharmacyLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						pan_PharmacyLayout
								.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btn_Pharmacy,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		pan_CodeMaintenance.setBackground(new java.awt.Color(240, 246, 255));
		javax.swing.GroupLayout pan_CodeMaintenanceLayout = new javax.swing.GroupLayout(
				pan_CodeMaintenance);
		pan_CodeMaintenance.setLayout(pan_CodeMaintenanceLayout);
		pan_CodeMaintenanceLayout
				.setHorizontalGroup(pan_CodeMaintenanceLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_CodeMaintenanceLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_CodeMaintenanceLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_CodeMaintenance,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																200,
																Short.MAX_VALUE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		pan_CodeMaintenanceLayout.setVerticalGroup(pan_CodeMaintenanceLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						pan_CodeMaintenanceLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(btn_CodeMaintenance,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pan_BedManagement.setBackground(new java.awt.Color(240, 246, 255));

		javax.swing.GroupLayout pan_BedManagementLayout = new javax.swing.GroupLayout(
				pan_BedManagement);
		pan_BedManagement.setLayout(pan_BedManagementLayout);
		pan_BedManagementLayout
				.setHorizontalGroup(pan_BedManagementLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_BedManagementLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_BedManagementLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_BedManagement,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																200,
																Short.MAX_VALUE)
										// .addComponent(btn_System,
										// javax.swing.GroupLayout.Alignment.LEADING,
										// javax.swing.GroupLayout.DEFAULT_SIZE,
										// 200, Short.MAX_VALUE)
										// .addComponent(btn_Statistic,
										// javax.swing.GroupLayout.Alignment.LEADING,
										// javax.swing.GroupLayout.PREFERRED_SIZE,
										// 200,
										// javax.swing.GroupLayout.PREFERRED_SIZE)
										)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		pan_BedManagementLayout.setVerticalGroup(pan_BedManagementLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						pan_BedManagementLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(btn_BedManagement,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								// .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								// .addComponent(btn_System,
								// javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								// javax.swing.GroupLayout.PREFERRED_SIZE)
								// .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								// .addComponent(btn_Statistic,
								// javax.swing.GroupLayout.PREFERRED_SIZE, 60,
								// javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		pan_StockManagement.setBackground(new java.awt.Color(240, 246, 255));

		javax.swing.GroupLayout pan_StockManagementLayout = new javax.swing.GroupLayout(
				pan_StockManagement);
		pan_StockManagement.setLayout(pan_StockManagementLayout);
		pan_StockManagementLayout
				.setHorizontalGroup(pan_StockManagementLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_StockManagementLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_StockManagementLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_MedicineStock,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																200,
																Short.MAX_VALUE)
														.addComponent(
																btn_MaterialStock,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																200,
																Short.MAX_VALUE)
										// .addComponent(btn_Statistic,
										// javax.swing.GroupLayout.Alignment.LEADING,
										// javax.swing.GroupLayout.PREFERRED_SIZE,
										// 200,
										// javax.swing.GroupLayout.PREFERRED_SIZE)
										)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		pan_StockManagementLayout
				.setVerticalGroup(pan_StockManagementLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_StockManagementLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_MedicineStock,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_MaterialStock,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										// .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										// .addComponent(btn_Statistic,
										// javax.swing.GroupLayout.PREFERRED_SIZE,
										// 60,
										// javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		pan_Cashier.setBackground(new java.awt.Color(240, 246, 255));
		javax.swing.GroupLayout pan_CashierLayout = new javax.swing.GroupLayout(
				pan_Cashier);
		pan_Cashier.setLayout(pan_CashierLayout);
		pan_CashierLayout
				.setHorizontalGroup(pan_CashierLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_CashierLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_CashierLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_Cashier,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																200,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_BedCashier,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																200,
																javax.swing.GroupLayout.PREFERRED_SIZE)
															)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
								));
		pan_CashierLayout
				.setVerticalGroup(pan_CashierLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_CashierLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_Cashier,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_BedCashier,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addContainerGap(
												16,
												Short.MAX_VALUE)

						));
		
		btn_Premission.setText(paragraph.getString("PERMISSION"));
		btn_Premission.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_PremissionActionPerformed(evt);
			}
		});

		btn_System.setText(paragraph.getString("SYSTEM"));
		btn_System.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_SystemActionPerformed(evt);
			}
		});

		btn_Statistic.setText(paragraph.getString("STATISTIC"));
		btn_Statistic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_StatisticActionPerformed(evt);
			}
		});

		btn_Cashier.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CashierActionPerformed(evt);
			}
		});
		
		btn_BedCashier.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_BedCashierActionPerformed(evt);
			}
		});

		pan_SystemManagement.setBackground(new java.awt.Color(240, 246, 255));
		javax.swing.GroupLayout pan_SystemManagementLayout = new javax.swing.GroupLayout(
				pan_SystemManagement);
		pan_SystemManagement.setLayout(pan_SystemManagementLayout);
		pan_SystemManagementLayout
				.setHorizontalGroup(pan_SystemManagementLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_SystemManagementLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_SystemManagementLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pan_SystemManagementLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																		.addComponent(
																				btn_Premission,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				200,
																				Short.MAX_VALUE)
																		.addComponent(
																				btn_System,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				200,
																				Short.MAX_VALUE)
																		.addComponent(
																				btn_Statistic,
																				javax.swing.GroupLayout.Alignment.LEADING,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				200,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		pan_SystemManagementLayout
				.setVerticalGroup(pan_SystemManagementLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_SystemManagementLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_Premission,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_System,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_Statistic,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)

						));

		pan_Investgations.setBackground(new java.awt.Color(240, 246, 255));
		pan_Investgations.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("INVESTGATIONS")));

		btn_Laboratory.setText(paragraph.getString("LABORATORY"));
		btn_Laboratory.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_LaboratoryActionPerformed(evt);
			}
		});

		btn_Radiology.setText(paragraph.getString("RADIOLOGY(X-RAY)"));
		btn_Radiology.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_RadiologyActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_InvestgationsLayout = new javax.swing.GroupLayout(
				pan_Investgations);
		pan_Investgations.setLayout(pan_InvestgationsLayout);
		pan_InvestgationsLayout
				.setHorizontalGroup(pan_InvestgationsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_InvestgationsLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_InvestgationsLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btn_Laboratory,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																200,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_Radiology,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																200,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(8, Short.MAX_VALUE)));
		pan_InvestgationsLayout
				.setVerticalGroup(pan_InvestgationsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_InvestgationsLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_Laboratory,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_Radiology,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												60,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		btn_PositionManagement.setText(paragraph
				.getString("POSITION_MANAGEMENT"));
		btn_PositionManagement
				.setPreferredSize(new java.awt.Dimension(200, 60));
		btn_PositionManagement
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_PositionManagementActionPerformed(evt);
					}
				});

		btn_DepartmentManagement.setText(paragraph
				.getString("POLINLIC_MANAGEMENT"));
		btn_DepartmentManagement.setPreferredSize(new java.awt.Dimension(200,
				60));
		btn_DepartmentManagement
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_DepartmentManagementActionPerformed(evt);
					}
				});

		btn_MedicineStock
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_MedicineStockActionPerformed(evt);
					}
				});
		btn_MaterialStock
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_MaterialStockActionPerformed(evt);
					}
				});

		btn_BedManagement
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_BedManagementActionPerformed(evt);
					}
				});
		btn_CodeMaintenance
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						btn_CodeMaintenanceActionPerformed(evt);
					}
				});

		pan_Case.setBackground(new java.awt.Color(240, 246, 255));
		pan_Case.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getString("CASE_MANAGEMENT")));

		btn_HIVCase.setText(paragraph.getString("CASE_MANAGEMENT"));
		btn_HIVCase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CaseActionPerformed(evt);
			}
		});

		btn_Sms.setText(paragraph.getString("MOBILE_HEALTH"));
		btn_Sms.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_SmsActionPerformed(evt);
			}
		});

		btn_DiabetesCase = new JButton(
				paragraph.getString("DIABETES_CASE_MANAGEMENT"));
		btn_DiabetesCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDiabetesCaseClicked(e);
			}
		});
		btn_WoundCase = new JButton(
				paragraph.getString("WOUND_CASE_MANAGEMENT"));
		btn_WoundCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onWoundCaseClicked(e);
			}
		});
		btn_WoundCase.setEnabled(false);

		javax.swing.GroupLayout pan_CaseLayout = new javax.swing.GroupLayout(
				pan_Case);
		pan_CaseLayout.setHorizontalGroup(pan_CaseLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				pan_CaseLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								pan_CaseLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_DiabetesCase,
												GroupLayout.DEFAULT_SIZE, 200,
												Short.MAX_VALUE)
										.addComponent(btn_WoundCase,
												GroupLayout.DEFAULT_SIZE, 200,
												Short.MAX_VALUE)
										.addComponent(btn_HIVCase,
												GroupLayout.DEFAULT_SIZE, 200,
												Short.MAX_VALUE)
										.addComponent(btn_Sms,
												GroupLayout.DEFAULT_SIZE, 200,
												Short.MAX_VALUE))
						.addContainerGap()));
		pan_CaseLayout.setVerticalGroup(pan_CaseLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				pan_CaseLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(btn_WoundCase,
								GroupLayout.PREFERRED_SIZE, 60,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btn_HIVCase, GroupLayout.PREFERRED_SIZE,
								60, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btn_DiabetesCase,
								GroupLayout.PREFERRED_SIZE, 60,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btn_Sms, GroupLayout.PREFERRED_SIZE, 60,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		pan_Case.setLayout(pan_CaseLayout);

		jMenu1.setText(paragraph.getString("FILE"));

		mnit_Logout.setText(paragraph.getString("LOGOUT"));
		mnit_Logout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_LogoutActionPerformed(evt);
			}
		});
		jMenu1.add(mnit_Logout);

		mnit_Exit.setText(paragraph.getString("EXIT"));
		mnit_Exit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_ExitActionPerformed(evt);
			}
		});
		jMenu1.add(mnit_Exit);

		mbar.add(jMenu1);

		setJMenuBar(mbar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														pan_Registration,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														216,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_Doctor,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														216,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_Anamnesis,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														216,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														pan_Pharmacy,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														230,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_Investgations,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														230,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(10, 10,
																		10)
												// .addComponent(btn_DepartmentManagement,
												// javax.swing.GroupLayout.PREFERRED_SIZE,
												// javax.swing.GroupLayout.DEFAULT_SIZE,
												// javax.swing.GroupLayout.PREFERRED_SIZE)
												)
												.addComponent(
														pan_Case,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														pan_Cashier,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_StockManagement,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_PersonalManagement,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														230,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														pan_SystemManagement,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_BedManagement,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_CodeMaintenance,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)

								// .addGroup(
								// layout.createSequentialGroup()
								// .addGap(10, 10, 10)
								// .addComponent(btn_MedicineStock,
								// javax.swing.GroupLayout.PREFERRED_SIZE,
								// 36,
								// javax.swing.GroupLayout.PREFERRED_SIZE)
								// .addPreferredGap(
								// javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								// .addComponent(btn_PositionManagement,
								// javax.swing.GroupLayout.PREFERRED_SIZE,
								// 45,
								// javax.swing.GroupLayout.PREFERRED_SIZE)
								).addGap(61, 61, 61)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																				.addComponent(
																						pan_Pharmacy,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						pan_Registration,
																						javax.swing.GroupLayout.Alignment.LEADING,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						pan_Doctor,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						pan_Case,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						pan_Anamnesis,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						pan_Investgations,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																// .addComponent(btn_DepartmentManagement,
																// javax.swing.GroupLayout.PREFERRED_SIZE,
																// javax.swing.GroupLayout.DEFAULT_SIZE,
																// javax.swing.GroupLayout.PREFERRED_SIZE)
																// .addComponent(btn_MedicineStock,
																// javax.swing.GroupLayout.PREFERRED_SIZE,
																// 60,
																// javax.swing.GroupLayout.PREFERRED_SIZE)
																// .addComponent(btn_PositionManagement,
																// javax.swing.GroupLayout.PREFERRED_SIZE,
																// javax.swing.GroupLayout.DEFAULT_SIZE,
																// javax.swing.GroupLayout.PREFERRED_SIZE)
																))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		pan_PersonalManagement,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		pan_Cashier,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		pan_StockManagement,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED))
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		pan_SystemManagement,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		pan_BedManagement,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		pan_CodeMaintenance,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	protected void onWoundCaseClicked(ActionEvent e) {
		new casemgmt.Frm_WorkList(0, "W").setVisible(true);
		this.dispose();
	}

	protected void onDiabetesCaseClicked(ActionEvent e) {
		new casemgmt.Frm_WorkList(0, "D").setVisible(true);
		this.dispose();
	}

	private void btn_RegisterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_RegisterActionPerformed
		// 開啟掛號視窗
		new registration.Frm_RegAndInpatient().setVisible(true);
		// new registration.Frm_Registration().setVisible(true);
		// 關閉此視窗
		this.dispose();
	}// GEN-LAST:event_btn_RegisterActionPerformed

	private void btn_DiagnosisActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_DiagnosisActionPerformed
		// 自動更新diagnosis_code資料
		new Thread() {
			@Override
			public void run() {

				ExecutorService executor = Executors.newFixedThreadPool(3);
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							Frm_Loading frm_Loading = new cc.johnwu.loading.Frm_Loading(
									"diagnosis_code");
							frm_Loading.show_Loading();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							Frm_Loading frm_Loading1 = new cc.johnwu.loading.Frm_Loading(
									"prescription_code");
							frm_Loading1.show_Loading();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				executor.execute(new Runnable() {

					@Override
					public void run() {
						try {
							Frm_Loading frm_Loading2 = new cc.johnwu.loading.Frm_Loading(
									"medicines");
							frm_Loading2.show_Loading();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});

				try {
					synchronized (executor) {
						executor.shutdown();
						if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
							JOptionPane
									.showMessageDialog(
											null,
											"Timeout! Downloading fail , some diagnosis_code, prescription_code, medicines may be incorrect!");
						}
						// DBC.localExecute("SHUTDOWN");
					}
					;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();

		// 開啟看診 視窗
		new worklist.Frm_WorkList(0, "dia").setVisible(true);
		// 關閉此視窗
		this.dispose();
	}// GEN-LAST:event_btn_DiagnosisActionPerformed

	private void btn_AdmissionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_DiagnosisActionPerformed
		// 開啟看診 視窗
		try {
			new admission.Frm_WorkList(0, "inp").setVisible(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 關閉此視窗
		this.dispose();
	}// GEN-LAST:event_btn_DiagnosisActionPerformed

	private void btn_PharmacyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_PharmacyActionPerformed
		new pharmacy.Frm_Pharmacy().setVisible(true);
		// 關閉此視窗
		this.dispose();
	}// GEN-LAST:event_btn_PharmacyActionPerformed

	private void btn_PremissionActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_PremissionActionPerformed
		new permission.Frm_Permission_Info().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_PremissionActionPerformed

	private void btn_PatientsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_PatientsActionPerformed
		new patients.Frm_PatientsList().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_PatientsActionPerformed

	private void btn_StaffManagementActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_StaffManagementActionPerformed
		new staff.Frm_StaffInfo().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_StaffManagementActionPerformed

	private void btn_ShiftManagementActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_ShiftManagementActionPerformed
		new shiftwork.Frm_ShiftWorkInfo().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_ShiftManagementActionPerformed

	private void btn_DepartmentManagementActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_DepartmentManagementActionPerformed
		new staff.Frm_Department().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_DepartmentManagementActionPerformed

	private void btn_PositionManagementActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_PositionManagementActionPerformed
		new staff.Frm_Position().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_PositionManagementActionPerformed

	private void btn_MaterialStockActionPerformed(java.awt.event.ActionEvent evt) {
		new materialstock.Frm_MaterialStockInfo().setVisible(true);
		this.dispose();
	}

	private void btn_MedicineStockActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_MedicineStockActionPerformed
		new medicinestock.Frm_MidicineStockInfo().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_MedicineStockActionPerformed

	private void btn_AnamnesisActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_AnamnesisActionPerformed
		new anamnesis.Frm_Anamnesis(true).setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_AnamnesisActionPerformed

	private void btn_AnamnesisReturnActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_AnamnesisReturnActionPerformed
		new anamnesis.Frm_Anamnesis(false).setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_AnamnesisReturnActionPerformed

	private void btn_CaseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CaseActionPerformed
		new casemgmt.Frm_WorkList(0, "H").setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_CaseActionPerformed

	private void btn_RadiologyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_RadiologyActionPerformed
		new worklist.Frm_WorkList(0, "xray").setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_RadiologyActionPerformed

	private void btn_BedManagementActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_LaboratoryActionPerformed
		new bedMgmt.Frm_BedList(0).setVisible(true);
		this.dispose();
	}

	private void btn_CodeMaintenanceActionPerformed(
			java.awt.event.ActionEvent evt) {
		Frm_TableChooser chooser = new Frm_TableChooser();
		chooser.setLocationRelativeTo(this);
		chooser.setVisible(true);
		this.dispose();
	}

	private void btn_LaboratoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_LaboratoryActionPerformed
		new worklist.Frm_WorkList(0, "lab").setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_LaboratoryActionPerformed

	private void btn_SystemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_SystemActionPerformed
		new Frm_Setting().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_SystemActionPerformed

	private void btn_SmsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_SmsActionPerformed
		new Frm_MobileHealth().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_SmsActionPerformed

	private void btn_StatisticActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_StatisticActionPerformed
		new Frm_Statistic().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_StatisticActionPerformed

	private void btn_CashierActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CashierActionPerformed
		new Frm_CashierList().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_CashierActionPerformed
	
	private void btn_BedCashierActionPerformed(java.awt.event.ActionEvent evt) {
		new Frm_CashierList("bed").setVisible(true);
		this.dispose();
	}

	private void mnit_ExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_ExitActionPerformed
		cc.johnwu.login.OnlineState.LogOut();
		JPAUtil.shutdown();
		System.exit(0);
	}// GEN-LAST:event_mnit_ExitActionPerformed

	@SuppressWarnings("unused")
	private void mnit_LogoutActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_LogoutActionPerformed
		Frm_Login m_frm_Login;

		Object[] options = { "YES", "NO" };
		int response = JOptionPane.showOptionDialog(new Frame(),
				"System Logout?", "Wairning", JOptionPane.YES_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (response == 0) {
			try {
				// cc.johnwu.login.OnlineState.LogOut();
				// this.dispose();
				// new UserInfo m_UserInfo m_UserInfo.set

				// new UserInfo(false).
				new UserInfo(false).openFrame(new Frm_Main());
				//
			} catch (InterruptedException ex) {
				Logger.getLogger(Frm_Main.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}// GEN-LAST:event_mnit_LogoutActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btn_Anamnesis;
	private javax.swing.JButton btn_AnamnesisReturn;
	private javax.swing.JButton btn_HIVCase;
	private JButton btn_DiabetesCase;
	private JButton btn_WoundCase;
	private javax.swing.JButton btn_Cashier;
	private javax.swing.JButton btn_BedCashier;
	private javax.swing.JButton btn_DepartmentManagement;
	private javax.swing.JButton btn_Diagnosis;
	private javax.swing.JButton btn_Laboratory;
	private javax.swing.JButton btn_MedicineStock;
	private javax.swing.JButton btn_MaterialStock;
	private javax.swing.JButton btn_BedManagement;
	private javax.swing.JButton btn_Inpatient;
	private javax.swing.JButton btn_Patients;
	private javax.swing.JButton btn_Pharmacy;
	private javax.swing.JButton btn_PositionManagement;
	private javax.swing.JButton btn_Premission;
	private javax.swing.JButton btn_Radiology;
	private javax.swing.JButton btn_Register;
	private javax.swing.JButton btn_ShiftManagement;
	private javax.swing.JButton btn_Sms;
	private javax.swing.JButton btn_StaffManagement;
	private javax.swing.JButton btn_Statistic;
	private javax.swing.JButton btn_System;
	private javax.swing.JButton btn_CodeMaintenance;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar mbar;
	private javax.swing.JMenuItem mnit_Exit;
	private javax.swing.JMenuItem mnit_Logout;
	private javax.swing.JPanel pan_Anamnesis;
	private javax.swing.JPanel pan_Case;
	private javax.swing.JPanel pan_Doctor;
	private javax.swing.JPanel pan_Investgations;
	private javax.swing.JPanel pan_PersonalManagement;
	private javax.swing.JPanel pan_Pharmacy;
	private javax.swing.JPanel pan_Registration;
	private javax.swing.JPanel pan_SystemManagement;
	private javax.swing.JPanel pan_Cashier;
	private javax.swing.JPanel pan_StockManagement;
	private javax.swing.JPanel pan_BedManagement;
	private javax.swing.JPanel pan_CodeMaintenance;
}
