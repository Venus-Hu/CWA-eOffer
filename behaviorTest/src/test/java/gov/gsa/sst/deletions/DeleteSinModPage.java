package gov.gsa.sst.deletions;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.TableElementUtils;

public class DeleteSinModPage extends Page {

	ExecutionContext executionContext;
	private String headerText = "//h1[contains(text(), 'Delete SIN(s)')]";
	private final int TIMEOUT_IN_SECS = 10;
	private static Logger log = LoggerFactory.getLogger(DeleteSinModPage.class);
	

	public DeleteSinModPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}
	
	@Override
	protected void load() {
		try {
			LeftNavigationMenu.navigateTo(driver, "Deletions");
		} catch (Exception ex) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Deletions");
		}
		try {
			executionContext.testPageFor508("Add SIN mod");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Could not load Deletions page for Delete SIN mod", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, By.xpath(headerText), TIMEOUT_IN_SECS);
	}

	/**
	 * 
	 * @param array array of sinDetails
	 */
	public void selectSinsToDelete(JsonArray array){
		array.forEach( element -> {
			String sin = element.getAsJsonObject().get("sin").getAsString();
			String date = element.getAsJsonObject().get("terminationDate").getAsString();
			if(date == null || date.isEmpty()){
				Date today = new Date();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
				date = LocalDateTime.from(today.toInstant().atZone(ZoneId.of("UTC"))).plusDays(1).format(formatter);
			}
			 log.info("Add Termination date: " + date );
			 WebElement sinRow = getRowBySinName(sin);
			 //select SIN to delete
			 sinRow.findElement(By.name("selectSin")).click();
			 addTerminationDate(sin, date);
		});
		Octo_SeleniumLibrary.clickElement(driver, By.name( "saveAndContinue"));
	}
	
	public void addTerminationDate(String sin, String date) {
		String[] dateArr = date.split("/");
		sin = sin.replace(" ", "");
		ActionByName.selectByValue(driver, "EndDateMonth" + sin, dateArr[0], TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "EndDateDay" + sin, dateArr[1], TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "EndDateYear" + sin, dateArr[2], TIMEOUT_IN_SECS);
	}
	
	public WebElement getRowBySinName(String sinName) {
		WebElement offersTable = ActionById.getElement(driver, "delSin", TIMEOUT_IN_SECS);
		WebElement sinRow = TableElementUtils.getTableRowByCellValue(offersTable, "SIN", sinName);
		return sinRow;
	}
}
