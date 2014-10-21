package casemgmt;

import java.awt.LayoutManager;
import java.sql.Connection;

import javax.swing.JPanel;

public class Tab_Prescription extends JPanel implements ISaveable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2928231281078742708L;

	public Tab_Prescription() {
		// TODO Auto-generated constructor stub
	}

	public Tab_Prescription(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Tab_Prescription(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Tab_Prescription(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
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
