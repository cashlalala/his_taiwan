package casemgmt;

import java.awt.LayoutManager;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTable;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import multilingual.Language;

public class HIVCase extends JPanel {
	public HIVCase() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 10, 60, 15);
		add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 35, 280, 300);
		add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(300, 10, 60, 15);
		add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(300, 35, 280, 165);
		add(panel_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(300, 210, 60, 15);
		add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(300, 235, 280, 98);
		add(panel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(590, 10, 60, 15);
		add(lblNewLabel_3);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(590, 35, 391, 298);
		add(panel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(10, 345, 60, 15);
		add(lblNewLabel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 370, 982, 80);
		add(scrollPane);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(10, 460, 60, 15);
		add(lblNewLabel_5);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 485, 982, 112);
		add(scrollPane_1);
	}

	private static final long serialVersionUID = 1L;

	private static final Language lang = Language.getInstance();
}
