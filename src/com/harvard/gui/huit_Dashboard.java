package com.harvard.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.harvard.config.Configuration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;

public class huit_Dashboard {

	private JFrame frmHuitDashboard;
	/**
	 * @wbp.nonvisual location=60,1009
	 */
	private final JPanel ButtonPanel = new JPanel();
	/**
	 * @wbp.nonvisual location=541,-1
	 */
	private final JLabel Hello = new JLabel("HUIT Portal");
	private final JPanel HelloPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					huit_Dashboard window = new huit_Dashboard();
					window.frmHuitDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws URISyntaxException
	 */
	public huit_Dashboard() throws URISyntaxException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws URISyntaxException
	 */
	private void initialize() throws URISyntaxException {
		frmHuitDashboard = new JFrame();
		frmHuitDashboard.getContentPane().setLayout(new BorderLayout());
		frmHuitDashboard.setResizable(false);
		frmHuitDashboard.setTitle("HUIT DashBoard");
		frmHuitDashboard.setBounds(100, 100, 655, 443);
		frmHuitDashboard.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmHuitDashboard.setUndecorated(true);
		frmHuitDashboard.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		frmHuitDashboard.setVisible(true);

		ButtonPanel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		List<String[]> url = Configuration.readURL();

		for (int i = 0; i < url.size(); i++) {
			URI uri = new URI(url.get(i)[1].trim());

			JButton button = new JButton();
			button.setText("<HTML><FONT color=\"#0000EE\"><U>" + url.get(i)[0] + "</U></FONT></HTML>");
			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setBorderPainted(false);
			button.setOpaque(false);
			button.setBackground(Color.WHITE);
			button.setToolTipText(uri.toString());
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						java.awt.Desktop.getDesktop().browse(uri);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}

			});
			ButtonPanel.add(button);

		}
		HelloPanel.setForeground(Color.WHITE);
		HelloPanel.setBackground(new Color(79, 0, 9));
		frmHuitDashboard.getContentPane().add(HelloPanel, BorderLayout.NORTH);
		Hello.setIcon(new ImageIcon(new ImageIcon("res/harvard_shield_logo.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
		Hello.setBackground(Color.WHITE);
		Hello.setFont(new Font("Century Schoolbook", Font.BOLD, 15));
		Hello.setForeground(Color.WHITE);
		HelloPanel.add(Hello);
		frmHuitDashboard.getContentPane().add(ButtonPanel);
		ButtonPanel.setLayout(new BoxLayout(ButtonPanel, BoxLayout.Y_AXIS));

	}

}
