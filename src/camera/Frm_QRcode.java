package camera;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
//import javax.swing.JTextArea;

public class Frm_QRcode extends JFrame implements Runnable, ThreadFactory {

	private static final long serialVersionUID = 6441489157408381878L;
	private javax.swing.JTextField mTxtField;
	private JFrame mFrmParent;

	private Executor executor = Executors.newSingleThreadExecutor(this);

	private Webcam webcam = null;
	private WebcamPanel panel = null;
	private boolean isRunning = true;
	//private JTextArea textarea = null;

	// invoke this by 
	// new Frm_QRcode(this, txtField).setVisible(true);
	// it will set txtField for you if it got anything
	// it will set txtField with "N/A" if it is closed before it got anything
	public Frm_QRcode(JFrame parent, javax.swing.JTextField txtField) {
		super();
		mTxtField = txtField;
		mFrmParent = parent;
		mTxtField.setText("");
			
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	System.out.println("wtf");
		    	if(mTxtField.getText().length() == 0) 
		    		{
		    		mTxtField.setText("N/A");
		    		onClose();
		    		}
		    }
		});
				
		setLayout(new FlowLayout());
		setTitle("Reading QRcode");

		Dimension size = WebcamResolution.QVGA.getSize();

		webcam = Webcam.getWebcams().get(0);
		webcam.setViewSize(size);

		panel = new WebcamPanel(webcam);
		panel.setPreferredSize(size);

		//textarea = new JTextArea();
		//textarea.setEditable(false);
		//textarea.setPreferredSize(size);

		add(panel);
		//add(textarea);

		pack();
		setVisible(true);

		executor.execute(this);
	}

	@Override
	public void run() {
		if(isRunning) {
			do {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				Result result = null;
				BufferedImage image = null;
	
				if (webcam.isOpen()) {
	
					if ((image = webcam.getImage()) == null) {
						continue;
					}
	
					LuminanceSource source = new BufferedImageLuminanceSource(image);
					BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	
					try {
						result = new MultiFormatReader().decode(bitmap);
					} catch (NotFoundException e) {
						// fall thru, it means there is no QR code in image
					}
				}
	
				if (result != null) {
					//textarea.setText(result.getText());
					mTxtField.setText(result.getText());
					onClose();
				}
	
			} while (true);
		}
	}
	
	private void onClose () {
		isRunning = false;
		webcam.close();
		mFrmParent.setEnabled(true);
		this.dispose();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "qrcode-runner");
		t.setDaemon(true);
		return t;
	}

}
