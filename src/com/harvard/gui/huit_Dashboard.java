package com.harvard.gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

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
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import java.awt.Font;
import java.awt.GraphicsConfiguration;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class huit_Dashboard {
	private static final double SCALE_FACTOR = 0.6;
	private JFrame frmHuitDashboard;
	private final JLayeredPane layers = new JLayeredPane();
	private final JPanel buttonPanel = new JPanel();
	private final JLabel helloLabel = new JLabel("HUIT Portal (Computer: " + Configuration.getComputerName() + ")");
	private final JPanel helloPanel = new JPanel();
	private final JFXPanel webPanel = new JFXPanel();
	private final JPanel computerInfoPanel = new JPanel();
	private final JLabel computerInfoLabel = new JLabel();
	private int helloFontSize, harvardIconScale, linkFontSize, infoFontSize;

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
		setUpFontSizes();

		setUpMainFrame();

		setUpHelloPanel(helloFontSize, harvardIconScale);

		setUpButtonPanel();

		setUpWebView();
		
		setUpComputerInfoPanel();

		setUpLayeredPanel();

		frmHuitDashboard.getContentPane().add(layers);

		setMainFrameBounds();

	}

	private void setUpWebView() {
		Platform.runLater(() -> {

			WebView webView = new WebView();
			webPanel.setScene(new Scene(webView));
			webView.setZoom(2.2 * SCALE_FACTOR);
			webView.getEngine().load(Configuration.getHomePage());
			webView.getEngine().getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
				System.err.println(webView.getEngine().getLoadWorker().exceptionProperty());
			});
		});
	}

	private void setMainFrameBounds() {
		GraphicsConfiguration config = frmHuitDashboard.getGraphicsConfiguration();
		Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(config.getDevice());
		Rectangle bounds = new Rectangle((int) (usableBounds.getMaxX() * SCALE_FACTOR),
				(int) (usableBounds.getMaxY() * SCALE_FACTOR));
		bounds.setLocation(0, 22);
		frmHuitDashboard.setMaximizedBounds(bounds);
		frmHuitDashboard.setBounds(bounds);
	}

	private void setUpLayeredPanel() {
		layers.setLayout(new BorderLayout(0, 0));
		layers.add(buttonPanel, BorderLayout.LINE_START);

		layers.add(webPanel);

		layers.add(computerInfoPanel, BorderLayout.SOUTH);

		layers.setLayer(webPanel, 2);
		layers.setLayer(computerInfoPanel, 1);
	}

	private void setUpComputerInfoPanel() throws UnknownHostException, SocketException {
		computerInfoLabel.setBackground(UIManager.getColor("Button.highlight"));

		computerInfoLabel.setText("<HTML><FONT size = \"" + infoFontSize + "\">" + "IP Address: "
				+ Configuration.getIP() + "<br>" + "MAC Address: " + Configuration.getMAC() + "</FONT></HTML>");
		computerInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		computerInfoPanel.add(computerInfoLabel);
		computerInfoPanel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
	}

	private void setUpButtonPanel() throws URISyntaxException {
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
	}

	private void setUpFontSizes() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		helloFontSize = (int) (width * 0.025 * SCALE_FACTOR);
		harvardIconScale = (int) (helloFontSize * 2 * SCALE_FACTOR);
		linkFontSize = (int) (helloFontSize * 0.35 * SCALE_FACTOR);
		infoFontSize = (int) (helloFontSize * 0.25 * SCALE_FACTOR);
	}

	private void setUpHelloPanel(int helloFontSize, int harvardIconScale) throws IOException {
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
	}

	private void setUpMainFrame() {
		frmHuitDashboard = new JFrame();
		frmHuitDashboard.getContentPane().setLayout(new BorderLayout());
		frmHuitDashboard.setResizable(false);
		frmHuitDashboard.setTitle("HUIT DashBoard");
		frmHuitDashboard.setBounds(100, 100, 655, 443);
		frmHuitDashboard.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmHuitDashboard.setUndecorated(true);
		frmHuitDashboard.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		frmHuitDashboard.setVisible(true);
	}

}
