package worklist;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import laboratory.Frm_Laboratory;
import main.Frm_Main;
import multilingual.Language;
import radiology.Frm_Radiology;
import admission.Frm_InpatientInfo;
import casemgmt.Frm_Case;
import cc.johnwu.login.UserInfo;
import diagnosis.Frm_DiagnosisDiagnostic;
import diagnosis.Frm_DiagnosisInfo;
import diagnosis.Frm_DiagnosisPrintChooser;
import errormessage.StoredErrorMessage;

public class Frm_WorkList extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long REFRASHTIME = 1000; // 自度刷新跨號資訊時間
	private RefrashWorkList m_RefrashWorkList;
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
			this.setTitle(paragraph.getString("INPATIENT_WORKLIST"));
		}

		this.setExtendedState(Frm_WorkList.MAXIMIZED_BOTH); // 最大化
		this.setLocationRelativeTo(this);
		this.tab_WorkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // table不可按住多選
		addWindowListener(new WindowAdapter() { // 畫面關閉原視窗enable
			@Override
			public void windowClosing(WindowEvent windowevent) {
				mnit_CloseActionPerformed(null);
			}
		});
		this.m_RefrashWorkList = new RefrashWorkList(this.tab_WorkList,
				REFRASHTIME, m_SysName);
		m_RefrashWorkList.setParentFrame(this);
		this.m_RefrashWorkList.start();
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
		int finishCount = 0;
		if (this.tab_WorkList.getRowCount() > 0)
			for (int i = 0; i < this.tab_WorkList.getRowCount(); i++) {
				if (this.tab_WorkList.getValueAt(i, 2) != null
						&& this.tab_WorkList.getValueAt(i, 2).toString()
								.equals("F"))
					finishCount++;
			}
		this.lab_WaitCount.setText(""
				+ (tab_WorkList.getRowCount() - finishCount));
		this.lab_FinishCount.setText("" + finishCount);
	}

	// 進入看診
	public void setEnter(boolean finishState) {
		m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
		m_Clock.interrupt();
		m_Pno = (String) this.tab_WorkList.getValueAt(
				tab_WorkList.getSelectedRow(), 4);
		m_RegGuid = (String) this.tab_WorkList.getValueAt(
				tab_WorkList.getSelectedRow(), 11);
		int getSelectRow = this.tab_WorkList.getSelectedRow();

		if (m_SysName.equals("dia")) {
			boolean getFirst = false; // 是否為初診(用於彈出過敏設定)
			if (tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 1) != null
					&& tab_WorkList
							.getValueAt(tab_WorkList.getSelectedRow(), 1)
							.toString().equals("*")) {
				getFirst = true;
			}
			new Frm_DiagnosisInfo(m_Pno, m_RegGuid, getSelectRow, finishState,
					getFirst).setVisible(true);
		} else if (m_SysName.equals("lab")) {
			m_Pno = (String) this.tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 5);
			new Frm_Laboratory(m_Pno, m_RegGuid, getSelectRow, finishState)
					.setVisible(true);
		} else if (m_SysName.equals("xray")) {
			m_Pno = (String) this.tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 5);
			new Frm_Radiology(m_Pno, m_RegGuid, getSelectRow, finishState)
					.setVisible(true);
		} else if (m_SysName.equals("case")) {
			if (tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 3) != null
					&& tab_WorkList
							.getValueAt(tab_WorkList.getSelectedRow(), 3)
							.toString().equals("F")) {
				new Frm_Case(m_Pno, m_RegGuid, true, "").setVisible(true);
			} else {
				new Frm_Case(m_Pno, m_RegGuid, false, "").setVisible(true);
			}

		} else if (m_SysName.equals("inp")) {
			boolean getFirst = false; // 是否為初診(用於彈出過敏設定)
			if (tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 1) != null
					&& tab_WorkList
							.getValueAt(tab_WorkList.getSelectedRow(), 1)
							.toString().equals("*")) {
				getFirst = true;
			}
			new Frm_InpatientInfo(m_Pno, m_RegGuid, getSelectRow, finishState,
					getFirst).setVisible(true);
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
		lab_poli = new javax.swing.JLabel();
		lab_Finish = new javax.swing.JLabel();
		lab_FinishCount = new javax.swing.JLabel();
		lab_Wait = new javax.swing.JLabel();
		lab_WaitCount = new javax.swing.JLabel();
		txt_Name = new javax.swing.JTextField();
		txt_Poli = new javax.swing.JTextField();
		lab_SystemTime = new javax.swing.JLabel();
		dateComboBox = new cc.johnwu.date.DateComboBox();
		btn_Search = new javax.swing.JButton();
		lab_Date = new javax.swing.JLabel();
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
												span_WaitDiagnosis,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												601, Short.MAX_VALUE)
										.addContainerGap()));
		pan_CenterLayout.setVerticalGroup(pan_CenterLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				pan_CenterLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(span_WaitDiagnosis,
								javax.swing.GroupLayout.DEFAULT_SIZE, 434,
								Short.MAX_VALUE).addContainerGap()));

		lab_Name.setText("Doctor");

		lab_poli.setText("Department");

		lab_Finish.setText("Finish");

		lab_FinishCount.setText("-----");

		lab_Wait.setText("Await");

		lab_WaitCount.setText("-----");

		txt_Name.setEditable(false);

		txt_Poli.setEditable(false);

		lab_SystemTime.setFont(new java.awt.Font("UnDotum", 0, 18));
		lab_SystemTime.setText("-----");

		btn_Search.setText("Search");
		btn_Search.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_SearchActionPerformed(evt);
			}
		});

		lab_Date.setText("Date:");

		javax.swing.GroupLayout pan_TopLayout = new javax.swing.GroupLayout(
				pan_Top);
		pan_Top.setLayout(pan_TopLayout);
		pan_TopLayout
				.setHorizontalGroup(pan_TopLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_TopLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(lab_Wait)
														.addComponent(lab_Name)
														.addComponent(lab_Date))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																pan_TopLayout
																		.createSequentialGroup()
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								txt_Name,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								160,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lab_WaitCount,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								29,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								lab_poli)
																						.addComponent(
																								lab_Finish))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				pan_TopLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								pan_TopLayout
																										.createSequentialGroup()
																										.addComponent(
																												txt_Poli,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												160,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												lab_SystemTime,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												207,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								lab_FinishCount,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								20,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																pan_TopLayout
																		.createSequentialGroup()
																		.addComponent(
																				dateComboBox,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				btn_Search,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				152,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(137, Short.MAX_VALUE)));
		pan_TopLayout
				.setVerticalGroup(pan_TopLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pan_TopLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lab_Name)
														.addComponent(
																txt_Name,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(lab_poli)
														.addComponent(
																txt_Poli,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lab_SystemTime))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(lab_Wait)
														.addComponent(
																lab_Finish)
														.addComponent(
																lab_FinishCount)
														.addComponent(
																lab_WaitCount))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												pan_TopLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(lab_Date)
														.addComponent(
																dateComboBox,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btn_Search))
										.addGap(12, 12, 12)));

		btn_Close.setText("Close");
		btn_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_CloseActionPerformed(evt);
			}
		});

		btn_Enter.setText("Enter");
		btn_Enter.setEnabled(false);
		btn_Enter.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_EnterActionPerformed(evt);
			}
		});

		btn_RePrint.setText("Print");
		btn_RePrint.setEnabled(false);
		btn_RePrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_RePrintActionPerformed(evt);
			}
		});
		
		btn_Diagnostic = new JButton("Diagnostic");
		btn_Diagnostic.addActionListener(new java.awt.event.ActionListener() {
			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				onDiagnosticClicked(evt);
			}


		});
		btn_Diagnostic.setVisible(false);

		javax.swing.GroupLayout pan_RightLayout = new javax.swing.GroupLayout(
				pan_Right);
		pan_RightLayout.setHorizontalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(pan_RightLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_Enter, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_RePrint, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Close, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
						.addComponent(btn_Diagnostic, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
					.addContainerGap())
		);
		pan_RightLayout.setVerticalGroup(
			pan_RightLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(pan_RightLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btn_Enter)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_RePrint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Close)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_Diagnostic)
					.addContainerGap(335, Short.MAX_VALUE))
		);
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

		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	private void onDiagnosticClicked(java.awt.event.ActionEvent evt) {
		m_Pno = (String) tab_WorkList.getValueAt(
				tab_WorkList.getSelectedRow(), 4);
		
		m_RegGuid = (String) tab_WorkList.getValueAt(
				tab_WorkList.getSelectedRow(), 11);
		
		boolean finishState = false;
		if (tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 2) != null
				&& tab_WorkList
						.getValueAt(tab_WorkList.getSelectedRow(), 2)
						.toString().equals("F")) {
			finishState = true;
		}
		
		int getSelectRow = tab_WorkList.getSelectedRow();
		
		Frm_DiagnosisDiagnostic frm_DiagnosisDiagnostic = new Frm_DiagnosisDiagnostic(new Frm_DiagnosisInfo(m_Pno, m_RegGuid, getSelectRow, finishState,
				false), m_Pno, (String) tab_WorkList.getValueAt(
				tab_WorkList.getSelectedRow(), 1));
		frm_DiagnosisDiagnostic.setIsFromDiagInfo(false);
		frm_DiagnosisDiagnostic.setVisible(true);
		
		m_RefrashWorkList.stopRunning();
		m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
		m_Clock.interrupt();
		this.dispose();
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
		if (this.tab_WorkList.getRowCount() > 0) {
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

	@SuppressWarnings("deprecation")
	private void btn_EnterActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_EnterActionPerformed

		boolean finishState = false;
		if (tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 2) != null
				&& tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 2)
						.equals("F")) {

			Object[] options = { paragraph.getLanguage(message, "YES"),
					paragraph.getLanguage(message, "NO") };
			int dialog = JOptionPane.showOptionDialog(new Frame(),
					paragraph.getLanguage(message, "DOYOUWANTTOCHANGETHEDATA"),
					paragraph.getLanguage(message, "MESSAGE"),
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);

			if (dialog == 0) {
				finishState = true;
				m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
				setEnter(finishState);
			}
		} else if ( (tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 2) == null)
				|| (tab_WorkList.getValueAt(tab_WorkList.getSelectedRow(), 2)).toString().compareTo("") == 0 ) {
			m_RefrashWorkList.interrupt(); // 終止重複讀取掛號表單
			setEnter(finishState);
		}
	}// GEN-LAST:event_btn_EnterActionPerformed

	private void btn_CloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_CloseActionPerformed
		mnit_CloseActionPerformed(null);
	}// GEN-LAST:event_btn_CloseActionPerformed

	private void btn_RePrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_RePrintActionPerformed
		String regGuid = (String) tab_WorkList.getValueAt(
				tab_WorkList.getSelectedRow(), 11);
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
					REFRASHTIME, m_SysName);
			this.m_RefrashWorkList.start();
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
			m_RefrashWorkList.getSelectDate(dateComboBox.getValue());
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
	private javax.swing.JTextField txt_Name;
	private javax.swing.JTextField txt_Poli;
	public JButton btn_Diagnostic;
}