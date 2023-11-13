package gov.gsa.sst.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpDownloadUtility {

	 private static final int BUFFER_SIZE = 4096;
	 private final static Logger logger = LoggerFactory.getLogger(HttpDownloadUtility.class);
	
	    /**
	     * @author sauravkumar
	     * Downloads a file from a URL by adding a JSESSIONID from the browser cookie
	     * Forms a url with combination or current url and property url
	     * @param fileURL HTTP URL of the file to be download
	     * @param saveDir reference to directory
	     * @throws IOException
	     */
	    public static void downloadFile(WebDriver driver,String fileURL, File saveDir)
	            throws IOException,MalformedURLException {
	    	
	    	logger.info("File url:"+fileURL);
	    	logger.info("saveDir:"+saveDir);
	    	logger.info("Current url:"+driver.getCurrentUrl());
	    	
	    	URL propertyUrl = new URL(LoadProperties.getProperty("eoffer.web.url"));
	    	URL formatCurrentUrl = new URL(driver.getCurrentUrl());
	    	logger.info("URL from property:"+propertyUrl.toString());
	    	logger.info("Current URl from page:"+formatCurrentUrl.toString());
	    	String urlForFileDownload = propertyUrl.getProtocol()+"://"+formatCurrentUrl.getHost()+":"+propertyUrl.getPort()+formatCurrentUrl.getPath().substring(0, formatCurrentUrl.getPath().lastIndexOf("/")+1)+fileURL;
	    	URL url = new URL(urlForFileDownload);
	    	
	    	logger.info("File download URL formed from current URL and property URL:"+url.toString());
	    	disableSSLCertificateChecking();
	    	HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
	        logger.info("JSESSIONID value:"+driver.manage().getCookieNamed("JSESSIONID").getValue());
	        httpConn.setRequestProperty("Cookie", "JSESSIONID="+driver.manage().getCookieNamed("JSESSIONID").getValue());
	        int responseCode = httpConn.getResponseCode();
	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 9,
	                            disposition.length());
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	 
	            logger.info("Content-Type = " + contentType);
	            logger.info("Content-Disposition = " + disposition);
	            logger.info("Content-Length = " + contentLength);
	            logger.info("fileName = " + fileName);
	 
	            // opens input stream from the HTTP connection
	            InputStream inputStream = httpConn.getInputStream();
	            String saveFilePath = saveDir.getPath() + File.separator + fileName.replace("\"", "");
	             
	            // opens an output stream to save into file
	            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
	 
	            int bytesRead = -1;
	            byte[] buffer = new byte[BUFFER_SIZE];
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	 
	            outputStream.close();
	            inputStream.close();
	 
	            logger.info("File downloaded");
	        } else {
	            logger.info("No file to download. Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
	    }
	    
	    /**
	     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to
	     * aid testing on a local box, not for use on production.
	     */
	    private static void disableSSLCertificateChecking() {
	        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

	        	@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					// not implemented					
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
					// not implemented
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
	        } };

	        try {
	            SSLContext sc = SSLContext.getInstance("TLS");
	            sc.init(null, trustAllCerts, new java.security.SecureRandom());
	            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	            // Fix to "java.security.cert.CertificateException: No subject alternative names matching IP address ..." exception 
	            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	            	public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            });
	        } catch (KeyManagementException e) {
	            logger.error(e.getLocalizedMessage());
	        } catch (NoSuchAlgorithmException e) {
	        	logger.error(e.getLocalizedMessage());
	        }
	    }
}
