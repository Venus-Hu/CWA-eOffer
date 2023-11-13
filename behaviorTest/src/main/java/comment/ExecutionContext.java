package comment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.cucumber.listener.Reporter;
import com.deque.axe.AXE;
import com.deque.axe.AXE.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import driver.WebDriverFactory;
import exception.UnsupportedDriverException;
//import jdk.internal.module.Resources;
//import jdk.internal.reflect.Reflection;

public class ExecutionContext {
	private final Logger logger = LoggerFactory.getLogger(ExecutionContext.class);
	private List<JsonObject> jsonTestData = new ArrayList();
	private List<Integer> dataSetChangeTriggers = new ArrayList();
	private WebDriver driver;
	private Scenario currentScenario;
	private Map<String, Integer> scenarioCounter = new HashMap();
	private int currentDataSet = 0;
	private int scenariosCount = 0;
	private WebDriverFactory driverFactory;
	@Value("${driver.takeAllScreenshot:#{yes}}")
	private String takeAllScreenshot;
	@Value("${driver.location:#{null}}")
	private String driverLocation;
	@Value("${driver.takeAllVideo:#{null}}")
	private String takeAllVideo;
//	private ScreenRecorder screenRecorder;
	private Properties overRideDriverProps;
	int i = 0;
	int x = 0;

//	public void startRecording_NotUse() throws Exception {
//		File file = new File("build/videorecording/");
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = screenSize.width;
//		int height = screenSize.height;
//		Rectangle captureSize = new Rectangle(0, 0, width, height);
//		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
//				.getDefaultConfiguration();
//		this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize, new Format(
//				new Object[]{FormatKeys.MediaTypeKey, MediaType.FILE, FormatKeys.MimeTypeKey, "video/quicktime"}),
//				new Format(new Object[]{FormatKeys.MediaTypeKey, MediaType.VIDEO, FormatKeys.EncodingKey, "jpeg",
//						VideoFormatKeys.CompressorNameKey, "jpeg", VideoFormatKeys.DepthKey, 24,
//						FormatKeys.FrameRateKey, Rational.valueOf(15.0D), VideoFormatKeys.QualityKey, 1.0F,
//						FormatKeys.KeyFrameIntervalKey, 900}),
//				new Format(new Object[]{FormatKeys.MediaTypeKey, MediaType.VIDEO, FormatKeys.EncodingKey, "black",
//						FormatKeys.FrameRateKey, Rational.valueOf(30.0D)}),
//				(Format) null, file, this.currentScenario.getName());
//		this.screenRecorder.start();
//	}
//
//	public void stopRecording() throws Exception {
//		this.screenRecorder.stop();
//	}

	public ExecutionContext() throws UnsupportedDriverException {
	}

	public void init(WebDriverFactory webDriverFactory, Class clazz) {
		this.driverFactory = webDriverFactory;
		if (clazz.isAnnotationPresent(OverrideDriverProps.class)) {
			this.overRideDriverProps = new Properties();
			OverrideDriverProps annotationDrive = (OverrideDriverProps) clazz.getAnnotation(OverrideDriverProps.class);
			DriverProperty[] var4 = annotationDrive.properties();
			int var5 = var4.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				DriverProperty driverProperty = var4[var6];
				this.overRideDriverProps.put(driverProperty.key(), driverProperty.value());
			}
		} else {
			this.overRideDriverProps = new Properties();
		}

		this.takeAllScreenshot = this.overRideDriverProps.getProperty("driver.takeAllScreenshot",
				this.takeAllScreenshot);
		this.driverLocation = this.overRideDriverProps.getProperty("driver.location", this.driverLocation);
		this.takeAllVideo = this.overRideDriverProps.getProperty("driver.takeAllVideo", this.takeAllVideo);
	}

	@Before
	public void setUpScenario(Scenario scenario) throws UnsupportedDriverException {
		// This code will check the browser and only open new browser if there is no
		// any.
		if (this.driver == null) {
			this.driver = this.driverFactory.getDriver(this.overRideDriverProps);
		}
		if(scenariosCount ==0) {
			cleanUpScreenshot();
		}
		this.currentScenario = scenario;
		++this.scenariosCount;
		this.logger.info("Scenario Started: " + this.currentScenario.getName());
		if (this.dataSetChangeTriggers.contains(this.scenariosCount)) {
			++this.currentDataSet;
			this.scenarioCounter = new HashMap();
		}

		if (this.scenarioCounter.get(this.currentScenario.getName()) != null) {
			this.scenarioCounter.put(this.currentScenario.getName(),
					(Integer) this.scenarioCounter.get(this.currentScenario.getName()) + 1);
		} else {
			this.scenarioCounter.put(this.currentScenario.getName(), 1);
		}

//		try {
//			if (this.takeAllVideo != null) {
//				if (this.driverLocation.trim().equalsIgnoreCase("local")) {
//					this.logger.info("Recording started for local execution");
//					this.startRecording();
//				} else {
//					this.logger.info("Recording NOT applicable - Remote");
//				}
//			}
//		} catch (Exception var3) {
//			var3.printStackTrace();
//		}

	}

	public void takeScreenshot(WebDriver driver) throws IOException {
		try {
			if (driver instanceof TakesScreenshot) {
				byte[] screenshot = (byte[]) ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				this.currentScenario.embed(screenshot, "image/png");
			}
		} catch (WebDriverException var3) {
			System.err.println(var3.getMessage());
		} catch (ClassCastException var4) {
			var4.printStackTrace();
		}

	}

	@After
	public void tearDownScenario(Scenario scenario) {
		this.logger.info("Scenario Ended: " + scenario.getName() + " Status: " + scenario.getStatus());
		boolean takeSnapshot = this.takeAllScreenshot.equalsIgnoreCase("yes") ? true : scenario.isFailed();
		if(takeSnapshot) {
				takeSnapShot(driver, "target/extent-reports/screenshot/"+scenario.getName());
		}

		if (this.scenariosCount == (Integer) this.dataSetChangeTriggers.get(this.dataSetChangeTriggers.size() - 1)
				- 1) {
			this.logger.info("Last Scenarios - initiating clean up");
			this.logger.info("<--- Calling the Driver Quit --->");
			this.driver.quit();
			if (this.driver != null) {
				this.driver.quit();
				this.driver = null;
				this.driverFactory.cleanSharedInstance();
			}
		} else {
			this.driverFactory.closeDriver(this.driver);
			this.driver = null;
		}

//		try {
//			if (this.takeAllVideo != null && this.driverLocation.trim().equalsIgnoreCase("local")) {
//				this.stopRecording();
//				if (!this.takeAllVideo.trim().equalsIgnoreCase("yes") && !this.currentScenario.isFailed()) {
//					File directory = new File("build/videorecording/");
//					List<File> files = (List) FileUtils.listFiles(directory,
//							new WildcardFileFilter(this.currentScenario.getName() + "*.mov"), (IOFileFilter) null);
//					Iterator var5 = files.iterator();
//
//					while (var5.hasNext()) {
//						File file = (File) var5.next();
//						file.delete();
//					}
//				}
//			}
//		} catch (Exception var9) {
//			var9.printStackTrace();
//		}

	}
	
	/**
	 * This function will take screenshot
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 */
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) {
		try {
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
			
			// Call getScreenshotAs method to create image file
			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
			
			// Move image file to new destination
			File destinationPath = new File(System.getProperty("user.dir")+"/" + fileWithPath+ ".png");

			// Copy file at destination
			FileUtils.copyFile(SrcFile, destinationPath);
			
			Reporter.addScreenCaptureFromPath(destinationPath.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanUpScreenshot() {
		try {
			FileUtils.deleteDirectory(new File(System.getProperty("user.dir")+"/target/extent-reports/screenshot/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}

	public List<JsonObject> getJsonTestData() {
		return this.jsonTestData;
	}

	public JsonObject getCurrentDataSet() {
		return (JsonObject) this.jsonTestData.get(this.currentDataSet);
	}

	public JsonObject getCurrentScenarioObj() {

		if (this.getCurrentDataSet().has(this.currentScenario.getName().trim())) {
//			this.logger.info("current scenario:" + this.currentScenario.getName().trim());
			return this.getCurrentDataSet().get(this.currentScenario.getName().trim()).getAsJsonObject();
		} else {
			this.logger.info("In getScenario object for Scenario Name:" + this.currentScenario.getName().trim());
			this.logger.info("Trimming scenario from golean2 format:"
					+ this.currentScenario.getName().substring(this.currentScenario.getName().indexOf("-") + 2).trim());
			return this.getCurrentDataSet().get(
					this.currentScenario.getName().substring(this.currentScenario.getName().indexOf("-") + 2).trim())
					.getAsJsonObject();
		}
	}

	public JsonElement getDataElement(String key) {
		JsonElement result = null;
		if (key.indexOf(".") > 0) {
			String[] keyParts = key.split(".", 1000);
			result = ((JsonObject) this.jsonTestData.get(this.currentDataSet)).get(keyParts[0]).getAsJsonObject();

			for (int i = 1; i < keyParts.length; ++i) {
				result = result.getAsJsonObject().get(keyParts[i]).getAsJsonObject();
			}
		} else {
			result = ((JsonObject) this.jsonTestData.get(this.currentDataSet)).get(key).getAsJsonObject();
		}

		return result;
	}

	public JsonArray getDataArray(String key) {
		JsonArray result = null;
		if (key.indexOf(".") > 0) {
			String[] keyParts = key.split(".", 1000);
			JsonElement parent = ((JsonObject) this.jsonTestData.get(this.currentDataSet)).get(keyParts[0])
					.getAsJsonObject();

			for (int i = 1; i < keyParts.length - 1; ++i) {
				parent = parent.getAsJsonObject().get(keyParts[i]).getAsJsonObject();
			}

			result = parent.getAsJsonObject().get(keyParts[keyParts.length - 1]).getAsJsonArray();
		} else {
			result = ((JsonObject) this.jsonTestData.get(this.currentDataSet)).get(key).getAsJsonArray();
		}

		return result;
	}

	public JsonObject getDataArrayObject(String key) {
		JsonArray array = ((JsonObject) this.jsonTestData.get(this.currentDataSet)).get(key).getAsJsonArray();
		Integer i = (Integer) this.scenarioCounter.get(this.currentScenario.getName());
		JsonObject element = null;
		if (i != null) {
			element = array.get(i - 1).getAsJsonObject();
		}

		return element;
	}

	public String getDataArrayString(String key) {
		JsonArray array = ((JsonObject) this.jsonTestData.get(this.currentDataSet)).get(key).getAsJsonArray();
		Integer i = (Integer) this.scenarioCounter.get(this.currentScenario.getName());
		String element = null;
		if (i != null) {
			element = array.get(i - 1).getAsString();
		}

		return element;
	}

	public String getDataAsString(String key) {
		return ((JsonObject) this.jsonTestData.get(this.currentDataSet)).get(key).getAsString();
	}

	public List<Integer> getDataSetChangeTriggers() {
		return this.dataSetChangeTriggers;
	}

	public WebDriverFactory getDriverFactory() {
		return this.driverFactory;
	}

	public void setDriverFactory(WebDriverFactory driverFactory) {
		this.driverFactory = driverFactory;
	}

	public void testPageFor508(String pageName) throws IOException, Exception {
		boolean is508Ready = false;
		System.out.println("ExecutionContext:313: **508 testing started for the page: " + is508Ready);
		if (is508Ready) {
			if (this.driverLocation.trim().equalsIgnoreCase("local")) {

				System.out.println("Debug - getResource : " + this.getClass().getResource("/axe.min.js"));

				JSONObject responseJSON = (new Builder(this.driver, this.getClass().getResource("/axe.min.js")))
						.options("{runOnly: {type: \"tag\",values: [\"section508\"]}}").analyze();
				System.out.println("Line 3");
				JSONArray violations = responseJSON.getJSONArray("violations");
				System.out.println("Line 4");
				if (violations.length() != 0) {
					System.out.println("Line 5");
					File file = new File(
							"build/AccessibilityReport/508-violations_for_" + pageName + "_" + this.i + ".txt");
					System.out.println("Line 6");
					FileUtils.writeStringToFile(file, AXE.report(violations), "UTF-16");
					++this.i;
					System.out.println("Line 7");
					this.currentScenario
							.write("508 violations created in 508-violations_for_" + pageName + "_" + this.i + ".txt");
				} else {
					System.out.println("Line 8");
					this.currentScenario.write("508 test was performed and No Violations found with AXE");
				}
			} else {
				System.out.println("Line 9");
				this.currentScenario.write("508 test can only be executed in local and not via GRID");
				System.out.println("Line 10");
				this.logger.info("508 test can only be executed in local and not via GRID");
			}
		}

	}

	public void testPageForAllRules(String pageName) throws IOException, Exception {
		if (this.driverLocation.trim().equalsIgnoreCase("local")) {
			JSONObject responseJSON = (new Builder(this.driver, this.getClass().getResource("/axe.min.js"))).options(
					"{runOnly: {type: \"tag\",values: [\"wcag2a\", \"wcag2aa\",\"section508\",\"best-practice\"]}}")
					.analyze();
			JSONArray violations = responseJSON.getJSONArray("violations");
			if (violations.length() != 0) {
				File file = new File(
						"build/AccessibilityReport/AllRules-violations_for_" + pageName + "_" + this.x + ".txt");
				FileUtils.writeStringToFile(file, AXE.report(violations), "UTF-16");
				++this.i;
				this.currentScenario.write("Accessibility violations created in AllRules-violations_for_" + pageName
						+ "_" + this.x + ".txt");
			} else {
				this.currentScenario.write("AllRules test was performed and No Violations found with AXE");
			}
		} else {
			this.currentScenario.write("Accessibility test can only be executed in local and not via GRID");
			this.logger.info("Accessibility test can only be executed in local and not via GRID");
		}

	}

}
