package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;
	private final static String propertyFilePath = "./src/test/resources/config/config.properties";
	private static String browserType = null;

	public static void loadConfig() throws Throwable {

		try {
			FileInputStream fis;
			fis = new FileInputStream(propertyFilePath);
			properties = new Properties();
			try {
				properties.load(fis);
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	FileInputStream fis1;

	public Properties loadProperties() {
		try {
			fis1 = new FileInputStream("src/test/resources/config/config.properties");
			Properties prop = new Properties();
			prop.load(fis1);
			fis1.close();
			return prop;

		} catch (Exception e) {
			System.out.println("Config.properties file not found");
			return null;
		}

	}

	public static void setBrowserType(String browser) {
		browserType = browser;
	}

	public static String getBrowserType() throws Throwable {
		if (browserType != null)
			return browserType;
		else
			throw new RuntimeException("browser not specified in the testng.xml");
	}

//	public static String getBrowserType() {
//		String browser = properties.getProperty("browser");
//		Loggerload.info("Get property BrowserType");
//		if (browser != null)
//			return browser;
//		else
//			throw new RuntimeException("browser not specified in the Configuration.properties file.");
//	}
	public static String getApplicationUrl() {
		String url = properties.getProperty("url");
		if (url != null)
			return url;
		else
			throw new RuntimeException("url not specified in the Configuration.properties file.");
	}

	public static String getHomePage() {
		String homeurl = properties.getProperty("homepage");
		if (homeurl != null)
			return homeurl;
		else
			throw new RuntimeException("Homeurl not specified in the Configuration.properties file.");
	}

	public static String getDepositPage() {
		String depositUrl = properties.getProperty("depositpage");
		if (depositUrl != null)
			return depositUrl;
		else
			throw new RuntimeException("Depositurl not secified in the configuration.properties");
	}

	// Signin
	public static String getLoginPage() {
		String loginurl = properties.getProperty("loginpage");
		if (loginurl != null)
			return loginurl;
		else
			throw new RuntimeException("Homeurl not specified in the Configuration.properties file.");
	}

	public static String getexcelfilepath() {
		String excelfilelpath = properties.getProperty("Excelpath");
		if (excelfilelpath != null)
			return excelfilelpath;
		else
			throw new RuntimeException("Excel file path not specified in the Configuration.properties file.");
	}

	public static String getDepositSheetName() {
		String sheetname = properties.getProperty("sheetDeposit");
		if (sheetname != null)
			return sheetname;
		else
			throw new RuntimeException("sheetDeposit not specified in the Configuration.properties file.");
	}

	public static String getLoginSheetName() {
		String sheetname = properties.getProperty("loginSheet");
		if (sheetname != null)
			return sheetname;
		else
			throw new RuntimeException("Login Sheet not specified in the Configuration.properties file.");
	}

	public static String getnewCustomerPage() {
		String newCustomerPageUrl = properties.getProperty("newcustomerpage");
		if (newCustomerPageUrl != null)
			return newCustomerPageUrl;
		else
			throw new RuntimeException("newCustomerPage url not secified in the configuration.properties");
	}

	public static String getCustomerSheetName() {
		String sheetname = properties.getProperty("sheetCustomer");
		if (sheetname != null)
			return sheetname;
		else
			throw new RuntimeException("sheet Customer not specified in the Configuration.properties file.");
	}

}
