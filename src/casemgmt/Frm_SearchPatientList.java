package casemgmt;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.Frm_Main;
import multilingual.Language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

public class Frm_SearchPatientList extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6294929510817420045L;
	private JTextField textDiaCode;
	private JTextField textPresCode;
	private JTable table;
	private JPanel panel;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JPanel panel_2;
	private JButton btnEnter;
	private JButton btnClose;
	private JButton btnSearch;
	private JLabel lblPrescriptionCode;
	private JLabel lblDiagnosisCode;
	private String icdVersion = "ICD-10";
	private static Language LANG = Language.getInstance();
	private static Logger logger = LogManager
			.getLogger(Frm_SearchPatientList.class.getName());

	public Frm_SearchPatientList() throws HeadlessException {

		String sql = "select ICDVersion from setting";
		ResultSet rs = null;
		try {
			rs = DBC.executeQuery(sql);
			if (rs.next()) {
				icdVersion = rs.getString("ICDVersion");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			logger.info("{}{} get icd version : {}", UserInfo.getUserID(),
					UserInfo.getUserName(), icdVersion);
		}

		initComponents();
		initLanguage();
	}

	private void initLanguage() {
		setTitle(LANG.getString("CASE_SEARCH_PATIENT_LIST"));
		lblDiagnosisCode.setText(LANG.getString("CASE_DIAGNOSIS_CODE"));
		lblPrescriptionCode.setText(LANG.getString("CASE_PRESCRIPTION_CODE"));
		btnClose.setText(LANG.getString("CLOSE"));
		btnEnter.setText(LANG.getString("ENTER"));
		btnSearch.setText(LANG.getString("SEARCH"));
	}

	protected void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setExtendedState(Frm_Case.MAXIMIZED_BOTH); // 最大化
		setLocationRelativeTo(this);

		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle("Case Management Bring-in Patient List");

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		panel_1 = new JPanel();

		scrollPane = new JScrollPane();

		scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														panel_1,
														GroupLayout.DEFAULT_SIZE,
														686, Short.MAX_VALUE)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		scrollPane,
																		GroupLayout.DEFAULT_SIZE,
																		537,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		scrollPane_1,
																		GroupLayout.PREFERRED_SIZE,
																		139,
																		GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 84,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane_1,
												GroupLayout.DEFAULT_SIZE, 355,
												Short.MAX_VALUE)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 355,
												Short.MAX_VALUE)).addGap(12)));

		panel_2 = new JPanel();
		scrollPane_1.setViewportView(panel_2);

		btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnEnterClicked(e);
			}
		});

		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnCloseClicked(e);
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								gl_panel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																btnClose,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																117,
																Short.MAX_VALUE)
														.addComponent(
																btnEnter,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																117,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_2.createSequentialGroup().addGap(5)
						.addComponent(btnEnter)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnClose)
						.addContainerGap(296, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(25);
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(new DefaultTableModel(null, colNames));

		scrollPane.setViewportView(table);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 100, 100, 100, 100, 1 };
		gbl_panel_1.rowHeights = new int[] { 29, 29, 29, 3 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblDiagnosisCode = new JLabel("Diagnosis Code");
		GridBagConstraints gbc_lblDiagnosisCode = new GridBagConstraints();
		gbc_lblDiagnosisCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDiagnosisCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblDiagnosisCode.gridx = 0;
		gbc_lblDiagnosisCode.gridy = 0;
		panel_1.add(lblDiagnosisCode, gbc_lblDiagnosisCode);

		textDiaCode = new JTextField();
		GridBagConstraints gbc_textDiaCode = new GridBagConstraints();
		gbc_textDiaCode.insets = new Insets(0, 0, 5, 5);
		gbc_textDiaCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDiaCode.gridx = 1;
		gbc_textDiaCode.gridy = 0;
		panel_1.add(textDiaCode, gbc_textDiaCode);
		textDiaCode.setColumns(10);

		lblPrescriptionCode = new JLabel("Prescription Code");
		GridBagConstraints gbc_lblPrescriptionCode = new GridBagConstraints();
		gbc_lblPrescriptionCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrescriptionCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrescriptionCode.gridx = 0;
		gbc_lblPrescriptionCode.gridy = 2;
		panel_1.add(lblPrescriptionCode, gbc_lblPrescriptionCode);

		textPresCode = new JTextField();
		GridBagConstraints gbc_textPresCode = new GridBagConstraints();
		gbc_textPresCode.insets = new Insets(0, 0, 5, 5);
		gbc_textPresCode.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPresCode.gridx = 1;
		gbc_textPresCode.gridy = 2;
		panel_1.add(textPresCode, gbc_textPresCode);
		textPresCode.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnSearchClicked(e);
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 3;
		gbc_btnSearch.gridy = 3;
		panel_1.add(btnSearch, gbc_btnSearch);
		panel.setLayout(gl_panel);
	}

	protected void onBtnEnterClicked(ActionEvent e) {
		int idx = table.getSelectedRow();
		String pNo = (String) table.getValueAt(idx, 0);
		String sql = String.format("select guid from registration_info r "
				+ "where r.p_no = '%s' order by r.reg_time desc", pNo);
		ResultSet rs = null;
		String regGuid = null;
		try {
			rs = DBC.executeQuery(sql);
			if (rs.next()) {
				regGuid = rs.getString("guid");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		new Dlg_CaseMgmtType(this, pNo, regGuid, false, "dia").setVisible(true);

	}

	protected void onBtnCloseClicked(ActionEvent e) {
		new Frm_Main().setVisible(true);
		this.dispose();
	}

	protected static String[] colNames = new String[] {
			LANG.getString("COL_PATIENTNO"), LANG.getString("COL_NAME"),
			LANG.getString("BIRTH"), LANG.getString("COL_GENDER"),
			LANG.getString("COL_BLOODTYPE") };

	protected void onBtnSearchClicked(ActionEvent e) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				String diaCode = textDiaCode.getText().trim().toUpperCase();
				String presCode = textPresCode.getText().trim().toUpperCase();

				String sql = "";
				String patientInfo = String
						.format("p.p_no as '%s', concat(p.firstname, ' ',p.lastname) as '%s', "
								+ "p.birth as '%s', p.gender as '%s', p.bloodtype as '%s' ",
								colNames);
				if (!diaCode.isEmpty() && !presCode.isEmpty()) {
					String patientInfoJoin = "p.p_no, p.firstname, p.lastname, p.birth, p.gender, p.bloodtype ";
					String interSectCol = String
							.format("a.p_no as '%s', concat(a.firstname, ' ',a.lastname) as '%s', "
									+ "a.birth as '%s', a.gender as '%s', a.bloodtype as '%s' ",
									colNames);
					sql = String
							.format("select %s from (select %s from patients_info p "
									+ "where p.p_no in (select p_no from registration_info r "
									+ "where r.guid in (select reg_guid from diagnostic d "
									+ "where d.dia_code like '%s%%-%s' group by reg_guid) "
									+ "order by r.reg_time desc) "
									+ "group by p.p_no) a "
									+ "join "
									+ "(select %s from patients_info p "
									+ "where p.p_no in (select p_no from registration_info r "
									+ "where r.guid in (select reg_guid from prescription pre "
									+ "where pre.code in (select code from prescription_code "
									+ "where ICDVersion = '%s') "
									+ "and pre.code like '%s%%' group by reg_guid) "
									+ "order by r.reg_time desc) "
									+ "group by p.p_no) b "
									+ "on a.p_no = b.p_no ", interSectCol,
									patientInfoJoin, diaCode,
									icdVersion.split("-")[1], patientInfoJoin,
									icdVersion, presCode);
				} else if (!diaCode.isEmpty()) {
					sql = String
							.format("select %s from patients_info p "
									+ "where p.p_no in (select p_no from registration_info r "
									+ "where r.guid in (select reg_guid from diagnostic d "
									+ "where d.dia_code like '%s%%-%s' group by reg_guid) "
									+ "order by r.reg_time desc) "
									+ "group by p.p_no ", patientInfo, diaCode,
									icdVersion.split("-")[1]);
				} else if (!presCode.isEmpty()) {
					sql = String
							.format("select %s from patients_info p "
									+ "where p.p_no in (select p_no from registration_info r "
									+ "where r.guid in (select reg_guid from prescription pre "
									+ "where pre.code in (select code from prescription_code "
									+ "where ICDVersion = '%s') "
									+ "and pre.code like '%s%%' group by reg_guid) "
									+ "order by r.reg_time desc) "
									+ "group by p.p_no ", patientInfo,
									icdVersion, presCode);
				}
				ResultSet rs = null;
				if (!sql.isEmpty()) {
					try {
						rs = DBC.executeQuery(sql);
						table.setModel(HISModel.getModel(rs));

					} catch (SQLException e1) {
						e1.printStackTrace();
					} finally {
						try {
							DBC.closeConnection(rs);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}).start();

	}

	public Frm_SearchPatientList(GraphicsConfiguration gc) {
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public Frm_SearchPatientList(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public Frm_SearchPatientList(String title, GraphicsConfiguration gc) {
		super(title, gc);
		// TODO Auto-generated constructor stub
	}
}
