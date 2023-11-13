package driver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TanFirefoxCertProfile extends FirefoxProfile {
	
	private Logger logger = LoggerFactory.getLogger(TanFirefoxCertProfile.class);

	//@Value("${driver.firefox.cert:#{null}}")
	private String cert = null;
	
	//@Value("${driver.firefox.key:#{null}}")
	private String key = null;

	public TanFirefoxCertProfile(String cert, String key)
	{
		super();
		this.cert = cert;
		this.key = key;
	}

	public File layoutOnDisk() {
		File profileDir = super.layoutOnDisk();

		try {
			File certFile = new File(cert);
			File keyFile = new File(key);
		
//		URL certFilePath = TanFirefoxCertProfile.class.getResource(cert);
//		URL keyFilePath = TanFirefoxCertProfile.class.getResource(key);
//			logger.info("certFilePath = " + certFilePath);
//			logger.info(" keyFilePath = " + keyFilePath);
//			certFile = new File(certFilePath.toURI());
//			keyFile = new File(keyFilePath.toURI());

			FileUtils.copyFileToDirectory(certFile, profileDir);
			FileUtils.copyFileToDirectory(keyFile, profileDir);
//		} catch (URISyntaxException e1) {
//			logger.error(e1.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return profileDir;
	}
}
