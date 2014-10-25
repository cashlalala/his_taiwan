package camera;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import radiology.Frm_Radiology;
import multilingual.Language;
import cc.johnwu.sql.DBC;

public class Frm_ConfirmImage extends JFrame {
	private static final long serialVersionUID = 1L;

	//private Webcam webcam = null;
	//private WebcamPanel panel = null;
	//private WebcamPicker picker = null;
	
	// UI 
	private javax.swing.JPanel pan_Right;
	private javax.swing.JLabel label_Image;
	private JButton btnReTake;
	private JButton btnSave;
	//private JButton btnCancel;
	
	private Language paragraph = Language.getInstance();
	private BufferedImage mImage;
	private String mPno;
	private String mItemGuid;
	private String mType;
	//private JFrame mParent;
	
    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {
        new main.Frm_Main().setVisible(true);
        this.dispose();
    }

    public Frm_ConfirmImage(BufferedImage image, String p_no, String item_guid, String type) {
		//mParent = parent;
		mImage = image;
		mPno = p_no;
		mItemGuid = item_guid;
		mType = type;
		
		setTitle(paragraph.getString("SAVEIMAGE"));
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
	
	/*private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
		new Frm_TakeImage().setVisible(true);
		this.dispose();
	}*/
	
	private void btnReTakeActionPerformed(java.awt.event.ActionEvent evt) {
		new Frm_TakeImage(mPno, mItemGuid, mType).setVisible(true);
		this.dispose();
	}
	
	private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {
		/*Webcam webcam = Webcam.getDefault();
		//webcam.open();
		BufferedImage image = webcam.getImage();
		try {
			ImageIO.write(image, "PNG", new File("test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
		String sql = "INSERT INTO image_meta(guid, p_no, item_guid, type, image_data) VALUES(?, ? ,?, ?, ?)";
		Connection conn = DBC.getConnectionExternel();
		PreparedStatement prestate =  conn.prepareStatement(sql); //先建立一個 SQL 語句並回傳一個 PreparedStatement 物件
		prestate.setString(1, UUID.randomUUID().toString()); 
		prestate.setString(2, mPno); 
		prestate.setString(3, mItemGuid); 
		prestate.setString(4, mType);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
		ImageIO.write(mImage,"jpg",stream);
		byte[] bytes = stream.toByteArray(); 
		ByteArrayInputStream in = new ByteArrayInputStream(bytes); 
		prestate.setBinaryStream(5, in, bytes.length); 
 
		prestate.executeUpdate(); 
		DBC.closeConnectionExternel(conn);
		
		JOptionPane.showMessageDialog(null,"Saved successfully.");
		} catch (Exception ex) {
			Logger.getLogger(Frm_ConfirmImage.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		this.dispose();
	}
	
	private void initComponents() {
		pan_Right  = new javax.swing.JPanel();
		label_Image = new javax.swing.JLabel();
		btnReTake = new JButton("Re-Take");
		btnSave = new JButton("Save");
		//btnCancel = new JButton("Cancel");
		
		//Webcam webcam = Webcam.getDefault();
		//[176x144] [320x240] [640x480
		//webcam.setViewSize(new Dimension(640, 480));
		//webcam.setViewSize(WebcamResolution.VGA.getSize());
		//WebcamPanel camPanel = new WebcamPanel(webcam);
		//camPanel.setFPSDisplayed(true);
		
		//setMinimumSize(new java.awt.Dimension(500, 260));
		//setResizable(false);
		setLocationRelativeTo(null);
		
		label_Image.setIcon(new ImageIcon(mImage));
		
		btnReTake.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnReTakeActionPerformed(evt);
			}
		});
		
		btnSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSaveActionPerformed(evt);
			}
		});
		
		/*btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});*/
		
		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_RightLayout.setHorizontalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(pan_RightLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnReTake, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btnSave, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						//.addComponent(btnCancel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						)
					.addContainerGap())
		);
		pan_RightLayout.setVerticalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReTake)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSave)
					//.addContainerGap()
					//.addComponent(btnCancel)
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
																		label_Image,
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
														label_Image,
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
