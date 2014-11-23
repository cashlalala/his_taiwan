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

public class Tab_WoundHistory extends JPanel {
	private Frm_Case parent;
	private static final long serialVersionUID = 1L;
	private static final Language lang = Language.getInstance();
	private JTable table;
	private JScrollPane scrollPane;

	public Tab_WoundHistory(String pNo) {
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

		
		String sql = "Select * from wound_accessment " + "WHERE p_no = '" + pNo
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
				Object[][] matrix = new Object[rowCount][14];
				String[] header = new String[] { lang.getString("PHHD"),
						lang.getString("PHCD"), lang.getString("PHHC"),
						lang.getString("PHDM"), lang.getString("PHTYPEA"),
						lang.getString("PHTYPEB"), lang.getString("PHTYPEC"),
						lang.getString("PHMALIG"), lang.getString("HD"),
						lang.getString("PHNS"), lang.getString("PHSMOKE"),
						lang.getString("PHALCOHOLISM"), lang.getString("PHOW"),
						lang.getString("PHOTHER")};
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
