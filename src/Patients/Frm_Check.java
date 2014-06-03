/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Frm_Check.java
 *
 * Created on 2009/9/24, 下午 07:33:31
 */

package Patients;

import multilingual.Language;

/**
 *
 * @author johnwu1114
 */
public class Frm_Check extends javax.swing.JFrame {

    private String m_PNO = "";
    private PatientsInterface m_frame;
    /*多國語言變數*/
    private Language paragraph = Language.getInstance();
    private String[] line = new String(paragraph.setlanguage("CHECK")).split("\n") ;
    private String[] message = new String(paragraph.setlanguage("MESSAGE")).split("\n") ;

    /** Creates new form Frm_Check */
    public Frm_Check(PatientsInterface m_frame, String p_no) {
        initComponents();
        initLanguage() ;
        this.setLocationRelativeTo(this);//視窗顯示至中
        this.m_PNO = p_no;
        this.m_frame = m_frame;
        this.lab_Data.setText(lab_Data.getText()+" "+p_no);
    }
    private void initLanguage() {
        this.lab_Data.setText(paragraph.getLanguage(line, "DATA"));
        this.btn_Yes.setText(paragraph.getLanguage(message, "YES"));
        this.btn_No.setText(paragraph.getLanguage(message, "NO"));
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Yes = new javax.swing.JButton();
        btn_No = new javax.swing.JButton();
        lab_Data = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btn_Yes.setText("Yes");
        btn_Yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_YesActionPerformed(evt);
            }
        });

        btn_No.setText("No");
        btn_No.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NoActionPerformed(evt);
            }
        });

        lab_Data.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lab_Data.setText("Data obtained ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lab_Data, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(btn_Yes, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_No, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lab_Data)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Yes)
                    .addComponent(btn_No))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_NoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NoActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_NoActionPerformed

    private void btn_YesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_YesActionPerformed
        new Frm_PatientMod(m_frame, m_PNO).setVisible(true);
        ((javax.swing.JFrame)m_frame).setEnabled(false);
        ((javax.swing.JFrame)m_frame).setVisible(false);
        this.dispose();
    }//GEN-LAST:event_btn_YesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_No;
    private javax.swing.JButton btn_Yes;
    private javax.swing.JLabel lab_Data;
    // End of variables declaration//GEN-END:variables

}
