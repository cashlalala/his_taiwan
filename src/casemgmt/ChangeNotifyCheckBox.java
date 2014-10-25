package casemgmt;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChangeNotifyCheckBox extends JCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5781563835783586690L;

	private JComponent comp;

	public ChangeNotifyCheckBox(String text, JComponent targetCtrl) {
		super(text);
		this.comp = targetCtrl;
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				comp.setEnabled(true);
			}
		});
	}

}
