package materialstock;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import multilingual.Language;
import autocomplete.CompleterComboBox;
import cc.johnwu.date.DateInterface;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import errormessage.StoredErrorMessage;

public class Frm_MaterialStockDispose extends JFrame implements DateInterface {
    private CompleterComboBox m_Cobww;
    private Frm_MaterialStockInfo m_MaterialStockInfo;
    /*多國語言變數*/
    private Language paragraph = Language.getInstance();
//    private String[] line = new String(paragraph.setlanguage("MEDICINESTOCKPURCHASE")).split("\n") ;
//    private String[] message = new String(paragraph.setlanguage("MESSAGE")).split("\n") ;
    /*輸出錯誤資訊變數*/
    StoredErrorMessage ErrorMessage = new StoredErrorMessage();

    public Frm_MaterialStockDispose(Frm_MaterialStockInfo materialStockInfo) {
    	m_MaterialStockInfo = materialStockInfo;
        initComponents();
        initLanguage();
        showCombo();
        this.date_Choose.setParentFrame(this);
        setAddEnable();
        this.tab_Dispose.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // table不可按住多選
        this.tab_Dispose.setAutoCreateRowSorter(true);
        this.setLocationRelativeTo(this);
        addWindowListener(new WindowAdapter() {  // 畫面關閉原視窗enable
        @Override
        public void windowClosing(WindowEvent windowevent) {
            btn_CloseActionPerformed(null);
        }});
        //if (!UserInfo.getSelectPow("Stock")) {
        //    setHidePrice();
        //}       
    }

    private void initLanguage() {
    	this.lab_MedicineCode.setText(paragraph.getString("CODE"));
        this.lab_Date.setText(paragraph.getString("DISPOSALEDATE"));
        //this.lab_Company.setText(paragraph.getString("VENDOR"));
        this.lab_Quantity.setText(paragraph.getString("QUANTITY"));
        //this.lab_Price.setText(paragraph.getString("TOTALCOST"));
        this.btn_Delete.setText(paragraph.getString("DELETE"));
        this.btn_Add.setText(paragraph.getString("ADD"));
        this.btn_Send.setText(paragraph.getString("SEND"));
        this.btn_Close.setText(paragraph.getString("CLOSE"));
        this.setTitle(paragraph.getString("TITLEPHARMACYDISPOSE"));
    }
    /**目前庫存頁  藥品的combo選單*/
    private void showCombo(){//
        ResultSet rsArray= null;
   
        try{
            //String ArraySQL="SELECT medicines.code AS name FROM medicines WHERE effective = 1";
        	String ArraySQL = "SELECT item_guid as 'name' FROM medical_stock WHERE type = 'P' order by item_guid asc";
            rsArray= DBC.executeQuery(ArraySQL);
            rsArray.last();
            String[] medicineArray = new String[(rsArray.getRow()+1)];
            rsArray.beforeFirst();
            medicineArray[0]="";
            int pathArray=1;
            while(rsArray.next()){
                medicineArray[pathArray]=rsArray.getString("name");
                pathArray++;
            }
            new CompleterComboBox(medicineArray);
            m_Cobww = new CompleterComboBox(medicineArray);
            m_Cobww.setBounds(85, 10, 170, 25);
            m_Cobww.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent evt) {
                           setAddEnable();
                   }
                });
            this.pan_PurListRight.add(m_Cobww);
            m_Cobww.setSelectedIndex(0);
       
        }catch(SQLException e) {
            ErrorMessage.setData("MedicineStock", "Frm_MedicineStockPurchase" ,"showCombo()",
                e.toString().substring(e.toString().lastIndexOf(".")+1, e.toString().length()));
        } finally {
            try {DBC.closeConnection(rsArray);}
            catch (SQLException e){
                ErrorMessage.setData("MedicineStock", "Frm_MedicineStockPurchase" ,"showCombo() - DBC.closeConnection",
                e.toString().substring(e.toString().lastIndexOf(".")+1, e.toString().length()));
            }
        }
    }
    // 加入鍵是否可按
    public void setAddEnable() {
        /*if (txt_Price.isVisible()) {
            if (txt_Quantity.getText() != null && txt_Company.getText() != null && txt_Price.getText() != null
            && !m_Cobww.getSelectedItem().equals(null) && !txt_Company.getText().trim().equals("")
            && !txt_Quantity.getText().trim().equals("") && !m_Cobww.getSelectedItem().equals("") && !txt_Price.getText().trim().equals("")
            && isNumber(txt_Quantity.getText().toString()) && isNumber(txt_Price.getText().toString())) {
                btn_Add.setEnabled(true);
            } else {
                btn_Add.setEnabled(false);
            }
        } else {
            if (txt_Quantity.getText() != null && txt_Company.getText() != null
            && !m_Cobww.getSelectedItem().equals(null) && !txt_Company.getText().trim().equals("")
            && !txt_Quantity.getText().trim().equals("") && !m_Cobww.getSelectedItem().equals("")
            && isNumber(txt_Quantity.getText().toString())) {
                btn_Add.setEnabled(true);
            } else {
                btn_Add.setEnabled(false);
            }
        }*/
        
    }

    // 隱藏成本
    //public void setHidePrice() {
        //txt_Price.setVisible(false);
        //lab_Price.setVisible(false);
    //    setHideColumn(tab_Dispose, 4);
    //}

    // 隱藏欄位
    public void setHideColumn(javax.swing.JTable table,int index){
        TableColumn tc= table.getColumnModel().getColumn(index);
        tc.setMaxWidth(0); tc.setPreferredWidth(0); tc.setMinWidth(0);
        tc.setWidth(0); table.getTableHeader().getColumnModel().getColumn(index).setMaxWidth(0);
        table.getTableHeader().getColumnModel().getColumn(index).setMinWidth(0);
    }

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        span_Purchase = new javax.swing.JScrollPane();
        tab_Dispose = new javax.swing.JTable();
        pan_PurListRight = new javax.swing.JPanel();
        lab_MedicineCode = new javax.swing.JLabel();
        lab_Quantity = new javax.swing.JLabel();
        //lab_Company = new javax.swing.JLabel();
        lab_Date = new javax.swing.JLabel();
        txt_Quantity = new javax.swing.JTextField();
        //txt_Company = new javax.swing.JTextField();
        btn_Add = new javax.swing.JButton();
        date_Choose = new cc.johnwu.date.DateChooser();
        //lab_Price = new javax.swing.JLabel();
        //txt_Price = new javax.swing.JTextField();
        btn_Delete = new javax.swing.JButton();
        btn_Send = new javax.swing.JButton();
        btn_Close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        //setTitle("Medicine Purchase");
        //setAlwaysOnTop(true);

        tab_Dispose.setModel(new javax.swing.table.DefaultTableModel(
        		new Object [][] {

                },
                new String [] {
                    paragraph.getString("PHARMACY"), 
                    //paragraph.getString("VENDOR"), 
                    paragraph.getString("QUANTITY"), 
                    paragraph.getString("DATE")//, 
                    //paragraph.getString("TOTALCOST")
                }
            ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tab_Dispose.getTableHeader().setReorderingAllowed(false);
        tab_Dispose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_PurchaseMouseClicked(evt);
            }
        });
        span_Purchase.setViewportView(tab_Dispose);
        //tab_Dispose.getColumnModel().getColumn(0).setResizable(false);
        //tab_Dispose.getColumnModel().getColumn(1).setResizable(false);
        //tab_Dispose.getColumnModel().getColumn(2).setResizable(false);
        //tab_Dispose.getColumnModel().getColumn(3).setResizable(false);
        //tab_Dispose.getColumnModel().getColumn(4).setResizable(false);

        txt_Quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_QuantityKeyReleased(evt);
            }
        });

        //txt_Company.addKeyListener(new java.awt.event.KeyAdapter() {
        //    public void keyReleased(java.awt.event.KeyEvent evt) {
        //        txt_CompanyKeyReleased(evt);
        //    }
        //});

        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });

        //txt_Price.addKeyListener(new java.awt.event.KeyAdapter() {
        //    public void keyReleased(java.awt.event.KeyEvent evt) {
        //        txt_PriceKeyReleased(evt);
        //    }
        //});

        btn_Delete.setEnabled(false);
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pan_PurListRightLayout = new javax.swing.GroupLayout(pan_PurListRight);
        pan_PurListRight.setLayout(pan_PurListRightLayout);
        pan_PurListRightLayout.setHorizontalGroup(
            pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_PurListRightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                		.addComponent(lab_MedicineCode)
                		//.addComponent(lab_MedicineName)
                		
                    	.addGroup(pan_PurListRightLayout.createSequentialGroup()
                            .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lab_MedicineCode)
                                //.addComponent(lab_MedicineName)
                                )
                            .addGap(18, 18, 18)
                        )
                		
                    .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        
                    	.addGroup(pan_PurListRightLayout.createSequentialGroup()
                            .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lab_Quantity)
                                //.addComponent(lab_Price)
                                )
                            .addGap(18, 18, 18)
                            .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                //.addComponent(txt_Price, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                .addComponent(txt_Quantity))
                        )
                                
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_PurListRightLayout.createSequentialGroup()
                            .addComponent(btn_Delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_Add, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pan_PurListRightLayout.createSequentialGroup()
                            .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                //.addComponent(lab_Company)
                                .addComponent(lab_Date))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(date_Choose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                //.addComponent(txt_Company, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                ))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pan_PurListRightLayout.setVerticalGroup(
            pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            	.addGroup(pan_PurListRightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(lab_MedicineCode)
                )
                .addGap(18, 18, 18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                //.addComponent(lab_MedicineName)
                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lab_Date)
                    .addComponent(date_Choose, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    //.addComponent(lab_Company)
                    //.addComponent(txt_Company, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab_Quantity))
                .addGap(18, 18, 18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    //.addComponent(txt_Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    //.addComponent(lab_Price)
                    )
                .addGap(12, 12, 12)
                .addGroup(pan_PurListRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Add)
                    .addComponent(btn_Delete)))
        );

        btn_Send.setText(paragraph.getString("DISPOSE"));
        btn_Send.setEnabled(false);
        btn_Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SendActionPerformed(evt);
            }
        });

        btn_Close.setText("Close");
        btn_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(span_Purchase, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Send, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Close, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                    .addComponent(pan_PurListRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(span_Purchase, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pan_PurListRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Close)
                            .addComponent(btn_Send))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed

    	 ((DefaultTableModel)this.tab_Dispose.getModel()).addRow(new Object[] {
            m_Cobww.getSelectedItem(),
            //txt_Company.getText(),
            txt_Quantity.getText(),
            date_Choose.getValue()//,
            //txt_Price.getText()
            }) ;
		tab_Dispose.setRowSelectionInterval(tab_Dispose.getRowCount()-1, tab_Dispose.getRowCount()-1);
		tab_Dispose.changeSelection(tab_Dispose.getSelectedRow(), 0, false, false);
		m_Cobww.setSelectedIndex(0);
		txt_Quantity.setText(null);
		//txt_Company.setText(null);
		//txt_Price.setText(null);
		if (tab_Dispose.getRowCount() != 0) {
		    btn_Send.setEnabled(true);
		}
}//GEN-LAST:event_btn_AddActionPerformed

    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
    	m_MaterialStockInfo.reSetEnable();
    	m_MaterialStockInfo.showMedTabList();
        this.dispose();
     
}//GEN-LAST:event_btn_CloseActionPerformed

    private void btn_SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SendActionPerformed
        //String sql = null;
        if(this.tab_Dispose.getRowCount() != 0){
           try {
                for(int i = 0; i < tab_Dispose.getRowCount(); i++){
                	
                	String code = tab_Dispose.getValueAt(i, 0).toString();
                	float purchasedAmount = Float.parseFloat(tab_Dispose.getValueAt(i, 1).toString());
                	//String vendor = tab_Dispose.getValueAt(i, 1).toString();
                	String date = tab_Dispose.getValueAt(i, 2).toString();
                	//float cost = Float.parseFloat(tab_Dispose.getValueAt(i, 4).toString()) / purchasedAmount; 
                	
                	String sql;
                	String sqlChangeRecord;
                	
                	ResultSet rsStockSQL= null;
                    //try{
                	String stockSQL = "SELECT * FROM medical_stock where type = 'P' AND item_guid = '" + code + "'";
                	rsStockSQL= DBC.executeQuery(stockSQL);
                    if(rsStockSQL.next()) {
                    	String guid = rsStockSQL.getString("guid");
                    	float currentAmount = rsStockSQL.getFloat("current_amount");
	                    float newCurrentAmount = currentAmount - purchasedAmount;
	                    if(newCurrentAmount < 0) {
	                    	JOptionPane.showMessageDialog(null, paragraph.getString("CANNOTDISPOSE") + " " + code);
	                    	continue;
	                    }
	                    sql = "UPDATE medical_stock SET `current_amount`='" + newCurrentAmount + "' WHERE guid = '" + guid + "'";
                    
	                    sqlChangeRecord = "INSERT INTO medical_stock_change_record (`guid`, `action`, `item_guid`, `type`, "
	                    		+ "`diff_amount`, `s_no`, `purchase_date` ) "
	                    		+ " VALUES (uuid(), 'D', '" + code + "', 'P', '" + purchasedAmount + "', '" + 
	                    		UserInfo.getUserNO() + "', '" + date + "');";
	                    try {DBC.closeConnection(rsStockSQL);}
	                    catch (SQLException e){
	                        ErrorMessage.setData("MedicineStock", "Frm_MedicineStockPurchase" ,"btn_SendActionPerformed() - DBC.closeConnection",
	                        		e.toString().substring(e.toString().lastIndexOf(".")+1, e.toString().length()));
                    }
                    
                    DBC.executeUpdate(sql);
                    DBC.executeUpdate(sqlChangeRecord);
                    }
                	/*
                    if (txt_Price.isVisible()) {
                        sql = "INSERT INTO medicine_stock VALUES " +
                            "(uuid(), null,'" +
                            tab_Purchase.getValueAt(i, 0) +"' ,'" +
                            tab_Purchase.getValueAt(i, 3)+"', '" +
                            tab_Purchase.getValueAt(i, 1)+"', " +
                            tab_Purchase.getValueAt(i, 4)+ 
                            " , null, null, null, null,'" +
                            tab_Purchase.getValueAt(i, 2) +
                            "',null,null,null,1)";
                    } else {
                        sql = "INSERT INTO medicine_stock VALUES " +
                            "(uuid(), null,'" +
                            tab_Purchase.getValueAt(i, 0) +"' ,'" +
                            tab_Purchase.getValueAt(i, 3)+"', '" +
                            tab_Purchase.getValueAt(i, 1)+"', " +
                            "1 "+
                            " , null, null, null, null,'" +
                            tab_Purchase.getValueAt(i, 2) +
                            "',null,null,null,1)";
                    }
                    DBC.executeUpdate(sql);
                    */
                }
               
                JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));
                btn_CloseActionPerformed(null);
            } catch (SQLException e) {
                ErrorMessage.setData("MedicineStock", "Frm_MedicineStockPurchase" ,"btn_SendActionPerformed()",
                e.toString().substring(e.toString().lastIndexOf(".")+1, e.toString().length()));
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btn_SendActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
    	if(this.tab_Dispose.getSelectedRow() == -1) return;
        ((DefaultTableModel)this.tab_Dispose.getModel()).removeRow(this.tab_Dispose.getSelectedRow());
        if (tab_Dispose.getRowCount() == 0) {
            btn_Send.setEnabled(false);
        }
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void tab_PurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_PurchaseMouseClicked
        btn_Delete.setEnabled(true);
    }//GEN-LAST:event_tab_PurchaseMouseClicked

    //private void txt_CompanyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_CompanyKeyReleased
    //    setAddEnable();
    //}//GEN-LAST:event_txt_CompanyKeyReleased

    private void txt_QuantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_QuantityKeyReleased
        setAddEnable();
    }//GEN-LAST:event_txt_QuantityKeyReleased

    //private void txt_PriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PriceKeyReleased
    //    setAddEnable();
    //}//GEN-LAST:event_txt_PriceKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Close;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Send;
    private cc.johnwu.date.DateChooser date_Choose;
    //private javax.swing.JLabel lab_Company;
    private javax.swing.JLabel lab_Date;
    private javax.swing.JLabel lab_MedicineCode;
    //private javax.swing.JLabel lab_Price;
    private javax.swing.JLabel lab_Quantity;
    private javax.swing.JPanel pan_PurListRight;
    private javax.swing.JScrollPane span_Purchase;
    private javax.swing.JTable tab_Dispose;
    //private javax.swing.JTextField txt_Company;
    //private javax.swing.JTextField txt_Price;
    private javax.swing.JTextField txt_Quantity;
    // End of variables declaration//GEN-END:variables

    public void onDateChanged() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
