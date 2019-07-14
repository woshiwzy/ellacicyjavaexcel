package com.wangzy.ui;

import java.awt.EventQueue;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

public class MainUI_v2 {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI_v2 window = new MainUI_v2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI_v2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("年度工作任务");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JFormattedTextField formattedTextFieldYear = new JFormattedTextField(NumberFormat.getIntegerInstance());
		formattedTextFieldYear.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextFieldYear.setBounds(33, 33, 100, 31);
		formattedTextFieldYear.setText("2019");
		frame.getContentPane().add(formattedTextFieldYear);
		
		JLabel label = new JLabel("年度是否维护");
		label.setBounds(145, 40, 89, 16);
		frame.getContentPane().add(label);
		
		JButton button = new JButton("生成");
		button.setBounds(255, 35, 117, 29);
		frame.getContentPane().add(button);
	}
}
