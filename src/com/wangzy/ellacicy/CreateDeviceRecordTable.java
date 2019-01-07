package com.wangzy.ellacicy;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

public class CreateDeviceRecordTable extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateDeviceRecordTable frame = new CreateDeviceRecordTable();
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
	public CreateDeviceRecordTable() {
		setTitle("生成机具登记表");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("机具登记表(旧表)");
		lblNewLabel.setBounds(37, 61, 126, 16);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("员工工作明细");
		label.setBounds(37, 103, 96, 16);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("商户信息表");
		label_1.setBounds(37, 155, 79, 16);
		contentPane.add(label_1);
		
		JButton button = new JButton("生成新的机具表");
		button.setBounds(197, 267, 186, 29);
		contentPane.add(button);
		
		textField = new JTextField();
		textField.setBounds(163, 56, 186, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(163, 98, 186, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(164, 150, 186, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
	}
}
