package casemgmt;

import java.sql.Connection;

import javax.swing.JPanel;
import javax.swing.JButton;

import multilingual.Language;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tab_Wound extends JPanel implements ISaveable {
	private static final Language lang = Language.getInstance();
	private JButton btn_TakeImage;
	private String case_guid;
	private String p_no;

	public Tab_Wound(String p_no, String case_guid, String type) {
		setLayout(null);
		this.p_no = p_no;
		this.case_guid = case_guid;
		btn_TakeImage = new JButton(lang.getString("WOUND_TAKE_IMAGE"));
		btn_TakeImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btn_TakeImageactionPerformed();
			}
		});
		btn_TakeImage.setBounds(12, 12, 100, 25);
		add(btn_TakeImage);
	}

	public void btn_TakeImageactionPerformed() {
		new Camera.Frm_TakeImage(p_no, case_guid, "wound").setVisible(true);
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
