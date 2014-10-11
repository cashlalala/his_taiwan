package casemgmt;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import multilingual.Language;

public class Tab_HIVCase extends JPanel {

	private Frm_Case parent;

	public void setParent(Frm_Case parent) {
		this.parent = parent;
	}

	public Tab_HIVCase() {
		setLayout(null);

		JLabel lbl_Risk = new JLabel("Risk:");
		lbl_Risk.setBounds(10, 10, 60, 15);
		add(lbl_Risk);

		JPanel panel = new JPanel();
		panel.setBounds(10, 35, 280, 273);
		add(panel);
		panel.setLayout(null);

		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setBounds(6, 6, 97, 23);
		panel.add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("New check box");
		chckbxNewCheckBox_1.setBounds(6, 29, 97, 23);
		panel.add(chckbxNewCheckBox_1);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("New check box");
		chckbxNewCheckBox_2.setBounds(6, 54, 97, 23);
		panel.add(chckbxNewCheckBox_2);

		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("New check box");
		chckbxNewCheckBox_3.setBounds(6, 79, 97, 23);
		panel.add(chckbxNewCheckBox_3);

		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("New check box");
		chckbxNewCheckBox_4.setBounds(6, 104, 97, 23);
		panel.add(chckbxNewCheckBox_4);

		JCheckBox chckbxNewCheckBox_5 = new JCheckBox("New check box");
		chckbxNewCheckBox_5.setBounds(6, 129, 97, 23);
		panel.add(chckbxNewCheckBox_5);

		JCheckBox chckbxNewCheckBox_6 = new JCheckBox("New check box");
		chckbxNewCheckBox_6.setBounds(6, 154, 97, 23);
		panel.add(chckbxNewCheckBox_6);

		JCheckBox chckbxNewCheckBox_8 = new JCheckBox("New check box");
		chckbxNewCheckBox_8.setBounds(152, 6, 97, 23);
		panel.add(chckbxNewCheckBox_8);

		JCheckBox chckbxNewCheckBox_9 = new JCheckBox("New check box");
		chckbxNewCheckBox_9.setBounds(152, 29, 97, 23);
		panel.add(chckbxNewCheckBox_9);

		JCheckBox chckbxNewCheckBox_10 = new JCheckBox("New check box");
		chckbxNewCheckBox_10.setBounds(152, 54, 97, 23);
		panel.add(chckbxNewCheckBox_10);

		JCheckBox chckbxNewCheckBox_11 = new JCheckBox("New check box");
		chckbxNewCheckBox_11.setBounds(152, 79, 97, 23);
		panel.add(chckbxNewCheckBox_11);

		JCheckBox chckbxNewCheckBox_12 = new JCheckBox("New check box");
		chckbxNewCheckBox_12.setBounds(152, 104, 97, 23);
		panel.add(chckbxNewCheckBox_12);

		JCheckBox chckbxNewCheckBox_13 = new JCheckBox("New check box");
		chckbxNewCheckBox_13.setBounds(152, 129, 97, 23);
		panel.add(chckbxNewCheckBox_13);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(6, 183, 46, 15);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(62, 180, 187, 21);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lbl_HighBloodPressure = new JLabel("High Blood Pressure:");
		lbl_HighBloodPressure.setBounds(300, 10, 104, 15);
		add(lbl_HighBloodPressure);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(300, 35, 186, 165);
		add(panel_1);
		panel_1.setLayout(null);

		JCheckBox chckbxNewCheckBox_7 = new JCheckBox("New check box");
		chckbxNewCheckBox_7.setBounds(6, 6, 97, 23);
		panel_1.add(chckbxNewCheckBox_7);

		JCheckBox chckbxNewCheckBox_14 = new JCheckBox("New check box");
		chckbxNewCheckBox_14.setBounds(6, 31, 97, 23);
		panel_1.add(chckbxNewCheckBox_14);

		JCheckBox chckbxNewCheckBox_15 = new JCheckBox("New check box");
		chckbxNewCheckBox_15.setBounds(6, 56, 97, 23);
		panel_1.add(chckbxNewCheckBox_15);

		JCheckBox chckbxNewCheckBox_16 = new JCheckBox("New check box");
		chckbxNewCheckBox_16.setBounds(6, 81, 97, 23);
		panel_1.add(chckbxNewCheckBox_16);

		JCheckBox chckbxNewCheckBox_17 = new JCheckBox("New check box");
		chckbxNewCheckBox_17.setBounds(6, 106, 97, 23);
		panel_1.add(chckbxNewCheckBox_17);

		JCheckBox chckbxNewCheckBox_18 = new JCheckBox("New check box");
		chckbxNewCheckBox_18.setBounds(6, 131, 97, 23);
		panel_1.add(chckbxNewCheckBox_18);

		JLabel lbl_3 = new JLabel("Sexual  Behavior:");
		lbl_3.setBounds(300, 210, 83, 15);
		add(lbl_3);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(300, 235, 186, 73);
		add(panel_2);
		panel_2.setLayout(null);

		JCheckBox chckbxNewCheckBox_19 = new JCheckBox("New check box");
		chckbxNewCheckBox_19.setBounds(0, 0, 97, 23);
		panel_2.add(chckbxNewCheckBox_19);

		JCheckBox chckbxNewCheckBox_20 = new JCheckBox("New check box");
		chckbxNewCheckBox_20.setBounds(0, 25, 97, 23);
		panel_2.add(chckbxNewCheckBox_20);

		JCheckBox chckbxNewCheckBox_21 = new JCheckBox("New check box");
		chckbxNewCheckBox_21.setBounds(0, 50, 97, 23);
		panel_2.add(chckbxNewCheckBox_21);

		JLabel lbl_IDU = new JLabel("IDU:");
		lbl_IDU.setBounds(496, 10, 60, 15);
		add(lbl_IDU);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(496, 35, 258, 273);
		add(panel_3);
		panel_3.setLayout(null);

		textField_1 = new JTextField();
		textField_1.setBounds(100, 10, 96, 21);
		panel_3.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(100, 41, 96, 21);
		panel_3.add(textField_2);
		textField_2.setColumns(10);

		JCheckBox chckbxNewCheckBox_22 = new JCheckBox("New check box");
		chckbxNewCheckBox_22.setBounds(6, 68, 97, 23);
		panel_3.add(chckbxNewCheckBox_22);

		JCheckBox chckbxNewCheckBox_23 = new JCheckBox("New check box");
		chckbxNewCheckBox_23.setBounds(6, 93, 97, 23);
		panel_3.add(chckbxNewCheckBox_23);

		JCheckBox chckbxNewCheckBox_24 = new JCheckBox("New check box");
		chckbxNewCheckBox_24.setBounds(6, 118, 97, 23);
		panel_3.add(chckbxNewCheckBox_24);

		JCheckBox chckbxNewCheckBox_25 = new JCheckBox("New check box");
		chckbxNewCheckBox_25.setBounds(6, 143, 97, 23);
		panel_3.add(chckbxNewCheckBox_25);

		JCheckBox chckbxNewCheckBox_26 = new JCheckBox("New check box");
		chckbxNewCheckBox_26.setBounds(6, 168, 97, 23);
		panel_3.add(chckbxNewCheckBox_26);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(6, 198, 46, 15);
		panel_3.add(lblNewLabel_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(100, 195, 29, 21);
		panel_3.add(comboBox);

		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(6, 224, 46, 15);
		panel_3.add(lblNewLabel_2);

		textField_3 = new JTextField();
		textField_3.setBounds(100, 221, 96, 21);
		panel_3.add(textField_3);
		textField_3.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(6, 249, 46, 15);
		panel_3.add(lblNewLabel_3);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(100, 246, 29, 21);
		panel_3.add(comboBox_1);

		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(6, 13, 46, 15);
		panel_3.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(6, 44, 46, 15);
		panel_3.add(lblNewLabel_5);

		JLabel lbl_Vaccine = new JLabel("Vaccine:");
		lbl_Vaccine.setBounds(10, 318, 60, 15);
		add(lbl_Vaccine);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 332, 744, 118);
		add(scrollPane);

		JLabel lbl_VenerealDisease = new JLabel("Venereal Disease:");
		lbl_VenerealDisease.setBounds(10, 460, 93, 15);
		add(lbl_VenerealDisease);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 485, 744, 112);
		add(scrollPane_1);
	}

	private static final long serialVersionUID = 1L;

	private static final Language lang = Language.getInstance();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
}
