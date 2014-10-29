package cashier;

import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;

import java.awt.Frame;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import common.Constant;
import common.PrintTools;

/**
 *
 * @author bee
 */
public class Frm_CashierInfo extends javax.swing.JFrame {

    private String m_Sysname;
    private String m_Guid;
    private String m_Pno;
    private Frm_CashierList m_Frm;
    Double gPhaTotal = 0.0;
    Double gXrayTotal = 0.0;
    Double gLabTotal = 0.0;
    Double gBedTotal = 0.0;


    public Frm_CashierInfo(Frm_CashierList frm, String guid, String sysname, String pno) {
        initComponents();
        m_Sysname = sysname;
        m_Guid = guid;
        m_Frm = frm;
        m_Pno = pno;
        this.setLocationRelativeTo(this);
        this.tab_Payment.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     // tabble不可按住多選
        
        addWindowListener(new WindowAdapter() {  // 畫面關閉原視窗enable
            @Override
            public void windowClosing(WindowEvent windowevent) {
                m_Frm.setEnabled(true);
            }
        });
        this.tab_Paid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);     // tabble不可按住多選
        
        String sql = "SELECT * FROM patients_info WHERE p_no = '"+m_Pno+"'";
        try {
            ResultSet rs = DBC.executeQuery(sql);
            rs.next();
            // 取出病患基本資料
            this.txt_No.setText(rs.getString("p_no"));
            this.txt_Name.setText(rs.getString("firstname")+" "+rs.getString("lastname"));
            this.txt_Gender.setText(rs.getString("gender"));
            this.txt_Ps.setText(rs.getString("ps"));
        } catch (SQLException ex) {
            Logger.getLogger(Frm_CashierInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
 
       initTable();
    }



    private void initTable() {
        DefaultTableModel tabModel = null;
        DefaultTableModel paidTabModel = null;
        ResultSet rs = null;
        ResultSet rsPaid = null;
        String sql =null;
        String sqlPaid =null;
        Object[][] dataArray = null;
        Object[][] paidDataArray = null;
        String[] titlePaid = {" ", "Paid Money", "time", "Staff no"};
        String sqlPha = "SELECT medicine_stock.guid, medicines.code AS 'Code', " +
                "medicines.item AS 'Item', " +
                "medicine_stock.quantity AS 'Quantity', " +
                "medicine_stock.urgent AS 'Urgent', " +
                "IFNULL(medicine_stock.price,medicine_stock.quantity * medical_stock.default_unit_price) AS 'Price' " +
                //"medicine_stock.price AS 'Price' " +
                "FROM medicines, medicine_stock, registration_info, medical_stock " +
                "WHERE registration_info.guid = '" + m_Guid + "' " +
                " AND medicine_stock.return_medicine_time is null " +
                " AND medicine_stock.reg_guid = registration_info.guid " +
                " AND medicines.code = medicine_stock.m_code " +
                " AND medical_stock.type = 'P' AND medical_stock.item_guid = medicine_stock.m_code " +
                " ORDER BY registration_info.reg_time ASC";
        String sqlLab = "SELECT prescription.guid, " +
                "prescription.code AS 'Code', " +
                "prescription_code.name AS 'Name', " +
                "prescription.cost AS 'Cost' " +
                "FROM prescription, registration_info, " +
                     "prescription_code, shift_table, policlinic,poli_room,staff_info " +
                "WHERE registration_info.guid = '"+m_Guid+"' " +
                    "AND registration_info.shift_guid = shift_table.guid " +
                    "AND shift_table.room_guid = poli_room.guid " +
                    "AND poli_room.poli_guid = policlinic.guid " +
                    "AND staff_info.s_id = shift_table.s_id " +
                    "AND prescription.reg_guid = registration_info.guid " +
                    "AND prescription_code.code = prescription.code " +
                    "AND prescription_code.type <> '"+Constant.X_RAY_CODE+"' " +
                    " ORDER BY registration_info.reg_time ASC";
        String sqlReg = "SELECT registration_info.guid, " +
                "registration_info.reg_time AS 'Registration Time',"+
                "CONCAT(patients_info.firstname, ' ' ,patients_info.lastname) AS 'Name', " +
                "policlinic.name AS 'Dept.', " +
                "poli_room.name AS 'Clinic', "+
                "registration_info.reg_cost AS 'Cost' "+
                "FROM registration_info, shift_table,policlinic , poli_room ,patients_info "+
                "WHERE registration_payment IS NULL "+
                "AND shift_table.guid = registration_info.shift_guid "+
                "AND policlinic.guid = poli_room.poli_guid "+
                "AND poli_room.guid = shift_table.room_guid  "+
                "AND registration_info.guid = '"+m_Guid+"' "+
                //" AND registration_info.p_no = '" + m_Pno + "' " +
                "AND registration_info.p_no = patients_info.p_no ORDER BY registration_info.reg_time ASC";
        String sqlXray = "SELECT prescription.guid, " +
                "prescription.code AS 'Code', " +
                "prescription_code.name AS 'Name', " +
                "prescription.cost AS 'Cost' " +
                "FROM prescription, registration_info, " +
                     "prescription_code, shift_table, policlinic,poli_room,staff_info " +
                "WHERE registration_info.guid = '"+m_Guid+"' " +
                    "AND registration_info.shift_guid = shift_table.guid " +
                    "AND shift_table.room_guid = poli_room.guid " +
                    "AND poli_room.poli_guid = policlinic.guid " +
                    "AND staff_info.s_id = shift_table.s_id " +
                    "AND prescription.reg_guid = registration_info.guid " +
                    "AND prescription_code.code = prescription.code " +
                    "AND prescription_code.type = '"+Constant.X_RAY_CODE+"' " +
                    " ORDER BY registration_info.reg_time ASC ";
        
        try {
            if (m_Sysname.equals("pha")) {

                String[] title = {"guid", " ", "Code", "Item", "Quantity", "Urgent", "Cost"}; // table表頭
                //sql = sqlPha;
                System.out.println(sqlPha);
                rs = DBC.executeQuery(sqlPha);
                rs.last();
                dataArray = new Object[rs.getRow()][8];
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    dataArray[i][0] = rs.getString("guid");
                    dataArray[i][1] = i + 1;
                    dataArray[i][2] = rs.getString("Code");
                    dataArray[i][3] = rs.getString("Item");
                    dataArray[i][4] = rs.getString("Quantity");
                    dataArray[i][6] = rs.getString("Price");
                    i++;
                }
                tabModel = new DefaultTableModel(dataArray, title) {

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
          
                        if (columnIndex == 6) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                };

                sqlPaid = "SELECT * FROM cashier WHERE reg_guid = '" + m_Guid + "' " +
                        "AND no like 'PH%' " +
                        " order by payment_time desc ";

            } else if (m_Sysname.equals("lab")) {
                String[] title = {"guid"," ", "Code","Name", "Urgent","Cost"};   // table表頭
                rs = DBC.executeQuery(sqlLab);
                rs.last();
                dataArray = new Object[rs.getRow()][6];
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    dataArray[i][0] = rs.getString("guid");
                    dataArray[i][1] = i + 1;
                    dataArray[i][2] = rs.getString("Code");
                    dataArray[i][3] = rs.getString("Name");
                    dataArray[i][5] = rs.getString("Cost");
                    i++;
                }
                tabModel = new DefaultTableModel(dataArray, title) {

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        if (columnIndex == 5) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                };
                
                sqlPaid = "SELECT * FROM cashier WHERE reg_guid = '" + m_Guid + "' " +
                        "AND no like 'LA%' " +
                        " order by payment_time desc ";
    
            } else if (m_Sysname.equals("reg")) {
                String[] title = {"guid"," ", "Registration Time","Name", "Dept.","Clinic","Cost" };   // table表頭
              
                rs = DBC.executeQuery(sqlReg);
                rs.last();
                dataArray = new Object[rs.getRow()][7];
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    dataArray[i][0] = rs.getString("guid");
                    dataArray[i][1] = i + 1;
                    dataArray[i][2] = rs.getString("Registration Time");
                    dataArray[i][3] = rs.getString("Name");
                    dataArray[i][4] = rs.getString("Dept.");
                    dataArray[i][5] = rs.getString("Clinic");
                    dataArray[i][6] = rs.getString("Cost");
                    i++;
                }
                tabModel = new DefaultTableModel(dataArray, title) {

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        if (columnIndex == 6) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                };
                
                sqlPaid = "SELECT * FROM cashier WHERE reg_guid = '" + m_Guid + "' " +
                        "AND no like 'RE%' " +
                        " order by payment_time desc ";

            } else if (m_Sysname.equals("xray")) {
                String[] title = {"guid"," ", "Code","Name", "Urgent","Cost"};   // table表頭
                
                rs = DBC.executeQuery(sqlXray);
				rs.last();
				dataArray = new Object[rs.getRow()][6];
				rs.beforeFirst();
				int i = 0;
				while (rs.next()) {
				    dataArray[i][0] = rs.getString("guid");
				    dataArray[i][1] = i + 1;
				    dataArray[i][2] = rs.getString("Code");
				    dataArray[i][3] = rs.getString("Name");
				    dataArray[i][5] = rs.getString("Cost");
				
				    i++;
				}
				tabModel = new DefaultTableModel(dataArray, title) {
				
				    @Override
				    public boolean isCellEditable(int rowIndex, int columnIndex) {
				        if (columnIndex == 5) {
				            return true;
				        } else {
				            return false;
				        }
				    }
				};
				sqlPaid = "SELECT * FROM cashier WHERE reg_guid = '" + m_Guid + "' " +
				        "AND no like 'XR%' " +
				        " order by payment_time desc ";
	
	        } else if (m_Sysname.equals("bed")) {
	        	Integer itemCount=0;
	        	Vector<String> title = new Vector<String>();
	        	Vector<Vector> rowData = new Vector<Vector>();
	        	title.addElement("guid");
	        	title.addElement("");
	        	title.addElement("type");
	        	title.addElement("Code");
	        	title.addElement("Name");
	        	title.addElement("Quantity");
	        	title.addElement("Check in");
	        	title.addElement("Check out");
	        	title.addElement("Price");
	        	
	        	sql = " SELECT registration_info.guid, registration_info.bed_guid, "
	        			+ " registration_info.p_no, registration_info.reg_time, "
	        			+ " registration_info.reg_cost, registration_info.dia_cost, "
	        			+ " bed_record.checkinTime, bed_record.checkoutTime, "
	        			+ " IFNULL(bed_record.cost,DATEDIFF(bed_record.checkoutTime, "
	        			+ " bed_record.checkinTime) * setting.bed_price) as 'Price' "
	        			+ " FROM registration_info, bed_record, setting "
	        			+ " WHERE registration_info.bed_guid = bed_record.guid "
		        		+ " AND registration_info.guid = '" + m_Guid + "' "
		        		+ " ORDER by registration_info.reg_time ASC ";
	        	
	        	rs = DBC.executeQuery(sql);
	        	Vector<String> rowItem = null;

				while (rs.next()) {
					itemCount++;
					rowItem = new Vector<String>();
                	rowItem.addElement(rs.getString("guid"));
                	rowItem.addElement(itemCount.toString());
                	rowItem.addElement("Bed");
                	rowItem.addElement(rs.getString("bed_guid"));
                	rowItem.addElement("");
                	rowItem.addElement("");
                	rowItem.addElement(rs.getString("checkinTime"));
                	rowItem.addElement(rs.getString("checkoutTime"));
                	rowItem.addElement(rs.getString("Price"));
                	
                	rowData.addElement(rowItem);
                    gBedTotal += Double.parseDouble(rs.getString("Price"));
				}
				
				//String[] title = {"guid", " ", "Code", "Item", "Quantity", "Urgent", "Cost"}; // table表頭
                rs = DBC.executeQuery(sqlPha);
                while (rs.next()) {
                	itemCount++;
					rowItem = new Vector<String>();
                	rowItem.addElement(rs.getString("guid"));
                	rowItem.addElement(itemCount.toString());
                	rowItem.addElement("Pharmacy");
                	rowItem.addElement(rs.getString("Code"));
                	rowItem.addElement(rs.getString("Item"));
                	rowItem.addElement(rs.getString("Quantity"));
                	rowItem.addElement("");
                	rowItem.addElement("");
                	rowItem.addElement(rs.getString("Price"));
                	
                	rowData.addElement(rowItem);
                	gPhaTotal += Double.parseDouble(rs.getString("Price"));
                }
                
                rs = DBC.executeQuery(sqlLab);

                while (rs.next()) {
                	itemCount++;
					rowItem = new Vector<String>();
                	rowItem.addElement(rs.getString("guid"));
                	rowItem.addElement(itemCount.toString());
                	rowItem.addElement("Laboratory");
                	rowItem.addElement(rs.getString("Code"));
                	rowItem.addElement(rs.getString("Name"));
                	rowItem.addElement("");
                	rowItem.addElement("");
                	rowItem.addElement("");
                	rowItem.addElement(rs.getString("Cost"));
                	
                	rowData.addElement(rowItem);
                    gLabTotal += Double.parseDouble(rs.getString("Price"));
                }
				rs = DBC.executeQuery(sqlXray);
				while (rs.next()) {
					itemCount++;
					rowItem = new Vector<String>();
                	rowItem.addElement(rs.getString("guid"));
                	rowItem.addElement(itemCount.toString());
                	rowItem.addElement("Radiology");
                	rowItem.addElement(rs.getString("Code"));
                	rowItem.addElement(rs.getString("Name"));
                	rowItem.addElement("");
                	rowItem.addElement("");
                	rowItem.addElement("");
                	rowItem.addElement(rs.getString("Cost"));
                	
                	rowData.addElement(rowItem);
                	gXrayTotal += Double.parseDouble(rs.getString("Price"));
				}
				
				tabModel = new DefaultTableModel(rowData, title) {
				
				    @Override
				    public boolean isCellEditable(int rowIndex, int columnIndex) {
				        if (columnIndex == 8) {
				            return true;
				        } else {
				            return false;
				        }
				    }
				};
				sqlPaid = "SELECT * FROM cashier WHERE reg_guid = '" + m_Guid + "' " +
				        "AND no REGEXP 'BE|XR|PH|LA' " +
				        " order by payment_time desc ";
	        	
	        	
            }

            rsPaid = DBC.executeQuery(sqlPaid);
            rsPaid.last();
            paidDataArray = new Object[rsPaid.getRow()][4];
            rsPaid.beforeFirst();
            int i = 0;
            while (rsPaid.next()) {
            	paidDataArray[i][0] = i + 1;
            	paidDataArray[i][1] = rsPaid.getString("paid_amount");
            	paidDataArray[i][2] = rsPaid.getString("payment_time");
            	paidDataArray[i][3] = rsPaid.getString("s_no");
                i++;
            }
            paidTabModel = new DefaultTableModel(paidDataArray, titlePaid) {
            	@Override
			    public boolean isCellEditable(int rowIndex, int columnIndex) {
			        return false;
			    }
            };
            System.out.println(sql);
        }  catch (SQLException ex) {
                Logger.getLogger(Frm_CashierInfo.class.getName()).log(Level.SEVERE, null, ex);
         }

        tab_Payment.setModel(tabModel);
        tab_Payment.setRowHeight(30);
        common.TabTools.setHideColumn(tab_Payment, 0);
        
        tab_Paid.setModel(paidTabModel);
        tab_Paid.setRowHeight(30);
        
        double total = 0;
        for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
        	/*if (m_Sysname.equals("pha")) {
        		if (tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1) != null && common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())) {
        			String unit_price = tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString();
        			if (tab_Payment.getValueAt(i, 4) != null && common.Tools.isNumber(tab_Payment.getValueAt(i, 4).toString())) {
            			String quantity = tab_Payment.getValueAt(i, 4).toString();
            			total += Double.parseDouble(unit_price) * Double.parseDouble(quantity);
            		}
        		}
        	}
        	else {*/
	            if (tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1) != null && common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString()))
	                total += Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
        	//}
        }
        txt_AmountReceivable.setText(String.valueOf(total));
        txt_PaidAmount.setText("");
        txt_PaidAmountKeyReleased(null);

    }

/*
    private void setFinish(String finish) {
        // 判斷資料不是數值先把格子清空
        boolean IsCanFinish = true;
        String paymentType = null;
        for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
                if (tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1) == null || tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString().trim().equals("")
                        || !common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())){
                    tab_Payment.setValueAt("", i, tab_Payment.getColumnCount()-1);
                    IsCanFinish = false;
                }
         }

        if (!txt_PaidAmount.getText().trim().equals("") && !txt_AmountReceivable.getText().trim().equals("") && common.Tools.isNumber(txt_PaidAmount.getText().trim()) && common.Tools.isNumber(txt_AmountReceivable.getText().trim())) {
            txt_Arrears.setText(String.valueOf(Double.parseDouble(txt_AmountReceivable.getText().trim()) - Double.parseDouble(txt_PaidAmount.getText().trim())));
        } else {
            txt_Arrears.setText("");
            IsCanFinish = false;
        }

        if (!IsCanFinish) {
            JOptionPane.showMessageDialog(null, "Cost does not enter the complete.");
        } else {
            try {
                String sql = null;
                String sqlStr = null;
                
                if (m_Sysname.equals("pha")) {
                    paymentType = "P";
                    sqlStr = "PH";
                    sql = "UPDATE registration_info SET payment = '"+finish+"'";
                } else if (m_Sysname.equals("lab")) {
                    paymentType = "L";
                    sqlStr = "LA";
                      sql = "UPDATE registration_info SET lab_payment = '"+finish+"'";
                } else if (m_Sysname.equals("reg")) {
                    paymentType = "R";
                    sqlStr = "RE";
                     sql = "UPDATE registration_info SET registration_payment = '"+finish+"'";
                }else if (m_Sysname.equals("xray")) {
                    sqlStr = "XR";
                    paymentType = "X";
                     sql = "UPDATE registration_info SET radiology_payment = '"+finish+"'";
                }
                sql += ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " +
                                "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " +
                                "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " +
                                "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " +
                                "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid+ "'";
                DBC.executeUpdate(sql);

                // 儲存付費記錄
                sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                        "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+sqlStr+"00000001' ELSE "+
                        " INSERT (MAX(`no`), "+
                        "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                        "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                        "SUBSTRING(MAX(`no`),3)+1) END  "+
                        ",'"+m_Guid+"', '"+m_Pno+"', '"+paymentType+"', " +
                        "NOW(), "+txt_AmountReceivable.getText()+", "+txt_PaidAmount.getText()+", " +
                        ""+txt_Arrears.getText()+", '"+UserInfo.getUserNO()+"' " +
                        "FROM cashier WHERE no LIKE '"+sqlStr+"%'  " ;
                        //System.out.println(sql);
                DBC.executeUpdate(sql);
                

             } catch (SQLException ex) {
                Logger.getLogger(Frm_CashierInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            PrintTools pt = null;
            pt = new PrintTools();
            pt.DoPrint(11, m_Guid,paymentType);
            JOptionPane.showMessageDialog(null, "Saved successfully.");
            m_Frm.setEnabled(true);
            this.dispose();
        }
    }
*/


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pan_Top = new javax.swing.JPanel();
        lab_TitleNo = new javax.swing.JLabel();
        lab_TitleName = new javax.swing.JLabel();
        lab_TitlePs = new javax.swing.JLabel();
        lab_TitleSex = new javax.swing.JLabel();
        txt_Ps = new javax.swing.JTextField();
        txt_Gender = new javax.swing.JTextField();
        txt_No = new javax.swing.JTextField();
        txt_Name = new javax.swing.JTextField();
        btn_Save = new javax.swing.JButton();
        btn_Close = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tab_Payment = new javax.swing.JTable();
        tab_Paid = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_AmountReceivable = new javax.swing.JTextField();
        txt_PaidAmount = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_Arrears = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        mnb = new javax.swing.JMenuBar();
        menu_File = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        mnit_Back = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cashier Information");
        setResizable(true);

        pan_Top.setBackground(new java.awt.Color(228, 228, 228));
        pan_Top.setFont(new java.awt.Font("Bitstream Vera Sans", 1, 18));

        lab_TitleNo.setText("Patient No.:");

        lab_TitleName.setText("Name:");

        lab_TitlePs.setText("PS:");

        lab_TitleSex.setText("Gender:");

        txt_Ps.setEditable(false);

        txt_Gender.setEditable(false);

        txt_No.setEditable(false);

        txt_Name.setEditable(false);

        javax.swing.GroupLayout pan_TopLayout = new javax.swing.GroupLayout(pan_Top);
        pan_Top.setLayout(pan_TopLayout);
        pan_TopLayout.setHorizontalGroup(
            pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_TopLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lab_TitleName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lab_TitleNo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_No, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                    .addComponent(txt_Name, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
                .addGap(63, 63, 63)
                .addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lab_TitlePs)
                    .addComponent(lab_TitleSex))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_Ps)
                    .addComponent(txt_Gender, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        pan_TopLayout.setVerticalGroup(
            pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pan_TopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_TitleNo)
                    .addComponent(txt_No, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab_TitleSex))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pan_TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab_TitleName)
                    .addComponent(txt_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Ps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab_TitlePs))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pan_TopLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_Gender, txt_Name, txt_No, txt_Ps});

        btn_Save.setText("Save");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        btn_Close.setText("Close");
        btn_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	mnit_BackActionPerformed(evt);
            	//jButton1ActionPerformed(evt);
            }
        });

        tab_Payment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tab_Paid.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                    },
                    new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                    }
                ));
        tab_Payment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_PaymentMouseClicked(evt);
            }
        });
        tab_Payment.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tab_PaymentFocusLost(evt);
            }
        });
        tab_Payment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tab_PaymentKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tab_Payment);
        
        jScrollPane2.setViewportView(tab_Paid);

        jLabel1.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel1.setText("Amount Receivable:");

        txt_AmountReceivable.setEditable(false);
        txt_AmountReceivable.setFont(new java.awt.Font("新細明體", 1, 18));
        txt_AmountReceivable.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txt_PaidAmount.setFont(new java.awt.Font("新細明體", 1, 18)); // NOI18N
        txt_PaidAmount.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_PaidAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PaidAmountActionPerformed(evt);
            }
        });
        txt_PaidAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_PaidAmountKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_PaidAmountKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel2.setText("-");

        jLabel3.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel3.setText("Paid Amount:");

        jLabel4.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel4.setText("=");

        txt_Arrears.setEditable(false);
        txt_Arrears.setFont(new java.awt.Font("新細明體", 1, 18));
        txt_Arrears.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel5.setFont(new java.awt.Font("新細明體", 1, 18));
        jLabel5.setText("Arrears:");

        menu_File.setText("File");

        jMenuItem3.setText("Cashier Record");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        //menu_File.add(jMenuItem3);

        mnit_Back.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        mnit_Back.setText("Close");
        mnit_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnit_BackActionPerformed(evt);
            }
        });
        menu_File.add(mnit_Back);

        mnb.add(menu_File);

        setJMenuBar(mnb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pan_Top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_AmountReceivable, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_PaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btn_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_Close, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Arrears, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pan_Top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_AmountReceivable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(txt_PaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txt_Arrears, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Close)
                    .addComponent(btn_Save))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String getSqlPrefix(String sysname) {
    	if (sysname.equals("pha")) return "PH";
    	else if (sysname.equals("lab")) return "LA";
        else if (sysname.equals("reg")) return "RE";
        else if (sysname.equals("xray")) return "XR";
        else if (sysname.equals("bed")) return "BE"; 
        else return "";
    }
    private String getPaymentType(String sysname) {
    	if (sysname.equals("pha")) return "P";
    	else if (sysname.equals("lab")) return "L";
        else if (sysname.equals("reg")) return "R";
        else if (sysname.equals("xray")) return "X";
        else if (sysname.equals("bed")) return "B"; 
        else return "";
    }
    
    private void savePayment() {
    	if (txt_PaidAmount.getText().trim().equals("") || txt_AmountReceivable.getText().trim().equals("")) {
    		JOptionPane.showMessageDialog(null, "Save error");
    		return;
    	}
    	
    	try {
	    	Double paidMoney = Double.parseDouble(txt_PaidAmount.getText().trim());
	    	Double totalCost = Double.parseDouble(txt_AmountReceivable.getText().trim());
	    	Double remainingMoney = paidMoney;
	    	Double costOfItem = 0.0;
	    	Double arrearOfItem = 0.0;
	    	
	        // 判斷資料不是數值先把格子清空
	        for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
	                if (tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1) == null || !common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString()))
	                   tab_Payment.setValueAt("", i, tab_Payment.getColumnCount()-1);
	         }

        
            String sql = "";
            if (m_Sysname.equals("pha")) {
            	for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
                    if (common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())) {
                    	costOfItem = Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
                        if(costOfItem.compareTo(0.0) == 0) continue;
                    	String guid = tab_Payment.getValueAt(i, 0).toString();
                    	if(remainingMoney >= costOfItem ) {
                        	remainingMoney -= costOfItem;
                        	arrearOfItem = 0.0;
                        } else {
                        	arrearOfItem = costOfItem - remainingMoney;
                        	remainingMoney = 0.0;
                        }
                    	sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix(m_Sysname) +"00000001' ELSE "+
                                " INSERT (MAX(`no`), "+
                                "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                "SUBSTRING(MAX(`no`),3)+1) END  "+
                                ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType(m_Sysname) +"', " +
                                "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                "FROM cashier WHERE no LIKE '"+ getSqlPrefix(m_Sysname) +"%'  " ;
                    	//System.out.println(sql);
                    	DBC.executeUpdate(sql);
                    	
                   		sql = "UPDATE medicine_stock SET price = " + arrearOfItem + " WHERE guid = '" + guid + "'";
                    	//System.out.println(sql);
                    	DBC.executeUpdate(sql);
                    	
                    	if(remainingMoney.compareTo(0.0) == 0) break;
                    	//else System.out.println(remainingMoney);
                    }
                }
                if(totalCost.compareTo(paidMoney) == 0) {
	            	sql = "UPDATE registration_info SET pharmacy_payment = 'F'"
	                		//+ ", reg_cost = 0.0 "
	                    	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
	                        + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
	                        + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
	                        + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
	                        + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
	                DBC.executeUpdate(sql);
                }
            } else if (m_Sysname.equals("reg")) {
                for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
                    if (common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())) {
                    	costOfItem = Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
                    	if(costOfItem.compareTo(0.0) == 0) continue;
                    	String guid = tab_Payment.getValueAt(i, 0).toString();
                    	if(remainingMoney >= costOfItem ) {
                        	remainingMoney -= costOfItem;
                        	arrearOfItem = 0.0;
                        } else {
                        	arrearOfItem = costOfItem - remainingMoney;
                        	remainingMoney = 0.0;
                        }
                    	sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix(m_Sysname) +"00000001' ELSE "+
                                " INSERT (MAX(`no`), "+
                                "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                "SUBSTRING(MAX(`no`),3)+1) END  "+
                                ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType(m_Sysname) +"', " +
                                "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                "FROM cashier WHERE no LIKE '"+ getSqlPrefix(m_Sysname) +"%'  " ;
                    	//System.out.println(sql);
                    	DBC.executeUpdate(sql);
                    	
                    	sql = "UPDATE registration_info SET reg_cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
                    	//System.out.println(sql);
                    	DBC.executeUpdate(sql);
                    	
                    	if(remainingMoney.compareTo(0.0) == 0) break;
                    	//else System.out.println(remainingMoney);
                    }
                }
	            if(totalCost.compareTo(paidMoney) == 0) {
	            	sql = "UPDATE registration_info SET registration_payment = 'F'"
                    		+ ", reg_cost = 0.0 "
                        	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                            + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
                            + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                            + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
                            + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
	            	DBC.executeUpdate(sql);
	            }
            }else if (m_Sysname.equals("xray")) {
                for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
                    if (common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())) {
                    	costOfItem = Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
                    	if(costOfItem.compareTo(0.0) == 0) continue;
                    	String guid = tab_Payment.getValueAt(i, 0).toString();
                    	if(remainingMoney >= costOfItem ) {
                        	remainingMoney -= costOfItem;
                        	arrearOfItem = 0.0;
                        } else {
                        	arrearOfItem = costOfItem - remainingMoney;
                        	remainingMoney = 0.0;
                        }
                    	sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix(m_Sysname) +"00000001' ELSE "+
                                " INSERT (MAX(`no`), "+
                                "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                "SUBSTRING(MAX(`no`),3)+1) END  "+
                                ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType(m_Sysname) +"', " +
                                "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                "FROM cashier WHERE no LIKE '"+ getSqlPrefix(m_Sysname) +"%'  " ;
                    	//System.out.println(sql);
                    	DBC.executeUpdate(sql);
                    	
                    	sql = "UPDATE prescription SET cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
                    	DBC.executeUpdate(sql);
                    	
                    	if(remainingMoney.compareTo(0.0) == 0) break;
                    	//else System.out.println(remainingMoney);
                    }
                }
                if(totalCost.compareTo(paidMoney) == 0) {
                	sql = "UPDATE registration_info SET radiology_payment = 'F'"
                    		//+ ", reg_cost = 0.0 "
                        	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                            + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
                            + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                            + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
                            + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
                	//System.out.println(sql);
                	DBC.executeUpdate(sql);
                }
            } else if (m_Sysname.equals("lab")) {
            	for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
                    if (common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())) {
                    	costOfItem = Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
                    	if(costOfItem.compareTo(0.0) == 0) continue;
                    	String guid = tab_Payment.getValueAt(i, 0).toString();
                    	if(remainingMoney >= costOfItem ) {
                        	remainingMoney -= costOfItem;
                        	arrearOfItem = 0.0;
                        } else {
                        	arrearOfItem = costOfItem - remainingMoney;
                        	remainingMoney = 0.0;
                        }
                    	sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix(m_Sysname) +"00000001' ELSE "+
                                " INSERT (MAX(`no`), "+
                                "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                "SUBSTRING(MAX(`no`),3)+1) END  "+
                                ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType(m_Sysname) +"', " +
                                "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                "FROM cashier WHERE no LIKE '"+ getSqlPrefix(m_Sysname) +"%'  " ;
                    	//System.out.println(sql);
                    	DBC.executeUpdate(sql);
                    	
                    	sql = "UPDATE prescription SET cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
                    	DBC.executeUpdate(sql);
                    	
                    	if(remainingMoney.compareTo(0.0) == 0) break;
                    	//else System.out.println(remainingMoney);
                    }
                }
                if(totalCost.compareTo(paidMoney) == 0) {
                	sql = "UPDATE registration_info SET lab_payment = 'F'"
                    		//+ ", reg_cost = 0.0 "
                        	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                            + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
                            + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                            + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
                            + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
                	//System.out.println(sql);
                	DBC.executeUpdate(sql);
                }
            }else if (m_Sysname.equals("bed")) {
            	for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
                    if (common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString())) {
                    	costOfItem = Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
                    	if(costOfItem.compareTo(0.0) == 0) continue;
                    	String guid = tab_Payment.getValueAt(i, 0).toString();
                    	if(remainingMoney >= costOfItem ) {
                        	remainingMoney -= costOfItem;
                        	arrearOfItem = 0.0;
                        } else {
                        	arrearOfItem = costOfItem - remainingMoney;
                        	remainingMoney = 0.0;
                        }
                    	if (tab_Payment.getValueAt(i, 2).toString().compareTo("Bed") == 0) {
                    		sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                    "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix("bed") +"00000001' ELSE "+
                                    " INSERT (MAX(`no`), "+
                                    "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                    "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                    "SUBSTRING(MAX(`no`),3)+1) END  "+
                                    ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType("bed") +"', " +
                                    "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                    ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                    "FROM cashier WHERE no LIKE '"+ getSqlPrefix("bed") +"%'  " ;
                        	//System.out.println(sql);
                        	DBC.executeUpdate(sql);
                        	
                        	sql = "UPDATE bed_record SET cost = " + arrearOfItem + " WHERE guid = '" + tab_Payment.getValueAt(i, 3).toString() + "'";
                        	DBC.executeUpdate(sql);
                        	gBedTotal -= (costOfItem - arrearOfItem);
                        	
                        	if(gBedTotal.compareTo(0.0) == 0) {
                            	sql = "UPDATE registration_info SET bed_payment = 'F'"
                                		//+ ", reg_cost = 0.0 "
                                    	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                                        + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
                                        + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                                        + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
                                        + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
                            	//System.out.println(sql);
                            	DBC.executeUpdate(sql);
                            }
                    	} else if (tab_Payment.getValueAt(i, 2).toString().compareTo("Pharmacy") == 0) {
                    		sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                    "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix("pha") +"00000001' ELSE "+
                                    " INSERT (MAX(`no`), "+
                                    "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                    "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                    "SUBSTRING(MAX(`no`),3)+1) END  "+
                                    ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType("pha") +"', " +
                                    "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                    ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                    "FROM cashier WHERE no LIKE '"+ getSqlPrefix("pha") +"%'  " ;
                        	//System.out.println(sql);
                        	DBC.executeUpdate(sql);
                        	
                       		sql = "UPDATE medicine_stock SET price = " + arrearOfItem + " WHERE guid = '" + guid + "'";
                        	DBC.executeUpdate(sql);
                        	gPhaTotal -= (costOfItem - arrearOfItem);
                        	if(gPhaTotal.compareTo(0.0) == 0) {
            	            	sql = "UPDATE registration_info SET pharmacy_payment = 'F'"
            	                		//+ ", reg_cost = 0.0 "
            	                    	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
            	                        + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
            	                        + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
            	                        + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
            	                        + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
            	                DBC.executeUpdate(sql);
                            }
                        	
                    	} else if (tab_Payment.getValueAt(i, 2).toString().compareTo("Laboratory") == 0) {
                    		sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                    "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix("lab") +"00000001' ELSE "+
                                    " INSERT (MAX(`no`), "+
                                    "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                    "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                    "SUBSTRING(MAX(`no`),3)+1) END  "+
                                    ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType("lab") +"', " +
                                    "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                    ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                    "FROM cashier WHERE no LIKE '"+ getSqlPrefix("lab") +"%'  " ;
                        	//System.out.println(sql);
                        	DBC.executeUpdate(sql);
                        	
                        	sql = "UPDATE prescription SET cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
                        	DBC.executeUpdate(sql);
                        	gLabTotal -= (costOfItem - arrearOfItem);
                        	
                        	if(gLabTotal.compareTo(0.0) == 0) {
                            	sql = "UPDATE registration_info SET lab_payment = 'F'"
                                		//+ ", reg_cost = 0.0 "
                                    	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                                        + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
                                        + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                                        + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
                                        + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
                            	//System.out.println(sql);
                            	DBC.executeUpdate(sql);
                            }
                    	} else if (tab_Payment.getValueAt(i, 2).toString().compareTo("Radiology") == 0) {
                    		sql = "INSERT INTO cashier(no, reg_guid, p_no, type, payment_time, amount_receivable, paid_amount, arrears, s_no) " +
                                    "SELECT CASE  WHEN  MAX(`no`)  IS  NULL THEN '"+ getSqlPrefix("xray") +"00000001' ELSE "+
                                    " INSERT (MAX(`no`), "+
                                    "LENGTH(MAX(`no`)) - LENGTH(SUBSTRING(MAX(`no`),3)+1) + 1, "+
                                    "LENGTH(SUBSTRING(MAX(`no`),3)+1), "+
                                    "SUBSTRING(MAX(`no`),3)+1) END  "+
                                    ",'"+ m_Guid +"', '"+m_Pno+"', '"+ getPaymentType("xray") +"', " +
                                    "NOW(), "+costOfItem+", "+(costOfItem - arrearOfItem)+", " +
                                    ""+arrearOfItem+", '"+UserInfo.getUserNO()+"' " +
                                    "FROM cashier WHERE no LIKE '"+ getSqlPrefix("xray") +"%'  " ;
                        	//System.out.println(sql);
                        	DBC.executeUpdate(sql);
                        	
                        	sql = "UPDATE prescription SET cost = " + arrearOfItem + " WHERE guid = '" + guid + "'";
                        	DBC.executeUpdate(sql);
                        	gXrayTotal -= (costOfItem - arrearOfItem);
                        	
                        	if(gXrayTotal.compareTo(0.0) == 0) {
                            	sql = "UPDATE registration_info SET radiology_payment = 'F'"
                                		//+ ", reg_cost = 0.0 "
                                    	+ ",touchtime = RPAD((SELECT CASE WHEN MAX(B.touchtime) >= DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                                        + "THEN concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'), COUNT(B.touchtime)) " 
                                        + "ELSE DATE_FORMAT(now(),'%Y%m%d%H%i%S') " 
                                        + "END touchtime FROM (SELECT touchtime FROM registration_info) AS B WHERE B.touchtime LIKE " 
                                        + "concat(DATE_FORMAT(now(),'%Y%m%d%H%i%S'),'%')),20,'000000')   WHERE guid = '" + m_Guid + "'";
                            	//System.out.println(sql);
                            	DBC.executeUpdate(sql);
                            }
                    	}
                    	if(remainingMoney.compareTo(0.0) == 0) break;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Saved successfully.");

         } catch (Exception ex) {
            Logger.getLogger(Frm_CashierInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {
    	savePayment();
    	initTable();
    }

    private void tab_PaymentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tab_PaymentKeyReleased
        double total = 0;
        if (tab_Payment.getValueAt(tab_Payment.getSelectedRow(), tab_Payment.getColumnCount()-1) != null && common.Tools.isNumber(tab_Payment.getValueAt(tab_Payment.getSelectedRow(), tab_Payment.getColumnCount()-1).toString())) {
            for (int i = 0; i < tab_Payment.getRowCount() ; i++) {
                if ( common.Tools.isNumber(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString()))
                    total += Double.parseDouble(tab_Payment.getValueAt(i, tab_Payment.getColumnCount()-1).toString());
            }
            txt_AmountReceivable.setText(String.valueOf(total));
            txt_PaidAmountKeyReleased(null);
        }

    }//GEN-LAST:event_tab_PaymentKeyReleased

    //private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //if (txt_Arrears.getText().equals("0.0")) setFinish("F");
        //else setFinish("A");

    //}//GEN-LAST:event_jButton1ActionPerformed

    private void tab_PaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_PaymentMouseClicked
        tab_PaymentKeyReleased(null);
    }//GEN-LAST:event_tab_PaymentMouseClicked

    private void mnit_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnit_BackActionPerformed
    	//savePayment();
    	m_Frm.setEnabled(true);
        this.dispose();
}//GEN-LAST:event_mnit_BackActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.setEnabled(false);
        new Frm_CashierHistory(this,m_Pno).setVisible(true);
}//GEN-LAST:event_jMenuItem3ActionPerformed

    private void txt_PaidAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PaidAmountKeyReleased
        if (!txt_PaidAmount.getText().trim().equals("") && !txt_AmountReceivable.getText().trim().equals("") 
                && common.Tools.isNumber(txt_PaidAmount.getText().trim()) && common.Tools.isNumber(txt_AmountReceivable.getText().trim()) &&
                Double.parseDouble(txt_AmountReceivable.getText().trim()) >= Double.parseDouble(txt_PaidAmount.getText().trim())) {
            txt_Arrears.setText(String.valueOf(Double.parseDouble(txt_AmountReceivable.getText().trim()) - Double.parseDouble(txt_PaidAmount.getText().trim())));
        } else {
            txt_Arrears.setText("");
        }
    }//GEN-LAST:event_txt_PaidAmountKeyReleased

    private void txt_PaidAmountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PaidAmountKeyPressed
        
    }//GEN-LAST:event_txt_PaidAmountKeyPressed

    private void tab_PaymentFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tab_PaymentFocusLost
        tab_Payment.removeEditor();
        tab_PaymentKeyReleased(null);
    }//GEN-LAST:event_tab_PaymentFocusLost

    private void txt_PaidAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PaidAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PaidAmountActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Save;
    private javax.swing.JButton btn_Close;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lab_TitleName;
    private javax.swing.JLabel lab_TitleNo;
    private javax.swing.JLabel lab_TitlePs;
    private javax.swing.JLabel lab_TitleSex;
    private javax.swing.JMenu menu_File;
    private javax.swing.JMenuBar mnb;
    private javax.swing.JMenuItem mnit_Back;
    private javax.swing.JPanel pan_Top;
    private javax.swing.JTable tab_Payment;
    private javax.swing.JTable tab_Paid;
    private javax.swing.JTextField txt_AmountReceivable;
    private javax.swing.JTextField txt_Arrears;
    private javax.swing.JTextField txt_Gender;
    private javax.swing.JTextField txt_Name;
    private javax.swing.JTextField txt_No;
    private javax.swing.JTextField txt_PaidAmount;
    private javax.swing.JTextField txt_Ps;
    // End of variables declaration//GEN-END:variables

}
