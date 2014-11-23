package casemgmt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import multilingual.Language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;

import javax.swing.JTable;

public class Tab_WoundComplication extends JPanel implements ISaveable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4306332931634843693L;

	private String pNo;
	private String caseGuid;
	@SuppressWarnings("unused")
	private String regGuid;
	private String guid;
	private static Language lang = Language.getInstance();
	private static Logger logger = LogManager
			.getLogger(Tab_WoundComplication.class.getName());

	public Tab_WoundComplication(String pNo, String caseGuid, String regGuid) {
		this.pNo = pNo;
		this.caseGuid = caseGuid;
		this.regGuid = regGuid;
		this.guid = UUID.randomUUID().toString();

		initComponents();
		new Thread(new Runnable() {

			@Override
			public void run() {
				init();
			}
		}).start();
		btnSave.setEnabled(false);
	}

	private void refreshHist() {
		String sql = String
				.format("select wc.createdatetime, wc.increase_redness,"
						+ "wc.pain, wc.swelling, wc.continuous_bleeding, "
						+ "wc.dark_dry, wc.bigger_Deeper, wc.drainage,"
						+ "wc.pus_color, wc.pus_smell, wc.fever, wc.tender_lump,"
						+ "wc.not_healing " + "from wound_complication wc "
						+ "where wc.p_no = '%s' "
						+ "order by wc.createdatetime desc", pNo);

		ResultSet rs = null;
		try {
			rs = DBC.executeQuery(sql);
			table.setModel(HISModel.getModel(rs));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		String sql = String.format("select * from wound_complication wc "
				+ "where wc.case_guid = '%s'", caseGuid);

		ResultSet rs = null;
		try {
			rs = DBC.executeQuery(sql);
			if (rs.first()) {
				chckbxPain.setSelected(rs.getBoolean("pain"));
				chckbxBiggerDeeper.setSelected(rs.getBoolean("bigger_Deeper"));
				chckbxContinueBleeding.setSelected(rs
						.getBoolean("continuous_bleeding"));
				chckbxDarkDry.setSelected(rs.getBoolean("dark_dry"));
				chckbxSwelling.setSelected(rs.getBoolean("swelling"));
				chckbxDrainage.setSelected(rs.getBoolean("drainage"));
				chckbxPus_Color.setSelected(rs.getBoolean("pus_color"));
				chckbxPus_Smell.setSelected(rs.getBoolean("pus_smell"));
				chckbxFever.setSelected(rs.getBoolean("fever"));
				chckbxTenderLump.setSelected(rs.getBoolean("tender_lump"));
				chckbxNotHealing.setSelected(rs.getBoolean("not_healing"));
			}
			refreshHist();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void initComponents() {
		JScrollPane scrollPane = new JScrollPane();

		btnSave = new JButton(lang.getString("SAVE"));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnSaveClicked(e);
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(btnSave,
										GroupLayout.PREFERRED_SIZE, 80,
										GroupLayout.PREFERRED_SIZE).addGap(10))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 450,
						Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								410, Short.MAX_VALUE).addGap(18)
						.addComponent(btnSave).addContainerGap()));

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);

		chckbxIncreaseRedness = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_INCREASE_REDNESS"), btnSave);

		chckbxPain = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_PAIN"), btnSave);

		chckbxSwelling = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_SWELLING"), btnSave);

		chckbxContinueBleeding = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_CONTINUE_BLEED"), btnSave);

		chckbxDarkDry = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_DARK_DRY"), btnSave);

		chckbxBiggerDeeper = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_BIGGER_DEEPER"), btnSave);

		chckbxDrainage = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_DRAINAGE"), btnSave);

		chckbxPus_Color = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_PUS_COLOR"), btnSave);

		chckbxPus_Smell = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_PUS_SMELL"), btnSave);

		chckbxFever = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_FEVER"), btnSave);

		chckbxTenderLump = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_TENDER_LUMP"), btnSave);

		chckbxNotHealing = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_NOT_HEALING"), btnSave);

		scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGap(24)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		chckbxIncreaseRedness,
																		GroupLayout.PREFERRED_SIZE,
																		181,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(2)
																.addComponent(
																		chckbxPain,
																		GroupLayout.PREFERRED_SIZE,
																		187,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		chckbxSwelling,
																		GroupLayout.PREFERRED_SIZE,
																		181,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(2)
																.addComponent(
																		chckbxContinueBleeding,
																		GroupLayout.PREFERRED_SIZE,
																		187,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		chckbxDarkDry,
																		GroupLayout.PREFERRED_SIZE,
																		181,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(2)
																.addComponent(
																		chckbxBiggerDeeper,
																		GroupLayout.PREFERRED_SIZE,
																		187,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		chckbxDrainage,
																		GroupLayout.PREFERRED_SIZE,
																		181,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(2)
																.addComponent(
																		chckbxPus_Color,
																		GroupLayout.PREFERRED_SIZE,
																		187,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		chckbxPus_Smell,
																		GroupLayout.PREFERRED_SIZE,
																		181,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(2)
																.addComponent(
																		chckbxFever,
																		GroupLayout.PREFERRED_SIZE,
																		187,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		chckbxTenderLump,
																		GroupLayout.PREFERRED_SIZE,
																		181,
																		GroupLayout.PREFERRED_SIZE)
																.addGap(2)
																.addComponent(
																		chckbxNotHealing,
																		GroupLayout.PREFERRED_SIZE,
																		187,
																		GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPane_1,
										GroupLayout.DEFAULT_SIZE, 398,
										Short.MAX_VALUE).addGap(49)));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGap(7)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						chckbxIncreaseRedness,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						chckbxPain,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(16)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						chckbxSwelling,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						chckbxContinueBleeding,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(17)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						chckbxDarkDry,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						chckbxBiggerDeeper,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(19)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						chckbxDrainage,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						chckbxPus_Color,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(23)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						chckbxPus_Smell,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						chckbxFever,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))
																.addGap(26)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.LEADING)
																				.addComponent(
																						chckbxTenderLump,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						chckbxNotHealing,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														gl_panel.createSequentialGroup()
																.addContainerGap()
																.addComponent(
																		scrollPane_1,
																		GroupLayout.DEFAULT_SIZE,
																		375,
																		Short.MAX_VALUE)))
								.addContainerGap()));

		table = new JTable();
		scrollPane_1.setViewportView(table);
		panel.setLayout(gl_panel);

		setLayout(groupLayout);

		btnSave.setEnabled(false);
	}

	protected void onBtnSaveClicked(ActionEvent e) {
		try {
			save();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					refreshHist();
				}
			}).start();
			JOptionPane.showMessageDialog(null, "Save Complete");

			btnSave.setEnabled(false);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Save Failed: " + ex.getMessage());
			btnSave.setEnabled(true);
		}
	}

	private ChangeNotifyCheckBox chckbxIncreaseRedness;
	private ChangeNotifyCheckBox chckbxPain;
	private ChangeNotifyCheckBox chckbxSwelling;
	private ChangeNotifyCheckBox chckbxContinueBleeding;
	private ChangeNotifyCheckBox chckbxDarkDry;
	private ChangeNotifyCheckBox chckbxBiggerDeeper;
	private ChangeNotifyCheckBox chckbxDrainage;
	private ChangeNotifyCheckBox chckbxPus_Color;
	private ChangeNotifyCheckBox chckbxPus_Smell;
	private ChangeNotifyCheckBox chckbxFever;
	private ChangeNotifyCheckBox chckbxTenderLump;
	private ChangeNotifyCheckBox chckbxNotHealing;
	private JButton btnSave;
	private JScrollPane scrollPane_1;
	private JTable table;

	@Override
	public boolean isSaveable() {
		return this.btnSave.isEnabled();
	}

	@Override
	public void save() throws Exception {
		Connection conn = DBC.getConnectionExternel();
		try {
			conn.setAutoCommit(false);
			save(conn);
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	@Override
	public void save(Connection conn) throws Exception {
		PreparedStatement ps = null;

		try {
			String sql = String
					.format("insert into wound_complication ("
							+ "guid,case_guid,p_no,increase_redness,pain,swelling,"
							+ "continuous_bleeding,dark_dry,bigger_Deeper,drainage,"
							+ "pus_color,pus_smell,fever,tender_lump,not_healing,"
							+ "createdatetime,s_no)"
							+ "values ('%s','%s','%s','%s','%s','%s',"
							+ "'%s','%s','%s','%s',"
							+ "'%s','%s','%s','%s','%s',"
							+ "now(),'%s') ON DUPLICATE KEY update "
							+ "increase_redness = '%s',pain = '%s',swelling = '%s',"
							+ "continuous_bleeding = '%s',dark_dry = '%s',"
							+ "bigger_Deeper = '%s',drainage = '%s',"
							+ "pus_color = '%s',pus_smell = '%s',fever = '%s',"
							+ "tender_lump = '%s',not_healing = '%s'",
							guid,
							caseGuid,
							pNo,
							boolean2StringConvertor(chckbxIncreaseRedness
									.isSelected()),
							boolean2StringConvertor(chckbxPain.isSelected()),
							boolean2StringConvertor(chckbxSwelling.isSelected()),
							boolean2StringConvertor(chckbxContinueBleeding
									.isSelected()),
							boolean2StringConvertor(chckbxDarkDry.isSelected()),
							boolean2StringConvertor(chckbxBiggerDeeper
									.isSelected()),
							boolean2StringConvertor(chckbxDrainage.isSelected()),
							boolean2StringConvertor(chckbxPus_Color
									.isSelected()),
							boolean2StringConvertor(chckbxPus_Smell
									.isSelected()),
							boolean2StringConvertor(chckbxFever.isSelected()),
							boolean2StringConvertor(chckbxTenderLump
									.isSelected()),
							boolean2StringConvertor(chckbxNotHealing
									.isSelected()),
							UserInfo.getUserNO(),
							boolean2StringConvertor(chckbxIncreaseRedness
									.isSelected()),
							boolean2StringConvertor(chckbxPain.isSelected()),
							boolean2StringConvertor(chckbxSwelling.isSelected()),
							boolean2StringConvertor(chckbxContinueBleeding
									.isSelected()),
							boolean2StringConvertor(chckbxDarkDry.isSelected()),
							boolean2StringConvertor(chckbxBiggerDeeper
									.isSelected()),
							boolean2StringConvertor(chckbxDrainage.isSelected()),
							boolean2StringConvertor(chckbxPus_Color
									.isSelected()),
							boolean2StringConvertor(chckbxPus_Smell
									.isSelected()),
							boolean2StringConvertor(chckbxFever.isSelected()),
							boolean2StringConvertor(chckbxTenderLump
									.isSelected()),
							boolean2StringConvertor(chckbxNotHealing
									.isSelected()));
			logger.debug("[{}][{}] {}", UserInfo.getUserID(),
					UserInfo.getUserName(), sql);

			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			guid = UUID.randomUUID().toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (ps != null)
				ps.close();
		}

		btnSave.setEnabled(false);

	}

	private String boolean2StringConvertor(boolean value) {
		return value ? "1" : "0";
	}

	@SuppressWarnings("unused")
	private boolean boolean2StringConvertor(String value) {
		return value.equals("1");
	}
}
