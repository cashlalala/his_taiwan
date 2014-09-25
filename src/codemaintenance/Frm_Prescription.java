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
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import main.Frm_Main;
import multilingual.Language;
import cc.johnwu.sql.DBC;
import common.TabTools;
import errormessage.StoredErrorMessage;

public class Frm_Prescription extends JFrame {

	private static final long serialVersionUID = 1L;

	/* 多國語言變數 */
	private Language paragraph = Language.getInstance();
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	public Frm_Prescription() {
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

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Pharmacy Code Maintenance");

		span_List = new javax.swing.JScrollPane();
		tab_List = new javax.swing.JTable();
		String s[]={"changed", "code", "ICDVersion", "loinc", "name", "shortname", 
				"type", "equipment_ID", "data_type", "limit", "unit", "effective", 
				"guideline", "cost", "default_price"};
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
   		String sql = "SELECT * FROM prescription_code";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            String[] rowData = new String[15];
            while(rs.next()){
            	rowData[0] = "N";
            	rowData[1] = rs.getString("code");
            	rowData[2] = rs.getString("ICDVersion");
            	rowData[3] = rs.getString("loinc");
            	rowData[4] = rs.getString("name");
            	rowData[5] = rs.getString("shortname");
            	rowData[6] = rs.getString("type");
            	rowData[7] = rs.getString("equipment_ID");
            	rowData[8] = rs.getString("data_type");
            	rowData[9] = rs.getString("limit");
            	rowData[10] = rs.getString("unit");
            	rowData[11] = rs.getString("effective");
            	rowData[12] = rs.getString("guideline");
            	rowData[13] = rs.getString("cost");
            	rowData[14] = rs.getString("default_price");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Prescription.class.getName()).log(Level.SEVERE, null, ex);
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
		model.addRow(new Object[]{"A", "", "", "", "", "", "", "", "", "", "", "", "", "", ""});
    	JOptionPane.showMessageDialog(null, paragraph.getString("ADDCOMPLETE"));
	}
	
	private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {
		
		String code = (String) tab_List.getValueAt(tab_List.getSelectedRow(), 1);
		try {
    	  	String sql = "DELETE FROM `prescription_code` WHERE code ='" + code + "'";
            DBC.executeUpdate(sql);
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Prescription.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	        return;
	    }
    	reloadList(tableModel);
    	JOptionPane.showMessageDialog(null, paragraph.getString("DELETECOMPLETE"));
	}
	
	private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel dtm =(DefaultTableModel) tab_List.getModel();
    	for ( int i = 0 ;i < dtm.getRowCount(); i++){
    		String changed = (String) dtm.getValueAt(i, 0);
    		if(changed.compareTo("Y") == 0 || changed.compareTo("A") == 0) {
 
    	   		String s[]={"changed", "code", "ICDVersion", "loinc", "name", 
    	   				"shortname", "type", "equipment_ID", "data_type", "limit", 
    	   				"unit", "effective", "guideline", "cost", "default_price"};
    	   		
    			String code = (String) dtm.getValueAt(i, 1);
    			String ICDVersion = (String) dtm.getValueAt(i, 2);
    			String loinc = (String) dtm.getValueAt(i, 3);
    			String name = (String) dtm.getValueAt(i, 4);
    			String shortname = (String) dtm.getValueAt(i, 5);// == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 5));
    			String type = (String) dtm.getValueAt(i, 6);// == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 6));
    			String equipment_ID = (String) dtm.getValueAt(i, 7);
    			String data_type = (String) dtm.getValueAt(i, 8);// == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 8));
    			String limit = (String) dtm.getValueAt(i, 9);// == null ? 0 : Integer.parseInt((String) dtm.getValueAt(i, 9));
    			String unit = (String) dtm.getValueAt(i, 10);// == null ? 0 : Integer.parseInt((String) dtm.getValueAt(i, 10));
    			String effective = (String) dtm.getValueAt(i, 11);
    			String guideline = (String) dtm.getValueAt(i, 12);
    			String cost = (String) dtm.getValueAt(i, 13);
    			String default_price = (String) dtm.getValueAt(i, 14);
    			
    			try {
    				String sql = "UPDATE prescription_code SET ICDVersion = ?, loinc = ?, "
	    					+ " name = ?, shortname = ?, type = ?, equipment_ID = ?, "
	    					+ " data_type = ?, limit = ?, unit = ?, effective = ?, "
	    					+ " guideline = ?, cost = ?, default_price = ? "
	    					+ " WHERE code = ? ";
    				if(changed.compareTo("A") == 0)
		    			sql = "INSERT INTO prescription_code (`ICDVersion`, `loinc`, `name`, `shortname`, " 
		    				+ " `type`, `equipment_ID`, `data_type`, `limit`, `unit`, "
		    				+ " `effective`, `guideline`, `cost`, `default_price`, `code`) VALUES "
		    				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    				
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
					if(ICDVersion != null && !ICDVersion.isEmpty()) prestate.setString(1, ICDVersion); else prestate.setNull(1, java.sql.Types.VARCHAR);
					if(loinc != null && !loinc.isEmpty()) prestate.setString(2, loinc); else prestate.setNull(2, java.sql.Types.VARCHAR);
					if(name != null && !name.isEmpty()) prestate.setString(3, name); else prestate.setNull(3, java.sql.Types.VARCHAR);
					if(shortname != null && !shortname.isEmpty()) prestate.setString(4, shortname); else prestate.setNull(4, java.sql.Types.VARCHAR);
					if(type != null && !type.isEmpty()) prestate.setString(5, type); else prestate.setNull(5, java.sql.Types.VARCHAR);
					if(equipment_ID != null && !equipment_ID.isEmpty()) prestate.setString(6, equipment_ID); else prestate.setNull(6, java.sql.Types.VARCHAR);
					if(data_type != null && !data_type.isEmpty()) prestate.setString(7, data_type); else prestate.setNull(7, java.sql.Types.CHAR);
					if(limit != null && !limit.isEmpty()) prestate.setString(8, limit); else prestate.setNull(8, java.sql.Types.BLOB);
					if(unit != null && !unit.isEmpty()) prestate.setString(9, unit); else prestate.setNull(9, java.sql.Types.CHAR);
					if(effective != null && !effective.isEmpty()) prestate.setInt(10, Integer.parseInt(effective)); else prestate.setNull(10, java.sql.Types.BIT);
					if(guideline != null && !guideline.isEmpty()) prestate.setString(11, guideline); else prestate.setNull(11, java.sql.Types.VARCHAR);
					if(cost != null && !cost.isEmpty()) prestate.setFloat(12, Float.parseFloat(cost)); else prestate.setNull(12, java.sql.Types.FLOAT);
					if(default_price != null && !default_price.isEmpty()) prestate.setFloat(13, Float.parseFloat(default_price)); else prestate.setNull(13, java.sql.Types.FLOAT);
					prestate.setString(14, code);
					prestate.executeUpdate();  //真正執行
					DBC.closeConnectionExternel(conn);
    		    } catch (SQLException ex) {
    		        Logger.getLogger(Frm_Prescription.class.getName()).log(Level.SEVERE, null, ex);
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
	private javax.swing.JPanel pan_Center;
	private javax.swing.JPanel pan_Right;
	private javax.swing.JScrollPane span_List;
	private javax.swing.JTable tab_List;
}
