package com.wangzy.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.wangzy.tool.Tool;

import java.awt.SystemColor;
import javax.swing.JTabbedPane;

public class FrameExcelExportTool {

	public JFrame frmExcel;
	private JTextField textFieldExcelPath;
	private JTextField textFieldSavePath;
	private File fileExcel;
	private File fileSaveDir;

	private File fileSrc;

	private JTextArea textAreaLog;
	private JTextField textFieldSrc;

	private JButton button_1;

	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrameExcelExportTool window = new FrameExcelExportTool();
//					window.frmExcel.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//	}

	/**
	 * Create the application.
	 */
	public FrameExcelExportTool() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExcel = new JFrame();
		frmExcel.setBackground(SystemColor.scrollbar);
		frmExcel.setIconImage(Toolkit.getDefaultToolkit().getImage("/Users/wangzy/Documents/icon_surf.png"));
		frmExcel.setResizable(false);
		frmExcel.setTitle("Excel文件拷贝工具");

		int width = 450, height = 400;

		frmExcel.setBounds(100, 100, 450, 550);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();

		frmExcel.setLocation(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);

		frmExcel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmExcel.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("选择Excel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new FileFilter() {
					public String getDescription() {
						return "*.xls;*.xlsx";
					}

					public boolean accept(File file) {
						String name = file.getName();
						return name.toLowerCase().endsWith(".xls") || name.toLowerCase().endsWith(".xlsx"); // 仅显示目录和xls、xlsx文件
					}

				});
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.showDialog(new JLabel(), "选择Excel 文件");
				fileExcel = chooser.getSelectedFile();
				if (null == fileExcel) {
					textFieldExcelPath.setText("");
				} else {
					textFieldExcelPath.setText(fileExcel.getAbsolutePath());
				}

			}
		});
		
		btnNewButton.setBounds(289, 47, 117, 29);
		frmExcel.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("选择存放文件夹");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showDialog(new JLabel(), "选择文件存放到这个文件夹");
				fileSaveDir = chooser.getSelectedFile();
				if (null == fileSaveDir) {
					textFieldSavePath.setText("");
				} else {
					textFieldSavePath.setText(fileSaveDir.getAbsolutePath());
				}

			}
		});
		btnNewButton_1.setBounds(289, 163, 117, 29);
		frmExcel.getContentPane().add(btnNewButton_1);

		textFieldExcelPath = new JTextField();
		textFieldExcelPath.setBounds(55, 46, 210, 29);
		frmExcel.getContentPane().add(textFieldExcelPath);
		textFieldExcelPath.setColumns(10);

		textFieldSavePath = new JTextField();
		textFieldSavePath.setBounds(55, 163, 210, 26);
		frmExcel.getContentPane().add(textFieldSavePath);
		textFieldSavePath.setColumns(10);

		JButton button = new JButton("开始");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					startCopyFile(fileExcel, fileSaveDir);
				} catch (Exception e1) {
					e1.printStackTrace();
					textAreaLog.setText(e1.getLocalizedMessage());
				}
			}
		});
		button.setBounds(167, 343, 117, 29);
		frmExcel.getContentPane().add(button);

		textFieldSrc = new JTextField();
		textFieldSrc.setBounds(55, 104, 210, 29);
		frmExcel.getContentPane().add(textFieldSrc);
		textFieldSrc.setColumns(10);

		button_1 = new JButton("源文件夹");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showDialog(new JLabel(), "选择这个文件夹");
				fileSrc = chooser.getSelectedFile();
				if (null == fileSrc) {
					textFieldSrc.setText("");
				} else {
					textFieldSrc.setText(fileSrc.getAbsolutePath());
				}
			}
		});
		button_1.setBounds(289, 104, 117, 29);
		frmExcel.getContentPane().add(button_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 230, 351, 101);
		frmExcel.getContentPane().add(scrollPane);

		textAreaLog = new JTextArea();
		scrollPane.setViewportView(textAreaLog);
		textAreaLog.setEditable(false);
		textAreaLog.setWrapStyleWord(true);
		textAreaLog.setLineWrap(true);
	}

	public void startCopyFile(File srcExcelFile, File dstPath) throws InvalidFormatException, IOException {

		textAreaLog.setText("");
		textAreaLog.setText("excel:" + srcExcelFile.getAbsolutePath() + "\n");

		if (null != srcExcelFile) {
			if (srcExcelFile.exists() == false) {
				srcExcelFile = new File(srcExcelFile.getAbsolutePath() + ".xlsx");
				textFieldExcelPath.setText(srcExcelFile.getAbsolutePath());
			}

			FileInputStream fis = new FileInputStream(srcExcelFile);
			Workbook workBook = WorkbookFactory.create(fis);

			Sheet sheet = workBook.getSheetAt(0);

			int numberOfSheets = sheet.getPhysicalNumberOfRows();

			textAreaLog.append("总共:" + numberOfSheets + "行\n");

			Set<String> filesList = new HashSet<String>();

			for (int r = 0; r < numberOfSheets; r++) { // 总行
				Row row = sheet.getRow(r);
				if (null != row && null != row.getCell(0)) {

					String value = "";
					try {
						value = row.getCell(0).getStringCellValue().replace(" ", "");
					} catch (Exception e) {
						value = String.valueOf((long) row.getCell(0).getNumericCellValue());
						JOptionPane.showMessageDialog(null, "发现excel有非文本格式，请调整后重试！", "标题", JOptionPane.WARNING_MESSAGE);
						return;
					}

					filesList.add(value + ".mp3");
				}
			}

			int totalNeedCopy = filesList.size();

			if (null == fileSrc) {

				return;
			}

			System.out.println(fileSrc.getAbsolutePath());
			File[] files = fileSrc.listFiles();
			int copyCount = 0;
			for (int i = 0, isize = files.length; i < isize; i++) {
				File f = files[i];
				System.out.println(f.getAbsolutePath());
				System.out.println(f.getName());
				if (filesList.contains(f.getName())) {
					copyCount++;
					Tool.copyFileUsingFileStreams(f, new File(dstPath.getAbsolutePath() + File.separator + f.getName()));
					filesList.remove(f.getName());
				} else {
//					textAreaLog.append("\n" + f.getName() + " 没有拷贝\n");
				}
			}

			textAreaLog.append("\n共拷贝：" + copyCount + "/" + totalNeedCopy + "个文件\n");
			for (String f : filesList) {
				textAreaLog.append("\n未找到：" + f + "\n");
			}

		} else {
			textAreaLog.append("\n源Excel 为空\n");
		}

	}

}
