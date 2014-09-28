
package cashier;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import common.Constant;
/**
 *
 * @author Steven
 */
public class Frm_CashierHistory extends javax.swing.JFrame {
    private String m_Pno;
    private javax.swing.JFrame m_Frm;
    Object[][] regArray = null;
    /** Creates new form Frm_CashierHistory */
    public Frm_CashierHistory(javax.swing.JFrame frm, String pno) {
        m_Frm = frm;
        m_Pno = pno;
        initComponents();
        this.setLocationRelativeTo(this);
        this.tab_Payment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     // tabble不可按住多選
        addWindowListener(new WindowAdapter() {  // 畫面關閉原視窗enable
            @Override
            public void windowClosing(WindowEvent windowevent) {
               jButton1ActionPerformed(null);
            }
        });
        initTab();
    }

    private void initTab() {
        try {
            txt_PaidAmount.setText("");
            txt_Arrears.setText("");
            DefaultTableModel tabModel = null;
            ResultSet rs = null;
            String sql = null;
            String sqlLab = null;
            String sqlXray = null;
            //Object[][] dataArray = null;
            Vector<Vector> rowData = new Vector<Vector>();
            double total = 0;
            Integer gCount = 0;
            Vector<String> title = new Vector<String>();
            title.addElement("reg_guid");
            title.addElement("guid");
            title.addElement("");
            title.addElement("Payment Time");
            title.addElement("code");
            title.addElement("Type");
            title.addElement("Amount Receivable");
            title.addElement("Paid Amount");
            title.addElement("Arrears");
            
            if (cbox_Status.getSelectedIndex() == 0) {
                //sql +=  "AND cashier.arrears <> 0 ORDER BY paytime DESC";
                btn_Complete.setEnabled(true);
            
	            sql = "SELECT registration_info.guid as 'reg_guid', registration_info.reg_time, "
	            		+ " registration_info.reg_cost, registration_info.registration_payment, "
	            		+ " registration_info.bed_payment, registration_info.pharmacy_payment, "
	            		+ " registration_info.lab_payment, registration_info.radiology_payment "
	            		+ " FROM registration_info WHERE "
	            		+ " (registration_payment IS NULL OR registration_payment IS NULL "
	            		+ " OR pharmacy_payment is NULL OR lab_payment is NULL "
	            		+ " OR radiology_payment is NULL) "
	            		+ " AND registration_info.p_no = '" + m_Pno + "' "
	            		+ " AND reg_time < CURDATE() "
	            		+ " ORDER BY registration_info.reg_time ASC";
	            //System.out.println("1 " + sql);
	            rs = DBC.executeQuery(sql);
	            rs.last();
	            regArray = new Object[rs.getRow()][8];
	            rs.beforeFirst();
	            int i = 0;
	            while (rs.next()) {
	            	regArray[i][0] = rs.getString("reg_guid");
	            	regArray[i][1] = rs.getString("reg_time");
	            	regArray[i][2] = rs.getString("reg_cost");
	            	regArray[i][3] = rs.getString("registration_payment");
	            	regArray[i][4] = rs.getString("bed_payment");
	            	regArray[i][5] = rs.getString("pharmacy_payment");
	            	regArray[i][6] = rs.getString("lab_payment");
	            	regArray[i][7] = rs.getString("radiology_payment");
	                i++;
	            }
	            
	            Vector<String> rowItem = null;
	            for(i = 0; i < regArray.length; i++) {
	            	String reg_guid = (String) regArray[i][0];
	            	Float reg_cost = Float.parseFloat((String) (regArray[i][2]));
	            	if(reg_cost.compareTo((float) 0.0) != 0) {
	            		gCount++;
	            		rowItem = new Vector<String>();
	                	rowItem.addElement(reg_guid);
	                	rowItem.addElement("");
	                	rowItem.addElement(gCount.toString());
	                	rowItem.addElement("");
	                	rowItem.addElement("");
	                	rowItem.addElement("Registration");
	                	rowItem.addElement((String) regArray[i][2]);
	                	rowItem.addElement("0");
	                	rowItem.addElement((String) regArray[i][2]);
	                	
	                	rowData.addElement(rowItem);
	                	total += Float.parseFloat((String) regArray[i][2]);
	            	}
	            	
	            	sqlLab = "SELECT registration_info.guid as 'reg_guid', "
	            			+ " prescription.guid as 'guid', "
	            			+ " '' as 'Payment Time', prescription.code AS 'Code', "
	            			+ " 'Laboratory' as 'Type', prescription.cost as 'Amount Receivable', "
	            			+ " '0' as 'Paid Amount', prescription.cost as 'Arrears' "
	            			+ " FROM prescription, registration_info, prescription_code "
	            			+ " WHERE registration_info.guid = '" + reg_guid + "' "
	            			+ " AND prescription.reg_guid = registration_info.guid "
	            			+ " AND prescription_code.code = prescription.code "
	            			+ " AND prescription_code.type <> '" + Constant.X_RAY_CODE + "' "
	            			+ " AND prescription.cost > 0 ORDER BY registration_info.reg_time ASC";
	            	//System.out.println("2 " + sqlLab);
	            	rs = DBC.executeQuery(sqlLab);
	                while (rs.next()) {
	                	gCount++;
	                	rowItem = new Vector<String>();
	                	rowItem.addElement(rs.getString("reg_guid"));
	                	rowItem.addElement(rs.getString("guid"));
	                	rowItem.addElement(gCount.toString());
	                	rowItem.addElement(rs.getString("Payment Time"));
	                	rowItem.addElement(rs.getString("Code"));
	                	rowItem.addElement(rs.getString("Type"));
	                	rowItem.addElement(rs.getString("Amount Receivable"));
	                	rowItem.addElement(rs.getString("Paid Amount"));
	                	rowItem.addElement(rs.getString("Arrears"));
	                	
	                	rowData.addElement(rowItem);
	                	total += Float.parseFloat(rs.getString("Arrears"));
	                }
	                
	                sqlXray = "SELECT registration_info.guid as 'reg_guid', "
	                		+ " prescription.guid as 'guid', "
	            			+ " '' as 'Payment Time', prescription.code AS 'Code', "
	            			+ " 'Radiology' as 'Type', prescription.cost as 'Amount Receivable', "
	            			+ " '0' as 'Paid Amount', prescription.cost as 'Arrears' "
	            			+ " FROM prescription, registration_info, prescription_code "
	            			+ " WHERE registration_info.guid = '" + reg_guid + "' "
	            			+ " AND prescription.reg_guid = registration_info.guid "
	            			+ " AND prescription_code.code = prescription.code "
	            			+ " AND prescription_code.type = '" + Constant.X_RAY_CODE + "' "
	            			+ " AND prescription.cost > 0 ORDER BY registration_info.reg_time ASC";
	                //System.out.println("22 " + sqlXray);
	                rs = DBC.executeQuery(sqlXray);
	                while (rs.next()) {
	                	gCount++;
	                	rowItem = new Vector<String>();
	                	rowItem.addElement(rs.getString("reg_guid"));
	                	rowItem.addElement(rs.getString("guid"));
	                	rowItem.addElement(gCount.toString());
	                	rowItem.addElement(rs.getString("Payment Time"));
	                	rowItem.addElement(rs.getString("Code"));
	                	rowItem.addElement(rs.getString("Type"));
	                	rowItem.addElement(rs.getString("Amount Receivable"));
	                	rowItem.addElement(rs.getString("Paid Amount"));
	                	rowItem.addElement(rs.getString("Arrears"));
	                	
	                	rowData.addElement(rowItem);
	                	total += Float.parseFloat(rs.getString("Arrears"));
	                }
	                
	            }
            }
            
            /*
            if (cbox_Status.getSelectedIndex() == 0) {
                sql +=  "AND cashier.arrears <> 0 ORDER BY paytime DESC";
                btn_Complete.setEnabled(true);
            } else {
                sql +=  "AND cashier.arrears = 0 ORDER BY paytime DESC";
                txt_AmountReceivable.setText("");
                txt_Arrears.setText("");

                btn_Complete.setEnabled(false);
            }*/

            tabModel = new DefaultTableModel(rowData, title) {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return false;
                }
            };
            if (cbox_Status.getSelectedIndex() == 0) txt_AmountReceivable.setText(String.valueOf(total));
            
            //tabModel.setDataVector(rowData, title);
            tab_Payment.setModel(tabModel);
            tab_Payment.setRowHeight(30);
            common.TabTools.setHideColumn(tab_Payment, 0);
            common.TabTools.setHideColumn(tab_Payment, 1);
            common.TabTools.setHideColumn(tab_Payment, 3);
            //common.TabTools.setHideColumn(tab_Payment, tab_Payment.getColumnCount()-2);
        } catch (SQLException ex) {
            Logger.getLogger(Frm_CashierHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

    private void savePayment() {
    	if (txt_PaidAmount.getText().trim().equals("") || txt_AmountReceivable.getText().trim().equals("")) {
    		JOptionPane.showMessageDialog(null, "Save error");
    		return;
    	}
    	
    	try {
    		Double paidMoney = Double.parseDouble(txt_PaidAmount.getText().trim());
	    	//Double totalCost = Double.parseDouble(txt_AmountReceivable.getText().trim());
	    	Double remainingMoney = paidMoney;
	    	Double costOfItem = 0.0;
	    	Double arrearOfItem = 0.0;
	    	String sql = "";
	    	String sqlStr = "";
	    	String paymentType = "";
	    	
	    	for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
	    		if (common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())) {
	    			costOfItem = Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
	    			if(costOfItem.compareTo(0.0) == 0) continue;
	    			String reg_guid = tab_Payment.getValueAt(i, 0).toString();
	    			String guid = tab_Payment.getValueAt(i, 1).toString();
	    			if(remainingMoney >= costOfItem ) {
                    	remainingMoney -= costOfItem;
                    	arrearOfItem = 0.0;
                    } else {
                    	arrearOfItem = costOfItem - remainingMoney;
                    	remainingMoney = 0.0;
                    }
	    			
	    			String itemType = tab_Payment.getValueAt(i, 5).toString();
	    			if (itemType.equals("Pharmacy")) {
	    	            paymentType = "P";
	    	            sqlStr = "PH";
	    	            //sql = "UPDATE registration_info SET payment = '"+finish+"'";
	    	        } else if (itemType.equals("Laboratory")) {
	    	            paymentType = "L";
	    	            sqlStr = "LA";
	    	            //sql = "UPDATE registration_info SET lab_payment = '"+finish+"'";
	    	        } else if (itemType.equals("Registration")) {
	    	            paymentType = "R";
	    	            sqlStr = "RE";
	    	            //sql = "UPDATE registration_info SET registration_payment = '"+finish+"'";
	    	        }else if (itemType.equals("Radiology")) {
	    	            sqlStr = "XR";
	    	            paymentType = "X";
	    	            //sql = "UPDATE registration_info SET radiology_payment = '"+finish+"'";
	    	        }
	    			
                	sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                            "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+sqlStr+"00000001' ELSE "+
                            " INSERT (MAX(`no`), "+
                            "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                            "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                            "SUBSTRING(MAX(`no`),3)+1) END  "+
                            ",'"+reg_guid+"', '"+m_Pno+"', '"+paymentType+"', " +
                            "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                            ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                            "FROM cashier WHERE no LIKE '"+sqlStr+"%'  " ;
                	//System.out.println(sql);
                	//System.out.println("3 " + sql);
                	DBC.executeUpdate(sql);
	    			
                	if (itemType.equals("Pharmacy")) {
                		//sql = "UPDATE prescription SET cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
	    	        } else if (itemType.equals("Laboratory")) {
	    	        	sql = "UPDATE prescription SET cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
	    	        } else if (itemType.equals("Registration")) {
	    	        	sql = "UPDATE registration_info SET reg_cost = " + arrearOfItem + " WHERE guid = '" + reg_guid + "'";
	    	        }else if (itemType.equals("Radiology")) {
	    	        	sql = "UPDATE prescription SET cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
	    	        }
                	//System.out.println("4 " + sql);
                	DBC.executeUpdate(sql);
                	
                	Double paidOfItem = costOfItem - arrearOfItem;
                	tab_Payment.setValueAt( arrearOfItem.toString(), i, tab_Payment.getColumnCount()-1);
                	tab_Payment.setValueAt( paidOfItem.toString(), i, tab_Payment.getColumnCount()-2);
                	
                	//System.out.println("remainingMoney = " + remainingMoney);
                	if(remainingMoney.compareTo(0.0) == 0) break;
	    		}
	    	}
	    		
    		for(int i = 0; i < regArray.length; i++) {
    			Boolean labFullPaid = true;
    			Boolean regFullPaid = true;
    			Boolean xrayFullPaid = true;
	    		for (int j = 0; j < tab_Payment.getRowCount() ; j++) {
	    			String reg_guid = (String) tab_Payment.getValueAt(j, 0);
	    			Float arrear = Float.parseFloat(tab_Payment.getValueAt(j, tab_Payment.getColumnCount()-1).toString());
	    			String type = (String) tab_Payment.getValueAt(j, 5);
	    			if (reg_guid.compareTo(regArray[i][0].toString()) == 0 && arrear.compareTo((float) 0.0) != 0) {
	    				//System.out.println("arrear = " + arrear);
	    				//System.out.println("type = " + type);
	    				if (type.equals("Pharmacy")) {
		    	        } else if (type.equals("Laboratory")) {
		    	        	labFullPaid = false;
		    	        } else if (type.equals("Registration")) {
		    	        	regFullPaid = false;
		    	        }else if (type.equals("Radiology")) {
		    	        	xrayFullPaid = false;
		    	        }
	    			}
		    	}
	    		sql = "UPDATE registration_info SET lab_payment = "
	    				+ (labFullPaid == true ? "'F'" : "null")
                		//+ ", reg_cost = 0.0 "
	    				+ ", registration_payment = " + (regFullPaid == true ? "'F'" : "null")
	    				+ ", radiology_payment = " + (xrayFullPaid == true ? "'F'" : "null")
                    	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                        + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
                        + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                        + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
                        + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + regArray[i][0].toString() + "'";
                //System.out.println("5 " + sql);
	    		DBC.executeUpdate(sql);
    		}
	    	
	        JOptionPane.showMessageDialog(null, "Saved successfully.");
        
    	} catch (Exception ex) {
            Logger.getLogger(Frm_CashierHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private void setFinish(String finish) {
        // 判斷資料不是數值先把格子清空
        boolean IsCanFinish = true;



        if (!txt_PaidAmount.getText().trim().equals("") && !txt_AmountReceivable.getText().trim().equals("") && common.Tools.isNumber(txt_PaidAmount.getText().trim()) && common.Tools.isNumber(txt_AmountReceivable.getText().trim())) {
            txt_AmountReceivable.setText(String.valueOf(Double.parseDouble(txt_AmountReceivable.getText().trim()) - Double.parseDouble(txt_PaidAmount.getText().trim())));
        } else {
            txt_AmountReceivable.setText("");
            IsCanFinish = false;
        }

        if (!IsCanFinish) {
            JOptionPane.showMessageDialog(null, "Cost does not enter the complete.");
        } else {
            try {
                String sql = null;
                String paymentType = null;

                // 儲存付費記錄
                for (int i = 0; i < tab_Payment.getRowCount(); i++) {
                    sql = "UPDATE cashier SET backin = 'Y', backin_time = NOW(), backin_sno = '"+UserInfo.getUserNO()+"' WHERE no = '"+tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-2)+"'";
                    DBC.executeUpdate(sql);
                }
             } catch (SQLException ex) {
                Logger.getLogger(Frm_CashierInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Saved successfully.");
            initTab();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tab_Payment = new javax.swing.JTable();
        cbox_Status = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txt_AmountReceivable = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_Arrears = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_Complete = new javax.swing.JButton();
        txt_PaidAmount = new javax.swing.JTextField();
        mnb = new javax.swing.JMenuBar();
        menu_File = new javax.swing.JMenu();
        mnit_Back = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cashier Record");

        tab_Payment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "2010/10/09", "30", "20", "10"},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No.", "Date", "Payment", "Actual payment", "Actual"
            }
        ));
        jScrollPane1.setViewportView(tab_Payment);

        cbox_Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Arrears", "All" }));
        cbox_Status.setVisible(false);
        cbox_Status.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbox_StatusItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel1.setText("Total Arrears:");

        txt_AmountReceivable.setEditable(false);
        txt_AmountReceivable.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel2.setText("-");

        jLabel3.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel3.setText("Paid Amount:");

        jLabel5.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel5.setText("Arrears:");

        txt_Arrears.setEditable(false);
        txt_Arrears.setFont(new java.awt.Font("新細明體", 1, 18));
        txt_Arrears.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel4.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel4.setText("=");

        btn_Complete.setText("Save payment");
        btn_Complete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CompleteActionPerformed(evt);
            }
        });

        txt_PaidAmount.setFont(new java.awt.Font("新細明體", 1, 18));
        txt_PaidAmount.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_PaidAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PaidAmountActionPerformed(evt);
            }
        });
        txt_PaidAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_PaidAmountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PaidAmountKeyReleased(evt);
            }
        });

        menu_File.setText("File");

        mnit_Back.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        mnit_Back.setText("Close");
        mnit_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnit_BackActionPerformed(evt);
            }
        });
        menu_File.add(mnit_Back);

        mnb.add(menu_File);

        setJMenuBar(mnb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                    .addComponent(cbox_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_Complete, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_AmountReceivable, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_PaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Arrears, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbox_Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_AmountReceivable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txt_PaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txt_Arrears, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btn_Complete))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnit_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnit_BackActionPerformed

    }//GEN-LAST:event_mnit_BackActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        m_Frm.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_CompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CompleteActionPerformed
        //if (txt_Arrears.getText().equals("0.0")) setFinish("F");
    	savePayment();
    	initTab();
    }//GEN-LAST:event_btn_CompleteActionPerformed

    private void txt_PaidAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PaidAmountActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txt_PaidAmountActionPerformed

    private void txt_PaidAmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PaidAmountKeyPressed

}//GEN-LAST:event_txt_PaidAmountKeyPressed

    private void txt_PaidAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PaidAmountKeyReleased
        if (!txt_PaidAmount.getText().trim().equals("") && !txt_AmountReceivable.getText().trim().equals("")
                && common.Tools.isNumber(txt_PaidAmount.getText().trim()) && common.Tools.isNumber(txt_AmountReceivable.getText().trim()) &&
                Double.parseDouble(txt_AmountReceivable.getText().trim()) >= Double.parseDouble(txt_PaidAmount.getText().trim())) {
            txt_Arrears.setText(String.valueOf(Double.parseDouble(txt_AmountReceivable.getText().trim()) - Double.parseDouble(txt_PaidAmount.getText().trim())));
        } else {
            txt_Arrears.setText("");
        }
}//GEN-LAST:event_txt_PaidAmountKeyReleased

    private void cbox_StatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbox_StatusItemStateChanged
         if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) initTab();
    }//GEN-LAST:event_cbox_StatusItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Complete;
    private javax.swing.JComboBox cbox_Status;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenu menu_File;
    private javax.swing.JMenuBar mnb;
    private javax.swing.JMenuItem mnit_Back;
    private javax.swing.JTable tab_Payment;
    private javax.swing.JTextField txt_AmountReceivable;
    private javax.swing.JTextField txt_Arrears;
    private javax.swing.JTextField txt_PaidAmount;
    // End of variables declaration//GEN-END:variables

}
