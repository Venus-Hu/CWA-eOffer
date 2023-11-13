package apiPackage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;


public class APIUtility {

	
	
	
	/**
	 * this method will generate random number with given area code
	 * @return
	 * @throws IOException 
	 */
	public String generateRandomNumberApi(String areaCode) throws IOException {

		Random rand = new Random();
		int num2 = rand.nextInt(900000) + 1000000;
		String phoneNum = areaCode + num2;
		return phoneNum;
	}
	
	/**
	 * this method will generate random username for create new user call
	 * @throws IOException 
	 */
	public String generateRandomEmailApi() throws IOException {

		String randomText = "0123456789abcdefghijklmnopqrstuvwxyz";
		int randomInt = 5;
		String text = RandomStringUtils.random(randomInt, randomText);
		String randomEmail = (text + "@totalwine.com");
		
		return randomEmail;
	}
	
	public String generateRandomDate(int numOfFutureDays) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE,  numOfFutureDays);	
		return (dateFormat.format(c.getTime()));
	}
	
	public void waitSeconds(int secondsToWait) throws InterruptedException {
		Thread.sleep(secondsToWait * 1000);
	}
	
}
