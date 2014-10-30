package codemaintenance;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import main.Frm_Main;
import multilingual.Language;
import cc.johnwu.sql.DBC;
import common.TabTools;
import errormessage.StoredErrorMessage;

public class Frm_Material extends JFrame {
	/*private class DivisionClass {
		public String guid;
		public String name;
		public DivisionClass(String _guid, String _name) {
			guid = _guid;
			name = _name;
		}
		public String toString()  
        {  
            return name;  
        } 
	}*/
	
/*    class DivisionComboRenderer extends BasicComboBoxRenderer
    {
        public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus)
        {
            super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

            if (value != null)
            {
                DivisionClass item = (DivisionClass)value;
                setText( item.name );
            }

            if (index == -1)
            {
            	DivisionClass item = (DivisionClass)value;
                setText( "" + item.name );
            }
            return this;
        }
    }
*/	
	private static final long serialVersionUID = 1L;
//	private long REFRASHTIME = 1000; // 自度刷新跨號資訊時間
//	private RefreshBedList m_RefreshBedList;
//	private Thread m_Clock;
	//private Vector<DivisionClass> DivisionData;
	
	/* 多國語言變數 */
	private Language paragraph = Language.getInstance();
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	// LastSelectRow 最後選擇行號　　SysName　系統名
	public Frm_Material() {
		initComponents();
		//initWorkList();
		initLanguage();
		/*if (tab_BedList.getRowCount() != 0) {
			btn_Enter.setEnabled(true);
			tab_BedList.addRowSelectionInterval(0, 0);
			tab_BedList.changeSelection(LastSelectRow, LastSelectRow, false, false);
		}*/
		initLanguage();
		init();
	}

	public class ForcedListSelectionModel extends DefaultListSelectionModel {

	    public ForcedListSelectionModel () {
	        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	    @Override
	    public void clearSelection() {
	    }

	    @Override
	    public void removeSelectionInterval(int index0, int index1) {
	    }

	}
    private DefaultTableModel tableModel = new DefaultTableModel(){
    	public boolean isCellEditable(int rowIndex, int columnIndex){
    		if (columnIndex == 0 || columnIndex == 1) {
                return false;
            } else {
                return true;
            }
    	}
   	};
   	
	// 初始化
	public void initWorkList() {
		//this.setTitle("Bed list");
		//lab_Name.setText("Staff");

		//this.setExtendedState(Frm_BedList.MAXIMIZED_BOTH); // 最大化
		//this.setLocationRelativeTo(this);
		
		//this.tab_BedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // table不可按住多選
		
//		this.m_RefreshBedList = new RefreshBedList(this.tab_BedList, REFRASHTIME);
//		m_RefreshBedList.setParentFrame(this);
//		this.m_RefreshBedList.start();
/*		this.m_Clock = new Thread() { // Clock
			@Override
			@SuppressWarnings("static-access")
			public void run() {
				try {
					while (true) {
						lab_SystemTime.setText(new SimpleDateFormat(
								"MM/dd/yyyy HH:mm:ss").format(Calendar
								.getInstance().getTime()));
						//showVisitsCount();
						this.sleep(500);
					}
				} catch (InterruptedException e) {
					ErrorMessage.setData(
							"BedMgmt",
							"Frm_BedList",
							"initWorkList() - run()",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
				}
			}
		};
		this.m_Clock.start();
		*/


	}

	/*public void getDivisionData() {
		//DivisionData
		String sql = "SELECT * FROM policlinic WHERE status = 'N'";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            //String[] rowData = new String[5];
            while(rs.next()){
            	DivisionData.add(new DivisionClass(rs.getString("guid"), rs.getString("name")));
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Material.class.getName()).log(Level.SEVERE, null, ex);
         }
	}*/
	@SuppressWarnings("deprecation")
	private void initLanguage() {
		//this.lab_poli.setText(paragraph.getString("POLI"));
		this.btn_Close.setText(paragraph.getString("CLOSE"));
		this.btn_Save.setText(paragraph.getString("SAVE"));
		this.btn_Add.setText(paragraph.getString("ADD"));
		this.btn_Delete.setText(paragraph.getString("DELETE"));
		this.btn_Import.setText(paragraph.getString("IMPORTCSV"));
	}
	
	public void init() {
		reloadList(tableModel);
	}

	private void initComponents() {

		//DivisionData = new Vector<DivisionClass>();
		
		pan_Center = new javax.swing.JPanel();
		//pan_Top = new javax.swing.JPanel();
		//lab_Name = new javax.swing.JLabel();
		//lab_poli = new javax.swing.JLabel();
		//txt_Name = new javax.swing.JTextField();
		//txt_Poli = new javax.swing.JTextField();
		//lab_SystemTime = new javax.swing.JLabel();
		pan_Right = new javax.swing.JPanel();
		btn_Close = new javax.swing.JButton();
		btn_Save = new javax.swing.JButton();
		btn_Add = new javax.swing.JButton();
		btn_Delete = new javax.swing.JButton();
		btn_Import = new javax.swing.JButton();

		//this.lab_Name.setText("Staff");
		//this.txt_Name.setText(UserInfo.getUserName());
		//this.txt_Poli.setText(UserInfo.getUserPoliclinic());
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Material Code Maintenance");

		span_List = new javax.swing.JScrollPane();
		tab_List = new javax.swing.JTable();
		String s[]={"changed", "guid", "name", "description", "unit", "unit_cost", "unit_price", "effective"};
		tableModel.setColumnIdentifiers(s);
		tab_List.setSelectionModel(new ForcedListSelectionModel());
		tab_List.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tab_List.setRowHeight(25);
		//tab_BedList.getTableHeader().setReorderingAllowed(false);
		tab_List.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_BedListMouseClicked(evt);
			}
		});
		tab_List.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_BedListKeyPressed(evt);
			}
		});
		span_List.setViewportView(tab_List);
		tab_List.setModel(tableModel);

		//getDivisionData();
		
		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable
			@Override
			public void windowClosing(WindowEvent windowevent) {
				mnit_CloseActionPerformed(null);
			}
		});
		
		javax.swing.GroupLayout pan_CenterLayout = new javax.swing.GroupLayout(
				pan_Center);
		pan_Center.setLayout(pan_CenterLayout);
		pan_CenterLayout
				.setHorizontalGroup(pan_CenterLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_CenterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												span_List,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												601, Short.MAX_VALUE)
										.addContainerGap()));
		pan_CenterLayout.setVerticalGroup(pan_CenterLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				pan_CenterLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(span_List,
								javax.swing.GroupLayout.DEFAULT_SIZE, 434,
								Short.MAX_VALUE).addContainerGap()));

		//lab_Name.setText("Doctor");

		//lab_poli.setText("Department");

		//txt_Name.setEditable(false);

		//txt_Poli.setEditable(false);

		//lab_SystemTime.setFont(new java.awt.Font("UnDotum", 0, 18));
		//lab_SystemTime.setText("-----");
/*
		javax.swing.GroupLayout pan_TopLayout = new javax.swing.GroupLayout(pan_Top);
		pan_Top.setLayout(pan_TopLayout);
		pan_TopLayout
			.setHorizontalGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(pan_TopLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							//.addComponent(lab_Wait)
							.addComponent(lab_Name)
							//.addComponent(lab_Date)
							)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(pan_TopLayout.createSequentialGroup()
								.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addComponent(txt_Name,javax.swing.GroupLayout.PREFERRED_SIZE,160,javax.swing.GroupLayout.PREFERRED_SIZE)
									//.addComponent(lab_WaitCount,njavax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
											)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addComponent(lab_poli)
									//.addComponent(lab_Finish)
								)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
									.addGroup(pan_TopLayout.createSequentialGroup()
										.addComponent(txt_Poli,javax.swing.GroupLayout.PREFERRED_SIZE,160,javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										//.addComponent(lab_SystemTime,javax.swing.GroupLayout.PREFERRED_SIZE,207,javax.swing.GroupLayout.PREFERRED_SIZE)
										)
									//.addComponent(lab_FinishCount, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
								)
							)
							.addGroup(pan_TopLayout.createSequentialGroup()
								//.addComponent(dateComboBox,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
							))
						.addContainerGap(137, Short.MAX_VALUE)
					)
			);
		pan_TopLayout
			.setVerticalGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(pan_TopLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(lab_Name)
						.addComponent(txt_Name,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(lab_poli)
						.addComponent(txt_Poli,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
						//.addComponent(lab_SystemTime)
						)
					//.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					//.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						//.addComponent(lab_Wait)
						//.addComponent(lab_Finish)
						//.addComponent(lab_FinishCount)
						//.addComponent(lab_WaitCount)
					//	)
					//.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					//.addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						//.addComponent(lab_Date)
						//.addComponent(dateComboBox,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,javax.swing.GroupLayout.PREFERRED_SIZE)
					//	)
					.addGap(12, 12, 12)
				)
			);
*/
		//btn_Close.setText("Close");
		btn_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CloseActionPerformed(evt);
			}
		});
		
		btn_Save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_SaveActionPerformed(evt);
			}
		});
		
		btn_Add.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_AddActionPerformed(evt);
			}
		});
		
		btn_Delete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_DeleteActionPerformed(evt);
			}
		});
		
		btn_Import.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_ImportActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_RightLayout.setHorizontalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(pan_RightLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_Add, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Save, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Delete, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Import, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Close, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
					.addContainerGap())
		);
		pan_RightLayout.setVerticalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btn_Add)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Save)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Delete)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Import)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Close)
					.addContainerGap(335, Short.MAX_VALUE))
		);
		pan_Right.setLayout(pan_RightLayout);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												//.addComponent(
												//		pan_Top,
												//		javax.swing.GroupLayout.Alignment.LEADING,
												//		javax.swing.GroupLayout.DEFAULT_SIZE,
												//		javax.swing.GroupLayout.DEFAULT_SIZE,
												//		Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		pan_Center,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		pan_Right,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								//.addContainerGap()
								//.addComponent(pan_Top,
								//		javax.swing.GroupLayout.PREFERRED_SIZE,
								//		99,
								//		javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														pan_Center,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														pan_Right,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/*private void setCloumnWidth(javax.swing.JTable tab) {
		// 設定column寬度
		TableColumn columnDesc = tab.getColumnModel().getColumn(3);
		TableColumn columnPoli = tab.getColumnModel().getColumn(4);
		TableColumn columnStatus = tab.getColumnModel().getColumn(5);
		columnDesc.setPreferredWidth(300);
		columnPoli.setPreferredWidth(200);
		columnStatus.setPreferredWidth(100);
		tab.setRowHeight(30);
	}*/
   	
	/*public void setUpDivisionColumn(javax.swing.JTable table, TableColumn sportColumn) {
		//Set up the editor for the sport cells.
		javax.swing.JComboBox comboBox = new javax.swing.JComboBox(DivisionData);
		//comboBox.setRenderer( new DivisionComboRenderer() );
		comboBox.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox)e.getSource();
		        DivisionClass division = (DivisionClass)comboBox.getSelectedItem();
		        if(division != null) {
			        tab_List.setValueAt(division.name, tab_List.getSelectedRow(), tab_List.getSelectedColumn());
			        tab_List.setValueAt(division.guid, tab_List.getSelectedRow(), 2);
			        tab_List.setValueAt("Y", tab_List.getSelectedRow(), 0);
		        }
			}
			
		} );
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
	
	public void setUpStatusColumn(javax.swing.JTable table, TableColumn sportColumn) {
		//Set up the editor for the sport cells.
		javax.swing.JComboBox comboBox = new javax.swing.JComboBox();
		comboBox.addItem("Normal");
		comboBox.addItem("Disabled");
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(tab_BedList.getSelectedRow());
				if(tab_List.getSelectedRow() != -1)
					tab_List.setValueAt("Y", tab_List.getSelectedRow(), 0);
			}
			
		});
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}*/
	
   	private void reloadList(DefaultTableModel dtm) {
   		dtm.setRowCount(0);
   		String sql = "SELECT * FROM material_code";
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            String[] rowData = new String[8];
            while(rs.next()){
            	rowData[0] = "N";
            	rowData[1] = rs.getString("guid");
            	rowData[2] = rs.getString("name");
            	rowData[3] = rs.getString("description");
            	rowData[4] = rs.getString("unit");
            	rowData[5] = rs.getString("unit_cost");
            	rowData[6] = rs.getString("unit_price");
            	rowData[7] = rs.getString("effective");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Material.class.getName()).log(Level.SEVERE, null, ex);
         }
        // setup status combobox
   		//setUpDivisionColumn(tab_List, tab_List.getColumnModel().getColumn(4));
        //setUpStatusColumn(tab_List, tab_List.getColumnModel().getColumn(5));
        //setCloumnWidth(tab_List);
        
        TabTools.setHideColumn(tab_List, 0);
        TabTools.setHideColumn(tab_List, 1);
   	}
   	
	private void mnit_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_CloseActionPerformed
		new Frm_Main().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_mnit_CloseActionPerformed

	private void tab_BedListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_BedListMouseClicked
		//if (evt.getClickCount() == 2 ) {
		tab_List.setValueAt("Y", tab_List.getSelectedRow(), 0);
		//	System.out.println("22");
		//}
	}// GEN-LAST:event_tab_BedListMouseClicked

	@SuppressWarnings("deprecation")
	private void tab_BedListKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tab_BedListKeyPressed
		//if (this.tab_BedList.getRowCount() > 0) {
		//	this.btn_Save.setEnabled(true);
		//}
		//System.out.println("11");
		tab_List.setValueAt("Y", tab_List.getSelectedRow(), 0);
	}// GEN-LAST:event_tab_BedListKeyPressed

	private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {
		try {
    	  	String sql = "INSERT INTO `material_code` (`guid`, `effective`) VALUES (uuid(), 0)";
            DBC.executeUpdate(sql);
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Material.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	        return;
	    }
    	reloadList(tableModel);
    	JOptionPane.showMessageDialog(null, paragraph.getString("ADDCOMPLETE"));
	}
	
	private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {
		
		String guid = (String) tab_List.getValueAt(tab_List.getSelectedRow(), 1);
		try {
    	  	String sql = "DELETE FROM `material_code` WHERE guid ='" + guid + "'";
            DBC.executeUpdate(sql);
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Material.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	        return;
	    }
    	reloadList(tableModel);
    	JOptionPane.showMessageDialog(null, paragraph.getString("DELETECOMPLETE"));
	}
	
	private void btn_ImportActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CloseActionPerformed
		//mnit_CloseActionPerformed(null);
		String filePath;
		JFileChooser jFileChooser1 = new javax.swing.JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv files", "csv");
        jFileChooser1.setFileFilter(filter);
		int returnVal = jFileChooser1.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			filePath = jFileChooser1.getSelectedFile().getAbsolutePath();
			try {
				String sql = "LOAD DATA LOCAL INFILE '" + filePath
						+ "' INTO TABLE material_code FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES";
				DBC.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, paragraph.getString("WOUND_CASE_CONFIRM_SAVE_FAIL"));
			}
			JOptionPane.showMessageDialog(null, paragraph.getString("WOUND_CASE_CONFIRM_SAVE_SUCC"));
		}
	}// GEN-LAST:event_btn_CloseActionPerformed
	
	private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel dtm =(DefaultTableModel) tab_List.getModel();
    	for ( int i = 0 ;i < dtm.getRowCount(); i++){
    		String changed = (String) dtm.getValueAt(i, 0);
    		if(changed.compareTo("Y") == 0) {
    			String guid = (String) dtm.getValueAt(i, 1);
    			String name = (String) dtm.getValueAt(i, 2);
    			String description = (String) dtm.getValueAt(i, 3);
    			String unit = (String) dtm.getValueAt(i, 4);
    			float unit_cost = dtm.getValueAt(i, 5) == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 5));
    			float unit_price = dtm.getValueAt(i, 6) == null ? 0 : Float.parseFloat((String) dtm.getValueAt(i, 6));
    			String effectiveStr = (String) dtm.getValueAt(i, 7);
    			int effective = 1;
    			if(effectiveStr.compareTo("1") != 0) effective = 0;
    			
    			try {
	    			String sql = "UPDATE material_code SET name = ?, description = ?, "
	    					+ " unit = ?, unit_cost = ?, unit_price = ?, effective = ? "
	    					+ " WHERE guid = ? ";
					Connection conn = DBC.getConnectionExternel();
					PreparedStatement prestate = conn.prepareStatement(sql); //先建立一個 SQL 語句並回傳一個 PreparedStatement 物件
					if(name != null) prestate.setString(1, name); else prestate.setNull(1, java.sql.Types.VARCHAR);
					if(description != null) prestate.setString(2, description); else prestate.setNull(2, java.sql.Types.BLOB);
					if(unit != null) prestate.setString(3, unit); else prestate.setNull(3, java.sql.Types.VARCHAR);
					if(unit_cost != 0) prestate.setFloat(4, unit_cost); else prestate.setNull(4, java.sql.Types.FLOAT);
					if(unit_price != 0) prestate.setFloat(5, unit_price); else prestate.setNull(5, java.sql.Types.FLOAT);
					prestate.setInt(6, effective);
					prestate.setString(7, guid); 
					prestate.executeUpdate();  //真正執行
					DBC.closeConnectionExternel(conn);
    		    } catch (SQLException ex) {
    		        Logger.getLogger(Frm_Material.class.getName()).log(Level.SEVERE, null, ex);
    		        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
    		        return;
    		    }
    		}
    	}

    	reloadList(tableModel);
    	JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));
	}
	
	private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CloseActionPerformed
		mnit_CloseActionPerformed(null);
	}// GEN-LAST:event_btn_CloseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btn_Close;
	//private javax.swing.JButton btn_Enter;
	private javax.swing.JButton btn_Save;
	private javax.swing.JButton btn_Add;
	private javax.swing.JButton btn_Delete;
	private javax.swing.JButton btn_Import;
	//private cc.johnwu.date.DateComboBox dateComboBox;
	//private javax.swing.JLabel lab_Date;
	//private javax.swing.JLabel lab_Name;
	//private javax.swing.JLabel lab_SystemTime;
	//private javax.swing.JLabel lab_poli;
	private javax.swing.JPanel pan_Center;
	private javax.swing.JPanel pan_Right;
	//private javax.swing.JPanel pan_Top;
	private javax.swing.JScrollPane span_List;
	private javax.swing.JTable tab_List;
	//private javax.swing.JTextField txt_Name;
	//private javax.swing.JTextField txt_Poli;
}
