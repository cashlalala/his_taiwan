package camera;

import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import multilingual.Language;
import cc.johnwu.sql.DBC;

import common.TabTools;

public class Frm_ImageMgmt extends JFrame {

	private Language paragraph = Language.getInstance();
	
	private JPanel contentPane;
	private JTextField textField1;
	private JTextField textField2;
	private JTable tbImage;
    private JScrollPane spImage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frm_ImageMgmt frame = new Frm_ImageMgmt();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private DefaultTableModel imageTableModel = new DefaultTableModel(){
    	public boolean isCellEditable(int rowIndex, int columnIndex){
    		//if (columnIndex == 1 || columnIndex == 2) {
            //    return true;
            //} else {
                return false;
            //}
    	}
   	};
   	
	private void reloadImageTable(DefaultTableModel dtm) {
		dtm.setRowCount(0);
//String s[]={"guid", "item_guid", "p_no", "type", "category", "keyword", "note", "create_time"};
   		try{
            ResultSet rs = DBC.executeQuery("SELECT guid, item_guid, p_no, type, category, keyword, note, create_time FROM image_meta order by create_time asc");
            String[] rowData = new String[8];
            while(rs.next()){
            	rowData[0] = rs.getString("guid");
            	rowData[1] = rs.getString("item_guid");
            	rowData[2] = rs.getString("p_no");
            	rowData[3] = rs.getString("type");
            	rowData[4] = rs.getString("category");
            	rowData[5] = rs.getString("keyword");
            	rowData[6] = rs.getString("note");
            	rowData[7] = rs.getString("create_time");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_ImageMgmt.class.getName()).log(Level.SEVERE, null, ex);
         }
        // setup status combobox
        //setUpStatusColumn(tb_division, tb_division.getColumnModel().getColumn(2));
   		
   		//TabTools.setHideColumn(tbImage, 0);
   		//TabTools.setHideColumn(tbImage, 1);
		
	}
	
	void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		//setMinimumSize(new java.awt.Dimension(800, 600));
		contentPane = new JPanel();
		//contentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

		setContentPane(contentPane);
		
		JPanel paneleftTop = new JPanel();
		JPanel panelRightTop = new JPanel();
		JPanel panelBottom = new JPanel();
		
		JLabel lblQuery1 = new JLabel("query1:");
		JLabel lblQuery2 = new JLabel("query2:");
		
		textField1 = new JTextField();
		textField2 = new JTextField();
		
		tbImage = new JTable();
		spImage = new JScrollPane();
		
		spImage.setViewportView(tbImage);
		//spImage.setPreferredSize(new Dimension(GroupLayout.DEFAULT_SIZE, spImage.getMinimumSize().height));
		
		//panelRightTop.add(lblQuery2);
		//panelRightTop.add(textField2);
		//textField2.setColumns(10);
		
		//paneleftTop.add(lblQuery1);
		//paneleftTop.add(textField1);
		
		//textField1.setColumns(10);
		
		//String s[]={"guid", "p_no", paragraph.getString("STATUS")};
		String s[]={"guid", "item_guid", "p_no", "type", "category", "keyword", "note", "create_time"};
		imageTableModel.setColumnIdentifiers(s);
		
		tbImage.setModel(imageTableModel);
		tbImage.setRowHeight(30);
		tbImage.setAutoCreateRowSorter(true);
		reloadImageTable(imageTableModel);

		//panelBottom.add(spImage);
		
		javax.swing.GroupLayout jPanelLTLayout = new javax.swing.GroupLayout(paneleftTop);
		paneleftTop.setLayout(jPanelLTLayout);
		jPanelLTLayout.setHorizontalGroup(
				jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLTLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblQuery1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            //.addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            //.addComponent(txt_NoonE, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                         )
                        .addContainerGap(377, Short.MAX_VALUE))
                    ))
        );
		jPanelLTLayout.setVerticalGroup(
				jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuery1)
                    .addComponent(textField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    //.addComponent(jLabel10)
                    //.addComponent(txt_NightE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                .addContainerGap())
        );
        
		javax.swing.GroupLayout jPanelRTLayout = new javax.swing.GroupLayout(panelRightTop);
		panelRightTop.setLayout(jPanelRTLayout);
		jPanelRTLayout.setHorizontalGroup(
				jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRTLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblQuery2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            //.addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            //.addComponent(txt_NoonE, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                         )
                        .addContainerGap(377, Short.MAX_VALUE))
                    ))
        );
		jPanelRTLayout.setVerticalGroup(
				jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuery2)
                    .addComponent(textField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    //.addComponent(jLabel10)
                    //.addComponent(txt_NightE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                .addContainerGap())
        );		
		
		javax.swing.GroupLayout jPanelBotLayout = new javax.swing.GroupLayout(panelBottom);
		panelBottom.setLayout(jPanelBotLayout);

		jPanelBotLayout.setHorizontalGroup(
			jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                	.addComponent(spImage, javax.swing.GroupLayout.Alignment.LEADING)
                )
            )
        );
		jPanelBotLayout.setVerticalGroup(
			jPanelBotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()
            )
        );
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(paneleftTop, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelRightTop, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(panelBottom, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(12))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panelRightTop, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(paneleftTop, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBottom, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		pack();
	}

	/**
	 * Create the frame.
	 */
	public Frm_ImageMgmt() {
		initComponents();
	}
}
