package casemgmt;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import multilingual.Language;
import cc.johnwu.sql.DBC;

public class Tab_Wound extends JPanel implements ISaveable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6373888539986688558L;
	private static final Language lang = Language.getInstance();
	private JButton btn_TakeImage;
	private String caseGuid;
	private String pNo;

	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton btnSave;

	private Map<String, JButton> imgs;
	public UpdateImage updateImgThread;

	public static class UpdateImage extends Thread {

		private boolean running;
		private Tab_Wound parent;

		public UpdateImage(Tab_Wound parent) {
			this.parent = parent;
			running = true;
		}

		public void stopRunning() {
			running = false;
		}

		public void run() {
			try {
				while (running) {
					this.parent.init();
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Tab_Wound(String p_no, String case_guid, String type) {
		this.pNo = p_no;
		this.caseGuid = case_guid;
		imgs = new HashMap<String, JButton>();

		initComponents();

		updateImgThread = new UpdateImage(this);
		updateImgThread.start();
	}

	protected void init() {

		String sql = String.format("select * from image_meta "
				+ "where item_guid = '%s' and type = 'wound' order by create_time desc", caseGuid);

		ResultSet rs = null;
		boolean isUIChanged = false;
		try {
			rs = DBC.executeQuery(sql);
			while (rs.next()) {
				final String guid = rs.getString("guid");
				String status = rs.getString("status");

				if (status.equalsIgnoreCase("N")) {
					if (imgs.containsKey(guid)) {
						continue;
					} else {
						byte[] mem = null;
						Blob image = rs.getBlob("image_data");
						mem = image.getBytes(1, (int) image.length());
						JButton button = new JButton();
						button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								Dlg_WoundPicOper dlg = new Dlg_WoundPicOper(
										guid);
								dlg.setLocationRelativeTo(null);
								dlg.setVisible(true);
							}
						});
						// button.setBorder(BorderFactory.createEmptyBorder());
						button.setContentAreaFilled(false);
						Image img = Toolkit.getDefaultToolkit()
								.createImage(mem);
						Image newimg = img.getScaledInstance(150, 150,
								java.awt.Image.SCALE_SMOOTH);
						button.setIcon(new ImageIcon(newimg));
						button.setSize(150, 150);
						imgs.put(guid, button);
						panel.add(button);
						isUIChanged = true;
					}

				} else if (status.equalsIgnoreCase("D")) {
					if (imgs.containsKey(guid)) {
						JButton btn = imgs.remove(guid);
						panel.remove(btn);
						isUIChanged = true;
					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBC.closeConnection(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (isUIChanged) {
			scrollPane.revalidate();
			panel.revalidate();
		}
	}

	private void initComponents() {
		scrollPane = new JScrollPane();

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		WrapLayout wl_panel = new WrapLayout();
		wl_panel.setAlignment(FlowLayout.LEFT);
		panel.setLayout(wl_panel);
		btn_TakeImage = new JButton(lang.getString("WOUND_TAKE_IMAGE"));
		btn_TakeImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btn_TakeImageactionPerformed();
			}
		});

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				btn_TakeImage)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				btnSave,
																				GroupLayout.PREFERRED_SIZE,
																				84,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																scrollPane,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																438,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(scrollPane,
										GroupLayout.DEFAULT_SIZE, 256,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(btnSave)
												.addComponent(btn_TakeImage))
								.addContainerGap()));
		setLayout(groupLayout);

	}

	public void btn_TakeImageactionPerformed() {
		new camera.Frm_TakeImage(pNo, caseGuid, "wound").setVisible(true);
	}

	@Override
	public boolean isSaveable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void save() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Connection conn) throws Exception {
		// TODO Auto-generated method stub

	}
}
