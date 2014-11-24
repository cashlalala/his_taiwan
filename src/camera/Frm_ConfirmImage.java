package camera;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;
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

import multilingual.Language;
import cc.johnwu.sql.DBC;

public class Frm_ConfirmImage extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Vector<TwoColumeClass> cobCategoryData;
	private Vector<TwoColumeClass> cobKeywordData;

	//private Webcam webcam = null;
	//private WebcamPanel panel = null;
	//private WebcamPicker picker = null;
	
	// UI 
	private javax.swing.JPanel pan_Right;
	private javax.swing.JLabel label_Image;
	private JButton btnReTake;
	private JButton btnSave;
	private javax.swing.JLabel lblNote;
	private javax.swing.JLabel lblKeyword;
	private javax.swing.JLabel lblCategory;
	private javax.swing.JScrollPane scrollingNote;
	private javax.swing.JTextArea txtNote;
	private javax.swing.JTextField txtKeyword;
	private javax.swing.JComboBox cobCategory;
	private javax.swing.JComboBox cobKeyword;
	//private JButton btnCancel;
	
	private Language paragraph = Language.getInstance();
	private BufferedImage mImage;
	private String mPno;
	private String mItemGuid;
	private String mType;
	//private JFrame mParent;

	private void getCategoryData() {
		String sql = "SELECT * FROM body_code";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            //String[] rowData = new String[5];
            while(rs.next()){
            	cobCategoryData.add(new TwoColumeClass(rs.getString("id"), rs.getString("desc")));
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_ConfirmImage.class.getName()).log(Level.SEVERE, null, ex);
         }
	}
	
	private void getKeywordData() {
		cobKeywordData.add(new TwoColumeClass("", ""));
		
		String sql = "SELECT distinct keyword FROM image_meta WHERE type='wound' AND keyword is not null order by keyword ASC";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            //String[] rowData = new String[5];
            while(rs.next()){
            	cobKeywordData.add(new TwoColumeClass("", rs.getString("keyword")));
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_ConfirmImage.class.getName()).log(Level.SEVERE, null, ex);
         }
	}
	
    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {
    	if (!mType.equalsIgnoreCase("wound")) {
    		new main.Frm_Main().setVisible(true);	
    	}
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
		setLocationRelativeTo(this);
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
			if (mType.equalsIgnoreCase("wound")) {
				String categoryId = ((TwoColumeClass) cobCategory.getSelectedItem()).col1;
				String keyword = ((TwoColumeClass) cobKeyword.getSelectedItem()).col2;
				if(keyword.isEmpty()) keyword = txtKeyword.getText();
				String note = txtNote.getText();
				
				String sql = "INSERT INTO image_meta(guid, p_no, item_guid, type, image_data, create_time, category, keyword, note) VALUES(?, ? ,?, ?, ?, now(), ?, ?, ?)";
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
		 
				prestate.setString(6, categoryId);
				prestate.setString(7, keyword);
				prestate.setString(8, note);
				
				prestate.executeUpdate(); 
				DBC.closeConnectionExternel(conn);
				
			} else {
				String sql = "INSERT INTO image_meta(guid, p_no, item_guid, type, image_data, create_time) VALUES(?, ? ,?, ?, ?, now())";
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
			}
			
			
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
		
		lblNote = new javax.swing.JLabel("Note:");
		txtNote = new javax.swing.JTextArea(5,20);
		scrollingNote = new javax.swing.JScrollPane(txtNote);
		
		lblKeyword = new javax.swing.JLabel("Keyword:");
		txtKeyword = new javax.swing.JTextField();
		
		lblCategory = new javax.swing.JLabel("Category:");
		cobCategory = new javax.swing.JComboBox();
		cobKeyword = new javax.swing.JComboBox();
		
		if (!mType.equalsIgnoreCase("wound")) {
			lblNote.setVisible(false);
			txtNote.setVisible(false);
			scrollingNote.setVisible(false);
			lblKeyword.setVisible(false);
			txtKeyword.setVisible(false);
			lblCategory.setVisible(false);
			cobCategory.setVisible(false);
			cobKeyword.setVisible(false);
		} else {
			cobCategoryData = new Vector<TwoColumeClass>();
			cobKeywordData = new Vector<TwoColumeClass>();
			getCategoryData();
			getKeywordData();
			cobCategory = new javax.swing.JComboBox(cobCategoryData);
			cobKeyword = new javax.swing.JComboBox(cobKeywordData);
		}
		
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
						.addComponent(lblCategory, Alignment.LEADING)
						.addComponent(cobCategory, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKeyword, Alignment.LEADING)
						.addComponent(cobKeyword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(txtKeyword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNote, Alignment.LEADING)
						.addComponent(scrollingNote, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReTake, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btnSave, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						//.addComponent(btnCancel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						)
					.addContainerGap())
		);
		pan_RightLayout.setVerticalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCategory)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cobCategory)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGap(5)
					.addComponent(lblKeyword)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cobKeyword)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtKeyword)
					.addGap(5)
					.addComponent(lblNote)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollingNote)
					.addGap(20)
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
