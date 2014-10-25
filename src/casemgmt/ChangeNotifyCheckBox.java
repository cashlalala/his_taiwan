package casemgmt;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
