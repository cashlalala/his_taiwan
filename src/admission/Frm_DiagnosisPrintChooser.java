package admission;

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

public class Frm_DiagnosisPrintChooser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	private String regGuid;
	
	private PrintTools pt = new PrintTools();

	class MyPrintable implements Printable {
		public int print(Graphics g, PageFormat pf, int pageIndex) {

			String sqlMedicines = "SELECT medicines.code, medicines.item, medicine_stock.dosage, medicines.unit, medicine_stock.usage, "
					+ "medicine_stock.way, medicine_stock.repeat_number, medicine_stock.quantity, medicine_stock.urgent, "
					+ "medicine_stock.powder, medicine_stock.ps "
					+ "FROM medicines, medicine_stock, registration_info "
					+ "WHERE registration_info.guid = '"
					+ regGuid
					+ "' "
					+ "AND medicine_stock.reg_guid = registration_info.guid "
					+ "AND medicines.code = medicine_stock.m_code";

			String sqlPatient = "SELECT registration_info.touchtime, patients_info.p_no, registration_info.pharmacy_no, "
					+ "registration_info.modify_count, concat(patients_info.firstname,'  ',patients_info.lastname) AS name, "
					+ "patients_info.gender, patients_info.birth "
					+ "FROM registration_info, patients_info "
					+ "WHERE guid = '"
					+ regGuid
					+ "' "
					+ "AND registration_info.p_no = patients_info.p_no";

			ResultSet rsMedicines = null;
			ResultSet rsReceiveMedicineNo = null;
			ResultSet rsPatient = null;
			if (pageIndex != 0)
				return NO_SUCH_PAGE;
			Graphics2D g2 = (Graphics2D) g;
			g2.setFont(new Font("Serif", Font.PLAIN, 12));
			g2.setPaint(Color.black);
			int i = 80;
			int r = 0;
			String urgent = null;
			String powder = null;
			String ps = null;
			String finishNo = null;
			// g2.drawString("文字", X, Y);
			// ********************************************************//
			try {
				rsMedicines = DBC.executeQuery(sqlMedicines);
				rsPatient = DBC.executeQuery(sqlPatient);

				rsPatient.next();
				if (rsMedicines.next()) {
					finishNo = rsPatient.getString("pharmacy_no");
				} else {
					finishNo = "--";
				}
				rsMedicines.beforeFirst();
				if (rsPatient.getInt("modify_count") != 0) {
					g2.drawString("Modify-" + rsPatient.getInt("modify_count"),
							380, i);
				}
				g2.drawString(
						rsPatient.getString("touchtime").substring(4, 14), 450,
						i);
				i += 60;
				g2.drawString("Date: " + DateMethod.getTodayYMD(), 80, i);
				g2.drawString("Department: " + UserInfo.getUserPoliclinic(),
						220, i);
				g2.drawString("Doctor: " + UserInfo.getUserName(), 400, i);
				i += 20;
				g2.drawString("Name: " + rsPatient.getString("name"), 80, i);
				g2.drawString("Gender: " + rsPatient.getString("gender"), 220,
						i);
				g2.drawString("ID: " + rsPatient.getString("p_no"), 400, i);
				i += 20;
				g2.drawString(
						"Age: "
								+ ((rsPatient.getDate("birth") == null) ? ""
										: DateMethod.getAgeWithMonth(rsPatient
												.getDate("birth"))), 80, i);
				g2.drawString("Receive Medicine Number: " + finishNo, 220, i);
				i += 15;
				g2.drawString(
						"------------------------------------------------------------------------",
						0, i);
				i += 15;
				g2.drawString("Medicine", 80, i);

				while (rsMedicines.next()) {
					++r;
					if (rsMedicines.getString("urgent").equals("Y")) {
						urgent = "Urgent";
					} else {
						urgent = "--";
					}
					if (rsMedicines.getString("powder").equals("Y")) {
						powder = "Powder";
					} else {
						powder = "--";
					}
					if (rsMedicines.getString("ps") != null) {
						ps = rsMedicines.getString("ps");
					} else {
						ps = "";
					}
					i += 25;
					g2.drawString(String.valueOf(r), 80, i);
					g2.drawString(rsMedicines.getString("item"), 90, i);
					i += 15;
					g2.drawString(rsMedicines.getString("usage"), 90, i);
					g2.drawString(rsMedicines.getString("way"), 125, i);
					g2.drawString(ps, 160, i);
					i += 15;
					g2.drawString(rsMedicines.getString("dosage"), 90, i); // +" "+
																			// rsMedicines.getString("unit")
					g2.drawString(rsMedicines.getString("repeat_number")
							+ " Day", 125, i);
					g2.drawString(urgent, 190, i);
					g2.drawString(powder, 245, i);
					g2.drawString(
							"Total: " + rsMedicines.getString("quantity"), 330,
							i); // +" "+rsMedicines.getString("unit")
					// ********************************************************//
				}
			} catch (SQLException e) {
				e.printStackTrace();
				ErrorMessage
						.setData(
								"Diagnosis",
								"Frm_DiagnosisPrintChooser",
								"MyPrintable - print(Graphics g, PageFormat pf, int pageIndex)",
								e.toString().substring(
										e.toString().lastIndexOf(".") + 1,
										e.toString().length()));
			} finally {
				try {
					DBC.closeConnection(rsMedicines);
					DBC.closeConnection(rsReceiveMedicineNo);
					DBC.closeConnection(rsPatient);
				} catch (SQLException e) {
					e.printStackTrace();
					ErrorMessage
							.setData(
									"Diagnosis",
									"Frm_DiagnosisPrintChooser",
									"MyPrintable - print(Graphics g, PageFormat pf, int pageIndex) - DBC.closeConnection",
									e.toString().substring(
											e.toString().lastIndexOf(".") + 1,
											e.toString().length()));
				}
			}
			return PAGE_EXISTS;
		}
	}

	/**
	 * Create the dialog.
	 */
	public Frm_DiagnosisPrintChooser() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setModal(true);
		setResizable(false);
		setTitle("Print");
		setBounds(100, 100, 193, 275);

		JButton btnAll = new JButton("All");
		btnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pt.DoPrintWithDialog(10, regGuid);
				pt.DoPrintWithDialog(6, regGuid);
				pt.DoPrintWithDialog(4, regGuid);
				pt.DoPrintWithDialog(8, regGuid);
				
			}
		});

		JButton btnDiagnosis = new JButton("Diagnosis");
		btnDiagnosis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pt.DoPrintWithDialog(10, regGuid);
			}
		});

		JButton btnPrescription = new JButton("Prescription");
		btnPrescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pt.DoPrintWithDialog(6, regGuid);
			}
				
		});

		JButton btnXray = new JButton("X-Ray");
		btnXray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pt.DoPrintWithDialog(4, regGuid);
			}
		});

		JButton btnMedicine = new JButton("Medicine");
		btnMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pt.DoPrintWithDialog(8, regGuid);
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
																btnMedicine,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnXray,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnPrescription,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnDiagnosis,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnAll,
																GroupLayout.PREFERRED_SIZE,
																110,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(40, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(btnAll)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnDiagnosis)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnPrescription)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnXray)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnMedicine)
						.addContainerGap(87, Short.MAX_VALUE)));
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		getContentPane().setLayout(groupLayout);
		initDataBindings();

	}

	public Frm_DiagnosisPrintChooser(String regGuid) {
		this();
		this.regGuid = regGuid;
	}

	protected void initDataBindings() {
	}
	
	private void onDiagnosisPrintClicked() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = pj.defaultPage();
		Paper paper = new Paper();
		pf.setPaper(paper);
		pj.setPrintable(new MyPrintable(), pf);
		// ***************************//
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
				ErrorMessage.setData(
						"Diagnosis",
						"Frm_DiagnosisPrintChooser",
						"btn_RePrintActionPerformed()",
						e1.toString().substring(
								e1.toString().lastIndexOf(".") + 1,
								e1.toString().length()));
			}
		}
	}

}
