package system;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import multilingual.Language;
import cc.johnwu.sql.DBC;


/**
 *
 * @author Steven
 */
public class Frm_Setting extends javax.swing.JFrame {

	private Language paragraph = Language.getInstance();
	
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
	
	class SharedListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) { 
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
 
            //int firstIndex = e.getFirstIndex();
            //int lastIndex = e.getLastIndex();
            boolean isAdjusting = e.getValueIsAdjusting(); 
            if(isAdjusting) return;
            
/*            System.out.println("Event for indexes "
                          + firstIndex + " - " + lastIndex
                          + "; isAdjusting is " + isAdjusting
                          + "; selected indexes:");
*/ 
            if (lsm.isSelectionEmpty()) {
//            	System.out.println(" <none>");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                    	//System.out.println(" " + i);
                    	reloadPoliRoomTable(poliRoomTableModel, tb_division.getValueAt(i, 0).toString(), tb_division.getValueAt(i, 1).toString());
                    }
                }
            }

        }
    }
	
	public Frm_Setting() {
        initComponents();
        addWindowListener(new WindowAdapter() {                                // 畫面關閉原視窗enable
            @Override
            public void windowClosing(WindowEvent windowevent) {
                btn_CloseActionPerformed(null);
            }
        });
        this.setExtendedState(Frm_Setting.MAXIMIZED_BOTH);  // 最大化
        this.setLocationRelativeTo(this); // 置中
        btn_Save.setEnabled(false);
        init();

    }

	public void setUpStatusColumn(javax.swing.JTable table, TableColumn sportColumn) {
		//Set up the editor for the sport cells.
		javax.swing.JComboBox comboBox = new javax.swing.JComboBox();
		comboBox.addItem("Normal");
		comboBox.addItem("Disabled");
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
		
    private DefaultTableModel divisionTableModel = new DefaultTableModel(){
    	public boolean isCellEditable(int rowIndex, int columnIndex){
    		if (columnIndex == 1 || columnIndex == 2) {
                return true;
            } else {
                return false;
            }
    	}
   	};
   	
   	private DefaultTableModel poliRoomTableModel = new DefaultTableModel(){
    	public boolean isCellEditable(int rowIndex, int columnIndex){
    		if (columnIndex == 2 || columnIndex == 3) {
                return true;
            } else {
                return false;
            }
    	}
   	};
   	
   	private void reloadPoliRoomTable(DefaultTableModel dtm, String divisionGUID, String divisionName) {
   		dtm.setRowCount(0);
       	
   		try{
            ResultSet rs = DBC.executeQuery("SELECT * FROM poli_room where poli_guid = '" + divisionGUID + "' order by name asc");
            String[] rowData = new String[4];
            while(rs.next()){
            	rowData[0] = rs.getString("guid");
            	rowData[1] = rs.getString("poli_guid");
            	rowData[2] = rs.getString("name");
            	rowData[3] = (rs.getString("status").compareTo("N") == 0 ? "Normal" : "Disabled");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        // setup status combobox
        setUpStatusColumn(tb_poliRoom, tb_poliRoom.getColumnModel().getColumn(3));
        
        // refresh btn_AddRoom text
        btn_AddRoom.setText(paragraph.getString("ADDCLINICFOR") + " " + divisionName);
        
        // refresh jLabelRoom text
        jLabelRoom.setText(paragraph.getString("CLINICLISTOFDIVISION") + " " + divisionName);
   	}
   	
   	private void reloadDivisionTable(DefaultTableModel dtm) {
   		dtm.setRowCount(0);

   		try{
            ResultSet rs = DBC.executeQuery("SELECT * FROM policlinic order by name asc");
            String[] rowData = new String[3];
            while(rs.next()){
            	rowData[0] = rs.getString("guid");
            	rowData[1] = rs.getString("name");
            	rowData[2] = (rs.getString("status").compareTo("N") == 0 ? "Normal" : "Disabled");
            	//rowData[3] = rs.getString("room_num");
            	dtm.addRow(rowData);
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
         }
        // setup status combobox
        setUpStatusColumn(tb_division, tb_division.getColumnModel().getColumn(2));
   	}
   	
    // 初始化
    private void init() {
    	initLanguage() ;
    	cob_Language.setModel(new javax.swing.DefaultComboBoxModel(
	                new String[] { "en", "fr", "es", "tw"}
	            )
	    );
    	cob_ICDVersion.setModel(new javax.swing.DefaultComboBoxModel(
	                new String[] { "ICD-9", "ICD-10"}
	            )
	    );
    	reloadSystemSetting();
    	reloadShiftSetting();
    	reloadPriceSetting();
        reloadDivisionTable(divisionTableModel);
        reloadPoliRoomTable(poliRoomTableModel, "", "");
    }
    
    private void initLanguage() {
    	this.btn_SaveSystemSetting.setText(paragraph.getString("SAVE"));
    	this.btn_ReloadSystemSetting.setText(paragraph.getString("RELOAD"));
        this.btn_SelectHosIcon.setText(paragraph.getString("CHOOSE") + "...");
        this.jLabelHosCode.setText(paragraph.getString("HOSPITALCODE") + " :");
        this.jLabelHosName.setText(paragraph.getString("HOSPITALNAME") + " :");
        this.jLabelHosPhone.setText(paragraph.getString("HOSPITALPHONE") + " :");
        this.jLabelHosAddr.setText(paragraph.getString("HOSPITALADDRESS") + " :");
        this.jLabelHosIcon.setText(paragraph.getString("HOSPITALICON") + " :");
        this.jLabelLanguage.setText(paragraph.getString("SYSTEMLANGUAGE") + " :");
        this.jLabelICDVersion.setText(paragraph.getString("ICDVERSION") + " :");
        this.jLabel1.setText(paragraph.getString("MORNING") + " " + paragraph.getString("SHIFT") + " : " + paragraph.getString("FROM") + " ");
        this.jLabel2.setText(paragraph.getString("AFTERNOON") + " " + paragraph.getString("SHIFT") + " : " + paragraph.getString("FROM") + " ");
        this.jLabel3.setText(paragraph.getString("NIGHT") + " " + paragraph.getString("SHIFT") + " : " + paragraph.getString("FROM") + " ");
        this.jLabel8.setText(paragraph.getString("TO"));
        this.jLabel9.setText(paragraph.getString("TO"));
        this.jLabel10.setText(paragraph.getString("TO"));
        this.btn_Save.setText(paragraph.getString("SAVE"));
        this.btn_ReLoad.setText(paragraph.getString("RELOAD"));
        this.jLabelDivision.setText(paragraph.getString("DIVISIONLIST"));
        this.btn_SaveDivisionSetting.setText(paragraph.getString("SAVE"));
        this.btn_ReloadDivisionSetting.setText(paragraph.getString("RELOAD"));
        this.btn_AddDivision.setText(paragraph.getString("AddDivision"));
        this.btn_SavePrice.setText(paragraph.getString("SAVE"));
        this.btn_ReloadPrice.setText(paragraph.getString("RELOAD"));
        this.jLabelPriceRegistration.setText(paragraph.getString("REGISTRATION") + " " + paragraph.getString("PRICE"));
        this.jLabelPriceBed.setText(paragraph.getString("BED") + " " + paragraph.getString("PRICE"));
        this.jLabelPriceDiagnosis.setText(paragraph.getString("DIAGNOSIS") + " " + paragraph.getString("PRICE"));
        this.btn_Close.setText(paragraph.getString("CLOSE"));

   		String s[]={"guid", paragraph.getString("COL_NAME"), paragraph.getString("STATUS")};
   		divisionTableModel.setColumnIdentifiers(s);
        tb_division.getColumnModel().getColumn(0).setMinWidth(0);
        tb_division.getColumnModel().getColumn(0).setMaxWidth(0);
        
   		String s2[]={"guid", "poli_guid", paragraph.getString("COL_NAME"), paragraph.getString("STATUS")};
   		poliRoomTableModel.setColumnIdentifiers(s2);
        tb_poliRoom.getColumnModel().getColumn(0).setMinWidth(0);
        tb_poliRoom.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_poliRoom.getColumnModel().getColumn(1).setMinWidth(0);
        tb_poliRoom.getColumnModel().getColumn(1).setMaxWidth(0);
    }
    
    private ImageIcon scaledIcon(String absolutePath, int width) {
    	String path = "./img/nofile.png";
    	if(absolutePath != null) {
    		File file = new File(absolutePath);
        	if(file.exists()) path = absolutePath;
    	}
        
    	ImageIcon image = new ImageIcon(path);
        Image img = image.getImage();
        int scaledX = width;
        int scaledY = (scaledX * img.getHeight(null))/img.getWidth(null);
        BufferedImage bi = new BufferedImage(scaledX, scaledY, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, scaledX, scaledY, null);
        ImageIcon newIcon = new ImageIcon(bi);
        return newIcon;
    } 
    
    private void reloadSystemSetting() {
    	try{
            ResultSet rs = DBC.executeQuery("SELECT * FROM setting WHERE id = 1");
            if(rs.next()){
            	this.txt_HosCode.setText(rs.getString("hospicalID"));
                this.txt_HosName.setText(rs.getString("hos_name"));
                this.txt_HosPhone.setText(rs.getString("hos_phone"));
                this.txt_HosAddr.setText(rs.getString("hos_address"));
                this.txt_HosIcon.setText(rs.getString("hos_icon_path"));
                for (int i = 0; i < cob_Language.getItemCount(); i++) { 
                    if (rs.getString("language").equals(cob_Language.getItemAt(i).toString() )) {
                    	cob_Language.setSelectedIndex(i);
                        break;
                    }
                }
                for (int i = 0; i < cob_ICDVersion.getItemCount(); i++) { 
                    if (rs.getString("ICDVersion").equals(cob_ICDVersion.getItemAt(i).toString() )) {
                    	cob_ICDVersion.setSelectedIndex(i);
                        break;
                    }
                }
    			jLabelHosIconImg.setIcon(scaledIcon(rs.getString("hos_icon_path"), 40));
    			
    			String langCode = rs.getString("language");
    			Language.getInstance().setLocale(langCode);
    			initLanguage();
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    private void reloadShiftSetting() {
    	try{
            ResultSet rs = DBC.executeQuery("SELECT * FROM setting WHERE id = 1");
            if(rs.next()){
                this.txt_MorningS.setText(rs.getString("morning_shift_s"));
                this.txt_MorningE.setText(rs.getString("morning_shift_e"));
                this.txt_NoonS.setText(rs.getString("noon_shift_s"));
                this.txt_NoonE.setText(rs.getString("noon_shift_e"));
                this.txt_NightS.setText(rs.getString("evening_shift_s"));
                this.txt_NightE.setText(rs.getString("evening_shift_e"));
                this.txt_NightE.setText(rs.getString("evening_shift_e"));
            }
            reLoad();
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    private void reloadDivisionSetting() {
        reloadDivisionTable(divisionTableModel);
        reloadPoliRoomTable(poliRoomTableModel, tb_division.getValueAt(0, 0).toString(), tb_division.getValueAt(0, 1).toString());
    }
    
    private void reloadPriceSetting() {
    	try{
            ResultSet rs = DBC.executeQuery("SELECT * FROM setting WHERE id = 1");
            if(rs.next()){
                this.txt_RegPrice.setText(rs.getString("registration_price"));
                this.txt_BedPrice.setText(rs.getString("bed_price"));
                this.txt_DiagnosisPrice.setText(rs.getString("diagnosis_price"));
            }
         }
         catch (SQLException ex){
             Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    private void reLoad() {
        btn_ReLoad.setEnabled(false);
        btn_Save.setEnabled(false);
    }

    // 判斷藥品輸入的值是否為數字
    public boolean isNumber(String str) {
        if (str.length() ==3 || str.length() == 4) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            } else {
                if (Integer.parseInt(str) < 2400 && Integer.parseInt(str) >= 0) {
                    return true;
                } else {
                    return false;
                }

            }
        }
        return false;

    }

 // 判斷藥品輸入的值是否為數字
    public boolean isFloat(String str) {
    	Pattern pattern = Pattern.compile("[0-9.]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches() || str.trim().equals("")) {
            System.out.println("FALSE");
            return false;
        } else {
            System.out.println("TRUE");
            return true;
        }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_MorningS = new javax.swing.JTextField();
        txt_NoonS = new javax.swing.JTextField();
        txt_NightS = new javax.swing.JTextField();
        txt_NoonE = new javax.swing.JTextField();
        txt_NightE = new javax.swing.JTextField();
        txt_MorningE = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pan_under = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();
        btn_ReLoad = new javax.swing.JButton();
        //btn_Default = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn_EnterHL7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_Pno = new javax.swing.JTextField();
        btn_Close = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Setting");
        setMinimumSize(new java.awt.Dimension(800, 600));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        // start of tab system setting    
        btn_SaveSystemSetting = new javax.swing.JButton();
        btn_ReloadSystemSetting = new javax.swing.JButton();
        btn_SelectHosIcon = new javax.swing.JButton();
        txt_HosCode = new javax.swing.JTextField();
        txt_HosName = new javax.swing.JTextField();
        txt_HosPhone = new javax.swing.JTextField();
        txt_HosAddr = new javax.swing.JTextField();
        txt_HosIcon = new javax.swing.JTextField();
        //txt_Language = new javax.swing.JTextField();
        cob_Language = new javax.swing.JComboBox();
        cob_ICDVersion = new javax.swing.JComboBox();
        jLabelHosCode = new javax.swing.JLabel();
        jLabelHosName = new javax.swing.JLabel();
        jLabelHosPhone = new javax.swing.JLabel();
        jLabelHosAddr = new javax.swing.JLabel();
        jLabelHosIcon = new javax.swing.JLabel();
        jLabelHosIconImg = new javax.swing.JLabel();
        jLabelLanguage = new javax.swing.JLabel();
        jLabelICDVersion = new javax.swing.JLabel();
        pan_SystemSettingButton = new javax.swing.JPanel();
        
        btn_SaveSystemSetting.setEnabled(true);
        btn_ReloadSystemSetting.setEnabled(true);
        btn_SelectHosIcon.setEnabled(true);
        
        btn_ReloadSystemSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	reloadSystemSetting();
            }
        });
        btn_SaveSystemSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveSystemSettingActionPerformed(evt);
            }
        });
        
        btn_SelectHosIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1 = new javax.swing.JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF and PNG Images", "jpg", "jpeg", "gif", "png");
                jFileChooser1.setFileFilter(filter);
        		int returnVal = jFileChooser1.showOpenDialog(null);
        		if(returnVal == JFileChooser.APPROVE_OPTION) {
        			txt_HosIcon.setText(jFileChooser1.getSelectedFile().getAbsolutePath());
        			jLabelHosIconImg.setIcon(scaledIcon(jFileChooser1.getSelectedFile().getAbsolutePath(), 40));
        		}
            }
        });
        
        javax.swing.GroupLayout pan_SystemSettingButtonLayout = new javax.swing.GroupLayout(pan_SystemSettingButton);
        pan_SystemSettingButton.setLayout(pan_SystemSettingButtonLayout);
        pan_SystemSettingButtonLayout.setHorizontalGroup(
        		pan_SystemSettingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_SystemSettingButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ReloadSystemSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_SaveSystemSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pan_SystemSettingButtonLayout.setVerticalGroup(
        		pan_SystemSettingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_SystemSettingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_ReloadSystemSetting)
                .addComponent(btn_SaveSystemSetting)
                )
        );
        
        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        
        jPanel4Layout.setHorizontalGroup(
        		jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                        		.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        				.addComponent(jLabelHosCode, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelHosName, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelHosPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelHosAddr, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelLanguage, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelICDVersion, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelHosIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                                ).addGap(10, 10, 10)
                        		.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        				.addComponent(txt_HosCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_HosName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_HosPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_HosAddr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cob_Language, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cob_ICDVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_HosIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                ).addGap(10, 10, 10)
                        		.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btn_SelectHosIcon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                ).addGap(10, 10, 10)
                        		.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelHosIconImg)
                                ).addContainerGap(491, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(pan_SystemSettingButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                            
                    ))
            );

        jPanel4Layout.setVerticalGroup(
        		jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(jLabelHosCode)
                    		.addComponent(txt_HosCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(jLabelHosName)
                    		.addComponent(txt_HosName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(jLabelHosPhone)
                    		.addComponent(txt_HosPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelHosAddr)
                            .addComponent(txt_HosAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLanguage)
                            .addComponent(cob_Language, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelICDVersion)
                            .addComponent(cob_ICDVersion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelHosIcon)
                            .addComponent(txt_HosIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_SelectHosIcon)
                            .addComponent(jLabelHosIconImg)
                    )
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 376, Short.MAX_VALUE)
                    .addComponent(pan_SystemSettingButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
        jTabbedPane1.addTab(paragraph.getString("SYSTEM"), jPanel4);
        // end of tab system setting
        
        txt_MorningS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_MorningSKeyReleased(evt);
            }
        });

        txt_NoonS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NoonSKeyReleased(evt);
            }
        });

        txt_NightS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NightSActionPerformed(evt);
            }
        });
        txt_NightS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NightSKeyReleased(evt);
            }
        });

        txt_NoonE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NoonEKeyReleased(evt);
            }
        });

        txt_NightE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_NightEActionPerformed(evt);
            }
        });
        txt_NightE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_NightEKeyReleased(evt);
            }
        });

        txt_MorningE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_MorningEActionPerformed(evt);
            }
        });
        txt_MorningE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_MorningEKeyReleased(evt);
            }
        });

        btn_Save.setEnabled(false);
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        btn_ReLoad.setEnabled(false);
        btn_ReLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	reloadShiftSetting();
            }
        });

        //btn_Default.setText("Default");
        //btn_Default.addActionListener(new java.awt.event.ActionListener() {
        //    public void actionPerformed(java.awt.event.ActionEvent evt) {
        //        btn_DefaultActionPerformed(evt);
        //    }
        //});

        javax.swing.GroupLayout pan_underLayout = new javax.swing.GroupLayout(pan_under);
        pan_under.setLayout(pan_underLayout);
        pan_underLayout.setHorizontalGroup(
            pan_underLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_underLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                //.addComponent(btn_Default, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ReLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pan_underLayout.setVerticalGroup(
            pan_underLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_underLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_Save)
                .addComponent(btn_ReLoad)
                //.addComponent(btn_Default)
                )
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_NoonS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NightS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_MorningS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_MorningE, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NoonE, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_NightE, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(377, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pan_under, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_MorningS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txt_MorningE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_NoonS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txt_NoonE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_NightS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txt_NightE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 376, Short.MAX_VALUE)
                .addComponent(pan_under, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(paragraph.getString("SHIFT"), jPanel1);

        // start of tab division setting
        
        btn_SaveDivisionSetting = new javax.swing.JButton();
        btn_ReloadDivisionSetting = new javax.swing.JButton();
        btn_AddDivision = new javax.swing.JButton();
        btn_AddRoom = new javax.swing.JButton();
        pan_DivisionSettingButton = new javax.swing.JPanel();
        jLabelDivision = new javax.swing.JLabel();
        jLabelRoom = new javax.swing.JLabel();
        
        btn_SaveDivisionSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDivisionSetting();
            }
        });
        
        btn_ReloadDivisionSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	reloadDivisionSetting();
            }
        });
        
        btn_AddDivision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDivision();
            }
        });
        
        btn_AddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	int selectedDivisionIndex = tb_division.getSelectedRow(); 
            	if(selectedDivisionIndex == -1) JOptionPane.showMessageDialog(null, paragraph.getString("PLEASESELECTDIVISIONFIRST"));
            	else AddDivisionClinic(tb_division.getValueAt(selectedDivisionIndex, 0).toString());
            }
        });
        
        tb_division = new javax.swing.JTable();
        tb_division.setRowHeight(30);
        tb_division.setSelectionModel(new ForcedListSelectionModel());
        sp_division = new javax.swing.JScrollPane();
        sp_division.setViewportView(tb_division);
        //sp_division.setPreferredSize(new Dimension(200,50));
        tb_division.setModel(divisionTableModel);
                
        // listener when selected row changed
        ListSelectionModel listSelectionModel = tb_division.getSelectionModel();
        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
        tb_division.setSelectionModel(listSelectionModel);
        
        tb_poliRoom = new javax.swing.JTable();
        tb_poliRoom.setRowHeight(30);
        tb_poliRoom.setSelectionModel(new ForcedListSelectionModel());
        sp_poliRoom = new javax.swing.JScrollPane();
        sp_poliRoom.setViewportView(tb_poliRoom);
        //sp_division.setPreferredSize(new Dimension(200,50));
        tb_poliRoom.setModel(poliRoomTableModel);

        javax.swing.GroupLayout pan_DivisionSettingButtonLayout = new javax.swing.GroupLayout(pan_DivisionSettingButton);
        pan_DivisionSettingButton.setLayout(pan_DivisionSettingButtonLayout);
        pan_DivisionSettingButtonLayout.setHorizontalGroup(
        		pan_DivisionSettingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_DivisionSettingButtonLayout.createSequentialGroup()
        		.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_AddDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_AddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ReloadDivisionSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_SaveDivisionSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pan_DivisionSettingButtonLayout.setVerticalGroup(
        		pan_DivisionSettingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_DivisionSettingButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        		.addComponent(btn_AddDivision)
                .addComponent(btn_AddRoom)
                .addComponent(btn_ReloadDivisionSetting)
                .addComponent(btn_SaveDivisionSetting)
                )
        );
        
        
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);

        jPanel3Layout.setHorizontalGroup(
        		jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                        		.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        				.addComponent(jLabelDivision, javax.swing.GroupLayout.Alignment.LEADING)
                        				.addComponent(sp_division, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .addComponent(jLabelRoom, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sp_poliRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                )
                                .addContainerGap(10, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(pan_DivisionSettingButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                            
                    ))
            );

        jPanel3Layout.setVerticalGroup(
        		jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(jLabelDivision)
                    )
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(sp_division, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    ).addGap(30, 30, 30)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(jLabelRoom)
                    ).addGap(10, 10, 10)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(sp_poliRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    )
                    .addGap(10, 10, 10)
                    .addComponent(pan_DivisionSettingButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
        
        jTabbedPane1.addTab(paragraph.getString("DIVISION"), jPanel3);
        // end of tab division setting

        // start of tab Price setting
        btn_SavePrice = new javax.swing.JButton();
        btn_ReloadPrice = new javax.swing.JButton();
        txt_RegPrice = new javax.swing.JTextField();
        txt_BedPrice = new javax.swing.JTextField();
        txt_DiagnosisPrice = new javax.swing.JTextField();
        
        jLabelPriceRegistration = new javax.swing.JLabel();
        jLabelPriceBed = new javax.swing.JLabel();
        jLabelPriceDiagnosis = new javax.swing.JLabel();
        pan_PriceButton = new javax.swing.JPanel();
        
        btn_SavePrice.setEnabled(true);
        btn_ReloadPrice.setEnabled(true);
        
        btn_ReloadPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	reloadPriceSetting();
            }
        });
        btn_SavePrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SavePriceActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout pan_PriceButtonLayout = new javax.swing.GroupLayout(pan_PriceButton);
        pan_PriceButton.setLayout(pan_PriceButtonLayout);
        pan_PriceButtonLayout.setHorizontalGroup(
        		pan_PriceButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pan_PriceButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ReloadPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_SavePrice, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pan_PriceButtonLayout.setVerticalGroup(
        		pan_PriceButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_PriceButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_ReloadPrice)
                .addComponent(btn_SavePrice)
                )
        );
        
        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        
        jPanel5Layout.setHorizontalGroup(
        		jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                        		.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelPriceRegistration, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelPriceBed, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelPriceDiagnosis, javax.swing.GroupLayout.Alignment.TRAILING)
                                ).addGap(10, 10, 10)
                        		.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_RegPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_BedPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_DiagnosisPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                ).addContainerGap(491, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(pan_PriceButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                            
                    ))
            );

        jPanel5Layout.setVerticalGroup(
        		jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(jLabelPriceRegistration)
                    		.addComponent(txt_RegPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    		.addComponent(jLabelPriceBed)
                    		.addComponent(txt_BedPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    ).addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPriceDiagnosis)
                            .addComponent(txt_DiagnosisPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 376, Short.MAX_VALUE)
                    .addComponent(pan_PriceButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );
        
        jTabbedPane1.addTab(paragraph.getString("DEFAULT") + " " + paragraph.getString("PRICE"), jPanel5);
        // end of tab Price setting
        
/*        jLabel4.setText("Patient No.:");
        btn_EnterHL7.setText("Enter");
        btn_EnterHL7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EnterHL7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Pno, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_EnterHL7)
                .addContainerGap(449, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_EnterHL7)
                    .addComponent(jLabel4)
                    .addComponent(txt_Pno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(491, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("HL7", jPanel2);
*/

        btn_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
                    .addComponent(btn_Close, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Close)
                .addContainerGap())
        );

        pack();
        
        
         
    }// </editor-fold>//GEN-END:initComponents
    
    private void btn_SaveSystemSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        try {
            DBC.executeUpdate("UPDATE setting SET " +
            	"hospicalID = '"+txt_HosCode.getText()+"', " +
            	"hos_name = '"+txt_HosName.getText()+"', " +
            	"hos_phone = '"+txt_HosPhone.getText()+"', " +
            	"hos_icon_path = '"+txt_HosIcon.getText()+"', " +
            	"language = '"+cob_Language.getSelectedItem().toString()+"', " +
            	"ICDVersion = '"+cob_ICDVersion.getSelectedItem().toString()+"', " +
                "hos_address = '"+txt_HosAddr.getText()+"' WHERE id = 1");
            reloadSystemSetting();
            JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	    }
    }
    private void addDivision() {
    	try {
    		String sql = "INSERT INTO policlinic (`guid`, `name`, `status`, `room_num`) VALUES (" +
        			"uuid(), '__temp', 'N', 0)";
    		DBC.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Add division completed.");
            reloadDivisionTable(divisionTableModel);
            reloadPoliRoomTable(poliRoomTableModel, tb_division.getValueAt(0, 0).toString(), tb_division.getValueAt(0, 1).toString());
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	    }
    }
    
    private void AddDivisionClinic(String divisionGUID) {
    	try {
    		String sql = "INSERT INTO poli_room (`guid`, `poli_guid`, `name`, `status`) VALUES (" +
        			"uuid(), '" + divisionGUID + "', '__temp', 'N')";
            DBC.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, paragraph.getString("ADDDIVISIONCLINICCOMPLETE"));
            reloadDivisionTable(divisionTableModel);
            reloadPoliRoomTable(poliRoomTableModel, tb_division.getValueAt(0, 0).toString(), tb_division.getValueAt(0, 1).toString());
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	    }
    }
    
    private void saveDivisionSetting() {
    	
    	int activeRoomNum = 0;
    	String poli_guid = "";
    	DefaultTableModel dtm2 =(DefaultTableModel) tb_poliRoom.getModel();
    	for ( int i = 0 ;i < dtm2.getRowCount(); i++){
    	      String guid = (String) dtm2.getValueAt(i, 0);
    	      String name = (String) dtm2.getValueAt(i, 2);
    	      String status = (String) dtm2.getValueAt(i, 3);
    	      poli_guid = (String) dtm2.getValueAt(i, 1);
    	      if(status.compareTo("Normal") == 0) {status = "N"; activeRoomNum++;}
    	      else status = "D";
    	      
    	      try {
    	    	  	String sql2 = "UPDATE poli_room SET " +
        	            	"name = '"+name+"', " +
        	                "status = '"+status+"' WHERE guid = '" + guid + "'";
    	            DBC.executeUpdate(sql2);
    	    	  	//System.out.println(sql2);
    		    } catch (SQLException ex) {
    		        Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
    		        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
    		    }
    	}
    	
    	DefaultTableModel dtm =(DefaultTableModel) tb_division.getModel();
    	for ( int i = 0 ;i < dtm.getRowCount(); i++){
    	      String guid = (String) dtm.getValueAt(i, 0);
    	      String name = (String) dtm.getValueAt(i, 1);
    	      String status = (String) dtm.getValueAt(i, 2);
    	      if(status.compareTo("Normal") == 0) status = "N";
    	      else status = "D";
    	      
    	      try {
    	    	  	String sql = "UPDATE policlinic SET " +
        	            	"name = '"+name+"', " +
        	            	"status = '"+status+"' WHERE guid = '" + guid + "'";
    	            DBC.executeUpdate(sql);
    		    } catch (SQLException ex) {
    		        Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
    		        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
    		    }
    	}
    	
    	try {
    	  	String sql3 = "UPDATE policlinic SET " +
	                "room_num = '"+activeRoomNum+"' WHERE guid = '" + poli_guid + "'";
            DBC.executeUpdate(sql3);
            //System.out.println(sql);
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
	    }
    	
    	reloadDivisionTable(divisionTableModel);
        reloadPoliRoomTable(poliRoomTableModel, tb_division.getValueAt(0, 0).toString(), tb_division.getValueAt(0, 1).toString());
        JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));

    }

    private void btn_SavePriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        try {
                if (isFloat(txt_RegPrice.getText())
                		&& isFloat(txt_BedPrice.getText())
                		&& isFloat(txt_DiagnosisPrice.getText()) ) {
                	
                        DBC.executeUpdate("UPDATE setting SET " +
                        	"bed_price = '"+txt_BedPrice.getText()+"'," +
                        	"diagnosis_price = '"+txt_DiagnosisPrice.getText()+"'," +
                            "registration_price = '"+txt_RegPrice.getText()+"' WHERE id = 1");
                    	reloadPriceSetting();
                        JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));
                } else {
                    JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
                }
        } catch (SQLException ex) {
            Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
        }
    }
    
    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        try {

                if (isNumber(txt_MorningS.getText()) && isNumber(txt_MorningE.getText())
                        && isNumber(txt_NoonS.getText()) && isNumber(txt_NoonE.getText())
                        && isNumber(txt_NightS.getText()) && isNumber(txt_NightE.getText())) {
                        DBC.executeUpdate("UPDATE setting SET " +
                            "morning_shift_s = '"+txt_MorningS.getText()+"'," +
                            "morning_shift_e = '"+txt_MorningE.getText()+"'," +
                            "noon_shift_s ='"+txt_NoonS.getText()+"'," +
                            "noon_shift_e ='"+txt_NoonE.getText()+"'," +
                            "evening_shift_s ='"+txt_NightS.getText()+"'," +
                            "evening_shift_e ='"+txt_NightE.getText()+"'" +
                            " WHERE id = 1");
                    	reloadShiftSetting();
                        JOptionPane.showMessageDialog(null, paragraph.getString("SAVECOMPLETE"));
                        reLoad();
                } else {
                    JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
                }

        } catch (SQLException ex) {
            Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, paragraph.getString("ERROR"));
        }
}//GEN-LAST:event_btn_SaveActionPerformed

    private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CloseActionPerformed
        new main.Frm_Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_CloseActionPerformed

    private void txt_NightSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NightSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NightSActionPerformed

    private void txt_NightEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_NightEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_NightEActionPerformed

    private void txt_MorningSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_MorningSKeyReleased
        btn_ReLoad.setEnabled(true);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_txt_MorningSKeyReleased

    private void txt_MorningEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_MorningEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_MorningEActionPerformed

    private void txt_MorningEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_MorningEKeyReleased
        btn_ReLoad.setEnabled(true);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_txt_MorningEKeyReleased

    private void txt_NoonSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NoonSKeyReleased
        btn_ReLoad.setEnabled(true);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_txt_NoonSKeyReleased

    private void txt_NoonEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NoonEKeyReleased
        btn_ReLoad.setEnabled(true);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_txt_NoonEKeyReleased

    private void txt_NightSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NightSKeyReleased
       btn_ReLoad.setEnabled(true);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_txt_NightSKeyReleased

    private void txt_NightEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_NightEKeyReleased
        btn_ReLoad.setEnabled(true);
        btn_Save.setEnabled(true);
    }//GEN-LAST:event_txt_NightEKeyReleased

    //private void btn_DefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DefaultActionPerformed
    //    this.txt_MorningS.setText("800");
    //    this.txt_MorningE.setText("1159");
    //    this.txt_NoonS.setText("1200");
    //    this.txt_NoonE.setText("1759");
    //    this.txt_NightS.setText("1800");
    //    this.txt_NightE.setText("2359");
    //}//GEN-LAST:event_btn_DefaultActionPerformed

    private void btn_EnterHL7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EnterHL7ActionPerformed
        try {
            ResultSet rs = DBC.executeQuery("SELECT p_no FROM patients_info WHERE p_no = '"+txt_Pno.getText().trim()+"'");
            if (rs.next()) new frm_outputHl7(txt_Pno.getText().trim()).setVisible(true);
            else JOptionPane.showMessageDialog(new Frame(), "No Information.");

        } catch (SQLException ex) {
            Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_EnterHL7ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Close;
    //private javax.swing.JButton btn_Default;
    private javax.swing.JButton btn_EnterHL7;
    private javax.swing.JButton btn_ReLoad;
    private javax.swing.JButton btn_Save;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pan_under;
    private javax.swing.JTextField txt_MorningE;
    private javax.swing.JTextField txt_MorningS;
    private javax.swing.JTextField txt_NightE;
    private javax.swing.JTextField txt_NightS;
    private javax.swing.JTextField txt_NoonE;
    private javax.swing.JTextField txt_NoonS;
    private javax.swing.JTextField txt_Pno;
    
    // for price tab
    private javax.swing.JButton btn_SavePrice;
    private javax.swing.JButton btn_ReloadPrice;
    private javax.swing.JLabel jLabelPriceRegistration;
    private javax.swing.JLabel jLabelPriceBed;
    private javax.swing.JLabel jLabelPriceDiagnosis;
    private javax.swing.JTextField txt_BedPrice;
    private javax.swing.JTextField txt_RegPrice;
    private javax.swing.JTextField txt_DiagnosisPrice;
    private javax.swing.JPanel pan_PriceButton;
    
    // for system setting tab
    private javax.swing.JButton btn_SaveSystemSetting;
    private javax.swing.JButton btn_ReloadSystemSetting;
    private javax.swing.JButton btn_SelectHosIcon;
    private javax.swing.JLabel jLabelHosCode;
    private javax.swing.JLabel jLabelHosName;
    private javax.swing.JLabel jLabelHosPhone;
    private javax.swing.JLabel jLabelHosAddr;
    private javax.swing.JLabel jLabelHosIcon;
    private javax.swing.JLabel jLabelHosIconImg;
    private javax.swing.JLabel jLabelLanguage;
    private javax.swing.JLabel jLabelICDVersion;
    private javax.swing.JTextField txt_HosCode;
    private javax.swing.JTextField txt_HosName;
    private javax.swing.JTextField txt_HosPhone;
    private javax.swing.JTextField txt_HosAddr;
    private javax.swing.JTextField txt_HosIcon;
    //private javax.swing.JTextField txt_Language;
    private javax.swing.JComboBox cob_Language;
    private javax.swing.JComboBox cob_ICDVersion;
    private javax.swing.JPanel pan_SystemSettingButton;
    
    // for division tab

    private javax.swing.JTable tb_division;
    private javax.swing.JScrollPane sp_division;
    private javax.swing.JTable tb_poliRoom;
    private javax.swing.JScrollPane sp_poliRoom;
    private javax.swing.JButton btn_SaveDivisionSetting;
    private javax.swing.JButton btn_ReloadDivisionSetting;
    private javax.swing.JButton btn_AddDivision;
    private javax.swing.JButton btn_AddRoom;
    private javax.swing.JPanel pan_DivisionSettingButton;
    private javax.swing.JLabel jLabelDivision;
    private javax.swing.JLabel jLabelRoom;
    
    // End of variables declaration//GEN-END:variables
}
