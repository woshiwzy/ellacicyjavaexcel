package com.wangzy.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class MainUI {

	private JFrame frmExcel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {

			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

			UIManager.put("RootPane.setupButtonVisible", false);

			BeautyEyeLNFHelper.translucencyAtFrameInactive = true;
			
		} catch (Exception e) {
			// TODO exception
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frmExcel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExcel = new JFrame();
//		frmExcel.setOpacity(0.0f);
		frmExcel.setType(Type.UTILITY);
		frmExcel.setResizable(false);
		frmExcel.setTitle("Excel 工具集(v1.1)");

		int width = 450, height = 416;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();

		frmExcel.setBounds(100, 100, width, height);
		frmExcel.setLocation(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);

		frmExcel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmExcel.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("自动抽取文件");

		btnNewButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							FrameExcelExportTool window = new FrameExcelExportTool();
							window.frmExcel.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton.setBounds(93, 53, 254, 125);
		frmExcel.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("按列重命名工具");
		btnNewButton_1.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							FrameRenameExcel window = new FrameRenameExcel();
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnNewButton_1.setBounds(93, 247, 254, 125);
		frmExcel.getContentPane().add(btnNewButton_1);
	}

}
