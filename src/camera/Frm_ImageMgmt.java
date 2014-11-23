package camera;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import multilingual.Language;
import cc.johnwu.sql.DBC;

import common.Constant;
import common.TabTools;

public class Frm_ImageMgmt extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Language paragraph = Language.getInstance();
	
	private JPanel contentPane;
	private JButton btnEnter;
	private JButton btnClose;
	private JButton btnQuery;
	private JTable tbImage;
    private JScrollPane spImage;
    private JCheckBox cbxDate;
    
    private javax.swing.JComboBox cobType;
    private javax.swing.JComboBox cobCategory;
	private javax.swing.JComboBox cobKeyword;
	private cc.johnwu.date.DateComboBox dateComboBox;
	
	private Vector<TwoColumeClass> cobTypeData;
	private Vector<TwoColumeClass> cobCategoryData;
	private Vector<TwoColumeClass> cobKeywordData;

	private void getCategoryData() {
		cobCategoryData.add(new TwoColumeClass("", ""));
		
		String sql = "SELECT * FROM body_code";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            //String[] rowData = new String[5];
            while(rs.next()){
            	cobCategoryData.add(new TwoColumeClass(rs.getString("id"), rs.getString("desc")));
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_ConfirmImage.class.getName()).log(Level.SEVERE, null, ex);
         }
	}
	
	private void getKeywordData() {
		cobKeywordData.add(new TwoColumeClass("", ""));
		
		String sql = "SELECT distinct keyword FROM image_meta WHERE type='wound' AND keyword is not null order by keyword ASC";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            //String[] rowData = new String[5];
            while(rs.next()){
            	cobKeywordData.add(new TwoColumeClass("", rs.getString("keyword")));
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_ConfirmImage.class.getName()).log(Level.SEVERE, null, ex);
         }
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
   	
	private void reloadImageTable(DefaultTableModel dtm, String date, String type, String category, String keyword) {
		Boolean conditionEnabled = false;
		String where = "";
		if(!date.isEmpty()) {
			if(!conditionEnabled) {
				conditionEnabled = true;
				where += "where ";
			}
			else {
				where += " AND ";
			}
			where += "create_time LIKE '" + date + "%' ";
		}
		if(!type.isEmpty()) {
			if(!conditionEnabled) {
				conditionEnabled = true;
				where += "where ";
			}
			else {
				where += " AND ";
			}
			where += "type = '" + type + "' ";
		}
		if(!category.isEmpty()) {
			if(!conditionEnabled) {
				conditionEnabled = true;
				where += "where ";
			}
			else {
				where += " AND ";
			}
			where += "category = '" + category + "' ";
		}
		if(!keyword.isEmpty()) {
			if(!conditionEnabled) {
				conditionEnabled = true;
				where += "where ";
			}
			else {
				where += " AND ";
			}
			where += "keyword = '" + keyword + "' ";
		}
				
		dtm.setRowCount(0);
//String s[]={"guid", "item_guid", "p_no", "type", "category", "keyword", "note", "create_time"};
   		try{
            ResultSet rs = DBC.executeQuery("SELECT guid, item_guid, p_no, type, body_code.desc as 'category', keyword, note, create_time FROM image_meta LEFT JOIN body_code on image_meta.category = body_code.id " + where + " order by create_time asc");
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
   		
   		TabTools.setHideColumn(tbImage, 0);
   		TabTools.setHideColumn(tbImage, 1);
		
	}
	
	private void initLanguage() { 
		this.btnClose.setText(paragraph.getString("CLOSE"));
		this.btnEnter.setText(paragraph.getString("ENTER"));
		this.btnQuery.setText(paragraph.getString("SEARCH"));
	}
	
	void initComponents() {
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	    setTitle(paragraph.getString("IMAGEMANAGEMENT"));
	        
		contentPane = new JPanel();
		setContentPane(contentPane);
		//contentPane.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
		
		JPanel paneleftTop = new JPanel();
		JPanel panelRightTop = new JPanel();
		JPanel panelBottom = new JPanel();
		JPanel panelRight = new JPanel();
		JPanel panelLeft = new JPanel();
		
		btnEnter = new JButton();
		btnClose = new JButton();
		btnQuery = new JButton();
		cobTypeData = new Vector<TwoColumeClass>();
		cobTypeData.add(new TwoColumeClass("", ""));
		cobTypeData.add(new TwoColumeClass(Constant.WOUND_CODE, Constant.WOUND_CODE));
		cobTypeData.add(new TwoColumeClass(Constant.X_RAY_CODE, Constant.X_RAY_CODE));
		cobType = new javax.swing.JComboBox(cobTypeData);
		
		cobCategoryData = new Vector<TwoColumeClass>();
		cobKeywordData = new Vector<TwoColumeClass>();
		getCategoryData();
		getKeywordData();
		cobCategory = new javax.swing.JComboBox(cobCategoryData);
		cobKeyword = new javax.swing.JComboBox(cobKeywordData);
		
		JLabel lblQueryType = new JLabel(paragraph.getString("TYPE"));
		JLabel lblQueryDate =  new JLabel(paragraph.getString("DATE"));
		//JLabel lblQueryCategory =  new JLabel(paragraph.getString("CATEGORY"));
		JLabel lblQueryCategory =  new JLabel("Category");
		//JLabel lblQueryKeyword =  new JLabel(paragraph.getString("KEYWORD"));
		JLabel lblQueryKeyword =  new JLabel("Keyword");
		
		dateComboBox = new cc.johnwu.date.DateComboBox();
		cbxDate = new JCheckBox("Enable");
		cbxDate.setSelected(true);
		
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
		
		btnQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueryActionPerformed(evt);
            }
        });
		
		btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
		
		btnEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnterActionPerformed(evt);
            }
        });
		
		//String s[]={"guid", "p_no", paragraph.getString("STATUS")};
		String s[]={"guid", "item_guid", "p_no", "type", "category", "keyword", "note", "create_time"};
		imageTableModel.setColumnIdentifiers(s);
		
		tbImage.setModel(imageTableModel);
		tbImage.setRowHeight(30);
		tbImage.setAutoCreateRowSorter(true);
		reloadImageTable(imageTableModel, "", "", "", "");

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
                            .addComponent(lblQueryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQueryCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(cobCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                         )
                    )
               )
          )
        );
		jPanelLTLayout.setVerticalGroup(
				jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQueryType)
                    .addComponent(cobType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(10)
                .addGroup(jPanelLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQueryCategory)
                    .addComponent(cobCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(lblQueryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQueryKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        )
                        //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    		.addGroup(jPanelRTLayout.createSequentialGroup()
                                .addComponent(dateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbxDate)
                            )
                            .addComponent(cobKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                         )
                         .addContainerGap(350, Short.MAX_VALUE)
                    )
               ))
        );
		jPanelRTLayout.setVerticalGroup(
				jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQueryDate)
                    .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxDate)
                    )
                )
                .addGap(10)
                .addGroup(jPanelRTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQueryKeyword)
                    .addComponent(cobKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
		
		javax.swing.GroupLayout jPanelRLayout = new javax.swing.GroupLayout(panelRight);
		panelRight.setLayout(jPanelRLayout);
		jPanelRLayout.setHorizontalGroup(
				jPanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            		.addComponent(btnQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ))
        );
		jPanelRLayout.setVerticalGroup(
				jPanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(10)
                .addGroup(jPanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(10)
                .addGroup(jPanelRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addContainerGap())
        );
		
		javax.swing.GroupLayout jPanelLeftLayout = new javax.swing.GroupLayout(panelLeft);
		panelLeft.setLayout(jPanelLeftLayout);
		jPanelLeftLayout.setHorizontalGroup(
				jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
				.addComponent(paneleftTop, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGap(20)
				.addComponent(panelRightTop, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			)
			.addGroup(Alignment.TRAILING, jPanelLeftLayout.createSequentialGroup()
				.addComponent(panelBottom, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			)
        );
		jPanelLeftLayout.setVerticalGroup(
				jPanelLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLeftLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(jPanelLeftLayout.createParallelGroup(Alignment.LEADING, false)
					.addComponent(panelRightTop, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(paneleftTop, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panelBottom, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(panelLeft, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelRight, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap()
				)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panelLeft, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelRight, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					)
					.addContainerGap()
				)
		);
		contentPane.setLayout(gl_contentPane);
		
		pack();
	}

	/**
	 * Create the frame.
	 */
	public Frm_ImageMgmt() {
		initComponents();
		addWindowListener(new WindowAdapter() {                                // 畫面關閉原視窗enable
            @Override
            public void windowClosing(WindowEvent windowevent) {
                btnCloseActionPerformed(null);
            }
        });
		//setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		initLanguage();
	}
	
	private void btnEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        //new main.Frm_Main().setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_btn_CloseActionPerformed
	
	private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        new main.Frm_Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_CloseActionPerformed
	
	private void btnQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        //new main.Frm_Main().setVisible(true);
        //this.dispose();
		String date = (cbxDate.isSelected()? dateComboBox.getValue() : "");
		String type = ((TwoColumeClass) cobType.getSelectedItem()).col1;
		String categoryID = ((TwoColumeClass) cobCategory.getSelectedItem()).col1;
		String keyword = ((TwoColumeClass) cobKeyword.getSelectedItem()).col2;
		
		reloadImageTable(imageTableModel, date, type, categoryID, keyword); 
		
    }//GEN-LAST:event_btn_CloseActionPerformed
	
}
