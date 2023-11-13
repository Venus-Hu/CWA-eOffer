package gov.gsa.sst.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import customUtility.Octo_SeleniumLibrary;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.dunspage.DunsPage;
import gov.gsa.sst.util.LoadProperties;
import util.ActionById;
import util.ActionByName;
import util.ActionByXpath;
import util.PageUtil;

public class SignIn {
	private static int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(DunsPage.class);

	public static void login(WebDriver driver, JsonObject object) {
		String module = object.get("module").getAsString();
		if ((LoadProperties.getProperty("environment") == null) || (LoadProperties.getProperty("environment").isEmpty())
				|| LoadProperties.getProperty("environment").trim().equalsIgnoreCase("cloud")) {
			Octo_SeleniumLibrary.clickElement(driver, By.name( "guestSignIneMods"));

			ActionByName.sendKeys(driver, "guestUserName", object.get("name").getAsString(), TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "guestUserEmail", object.get("email").getAsString(), TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "guestUserCompany", object.get("company").getAsString(), TIMEOUT_IN_SECS);
			ActionByName.sendKeys(driver, "guestUserPhone", object.get("phone").getAsString(), TIMEOUT_IN_SECS);
			if (module.equalsIgnoreCase("eoffer")) {
				ActionByName.selectByText(driver, "guestLoginModule", "Contract Offers (eOffers)", TIMEOUT_IN_SECS);
			} else
				ActionByName.selectByText(driver, "guestLoginModule", "Contract Modifications (eMods)",
						TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.id( "signInSubmit"));

		} else if (LoadProperties.getProperty("environment").trim().equalsIgnoreCase("gsa")) {
			acknowledgeTerms(driver);

			String appAccess = "signIneOffers";
			if (module.equalsIgnoreCase("eMod")) {
				appAccess = "signIneMods";
			}
			Octo_SeleniumLibrary.clickElement(driver, By.name( appAccess));
			/*
			 * eOffer/eMod MFA login implementation
			 */
			if (ActionByXpath.isDisplayed(driver, "//h2[@class='okta-form-title o-form-head']", TIMEOUT_IN_SECS)) {

//				ActionById.sendKeys(driver, "okta-signin-username", object.get("email").getAsString(), TIMEOUT_IN_SECS);
//				ActionById.sendKeys(driver, "okta-signin-password", object.get("password").getAsString(),
//						TIMEOUT_IN_SECS);

				ActionById.sendKeys(driver, "okta-signin-username",
						LoadProperties.getProperty("eoffereMod.userName").trim(), TIMEOUT_IN_SECS);
				ActionById.sendKeys(driver, "okta-signin-password",
						LoadProperties.getProperty("eoffereMod.password").trim(), TIMEOUT_IN_SECS);

				Octo_SeleniumLibrary.clickElement(driver, By.id( "okta-signin-submit"));
				if (ActionByXpath.isDisplayed(driver,
						"//input[@placeholder='Enter Code' and @name='answer'] | //input[@name='answer']",
						TIMEOUT_IN_SECS)) {
					ActionByXpath.sendKeys(driver,
							"//input[@placeholder='Enter Code' and @name='answer'] | //input[@name='answer']",
							TOTPGGenerator.getTwoFactorCode(), TIMEOUT_IN_SECS);
					Octo_SeleniumLibrary.clickElement(driver, By.xpath(
							"//input[@class='button button-primary' and @type='submit' and @value='Verify']"));
				}
				acknowledgeUEITerms(driver);

			} else if (module.equalsIgnoreCase("emod"))
				acknowledgeUEITerms(driver);
			// Octo_SeleniumLibrary.clickElement(driver, By.name( "signIneMods", TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.id( "inputDunsFormAlt"));
		}

		PageUtil.verifyTitleContains(driver, "Sign In - Enter UEI", TIMEOUT_IN_SECS);
	}

	public static void acknowledgeTerms(WebDriver driver) {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "ack"));
		Octo_SeleniumLibrary.clickElement(driver, By.id( "ackButton"));
	}

	public static void acknowledgeUEITerms(WebDriver driver) {
		if(ActionById.isDisplayed(driver, "ackButton", TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "ackButton"));
		}
	}

}
