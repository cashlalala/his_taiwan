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
import javax.swing.table.DefaultTableModel;

import multilingual.Language;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class Tab_FootCase extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Language lang = Language.getInstance();

	private JTextField textField;
	private JTable table;
	private JButton btnSearch;
	private JButton btnSave;
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
			lang.getString("FOOT_CASE_NO"), lang.getString("FOOT_CTIME"),
			lang.getString("FOOT_DOC"), lang.getString("FOOT_EDU"),
			lang.getString("FOOT_1"), lang.getString("FOOT_2"),
			lang.getString("FOOT_3"), lang.getString("FOOT_4"),
			lang.getString("FOOT_5"), lang.getString("FOOT_6"),
			lang.getString("FOOT_7"), lang.getString("FOOT_8"),
			lang.getString("FOOT_9"), lang.getString("FOOT_10"), };
	private JScrollPane scrollPane_1;

	private String caseGuid;

	/**
	 * @wbp.parser.constructor
	 */
	public Tab_FootCase(String caseGuid) {
		this.caseGuid = caseGuid;
		initModel();
		initComponent();
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

		textField = new JTextField();
		textField.setColumns(10);

		btnSearch = new JButton(lang.getString("FOOT_SEARCH"));

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
																		textField,
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
										GroupLayout.PREFERRED_SIZE, 210,
										GroupLayout.PREFERRED_SIZE)
								.addGap(93)
								.addComponent(panelFoot,
										GroupLayout.PREFERRED_SIZE, 292,
										GroupLayout.PREFERRED_SIZE).addGap(21)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														panelFootChk,
														GroupLayout.DEFAULT_SIZE,
														635, Short.MAX_VALUE)
												.addComponent(
														panelFoot,
														GroupLayout.DEFAULT_SIZE,
														635, Short.MAX_VALUE)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						textField,
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
																		554,
																		Short.MAX_VALUE)
																.addGap(35)))
								.addContainerGap()));

		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(diabeteRec);
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
						FormFactory.DEFAULT_ROWSPEC, }));

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

		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		panelFootChk.add(chckbxNewCheckBox, "8, 46");
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}

	public Tab_FootCase(LayoutManager layout) {
		super(layout);
	}

	public Tab_FootCase(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public Tab_FootCase(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}
}
