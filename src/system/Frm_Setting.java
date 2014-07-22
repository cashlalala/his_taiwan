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

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import cc.johnwu.sql.DBC;

/**
 *
 * @author Steven
 */
public class Frm_Setting extends javax.swing.JFrame {
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

    // 初始化
    private void init() {
    	cob_Language.setModel(new javax.swing.DefaultComboBoxModel(
	                new String[] { "en", "fr", "sp"}
	            )
	    );
    	cob_ICDVersion.setModel(new javax.swing.DefaultComboBoxModel(
	                new String[] { "ICD-9", "ICD-10"}
	            )
	    );
    	reloadSystemSetting();
    	reloadShiftSetting();
    	reloadPriceSetting();
    }
    
    private ImageIcon scaledIcon(String absolutePath, int width) {
    	String path;
    	File file = new File(absolutePath);
        if(file.exists()) path = absolutePath;
        else path = "./img/nofile.png";

    	ImageIcon image = new ImageIcon(path);
        Image img = image.getImage();
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        int scaledX = width;
        int scaledY = (scaledX * img.getHeight(null))/img.getWidth(null);
        g.drawImage(img, 0, 0, scaledX, scaledY, null);
        ImageIcon newIcon = new ImageIcon(bi);
        return newIcon;
    } 
    
    private void reloadSystemSetting() {
    	try{
            ResultSet rs = DBC.executeQuery("SELECT * FROM setting WHERE id = 1");
            if(rs.next()){
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

        // start of tab system setting    
        btn_SaveSystemSetting = new javax.swing.JButton();
        btn_ReloadSystemSetting = new javax.swing.JButton();
        btn_SelectHosIcon = new javax.swing.JButton();
        txt_HosName = new javax.swing.JTextField();
        txt_HosPhone = new javax.swing.JTextField();
        txt_HosAddr = new javax.swing.JTextField();
        txt_HosIcon = new javax.swing.JTextField();
        //txt_Language = new javax.swing.JTextField();
        cob_Language = new javax.swing.JComboBox();
        cob_ICDVersion = new javax.swing.JComboBox();
        jLabelHosName = new javax.swing.JLabel();
        jLabelHosPhone = new javax.swing.JLabel();
        jLabelHosAddr = new javax.swing.JLabel();
        jLabelHosIcon = new javax.swing.JLabel();
        jLabelHosIconImg = new javax.swing.JLabel();
        jLabelLanguage = new javax.swing.JLabel();
        jLabelICDVersion = new javax.swing.JLabel();
        pan_SystemSettingButton = new javax.swing.JPanel();
        
        btn_SaveSystemSetting.setText("Save");
        btn_SaveSystemSetting.setEnabled(true);
        btn_ReloadSystemSetting.setText("Reload");
        btn_ReloadSystemSetting.setEnabled(true);
        btn_SelectHosIcon.setText("choose...");
        btn_SelectHosIcon.setEnabled(true);
        jLabelHosName.setText("Hospital Name :");
        jLabelHosPhone.setText("Hospital Phone :");
        jLabelHosAddr.setText("Hospital Address :");
        jLabelHosIcon.setText("Hospital Icon :");
        jLabelLanguage.setText("System Language :");
        jLabelICDVersion.setText("ICD version :");
        
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
                                        .addComponent(jLabelHosName, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelHosPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelHosAddr, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelLanguage, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelICDVersion, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelHosIcon, javax.swing.GroupLayout.Alignment.TRAILING)
                                ).addGap(10, 10, 10)
                        		.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
        jTabbedPane1.addTab("System", jPanel4);
        // end of tab system setting
        
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Morning Shift : from ");
        jLabel2.setText("Afternoon Shift : from ");
        jLabel3.setText("Night Shift : from ");

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

        jLabel8.setText("To");

        jLabel9.setText("To");

        jLabel10.setText("To");

        btn_Save.setText("Save");
        btn_Save.setEnabled(false);
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        btn_ReLoad.setText("Re-read");
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

        jTabbedPane1.addTab("Shift", jPanel1);

        btn_EnterHL7.setText("Enter");
        btn_EnterHL7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EnterHL7ActionPerformed(evt);
            }
        });
        
        // start of tab policlinic setting
        //jLabel4.setText("Patient No.:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
        		jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                //.addComponent(jLabel4)
                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                //.addComponent(txt_Pno, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                //.addComponent(btn_EnterHL7)
                .addContainerGap(449, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
        		jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    //.addComponent(btn_EnterHL7)
                    //.addComponent(jLabel4)
                    //.addComponent(txt_Pno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                ).addContainerGap(491, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Division", jPanel3);
        // end of tab policlinic setting

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
        
        btn_SavePrice.setText("Save");
        btn_SavePrice.setEnabled(true);
        btn_ReloadPrice.setText("Reload");
        btn_ReloadPrice.setEnabled(true);
        jLabelPriceRegistration.setText("Registration Price :");
        jLabelPriceBed.setText("Bed Price :");
        jLabelPriceDiagnosis.setText("Diagnosis Price :");
        
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
        
        jTabbedPane1.addTab("Default Price", jPanel5);
        // end of tab Price setting
        
        jLabel4.setText("Patient No.:");

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

        btn_Close.setText("Close");
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
            	"hos_name = '"+txt_HosName.getText()+"', " +
            	"hos_phone = '"+txt_HosPhone.getText()+"', " +
            	"hos_icon_path = '"+txt_HosIcon.getText()+"', " +
            	"language = '"+cob_Language.getSelectedItem().toString()+"', " +
            	"ICDVersion = '"+cob_ICDVersion.getSelectedItem().toString()+"', " +
                "hos_address = '"+txt_HosAddr.getText()+"' WHERE id = 1");
            reloadSystemSetting();
            JOptionPane.showMessageDialog(null, "Save Completed.");
	    } catch (SQLException ex) {
	        Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
	        JOptionPane.showMessageDialog(null, "db: Input System Setting Error.");
	    }
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
                        JOptionPane.showMessageDialog(null, "Save Completed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Input Price Error.");
                }
        } catch (SQLException ex) {
            Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "db: Input Price Error.");
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
                        JOptionPane.showMessageDialog(null, "Save Completed.");
                        reLoad();
                } else {
                    JOptionPane.showMessageDialog(null, "db: Input Time Error.");
                }

        } catch (SQLException ex) {
            Logger.getLogger(Frm_Setting.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Input Time Error.");
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
    private javax.swing.JLabel jLabelHosName;
    private javax.swing.JLabel jLabelHosPhone;
    private javax.swing.JLabel jLabelHosAddr;
    private javax.swing.JLabel jLabelHosIcon;
    private javax.swing.JLabel jLabelHosIconImg;
    private javax.swing.JLabel jLabelLanguage;
    private javax.swing.JLabel jLabelICDVersion;
    private javax.swing.JTextField txt_HosName;
    private javax.swing.JTextField txt_HosPhone;
    private javax.swing.JTextField txt_HosAddr;
    private javax.swing.JTextField txt_HosIcon;
    //private javax.swing.JTextField txt_Language;
    private javax.swing.JComboBox cob_Language;
    private javax.swing.JComboBox cob_ICDVersion;
    private javax.swing.JPanel pan_SystemSettingButton;
    
    // End of variables declaration//GEN-END:variables
}
