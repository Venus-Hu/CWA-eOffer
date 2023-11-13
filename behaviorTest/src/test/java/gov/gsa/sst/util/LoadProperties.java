package gov.gsa.sst.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
	static private Properties testProperties = null;

	public static String getProperty(String key) {
		if (testProperties == null) {
			LoadProperties.loadProperties();
		}
		return testProperties.getProperty(key);
	}

	/**
	 * Read configuration values from the Application.properties file
	 */
	public static void loadProperties() {
		try {
			InputStream inputStream = LoadProperties.class.getClassLoader()
					.getResourceAsStream("config/Application.properties");

			InputStream driverProp = LoadProperties.class.getClassLoader()
					.getResourceAsStream("driver.properties");
			testProperties = new Properties();
			testProperties.load(driverProp);
			testProperties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns Application.properties 'execute.before' status
	 * 
	 * @return true if "before scenario" steps should be executed
	 */
	public static boolean executeBeforeSteps() {
		if (LoadProperties.getProperty("execute.before") == null
				|| LoadProperties.getProperty("execute.before").contains(
						"false")) {
			return false;
		}
		return true;
	}

	public static Object getAuthType() {
		// TODO Implement method
		return null;
	}

}
