package com.harvard.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.harvard.config.Configuration;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import sun.java2d.SunGraphicsEnvironment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import javax.swing.ImageIcon;

public class huit_Dashboard {
	private static final double SCALE_FACTOR = 0.5;
	private JFrame frmHuitDashboard;
	private final JPanel buttonPanel = new JPanel();
	private final JLabel helloLabel = new JLabel("HUIT Portal");
	private final JPanel helloPanel = new JPanel();
	private final JFXPanel webPanel = new JFXPanel();

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
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public huit_Dashboard() throws URISyntaxException, IOException, KeyManagementException, NoSuchAlgorithmException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private void initialize() throws URISyntaxException, IOException, NoSuchAlgorithmException, KeyManagementException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int helloFontSize = (int) (width * 0.025 * SCALE_FACTOR);
		int harvardIconScale = (int) (helloFontSize * 2 * SCALE_FACTOR);
		int linkFontSize = (int) (helloFontSize * 0.35 * SCALE_FACTOR);

		frmHuitDashboard = new JFrame();
		frmHuitDashboard.getContentPane().setLayout(new BorderLayout());
		frmHuitDashboard.setResizable(false);
		frmHuitDashboard.setTitle("HUIT DashBoard");
		frmHuitDashboard.setBounds(100, 100, 655, 443);
		frmHuitDashboard.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmHuitDashboard.setUndecorated(true);
		frmHuitDashboard.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		frmHuitDashboard.setVisible(true);

		helloPanel.setForeground(Color.WHITE);
		helloPanel.setBackground(new Color(79, 0, 9));
		frmHuitDashboard.getContentPane().add(helloPanel, BorderLayout.NORTH);
		Image icon = ImageIO.read(getClass().getResource("/res/harvard_shield_logo.png"));
		helloLabel.setIcon(
				new ImageIcon(icon.getScaledInstance(harvardIconScale, harvardIconScale, Image.SCALE_DEFAULT)));
		helloLabel.setBackground(Color.WHITE);
		helloLabel.setFont(new Font("Century Schoolbook", Font.BOLD, helloFontSize));
		helloLabel.setForeground(Color.WHITE);
		helloPanel.add(helloLabel);

		buttonPanel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		List<String[]> url = Configuration.readURL();

		for (int i = 0; i < url.size(); i++) {
			URI uri = new URI(url.get(i)[1].trim());

			JButton button = new JButton();
			button.setText("<HTML><FONT size = \"" + linkFontSize + "\" color=\"#0000EE\"><U>" + url.get(i)[0].trim()
					+ "</U></FONT></HTML>");
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
			buttonPanel.add(button);
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		}

		Platform.runLater(() -> {

			WebView webView = new WebView();
			webPanel.setScene(new Scene(webView));
			webView.setZoom(2.2*SCALE_FACTOR);
			webView.getEngine().load("https://huit.harvard.edu/");
			webView.getEngine().getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
				System.err.println(webView.getEngine().getLoadWorker().exceptionProperty());
			});
		});

		frmHuitDashboard.getContentPane().add(buttonPanel, BorderLayout.LINE_START);

		frmHuitDashboard.getContentPane().add(webPanel, BorderLayout.CENTER);

		GraphicsConfiguration config = frmHuitDashboard.getGraphicsConfiguration();
		Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(config.getDevice());
		Rectangle bounds = new Rectangle((int) (usableBounds.getMaxX() * SCALE_FACTOR), (int) (usableBounds.getMaxY() * SCALE_FACTOR));
		frmHuitDashboard.setMaximizedBounds(bounds);
		frmHuitDashboard.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

}
