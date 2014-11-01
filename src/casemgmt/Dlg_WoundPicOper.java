package casemgmt;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import multilingual.Language;
import cc.johnwu.sql.DBC;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowStateListener;

public class Dlg_WoundPicOper extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7724889412620480028L;
	protected String wondPicGuid;

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
		setSize(800, 700);
		refrashPic();
	}

	private JScrollPane scrollPane;
	private JPanel panel;
	private ImagePanel panel_1;
	private JButton btnDelete;
	private JButton btnClose;

	private void initComponents() {
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		panel = new JPanel();
		scrollPane.setViewportView(panel);

		Image img = null;
		ResultSet rs = null;
		String sql = String.format(
				"select * from image_meta where guid = '%s'", wondPicGuid);
		try {
			rs = DBC.executeQuery(sql);
			if (rs.next()) {
				byte[] mem = null;
				Blob image = rs.getBlob("image_data");
				mem = image.getBytes(1, (int) image.length());
				img = Toolkit.getDefaultToolkit().createImage(mem);
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
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 483,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_panel.createParallelGroup(
										Alignment.TRAILING, false)
										.addComponent(btnClose,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnDelete,
												GroupLayout.DEFAULT_SIZE, 73,
												Short.MAX_VALUE))
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addComponent(
														panel_1,
														GroupLayout.DEFAULT_SIZE,
														388, Short.MAX_VALUE)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		btnDelete)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		btnClose)))
								.addContainerGap()));
		panel.setLayout(gl_panel);
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
