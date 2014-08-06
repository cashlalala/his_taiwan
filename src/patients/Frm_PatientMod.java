package patients;

//import Diagnosis.*;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityTransaction;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import laboratory.Frm_LabHistory;
import multilingual.Language;

import org.his.JPAUtil;
import org.his.dao.ContactpersonInfoDao;
import org.his.dao.DeathInfoDao;
import org.his.dao.HlsGroupDao;
import org.his.dao.PatientsInfoDao;
import org.his.dao.ReligionDao;
import org.his.model.ContactpersonInfo;
import org.his.model.DeathInfo;
import org.his.model.HlsGroup;
import org.his.model.PatientsInfo;
import org.his.model.Religion;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.Converter;

import radiology.Frm_RadiologyHistory;
import barcode.PrintBarcode;
import cashier.Frm_CashierHistory;
import cc.johnwu.date.DateInterface;
import cc.johnwu.finger.FingerPrintScanner;
import cc.johnwu.finger.FingerPrintViewerInterface;
import cc.johnwu.sql.DBC;
import errormessage.StoredErrorMessage;

public class Frm_PatientMod extends javax.swing.JFrame implements
		FingerPrintViewerInterface, DateInterface, diagnosis.DiagnosisInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PatientsInterface m_frame = null;
	private static String m_UUID;
	public static final String[] m_MaritalStatus = { "m", "d", "s", "o" };
	private String m_Status;
	/* 多國語言變數 */

	private static final Language paragraph = Language.getInstance();
	private static final String[] LOCALED_MARRITALNAME = new String[] {
			paragraph.getString("MARRIED"), paragraph.getString("DIVORCED"),
			paragraph.getString("SINGLE"), paragraph.getString("OTHER") };
	private String[] line = paragraph.setlanguage("PATIENTMOD").split("\n");
	private String[] message = paragraph.setlanguage("MESSAGE").split("\n");
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	private PatientsInfo patientInfo;

	private ContactpersonInfo contactpersonInfo;

	private PatientsInfoDao patientInfoDao;

	private ContactpersonInfoDao contactpersonInfoDao;

	private DeathInfoDao deathInfoDao;

	private DeathInfo deathInfo;

	java.awt.event.ActionEvent evt;

	private EntityTransaction etx;

	public Frm_PatientMod(PatientsInterface m_frame, boolean isNewPatient) {
		etx = JPAUtil.getTransaction();
		etx.begin();
		initComponents();
		initPatientInfoCtrl();
		initContactCtrl();
		init();
		initcBox();
		initPermission();
		initLanguage();
		this.m_frame = m_frame;
		this.setTitle("New Patient Data");
		m_Status = "NEW";
		check_Deal.setVisible(false);
		txt_Language.setVisible(false);
		lab_Language.setVisible(false);
		txt_EmeLanguage.setVisible(false);
		lab_EmeLanguage.setVisible(false);
		if (m_frame.getClass().getName()
				.equals("Registration.Frm_Registration")) {
			bar_menu.setVisible(false);
		}

		patientInfoDao = new PatientsInfoDao();
		contactpersonInfoDao = new ContactpersonInfoDao();
		deathInfoDao = new DeathInfoDao();

		if (isNewPatient) {
			initPatientInfo();
			contactpersonInfo = new ContactpersonInfo();
			contactpersonInfo.setGender("M");
			contactpersonInfo.setMaritalStatus(m_MaritalStatus[0]);
			initDataBindings();
		}
	}

	public Frm_PatientMod(PatientsInterface m_frame, PatientsInfo pInfo) {
		this(m_frame, false);
		this.patientInfo = patientInfoDao.merge(pInfo);
		this.contactpersonInfo = (patientInfo.getContactpersonInfo() != null) ? patientInfo
				.getContactpersonInfo() : new ContactpersonInfo();

		if (patientInfo.getDeathInfo() != null) {
			deathInfo = patientInfo.getDeathInfo();
			check_Deal.setSelected(true);
		}

		this.setTitle(paragraph.getString("EDITPATIENTDATA"));
		m_Status = "EDIT";
		this.btn_OK.setEnabled(isCanSave());
		check_Deal.setVisible(true);
		initLanguage();
		if (m_frame.getClass().getName()
				.equals("Registration.Frm_Registration")) {
			bar_menu.setVisible(false);
		}
		initDataBindings();
	}

	public Frm_PatientMod(PatientsInterface m_frame, String p_no) {
		this(m_frame, false);
		this.patientInfo = this.patientInfoDao.QueryPatientInfoById(p_no);
		this.contactpersonInfo = (patientInfo.getContactpersonInfo() != null) ? patientInfo
				.getContactpersonInfo() : new ContactpersonInfo();

		if (patientInfo.getDeathInfo() != null) {
			deathInfo = patientInfo.getDeathInfo();
			check_Deal.setSelected(true);
		}

		this.setTitle(paragraph.getString("EDITPATIENTDATA"));
		m_Status = "EDIT";
		this.btn_OK.setEnabled(isCanSave());
		check_Deal.setVisible(true);
		initLanguage();
		if (m_frame.getClass().getName()
				.equals("Registration.Frm_Registration")) {
			bar_menu.setVisible(false);
		}
		initDataBindings();
	}

	/** 初始化 */
	private void init() {
		this.setExtendedState(Frm_PatientMod.MAXIMIZED_BOTH); // 最大化
		this.setLocationRelativeTo(this);// 視窗顯示至中
		this.dateChooser1.setParentFrame(this);
		FingerPrintScanner.setParentFrame(this);// 打開指紋機
		addWindowListener(new WindowAdapter() {// 視窗關閉事件
			@Override
			public void windowClosing(WindowEvent windowevent) {
				btn_CancelActionPerformed(null);
			}
		});

		m_UUID = UUID.randomUUID().toString();
	}

	public void initPatientInfo() {
		PatientsInfo pInfo = new PatientsInfo();
		pInfo.setPNo(UUID.randomUUID().toString());
		pInfo.setFirstname(m_UUID);
		pInfo.setLastname(m_UUID);
		pInfo.setExist((byte) 0);
		pInfo.setBirth(null);
		pInfo.setGender((String) cob_Gender.getSelectedItem());
		pInfo.setMaritalStatus(m_MaritalStatus[getMaritalStatusIndexByFullName((String) cob_MaritalStatus
				.getSelectedItem())]);
		patientInfo = patientInfoDao.merge(pInfo);
		patientInfoDao.persist(patientInfo);
	}

	protected void initDataBindings() {

		BeanProperty<PatientsInfo, String> jIntBeanProp1 = BeanProperty
				.create("PNo");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind1 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp1, this.txt_No, jTextFieldBeanProperty);
		bind1.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp2 = BeanProperty
				.create("nhisNo");
		BeanProperty<JTextField, String> jTextFieldProp2 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind2 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp2, txt_NhisNo, jTextFieldProp2);
		bind2.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp3 = BeanProperty
				.create("firstname");
		BeanProperty<JTextField, String> jTextFieldProp3 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind3 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp3, txt_FirstName, jTextFieldProp3);
		bind3.setConverter(new Converter<String, String>() {

			@Override
			public String convertForward(String paramS) {
				if (Frm_PatientMod.m_UUID == paramS) {
					return "";
				}
				return paramS;
			}

			@Override
			public String convertReverse(String paramT) {
				if (paramT == "") {
					return Frm_PatientMod.m_UUID;
				}
				return paramT;
			}

		});
		bind3.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp4 = BeanProperty
				.create("gender");
		BeanProperty<JComboBox, String> jComboBoxBeanProperty = BeanProperty
				.create("selectedItem");
		AutoBinding<PatientsInfo, String, JComboBox, String> bind4 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						patientInfo, jIntBeanProp4, cob_Gender,
						jComboBoxBeanProperty);
		bind4.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp5 = BeanProperty
				.create("bloodtype");
		BeanProperty<JComboBox, String> jComboBoxBeanProperty2 = BeanProperty
				.create("selectedItem");
		AutoBinding<PatientsInfo, String, JComboBox, String> bind5 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						patientInfo, jIntBeanProp5, cob_Bloodtype,
						jComboBoxBeanProperty2);
		bind5.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp6 = BeanProperty
				.create("lastname");
		BeanProperty<JTextField, String> jTextFieldProp6 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind6 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp6, txt_LastName, jTextFieldProp6);
		bind6.setConverter(new Converter<String, String>() {

			@Override
			public String convertForward(String paramS) {
				if (Frm_PatientMod.m_UUID == paramS) {
					return "";
				}
				return paramS;
			}

			@Override
			public String convertReverse(String paramT) {
				if (paramT == "") {
					return Frm_PatientMod.m_UUID;
				}
				return paramT;
			}

		});
		bind6.bind();

		BeanProperty<PatientsInfo, Date> jIntBeanProp7 = BeanProperty
				.create("birth");
		BeanProperty<JTextField, String> jTextFieldProp7 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, Date, JTextField, String> bind7 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp7, dateChooser1.getTxt_Date(),
						jTextFieldProp7);
		bind7.setConverter(new Converter<Date, String>() {

			private SimpleDateFormat formatter = new SimpleDateFormat(
					"dd-MM-yyyy");

			@Override
			public String convertForward(Date arg0) {
				return formatter.format(arg0);
			}

			@Override
			public Date convertReverse(String arg0) {
				Date date = null;
				try {
					date = formatter.parse(arg0);
				} catch (ParseException e) {
					e.printStackTrace();
					date = new Date();
				}
				return date;
			}

		});
		bind7.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp8 = BeanProperty
				.create("rhType");
		BeanProperty<JComboBox, String> jComboBoxBeanProperty8 = BeanProperty
				.create("selectedItem");
		AutoBinding<PatientsInfo, String, JComboBox, String> bind8 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						patientInfo, jIntBeanProp8, cob_Rh,
						jComboBoxBeanProperty8);
		bind8.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp9 = BeanProperty
				.create("maritalStatus");
		BeanProperty<JComboBox, String> jComboBoxBeanProperty9 = BeanProperty
				.create("selectedItem");
		AutoBinding<PatientsInfo, String, JComboBox, String> bind9 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						patientInfo, jIntBeanProp9, cob_MaritalStatus,
						jComboBoxBeanProperty9);
		bind9.setConverter(new Converter<String, String>() {

			@Override
			public String convertForward(String arg0) {
				return Frm_PatientMod.getMaritalStatusName(arg0);
			}

			@Override
			public String convertReverse(String arg0) {
				return Frm_PatientMod.m_MaritalStatus[Frm_PatientMod
						.getMaritalStatusIndexByFullName(arg0)];
			}

		});
		bind9.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp10 = BeanProperty
				.create("height");
		BeanProperty<JTextField, String> jTextFieldProp10 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind10 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp10, txt_Height, jTextFieldProp10);
		bind10.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp11 = BeanProperty
				.create("weight");
		BeanProperty<JTextField, String> jTextFieldProp11 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind11 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp11, txt_Weight, jTextFieldProp11);
		bind11.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp12 = BeanProperty
				.create("phone");
		BeanProperty<JTextField, String> jTextFieldProp12 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind12 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp12, txt_Phone1, jTextFieldProp12);
		bind12.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp13 = BeanProperty
				.create("cellPhone");
		BeanProperty<JTextField, String> jTextFieldProp13 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind13 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp13, txt_Phone2, jTextFieldProp13);
		bind13.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp14 = BeanProperty
				.create("town");
		BeanProperty<JTextField, String> jTextFieldProp14 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind14 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp14, txt_Town, jTextFieldProp14);
		bind14.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp15 = BeanProperty
				.create("state");
		BeanProperty<JTextField, String> jTextFieldProp15 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind15 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp15, txt_State, jTextFieldProp15);
		bind15.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp16 = BeanProperty
				.create("country");
		BeanProperty<JTextField, String> jTextFieldProp16 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind16 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp16, txt_Country, jTextFieldProp16);
		bind16.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp17 = BeanProperty
				.create("address");
		BeanProperty<JTextArea, String> jTextFieldProp17 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextArea, String> bind17 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp17, txt_Address, jTextFieldProp17);
		bind17.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp18 = BeanProperty
				.create("occupation");
		BeanProperty<JTextField, String> jTextFieldProp18 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind18 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp18, txt_Occupation, jTextFieldProp18);
		bind18.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp19 = BeanProperty
				.create("language");
		BeanProperty<JTextField, String> jTextFieldProp19 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind19 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp19, txt_Language, jTextFieldProp19);
		bind19.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp20 = BeanProperty
				.create("placeOfBirth");
		BeanProperty<JTextField, String> jTextFieldProp20 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind20 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp20, txt_PlaceOfBirth, jTextFieldProp20);
		bind20.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp21 = BeanProperty
				.create("ps");
		BeanProperty<JTextArea, String> jTextFieldProp21 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextArea, String> bind21 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp21, txt_Ps, jTextFieldProp21);
		bind21.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp22 = BeanProperty
				.create("tribe");
		BeanProperty<JComboBox, String> jComboBoxBeanProperty22 = BeanProperty
				.create("selectedItem");
		AutoBinding<PatientsInfo, String, JComboBox, String> bind22 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						patientInfo, jIntBeanProp22, cbox_Trible,
						jComboBoxBeanProperty22);
		bind22.setConverter(new Converter<String, String>() {
			private HlsGroupDao dao = new HlsGroupDao();

			@Override
			public String convertForward(String arg0) {
				return dao.QueryCompStringByValue(arg0);
			}

			@Override
			public String convertReverse(String arg0) {
				if (arg0 != null) {
					return arg0.toString().split(" - ")[0];
				}
				return "";
			}

		});
		bind22.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp23 = BeanProperty
				.create("religion");
		BeanProperty<JComboBox, String> jComboBoxBeanProperty23 = BeanProperty
				.create("selectedItem");
		AutoBinding<PatientsInfo, String, JComboBox, String> bind23 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						patientInfo, jIntBeanProp23, cbox_Religion,
						jComboBoxBeanProperty23);
		bind23.setConverter(new Converter<String, String>() {
			private ReligionDao dao = new ReligionDao();

			@Override
			public String convertForward(String arg0) {
				return dao.QueryCompStringByValue(arg0);
			}

			@Override
			public String convertReverse(String arg0) {
				if (arg0 != null) {
					return arg0.toString().split(" - ")[0];
				}
				return "";
			}

		});
		bind23.bind();

		BeanProperty<PatientsInfo, String> jIntBeanProp24 = BeanProperty
				.create("niaNo");
		BeanProperty<JTextField, String> jTextFieldProp24 = BeanProperty
				.create("text");
		AutoBinding<PatientsInfo, String, JTextField, String> bind24 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE, patientInfo,
						jIntBeanProp24, txt_NiaNo, jTextFieldProp24);
		bind24.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc1 = BeanProperty
				.create("firstname");
		BeanProperty<JTextField, String> jTextFieldPropc1 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc1 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc1, txt_EmeFirstName,
						jTextFieldPropc1);
		bindc1.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc2 = BeanProperty
				.create("lastname");
		BeanProperty<JTextField, String> jTextFieldPropc2 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc2 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc2, txt_EmeLastName,
						jTextFieldPropc2);
		bindc2.setConverter(new Converter<String, String>() {
			@Override
			public String convertForward(String paramS) {
				return paramS;
			}

			@Override
			public String convertReverse(String paramT) {
				return paramT;
			}
		});
		bindc2.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc3 = BeanProperty
				.create("gender");
		BeanProperty<JComboBox, String> jComboBoxBeanPropertyc3 = BeanProperty
				.create("selectedItem");
		AutoBinding<ContactpersonInfo, String, JComboBox, String> bindc3 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc3, cob_EmeGender,
						jComboBoxBeanPropertyc3);
		bindc3.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc4 = BeanProperty
				.create("phone");
		BeanProperty<JTextField, String> jTextFieldPropc4 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc4 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc4, txt_EmePhone,
						jTextFieldPropc4);
		bindc4.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc5 = BeanProperty
				.create("cellPhone");
		BeanProperty<JTextField, String> jTextFieldPropc5 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc5 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc5, txt_empCellPhone,
						jTextFieldPropc5);
		bindc5.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc6 = BeanProperty
				.create("town");
		BeanProperty<JTextField, String> jTextFieldPropc6 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc6 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc6, txt_CPTown,
						jTextFieldPropc6);
		bindc6.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc7 = BeanProperty
				.create("state");
		BeanProperty<JTextField, String> jTextFieldPropc7 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc7 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc7, txt_EmeState,
						jTextFieldPropc7);
		bindc7.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc8 = BeanProperty
				.create("country");
		BeanProperty<JTextField, String> jTextFieldPropc8 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc8 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc8, txt_EmeCountry,
						jTextFieldPropc8);
		bindc8.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc9 = BeanProperty
				.create("address");
		BeanProperty<JTextArea, String> jTextFieldPropc9 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextArea, String> bindc9 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc9, txta_EmeAddress,
						jTextFieldPropc9);
		bindc9.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc10 = BeanProperty
				.create("occupation");
		BeanProperty<JTextField, String> jTextFieldPropc10 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc10 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc10, txt_EmeOccupation,
						jTextFieldPropc10);
		bindc10.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc11 = BeanProperty
				.create("language");
		BeanProperty<JTextField, String> jTextFieldPropc11 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc11 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc11, txt_EmeLanguage,
						jTextFieldPropc11);
		bindc11.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc12 = BeanProperty
				.create("maritalStatus");
		BeanProperty<JComboBox, String> jComboBoxBeanPropertyc12 = BeanProperty
				.create("selectedItem");
		AutoBinding<ContactpersonInfo, String, JComboBox, String> bindc12 = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc12,
						cob_EmeMaritalStatus, jComboBoxBeanPropertyc12);
		bindc12.setConverter(new Converter<String, String>() {

			@Override
			public String convertForward(String arg0) {
				return Frm_PatientMod.getMaritalStatusName(arg0);
			}

			@Override
			public String convertReverse(String arg0) {
				return Frm_PatientMod.m_MaritalStatus[Frm_PatientMod
						.getMaritalStatusIndexByFullName(arg0)];
			}

		});
		bindc12.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc13 = BeanProperty
				.create("placeOfBirth");
		BeanProperty<JTextField, String> jTextFieldPropc13 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc13 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc13,
						txt_EmePlaceOfBirth, jTextFieldPropc13);
		bindc13.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc14 = BeanProperty
				.create("tribe");
		BeanProperty<JTextField, String> jTextFieldPropc14 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc14 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc14, txt_EmeTribe,
						jTextFieldPropc14);
		bindc14.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc15 = BeanProperty
				.create("religion");
		BeanProperty<JTextField, String> jTextFieldPropc15 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc15 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc15, txt_EmeReligion,
						jTextFieldPropc15);
		bindc15.bind();

		BeanProperty<ContactpersonInfo, String> jIntBeanPropc16 = BeanProperty
				.create("relation");
		BeanProperty<JTextField, String> jTextFieldPropc16 = BeanProperty
				.create("text");
		AutoBinding<ContactpersonInfo, String, JTextField, String> bindc16 = Bindings
				.createAutoBinding(UpdateStrategy.READ_WRITE,
						contactpersonInfo, jIntBeanPropc16, txt_EmeRelation,
						jTextFieldPropc16);
		bindc16.bind();
	}

	/** 初始化權限 */
	private void initPermission() {
		// this.btn_OK.setEnabled(false);
	}

	/** 初始化病患資料欄位 */
	private void initPatientInfoCtrl() {
		this.txt_No.setText("");
		this.txt_FirstName.setText("");
		this.txt_LastName.setText("");
		this.txt_NhisNo.setText("");
		this.txt_NiaNo.setText("");
		this.txt_Height.setText("");
		this.txt_Weight.setText("");
		this.cob_Gender.setSelectedItem("");
		this.cob_Bloodtype.setSelectedItem("");
		this.cob_Rh.setSelectedItem("");
		this.txt_Address.setText("");
		this.txt_Town.setText("");
		this.txt_State.setText("");
		this.txt_Country.setText("");
		this.txt_Phone1.setText("");
		this.txt_Phone2.setText("");
		this.txt_Ps.setText("");
		this.btn_Enroll.setEnabled(false);
		this.btn_OK.setEnabled(false);
		this.showImage(null, "");
	}

	/** 初始化聯絡人欄位 */
	private void initContactCtrl() {
		this.txt_EmeFirstName.setText("");
		this.txt_EmeLastName.setText("");
		this.txt_EmePhone.setText("");
		this.txt_EmeRelation.setText("");
		this.txt_EmeState.setText("");
		this.txta_EmeAddress.setText("");
		this.txt_CPTown.setText("");
		this.txt_EmeCountry.setText("");
	}

	@SuppressWarnings("deprecation")
	private void initLanguage() {
		this.lab_TitleNo.setText(paragraph.getLanguage(line, "TITLENO"));
		this.lab_TitleFirstName.setText("* "
				+ paragraph.getLanguage(line, "TITLEFIRSTNAME"));
		this.lab_TitleLastName.setText("* "
				+ paragraph.getLanguage(line, "TITLELASTNAME"));
		this.lab_NhisNo.setText(paragraph.getLanguage(line, "NHISNO"));
		this.lab_NiaNo.setText(paragraph.getLanguage(line, "NIANO"));
		this.lab_PlaceOfBirth.setText(paragraph.getLanguage(line,
				"PLACEOFBIRTH"));
		this.lab_EmePlaceOfBirth.setText(paragraph.getLanguage(line,
				"PLACEOFBIRTH"));
		this.lab_MaritalStatus.setText(paragraph.getLanguage(line,
				"MARITALSTATUS"));
		this.lab_EmeMaritalStatus.setText(paragraph.getLanguage(line,
				"MARITALSTATUS"));
		this.lab_Occupation.setText(paragraph.getLanguage(line, "OCCUPATION"));
		this.lab_EmeOccupation.setText(paragraph
				.getLanguage(line, "OCCUPATION"));
		this.lab_Tribe.setText(paragraph.getLanguage(line, "TRIBE"));
		this.lab_EmeTribe.setText(paragraph.getLanguage(line, "TRIBE"));
		this.lab_Religion.setText(paragraph.getLanguage(line, "RELIGION"));
		this.lab_EmeReligion.setText(paragraph.getLanguage(line, "RELIGION"));
		this.lab_Language.setText(paragraph.getLanguage(line, "LANGUAGE"));
		this.lab_EmeLanguage.setText(paragraph.getLanguage(line, "LANGUAGE"));

		this.lab_TitleGender
				.setText(paragraph.getLanguage(line, "TITLEGENDER"));
		this.lab_EmeGender.setText(paragraph.getLanguage(line, "TITLEGENDER"));
		// this.lab_TitleBirth.setText(paragraph.getLanguage(line,
		// "TITLEBIRTH"));
		this.lab_TitleBloodtype.setText(paragraph.getLanguage(line,
				"TITLEBLOODTYPE"));
		this.lab_TitleHeight
				.setText(paragraph.getLanguage(line, "TITLEHEIGHT"));
		this.lab_TitleWeight
				.setText(paragraph.getLanguage(line, "TITLEWEIGHT"));

		this.check_Deal.setText(paragraph.getLanguage(line, "DEAL"));

		this.lab_TitleEmeFirstName.setText(paragraph.getLanguage(line,
				"TITLEEMEFIRSTNAME"));
		this.lab_TitleEmelastName.setText(paragraph.getLanguage(line,
				"TITLEEMELASTNAME"));
		this.lab_TitleEmeRelation.setText(paragraph.getLanguage(line,
				"TITLEEMERELATION"));
		this.lab_TitleCPTown.setText(paragraph.getLanguage(line,
				"TITLEEMECPTOWN"));
		this.lab_TitleEmeAddress.setText(paragraph.getLanguage(line,
				"TITLEEMEADDRESS"));
		this.lab_TitleEmePhone.setText(paragraph.getLanguage(line,
				"TITLEEMEPHONE"));
		// this.lab_TitleEmeState.setText(paragraph.getLanguage(line,
		// "TITLEEMESTATE"));
		this.lab_TitleEmeCountry.setText(paragraph.getLanguage(line,
				"TITLEEMECOUNTRY"));

		// this.lab_TitlePhone1.setText(paragraph.getLanguage(line,
		// "TITLEPHONE1"));
		// this.lab_TitlePhone2.setText(paragraph.getLanguage(line,
		// "TITLEPHONE2"));
		this.lab_TitleTown.setText(paragraph.getLanguage(line, "TITLETOWN"));
		// this.lab_TitleState.setText(paragraph.getLanguage(line,
		// "TITLESTATE"));
		this.lab_Country.setText(paragraph.getLanguage(line, "COUNTRY"));
		this.lab_TitleAddress.setText(paragraph.getLanguage(line,
				"TITLEADDRESS"));
		this.lab_Ps.setText(paragraph.getLanguage(line, "PS"));

		this.btn_Enroll.setText(paragraph.getLanguage(line, "ENROLL"));
		this.btn_OK.setText(paragraph.getLanguage(message, "SAVE"));
		this.btn_Cancel.setText(paragraph.getLanguage(line, "CANCEL"));

		this.pan_Right.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getLanguage(line, "RIGHT")));
		this.pan_Left.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getLanguage(line, "LEFT")));
		this.jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder(paragraph.getLanguage(line, "PANEL")));
		this.Pat_List.setTitleAt(0,
				paragraph.getLanguage(line, "PATIENTINFRMATION"));
		this.Pat_List.setTitleAt(1,
				paragraph.getLanguage(line, "CONTRACTPERSON"));

	}

	// 初始化 種族 宗教
	private void initcBox() {
		cbox_Trible.addItem("");
		cbox_Religion.addItem("");

		ReligionDao religionDao = new ReligionDao();
		List<Religion> religions = religionDao.QueryReligions();
		for (Religion religion : religions)
			cbox_Religion.addItem(religionDao.getComposedString(religion));

		HlsGroupDao hlsgpDao = new HlsGroupDao();
		List<HlsGroup> hlsGrps = hlsgpDao.QueryHlsGroups();
		for (HlsGroup hlsGrp : hlsGrps)
			cbox_Trible.addItem(hlsgpDao.getComposedString(hlsGrp));
	}

	private static int getMaritalStatusIndexByFullName(String marital_status) {

		if (marital_status == null || marital_status.equals("Other")) {
			return 3;
		} else if (marital_status.equals("Married")) {
			return 0;
		} else if (marital_status.equals("Divorsed")) {
			return 1;
		}
		return 2;

	}

	private static int getMaritalStatusIndex(String marital_status) {

		if (marital_status == null || marital_status.equals("o")) {
			return 3;
		} else if (marital_status.equals("m")) {
			return 0;
		} else if (marital_status.equals("d")) {
			return 1;
		}
		return 2;

	}

	private static String getMaritalStatusName(String marital_status) {

		if (marital_status == null || marital_status.equals("o")) {
			return "Other";
		} else if (marital_status.equals("m")) {
			return "Married";
		} else if (marital_status.equals("d")) {
			return "Divorsed";
		}
		return "Single";

	}

	/** 檢查病患編號是否存在 */
	/* 如存在顯示病患、聯絡人資料 */
	@SuppressWarnings("unused")
	private void setPatientIndo(String p_no) {
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "SELECT *, CONCAT(patients_info.tribe,' - ', hls_group.descrition) AS hls_group  ,CONCAT(patients_info.religion,' - ', religion.descrition) AS hls_religion  "
					+ "FROM patients_info LEFT JOIN hls_group ON  hls_group.value = patients_info.tribe "
					+ "LEFT JOIN religion ON religion.value = patients_info.religion  WHERE p_no ='"
					+ p_no + "' ";
			System.out.println(sql);
			rs = DBC.executeQuery(sql);
			if (rs.next()) {

				String date = rs.getString("birth").substring(8, 10) + "-"
						+ rs.getString("birth").substring(5, 7) + "-"
						+ rs.getString("birth").substring(0, 4);

				this.txt_No.setText(rs.getString("p_no"));
				this.txt_FirstName.setText(rs.getString("firstname"));
				this.txt_LastName.setText(rs.getString("lastname"));

				this.txt_NhisNo.setText(rs.getString("nhis_no"));
				this.txt_NiaNo.setText(rs.getString("nia_no"));

				this.dateChooser1.setValue(date);

				this.txt_Height.setText(rs.getString("height"));
				this.txt_Weight.setText(rs.getString("weight"));
				this.cob_Gender.setSelectedItem(rs.getString("gender"));
				this.cob_Bloodtype.setSelectedItem(rs.getString("bloodtype"));
				this.cob_Rh.setSelectedItem(rs.getString("rh_type"));
				this.txt_Address.setText(rs.getString("address"));
				this.txt_Town.setText(rs.getString("town"));
				this.txt_State.setText(rs.getString("state"));
				this.txt_Country.setText(rs.getString("country"));
				this.txt_Phone1.setText(rs.getString("phone"));
				this.txt_Phone2.setText(rs.getString("cell_phone"));
				this.txt_Ps.setText(rs.getString("ps"));
				this.txt_Occupation.setText(rs.getString("occupation"));
				this.txt_Language.setText(rs.getString("language"));

				this.cob_MaritalStatus
						.setSelectedIndex(getMaritalStatusIndex(rs
								.getString("marital_status")));

				this.txt_PlaceOfBirth.setText(rs.getString("place_of_birth"));

				this.cbox_Trible.setSelectedItem(rs.getString("hls_group"));
				this.cbox_Religion
						.setSelectedItem(rs.getString("hls_religion"));
				if (rs.getString("dead_guid") != null) {
					check_Deal.setSelected(true);
					showDealTimer();
				}
				/* 病患聯絡人資料 */
				sql = "SELECT * FROM contactperson_info WHERE guid = '"
						+ rs.getString("cp_guid") + "'";
				rs = DBC.executeQuery(sql);
				if (rs.next()) {
					this.txt_EmeFirstName.setText(rs.getString("firstName"));
					this.txt_EmeLastName.setText(rs.getString("lastName"));
					this.cob_EmeGender.setSelectedItem(rs.getString("gender"));
					this.txt_EmeOccupation.setText(rs.getString("occupation"));
					this.txt_EmeLanguage.setText(rs.getString("language"));
					this.cob_EmeMaritalStatus
							.setSelectedIndex(getMaritalStatusIndex(rs
									.getString("marital_status")));
					this.txt_EmePlaceOfBirth.setText(rs
							.getString("place_of_birth"));
					this.txt_EmePhone.setText(rs.getString("phone"));
					this.txt_EmeTribe.setText(rs.getString("tribe"));
					this.txt_EmeReligion.setText(rs.getString("religion"));
					this.txt_EmeRelation.setText(rs.getString("relation"));
					this.txt_EmeState.setText(rs.getString("state"));
					this.txt_empCellPhone.setText(rs.getString("cell_phone"));
					this.txta_EmeAddress.setText(rs.getString("address"));
					this.txt_CPTown.setText(rs.getString("town"));
					this.txt_EmeCountry.setText(rs.getString("country"));
				}
			}
		} catch (SQLException e) {
			System.out.println(e);
			ErrorMessage.setData(
					"Patients",
					"Frm_PatientMod",
					"setPatientIndo(String p_no)",
					e.toString().substring(e.toString().lastIndexOf(".") + 1,
							e.toString().length()));
			initPatientInfoCtrl();
			initContactCtrl();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				ErrorMessage.setData(
						"Patients",
						"Frm_PatientMod",
						"setPatientIndo(String p_no) - DBC.closeConnection",
						e.toString().substring(
								e.toString().lastIndexOf(".") + 1,
								e.toString().length()));
			}
		}
	}

	private void showDealTimer() {
		String sql = "SELECT * FROM death_info "
				+ "WHERE guid = (SELECT dead_guid FROM patients_info "
				+ "WHERE p_no = '" + this.txt_No.getText() + "')";
		try {
			ResultSet rs = DBC.executeQuery(sql);
			rs.next();
			this.check_Deal.setText(paragraph.getLanguage(line, "DEATHTIME")
					+ "\n" + rs.getString("date_of_death").substring(0, 16));
		} catch (SQLException e) {
			ErrorMessage.setData(
					"Patients",
					"Frm_PatientMod",
					"showDealTimer()",
					e.toString().substring(e.toString().lastIndexOf(".") + 1,
							e.toString().length()));
			Logger.getLogger(Frm_PatientMod.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

	private boolean isCanSave() {
		try {
			if (this.patientInfo.getFirstname().equals("")
					|| this.patientInfo.getLastname().equals("")
					|| this.patientInfo.getCellPhone().equals("")) {
				return false;
			}
			return true;
		} catch (Exception e) { // catch all null pointer exceptions
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jDialog1 = new javax.swing.JDialog();
		lab_date_of_death = new javax.swing.JLabel();
		dia_spi_Hour = new javax.swing.JSpinner();
		dia_spi_Min = new javax.swing.JSpinner();
		jScrollPane2 = new javax.swing.JScrollPane();
		dia_txt_Cause = new javax.swing.JTextArea();
		jLabel2 = new javax.swing.JLabel();
		btn_DealSave = new javax.swing.JButton();
		btn_DealCancel = new javax.swing.JButton();
		dia_cob_date = new cc.johnwu.date.DateComboBox();
		Pat_List = new javax.swing.JTabbedPane();
		pan_Left = new javax.swing.JPanel();
		pan_Noth = new javax.swing.JPanel();
		lab_TitleNo = new javax.swing.JLabel();
		txt_FirstName = new javax.swing.JFormattedTextField();
		lab_TitleFirstName = new javax.swing.JLabel();
		lab_TitleGender = new javax.swing.JLabel();
		cob_Gender = new javax.swing.JComboBox();
		lab_TitleLastName = new javax.swing.JLabel();
		txt_LastName = new javax.swing.JFormattedTextField();
		txt_NhisNo = new javax.swing.JFormattedTextField();
		lab_NhisNo = new javax.swing.JLabel();
		txt_No = new javax.swing.JTextField();
		lab_NiaNo = new javax.swing.JLabel();
		txt_NiaNo = new javax.swing.JTextField();
		jPanel3 = new javax.swing.JPanel();
		dateChooser1 = new cc.johnwu.date.DateChooser();
		lab_TitleBirth = new javax.swing.JLabel();
		lab_PlaceOfBirth = new javax.swing.JLabel();
		txt_PlaceOfBirth = new javax.swing.JTextField();
		lab_MaritalStatus = new javax.swing.JLabel();
		cob_MaritalStatus = new javax.swing.JComboBox();
		lab_Occupation = new javax.swing.JLabel();
		txt_Occupation = new javax.swing.JTextField();
		check_Deal = new javax.swing.JCheckBox();
		jPanel2 = new javax.swing.JPanel();
		txt_Phone2 = new javax.swing.JFormattedTextField();
		txt_Phone1 = new javax.swing.JFormattedTextField();
		txt_State = new javax.swing.JFormattedTextField();
		txt_Town = new javax.swing.JFormattedTextField();
		jScrollPane4 = new javax.swing.JScrollPane();
		txt_Ps = new javax.swing.JTextArea();
		jScrollPane3 = new javax.swing.JScrollPane();
		txt_Address = new javax.swing.JTextArea();
		lab_Ps = new javax.swing.JLabel();
		lab_TitlePhone2 = new javax.swing.JLabel();
		lab_TitlePhone1 = new javax.swing.JLabel();
		lab_TitleState = new javax.swing.JLabel();
		lab_TitleAddress = new javax.swing.JLabel();
		lab_TitleTown = new javax.swing.JLabel();
		txt_Country = new javax.swing.JTextField();
		lab_Country = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		fingerPrintViewer1 = new cc.johnwu.finger.FingerPrintViewer();
		btn_Enroll = new javax.swing.JButton();
		lab_Language = new javax.swing.JLabel();
		txt_Language = new javax.swing.JTextField();
		jPanel4 = new javax.swing.JPanel();
		lab_Tribe = new javax.swing.JLabel();
		lab_Religion = new javax.swing.JLabel();
		cob_Bloodtype = new javax.swing.JComboBox();
		lab_TitleBloodtype = new javax.swing.JLabel();
		cob_Rh = new javax.swing.JComboBox();
		txt_Height = new javax.swing.JFormattedTextField();
		txt_Weight = new javax.swing.JFormattedTextField();
		lab_TitleWeight = new javax.swing.JLabel();
		lab_TitleHeight = new javax.swing.JLabel();
		cbox_Trible = new javax.swing.JComboBox();
		cbox_Religion = new javax.swing.JComboBox();
		pan_Right = new javax.swing.JPanel();
		lab_TitleCPTown = new javax.swing.JLabel();
		lab_TitleEmePhone = new javax.swing.JLabel();
		lab_TitleEmelastName = new javax.swing.JLabel();
		lab_TitleEmeRelation = new javax.swing.JLabel();
		lab_TitleEmeAddress = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		txta_EmeAddress = new javax.swing.JTextArea();
		txt_EmeLastName = new javax.swing.JFormattedTextField();
		txt_EmeRelation = new javax.swing.JFormattedTextField();
		txt_EmePhone = new javax.swing.JFormattedTextField();
		txt_CPTown = new javax.swing.JFormattedTextField();
		lab_TitleEmeState = new javax.swing.JLabel();
		txt_EmeState = new javax.swing.JFormattedTextField();
		lab_TitleEmeFirstName = new javax.swing.JLabel();
		txt_EmeFirstName = new javax.swing.JFormattedTextField();
		lab_TitleEmeCountry = new javax.swing.JLabel();
		txt_EmeCountry = new javax.swing.JFormattedTextField();
		txt_EmeTribe = new javax.swing.JTextField();
		txt_EmePlaceOfBirth = new javax.swing.JTextField();
		txt_EmeReligion = new javax.swing.JTextField();
		txt_EmeOccupation = new javax.swing.JTextField();
		lab_EmeOccupation = new javax.swing.JLabel();
		lab_EmeMaritalStatus = new javax.swing.JLabel();
		lab_EmePlaceOfBirth = new javax.swing.JLabel();
		lab_EmeTribe = new javax.swing.JLabel();
		lab_EmeReligion = new javax.swing.JLabel();
		lab_EmeGender = new javax.swing.JLabel();
		cob_EmeGender = new javax.swing.JComboBox();
		cob_EmeMaritalStatus = new javax.swing.JComboBox();
		lab_EmeLanguage = new javax.swing.JLabel();
		txt_EmeLanguage = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		txt_empCellPhone = new javax.swing.JTextField();
		pan_BtnGrp = new javax.swing.JPanel();
		btn_Cancel = new javax.swing.JButton();
		btn_OK = new javax.swing.JButton();
		bar_menu = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		mnit_XRayHistory = new javax.swing.JMenuItem();
		mnit_LabHistory = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();

		jDialog1.setTitle("Death records");
		jDialog1.setMinimumSize(new java.awt.Dimension(407, 181));
		jDialog1.setModal(true);
		jDialog1.setResizable(false);
		jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				jDialog1WindowClosing(evt);
			}
		});

		lab_date_of_death.setText("Datetime :");

		dia_spi_Hour.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));

		dia_spi_Min.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));

		dia_txt_Cause.setColumns(20);
		dia_txt_Cause.setRows(5);
		jScrollPane2.setViewportView(dia_txt_Cause);

		jLabel2.setText("Cause :");

		btn_DealSave.setText("Save");
		btn_DealSave.setPreferredSize(new java.awt.Dimension(100, 29));
		btn_DealSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_DealSaveActionPerformed(evt);
			}
		});

		btn_DealCancel.setText("Cancel");
		btn_DealCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_DealCancelActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(
				jDialog1.getContentPane());
		jDialog1.getContentPane().setLayout(jDialog1Layout);
		jDialog1Layout
				.setHorizontalGroup(jDialog1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jDialog1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jDialog1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																jDialog1Layout
																		.createSequentialGroup()
																		.addComponent(
																				btn_DealCancel,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				95,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btn_DealSave,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jDialog1Layout
																		.createSequentialGroup()
																		.addGroup(
																				jDialog1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lab_date_of_death)
																						.addComponent(
																								jLabel2))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jDialog1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addComponent(
																								jScrollPane2,
																								javax.swing.GroupLayout.Alignment.LEADING,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								jDialog1Layout
																										.createSequentialGroup()
																										.addComponent(
																												dia_cob_date,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												dia_spi_Hour,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												dia_spi_Min,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jDialog1Layout
				.setVerticalGroup(jDialog1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jDialog1Layout
										.createSequentialGroup()
										.addGap(12, 12, 12)
										.addGroup(
												jDialog1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.CENTER)
														.addComponent(
																lab_date_of_death)
														.addComponent(
																dia_spi_Min,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																29,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																dia_spi_Hour,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																29,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																dia_cob_date,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jDialog1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jDialog1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jScrollPane2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jDialog1Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								btn_DealSave,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								btn_DealCancel)))
														.addComponent(jLabel2))
										.addContainerGap(29, Short.MAX_VALUE)));

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("New Patient");

		pan_Left.setBackground(new java.awt.Color(234, 234, 234));
		pan_Left.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Patient Information"));
		pan_Left.setPreferredSize(new java.awt.Dimension(383, 402));

		lab_TitleNo.setText("Patient No. :");

		txt_FirstName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txt_FirstNameKeyReleased(evt);
			}
		});

		lab_TitleFirstName.setText("First Name :");

		lab_TitleGender.setText("Gender :");

		cob_Gender.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"M", "F" }));
		cob_Gender.setRequestFocusEnabled(false);

		lab_TitleLastName.setText("Last Name :");

		txt_LastName.setHighlighter(null);
		txt_LastName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txt_LastNameKeyReleased(evt);
			}
		});

		lab_NhisNo.setText("NHIS No. :");

		txt_No.setText("00000000");
		txt_No.setEnabled(false);

		lab_NiaNo.setText("NIA No. :");

		javax.swing.GroupLayout pan_NothLayout = new javax.swing.GroupLayout(
				pan_Noth);
		pan_Noth.setLayout(pan_NothLayout);
		pan_NothLayout
				.setHorizontalGroup(pan_NothLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_NothLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_NothLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lab_TitleLastName,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleFirstName,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_NiaNo,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleNo,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_NhisNo,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addGap(24, 24, 24)
										.addGroup(
												pan_NothLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txt_NiaNo,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																297,
																Short.MAX_VALUE)
														.addComponent(
																txt_NhisNo,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																297,
																Short.MAX_VALUE)
														.addComponent(
																txt_FirstName,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																297,
																Short.MAX_VALUE)
														.addGroup(
																pan_NothLayout
																		.createSequentialGroup()
																		.addComponent(
																				txt_No,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				192,
																				Short.MAX_VALUE)
																		.addGap(7,
																				7,
																				7)
																		.addComponent(
																				lab_TitleGender)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cob_Gender,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				54,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																txt_LastName,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																297,
																Short.MAX_VALUE))));
		pan_NothLayout
				.setVerticalGroup(pan_NothLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_NothLayout
										.createSequentialGroup()
										.addGroup(
												pan_NothLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitleNo)
														.addComponent(
																txt_No,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_TitleGender)
														.addComponent(
																cob_Gender,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_NothLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE,
																false)
														.addComponent(
																txt_FirstName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_TitleFirstName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_NothLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitleLastName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_LastName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_NothLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_NhisNo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_NhisNo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												pan_NothLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lab_NiaNo)
														.addComponent(
																txt_NiaNo,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))));

		lab_TitleBirth.setText("Date Of Birth :");

		lab_PlaceOfBirth.setText("Place Of Birth :");

		lab_MaritalStatus.setText("Marital Status :");

		cob_MaritalStatus.setModel(new javax.swing.DefaultComboBoxModel(
				LOCALED_MARRITALNAME));

		lab_Occupation.setText("Occupation :");

		check_Deal.setText("Dead");
		check_Deal.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				check_DealActionPerformed(evt);
			}
		});

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
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lab_Occupation,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleBirth,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_PlaceOfBirth,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_MaritalStatus,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txt_Occupation,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																292,
																Short.MAX_VALUE)
														.addComponent(
																cob_MaritalStatus,
																0, 292,
																Short.MAX_VALUE)
														.addComponent(
																txt_PlaceOfBirth,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																292,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel3Layout
																		.createSequentialGroup()
																		.addComponent(
																				dateChooser1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																				119,
																				Short.MAX_VALUE)
																		.addComponent(
																				check_Deal)))
										.addContainerGap()));
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
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleBirth,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanel3Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(
																				dateChooser1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				check_Deal)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE,
																false)
														.addComponent(
																lab_PlaceOfBirth)
														.addComponent(
																txt_PlaceOfBirth,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_MaritalStatus)
														.addComponent(
																cob_MaritalStatus,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_Occupation,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_Occupation))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		txt_Phone2.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txt_Phone2KeyReleased(evt);
			}
		});

		txt_Ps.setColumns(20);
		txt_Ps.setLineWrap(true);
		txt_Ps.setRows(3);
		jScrollPane4.setViewportView(txt_Ps);

		txt_Address.setColumns(20);
		txt_Address.setLineWrap(true);
		txt_Address.setRows(3);
		jScrollPane3.setViewportView(txt_Address);

		lab_Ps.setText("P.S. :");

		lab_TitlePhone2.setText("*Cell Phone :");

		lab_TitlePhone1.setText("Phone :");

		lab_TitleState.setText("Region :");

		lab_TitleAddress.setText("Address :");

		lab_TitleTown.setText("City/Town :");

		lab_Country.setText("Country :");

		jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Fingerprint"));

		fingerPrintViewer1.setMaximumSize(new java.awt.Dimension(100, 150));
		fingerPrintViewer1.setNormalBounds(new java.awt.Rectangle(0, 0, 120,
				180));
		fingerPrintViewer1.setVisible(true);

		javax.swing.GroupLayout fingerPrintViewer1Layout = new javax.swing.GroupLayout(
				fingerPrintViewer1.getContentPane());
		fingerPrintViewer1.getContentPane().setLayout(fingerPrintViewer1Layout);
		fingerPrintViewer1Layout.setHorizontalGroup(fingerPrintViewer1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 78, Short.MAX_VALUE));
		fingerPrintViewer1Layout.setVerticalGroup(fingerPrintViewer1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 0, Short.MAX_VALUE));

		btn_Enroll.setText("Enroll");
		btn_Enroll.setMaximumSize(new java.awt.Dimension(120, 29));
		btn_Enroll.setMinimumSize(new java.awt.Dimension(120, 29));
		btn_Enroll.setPreferredSize(new java.awt.Dimension(120, 29));
		btn_Enroll.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_EnrollActionPerformed(evt);
			}
		});

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
										.addComponent(
												fingerPrintViewer1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_Enroll,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												92,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(140, Short.MAX_VALUE)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(fingerPrintViewer1,
								javax.swing.GroupLayout.PREFERRED_SIZE, 146,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												btn_Enroll,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												56,
												javax.swing.GroupLayout.PREFERRED_SIZE)));

		lab_Language.setText("Language :");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lab_Country,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lab_TitleAddress,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lab_TitleTown,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lab_TitlePhone1,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lab_TitleState,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lab_Ps,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lab_TitlePhone2,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								lab_Language,
																								javax.swing.GroupLayout.Alignment.TRAILING))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jPanel2Layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								txt_State,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								273,
																								Short.MAX_VALUE)
																						.addComponent(
																								txt_Phone1,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								273,
																								Short.MAX_VALUE)
																						.addComponent(
																								jScrollPane4,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								270,
																								Short.MAX_VALUE)
																						.addComponent(
																								txt_Town,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								273,
																								Short.MAX_VALUE)
																						.addComponent(
																								jScrollPane3,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								273,
																								Short.MAX_VALUE)
																						.addComponent(
																								txt_Language,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								273,
																								Short.MAX_VALUE)
																						.addComponent(
																								txt_Phone2,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								273,
																								Short.MAX_VALUE)
																						.addComponent(
																								txt_Country,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								273,
																								Short.MAX_VALUE)))
														.addComponent(
																jPanel1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel2Layout
										.createSequentialGroup()
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_Language)
														.addComponent(
																txt_Language,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(7, 7, 7)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitlePhone1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																22,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_Phone1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitlePhone2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																21,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_Phone2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitleTown,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_Town,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitleState,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txt_State,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_Country,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_Country))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lab_TitleAddress,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jScrollPane3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lab_Ps,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanel2Layout
																		.createSequentialGroup()
																		.addGap(2,
																				2,
																				2)
																		.addComponent(
																				jScrollPane4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				29,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jPanel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		lab_Tribe.setText("Tribe :");

		lab_Religion.setText("Religion :");

		cob_Bloodtype.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "", "A", "B", "AB", "O" }));

		lab_TitleBloodtype.setText("Blood/RH :");

		cob_Rh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "",
				"+", "-" }));

		lab_TitleWeight.setText("Weight :");

		lab_TitleHeight.setText("Height :");

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
										.addContainerGap()
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lab_Tribe,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_Religion,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleWeight,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleBloodtype,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleHeight,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txt_Weight,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																310,
																Short.MAX_VALUE)
														.addComponent(
																txt_Height,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																310,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jPanel4Layout
																		.createSequentialGroup()
																		.addComponent(
																				cob_Bloodtype,
																				0,
																				152,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				cob_Rh,
																				0,
																				152,
																				Short.MAX_VALUE))
														.addComponent(
																cbox_Trible, 0,
																310,
																Short.MAX_VALUE)
														.addComponent(
																cbox_Religion,
																0, 310,
																Short.MAX_VALUE))
										.addContainerGap()));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel4Layout
										.createSequentialGroup()
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																cob_Bloodtype,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																cob_Rh,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_TitleBloodtype))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_Height,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_TitleHeight,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_Weight,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_TitleWeight,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																19,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lab_Tribe)
														.addComponent(
																cbox_Trible,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_Religion)
														.addComponent(
																cbox_Religion,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		javax.swing.GroupLayout pan_LeftLayout = new javax.swing.GroupLayout(
				pan_Left);
		pan_Left.setLayout(pan_LeftLayout);
		pan_LeftLayout
				.setHorizontalGroup(pan_LeftLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_LeftLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_LeftLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jPanel4,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanel3,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																pan_Noth,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(12, 12, 12)));
		pan_LeftLayout
				.setVerticalGroup(pan_LeftLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_LeftLayout
										.createSequentialGroup()
										.addGroup(
												pan_LeftLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pan_LeftLayout
																		.createSequentialGroup()
																		.addComponent(
																				pan_Noth,
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
																				jPanel4,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jPanel2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGap(21, 21, 21)));

		Pat_List.addTab("Patient Infrmation", pan_Left);

		pan_Right.setBackground(new java.awt.Color(234, 234, 234));
		pan_Right.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Contact Person"));
		pan_Right.setPreferredSize(new java.awt.Dimension(383, 402));

		lab_TitleCPTown.setText("Town :");

		lab_TitleEmePhone.setText("Phone :");

		lab_TitleEmelastName.setText("Last Name :");

		lab_TitleEmeRelation.setText("Releation :");

		lab_TitleEmeAddress.setText("Address :");

		txta_EmeAddress.setColumns(20);
		txta_EmeAddress.setLineWrap(true);
		txta_EmeAddress.setRows(3);
		jScrollPane1.setViewportView(txta_EmeAddress);

		lab_TitleEmeState.setText("Region :");

		lab_TitleEmeFirstName.setText("First Name :");

		lab_TitleEmeCountry.setText("Country :");

		lab_EmeOccupation.setText("Occupation :");

		lab_EmeMaritalStatus.setText("Marital Status :");

		lab_EmePlaceOfBirth.setText("Place Of Birth :");

		lab_EmeTribe.setText("Tribe :");

		lab_EmeReligion.setText("Religion :");

		lab_EmeGender.setText("Gender :");

		cob_EmeGender.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "M", "F" }));
		cob_EmeGender.setRequestFocusEnabled(false);

		cob_EmeMaritalStatus.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "Married", "Divorced", "Single", "Other" }));

		lab_EmeLanguage.setText("Language");

		jLabel1.setText("Cell Phone:");

		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_Right.setLayout(pan_RightLayout);
		pan_RightLayout
				.setHorizontalGroup(pan_RightLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_RightLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lab_EmePlaceOfBirth,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_EmeTribe,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleEmeAddress,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleEmePhone,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleEmelastName,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleCPTown,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_EmeOccupation,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_EmeMaritalStatus,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jLabel1,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_TitleEmeFirstName,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_EmeGender,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_EmeLanguage,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lab_EmeReligion,
																javax.swing.GroupLayout.Alignment.TRAILING))
										.addGap(12, 12, 12)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																txt_empCellPhone,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																txt_EmeLanguage,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																cob_EmeMaritalStatus,
																0, 672,
																Short.MAX_VALUE)
														.addComponent(
																txt_EmeLastName,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																txt_EmeReligion,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																txt_EmeOccupation,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																txt_EmeTribe,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																txt_EmePlaceOfBirth,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addComponent(
																txt_EmeFirstName,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE)
														.addGroup(
																pan_RightLayout
																		.createSequentialGroup()
																		.addComponent(
																				txt_CPTown,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				167,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lab_TitleEmeState)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txt_EmeState,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				165,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				lab_TitleEmeCountry)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txt_EmeCountry,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				223,
																				Short.MAX_VALUE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																pan_RightLayout
																		.createSequentialGroup()
																		.addComponent(
																				cob_EmeGender,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				54,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				lab_TitleEmeRelation)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				txt_EmeRelation,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				560,
																				Short.MAX_VALUE))
														.addComponent(
																txt_EmePhone,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																672,
																Short.MAX_VALUE))
										.addContainerGap()));
		pan_RightLayout
				.setVerticalGroup(pan_RightLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_RightLayout
										.createSequentialGroup()
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitleEmeFirstName)
														.addComponent(
																txt_EmeFirstName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitleEmelastName)
														.addComponent(
																txt_EmeLastName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_EmeGender)
														.addComponent(
																cob_EmeGender,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_TitleEmeRelation)
														.addComponent(
																txt_EmeRelation,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_EmePlaceOfBirth,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_EmePlaceOfBirth))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_TitleEmePhone)
														.addComponent(
																txt_EmePhone,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel1)
														.addComponent(
																txt_empCellPhone,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																lab_EmeMaritalStatus)
														.addComponent(
																cob_EmeMaritalStatus,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_EmeOccupation,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_EmeOccupation))
										.addGap(8, 8, 8)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_EmeLanguage,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_EmeLanguage))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_EmeTribe,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_EmeTribe))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																txt_EmeReligion,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_EmeReligion))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pan_RightLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				txt_CPTown,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				txt_EmeCountry,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				lab_TitleEmeCountry)
																		.addComponent(
																				lab_TitleEmeState)
																		.addComponent(
																				txt_EmeState,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																lab_TitleCPTown))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_RightLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																lab_TitleEmeAddress)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																162,
																Short.MAX_VALUE))
										.addContainerGap()));

		Pat_List.addTab("Contract Person", pan_Right);

		btn_Cancel.setText("Cancel");
		btn_Cancel.setMaximumSize(new java.awt.Dimension(60, 29));
		btn_Cancel.setMinimumSize(new java.awt.Dimension(60, 29));
		btn_Cancel.setPreferredSize(new java.awt.Dimension(40, 29));
		btn_Cancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CancelActionPerformed(evt);
			}
		});

		btn_OK.setText("Save");
		btn_OK.setMaximumSize(new java.awt.Dimension(60, 29));
		btn_OK.setMinimumSize(new java.awt.Dimension(60, 29));
		btn_OK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_OKActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_BtnGrpLayout = new javax.swing.GroupLayout(
				pan_BtnGrp);
		pan_BtnGrp.setLayout(pan_BtnGrpLayout);
		pan_BtnGrpLayout
				.setHorizontalGroup(pan_BtnGrpLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pan_BtnGrpLayout
										.createSequentialGroup()
										.addContainerGap(582, Short.MAX_VALUE)
										.addComponent(
												btn_OK,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btn_Cancel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		pan_BtnGrpLayout.setVerticalGroup(pan_BtnGrpLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				pan_BtnGrpLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(btn_Cancel,
								javax.swing.GroupLayout.PREFERRED_SIZE, 29,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_OK,
								javax.swing.GroupLayout.PREFERRED_SIZE, 29,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		jMenu1.setText("File");
		jMenu1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenu1ActionPerformed(evt);
			}
		});

		jMenuItem1.setText("Drug Allergy");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem2.setText("Medical Record");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		mnit_XRayHistory.setText("Radiology(X-RAY) Record");
		mnit_XRayHistory.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_XRayHistoryActionPerformed(evt);
			}
		});
		jMenu1.add(mnit_XRayHistory);

		mnit_LabHistory.setText("Laboratory Record");
		mnit_LabHistory.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_LabHistoryActionPerformed(evt);
			}
		});
		jMenu1.add(mnit_LabHistory);

		jMenuItem3.setText("Cashier Record");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem3);

		bar_menu.add(jMenu1);

		setJMenuBar(bar_menu);

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
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		pan_BtnGrp,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addContainerGap())
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		Pat_List,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		793,
																		Short.MAX_VALUE)
																.addGap(15, 15,
																		15)))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(Pat_List,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										538, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(pan_BtnGrp,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btn_DealSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_DealSaveActionPerformed

		try {
			if (deathInfo == null) {
				deathInfo = new DeathInfo();
				String uuid = UUID.randomUUID().toString();
				deathInfo.setGuid(uuid);
			}
			deathInfo.setCause(this.dia_txt_Cause.getText());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
			try {
				deathInfo.setDateOfDeath(format.parse(String.format("%s:%s:%s",
						dia_cob_date.getValue().toString(), dia_spi_Hour
								.getValue().toString(), dia_spi_Min.getValue()
								.toString())));
			} catch (ParseException e) {
				e.printStackTrace();
				deathInfo.setDateOfDeath(new Date());
			}
			deathInfo = deathInfoDao.merge(deathInfo);
			deathInfoDao.persist(deathInfo);
			patientInfo.setDeathInfo(deathInfo);
		} catch (Exception e) {
			ErrorMessage
					.setData(
							"Patients",
							"Frm_PatientMod",
							"btn_DealSaveActionPerformed(java.awt.event.ActionEvent evt)",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
			System.out.println(e);
		}
		this.jDialog1.setVisible(false);
	}// GEN-LAST:event_btn_DealSaveActionPerformed

	private void btn_DealCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_DealCancelActionPerformed
		this.jDialog1.setVisible(false);
		try {
			patientInfo.setDeathInfo(null);
			if (deathInfo != null)
				deathInfoDao.remove(deathInfo);
			this.check_Deal.setSelected(false);
		} catch (Exception e) {
			ErrorMessage
					.setData(
							"Patients",
							"Frm_PatientMod",
							"btn_DealCancelActionPerformed(java.awt.event.ActionEvent evt)",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
		}
	}// GEN-LAST:event_btn_DealCancelActionPerformed

	private void jDialog1WindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_jDialog1WindowClosing
		this.jDialog1.setVisible(false);
		if (deathInfo != null
				&& patientInfo.getDeathInfo().getGuid() == deathInfo.getGuid()) {
			this.check_Deal.setSelected(true);
		} else {
			this.check_Deal.setSelected(false);
		}
	}// GEN-LAST:event_jDialog1WindowClosing

	private void btn_OKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_OKActionPerformed
		try {
			patientInfo.setExist((byte) 1);

			cc.johnwu.login.ChangesLog.ChangesLog("patients_info",
					this.txt_No.getText(), "add");
			/* 新增或修改聯絡人資料 */
			if (!txt_EmeRelation.getText().trim().equals("")
					|| !this.txt_EmeFirstName.getText().trim().equals("")
					|| !this.txt_EmeLastName.getText().trim().equals("")) {

				if (patientInfo.getContactpersonInfo() == null) {
					String uuid = UUID.randomUUID().toString();
					contactpersonInfo.setGuid(uuid);
					contactpersonInfo = contactpersonInfoDao
							.merge(contactpersonInfo);
					contactpersonInfoDao.persist(contactpersonInfo);
					patientInfo.setContactpersonInfo(contactpersonInfo);
				}

			}
			this.patientInfoDao.persist(patientInfo);
			etx.commit();
			// ***********************列印barcode
			if (this.m_Status.equals("NEW")) {
				PrintBarcode.PrintBarcode(txt_No.getText());
			}

			
			// ************************
			JOptionPane.showMessageDialog(new Frame(),
					paragraph.getString("SAVECOMPLETE"));
			btn_CancelActionPerformed(null);
			m_frame.onPatientMod(this.txt_No.getText());
		} catch (Exception e) {
			ErrorMessage.setData(
					"Patients",
					"Frm_PatientMod",
					"btn_OKActionPerformed(java.awt.event.ActionEvent evt)",
					e.toString().substring(e.toString().lastIndexOf(".") + 1,
							e.toString().length()));
			System.out.println(e);
		}
	}// GEN-LAST:event_btn_OKActionPerformed

	private void btn_CancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CancelActionPerformed
		if (etx.isActive())
			etx.rollback();
		if (m_frame != null)
			m_frame.reLoad();
		dispose();
	}// GEN-LAST:event_btn_CancelActionPerformed

	private void btn_EnrollActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_EnrollActionPerformed
		if (this.txt_No.getText().equals("")) {
			btn_OKActionPerformed(null);
		}
		try {
			PreparedStatement pstmt = DBC
					.prepareStatement("INSERT INTO fingertemplate VALUES(?, ?, ?)");
			FingerPrintScanner.enroll(this.txt_No.getText(), pstmt);
			JOptionPane.showMessageDialog(new Frame(), "Saved successfully.");

		} catch (SQLException e) {
			ErrorMessage
					.setData(
							"Patients",
							"Frm_PatientMod",
							"btn_EnrollActionPerformed(java.awt.event.ActionEvent evt)",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
			System.out.println(e);
		}
	}// GEN-LAST:event_btn_EnrollActionPerformed

	private void check_DealActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_check_DealActionPerformed
		Point p = this.getLocation();
		String timer = null;
		int x = p.x + (this.getWidth() - jDialog1.getWidth()) / 2;
		int y = p.y + (this.getHeight() - jDialog1.getHeight()) / 2;
		this.jDialog1.setLocation(x, y);
		// death_info

		try {
			if (patientInfo.getDeathInfo() != null) {
				deathInfo = patientInfo.getDeathInfo();
				this.check_Deal.setSelected(true);
				this.dia_txt_Cause.setText(deathInfo.getCause());

				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
				String dateTime = ft.format(deathInfo.getDateOfDeath());
				timer = dateTime.substring(0, 10);
				this.dia_cob_date.setValue(timer);
				int time = Integer.parseInt(dateTime.substring(11, 13));
				this.dia_spi_Hour.setValue(time);
				time = Integer.parseInt(dateTime.substring(14, 16));
				this.dia_spi_Min.setValue(time);
			} else {
				this.btn_DealSave.setEnabled(true);
			}
		} catch (Exception e) {
			ErrorMessage
					.setData(
							"Patients",
							"Frm_PatientMod",
							"check_DealActionPerformed(java.awt.event.ActionEvent evt)",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
			System.out.println(e);
		}
		this.jDialog1.setVisible(true);
	}// GEN-LAST:event_check_DealActionPerformed

	private void txt_LastNameKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txt_LastNameKeyReleased
		this.btn_OK.setEnabled(isCanSave());
	}// GEN-LAST:event_txt_LastNameKeyReleased

	private void txt_FirstNameKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txt_FirstNameKeyReleased
		this.btn_OK.setEnabled(isCanSave());
	}// GEN-LAST:event_txt_FirstNameKeyReleased

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
		new diagnosis.Frm_DiagnosisAllergy(this, this.txt_No.getText(),
				this.txt_FirstName.getText() + " "
						+ this.txt_LastName.getText()).setVisible(true);
		this.setEnabled(false);

	}// GEN-LAST:event_jMenuItem1ActionPerformed

	private void mnit_LabHistoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_LabHistoryActionPerformed
		this.setEnabled(false);
		new Frm_LabHistory(this, this.txt_No.getText()).setVisible(true);

	}// GEN-LAST:event_mnit_LabHistoryActionPerformed

	private void mnit_XRayHistoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_XRayHistoryActionPerformed
		this.setEnabled(false);
		new Frm_RadiologyHistory(this, this.txt_No.getText()).setVisible(true);

	}// GEN-LAST:event_mnit_XRayHistoryActionPerformed

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
		new diagnosis.Frm_DiagnosisDiagnostic(this, this.txt_No.getText(),
				this.txt_FirstName.getText() + " "
						+ this.txt_LastName.getText()).setVisible(true);
		this.setEnabled(false);

	}// GEN-LAST:event_jMenuItem2ActionPerformed

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem3ActionPerformed
		this.setEnabled(false);
		new Frm_CashierHistory(this, txt_No.getText().trim()).setVisible(true);

	}// GEN-LAST:event_jMenuItem3ActionPerformed

	private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenu1ActionPerformed

	}// GEN-LAST:event_jMenu1ActionPerformed

	private void txt_Phone2KeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txt_Phone2KeyReleased
		this.btn_OK.setEnabled(isCanSave());
	}// GEN-LAST:event_txt_Phone2KeyReleased

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTabbedPane Pat_List;
	private javax.swing.JMenuBar bar_menu;
	private javax.swing.JButton btn_Cancel;
	private javax.swing.JButton btn_DealCancel;
	private javax.swing.JButton btn_DealSave;
	private javax.swing.JButton btn_Enroll;
	private javax.swing.JButton btn_OK;
	private javax.swing.JComboBox cbox_Religion;
	private javax.swing.JComboBox cbox_Trible;
	private javax.swing.JCheckBox check_Deal;
	private javax.swing.JComboBox cob_Bloodtype;
	private javax.swing.JComboBox cob_EmeGender;
	private javax.swing.JComboBox cob_EmeMaritalStatus;
	private javax.swing.JComboBox cob_Gender;
	public javax.swing.JComboBox cob_MaritalStatus;
	private javax.swing.JComboBox cob_Rh;
	private cc.johnwu.date.DateChooser dateChooser1;
	private cc.johnwu.date.DateComboBox dia_cob_date;
	private javax.swing.JSpinner dia_spi_Hour;
	private javax.swing.JSpinner dia_spi_Min;
	private javax.swing.JTextArea dia_txt_Cause;
	private cc.johnwu.finger.FingerPrintViewer fingerPrintViewer1;
	private javax.swing.JDialog jDialog1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JLabel lab_Country;
	private javax.swing.JLabel lab_EmeGender;
	private javax.swing.JLabel lab_EmeLanguage;
	private javax.swing.JLabel lab_EmeMaritalStatus;
	private javax.swing.JLabel lab_EmeOccupation;
	private javax.swing.JLabel lab_EmePlaceOfBirth;
	private javax.swing.JLabel lab_EmeReligion;
	private javax.swing.JLabel lab_EmeTribe;
	private javax.swing.JLabel lab_Language;
	private javax.swing.JLabel lab_MaritalStatus;
	private javax.swing.JLabel lab_NhisNo;
	private javax.swing.JLabel lab_NiaNo;
	private javax.swing.JLabel lab_Occupation;
	private javax.swing.JLabel lab_PlaceOfBirth;
	private javax.swing.JLabel lab_Ps;
	private javax.swing.JLabel lab_Religion;
	private javax.swing.JLabel lab_TitleAddress;
	private javax.swing.JLabel lab_TitleBirth;
	private javax.swing.JLabel lab_TitleBloodtype;
	private javax.swing.JLabel lab_TitleCPTown;
	private javax.swing.JLabel lab_TitleEmeAddress;
	private javax.swing.JLabel lab_TitleEmeCountry;
	private javax.swing.JLabel lab_TitleEmeFirstName;
	private javax.swing.JLabel lab_TitleEmePhone;
	private javax.swing.JLabel lab_TitleEmeRelation;
	private javax.swing.JLabel lab_TitleEmeState;
	private javax.swing.JLabel lab_TitleEmelastName;
	private javax.swing.JLabel lab_TitleFirstName;
	private javax.swing.JLabel lab_TitleGender;
	private javax.swing.JLabel lab_TitleHeight;
	private javax.swing.JLabel lab_TitleLastName;
	private javax.swing.JLabel lab_TitleNo;
	private javax.swing.JLabel lab_TitlePhone1;
	private javax.swing.JLabel lab_TitlePhone2;
	private javax.swing.JLabel lab_TitleState;
	private javax.swing.JLabel lab_TitleTown;
	private javax.swing.JLabel lab_TitleWeight;
	private javax.swing.JLabel lab_Tribe;
	private javax.swing.JLabel lab_date_of_death;
	private javax.swing.JMenuItem mnit_LabHistory;
	private javax.swing.JMenuItem mnit_XRayHistory;
	private javax.swing.JPanel pan_BtnGrp;
	private javax.swing.JPanel pan_Left;
	private javax.swing.JPanel pan_Noth;
	private javax.swing.JPanel pan_Right;
	private javax.swing.JTextArea txt_Address;
	private javax.swing.JFormattedTextField txt_CPTown;
	private javax.swing.JTextField txt_Country;
	private javax.swing.JFormattedTextField txt_EmeCountry;
	private javax.swing.JFormattedTextField txt_EmeFirstName;
	private javax.swing.JTextField txt_EmeLanguage;
	private javax.swing.JFormattedTextField txt_EmeLastName;
	private javax.swing.JTextField txt_EmeOccupation;
	private javax.swing.JFormattedTextField txt_EmePhone;
	private javax.swing.JTextField txt_EmePlaceOfBirth;
	private javax.swing.JFormattedTextField txt_EmeRelation;
	private javax.swing.JTextField txt_EmeReligion;
	private javax.swing.JFormattedTextField txt_EmeState;
	private javax.swing.JTextField txt_EmeTribe;
	private javax.swing.JFormattedTextField txt_FirstName;
	private javax.swing.JFormattedTextField txt_Height;
	private javax.swing.JTextField txt_Language;
	private javax.swing.JFormattedTextField txt_LastName;
	private javax.swing.JFormattedTextField txt_NhisNo;
	private javax.swing.JTextField txt_NiaNo;
	private javax.swing.JTextField txt_No;
	private javax.swing.JTextField txt_Occupation;
	private javax.swing.JFormattedTextField txt_Phone1;
	private javax.swing.JFormattedTextField txt_Phone2;
	private javax.swing.JTextField txt_PlaceOfBirth;
	private javax.swing.JTextArea txt_Ps;
	private javax.swing.JFormattedTextField txt_State;
	private javax.swing.JFormattedTextField txt_Town;
	private javax.swing.JFormattedTextField txt_Weight;
	private javax.swing.JTextField txt_empCellPhone;
	private javax.swing.JTextArea txta_EmeAddress;

	// End of variables declaration//GEN-END:variables

	@Override
	public void showImage(BufferedImage bufferedimage, String msg) {
		this.fingerPrintViewer1.showImage(bufferedimage);
		this.fingerPrintViewer1.setTitle(msg);
	}

	@Override
	public void onFingerDown() {
		this.btn_Enroll.setEnabled(isCanSave());
	}

	@Override
	public void onDateChanged() {
	}

	public void reSetEnable() {
		this.setEnabled(true);
	}

	public void getAllergy() {
	}

}
