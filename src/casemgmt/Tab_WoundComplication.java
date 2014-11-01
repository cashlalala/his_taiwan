package casemgmt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		this.guid = caseGuid;

		initComponents();
		init();
		btnSave.setEnabled(false);
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
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(btnSave,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addGap(10)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
								269, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSave).addContainerGap()));

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		chckbxIncreaseRedness = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_INCREASE_REDNESS"), btnSave);
		chckbxIncreaseRedness.setBounds(24, 7, 181, 23);
		panel.add(chckbxIncreaseRedness);

		chckbxPain = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_PAIN"), btnSave);
		chckbxPain.setBounds(207, 7, 187, 23);
		panel.add(chckbxPain);

		chckbxSwelling = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_SWELLING"), btnSave);
		chckbxSwelling.setBounds(24, 46, 181, 23);
		panel.add(chckbxSwelling);

		chckbxContinueBleeding = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_CONTINUE_BLEED"), btnSave);
		chckbxContinueBleeding.setBounds(207, 46, 187, 23);
		panel.add(chckbxContinueBleeding);

		chckbxDarkDry = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_DARK_DRY"), btnSave);
		chckbxDarkDry.setBounds(24, 86, 181, 23);
		panel.add(chckbxDarkDry);

		chckbxBiggerDeeper = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_BIGGER_DEEPER"), btnSave);
		chckbxBiggerDeeper.setBounds(207, 86, 187, 23);
		panel.add(chckbxBiggerDeeper);

		chckbxDrainage = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_DRAINAGE"), btnSave);
		chckbxDrainage.setBounds(24, 128, 181, 23);
		panel.add(chckbxDrainage);

		chckbxPus_Color = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_PUS_COLOR"), btnSave);
		chckbxPus_Color.setBounds(207, 128, 187, 23);
		panel.add(chckbxPus_Color);

		chckbxPus_Smell = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_PUS_SMELL"), btnSave);
		chckbxPus_Smell.setBounds(24, 174, 181, 23);
		panel.add(chckbxPus_Smell);

		chckbxFever = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_FEVER"), btnSave);
		chckbxFever.setBounds(207, 174, 187, 23);
		panel.add(chckbxFever);

		chckbxTenderLump = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_TENDER_LUMP"), btnSave);
		chckbxTenderLump.setBounds(24, 223, 181, 23);
		panel.add(chckbxTenderLump);

		chckbxNotHealing = new ChangeNotifyCheckBox(
				lang.getString("WOUND_CASE_NOT_HEALING"), btnSave);
		chckbxNotHealing.setBounds(207, 223, 187, 23);
		panel.add(chckbxNotHealing);

		setLayout(groupLayout);

		btnSave.setEnabled(false);
	}

	protected void onBtnSaveClicked(ActionEvent e) {
		try {
			save();
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

	@Override
	public boolean isSaveable() {
		// TODO Auto-generated method stub
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
