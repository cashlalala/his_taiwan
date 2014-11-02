package codemaintenance;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import main.Frm_Main;
import multilingual.Language;
import cc.johnwu.sql.DBC;
import common.TabTools;
import errormessage.StoredErrorMessage;

public class Frm_Pharmacy extends JFrame {

	private static final long serialVersionUID = 1L;

	/* 多國語言變數 */
	private Language paragraph = Language.getInstance();
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	public Frm_Pharmacy() {
		initComponents();
		initLanguage();
		initLanguage();
		init();
	}

	public class ForcedListSelectionModel extends DefaultListSelectionModel {

	    public ForcedListSelectionModel () {
	        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	    @Override
	    public void clearSelection() {
	    }

	    @Override
	    public void removeSelectionInterval(int index0, int index1) {
	    }

	}
    private DefaultTableModel tableModel = new DefaultTableModel(){
    	public boolean isCellEditable(int rowIndex, int columnIndex){
    		if (columnIndex == 0) {
                return false;
            } else {
                return true;
            }
    	}
   	};
   	
	@SuppressWarnings("deprecation")
	private void initLanguage() {
		//this.lab_poli.setText(paragraph.getString("POLI"));
		this.btn_Close.setText(paragraph.getString("CLOSE"));
		this.btn_Save.setText(paragraph.getString("SAVE"));
		this.btn_Add.setText(paragraph.getString("ADD"));
		this.btn_Delete.setText(paragraph.getString("DELETE"));
		this.btn_Import.setText(paragraph.getString("IMPORTCSV"));
	}
	
	public void init() {
		reloadList(tableModel);
	}

	private void initComponents() {
		
		pan_Center = new javax.swing.JPanel();
		pan_Right = new javax.swing.JPanel();
		btn_Close = new javax.swing.JButton();
		btn_Save = new javax.swing.JButton();
		btn_Add = new javax.swing.JButton();
		btn_Delete = new javax.swing.JButton();
		btn_Import = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Pharmacy Code Maintenance");

		span_List = new javax.swing.JScrollPane();
		tab_List = new javax.swing.JTable();
		String s[]={"changed", "code", "ATC_code", "name", "description", "unit_dosage", 
				"unit_cost", "unit", "unit_price", "effective", "sort", "selfDrug", 
				"default_freq", "default_way"};
		tableModel.setColumnIdentifiers(s);
		tab_List.setSelectionModel(new ForcedListSelectionModel());
		tab_List.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tab_List.setRowHeight(25);

		tab_List.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_BedListMouseClicked(evt);
			}
		});
		tab_List.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_BedListKeyPressed(evt);
			}
		});
		span_List.setViewportView(tab_List);
		tab_List.setModel(tableModel);

		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable
			@Override
			public void windowClosing(WindowEvent windowevent) {
				mnit_CloseActionPerformed(null);
			}
		});
		
		javax.swing.GroupLayout pan_CenterLayout = new javax.swing.GroupLayout(
				pan_Center);
		pan_Center.setLayout(pan_CenterLayout);
		pan_CenterLayout
				.setHorizontalGroup(pan_CenterLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_CenterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												span_List,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												601, Short.MAX_VALUE)
										.addContainerGap()));
		pan_CenterLayout.setVerticalGroup(pan_CenterLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				pan_CenterLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(span_List,
								javax.swing.GroupLayout.DEFAULT_SIZE, 434,
								Short.MAX_VALUE).addContainerGap()));


		btn_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CloseActionPerformed(evt);
			}
		});
		
		btn_Save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_SaveActionPerformed(evt);
			}
		});
		
		btn_Add.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_AddActionPerformed(evt);
			}
		});
		
		btn_Delete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_DeleteActionPerformed(evt);
			}
		});
		
		btn_Import.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_ImportActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_RightLayout.setHorizontalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(pan_RightLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_Add, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Save, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Delete, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Import, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Close, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
					.addContainerGap())
		);
		pan_RightLayout.setVerticalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btn_Add)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Save)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Delete)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Import)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Close)
					.addContainerGap(335, Short.MAX_VALUE))
		);
		pan_Right.setLayout(pan_RightLayout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		pan_Center,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		pan_Right,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														pan_Center,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														pan_Right,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));

		pack();
	}
	
   	private void reloadList(DefaultTableModel dtm) {
   		dtm.setRowCount(0);
   		String sql = "SELECT * FROM medicines";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            String[] rowData = new String[14];
            while(rs.next()){
            	rowData[0] = "N";
            	rowData[1] = rs.getString("code");
            	rowData[2] = rs.getString("ATC_code");
            	rowData[3] = rs.getString("item");
            	rowData[4] = rs.getString("injection");
            	rowData[5] = rs.getString("unit_dosage");
            	rowData[6] = rs.getString("unit_cost");
            	rowData[7] = rs.getString("unit");
            	rowData[8] = rs.getString("unit_price");
            	rowData[9] = rs.getString("effective");
            	rowData[10] = rs.getString("sort");
            	rowData[11] = rs.getString("selfDrug");
            	rowData[12] = rs.getString("default_freq");
            	rowData[13] = rs.getString("default_way");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Pharmacy.class.getName()).log(Level.SEVERE, null, ex);
         }
        // setup status combobox
   		//setUpDivisionColumn(tab_List, tab_List.getColumnModel().getColumn(4));
        //setUpStatusColumn(tab_List, tab_List.getColumnModel().getColumn(5));
        //setCloumnWidth(tab_List);
        
        TabTools.setHideColumn(tab_List, 0);
        //TabTools.setHideColumn(tab_List, 1);
   	}
   	
	private void mnit_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_CloseActionPerformed
		new Frm_Main().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_mnit_CloseActionPerformed

	private void tab_BedListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_BedListMouseClicked
		//if (evt.getClickCount() == 2 ) {
		if(tab_List.getSelectedRow() == -1) return;
		String orgVaue = (String) tab_List.getValueAt(tab_List.getSelectedRow(), 0);
		if(orgVaue.compareTo("N") == 0)
			tab_List.setValueAt("Y", tab_List.getSelectedRow(), 0);
		//	System.out.println("22");
		//}
	}// GEN-LAST:event_tab_BedListMouseClicked

	@SuppressWarnings("deprecation")
	private void tab_BedListKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tab_BedListKeyPressed
		//if (this.tab_BedList.getRowCount() > 0) {
		//	this.btn_Save.setEnabled(true);
		//}
		//System.out.println("11");
		if(tab_List.getSelectedRow() == -1) return;
		String orgVaue = (String) tab_List.getValueAt(tab_List.getSelectedRow(), 0);
		if(orgVaue.compareTo("N") == 0)
			tab_List.setValueAt("Y", tab_List.getSelectedRow(), 0);

	}// GEN-LAST:event_tab_BedListKeyPressed

	private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel model = (DefaultTableModel) tab_List.getModel();
		model.addRow(new Object[]{"A", "", "", "", "", "", "", "", "", "", "", "", "", ""});
    	JOptionPane.showMessageDialog(null, paragraph.getString("ADDCOMPLETE"));
	}
	
	private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {
		
		String code = (String) tab_List.getValueAt(tab_List.getSelectedRow(), 1);
		try {
    	  	String sql = "DELETE FROM `medicines` WHERE code ='" + code + "'";
            DBC.executeUpdate(sql);
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Pharmacy.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	        return;
	    }
    	reloadList(tableModel);
    	JOptionPane.showMessageDialog(null, paragraph.getString("DELETECOMPLETE"));
	}
	
	private void btn_ImportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CloseActionPerformed
		//mnit_CloseActionPerformed(null);
		String filePath;
		JFileChooser jFileChooser1 = new javax.swing.JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
        jFileChooser1.setFileFilter(filter);
		int returnVal = jFileChooser1.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			filePath = jFileChooser1.getSelectedFile().getAbsolutePath();
			try {
				String sql = "LOAD DATA LOCAL INFILE '" + filePath
						+ "' INTO TABLE medicines FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES";
				DBC.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, paragraph.getString("WOUND_CASE_CONFIRM_SAVE_FAIL"));
			}
			JOptionPane.showMessageDialog(null, paragraph.getString("WOUND_CASE_CONFIRM_SAVE_SUCC"));
		}
	}// GEN-LAST:event_btn_CloseActionPerformed
	
	private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel dtm =(DefaultTableModel) tab_List.getModel();
    	for ( int i = 0 ;i < dtm.getRowCount(); i++){
    		String changed = (String) dtm.getValueAt(i, 0);
    		if(changed.compareTo("Y") == 0 || changed.compareTo("A") == 0) {
            	
    			String code = (String) dtm.getValueAt(i, 1);
    			String ATC_code = (String) dtm.getValueAt(i, 2);
    			String item = (String) dtm.getValueAt(i, 3);
    			String injection = (String) dtm.getValueAt(i, 4);
    			String unit_dosage = (String) dtm.getValueAt(i, 5);// == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 5));
    			String unit_cost = (String) dtm.getValueAt(i, 6);// == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 6));
    			String unit = (String) dtm.getValueAt(i, 7);
    			String unit_price = (String) dtm.getValueAt(i, 8);// == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 8));
    			String effective = (String) dtm.getValueAt(i, 9);// == null ? 0 : Integer.parseInt((String) dtm.getValueAt(i, 9));
    			String sort = (String) dtm.getValueAt(i, 10);// == null ? 0 : Integer.parseInt((String) dtm.getValueAt(i, 10));
    			String selfDrug = (String) dtm.getValueAt(i, 11);
    			String default_freq = (String) dtm.getValueAt(i, 12);
    			String default_way = (String) dtm.getValueAt(i, 13);
    			
    			try {
    				String sql = "UPDATE medicines SET ATC_code = ?, item = ?, "
	    					+ " injection = ?, unit_dosage = ?, unit_cost = ?, unit = ?, "
	    					+ " unit_price = ?, effective = ?, sort = ?, selfDrug = ?, "
	    					+ " default_freq = ?, default_way = ? "
	    					+ " WHERE code = ? ";
    				if(changed.compareTo("A") == 0)
		    			sql = "INSERT INTO medicines (`ATC_code`, `item`, `injection`, `unit_dosage`, " 
		    				+ " `unit_cost`, `unit`, `unit_price`, `effective`, `sort`, "
		    				+ " `selfDrug`, `default_freq`, `default_way`, `code`) VALUES "
		    				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    				
    				/*System.out.println();
    				System.out.println(sql);
    				System.out.println("changed = " + changed);
    				System.out.println("code = " + code);
    				System.out.println("ATC_code = " + ATC_code);
    				System.out.println("item = " + item);
    				System.out.println("injection = " + injection);
    				System.out.println("unit_dosage = " + unit_dosage);
    				System.out.println("unit_cost = " + unit_cost);
    				System.out.println("unit = " + unit);
    				System.out.println("unit_price = " + unit_price);
    				System.out.println("effective = " + effective);
    				System.out.println("sort = " + sort);
    				System.out.println("selfDrug = " + selfDrug);
    				System.out.println("default_freq = " + default_freq);
    				System.out.println("default_way = " + default_way);
    				System.out.println();*/
    				
					Connection conn = DBC.getConnectionExternel();
					PreparedStatement prestate = conn.prepareStatement(sql); //先建立一個 SQL 語句並回傳一個 PreparedStatement 物件
					if(ATC_code != null && !ATC_code.isEmpty()) prestate.setString(1, ATC_code); else prestate.setNull(1, java.sql.Types.CHAR);
					if(item != null && !item.isEmpty()) prestate.setString(2, item); else prestate.setNull(2, java.sql.Types.VARCHAR);
					if(injection != null && !injection.isEmpty()) prestate.setString(3, injection); else prestate.setNull(3, java.sql.Types.VARCHAR);
					if(unit_dosage != null && !unit_dosage.isEmpty()) prestate.setFloat(4, Float.parseFloat(unit_dosage)); else prestate.setNull(4, java.sql.Types.FLOAT);
					if(unit_cost != null && !unit_cost.isEmpty()) prestate.setFloat(5, Float.parseFloat(unit_cost)); else prestate.setNull(5, java.sql.Types.FLOAT);
					if(unit != null && !unit.isEmpty()) prestate.setString(6, unit); else prestate.setNull(6, java.sql.Types.CHAR);
					if(unit_price != null && !unit_price.isEmpty()) prestate.setFloat(7, Float.parseFloat(unit_price)); else prestate.setNull(7, java.sql.Types.FLOAT);
					if(effective != null && !effective.isEmpty()) prestate.setInt(8, Integer.parseInt(effective)); else prestate.setNull(8, java.sql.Types.BIT);
					if(sort != null && !sort.isEmpty()) prestate.setInt(9, Integer.parseInt(sort)); else prestate.setNull(9, java.sql.Types.INTEGER);
					if(selfDrug != null && !selfDrug.isEmpty()) prestate.setString(10, selfDrug); else prestate.setNull(10, java.sql.Types.CHAR);
					if(default_freq != null && !default_freq.isEmpty()) prestate.setString(11, default_freq); else prestate.setNull(11, java.sql.Types.VARCHAR);
					if(default_way != null && !default_way.isEmpty()) prestate.setString(12, default_way); else prestate.setNull(12, java.sql.Types.VARCHAR);
					prestate.setString(13, code);
					prestate.executeUpdate();  //真正執行
					DBC.closeConnectionExternel(conn);
    		    } catch (SQLException ex) {
    		        Logger.getLogger(Frm_Pharmacy.class.getName()).log(Level.SEVERE, null, ex);
    		        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
    		        return;
    		    }
    		}
    	}

    	reloadList(tableModel);
    	JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));
	}
	
	private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CloseActionPerformed
		mnit_CloseActionPerformed(null);
	}// GEN-LAST:event_btn_CloseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btn_Close;
	private javax.swing.JButton btn_Save;
	private javax.swing.JButton btn_Add;
	private javax.swing.JButton btn_Delete;
	private javax.swing.JButton btn_Import;
	private javax.swing.JPanel pan_Center;
	private javax.swing.JPanel pan_Right;
	private javax.swing.JScrollPane span_List;
	private javax.swing.JTable tab_List;
}
