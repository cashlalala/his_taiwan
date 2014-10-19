package casemgmt;

import java.awt.LayoutManager;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import multilingual.Language;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import common.TabTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Tab_FootCase extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Language lang = Language.getInstance();

	private JTextField textSearch;
	private JTable table;
	private JButton btnSearch;
	public JButton btnSave;
	private JPanel panelFoot;

	private JPanel panelFootChk;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	private JComboBox comboBox_4;
	private JComboBox comboBox_5;
	private JComboBox comboBox_6;
	private JComboBox comboBox_7;
	private JComboBox comboBox_8;
	private JComboBox comboBox_9;
	private JLabel lblEducated;

	private DefaultComboBoxModel footPos1;
	private DefaultComboBoxModel footPos2;
	private DefaultComboBoxModel footPos3;
	private DefaultComboBoxModel footPos4;
	private DefaultComboBoxModel footPos5;
	private DefaultComboBoxModel footPos6;
	private DefaultComboBoxModel footPos7;
	private DefaultComboBoxModel footPos8;
	private DefaultComboBoxModel footPos9;
	private DefaultComboBoxModel footPos10;

	private DefaultTableModel diabeteRec;

	private static final String[] colName = new String[] {
			lang.getString("FOOT_CASE_RECORD_NO"), lang.getString("FOOT_EDU"),
			lang.getString("FOOT_CTIME"), lang.getString("FOOT_DOC"),
			lang.getString("FOOT_1"), lang.getString("FOOT_2"),
			lang.getString("FOOT_3"), lang.getString("FOOT_4"),
			lang.getString("FOOT_5"), lang.getString("FOOT_6"),
			lang.getString("FOOT_7"), lang.getString("FOOT_8"),
			lang.getString("FOOT_9"), lang.getString("FOOT_10"), };
	private JScrollPane scrollPane_1;

	private String caseGuid;

	private String regGuid;

	private String pNo;

	private String guid;

	private JCheckBox chckbxNewCheckBox;

	private String finished;
	private JLabel lblRecNo;

	private JLabel lblRecNoValue;

	private String searchSqlTemplate;

	/**
	 * @param regGuid
	 * @param pNo
	 * @param finishState
	 * @wbp.parser.constructor
	 */
	public Tab_FootCase(String caseGuid, String pNo, String regGuid,
			boolean finishState) {
		this.caseGuid = caseGuid;
		this.regGuid = regGuid;
		this.pNo = pNo;
		this.finished = finishState ? "C" : "N";
		this.guid = UUID.randomUUID().toString();
		initModel();
		initComponent();

		new Thread() {

			@Override
			public void run() {
				showList();
			}

		}.start();
	}

	protected void showList() {
		String sqlCase = String
				.format("select * from case_manage where p_no = '%s' and status = '%s'",
						pNo, finished);
		List<String> varList = new ArrayList<String>(Arrays.asList(colName));

		String searchKeyWord = textSearch.getText().trim();
		String sqlDia = "select d.guid as '%s', d.case_guid, d.educated as '%s', d.createdatetime as '%s', "
				+ "(select concat(s.firstname, s.lastname) from staff_info s where s.s_no = d.s_no) as '%s', "
				+ "d.foot1 as '%s', d.foot2 as '%s', "
				+ "d.foot3 as '%s', d.foot4 as '%s', d.foot5 as '%s', d.foot6  as '%s', "
				+ "d.foot7 as '%s', d.foot8 as '%s', d.foot9 as '%s', d.foot10 as '%s' "
				+ " from diabetes_record d where case_guid in (%s) "
				+ ((searchKeyWord.isEmpty()) ? "" : "and guid like '"
						+ searchKeyWord + "%%' ")
				+ "order by d.createdatetime desc";
		ResultSet rs = null;
		ResultSet rsRec = null;
		try {
			String caseGuids = "";
			rs = DBC.executeQuery(sqlCase);
			while (rs.next()) {
				caseGuids = String.format("'%s',", rs.getString("guid"));
			}
			varList.add(caseGuids.substring(0, caseGuids.length() - 1));
			sqlDia = String.format(sqlDia, varList.toArray());
			rsRec = DBC.executeQuery(sqlDia);
			diabeteRec = HISModel.getModel(rsRec);
			table.setModel(diabeteRec);
			TabTools.setHideColumn(table, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					DBC.closeConnection(rs);
				if (rsRec != null)
					DBC.closeConnection(rsRec);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void initModel() {
		footPos1 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos2 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos3 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos4 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos5 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos6 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos7 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos8 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos9 = new DefaultComboBoxModel(new String[] { "-", "+" });
		footPos10 = new DefaultComboBoxModel(new String[] { "-", "+" });

		diabeteRec = new DefaultTableModel(new Object[][] { { "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", } }, colName);
	}

	private void initComponent() {
		JScrollPane scrollPane = new JScrollPane();

		btnSave = new JButton(lang.getString("FOOT_SAVE"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnSaveFootCaseClicked(e);
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																btnSave,
																GroupLayout.PREFERRED_SIZE,
																133,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																scrollPane,
																GroupLayout.DEFAULT_SIZE,
																988,
																Short.MAX_VALUE))
										.addGap(17)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								636, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnSave, GroupLayout.PREFERRED_SIZE, 31,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);

		textSearch = new JTextField();
		textSearch.setColumns(10);

		btnSearch = new JButton(lang.getString("FOOT_SEARCH"));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnSearchClicked(e);
			}
		});

		panelFoot = new ImagePanel();

		panelFootChk = new JPanel();

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
														scrollPane_1,
														GroupLayout.DEFAULT_SIZE,
														752, Short.MAX_VALUE)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		textSearch,
																		GroupLayout.DEFAULT_SIZE,
																		661,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		btnSearch,
																		GroupLayout.PREFERRED_SIZE,
																		81,
																		GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelFootChk,
										GroupLayout.PREFERRED_SIZE, 293,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(panelFoot,
										GroupLayout.PREFERRED_SIZE, 292,
										GroupLayout.PREFERRED_SIZE).addGap(21)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														panelFootChk,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														635, Short.MAX_VALUE)
												.addComponent(
														panelFoot,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														635, Short.MAX_VALUE)
												.addGroup(
														Alignment.LEADING,
														gl_panel.createSequentialGroup()
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						textSearch,
																						GroupLayout.PREFERRED_SIZE,
																						35,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						btnSearch,
																						GroupLayout.PREFERRED_SIZE,
																						35,
																						GroupLayout.PREFERRED_SIZE))
																.addPreferredGap(
																		ComponentPlacement.UNRELATED)
																.addComponent(
																		scrollPane_1,
																		GroupLayout.DEFAULT_SIZE,
																		555,
																		Short.MAX_VALUE)
																.addGap(35)))
								.addContainerGap()));

		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(diabeteRec);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						onItemSelected(event);
					}
				});
		gl_panel.setAutoCreateGaps(true);
		gl_panel.setAutoCreateContainerGaps(true);
		panelFootChk
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		lblRecNo = new JLabel("Record No.:");
		panelFootChk.add(lblRecNo, "2, 2, left, default");

		lblRecNoValue = new JLabel("New");
		panelFootChk.add(lblRecNoValue, "2, 4, 9, 1, default, bottom");

		lblNewLabel = new JLabel("1");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel, "2, 6, 3, 1");

		comboBox = new JComboBox();
		panelFootChk.add(comboBox, "6, 6, 5, 1, fill, default");
		comboBox.setModel(footPos1);

		lblNewLabel_1 = new JLabel("2");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_1, "2, 10, 3, 1");

		comboBox_1 = new JComboBox();
		panelFootChk.add(comboBox_1, "6, 10, 5, 1, fill, default");
		comboBox_1.setModel(footPos2);

		lblNewLabel_2 = new JLabel("3");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_2, "2, 14, 3, 1");

		comboBox_2 = new JComboBox();
		panelFootChk.add(comboBox_2, "6, 14, 5, 1, fill, default");
		comboBox_2.setModel(footPos3);

		lblNewLabel_3 = new JLabel("4");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_3, "2, 18, 3, 1");

		comboBox_3 = new JComboBox();
		panelFootChk.add(comboBox_3, "6, 18, 5, 1, fill, default");
		comboBox_3.setModel(footPos4);

		lblNewLabel_4 = new JLabel("5");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_4, "2, 22, 3, 1");

		comboBox_4 = new JComboBox();
		panelFootChk.add(comboBox_4, "6, 22, 5, 1, fill, default");
		comboBox_4.setModel(footPos5);

		lblNewLabel_5 = new JLabel("6");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_5, "2, 26, 3, 1");

		comboBox_5 = new JComboBox();
		panelFootChk.add(comboBox_5, "6, 26, 5, 1, fill, default");
		comboBox_5.setModel(footPos6);

		lblNewLabel_6 = new JLabel("7");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_6, "2, 30, 3, 1");

		comboBox_6 = new JComboBox();
		panelFootChk.add(comboBox_6, "6, 30, 5, 1, fill, default");
		comboBox_6.setModel(footPos7);

		lblNewLabel_7 = new JLabel("8");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_7, "2, 34, 3, 1");

		comboBox_7 = new JComboBox();
		panelFootChk.add(comboBox_7, "6, 34, 5, 1, fill, default");
		comboBox_7.setModel(footPos8);

		lblNewLabel_8 = new JLabel("9");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_8, "2, 38, 3, 1");

		comboBox_8 = new JComboBox();
		panelFootChk.add(comboBox_8, "6, 38, 5, 1, fill, default");
		comboBox_8.setModel(footPos9);

		lblNewLabel_9 = new JLabel("10");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panelFootChk.add(lblNewLabel_9, "2, 42, 3, 1");

		comboBox_9 = new JComboBox();
		panelFootChk.add(comboBox_9, "6, 42, 5, 1");
		comboBox_9.setModel(footPos10);

		lblEducated = new JLabel("Medical Education");
		panelFootChk.add(lblEducated, "2, 46");

		chckbxNewCheckBox = new JCheckBox("");
		panelFootChk.add(chckbxNewCheckBox, "8, 46");

		JButton btnReset = new JButton(lang.getString("DIABETES_RESET"));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnResetClicked(e);
			}
		});
		panelFootChk.add(btnReset, "8, 48");
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}

	protected void onBtnSearchClicked(ActionEvent e) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				showList();
			}
		}).run();
	}

	protected void onBtnResetClicked(ActionEvent e) {
		footPos1.setSelectedItem("-");
		footPos2.setSelectedItem("-");
		footPos3.setSelectedItem("-");
		footPos4.setSelectedItem("-");
		footPos5.setSelectedItem("-");
		footPos6.setSelectedItem("-");
		footPos7.setSelectedItem("-");
		footPos8.setSelectedItem("-");
		footPos9.setSelectedItem("-");
		footPos10.setSelectedItem("-");

		chckbxNewCheckBox.setSelected(false);

		guid = UUID.randomUUID().toString();

		lblRecNoValue.setText(lang.getString("DIABETES_NEW_RECORD"));
		table.getSelectionModel().clearSelection();
	}

	protected void onItemSelected(ListSelectionEvent event) {
		int selectedIdx = table.getSelectedRow();
		if (selectedIdx == -1)
			return;
		try {
			guid = (String) table.getValueAt(selectedIdx, 0);
			this.lblRecNoValue.setText(guid);

			Boolean isEdued = (((String) table.getValueAt(selectedIdx, 2))
					.equalsIgnoreCase("1") ? true : false);
			this.chckbxNewCheckBox.setSelected(isEdued);

			String foot1 = (String) table.getValueAt(selectedIdx, 5);
			comboBox.setSelectedItem(foot1);
			String foot2 = (String) table.getValueAt(selectedIdx, 6);
			comboBox_1.setSelectedItem(foot2);
			String foot3 = (String) table.getValueAt(selectedIdx, 7);
			comboBox_2.setSelectedItem(foot3);
			String foot4 = (String) table.getValueAt(selectedIdx, 8);
			comboBox_3.setSelectedItem(foot4);
			String foot5 = (String) table.getValueAt(selectedIdx, 9);
			comboBox_4.setSelectedItem(foot5);
			String foot6 = (String) table.getValueAt(selectedIdx, 10);
			comboBox_5.setSelectedItem(foot6);
			String foot7 = (String) table.getValueAt(selectedIdx, 11);
			comboBox_6.setSelectedItem(foot7);
			String foot8 = (String) table.getValueAt(selectedIdx, 12);
			comboBox_7.setSelectedItem(foot8);
			String foot9 = (String) table.getValueAt(selectedIdx, 13);
			comboBox_8.setSelectedItem(foot9);
			String foot10 = (String) table.getValueAt(selectedIdx, 14);
			comboBox_9.setSelectedItem(foot10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void onBtnSaveFootCaseClicked(ActionEvent e) {

		String sql = String
				.format("insert into diabetes_record (guid, case_guid, educated, createdatetime, s_no, "
						+ "foot1, foot2, foot3, foot4, foot5, "
						+ "foot6, foot7, foot8, foot9, foot10) "
						+ "values('%s','%s','%s',NOW(),'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s') "
						+ "ON DUPLICATE KEY update "
						+ "foot1 = '%s', foot2 = '%s', foot3 = '%s', foot4 = '%s', foot5 = '%s', "
						+ "foot6 = '%s', foot7 = '%s', foot8 = '%s', foot9 = '%s', foot10 = '%s', "
						+ "educated = '%s', createdatetime = NOW(), s_no = '%s'",
						guid, caseGuid, (chckbxNewCheckBox.isSelected() ? "1"
								: "0"), UserInfo.getUserNO(), comboBox
								.getSelectedItem().toString(), comboBox_1
								.getSelectedItem().toString(), comboBox_2
								.getSelectedItem().toString(), comboBox_3
								.getSelectedItem().toString(), comboBox_4
								.getSelectedItem().toString(), comboBox_5
								.getSelectedItem().toString(), comboBox_6
								.getSelectedItem().toString(), comboBox_7
								.getSelectedItem().toString(), comboBox_8
								.getSelectedItem().toString(), comboBox_9
								.getSelectedItem().toString(), comboBox
								.getSelectedItem().toString(), comboBox_1
								.getSelectedItem().toString(), comboBox_2
								.getSelectedItem().toString(), comboBox_3
								.getSelectedItem().toString(), comboBox_4
								.getSelectedItem().toString(), comboBox_5
								.getSelectedItem().toString(), comboBox_6
								.getSelectedItem().toString(), comboBox_7
								.getSelectedItem().toString(), comboBox_8
								.getSelectedItem().toString(), comboBox_9
								.getSelectedItem().toString(),
						(chckbxNewCheckBox.isSelected() ? "1" : "0"), UserInfo
								.getUserNO());

		try {
			DBC.executeUpdate(sql);
			table.getSelectionModel().clearSelection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			new Thread(new Runnable() {

				@Override
				public void run() {
					showList();
				}
			}).start();
		}
	}
}
