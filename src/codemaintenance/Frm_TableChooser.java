package codemaintenance;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;

import multilingual.Language;
import common.PrintTools;
import cc.johnwu.date.DateMethod;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import errormessage.StoredErrorMessage;

public class Frm_TableChooser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Language paragraph = Language.getInstance();

	//private StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	private void btnMaterialActionPerformed(java.awt.event.ActionEvent evt) {
		new codemaintenance.Frm_Material().setVisible(true);
		this.dispose();
	}
	
	private void btnMedicineActionPerformed(java.awt.event.ActionEvent evt) {
		new codemaintenance.Frm_Pharmacy().setVisible(true);
		this.dispose();
	}
	
	private void btnPrescriptionActionPerformed(java.awt.event.ActionEvent evt) {
		new codemaintenance.Frm_Prescription().setVisible(true);
		this.dispose();
	}
	
	private void btnDiagnosisActionPerformed(java.awt.event.ActionEvent evt) {
		new codemaintenance.Frm_Diagnosis().setVisible(true);
		this.dispose();
	}
	
	/**
	 * Create the dialog.
	 */
	public Frm_TableChooser() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setModal(true);
		setResizable(false);
		setTitle("Choose Code Table");
		setBounds(100, 100, 225, 220);

		JButton btnMaterial = new JButton(paragraph.getString("MATERIAL"));
		JButton btnMedicine = new JButton(paragraph.getString("MEDICINE"));
		JButton btnPrescription = new JButton(paragraph.getString("PRESCRIPTION"));
		JButton btnDiagnosis = new JButton(paragraph.getString("DIAGNOSIS"));
		btnMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMaterialActionPerformed(e);
			}
		});
		btnMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMedicineActionPerformed(e);
			}
		});
		btnPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPrescriptionActionPerformed(e);
			}
		});
		btnDiagnosis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDiagnosisActionPerformed(e);
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(37)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																btnDiagnosis,
																GroupLayout.PREFERRED_SIZE,
																150,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnPrescription,
																GroupLayout.PREFERRED_SIZE,
																150,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnMedicine,
																GroupLayout.PREFERRED_SIZE,
																150,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnMaterial,
																GroupLayout.PREFERRED_SIZE,
																150,
																GroupLayout.PREFERRED_SIZE)
														)
										.addContainerGap(40, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(btnDiagnosis)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGap(18,18,18)
						.addComponent(btnPrescription)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGap(18,18,18)
						.addComponent(btnMedicine)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGap(18,18,18)
						.addComponent(btnMaterial)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addContainerGap(87, Short.MAX_VALUE)));
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		getContentPane().setLayout(groupLayout);
		initDataBindings();

	}

	protected void initDataBindings() {
	}

}
