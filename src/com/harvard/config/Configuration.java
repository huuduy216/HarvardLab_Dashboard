package com.harvard.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
	private static final String URL_PATH = "C:\\Users\\Duy\\Desktop\\url.txt";
	
	/*
	 * Return a list with URL and their names
	 */
	public static List<String[]> readURL() {
		List<String[]> result = new ArrayList<String[]>();
		try (BufferedReader urlReader = new BufferedReader(new FileReader(URL_PATH))) {

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
}
