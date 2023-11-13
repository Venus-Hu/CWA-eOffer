package driver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.gargoylesoftware.htmlunit.WebClient;

import exception.UnsupportedDriverException;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	
	public static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

	private AtomicReference<WebDriver> SHARED_INSTANCE = new AtomicReference<>();
	
	private WebDriverFactory() {
		logger.info("Initiating driver factory - 1");	
	}


	
	public enum SupportedBrowser {
		 firefox, chrome
//		 ,html_unit, phantomjs,  edge
	}
	
	public enum DriverType {
		shared, regular
	}
	
	public enum DriverLocation {
		remote, local
	}
	
	public enum AuthType {
		client_cert, guest, password, na
	}
	
	//SupportedBrowser
	@Value("${driver.browser:#{null}}")
	private String browser;
	
	//shared, regular
	@Value("${driver.type:#{null}}")
	private String type;
	
	//remote, local
	@Value("${driver.location:#{null}}")
	private String location;
	
	//client cert, login, na
	@Value("${driver.auth.type:#{null}}")
	private String authType = null;
	
	@Value("${driver.auth.cert.path:#{null}}")
	private String certificatePath = null;
	
	@Value("${driver.auth.cert.pwd:#{null}}")
	private String certificatePwd = null;
	
	@Value("${driver.auth.cert.type:#{null}}")
	private String certificateType = null;

	@Value("${driver.remote.url:#{null}}")
	private String remotDriverUrl;
	
	@Value("${driver.remote.platform:#{null}}")
	private String remoteDriverPlatform = null;
	
	@Value("${driver.remote.browserversion:#{null}}")
	private String remoteBrowserVersion = null;
	
	@Value("${driver.proxy.url:#{null}}")
	private String proxyDriverUrl;
	
	@Value("${driver.mac.path:#{null}}")
	private String driverPath_Mac = null;
	
	@Value("${driver.windows.path:#{null}}")
	private String driverPath_Window = null;
	
	@Value("${driver.firefox.cert:#{null}}")
	private String cert = null;
	
	@Value("${driver.firefox.key:#{null}}")
	private String key = null;

	@Value("${driver.filedownloadPath:#{null}}")
	private String filedownloadPath = null;
	
	@Value("${driver.firefox.profilename:#{null}}")
	private String firefoxProfilename = null;

	@Value("${driver.firefox.marionette:#{null}}")
	private String marionette = null;
	
	@Value("${driver.chrome.profilepath:#{null}}")
	private String chromeProfilePath = null;	
	
	@Value("${driver.chrome.deviceName:#{null}}")
	private String deviceName = null;
	
	private String pathOfDriver;

	@Deprecated
	public WebDriver getDriver() throws UnsupportedDriverException {
		return getWebDriver();
	}

	public WebDriver getDriver(Properties properties) throws UnsupportedDriverException {
		browser = properties.getProperty("driver.browser", browser);
		type = properties.getProperty("driver.type", type);
		location = properties.getProperty("driver.location", location);
		authType = properties.getProperty("driver.auth.type", authType);
		certificatePath = properties.getProperty("driver.auth.cert.path", certificatePath);
		certificatePwd = properties.getProperty("driver.auth.cert.pwd", certificatePwd);
		certificateType = properties.getProperty("driver.auth.cert.type", certificateType);
		remotDriverUrl = properties.getProperty("driver.remote.url", remotDriverUrl);
		remoteDriverPlatform = properties.getProperty("driver.remote.platform", remoteDriverPlatform);
		remoteBrowserVersion = properties.getProperty("driver.remote.browserversion", remoteBrowserVersion);
		proxyDriverUrl = properties.getProperty("driver.proxy.url", proxyDriverUrl);
		driverPath_Mac = properties.getProperty("driver.mac.path", driverPath_Mac);
		driverPath_Window = properties.getProperty("driver.windows.path",driverPath_Window);
		cert = properties.getProperty("driver.firefox.cert", cert);
		key = properties.getProperty("driver.firefox.key", key);
		filedownloadPath = properties.getProperty("driver.filedownloadPath", filedownloadPath);
		firefoxProfilename = properties.getProperty("driver.firefox.profilename", firefoxProfilename);
		chromeProfilePath = properties.getProperty("driver.chrome.profilepath", chromeProfilePath);
		deviceName = properties.getProperty("driver.chrome.deviceName", deviceName);
		pathOfDriver = getDriverPathByOS();
		return getWebDriver();
	}
	
	private String getDriverPathByOS() {
		String os = System.getProperty("os.name").toLowerCase();
		logger.info("****os Name: "+os);
		if(os.contains("mac")) {
			return driverPath_Mac;
		}else{
			return driverPath_Window;
		}
		
	}

	private WebDriver getWebDriver() throws UnsupportedDriverException {
		logger.info("Initiating driver in getDriver()");
		DriverType driverType = getDriverType();
		WebDriver driver;
		
//		if (driverType.equals(DriverType.shared) && SHARED_INSTANCE.get() != null) {
//			driver = SHARED_INSTANCE.get();
//		} else {
			
			SupportedBrowser browserType = getBrowserType();
//			DriverLocation locationType = getLocationType();
//			AuthType auth = getAuthType();
//			URL remoteDriverUrlValue = null;

//			if(locationType.equals(DriverLocation.remote)) {
//				if(remotDriverUrl == null || remotDriverUrl.trim().length() < 1) {
//					throw new UnsupportedDriverException("Remote driver URL is required, configured as driver.remote.url");
//				} else {
//					try {
//						remoteDriverUrlValue = new URL(remotDriverUrl);
//					} catch (MalformedURLException e) {
//						throw new UnsupportedDriverException("Remote driver URL is required, configured as driver.remote.url. A valid URL is expected.");
//					}
//				}
//			}

			switch(browserType) {
			case firefox:
				driver = getFirefoxDriver();
				driver.manage().window().maximize();
				break;
			case chrome:
				driver = getChromeDriver();
				break;

			default:
				logger.info("***We Only Support Chrome and FireFox");
				driver = getChromeDriver();
				break;
			}

//			if (DriverType.shared.equals(driverType)) {
//				SHARED_INSTANCE.set(driver);
//			}
			final WebDriver returnedDriver = driver;
			/*Below method will close browser and quit driver when the test complete OR it terminated in-normally*/
			Runtime.getRuntime().addShutdownHook(new Thread(){

				@Override
				public void run() {
					super.run();
					returnedDriver.quit();
				}
			});
		

		return driver;
	}
	
	public void closeDriver(WebDriver driver)
	{
//		if (!DriverType.shared.name().equalsIgnoreCase(type.trim()))
//		{
			logger.info("WebDriverFactory - Quite the Driver");
			if(driver != null) {
				driver.quit();
			}
//		}
//		else
//		{
//			logger.info("WebDriverFactory - closeDriver - shared - do nothing");
//		}
	}
	

    public void cleanSharedInstance()
    {
		if (DriverType.shared.name().equalsIgnoreCase(type.trim()))
		{
           	SHARED_INSTANCE = null;
		}

    }

	private WebDriver getPhantomDriver_NotUse(DriverLocation locationType, AuthType auth, URL remoteDriverUrlValue) {
		
		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//		if(proxyDriverUrl!=null && proxyDriverUrl.trim().length()>0) {
//			logger.info("Setting up proxy since proxy url was declared");
//			capabilities = setProxy(capabilities);
//		}
//		if(auth.equals(AuthType.client_cert)) {
//			//TODO
//		} 
//		
//		String[] cli_args = new String[]{ "--ignore-ssl-errors=true","--proxy=localhost:8888" ,"--ssl-protocol=any"};
//		capabilities.setJavascriptEnabled(true);
//		capabilities.setCapability( PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cli_args );
//		capabilities.setCapability("takesScreenshot", true);
//        if(pathOfDriver != null && pathOfDriver.trim().length() > 0) {
//        	capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, pathOfDriver);
//        }else{
//			PhantomJsDriverManager.getInstance().setup();
//		}
//
		WebDriver driver;
		if(locationType.equals(DriverLocation.remote)) {
			driver = new RemoteWebDriver(remoteDriverUrlValue, capabilities);
	        ((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
		} else {
			driver = new PhantomJSDriver(capabilities);
		}
		
		return driver;
	}
	
	private WebDriver getHtmlUnitDriver_NotUse(DriverLocation locationType, AuthType auth, URL remoteDriverUrlValue) throws UnsupportedDriverException {
		
		DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
		if(proxyDriverUrl!=null && proxyDriverUrl.trim().length()>0) {
			logger.info("Setting up proxy since proxy url was declared");
			capabilities = setProxy(capabilities);
		}
		ScreenCaptureHtmlUnitDriver htmlDriver;
		capabilities.setJavascriptEnabled(true);
		if(auth.equals(AuthType.client_cert)) {
			if(certificatePath == null || certificatePwd == null || certificateType == null ||
					certificatePath.trim().length() < 1 || certificatePwd.trim().length() < 1 ||
					certificateType.trim().length() < 1) {
				throw new UnsupportedDriverException("driver.auth.cert.path, driver.auth.cert.pwd, driver.auth.cert.type are all expected.");
			}
			
			try {
				new File(certificatePath).toURI().toURL();
			} catch (MalformedURLException e) {
				throw new UnsupportedDriverException(e);
			}
			
			htmlDriver = new ScreenCaptureHtmlUnitDriver() {
				@Override
				protected WebClient modifyWebClient(WebClient webClient) {
					try {
						webClient.getOptions().setSSLClientCertificate(new File(certificatePath).toURI().toURL(), certificatePath, certificateType);
					} catch(MalformedURLException e) { 	//Handled above.  
					}
					webClient.getOptions().setUseInsecureSSL(true);
					return webClient;
				}
			};
			
			htmlDriver.setJavascriptEnabled(true);
		} else {
			htmlDriver = new ScreenCaptureHtmlUnitDriver();
			htmlDriver.setJavascriptEnabled(true);
		}
		
		WebDriver driver;
		if(locationType.equals(DriverLocation.remote)) {
			driver = new RemoteWebDriver(remoteDriverUrlValue, htmlDriver.getCapabilities());
	        ((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
		} else {
			driver = new ScreenCaptureHtmlUnitDriver(htmlDriver.getCapabilities());
		}
				
		return driver;
	}
	
	private WebDriver getChromeDriver() {
		WebDriver driver;
//		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		
		if(pathOfDriver != null && pathOfDriver.trim().length() > 0) {
			logger.info("Setting the chrome driver path to "+pathOfDriver.trim());
			System.setProperty("webdriver.chrome.driver", pathOfDriver.trim());

		}else
		{
			logger.info("***Since chrome driver path was not set - setting up using webdriver manager");
//			ChromeDriverManager.getInstance().setup();
		}

//		if(proxyDriverUrl!=null && proxyDriverUrl.trim().length()>0)
//		{
//			logger.info("Setting up proxy since proxy url was declared");
//			capabilities = setProxy(capabilities);
//		}
//
//		if(deviceName != null && deviceName.trim().length() > 0) {
//			logger.info("Setting chrome browser emulation mode to "+deviceName);
//			capabilities = setChromeOptionsForMobile(capabilities);
//		}else
//		{
//			logger.info("Setting chrome options and capabilities for regular browser execution");
//			capabilities = setChromeOptions(capabilities);
//			capabilities = setChromeCapabilities(capabilities);
//		}

//		if(locationType.equals(DriverLocation.remote)) {
//			capabilities = setRemoteCapabilities(capabilities);
//			logger.info("Printing out all the capabilities set for remote browser "+capabilities.toString());
//			driver = new RemoteWebDriver(remoteDriverUrlValue, capabilities);
//			logger.info("setting the setFileDetector to LocalFileDetector for remote environment for upload feature");
//			((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
//		} else {
//			logger.info("Printing out all the capabilities set for local browser "+capabilities.toString());
//			driver = new ChromeDriver();  //new ChromeDriver(capabilities);
//		}
			
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("start-maximized"); // open Browser in maximized mode
//			options.addArguments("disable-infobars"); // disabling infobars
//			options.addArguments("--disable-extensions"); // disabling extensions
//			options.addArguments("--disable-gpu"); // applicable to windows os only
//			options.addArguments("--no-sandbox"); // Bypass OS security model
//			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems

//			WebDriverManager.chromedriver().setup();
			
			/* set up for Chrome For Test*/
			
			System.setProperty("webdriver.chrome.driver", "ChromeForTest/chromedriver");
			options.setBinary("ChromeForTest/Google Chrome for Testing.app/Contents/MacOS/Google Chrome for Testing");
			
			
			driver = new ChromeDriver(options);
			
		return driver;
	}

	private DesiredCapabilities setProxy(DesiredCapabilities capabilities)
	{
		Proxy proxy = new Proxy();
			if(browser.equalsIgnoreCase("chrome"))
			{
				logger.info("Setting up the proxy for chrome browser as "+proxyDriverUrl.trim());
				proxy.setHttpProxy(proxyDriverUrl.trim());
				capabilities.setCapability("proxy", proxy);
			}else if(browser.equalsIgnoreCase("firefox"))
			{
				logger.info("Setting up the proxy for firefox browser as "+proxyDriverUrl.trim());
				proxy.setHttpProxy(proxyDriverUrl.trim());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
			}else
			{
				logger.info("Setting up the proxy "+proxyDriverUrl.trim());
				proxy.setHttpProxy(proxyDriverUrl.trim());
				capabilities.setCapability(CapabilityType.PROXY, proxy);
			}
		return  capabilities;
	}

	private DesiredCapabilities setRemoteCapabilities(DesiredCapabilities capabilities)
	{
		if(remoteDriverPlatform!=null)
		{
			logger.info("setting up remote driver platform as "+remoteDriverPlatform);
			switch(remoteDriverPlatform.toLowerCase().trim())
			{
				case "windows":capabilities.setPlatform(Platform.WINDOWS);
					break;
				case "linux" : capabilities.setPlatform(Platform.LINUX);
					break;
				case "any" : capabilities.setPlatform(Platform.ANY);
					break;
				case "unix" : capabilities.setPlatform(Platform.UNIX);
					break;
				case "mac" : capabilities.setPlatform(Platform.MAC);
					break;
				default: break;
			}
		}

		if(remoteBrowserVersion!=null)
		{
			logger.info("setting up remote browser version as "+remoteBrowserVersion);
			capabilities.setVersion(remoteBrowserVersion.trim());
		}

		return  capabilities;
	}

	private DesiredCapabilities setChromeOptions(DesiredCapabilities capabilities)
	{
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		if(filedownloadPath!=null && filedownloadPath.trim().length()>0)
		{
			logger.info("setting download path for chrome directory as :"+filedownloadPath);
			chromePrefs.put("download.default_directory", filedownloadPath);
		}
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("test-type");
		logger.info("setting chrome to maximized");
		options.addArguments("start-maximized");
		logger.info("setting chrome disable plugins");
		options.addArguments("disable-plugins");
		logger.info("setting chrome disable extensions");
		options.addArguments("disable-extensions");
		if (chromeProfilePath != null && chromeProfilePath.trim().length()>0)
		{
			logger.info("Setting chrome profile path since chrome profile was set in driver.properties to "+chromeProfilePath);
			options.addArguments("user-data-dir="+chromeProfilePath);
		}
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return capabilities;
	}

	private DesiredCapabilities setChromeOptionsForMobile(DesiredCapabilities capabilities)
	{
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", deviceName.trim());

		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		return capabilities;
	}

	private DesiredCapabilities setChromeCapabilities(DesiredCapabilities capabilities)
	{
		logger.info("setting SSL Certificate Error Handling in Chrome");
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		logger.info("setting screenshot capability");
		capabilities.setCapability("takesScreenshot", true);
		logger.info("setting mode to ignore certificate errors");
		capabilities.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));

		logger.info("setting javascript enabled");
		capabilities.setJavascriptEnabled(true);
		return capabilities;
	}


	
	private WebDriver getEdgeDriver_NotUse(DriverLocation locationType, URL remoteDriverUrlValue) {
		WebDriver driver;
		DesiredCapabilities capabilities = DesiredCapabilities.edge();

		if(pathOfDriver != null && pathOfDriver.trim().length() > 0) {
			System.setProperty("webdriver.edge.driver", pathOfDriver);
		}else
		{
//			EdgeDriverManager.getInstance().setup();
		}

		if(proxyDriverUrl!=null && proxyDriverUrl.trim().length()>0) {
			logger.info("Setting up proxy since proxy url was declared");
			capabilities = setProxy(capabilities);
		}

		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability("ignoreProtectedModeSettings", true);
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		//capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
		//capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
		//capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
		capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		capabilities.setJavascriptEnabled(true);

		EdgeOptions options = new EdgeOptions();
		options.setPageLoadStrategy("normal");
		capabilities.setCapability(EdgeOptions.CAPABILITY,options);
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability("javascriptEnabled",true);
		
		if(locationType.equals(DriverLocation.remote)) {
			capabilities = setRemoteCapabilities(capabilities);
			driver = new RemoteWebDriver(remoteDriverUrlValue, capabilities);
			((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
		} else {
			driver = new EdgeDriver(capabilities);
		}
		return driver;
	}
	
	private WebDriver getFirefoxDriver() {
		WebDriver driver;
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setJavascriptEnabled(true);
/*
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
*/
		capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT,true);
		capabilities.setCapability("marionette",true);
		capabilities.setCapability("acceptSslCerts",true);
		if(pathOfDriver != null && pathOfDriver.trim().length() > 0) {
            System.setProperty("webdriver.gecko.driver", pathOfDriver);
        }else
        {
//            FirefoxDriverManager.getInstance().setup();
        }

		if(proxyDriverUrl!=null && proxyDriverUrl.trim().length()>0) {
			logger.info("Setting up proxy since proxy url was declared");
			capabilities = setProxy(capabilities);
		}

//		capabilities.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile(auth));

//		if(locationType.equals(DriverLocation.remote)) {
//			capabilities = setRemoteCapabilities(capabilities);
//			driver = new RemoteWebDriver(remoteDriverUrlValue, capabilities);
//			((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
//		}else
//		{
			driver = new FirefoxDriver(capabilities);
//		}
		return driver;
	}
	
	public FirefoxProfile getFirefoxProfile(AuthType auth){

		FirefoxProfile profile = null;

		if (firefoxProfilename == null || firefoxProfilename.trim().length() == 0)
		{
			if(auth.equals(AuthType.client_cert)) {
				logger.info("Setting up cert and key based firefox profile");
				profile = new TanFirefoxCertProfile(cert, key);
			}else {
				logger.info("Setting up new empty profile for execution since no profile name was mentioned and doesn't require cert and key");
				profile = new FirefoxProfile();
			}

		}else if (firefoxProfilename != null && firefoxProfilename.trim().length() > 0)
		{
			ProfilesIni allProfiles = new ProfilesIni();
			logger.info("Setting up firefox profile from existing system "+firefoxProfilename.trim());
			profile = allProfiles.getProfile(firefoxProfilename.trim());
		}

		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);

		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		 if(filedownloadPath != null && filedownloadPath.trim().length() > 0)
		 {
			 profile.setPreference("browser.download.dir", filedownloadPath);
		 }
		profile.setPreference("javascript.enabled", true);
		profile.setPreference("browser.helperApps.neverAsk.openFile",
				"text/csv,application/x-msexcel,application/pdf,application/octet-stream,application/x-gzip,application/zip,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
"text/csv,application/x-msexcel,application/pdf,application/octet-stream,application/x-gzip,application/zip,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
		profile.setPreference("browser.helperApps.alwaysAsk.force", false);
		profile.setPreference("pdfjs.disabled", true);
		profile.setPreference("plugin.scan.Acrobat", "99.0");
		profile.setPreference("plugin.scan.plid.all", false);
		profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf");
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("browser.download.manager.focusWhenStarting", false);
		profile.setPreference("browser.download.manager.useWindow", false);
		profile.setPreference("browser.download.manager.showAlertOnComplete", false);
		profile.setPreference("browser.download.manager.closeWhenDone", false);
		return profile;
	}

	private DriverLocation getLocationType_NotUse() throws UnsupportedDriverException {
		DriverLocation locationType;
		if(location == null || location.trim().length() == 0) { 	
			locationType = DriverLocation.local;
		} else {
			locationType = DriverLocation.valueOf(location.trim().toLowerCase());
		}
		if(locationType == null) {
			throw new UnsupportedDriverException("Unsupported driver location. Supported types: remote, local");
		}
		logger.info("locationType: " + locationType);
		return locationType;
	}

	private AuthType getAuthType_NotUse() throws UnsupportedDriverException {
		AuthType auth;
		if(authType == null || authType.trim().length() == 0) { 	
			auth = AuthType.na;
		} else {
			auth = AuthType.valueOf(authType.trim().toLowerCase());
		}
		if(auth == null) {
			throw new UnsupportedDriverException("Unsupported auth type. Supported types: client_cert, password, na");
		}
		logger.info("auth: " + auth);
		return auth;
	}
	
	private DriverType getDriverType() throws UnsupportedDriverException {
		DriverType driverType;
		if(type == null || type.trim().length() == 0) {	
			driverType = DriverType.regular;
		} else {
			driverType = DriverType.valueOf(type.trim().toLowerCase());
		}
		if(driverType == null) {
			throw new UnsupportedDriverException("Unsupported driver type. Supported types: shared, regular");
		}
		logger.info("driverType: " + driverType);
		return driverType;
	}
	
	private SupportedBrowser getBrowserType() throws UnsupportedDriverException {
		SupportedBrowser browserType;
//		if(browser == null || browser.trim().length() == 0) { 	
//			browserType = SupportedBrowser.html_unit;
//		} else {
			browserType = SupportedBrowser.valueOf(browser.trim().toLowerCase());
//		}
		if(browserType == null) {
			throw new UnsupportedDriverException("Unsupported browser. Supported browsers: html_unit, firefox, chrome, phantomjs, internet_explorer, edge");
		}
		logger.info("browserType: " + browserType);
		return browserType;
	}
}
