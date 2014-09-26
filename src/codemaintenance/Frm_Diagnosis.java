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

public class Frm_Diagnosis extends JFrame {

	private static final long serialVersionUID = 1L;

	/* 多國語言變數 */
	private Language paragraph = Language.getInstance();
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	public Frm_Diagnosis() {
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
		String s[]={"changed", "dia_code", "icd_code", "ICDVersion", "name", "effective"};
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
   		String sql = "SELECT * FROM diagnosis_code";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            String[] rowData = new String[6];
            while(rs.next()){
            	rowData[0] = "N";
            	rowData[1] = rs.getString("dia_code");
            	rowData[2] = rs.getString("icd_code");
            	rowData[3] = rs.getString("ICDVersion");
            	rowData[4] = rs.getString("name");
            	rowData[5] = rs.getString("effective");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
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
		
		String dia_code = (String) tab_List.getValueAt(tab_List.getSelectedRow(), 1);
		try {
    	  	String sql = "DELETE FROM `diagnosis_code` WHERE dia_code ='" + dia_code + "'";
            DBC.executeUpdate(sql);
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
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
            	
    			String dia_code = (String) dtm.getValueAt(i, 1);
    			String icd_code = (String) dtm.getValueAt(i, 2);
    			String ICDVersion = (String) dtm.getValueAt(i, 3);
    			String name = (String) dtm.getValueAt(i, 4);
    			String effective = (String) dtm.getValueAt(i, 5);
    			
    			try {
    				String sql = "UPDATE diagnosis_code SET icd_code = ?, "
	    					+ " ICDVersion = ?, name = ?, effective = ? "
	    					+ " WHERE dia_code = ? ";
    				if(changed.compareTo("A") == 0)
		    			sql = "INSERT INTO diagnosis_code (`icd_code`, `ICDVersion`, `name`, `effective`, " 
		    				+ " `dia_code`) VALUES "
		    				+ "(?, ?, ?, ?, ?)";
    				
					Connection conn = DBC.getConnectionExternel();
					PreparedStatement prestate = conn.prepareStatement(sql); //先建立一個 SQL 語句並回傳一個 PreparedStatement 物件
					if(icd_code != null && !icd_code.isEmpty()) prestate.setString(1, icd_code); else prestate.setNull(1, java.sql.Types.CHAR);
					if(ICDVersion != null && !ICDVersion.isEmpty()) prestate.setString(2, ICDVersion); else prestate.setNull(2, java.sql.Types.VARCHAR);
					if(name != null && !name.isEmpty()) prestate.setString(3, name); else prestate.setNull(3, java.sql.Types.VARCHAR);
					if(effective != null && !effective.isEmpty()) prestate.setFloat(4, Integer.parseInt(effective)); else prestate.setNull(4, java.sql.Types.BIT);
					prestate.setString(5, dia_code);
					prestate.executeUpdate();  //真正執行
					DBC.closeConnectionExternel(conn);
    		    } catch (SQLException ex) {
    		        Logger.getLogger(Frm_Diagnosis.class.getName()).log(Level.SEVERE, null, ex);
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
