package casemgmt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;

public class ChangeNotifyCheckBox extends JCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5781563835783586690L;

	private JComponent comp;

	public ChangeNotifyCheckBox(String text, JComponent targetCtrl) {
		super(text);
		this.comp = targetCtrl;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comp.setEnabled(true);
			}
		});
	}

}
