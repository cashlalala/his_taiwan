package casemgmt;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cc.johnwu.sql.DBC;
import multilingual.Language;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Tab_HIVHistory extends JPanel {
	private Frm_Case parent;
	private static final long serialVersionUID = 1L;
	private static final Language lang = Language.getInstance();
	private JTable table;
	private JScrollPane scrollPane;

	public Tab_HIVHistory(String pNo) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1, 0};
		gridBagLayout.rowHeights = new int[]{1, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);

		
		String sql = "Select * from HIV_assessment " + "WHERE p_no = '" + pNo
				+ "' ORDER BY createdatetime DESC";
		ResultSet rs = null;
		try {
			rs = DBC.executeQuery(sql);
			rs.last();
			if (rs.getRow() == 0) {
				table.setModel(new DefaultTableModel(
						new String[][] { { "No Information." } },
						new String[] { "Message" }));
			} else {
				int rowCount = rs.getRow();
				Object[][] matrix = new Object[rowCount][32];
				String[] header = new String[] { lang.getString("PHHD"),
						lang.getString("PHCD"), lang.getString("PHHC"),
						lang.getString("PHDM"), lang.getString("PHTYPEA"),
						lang.getString("PHTYPEB"), lang.getString("PHTYPEC"),
						lang.getString("PHMALIG"), lang.getString("HD"),
						lang.getString("PHNS"), lang.getString("PHSMOKE"),
						lang.getString("PHALCOHOLISM"), lang.getString("PHOW"),
						lang.getString("PHOTHER"), lang.getString("IHHC"),
						lang.getString("IHDM"), lang.getString("IHEBP"),
						lang.getString("FMHHC"), lang.getString("FMHDM"),
						lang.getString("FMHEBP"), lang.getString("WSVD"),
						lang.getString("10SP"), lang.getString("HSIWS"),
						lang.getString("IDUAGE"),
						lang.getString("IDUDURATION"), lang.getString("WTD"),
						lang.getString("HUID"), lang.getString("SN"),
						lang.getString("SW"), lang.getString("METHADONE"),
						lang.getString("STARTMETHADONE"),
						lang.getString("STATICSMETHADONE"), };
				rs.beforeFirst();
				int i = 0;
				while (rs.next()) {
					matrix[i][0] = (String) rs.getString("Hypertension");
					matrix[i][1] = (String) rs.getString("BrainVessel");
					matrix[i][2] = (String) rs.getString("Hyperlipidemia");
					matrix[i][3] = (String) rs.getString("Diabetes");
					matrix[i][4] = (String) rs.getString("HepatitisA");
					matrix[i][5] = (String) rs.getString("HepatitisB");
					matrix[i][6] = (String) rs.getString("HepatitisC");
					matrix[i][7] = (String) rs.getString("Cancer");
					matrix[i][8] = (String) rs.getString("HeartDisease");
					matrix[i][9] = (String) rs.getString("NephroticSyndrome");
					matrix[i][10] = (String) rs.getString("Smoking");
					matrix[i][11] = (String) rs.getString("Drinking");
					matrix[i][12] = (String) rs.getString("OverWeight");
					if (rs.getString("OtherDiseaseHistory") != null) {
						matrix[i][13] = (String) rs
								.getString("OtherDiseaseHistory");
					} else {
						matrix[i][13] = "";
					}
					matrix[i][14] = (String) rs.getString("SelfHPL");
					matrix[i][15] = (String) rs.getString("SelfDiabetes");
					matrix[i][16] = (String) rs.getString("SelfHTN");
					matrix[i][17] = (String) rs.getString("DirectHPL");
					matrix[i][18] = (String) rs.getString("DirectDiabetes");
					matrix[i][19] = (String) rs.getString("DirectHTN");
					matrix[i][20] = (String) rs.getString("HasSTD");
					matrix[i][21] = (String) rs.getString("Has10ST");
					matrix[i][22] = (String) rs.getString("HasOTS");
					if (rs.getString("IDUAge") != null) {
						matrix[i][23] = (String) rs.getString("IDUAge");
					} else {
						matrix[i][23] = "";
					}
					if (rs.getString("IDUDuration") != null) {
						matrix[i][24] = (String) rs.getString("IDUDuration");
					} else {
						matrix[i][24] = "";
					}
					matrix[i][25] = (String) rs.getString("UsedDrugs");
					matrix[i][26] = (String) rs.getString("UsedInjectionDrugs");
					matrix[i][27] = (String) rs.getString("SharingNeedle");
					matrix[i][28] = (String) rs.getString("SharingWater");
					matrix[i][29] = (String) rs.getString("UsedMethadone");
					if (rs.getString("FirstMethadone") != null) {
						matrix[i][30] = (String) rs.getString("FirstMethadone");
					} else {
						matrix[i][30] = "";
					}
					matrix[i][31] = (String) rs.getString("MethadoneStatus");
					i = i + 1;
				}
				table.setModel(new DefaultTableModel(matrix, header));
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
}
