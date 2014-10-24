package common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

public class WebcamAdapter {

	/*public WebcamAdapter() {

	
	}*/
	
	// path is either absolute or relative
	public static void takeImage(String path) {
		// automatically open if webcam is closed
		Webcam.setAutoOpenMode(true);

		try {
			// get image
			BufferedImage image = Webcam.getDefault().getImage();

			// save image to PNG file
			ImageIO.write(image, "PNG", new File("test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
