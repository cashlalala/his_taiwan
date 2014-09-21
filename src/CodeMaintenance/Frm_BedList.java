package CodeMaintenance;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import system.Frm_Setting;
import main.Frm_Main;
import multilingual.Language;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import common.TabTools;
import errormessage.StoredErrorMessage;

public class Frm_BedList extends JFrame {
	private class DivisionClass {
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
	}
	
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
	private Vector<DivisionClass> DivisionData;
	
	/* 多國語言變數 */
	private Language paragraph = Language.getInstance();
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	// LastSelectRow 最後選擇行號　　SysName　系統名
	public Frm_BedList(int LastSelectRow) {
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
    private DefaultTableModel bedTableModel = new DefaultTableModel(){
    	public boolean isCellEditable(int rowIndex, int columnIndex){
    		if (columnIndex == 3 || columnIndex == 4 || columnIndex == 5) {
                return true;
            } else {
                return false;
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

	public void getDivisionData() {
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
             Logger.getLogger(Frm_BedList.class.getName()).log(Level.SEVERE, null, ex);
         }
	}
	@SuppressWarnings("deprecation")
	private void initLanguage() {
		this.lab_poli.setText(paragraph.getString("POLI"));
		this.btn_Close.setText(paragraph.getString("CLOSE"));
		this.btn_Save.setText(paragraph.getString("SAVE"));
		this.btn_Add.setText(paragraph.getString("ADD"));
	}
	
	public void init() {
		reloadBedList(bedTableModel);
	}

	private void initComponents() {

		DivisionData = new Vector<DivisionClass>();
		
		pan_Center = new javax.swing.JPanel();
		pan_Top = new javax.swing.JPanel();
		lab_Name = new javax.swing.JLabel();
		lab_poli = new javax.swing.JLabel();
		txt_Name = new javax.swing.JTextField();
		txt_Poli = new javax.swing.JTextField();
		//lab_SystemTime = new javax.swing.JLabel();
		pan_Right = new javax.swing.JPanel();
		btn_Close = new javax.swing.JButton();
		btn_Save = new javax.swing.JButton();
		btn_Add = new javax.swing.JButton();

		this.lab_Name.setText("Staff");
		this.txt_Name.setText(UserInfo.getUserName());
		this.txt_Poli.setText(UserInfo.getUserPoliclinic());
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Bed Management");

		span_BedList = new javax.swing.JScrollPane();
		tab_BedList = new javax.swing.JTable();
		String s[]={"changed", "guid", "division_guid", "description", "Division", "Status"};
		bedTableModel.setColumnIdentifiers(s);
		tab_BedList.setSelectionModel(new ForcedListSelectionModel());
		tab_BedList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tab_BedList.setRowHeight(25);
		//tab_BedList.getTableHeader().setReorderingAllowed(false);
		tab_BedList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_BedListMouseClicked(evt);
			}
		});
		tab_BedList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_BedListKeyPressed(evt);
			}
		});
		span_BedList.setViewportView(tab_BedList);
		tab_BedList.setModel(bedTableModel);

		getDivisionData();
		
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
												span_BedList,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												601, Short.MAX_VALUE)
										.addContainerGap()));
		pan_CenterLayout.setVerticalGroup(pan_CenterLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				pan_CenterLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(span_BedList,
								javax.swing.GroupLayout.DEFAULT_SIZE, 434,
								Short.MAX_VALUE).addContainerGap()));

		lab_Name.setText("Doctor");

		lab_poli.setText("Department");

		txt_Name.setEditable(false);

		txt_Poli.setEditable(false);

		//lab_SystemTime.setFont(new java.awt.Font("UnDotum", 0, 18));
		//lab_SystemTime.setText("-----");

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

		//btn_Enter.setText("Enter");
		//btn_Enter.setEnabled(false);
		/*btn_Enter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_EnterActionPerformed(evt);
			}
		});*/

		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_RightLayout.setHorizontalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(pan_RightLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_Add, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Save, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
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

	private void setCloumnWidth(javax.swing.JTable tab) {
		// 設定column寬度
		TableColumn columnDesc = tab.getColumnModel().getColumn(3);
		TableColumn columnPoli = tab.getColumnModel().getColumn(4);
		TableColumn columnStatus = tab.getColumnModel().getColumn(5);
		columnDesc.setPreferredWidth(300);
		columnPoli.setPreferredWidth(200);
		columnStatus.setPreferredWidth(100);
		tab.setRowHeight(30);
	}
	
	protected String getSQLString() {
		String sql = "SELECT 'N' as 'changed', A.guid, B.guid as 'division_guid', A.description, B.name as 'Division', A.status as 'Status' "
				+ " FROM bed_code A "
				+ " LEFT JOIN policlinic B ON A.poli_guid = B.guid "
				+ " order by A.status desc, A.description asc ";
		return sql;
	}
   	
	public void setUpDivisionColumn(javax.swing.JTable table, TableColumn sportColumn) {
		//Set up the editor for the sport cells.
		javax.swing.JComboBox comboBox = new javax.swing.JComboBox(DivisionData);
		//comboBox.setRenderer( new DivisionComboRenderer() );
		comboBox.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox)e.getSource();
		        DivisionClass division = (DivisionClass)comboBox.getSelectedItem();
		        if(division != null) {
			        tab_BedList.setValueAt(division.name, tab_BedList.getSelectedRow(), tab_BedList.getSelectedColumn());
			        tab_BedList.setValueAt(division.guid, tab_BedList.getSelectedRow(), 2);
			        tab_BedList.setValueAt("Y", tab_BedList.getSelectedRow(), 0);
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
				if(tab_BedList.getSelectedRow() != -1)
					tab_BedList.setValueAt("Y", tab_BedList.getSelectedRow(), 0);
			}
			
		});
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
	
   	private void reloadBedList(DefaultTableModel dtm) {
   		dtm.setRowCount(0);
   		String sql = getSQLString();
   		try{
            ResultSet rs = DBC.executeQuery(sql);
            String[] rowData = new String[6];
            while(rs.next()){
            	rowData[0] = rs.getString("changed");
            	rowData[1] = rs.getString("guid");
            	rowData[2] = rs.getString("division_guid");
            	rowData[3] = rs.getString("description");
            	rowData[4] = rs.getString("Division");
            	rowData[5] = (rs.getString("Status").compareTo("N") == 0 ? "Normal" : "Disabled");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_BedList.class.getName()).log(Level.SEVERE, null, ex);
         }
        // setup status combobox
   		setUpDivisionColumn(tab_BedList, tab_BedList.getColumnModel().getColumn(4));
        setUpStatusColumn(tab_BedList, tab_BedList.getColumnModel().getColumn(5));
        setCloumnWidth(tab_BedList);
        
        TabTools.setHideColumn(tab_BedList, 0);
        TabTools.setHideColumn(tab_BedList, 1);
        TabTools.setHideColumn(tab_BedList, 2);
   	}
   	
	private void mnit_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_CloseActionPerformed
		new Frm_Main().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_mnit_CloseActionPerformed

	private void tab_BedListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_BedListMouseClicked
		//if (evt.getClickCount() == 2 ) {
			tab_BedList.setValueAt("Y", tab_BedList.getSelectedRow(), 0);
		//	System.out.println("22");
		//}
	}// GEN-LAST:event_tab_BedListMouseClicked

	@SuppressWarnings("deprecation")
	private void tab_BedListKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tab_BedListKeyPressed
		//if (this.tab_BedList.getRowCount() > 0) {
		//	this.btn_Save.setEnabled(true);
		//}
		//System.out.println("11");
		tab_BedList.setValueAt("Y", tab_BedList.getSelectedRow(), 0);
	}// GEN-LAST:event_tab_BedListKeyPressed

	private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {
		try {
    	  	String sql = "INSERT INTO `bed_code` (`guid`, `description`, `status`) VALUES (uuid(), '', 'N')";
            DBC.executeUpdate(sql);
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_BedList.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	    }
    	reloadBedList(bedTableModel);
    	JOptionPane.showMessageDialog(null, paragraph.getString("ADDCOMPLETE"));
	}
	
	private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		DefaultTableModel dtm =(DefaultTableModel) tab_BedList.getModel();
    	for ( int i = 0 ;i < dtm.getRowCount(); i++){
    		String changed = (String) dtm.getValueAt(i, 0);
    		if(changed.compareTo("Y") == 0) {
    			String guid = (String) dtm.getValueAt(i, 1);
    			String division_guid = (String) dtm.getValueAt(i, 2);
    			String description = (String) dtm.getValueAt(i, 3);
    			String status = (String) dtm.getValueAt(i, 5);
    			
    			if(status.compareTo("Normal") == 0) status = "N";
      	      		else status = "D";
    			
    			try {
    	    	  	String sql = "UPDATE bed_code SET " +
        	            	"description = '"+description+"', " +
        	            	"poli_guid = '"+division_guid+"', " +
        	            	"status = '"+status+"' WHERE guid = '" + guid + "'";
    	            DBC.executeUpdate(sql);
    		    } catch (SQLException ex) {
    		        Logger.getLogger(Frm_BedList.class.getName()).log(Level.SEVERE, null, ex);
    		        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
    		    }
    		}
    	}

    	reloadBedList(bedTableModel);
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
	//private cc.johnwu.date.DateComboBox dateComboBox;
	//private javax.swing.JLabel lab_Date;
	private javax.swing.JLabel lab_Name;
	//private javax.swing.JLabel lab_SystemTime;
	private javax.swing.JLabel lab_poli;
	private javax.swing.JPanel pan_Center;
	private javax.swing.JPanel pan_Right;
	private javax.swing.JPanel pan_Top;
	private javax.swing.JScrollPane span_BedList;
	private javax.swing.JTable tab_BedList;
	private javax.swing.JTextField txt_Name;
	private javax.swing.JTextField txt_Poli;
}
