package casemgmt;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import multilingual.Language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.his.util.CustomLogger;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

public class Dlg_CaseMgmtType extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3305427335165174594L;

	private static final Language lang = Language.getInstance();

	private String pNo;
	private String regGuid;
	private Map<String, String> mapCaseGuid;
	private boolean isFinished;
	private String from;

	private static String HIVCASE = "HIV";
	private static String DIABETESCASE = "DIA";
	private static String WOUNDCASE = "WOD";

	private static Logger logger = LogManager.getLogger(Dlg_CaseMgmtType.class
			.getName());

	public Dlg_CaseMgmtType(Frame owner, String pNo, String regGuid,
			boolean isFinished, String from) {
		super(owner);
		this.pNo = pNo;
		this.regGuid = regGuid;
		this.isFinished = isFinished;
		this.from = from;
		mapCaseGuid = new HashMap<String, String>();
		setTitle(lang.getString("CASE_TYPE"));
		initComponents();
		initLanguage();
		init();
	}

	private void init() {
		String sql = String
				.format("select * from case_manage c where c.p_no = '%s' and  status = 'N'",
						this.pNo);
		ResultSet rsCase = null;
		try {
			rsCase = DBC.executeQuery(sql);
			int i = 0;
			while (rsCase.next()) {
				String type = rsCase.getString("type");
				String guid = rsCase.getString("guid");
				CustomLogger.debug(logger,
						"Case [{}] : type [{}] : status [{}] ", i, type,
						rsCase.getString("status"));
				if (type.equalsIgnoreCase("H"))
					mapCaseGuid.put(HIVCASE, guid);
				else if (type.equalsIgnoreCase("D"))
					mapCaseGuid.put(DIABETESCASE, guid);
				else if (type.equalsIgnoreCase("W"))
					mapCaseGuid.put(WOUNDCASE, guid);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsCase != null)
					DBC.closeConnection(rsCase);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			btnHIVCase.setEnabled(!mapCaseGuid.containsKey(HIVCASE)
					|| mapCaseGuid.get(HIVCASE).isEmpty());
			btnDiabetesCase.setEnabled(!mapCaseGuid.containsKey(DIABETESCASE)
					|| mapCaseGuid.get(DIABETESCASE).isEmpty());
		}
	}

	private void initLanguage() {
		btnHIVCase.setText(lang.getString("HIV_CASE"));
		btnDiabetesCase.setText(lang.getString("DIABETES_CASE"));
		btnWoundCase.setText(lang.getString("WOUND_CASE"));
	}

	JButton btnHIVCase;
	JButton btnDiabetesCase;
	JButton btnWoundCase;

	private void initComponents() {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 1, 0, 0));

		btnHIVCase = new JButton("HIV");
		btnHIVCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onHIVClicked(e);
			}
		});
		panel.add(btnHIVCase);

		btnDiabetesCase = new JButton("Diabetes");
		btnDiabetesCase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDiabetesClicked(e);
			}
		});
		panel.add(btnDiabetesCase);

		btnWoundCase = new JButton("Wound");
		btnWoundCase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onWoundClicked(e);
			}

		});
		panel.add(btnWoundCase);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 193, 275);
		setLocationRelativeTo(null);
	}

	public void onHIVClicked(ActionEvent e) {
		if (!mapCaseGuid.containsKey(HIVCASE)) {
			mapCaseGuid.put(HIVCASE, UUID.randomUUID().toString());
			String sqlInsert = String
					.format("insert into case_manage (guid, reg_guid, p_no, type, status, finish_time, modify_count, s_no, isdiagnosis) "
							+ "values ('%s', '%s', '%s', 'H', 'N', NOW(), 1, %s, 1)",
							mapCaseGuid.get(HIVCASE), regGuid, pNo,
							UserInfo.getUserNO());
			try {
				DBC.executeUpdate(sqlInsert);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		new Frm_Case(mapCaseGuid.get(HIVCASE), "H", pNo, regGuid, isFinished,
				from).setVisible(true);
		this.dispose();
	}

	public void onDiabetesClicked(ActionEvent e) {
		if (!mapCaseGuid.containsKey(DIABETESCASE)) {
			mapCaseGuid.put(DIABETESCASE, UUID.randomUUID().toString());
			String sqlInsert = String
					.format("insert into case_manage (guid, reg_guid, p_no, type, status, finish_time, modify_count, s_no, isdiagnosis) "
							+ "values ('%s', '%s', '%s', 'D', 'N', NOW(), 1, %s, 1)",
							mapCaseGuid.get(DIABETESCASE), regGuid, pNo,
							UserInfo.getUserNO());
			try {
				DBC.executeUpdate(sqlInsert);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		new Frm_Case(mapCaseGuid.get(DIABETESCASE), "D", pNo, regGuid,
				isFinished, from).setVisible(true);
		this.dispose();
	}

	public void onWoundClicked(ActionEvent e) {
		String woundId = UUID.randomUUID().toString();
		String sqlInsert = String
				.format("insert into case_manage (guid, reg_guid, p_no, type, status, finish_time, modify_count, s_no, isdiagnosis) "
						+ "values ('%s', '%s', '%s', 'W', 'N', NOW(), 1, %s, 1)",
						woundId, regGuid, pNo, UserInfo.getUserNO());
		try {
			DBC.executeUpdate(sqlInsert);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		new Frm_Case(woundId, "W", pNo, regGuid, isFinished, from)
				.setVisible(true);
		this.dispose();
	}

}
