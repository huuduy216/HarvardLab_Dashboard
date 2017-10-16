package com.harvard.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Configuration {
	private static final String WINDOWS_URL_CONFIG_PATH = System.getProperty("user.home") + "\\Desktop\\url.txt";
	private static final String MAC_URL_CONFIG_PATH = "/Users/huitlabadmin/Desktop/url.txt";
	private static final String WINDOWS_HOMEPAGE_CONFIG_PATH = System.getProperty("user.home") + "\\Desktop\\homepage.txt";
	private static final String OS_NAME = System.getProperty("os.name");

	/*
	 * Return a list with URL and their names
	 */
	public static List<String[]> readURL() {
		String path = "";
		List<String[]> result = new ArrayList<String[]>();

		if (OS_NAME.contains("Mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			path = MAC_URL_CONFIG_PATH;
		} else if (OS_NAME.contains("Window")) {
			path = WINDOWS_URL_CONFIG_PATH;
		}
		
		try (BufferedReader urlReader = new BufferedReader(new FileReader(path))) {

			String currentLine;

			while ((currentLine = urlReader.readLine()) != null) {
				System.out.println(currentLine);
				String[] url = currentLine.split("#");
				result.add(url);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getHomePage() {
		String path = "";
		String result = "";

		if (OS_NAME.contains("Mac")) {
			path = MAC_URL_CONFIG_PATH;
		} else if (OS_NAME.contains("Window")) {
			path = WINDOWS_HOMEPAGE_CONFIG_PATH;
		}
		
		try (BufferedReader urlReader = new BufferedReader(new FileReader(path))) {

			String currentLine;

			while ((currentLine = urlReader.readLine()) != null) {
				result = currentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Home page: "+result);
		return result;
	}
	
	public static String getComputerName() {

		String hostname = "Unknown";

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		}
		return hostname;
	}
	
	public static String getIP() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		return ip.getHostAddress();
	}
	
	public static String getMAC() throws UnknownHostException, SocketException {
		InetAddress ip = InetAddress.getLocalHost();
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		StringBuilder macFormat = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			macFormat.append(String.format("%02X%s", mac[i], (i < mac.length -1) ? "-" : ""));
		}
		return macFormat.toString();
	}
}
