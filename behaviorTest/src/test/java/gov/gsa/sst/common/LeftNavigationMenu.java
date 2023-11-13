package gov.gsa.sst.common;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.google.gson.JsonArray;

import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionByXpath;

/**
 * Left-side menu navigation convenience class for eOffer/eMod
 */
public class LeftNavigationMenu {

	private static String LEFT_NAV_ANCHOR_XPATH = "//a[contains(text(),'#LEFT_NAV_MENU_ITEM_NAME')]";
	private static String LEFT_NAV_COMPLETE_XPATH = "//a[contains(text(),'#LEFT_NAV_MENU_ITEM_NAME')]//span[contains(text(),'Completed')]";
	private static String LEFT_NAV_INCOMPLETE_XPATH = "//a[contains(text(),'#LEFT_NAV_MENU_ITEM_NAME')]//span[contains(text(),'Incomplete')]";
	
	/**
	 * Verify the left-nav menu item status
	 * 
	 * @param leftNavMenuItemName e.g. 'Corporate Information'
	 * @param driver
	 * @return true if left-nav menu item is found
	 */
	public static boolean isStepComplete(WebDriver driver, String leftNavMenuItemName) {
		return LeftNavigationMenu.isStepComplete(driver, leftNavMenuItemName, 0);
	}

	/**
	 * Verify the left-nav menu item status
	 * 
	 * @param leftNavMenuItemName e.g. 'Corporate Information'
	 * @param driver
	 * @param timeOutSeconds seconds to wait for element
	 * @return true if left-nav menu item is found
	 */
	public static boolean isStepComplete(WebDriver driver, String leftNavMenuItemName, int timeOutSeconds) {
		boolean navCompleteDisplayed = ActionByXpath.isDisplayed(driver, LEFT_NAV_COMPLETE_XPATH.replace("#LEFT_NAV_MENU_ITEM_NAME", leftNavMenuItemName), timeOutSeconds);
		if (!navCompleteDisplayed) {
			boolean navIncompleteDisplayed = ActionByXpath.isDisplayed(driver, LEFT_NAV_INCOMPLETE_XPATH.replace("#LEFT_NAV_MENU_ITEM_NAME", leftNavMenuItemName), timeOutSeconds);
			if (!navIncompleteDisplayed) {
				throw new TestExecutionException("Neither 'nav_incomplete' or 'nav_complete' elements were found on page left navigation for '" + leftNavMenuItemName + "'");
			};
		}
		return navCompleteDisplayed;
	}
	
	/**
	 * Navigate to page using left-nav menu without waiting
	 * 
	 * @param leftNavMenuItemName e.g. 'Corporate Information'
	 * @param driver
	 */
	public static void navigateTo(WebDriver driver, String leftNavMenuItemName) {
		LeftNavigationMenu.navigateTo(driver, leftNavMenuItemName, 0);
	}
	
	/**
	 * Navigate to page using left-nav menu
	 * 
	 * @param leftNavMenuItemName e.g. 'Corporate Information'
	 * @param driver
	 * @param timeOutSeconds seconds to wait for element
	 */
	public static void navigateTo(WebDriver driver, String leftNavMenuItemName, int timeOutSeconds) {
    	Octo_SeleniumLibrary.clickElement(driver, By.xpath( LEFT_NAV_ANCHOR_XPATH.replace("#LEFT_NAV_MENU_ITEM_NAME", leftNavMenuItemName)));
	
	}

	/**
	 * Check if menu item is present
	 * @param driver
	 * @param leftNavMenuItemName e.g. 'Corporate Information'
	 * @return 
	 */
	public static boolean isMenuItemPresent(WebDriver driver, String leftNavMenuItemName) {
		return ActionByXpath.isDisplayed(driver, LEFT_NAV_ANCHOR_XPATH.replace("#LEFT_NAV_MENU_ITEM_NAME", leftNavMenuItemName), 1);
	}

	/**
	 * Given a JSON array of menu item names, return the 'completeness'
	 * of the step
	 * 
	 * @param driver
	 * @param menuItems e.g. JSON array of menu item names
	 * @return true if menu item name is complete, false otherwise
	 */
	public static Map<String, Boolean> areStepsComplete(WebDriver driver, JsonArray menuItems) {
		Map<String, Boolean> menuItemState = new HashMap<>();
		menuItems.forEach((menuItem) -> {
			String menuItemName = menuItem.getAsString();
			menuItemState.put(menuItemName, LeftNavigationMenu.isStepComplete(driver, menuItemName));
		});
		return menuItemState;
	}
}
