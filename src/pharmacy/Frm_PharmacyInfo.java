package pharmacy;

import casemgmt.Frm_Case;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import errormessage.StoredErrorMessage;
import multilingual.Language;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author bee
 */
public class Frm_PharmacyInfo extends javax.swing.JFrame {
	private String m_Dep, m_Docter, m_No, m_Name, m_Birth, m_Gender, m_Blood,
			m_Guid, m_Ps;
	private Frm_Pharmacy Frm_p = null;
	private Frm_PharmacyInquire Frm_pi = null;
	private String[] medicine_guid = null;

	private Language paragraph = Language.getInstance();

	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	public Frm_PharmacyInfo(Frm_PharmacyInquire frm, String dep, String doctor,
			String no, String name, String birth, String gender, String blood,
			String ps, String guid) {
		initComponents();
		initLanguage();
		this.setLocationRelativeTo(this);
		Frm_pi = frm;
		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable
			@Override
			public void windowClosing(WindowEvent windowevent) {
				Frm_pi.setEnabled(true);
			}
		});
		System.out.print(dep + doctor + no + name + birth + gender + blood + ps
				+ guid);
		this.m_Dep = dep;
		this.m_Docter = doctor;
		this.m_No = no;
		this.m_Name = name;
		this.m_Birth = birth;
		this.m_Gender = gender;
		this.m_Blood = blood;
		this.m_Ps = ps;
		this.m_Guid = guid;
		setTab_Medicines();
	}

	public Frm_PharmacyInfo(Frm_Pharmacy frm, String dep, String doctor,
			String no, String name, String birth, String gender, String blood,
			String ps, String guid) {
		initComponents();
		initLanguage();
		this.setLocationRelativeTo(this);
		Frm_p = frm;
		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable
			@Override
			public void windowClosing(WindowEvent windowevent) {
				Frm_p.setEnabled(true);
			}
		});
		this.m_Dep = dep;
		this.m_Docter = doctor;
		this.m_No = no;
		this.m_Name = name;
		this.m_Birth = birth;
		this.m_Gender = gender;
		this.m_Blood = blood;
		this.m_Ps = ps;
		this.m_Guid = guid;
		setTab_Medicines();
	}

	public void initFram() {

	}

	private void initLanguage() {
		this.lab_TitleNo.setText(m_No);
		this.lab_TitleNo.setText(paragraph.getString("TITLENO"));
		this.lab_TitleName.setText(paragraph.getString("TITLENAME"));
		this.lab_TitleSex.setText(paragraph.getString("TITLESEX"));
		this.lab_TitleBloodtype.setText(paragraph.getString("TITLEBLOODTYPE"));
		// this.lab_TitleAge.setText(paragraph.getLanguage(line, "TITLEAGE"));
		this.lab_Doctor.setText(paragraph.getString("DOCTOR"));
		// this.lab_Department.setText(paragraph.getLanguage(line,
		// "DEPARTMENT"));
		this.lab_TitlePs.setText(paragraph.getString("TITLEPS"));
		this.btn_Close.setText(paragraph.getString("CLOSE"));
		this.setTitle(paragraph.getString("TITLEMEDICINEINFORMATION"));

	}

	// 藥品
	public void setTab_Medicines() {
		ResultSet rsMedicines = null;
		try {
			String sqlMedicines = "SELECT medicine_stock.reg_guid, "
					+ "medicines.code, medicines.item, "
					+ "medicine_stock.dosage, medicines.unit, "
					+ "medicine_stock.usage, medicine_stock.way, "
					+ "medicine_stock.repeat_number, medicine_stock.quantity, "
					+ "medicine_stock.urgent, medicine_stock.powder, "
					+ "medicine_stock.ps, medicines.unit_price "
					+ "FROM medicines, medicine_stock "
					+ "WHERE medicine_stock.reg_guid = '" + m_Guid + "' "
					+ "AND medicines.code = medicine_stock.m_code "
					+ "AND medicine_stock.get_medicine_time IS NULL ";
			System.out.println(sqlMedicines);
			rsMedicines = DBC.executeQuery(sqlMedicines);
			rsMedicines.last();
			String[] medicineHeader = { "",
					paragraph.getString("COL_MEDICINE_CODE"),
					paragraph.getString("COL_MEDICINE_NAME"),
					paragraph.getString("COL_MEDICINE_DOSAGE"),
					paragraph.getString("COL_MEDICINE_UNIT"),
					paragraph.getString("COL_MEDICINE_USAGE"),
					paragraph.getString("COL_MEDICINE_WAY"),
					paragraph.getString("COL_MEDICINE_REPEAT"),
					paragraph.getString("COL_MEDICINE_QUANTITY"),
					paragraph.getString("COL_MEDICINE_URGENT"),
					paragraph.getString("COL_MEDICINE_POWDER"),
					paragraph.getString("COL_MEDICINE_PS"),
					paragraph.getString("COL_MEDICINE_UNIT_PRICE"),
					paragraph.getString("COL_MEDICINE_TOTAL_PRICE") };
			if (rsMedicines.getRow() != 0) {
				ResultSetMetaData rsMetaData = rsMedicines.getMetaData();
				Integer rowCount = rsMedicines.getRow();
				Object[][] Matrix = new Object[rowCount][rsMetaData
						.getColumnCount() + 2];
				int i;
				int j;
				rsMedicines.beforeFirst();
				this.medicine_guid = new String[rowCount];
				for (i = 0; i < rowCount; i++) {
					rsMedicines.next();
					Matrix[i][0] = false;
					this.medicine_guid[i] = rsMedicines.getString(1);
					for (j = 2; j < rsMetaData.getColumnCount() + 1; j++) {
						Matrix[i][j-1] = rsMedicines.getString(j);
					}
					Matrix[i][13] = Integer.parseInt((String) Matrix[i][8])
							* Integer.parseInt((String) Matrix[i][12]);
				}
				this.tab_Medicines.setModel(new DefaultTableModel(Matrix,
						medicineHeader) {
					@Override
					public Class<?> getColumnClass(int columnIndex) {
						if (columnIndex == 0) {
							return Boolean.class;
						} else {
							return String.class;
						}
					}

					@Override
					public boolean isCellEditable(int row, int column) {
						if (column == 0) {
							return true;
						} else {
							return false;
						}
					}
				});
			} else {
				this.tab_Medicines
						.setModel(new DefaultTableModel(new Object[][] { {
								null, null, null, null, null, null, null, null,
								null, null, null, null, null, null } },
								medicineHeader));
			}
		} catch (SQLException e) {
			ErrorMessage.setData(
					"Pharmacy",
					"Frm_PharmacyInfo",
					"setTab_Medicines()",
					e.toString().substring(e.toString().lastIndexOf(".") + 1,
							e.toString().length()));
			Logger.getLogger(Frm_PharmacyInfo.class.getName()).log(
					Level.SEVERE, null, e);
		} finally {

			try {
				DBC.closeConnection(rsMedicines);
			} catch (SQLException e) {
				ErrorMessage.setData(
						"Pharmacy",
						"Frm_PharmacyInfo",
						"setTab_Medicines() - DBC.closeConnection",
						e.toString().substring(
								e.toString().lastIndexOf(".") + 1,
								e.toString().length()));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		span_How = new javax.swing.JScrollPane();
		tab_Medicines = new javax.swing.JTable();
		pan_Top = new javax.swing.JPanel();
		lab_TitleNo = new javax.swing.JLabel();
		lab_TitleName = new javax.swing.JLabel();
		lab_TitlePs = new javax.swing.JLabel();
		lab_TitleSex = new javax.swing.JLabel();
		lab_TitleAge = new javax.swing.JLabel();
		lab_TitleBloodtype = new javax.swing.JLabel();
		txt_No = new javax.swing.JTextField();
		txt_Age = new javax.swing.JTextField();
		txt_Bloodtype = new javax.swing.JTextField();
		txt_Name = new javax.swing.JTextField();
		txt_Sex = new javax.swing.JTextField();
		txt_Ps = new javax.swing.JTextField();
		lab_Doctor = new javax.swing.JLabel();
		lab_Department = new javax.swing.JLabel();
		txt_Dep = new javax.swing.JTextField();
		txt_Doctor = new javax.swing.JTextField();
		btn_Close = new javax.swing.JButton();
		btn_Education = new javax.swing.JButton();
		btn_Save = new javax.swing.JButton();
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Medicine Information");
		setAlwaysOnTop(true);

		tab_Medicines.setAutoCreateRowSorter(true);
		tab_Medicines.setModel(new DefaultTableModel(new Object[][] { { false,
				null, null, null, null, null, null, null, null, null, null,
				null, null, null } }, new String[] { "",
				paragraph.getString("COL_MEDICINE_CODE"),
				paragraph.getString("COL_MEDICINE_NAME"),
				paragraph.getString("COL_MEDICINE_DOSAGE"),
				paragraph.getString("COL_MEDICINE_UNIT"),
				paragraph.getString("COL_MEDICINE_USAGE"),
				paragraph.getString("COL_MEDICINE_WAY"),
				paragraph.getString("COL_MEDICINE_REPEAT"),
				paragraph.getString("COL_MEDICINE_QUANTITY"),
				paragraph.getString("COL_MEDICINE_URGENT"),
				paragraph.getString("COL_MEDICINE_POWDER"),
				paragraph.getString("COL_MEDICINE_PS"),
				paragraph.getString("COL_MEDICINE_UNIT_PRICE"),
				paragraph.getString("COL_MEDICINE_TOTAL_PRICE") }) {
			boolean[] canEdit = new boolean[] { true, false, false, false,
					false, false, false, false, false, false, false, false,
					false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tab_Medicines.setRowHeight(25);
		span_How.setViewportView(tab_Medicines);

		pan_Top.setBackground(new java.awt.Color(228, 228, 228));
		pan_Top.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 18));

		lab_TitleNo.setText("ID");

		lab_TitleName.setText("Name");

		lab_TitlePs.setText("PS");

		lab_TitleSex.setText("Gender");

		lab_TitleAge.setText("Birthday");

		lab_TitleBloodtype.setText("Blood Type");

		txt_No.setEditable(false);

		txt_Age.setEditable(false);

		txt_Bloodtype.setEditable(false);

		txt_Name.setEditable(false);

		txt_Sex.setEditable(false);

		txt_Ps.setEditable(false);

		lab_Doctor.setText("Doctor");

		lab_Department.setText("Depart/Clinic");

		txt_Dep.setEditable(false);

		txt_Doctor.setEditable(false);

		javax.swing.GroupLayout pan_TopLayout = new javax.swing.GroupLayout(
				pan_Top);
		pan_Top.setLayout(pan_TopLayout);
		pan_TopLayout
				.setHorizontalGroup(pan_TopLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_TopLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																pan_TopLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																		.addComponent(
																				lab_TitleBloodtype,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				66,
																				Short.MAX_VALUE)
																		.addComponent(
																				lab_TitleSex,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				lab_TitleName,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				lab_TitleNo,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addComponent(
																lab_TitleAge,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																66,
																Short.MAX_VALUE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txt_No,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE)
														.addComponent(
																txt_Name,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE)
														.addComponent(
																txt_Dep,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE)
														.addComponent(
																txt_Doctor,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE)
														.addComponent(
																txt_Ps,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE))
										.addGap(54, 54, 54)
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pan_TopLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.TRAILING,
																				false)
																		.addComponent(
																				lab_Doctor,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				lab_Department,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addComponent(
																lab_TitlePs,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																43,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																txt_Age,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE)
														.addComponent(
																txt_Bloodtype,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE)
														.addComponent(
																txt_Sex,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																245,
																Short.MAX_VALUE))
										.addGap(117, 117, 117)));
		pan_TopLayout
				.setVerticalGroup(pan_TopLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_TopLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pan_TopLayout
																		.createSequentialGroup()
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lab_Doctor)
																						.addComponent(
																								txt_Bloodtype,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGap(7,
																				7,
																				7)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lab_Department)
																						.addComponent(
																								txt_Sex,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lab_TitlePs)
																						.addComponent(
																								txt_Age,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																pan_TopLayout
																		.createSequentialGroup()
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lab_TitleNo)
																						.addComponent(
																								txt_Dep,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lab_TitleName)
																						.addComponent(
																								txt_Doctor,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lab_TitleSex)
																						.addComponent(
																								txt_Name,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								lab_TitleBloodtype)
																						.addComponent(
																								txt_Ps,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								txt_No,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lab_TitleAge))))
										.addContainerGap(13, Short.MAX_VALUE)));

		pan_TopLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { txt_Age, txt_Bloodtype, txt_Dep,
						txt_Doctor, txt_Name, txt_No, txt_Ps, txt_Sex });

		btn_Close.setText(paragraph.getString("CLOSE"));
		btn_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CloseActionPerformed(evt);
			}
		});

		btn_Education.setText(paragraph.getString("MEDICINEEDUCATION"));
		btn_Education.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_EducationActionPerformed(evt);
			}
		});

		btn_Save.setText(paragraph.getString("SAVE"));
		btn_Save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.print("**************************************\n");
				btn_SaveActionPerformed(evt);
			}
		});

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
														span_How,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														796, Short.MAX_VALUE)
												.addComponent(
														pan_Top,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		btn_Save)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		btn_Education)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		btn_Close,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		100,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(span_How,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										362, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(pan_Top,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btn_Save)
												.addComponent(btn_Close)
												.addComponent(btn_Education))
								.addGap(22, 22, 22)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {
		if (Frm_p == null) {
			Frm_pi.reSetEnable();
		} else {
			Frm_p.setEnabled(true);
		}
		this.dispose();
	}

	private void btn_EducationActionPerformed(java.awt.event.ActionEvent evt) {
		this.setAlwaysOnTop(false);
		new Frm_Case(txt_Dep.getText(), m_Guid, true, "medicine")
				.setVisible(true);
	}

	private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		int i;
		Integer amountReceivable = 0;
		for (i = 0; i < this.tab_Medicines.getRowCount(); i++) {
			if ((Boolean) this.tab_Medicines.getValueAt(i, 0) == true) {
				String sql = "UPDATE medicine_stock SET medicine_stock.get_medicine_time=now() WHERE medicine_stock.guid='"
						+ this.medicine_guid[i] + "'";
				amountReceivable = amountReceivable
						+ (Integer) this.tab_Medicines.getValueAt(i, 13);
			}
		}
		System.out.print(amountReceivable+"\n");
		String message=paragraph.getString("AMOUNTRECEIVABLE") + String.valueOf(amountReceivable);
		JOptionPane.showMessageDialog(null,"aaaa");
		return;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btn_Close;
	private javax.swing.JButton btn_Education;
	private javax.swing.JButton btn_Save;
	private javax.swing.JLabel lab_Department;
	private javax.swing.JLabel lab_Doctor;
	private javax.swing.JLabel lab_TitleAge;
	private javax.swing.JLabel lab_TitleBloodtype;
	private javax.swing.JLabel lab_TitleName;
	private javax.swing.JLabel lab_TitleNo;
	private javax.swing.JLabel lab_TitlePs;
	private javax.swing.JLabel lab_TitleSex;
	private javax.swing.JPanel pan_Top;
	private javax.swing.JScrollPane span_How;
	private javax.swing.JTable tab_Medicines;
	private javax.swing.JTextField txt_Age;
	private javax.swing.JTextField txt_Bloodtype;
	private javax.swing.JTextField txt_Dep;
	private javax.swing.JTextField txt_Doctor;
	private javax.swing.JTextField txt_Name;
	private javax.swing.JTextField txt_No;
	private javax.swing.JTextField txt_Ps;
	private javax.swing.JTextField txt_Sex;
	// End of variables declaration//GEN-END:variables

}
