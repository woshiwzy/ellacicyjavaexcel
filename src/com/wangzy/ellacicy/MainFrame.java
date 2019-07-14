package com.wangzy.ellacicy;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setTitle("入口");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 317, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("标记存量机具表是否已维护");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TagDevices tagDevices=new TagDevices();
				tagDevices.setVisible(true);
			}
		});
		btnNewButton.setBounds(58, 104, 209, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("机具维护记录查询");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryUI queryUI=new QueryUI();
				queryUI.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(86, 185, 148, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("生成机具登机表");
		btnNewButton_2.setBounds(102, 261, 117, 29);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("生成银行报表");
		btnNewButton_3.setBounds(102, 347, 117, 29);
		contentPane.add(btnNewButton_3);
	}

}
