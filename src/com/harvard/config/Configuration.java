package com.harvard.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
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
}
