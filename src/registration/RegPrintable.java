package registration;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.his.util.CustomLogger;

import com.google.zxing.WriterException;

import QR.QRUtility;
import barcode.PrintBarcode;

public class RegPrintable implements Printable {

	private String[] regInfo;

	private static Logger logger = LogManager.getLogger();

	public RegPrintable(String[] rInfo) {
		regInfo = rInfo;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		if (pageIndex != 0) {
			return NO_SUCH_PAGE;
		}

		Graphics2D g2 = (Graphics2D) graphics;
		double width = pageFormat.getWidth();
		double height = pageFormat.getHeight();
		CustomLogger.debug(logger, "Current paper size w:{} h:{}", width,
				height);

		BufferedImage bSrc;
		BufferedImage QR_p_no;
		BufferedImage QR_regguid;
		try {
			bSrc = ImageIO.read(new File("./img/taiwan-logo.png"));
			AffineTransform at = new AffineTransform();
			g2.translate(80, 100);
			g2.scale(0.3, 0.3);
			g2.drawRenderedImage(bSrc, at);

			g2.setFont(new Font("Serif", Font.BOLD, 48));
			g2.drawString("National Bagana 1 Hospital", 210, 90);

			g2.setFont(new Font("Serif", Font.BOLD, 40));
			g2.drawString("Single Registration or Inpatient (For Patient)",
					400, 150);

			PrintBarcode.drawBarcodeToGraphic(graphics, 50, 210, regInfo[0]);


			try {
				QRUtility.createQRCode(regInfo[0], "./img/QRCode.png", "UTF-8", 200, 200);
			} catch (Exception e) {
				e.printStackTrace();
			}

			QR_regguid = ImageIO.read(new File("./img/QRCode.png"));
			g2.drawImage(QR_regguid, null, 1000, 210);
			
			PrintBarcode.drawBarcodeToGraphic(graphics, 50, 510, regInfo[1]);
	
			try {
				QRUtility.createQRCode(regInfo[1], "./img/QRCode.png", "UTF-8", 200, 200);
			} catch (Exception e) {
				e.printStackTrace();
			}

			QR_regguid = ImageIO.read(new File("./img/QRCode.png"));
			g2.drawImage(QR_regguid, null, 1000, 510);	
			
			
			int x = 150;
			int y = 750;
			int space = 80;

			g2.setFont(new Font("Serif", Font.PLAIN, 40));
			g2.drawString("Patient No.: " + regInfo[1], x, y += space);

			g2.drawString("Name:" + regInfo[2], x, y += space);
			g2.drawString("Gender: " + regInfo[3], x, y += space);
			g2.drawString("Date: " + regInfo[4], x, y += space);
			g2.drawString("Dept.: " + regInfo[5], x, y += space);
			

			x = 750;
			y = 830;

			g2.drawString("Shift: " + regInfo[6], x, y += space);
			g2.drawString("Clinic: " + regInfo[7], x, y += space);
			g2.drawString("Doctor: " + regInfo[8], x, y += space);
			g2.drawString("Waiting No.: " + regInfo[9], x, y += space);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return PAGE_EXISTS;
		}
	}
}
