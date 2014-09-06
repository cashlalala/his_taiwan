package registration;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public class Frm_RegAndInpatient extends JFrame {

	private JPanel pan_WholeFrame;
	private JTextField txt_PatientSearch;
	private JTable tab_PatientList;
	private JTabbedPane pan_ClinicOrInpatient;
	private JTable tab_BedList;
	private JTable tab_ClinicList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frm_RegAndInpatient frame = new Frm_RegAndInpatient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frm_RegAndInpatient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 705);
		pan_WholeFrame = new JPanel();
		pan_WholeFrame.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pan_WholeFrame);
		pan_WholeFrame.setLayout(null);

		JPanel pan_PatientInfo = new JPanel();
		pan_PatientInfo.setBounds(5, 5, 239, 274);
		pan_WholeFrame.add(pan_PatientInfo);
		pan_PatientInfo.setLayout(null);
		
		JLabel lbl_Patient_No = new JLabel("New label");
		lbl_Patient_No.setBounds(0, 0, 234, 15);
		pan_PatientInfo.add(lbl_Patient_No);
		
		JLabel lbl_NHIS_No = new JLabel("New label");
		lbl_NHIS_No.setBounds(0, 15, 234, 15);
		pan_PatientInfo.add(lbl_NHIS_No);
		
		JLabel lbl_NIA_No = new JLabel("New label");
		lbl_NIA_No.setBounds(0, 30, 234, 15);
		pan_PatientInfo.add(lbl_NIA_No);
		
		JLabel lbl_FirstName = new JLabel("New label");
		lbl_FirstName.setBounds(0, 45, 46, 15);
		pan_PatientInfo.add(lbl_FirstName);
		
		JLabel lbl_Last_Name = new JLabel("New label");
		lbl_Last_Name.setBounds(0, 60, 46, 15);
		pan_PatientInfo.add(lbl_Last_Name);
		
		JLabel lbl_Birthday = new JLabel("New label");
		lbl_Birthday.setBounds(0, 75, 46, 15);
		pan_PatientInfo.add(lbl_Birthday);
		
		JLabel lbl_Age = new JLabel("New label");
		lbl_Age.setBounds(0, 90, 46, 15);
		pan_PatientInfo.add(lbl_Age);
		
		JLabel lbl_Gender = new JLabel("New label");
		lbl_Gender.setBounds(0, 105, 46, 15);
		pan_PatientInfo.add(lbl_Gender);
		
		JLabel lbl_BloodType = new JLabel("New label");
		lbl_BloodType.setBounds(0, 120, 46, 15);
		pan_PatientInfo.add(lbl_BloodType);
		
		JLabel lbl_Height = new JLabel("New label");
		lbl_Height.setBounds(0, 135, 46, 15);
		pan_PatientInfo.add(lbl_Height);
		
		JLabel lbl_Weight = new JLabel("New label");
		lbl_Weight.setBounds(0, 150, 46, 15);
		pan_PatientInfo.add(lbl_Weight);
		
		JPanel pan_Code = new JPanel();
		pan_Code.setBounds(0, 169, 135, 106);
		pan_PatientInfo.add(pan_Code);
		
		JButton btn_AddPatient = new JButton("New button");
		btn_AddPatient.setBounds(150, 250, 87, 23);
		pan_PatientInfo.add(btn_AddPatient);

		txt_PatientSearch = new JTextField();
		txt_PatientSearch.setBounds(249, 5, 303, 24);
		pan_WholeFrame.add(txt_PatientSearch);
		txt_PatientSearch.setColumns(10);

		JButton btn_PatientSearch = new JButton("New button");
		btn_PatientSearch.setBounds(557, 5, 92, 24);
		pan_WholeFrame.add(btn_PatientSearch);

		JScrollPane scrollPane_Patient = new JScrollPane();
		scrollPane_Patient.setBounds(249, 34, 400, 245);
		pan_WholeFrame.add(scrollPane_Patient);

		
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
		
		tab_PatientList = new JTable();
		scrollPane_Patient.setViewportView(tab_PatientList);
		tab_PatientList.setModel(new DefaultTableModel(matrix, header));



		pan_ClinicOrInpatient = new JTabbedPane(JTabbedPane.TOP);
		pan_ClinicOrInpatient.setBounds(5, 280, 644, 381);
		pan_WholeFrame.add(pan_ClinicOrInpatient);

		JPanel pan_tabClinicInfo = new JPanel();
		pan_ClinicOrInpatient.addTab("Clinic", pan_tabClinicInfo);
		GridBagLayout gbl_pan_tabClinicInfo = new GridBagLayout();
		gbl_pan_tabClinicInfo.columnWidths = new int[] { 231, 388, 0 };
		gbl_pan_tabClinicInfo.rowHeights = new int[] { 369, 0 };
		gbl_pan_tabClinicInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_tabClinicInfo.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pan_tabClinicInfo.setLayout(gbl_pan_tabClinicInfo);

		JPanel pan_ClinicInfo = new JPanel();
		GridBagConstraints gbc_pan_ClinicInfo = new GridBagConstraints();
		gbc_pan_ClinicInfo.weighty = 1.0;
		gbc_pan_ClinicInfo.weightx = 0.45;
		gbc_pan_ClinicInfo.anchor = GridBagConstraints.WEST;
		gbc_pan_ClinicInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_ClinicInfo.insets = new Insets(0, 0, 0, 5);
		gbc_pan_ClinicInfo.gridx = 0;
		gbc_pan_ClinicInfo.gridy = 0;
		pan_tabClinicInfo.add(pan_ClinicInfo, gbc_pan_ClinicInfo);

		JScrollPane scrollPane_Clinic = new JScrollPane();
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
		
		JPanel pan_tabInpatientInfo = new JPanel();
		pan_ClinicOrInpatient.addTab("Inpatient", pan_tabInpatientInfo);
		GridBagLayout gbl_pan_tabInpatientInfo = new GridBagLayout();
		gbl_pan_tabInpatientInfo.columnWidths = new int[] { 231, 388, 0 };
		gbl_pan_tabInpatientInfo.rowHeights = new int[] { 369, 0 };
		gbl_pan_tabInpatientInfo.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pan_tabInpatientInfo.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		pan_tabInpatientInfo.setLayout(gbl_pan_tabInpatientInfo);

		JPanel pan_InpatientInfo = new JPanel();
		GridBagConstraints gbc_pan_InpatientInfo = new GridBagConstraints();
		gbc_pan_InpatientInfo.weighty = 1.0;
		gbc_pan_InpatientInfo.weightx = 0.45;
		gbc_pan_InpatientInfo.anchor = GridBagConstraints.SOUTHWEST;
		gbc_pan_InpatientInfo.fill = GridBagConstraints.BOTH;
		gbc_pan_InpatientInfo.insets = new Insets(0, 0, 0, 5);
		gbc_pan_InpatientInfo.gridx = 0;
		gbc_pan_InpatientInfo.gridy = 0;
		pan_tabInpatientInfo.add(pan_InpatientInfo, gbc_pan_InpatientInfo);

		JScrollPane scrollPane_Bed = new JScrollPane();
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
	}
}
