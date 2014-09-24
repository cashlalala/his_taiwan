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

	//private StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	private void btnMaterialActionPerformed(java.awt.event.ActionEvent evt) {
		new codemaintenance.Frm_Material().setVisible(true);
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
		setBounds(100, 100, 193, 275);

		JButton btnMaterial = new JButton("Material");
		btnMaterial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnMaterialActionPerformed(e);
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
																btnMaterial,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														//.addComponent(
														//		btnXray,
														//		GroupLayout.PREFERRED_SIZE,
														//		110,
														//		GroupLayout.PREFERRED_SIZE)
														)
										.addContainerGap(40, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(btnMaterial)
						.addPreferredGap(ComponentPlacement.RELATED)
						//.addComponent(btnDiagnosis)
						//.addPreferredGap(ComponentPlacement.UNRELATED)
						.addContainerGap(87, Short.MAX_VALUE)));
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		getContentPane().setLayout(groupLayout);
		initDataBindings();

	}

	protected void initDataBindings() {
	}

}
