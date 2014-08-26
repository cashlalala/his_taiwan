package medicinestock;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import multilingual.Language;
import autocomplete.CompleterComboBox;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import cc.johnwu.sql.HISModel;
import cc.johnwu.sql.HISModel.EditableTableModel;
import errormessage.StoredErrorMessage;


public class Frm_MidicineStockInfo extends javax.swing.JFrame implements cc.johnwu.date.DateInterface {
    private CompleterComboBox m_Cobww;
    private CompleterComboBox m_PurchaseCombo;
    /*多國語言變數*/
    private Language paragraph = Language.getInstance();
    private String[] line = new String(paragraph.setlanguage("MIDICINESTOCKINFO")).split("\n") ;
    private String[] message = new String(paragraph.setlanguage("MESSAGE")).split("\n") ;
    /*輸出錯誤資訊變數*/
    StoredErrorMessage ErrorMessage = new StoredErrorMessage() ;

    public Frm_MidicineStockInfo() {
        initComponents();
        initLanguage() ;
        this.setExtendedState(this.MAXIMIZED_BOTH);  // 最大化
        this.tab_MedicineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // tabble不可按住多選
        this.setLocationRelativeTo(this);
        this.tab_MedicineList.setAutoCreateRowSorter(true);
        tab_MedicineList.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        addWindowListener(new WindowAdapter() {  // 畫面關閉原視窗enable
        @Override
        public void windowClosing(WindowEvent windowevent) {
            btn_CloseActionPerformed(null);
        }});
        showMedTabList();

    }
    private void initLanguage() {
        this.btn_Search.setText(paragraph.getString("SEARCH"));
        this.btn_Purchase.setText(paragraph.getString("PURCHASE"));
        this.btn_History.setText(paragraph.getString("PURCHASEHISTORY"));
        this.btn_Close.setText(paragraph.getString("CLOSE"));
        this.btn_saveModification.setText(paragraph.getString("SAVEMODIFICATION"));
        this.setTitle(paragraph.getString("PHARMACYSTOCK"));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pan_Center = new javax.swing.JPanel();
        pan_Top = new javax.swing.JPanel();
        btn_Search = new javax.swing.JButton();
        btn_saveModification = new javax.swing.JButton();
        text_Search = new javax.swing.JTextField();
        span_MedicineList = new javax.swing.JScrollPane();
        tab_MedicineList = new javax.swing.JTable();
        pan_Right = new javax.swing.JPanel();
        btn_Purchase = new javax.swing.JButton();
        btn_Close = new javax.swing.JButton();
        btn_History = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btn_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchActionPerformed(evt);
            }
        });
        
        btn_saveModification.setEnabled(false);
        btn_saveModification.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDefaultPriceAndThreshold();
            }
        });

        text_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_SearchKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pan_TopLayout = new javax.swing.GroupLayout(pan_Top);
        pan_Top.setLayout(pan_TopLayout);
        pan_TopLayout.setHorizontalGroup(
            pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_TopLayout.createSequentialGroup()
                .addComponent(text_Search, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117))
        );
        pan_TopLayout.setVerticalGroup(
            pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(text_Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_Search))
        );

        tab_MedicineList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_MedicineListMouseClicked(evt);
            }
        });
        
        tab_MedicineList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            	tab_MedicineListMouseReleased(evt);
            }
        });//tab_MedicineListMouseReleased
        
        span_MedicineList.setViewportView(tab_MedicineList);

        btn_Purchase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PurchaseActionPerformed(evt);
            }
        });

        btn_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CloseActionPerformed(evt);
            }
        });

        btn_History.setEnabled(false);
        btn_History.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(pan_Right);
        pan_Right.setLayout(pan_RightLayout);
        pan_RightLayout.setHorizontalGroup(
            pan_RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_RightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Purchase, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(btn_History, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(btn_saveModification, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(btn_Close, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                .addContainerGap())
        );
        pan_RightLayout.setVerticalGroup(
            pan_RightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_RightLayout.createSequentialGroup()
                .addComponent(btn_Purchase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_History)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_saveModification)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Close)
                .addContainerGap(396, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pan_CenterLayout = new javax.swing.GroupLayout(pan_Center);
        pan_Center.setLayout(pan_CenterLayout);
        pan_CenterLayout.setHorizontalGroup(
            pan_CenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_CenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_CenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan_Top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_CenterLayout.createSequentialGroup()
                        .addComponent(span_MedicineList, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pan_Right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pan_CenterLayout.setVerticalGroup(
            pan_CenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_CenterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_Top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_CenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan_Right, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(span_MedicineList, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_Center, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_Center, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showMedTabList(){//各藥品剩餘總量
        ResultSet tabArray= null;
        try{
        	String ArraySql = "SELECT medical_stock.item_guid as Code, medicines.item as Description, medical_stock.current_amount as Quantity, "
        			+ " medical_stock.minimal_amount as Threshold, medical_stock.default_unit_price as 'Default price' "
        			+ " FROM medical_stock, medicines " 
        			+ " WHERE medicines.code = medical_stock.item_guid "
        			+ " AND type = 'P' " 
        			+ " ORDER BY item_guid";
            tabArray= DBC.executeQuery(ArraySql);
            EditableTableModel dtm = HISModel.getModel(tabArray, false);
            dtm.setCellEditable(3, true);
            dtm.setCellEditable(4, true);
            this.tab_MedicineList.setModel(dtm);
            DBC.closeConnection(tabArray);
        }catch(SQLException e) {
            ErrorMessage.setData("MedicineStock", "Frm_MedicineStockInfo" ,"showMedTabList()",
                e.toString().substring(e.toString().lastIndexOf(".")+1, e.toString().length()));
        }
        setCloumnWidth(this.tab_MedicineList);
    }

    private void setCloumnWidth(javax.swing.JTable tab){
        //設定column寬度
        TableColumn columnVisitNo = tab.getColumnModel().getColumn(0);
        TableColumn columnVisitName = tab.getColumnModel().getColumn(1);
        TableColumn columnVisitQuantity = tab.getColumnModel().getColumn(2);
        columnVisitNo.setPreferredWidth(30);
        columnVisitName.setPreferredWidth(250);
        columnVisitQuantity.setPreferredWidth(150);
        tab.setRowHeight(30);
    }

    // 回到看診畫面 畫面重設為可編輯
    public void reSetEnable() {
        this.setEnabled(true);
    }
    
    private void btn_PurchaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PurchaseActionPerformed
        new Frm_MedicineStockPurchase(this).setVisible(true);
        btn_History.setEnabled(false);
        btn_saveModification.setEnabled(false);
        tab_MedicineList.removeRowSelectionInterval(0, tab_MedicineList.getRowCount()-1);
        this.setEnabled(false);
    }//GEN-LAST:event_btn_PurchaseActionPerformed

    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        new main.Frm_Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_CloseActionPerformed

    // 判斷進貨輸入的值是否為數字
    public boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9,.]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        } else {
            return true;
        }
    }
    
    private void saveDefaultPriceAndThreshold() {
    	int rowCount = tab_MedicineList.getRowCount();
    	for(int i = 0; i < rowCount; i++) {
    		if (! ( isNumber(tab_MedicineList.getValueAt(i,3).toString()) 
    				&& isNumber(tab_MedicineList.getValueAt(i,4).toString()) )) {
    			JOptionPane.showMessageDialog(null, paragraph.getString("PLEASEENTERVALIDNUMBER"));
    			return;
    		}
    	}
    	
    	for(int i = 0; i < rowCount; i++) {
    		float threshold = Float.parseFloat(tab_MedicineList.getValueAt(i,3).toString());
    		float defaultPrice = Float.parseFloat(tab_MedicineList.getValueAt(i,4).toString());
    		String item_code = tab_MedicineList.getValueAt(i,0).toString();
    		
    		String sqlChangeRecord = "";
    		ResultSet rsStockSQL= null;
    		try{
	        	String stockSQL = "SELECT * FROM medical_stock WHERE type ='P' AND item_guid = '" + item_code + "'";
	        	rsStockSQL= DBC.executeQuery(stockSQL);
	            if(rsStockSQL.next()) {
	            	float minimalAmount = rsStockSQL.getFloat("minimal_amount");
	            	float defaultUnitPrice = rsStockSQL.getFloat("default_unit_price");
	                float diffMinimal = threshold - minimalAmount;
	                float diffPrice = defaultPrice - defaultUnitPrice;
	                if(diffMinimal == 0 && diffPrice == 0)	continue;
	                
	                sqlChangeRecord = "INSERT INTO medical_stock_change_record (`guid`, `action`, `item_guid`, `type`, "
	                		+ " `s_no`, `diff_price`, `diff_minimal_amount`) "
	                		+ " VALUES (uuid(), 'M', '" + item_code + "', 'P', '" + UserInfo.getUserNO() + "', '" + diffPrice + "', '" + diffMinimal + "');";
	            }
	            DBC.closeConnection(rsStockSQL);
	            String sql = "UPDATE medical_stock SET `minimal_amount`='" + threshold + "', `default_unit_price`='" + defaultPrice + "'  WHERE type ='P' AND item_guid = '" + item_code + "'";

				DBC.executeUpdate(sql);
				DBC.executeUpdate(sqlChangeRecord);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
                ErrorMessage.setData("MedicineStock", "Frm_MedicineStockPurchase" ,"saveDefaultPriceAndThreshold() - DBC.closeConnection",
                		e.toString().substring(e.toString().lastIndexOf(".")+1, e.toString().length()));
				e.printStackTrace();
			}
    	}
    	
    	JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));
    	showMedTabList();
    	btn_History.setEnabled(false);
    	btn_saveModification.setEnabled(false);    	
    }
    
    private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchActionPerformed
        ResultSet tabArray= null;
        if(!text_Search.getText().equals("")){
            String conditions = "AND (UPPER(medicines.code) LIKE UPPER('%" + text_Search.getText().replace(" ", "%") + "%') ) ";
            try{
            	String ArraySql="SELECT medical_stock.item_guid as Code, medicines.item as Description, medical_stock.current_amount as Quantity, "
        			+ " medical_stock.minimal_amount as Threshold, medical_stock.default_unit_price as 'Default price' "
            			+ " FROM medical_stock, medicines "
            			+ " WHERE medicines.code = medical_stock.item_guid AND type = 'P' "
            			+ conditions;
                tabArray= DBC.executeQuery(ArraySql);
                EditableTableModel dtm = HISModel.getModel(tabArray, false);
                dtm.setCellEditable(3, true);
                dtm.setCellEditable(4, true);
                this.tab_MedicineList.setModel(dtm);
                DBC.closeConnection(tabArray);
            }catch(SQLException e) {
            ErrorMessage.setData("MedicineStock", "Frm_MedicineStockInfo" ,"btn_SearchActionPerformed()",
                e.toString().substring(e.toString().lastIndexOf(".")+1, e.toString().length()));
            e.printStackTrace();
            }
            setCloumnWidth(this.tab_MedicineList);
        }else{showMedTabList();}
    }//GEN-LAST:event_btn_SearchActionPerformed

    private void btn_MoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MoreActionPerformed
        this.setEnabled(false);
        new Frm_MedicineStockPurchaseList(this, tab_MedicineList.getValueAt(tab_MedicineList.getSelectedRow(), 0)).setVisible(true);

    }//GEN-LAST:event_btn_MoreActionPerformed

    private void tab_MedicineListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_MedicineListMouseClicked
        if (evt.getClickCount() == 2) 
        	if ((tab_MedicineList.getSelectedColumn() == 3 || tab_MedicineList.getSelectedColumn() == 4))
            	btn_saveModification.setEnabled(true);
    }//GEN-LAST:event_tab_MedicineListMouseClicked
    
    private void tab_MedicineListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_MedicineListMouseClicked
        if (tab_MedicineList.getSelectedRow() != -1) {
        	btn_History.setEnabled(true);
        }else {btn_History.setEnabled(false);}
       
        //if ((tab_MedicineList.getSelectedColumn() == 3 || tab_MedicineList.getSelectedColumn() == 4))
        //	btn_saveModification.setEnabled(true);
        
        if (evt.getClickCount() == 2) 
            btn_MoreActionPerformed(null);
    }//GEN-LAST:event_tab_MedicineListMouseClicked

    private void text_SearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_SearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btn_SearchActionPerformed(null);
        }
    }//GEN-LAST:event_text_SearchKeyPressed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Close;
    private javax.swing.JButton btn_History;
    private javax.swing.JButton btn_Purchase;
    private javax.swing.JButton btn_Search;
    private javax.swing.JButton btn_saveModification;
    private javax.swing.JPanel pan_Center;
    private javax.swing.JPanel pan_Right;
    private javax.swing.JPanel pan_Top;
    private javax.swing.JScrollPane span_MedicineList;
    private javax.swing.JTable tab_MedicineList;
    private javax.swing.JTextField text_Search;
    // End of variables declaration//GEN-END:variables

    public void onDateChanged() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}
