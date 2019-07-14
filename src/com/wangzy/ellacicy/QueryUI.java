package com.wangzy.ellacicy;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import com.eltima.components.ui.DatePicker;
import com.wangzy.ellacicy.domain.DeviceRecord;
import com.wangzy.ellacicy.domain.WorkHistory;
import com.wangzy.ellacicy.tool.ExcelTool;

public class QueryUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldBusnessNo;
	private JTextField textFieldTerminalNo;
	private JTable table;
	private JTable tableWorkHistory;
	private JTextField textFieldDeviceRecord;
	private JTextField textFieldWorkDir;
	protected File fileJobDetailDir;
	protected ArrayList<WorkHistory> allwork;// 员工工作明细
	private JTextArea textAreaLog;
	protected File fileExcelTotal;
	protected ArrayList<DeviceRecord> alldevices;
	private DatePicker datePickerStart;
	private DatePicker datePickerEnd;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private JLabel labelBusnessInfo;
	private JLabel labelRecord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryUI frame = new QueryUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QueryUI() {
		setTitle("机具维护记录查询");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1171, 728);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldBusnessNo = new JTextField();
		textFieldBusnessNo.setBounds(138, 110, 452, 26);
		contentPane.add(textFieldBusnessNo);
		textFieldBusnessNo.setColumns(10);

		textFieldTerminalNo = new JTextField();
		textFieldTerminalNo.setBounds(138, 148, 452, 26);
		contentPane.add(textFieldTerminalNo);
		textFieldTerminalNo.setColumns(10);

		JButton btnNewButton = new JButton("按商户号查询");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 按商户号查询

				System.out.println("date :" + getStartDate() + "到" + getEndDate());

				if (null != alldevices) {
					String inputBusnessNo = textFieldBusnessNo.getText().trim();

					ArrayList<DeviceRecord> devices = new ArrayList<>();
					ArrayList<WorkHistory> queriedWorks = new ArrayList<>();

					for (WorkHistory work : allwork) {

						if ((null != getStartDate() && null != getEndDate())
								&& (work.getDate().getTime() / 1000 >= getStartDate().getTime() / 1000
										&& work.getDate().getTime() / 1000 <= getEndDate().getTime() / 1000)) {
							queriedWorks.add(work);
						} else if ((null != getStartDate() && null == getEndDate())
								&& (work.getDate().getTime() / 1000 >= getStartDate().getTime() / 1000)) {
							queriedWorks.add(work);
						} else if ((null == getStartDate() && null != getEndDate())
								&& (work.getDate().getTime() / 1000 <= getEndDate().getTime() / 1000)) {
							queriedWorks.add(work);
						} else if (null == getStartDate() && null == getEndDate()) {
							queriedWorks.add(work);
						}
					}

					for (WorkHistory work : queriedWorks) {

						if (null != work.getBusnessNo()) {
							String busnessNo = work.getBusnessNo().trim();
							if ((inputBusnessNo.equalsIgnoreCase(busnessNo) && null != work.getDeviceRecord())
									|| "".equals(inputBusnessNo) || null == inputBusnessNo) {
								devices.add(work.getDeviceRecord());
							}
						}
					}
					fillData(devices);
				}
			}
		});
		btnNewButton.setBounds(617, 110, 117, 29);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("按终端号查询");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String inputTerminalNo = textFieldTerminalNo.getText().trim();
				
				ArrayList<WorkHistory> queriedWorks=new ArrayList<>();

				for (WorkHistory work : allwork) {

					if ((null != getStartDate() && null != getEndDate())
							&& (work.getDate().getTime() / 1000 >= getStartDate().getTime() / 1000
									&& work.getDate().getTime() / 1000 <= getEndDate().getTime() / 1000)) {
						queriedWorks.add(work);
					} else if ((null != getStartDate() && null == getEndDate())
							&& (work.getDate().getTime() / 1000 >= getStartDate().getTime() / 1000)) {
						queriedWorks.add(work);
					} else if ((null == getStartDate() && null != getEndDate())
							&& (work.getDate().getTime() / 1000 <= getEndDate().getTime() / 1000)) {
						queriedWorks.add(work);
					} else if (null == getStartDate() && null == getEndDate()) {
						queriedWorks.add(work);
					}
				}
				
				ArrayList<DeviceRecord> devices = new ArrayList<>();
				
				for (WorkHistory wh : queriedWorks) {
					
					if (String.valueOf(wh.getTerminalNo()).equals(inputTerminalNo) || "".equals(inputTerminalNo)) {
						devices.add(wh.getDeviceRecord());
					}
				}
				
				fillData(devices);
			}
		});
		btnNewButton_1.setBounds(617, 148, 117, 29);
		contentPane.add(btnNewButton_1);

		JLabel lblhao = new JLabel("输入商户号");
		lblhao.setBounds(23, 114, 82, 16);
		contentPane.add(lblhao);

		JLabel label_1 = new JLabel("输入终端号");
		label_1.setBounds(23, 153, 71, 16);
		contentPane.add(label_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(23, 280, 1119, 81);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.scrollbar, SystemColor.windowBorder,
				SystemColor.scrollbar, SystemColor.window));
		scrollPane.setViewportView(table);
		table.setBackground(SystemColor.inactiveCaptionText);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {

					System.out.println("选中行-----");
					MyTableModel mmmodel = (MyTableModel) table.getModel();///
					DeviceRecord device = mmmodel.devicesRecord.get(row);
					System.out.println("巡检记录:" + device.getWorkhistories().size());

					fillWorkData(device.getWorkhistories());
				}
			}
		});

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(23, 401, 1119, 167);
		contentPane.add(scrollPane_1);

		tableWorkHistory = new JTable();
		scrollPane_1.setViewportView(tableWorkHistory);

		labelRecord = new JLabel("维护记录");
		labelRecord.setBounds(23, 373, 164, 16);
		contentPane.add(labelRecord);

		labelBusnessInfo = new JLabel("商户信息");
		labelBusnessInfo.setBounds(23, 252, 145, 16);
		contentPane.add(labelBusnessInfo);

		JLabel label = new JLabel("选择时间段");
		label.setBounds(23, 181, 82, 42);
		contentPane.add(label);

		JLabel label_4 = new JLabel("到");
		label_4.setBounds(360, 194, 21, 16);
		contentPane.add(label_4);

		JLabel label_5 = new JLabel("导入机具登记表");
		label_5.setBounds(21, 76, 117, 16);
		contentPane.add(label_5);

		textFieldDeviceRecord = new JTextField();
		textFieldDeviceRecord.setBounds(138, 72, 452, 26);
		contentPane.add(textFieldDeviceRecord);
		textFieldDeviceRecord.setColumns(10);

		JButton btnNewButton_2 = new JButton("选择excel");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 查询机具登机表

				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileFilter() {
					public String getDescription() {
						return "*.xls;*.xlsx";
					}

					public boolean accept(File file) {
						String name = file.getName();
						return file.isDirectory() || name.toLowerCase().endsWith(".xls")
								|| name.toLowerCase().endsWith(".xlsx"); // 仅显示目录和xls、xlsx文件
					}

				});
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.showDialog(new JLabel(), "选择Excel文件");
				fileExcelTotal = chooser.getSelectedFile();
				if (null == fileExcelTotal) {
					textFieldDeviceRecord.setText("");
				} else {
					if (!fileExcelTotal.getAbsolutePath().toLowerCase().endsWith(".xls")
							&& !fileExcelTotal.getAbsolutePath().toLowerCase().endsWith(".xlsx")) {
						fileExcelTotal = new File(fileExcelTotal.getAbsolutePath() + ".xlsx");
					}
					textFieldDeviceRecord.setText(fileExcelTotal.getAbsolutePath());

					alldevices = ExcelTool.readAllDeviceRecode(fileExcelTotal.getAbsolutePath());
					log2Area("读取机具登机表:" + alldevices.size());

					for (DeviceRecord deviceRecord : alldevices) {
						for (WorkHistory wh : allwork) {
							if (deviceRecord.getDeviceNo() == wh.getTerminalNo()) {
								deviceRecord.addWorkHistory(wh);
								wh.setBusnessNo(deviceRecord.getBusnessNo());
								wh.setDeviceRecord(deviceRecord);
							}
						}
					}
					log2Area("完成数据关联:" + alldevices.size());
				}

			}
		});
		btnNewButton_2.setBounds(617, 69, 117, 29);
		contentPane.add(btnNewButton_2);

		textFieldWorkDir = new JTextField();
		textFieldWorkDir.setBounds(138, 36, 452, 26);
		contentPane.add(textFieldWorkDir);
		textFieldWorkDir.setColumns(10);

		JLabel lblNewLabel = new JLabel("员工工作明细");
		lblNewLabel.setBounds(23, 41, 92, 16);
		contentPane.add(lblNewLabel);

		JButton button = new JButton("选择文件夹");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 选择工作明细文件夹
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showDialog(new JLabel(), "选择这个文件夹");
				fileJobDetailDir = chooser.getSelectedFile();
				if (null == fileJobDetailDir) {
					textFieldWorkDir.setText("");
				} else {
					textFieldWorkDir.setText(fileJobDetailDir.getAbsolutePath());
				}
				allwork = ExcelTool.readAllWorkHistory(fileJobDetailDir.getAbsolutePath());
				log2Area("读取到工作明细:" + allwork.size());
			}
		});
		button.setBounds(617, 36, 117, 29);
		contentPane.add(button);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(23, 607, 1119, 50);
		contentPane.add(scrollPane_2);

		textAreaLog = new JTextArea();
		scrollPane_2.setViewportView(textAreaLog);

		JLabel label_6 = new JLabel("操作日志");
		label_6.setBounds(23, 579, 61, 16);
		contentPane.add(label_6);

		datePickerStart = new DatePicker();
		datePickerStart.setBounds(143, 193, 180, 21);
		contentPane.add(datePickerStart);

		datePickerEnd = new DatePicker();
		datePickerEnd.setBounds(405, 193, 180, 21);
		contentPane.add(datePickerEnd);

		fillData(new ArrayList<>());

	}

	private void fillData(ArrayList<DeviceRecord> devicesRecords) {

		System.out.println("收到数据:" + devicesRecords.size());
		log2Area("查到商户信息:" + devicesRecords.size());
		labelBusnessInfo.setText("商户信息:"+devicesRecords.size());

		MyTableModel dataModel = new MyTableModel();
		dataModel.devicesRecord = devicesRecords;

		Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
		if (null != devicesRecords && !devicesRecords.isEmpty()) {

			for (int i = 0, isize = devicesRecords.size(); i < isize; i++) {

				DeviceRecord device = devicesRecords.get(i);

				Vector<String> tabItem = new Vector<>();
				tabItem.add(String.valueOf(device.getManager()));
				tabItem.add(String.valueOf(device.getDesc()));
				tabItem.add(String.valueOf(device.getBusnessName()));
				tabItem.add(String.valueOf(device.getAddress()));
				tabItem.add(String.valueOf(device.getContact()));
				tabItem.add(String.valueOf(device.getContactPhone()));
				tabItem.add(String.valueOf(device.getBusnessNo()));
				tabItem.add(String.valueOf(device.getDeviceNo()));
//				tabItem.add(String.valueOf(device.getBrand()));
//				tabItem.add(String.valueOf(device.getModel()));
//				tabItem.add(String.valueOf(device.getHost()));
//				tabItem.add(String.valueOf(device.getKeyborad()));
//				tabItem.add(String.valueOf(device.getSwitchDate()));
//				tabItem.add(String.valueOf(device.getConnectType()));
//				tabItem.add(String.valueOf(device.getPartBank()));
				tabItem.add(String.valueOf(device.getWorkhistories().size()));
				dataVector.add(tabItem);
			}
		}

		Vector<String> columnIdentifiers = new Vector<String>();
		columnIdentifiers.add("商户经理");
		columnIdentifiers.add("说明");
		columnIdentifiers.add("商户名称");
		columnIdentifiers.add("地址");
		columnIdentifiers.add("联系人");
		columnIdentifiers.add("联系电话");
		columnIdentifiers.add("商户号");
		columnIdentifiers.add("终端号");
//		columnIdentifiers.add("品牌");
//		columnIdentifiers.add("型号");
//		columnIdentifiers.add("主机");
//		columnIdentifiers.add("密码键盘");
//		columnIdentifiers.add("交接日期");
//		columnIdentifiers.add("连接方式");
//		columnIdentifiers.add("分润行");
		columnIdentifiers.add("维护次数");
		dataModel.setDataVector(dataVector, columnIdentifiers);
		table.setModel(dataModel);
		if (devicesRecords.size() == 1) {
			fillWorkData(devicesRecords.get(0).getWorkhistories());
		} else {
			fillWorkData(new ArrayList<>());
		}
	}

	private void fillWorkData(ArrayList<WorkHistory> workhistry) {
		
		log2Area("查到维护记录:"+workhistry.size());
		labelRecord.setText("维护记录:"+workhistry.size());

		MyTableModelWork dataModel = new MyTableModelWork();
		dataModel.works = workhistry;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
		if (null != workhistry && !workhistry.isEmpty()) {

			for (int i = 0, isize = workhistry.size(); i < isize; i++) {

				WorkHistory work = workhistry.get(i);

				Vector<String> tabItem = new Vector<>();
//				tabItem.add(String.valueOf(i));
				tabItem.add(String.valueOf(work.getWorkName()));
				tabItem.add(String.valueOf(work.getTerminalNo()));
				tabItem.add(sdf.format(work.getDate()));
				tabItem.add(work.getWorkType());

				dataVector.add(tabItem);

//				if ((null != getStartDate() && null != getEndDate())) {
//
//					if (work.getDate().getTime() >= getStartDate().getTime()
//							&& work.getDate().getTime() <= getEndDate().getTime()) {
//						dataVector.add(tabItem);
//					}
//				} else if (null != getStartDate() && null == getEndDate()) {
//
//					if (work.getDate().getTime() >= getStartDate().getTime()) {
//						dataVector.add(tabItem);
//					}
//				} else if (null == getStartDate() && null != getEndDate()) {
//					if (work.getDate().getTime() <= getEndDate().getTime()) {
//						dataVector.add(tabItem);
//					}
//				} else {
//					dataVector.add(tabItem);
//				}

			}
		}

		Vector<String> columnIdentifiers = new Vector<String>();
		columnIdentifiers.add("姓名");
		columnIdentifiers.add("终端号");
		columnIdentifiers.add("日期");
		columnIdentifiers.add("任务类型");
		dataModel.setDataVector(dataVector, columnIdentifiers);
		tableWorkHistory.setModel(dataModel);
	}

	private Date getStartDate() {
		try {
			Date da = (Date) datePickerStart.getValue();
			return da;
		} catch (Exception e) {
		}
		return null;
	}

	private Date getEndDate() {
		try {
			Date da = (Date) datePickerEnd.getValue();

			Calendar ca = Calendar.getInstance();
			ca.setTimeInMillis(da.getTime() + 24 * 60 * 60 * 1000 - 1000);
			return ca.getTime();
		} catch (Exception e) {
		}
		return null;
	}

	class MyTableModel extends DefaultTableModel {
		public ArrayList<DeviceRecord> devicesRecord;
	}

	class MyTableModelWork extends DefaultTableModel {
		public ArrayList<WorkHistory> works;
	}

	private static DatePicker getDatePicker() {
		final DatePicker datepick;
		// 格式
		String DefaultFormat = "yyyy-MM-dd";
		// 当前时间
		Date date = new Date();
		// 字体
		Font font = new Font("Times New Roman", Font.BOLD, 14);

		Dimension dimension = new Dimension(177, 24);

		int[] hilightDays = { 1, 3, 5, 7 };

		int[] disabledDays = { 4, 6, 5, 9 };
		// 构造方法（初始时间，时间显示格式，字体，控件大小）
		datepick = new DatePicker(date, DefaultFormat, font, dimension);

		datepick.setLocation(137, 83);// 设置起始位置
		/*
		 * //也可用setBounds()直接设置大小与位置 datepick.setBounds(137, 83, 177, 24);
		 */
		// 设置一个月份中需要高亮显示的日子
		datepick.setHightlightdays(hilightDays, Color.red);
		// 设置一个月份中不需要的日子，呈灰色显示
		datepick.setDisableddays(disabledDays);
		// 设置国家
		datepick.setLocale(Locale.CHINA);
		// 设置时钟面板可见
		datepick.setTimePanleVisible(true);
		return datepick;
	}

	private void log2Area(String log) {

		textAreaLog.append("\n" + log + "\n");

	}
}
