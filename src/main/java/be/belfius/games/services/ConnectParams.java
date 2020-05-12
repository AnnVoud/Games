package be.belfius.games.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectParams {
	private ConnectParams() {
	}

	public static Properties loadPropertiesFile() {
		Properties prop = new Properties();
		try (InputStream resourceAsStream = ConnectParams.class.getClassLoader()
				.getResourceAsStream("connectParams.properties")) {
			prop.load(resourceAsStream);
		} catch (IOException e) {
			System.err.println("Unable to load properties file : " + "connectParams.properties");
		}
		return prop;
	}

}
