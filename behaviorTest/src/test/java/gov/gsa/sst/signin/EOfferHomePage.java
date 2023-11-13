package gov.gsa.sst.signin;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.util.LoadProperties;

public class EOfferHomePage extends Page {

	private static Logger log = LoggerFactory.getLogger(EOfferHomePage.class);

	public EOfferHomePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		log.info("***called eOffer HomePage");
	}

	@Override
	protected boolean isLoaded() {
		if (LoadProperties.getProperty("environment").trim().equalsIgnoreCase("gsa"))
			return driver.getTitle().contains("eOffer/eMod Home");
		else
			return driver.getTitle().contains("Welcome");
	}

	@Override
	protected void load() {
		log.info("Going to website: "+LoadProperties.getProperty("web.url"));
		driver.get(LoadProperties.getProperty("web.url"));
		Assert.assertTrue("EOffer home page not loaded", isLoaded());
	}

	public void verifyBrokenLinks(WebDriver driver) {

		List<WebElement> links = driver.findElements(By.tagName("a"));
		List<String> failedLink = new ArrayList<String>();
		log.info("No. of links are " + links.size());

		for (int i = 0; i < links.size(); i++) {
			WebElement element = links.get(i);
			String url = element.getAttribute("href");
			if (!verifyLinks(url)) {
				failedLink.add(url);
			}
		}
		if (failedLink.size() > 0) {
			for (String failedUlr : failedLink) {
				log.info("*********" + failedUlr + " is a broken link");
			}
			Assert.fail();
		}
	}

	public boolean verifyLinks(String linkUrl) {

		try {
			if (linkUrl.equals("tel:1-866 472-9114") || linkUrl.equals("mailto:eoffer@gsa.gov")) {
				log.info(linkUrl + " - Skip");
				return true;
			}

			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(5000);
			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() >= 400) {
				log.info(linkUrl + " - " + httpURLConnect.getResponseMessage() + "is a broken link");
				return false;

			} else {
				log.info(linkUrl + " - " + httpURLConnect.getResponseMessage());
				return true;
			}

		} catch (Exception e) {
			return false;
		}
	}

}
