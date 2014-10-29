package patients;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.his.model.PatientsInfo;
import org.his.util.CustomLogger;

public class LabelSticker implements Printable {

	private PatientsInfo patientInfo;

	private static Logger logger = LogManager.getLogger();

	public LabelSticker(PatientsInfo pInfo) {
		patientInfo = pInfo;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		if (pageIndex != 0) {
			return NO_SUCH_PAGE;
		}

		int x = 0;
		int y = 0;
		int space = 0;

		Graphics2D g2 = (Graphics2D) graphics;
		double width = pageFormat.getWidth();
		double height = pageFormat.getHeight();
		CustomLogger.debug(logger, "Current paper size w:{} h:{}", width,
				height);

		int xOffset = (int) (width / 2);
		int yOffset = (int) (height / 4);
		for (int i = 0; i < 4; ++i) {
			g2.setFont(new Font("Serif", Font.PLAIN, 8));
			g2.drawLine(0, yOffset * i, (int) width, yOffset * i);

			for (int j = 0; j < 2; ++j) { // 300*200
				if (j == 0) {
					g2.setFont(new Font("Serif", Font.PLAIN, 8));
					g2.drawLine(xOffset, yOffset * i, xOffset, yOffset
							* (i + 1));
				}

				x = 10 + xOffset * j;
				y = 10 + yOffset * i; // 起始值
				space = 20; // 間距
				g2.setFont(new Font("Serif", Font.PLAIN, 10));
				g2.drawString("Patient No.:", x, y += space);
				g2.drawString("Name:", x, y += space);
				g2.drawString("Gender:", x, y += space);
				g2.drawString("Birth:", x, y += space);

				x = 75 + xOffset * j;
				y = 10 + yOffset * i; // 起始值
				space = 20; // 間距
				g2.setFont(new Font("Serif", Font.BOLD, 10));
				g2.drawString(patientInfo.getPNo(), x, y += space);
				g2.drawString(
						patientInfo.getFirstname() + " "
								+ patientInfo.getLastname(), x - 25, y += space);
				g2.drawString(patientInfo.getGender(), x + 5, y += space);

				if (patientInfo.getBirth() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					g2.drawString(sdf.format(patientInfo.getBirth()), x - 25,
							y += space);
				} else {
					g2.drawString("", x - 25, y += space);
				}
			}
		}

		return PAGE_EXISTS;
	}

}
