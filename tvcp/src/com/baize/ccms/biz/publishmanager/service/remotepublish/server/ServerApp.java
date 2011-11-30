/**
 * project：通用内容管理系统
 * Company:  
 */
package com.j2ee.cms.biz.publishmanager.service.remotepublish.server;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

/**
 * 
 * @author Administrator
 *@version 1.0 ServerApp类提供服务器运行实例
 */
public class ServerApp extends JFrame implements ActionListener {
	private JLabel tip;
	private JLabel portTip;
	private JTextField filePathInput;
	private JTextField portInput;
	private JButton chooseButton;
	private JButton startServerButton;
	private PublishServer backupServer;
	private Toolkit toolkit;
	private int frameWidth = 600;
	private int frameHeight = 100;
	private int posX;
	private int posY;
	private int winWidth;
	private int winHeight;

	public ServerApp() {
		toolkit = Toolkit.getDefaultToolkit();
		winWidth = toolkit.getScreenSize().width;
		winHeight = toolkit.getScreenSize().height;
		// 设置窗口位置
		posX = (winWidth - frameWidth) / 2;
		posY = (winHeight - frameHeight) / 2;

		tip = new JLabel("服务端文件目录：");
		portTip = new JLabel("接受端口：");
		filePathInput = new JTextField(20);
		portInput = new JTextField(8);
		chooseButton = new JButton("选择");
		chooseButton.addActionListener(this);
		filePathInput = new JTextField(20);
		startServerButton = new JButton("启动");
		startServerButton.addActionListener(this);

		setTitle("Socket发布服务器端");
		setLayout(new FlowLayout());

		add(tip);
		add(filePathInput);
		add(chooseButton);
		add(portTip);
		add(portInput);
		add(startServerButton);

		setSize(frameWidth, frameHeight);
		setLocation(posX, posY);
		setResizable(false);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		new ServerApp();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == startServerButton) {
			String dir = filePathInput.getText();
			String portStr = portInput.getText();
			if (dir != null && !"".equals(dir.trim())
					&& portStr != null && !"".equals(portStr.trim())) {
				backupServer = new PublishServer(Integer.parseInt(portStr), filePathInput.getText());
				backupServer.listeningPort();
			}
		} else if (event.getSource() == chooseButton) {
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("选择服务端目录");
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    //chooser.setAcceptAllFileFilterUsed(false);
		    int result = chooser.showOpenDialog(new JPanel());
		    if (result == JFileChooser.APPROVE_OPTION) {
		    		try {
						filePathInput.setText(chooser.getSelectedFile().getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
		    } else {
		          System.out.println("No Selection ");
		    }
		}
	}
}
