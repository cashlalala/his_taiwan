package casemgmt;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	public ImagePanel() {
		try {
			image = ImageIO.read(new File("img/diabetes-foot-exam.jpg"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ImagePanel(LayoutManager layout) {
		super(layout);
	}

	public ImagePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public ImagePanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, 300, 500, null);
	}

}
