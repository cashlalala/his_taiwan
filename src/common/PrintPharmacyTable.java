package common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cc.johnwu.sql.DBC;

public class PrintPharmacyTable {
	//private int m_Type;
	//private ResultSet m_Rs; // 基本資料
	//private ResultSet m_RsData; // 迴圈列印資料
	private int rowNo;
	private Vector<String[]> m_printData;

	//private String m_RegGuid; // 迴圈列印資料
	//private String m_CashierType; // 收據類別
	//private final String X_RAY_CODE = Constant.X_RAY_CODE; // 處置X光代碼

	private void preparePharmacyData() {
		
		m_printData = new Vector<String[]>();
		String sql = "SELECT B.code, B.ATC_code, B.item, A.current_amount FROM medical_stock A, medicines B where A.item_guid = B.code order by A.item_guid asc";
		try {
			ResultSet m_RsData = DBC.executeQuery(sql);
			while (m_RsData.next()) {
				m_printData.add(new String[] {m_RsData.getString("code"),
						m_RsData.getString("ATC_code"),
						m_RsData.getString("item"),
						m_RsData.getString("current_amount")});
			}
			DBC.closeConnection(m_RsData);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DoPrint() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = pj.defaultPage();
		Paper paper = new Paper();
		pf.setPaper(paper);
		pj.setPrintable(new MyPrintable(), pf);
		preparePharmacyData();
		
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	public class MyPrintable implements Printable {

		// note: a page may print several times
		public int print(Graphics g, PageFormat pf, int pageIndex) {
			//String sqlData = "";
			int x = 0;
			int y = 220; // 起始值
			int space = 50; // 間距
			int itemPerPage = 32;
			int start = pageIndex * itemPerPage;

			//System.out.println("pageIndex = " + pageIndex + ", start = " + start);
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setFont(new Font("Serif", Font.PLAIN, 10));
			g2.setPaint(Color.black);
				
			setLogoTitle(g2, "Pharmacy Stock");
			x = 60;
			//y = setPatientInfoTitle(g2);

			g2.setFont(new Font("Serif", Font.PLAIN, 32)); // 表頭
			//g2.drawString("Urgent", x - 45, y);
			g2.drawString("code", x + 80, y);
			g2.drawString("ATC code", x + 300, y);
			g2.drawString("pharmacy ", x + 500, y);
			g2.drawString("amount", x + 1150, y);
			g2.setFont(new Font("Serif", Font.BOLD, 32)); // 資料
			y += 80;
			for(int i = start; i < start + itemPerPage; i++) {
				if (i >= m_printData.size()) break;
				g2.drawString((i + 1) + ".", x, y);
				if(m_printData.get(i)[0] != null) g2.drawString(m_printData.get(i)[0], x + 80, y);
				if(m_printData.get(i)[1] != null) g2.drawString(m_printData.get(i)[1], x + 300, y);
				if(m_printData.get(i)[2] != null) g2.drawString(m_printData.get(i)[2], x + 500, y);
				if(m_printData.get(i)[3] != null) g2.drawString(m_printData.get(i)[3], x + 1150, y);
				y += space;
			}
			if(start < m_printData.size())
				return PAGE_EXISTS;
			else
				return NO_SUCH_PAGE;
		}

		private int setPatientInfoTitle(Graphics2D g2) {

			//try {
				int x = 130;
				int y = 180; // 起始值
				int space = 60; // 間距
				g2.setFont(new Font("Serif", Font.PLAIN, 36));
				// g2.drawString("Modify", 1300, 100);
				// g2.drawString("Reprint", 1300, 140);
				//if (m_Type == 1 || m_Type == 4 || m_Type == 6 || m_Type == 12) 
				{
					g2.drawString("Unpaid", 1300, 180);
				}

				g2.drawString("Patient No.:", x, y += space);
				g2.drawString("Name:", x, y += space);
				g2.drawString("Gender:", x, y += space);
				g2.drawString("Birth:", x, y += space);
				g2.drawString("Age:", x, y += space);
				x = 820;
				y = 180;
				g2.drawString("Date:", x, y += space);
				g2.drawString("Shift:", x, y += space);
				g2.drawString("Dept.:", x, y += space);
				g2.drawString("Clinic:", x, y += space);
				g2.drawString("Doctor:", x, y += space);
				g2.setFont(new Font("Serif", Font.BOLD, 32));
				x = 320;
				y = 180;
				/*g2.drawString(m_Rs.getString("p_no"), x + 10, y += space);
				g2.drawString(m_Rs.getString("name"), x - 65, y += space);
				g2.drawString(m_Rs.getString("gender"), x - 45, y += space);
				if (m_Rs.getString("birth") == null) {
					g2.drawString("", x - 70, y += space);
				} else {
					g2.drawString(m_Rs.getString("birth"), x - 70, y += space);
				}
				if (m_Rs.getString("age") == null) {
					g2.drawString("", x - 90, y += space);
				} else {
					g2.drawString(m_Rs.getString("age"), x - 90, y += space);
				}

				x = 960;
				y = 180;
				g2.drawString(m_Rs.getString("date"), x - 30, y += space);
				g2.drawString(m_Rs.getString("shift"), x - 25, y += space);
				g2.drawString(m_Rs.getString("dept"), x - 20, y += space);
				g2.drawString(m_Rs.getString("clinic"), x - 10, y += space);
				g2.drawString(m_Rs.getString("doctor"), x, y += space);
				g2.drawString(
						"========================================"
								+ "==============================================================",
						0, y += space);*/
				return y + space;
			//}
			//catch (SQLException ex) {
			//	Logger.getLogger(PrintTools.class.getName()).log(Level.SEVERE,
			//			null, ex);
			//}
			//return 0;
		}

		private void setLogoTitle(Graphics2D g2, String tabName) {
			String hosName = "National Bagana 1 Hospital"; // 醫院名稱
			try {
				// -------------　列印醫院LOGO --------------
				BufferedImage bSrc = ImageIO.read(new File(
						"./img/taiwan-logo.png"));
				AffineTransform at = new AffineTransform();
				g2.translate(80, 100);
				g2.scale(0.3, 0.3);
				g2.drawRenderedImage(bSrc, at);
				// -------------　列印醫院名稱 --------------
				g2.setFont(new Font("Serif", Font.BOLD, 48));
				g2.drawString(hosName, 210, 90);
				// -------------　列印表單名稱 --------------
				g2.setFont(new Font("Serif", Font.BOLD, 40));
				g2.drawString(tabName, 400, 150);
			} catch (IOException ex) {
				Logger.getLogger(PrintTools.class.getName()).log(Level.SEVERE,
						null, ex);
			}
		}
	}
}
