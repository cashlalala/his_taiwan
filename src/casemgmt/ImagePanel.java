package casemgmt;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Image image;

	private int wid;

	private int high;

	public ImagePanel() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				onFocusGained(e);
			}
		});
		try {
			image = ImageIO.read(new File("img/diabetes-foot-exam.jpg"));
			this.wid = 300;
			this.high = 500;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void onFocusGained(FocusEvent e) {
		revalidate();
		repaint();
	}

	public ImagePanel(Image img, int i, int j) {
		this.image = img;
		this.wid = i;
		this.high = j;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, wid, high, null);
	}

}
