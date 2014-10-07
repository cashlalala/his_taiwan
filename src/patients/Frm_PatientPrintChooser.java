package patients;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;

import multilingual.Language;

import org.his.model.PatientsInfo;

import barcode.PrintBarcode;

public class Frm_PatientPrintChooser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Language lang = Language.getInstance();

	private PatientsInfo patientInfo;

	/**
	 * Create the dialog.
	 */
	public Frm_PatientPrintChooser(PatientsInfo pInfo) {
		patientInfo = pInfo;

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setModal(true);
		setResizable(false);
		setTitle(lang.getString("PRINT"));
		setBounds(100, 100, 245, 198);

		JButton btnAll = new JButton(lang.getString("PRINT_DIAGNOSIS_COVER"));
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintBarcode.printDiagnosisCover(patientInfo.getPNo());
			}
		});

		JButton btnDiagnosis = new JButton(
				lang.getString("PRINT_REGSITER_CARD"));
		btnDiagnosis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintBarcode.printRegistrationCard(patientInfo.getPNo());
			}
		});

		JButton btnPrescription = new JButton(lang.getString("PRINT_LABEL"));
		btnPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PrinterJob pj = PrinterJob.getPrinterJob();
					PageFormat pf = pj.defaultPage();
					Paper paper = new Paper();
					paper.setSize(600, 800);
					paper.setImageableArea(0, 0, 600, 800);
					pf.setPaper(paper);
					pj.setPrintable(new LabelSticker(patientInfo), pf);

					pj.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}

		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDiagnosis, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
						.addComponent(btnAll, GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
						.addComponent(btnPrescription, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
					.addGap(40))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAll)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDiagnosis)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnPrescription)
					.addContainerGap(128, Short.MAX_VALUE))
		);
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		getContentPane().setLayout(groupLayout);
		initDataBindings();

	}

	protected void initDataBindings() {
	}

}
