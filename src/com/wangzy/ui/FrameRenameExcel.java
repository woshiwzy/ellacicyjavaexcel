package com.wangzy.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.wangzy.tool.Pair;
import com.wangzy.tool.Tool;

public class FrameRenameExcel {

	public JFrame frame;

	private JTextField textFieldRenameExcel;
	private JTextField textFieldSrcCol;
	private JTextField textFieldTargetCol;

	private JTextField textFieldSrcDir;
	private JTextField textFieldDstDir;

	private File fileExcel;
	private File fileSrcDir;
	private File fileDstDir;
	private JTextField textFieldIgnore;
	private JTextArea textAreaLogRename;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrameRenameExcel window = new FrameRenameExcel();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public FrameRenameExcel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("命名指定列文件名");
		frame.setResizable(false);

		int width = 729, height = 585;
		frame.setBounds(100, 100, 729, 585);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();

		frame.setLocation(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 717, 551);
		frame.getContentPane().add(tabbedPane);

		JPanel panelRename = new JPanel();
		tabbedPane.addTab("批量命名", null, panelRename, null);
		panelRename.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(6, 6, 684, 203);
		panelRename.add(panel);
		panel.setLayout(null);

		textFieldRenameExcel = new JTextField();
		textFieldRenameExcel.setBounds(6, 20, 439, 30);
		panel.add(textFieldRenameExcel);
		textFieldRenameExcel.setColumns(10);

		JButton findExcel2RenameCol = new JButton("选择Excel");
		findExcel2RenameCol.setBounds(502, 20, 120, 30);
		panel.add(findExcel2RenameCol);

		JLabel lblNewLabel = new JLabel("第");
		lblNewLabel.setBounds(103, 151, 43, 36);
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		textFieldSrcCol = new JTextField();
		textFieldSrcCol.setBounds(158, 156, 43, 26);
		panel.add(textFieldSrcCol);
		textFieldSrcCol.setColumns(10);

		JLabel label = new JLabel("列重命名为新列-->");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(227, 161, 174, 16);
		panel.add(label);

		textFieldTargetCol = new JTextField();
		textFieldTargetCol.setBounds(402, 156, 43, 26);
		panel.add(textFieldTargetCol);
		textFieldTargetCol.setColumns(10);

		JButton buttonStart = new JButton("开始");
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				if (null != fileExcel) {
					textAreaLogRename.setText("");

					FileInputStream fis;
					try {
						fis = new FileInputStream(fileExcel);
						Workbook workBook = WorkbookFactory.create(fis);
						Sheet sheet = workBook.getSheetAt(0);
						int numberOfSheets = sheet.getPhysicalNumberOfRows();
						textAreaLogRename.append("总共:" + numberOfSheets + "行\n");

						Set<Pair> filesList = new HashSet<Pair>();

						int ignore = Integer.parseInt(textFieldIgnore.getText());

						int srcCol = 0;
						int targetCol = 1;

						srcCol = Integer.parseInt(textFieldSrcCol.getText());
						targetCol = Integer.parseInt(textFieldTargetCol.getText());

						for (int r = 0; r < numberOfSheets; r++) { // 总行
							if (r <= ignore) {
								continue;
							}

							Row row = sheet.getRow(r);
							if (null != row && null != row.getCell(srcCol) && null != row.getCell(targetCol)) {
								String value = "";
								try {
									value = row.getCell(srcCol).getStringCellValue().replace(" ", "");
								} catch (Exception e) {
									value = String.valueOf((long) row.getCell(srcCol).getNumericCellValue());
									JOptionPane.showMessageDialog(null, "发现excel有非文本格式，请调整后重试！", "标题",
											JOptionPane.WARNING_MESSAGE);
									return;
								}

								String targetVaue = "";
								try {
									targetVaue = row.getCell(targetCol).getStringCellValue().replace(" ", "");
								} catch (Exception e) {
									targetVaue = String.valueOf((long) row.getCell(targetCol).getNumericCellValue());
									JOptionPane.showMessageDialog(null, "发现excel有非文本格式，请调整后重试！", "标题",
											JOptionPane.WARNING_MESSAGE);
									return;
								}
								filesList.add(new Pair(value, targetVaue));
							}
						}

						int totalNeedCopy = filesList.size();

						textAreaLogRename.append("共有：" + totalNeedCopy + "行，忽略第" + ignore + "行以及之前行");

//						System.out.println("目标excel：" + fileExcel.getAbsolutePath());
//						System.out.println("源文件夹：" + fileSrcDir.getAbsolutePath());
//						System.out.println("目标文件夹：" + fileDstDir.getAbsolutePath());
//						System.out.println("忽略行：" + ignore);
//						System.out.println("替换列：" + srcCol);
//						System.out.println("目标列：" + targetCol);
						// ======================开始查找指定文件夹文件=================================================================
						if (null == fileSrcDir) {
							return;
						}

						File[] files = fileSrcDir.listFiles();

						int copyCount = 0;

						for (int i = 0, isize = files.length; i < isize; i++) {
							File f = files[i];

//							System.out.println("====>" + f.getAbsolutePath());

							Pair par = fileNeedRename(f, filesList);
							if (null != par) {
								copyCount++;
								Tool.copyFileUsingFileStreams(f, new File(
										fileDstDir.getAbsolutePath() + File.separator + par.getDst() + ".mp3"));
								filesList.remove(par);
							} else {
//								textAreaLogRename.append("\n" + f.getName() + " 没有拷贝\n");
							}
						}

						for (Pair p : filesList) {
							textAreaLogRename.append("\n" + p.getSrc() + " 没有拷贝\n");
						}

						textAreaLogRename.append("\n成功拷贝:" + copyCount + "/" + totalNeedCopy + "\n");

					} catch (Exception e1) {
						e1.printStackTrace();
						textAreaLogRename.append("" + e1.getLocalizedMessage());
					}

				} else {
					textAreaLogRename.append("excel文件为空！\n");
				}

			}
		});
		buttonStart.setBounds(502, 156, 117, 29);
		panel.add(buttonStart);

		textFieldSrcDir = new JTextField();
		textFieldSrcDir.setBounds(6, 58, 439, 30);
		panel.add(textFieldSrcDir);
		textFieldSrcDir.setColumns(10);

		JButton btnNewButton = new JButton("源文件夹");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showDialog(new JLabel(), "选择文件存放到这个文件夹");
				fileSrcDir = chooser.getSelectedFile();
				if (null == fileSrcDir) {
					textFieldSrcDir.setText("");
				} else {
					textFieldSrcDir.setText(fileSrcDir.getAbsolutePath());
				}

			}
		});
		btnNewButton.setBounds(502, 61, 120, 30);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("存储到");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showDialog(new JLabel(), "选择文件存放到这个文件夹");
				fileDstDir = chooser.getSelectedFile();
				if (null == fileDstDir) {
					textFieldDstDir.setText("");
				} else {
					textFieldDstDir.setText(fileDstDir.getAbsolutePath());
				}
			}
		});
		btnNewButton_1.setBounds(502, 103, 120, 30);
		panel.add(btnNewButton_1);

		textFieldDstDir = new JTextField();
		textFieldDstDir.setBounds(6, 105, 439, 30);
		panel.add(textFieldDstDir);
		textFieldDstDir.setColumns(10);

		JLabel lblHuLe = new JLabel("忽略");
		lblHuLe.setBounds(13, 161, 26, 16);
		panel.add(lblHuLe);

		textFieldIgnore = new JTextField();
		textFieldIgnore.setText("-1");
		textFieldIgnore.setBounds(48, 156, 43, 26);
		panel.add(textFieldIgnore);
		textFieldIgnore.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 221, 684, 186);
		panelRename.add(scrollPane);

		textAreaLogRename = new JTextArea();
		scrollPane.setViewportView(textAreaLogRename);
		findExcel2RenameCol.addActionListener(new ActionListener() {

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
					textFieldRenameExcel.setText("");
				} else {
					if (!fileExcel.getAbsolutePath().toLowerCase().endsWith(".xls")
							&& !fileExcel.getAbsolutePath().toLowerCase().endsWith(".xlsx")) {
						fileExcel = new File(fileExcel.getAbsolutePath() + ".xlsx");
					}
					textFieldRenameExcel.setText(fileExcel.getAbsolutePath());
				}

			}
		});
	}

	public Pair fileNeedRename(File file, Set<Pair> files) {
		for (Pair pair : files) {
			String fname = file.getName().toLowerCase();
			String pairsrc = pair.getSrc() + ".mp3";
			if (fname.equals(pairsrc)) {
				return pair;
			}
		}
		return null;
	}
}
