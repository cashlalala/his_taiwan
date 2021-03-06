
/*
 * Frm_Cashier.java
 *
 * Created on 2010/10/6, 下午 08:37:02
 */

package cashier;

import cc.johnwu.login.UserInfo;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ListSelectionModel;

import main.Frm_Main;
import multilingual.Language;
import common.Constant;

/**
 *
 * @author Steven
 */
public class Frm_CashierList extends javax.swing.JFrame {


    private RefrashCashier m_RefrashCashier;
    private Thread m_Clock;
    private String m_SysName ;  // 系統名稱
    
    private Language paragraph = Language.getInstance();;
    //private boolean m_IsStop = false;
    //private String m_RegGuid;
    //private String m_Pno;
    public Frm_CashierList(String sysname) {
    	if(sysname != null)
    		m_SysName = sysname;
    	
        initComponents();
        
        if(sysname == "bed") {
        	cbox_System.setVisible(false);
        	setTitle("Bed Cashier");
        	reFreshCashier();
        }

        this.setExtendedState(Frm_CashierList.MAXIMIZED_BOTH);  // 最大化
        this.setLocationRelativeTo(this);//視窗顯示至中
        this.tab_Cashier.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     // tabble不可按住多選
        addWindowListener(new WindowAdapter() {                                // 畫面關閉原視窗enable
            @Override
            public void windowClosing(WindowEvent windowevent) {
                jButton2ActionPerformed(null);
            }
        });

        this.m_Clock = new Thread(){ // Clock
            @Override
            @SuppressWarnings("static-access")
            public void run(){
            try{
                while(true){
                    lab_SystemTime.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
                     this.sleep(500);
                    }}catch(InterruptedException e) {
                }
            }
        };
        this.m_Clock.start();
        this.txt_Name.setText(UserInfo.getUserName());
        
    	//this();
    	
    }
    public Frm_CashierList() {
    	this(null);
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbox_System = new javax.swing.JComboBox();
        txt_Name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lab_Wait = new javax.swing.JLabel();
        lab_WaitCount = new javax.swing.JLabel();
        lab_SystemTime = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tab_Cashier = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_Enter = new javax.swing.JButton();
        btn_History = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        mnb = new javax.swing.JMenuBar();
        mn_Fiele = new javax.swing.JMenu();
        mnit_Enter = new javax.swing.JMenuItem();
        mnit_Close = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cashier");

        //jLabel1.setFont(new java.awt.Font("新細明體", 1, 14));
        jLabel1.setText("System:");

        //cbox_System.setFont(new java.awt.Font("新細明體", 1, 14)); // NOI18N
        cbox_System.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Registration", "Laboratory", "Radiology(X-RAY)", "Pharmacy" }));
        cbox_System.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbox_SystemItemStateChanged(evt);
            }
        });

        txt_Name.setEditable(false);

        jLabel2.setFont(new java.awt.Font("新細明體", 1, 14));
        jLabel2.setText("User:");

        lab_Wait.setFont(new java.awt.Font("新細明體", 1, 14));
        lab_Wait.setText("Await:");

        lab_WaitCount.setText("-----");

        lab_SystemTime.setFont(new java.awt.Font("UnDotum", 0, 18));
        lab_SystemTime.setText("-----");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lab_Wait)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbox_System, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lab_SystemTime, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lab_WaitCount, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(cbox_System, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab_SystemTime))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_Wait)
                    .addComponent(lab_WaitCount))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tab_Cashier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tab_Cashier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_CashierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tab_Cashier);

        btn_History.setText("Cashier Arrears");
        btn_History.setEnabled(false);
        btn_History.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HistoryActionPerformed(evt);
            }
        });
        
        btn_Enter.setText("Enter");
        btn_Enter.setEnabled(false);
        btn_Enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EnterActionPerformed(evt);
            }
        });

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //jButton2ActionPerformed(evt);
            	mnit_CloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(btn_History, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(btn_Enter, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Enter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_History)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(405, Short.MAX_VALUE))
        );

        mn_Fiele.setText("File");

        mnit_Enter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, 0));
        mnit_Enter.setText("Enter");
        mnit_Enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnit_EnterActionPerformed(evt);
            }
        });
        mn_Fiele.add(mnit_Enter);

        mnit_Close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        mnit_Close.setText("Close");
        mnit_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnit_CloseActionPerformed(evt);
            }
        });
        mn_Fiele.add(mnit_Close);

        mnb.add(mn_Fiele);

        setJMenuBar(mnb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reFreshCashier() {
    	if(m_RefrashCashier != null) {
    		m_RefrashCashier.interrupt();  // 終止重複讀取掛號表單
            m_Clock.interrupt();
    	}
    	System.out.println(m_SysName);
        m_RefrashCashier = new RefrashCashier(this.tab_Cashier, Constant.REFRASHTIME, m_SysName, lab_WaitCount);
        m_RefrashCashier.start();
    }
    private void switchSystem() {
    	switch(cbox_System.getSelectedIndex()) {
	        case 1:
	            m_SysName = "reg";
	            break;
	        case 2:
	            m_SysName = "lab";
	            break;
	        case 3:
	            m_SysName = "xray";
	            break;
	        case 4:
	            m_SysName = "pha";
	            break;
	    }
    	reFreshCashier();
    }
    
    private void cbox_SystemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbox_SystemItemStateChanged
    	if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED && cbox_System.getSelectedIndex() != 0) {
    		switchSystem();
	    }
    }//GEN-LAST:event_cbox_SystemItemStateChanged

    private void mnit_EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnit_EnterActionPerformed
//        if(btn_Enter.isEnabled()) {
//            btn_EnterActionPerformed(null);
//        }
}//GEN-LAST:event_mnit_EnterActionPerformed

    private void mnit_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnit_CloseActionPerformed
    	if(m_RefrashCashier != null) {
    		m_RefrashCashier.interrupt();  // 終止重複讀取掛號表單
            m_Clock.interrupt();
    	}
        new Frm_Main().setVisible(true);
        this.dispose();
}//GEN-LAST:event_mnit_CloseActionPerformed

    private void tab_CashierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_CashierMouseClicked
        if(this.tab_Cashier.getRowCount() > 0) {
        	this.btn_Enter.setEnabled(true);
        	this.btn_History.setEnabled(true);
        }

        if(evt.getClickCount() == 2) btn_EnterActionPerformed(null);
    }//GEN-LAST:event_tab_CashierMouseClicked

    private void btn_HistoryActionPerformed(java.awt.event.ActionEvent evt) {
    	if (tab_Cashier.getValueAt(tab_Cashier.getSelectedRow(), 0) != null) {
	    	String p_no = tab_Cashier.getValueAt(tab_Cashier.getSelectedRow(), 2).toString();
	    	this.setEnabled(false);
	    	new Frm_CashierHistory(this, p_no).setVisible(true);
    	}
    }
    
    private void btn_EnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EnterActionPerformed
        if (tab_Cashier.getValueAt(tab_Cashier.getSelectedRow(), 0) != null) {
            this.setEnabled(false);
            new Frm_CashierInfo(this,tab_Cashier.getValueAt(tab_Cashier.getSelectedRow(), 0).toString(),m_SysName ,tab_Cashier.getValueAt(tab_Cashier.getSelectedRow(), 2).toString() ).setVisible(true);
        }
    }//GEN-LAST:event_btn_EnterActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Frm_Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Enter;
    private javax.swing.JButton btn_History;
    private javax.swing.JComboBox cbox_System;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lab_SystemTime;
    private javax.swing.JLabel lab_Wait;
    private javax.swing.JLabel lab_WaitCount;
    private javax.swing.JMenu mn_Fiele;
    private javax.swing.JMenuBar mnb;
    private javax.swing.JMenuItem mnit_Close;
    private javax.swing.JMenuItem mnit_Enter;
    private javax.swing.JTable tab_Cashier;
    private javax.swing.JTextField txt_Name;
    // End of variables declaration//GEN-END:variables

}
