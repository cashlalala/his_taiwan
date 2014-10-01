package casemgmt;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import laboratory.Frm_Laboratory;
import main.Frm_Main;
import multilingual.Language;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;

import radiology.Frm_Radiology;
import registration.Frm_RegAndInpatient;
import admission.Frm_InpatientHistory;
import admission.InpatientInterface;
import admission.SysName2Invisible;
import admission.SysName2Visible;
import casemgmt.Frm_Case;
import cc.johnwu.login.UserInfo;
import cc.johnwu.sql.DBC;
import diagnosis.DiagnosisInterface;
import diagnosis.Frm_DiagnosisDiagnostic;
import diagnosis.Frm_DiagnosisInfo;
import diagnosis.Frm_DiagnosisPrintChooser;
import errormessage.StoredErrorMessage;

public class Frm_WorkList extends javax.swing.JFrame implements
		InpatientInterface, DiagnosisInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long REFRASHTIME = 1000; // 自度刷新跨號資訊時間
	private RefrashWorkList m_RefrashWorkList;
	private RefrashWorkList m_RefrashWorkList2;
	private Thread m_Clock;
	private String m_SysName; // 系統名稱
	private boolean m_IsStop = false;
	private String m_RegGuid;
	private String m_Pno;
	/* 多國語言變數 */
	private Language paragraph = Language.getInstance();
	private String[] line = paragraph.setlanguage("DIAGNOSISWORKLIST").split(
			"\n");
	private String[] message = paragraph.setlanguage("MESSAGE").split("\n");
	/* 輸出錯誤資訊變數 */
	StoredErrorMessage ErrorMessage = new StoredErrorMessage();

	// LastSelectRow 最後選擇行號　　SysName　系統名
	public Frm_WorkList(int LastSelectRow, String SysName) {
		m_SysName = SysName;
		initComponents();
		// ---------迦納-------------------------
		// dateComboBox.setVisible(false);
		// btn_Search.setVisible(false);
		// lab_Date.setVisible(false);
		// ----------------------------------
		initWorkList();
		initLanguage();
		if (m_SysName.equals("dia")) {
			btn_Diagnostic.setVisible(true);
			btn_Diagnostic.setEnabled(false);
		}
		if (tab_WorkList.getRowCount() != 0) {
			btn_Enter.setEnabled(true);

			if (SysName.equals("dia")) {
				tab_WorkList.addRowSelectionInterval(LastSelectRow,
						LastSelectRow);
			} else {
				tab_WorkList.addRowSelectionInterval(0, 0);
			}
			tab_WorkList.changeSelection(LastSelectRow, LastSelectRow, false,
					false);
		}
		initLanguage();
	}

	// 初始化
	public void initWorkList() {
		// 依系統不同初始化
		if (m_SysName.equals("dia")) {
			dateComboBox.setVisible(false);
			btn_Search.setVisible(false);
			lab_Date.setVisible(false);
			this.setTitle("Diagnosis WorkList");
		} else if (m_SysName.equals("lab")) {
			this.setTitle("Laboratory WorkList");
			btn_RePrint.setVisible(false);
			lab_Name.setText("Staff");
			lab_Finish.setVisible(false);
			lab_FinishCount.setVisible(false);
		} else if (m_SysName.equals("xray")) {
			this.setTitle("Radiology(X-RAY) WorkList");
			btn_RePrint.setVisible(false);
			lab_Name.setText("Staff");
			lab_Finish.setVisible(false);
			lab_FinishCount.setVisible(false);
		} else if (m_SysName.equals("case")) {
			this.setTitle("Case Management WorkList");
			btn_RePrint.setVisible(false);
			lab_Name.setText("Staff");
			lab_Finish.setVisible(false);
			lab_FinishCount.setVisible(false);
			btn_RePrint.setVisible(false);
		} else if (m_SysName.equals("inp")) {
			this.setTitle("住院病患列表");
			dateComboBox.setVisible(false);
			btn_Search.setVisible(false);
			lab_Date.setVisible(false);
			lbl_InpNo.setVisible(true);
			lbl_InpNoVal.setVisible(true);
			this.repaint();
			this.setTitle(paragraph.getString("INPATIENT_WORKLIST"));
		}

		this.setExtendedState(Frm_WorkList.MAXIMIZED_BOTH); // 最大化
		this.setLocationRelativeTo(this);
		this.tab_WorkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // table不可按住多選
		table_FinishList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // table不可按住多選
		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable
			@Override
			public void windowClosing(WindowEvent windowevent) {
				mnit_CloseActionPerformed(null);
			}
		});
		this.m_RefrashWorkList = new RefrashWorkList(this.tab_WorkList,
				REFRASHTIME, m_SysName, "W");
		m_RefrashWorkList.setParentFrame(this);
		this.m_RefrashWorkList.start();

		m_RefrashWorkList2 = new RefrashWorkList(this.table_FinishList,
				REFRASHTIME, m_SysName, "F");
		m_RefrashWorkList2.setParentFrame(this);
		this.m_RefrashWorkList2.start();

		this.m_Clock = new Thread() { // Clock
			@Override
			@SuppressWarnings("static-access")
			public void run() {
				try {
					while (true) {
						lab_SystemTime.setText(new SimpleDateFormat(
								"MM/dd/yyyy HH:mm:ss").format(Calendar
								.getInstance().getTime()));
						showVisitsCount();
						this.sleep(500);
					}
				} catch (InterruptedException e) {
					ErrorMessage.setData(
							"Diagnosis",
							"Frm_DiagnosisWorkList",
							"initWorkList() - run()",
							e.toString().substring(
									e.toString().lastIndexOf(".") + 1,
									e.toString().length()));
				}
			}
		};
		this.m_Clock.start();
		this.txt_Name.setText(UserInfo.getUserName());
		this.txt_Poli.setText(UserInfo.getUserPoliclinic());

	}

	@SuppressWarnings("deprecation")
	private void initLanguage() {
		this.btn_RePrint.setText(paragraph.getLanguage(line, "PRINT"));
		// this.lab_Name.setText(paragraph.getLanguage(line, "NAME"));
		this.lab_Wait.setText(paragraph.getLanguage(line, "WAIT"));
		this.lab_poli.setText(paragraph.getLanguage(line, "POLI"));
		this.lab_Finish.setText(paragraph.getLanguage(line, "FINISH"));
		this.btn_Enter.setText(paragraph.getLanguage(message, "ENTER"));
		this.btn_Close.setText(paragraph.getLanguage(message, "CLOSE"));
		mn_Fiele.setText(paragraph.getLanguage(message, "FILE"));
		mnit_Enter.setText(paragraph.getLanguage(message, "ENTER"));
		mnit_Close.setText(paragraph.getLanguage(message, "CLOSE"));
		// this.setTitle(paragraph.getLanguage(line, "TITLEWORKLIST"));
	}

	// 計算待診 已診人數
	private void showVisitsCount() {
		// int finishCount = 0;
		// if (this.tab_WorkList.getRowCount() > 0)
		// for (int i = 0; i < this.tab_WorkList.getRowCount(); i++) {
		// if (this.tab_WorkList.getValueAt(i, 2) != null
		// && this.tab_WorkList.getValueAt(i, 2).toString()
		// .equals("F"))
		// finishCount++;
		// }
		this.lab_WaitCount.setText("" + tab_WorkList.getRowCount());
		this.lab_FinishCount.setText("" + table_FinishList.getRowCount());
	}

	// 進入看診
	public void setEnter(boolean finishState) {
		getSelectedTable();
		m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
		m_RefrashWorkList2.interrupt();
		m_Clock.interrupt();
		m_Pno = (String) this.tab_WorkListInterface.getValueAt(
				tab_WorkListInterface.getSelectedRow(), 4);
		m_RegGuid = (String) this.tab_WorkListInterface.getValueAt(
				tab_WorkListInterface.getSelectedRow(), 11);
		int getSelectRow = this.tab_WorkListInterface.getSelectedRow();

		if (m_SysName.equals("dia")) {
			boolean getFirst = false; // 是否為初診(用於彈出過敏設定)
			if (tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 1) != null
					&& tab_WorkListInterface
							.getValueAt(tab_WorkListInterface.getSelectedRow(),
									1).toString().equals("*")) {
				getFirst = true;
			}
			this.dummy = "dia_hist";
			new Frm_DiagnosisInfo(null, m_Pno, m_RegGuid, getSelectRow,
					finishState, getFirst).setVisible(true);
		} else if (m_SysName.equals("lab")) {
			m_Pno = (String) this.tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 5);
			new Frm_Laboratory(m_Pno, m_RegGuid, getSelectRow, finishState)
					.setVisible(true);
		} else if (m_SysName.equals("xray")) {
			m_Pno = (String) this.tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 5);
			new Frm_Radiology(m_Pno, m_RegGuid, getSelectRow, finishState)
					.setVisible(true);
		} else if (m_SysName.equals("case")) {
			if (tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 3) != null
					&& tab_WorkList
							.getValueAt(tab_WorkListInterface.getSelectedRow(),
									3).toString().equals("F")) {
				new Frm_Case(m_Pno, m_RegGuid, true, "").setVisible(true);
			} else {
				new Frm_Case(m_Pno, m_RegGuid, false, "").setVisible(true);
			}

		} else if (m_SysName.equals("inp")) {
			this.dummy = "inp_hist";
			m_RegGuid = (String) tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 13);
			m_Pno = (String) tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 0);
			new Frm_DiagnosisInfo(this, m_Pno, m_RegGuid, getSelectRow, true,
					false).setVisible(true);
		}
		this.dispose();
	}

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		pan_Center = new javax.swing.JPanel();
		span_WaitDiagnosis = new javax.swing.JScrollPane();
		tab_WorkList = new javax.swing.JTable();
		pan_Top = new javax.swing.JPanel();
		lab_Name = new javax.swing.JLabel();
		lab_Name.setHorizontalAlignment(SwingConstants.LEFT);
		txt_Name = new javax.swing.JTextField();
		txt_Name.setHorizontalAlignment(SwingConstants.LEFT);
		pan_Right = new javax.swing.JPanel();
		btn_Close = new javax.swing.JButton();
		btn_Enter = new javax.swing.JButton();
		btn_RePrint = new javax.swing.JButton();
		mnb = new javax.swing.JMenuBar();
		mn_Fiele = new javax.swing.JMenu();
		mnit_Enter = new javax.swing.JMenuItem();
		mnit_Close = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("WorkList");

		tab_WorkList.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {}, {} }, new String[] {

				}));
		tab_WorkList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		tab_WorkList.setRowHeight(25);
		tab_WorkList.getTableHeader().setReorderingAllowed(false);
		tab_WorkList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_WorkListMouseClicked(evt);
			}
		});
		tab_WorkList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_WorkListKeyPressed(evt);
			}
		});

		span_WaitDiagnosis.setViewportView(tab_WorkList);

		JScrollPane span_Finish = new JScrollPane();

		javax.swing.GroupLayout pan_CenterLayout = new javax.swing.GroupLayout(
				pan_Center);
		pan_CenterLayout
				.setHorizontalGroup(pan_CenterLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								pan_CenterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_CenterLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																span_Finish,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																1614,
																Short.MAX_VALUE)
														.addComponent(
																span_WaitDiagnosis,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																1614,
																Short.MAX_VALUE))
										.addContainerGap()));
		pan_CenterLayout.setVerticalGroup(pan_CenterLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				pan_CenterLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(span_WaitDiagnosis,
								GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(span_Finish, GroupLayout.DEFAULT_SIZE,
								448, Short.MAX_VALUE).addContainerGap()));

		table_FinishList = new JTable();
		span_Finish.setViewportView(table_FinishList);

		table_FinishList.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { {}, {}, {} }, new String[] {

				}));
		table_FinishList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		table_FinishList.setRowHeight(25);
		table_FinishList.getTableHeader().setReorderingAllowed(false);
		table_FinishList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tab_FinishListMouseClicked(evt);
			}
		});
		table_FinishList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				tab_FinishListKeyPressed(evt);
			}
		});

		pan_Center.setLayout(pan_CenterLayout);

		lab_Name.setText("Doctor");

		txt_Name.setEditable(false);

		btn_Close.setText(this.paragraph.getString("CLOSE")); //$NON-NLS-1$ //$NON-NLS-2$
		btn_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CloseActionPerformed(evt);
			}
		});

		btn_Enter.setText(this.paragraph.getString("ENTER")); //$NON-NLS-1$ //$NON-NLS-2$
		btn_Enter.setEnabled(false);
		btn_Enter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_EnterActionPerformed(evt);
			}
		});

		btn_RePrint.setText(this.paragraph.getString("PRINT")); //$NON-NLS-1$ //$NON-NLS-2$
		btn_RePrint.setEnabled(false);
		btn_RePrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_RePrintActionPerformed(evt);
			}
		});

		btn_Diagnostic = new JButton(
				this.paragraph.getString("MEDICAL_HISTORYT")); //$NON-NLS-1$ //$NON-NLS-2$
		btn_Diagnostic.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onDiagnosticClicked(evt);
			}

		});
		btn_Diagnostic.setVisible(false);

		btn_Reg = new JButton(this.paragraph.getString("REGISTRATION")); //$NON-NLS-1$ //$NON-NLS-2$
		btn_Reg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onRegistrationClicked(e);
			}
		});

		btn_CheckOut = new JButton(this.paragraph.getString("CHECKOUT")); //$NON-NLS-1$ //$NON-NLS-2$
		btn_CheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCheckOut(e);
			}
		});

		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_RightLayout.setHorizontalGroup(pan_RightLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				pan_RightLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								pan_RightLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(btn_Enter,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 133,
												Short.MAX_VALUE)
										.addComponent(btn_RePrint,
												GroupLayout.DEFAULT_SIZE, 133,
												Short.MAX_VALUE)
										.addComponent(btn_Close,
												GroupLayout.DEFAULT_SIZE, 133,
												Short.MAX_VALUE)
										.addComponent(btn_Diagnostic,
												GroupLayout.DEFAULT_SIZE, 133,
												Short.MAX_VALUE)
										.addComponent(btn_Reg,
												GroupLayout.DEFAULT_SIZE, 133,
												Short.MAX_VALUE)
										.addComponent(btn_CheckOut,
												GroupLayout.DEFAULT_SIZE, 133,
												Short.MAX_VALUE))
						.addContainerGap()));
		pan_RightLayout.setVerticalGroup(pan_RightLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				pan_RightLayout.createSequentialGroup().addContainerGap()
						.addComponent(btn_Enter)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btn_RePrint)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btn_Close)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btn_Diagnostic)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btn_Reg)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btn_CheckOut)
						.addContainerGap(277, Short.MAX_VALUE)));
		pan_Right.setLayout(pan_RightLayout);

		mn_Fiele.setText("File");

		mnit_Enter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_SPACE, 0));
		mnit_Enter.setText("Enter");
		mnit_Enter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_EnterActionPerformed(evt);
			}
		});
		mn_Fiele.add(mnit_Enter);

		mnit_Close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_ESCAPE, 0));
		mnit_Close.setText("Close");
		mnit_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mnit_CloseActionPerformed(evt);
			}
		});
		mn_Fiele.add(mnit_Close);

		mnb.add(mn_Fiele);

		setJMenuBar(mnb);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
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
												.addComponent(
														pan_Top,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
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
								.addContainerGap()
								.addComponent(pan_Top,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										99,
										javax.swing.GroupLayout.PREFERRED_SIZE)
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
		pan_Top.setLayout(new GridLayout(3, 10, 10, 5));
		pan_Top.add(lab_Name);
		pan_Top.add(txt_Name);
		lab_poli = new javax.swing.JLabel();
		lab_poli.setHorizontalAlignment(SwingConstants.LEFT);

		lab_poli.setText("Department");
		pan_Top.add(lab_poli);
		txt_Poli = new javax.swing.JTextField();
		txt_Poli.setHorizontalAlignment(SwingConstants.LEFT);

		txt_Poli.setEditable(false);
		pan_Top.add(txt_Poli);
		lbl_InpNo = new JLabel(this.paragraph.getString("INPATIENT_NO")); //$NON-NLS-1$ //$NON-NLS-2$
		lbl_InpNo.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_InpNo.setVisible(false);
		// lbl_InpNo.setLocation(pt);
		pan_Top.add(lbl_InpNo);
		lbl_InpNoVal = new JLabel("----"); //$NON-NLS-1$ //$NON-NLS-2$
		lbl_InpNoVal.setVisible(false);
		pan_Top.add(lbl_InpNoVal);
		lab_SystemTime = new javax.swing.JLabel();

		lab_SystemTime.setFont(new java.awt.Font("UnDotum", 0, 18));
		lab_SystemTime.setText("-----");
		pan_Top.add(lab_SystemTime);
		lab_Wait = new javax.swing.JLabel();
		lab_Wait.setHorizontalAlignment(SwingConstants.LEFT);

		lab_Wait.setText("Await");

		pan_Top.add(lab_Wait);
		lab_WaitCount = new javax.swing.JLabel();
		lab_WaitCount.setHorizontalAlignment(SwingConstants.LEFT);

		lab_WaitCount.setText("-----");
		pan_Top.add(lab_WaitCount);
		lab_Finish = new javax.swing.JLabel();
		lab_Finish.setHorizontalAlignment(SwingConstants.LEFT);

		lab_Finish.setText("Finish");
		pan_Top.add(lab_Finish);
		lab_FinishCount = new javax.swing.JLabel();
		lab_FinishCount.setHorizontalAlignment(SwingConstants.LEFT);

		lab_FinishCount.setText("-----");
		pan_Top.add(lab_FinishCount);

		label_6 = new JLabel("");
		pan_Top.add(label_6);

		label = new JLabel("");
		pan_Top.add(label);

		label_1 = new JLabel("");
		pan_Top.add(label_1);
		lab_Date = new javax.swing.JLabel();
		lab_Date.setHorizontalAlignment(SwingConstants.LEFT);

		lab_Date.setText("Date:");
		pan_Top.add(lab_Date);
		dateComboBox = new cc.johnwu.date.DateComboBox();
		pan_Top.add(dateComboBox);
		btn_Search = new javax.swing.JButton();

		btn_Search.setText("Search");
		btn_Search.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_SearchActionPerformed(evt);
			}
		});
		pan_Top.add(btn_Search);

		label_2 = new JLabel("");
		pan_Top.add(label_2);

		label_3 = new JLabel("");
		pan_Top.add(label_3);

		label_4 = new JLabel("");
		pan_Top.add(label_4);

		label_5 = new JLabel("");
		pan_Top.add(label_5);

		pack();
		initDataBindings();
	}// </editor-fold>//GEN-END:initComponents

	private String dummy = "";

	private void onDiagnosticClicked(java.awt.event.ActionEvent evt) {
		getSelectedTable();
		if (m_SysName.equals("dia")) {
			dummy = "dia_hist";

			m_Pno = (String) tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 4);

			m_RegGuid = (String) tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 11);

			if (tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 2) != null
					&& tab_WorkListInterface
							.getValueAt(tab_WorkListInterface.getSelectedRow(),
									2).toString().equals("F")) {
			}

			Frm_DiagnosisDiagnostic frm_DiagnosisDiagnostic = new Frm_DiagnosisDiagnostic(
					this, m_Pno, (String) tab_WorkListInterface.getValueAt(
							tab_WorkListInterface.getSelectedRow(), 5),
					m_RegGuid);
			frm_DiagnosisDiagnostic.setEntry("clinic_worklist_hist");
			frm_DiagnosisDiagnostic.setVisible(true);
		} else {
			dummy = "inp_hist";
			String pname = (String) tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 3);
			m_Pno = (String) tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 0);
			new Frm_InpatientHistory(this, m_Pno, pname).setVisible(true);
		}
		m_RefrashWorkList.stopRunning();
		m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
		m_RefrashWorkList2.stopRunning();
		m_RefrashWorkList2.interrupt();
		m_Clock.interrupt();
		this.dispose();
	}

	private void onCheckOut(ActionEvent e) {
		getSelectedTable();
		String bedRecGuid = (String) tab_WorkListInterface.getValueAt(
				tab_WorkListInterface.getSelectedRow(), 12);
		String sql = String
				.format("update bed_record set status = 'L', checkoutTime = now() where bed_record.guid = '%s'",
						bedRecGuid);
		try {
			DBC.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void onRegistrationClicked(MouseEvent e) {
		getSelectedTable();
		m_Pno = (String) ((m_SysName.equals("inp")) ? tab_WorkListInterface
				.getValueAt(tab_WorkList.getSelectedRow(), 0)
				: tab_WorkListInterface.getValueAt(
						tab_WorkList.getSelectedRow(), 4));
		new Frm_RegAndInpatient(this, m_Pno).setVisible(true);
		this.setVisible(false);
	}

	private void mnit_EnterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_EnterActionPerformed
		if (btn_Enter.isEnabled()) {
			btn_EnterActionPerformed(null);
		}
	}// GEN-LAST:event_mnit_EnterActionPerformed

	private void mnit_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mnit_CloseActionPerformed

		m_RefrashWorkList.stopRunning();
		m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
		m_Clock.interrupt();
		new Frm_Main().setVisible(true);
		this.dispose();
	}// GEN-LAST:event_mnit_CloseActionPerformed

	private void tab_WorkListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_WorkListMouseClicked
		if (evt.getClickCount() == 2) {
			btn_EnterActionPerformed(null);
		}
		tab_WorkListKeyPressed(null);
	}// GEN-LAST:event_tab_WorkListMouseClicked

	@SuppressWarnings("deprecation")
	private void tab_WorkListKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tab_WorkListKeyPressed
		table_FinishList.clearSelection();
		if (this.tab_WorkList.getRowCount() > 0) {
			this.btn_Diagnostic.setEnabled(true);
			this.btn_Enter.setEnabled(true);
			if (this.m_SysName.equals("dia")
					&& this.tab_WorkList.getValueAt(
							this.tab_WorkList.getSelectedRow(), 3) != null) {
				this.btn_Enter.setText(paragraph.getLanguage(line, "EDIT"));
				this.btn_RePrint.setEnabled(true);
			} else if (this.m_SysName.equals("dia")) {
				this.btn_Enter.setText(paragraph.getLanguage(message, "ENTER"));
				this.btn_RePrint.setEnabled(false);
			}
		}
	}// GEN-LAST:event_tab_WorkListKeyPressed

	private void tab_FinishListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tab_WorkListMouseClicked
		if (evt.getClickCount() == 2) {
			btn_EnterActionPerformed(null);
		}
		tab_FinishListKeyPressed(null);
	}// GEN-LAST:event_tab_WorkListMouseClicked

	@SuppressWarnings("deprecation")
	private void tab_FinishListKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_tab_WorkListKeyPressed
		tab_WorkList.clearSelection();
		if (this.table_FinishList.getRowCount() > 0) {
			this.btn_Enter.setEnabled(true);
			this.btn_Diagnostic.setEnabled(true);
			if (this.m_SysName.equals("dia")
					&& this.table_FinishList.getValueAt(
							this.table_FinishList.getSelectedRow(), 3) != null) {
				this.btn_Enter.setText(paragraph.getLanguage(line, "EDIT"));
				this.btn_RePrint.setEnabled(true);
			} else if (this.m_SysName.equals("dia")) {
				this.btn_Enter.setText(paragraph.getLanguage(message, "ENTER"));
				this.btn_RePrint.setEnabled(false);
			}
		}
	}// GEN-LAST:event_tab_WorkListKeyPressed

	private void getSelectedTable() {
		tab_WorkListInterface = (tab_WorkList.getSelectedRow() == -1) ? (table_FinishList
				.getSelectedRow() == -1) ? tab_WorkList : table_FinishList
				: tab_WorkList;
	}

	@SuppressWarnings("deprecation")
	private void btn_EnterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_EnterActionPerformed
		getSelectedTable();
		boolean finishState = false;
		if (!m_SysName.equals("inp")) {
			if (tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 2) != null
					&& tab_WorkListInterface.getValueAt(
							tab_WorkListInterface.getSelectedRow(), 2).equals(
							"F")) {

				Object[] options = { paragraph.getLanguage(message, "YES"),
						paragraph.getLanguage(message, "NO") };
				int dialog = JOptionPane.showOptionDialog(new Frame(),
						paragraph.getLanguage(message,
								"DOYOUWANTTOCHANGETHEDATA"), paragraph
								.getLanguage(message, "MESSAGE"),
						JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);

				if (dialog == 0) {
					finishState = true;
					m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
					m_RefrashWorkList2.interrupt(); // 終止重複讀取掛號表單
					setEnter(finishState);
				}
			} else if ((tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 2) == null)
					|| (tab_WorkListInterface.getValueAt(
							tab_WorkListInterface.getSelectedRow(), 2))
							.toString().compareTo("") == 0) {
				m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
				m_RefrashWorkList2.interrupt(); // 終止重複讀取掛號表單
				setEnter(finishState);
			}
		} else {
			if (tab_WorkListInterface.getValueAt(
					tab_WorkListInterface.getSelectedRow(), 0) != null) {
				m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
				m_RefrashWorkList2.interrupt(); // 終止重複讀取掛號表單
				setEnter(finishState);
			}
		}

	}// GEN-LAST:event_btn_EnterActionPerformed

	private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CloseActionPerformed
		mnit_CloseActionPerformed(null);
	}// GEN-LAST:event_btn_CloseActionPerformed

	private void btn_RePrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_RePrintActionPerformed
		getSelectedTable();
		String regGuid = (String) tab_WorkListInterface.getValueAt(
				tab_WorkListInterface.getSelectedRow(), 11);
		System.out.println(String.format("Selected Patient [%s]", regGuid));
		Frm_DiagnosisPrintChooser chooser = new Frm_DiagnosisPrintChooser(
				regGuid);
		chooser.setLocationRelativeTo(this);
		chooser.setVisible(true);
	}// GEN-LAST:event_btn_RePrintActionPerformed

	private void btn_SearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_SearchActionPerformed
		if (m_IsStop) {
			m_IsStop = false;
			dateComboBox.setEnabled(true);
			btn_Search.setText("Search");
			dateComboBox.setValue(new SimpleDateFormat("yyyy-MM-dd")
					.format(Calendar.getInstance().getTime()));
			this.m_RefrashWorkList = new RefrashWorkList(this.tab_WorkList,
					REFRASHTIME, m_SysName, "W");
			this.m_RefrashWorkList.start();
			this.m_RefrashWorkList2 = new RefrashWorkList(
					this.table_FinishList, REFRASHTIME, m_SysName, "F");
			this.m_RefrashWorkList2.start();
			this.m_Clock = new Thread() { // Clock
				@Override
				@SuppressWarnings("static-access")
				public void run() {
					try {
						while (true) {
							lab_SystemTime.setText(new SimpleDateFormat(
									"yyyy/MM/dd HH:mm:ss").format(Calendar
									.getInstance().getTime()));
							showVisitsCount();
							this.sleep(500);
						}
					} catch (InterruptedException e) {
						ErrorMessage.setData(
								"Diagnosis",
								"Frm_DiagnosisWorkList",
								"initWorkList() - run()",
								e.toString().substring(
										e.toString().lastIndexOf(".") + 1,
										e.toString().length()));
					}
				}
			};
		} else {
			m_IsStop = true;
			dateComboBox.setEnabled(false);
			btn_Search.setText("Cancels Search");
			m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
			m_RefrashWorkList.getSelectDate(dateComboBox.getValue(), "W");
			m_RefrashWorkList2.interrupt(); // 終止重複讀取掛號表單
			m_RefrashWorkList2.getSelectDate(dateComboBox.getValue(), "F");
		}
	}// GEN-LAST:event_btn_SearchActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btn_Close;
	private javax.swing.JButton btn_Enter;
	private javax.swing.JButton btn_RePrint;
	private javax.swing.JButton btn_Search;
	private cc.johnwu.date.DateComboBox dateComboBox;
	private javax.swing.JLabel lab_Date;
	private javax.swing.JLabel lab_Finish;
	private javax.swing.JLabel lab_FinishCount;
	private javax.swing.JLabel lab_Name;
	private javax.swing.JLabel lab_SystemTime;
	private javax.swing.JLabel lab_Wait;
	private javax.swing.JLabel lab_WaitCount;
	private javax.swing.JLabel lab_poli;
	private javax.swing.JMenu mn_Fiele;
	private javax.swing.JMenuBar mnb;
	private javax.swing.JMenuItem mnit_Close;
	private javax.swing.JMenuItem mnit_Enter;
	private javax.swing.JPanel pan_Center;
	private javax.swing.JPanel pan_Right;
	private javax.swing.JPanel pan_Top;
	private javax.swing.JScrollPane span_WaitDiagnosis;
	private javax.swing.JTable tab_WorkList;
	private javax.swing.JTable tab_WorkListInterface;
	private javax.swing.JTextField txt_Name;
	private javax.swing.JTextField txt_Poli;
	public JButton btn_Diagnostic;
	public JButton btn_CheckOut;
	public JButton btn_Reg;

	public JLabel lbl_InpNoVal;
	private JLabel lbl_InpNo;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JTable table_FinishList;

	protected void initDataBindings() {
		BeanProperty<JButton, Boolean> jButtonBeanProperty = BeanProperty
				.create("visible");
		AutoBinding<String, String, JButton, Boolean> autoBinding = Bindings
				.createAutoBinding(UpdateStrategy.READ, m_SysName,
						btn_CheckOut, jButtonBeanProperty, "bindBtnClinVis2Sys");
		autoBinding.setConverter(new SysName2Visible());
		autoBinding.bind();
		//
		AutoBinding<String, String, JButton, Boolean> autoBinding_1 = Bindings
				.createAutoBinding(UpdateStrategy.READ, m_SysName, btn_Reg,
						jButtonBeanProperty, "bindBtnRegVis2Sys");
		autoBinding_1.setConverter(new SysName2Visible());
		autoBinding_1.bind();
		//
		BeanProperty<JLabel, Boolean> jLabelBeanProperty = BeanProperty
				.create("visible");
		AutoBinding<String, String, JLabel, Boolean> autoBinding_2 = Bindings
				.createAutoBinding(UpdateStrategy.READ, m_SysName, lab_Wait,
						jLabelBeanProperty);
		autoBinding_2.setConverter(new SysName2Invisible());
		autoBinding_2.bind();
		//
		AutoBinding<String, String, JLabel, Boolean> autoBinding_3 = Bindings
				.createAutoBinding(UpdateStrategy.READ, m_SysName,
						lab_WaitCount, jLabelBeanProperty);
		autoBinding_3.setConverter(new SysName2Invisible());
		autoBinding_3.bind();
		//
		AutoBinding<String, String, JLabel, Boolean> autoBinding_4 = Bindings
				.createAutoBinding(UpdateStrategy.READ, m_SysName, lab_Finish,
						jLabelBeanProperty);
		autoBinding_4.setConverter(new SysName2Invisible());
		autoBinding_4.bind();
		//
		AutoBinding<String, String, JLabel, Boolean> autoBinding_5 = Bindings
				.createAutoBinding(UpdateStrategy.READ, m_SysName,
						lab_FinishCount, jLabelBeanProperty);
		autoBinding_5.setConverter(new SysName2Invisible());
		autoBinding_5.bind();
	}

	@Override
	public void reSetEnable() {
		new Frm_WorkList(0, m_SysName).setVisible(true);
	}

	@Override
	public void getAllergy() {
	}

	@Override
	public String from() {
		return this.getClass().getName() + dummy;
	}
}