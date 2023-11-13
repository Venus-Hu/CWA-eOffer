package gov.gsa.sst.common;

import org.jboss.aerogear.security.otp.Totp;

import gov.gsa.sst.util.LoadProperties;

public class TOTPGGenerator {

	/*
	 * Method is used to get the TOTP based on the security token
	 *
	 * @return
	 */

	public static String getTwoFactorCode() {
		Totp totp = new Totp(LoadProperties.getProperty("eoffer.okta.otp").trim());
		String twoFactorcode = totp.now();
		System.out.println(twoFactorcode);
		return twoFactorcode;

	}

}
