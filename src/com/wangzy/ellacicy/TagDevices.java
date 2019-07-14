package com.wangzy.ellacicy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wangzy.ellacicy.domain.Device;
import com.wangzy.ellacicy.domain.DeviceRecord;
import com.wangzy.ellacicy.domain.WorkHistory;
import com.wangzy.ellacicy.tool.ExcelTool;

public class TagDevices extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldTotal;
	private JTextField textFieldJobDetailDir;

	private File fileExcelTotal;
	private JTextArea textAreaLog;
	private File fileJobDetailDir;
	private File fileDeviceTab;

	public static ArrayList<WorkHistory> allwork;// 工作明细
	public static ArrayList<Device> alldevices;// 总机具
	public static ArrayList<DeviceRecord>devicelistrecodes;// 机具登机表

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TagDevices frame = new TagDevices();
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
	public TagDevices() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();

		int width = 729, height = 585;
		setBounds(100, 100, 720, 592);
		this.setLocation(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);
		this.setTitle("标记存量机具是否已维护");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldTotal = new JTextField();
		textFieldTotal.setBounds(143, 39, 307, 33);
		contentPane.add(textFieldTotal);
		textFieldTotal.setColumns(10);

		JButton btnNewButton = new JButton("选择excel");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 选择总表excel

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
				chooser.showDialog(new JLabel(), "选择Excel 文件");
				fileExcelTotal = chooser.getSelectedFile();
				if (null == fileExcelTotal) {
					textFieldTotal.setText("");
				} else {
					if (!fileExcelTotal.getAbsolutePath().toLowerCase().endsWith(".xls")
							&& !fileExcelTotal.getAbsolutePath().toLowerCase().endsWith(".xlsx")) {
						fileExcelTotal = new File(fileExcelTotal.getAbsolutePath() + ".xlsx");
					}
					textFieldTotal.setText(fileExcelTotal.getAbsolutePath());
					alldevices = readTotalExcelFile(fileExcelTotal.getAbsolutePath());
				}
			}
		});
		btnNewButton.setBounds(462, 42, 88, 29);
		contentPane.add(btnNewButton);

		JLabel label = new JLabel("银行机具总表");
		label.setBounds(33, 47, 98, 16);
		contentPane.add(label);

		JLabel label_1 = new JLabel("员工工作明细");
		label_1.setBounds(33, 129, 98, 16);
		contentPane.add(label_1);

		textFieldJobDetailDir = new JTextField();
		textFieldJobDetailDir.setBounds(143, 124, 307, 33);
		contentPane.add(textFieldJobDetailDir);
		textFieldJobDetailDir.setColumns(10);

		JButton btnNewButton_1 = new JButton("选择文件夹");
		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 选择工作明细文件夹
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showDialog(new JLabel(), "选择这个文件夹");
				fileJobDetailDir = chooser.getSelectedFile();
				if (null == fileJobDetailDir) {
					textFieldJobDetailDir.setText("");
				} else {
					textFieldJobDetailDir.setText(fileJobDetailDir.getAbsolutePath());
				}
				allwork = ExcelTool.readAllWorkHistory(fileJobDetailDir.getAbsolutePath());
				log2Area("总的维护记录:" + (allwork.size()));
			}
		});
		btnNewButton_1.setBounds(462, 122, 88, 33);
		contentPane.add(btnNewButton_1);

		textAreaLog = new JTextArea();
		textAreaLog.setBackground(Color.LIGHT_GRAY);
		textAreaLog.setBounds(6, 26, 673, 235);
//		textAreaLog.setLineWrap(true);

		JScrollPane scrollpan = new JScrollPane(textAreaLog);
		scrollpan.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollpan.setBorder(new TitledBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "\u64CD\u4F5C\u65E5\u5FD7",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 255, 255)),
				"\u64CD\u4F5C\u65E5\u5FD7", TitledBorder.LEADING, TitledBorder.TOP, null, null));

//		panel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		panel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		scrollpan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//		textAreaLog.setBounds(6, 26, scrollpan.getWidth(), scrollpan.getHeight());
		scrollpan.setBounds(20, 265, 685, 278);

		contentPane.add(scrollpan);

		JButton buttonDownlaodWorked = new JButton("标记已经巡检");
		buttonDownlaodWorked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 下载已经巡检

				for (int i = 0, isize = alldevices.size(); i < isize; i++) {

					Device device = alldevices.get(i);
					device.getWorkhistry().clear();

					for (int j = 0, jsize = allwork.size(); j < jsize; j++) {
						WorkHistory wh = allwork.get(j);
						if (device.getTerminalNo() == wh.getTerminalNo()) {
							device.addHistory(wh);
						}
					}

//					if (device.getWorkhistry().size() > 0) {
//						String log = "进度：" + i + "/" + isize + " 终端:" + device.getTerminalNo() + " 维护记录:"
//								+ device.getWorkhistry().size();
//						System.out.println(log);
//						log2Area(log);
//					}
				}
				log2Area("完成标记:" + alldevices.size() + "条记录，可点击下载了.");

				// 生成新的excel文件
				XSSFWorkbook workbook = new XSSFWorkbook(); // 读取的文件
				XSSFSheet sheet = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
				sheet = workbook.createSheet("标记后的数据_" + sdf.format(new Date()));

				int x = 0;
				for (Device data : alldevices) {
					XSSFRow row = sheet.getRow(x); // 获取指定的行对象，无数据则为空，需要创建
					if (row == null) {
						row = sheet.createRow(x); // 该行无数据，创建行对象
					}

					if (x == 0) {
						// 创建表头
						Cell cell = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
						cell.setCellValue("终端号"); // 设置内容

						Cell cell1 = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
						cell1.setCellValue("商户号"); // 设置内容

						Cell cell2 = row.createCell(2); // 创建指定单元格对象。如本身有数据会替换掉
						cell2.setCellValue("分润行"); // 设置内容

						Cell cell3 = row.createCell(3); // 创建指定单元格对象。如本身有数据会替换掉
						cell3.setCellValue("商户名称"); // 设置内容

						Cell cell4 = row.createCell(4); // 创建指定单元格对象。如本身有数据会替换掉
						cell4.setCellValue("是否巡检"); // 设置内容

					} else {
						Cell cell = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
						cell.setCellValue(data.getTerminalNo()); // 设置内容

						Cell cell1 = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
						cell1.setCellValue(data.getBusnessNo()); // 设置内容

						Cell cell2 = row.createCell(2); // 创建指定单元格对象。如本身有数据会替换掉
						cell2.setCellValue(data.getPartBank()); // 设置内容

						Cell cell3 = row.createCell(3); // 创建指定单元格对象。如本身有数据会替换掉
						cell3.setCellValue(data.getBusnessName()); // 设置内容

						Cell cell4 = row.createCell(4); // 创建指定单元格对象。如本身有数据会替换掉
						cell4.setCellValue(data.getWorkhistry().size() == 0 ? false : true); // 设置内容
					}
					x++;
				}

				String parentPath = fileExcelTotal.getParent() + File.separator + "标记后的总表" +sdf.format(new Date())
						+ ".xlsx";

				FileOutputStream fo;
				try {
					File file = new File(parentPath);
					if (file.exists()) {
						file.delete();
					}
					file.createNewFile();
					fo = new FileOutputStream(parentPath);
					workbook.write(fo);
					log2Area("完成路径:" + parentPath);
				} catch (Exception e1) {
					e1.printStackTrace();
					log2Area("输出失败:" + parentPath);
				} // 输出到文件

			}
		});
		buttonDownlaodWorked.setBounds(143, 196, 251, 29);
		contentPane.add(buttonDownlaodWorked);
	}

	/**
	 * 读取总表信息
	 * 
	 * @param path
	 */
	private ArrayList<Device> readTotalExcelFile(String path) {

		ArrayList<Device> devices = ExcelTool.readAllDevices(path);
		log2Area("总数据:" + devices.size());
		return devices;

	}

	private void log2Area(String log) {

		textAreaLog.append("\n" + log + "\n");

	}
}
