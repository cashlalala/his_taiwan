package Camera;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import multilingual.Language;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class Frm_Wound extends JFrame {
	private static final long serialVersionUID = 1L;

	//private Webcam webcam = null;
	//private WebcamPanel panel = null;
	//private WebcamPicker picker = null;
	
	private Language paragraph = Language.getInstance();;
	
    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {
        new main.Frm_Main().setVisible(true);
        this.dispose();
    }
    
	public Frm_Wound() {
		setTitle(paragraph.getString("TAKEIMAGE"));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {                                // 畫面關閉原視窗enable
            @Override
            public void windowClosing(WindowEvent windowevent) {
                btn_CloseActionPerformed(null);
            }
        });
		
		initComponents();
		//JFrame window = new JFrame("Test webcam panel");
		//window.add(panel);
		//window.setResizable(false);
		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.pack();
		//window.setVisible(true);
	}
	
	private void initComponents() {
		Webcam webcam = Webcam.getDefault();
		//[176x144] [320x240] [640x480
		webcam.setViewSize(new Dimension(320, 240));
		//webcam.setViewSize(WebcamResolution.VGA.getSize());
		WebcamPanel camPanel = new WebcamPanel(webcam);
		camPanel.setFPSDisplayed(true);
		
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														camPanel,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGap(61, 61, 61)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		camPanel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														))
								.addContainerGap()));
		
		pack();
	}
}
