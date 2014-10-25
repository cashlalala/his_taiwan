package camera;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

import main.Frm_Main;
import multilingual.Language;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class Frm_TakeImage extends JFrame {
	private static final long serialVersionUID = 1L;

	//private Webcam webcam = null;
	//private WebcamPanel panel = null;
	//private WebcamPicker picker = null;
	
	// UI 
	private javax.swing.JPanel pan_Right;
	private JButton btnTake;
	private JButton btnCancel;
	
	private Language paragraph = Language.getInstance();
	//private JFrame mParent;
	private String mPno;
	private String mItemGuid;
	private String mType;
	
    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {
        new main.Frm_Main().setVisible(true);
        this.dispose();
    }
    
    /**
     * for case mgmt of wound care
     * 	pass type = 'wound'  
     * 	pass item_guid = case_guid
     * 	pass p_no = p_no
     **/
	public Frm_TakeImage(String p_no, String item_guid, String type) {
		//mParent = parent;
		mPno = p_no;
		mItemGuid = item_guid;
		mType = type;
		
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
	
	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
		//mParent.setVisible(true);
		this.dispose();
	}
	
	private void btnTakeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_RadiologyActionPerformed
		Webcam webcam = Webcam.getDefault();
		//webcam.open();
		BufferedImage image = webcam.getImage();
		webcam.close();
		new Frm_ConfirmImage(image, mPno, mItemGuid, mType).setVisible(true);
		this.dispose();
	}// GEN-LAST:event_btn_RadiologyActionPerformed
	
	private void initComponents() {
		pan_Right  = new javax.swing.JPanel();
		btnTake = new JButton("Take");
		btnCancel = new JButton("Cancel");
		
		Webcam webcam = Webcam.getDefault();
		//[176x144] [320x240] [640x480
		webcam.setViewSize(new Dimension(640, 480));
		//webcam.setViewSize(WebcamResolution.VGA.getSize());
		WebcamPanel camPanel = new WebcamPanel(webcam);
		camPanel.setFPSDisplayed(true);
		
		//setMinimumSize(new java.awt.Dimension(500, 260));
		//setResizable(false);
		setLocationRelativeTo(null);
		
		btnTake.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnTakeActionPerformed(evt);
			}
		});
		
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});
		
		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_RightLayout.setHorizontalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(pan_RightLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnTake, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btnCancel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
					.addContainerGap())
		);
		pan_RightLayout.setVerticalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnTake)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCancel)
					.addContainerGap())
		);
		pan_Right.setLayout(pan_RightLayout);
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
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
																.addComponent(
																		pan_Right,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														camPanel,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														pan_Right,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		
		
		pack();
	}
}
