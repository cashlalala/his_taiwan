package casemgmt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import multilingual.Language;
import cc.johnwu.sql.DBC;

import common.Constant;

public class Dlg_WoundPicOper extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7724889412620480028L;
	private Language paragraph = Language.getInstance();
	
	protected String wondPicGuid;
	protected String imageType;
	protected String p_no;
	protected String itemGUID;
	protected String category;
	protected String keyword;
	protected String note;

	private static Language lang = Language.getInstance();

	public Dlg_WoundPicOper(String guid) {
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				refrashPic();
			}
		});
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				refrashPic();
			}
		});
		wondPicGuid = guid;
		initComponents();
		
		if(imageType.compareTo(Constant.WOUND_CODE) != 0) {
			lb_category.setVisible(false);
			lb_keyword.setVisible(false);
			lb_note.setVisible(false);
		}
		//lb_itemGUID.setVisible(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onBtnCloseClicked(null);
			}

			 @Override
			 public void windowActivated(WindowEvent e) {
			 refrashPic();
			 }

			@Override
			public void windowOpened(WindowEvent e) {
				refrashPic();
			}
		});
		setFocusable(true);
		setResizable(true);
		setAlwaysOnTop(true);
		setSize(1000, 700);
		refrashPic();
	}

	private JScrollPane scrollPane;
	private JPanel panel;
	private ImagePanel panel_1;
	private JButton btnDelete;
	private JButton btnClose;
	private JLabel lb_p_no;
	private JLabel lb_type;
	//private JLabel lb_itemGUID;
	private JLabel lb_category;
	private JLabel lb_keyword;
	private JLabel lb_note;

	private void initComponents() {
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		panel = new JPanel();
		//panel.setSize(800, 600);
		scrollPane.setViewportView(panel);
		//scrollPane.setPreferredSize(new Dimension(700,600));

		Image img = null;
		ResultSet rs = null;
		String sql = String.format(
				"select * from image_meta LEFT JOIN body_code on image_meta.category = body_code.id where guid = '%s'", wondPicGuid);
		try {
			rs = DBC.executeQuery(sql);
			if (rs.next()) {
				byte[] mem = null;
				Blob image = rs.getBlob("image_data");
				mem = image.getBytes(1, (int) image.length());
				img = Toolkit.getDefaultToolkit().createImage(mem);

				imageType = rs.getString("type");
				p_no = rs.getString("p_no");
				itemGUID = rs.getString("item_guid");
				category = rs.getString("desc"); if (rs.wasNull()) category = "";
				keyword = rs.getString("keyword"); if (rs.wasNull()) keyword = "";
				note = rs.getString("note"); if (rs.wasNull()) note = "";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		panel_1 = new ImagePanel(img, 550, 550);

		lb_p_no = new JLabel(paragraph.getString("PATIENTNO") + p_no);
		//lb_itemGUID = new JLabel("itemGUID");
		lb_type = new JLabel(paragraph.getString("TYPE") + imageType);
		lb_category = new JLabel("Category:" + category);
		lb_keyword = new JLabel("Keyword:" + keyword);
		lb_note = new JLabel("Note:" + note);
		
		btnDelete = new JButton(lang.getString("DELETE"));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnDelClicked(e);
			}
		});

		btnClose = new JButton(lang.getString("CLOSE"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onBtnCloseClicked(e);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addComponent(lb_p_no, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lb_type, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lb_category, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lb_keyword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lb_note, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnClose, GroupLayout.DEFAULT_SIZE, 120, 120)
							.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 120, 120))
					.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lb_p_no)
									.addComponent(lb_type)
									.addComponent(lb_category)
									.addComponent(lb_keyword)
									.addComponent(lb_note)
									.addGap(50)
									.addComponent(btnDelete)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnClose)
								)
						)
						.addContainerGap()));
		panel.setLayout(gl_panel);
		pack();
	}

	protected void onBtnCloseClicked(ActionEvent e) {
		setVisible(false);
		dispose();
	}

	protected void onBtnDelClicked(ActionEvent e) {
		String sql = String.format(
				"update image_meta set status = 'D' where guid = '%s'",
				wondPicGuid);
		int reply = JOptionPane.showConfirmDialog(this,
				lang.getString("WOUND_CASE_CONFIRM_DELETE"),
				lang.getString("WOUND_CASE_CONFIRM_TITLE"),
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			try {
				DBC.executeUpdate(sql);
				JOptionPane.showMessageDialog(this,
						lang.getString("WOUND_CASE_CONFIRM_DEL_SUCC"));
				onBtnCloseClicked(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Delete Fail: " + e1.getMessage());
			}
		}
	}

	protected void refrashPic() {
		panel_1.invalidate();
		panel_1.repaint();
	}
}
